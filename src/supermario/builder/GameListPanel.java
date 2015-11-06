// 
// Decompiled by Procyon v0.5.29
// 

package supermario.builder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GameListPanel extends JPanel
{
    private static final long serialVersionUID = -8018790933339952270L;
	private BuilderFrame frame;
    private JLabel authorLabel;
    private JLabel levelLabel;
    public JCheckBox hasPasswordCheckBox;
    private TextBox authorText;
    private JPasswordField passwordText;
    private JButton addLevel;
    private JButton deleteLevel;
    private JButton moveUp;
    private JButton moveDown;
    public JList<String> levelsList;
    private JButton testLevelButton;
    private JButton selectorButton;
    private JButton copyLevelButton;
    public static final String DEFAULT_AUTHOR_NAME = "ANONYMOUS";
    public static final int V_SPACER = 2;
    public static final int H_SPACER = 1;
    public static final int MAX_LEVELS = 1000;
    public static final int MAX_PASSWORD_CHARACTERS = 8;
    
    public GameListPanel(final BuilderFrame frame) {
        this.frame = frame;
        this.setBackground(Color.WHITE);
        this.setLayout(new GridBagLayout());
        this.initAuthor();
        this.initPassword();
        this.initLevelButtons();
        this.initLevelsList();
        this.initBottomLevelButtons();
    }
    
    public void reset() {
        this.authorText.setText("ANONYMOUS");
        this.repopulate(-1);
    }
    
    public void withoutPassword() {
        this.passwordText.setText("");
        this.passwordText.setEnabled(false);
        this.hasPasswordCheckBox.setSelected(false);
    }
    
    public void withPassword(final String password) {
        this.passwordText.setText(password);
        this.passwordText.setEnabled(true);
        this.hasPasswordCheckBox.setSelected(true);
    }
    
    public String getAuthorName() {
        if (this.authorText.getText().isEmpty()) {
            return "ANONYMOUS";
        }
        return this.authorText.getText();
    }
    
    public String getPassword() {
        String password;
        for (password = String.valueOf(this.passwordText.getPassword()); password.length() < 8; password += " ") {}
        return password;
    }
    
    public void setAuthorName(final String author) {
        this.authorText.setText(author);
    }
    
    public String[] getLevelsList(final boolean padWithSpace) {
        final String[] names = new String[this.frame.levelPanel.levels.length];
        for (int i = 0; i < this.frame.levelPanel.levels.length; ++i) {
            names[i] = this.frame.levelPanel.levels[i].getTitle();
            if (padWithSpace) {
                names[i] = " " + names[i];
            }
        }
        return names;
    }
    
    public void repopulate(final int selectedIndex) {
        String[] names = this.getLevelsList(false);
        if (names.length == 0) {
            names = new String[] { "" };
        }
        this.levelsList.setListData(names);
        if (selectedIndex >= 0) {
            this.selectLevel(selectedIndex);
        }
    }
    
    public void selectLevel(final int selectedLevel) {
        this.levelsList.setSelectedIndex(selectedLevel);
        this.levelsList.ensureIndexIsVisible(selectedLevel);
    }
    
    private void initAuthor() {
        (this.authorLabel = new JLabel("Game Author:")).setFont(this.frame.bold);
        final GridBagConstraints authorLabelConstraints = new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0, 10, 0, new Insets(0, 0, 2, 0), 0, 0);
        this.add(this.authorLabel, authorLabelConstraints);
        (this.authorText = new TextBox(20, false, true, false)).setFont(this.frame.boxFont);
        this.authorText.setText("ANONYMOUS");
        this.authorText.setToolTipText("The author of the game that is displayed between levels...");
        final GridBagConstraints authorTextConstraints = new GridBagConstraints(0, 1, 2, 1, 1.0, 0.0, 10, 2, new Insets(0, 10, 2, 10), 0, 0);
        this.add(this.authorText, authorTextConstraints);
    }
    
    private void initPassword() {
        (this.hasPasswordCheckBox = new JCheckBox("Password")).setFont(this.frame.boxFont);
        this.hasPasswordCheckBox.setFont(this.frame.bold);
        this.hasPasswordCheckBox.setOpaque(false);
        this.hasPasswordCheckBox.setSelected(false);
        this.hasPasswordCheckBox.setToolTipText("If the game is password protected to prevent tampering...");
        this.hasPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (GameListPanel.this.hasPasswordCheckBox.isSelected()) {
                    GameListPanel.this.passwordText.setEnabled(true);
                }
                else {
                    GameListPanel.this.passwordText.setText("");
                    GameListPanel.this.passwordText.setEnabled(false);
                }
            }
        });
        final GridBagConstraints hasPasswordCheckBoxConstraints = new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0, 13, 0, new Insets(0, 0, 2, 1), 0, 0);
        this.add(this.hasPasswordCheckBox, hasPasswordCheckBoxConstraints);
        (this.passwordText = TextBox.getPasswordTextBox(8, false, false)).setEnabled(false);
        this.passwordText.setToolTipText("A password to prevent others from modifying the game...");
        final GridBagConstraints passwordTextConstraints = new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, 17, 2, new Insets(0, 1, 2, 10), 0, 0);
        this.add(this.passwordText, passwordTextConstraints);
    }
    
    private void initLevelButtons() {
        (this.levelLabel = new JLabel("Levels:")).setFont(this.frame.bold);
        final GridBagConstraints levelLabelConstraints = new GridBagConstraints(0, 3, 2, 1, 1.0, 0.0, 10, 0, new Insets(0, 0, 2, 0), 0, 0);
        this.add(this.levelLabel, levelLabelConstraints);
        (this.addLevel = new JButton("  Add  ")).setToolTipText("Inserts a new level at the currently selected position...");
        this.addLevel.setFont(this.frame.bold);
        this.addLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final boolean success = GameListPanel.this.frame.levelPanel.newLevel(GameListPanel.this.levelsList.getSelectedIndex(), -1);
                if (!success) {
                    JOptionPane.showMessageDialog(GameListPanel.this.frame, "Max levels exceeded. What kind of game are you making?!", "Max Levels Exceeded", 2);
                    return;
                }
                GameListPanel.this.frame.levelPanel.modified = true;
                GameListPanel.this.frame.levelPanel.setLevelScheme();
                GameListPanel.this.frame.clearStacks();
                GameListPanel.this.frame.changePropertiesPanel(new PropertiesPanel(GameListPanel.this.frame, 2, null));
            }
        });
        final GridBagConstraints addLevelConstraints = new GridBagConstraints(0, 4, 1, 1, 1.0, 0.0, 13, 0, new Insets(0, 0, 2, 1), 0, 0);
        this.add(this.addLevel, addLevelConstraints);
        (this.deleteLevel = new JButton("Delete")).setToolTipText("Deletes the currently selected level...");
        this.deleteLevel.setFont(this.frame.bold);
        this.deleteLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (GameListPanel.this.frame.levelPanel.deleteLevel(GameListPanel.this.levelsList.getSelectedIndex())) {
                    GameListPanel.this.frame.levelPanel.modified = true;
                    GameListPanel.this.frame.clearStacks();
                }
                GameListPanel.this.frame.changePropertiesPanel(new PropertiesPanel(GameListPanel.this.frame, 1, null));
            }
        });
        final GridBagConstraints deleteLevelConstraints = new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0, 17, 0, new Insets(0, 1, 2, 0), 0, 0);
        this.add(this.deleteLevel, deleteLevelConstraints);
        (this.moveDown = new JButton("\u25bc")).setToolTipText("Moves the currently selected level down one position...");
        this.moveDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (GameListPanel.this.frame.levelPanel.shiftLevelDown(GameListPanel.this.levelsList.getSelectedIndex())) {
                    GameListPanel.this.frame.levelPanel.modified = true;
                }
            }
        });
        final GridBagConstraints moveDownConstraints = new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0, 17, 0, new Insets(0, 1, 2, 0), 0, 0);
        this.add(this.moveDown, moveDownConstraints);
        (this.moveUp = new JButton("\u25b2")).setToolTipText("Moves the currently selected level up one position...");
        this.moveUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (GameListPanel.this.frame.levelPanel.shiftLevelUp(GameListPanel.this.levelsList.getSelectedIndex())) {
                    GameListPanel.this.frame.levelPanel.modified = true;
                }
            }
        });
        final GridBagConstraints moveUpConstraints = new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, 13, 0, new Insets(0, 0, 2, 1), 0, 0);
        this.add(this.moveUp, moveUpConstraints);
    }
    
    private void initLevelsList() {
        final DefaultListModel<String> list = new DefaultListModel<String>();
        list.add(0, "");
        (this.levelsList = new JList<String>(list)).setFont(this.frame.boxFont);
        this.levelsList.setSelectionMode(0);
        this.levelsList.setLayoutOrientation(0);
        this.levelsList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(final MouseEvent e) {
            }
            
            @Override
            public void mousePressed(final MouseEvent e) {
                if (GameListPanel.this.frame.levelPanel.levels.length > 0) {
                    final int selectedIndex = ((JList<?>)e.getSource()).getSelectedIndex();
                    if (GameListPanel.this.frame.levelPanel.level == null || GameListPanel.this.frame.levelPanel.level.levelNumber != selectedIndex) {
                        GameListPanel.this.frame.clearStacks();
                        GameListPanel.this.frame.levelPanel.switchToLevel(selectedIndex);
                    }
                    GameListPanel.this.frame.changePropertiesPanel(new PropertiesPanel(GameListPanel.this.frame, 2, null));
                }
            }
            
            @Override
            public void mouseReleased(final MouseEvent e) {
            }
            
            @Override
            public void mouseEntered(final MouseEvent e) {
            }
            
            @Override
            public void mouseExited(final MouseEvent e) {
            }
        });
        this.levelsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(final ListSelectionEvent e) {
                if (GameListPanel.this.frame.levelPanel.levels.length > 0) {
                    final int selectedIndex = ((JList<?>)e.getSource()).getSelectedIndex();
                    if (selectedIndex >= 0) {
                        if (GameListPanel.this.frame.levelPanel.level == null || GameListPanel.this.frame.levelPanel.level.levelNumber != selectedIndex) {
                            GameListPanel.this.frame.levelPanel.switchToLevel(selectedIndex);
                            GameListPanel.this.frame.clearStacks();
                            GameListPanel.this.frame.propertiesPanel.updateButtonImages();
                        }
                        GameListPanel.this.frame.changePropertiesPanel(new PropertiesPanel(GameListPanel.this.frame, 2, null));
                    }
                }
            }
        });
        final GridBagConstraints levelsListConstraints = new GridBagConstraints(0, 6, 2, 1, 1.0, 1.0, 10, 1, new Insets(0, 2, 2, 2), 0, 0);
        final JScrollPane levelsListScroller = new JScrollPane(this.levelsList, 20, 30);
        levelsListScroller.setPreferredSize(new Dimension(1, 1));
        levelsListScroller.getVerticalScrollBar().setUnitIncrement(7);
        this.add(levelsListScroller, levelsListConstraints);
    }
    
    private void initBottomLevelButtons() {
        (this.testLevelButton = new JButton("Test Lvl")).setFont(this.frame.bold);
        this.testLevelButton.setToolTipText("Runs a game starting at the selected level...");
        this.testLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                GameListPanel.this.frame.testLevel();
            }
        });
        final GridBagConstraints testLevelButtonConstraints = new GridBagConstraints(0, 7, 2, 1, 1.0, 0.0, 10, 0, new Insets(0, 0, 2, 0), 0, 0);
        this.add(this.testLevelButton, testLevelButtonConstraints);
        (this.selectorButton = new JButton()).setToolTipText("Sets the selector tool which can both move items and change properties...");
        this.selectorButton.setIcon(this.frame.textures.iconPointer);
        this.selectorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                GameListPanel.this.frame.miscPanel.pointer.doClick();
            }
        });
        final GridBagConstraints selectorButtonConstraints = new GridBagConstraints(1, 7, 1, 0, 1.0, 0.0, 13, 0, new Insets(0, 0, 2, 0), 0, 0);
        this.add(this.selectorButton, selectorButtonConstraints);
        (this.copyLevelButton = new JButton("+")).setToolTipText("Copies the selected level and inserts it into the game...");
        this.copyLevelButton.setForeground(Color.BLUE);
        this.copyLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (GameListPanel.this.frame.levelPanel.level == null) {
                    return;
                }
                final boolean success = GameListPanel.this.frame.levelPanel.newLevel(GameListPanel.this.frame.levelPanel.level.levelNumber + 1, GameListPanel.this.frame.levelPanel.level.levelNumber);
                if (!success) {
                    JOptionPane.showMessageDialog(GameListPanel.this.frame, "Max levels exceeded. What kind of game are you making?!", "Max Levels Exceeded", 2);
                    return;
                }
                GameListPanel.this.frame.levelPanel.modified = true;
                GameListPanel.this.frame.levelPanel.setLevelScheme();
                GameListPanel.this.frame.clearStacks();
                GameListPanel.this.frame.changePropertiesPanel(new PropertiesPanel(GameListPanel.this.frame, 2, null));
            }
        });
        final GridBagConstraints copyLevelButtonConstraints = new GridBagConstraints(0, 7, 1, 0, 1.0, 0.0, 17, 0, new Insets(0, 0, 2, 0), 0, 0);
        this.add(this.copyLevelButton, copyLevelButtonConstraints);
    }
}
