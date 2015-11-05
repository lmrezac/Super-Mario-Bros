/*
 * Decompiled with CFR 0_102.
 */
package supermario.builder;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.ImageObserver;
import java.util.LinkedList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import supermario.builder.Action;
import supermario.builder.BuilderFrame;
import supermario.builder.Button;
import supermario.builder.GameListPanel;
import supermario.builder.IO;
import supermario.builder.Item;
import supermario.builder.ItemFitting;
import supermario.builder.Level;
import supermario.builder.PropertiesPanel;
import supermario.builder.TexturePacks;
import supermario.builder.Textures;
import supermario.builder.Warp;
import supermario.builder.itemPanels.BackgroundPanel;
import supermario.builder.itemPanels.BlocksPanel;
import supermario.builder.itemPanels.EnemiesPanel;
import supermario.builder.itemPanels.MiscPanel;
import supermario.builder.itemPanels.PipesPanel;
import supermario.builder.itemPanels.PlatformsPanel;
import supermario.builder.itemPanels.SolidsPanel;
import supermario.game.Game;
import supermario.game.Input;
import supermario.game.LevelLoader;

public final class LevelPanel
extends JPanel
implements MouseMotionListener,
MouseListener,
ComponentListener,
ChangeListener {
    private final BuilderFrame frame;
    public Level level;
    public Level[] levels;
    public double mouseX;
    public double mouseY;
    private boolean mouseOnPanel;
    private boolean justReleasedSlider;
    private boolean leftMouseDown;
    private boolean rightMouseDown;
    public double scaleFactor;
    private BasicStroke stroke;
    private static final double SCROLL_THRESHOLD = 0.25;
    private static final double MAX_SCROLL_SPEED = Game.xTiles * 2;
    private ScrollerThread scroller;
    private ColumnThread columnThread;
    private PulsingThread pulsingThread;
    public boolean modified;
    public Item item;
    public Item draggedItem;
    public Item quickReferenceItem;
    private int draggedItemXTile;
    private int draggedItemYTile;
    private int draggedXOffset;
    private int draggedYOffset;
    private int lastDragInsertPoint;
    public boolean ignoreDragInsertPoint;
    public int draggedItemRow;
    public int draggedItemCol;
    public static final int NO_ROW_LOCKED = -1;
    public static final int NO_COL_LOCKED = -1;
    private static final double TILE_SIZE = 8.0;
    private LinkedList<Item> warpItems;
    private LinkedList<Item> warpZoneItems;
    private LinkedList<Item> itemsBlockingInsert;
    private LinkedList<Item> checkpointColumns;

    public LevelPanel(BuilderFrame frame) {
        this.frame = frame;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addComponentListener(this);
        this.setBackground(Color.BLACK);
        this.draggedItemRow = -1;
        this.draggedItemCol = -1;
        this.warpItems = new LinkedList();
        this.warpZoneItems = new LinkedList();
        this.itemsBlockingInsert = new LinkedList();
        this.checkpointColumns = new LinkedList();
        this.levels = new Level[0];
        this.stroke = new BasicStroke(1.0f);
        this.startThreads();
    }

    private void startThreads() {
        this.scroller = new ScrollerThread();
        this.scroller.start();
        this.pulsingThread = new PulsingThread();
        this.pulsingThread.start();
    }

    public void stopThreads() {
        this.stopColumnThread();
        this.scroller.active = false;
        this.pulsingThread.active = false;
        LevelPanel levelPanel = this;
        synchronized (levelPanel) {
            this.notifyAll();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.level == null) {
            return;
        }
        this.scaleFactor = (double)this.getHeight() / (double)Game.renderHeight;
        if (this.scaleFactor < 1.0) {
            this.scaleFactor = 1.0;
        }
        Graphics2D g2D = (Graphics2D)g;
        this.drawLevel(g2D);
        if (this.checkpointColumns.size() > 0) {
            this.drawCheckpointColumns(g2D);
        }
        this.drawItem(g2D);
        if (this.frame.game.input.showGrid) {
            this.drawGrid(g2D);
        }
        if ((this.frame.game.input.lockDragRow && this.draggedItemRow != -1 || this.frame.game.input.lockDragColumn && this.draggedItemCol != -1) && this.item != null && this.item.draggable) {
            this.drawRowIndicator(g2D, this.getTilePixelsFromMouse(true));
        }
        if (this.frame.propertiesPanel.type != 1) {
            if (this.pulsingThread.waiting && this.inPulsingState()) {
                LevelPanel levelPanel = this;
                synchronized (levelPanel) {
                    this.notifyAll();
                }
            }
            this.drawPropertiesItem(g2D);
        }
        if (this.frame.game.input.screenMarker) {
            int screenX = (int)Math.round(this.scaleFactor * (double)Game.xTiles * 8.0);
            g2D.setColor(new Color(239, 228, 176));
            g2D.setStroke(this.stroke);
            g2D.drawLine(screenX, 0, screenX, this.getHeight());
        }
        this.drawWarpIDs(g2D);
        if (!(!this.mouseOnPanel || this.frame.levelSlider.getValueIsAdjusting() || this.justReleasedSlider)) {
            this.drawMouseTile(g2D);
        }
        this.blankRemaining(g2D);
        if (this.scroller != null) {
            this.scroller.lastRenderTime = System.currentTimeMillis() - this.scroller.startTime;
        }
    }

    private void shiftAllOutgoingWarpsOnward(int shiftLevel, boolean up) {
        for (int i = 0; i < this.levels.length; ++i) {
            if (this.levels[i].cliffDestLevel >= shiftLevel) {
                this.levels[i].cliffDestLevel = up ? ++this.levels[i].cliffDestLevel : --this.levels[i].cliffDestLevel;
            }
            if (this.levels[i].nextLevelNumber >= shiftLevel && this.levels[i].nextLevelNumber != 999) {
                this.levels[i].nextLevelNumber = up ? ++this.levels[i].nextLevelNumber : --this.levels[i].nextLevelNumber;
            }
            for (Warp tempWarp : this.levels[i].outgoingWarps) {
                if (tempWarp.destLevelNumber < shiftLevel) continue;
                if (up) {
                    ++tempWarp.destLevelNumber;
                    continue;
                }
                --tempWarp.destLevelNumber;
            }
        }
    }

    private void swapAllOutgoingWarpsLevels(int firstLevel, int secondLevel) {
        LinkedList<Warp> firstLevelWarps = new LinkedList<Warp>();
        LinkedList<Warp> secondLevelWarps = new LinkedList<Warp>();
        for (int i = 0; i < this.levels.length; ++i) {
            if (this.levels[i].nextLevelNumber == firstLevel) {
                this.levels[i].nextLevelNumber = secondLevel;
            } else if (this.levels[i].nextLevelNumber == secondLevel) {
                this.levels[i].nextLevelNumber = firstLevel;
            }
            if (this.levels[i].cliffDestLevel == firstLevel) {
                this.levels[i].cliffDestLevel = secondLevel;
            } else if (this.levels[i].cliffDestLevel == secondLevel) {
                this.levels[i].cliffDestLevel = firstLevel;
            }
            for (Warp tempWarp : this.levels[i].outgoingWarps) {
                if (tempWarp.destLevelNumber == firstLevel) {
                    firstLevelWarps.add(tempWarp);
                    continue;
                }
                if (tempWarp.destLevelNumber != secondLevel) continue;
                secondLevelWarps.add(tempWarp);
            }
        }
        for (Warp w2 : firstLevelWarps) {
            w2.destLevelNumber = secondLevel;
        }
        for (Warp w2 : secondLevelWarps) {
            w2.destLevelNumber = firstLevel;
        }
    }

    public void removeGame() {
        this.level = null;
        this.item = null;
        this.draggedItem = null;
        this.frame.io.lastAccessedFileName = "";
        this.frame.clearStacks();
        this.frame.io.hasWorkingPath = false;
        this.levels = new Level[0];
        this.frame.changePropertiesPanel(new PropertiesPanel(this.frame, 1, null));
        this.frame.gameListPanel.reset();
        this.frame.setTitle("Super Mario Bros. Game Builder");
        this.setBackground(Color.BLACK);
        this.updateSlider();
        this.repaint();
    }

    public boolean newLevel(int levelIndex, int copiedLevel) {
        if (this.levels.length >= 1000) {
            return false;
        }
        if (this.levels.length > 0) {
            int i;
            if (levelIndex == -1 || levelIndex >= this.levels.length - 1 && copiedLevel != this.levels.length - 2) {
                levelIndex = this.levels.length;
            } else {
                this.shiftAllOutgoingWarpsOnward(levelIndex, true);
            }
            Level[] newLevels = new Level[this.levels.length + 1];
            for (i = 0; i < levelIndex; ++i) {
                newLevels[i] = this.levels[i];
            }
            newLevels[levelIndex] = new Level(this.frame, copiedLevel);
            newLevels[levelIndex].levelNumber = levelIndex;
            newLevels[levelIndex].reassignWarpSourceLevelNumbers();
            for (i = levelIndex; i < this.levels.length; ++i) {
                newLevels[i + 1] = this.levels[i];
                ++newLevels[i + 1].levelNumber;
                newLevels[i + 1].reassignWarpSourceLevelNumbers();
            }
            this.levels = newLevels;
            this.level = this.levels[levelIndex];
            this.level.marioStartItem.insertInLevel(this.level.marioStartItem.xTile, this.level.marioStartItem.yTile);
            this.frame.gameListPanel.repopulate(levelIndex);
        } else {
            this.levels = new Level[1];
            this.levels[0] = new Level(this.frame, -1);
            this.levels[0].levelNumber = 0;
            this.level = this.levels[0];
            this.level.marioStartItem.insertInLevel(this.level.marioStartItem.xTile, this.level.marioStartItem.yTile);
            this.frame.gameListPanel.repopulate(0);
        }
        this.updateSlider();
        this.repaint();
        this.frame.updateColumnStatus();
        return true;
    }

    public boolean deleteLevel(int levelIndex) {
        int i;
        if (this.levels.length == 0 || levelIndex < 0) {
            return false;
        }
        int answer = JOptionPane.showConfirmDialog(this.frame.gameListPanel, "Are you sure you want to delete level " + (levelIndex + 1) + "?", "Delete Level", 0);
        if (answer != 0) {
            return false;
        }
        LinkedList<Warp> warpConflicts = new LinkedList<Warp>();
        for (int i2 = 0; i2 < this.levels.length; ++i2) {
            if (this.levels[i2].levelNumber == this.level.levelNumber) continue;
            if (this.levels[i2].cliffDestLevel == this.level.levelNumber) {
                warpConflicts.add(new Warp(true, false, this.levels[i2].levelNumber, -2, this.levels[i2].cliffDestLevel, this.levels[i2].cliffDestID, null));
            }
            if (this.levels[i2].nextLevelNumber == this.level.levelNumber) {
                warpConflicts.add(new Warp(true, false, this.levels[i2].levelNumber, -3, this.level.levelNumber, 0, null));
            }
            for (Warp w : this.levels[i2].outgoingWarps) {
                if (w.destLevelNumber != this.level.levelNumber) continue;
                warpConflicts.add(w);
            }
        }
        if (warpConflicts.size() > 0) {
            String prompt = "At least one warp outside this level is dependent on a warp inside it.\r\nRemoving this level will remove any dependent warps in the game.\r\nAre you sure you want to proceed?";
            Warp sampleWarp = (Warp)warpConflicts.getFirst();
            if (sampleWarp.item == null) {
                if (sampleWarp.sourceWarpID == -2) {
                    prompt = prompt + "\r\nAffected Warp: Cliff death in level " + (sampleWarp.sourceLevelNumber + 1);
                } else if (sampleWarp.sourceWarpID == -3) {
                    prompt = prompt + "\r\nAffected Warp: Next level warp in level " + (sampleWarp.sourceLevelNumber + 1);
                }
            } else {
                prompt = prompt + "\r\nAffected Warp: Level " + (sampleWarp.sourceLevelNumber + 1) + ", x=" + sampleWarp.item.xTile + ", y=" + sampleWarp.item.yTile;
            }
            answer = JOptionPane.showConfirmDialog(this.frame.gameListPanel, prompt, "Warp Dependency", 0, 2);
            this.leftMouseDown = false;
            this.rightMouseDown = false;
            if (answer != 0) {
                return false;
            }
            for (Warp w : warpConflicts) {
                this.levels[w.sourceLevelNumber].outgoingWarps.remove(w);
                w.clearOutgoing();
                if (w.item != null) continue;
                if (w.sourceWarpID == -2) {
                    this.levels[w.sourceLevelNumber].cliffDestLevel = -1;
                    continue;
                }
                if (w.sourceWarpID != -3) continue;
                this.levels[w.sourceLevelNumber].nextLevelNumber = -1;
                this.levels[w.sourceLevelNumber].changeLevelEndType(0, true);
            }
        }
        this.level = null;
        this.shiftAllOutgoingWarpsOnward(levelIndex + 1, false);
        Level[] newLevels = new Level[this.levels.length - 1];
        for (i = 0; i < levelIndex; ++i) {
            newLevels[i] = this.levels[i];
        }
        for (i = levelIndex + 1; i < this.levels.length; ++i) {
            --this.levels[i].levelNumber;
            newLevels[i - 1] = this.levels[i];
            newLevels[i - 1].reassignWarpSourceLevelNumbers();
        }
        this.levels = newLevels;
        this.frame.gameListPanel.repopulate(-1);
        this.repaint();
        this.updateSlider();
        this.frame.updateColumnStatus();
        return true;
    }

    public boolean shiftLevelUp(int levelIndex) {
        if (levelIndex <= 0) {
            return false;
        }
        Level tempLevel = this.levels[levelIndex - 1];
        ++tempLevel.levelNumber;
        --this.levels[levelIndex].levelNumber;
        this.levels[levelIndex - 1] = this.levels[levelIndex];
        this.levels[levelIndex] = tempLevel;
        this.levels[levelIndex].reassignWarpSourceLevelNumbers();
        this.levels[levelIndex - 1].reassignWarpSourceLevelNumbers();
        this.swapAllOutgoingWarpsLevels(levelIndex, levelIndex - 1);
        this.frame.gameListPanel.repopulate(levelIndex - 1);
        return true;
    }

    public boolean shiftLevelDown(int levelIndex) {
        if (levelIndex == this.levels.length - 1 || levelIndex == -1) {
            return false;
        }
        Level tempLevel = this.levels[levelIndex + 1];
        --tempLevel.levelNumber;
        ++this.levels[levelIndex].levelNumber;
        this.levels[levelIndex + 1] = this.levels[levelIndex];
        this.levels[levelIndex] = tempLevel;
        this.levels[levelIndex].reassignWarpSourceLevelNumbers();
        this.levels[levelIndex + 1].reassignWarpSourceLevelNumbers();
        this.swapAllOutgoingWarpsLevels(levelIndex, levelIndex + 1);
        this.frame.gameListPanel.repopulate(levelIndex + 1);
        return true;
    }

    public void findUnusedWarps() {
        DefaultListModel<String> trace = new DefaultListModel<String>();
        final LinkedList<Warp> matchedWarps = new LinkedList<Warp>();
        for (int i = 0; i < this.levels.length; ++i) {
            for (Warp w : this.levels[i].incomingWarps) {
                if (!this.unusedWarp(w)) continue;
                trace.addElement("Lvl " + (w.sourceLevelNumber + 1) + ": " + w.item.name + " (x:" + w.item.xTile + " y:" + w.item.yTile + ")");
                matchedWarps.add(w);
            }
        }
        if (matchedWarps.isEmpty()) {
            JOptionPane.showMessageDialog(this.frame.propertiesScrollPane, "There are no unused warps in the game.", "Unused Warps", 1);
        } else {
            final JList list = new JList(trace);
            list.addListSelectionListener(new ListSelectionListener(){

                @Override
                public void valueChanged(ListSelectionEvent e) {
                    Warp trackWarp = (Warp)matchedWarps.get(list.getSelectedIndex());
                    LevelPanel.this.findWarpSource(trackWarp);
                }
            });
            JScrollPane listPane = new JScrollPane(list, 20, 30);
            JOptionPane.showMessageDialog(this.frame.propertiesScrollPane, listPane, "Unused Warps", 1);
        }
    }

    private boolean unusedWarp(Warp warp) {
        for (int i = 0; i < this.levels.length; ++i) {
            if (this.levels[i].nextLevelNumber == warp.sourceLevelNumber && warp.sourceWarpID == 0) {
                return false;
            }
            if (this.levels[i].cliffDestLevel == warp.sourceLevelNumber && this.levels[i].cliffDestID == warp.sourceWarpID) {
                return false;
            }
            for (Warp tempWarp : this.levels[i].outgoingWarps) {
                if (tempWarp.destLevelNumber != warp.sourceLevelNumber || tempWarp.destWarpID != warp.sourceWarpID) continue;
                return false;
            }
        }
        return true;
    }

    public void traceWarps(int levelNumber) {
        this.traceWarps(this.level.getLevelWarp(false));
    }

    public void traceWarps(Warp warp) {
        DefaultListModel<String> trace = new DefaultListModel<String>();
        final LinkedList<Warp> matchedWarps = new LinkedList<Warp>();
        if (warp.sourceLevelNumber == 999) {
            trace.addElement("<html><b>Tracing Game Ending Warps</b></html>");
        } else if (warp.sourceWarpID == 0 || warp.sourceWarpID == 1000) {
            trace.addElement("<html><b>Tracing Level " + (warp.sourceLevelNumber + 1) + " Warps</b></html>");
        } else {
            trace.addElement("<html><b>Tracing Warp: Lvl: " + (warp.sourceLevelNumber + 1) + ", ID: " + warp.sourceWarpID + " (x:" + warp.item.xTile + " y:" + warp.item.yTile + ")</b></html>");
        }
        matchedWarps.add(warp);
        for (int i = 0; i < this.levels.length; ++i) {
            if (this.levels[i].nextLevelNumber == warp.sourceLevelNumber && warp.sourceWarpID == 0) {
                trace.addElement("Lvl " + (i + 1) + ": Next Level");
                Warp levelEndWarp = this.levels[i].getLevelWarp(false);
                levelEndWarp.sourceWarpID = 1000;
                matchedWarps.add(levelEndWarp);
            }
            if (this.levels[i].cliffDestLevel == warp.sourceLevelNumber && this.levels[i].cliffDestID == warp.sourceWarpID) {
                trace.addElement("Lvl " + (i + 1) + ": Cliff Warp");
                matchedWarps.add(this.levels[i].getLevelWarp(true));
            }
            for (Warp tempWarp : this.levels[i].outgoingWarps) {
                if (tempWarp.destLevelNumber != warp.sourceLevelNumber || tempWarp.destWarpID != warp.sourceWarpID) continue;
                trace.addElement("Lvl " + (tempWarp.sourceLevelNumber + 1) + ": " + tempWarp.item.name + " (x:" + tempWarp.item.xTile + " y:" + tempWarp.item.yTile + ")");
                matchedWarps.add(tempWarp);
            }
        }
        if (trace.size() == 1) {
            String message = warp.sourceLevelNumber == 999 ? (this.frame.levelPanel.levels.length == 0 ? "There are no levels to trace..." : "No warps in the game currently lead to this game's ending...\r\n") : (warp.sourceWarpID == 0 || warp.sourceWarpID == 1000 ? "No warps in the game currently lead to this level's start...\r\n(Level " + (warp.sourceLevelNumber + 1) + ")" : "No warps in the game currently lead to this warp...\r\n(ID " + warp.sourceWarpID + ")");
            JOptionPane.showMessageDialog(this.frame.propertiesScrollPane, message, "Warp Trace", 1, warp.item.button.placedImage);
        } else {
            final JList list = new JList(trace);
            list.addListSelectionListener(new ListSelectionListener(){

                @Override
                public void valueChanged(ListSelectionEvent e) {
                    Warp trackWarp = (Warp)matchedWarps.get(list.getSelectedIndex());
                    if (trackWarp.sourceLevelNumber != 999) {
                        LevelPanel.this.findWarpSource(trackWarp);
                    }
                }
            });
            JScrollPane listPane = new JScrollPane(list, 20, 30);
            JOptionPane.showMessageDialog(this.frame.propertiesScrollPane, listPane, "Warp Trace", 1, warp.item.button.placedImage);
        }
    }

    public void switchToLevel(int index) {
        if (index >= 0) {
            this.level = this.levels[index];
            this.placeLevel();
            this.updateSlider();
            this.setLevelScheme();
            this.frame.updateColumnStatus();
        }
    }

    public void setLevelScheme() {
        this.item = null;
        this.draggedItem = null;
        boolean changedTextures = this.level != null ? this.frame.game.texturePacks.setTexturePack(this.level.texturePack) : this.frame.game.texturePacks.setTexturePack(0);
        if (changedTextures) {
            this.frame.texturePacks.setTexturePack(this.level.texturePack);
            this.frame.backgroundPanel.refreshIcons();
            this.frame.blocksPanel.refreshIcons();
            this.frame.enemiesPanel.refreshIcons();
            this.frame.miscPanel.refreshIcons();
            this.frame.pipesPanel.refreshIcons();
            this.frame.platformsPanel.refreshIcons();
            this.frame.solidsPanel.refreshIcons();
        }
        if (this.level == null || this.level.levelType == 0 || this.level.levelType == 5) {
            this.setBackground(Color.BLACK);
            if (this.level == null) {
                this.frame.pipesPanel.setPipeColor(1);
            } else {
                this.frame.pipesPanel.setPipeColor(this.level.pipeColor);
            }
            this.frame.enemiesPanel.setLandEnemies();
            this.frame.enemiesPanel.setFirebarScheme();
            this.frame.enemiesPanel.setCannonScheme();
            this.frame.platformsPanel.setLandLevel();
            this.frame.miscPanel.setLandMode();
            this.frame.blocksPanel.setLandMode();
            this.frame.blocksPanel.setBlocksScheme(0);
            this.frame.backgroundPanel.setEnabledState(true);
        } else if (this.level.levelType == 1) {
            this.setBackground(Color.GRAY);
            this.frame.pipesPanel.setPipeColor(this.level.pipeColor);
            this.frame.enemiesPanel.setLandEnemies();
            this.frame.enemiesPanel.setFirebarScheme();
            this.frame.enemiesPanel.setCannonScheme();
            this.frame.platformsPanel.setLandLevel();
            this.frame.miscPanel.setLandMode();
            this.frame.blocksPanel.setLandMode();
            this.frame.blocksPanel.setBlocksScheme(this.level.levelType);
            this.frame.backgroundPanel.setEnabledState(true);
        } else if (this.level.levelType == 2) {
            this.setBackground(Color.GRAY);
            this.frame.pipesPanel.setPipeColor(this.level.pipeColor);
            this.frame.enemiesPanel.setLandEnemies();
            this.frame.enemiesPanel.setFirebarScheme();
            this.frame.enemiesPanel.setCannonScheme();
            this.frame.platformsPanel.setLandLevel();
            this.frame.miscPanel.setLandMode();
            this.frame.blocksPanel.setLandMode();
            this.frame.blocksPanel.setBlocksScheme(this.level.levelType);
            this.frame.backgroundPanel.setEnabledState(true);
        } else if (this.level.levelType == 3) {
            this.setBackground(Color.BLACK);
            this.frame.pipesPanel.setPipeColor(this.level.pipeColor);
            this.frame.enemiesPanel.setWaterEnemies();
            this.frame.enemiesPanel.setFirebarScheme();
            this.frame.enemiesPanel.setCannonScheme();
            this.frame.platformsPanel.setWaterLevel();
            this.frame.miscPanel.setWaterMode();
            this.frame.blocksPanel.setWaterMode();
            this.frame.blocksPanel.setBlocksScheme(this.level.levelType);
            this.frame.backgroundPanel.setEnabledState(false);
        } else if (this.level.levelType == 4 || this.level.levelType == 6) {
            this.setBackground(Color.GRAY);
            this.frame.pipesPanel.setPipeColor(this.level.pipeColor);
            this.frame.enemiesPanel.setLandEnemies();
            this.frame.enemiesPanel.setFirebarScheme();
            this.frame.enemiesPanel.setCannonScheme();
            this.frame.platformsPanel.setLandLevel();
            this.frame.miscPanel.setLandMode();
            this.frame.blocksPanel.setLandMode();
            this.frame.blocksPanel.setBlocksScheme(this.level.levelType);
            this.frame.backgroundPanel.setEnabledState(true);
        }
        this.repaint();
    }

    public void removeNonWaterItems() {
        for (int i = 0; i < this.level.items.length; ++i) {
            for (int j = 0; j < this.level.items[0].length; ++j) {
                if (this.level.items[i][j] == null || !this.frame.enemiesPanel.isLandEnemyOnly(this.level.items[i][j]) && !this.frame.backgroundPanel.isBackgroundDecoration(this.level.items[i][j]) && !this.frame.blocksPanel.isLandBlockOnly(this.level.items[i][j]) && !this.frame.platformsPanel.isLandOnlyPlatform(this.level.items[i][j]) && !this.frame.miscPanel.isLandItemOnly(this.level.items[i][j])) continue;
                this.level.items[i][j].removeFromLevel();
            }
        }
    }

    public void removeNonLandItems() {
        for (int i = 0; i < this.level.items.length; ++i) {
            for (int j = 0; j < this.level.items[0].length; ++j) {
                if (this.level.items[i][j] == null || !LevelLoader.isWaterEnemyOnly(this.level.items[i][j].character)) continue;
                this.level.items[i][j].removeFromLevel();
            }
        }
    }

    public void findItem(Item i) {
        int newLeftMostX = (int)Math.round((double)i.xTile * 8.0 + (double)(i.button.placedImage.getIconWidth() / 2) - (double)this.getWidth() / this.scaleFactor / 2.0);
        if ((double)newLeftMostX + (double)this.getWidth() / this.scaleFactor > (double)this.level.items[0].length * 8.0) {
            newLeftMostX = (int)Math.round((double)this.level.items[0].length * 8.0 - (double)this.getWidth() / this.scaleFactor);
        }
        if (newLeftMostX < 0) {
            newLeftMostX = 0;
        }
        this.level.leftMostX = newLeftMostX;
        this.repaint();
        this.updateSlider();
    }

    public void findItem(int sourceID) {
        if (this.level != null) {
            if (sourceID == 0) {
                this.findItem(this.level.marioStartItem);
            } else if (sourceID == 1000) {
                this.findItem(this.level.endItem);
            }
        }
    }

    public void findColumn(int columnNumber) {
        Item i = this.frame.miscPanel.insertColumn.item.copy();
        i.xTile = columnNumber;
        if (columnNumber == -2) {
            i.xTile = this.level.items[0].length - 1;
        }
        this.findItem(i);
    }

    public boolean itemOnScreen(Item i) {
        ImageIcon image = i.button.placedImage;
        if (image == null) {
            image = i.button.iconImage;
        }
        int rightMost = i.xTile * 8 + image.getIconWidth();
        int leftMost = i.xTile * 8;
        int levelWidth = (int)Math.round((double)this.getWidth() / this.scaleFactor);
        if ((double)rightMost > this.level.leftMostX && (double)leftMost < this.level.leftMostX + (double)levelWidth) {
            return true;
        }
        return false;
    }

    public void findWarpSource(Warp warp) {
        if (this.level.levelNumber != warp.sourceLevelNumber) {
            this.frame.gameListPanel.levelsList.setSelectedIndex(warp.sourceLevelNumber);
            this.frame.gameListPanel.levelsList.ensureIndexIsVisible(warp.sourceLevelNumber);
            this.switchToLevel(warp.sourceLevelNumber);
        }
        if (warp.sourceWarpID == 0 || warp.sourceWarpID == 1000) {
            this.findItem(warp.sourceWarpID);
            this.frame.changePropertiesPanel(new PropertiesPanel(this.frame, 2, null));
        } else {
            this.findItem(warp.item);
            this.frame.changePropertiesPanel(new PropertiesPanel(this.frame, warp.item.propertiesType, warp.item));
        }
        this.repaint();
        this.updateSlider();
    }

    public void findWarpDestination(Warp warp) {
        if (this.level.levelNumber != warp.destLevelNumber) {
            this.frame.gameListPanel.levelsList.setSelectedIndex(warp.destLevelNumber);
            this.frame.gameListPanel.levelsList.ensureIndexIsVisible(warp.destLevelNumber);
            this.switchToLevel(warp.destLevelNumber);
        }
        if (warp.destWarpID == 0) {
            this.findItem(warp.destWarpID);
            this.frame.changePropertiesPanel(new PropertiesPanel(this.frame, 2, this.level.marioStartItem));
        } else {
            Warp destWarp = this.levels[warp.destLevelNumber].incomingWarps.get(this.levels[warp.destLevelNumber].getWarpsIndex(warp.destWarpID));
            this.findItem(destWarp.item);
            this.frame.changePropertiesPanel(new PropertiesPanel(this.frame, destWarp.item.propertiesType, destWarp.item));
        }
        this.repaint();
        this.updateSlider();
    }

    private void drawMouseTile(Graphics2D g2D) {
        Composite oldComposite = g2D.getComposite();
        g2D.setComposite(AlphaComposite.getInstance(3, 0.5f));
        if (this.columnOperation() && !this.columnOperationPossible()) {
            g2D.setColor(Color.RED);
        } else {
            g2D.setColor(Color.WHITE);
        }
        Point mousePixels = this.getTilePixelsFromMouse(false);
        g2D.fillRect(mousePixels.x, mousePixels.y, (int)Math.ceil(8.0 * this.scaleFactor), (int)Math.ceil(8.0 * this.scaleFactor));
        g2D.setComposite(oldComposite);
    }

    public Point getTileFromPixels(double xPixel, double yPixel) {
        int xTile = (int)((xPixel / this.scaleFactor + this.level.leftMostX) / 8.0);
        int yTile = (int)(yPixel / this.scaleFactor / 8.0);
        return new Point(xTile, yTile);
    }

    public Point getTileFromMouse() {
        int xTile = (int)((this.mouseX / this.scaleFactor + this.level.leftMostX) / 8.0);
        int yTile = (int)(this.mouseY / this.scaleFactor / 8.0);
        return new Point(xTile, yTile);
    }

    private Point getTilePixelsFromMouse(boolean includeDragOffset) {
        double xTile = (int)((this.mouseX / this.scaleFactor + this.level.leftMostX) / 8.0);
        double yTile = (int)(this.mouseY / this.scaleFactor / 8.0);
        double xOffset = 0.0;
        double yOffset = 0.0;
        if (this.draggedItem != null && includeDragOffset) {
            xOffset = (double)this.draggedXOffset * 8.0 * this.scaleFactor;
            yOffset = (double)this.draggedYOffset * 8.0 * this.scaleFactor;
        }
        int xPixel = (int)Math.round(xTile * 8.0 * this.scaleFactor - this.level.leftMostX * this.scaleFactor - xOffset);
        int yPixel = (int)Math.round(yTile * 8.0 * this.scaleFactor - yOffset);
        return new Point(xPixel, yPixel);
    }

    private boolean ignoresDragRowLocking(char c) {
        return c == '\u260e' || c == '@' || c == 'z' || c == '#' || c == 'S';
    }

    private boolean ignoresDragColumnLocking(char c) {
        return c == '\u2642' || c == 'y' || c == 'j' || c == '\u25b2' || c == '\u00e6' || c == '`' || c == 'U' || c == '\u010e';
    }

    private void drawItem(Graphics2D g2D) {
        if (!(this.frame.levelSlider.getValueIsAdjusting() || this.justReleasedSlider)) {
            Point tile;
            Point pixels = this.getTilePixelsFromMouse(true);
            if (this.mouseOnPanel && this.item != null) {
                this.setItemTiles(this.item);
                tile = this.getTileFromMouse();
                if (this.item.draggable && this.frame.game.input.lockDragRow && this.draggedItemRow != -1) {
                    ItemFitting.itemCanFit(this.level, this.item, tile.x, this.draggedItemRow, this.itemsBlockingInsert);
                } else if (this.item.draggable && this.frame.game.input.lockDragColumn && this.draggedItemCol != -1) {
                    ItemFitting.itemCanFit(this.level, this.item, this.draggedItemCol, tile.y, this.itemsBlockingInsert);
                } else {
                    this.item.drawAtMouse(g2D, pixels.x, pixels.y, this.scaleFactor, 8.0, !ItemFitting.itemCanFit(this.level, this.item, tile.x, tile.y, this.itemsBlockingInsert));
                }
            } else if (this.draggedItem != null) {
                tile = this.getTileFromMouse();
                this.setItemTiles(this.draggedItem);
                tile.x-=this.draggedXOffset;
                tile.y-=this.draggedYOffset;
                this.draggedItem.drawAtMouse(g2D, pixels.x, pixels.y, this.scaleFactor, 8.0, !ItemFitting.itemCanFit(this.level, this.draggedItem, tile.x, tile.y, this.itemsBlockingInsert));
            }
            while (this.itemsBlockingInsert.size() > 0) {
                this.itemsBlockingInsert.remove().drawBlockingRectangle(g2D, this.scaleFactor, 8.0);
            }
        }
    }

    private void drawRowIndicator(Graphics2D g2D, Point p) {
        int width = (int)Math.round((double)this.item.button.placedImage.getIconWidth() * this.scaleFactor);
        int height = (int)Math.round((double)this.item.button.placedImage.getIconHeight() * this.scaleFactor);
        if (this.frame.game.input.lockDragRow) {
            p.y = (int)Math.round(((double)this.draggedItemRow * 8.0 - (double)this.item.verticalDrawOffset) * this.scaleFactor);
        } else {
            p.x = (int)Math.round((- this.level.leftMostX + (double)this.draggedItemCol * 8.0 - (double)this.item.horizontalDrawOffset) * this.scaleFactor);
        }
        g2D.setColor(Color.WHITE);
        g2D.setStroke(this.stroke);
        g2D.drawRect(p.x, p.y, width, height);
    }

    private void drawPropertiesItem(Graphics2D g2D) {
        if (this.inPulsingState()) {
            Item pulseItem = this.frame.propertiesPanel.item;
            g2D.setColor(Color.WHITE);
            Composite oldComposite = g2D.getComposite();
            g2D.setComposite(AlphaComposite.getInstance(3, this.pulsingThread.getAlpha()));
            int xPos = (int)Math.round(((double)(pulseItem.xTile * 8 - pulseItem.horizontalDrawOffset) - this.level.leftMostX) * this.scaleFactor);
            if (pulseItem.spaceRequirement == 1 && pulseItem.shifted && pulseItem.character != '\u00fd') {
                xPos-=(int)Math.round((double)((pulseItem.tilesWidth - 1) * 8) * this.scaleFactor);
            }
            int yPos = (int)Math.round((double)(pulseItem.yTile * 8 - pulseItem.verticalDrawOffset) * this.scaleFactor);
            int width = (int)Math.ceil((double)(pulseItem.tilesWidth * 8) * this.scaleFactor);
            int height = (int)Math.ceil((double)(pulseItem.tilesHeight * 8) * this.scaleFactor);
            g2D.fillRect(xPos, yPos, width, height);
            g2D.setComposite(oldComposite);
        }
    }

    private boolean inPulsingState() {
        return this.frame.propertiesPanel.item != null && this.frame.propertiesPanel.item.inserted && this.draggedItem != this.frame.propertiesPanel.item;
    }

    private void drawGrid(Graphics2D g2D) {
        g2D.setStroke(this.stroke);
       /* switch(this.level.levelType){
        case supermario.game.Level.LEVEL_TYPE_CASTLE:
        case supermario.game.Level.LEVEL_TYPE_COIN_ZONE_NIGHT:
        case supermario.game.Level.LEVEL_TYPE_GHOST_HOUSE:
        case supermario.game.Level.LEVEL_TYPE_OUTSIDE_NIGHT:
        case supermario.game.Level.LEVEL_TYPE_UNDERGROUND:
        	g2D.setColor(Color.GRAY);
        default:
        	g2D.setColor(this.getBackground());
        }*/
        g2D.setColor(Color.GRAY);
        //g2D.setColor(this.getBackground());
        g2D.setComposite(AlphaComposite.getInstance(3, 0.3f));
        this.drawXLines(g2D);
        this.drawYLines(g2D);
        g2D.setComposite(AlphaComposite.getInstance(3, 1.0f));
    }

    private void drawLevel(Graphics2D g2D) {
        int j;
        int j2;
        int rightMost;
        int i;
        int i2;
        if (this.level.levelType == 0 || this.level.levelType == 5) {
            g2D.setColor(this.frame.game.textures.skyBlue);
            g2D.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        } else if (this.level.levelType == 3) {
            g2D.setColor(this.frame.game.textures.skyBlue);
            g2D.fillRect(0, 0, this.getWidth(), (int)Math.ceil(32.0 * this.scaleFactor));
            g2D.setColor(this.frame.game.textures.waterBlue);
            g2D.fillRect(0, (int)Math.round(32.0 * this.scaleFactor), this.getWidth(), (int)Math.ceil((double)this.getHeight() - 32.0 * this.scaleFactor));
            double offset = this.level.leftMostX % 8.0 * this.scaleFactor;
            for (double i3 = - offset; i3 < ((double)this.getWidth() / this.scaleFactor / 8.0 + 1.0) * this.scaleFactor * 8.0; i3+=8.0 * this.scaleFactor) {
                g2D.drawImage(this.frame.game.textures.waves.getImage(), (int)Math.round(i3), (int)Math.round(24.0 * this.scaleFactor), (int)Math.ceil((double)this.frame.game.textures.waves.getIconWidth() * this.scaleFactor), (int)Math.ceil((double)this.frame.game.textures.waves.getIconHeight() * this.scaleFactor), null);
            }
        } else{// if (this.level.levelType == 1 || this.level.levelType == 4 || this.level.levelType == 2 || this.level.levelType == 6) {
            g2D.setColor(this.frame.game.textures.black);
            g2D.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        this.warpItems.clear();
        this.warpZoneItems.clear();
        int leftMost = (int)(this.level.leftMostX / 8.0) - 19;
        if (leftMost < 0) {
            leftMost = 0;
        }
        if ((rightMost = leftMost + (int)Math.ceil((double)this.getWidth() / 8.0 * this.scaleFactor) + 38) >= this.level.items[0].length) {
            rightMost = this.level.items[0].length - 1;
        }
        
        for (i = 0; i < this.level.items.length; ++i) {
            for (j = leftMost; j <= rightMost; ++j) {
                if (this.level.items[i][j] == null || this.level.items[i][j].yTile != i || this.level.items[i][j].xTile != j || this.level.items[i][j].spaceRequirement != 0 && this.level.items[i][j].spaceRequirement != 1) continue;
                this.level.items[i][j].drawInLevel(g2D, this.level.leftMostX, this.scaleFactor, 8.0, true);
            }
        }
        for (i = 0; i < this.level.items.length; ++i) {
            for (j = leftMost; j <= rightMost; ++j) {
                if (this.level.items[i][j] == null || this.level.items[i][j].yTile != i || this.level.items[i][j].xTile != j || this.level.items[i][j].spaceRequirement == 0 || this.level.items[i][j].spaceRequirement == 1) continue;
                this.level.items[i][j].drawInLevel(g2D, this.level.leftMostX, this.scaleFactor, 8.0, true);
                Item tempItem = this.level.items[i][j];
                if (tempItem.warp != null && tempItem.warp.incoming) {
                    this.warpItems.add(tempItem);
                    continue;
                }
                if (tempItem.character == '\u0108') {
                    this.warpZoneItems.add(tempItem);
                    continue;
                }
                if (tempItem.character != '\u0292' || tempItem.checkpointType != 1) continue;
                this.checkpointColumns.add(tempItem);
            }
        }
        for (j2 = leftMost - Game.xTiles * 2; j2 < leftMost; ++j2) {
            if (j2 < 0 || j2 >= this.level.items[0].length) continue;
            for (i2 = 0; i2 < this.level.items.length; ++i2) {
                if (this.level.items[i2][j2] == null || this.level.items[i2][j2].yTile != i2 || this.level.items[i2][j2].xTile != j2 || this.level.items[i2][j2].character != '\u00f7') continue;
                this.level.items[i2][j2].drawInLevel(g2D, this.level.leftMostX, this.scaleFactor, 8.0, true);
            }
        }
        for (j2 = rightMost + 1; j2 < rightMost + 1 + Game.xTiles * 2; ++j2) {
            if (j2 >= this.level.items[0].length) continue;
            for (i2 = 0; i2 < this.level.items.length; ++i2) {
                if (this.level.items[i2][j2] == null || this.level.items[i2][j2].yTile != i2 || this.level.items[i2][j2].xTile != j2 || this.level.items[i2][j2].character != '\u00f7') continue;
                this.level.items[i2][j2].drawInLevel(g2D, this.level.leftMostX, this.scaleFactor, 8.0, true);
            }
        }
    }

    private void drawCheckpointColumns(Graphics2D g2D) {
        Composite oldComposite = g2D.getComposite();
        g2D.setComposite(AlphaComposite.getInstance(3, 0.4f));
        while (this.checkpointColumns.size() > 0) {
            Item tempItem = this.checkpointColumns.remove();
            double xPixel = ((double)tempItem.xTile * 8.0 - this.level.leftMostX) * this.scaleFactor;
            double width = (double)this.frame.textures.displayCheckPointColumn.getIconWidth() * this.scaleFactor;
            g2D.drawImage(this.frame.textures.displayCheckPointColumn.getImage(), (int)Math.round(xPixel), 0, (int)Math.round(width), (int)Math.round((double)Game.renderHeight * this.scaleFactor), null);
        }
        g2D.setComposite(oldComposite);
    }

    private void drawWarpIDs(Graphics2D g2D) {
        Point tile;
        double yCenter;
        double xCenter;
        Item tempItem;
        while (this.warpItems.size() > 0) {
            tempItem = this.warpItems.remove();
            xCenter = ((double)tempItem.xTile * 8.0 + (double)(tempItem.button.placedImage.getIconWidth() / 2) - this.level.leftMostX) * this.scaleFactor;
            yCenter = ((double)tempItem.yTile * 8.0 + (double)(tempItem.button.placedImage.getIconHeight() / 2)) * this.scaleFactor;
            tempItem.drawNumber(g2D, tempItem.warp.sourceWarpID, xCenter, yCenter, this.scaleFactor, 8.0);
        }
        while (this.warpZoneItems.size() > 0) {
            tempItem = this.warpZoneItems.remove();
            if (tempItem.displayWarpNumber == -1) continue;
            xCenter = ((double)tempItem.xTile * 8.0 + (double)(tempItem.button.placedImage.getIconWidth() / 2) - this.level.leftMostX) * this.scaleFactor;
            yCenter = ((double)tempItem.yTile * 8.0 - 24.0 + 4.0) * this.scaleFactor;
            tempItem.drawNumber(g2D, tempItem.displayWarpNumber, xCenter, yCenter, this.scaleFactor, 8.0);
        }
        if (this.draggedItem != null && this.draggedItem.warp != null && this.draggedItem.warp.incoming) {
            tile = this.getTileFromMouse();
            tile.y = this.draggedItem.requiredRow != -1 ? this.draggedItem.requiredRow : (tile.y-=this.draggedYOffset);
            tile.x-=this.draggedXOffset;
            xCenter = ((double)tile.x * 8.0 + (double)(this.draggedItem.button.placedImage.getIconWidth() / 2) - this.level.leftMostX) * this.scaleFactor;
            yCenter = ((double)tile.y * 8.0 + (double)(this.draggedItem.button.placedImage.getIconHeight() / 2)) * this.scaleFactor;
            this.draggedItem.drawNumber(g2D, this.draggedItem.warp.sourceWarpID, xCenter, yCenter, this.scaleFactor, 8.0);
        } else if (this.draggedItem != null && this.draggedItem.character == '\u0108' && this.draggedItem.displayWarpNumber != -1) {
            tile = this.getTileFromMouse();
            tile.x-=this.draggedXOffset;
            tile.y-=this.draggedYOffset;
            xCenter = ((double)tile.x * 8.0 + (double)(this.draggedItem.button.placedImage.getIconWidth() / 2) - this.level.leftMostX) * this.scaleFactor;
            yCenter = ((double)tile.y * 8.0 - 20.0) * this.scaleFactor;
            this.draggedItem.drawNumber(g2D, this.draggedItem.displayWarpNumber, xCenter, yCenter, this.scaleFactor, 8.0);
        }
    }

    private void drawXLines(Graphics2D g2D) {
        double yPos = 0.0;
        int i = 0;
        while (i <= this.getHeight()) {
            g2D.drawLine(0, i, this.getWidth(), i);
            i = (int)Math.round(yPos+=8.0 * this.scaleFactor);
        }
        g2D.drawLine(0, this.getHeight() - 1, this.getWidth(), this.getHeight() - 1);
    }

    private void drawYLines(Graphics2D g2D) {
        double xPos = (- this.level.leftMostX * this.scaleFactor) % (8.0 * this.scaleFactor);
        int i = (int)Math.round(xPos);
        while (i < this.getWidth()) {
            g2D.drawLine(i, 0, i, this.getHeight());
            i = (int)Math.round(xPos+=8.0 * this.scaleFactor);
        }
    }

    private void blankRemaining(Graphics2D g2D) {
        if (this.fitsWithoutScrolling()) {
            double left = (double)this.level.items[0].length * 8.0 * this.scaleFactor;
            int width = (int)Math.round((double)this.level.items[0].length * 8.0 * this.scaleFactor - this.level.leftMostX * this.scaleFactor + (double)this.getWidth());
            g2D.setColor(this.getBackground());
            g2D.fillRect((int)Math.round(left), 0, width, this.getHeight());
        }
    }

    private boolean fitsWithoutScrolling() {
        if (this.level == null) {
            return true;
        }
        return this.getWidth() >= (int)Math.round((double)this.level.items[0].length * 8.0 * this.scaleFactor);
    }

    public Point getRequiredTile(Item tempItem, boolean ignoreRowRequirements) {
        int xTile = (int)((this.mouseX / this.scaleFactor + this.level.leftMostX) / 8.0);
        int yTile = (int)(this.mouseY / this.scaleFactor / 8.0);
        if (tempItem == null || tempItem.requiredRow == -1 || ignoreRowRequirements) {
            return new Point(xTile, yTile);
        }
        return new Point(xTile, tempItem.requiredRow);
    }

    private void setMousePoint(Point p) {
        if (this.level == null) {
            return;
        }
        this.justReleasedSlider = false;
        this.mouseX = p.x;
        this.mouseY = p.y;
        this.setStatusBar();
    }

    private void setStatusBar() {
        Point tiles = this.getRequiredTile(this.item, false);
        int xTile = tiles.x;
        int yTile = tiles.y;
        String itemDescription = "Empty";
        if (this.tileInRange(xTile, yTile) && this.level != null && this.level.items[yTile][xTile] != null) {
            itemDescription = this.level.items[yTile][xTile].name;
        }
        if (this.level != null && xTile >= this.level.items[0].length) {
            if (!this.frame.statusBar.getText().isEmpty() && this.frame.statusBar.getText().substring(0, 1).equalsIgnoreCase("X")) {
                this.frame.statusBar.setText("");
            }
        } else if (this.draggedItem != null) {
            this.frame.statusBar.setText("X: " + this.draggedItem.xTile + "   Y: " + this.draggedItem.yTile + "  Item: " + itemDescription);
        } else {
            this.frame.statusBar.setText("X: " + xTile + "   Y: " + yTile + "  Item: " + itemDescription);
        }
    }

    private boolean tileInRange(int xTile, int yTile) {
        if (this.level == null) {
            return false;
        }
        if (xTile < 0 || xTile >= this.level.items[0].length) {
            return false;
        }
        if (yTile < 0 || yTile >= this.level.items.length) {
            return false;
        }
        return true;
    }

    public boolean columnOperationPossible() {
        Point p = this.getTileFromMouse();
        this.itemsBlockingInsert.clear();
        if (this.item.spaceRequirement == 15) {
            if (p.x == 0 || p.x == this.level.items[0].length - 1) {
                return true;
            }
            if (p.x > 0 && p.x < this.level.items[0].length && this.isBlankColumn(p.x) && !ItemFitting.isInReservedColumn(this.level, p.x, p.y) && this.level.items.length < 1000000) {
                return true;
            }
            return false;
        }
        if (this.item.spaceRequirement == 16) {
            return p.x >= 0 && p.x < this.level.items[0].length && (this.level.levelEndType == 0 && this.level.items[0].length > Game.xTiles || this.level.items[0].length > Game.xTiles + 4) && this.isBlankColumn(p.x);
        }
        return false;
    }

    private synchronized void insertLevelColumn() {
        Point p = this.getTileFromMouse();
        if (p.x == 0) {
            this.level.insertAtColumn(-1);
            this.frame.undoableActionMade(Action.columnInserted(p.x));
        } else if (p.x == this.level.items[0].length - 1) {
            this.level.insertAtColumn(-2);
            this.frame.undoableActionMade(Action.columnInserted(-2));
        } else if (p.x > 0 && p.x < this.level.items[0].length && this.isBlankColumn(p.x)) {
            this.level.insertAtColumn(p.x);
            this.frame.undoableActionMade(Action.columnInserted(p.x));
        } else if (p.x >= this.level.items[0].length) {
            this.level.insertAtColumn(-2);
            this.frame.undoableActionMade(Action.columnInserted(-2));
        }
        this.placeLevel();
        this.updateSlider();
        this.repaint();
    }

    private synchronized void removeLevelColumn() {
        Point p = this.getTileFromMouse();
        if (p.x >= 0 && p.x < this.level.items[0].length && this.isBlankColumn(p.x) && !ItemFitting.isInReservedColumn(this.level, p.x, p.y) && (this.level.levelEndType == 0 && this.level.items[0].length > Game.xTiles || this.level.levelEndType != 0 && this.level.items[0].length > Game.xTiles + 4)) {
            this.level.removeAtColumn(p.x);
            this.frame.undoableActionMade(Action.columnRemoved(p.x));
        }
        this.placeLevel();
        this.updateSlider();
        this.repaint();
    }

    public boolean isBlankColumn(int column) {
        if (column >= this.level.items[0].length) {
            return true;
        }
        for (int i = 0; i < this.level.items.length; ++i) {
            if (this.level.items[i][column] == null || this.itemsBlockingInsert.contains(this.level.items[i][column])) continue;
            this.itemsBlockingInsert.add(this.level.items[i][column]);
        }
        return this.itemsBlockingInsert.isEmpty();
    }

    private void insertItemInLevel(Item tempItem, boolean freshItem) {
        this.setItemTiles(tempItem);
        if (tempItem.spaceRequirement == 15) {
            this.insertLevelColumn();
        } else if (tempItem.spaceRequirement == 16) {
            this.removeLevelColumn();
        } else {
            this.insertItemInLevel(tempItem, tempItem.xTile, tempItem.yTile, freshItem);
        }
    }

    public boolean assignMandatoryWarpData(Item tempItem) {
        if (tempItem.spaceRequirement == 13) {
            tempItem.warp.sourceLevelNumber = this.level.levelNumber;
            tempItem.warp.outgoing = true;
            tempItem.warp.destLevelNumber = 0;
            tempItem.warp.destWarpID = 0;
            this.level.outgoingWarps.add(tempItem.warp);
            return true;
        }
        if (tempItem.character == '\u03e4') {
            tempItem.warp.sourceLevelNumber = this.level.levelNumber;
            tempItem.warp.sourceWarpID = this.level.getNextAvailableWarpID();
            tempItem.warp.incoming = true;
            tempItem.warp.destLevelNumber = 0;
            tempItem.warp.destWarpID = 0;
            return this.level.insertIncomingWarp(tempItem.warp);
        }
        if (tempItem.character == '\u00bd') {
            tempItem.warp.sourceLevelNumber = this.level.levelNumber;
            tempItem.warp.sourceWarpID = this.level.getNextAvailableWarpID();
            tempItem.warp.incoming = true;
            tempItem.warp.destLevelNumber = 0;
            tempItem.warp.destWarpID = 0;
            return this.level.insertIncomingWarp(tempItem.warp);
        }
        if (LevelLoader.isBeanstalkBlock(tempItem.character)) {
            tempItem.warp.sourceLevelNumber = this.level.levelNumber;
            tempItem.warp.outgoing = true;
            tempItem.warp.destLevelNumber = 0;
            tempItem.warp.destWarpID = 0;
            this.level.outgoingWarps.add(tempItem.warp);
            return true;
        }
        return true;
    }

    private void insertItemInLevel(Item tempItem, int xTile, int yTile, boolean freshItem) {
        if (tempItem != null && xTile < this.level.items[0].length) {
            Item copy = tempItem.copy();
            if (copy.requiredRow != -1) {
                yTile = copy.requiredRow;
            } else if (this.frame.game.input.lockDragRow && tempItem.draggable) {
                if (this.ignoreDragInsertPoint) {
                    return;
                }
                if (!(!freshItem || this.ignoresDragRowLocking(tempItem.character) || this.columnOperation())) {
                    if (this.draggedItemRow == -1) {
                        this.draggedItemRow = yTile;
                    } else {
                        yTile = this.draggedItemRow;
                    }
                }
            } else if (this.frame.game.input.lockDragColumn && tempItem.draggable) {
                if (this.ignoreDragInsertPoint) {
                    return;
                }
                if (!(!freshItem || this.ignoresDragColumnLocking(tempItem.character) || this.columnOperation())) {
                    if (this.draggedItemCol == -1) {
                        this.draggedItemCol = xTile;
                    } else {
                        xTile = this.draggedItemCol;
                    }
                }
            }
            tempItem.xTile = xTile;
            tempItem.yTile = yTile;
            if (ItemFitting.itemCanFit(this.level, tempItem, xTile, yTile, this.itemsBlockingInsert)) {
                copy.inserted = true;
                if (freshItem) {
                    boolean fits = this.assignMandatoryWarpData(copy);
                    if (!fits) {
                        JOptionPane.showMessageDialog(this.frame, "The max warps in this level have been reached. What kind of game are you making?!", "Max Warps Reached", 2);
                        return;
                    }
                    if (tempItem.character == 'k') {
                        this.frame.undoableActionMade(Action.itemMoved(this.level.marioStartItem, this.level.marioStartItem.warp, this.level.marioStartItem.xTile, this.level.marioStartItem.yTile, xTile, yTile));
                    } else {
                        this.frame.undoableActionMade(Action.itemInserted(copy, copy.warp));
                    }
                }
                if (copy.character == 'k') {
                    if (this.draggedItem == null) {
                        this.level.marioStartItem.removeFromLevel();
                    } else if (this.draggedItemXTile != xTile || this.draggedItemYTile != yTile) {
                        this.frame.undoableActionMade(Action.itemMoved(this.draggedItem, copy.warp, this.draggedItemXTile, this.draggedItemYTile, xTile, yTile));
                    }
                    this.level.marioStartItem.xTile = xTile;
                    this.level.marioStartItem.yTile = yTile;
                    this.level.marioStartItem.insertInLevel(this.level.marioStartItem.xTile, this.level.marioStartItem.yTile);
                    this.frame.changePropertiesPanel(new PropertiesPanel(this.frame, this.level.marioStartItem.propertiesType, this.level.marioStartItem));
                } else {
                    copy.insertInLevel(xTile, yTile);
                    if (this.draggedItem != null) {
                        if (this.draggedItemXTile != xTile || this.draggedItemYTile != yTile) {
                            this.frame.undoableActionMade(Action.itemMoved(this.draggedItem, copy.warp, this.draggedItemXTile, this.draggedItemYTile, xTile, yTile));
                        }
                        this.frame.changePropertiesPanel(new PropertiesPanel(this.frame, copy.propertiesType, copy));
                    }
                }
            }
        }
    }

    private void removeItemInLevel(Item tempItem, boolean permanent) {
        Point actualTile = this.getRequiredTile(tempItem, true);
        if (actualTile.y >= 0 && actualTile.y < this.level.items.length && actualTile.x >= 0 && actualTile.x < this.level.items[0].length && this.level.items[actualTile.y][actualTile.x] != null) {
            Item itemToRemove = this.level.items[actualTile.y][actualTile.x];
            if (itemToRemove.spaceRequirement == 20) {
                return;
            }
            if (itemToRemove.isWarpable()) {
                if (permanent) {
                    if (itemToRemove.warp.incoming && !this.checkWarpConflict(itemToRemove.warp)) {
                        return;
                    }
                    this.level.outgoingWarps.remove(itemToRemove.warp);
                    this.level.incomingWarps.remove(itemToRemove.warp);
                }
                if (this.frame.propertiesPanel.item != null && this.frame.propertiesPanel.item.isWarpable()) {
                    this.frame.changePropertiesPanel(new PropertiesPanel(this.frame, 1, null));
                }
            }
            itemToRemove.removeFromLevel();
            if (permanent) {
                this.frame.undoableActionMade(Action.itemRemoved(itemToRemove, itemToRemove.warp));
                if (this.frame.propertiesPanel.item == itemToRemove) {
                    this.frame.changePropertiesPanel(new PropertiesPanel(this.frame, 1, null));
                }
            }
        }
    }

    public boolean checkWarpConflict(Warp conflictWarp) {
        LinkedList<Warp> warpConflicts = this.dependentWarps(conflictWarp);
        if (warpConflicts.size() > 0) {
            String prompt = "At least one warp is dependent on this warp.\r\nRemoving this warp will remove any dependent warps in the game.\r\nAre you sure you want to proceed?";
            Warp sampleWarp = warpConflicts.getFirst();
            prompt = sampleWarp.item == null ? prompt + "\r\nAffected Warp: Cliff death in level " + (sampleWarp.sourceLevelNumber + 1) : prompt + "\r\nAffected Warp: Level " + (sampleWarp.sourceLevelNumber + 1) + ", x=" + sampleWarp.item.xTile + ", y=" + sampleWarp.item.yTile;
            int answer = JOptionPane.showConfirmDialog(this, prompt, "Warp Dependency", 0, 2);
            this.leftMouseDown = false;
            this.rightMouseDown = false;
            if (answer != 0) {
                return false;
            }
            for (Warp tempWarp : warpConflicts) {
                this.levels[tempWarp.sourceLevelNumber].outgoingWarps.remove(tempWarp);
                tempWarp.clearOutgoing();
                if (tempWarp.item != null) continue;
                this.levels[tempWarp.sourceLevelNumber].cliffDestLevel = -1;
            }
        }
        return true;
    }

    private LinkedList<Warp> dependentWarps(Warp warp) {
        LinkedList<Warp> dependentWarps = new LinkedList<Warp>();
        for (int i = 0; i < this.levels.length; ++i) {
            if (this.levels[i].cliffDestLevel == warp.sourceLevelNumber && this.levels[i].cliffDestID == warp.sourceWarpID) {
                dependentWarps.add(new Warp(true, false, this.levels[i].levelNumber, -1, this.levels[i].cliffDestLevel, this.levels[i].cliffDestID, null));
            }
            for (Warp tempWarp : this.levels[i].outgoingWarps) {
                if (tempWarp.destLevelNumber != warp.sourceLevelNumber || tempWarp.destWarpID != warp.sourceWarpID) continue;
                dependentWarps.add(tempWarp);
            }
        }
        return dependentWarps;
    }

    private void setItemTiles(Item tempItem) {
        Point tiles = this.getRequiredTile(tempItem, false);
        tempItem.xTile = tiles.x;
        tempItem.yTile = tiles.y;
    }

    public void moveToLevelStart() {
        if (!(this.level == null || this.fitsWithoutScrolling())) {
            this.level.leftMostX = 0.0;
            this.updateSlider();
            this.repaint();
        }
    }

    public void moveToLevelEnd() {
        if (!(this.level == null || this.fitsWithoutScrolling())) {
            this.level.leftMostX = this.getMaxLeftMostX();
            this.updateSlider();
            this.repaint();
        }
    }

    private boolean overMarioItem() {
        Point p = this.getTileFromMouse();
        if (this.level != null && this.tileInBounds(p) && this.level.items[p.y][p.x] != null && this.level.items[p.y][p.x].character == 'k') {
            return true;
        }
        return false;
    }

    private boolean tileInBounds(Point p) {
        return this.level != null && p.x >= 0 && p.x < this.level.items[0].length && p.y >= 0 && p.y < Game.yTiles;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.setMousePoint(e.getPoint());
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (this.level != null && this.item != null && this.item.draggable) {
                if (this.frame.game.input.lockDragRow && !this.ignoresDragRowLocking(this.item.character) && this.draggedItemRow != -1) {
                    while ((double)Math.abs(e.getX() - this.lastDragInsertPoint) > 8.0 * this.scaleFactor) {
                        this.lastDragInsertPoint = e.getX() < this.lastDragInsertPoint ? (int)((double)this.lastDragInsertPoint - Math.floor(8.0 * this.scaleFactor)) : (int)((double)this.lastDragInsertPoint + Math.floor(8.0 * this.scaleFactor));
                        Point p = this.getTileFromPixels(this.lastDragInsertPoint, this.item.yTile);
                        this.insertItemInLevel(this.item, p.x, p.y, true);
                    }
                } else if (this.frame.game.input.lockDragColumn && !this.ignoresDragColumnLocking(this.item.character) && this.draggedItemCol != -1) {
                    while ((double)Math.abs(e.getY() - this.lastDragInsertPoint) > 8.0 * this.scaleFactor) {
                        this.lastDragInsertPoint = e.getY() < this.lastDragInsertPoint ? (int)((double)this.lastDragInsertPoint - Math.floor(8.0 * this.scaleFactor)) : (int)((double)this.lastDragInsertPoint + Math.floor(8.0 * this.scaleFactor));
                        Point p = this.getTileFromPixels(this.item.xTile, this.lastDragInsertPoint);
                        this.insertItemInLevel(this.item, p.x, p.y, true);
                    }
                }
                this.insertItemInLevel(this.item, true);
            }
        } else if (this.level != null && SwingUtilities.isRightMouseButton(e) && !this.overMarioItem()) {
            this.removeItemInLevel(this.item, true);
        }
        this.repaint();
    }

    private void leftMouseDraggedByScrolling() {
        if (this.item != null && this.item.draggable && !this.columnOperation()) {
            SwingUtilities.invokeLater(new Runnable(){

                @Override
                public void run() {
                    LevelPanel.this.insertItemInLevel(LevelPanel.this.item, true);
                }
            });
        }
    }

    private void rightMouseDraggedByScrolling() {
        if (!(this.overMarioItem() || this.columnOperation())) {
            SwingUtilities.invokeLater(new Runnable(){

                @Override
                public void run() {
                    LevelPanel.this.removeItemInLevel(LevelPanel.this.item, true);
                }
            });
        }
    }

    private boolean columnOperation() {
        return this.item != null && (this.item.spaceRequirement == 15 || this.item.spaceRequirement == 16);
    }

    public void fixDragGUIBug() {
        if (this.item != null && this.draggedItem != null) {
            this.insertItemInLevel(this.draggedItem, this.draggedItemXTile, this.draggedItemYTile, false);
            this.draggedItem = null;
        }
    }

    private synchronized void stopColumnThread() {
        if (this.columnThread != null) {
            this.columnThread.active = false;
            this.columnThread.interrupt();
            this.columnThread = null;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.fixDragGUIBug();
        this.setMousePoint(e.getPoint());
        this.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.level != null && e.getClickCount() == 2 && this.quickReferenceItem != null && this.quickReferenceItem.spaceRequirement != 20 && e.getButton() == 1) {
            Item hoveredItem;
            this.item = this.quickReferenceItem;
            this.quickReferenceItem = null;
            if (!this.item.isWarpable()) {
                this.frame.changePropertiesPanel(new PropertiesPanel(this.frame, this.item.propertiesType, this.item));
            } else {
                this.frame.changePropertiesPanel(new PropertiesPanel(this.frame, 1, null));
            }
            Point p = this.getTileFromMouse();
            Item item = hoveredItem = p.x < this.level.items[0].length ? this.level.items[p.y][p.x] : null;
            if ((e.getClickCount() == 1 && this.item == null || e.getClickCount() == 2) && e.getButton() == 1 && hoveredItem != null && hoveredItem.spaceRequirement == 20) {
                this.frame.changePropertiesPanel(new PropertiesPanel(this.frame, 2, null));
                this.item = null;
            }
        } else if (e.getClickCount() == 1 && e.getButton() == 2) {
            this.frame.miscPanel.pointer.doClick();
        } else if (e.getClickCount() == 1 && e.getButton() == 1 && this.quickReferenceItem != null) {
            this.quickReferenceItem = null;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (this.level == null) {
            return;
        }
        this.setMousePoint(e.getPoint());
        if (e.getButton() == 1) {
            this.leftMouseDown = true;
            if (this.item != null) {
                Point tile = this.getTileFromMouse();
                if (tile.x < this.level.items[0].length && this.level.items[tile.y][tile.x] != null) {
                    Item oldItem = this.level.items[tile.y][tile.x];
                    this.quickReferenceItem = oldItem.button.item.copy();
                    Item.transferProperties(oldItem, this.quickReferenceItem);
                }
                this.insertItemInLevel(this.item, true);
                if (this.item.spaceRequirement == 15) {
                    this.stopColumnThread();
                    this.columnThread = new ColumnThread(true);
                } else if (this.item.spaceRequirement == 16) {
                    this.stopColumnThread();
                    this.columnThread = new ColumnThread(false);
                }
                if (this.item.draggable && this.frame.game.input.lockDragRow && !this.columnOperation()) {
                    this.lastDragInsertPoint = e.getX();
                    this.ignoreDragInsertPoint = this.getTileFromPixels((double)((double)e.getX()), (double)((double)e.getY())).x >= this.level.items[0].length;
                } else if (this.item.draggable && this.frame.game.input.lockDragColumn && !this.columnOperation()) {
                    this.lastDragInsertPoint = e.getY();
                    this.ignoreDragInsertPoint = this.getTileFromPixels((double)((double)e.getX()), (double)((double)e.getY())).x >= this.level.items[0].length;
                }
            } else {
                Point tile = this.getTileFromMouse();
                this.ignoreDragInsertPoint = false;
                if (this.tileInBounds(tile) && this.mouseOnPanel && this.level.items[tile.y][tile.x] != null && this.level.items[tile.y][tile.x].spaceRequirement != 20) {
                    this.draggedItem = this.level.items[tile.y][tile.x];
                    this.quickReferenceItem = this.draggedItem.button.item.copy();
                    Item.transferProperties(this.draggedItem, this.quickReferenceItem);
                    this.draggedItemXTile = this.draggedItem.xTile;
                    this.draggedItemYTile = this.draggedItem.yTile;
                    this.draggedXOffset = tile.x - this.draggedItem.xTile;
                    this.draggedYOffset = tile.y - this.draggedItem.yTile;
                    this.removeItemInLevel(null, false);
                }
            }
        } else if (e.getButton() == 3) {
            this.rightMouseDown = true;
            if (!this.overMarioItem()) {
                this.removeItemInLevel(this.item, true);
            }
        }
        this.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (this.level == null) {
            return;
        }
        this.setMousePoint(e.getPoint());
        if (e.getButton() == 1) {
            this.leftMouseDown = false;
            this.draggedItemRow = -1;
            this.draggedItemCol = -1;
            this.ignoreDragInsertPoint = false;
            if (this.item != null && (this.item.spaceRequirement == 15 || this.item.spaceRequirement == 16)) {
                this.stopColumnThread();
            }
        } else if (e.getButton() == 3) {
            this.rightMouseDown = false;
        }
        if (this.draggedItem != null) {
            this.setItemTiles(this.draggedItem);
            if (ItemFitting.itemCanFit(this.level, this.draggedItem, this.draggedItem.xTile - this.draggedXOffset, this.draggedItem.yTile - this.draggedYOffset, this.itemsBlockingInsert)) {
                this.insertItemInLevel(this.draggedItem, this.draggedItem.xTile - this.draggedXOffset, this.draggedItem.yTile - this.draggedYOffset, false);
            } else {
                this.insertItemInLevel(this.draggedItem, this.draggedItemXTile, this.draggedItemYTile, false);
            }
            this.draggedItem = null;
            this.setStatusBar();
        }
        this.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.mouseOnPanel = true;
        if (!this.frame.levelSlider.getValueIsAdjusting()) {
            this.setMousePoint(e.getPoint());
        }
        LevelPanel levelPanel = this;
        synchronized (levelPanel) {
            this.notifyAll();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.mouseOnPanel = false;
        this.frame.statusBar.setText("");
        this.repaint();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if (this.level != null) {
            this.placeLevel();
            this.updateSlider();
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    public void placeLevel() {
        if (this.fitsWithoutScrolling()) {
            this.level.leftMostX = (double)this.level.items[0].length * 8.0 - (double)this.getWidth() / this.scaleFactor;
            if (this.level.leftMostX < 0.0) {
                this.level.leftMostX = 0.0;
            }
            this.level.leftMostX = (int)Math.round(this.level.leftMostX);
            if (this.level.leftMostX == 0.0) {
                this.frame.levelSlider.setEnabled(false);
            }
        } else {
            int actualLength;
            int maxLength;
            this.frame.levelSlider.setEnabled(true);
            if (this.level != null && (actualLength = (int)Math.round(this.level.leftMostX + (double)this.getWidth() / this.scaleFactor)) > (maxLength = (int)Math.round((double)this.level.items[0].length * 8.0))) {
                this.level.leftMostX = maxLength - (int)Math.round((double)this.getWidth() / this.scaleFactor);
                if (this.level.leftMostX < 0.0) {
                    this.level.leftMostX = 0.0;
                }
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (this.level != null && e.getSource() instanceof JSlider) {
            double ratio = (double)this.frame.levelSlider.getValue() / (double)this.frame.levelSlider.getMaximum();
            this.level.leftMostX = ratio * this.getMaxLeftMostX();
            if (!this.frame.levelSlider.getValueIsAdjusting()) {
                this.justReleasedSlider = true;
            } else {
                int leftMostTile = (int)(this.level.leftMostX / 8.0);
                int widthTiles = (int)Math.ceil((double)this.getWidth() / this.scaleFactor / 8.0);
                this.frame.statusBar.setText("Tiles " + leftMostTile + "-" + (leftMostTile + widthTiles) + " of " + this.level.items[0].length + " Tiles (" + Math.round(ratio * 100.0) + "%)");
            }
            this.repaint();
        }
    }

    private double getMaxLeftMostX() {
        if (this.scaleFactor == 0.0) {
            this.scaleFactor = (double)this.getHeight() / (double)Game.renderHeight;
        }
        return (double)this.level.items[0].length * 8.0 - (double)this.getWidth() / this.scaleFactor;
    }

    public void updateSlider() {
        double ratio = 0.0;
        if (this.level != null) {
            ratio = this.level.leftMostX / this.getMaxLeftMostX();
        }
        int sliderValue = (int)Math.round(ratio * (double)this.frame.levelSlider.getMaximum());
        this.frame.levelSlider.setValue(sliderValue);
        this.justReleasedSlider = false;
        if (this.fitsWithoutScrolling() || this.level == null) {
            this.frame.levelSlider.setEnabled(false);
        } else {
            this.frame.levelSlider.setEnabled(true);
        }
    }

    static /* synthetic */ BuilderFrame access$1400(LevelPanel x0) {
        return x0.frame;
    }

    private class ScrollerThread
    extends Thread {
        private static final double TARGET_UPDATE_RATE = 16.0;
        private static final double MAX_UPDATE_RATE = 40.0;
        private double updateRate;
        private boolean active;
        private long lastRenderTime;
        private long startTime;

        private ScrollerThread() {
            this.updateRate = 16.0;
            this.active = true;
            this.lastRenderTime = 16;
        }

        @Override
        public void run() {
            while (this.active) {
                while (!(LevelPanel.this.mouseOnPanel && LevelPanel.this.level != null)) {
                    LevelPanel levelPanel = LevelPanel.this;
                    synchronized (levelPanel) {
                        try {
                            LevelPanel.this.wait();
                            if (!this.active) {
                                return;
                            }
                        }
                        catch (InterruptedException e) {
                            // empty catch block
                        }
                        continue;
                    }
                }
                if ((double)this.lastRenderTime <= 16.0) {
                    this.updateRate = 16.0;
                } else {
                    this.updateRate = this.lastRenderTime;
                    if (this.updateRate > 40.0) {
                        this.updateRate = 40.0;
                    }
                }
                this.startTime = System.currentTimeMillis();
                double width = LevelPanel.this.getWidth();
                if (LevelPanel.access$1400((LevelPanel)LevelPanel.this).game.input.autoScroll && LevelPanel.this.mouseOnPanel && !LevelPanel.access$1400((LevelPanel)LevelPanel.this).levelSlider.getValueIsAdjusting() && !LevelPanel.this.justReleasedSlider) {
                    if (LevelPanel.this.mouseX < 0.25 * width) {
                        LevelPanel.this.level.leftMostX-=this.getScrollSpeed(true, width) * this.updateRate / 1000.0;
                        if (LevelPanel.this.level.leftMostX < 0.0) {
                            LevelPanel.this.level.leftMostX = 0.0;
                        } else {
                            this.checkForScrollDragging();
                        }
                        try {
                            SwingUtilities.invokeAndWait(new Runnable(){

                                @Override
                                public void run() {
                                    LevelPanel.this.updateSlider();
                                    LevelPanel.this.setStatusBar();
                                    LevelPanel.this.repaint();
                                }
                            });
                        }
                        catch (Exception e) {}
                    } else if (LevelPanel.this.mouseX > width - 0.25 * width) {
                        LevelPanel.this.level.leftMostX+=this.getScrollSpeed(false, width) * this.updateRate / 1000.0;
                        if (LevelPanel.this.level.leftMostX > LevelPanel.this.getMaxLeftMostX()) {
                            LevelPanel.this.level.leftMostX = LevelPanel.this.getMaxLeftMostX();
                            if (LevelPanel.this.level.leftMostX < 0.0) {
                                LevelPanel.this.level.leftMostX = 0.0;
                            }
                        } else {
                            this.checkForScrollDragging();
                        }
                        try {
                            SwingUtilities.invokeAndWait(new Runnable(){

                                @Override
                                public void run() {
                                    LevelPanel.this.updateSlider();
                                    LevelPanel.this.setStatusBar();
                                    LevelPanel.this.repaint();
                                }
                            });
                        }
                        catch (Exception e) {
                            // empty catch block
                        }
                    }
                }
                try {
                    Thread.sleep((int)this.updateRate);
                }
                catch (InterruptedException e) {}
            }
        }

        private void checkForScrollDragging() {
            if (LevelPanel.this.leftMouseDown) {
                LevelPanel.this.leftMouseDraggedByScrolling();
            }
            if (LevelPanel.this.rightMouseDown) {
                LevelPanel.this.rightMouseDraggedByScrolling();
            }
        }

        private double getScrollSpeed(boolean leftScroll, double width) {
            double distance = leftScroll ? 0.25 * width - LevelPanel.this.mouseX : 0.25 * width - (width - LevelPanel.this.mouseX);
            double ratio = distance / (0.25 * width);
            return ratio * MAX_SCROLL_SPEED * 8.0;
        }

    }

    private class PulsingThread
    extends Thread {
        private boolean active;
        private final int DELAY = 30;
        private final float PULSE_CYCLE = 800.0f;
        private final float MAX_ALPHA = 0.5f;
        private final float MIN_ALPHA = 0.2f;
        private float alpha;
        private boolean increasing;
        private boolean waiting;

        private PulsingThread() {
            this.active = true;
           
            this.alpha = 0.35000002f;
            this.increasing = true;
            this.waiting = false;
        }

        @Override
        public void run() {
            while (this.active) {
                while (!LevelPanel.this.inPulsingState()) {
                    LevelPanel levelPanel = LevelPanel.this;
                    synchronized (levelPanel) {
                        try {
                            this.waiting = true;
                            LevelPanel.this.wait();
                            this.waiting = false;
                            if (!this.active) {
                                return;
                            }
                        }
                        catch (InterruptedException e) {
                            // empty catch block
                        }
                        continue;
                    }
                }
                this.alpha+=0.01125f * (float)(this.increasing ? 1 : -1);
                if (this.increasing && this.alpha >= 0.5f) {
                    this.alpha = 0.5f;
                    this.increasing = false;
                } else if (!(this.increasing || this.alpha > 0.2f)) {
                    this.alpha = 0.2f;
                    this.increasing = true;
                }
                LevelPanel.this.repaint();
                try {
                    Thread.sleep(30);
                }
                catch (InterruptedException e) {}
            }
        }

        public float getAlpha() {
            return this.alpha;
        }
    }

    private class ColumnThread
    extends Thread {
        private boolean insert;
        private boolean active;
        public static final int INITIAL_INSERTION_DELAY = 500;
        public static final int REPEATED_INSERTION_DELAY = 25;

        public ColumnThread(boolean insert) {
            this.insert = insert;
            this.start();
        }

        @Override
        public void run() {
            this.active = true;
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                // empty catch block
            }
            while (this.active) {
                if (this.insert) {
                    LevelPanel.this.insertLevelColumn();
                } else {
                    LevelPanel.this.removeLevelColumn();
                }
                try {
                    Thread.sleep(25);
                }
                catch (InterruptedException e) {}
            }
        }
    }

}

