// 
// Decompiled by Procyon v0.5.29
// 

package supermario.debug;

import javax.swing.Icon;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.Dimension;
import supermario.game.Sprite;
import supermario.game.sprites.misc.Platform;
import javax.swing.ImageIcon;
import supermario.game.sprites.enemies.LavaBall;
import supermario.game.sprites.enemies.RedFish;
import supermario.game.sprites.enemies.Squid;
import supermario.game.sprites.enemies.GrayFish;
import supermario.game.sprites.enemies.HammerBro;
import supermario.game.sprites.enemies.Spiny;
import supermario.game.sprites.friends.Coin;
import supermario.game.sprites.enemies.Koopa;
import supermario.game.sprites.enemies.Beetle;
import supermario.game.sprites.enemies.Goomba;
import supermario.game.LevelLoader;
import java.awt.Point;
import java.awt.GridBagConstraints;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import supermario.game.Tile;
import supermario.game.sprites.blocks.SolidTestTile;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import javax.swing.JLabel;
import supermario.builder.BuilderFrame;
import supermario.game.Game;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JButton;

public class ItemInjector extends DebugTool
{
    private ItemButton lightGoomba;
    private ItemButton darkGoomba;
    private ItemButton grayGoomba;
    private ItemButton lightBeetle;
    private ItemButton darkBeetle;
    private ItemButton grayBeetle;
    private ItemButton lightKoopa;
    private ItemButton darkKoopa;
    private ItemButton redKoopa;
    private ItemButton lightKoopaBouncing;
    private ItemButton darkKoopaBouncing;
    private ItemButton redKoopaBouncing;
    private ItemButton lightKoopaFlyingV;
    private ItemButton darkKoopaFlyingV;
    private ItemButton redKoopaFlyingV;
    private ItemButton lightKoopaFlyingH;
    private ItemButton darkKoopaFlyingH;
    private ItemButton redKoopaFlyingH;
    private ItemButton spiny;
    private ItemButton lightHammerBro;
    private ItemButton darkHammerBro;
    private ItemButton lavaball;
    private ItemButton redFish;
    private ItemButton grayFishStraight;
    private ItemButton grayFishZigZag;
    private ItemButton squid;
    private ItemButton coin;
    private ItemButton shortCarrier;
    private ItemButton solid;
    private JButton selectNone;
    private JButton clearAll;
    private JButton clearTiles;
    private JCheckBox leftStartingCheckBox;
    private JComboBox<String> oscOffsetComboBox;
    public static final char REMOVE_ITEM = '\uffff';
    private char c;
    private boolean leftStarting;
    private float oscOffset;
    public static final int CLICK_TYPE_LEFT = 0;
    public static final int CLICK_TYPE_RIGHT = 1;
    
    public ItemInjector(final Game game, final BuilderFrame builderFrame) {
        super(game, "Sprite Injector");
    }
    
