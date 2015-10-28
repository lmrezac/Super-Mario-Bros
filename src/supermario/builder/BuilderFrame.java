// 
// Decompiled by Procyon v0.5.29
// 

package supermario.builder;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.List;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.event.WindowEvent;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import java.io.File;
import javax.swing.filechooser.FileFilter;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.Icon;
import java.awt.GridLayout;
import javax.swing.event.ChangeListener;
import javax.swing.JScrollBar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import java.awt.Frame;
import supermario.Utilities;
import javax.swing.SwingUtilities;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.awt.Point;
import java.awt.Color;
import java.text.DecimalFormat;
import supermario.debug.ItemInjector;
import supermario.debug.StateModifier;
import supermario.debug.PhysicsTestbox;
import java.awt.dnd.DropTarget;
import java.awt.Font;
import javax.swing.JSlider;
import javax.swing.JLabel;
import java.util.Stack;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import supermario.builder.itemPanels.SolidsPanel;
import supermario.builder.itemPanels.BackgroundPanel;
import supermario.builder.itemPanels.BlocksPanel;
import supermario.builder.itemPanels.MiscPanel;
import supermario.builder.itemPanels.PlatformsPanel;
import supermario.builder.itemPanels.PipesPanel;
import supermario.builder.itemPanels.EnemiesPanel;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import supermario.game.Game;
import java.awt.dnd.DropTargetListener;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

public class BuilderFrame extends JFrame implements WindowListener, DropTargetListener
{
    public Game game;
    public IO io;
    public Textures textures;
    public LevelPanel levelPanel;
    public GameListPanel gameListPanel;
    public JScrollPane propertiesScrollPane;
    public JPanel propertiesContainer;
    public PropertiesPanel propertiesPanel;
    public JTabbedPane buttonsPane;
    public EnemiesPanel enemiesPanel;
    public PipesPanel pipesPanel;
    public PlatformsPanel platformsPanel;
    public MiscPanel miscPanel;
    public BlocksPanel blocksPanel;
    public BackgroundPanel backgroundPanel;
    public SolidsPanel solidsPanel;
    private JMenuBar menuBar;
    private JMenuItem undo;
    private JMenuItem redo;
    private JMenuItem showPhysicsTestbox;
    private JMenuItem showStateModifier;
    private JMenuItem showItemInjector;
    private JMenuItem showAll;
    private JCheckBoxMenuItem lockDragRow;
    private JCheckBoxMenuItem lockDragColumn;
    private JCheckBoxMenuItem disableLockedDragging;
    private Stack<Action> undoStack;
    private Stack<Action> redoStack;
    public JLabel statusBar;
    public JSlider levelSlider;
    private JCheckBoxMenuItem autoScroll;
    private JCheckBoxMenuItem grid;
    private JCheckBoxMenuItem screenMarker;
    public Font bold;
    public Font plain;
    public Font boxFont;
    private DropTarget dropTarget;
    public static final int LEFT_COLUMN_WIDTH = 180;
    public static final int BOTTOM_ROW_HEIGHT = 325;
    public static final int BUTTON_PANEL_DEFAULT_WIDTH;
    public static final int TOP_ROW_HEIGHT;
    public static final int SCROLL_UNIT_INCREMENT = 8;
    public static final String FRAME_TITLE = "Super Mario Bros. Game Builder";
    public PhysicsTestbox physicsTestbox;
    public StateModifier stateTestbox;
    public ItemInjector itemInjector;
    public TexturePacks texturePacks;
    private final DecimalFormat commaNumber;
    private Color defaultBackground;
    
