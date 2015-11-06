// 
// Decompiled by Procyon v0.5.29
// 

package supermario.builder;

import java.awt.GridBagConstraints;
import supermario.game.LevelLoader;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.AbstractButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Dimension;
import javax.swing.JSlider;
import javax.swing.JOptionPane;
import java.awt.Container;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import java.awt.Insets;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;
import java.awt.Component;
import javax.swing.JLabel;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class PropertiesPanel extends JPanel
{
    private BuilderFrame frame;
    public int type;
    public final Item item;
    public static final int TYPE_BLANK = 1;
    public static final int TYPE_LEVEL = 2;
    public static final int TYPE_REVERSIBLE_ENEMY = 3;
    public static final int TYPE_MULTIPLE_COINS = 4;
    public static final int TYPE_PIPE_WARP = 5;
    public static final int TYPE_BEANSTALK_BLOCK = 6;
    public static final int TYPE_WARP_ZONE_PIPE = 7;
    public static final int TYPE_SHIFTABLE_BACKGROUND = 8;
    public static final int TYPE_FIREBAR = 9;
    public static final int TYPE_REP_PLATFORM = 10;
    public static final int TYPE_LAVABALL = 11;
    public static final int TYPE_INCOMING_WARP = 12;
    public static final int TYPE_OSC_OFFSET = 13;
    public static final int TYPE_CHECKPOINT = 14;
    public static final int TYPE_CUSTOM_TEXT = 15;
    public static final int TYPE_POWERUP = 16;
    public static final int TYPE_SPRING = 17;
    public static final int H_SPACER = 2;
    public static final int V_SPACER = 5;
    public static final int EAST = 13;
    public static final int CENTER = 10;
    public static final int WEST = 17;
    public static final int FILL_NONE = 0;
    public static final String STANDARD_TEXTURES = " Original";
    public static final String LOST_TEXTURES = " Lost Levels";
    private static final String LEFT_FACING = "  Starts Left  ";
    private static final String RIGHT_FACING = " Starts Right ";
    private static final String SHIFT_LEFT = "<html><center>Shift To<br>Left Side</center></html>";
    private static final String SHIFT_RIGHT = "<html><center>Shift To<br>Right Side</center></html>";
    private JComboBox<String> warpsToLevelComboBox;
    private JComboBox<String> warpsToIDComboBox;
    private JComboBox<String> warpsOnCliffLevelComboBox;
    private JButton levelApplyButton;
    private JButton goToButton;
    private JButton goToButton2;
    
    public PropertiesPanel(final BuilderFrame frame, final int type, final Item item) {
        this.frame = frame;
        this.item = item;
        this.setBackground(Color.WHITE);
        this.setLayout(new GridBagLayout());
        this.type = type;
        if (type == 1) {
            this.initTitle(1);
        }
        else if (type == 2) {
            if (frame.levelPanel.levels.length > 0) {
                this.initTitle(6);
                this.initLevelComponents();
            }
        }
        else if (type == 3) {
            this.initTitle(1);
            this.initReversibleEnemyComponents();
        }
        else if (type == 9) {
            this.initTitle(1);
            this.initFirebarComponents();
        }
        else if (type == 4) {
            this.initTitle(1);
            this.initMultipleCoinsComponents();
        }
        else if (type == 11) {
            this.initTitle(1);
            this.initLavaballComponents();
        }
        else if (type == 5) {
            this.initTitle(1);
            this.initPipeWarpComponents();
        }
        else if (type == 12) {
            this.initTitle(1);
            this.initArrivalVineComponents();
        }
        else if (type == 6) {
            this.initTitle(1);
            this.initBeanstalkBlockComponents();
        }
        else if (type == 7) {
            this.initTitle(1);
            this.initWarpZoneComponents();
        }
        else if (type == 8) {
            this.initTitle(1);
            this.initShiftableBackgroundComponents();
        }
        else if (type == 10) {
            this.initTitle(1);
            this.initRepPlatformComponents();
        }
        else if (type == 13) {
            this.initTitle(1);
            this.initOscillatingOffsetComponents();
        }
        else if (type == 14) {
            this.initTitle(4);
            this.initCheckpointComponents();
        }
        else if (type == 15) {
            this.initTitle(1);
            this.initCustomTextComponents();
        }
        else if (type == 16) {
            this.initTitle(1);
            this.initPowerupComponents();
        }
        else if (type == 17) {
            this.initTitle(1);
            this.initSpringComponents();
        }
    }
    
    private void initTitle(final int cellWidth) {
        final JLabel titleLabel = new JLabel("Properties");
        titleLabel.setFont(this.frame.bold);
        titleLabel.setAlignmentX(0.5f);
        titleLabel.setAlignmentY(0.5f);
        this.add(titleLabel, getConstraints(0, 0, cellWidth, 1, 0, 1, 19, 0, 5, 0, 5, 0));
    }
    
    private void addSeparator(final int gridy, final int gridwidth) {
        final JSeparator separator = new JSeparator(0);
        this.add(separator, getConstraints(0, gridy, gridwidth, 1, 0, 0, 10, 2, 5, 10, 5, 10));
    }
    
    public JButton getLevelApplyButton() {
        return this.levelApplyButton;
    }
    
    private void initLevelComponents() {
        int row = 1;
        final JLabel nameLabel = new JLabel("Level Name:");
        nameLabel.setFont(this.frame.bold);
        this.add(nameLabel, getConstraints(0, row++, 6, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        final TextBox name1TextBox = new TextBox(8, false, true, false);
        name1TextBox.setFont(this.frame.boxFont);
        name1TextBox.setText(this.frame.levelPanel.level.name1);
        name1TextBox.setToolTipText("The first half of a level name...");
        this.add(name1TextBox, getConstraints(0, row, 3, 1, 0, 0, 13, 0, 0, 0, 0, 2));
        final TextBox name2TextBox = new TextBox(8, false, true, false);
        name2TextBox.setFont(this.frame.boxFont);
        name2TextBox.setText(this.frame.levelPanel.level.name2);
        name2TextBox.setToolTipText("The second half of a level name...");
        this.add(name2TextBox, getConstraints(3, row++, 3, 1, 0, 0, 17, 0, 0, 2, 0, 0));
        this.addSeparator(row++, 6);
        final TextBox timedLevelTextField = new TextBox(4, true, false, false);
        final JCheckBox timedLevelCheckBox = new JCheckBox("<html><center>Timed:</center></html>");
        timedLevelCheckBox.setToolTipText("If the level will have a time limit...");
        timedLevelCheckBox.setFont(this.frame.bold);
        timedLevelCheckBox.setOpaque(false);
        timedLevelCheckBox.setSelected(this.frame.levelPanel.level.timedLevel);
        timedLevelCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (timedLevelCheckBox.isSelected()) {
                    timedLevelTextField.setEnabled(true);
                    if (PropertiesPanel.this.frame.levelPanel.level.levelTime >= 30) {
                        timedLevelTextField.setText("" + PropertiesPanel.this.frame.levelPanel.level.levelTime);
                    }
                }
                else {
                    timedLevelTextField.setText("");
                    timedLevelTextField.setEnabled(false);
                }
            }
        });
        timedLevelTextField.setFont(this.frame.boxFont);
        timedLevelTextField.setToolTipText("The time limit of the level in seconds...");
        timedLevelTextField.setFont(this.frame.boxFont);
        timedLevelTextField.setOpaque(true);
        if (timedLevelCheckBox.isSelected()) {
            timedLevelTextField.setEnabled(true);
            timedLevelTextField.setText(String.valueOf(this.frame.levelPanel.level.levelTime));
        }
        else {
            timedLevelTextField.setEnabled(false);
        }
        this.add(timedLevelCheckBox, getConstraints(0, row, 3, 1, 0, 0, 13, 0, 0, 0, 0, 2));
        this.add(timedLevelTextField, getConstraints(3, row++, 3, 1, 0, 0, 17, 0, 0, 2, 0, 0));
        this.addSeparator(row++, 6);
        //MARK level types
        final JLabel levelTypeLabel = new JLabel("Level Type:");
        levelTypeLabel.setFont(this.frame.bold);
        this.add(levelTypeLabel, getConstraints(0, row++, 6, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        final String[] levelTypeList = { " Above Ground Daytime ", " Underground ", " Castle ", " Underwater ", " Above Ground Nighttime ", " Coin Zone Daytime ", " Coin Zone Nighttime ", "Ghost House" };
        final JComboBox<String> levelTypeComboBox = new JComboBox<String>(levelTypeList);
        final JComboBox<String> levelEndComboBox = new JComboBox<String>();
        levelTypeComboBox.setToolTipText("Determines the visual style and music of the level...");
        levelTypeComboBox.setFont(this.frame.plain);
        levelTypeComboBox.setSelectedIndex(this.frame.levelPanel.level.levelType);
        levelTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PropertiesPanel.this.populateEndingsComboBox(levelTypeComboBox.getSelectedIndex(), levelEndComboBox);
            }
        });
        this.add(levelTypeComboBox, getConstraints(0, row++, 6, 1, 0, 0, 10, 0, 0, 0, 0, 0));
        this.addSeparator(row++, 6);
        final JLabel levelEndLabel = new JLabel("Level End:");
        levelEndLabel.setFont(this.frame.bold);
        this.add(levelEndLabel, getConstraints(0, row++, 6, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        levelEndComboBox.setToolTipText("The type of ending in this level (if any)...");
        levelEndComboBox.setFont(this.frame.plain);
        this.populateEndingsComboBox(this.frame.levelPanel.level.levelType, levelEndComboBox);
        levelEndComboBox.setSelectedIndex(this.frame.levelPanel.level.getLevelEndIndex());
        levelEndComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (levelEndComboBox.getSelectedIndex() == 0) {
                    PropertiesPanel.this.warpsToLevelComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "" }));
                    PropertiesPanel.this.warpsToLevelComboBox.setEnabled(false);
                    PropertiesPanel.this.goToButton2.setVisible(false);
                }
                else {
                    PropertiesPanel.this.populateLevelComboBox();
                    PropertiesPanel.this.warpsToLevelComboBox.setEnabled(true);
                    if (levelTypeComboBox.getSelectedIndex() == PropertiesPanel.this.frame.levelPanel.level.levelType && levelEndComboBox.getSelectedIndex() == PropertiesPanel.this.frame.levelPanel.level.getLevelEndIndex()) {
                        PropertiesPanel.this.goToButton2.setVisible(true);
                        PropertiesPanel.this.warpsToLevelComboBox.setSelectedIndex(PropertiesPanel.this.frame.levelPanel.level.nextLevelNumber);
                    }
                    else {
                        PropertiesPanel.this.goToButton2.setVisible(false);
                    }
                }
            }
        });
        this.add(levelEndComboBox, getConstraints(0, row++, 6, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        final JLabel warpsToLevelLabel = new JLabel("Goes To Level:");
        warpsToLevelLabel.setFont(this.frame.bold);
        this.add(warpsToLevelLabel, getConstraints(0, row++, 6, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        (this.warpsToLevelComboBox = new JComboBox<String>()).setToolTipText("The level that this level leads to after its ending...");
        this.warpsToLevelComboBox.setFont(this.frame.plain);
        if (this.frame.levelPanel.level.nextLevelNumber != -1) {
            this.warpsToLevelComboBox.setEnabled(true);
            this.populateLevelComboBox();
            if (this.frame.levelPanel.level.nextLevelNumber == 999 || this.frame.levelPanel.level.nextLevelNumber >= this.frame.levelPanel.levels.length) {
                this.warpsToLevelComboBox.setSelectedIndex(this.warpsToLevelComboBox.getItemCount() - 1);
            }
            else {
                this.warpsToLevelComboBox.setSelectedIndex(this.frame.levelPanel.level.nextLevelNumber);
            }
        }
        else {
            this.warpsToLevelComboBox.setEnabled(false);
        }
        this.warpsToLevelComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PropertiesPanel.this.goToButton2.setVisible(PropertiesPanel.this.frame.levelPanel.level.nextLevelNumber != -1 && PropertiesPanel.this.frame.levelPanel.level.nextLevelNumber != 999 && PropertiesPanel.this.frame.levelPanel.level.levelType == levelTypeComboBox.getSelectedIndex() && PropertiesPanel.this.frame.levelPanel.level.getLevelEndTypeIndexFromEndType(PropertiesPanel.this.frame.levelPanel.level.levelEndType) == levelEndComboBox.getSelectedIndex() && PropertiesPanel.this.frame.levelPanel.level.nextLevelNumber == PropertiesPanel.this.warpsToLevelComboBox.getSelectedIndex());
            }
        });
        this.add(this.warpsToLevelComboBox, getConstraints(0, row++, 6, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        (this.goToButton2 = new JButton("Go")).setFont(this.frame.bold);
        this.goToButton2.setToolTipText("Jump to the place this warp heads to...");
        this.goToButton2.setMargin(new Insets(2, 2, 2, 2));
        this.goToButton2.setVisible(this.frame.levelPanel.level.nextLevelNumber != -1 && this.frame.levelPanel.level.nextLevelNumber != 999);
        this.goToButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PropertiesPanel.this.frame.levelPanel.findWarpDestination(new Warp(true, false, -1, -1, PropertiesPanel.this.frame.levelPanel.level.nextLevelNumber, 0, null));
            }
        });
        this.add(this.goToButton2, getConstraints(0, row++, 6, 1, 0, 0, 10, 0, 0, 0, 0, 0));
        this.addSeparator(row++, 6);
        final JCheckBox hasLakituCheckBox = new JCheckBox();
        hasLakituCheckBox.setToolTipText("The character who throws spinys from the sky at Mario...");
        hasLakituCheckBox.setBackground(this.getBackground());
        hasLakituCheckBox.setFont(this.frame.bold);
        hasLakituCheckBox.setSelected(this.frame.levelPanel.level.hasLakitu);
        if (this.frame.levelPanel.level.levelType == 3) {
            hasLakituCheckBox.setEnabled(false);
        }
        this.add(hasLakituCheckBox, getConstraints(0, row, 1, 1, 1, 0, 13, 0, 0, 0, 0, 0));
        final JLabel hasLakituLabel = new JLabel(this.frame.game.textures.spinyThrower1);
        hasLakituLabel.setFont(this.frame.bold);
        this.add(hasLakituLabel, getConstraints(1, row, 1, 1, 1, 0, 17, 0, 0, 0, 0, 0));
        final JCheckBox hasFlyingFishCheckBox = new JCheckBox();
        hasFlyingFishCheckBox.setToolTipText("Fish that are catapulted upward towards Mario...");
        hasFlyingFishCheckBox.setBackground(this.getBackground());
        hasFlyingFishCheckBox.setFont(this.frame.bold);
        hasFlyingFishCheckBox.setSelected(this.frame.levelPanel.level.hasFlyingFish);
        if (this.frame.levelPanel.level.levelType == 3) {
            hasFlyingFishCheckBox.setEnabled(false);
        }
        this.add(hasFlyingFishCheckBox, getConstraints(2, row, 1, 1, 1, 0, 13, 0, 0, 0, 0, 0));
        final JLabel hasFlyingFishLabel = new JLabel(this.frame.game.textures.redFish1);
        hasFlyingFishLabel.setFont(this.frame.bold);
        this.add(hasFlyingFishLabel, getConstraints(3, row, 1, 1, 1, 0, 17, 0, 0, 0, 0, 0));
        final JCheckBox hasBulletsCheckBox = new JCheckBox();
        hasBulletsCheckBox.setToolTipText("Randomly fired bullets that attack from the right...");
        hasBulletsCheckBox.setBackground(this.getBackground());
        hasBulletsCheckBox.setFont(this.frame.bold);
        hasBulletsCheckBox.setSelected(this.frame.levelPanel.level.hasBullets);
        this.add(hasBulletsCheckBox, getConstraints(4, row, 1, 1, 1, 0, 13, 0, 0, 0, 0, 0));
        final JLabel hasBulletsLabel = new JLabel(this.frame.game.textures.lightBullet);
        hasBulletsLabel.setFont(this.frame.bold);
        this.add(hasBulletsLabel, getConstraints(5, row++, 1, 1, 1, 0, 17, 0, 0, 0, 0, 0));
        final JCheckBox blackAndWhiteCheckBox = new JCheckBox("Black and White");
        blackAndWhiteCheckBox.setToolTipText("If the level should be rendered without color...");
        blackAndWhiteCheckBox.setBackground(this.getBackground());
        blackAndWhiteCheckBox.setFont(this.frame.bold);
        blackAndWhiteCheckBox.setSelected(this.frame.levelPanel.level.blackAndWhite);
        this.add(blackAndWhiteCheckBox, getConstraints(0, row++, 6, 1, 0, 0, 10, 0, 5, 0, 0, 0));
        final JCheckBox autoScrollingCheckBox = new JCheckBox("Auto-Scrolling");
        autoScrollingCheckBox.setToolTipText("If the level should move forward at a fixed speed...");
        autoScrollingCheckBox.setBackground(this.getBackground());
        autoScrollingCheckBox.setFont(this.frame.bold);
        autoScrollingCheckBox.setSelected(this.frame.levelPanel.level.autoScrolling);
        this.add(autoScrollingCheckBox, getConstraints(0, row++, 6, 1, 0, 0, 10, 0, 5, 0, 0, 0));
        final JLabel textureTypeLabel = new JLabel("Textures: ");
        textureTypeLabel.setFont(this.frame.bold);
        this.add(textureTypeLabel, getConstraints(0, row, 3, 1, 0, 0, 13, 0, 0, 0, 0, 0));
        final JComboBox<String> textureTypeComboBox = new JComboBox<String>();
        textureTypeComboBox.setToolTipText("The texture pack to use in this level...");
        textureTypeComboBox.setFont(this.frame.plain);
        textureTypeComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { " Original", " Lost Levels", "New SMB", "Special" }));
        textureTypeComboBox.setSelectedIndex(this.frame.levelPanel.level.texturePack);
        this.add(textureTypeComboBox, getConstraints(3, row++, 3, 1, 0, 0, 17, 0, 5, 0, 0, 0));
        this.addSeparator(row++, 6);
        final JButton traceWarps = new JButton("Trace Warps");
        traceWarps.setFont(this.frame.bold);
        traceWarps.setToolTipText("Find any warps in the game that lead to this level's start...");
        traceWarps.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PropertiesPanel.this.frame.levelPanel.traceWarps(PropertiesPanel.this.frame.levelPanel.level.levelNumber);
            }
        });
        this.add(traceWarps, getConstraints(0, row++, 6, 1, 0, 0, 10, 0, 0, 0, 0, 0));
        this.addSeparator(row++, 6);
        final JLabel warpsOnCliffLevelLabel = new JLabel("Below Screen Goes To:");
        warpsOnCliffLevelLabel.setFont(this.frame.bold);
        this.add(warpsOnCliffLevelLabel, getConstraints(0, row++, 6, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        (this.warpsOnCliffLevelComboBox = new JComboBox<String>()).setToolTipText("What happens when Mario falls below the bottom of the level...");
        this.warpsOnCliffLevelComboBox.setFont(this.frame.plain);
        this.warpsOnCliffLevelComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (PropertiesPanel.this.warpsOnCliffLevelComboBox.getSelectedIndex() == 0) {
                    PropertiesPanel.this.warpsToIDComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "" }));
                    PropertiesPanel.this.warpsToIDComboBox.setEnabled(false);
                    PropertiesPanel.this.goToButton.setVisible(false);
                }
                else {
                    PropertiesPanel.this.warpsToIDComboBox.setEnabled(true);
                    PropertiesPanel.this.populateIDComboBox();
                }
            }
        });
        this.add(this.warpsOnCliffLevelComboBox, getConstraints(0, row++, 6, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        final JLabel warpsOnCliffIDLabel = new JLabel("Goes to ID:");
        warpsOnCliffIDLabel.setFont(this.frame.bold);
        this.add(warpsOnCliffIDLabel, getConstraints(0, row++, 6, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        final JPanel goToPanel = new JPanel();
        goToPanel.setLayout(new BoxLayout(goToPanel, 0));
        (this.warpsToIDComboBox = new JComboBox<String>()).setFont(this.frame.plain);
        this.warpsToIDComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PropertiesPanel.this.goToButton.setVisible(PropertiesPanel.this.frame.levelPanel.level.cliffDestLevel != -1 && PropertiesPanel.this.warpsToIDComboBox.getSelectedIndex() == PropertiesPanel.this.frame.levelPanel.levels[PropertiesPanel.this.frame.levelPanel.level.cliffDestLevel].getWarpsIndex(PropertiesPanel.this.frame.levelPanel.level.cliffDestID) + 1);
            }
        });
        goToPanel.add(this.warpsToIDComboBox);
        (this.goToButton = new JButton("Go")).setFont(this.frame.bold);
        this.goToButton.setToolTipText("Jump to the place this warp heads to...");
        this.goToButton.setMargin(new Insets(2, 2, 2, 2));
        this.goToButton.setVisible(this.frame.levelPanel.level.cliffDestLevel != -1);
        this.goToButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PropertiesPanel.this.frame.levelPanel.findWarpDestination(new Warp(true, false, -1, -1, PropertiesPanel.this.frame.levelPanel.level.cliffDestLevel, PropertiesPanel.this.frame.levelPanel.level.cliffDestID, null));
            }
        });
        goToPanel.add(this.goToButton);
        this.add(goToPanel, getConstraints(0, row++, 6, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        this.populateCliffEndLevelComboBox();
        if (this.frame.levelPanel.level.cliffDestLevel == -1) {
            this.warpsOnCliffLevelComboBox.setSelectedIndex(0);
            this.warpsToIDComboBox.setEnabled(false);
        }
        else {
            this.warpsOnCliffLevelComboBox.setSelectedIndex(this.frame.levelPanel.level.cliffDestLevel + 1);
            this.populateIDComboBox();
            this.warpsToIDComboBox.setSelectedIndex(this.frame.levelPanel.levels[this.frame.levelPanel.level.cliffDestLevel].getWarpsIndex(this.frame.levelPanel.level.cliffDestID) + 1);
        }
        this.addSeparator(row++, 6);
        final JLabel pipeColorLabel = new JLabel("Pipe Color:");
        pipeColorLabel.setFont(this.frame.bold);
        this.add(pipeColorLabel, getConstraints(0, row++, 6, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        final JComboBox<String> pipeColorComboBox = new JComboBox<String>();
        pipeColorComboBox.setFont(this.frame.plain);
        pipeColorComboBox.setToolTipText("The color of the pipes in the level...");
        final String[] pipeColorList = { " Default ", " Green ", " White ", " Blue ", " Orange " };
        pipeColorComboBox.setModel(new DefaultComboBoxModel<String>(pipeColorList));
        pipeColorComboBox.setSelectedIndex(this.frame.levelPanel.level.pipeColor);
        this.add(pipeColorComboBox, getConstraints(0, row++, 6, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        (this.levelApplyButton = new JButton("Apply")).setFont(this.frame.bold);
        this.levelApplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int scrollValue = PropertiesPanel.this.frame.propertiesScrollPane.getVerticalScrollBar().getValue();
                final Level l = PropertiesPanel.this.frame.levelPanel.level;
                final String oldName1 = l.name1;
                final String oldName2 = l.name2;
                final boolean oldTimed = l.timedLevel;
                final int oldTime = l.levelTime;
                final int oldLevelType = l.levelType;
                final int oldLevelEndIndex = l.getLevelEndTypeIndexFromEndType(l.levelEndType);
                final int oldNextLevel = l.nextLevelNumber;
                final boolean oldLakitu = l.hasLakitu;
                final boolean oldFish = l.hasFlyingFish;
                final boolean oldBullets = l.hasBullets;
                final boolean oldBandW = l.blackAndWhite;
                final int oldDeathLevel = l.cliffDestLevel;
                final int oldDeathID = l.cliffDestID;
                final int oldPipeColor = l.pipeColor;
                final boolean oldAutoScrolling = l.autoScrolling;
                final int oldTexturePack = l.texturePack;
                if (timedLevelCheckBox.isSelected()) {
                    int timeLimit = 0;
                    final String timeString = timedLevelTextField.getText();
                    if (timeString.isEmpty()) {
                        JOptionPane.showMessageDialog(PropertiesPanel.this.frame, "Please enter a time limit for the level, or uncheck the timed checkbox.", "Level Time Limit", 0);
                        return;
                    }
                    timeLimit = Integer.valueOf(timeString);
                    if (timeLimit < 30) {
                        JOptionPane.showMessageDialog(PropertiesPanel.this.frame, "The time limit must be at least 30 seconds.", "Minimum Time Limit", 0);
                        return;
                    }
                    timedLevelTextField.setText(String.valueOf(timeLimit));
                    PropertiesPanel.this.frame.levelPanel.level.levelTime = timeLimit;
                    PropertiesPanel.this.frame.levelPanel.level.timedLevel = true;
                }
                else {
                    PropertiesPanel.this.frame.levelPanel.level.timedLevel = false;
                }
                boolean clearUndoRedoStack = false;
                if (levelTypeComboBox.getSelectedIndex() == 3 && PropertiesPanel.this.frame.levelPanel.level.levelType != 3) {
                    final String prompt = "Changing to a water level may remove existing items in this level such as:\r\nEmpty Bricks, Platforms Moving Up, Land Enemies, Springs, Background Decorations,\r\nLakitu, Flying Fish, and Infinite Corridors.\r\nAre you sure you want to proceed?";
                    final int answer = JOptionPane.showConfirmDialog(PropertiesPanel.this.frame.propertiesContainer, prompt, "Change to Water Level", 0);
                    if (answer != 0) {
                        return;
                    }
                    clearUndoRedoStack = true;
                    hasLakituCheckBox.setSelected(false);
                    hasLakituCheckBox.setEnabled(false);
                    hasFlyingFishCheckBox.setSelected(false);
                    hasFlyingFishCheckBox.setEnabled(false);
                    PropertiesPanel.this.frame.levelPanel.removeNonWaterItems();
                }
                else if (levelTypeComboBox.getSelectedIndex() != 3 && PropertiesPanel.this.frame.levelPanel.level.levelType == 3) {
                    final String prompt = "Changing to a land level will remove any water enemies in this level.\r\nAre you sure you want to proceed?";
                    final int answer = JOptionPane.showConfirmDialog(PropertiesPanel.this.frame.propertiesContainer, prompt, "Change to Land Level", 0);
                    if (answer != 0) {
                        return;
                    }
                    clearUndoRedoStack = true;
                    hasLakituCheckBox.setEnabled(true);
                    hasFlyingFishCheckBox.setEnabled(true);
                    PropertiesPanel.this.frame.levelPanel.removeNonLandItems();
                }
                else if (levelTypeComboBox.getSelectedIndex() != 3) {
                    hasLakituCheckBox.setEnabled(true);
                    hasFlyingFishCheckBox.setEnabled(true);
                    
                }
                final String newName1 = name1TextBox.getText();
                final String newName2 = name2TextBox.getText();
                if (!PropertiesPanel.this.frame.levelPanel.level.name1.equals(newName1) || !PropertiesPanel.this.frame.levelPanel.level.name2.equals(newName2)) {
                    PropertiesPanel.this.frame.levelPanel.level.name1 = newName1;
                    PropertiesPanel.this.frame.levelPanel.level.name2 = newName2;
                    PropertiesPanel.this.frame.gameListPanel.repopulate(PropertiesPanel.this.frame.levelPanel.level.levelNumber);
                }
                PropertiesPanel.this.frame.levelPanel.level.hasLakitu = hasLakituCheckBox.isSelected();
                PropertiesPanel.this.frame.levelPanel.level.hasFlyingFish = hasFlyingFishCheckBox.isSelected();
                PropertiesPanel.this.frame.levelPanel.level.hasBullets = hasBulletsCheckBox.isSelected();
                PropertiesPanel.this.frame.levelPanel.level.blackAndWhite = blackAndWhiteCheckBox.isSelected();
                PropertiesPanel.this.frame.levelPanel.level.autoScrolling = autoScrollingCheckBox.isSelected();
                PropertiesPanel.this.frame.levelPanel.level.texturePack = textureTypeComboBox.getSelectedIndex();
                PropertiesPanel.this.frame.levelPanel.level.levelType = levelTypeComboBox.getSelectedIndex();
                PropertiesPanel.this.updateButtonImages();
                PropertiesPanel.this.frame.levelPanel.level.changeLevelEndType(levelEndComboBox.getSelectedIndex(), true);
                if (PropertiesPanel.this.warpsToLevelComboBox.isEnabled()) {
                    if (PropertiesPanel.this.warpsToLevelComboBox.getSelectedIndex() == PropertiesPanel.this.warpsToLevelComboBox.getItemCount() - 1) {
                        PropertiesPanel.this.frame.levelPanel.level.nextLevelNumber = 999;
                    }
                    else {
                        PropertiesPanel.this.frame.levelPanel.level.nextLevelNumber = PropertiesPanel.this.warpsToLevelComboBox.getSelectedIndex();
                    }
                }
                else {
                    PropertiesPanel.this.frame.levelPanel.level.nextLevelNumber = -1;
                }
                if (PropertiesPanel.this.warpsToIDComboBox.isEnabled()) {
                    PropertiesPanel.this.frame.levelPanel.level.cliffDestLevel = PropertiesPanel.this.warpsOnCliffLevelComboBox.getSelectedIndex() - 1;
                    if (PropertiesPanel.this.warpsToIDComboBox.getSelectedIndex() == 0) {
                        PropertiesPanel.this.frame.levelPanel.level.cliffDestID = 0;
                    }
                    else {
                        PropertiesPanel.this.frame.levelPanel.level.cliffDestID = PropertiesPanel.this.frame.levelPanel.levels[PropertiesPanel.this.frame.levelPanel.level.cliffDestLevel].incomingWarps.get(PropertiesPanel.this.warpsToIDComboBox.getSelectedIndex() - 1).sourceWarpID;
                    }
                }
                else {
                    PropertiesPanel.this.frame.levelPanel.level.cliffDestLevel = -1;
                }
                PropertiesPanel.this.frame.levelPanel.level.pipeColor = pipeColorComboBox.getSelectedIndex();
                PropertiesPanel.this.frame.levelPanel.setLevelScheme();
                PropertiesPanel.this.frame.levelPanel.modified = true;
                PropertiesPanel.this.frame.changePropertiesPanel(new PropertiesPanel(PropertiesPanel.this.frame, 2, null));
                PropertiesPanel.this.frame.propertiesScrollPane.getVerticalScrollBar().setValue(scrollValue);
                if (clearUndoRedoStack) {
                    PropertiesPanel.this.frame.clearStacks();
                }
                else if (!oldName1.equals(l.name1) || !oldName2.equals(l.name2) || oldTimed != l.timedLevel || oldTime != l.levelTime || oldLevelType != l.levelType || oldLevelEndIndex != l.getLevelEndTypeIndexFromEndType(l.levelEndType) || oldNextLevel != l.nextLevelNumber || oldLakitu != l.hasLakitu || oldFish != l.hasFlyingFish || oldBullets != l.hasBullets || oldBandW != l.blackAndWhite || oldDeathLevel != l.cliffDestLevel || oldDeathID != l.cliffDestID || oldPipeColor != l.pipeColor || oldAutoScrolling != l.autoScrolling || oldTexturePack != l.texturePack) {
                    PropertiesPanel.this.frame.undoableActionMade(Action.levelPropChange(oldName1, oldName2, oldTimed, oldTime, oldLevelType, oldLevelEndIndex, oldNextLevel, oldLakitu, oldFish, oldBullets, oldBandW, oldDeathLevel, oldDeathID, oldPipeColor, oldAutoScrolling, oldTexturePack, l.name1, l.name2, l.timedLevel, l.levelTime, l.levelType, l.getLevelEndTypeIndexFromEndType(l.levelEndType), l.nextLevelNumber, l.hasLakitu, l.hasFlyingFish, l.hasBullets, l.blackAndWhite, l.cliffDestLevel, l.cliffDestID, l.pipeColor, l.autoScrolling, l.texturePack));
                }
            }
        });
        this.addBlank(row++);
    }
    
    public void updateButtonImages(){
    	//supermario.game.Textures textures = this.frame.game.textures;
    	//frame.backgroundPanel.bigHill.placedImage = textures.getLevelTypeAlt(leveltype,frame.backgroundPanel.bigHill.placedImage);
    	
    	frame.backgroundPanel.refreshIcons();
    	frame.solidsPanel.refreshIcons();
    	frame.blocksPanel.refreshIcons();//setBlocksScheme(leveltype);
    	frame.enemiesPanel.refreshIcons();
    	frame.miscPanel.refreshIcons();
    	frame.pipesPanel.setPipeColor(0);
	}

	private void populateEndingsComboBox(final int levelType, final JComboBox<String> levelEndComboBox) {
        String[] levelEndNames = null;
        if (levelType == 0 || levelType == 4) {
            levelEndNames = new String[] { " Nothing ", " Flag w/Small Castle ", " Flag w/Large Castle " };
        }
        else if (levelType == 1 || levelType == 3 || levelType == 5 || levelType == 6 || levelType == 7) {
            levelEndNames = new String[] { " Nothing " };
        }
        else if (levelType == 2) {
            levelEndNames = new String[] { " Nothing ", " Bowser Battle World 1", " Bowser Battle World 2", " Bowser Battle World 3", " Bowser Battle World 4", " Bowser Battle World 5", " Bowser Battle World 6", " Bowser Battle World 7", " Bowser Battle World 8" };
        }
        levelEndComboBox.setModel(new DefaultComboBoxModel<String>(levelEndNames));
        levelEndComboBox.setSelectedIndex(0);
    }
    
    private void initShiftableBackgroundComponents() {
        final JLabel orientationLabel = new JLabel("Shift Over:");
        orientationLabel.setFont(this.frame.bold);
        this.add(orientationLabel, getConstraints(0, 1, 1, 1, 0, 0, 10, 0, 5, 0, 0, 0));
        final JButton orientationButton = new JButton();
        orientationButton.setFont(this.frame.plain);
        orientationButton.setAlignmentX(0.5f);
        if (!this.item.shifted) {
            orientationButton.setText("<html><center>Shift To<br>Left Side</center></html>");
        }
        else {
            orientationButton.setText("<html><center>Shift To<br>Right Side</center></html>");
        }
        orientationButton.setIcon(this.item.button.iconImage);
        orientationButton.setHorizontalTextPosition(0);
        orientationButton.setVerticalTextPosition(3);
        orientationButton.setMargin(new Insets(1, 1, 1, 1));
        orientationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (!PropertiesPanel.this.item.shifted) {
                    PropertiesPanel.this.item.shifted = true;
                    orientationButton.setText("<html><center>Shift To<br>Right Side</center></html>");
                }
                else {
                    PropertiesPanel.this.item.shifted = false;
                    orientationButton.setText("<html><center>Shift To<br>Left Side</center></html>");
                }
                if (PropertiesPanel.this.item.inserted) {
                    PropertiesPanel.this.frame.undoableActionMade(Action.backgroundReverse(PropertiesPanel.this.item));
                }
                PropertiesPanel.this.frame.levelPanel.repaint();
                PropertiesPanel.this.frame.levelPanel.modified = true;
            }
        });
        this.add(orientationButton, getConstraints(0, 2, 1, 1, 0, 0, 10, 0, 0, 0, 0, 0));
        this.addBlank(3);
    }
    
    private void initReversibleEnemyComponents() {
        final JLabel orientationLabel = new JLabel("Orientation:");
        orientationLabel.setFont(this.frame.bold);
        this.add(orientationLabel, getConstraints(0, 1, 1, 1, 0, 0, 10, 0, 5, 0, 0, 0));
        final JButton orientationButton = new JButton();
        orientationButton.setFont(this.frame.plain);
        orientationButton.setAlignmentX(0.5f);
        if (!this.item.flip) {
            orientationButton.setText("  Starts Left  ");
        }
        else {
            orientationButton.setText(" Starts Right ");
        }
        orientationButton.setIcon(this.item.button.iconImage);
        orientationButton.setHorizontalTextPosition(0);
        orientationButton.setVerticalTextPosition(3);
        orientationButton.setMargin(new Insets(1, 1, 1, 1));
        orientationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (!PropertiesPanel.this.item.flip) {
                    PropertiesPanel.this.item.flip = true;
                    orientationButton.setText(" Starts Right ");
                }
                else {
                    PropertiesPanel.this.item.flip = false;
                    orientationButton.setText("  Starts Left  ");
                }
                if (PropertiesPanel.this.item.inserted) {
                    PropertiesPanel.this.frame.undoableActionMade(Action.enemyReverse(PropertiesPanel.this.item));
                }
                PropertiesPanel.this.frame.levelPanel.repaint();
                PropertiesPanel.this.frame.levelPanel.modified = true;
            }
        });
        this.add(orientationButton, getConstraints(0, 2, 1, 1, 0, 0, 10, 0, 0, 0, 0, 0));
        this.addBlank(3);
    }
    
    private void initFirebarComponents() {
        final JLabel orientationLabel = new JLabel("Orientation:");
        orientationLabel.setFont(this.frame.bold);
        this.add(orientationLabel, getConstraints(0, 1, 1, 1, 0, 0, 10, 0, 5, 0, 0, 0));
        final JButton orientationButton = new JButton();
        orientationButton.setFont(this.frame.plain);
        orientationButton.setAlignmentX(0.5f);
        if (!this.item.flip) {
            orientationButton.setText("  Starts Left  ");
        }
        else {
            orientationButton.setText(" Starts Right ");
        }
        orientationButton.setIcon(this.item.button.placedImage);
        orientationButton.setHorizontalTextPosition(0);
        orientationButton.setVerticalTextPosition(3);
        orientationButton.setMargin(new Insets(1, 1, 1, 1));
        orientationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (!PropertiesPanel.this.item.flip) {
                    PropertiesPanel.this.item.flip = true;
                    orientationButton.setText(" Starts Right ");
                }
                else {
                    PropertiesPanel.this.item.flip = false;
                    orientationButton.setText("  Starts Left  ");
                }
                if (PropertiesPanel.this.item.inserted) {
                    PropertiesPanel.this.frame.undoableActionMade(Action.firebarPropChange(PropertiesPanel.this.item, !PropertiesPanel.this.item.flip, PropertiesPanel.this.item.flip, PropertiesPanel.this.item.speedBoost, PropertiesPanel.this.item.speedBoost));
                }
                PropertiesPanel.this.frame.levelPanel.repaint();
                PropertiesPanel.this.frame.levelPanel.modified = true;
            }
        });
        this.add(orientationButton, getConstraints(0, 2, 1, 1, 0, 0, 10, 0, 0, 0, 10, 0));
        final JCheckBox speedCheckBox = new JCheckBox("Speed Boost");
        speedCheckBox.setSelected(this.item.speedBoost);
        speedCheckBox.setFont(this.frame.bold);
        speedCheckBox.setOpaque(false);
        speedCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (speedCheckBox.isSelected()) {
                    PropertiesPanel.this.item.speedBoost = true;
                }
                else {
                    PropertiesPanel.this.item.speedBoost = false;
                }
                if (PropertiesPanel.this.item.inserted) {
                    PropertiesPanel.this.frame.undoableActionMade(Action.firebarPropChange(PropertiesPanel.this.item, PropertiesPanel.this.item.flip, PropertiesPanel.this.item.flip, !PropertiesPanel.this.item.speedBoost, PropertiesPanel.this.item.speedBoost));
                }
                PropertiesPanel.this.frame.levelPanel.repaint();
            }
        });
        this.add(speedCheckBox, getConstraints(0, 3, 1, 1, 0, 0, 10, 0, 0, 0, 0, 0));
        this.addBlank(4);
    }
    
    private void initRepPlatformComponents() {
        final JCheckBox singlePlatformCheckBox = new JCheckBox("Single Platform");
        singlePlatformCheckBox.setSelected(this.item.singlePlatform);
        singlePlatformCheckBox.setFont(this.frame.bold);
        singlePlatformCheckBox.setOpaque(false);
        singlePlatformCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (singlePlatformCheckBox.isSelected()) {
                    PropertiesPanel.this.item.singlePlatform = true;
                }
                else {
                    PropertiesPanel.this.item.singlePlatform = false;
                }
                if (PropertiesPanel.this.item.inserted) {
                    PropertiesPanel.this.frame.undoableActionMade(Action.repPlatformPropChange(PropertiesPanel.this.item));
                }
                PropertiesPanel.this.frame.levelPanel.repaint();
            }
        });
        this.add(singlePlatformCheckBox, getConstraints(0, 1, 1, 1, 0, 0, 10, 0, 0, 0, 0, 0));
        this.addBlank(2);
    }
    
    private void initOscillatingOffsetComponents() {
        final JLabel oscOffsetLabel = new JLabel("Oscillation Offset:");
        oscOffsetLabel.setToolTipText("The offset that the oscillation starts with...");
        oscOffsetLabel.setFont(this.frame.bold);
        this.add(oscOffsetLabel, getConstraints(0, 1, 1, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        final JLabel oscIndicator = new JLabel();
        oscIndicator.setFont(this.frame.bold.deriveFont(16.0f));
        oscIndicator.setToolTipText("The offset that the oscillation starts with...");
        oscIndicator.setText(this.item.oscOffset * 25 + "%");
        this.add(oscIndicator, getConstraints(0, 3, 1, 1, 0, 0, 10, 0, 0, 2, 5, 2));
        final JSlider oscOffsetSlider = new JSlider(0, 0, 3, this.item.oscOffset);
        oscOffsetSlider.setPreferredSize(new Dimension(100, oscOffsetSlider.getPreferredSize().height + 20));
        oscOffsetSlider.setToolTipText("The offset that the oscillation starts with...");
        oscOffsetSlider.setSnapToTicks(true);
        oscOffsetSlider.setPaintTicks(true);
        oscOffsetSlider.setMajorTickSpacing(1);
        oscOffsetSlider.setMinorTickSpacing(1);
        oscOffsetSlider.setOpaque(false);
        oscOffsetSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent e) {
                final int oldOffset = PropertiesPanel.this.item.oscOffset;
                PropertiesPanel.this.item.oscOffset = oscOffsetSlider.getValue();
                if (oldOffset != PropertiesPanel.this.item.oscOffset && PropertiesPanel.this.item.inserted) {
                    PropertiesPanel.this.frame.undoableActionMade(Action.oscPlatformPropChange(PropertiesPanel.this.item, oldOffset, PropertiesPanel.this.item.oscOffset));
                    PropertiesPanel.this.frame.levelPanel.repaint();
                }
                oscIndicator.setText(PropertiesPanel.this.item.oscOffset * 25 + "%");
            }
        });
        this.add(oscOffsetSlider, getConstraints(0, 2, 1, 1, 0, 0, 10, 0, 0, 2, 5, 2));
        this.addBlank(4);
    }
    
    private void initCheckpointComponents() {
        final JLabel checkpointLabel = new JLabel("Checkpoint Type:");
        checkpointLabel.setToolTipText("Sets the type of checkpoint in the level...");
        checkpointLabel.setFont(this.frame.bold);
        checkpointLabel.setIcon(this.item.button.iconImage);
        checkpointLabel.setHorizontalTextPosition(0);
        checkpointLabel.setVerticalTextPosition(3);
        this.add(checkpointLabel, getConstraints(0, 1, 4, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        final ButtonGroup buttonGroup = new ButtonGroup();
        final JRadioButton checkpointFlag = new JRadioButton("Visible Flag");
        checkpointFlag.setToolTipText("A visible flag that must be grabbed at its location...");
        checkpointFlag.setFont(this.frame.bold);
        checkpointFlag.setOpaque(false);
        checkpointFlag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int flag = 0;
                final int column = 1;
                if (PropertiesPanel.this.item.checkpointType != flag) {
                    PropertiesPanel.this.item.checkpointType = flag;
                    if (PropertiesPanel.this.item.inserted) {
                        PropertiesPanel.this.frame.undoableActionMade(Action.checkpointPropChange(PropertiesPanel.this.item, column, flag));
                    }
                    PropertiesPanel.this.frame.levelPanel.repaint();
                }
            }
        });
        this.add(checkpointFlag, getConstraints(1, 2, 1, 1, 0, 0, 17, 0, 0, 0, 5, 0));
        final JRadioButton checkpointColumn = new JRadioButton("Invisible Column");
        checkpointColumn.setToolTipText("An invisible column that only needs to be passed through...");
        checkpointColumn.setFont(this.frame.bold);
        checkpointColumn.setOpaque(false);
        checkpointColumn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int flag = 0;
                final int column = 1;
                if (PropertiesPanel.this.item.checkpointType != column) {
                    PropertiesPanel.this.item.checkpointType = column;
                    if (PropertiesPanel.this.item.inserted) {
                        PropertiesPanel.this.frame.undoableActionMade(Action.checkpointPropChange(PropertiesPanel.this.item, flag, column));
                    }
                    PropertiesPanel.this.frame.levelPanel.repaint();
                }
            }
        });
        this.add(checkpointColumn, getConstraints(1, 3, 1, 1, 0, 0, 17, 0, 0, 0, 5, 0));
        if (this.item.checkpointType == 0) {
            checkpointFlag.setSelected(true);
        }
        else {
            checkpointColumn.setSelected(true);
        }
        buttonGroup.add(checkpointFlag);
        buttonGroup.add(checkpointColumn);
        this.addBlank(4);
    }
    
    private void initLavaballComponents() {
        final JLabel lavaballColorLabel = new JLabel("Background Color:");
        lavaballColorLabel.setFont(this.frame.bold);
        lavaballColorLabel.setIcon(this.item.button.iconImage);
        lavaballColorLabel.setHorizontalTextPosition(0);
        lavaballColorLabel.setVerticalTextPosition(3);
        this.add(lavaballColorLabel, getConstraints(0, 1, 1, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        final String[] colorList = { " Red (Lava)", " Blue (Water)", " Blank" };
        final JComboBox lavaballColorComboBox = new JComboBox(colorList);
        lavaballColorComboBox.setToolTipText("The background color behind this fireball...");
        lavaballColorComboBox.setFont(this.frame.plain);
        lavaballColorComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int index = ((JComboBox)e.getSource()).getSelectedIndex();
                if (PropertiesPanel.this.item.fireballColor != index && PropertiesPanel.this.item.inserted) {
                    PropertiesPanel.this.frame.undoableActionMade(Action.lavaballPropChange(PropertiesPanel.this.item, PropertiesPanel.this.item.fireballColor, index));
                }
                PropertiesPanel.this.item.fireballColor = index;
                PropertiesPanel.this.frame.levelPanel.modified = true;
                PropertiesPanel.this.frame.levelPanel.repaint();
            }
        });
        lavaballColorComboBox.setSelectedIndex(this.item.fireballColor);
        this.add(lavaballColorComboBox, getConstraints(0, 2, 1, 1, 0, 0, 10, 0, 0, 0, 0, 0));
        this.addBlank(3);
    }
    
    private void initMultipleCoinsComponents() {
        final JLabel coinCountLabel = new JLabel("Coins Inside:");
        coinCountLabel.setFont(this.frame.bold);
        coinCountLabel.setIcon(this.item.button.iconImage);
        coinCountLabel.setHorizontalTextPosition(0);
        coinCountLabel.setVerticalTextPosition(3);
        this.add(coinCountLabel, getConstraints(0, 1, 1, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        final String[] countList = { " Random ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 8 ", " 9 " };
        final JComboBox coinCountComboBox = new JComboBox(countList);
        coinCountComboBox.setToolTipText("The number of coins stored inside this brick (between 2 and 9)...");
        coinCountComboBox.setFont(this.frame.plain);
        coinCountComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int index = ((JComboBox)e.getSource()).getSelectedIndex();
                final int oldCount = PropertiesPanel.this.item.coinCount;
                if (index == 0) {
                    PropertiesPanel.this.item.coinCount = 0;
                }
                else {
                    PropertiesPanel.this.item.coinCount = ((JComboBox)e.getSource()).getSelectedIndex() + 1;
                }
                if (PropertiesPanel.this.item.inserted && oldCount != PropertiesPanel.this.item.coinCount) {
                    PropertiesPanel.this.frame.undoableActionMade(Action.coinCount(PropertiesPanel.this.item, oldCount, PropertiesPanel.this.item.coinCount));
                }
                PropertiesPanel.this.frame.levelPanel.modified = true;
            }
        });
        if (this.item.coinCount == 0) {
            coinCountComboBox.setSelectedIndex(0);
        }
        else {
            coinCountComboBox.setSelectedIndex(this.item.coinCount - 1);
        }
        this.add(coinCountComboBox, getConstraints(0, 2, 1, 1, 0, 0, 10, 0, 0, 0, 0, 0));
        this.addBlank(3);
    }
    
    private void initArrivalVineComponents() {
        final JButton traceWarps = new JButton("Trace Warps");
        traceWarps.setFont(this.frame.bold);
        traceWarps.setToolTipText("Find any warps in the game that lead here...");
        traceWarps.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PropertiesPanel.this.frame.levelPanel.traceWarps(PropertiesPanel.this.item.warp);
            }
        });
        this.add(traceWarps, getConstraints(0, 2, 1, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        this.addBlank(3);
    }
    
    private void initCustomTextComponents() {
        final JLabel customTextLabel = new JLabel("Custom Text");
        customTextLabel.setFont(this.frame.bold);
        customTextLabel.setHorizontalTextPosition(0);
        customTextLabel.setVerticalTextPosition(1);
        this.add(customTextLabel, getConstraints(0, 1, 1, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        final TextBox customTextBox = new TextBox(1, false, true, true);
        customTextBox.setToolTipText("The input field for choosing characters to insert into the level.");
        customTextBox.setText(this.frame.game.textures.customTextChars.get(this.item.character).toString().toUpperCase());
        customTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent e) {
                final char newChar = PropertiesPanel.this.frame.game.textures.customTextCharsInverted.get(customTextBox.getText().toLowerCase().charAt(0));
                if (PropertiesPanel.this.item.character != newChar) {
                    PropertiesPanel.this.item.character = newChar;
                    PropertiesPanel.this.frame.levelPanel.repaint();
                }
            }
            
            @Override
            public void removeUpdate(final DocumentEvent e) {
            }
            
            @Override
            public void changedUpdate(final DocumentEvent e) {
            }
        });
        this.add(customTextBox, getConstraints(0, 2, 1, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        final JLabel textHelp = new JLabel("<html><center>Use the above input field<br>to choose the character<br> you wish to insert.</center></html>");
        this.add(textHelp, getConstraints(0, 3, 1, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        this.addBlank(4);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                customTextBox.requestFocusInWindow();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        customTextBox.getCaret().setVisible(false);
                    }
                });
            }
        });
    }
    
    private void initPowerupComponents() {
        final ImageIcon powerup = this.frame.textures.powerup;
        final ImageIcon poison = this.frame.game.textures.poisonMushroom;
        final JLabel mushroomIcon = new JLabel(this.item.poison ? poison : powerup);
        this.add(mushroomIcon, getConstraints(0, 1, 1, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        final JCheckBox poisonMushroomCheckBox = new JCheckBox("Poison Mushroom");
        poisonMushroomCheckBox.setFont(this.frame.bold);
        poisonMushroomCheckBox.setToolTipText("Makes this powerup block hold a mushroom that hurts Mario...");
        poisonMushroomCheckBox.setBackground(this.getBackground());
        poisonMushroomCheckBox.setSelected(this.item.poison);
        poisonMushroomCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PropertiesPanel.this.item.poison = !PropertiesPanel.this.item.poison;
                if (PropertiesPanel.this.item.inserted) {
                    PropertiesPanel.this.frame.undoableActionMade(Action.powerupPropChange(PropertiesPanel.this.item));
                }
                mushroomIcon.setIcon(PropertiesPanel.this.item.poison ? poison : powerup);
                PropertiesPanel.this.frame.levelPanel.repaint();
            }
        });
        this.add(poisonMushroomCheckBox, getConstraints(0, 2, 1, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        this.addBlank(3);
    }
    
    private void initSpringComponents() {
        final ImageIcon springImage = this.item.button.iconImage;
        final ImageIcon greenSpringImage = this.frame.miscPanel.currentGreenSpring;
        final JLabel springIcon = new JLabel(this.item.superSpring ? greenSpringImage : springImage);
        this.add(springIcon, getConstraints(0, 1, 1, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        final JCheckBox springCheckBox = new JCheckBox("Super Spring");
        springCheckBox.setFont(this.frame.bold);
        springCheckBox.setToolTipText("Makes this spring launch Mario much higher than normal...");
        springCheckBox.setBackground(this.getBackground());
        springCheckBox.setSelected(this.item.superSpring);
        springCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PropertiesPanel.this.item.superSpring = !PropertiesPanel.this.item.superSpring;
                if (PropertiesPanel.this.item.inserted) {
                    PropertiesPanel.this.frame.undoableActionMade(Action.springPropChange(PropertiesPanel.this.item));
                }
                springIcon.setIcon(PropertiesPanel.this.item.superSpring ? greenSpringImage : springImage);
                PropertiesPanel.this.frame.levelPanel.repaint();
            }
        });
        this.add(springCheckBox, getConstraints(0, 2, 1, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        this.addBlank(3);
    }
    
    private void initPipeWarpComponents() {
        int gridY = 1;
        final JLabel warpsToLabel = new JLabel("Warp Settings");
        if (this.item.warp.incoming) {
            warpsToLabel.setText("<html><center>Warp Settings (ID " + this.item.warp.sourceWarpID + ")</center></html>");
        }
        warpsToLabel.setFont(this.frame.bold);
        warpsToLabel.setHorizontalTextPosition(0);
        warpsToLabel.setVerticalTextPosition(1);
        if (this.item.redPiranha) {
            warpsToLabel.setIcon(ImageBuilder.createPipeImage(LevelLoader.getPipeType(this.item.character), this.frame.pipesPanel.getCurrentPipeColor(), 0, true));
        }
        else {
            warpsToLabel.setIcon(this.item.button.iconImage);
        }
        this.add(warpsToLabel, getConstraints(0, gridY++, 1, 1, 0, 0, 10, 0, 0, 0, 10, 0));
        if (this.item.character == '~' || this.item.character == ';' || this.item.character == '\u0110' || this.item.character == '¿' || LevelLoader.isSideOpeningPipe(this.item.character)) {
            String piranhaMessage = "Add Piranha";
            boolean hasChomper = false;
            if (this.item.character == ';' || this.item.character == '¿' || this.item.character == '\u2021' || this.item.character == '\u00ee') {
                piranhaMessage = "Remove Piranha";
                hasChomper = true;
            }
            final JButton togglePiranha = new JButton(piranhaMessage);
            togglePiranha.setFont(this.frame.bold);
            togglePiranha.setToolTipText("Toggles a Piranha enemy inside the pipe...");
            togglePiranha.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    final Item oldItem = PropertiesPanel.this.item;
                    Item newItem = null;
                    if (oldItem.character == '~') {
                        newItem = PropertiesPanel.this.frame.pipesPanel.topWChomp.item.copy();
                    }
                    else if (oldItem.character == ';') {
                        newItem = PropertiesPanel.this.frame.pipesPanel.topWOChomp.item.copy();
                    }
                    else if (oldItem.character == '\u0110') {
                        newItem = PropertiesPanel.this.frame.pipesPanel.bottomWChomp.item.copy();
                    }
                    else if (oldItem.character == '¿') {
                        newItem = PropertiesPanel.this.frame.pipesPanel.bottomWOChomp.item.copy();
                    }
                    else if (oldItem.character == '!') {
                        newItem = PropertiesPanel.this.frame.pipesPanel.leftWChomp.item.copy();
                    }
                    else if (oldItem.character == '\u2021') {
                        newItem = PropertiesPanel.this.frame.pipesPanel.leftWOChomp.item.copy();
                    }
                    else if (oldItem.character == '\u2663') {
                        newItem = PropertiesPanel.this.frame.pipesPanel.rightWChomp.item.copy();
                    }
                    else if (oldItem.character == '\u00ee') {
                        newItem = PropertiesPanel.this.frame.pipesPanel.rightWOChomp.item.copy();
                    }
                    newItem.warp = oldItem.warp;
                    oldItem.removeFromLevel();
                    newItem.inserted = true;
                    newItem.insertInLevel(oldItem.xTile, oldItem.yTile);
                    PropertiesPanel.this.frame.undoableActionMade(Action.togglePiranha(newItem));
                    PropertiesPanel.this.frame.changePropertiesPanel(new PropertiesPanel(PropertiesPanel.this.frame, 5, newItem));
                    PropertiesPanel.this.frame.levelPanel.repaint();
                }
            });
            this.add(togglePiranha, getConstraints(0, gridY++, 1, 1, 0, 0, 10, 0, 0, 0, hasChomper ? 0 : 10, 0));
            if (hasChomper) {
                final JCheckBox redPiranha = new JCheckBox("Red Piranha");
                redPiranha.setFont(this.frame.bold);
                redPiranha.setToolTipText("Sets this piranha to be the red type...");
                redPiranha.setBackground(this.getBackground());
                redPiranha.setSelected(this.item.redPiranha);
                redPiranha.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        PropertiesPanel.this.item.redPiranha = !PropertiesPanel.this.item.redPiranha;
                        PropertiesPanel.this.frame.undoableActionMade(Action.toggleRedPiranha(PropertiesPanel.this.item));
                        PropertiesPanel.this.frame.changePropertiesPanel(new PropertiesPanel(PropertiesPanel.this.frame, 5, PropertiesPanel.this.item));
                        PropertiesPanel.this.frame.levelPanel.repaint();
                    }
                });
                this.add(redPiranha, getConstraints(0, gridY++, 1, 1, 0, 0, 10, 0, 0, 0, 0, 0));
            }
        }
        final JCheckBox canBeWarpedToCheckBox = new JCheckBox("Can Be Warped To");
        final JButton traceWarps = new JButton("Trace Warps");
        canBeWarpedToCheckBox.setFont(this.frame.bold);
        canBeWarpedToCheckBox.setToolTipText("Check for other warps to be able to lead to this warp...");
        canBeWarpedToCheckBox.setBackground(this.getBackground());
        canBeWarpedToCheckBox.setSelected(this.item.warp.incoming);
        canBeWarpedToCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final Warp warp = PropertiesPanel.this.item.warp;
                if (canBeWarpedToCheckBox.isSelected()) {
                    if (PropertiesPanel.this.frame.levelPanel.level.incomingWarps.size() >= 999) {
                        canBeWarpedToCheckBox.setSelected(false);
                        JOptionPane.showMessageDialog(PropertiesPanel.this.frame, "The max warps in this level have been reached. What kind of game are you making?!", "Max Warps Reached", 2);
                        return;
                    }
                    final Warp oldWarp = warp.copy(PropertiesPanel.this.item);
                    warp.incoming = true;
                    warp.sourceLevelNumber = PropertiesPanel.this.frame.levelPanel.level.levelNumber;
                    warp.sourceWarpID = PropertiesPanel.this.frame.levelPanel.level.getNextAvailableWarpID();
                    PropertiesPanel.this.frame.undoableActionMade(Action.pipePropChange(PropertiesPanel.this.item, warp, oldWarp, warp.copy(PropertiesPanel.this.item)));
                    PropertiesPanel.this.frame.levelPanel.level.insertIncomingWarp(warp);
                    warpsToLabel.setText("<html><center>Warp Settings (ID " + warp.sourceWarpID + ")</center></html>");
                    traceWarps.setEnabled(true);
                }
                else if (PropertiesPanel.this.frame.levelPanel.checkWarpConflict(warp)) {
                    final Warp oldWarp = warp.copy(PropertiesPanel.this.item);
                    warp.incoming = false;
                    PropertiesPanel.this.frame.undoableActionMade(Action.pipePropChange(PropertiesPanel.this.item, warp, oldWarp, warp.copy(PropertiesPanel.this.item)));
                    warpsToLabel.setText("Warp Settings");
                    PropertiesPanel.this.frame.levelPanel.level.incomingWarps.remove(warp);
                    traceWarps.setEnabled(false);
                }
                else {
                    canBeWarpedToCheckBox.setSelected(true);
                }
                PropertiesPanel.this.populateLevelComboBox();
                PropertiesPanel.this.populateIDComboBox();
                PropertiesPanel.this.setWarpIndices();
                PropertiesPanel.this.frame.levelPanel.repaint();
                PropertiesPanel.this.frame.levelPanel.modified = true;
            }
        });
        traceWarps.setFont(this.frame.bold);
        traceWarps.setToolTipText("Find any warps in the game that lead here...");
        traceWarps.setEnabled(canBeWarpedToCheckBox.isSelected());
        traceWarps.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PropertiesPanel.this.frame.levelPanel.traceWarps(PropertiesPanel.this.item.warp);
            }
        });
        this.add(canBeWarpedToCheckBox, getConstraints(0, gridY++, 1, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        this.add(traceWarps, getConstraints(0, gridY++, 1, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        this.addSeparator(gridY++, 1);
        final JLabel warpsToLevelLabel = new JLabel("Goes To Level:");
        warpsToLevelLabel.setFont(this.frame.bold);
        this.add(warpsToLevelLabel, getConstraints(0, gridY++, 1, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        (this.warpsToLevelComboBox = new JComboBox<String>()).setFont(this.frame.plain);
        this.warpsToLevelComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PropertiesPanel.this.populateIDComboBox();
            }
        });
        this.add(this.warpsToLevelComboBox, getConstraints(0, gridY++, 1, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        final JLabel warpsToIDLabel = new JLabel("Goes To ID:");
        warpsToIDLabel.setFont(this.frame.bold);
        this.add(warpsToIDLabel, getConstraints(0, gridY++, 1, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        final JPanel goToPanel = new JPanel();
        goToPanel.setLayout(new BoxLayout(goToPanel, 0));
        (this.warpsToIDComboBox = new JComboBox<String>()).setFont(this.frame.plain);
        this.warpsToIDComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PropertiesPanel.this.goToButton.setVisible(PropertiesPanel.this.item.warp.outgoing && PropertiesPanel.this.warpsToIDComboBox.getSelectedIndex() == PropertiesPanel.this.frame.levelPanel.levels[PropertiesPanel.this.item.warp.destLevelNumber].getWarpsIndex(PropertiesPanel.this.item.warp.destWarpID) + 1);
            }
        });
        goToPanel.add(this.warpsToIDComboBox);
        (this.goToButton = new JButton("Go")).setFont(this.frame.bold);
        this.goToButton.setToolTipText("Jump to the place this warp heads to...");
        this.goToButton.setMargin(new Insets(2, 2, 2, 2));
        this.goToButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PropertiesPanel.this.frame.levelPanel.findWarpDestination(PropertiesPanel.this.item.warp);
            }
        });
        goToPanel.add(this.goToButton);
        this.add(goToPanel, getConstraints(0, gridY++, 1, 1, 0, 0, 10, 0, 0, 0, 15, 0));
        final JButton updateInfo = new JButton("Apply");
        updateInfo.setFont(this.frame.bold);
        updateInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int sourceLevelNumber = PropertiesPanel.this.frame.levelPanel.level.levelNumber;
                final int sourceWarpID = PropertiesPanel.this.item.warp.sourceWarpID;
                final boolean incoming = PropertiesPanel.this.item.warp.incoming;
                boolean outgoing = false;
                int destLevelNumber = -1;
                int destWarpID = -1;
                if (PropertiesPanel.this.warpsToLevelComboBox.getSelectedIndex() >= 1) {
                    outgoing = true;
                    if (PropertiesPanel.this.warpsToLevelComboBox.getSelectedIndex() == PropertiesPanel.this.warpsToLevelComboBox.getItemCount() - 1) {
                        destLevelNumber = 999;
                    }
                    else {
                        destLevelNumber = PropertiesPanel.this.warpsToLevelComboBox.getSelectedIndex() - 1;
                    }
                    if (PropertiesPanel.this.warpsToIDComboBox.getSelectedIndex() == 0 || destLevelNumber == 999) {
                        destWarpID = 0;
                    }
                    else {
                        final Warp destWarp = PropertiesPanel.this.frame.levelPanel.levels[destLevelNumber].incomingWarps.get(PropertiesPanel.this.warpsToIDComboBox.getSelectedIndex() - 1);
                        destWarpID = destWarp.sourceWarpID;
                    }
                }
                final Warp oldWarp = PropertiesPanel.this.item.warp.copy(PropertiesPanel.this.item);
                PropertiesPanel.this.item.warp.update(outgoing, incoming, sourceLevelNumber, sourceWarpID, destLevelNumber, destWarpID, PropertiesPanel.this.item);
                PropertiesPanel.this.frame.undoableActionMade(Action.pipePropChange(PropertiesPanel.this.item, PropertiesPanel.this.item.warp, oldWarp, PropertiesPanel.this.item.warp.copy(PropertiesPanel.this.item)));
                PropertiesPanel.this.frame.levelPanel.level.outgoingWarps.remove(PropertiesPanel.this.item.warp);
                if (outgoing) {
                    PropertiesPanel.this.frame.levelPanel.level.outgoingWarps.add(PropertiesPanel.this.item.warp);
                }
                PropertiesPanel.this.populateLevelComboBox();
                PropertiesPanel.this.populateIDComboBox();
                PropertiesPanel.this.setWarpIndices();
                PropertiesPanel.this.frame.levelPanel.modified = true;
                PropertiesPanel.this.frame.levelPanel.repaint();
            }
        });
        this.add(updateInfo, getConstraints(0, gridY++, 1, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        this.addBlank(gridY++);
        this.populateLevelComboBox();
        this.populateIDComboBox();
        this.setWarpIndices();
    }
    
    private void initBeanstalkBlockComponents() {
        final JLabel warpsToLabel = new JLabel("Warp Settings");
        warpsToLabel.setFont(this.frame.bold);
        warpsToLabel.setHorizontalTextPosition(0);
        warpsToLabel.setVerticalTextPosition(1);
        warpsToLabel.setIcon(this.item.button.iconImage);
        this.add(warpsToLabel, getConstraints(0, 1, 1, 1, 0, 0, 10, 0, 0, 0, 10, 0));
        final JLabel warpsToLevelLabel = new JLabel("Goes To Level:");
        warpsToLevelLabel.setFont(this.frame.bold);
        this.add(warpsToLevelLabel, getConstraints(0, 2, 1, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        (this.warpsToLevelComboBox = new JComboBox<String>()).setFont(this.frame.plain);
        this.warpsToLevelComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PropertiesPanel.this.populateIDComboBox();
            }
        });
        this.add(this.warpsToLevelComboBox, getConstraints(0, 3, 1, 1, 0, 0, 10, 0, 0, 0, 15, 0));
        final JLabel warpsToIDLabel = new JLabel("Goes To ID:");
        warpsToIDLabel.setFont(this.frame.bold);
        this.add(warpsToIDLabel, getConstraints(0, 4, 1, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        final JPanel goToPanel = new JPanel();
        goToPanel.setLayout(new BoxLayout(goToPanel, 0));
        (this.warpsToIDComboBox = new JComboBox<String>()).setFont(this.frame.plain);
        this.warpsToIDComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PropertiesPanel.this.goToButton.setVisible(PropertiesPanel.this.item.warp.destLevelNumber != 999 && PropertiesPanel.this.warpsToIDComboBox.getSelectedIndex() == PropertiesPanel.this.frame.levelPanel.levels[PropertiesPanel.this.item.warp.destLevelNumber].getWarpsIndex(PropertiesPanel.this.item.warp.destWarpID) + 1);
            }
        });
        goToPanel.add(this.warpsToIDComboBox);
        (this.goToButton = new JButton("Go")).setFont(this.frame.bold);
        this.goToButton.setToolTipText("Jump to the place this warp heads to...");
        this.goToButton.setMargin(new Insets(2, 2, 2, 2));
        this.goToButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PropertiesPanel.this.frame.levelPanel.findWarpDestination(PropertiesPanel.this.item.warp);
            }
        });
        goToPanel.add(this.goToButton);
        this.add(goToPanel, getConstraints(0, 5, 1, 1, 0, 0, 10, 0, 0, 0, 15, 0));
        final JButton updateInfo = new JButton("Apply");
        updateInfo.setFont(this.frame.bold);
        updateInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int sourceLevelNumber = PropertiesPanel.this.frame.levelPanel.level.levelNumber;
                final int sourceWarpID = -1;
                final boolean incoming = false;
                final boolean outgoing = true;
                int destLevelNumber = 0;
                int destWarpID = 0;
                if (PropertiesPanel.this.warpsToLevelComboBox.getSelectedIndex() >= 0) {
                    if (PropertiesPanel.this.warpsToLevelComboBox.getSelectedIndex() == PropertiesPanel.this.warpsToLevelComboBox.getItemCount() - 1) {
                        destLevelNumber = 999;
                    }
                    else {
                        destLevelNumber = PropertiesPanel.this.warpsToLevelComboBox.getSelectedIndex();
                    }
                    if (PropertiesPanel.this.warpsToIDComboBox.getSelectedIndex() == 0 || destLevelNumber == 999) {
                        destWarpID = 0;
                    }
                    else {
                        final Warp destWarp = PropertiesPanel.this.frame.levelPanel.levels[destLevelNumber].incomingWarps.get(PropertiesPanel.this.warpsToIDComboBox.getSelectedIndex() - 1);
                        destWarpID = destWarp.sourceWarpID;
                    }
                }
                if (destLevelNumber != PropertiesPanel.this.item.warp.destLevelNumber || destWarpID != PropertiesPanel.this.item.warp.destWarpID) {
                    PropertiesPanel.this.frame.undoableActionMade(Action.beanstalkPropChange(PropertiesPanel.this.item, PropertiesPanel.this.item.warp.destLevelNumber, PropertiesPanel.this.item.warp.destWarpID, destLevelNumber, destWarpID));
                }
                PropertiesPanel.this.item.warp.update(outgoing, incoming, sourceLevelNumber, sourceWarpID, destLevelNumber, destWarpID, PropertiesPanel.this.item);
                PropertiesPanel.this.populateLevelComboBox();
                PropertiesPanel.this.populateIDComboBox();
                PropertiesPanel.this.setWarpIndices();
                PropertiesPanel.this.frame.levelPanel.modified = true;
            }
        });
        this.add(updateInfo, getConstraints(0, 6, 1, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        this.addBlank(7);
        this.populateLevelComboBox();
        this.populateIDComboBox();
        this.setWarpIndices();
    }
    
    private void initWarpZoneComponents() {
        final JLabel warpsToLabel = new JLabel("Warp Settings");
        if (this.item.warp.incoming) {
            warpsToLabel.setText("<html><center>Warp Settings<br>(ID " + this.item.warp.sourceWarpID + ")</center></html>");
        }
        warpsToLabel.setFont(this.frame.bold);
        warpsToLabel.setHorizontalTextPosition(0);
        warpsToLabel.setVerticalTextPosition(1);
        ImageIcon icon = this.item.button.iconImage;
        if (this.item.warpPipeColor == 3) {
            icon = this.frame.game.textures.warpZonePipeBlue;
        }
        else if (this.item.warpPipeColor == 1) {
            icon = this.frame.game.textures.warpZonePipeGreen;
        }
        else if (this.item.warpPipeColor == 0) {
            icon = this.frame.game.textures.warpZonePipeOrange;
        }
        else if (this.item.warpPipeColor == 2) {
            icon = this.frame.game.textures.warpZonePipeWhite;
        }
        warpsToLabel.setIcon(icon);
        this.add(warpsToLabel, getConstraints(0, 1, 1, 1, 0, 0, 10, 0, 0, 0, 10, 0));
        final JLabel shownWarpLevelLabel = new JLabel("Shown \"Warp To\" Level:");
        shownWarpLevelLabel.setFont(this.frame.bold);
        this.add(shownWarpLevelLabel, getConstraints(0, 2, 1, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        final TextBox shownWarpLevelTextField = new TextBox(3, true, true, false);
        shownWarpLevelTextField.setFont(this.frame.boxFont);
        shownWarpLevelTextField.setText((this.item.displayWarpNumber == -1) ? "" : String.valueOf(this.item.displayWarpNumber));
        this.add(shownWarpLevelTextField, getConstraints(0, 3, 1, 1, 0, 0, 10, 0, 0, 0, 10, 0));
        final JLabel actualWarpLevelLabel = new JLabel("Actual \"Warp To\" Level:");
        actualWarpLevelLabel.setFont(this.frame.bold);
        this.add(actualWarpLevelLabel, getConstraints(0, 4, 1, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        final JPanel goToPanel = new JPanel();
        goToPanel.setLayout(new BoxLayout(goToPanel, 0));
        (this.warpsToLevelComboBox = new JComboBox<String>()).setToolTipText("The level that this level leads to after its ending...");
        this.warpsToLevelComboBox.setFont(this.frame.plain);
        this.warpsToLevelComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PropertiesPanel.this.goToButton.setVisible(PropertiesPanel.this.item.warp.destLevelNumber == PropertiesPanel.this.warpsToLevelComboBox.getSelectedIndex());
            }
        });
        goToPanel.add(this.warpsToLevelComboBox);
        (this.goToButton = new JButton("Go")).setFont(this.frame.bold);
        this.goToButton.setToolTipText("Jump to the place this warp heads to...");
        this.goToButton.setMargin(new Insets(2, 2, 2, 2));
        this.goToButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PropertiesPanel.this.frame.levelPanel.findWarpDestination(new Warp(true, false, -1, -1, PropertiesPanel.this.item.warp.destLevelNumber, 0, null));
            }
        });
        goToPanel.add(this.goToButton);
        this.add(goToPanel, getConstraints(0, 5, 1, 1, 0, 0, 10, 0, 0, 0, 0, 0));
        this.populateLevelComboBox();
        this.warpsToLevelComboBox.setSelectedIndex(this.item.warp.destLevelNumber);
        this.addSeparator(6, 6);
        final JLabel pipeColorLabel = new JLabel("Pipe Color:");
        pipeColorLabel.setFont(this.frame.bold);
        this.add(pipeColorLabel, getConstraints(0, 7, 6, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        final JComboBox<String> pipeColorComboBox = new JComboBox<String>();
        pipeColorComboBox.setFont(this.frame.plain);
        pipeColorComboBox.setToolTipText("The color of the pipes in the level...");
        final String[] pipeColorList = { " Orange ", " Green ", " White ", " Blue " };
        pipeColorComboBox.setModel(new DefaultComboBoxModel<String>(pipeColorList));
        pipeColorComboBox.setSelectedIndex(this.frame.levelPanel.level.pipeColor);
        this.add(pipeColorComboBox, getConstraints(0, 8, 6, 1, 0, 0, 10, 0, 0, 0, 15, 0));
        pipeColorComboBox.setSelectedIndex(this.item.warpPipeColor);
        final JButton updateInfo = new JButton("Apply");
        updateInfo.setFont(this.frame.bold);
        updateInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int sourceLevelNumber = PropertiesPanel.this.frame.levelPanel.level.levelNumber;
                final int sourceWarpID = -1;
                final boolean incoming = false;
                final boolean outgoing = true;
                int destLevelNumber = PropertiesPanel.this.warpsToLevelComboBox.getSelectedIndex();
                if (destLevelNumber < 0) {
                    destLevelNumber = 0;
                }
                final int destWarpID = 0;
                final String shownTextValue = shownWarpLevelTextField.getText();
                final int displayID = shownTextValue.isEmpty() ? -1 : Integer.valueOf(shownTextValue);
                final int pipeColor = pipeColorComboBox.getSelectedIndex();
                if (destLevelNumber != PropertiesPanel.this.item.warp.destLevelNumber || displayID != PropertiesPanel.this.item.displayWarpNumber || pipeColor != PropertiesPanel.this.item.warpPipeColor) {
                    PropertiesPanel.this.frame.undoableActionMade(Action.warpZonePropChange(PropertiesPanel.this.item, PropertiesPanel.this.item.warp.destLevelNumber, PropertiesPanel.this.item.displayWarpNumber, PropertiesPanel.this.item.warpPipeColor, destLevelNumber, displayID, pipeColor));
                }
                PropertiesPanel.this.item.displayWarpNumber = displayID;
                PropertiesPanel.this.item.warp.update(outgoing, incoming, sourceLevelNumber, sourceWarpID, destLevelNumber, destWarpID, PropertiesPanel.this.item);
                PropertiesPanel.this.populateLevelComboBox();
                PropertiesPanel.this.warpsToLevelComboBox.setSelectedIndex(PropertiesPanel.this.item.warp.destLevelNumber);
                PropertiesPanel.this.item.warpPipeColor = pipeColor;
                PropertiesPanel.this.frame.levelPanel.repaint();
                PropertiesPanel.this.frame.levelPanel.modified = true;
                PropertiesPanel.this.frame.changePropertiesPanel(new PropertiesPanel(PropertiesPanel.this.frame, PropertiesPanel.this.item.propertiesType, PropertiesPanel.this.item));
            }
        });
        this.add(updateInfo, getConstraints(0, 9, 1, 1, 0, 0, 10, 0, 0, 0, 5, 0));
        this.addBlank(10);
    }
    
    private void addBlank(final int max) {
        final JLabel blank = new JLabel();
        this.add(blank, getConstraints(0, max, 1, 1, 0, 1, 10, 1, 0, 0, 0, 0));
    }
    
    private void populateCliffEndLevelComboBox() {
        final String[] levelList = this.frame.gameListPanel.getLevelsList(true);
        final String[] warpsToLevelList = new String[levelList.length + 1];
        warpsToLevelList[0] = " Death ";
        for (int i = 0; i < levelList.length; ++i) {
            warpsToLevelList[i + 1] = levelList[i];
        }
        this.warpsOnCliffLevelComboBox.setModel(new DefaultComboBoxModel<String>(warpsToLevelList));
    }
    
    private void populateLevelComboBox() {
        final String[] levelList = this.frame.gameListPanel.getLevelsList(true);
        String[] warpsToLevelList = null;
        if (this.type == 5) {
            warpsToLevelList = new String[1 + levelList.length + 1];
            warpsToLevelList[0] = " Nowhere ";
            for (int i = 1; i < warpsToLevelList.length - 1; ++i) {
                warpsToLevelList[i] = levelList[i - 1];
            }
            warpsToLevelList[warpsToLevelList.length - 1] = " Ending ";
        }
        else if (this.type == 6 || this.type == 2) {
            warpsToLevelList = new String[levelList.length + 1];
            for (int i = 0; i < warpsToLevelList.length - 1; ++i) {
                warpsToLevelList[i] = levelList[i];
            }
            warpsToLevelList[warpsToLevelList.length - 1] = " Ending ";
        }
        else if (this.type == 7) {
            warpsToLevelList = levelList;
        }
        this.warpsToLevelComboBox.setModel(new DefaultComboBoxModel<String>(warpsToLevelList));
    }
    
    private void populateIDComboBox() {
        final int selectedIndex = this.warpsToLevelComboBox.getSelectedIndex();
        if (this.type != 2 && ((this.type == 5 && selectedIndex <= 0) || (this.type == 6 && selectedIndex < 0) || selectedIndex == this.warpsToLevelComboBox.getItemCount() - 1)) {
            this.warpsToIDComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "" }));
            this.warpsToIDComboBox.setEnabled(false);
            this.goToButton.setVisible(false);
        }
        else {
            int selectedLevel = selectedIndex;
            if (this.type == 5) {
                selectedLevel = selectedIndex - 1;
            }
            else if (this.type == 2) {
                selectedLevel = this.warpsOnCliffLevelComboBox.getSelectedIndex() - 1;
            }
            this.warpsToIDComboBox.setEnabled(true);
            final String[] idList = this.frame.levelPanel.levels[selectedLevel].getIncomingWarpIDList();
            final String[] warpsToIDList = new String[1 + this.frame.levelPanel.levels[selectedLevel].incomingWarps.size()];
            warpsToIDList[0] = " Mario's Start";
            for (int i = 1; i < warpsToIDList.length; ++i) {
                warpsToIDList[i] = idList[i - 1];
            }
            this.warpsToIDComboBox.setModel(new DefaultComboBoxModel<String>(warpsToIDList));
            if (this.item != null && this.type != 2 && this.item.warp.destLevelNumber == selectedLevel) {
                this.warpsToIDComboBox.setSelectedIndex(this.frame.levelPanel.levels[selectedLevel].getWarpsIndex(this.item.warp.destWarpID) + 1);
                this.goToButton.setVisible(true);
            }
            else if (this.item == null && this.frame.levelPanel.level.cliffDestLevel == selectedLevel) {
                this.warpsToIDComboBox.setSelectedIndex(this.frame.levelPanel.levels[selectedLevel].getWarpsIndex(this.frame.levelPanel.level.cliffDestID) + 1);
                this.goToButton.setVisible(true);
            }
            else {
                this.goToButton.setVisible(false);
            }
        }
    }
    
    private void setWarpIndices() {
        if (this.item.warp.outgoing && this.item.warp.destLevelNumber != 999) {
            if (this.type == 5) {
                this.warpsToLevelComboBox.setSelectedIndex(this.item.warp.destLevelNumber + 1);
            }
            else if (this.type == 6) {
                this.warpsToLevelComboBox.setSelectedIndex(this.item.warp.destLevelNumber);
            }
            this.warpsToIDComboBox.setEnabled(true);
            if (this.item.warp.destWarpID == 0) {
                this.warpsToIDComboBox.setSelectedIndex(0);
            }
            else {
                this.warpsToIDComboBox.setSelectedIndex(this.frame.levelPanel.levels[this.item.warp.destLevelNumber].getWarpsIndex(this.item.warp.destWarpID) + 1);
            }
        }
        else if (this.item.warp.outgoing && this.item.warp.destLevelNumber == 999) {
            this.warpsToLevelComboBox.setSelectedIndex(this.warpsToLevelComboBox.getItemCount() - 1);
            this.warpsToIDComboBox.setEnabled(false);
        }
        else {
            this.warpsToLevelComboBox.setSelectedIndex(0);
            if (this.type == 5) {
                this.warpsToIDComboBox.setEnabled(false);
            }
        }
    }
    
    public static GridBagConstraints getConstraints(final int gridx, final int gridy, final int gridwidth, final int gridheight, final int weightx, final int weighty, final int anchor, final int fill, final int topI, final int leftI, final int bottomI, final int rightI) {
        final GridBagConstraints constraints = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, fill, new Insets(topI, leftI, bottomI, rightI), 0, 0);
        return constraints;
    }
}