    @Override
    public void initComponents() {
        int row = 0;
        final JLabel land = new JLabel("<html><b><u>Land</u></b></html>");
        this.add(land, this.constraintsWithInsets(0, row, 4, 1, 10, 2, 2, 2, 2));
        final JLabel water = new JLabel("<html><b><u>Water</u></b></html>");
        this.add(water, this.constraintsWithInsets(5, row, 1, 1, 10, 2, 2, 2, 2));
        final JLabel misc = new JLabel("<html><b><u>Misc.</u></b></html>");
        this.add(misc, this.constraintsWithInsets(7, row++, 1, 1, 10, 2, 2, 2, 2));
        (this.lightGoomba = new ItemButton(this.game.textures.lightGoomba1, '$')).addActionListener(this.lightGoomba);
        this.add(this.lightGoomba, this.constraints(0, row, 1, 1, 10));
        (this.darkGoomba = new ItemButton(this.game.textures.darkGoomba1, '%')).addActionListener(this.darkGoomba);
        this.add(this.darkGoomba, this.constraints(1, row, 1, 1, 10));
        (this.grayGoomba = new ItemButton(this.game.textures.grayGoomba1, '\u02e6')).addActionListener(this.grayGoomba);
        this.add(this.grayGoomba, this.constraints(2, row, 1, 1, 10));
        (this.spiny = new ItemButton(this.game.textures.spiny2, '\\')).addActionListener(this.spiny);
        this.add(this.spiny, this.constraints(3, row, 1, 1, 10));
        (this.redFish = new ItemButton(this.game.textures.redFish1, '{')).addActionListener(this.redFish);
        this.add(this.redFish, this.constraints(5, row, 1, 1, 10));
        (this.coin = new ItemButton(this.game.textures.lightCoin, 'h')).addActionListener(this.coin);
        this.add(this.coin, this.constraints(7, row++, 1, 1, 10));
        (this.lightBeetle = new ItemButton(this.game.textures.lightBeetle1, ':')).addActionListener(this.lightBeetle);
        this.add(this.lightBeetle, this.constraints(0, row, 1, 1, 10));
        (this.darkBeetle = new ItemButton(this.game.textures.darkBeetle1, '\u00f4')).addActionListener(this.darkBeetle);
        this.add(this.darkBeetle, this.constraints(1, row, 1, 1, 10));
        (this.grayBeetle = new ItemButton(this.game.textures.grayBeetle1, '\u02e7')).addActionListener(this.grayBeetle);
        this.add(this.grayBeetle, this.constraints(2, row, 1, 1, 10));
        (this.lavaball = new ItemButton(this.game.textures.lavaball1, '.')).addActionListener(this.lavaball);
        this.add(this.lavaball, this.constraints(3, row, 1, 1, 10));
        (this.grayFishStraight = new ItemButton(this.game.textures.grayFish1, ']')).addActionListener(this.grayFishStraight);
        this.add(this.grayFishStraight, this.constraints(5, row, 1, 1, 10));
        (this.shortCarrier = new ItemButton(this.game.textures.cloudCarrierShort, '\u00e7')).addActionListener(this.shortCarrier);
        this.add(this.shortCarrier, this.constraints(7, row++, 1, 1, 10));
        (this.lightKoopa = new ItemButton(this.game.textures.lightKoopa1, '^')).addActionListener(this.lightKoopa);
        this.add(this.lightKoopa, this.constraints(0, row, 1, 1, 10));
        (this.darkKoopa = new ItemButton(this.game.textures.darkKoopa1, '(')).addActionListener(this.darkKoopa);
        this.add(this.darkKoopa, this.constraints(1, row, 1, 1, 10));
        (this.redKoopa = new ItemButton(this.game.textures.redKoopa1, '_')).addActionListener(this.redKoopa);
        this.add(this.redKoopa, this.constraints(2, row, 1, 1, 10));
        (this.lightHammerBro = new ItemButton(this.game.textures.lightHammerBro1, '}')).addActionListener(this.lightHammerBro);
        this.add(this.lightHammerBro, this.constraints(3, row, 1, 1, 10));
        (this.solid = new ItemButton(this.game.textures.solidTestTile, '\u0245')).setText("<html><center><b>Solid<br>Tile</b></center></html>");
        this.solid.addActionListener(this.solid);
        this.add(this.solid, this.constraints(7, row++, 1, 1, 10));
        (this.lightKoopaBouncing = new ItemButton(this.game.textures.lightKoopa4, '&')).addActionListener(this.lightKoopaBouncing);
        this.add(this.lightKoopaBouncing, this.constraints(0, row, 1, 1, 10));
        (this.darkKoopaBouncing = new ItemButton(this.game.textures.darkKoopa4, ')')).addActionListener(this.darkKoopaBouncing);
        this.add(this.darkKoopaBouncing, this.constraints(1, row, 1, 1, 10));
        (this.redKoopaBouncing = new ItemButton(this.game.textures.redKoopa4, '=')).addActionListener(this.redKoopaBouncing);
        this.add(this.redKoopaBouncing, this.constraints(2, row, 1, 1, 10));
        (this.darkHammerBro = new ItemButton(this.game.textures.darkHammerBro1, '\u00f6')).addActionListener(this.darkHammerBro);
        this.add(this.darkHammerBro, this.constraints(3, row, 1, 1, 10));
        (this.clearAll = new JButton("<html><center><b>Clear<br>Solids</b></center></html>")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (ItemInjector.this.game.testMode && ItemInjector.this.game.getGameState() == 1 && !ItemInjector.this.game.mario.transitioning) {
                    final Tile[][] tiles = ItemInjector.this.game.level.tiles;
                    for (int i = 0; i < tiles.length; ++i) {
                        for (int j = 0; j < tiles[0].length; ++j) {
                            if (tiles[i][j].sprite != null && tiles[i][j].sprite instanceof SolidTestTile) {
                                ItemInjector.this.game.level.tilesToRemove.add(tiles[i][j]);
                            }
                        }
                    }
                }
                ItemInjector.this.game.requestFocus();
            }
        });
        this.add(this.clearAll, this.constraints(7, row++, 1, 1, 10));
        (this.lightKoopaFlyingV = new ItemButton(this.game.builderFrame.textures.iconLightKoopaFlyingV, '*')).addActionListener(this.lightKoopaFlyingV);
        this.add(this.lightKoopaFlyingV, this.constraints(0, row, 1, 1, 10));
        (this.darkKoopaFlyingV = new ItemButton(this.game.builderFrame.textures.iconDarkKoopaFlyingV, '-')).addActionListener(this.darkKoopaFlyingV);
        this.add(this.darkKoopaFlyingV, this.constraints(1, row, 1, 1, 10));
        (this.redKoopaFlyingV = new ItemButton(this.game.builderFrame.textures.iconRedKoopaFlyingV, '+')).addActionListener(this.redKoopaFlyingV);
        this.add(this.redKoopaFlyingV, this.constraints(2, row, 1, 1, 10));
        (this.grayFishZigZag = new ItemButton(this.game.builderFrame.textures.displayGrayFishZigZag, '|')).addActionListener(this.grayFishZigZag);
        this.add(this.grayFishZigZag, this.constraints(5, row, 1, 1, 10));
        (this.clearTiles = new JButton("<html><center><b>Remove<br>Blocks</b></center></html>")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                ItemInjector.this.c = '\uffff';
                ItemInjector.this.game.requestFocus();
            }
        });
        this.add(this.clearTiles, this.constraints(7, row++, 1, 1, 10));
        (this.lightKoopaFlyingH = new ItemButton(this.game.builderFrame.textures.iconLightKoopaFlyingH, '¬')).addActionListener(this.lightKoopaFlyingH);
        this.add(this.lightKoopaFlyingH, this.constraints(0, row, 1, 1, 10));
        (this.darkKoopaFlyingH = new ItemButton(this.game.builderFrame.textures.iconDarkKoopaFlyingH, '±')).addActionListener(this.darkKoopaFlyingH);
        this.add(this.darkKoopaFlyingH, this.constraints(1, row, 1, 1, 10));
        (this.redKoopaFlyingH = new ItemButton(this.game.builderFrame.textures.iconRedKoopaFlyingH, '®')).addActionListener(this.redKoopaFlyingH);
        this.add(this.redKoopaFlyingH, this.constraints(2, row, 1, 1, 10));
        (this.squid = new ItemButton(this.game.textures.squid1, '[')).addActionListener(this.squid);
        this.add(this.squid, this.constraints(5, row, 1, 1, 10));
        (this.selectNone = new JButton("<html><center><b>Select<br>None</b></center></html>")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                ItemInjector.this.c = '\0';
                ItemInjector.this.game.requestFocus();
            }
        });
        this.add(this.selectNone, this.constraints(7, row++, 1, 1, 10));
        final GridBagConstraints c = this.constraintsWithInsets(4, 0, 1, 7, 10, 2, 2, 0, 0);
        c.fill = 3;
        this.add(new JSeparator(1), c);
        c.gridx = 6;
        this.add(new JSeparator(1), c);
        final GridBagConstraints c2 = this.constraintsWithInsets(0, row++, 8, 1, 10, 0, 0, 0, 0);
        c2.fill = 2;
        this.add(new JSeparator(0), c2);
        (this.leftStartingCheckBox = new JCheckBox("Left Starting", true)).setOpaque(false);
        this.leftStartingCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                ItemInjector.this.leftStarting = ItemInjector.this.leftStartingCheckBox.isSelected();
                ItemInjector.this.game.requestFocus();
            }
        });
        this.add(this.leftStartingCheckBox, this.constraintsWithInsets(0, row, 2, 1, 10, 5, 5, 2, 0));
        this.leftStarting = this.leftStartingCheckBox.isSelected();
        final JLabel oscOffsetLabel = new JLabel("Osc Offset:");
        this.add(oscOffsetLabel, this.constraintsWithInsets(2, row, 1, 1, 10, 5, 5, 2, 0));
        (this.oscOffsetComboBox = new JComboBox<String>(new String[] { "0%", "25%", "50%", "75%" })).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int index = ((JComboBox)e.getSource()).getSelectedIndex();
                if (index == 0) {
                    ItemInjector.this.oscOffset = 0.0f;
                }
                else if (index == 1) {
                    ItemInjector.this.oscOffset = 0.25f;
                }
                else if (index == 2) {
                    ItemInjector.this.oscOffset = 0.5f;
                }
                else if (index == 3) {
                    ItemInjector.this.oscOffset = 0.75f;
                }
                ItemInjector.this.game.requestFocus();
            }
        });
        this.add(this.oscOffsetComboBox, this.constraintsWithInsets(3, row, 1, 1, 10, 5, 5, 2, 0));
        final JButton help = new JButton("Help");
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final String message = "Click a button and use the mouse to inject a sprite into a live level.\n\nLand enemies must go into land levels, and water enemies must go into water levels.\n\nSolid tiles can be inserted into a level with left-click, and removed with right-click.\n\nUse the \"Left Starting\" and \"Osc Offset\" options to change sprite behavior upon insertion.";
                JOptionPane.showMessageDialog(ItemInjector.this, message, "Sprite Injector Help", 1);
            }
        });
        this.add(help, this.constraintsWithInsets(5, row - 1, 3, 2, 10, 5, 5, 2, 0));
    }
    
    public void ItemInjection(final Point clickLoc, final int clickType, final boolean fromDragging) {
        final int shadowColor = LevelLoader.getShadowType(this.game.level.levelType);
        final boolean waterLevel = this.game.level.levelType == 3;
        Sprite s = null;
        if (this.c == '\uffff') {
            s = null;
        }
        else if (this.c != 'h' && this.c != '\u0245' && ((waterLevel && !LevelLoader.isWaterEnemyOnly(this.c)) || (!waterLevel && LevelLoader.isWaterEnemyOnly(this.c)))) {
            s = null;
            this.game.audio.play(8);
        }
        else if (this.c == '$') {
            s = new Goomba(this.game, this.game.textures.getGoombaTextures(), 0, this.leftStarting);
        }
        else if (this.c == '%') {
            s = new Goomba(this.game, this.game.textures.getGoombaTextures(), 1, this.leftStarting);
        }
        else if (this.c == '\u02e6') {
            s = new Goomba(this.game, this.game.textures.getGoombaTextures(), 3, this.leftStarting);
        }
        else if (this.c == ':') {
            s = new Beetle(this.game, this.game.textures.getBeetleTextures(), this.leftStarting, 0);
        }
        else if (this.c == '\u00f4') {
            s = new Beetle(this.game, this.game.textures.getBeetleTextures(), this.leftStarting, 1);
        }
        else if (this.c == '\u02e7') {
            s = new Beetle(this.game, this.game.textures.getBeetleTextures(), this.leftStarting, 2);
        }
        else if (this.c == '^') {
            s = new Koopa(this.game, this.game.textures.getKoopaTextures(), 0, 0, this.leftStarting);
        }
        else if (this.c == '(') {
            s = new Koopa(this.game, this.game.textures.getKoopaTextures(), 1, 0, this.leftStarting);
        }
        else if (this.c == '_') {
            s = new Koopa(this.game, this.game.textures.getKoopaTextures(), 2, 0, this.leftStarting);
        }
        else if (this.c == '&') {
            s = new Koopa(this.game, this.game.textures.getKoopaTextures(), 0, 1, this.leftStarting);
        }
        else if (this.c == ')') {
            s = new Koopa(this.game, this.game.textures.getKoopaTextures(), 1, 1, this.leftStarting);
        }
        else if (this.c == '=') {
            s = new Koopa(this.game, this.game.textures.getKoopaTextures(), 2, 1, this.leftStarting);
        }
        else if (this.c == '*') {
            s = new Koopa(this.game, this.game.textures.getKoopaTextures(), 0, 2, this.leftStarting);
        }
        else if (this.c == '-') {
            s = new Koopa(this.game, this.game.textures.getKoopaTextures(), 1, 2, this.leftStarting);
            ((Koopa)s).startingOffset = this.oscOffset;
        }
        else if (this.c == '+') {
            s = new Koopa(this.game, this.game.textures.getKoopaTextures(), 2, 2, this.leftStarting);
            ((Koopa)s).startingOffset = this.oscOffset;
        }
        else if (this.c == '¬') {
            s = new Koopa(this.game, this.game.textures.getKoopaTextures(), 0, 3, this.leftStarting);
            ((Koopa)s).startingOffset = this.oscOffset;
        }
        else if (this.c == '±') {
            s = new Koopa(this.game, this.game.textures.getKoopaTextures(), 1, 3, this.leftStarting);
            ((Koopa)s).startingOffset = this.oscOffset;
        }
        else if (this.c == '®') {
            s = new Koopa(this.game, this.game.textures.getKoopaTextures(), 2, 3, this.leftStarting);
            ((Koopa)s).startingOffset = this.oscOffset;
        }
        else if (this.c == 'h') {
            s = new Coin(this.game, this.game.textures.getCoinTextures(), shadowColor);
        }
        else if (this.c == '\\') {
            s = new Spiny(this.game, this.game.textures.getSpinyTextures(), this.leftStarting);
        }
        else if (this.c == '}') {
            s = new HammerBro(this.game, this.game.textures.getHammerBroTextures(), true);
        }
        else if (this.c == '\u00f6') {
            s = new HammerBro(this.game, this.game.textures.getHammerBroTextures(), false);
        }
        else if (this.c == '|') {
            s = new GrayFish(this.game, this.game.textures.getGrayFishTextures(), this.leftStarting, true);
        }
        else if (this.c == '[') {
            s = new Squid(this.game, this.game.textures.getSquidTextures());
        }
        else if (this.c == '{') {
            s = new RedFish(this.game, this.game.textures.getRedFishTextures(), this.leftStarting, false);
        }
        else if (this.c == ']') {
            s = new GrayFish(this.game, this.game.textures.getGrayFishTextures(), this.leftStarting, false);
        }
        else if (this.c == '.') {
            s = new LavaBall(this.game, this.game.textures.getLavaballTextures(), -1);
        }
        else if (this.c == '\u00e7') {
            s = new Platform(this.game, new ImageIcon[] { this.game.textures.cloudCarrierShort }, 13, false);
        }
        else if (this.c == '\u0245') {
            s = new SolidTestTile(this.game, new ImageIcon[] { this.game.textures.solidTestTile });
        }
        final Point p = this.getLevelInsertionPoint(this.game.getSize(), clickLoc, this.c == '\uffff');
        if (this.c == '\uffff' && p != null && (this.game.level.maxTravelX < 0 || p.x * 8 < this.game.level.maxTravelX)) {
            final Tile tile = this.game.level.tiles[p.y][p.x];
            final Tile tileToRemove = this.game.level.tiles[tile.rootYTile][tile.rootXTile];
            if (tileToRemove.removable) {
                this.game.level.tilesToRemove.add(tileToRemove);
            }
        }
        else if (s != null && p != null && (this.game.level.maxTravelX < 0 || p.x + s.width - s.avoidedCollisionCols < this.game.level.maxTravelX)) {
            s.xPos = p.x - s.width / 2;
            s.yPos = p.y - s.height / 2;
            s.x = (int)s.xPos;
            s.y = (int)s.yPos;
            s.injected = true;
            if (clickType == 1) {
                s.xTile = (int)(p.x / 8.0);
                s.yTile = (int)(p.y / 8.0);
                final Tile t = this.game.level.tiles[s.yTile][s.xTile];
                if (t.sprite instanceof SolidTestTile) {
                    t.sprite = null;
                    t.solid = false;
                    t.image = null;
                    t.removable = false;
                }
            }
            else if (s instanceof Platform) {
                ((Platform)s).activate();
            }
            else if (s instanceof SolidTestTile) {
                s.xTile = (int)(p.x / 8.0);
                s.yTile = (int)(p.y / 8.0);
                s.x = s.xTile * 8;
                s.y = s.yTile * 8;
                final Tile t = this.game.level.tiles[s.yTile][s.xTile];
                if (t.sprite == null && !t.solid && !t.isUnexposedHiddenTile()) {
                    t.sprite = s;
                    t.image = this.game.textures.solidTestTile;
                    t.solid = true;
                    t.removable = true;
                    t.rootXTile = s.xTile;
                    t.rootYTile = s.yTile;
                }
            }
            else {
                if (s instanceof Koopa) {
                    ((Koopa)s).flyingYCenter = s.y;
                    ((Koopa)s).flyingXCenter = s.x;
                }
                else if (s instanceof HammerBro) {
                    ((HammerBro)s).activate();
                }
                else if (s instanceof GrayFish) {
                    ((GrayFish)s).activate();
                }
                else if (s instanceof LavaBall) {
                    ((LavaBall)s).activate();
                }
                this.game.level.spritesToAdd.add(s);
            }
        }
    }
    
    private Point getLevelInsertionPoint(final Dimension panelDim, final Point clickLoc, final boolean returnTileLocations) {
        final double panelWidth = panelDim.getWidth();
        final double panelHeight = panelDim.getHeight();
        final double windowRatio = panelWidth / panelHeight;
        double scaleFactor;
        int widthOffset;
        int heightOffset;
        if (windowRatio > Game.initialRatio) {
            scaleFactor = panelHeight / Game.renderHeight;
            widthOffset = (int)(panelWidth - Game.renderWidth * scaleFactor) / 2;
            heightOffset = 0;
        }
        else {
            scaleFactor = panelWidth / Game.renderWidth;
            widthOffset = 0;
            heightOffset = (int)(panelHeight - Game.renderHeight * scaleFactor) / 2;
        }
        final int x = (int)Math.round(this.game.level.leftMostX + (clickLoc.x - widthOffset) / scaleFactor);
        final int y = (int)Math.round((clickLoc.y - heightOffset) / scaleFactor);
        final int xTile = (int)(x / 8.0);
        final int yTile = (int)(y / 8.0);
        if (yTile < 0 || yTile >= this.game.level.tiles.length || xTile < 0 || xTile >= this.game.level.tiles[0].length) {
            return null;
        }
        if (returnTileLocations) {
            return new Point(xTile, yTile);
        }
        return new Point(x, y);
    }
    
    @Override
    public void windowOpened(final WindowEvent e) {
    }
    
    @Override
    public void windowClosing(final WindowEvent e) {
        this.hideConsole();
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
    public void hideConsole() {
        this.frame.setVisible(false);
    }
    
    @Override
    public void keyTyped(final KeyEvent e) {
    }
    
    @Override
    public void keyPressed(final KeyEvent e) {
        if (e.getKeyCode() == 27) {
            this.hideConsole();
        }
    }
    
    @Override
    public void keyReleased(final KeyEvent e) {
    }
    
    private class ItemButton extends JButton implements ActionListener
    {
        private char c;
        
        public ItemButton(final ImageIcon i, final char c) {
            this.setIcon(i);
            this.c = c;
        }
        
        @Override
        public void actionPerformed(final ActionEvent e) {
            ItemInjector.this.c = this.c;
            ItemInjector.this.game.requestFocus();
        }
    }
}