    public BuilderFrame(final Game game, final Point gamePos) {
        super("Super Mario Bros. Game Builder");
        this.commaNumber = new DecimalFormat("#,###");
        this.game = game;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    BuilderFrame.this.init(gamePos);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(BuilderFrame.this, "The game builder could not be loaded successfully.", "Error", 0);
                    BuilderFrame.this.quitToMenu(false);
                }
            }
        });
        this.io = new IO(this);
        this.physicsTestbox = new PhysicsTestbox(game);
        this.stateTestbox = new StateModifier(game);
    }
    
    private void init(final Point gamePos) throws Exception {
        this.setDefaultCloseOperation(0);
        this.addWindowListener(this);
        this.dropTarget = new DropTarget(this, this);
        this.setFocusable(true);
        Utilities.setIcon(this);
        this.bold = new Font("Tahoma", 1, 11);
        this.plain = new Font("Tahoma", 0, 11);
        this.boxFont = new Font("Monospaced", 0, 12);
        this.textures = new Textures();
        this.texturePacks = new TexturePacks(this.textures);
        if (!this.textures.validTextures || !this.texturePacks.validTextures) {
            throw new RuntimeException("Error loading textures.");
        }
        this.itemInjector = new ItemInjector(this.game, this);
        ImageBuilder.textures = this.game.textures;
        ImageBuilder.bTextures = this.textures;
        this.setLayout(new GridBagLayout());
        this.getContentPane().setBackground(Color.BLACK);
        this.initGameListPanel();
        this.initPropertiesPanel();
        this.initStatusBar();
        this.initLevelPanel();
        this.initLevelSlider();
        this.initButtonsPanel();
        this.initMenuBar();
        this.pack();
        this.setMinimumSize(this.getSize());
        this.setPreferredSize(this.getSize());
        this.setPosition(gamePos);
        this.setVisible(true);
        Utilities.correctScreenLocation(this);
        this.addActionKeys();
    }
    
    private void addActionKeys() {
        this.getRootPane().getInputMap(2).put(KeyStroke.getKeyStroke(78, 128, false), "new");
        this.getRootPane().getActionMap().put("new", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.newGame();
            }
        });
        this.getRootPane().getInputMap(2).put(KeyStroke.getKeyStroke(76, 128, false), "load");
        this.getRootPane().getInputMap(2).put(KeyStroke.getKeyStroke(79, 128, false), "load");
        this.getRootPane().getActionMap().put("load", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.pickGame();
            }
        });
        this.getRootPane().getInputMap(2).put(KeyStroke.getKeyStroke(83, 128, false), "save");
        this.getRootPane().getActionMap().put("save", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.save();
            }
        });
        this.getRootPane().getInputMap(2).put(KeyStroke.getKeyStroke(69, 128, false), "exit");
        this.getRootPane().getInputMap(2).put(KeyStroke.getKeyStroke(81, 128, false), "exit");
        this.getRootPane().getActionMap().put("exit", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.quitToMenu(false);
            }
        });
        this.getRootPane().getInputMap(2).put(KeyStroke.getKeyStroke(90, 128, false), "undo");
        this.getRootPane().getActionMap().put("undo", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.undoAction();
            }
        });
        this.getRootPane().getInputMap(2).put(KeyStroke.getKeyStroke(89, 128, false), "redo");
        this.getRootPane().getActionMap().put("redo", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.redoAction();
            }
        });
        this.getRootPane().getInputMap(2).put(KeyStroke.getKeyStroke(82, 128, false), "lockDragRow");
        this.getRootPane().getActionMap().put("lockDragRow", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.enableLockDragRow();
            }
        });
        this.getRootPane().getInputMap(2).put(KeyStroke.getKeyStroke(67, 128, false), "lockDragCol");
        this.getRootPane().getActionMap().put("lockDragCol", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.enableLockDragColumn();
            }
        });
        this.getRootPane().getInputMap(2).put(KeyStroke.getKeyStroke(68, 128, false), "disableLockDrag");
        this.getRootPane().getActionMap().put("disableLockDrag", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.disableLockedDragging();
            }
        });
        this.getRootPane().getInputMap(2).put(KeyStroke.getKeyStroke(84, 128, false), "test");
        this.getRootPane().getActionMap().put("test", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.testLevel();
            }
        });
        this.getRootPane().getInputMap(2).put(KeyStroke.getKeyStroke(71, 128, false), "grid");
        this.getRootPane().getActionMap().put("grid", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.toggleGrid();
            }
        });
        this.getRootPane().getInputMap(2).put(KeyStroke.getKeyStroke(65, 128, false), "autoScroll");
        this.getRootPane().getActionMap().put("autoScroll", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.toggleAutoScroll();
            }
        });
        this.getRootPane().getInputMap(2).put(KeyStroke.getKeyStroke(77, 128, false), "marker");
        this.getRootPane().getActionMap().put("marker", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.toggleScreenMarker();
            }
        });
        this.getRootPane().getInputMap(2).put(KeyStroke.getKeyStroke(70, 128, false), "find");
        this.getRootPane().getActionMap().put("find", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (BuilderFrame.this.levelPanel.level != null) {
                    BuilderFrame.this.levelPanel.findItem(0);
                }
            }
        });
        this.getRootPane().getInputMap(2).put(KeyStroke.getKeyStroke(66, 128, false), "showAll");
        this.getRootPane().getActionMap().put("showAll", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.showTestBoxes(true, true, true);
            }
        });
    }
    
    public void setPosition(final Point gamePos) {
        if (this.getExtendedState() == 6) {
            return;
        }
        final int x = gamePos.x + this.game.getWidth() / 2;
        final int y = gamePos.y + this.game.getHeight() / 2;
        Utilities.centerOnPoint(this, new Point(x, y));
    }
    
    private void initGameListPanel() {
        this.gameListPanel = new GameListPanel(this);
        final GridBagConstraints gameListPanelConstraints = new GridBagConstraints(0, 0, 1, 2, 0.0, 0.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0);
        this.getContentPane().add(this.gameListPanel, gameListPanelConstraints);
    }
    
    private void initPropertiesPanel() {
        (this.propertiesContainer = new JPanel(new GridBagLayout())).setPreferredSize(new Dimension(180, 325));
        this.propertiesContainer.setMinimumSize(this.propertiesContainer.getPreferredSize());
        this.defaultBackground = this.propertiesContainer.getBackground();
        this.propertiesPanel = new PropertiesPanel(this, 1, null);
        (this.propertiesScrollPane = new JScrollPane(this.propertiesPanel, 20, 31)).setBorder(BorderFactory.createEmptyBorder());
        this.propertiesScrollPane.getVerticalScrollBar().setUnitIncrement(8);
        this.propertiesScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(final AdjustmentEvent e) {
                final JScrollBar scrollBar = BuilderFrame.this.propertiesScrollPane.getVerticalScrollBar();
                final boolean maxedOut = scrollBar.getValue() + scrollBar.getVisibleAmount() == scrollBar.getMaximum();
                if (maxedOut) {
                    BuilderFrame.this.propertiesContainer.setBackground(Color.WHITE);
                }
                else {
                    BuilderFrame.this.propertiesContainer.setBackground(BuilderFrame.this.defaultBackground);
                }
            }
        });
        this.propertiesContainer.add(this.propertiesScrollPane, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        this.add(this.propertiesContainer, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, 10, 1, new Insets(1, 0, 0, 1), 0, 0));
    }
    
    public void changePropertiesPanel(final PropertiesPanel propertiesPanel) {
        this.propertiesPanel = propertiesPanel;
        this.propertiesContainer.removeAll();
        this.propertiesContainer.add(this.propertiesScrollPane, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        this.propertiesScrollPane.getViewport().removeAll();
        this.propertiesScrollPane.getViewport().add(propertiesPanel);
        if (propertiesPanel.type == 2) {
            this.propertiesContainer.add(propertiesPanel.getLevelApplyButton(), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
        }
        this.propertiesContainer.validate();
        this.levelPanel.repaint();
    }
    
    private void initLevelPanel() {
        (this.levelPanel = new LevelPanel(this)).setPreferredSize(new Dimension(0, Game.renderHeight));
        this.levelPanel.setMinimumSize(new Dimension(Game.xTiles * 8, Game.renderHeight));
        final GridBagConstraints levelPanelConstraints = new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, 10, 1, new Insets(0, 0, 0, 0), 0, 0);
        this.getContentPane().add(this.levelPanel, levelPanelConstraints);
    }
    
    private void initLevelSlider() {
        (this.levelSlider = new JSlider()).setPaintTicks(true);
        this.levelSlider.setEnabled(false);
        this.levelSlider.setMinimum(0);
        this.levelSlider.setMaximum(100000);
        this.levelSlider.addChangeListener(this.levelPanel);
        final GridBagConstraints levelSliderConstraints = new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, 10, 2, new Insets(0, 0, 0, 0), 0, 0);
        this.getContentPane().add(this.levelSlider, levelSliderConstraints);
    }
    
    private void initButtonsPanel() {
        (this.buttonsPane = new JTabbedPane(1, 1)).setFont(this.bold);
        this.buttonsPane.setPreferredSize(new Dimension(BuilderFrame.BUTTON_PANEL_DEFAULT_WIDTH, 325));
        this.buttonsPane.setMinimumSize(this.buttonsPane.getPreferredSize());
        final GridBagConstraints buttonsPanelConstraints = new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, 10, 0, new Insets(1, 0, 0, 0), 0, 0);
        if (this.game.input.stretchButtons) {
            buttonsPanelConstraints.fill = 2;
        }
        this.initItemsPanels();
        this.getContentPane().add(this.buttonsPane, buttonsPanelConstraints);
    }
    
    private void changeButtonsLayout(final boolean stretch) {
        this.getContentPane().remove(this.buttonsPane);
        final GridBagConstraints buttonsPanelConstraints = new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, 10, 0, new Insets(1, 0, 0, 0), 0, 0);
        if (stretch) {
            buttonsPanelConstraints.fill = 2;
        }
        this.getContentPane().add(this.buttonsPane, buttonsPanelConstraints);
    }
    
    private void initItemsPanels() {
        (this.miscPanel = new MiscPanel(this)).setLayout(new GridLayout(2, 0));
        final JScrollPane miscScrollPane = new JScrollPane(this.miscPanel, 20, 30);
        miscScrollPane.getVerticalScrollBar().setUnitIncrement(8);
        this.updateColumnStatus();
        (this.enemiesPanel = new EnemiesPanel(this)).setLayout(new GridLayout(3, 0));
        final JScrollPane enemiesScrollPane = new JScrollPane(this.enemiesPanel, 20, 30);
        enemiesScrollPane.getVerticalScrollBar().setUnitIncrement(8);
        (this.pipesPanel = new PipesPanel(this)).setLayout(new GridLayout(0, 6));
        final JScrollPane pipesScrollPane = new JScrollPane(this.pipesPanel, 20, 30);
        pipesScrollPane.getVerticalScrollBar().setUnitIncrement(8);
        (this.platformsPanel = new PlatformsPanel(this)).setLayout(new GridLayout(2, 0));
        final JScrollPane platformsScrollPane = new JScrollPane(this.platformsPanel, 20, 30);
        platformsScrollPane.getVerticalScrollBar().setUnitIncrement(8);
        (this.blocksPanel = new BlocksPanel(this)).setLayout(new GridLayout(0, 7));
        final JScrollPane blocksScrollPane = new JScrollPane(this.blocksPanel, 20, 30);
        blocksScrollPane.getVerticalScrollBar().setUnitIncrement(8);
        (this.backgroundPanel = new BackgroundPanel(this)).setLayout(new GridLayout(3, 0));
        final JScrollPane backgroundScrollPane = new JScrollPane(this.backgroundPanel, 20, 30);
        backgroundScrollPane.getVerticalScrollBar().setUnitIncrement(8);
        (this.solidsPanel = new SolidsPanel(this)).setLayout(new GridLayout(4, 0));
        final JScrollPane solidsScrollPane = new JScrollPane(this.solidsPanel, 20, 30);
        solidsScrollPane.getVerticalScrollBar().setUnitIncrement(8);
        this.buttonsPane.removeAll();
        this.buttonsPane.addTab(" Misc ", null, miscScrollPane, "Various miscellaneous items that can be added/moved in a level...");
        this.buttonsPane.addTab(" Background ", null, backgroundScrollPane, "Decorative items that can be placed in the background of a level...");
        this.buttonsPane.addTab(" Solids ", null, solidsScrollPane, "Blocks that cannot be passed through in a level");
        this.buttonsPane.addTab(" Blocks ", null, blocksScrollPane, "Bumpable blocks that can contain various items useful to Mario...");
        this.buttonsPane.addTab(" Enemies ", null, enemiesScrollPane, "Items that attack Mario in a level...");
        this.buttonsPane.addTab(" Pipes ", null, pipesScrollPane, "Solid items that may allow Mario to warp to various locations in a game...");
        this.buttonsPane.addTab(" Platforms ", null, platformsScrollPane, "Moving platforms that can collide with Mario and enemies...");
        this.buttonsPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent e) {
                BuilderFrame.this.miscPanel.removeAll();
                BuilderFrame.this.backgroundPanel.removeAll();
                BuilderFrame.this.solidsPanel.removeAll();
                BuilderFrame.this.blocksPanel.removeAll();
                BuilderFrame.this.enemiesPanel.removeAll();
                BuilderFrame.this.pipesPanel.removeAll();
                BuilderFrame.this.platformsPanel.removeAll();
                final JTabbedPane source = (JTabbedPane)e.getSource();
                final int index = source.getSelectedIndex();
                if (index == 0) {
                    BuilderFrame.this.miscPanel.construct();
                }
                else if (index == 1) {
                    BuilderFrame.this.backgroundPanel.construct();
                }
                else if (index == 2) {
                    BuilderFrame.this.solidsPanel.construct();
                }
                else if (index == 3) {
                    BuilderFrame.this.blocksPanel.construct();
                }
                else if (index == 4) {
                    BuilderFrame.this.enemiesPanel.construct();
                }
                else if (index == 5) {
                    BuilderFrame.this.pipesPanel.construct();
                }
                else if (index == 6) {
                    BuilderFrame.this.platformsPanel.construct();
                }
            }
        });
    }
    
    public void updateColumnStatus() {
        final Level l = this.levelPanel.level;
        if (l != null && ((l.levelEndType == 0 && l.items[0].length > Game.xTiles) || (l.levelEndType != 0 && l.items[0].length > Game.xTiles + 4))) {
            this.miscPanel.removeColumn.setEnabled(true);
        }
        else {
            this.miscPanel.removeColumn.setEnabled(false);
        }
    }
    
    public JButton getSpacerButton() {
        final JButton spacer = new JButton();
        spacer.setEnabled(false);
        return spacer;
    }
    
    public void setStackStates() {
        if (this.undoStack.isEmpty()) {
            this.undo.setEnabled(false);
        }
        else {
            this.undo.setEnabled(true);
        }
        if (this.redoStack.isEmpty()) {
            this.redo.setEnabled(false);
        }
        else {
            this.redo.setEnabled(true);
        }
    }
    
    public void clearStacks() {
        this.undoStack.clear();
        this.undo.setEnabled(false);
        this.redoStack.clear();
        this.redo.setEnabled(false);
    }
    
    public void undoableActionMade(final Action action) {
        this.undoStack.push(action);
        this.redoStack.clear();
        this.setStackStates();
    }
    
    private void undoAction() {
        if (!this.undoStack.isEmpty()) {
            final Action undoAction = this.undoStack.pop();
            this.redoStack.push(undoAction);
            undoAction.undoAction(this);
            this.setStackStates();
        }
    }
    
    private void redoAction() {
        if (!this.redoStack.isEmpty()) {
            final Action redoAction = this.redoStack.pop();
            this.undoStack.push(redoAction);
            redoAction.redoAction(this);
            this.setStackStates();
        }
    }
    
    private void exportStandalone() {
        this.game.fileChooser.removeChoosableFileFilter(Game.gameFilter);
        this.game.fileChooser.addChoosableFileFilter(Game.programFilter);
        this.game.fileChooser.setCurrentDirectory(new File(this.game.input.getSessionDirectory()));
        this.game.fileChooser.setDialogTitle("Export a Standalone Mario Game");
        this.game.fileChooser.setApproveButtonText("Export");
        final int answer = this.game.fileChooser.showSaveDialog(this);
        if (answer == 0) {
            this.game.input.setSessionDirectory(this.game.fileChooser.getSelectedFile().getParent() + "\\");
            final File exportFile = this.game.fileChooser.getSelectedFile();
            if (exportFile.exists() && (this.game.input.jarFile == null || this.game.input.jarFile.equals(exportFile))) {
                JOptionPane.showMessageDialog(this, "You can't overwrite this program. Choose a different name.", "Invalid Game", 0);
            }
            else {
                this.io.exportGame(exportFile.getAbsolutePath());
            }
        }
        this.game.fileChooser.removeChoosableFileFilter(Game.programFilter);
        this.game.fileChooser.addChoosableFileFilter(Game.gameFilter);
    }
    
    private void exportLevelImages() {
        this.game.fileChooser.removeChoosableFileFilter(Game.gameFilter);
        this.game.fileChooser.addChoosableFileFilter(Game.zipFilter);
        this.game.fileChooser.setCurrentDirectory(new File(this.game.input.getSessionDirectory()));
        this.game.fileChooser.setDialogTitle("Export Level Images");
        this.game.fileChooser.setApproveButtonText("Export");
        final int answer = this.game.fileChooser.showSaveDialog(this);
        if (answer == 0) {
            this.game.input.setSessionDirectory(this.game.fileChooser.getSelectedFile().getParent() + "\\");
            final File exportFile = this.game.fileChooser.getSelectedFile();
            if (exportFile.exists() && (this.game.input.jarFile == null || this.game.input.jarFile.equals(exportFile))) {
                JOptionPane.showMessageDialog(this, "You can't overwrite this program. Choose a different name.", "Invalid Game", 0);
            }
            else {
                this.io.exportImages(exportFile.getAbsolutePath());
            }
        }
        this.game.fileChooser.removeChoosableFileFilter(Game.zipFilter);
        this.game.fileChooser.addChoosableFileFilter(Game.gameFilter);
    }
    
    private void saveAs() {
        this.game.fileChooser.setCurrentDirectory(new File(this.game.input.getSessionDirectory()));
        this.game.fileChooser.setDialogTitle("Save a Mario Game");
        this.game.fileChooser.setApproveButtonText("Save");
        final int answer = this.game.fileChooser.showSaveDialog(this);
        if (answer == 0) {
            this.game.input.setSessionDirectory(this.game.fileChooser.getSelectedFile().getParent() + "\\");
            this.io.workingPath = this.game.fileChooser.getSelectedFile().getAbsolutePath();
            this.io.hasWorkingPath = true;
            this.io.writeGame(true);
        }
    }
    
    private void save() {
        if (this.levelPanel.levels.length == 0) {
            JOptionPane.showMessageDialog(this, "There is no game to save.", "No Content", 0);
        }
        else {
            this.levelPanel.fixDragGUIBug();
            if (this.io.hasWorkingPath) {
                this.io.writeGame(false);
            }
            else {
                this.saveAs();
            }
        }
    }
    
    private void newGame() {
        if (this.levelPanel.levels.length > 0 && this.levelPanel.modified) {
            final int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to start a new game without saving?", "New Game", 0);
            if (answer == 0) {
                this.levelPanel.removeGame();
                this.gameListPanel.withoutPassword();
                this.clearStacks();
            }
        }
        else if (this.levelPanel.levels.length > 0) {
            this.levelPanel.removeGame();
            this.gameListPanel.withoutPassword();
            this.clearStacks();
        }
        this.levelPanel.setLevelScheme();
    }
    
    private void pickGame() {
        this.game.fileChooser.setCurrentDirectory(new File(this.game.input.getSessionDirectory()));
        this.game.fileChooser.setDialogTitle("Load a Mario Game");
        this.game.fileChooser.setApproveButtonText("Load");
        final int answer = this.game.fileChooser.showOpenDialog(this);
        if (answer == 0) {
            this.loadGame(this.game.fileChooser.getSelectedFile());
        }
    }
    
    private void loadGame(final File file) {
        if (this.levelPanel.levels.length > 0 && this.levelPanel.modified) {
            final int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to load a new game without saving?", "Load Game", 0);
            if (answer == 0) {
                this.game.input.setSessionDirectory(file.getParent() + "\\");
                this.io.loadGame(file);
            }
        }
        else {
            this.game.input.setSessionDirectory(file.getParent() + "\\");
            this.io.loadGame(file);
        }
        this.setTitle("Super Mario Bros. Game Builder" + (this.io.lastAccessedFileName.isEmpty() ? "" : (" - " + this.io.lastAccessedFileName)));
    }
    
    private void toggleScreenMarker() {
        if (this.game.input.screenMarker) {
            this.screenMarker.setSelected(false);
            this.game.input.screenMarker = false;
        }
        else {
            this.screenMarker.setSelected(true);
            this.game.input.screenMarker = true;
        }
        this.levelPanel.repaint();
        this.game.input.writeConfiguration();
    }
    
    private void toggleGrid() {
        if (this.game.input.showGrid) {
            this.grid.setSelected(false);
            this.game.input.showGrid = false;
        }
        else {
            this.grid.setSelected(true);
            this.game.input.showGrid = true;
        }
        this.levelPanel.repaint();
        this.game.input.writeConfiguration();
    }
    
    private void toggleAutoScroll() {
        if (this.game.input.autoScroll) {
            this.autoScroll.setSelected(false);
            this.game.input.autoScroll = false;
        }
        else {
            this.autoScroll.setSelected(true);
            this.game.input.autoScroll = true;
        }
        this.game.input.writeConfiguration();
    }
    
    private void enableLockDragRow() {
        if (!this.game.input.lockDragRow) {
            this.lockDragRow.setSelected(true);
            this.lockDragColumn.setSelected(false);
            this.game.input.lockDragRow = true;
            this.game.input.lockDragColumn = false;
            this.disableLockedDragging.setSelected(false);
            this.levelPanel.draggedItemRow = -1;
            this.levelPanel.ignoreDragInsertPoint = true;
            this.game.input.writeConfiguration();
        }
    }
    
    private void enableLockDragColumn() {
        if (!this.game.input.lockDragColumn) {
            this.lockDragColumn.setSelected(true);
            this.lockDragRow.setSelected(false);
            this.game.input.lockDragColumn = true;
            this.game.input.lockDragRow = false;
            this.disableLockedDragging.setSelected(false);
            this.levelPanel.draggedItemRow = -1;
            this.levelPanel.ignoreDragInsertPoint = true;
            this.game.input.writeConfiguration();
        }
    }
    
    private void disableLockedDragging() {
        if (this.game.input.lockDragColumn || this.game.input.lockDragRow) {
            this.lockDragColumn.setSelected(false);
            this.lockDragRow.setSelected(false);
            this.disableLockedDragging.setSelected(true);
            this.game.input.lockDragColumn = false;
            this.game.input.lockDragRow = false;
            this.game.input.writeConfiguration();
        }
    }
    
    private void initMenuBar() {
        this.menuBar = new JMenuBar();
        final JMenu file = new JMenu("File");
        final JMenuItem newGame = new JMenuItem("New Game (Ctrl+N)");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.newGame();
            }
        });
        final JMenuItem openGame = new JMenuItem("Load Game (Ctrl+L)");
        openGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.pickGame();
            }
        });
        final JMenuItem saveGame = new JMenuItem("Save Game (Ctrl+S)");
        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.save();
            }
        });
        final JMenuItem saveGameAs = new JMenuItem("Save Game as...");
        saveGameAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (BuilderFrame.this.levelPanel.levels.length == 0) {
                    JOptionPane.showMessageDialog(BuilderFrame.this, "There is no game to save.", "No Content", 0);
                }
                else {
                    BuilderFrame.this.saveAs();
                }
            }
        });
        final JMenuItem exportStandalone = new JMenuItem("Export Standalone Game");
        exportStandalone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (BuilderFrame.this.levelPanel.levels.length == 0) {
                    JOptionPane.showMessageDialog(BuilderFrame.this, "There is no game to export.", "No Content", 0);
                }
                else {
                    final String message = "This creates an entire program dedicated to a single game.\nAn easier option might be to have a game named \"autostart.mario\" in the same folder as the program.\n\nAre you sure you want to make a whole program for this one game?";
                    final int answer = JOptionPane.showConfirmDialog(BuilderFrame.this, message, "Exporting", 2);
                    if (answer == 0) {
                        BuilderFrame.this.exportStandalone();
                    }
                }
            }
        });
        final JMenuItem exportLevelImages = new JMenuItem("Export Level Images");
        exportLevelImages.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (BuilderFrame.this.levelPanel.levels.length == 0) {
                    JOptionPane.showMessageDialog(BuilderFrame.this, "There are no levels to export images from.", "No Content", 0);
                }
                else {
                    BuilderFrame.this.exportLevelImages();
                }
            }
        });
        final JMenuItem exit = new JMenuItem("Exit Builder (Ctrl+E)");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.quitToMenu(false);
            }
        });
        final JMenuItem endProgram = new JMenuItem("Quit Program (Ctrl+Q)");
        endProgram.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.quitToMenu(true);
            }
        });
        file.add(newGame);
        file.add(openGame);
        file.addSeparator();
        file.add(saveGame);
        file.add(saveGameAs);
        if (this.game.input.jarFile != null && this.game.input.jarFile.isFile()) {
            file.addSeparator();
            file.add(exportStandalone);
        }
        file.add(exportLevelImages);
        file.addSeparator();
        file.add(exit);
        file.add(endProgram);
        final JMenu edit = new JMenu("Edit");
        this.undo = new JMenuItem("Undo (Ctrl+Z)");
        this.undoStack = new Stack<Action>();
        this.undo.setEnabled(false);
        this.undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.undoAction();
            }
        });
        this.redo = new JMenuItem("Redo (Ctrl+Y)");
        this.redoStack = new Stack<Action>();
        this.redo.setEnabled(false);
        this.redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.redoAction();
            }
        });
        (this.lockDragRow = new JCheckBoxMenuItem("Lock Dragging to Row (Ctrl+R)", this.game.input.lockDragRow)).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.enableLockDragRow();
            }
        });
        (this.lockDragColumn = new JCheckBoxMenuItem("Lock Dragging to Column (Ctrl+C)", this.game.input.lockDragColumn)).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.enableLockDragColumn();
            }
        });
        (this.disableLockedDragging = new JCheckBoxMenuItem("Disable Locked Dragging (Ctrl+D)", !this.game.input.lockDragRow && !this.game.input.lockDragColumn)).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.disableLockedDragging();
            }
        });
        final JCheckBoxMenuItem godModeTesting = new JCheckBoxMenuItem("God Mode in Testing", this.game.input.godModeTesting);
        godModeTesting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (BuilderFrame.this.game.input.godModeTesting) {
                    godModeTesting.setSelected(false);
                    BuilderFrame.this.game.input.godModeTesting = false;
                }
                else {
                    godModeTesting.setSelected(true);
                    BuilderFrame.this.game.input.godModeTesting = true;
                }
                BuilderFrame.this.game.input.writeConfiguration();
            }
        });
        final JCheckBoxMenuItem superMarioTesting = new JCheckBoxMenuItem("Super Mario in Testing", this.game.input.superMarioTesting);
        final JCheckBoxMenuItem fireMarioTesting = new JCheckBoxMenuItem("Fire Mario in Testing", this.game.input.fireMarioTesting);
        superMarioTesting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (BuilderFrame.this.game.input.superMarioTesting) {
                    superMarioTesting.setSelected(false);
                    BuilderFrame.this.game.input.superMarioTesting = false;
                }
                else {
                    fireMarioTesting.setSelected(false);
                    BuilderFrame.this.game.input.fireMarioTesting = false;
                    superMarioTesting.setSelected(true);
                    BuilderFrame.this.game.input.superMarioTesting = true;
                }
                BuilderFrame.this.game.input.writeConfiguration();
            }
        });
        fireMarioTesting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (BuilderFrame.this.game.input.fireMarioTesting) {
                    fireMarioTesting.setSelected(false);
                    BuilderFrame.this.game.input.fireMarioTesting = false;
                }
                else {
                    superMarioTesting.setSelected(false);
                    BuilderFrame.this.game.input.superMarioTesting = false;
                    fireMarioTesting.setSelected(true);
                    BuilderFrame.this.game.input.fireMarioTesting = true;
                }
                BuilderFrame.this.game.input.writeConfiguration();
            }
        });
        final JCheckBoxMenuItem unlimitedFireballTesting = new JCheckBoxMenuItem("Unlimited Fireballs in Testing", this.game.input.unlimitedFireballsTesting);
        unlimitedFireballTesting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (BuilderFrame.this.game.input.unlimitedFireballsTesting) {
                    unlimitedFireballTesting.setSelected(false);
                    BuilderFrame.this.game.input.unlimitedFireballsTesting = false;
                }
                else {
                    unlimitedFireballTesting.setSelected(true);
                    BuilderFrame.this.game.input.unlimitedFireballsTesting = true;
                }
                BuilderFrame.this.game.input.writeConfiguration();
            }
        });
        (this.showStateModifier = new JMenuItem("Show State Modifier")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.showTestBoxes(true, false, false);
            }
        });
        (this.showPhysicsTestbox = new JMenuItem("Show Physics Testbox")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.showTestBoxes(false, true, false);
            }
        });
        (this.showItemInjector = new JMenuItem("Show Sprite Injector")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.showTestBoxes(false, false, true);
            }
        });
        (this.showAll = new JMenuItem("Show All (Ctrl+B)")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.showTestBoxes(true, true, true);
            }
        });
        edit.add(this.undo);
        edit.add(this.redo);
        edit.addSeparator();
        edit.add(this.lockDragRow);
        edit.add(this.lockDragColumn);
        edit.add(this.disableLockedDragging);
        edit.addSeparator();
        edit.add(godModeTesting);
        edit.add(superMarioTesting);
        edit.add(fireMarioTesting);
        edit.add(unlimitedFireballTesting);
        edit.addSeparator();
        edit.add(this.showPhysicsTestbox);
        edit.add(this.showStateModifier);
        edit.add(this.showItemInjector);
        edit.add(this.showAll);
        final JMenu view = new JMenu("View");
        (this.grid = new JCheckBoxMenuItem("Level Grid (Ctrl+G)", this.game.input.showGrid)).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.toggleGrid();
            }
        });
        (this.autoScroll = new JCheckBoxMenuItem("Auto-Scroll (Ctrl+A)", this.game.input.autoScroll)).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.toggleAutoScroll();
            }
        });
        (this.screenMarker = new JCheckBoxMenuItem("Screen Marker (Ctrl+M)", this.game.input.screenMarker)).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.toggleScreenMarker();
            }
        });
        final JCheckBoxMenuItem stretchButtons = new JCheckBoxMenuItem("Stretch Buttons", this.game.input.stretchButtons);
        stretchButtons.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (BuilderFrame.this.game.input.stretchButtons) {
                    stretchButtons.setSelected(false);
                    BuilderFrame.this.game.input.stretchButtons = false;
                }
                else {
                    stretchButtons.setSelected(true);
                    BuilderFrame.this.game.input.stretchButtons = true;
                }
                BuilderFrame.this.changeButtonsLayout(BuilderFrame.this.game.input.stretchButtons);
                BuilderFrame.this.revalidate();
                BuilderFrame.this.repaint();
                BuilderFrame.this.game.input.writeConfiguration();
            }
        });
        final JMenuItem showStatistics = new JMenuItem("Game Statistics");
        showStatistics.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (BuilderFrame.this.levelPanel.levels.length == 0) {
                    JOptionPane.showMessageDialog(BuilderFrame.this, "There are no levels to analyze.", "Game Statistics", 1, BuilderFrame.this.game.textures.marioWalk1);
                }
                else {
                    int totalLevels = 0;
                    int totalColumns = 0;
                    int totalItems = 0;
                    int totalCoins = 0;
                    int totalLevelEndings = 0;
                    int totalOutgoingWarps = 0;
                    int totalIncomingWarps = 0;
                    totalLevels = BuilderFrame.this.levelPanel.levels.length;
                    for (int i = 0; i < BuilderFrame.this.levelPanel.levels.length; ++i) {
                        final Level level = BuilderFrame.this.levelPanel.levels[i];
                        if (level.levelEndType != 0) {
                            ++totalLevelEndings;
                        }
                        final Item[][] items = level.items;
                        totalColumns += items[0].length;
                        totalOutgoingWarps += level.outgoingWarps.size();
                        totalIncomingWarps += level.incomingWarps.size();
                        for (int j = 0; j < items.length; ++j) {
                            for (int k = 0; k < items[0].length; ++k) {
                                final Item item = items[j][k];
                                if (item != null && item.xTile == k && item.yTile == j) {
                                    ++totalItems;
                                    if (item.character == 'h') {
                                        ++totalCoins;
                                    }
                                }
                            }
                        }
                    }
                    String message = "<html>";
                    message = message + "Levels: " + BuilderFrame.this.commaNumber.format(totalLevels);
                    message = message + "<br>Columns: " + BuilderFrame.this.commaNumber.format(totalColumns);
                    message = message + "<br>Items: " + BuilderFrame.this.commaNumber.format(totalItems);
                    message = message + "<br>Coins: " + BuilderFrame.this.commaNumber.format(totalCoins);
                    message = message + "<br>Level Endings: " + BuilderFrame.this.commaNumber.format(totalLevelEndings);
                    message = message + "<br>Outgoing Warps: " + BuilderFrame.this.commaNumber.format(totalOutgoingWarps);
                    message = message + "<br>Incoming Warps: " + BuilderFrame.this.commaNumber.format(totalIncomingWarps);
                    JOptionPane.showMessageDialog(BuilderFrame.this, message + "</html>", "Game Statistics", 1, BuilderFrame.this.game.textures.marioWalk1);
                }
            }
        });
        view.add(this.grid);
        view.add(this.autoScroll);
        view.add(this.screenMarker);
        view.add(stretchButtons);
        view.addSeparator();
        view.add(showStatistics);
        final JMenu level = new JMenu("Level");
        final JMenuItem testLevel = new JMenuItem("Test Level (Ctrl+T)");
        testLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.testLevel();
            }
        });
        final JMenuItem skipToStart = new JMenuItem("Skip to Start");
        skipToStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.levelPanel.moveToLevelStart();
            }
        });
        final JMenuItem skipToEnd = new JMenuItem("Skip to End");
        skipToEnd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.levelPanel.moveToLevelEnd();
            }
        });
        final JMenuItem skipToMario = new JMenuItem("Find Mario Start (Ctrl+F)");
        skipToMario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (BuilderFrame.this.levelPanel.level != null) {
                    BuilderFrame.this.levelPanel.findItem(0);
                }
            }
        });
        final JMenuItem matchScheme = new JMenuItem("Match Items to Level");
        matchScheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (BuilderFrame.this.levelPanel.levels.length == 0) {
                    JOptionPane.showMessageDialog(BuilderFrame.this, "There are no levels to match.", "Match Items", 2);
                }
                else if (BuilderFrame.this.levelPanel.level == null) {
                    JOptionPane.showMessageDialog(BuilderFrame.this, "There is no level selected to match.", "Match Items", 2);
                }
                else if (BuilderFrame.this.levelPanel.level.levelType == 3) {
                    JOptionPane.showMessageDialog(BuilderFrame.this, "Water levels have no items that can be matched.", "Match Items", 2);
                }
                else if (BuilderFrame.this.levelPanel.level.levelType == 5 || BuilderFrame.this.levelPanel.level.levelType == 6) {
                    JOptionPane.showMessageDialog(BuilderFrame.this, "Coin Zone levels have no items that can be matched.", "Match Items", 2);
                }
                else {
                    String message = "This will match all applicable enemies, pipes, and bricks in the level based on the level type.";
                    message += "\r\n(ie brick/pipe/block color, enemy color)";
                    message += "\r\nAre you sure you want to continue?";
                    final int answer = JOptionPane.showConfirmDialog(BuilderFrame.this, message, "Match Items", 0, 2);
                    if (answer == 0) {
                        BuilderFrame.this.levelPanel.level.pipeColor = 0;
                        BuilderFrame.this.levelPanel.level.matchItemsToLevelType();
                        BuilderFrame.this.clearStacks();
                        BuilderFrame.this.levelPanel.setLevelScheme();
                        BuilderFrame.this.changePropertiesPanel(new PropertiesPanel(BuilderFrame.this, 1, null));
                    }
                }
            }
        });
        final JMenuItem traceEndingWarps = new JMenuItem("Trace Ending Warps");
        traceEndingWarps.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final Warp w = new Warp(false, true, 999, 0, -1, -1, BuilderFrame.this.miscPanel.mario.item);
                BuilderFrame.this.levelPanel.traceWarps(w);
            }
        });
        final JMenuItem findUnusedWarps = new JMenuItem("Find Unused Warps");
        findUnusedWarps.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (BuilderFrame.this.levelPanel.levels.length == 0) {
                    JOptionPane.showMessageDialog(BuilderFrame.this.propertiesScrollPane, "There is no game to search.", "Unused Warps", 2);
                }
                else {
                    BuilderFrame.this.levelPanel.findUnusedWarps();
                }
            }
        });
        final JMenuItem setStandardTextures = new JMenuItem("Set all to Standard Textures");
        setStandardTextures.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.massEditTexturePacks(supermario.game.TexturePacks.SPECIAL);
            }
        });
        final JMenuItem setLostLevelsTextures = new JMenuItem("Set all to Lost Levels Textures");
        setLostLevelsTextures.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.massEditTexturePacks(supermario.game.TexturePacks.LOSTLEVELS);
            }
        });
        final JMenuItem setNewSMBTextures = new JMenuItem("Set all to New SMB Textures");
        setNewSMBTextures.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.massEditTexturePacks(supermario.game.TexturePacks.NEWSMB);
            }
        });
        final JMenuItem setSpecialTextures = new JMenuItem("Set all to SMB Special Textures");
        setSpecialTextures.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                BuilderFrame.this.massEditTexturePacks(supermario.game.TexturePacks.SPECIAL);
            }
        });
        level.add(testLevel);
        level.addSeparator();
        level.add(skipToStart);
        level.add(skipToEnd);
        level.add(skipToMario);
        level.addSeparator();
        level.add(matchScheme);
        level.addSeparator();
        level.add(traceEndingWarps);
        level.add(findUnusedWarps);
        level.addSeparator();
        level.add(setStandardTextures);
        level.add(setLostLevelsTextures);
        level.add(setNewSMBTextures);
        level.add(setSpecialTextures);
        final JMenu help = new JMenu("Help");
        final JMenuItem bowserBattleHelp = new JMenuItem("Bowser Battle Key");
        bowserBattleHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                String key = "<html><b>P</b> = Horizontal Platform<br><b>Sl</b> = Stone Bricks (line)<br><b>Sb</b> = Stone Bricks (block)<br><b>F</b> = Firebar on Bridge<br><b>L</b> = Lava Ball Under Bridge<br><b>H</b> = Hammer Throwing Bowser<br><b>M</b> = Missing Top Left Stone Blocks<br><br>";
                key += "World 1: <b>P</b><br>";
                key += "World 2: <b>P</b>, <b>Sl</b><br>";
                key += "World 3: <b>P</b>, <b>Sb</b><br>";
                key += "World 4: <b>F</b>, <b>L</b>, <b>M</b><br>";
                key += "World 5: <b>P</b>, <b>Sl</b>, <b>L</b><br>";
                key += "World 6: <b>P</b>, <b>L</b>, <b>H</b><br>";
                key += "World 7: <b>L</b>, <b>H</b>, <b>M</b><br>";
                key += "World 8: <b>H</b></html";
                JOptionPane.showMessageDialog(BuilderFrame.this, key, "Bowser Battle Key", 1, BuilderFrame.this.game.textures.bowser1);
            }
        });
        final JMenuItem tipsAndTricks = new JMenuItem("Tips & Tricks");
        tipsAndTricks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final String message = "<html>Click on Mario at the title screen to toggle Luigi on and off.<br><br>Double clicking an item in the level builder sets that item as the type to insert.<br><br>Clicking the middle mouse button in the level builder selects the pointer tool.<br><br>Use the keyboard shortcuts found in the menu options to quickly create levels.<br><br>Take advantage of the menu feature <b>Level > Find Unused Warps</b> to track dead end warps.<br><br>Use the <font color=\"blue\">+</font> button to easily copy levels (warps and endings not included).<br><br>Use the F1 key in a level to save a snapshot of the game.</html>";
                JOptionPane.showMessageDialog(BuilderFrame.this, message, "Tips & Tricks", 1, BuilderFrame.this.game.textures.marioWalk1);
            }
        });
        final JMenuItem about = new JMenuItem("About");
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                Utilities.showAbout(BuilderFrame.this);
            }
        });
        help.add(bowserBattleHelp);
        help.add(tipsAndTricks);
        help.addSeparator();
        help.add(about);
        this.menuBar.add(file);
        this.menuBar.add(edit);
        this.menuBar.add(view);
        this.menuBar.add(level);
        this.menuBar.add(help);
        this.setJMenuBar(this.menuBar);
    }
    
    private void massEditTexturePacks(final int texturePack) {
        if (this.levelPanel.levels.length == 0) {
            JOptionPane.showMessageDialog(this, "There are no levels to change.", "Texture Pack Change", 2);
            return;
        }
        if (!(texturePack >=0 && texturePack <= supermario.game.TexturePacks.HIGHEST_PACK_VALUE)) {
            throw new IllegalArgumentException("Unknown texture pack style set for mass edit: " + texturePack);
        }
        String texturePackName = supermario.game.TexturePacks.names[texturePack];
        
        final int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to change all levels to the " + texturePackName + " texture pack?", "Texture Pack Change", 0);
        if (answer == 0) {
            for (int i = 0; i < this.levelPanel.levels.length; ++i) {
                this.levelPanel.levels[i].texturePack = texturePack;
            }
            this.changePropertiesPanel(new PropertiesPanel(this, 2, null));
        }
    }
    
    private void showTestBoxes(final boolean stateModifier, final boolean physicsModifier, final boolean itemModifier) {
        boolean cancelled = false;
        if (physicsModifier) {
            if (!this.physicsTestbox.isShowing() && !this.game.input.hidePhysicsWarning) {
                final JCheckBox acknowledge = new JCheckBox("Do not show this message again.");
                final String message = "<html><center><b>Be careful!</b><br><br>Crazy values could make the program unstable.<br>Back up your game!<br><br>Are you sure you want to proceed?<br><br></center></html>";
                final Object[] content = { message, acknowledge };
                final int answer = JOptionPane.showConfirmDialog(this, content, "Physics Testbox", 0, 2);
                if (acknowledge.isSelected()) {
                    this.game.input.hidePhysicsWarning = true;
                    this.game.input.writeConfiguration();
                }
                if (answer == 0) {
                    this.physicsTestbox.showConsole(this, this.levelPanel);
                }
                else {
                    cancelled = true;
                }
            }
            else {
                this.physicsTestbox.showConsole(this, this.buttonsPane);
            }
        }
        if (stateModifier && !cancelled) {
            this.stateTestbox.showConsole(this, this.gameListPanel);
        }
        if (itemModifier && !cancelled) {
            this.itemInjector.showConsole(this, this.levelPanel);
        }
    }
    
    private void initStatusBar() {
        (this.statusBar = new JLabel()).setBackground(new Color(240, 240, 240));
        this.statusBar.setOpaque(true);
        this.statusBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        final GridBagConstraints statusBarConstraints = new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0, 10, 2, new Insets(1, 0, 0, 0), 0, 0);
        this.statusBar.setFont(this.plain);
        this.statusBar.setPreferredSize(new Dimension(0, 16));
        this.statusBar.setMinimumSize(this.statusBar.getPreferredSize());
        this.getContentPane().add(this.statusBar, statusBarConstraints);
    }
    
    public void testLevel() {
        if (this.levelPanel.levels.length == 0) {
            JOptionPane.showMessageDialog(this.gameListPanel, "There is no level to test.", "Level Test", 2);
        }
        else if (this.levelPanel.level == null) {
            JOptionPane.showMessageDialog(this.gameListPanel, "There is no selected level to test.", "Level Test", 2);
        }
        else {
            this.switchToLevelTest(this.levelPanel.level.levelNumber);
        }
    }
    
    private void quitToMenu(final boolean endProgram) {
        if (this.levelPanel != null && this.levelPanel.levels != null && this.levelPanel.levels.length > 0 && this.levelPanel.modified) {
            final int answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to leave the builder without saving?", "Exit Game Builder", 0);
            if (answer == 0) {
                if (endProgram) {
                    this.setVisible(false);
                    this.game.audio.dispose();
                    System.exit(0);
                }
                else {
                    this.leaveBuilder();
                }
            }
        }
        else if (endProgram) {
            this.setVisible(false);
            this.game.audio.dispose();
            System.exit(0);
        }
        else {
            this.leaveBuilder();
        }
    }
    
    private void leaveBuilder() {
        if (this.isVisible()) {
            this.game.switchFromBuilder(this.getLocationOnScreen(), this.getSize());
            this.physicsTestbox.hideConsole();
            this.stateTestbox.hideConsole();
            this.itemInjector.hideConsole();
        }
        else {
            this.game.switchFromBuilder(null, null);
        }
        this.setVisible(false);
        if (this.levelPanel != null) {
            this.levelPanel.stopThreads();
        }
        this.removeWindowListener(this);
        this.dropTarget.removeDropTargetListener(this);
    }
    
    private void switchToLevelTest(final int startingLevel) {
        try {
            final IO.GameData gameData = this.io.getGameData(startingLevel);
            final Point p = this.getLocationOnScreen();
            final Dimension size = this.getSize();
            this.setVisible(false);
            this.game.testBuilderLevel(p, size, gameData);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "There was a problem creating the level test. Make sure you have the latest version of the program.", "Level Test Error.", 0);
            e.printStackTrace();
        }
    }
    
    @Override
    public void windowOpened(final WindowEvent e) {
    }
    
    @Override
    public void windowClosing(final WindowEvent e) {
        this.quitToMenu(false);
    }
    
    @Override
    public void windowClosed(final WindowEvent e) {
    }
    
    @Override
    public void windowIconified(final WindowEvent e) {
    }
    
    @Override
    public void windowDeiconified(final WindowEvent e) {
    }
    
    @Override
    public void windowActivated(final WindowEvent e) {
    }
    
    @Override
    public void windowDeactivated(final WindowEvent e) {
    }
    
    @Override
    public void dragEnter(final DropTargetDragEvent dtde) {
    }
    
    @Override
    public void dragOver(final DropTargetDragEvent dtde) {
    }
    
    @Override
    public void dropActionChanged(final DropTargetDragEvent dtde) {
    }
    
    @Override
    public void dragExit(final DropTargetEvent dte) {
    }
    
    @Override
    public void drop(final DropTargetDropEvent dtde) {
        dtde.acceptDrop(1);
        final Transferable transferable = dtde.getTransferable();
        final DataFlavor[] flavors = transferable.getTransferDataFlavors();
        for (int i = 0; i < flavors.length; ++i) {
            if (flavors[i].isFlavorJavaFileListType()) {
                try {
                    final Object data = transferable.getTransferData(flavors[i]);
                    if (data instanceof List) {
                        final List<File> files = (List<File>)data;
                        if (files.size() == 1) {
                            final String path = files.get(0).toString();
                            final String ext = ".mario";
                            if (path.length() > ext.length() && path.endsWith(ext)) {
                                this.loadGame(files.get(0));
                            }
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        dtde.dropComplete(true);
    }
    
    static {
        BUTTON_PANEL_DEFAULT_WIDTH = ((Game.overlayXOffset == 0) ? (Game.xTiles * 8 * 2) : (Game.xTiles * 8));
        TOP_ROW_HEIGHT = Game.renderHeight;
    }
}
