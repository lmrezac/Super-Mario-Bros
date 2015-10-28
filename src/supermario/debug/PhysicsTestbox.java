// 
// Decompiled by Procyon v0.5.29
// 

package supermario.debug;

import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import supermario.game.sprites.friends.Star;
import supermario.game.sprites.misc.Spring;
import supermario.game.sprites.misc.Platform;
import supermario.game.interfaces.Constants;
import supermario.game.sprites.enemies.Bullet;
import supermario.game.sprites.enemies.Koopa;
import supermario.game.sprites.Mario;
import supermario.game.Level;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JSeparator;
import supermario.game.Game;

public class PhysicsTestbox extends DebugTool
{
    private TextBox[] boxes;
    private double[] defaults;
    private double[] stored;
    private static final int WORLD_AIR_GRAVITY = 0;
    private static final int WORLD_AIR_TERM_VEL = 1;
    private static final int WORLD_WATER_GRAVITY = 2;
    private static final int WORLD_WATER_TERM_VEL = 3;
    private static final int MARIO_MAX_WALK_VEL = 4;
    private static final int MARIO_MAX_RUN_VEL = 5;
    private static final int MARIO_FORWARD_ACC = 6;
    private static final int MARIO_IDLE_DEC = 7;
    private static final int MARIO_CROUCHING_DEC = 8;
    private static final int MARIO_SKID_DEC = 9;
    private static final int MARIO_JUMP_HEIGHT = 10;
    private static final int KOOPA_BOUNCE_GRAVITY = 11;
    private static final int KOOPA_BOUNCE_VEL = 12;
    private static final int KOOPA_V_OSC_PER = 13;
    private static final int KOOPA_H_OSC_PER = 14;
    private static final int CANNON_X_SPEED = 15;
    private static final int GEN_ENEMY_X_SPEED = 16;
    private static final int GEN_FRIEND_X_SPEED = 17;
    private static final int GEN_SHELLED_LAUNCH_SPEED = 18;
    private static final int PLAT_REP_Y_VEL = 19;
    private static final int PLAT_V_OSC_PER = 20;
    private static final int PLAT_H_OSC_PER = 21;
    private static final int PLAT_CARRIER_VEL = 22;
    private static final int PLAT_FALLING_VEL = 23;
    private static final int SPRING_BOUNCE_VEL = 24;
    private static final int SPRING_LAUNCH_VEL = 25;
    private static final int STAR_BOUNCE_VEL = 26;
    private static final int STAR_GRAVITY = 27;
    private static final int AUTO_SCROLL_VEL = 28;
    
    public PhysicsTestbox(final Game game) {
        super(game, "Physics Testbox");
    }
    
    @Override
    public final void initComponents() {
        this.boxes = new TextBox[29];
        this.defaults = new double[29];
        this.stored = new double[29];
        int i = 0;
        this.addTitle("World Physics", i++, 0);
        this.addLabelTextCombo("Air Gravity", i++, 0, 0, true);
        this.addLabelTextCombo("Air Terminal Velocity", i++, 0, 1, true);
        this.addLabelTextCombo("Water Gravity", i++, 0, 2, true);
        this.addLabelTextCombo("Water Terminal Velocity", i++, 0, 3, true);
        this.addTitle("Mario Physics", i++, 0);
        this.addLabelTextCombo("Max Walk Velocity", i++, 0, 4, true);
        this.addLabelTextCombo("Max Run Velocity", i++, 0, 5, true);
        this.addLabelTextCombo("Forward Acceleration", i++, 0, 6, true);
        this.addLabelTextCombo("Idle Deceleration", i++, 0, 7, true);
        this.addLabelTextCombo("Crouching Deceleration", i++, 0, 8, true);
        this.addLabelTextCombo("Skidding Deceleration", i++, 0, 9, true);
        this.addLabelTextCombo("Jump Height", i++, 0, 10, true);
        this.add(new JSeparator(1), new GridBagConstraints(2, 0, 1, i, 1.0, 1.0, 10, 3, new Insets(0, 0, 0, 0), 0, 0));
        i = 0;
        this.addTitle("General Physics", i++, 1);
        this.addLabelTextCombo("Enemy Horiz. Velocity", i++, 1, 16, true);
        this.addLabelTextCombo("Friend Horiz. Velocity", i++, 1, 17, true);
        this.addLabelTextCombo("Shell Launch Velocity", i++, 1, 18, true);
        this.addTitle("Koopa Physics", i++, 1);
        this.addLabelTextCombo("Bouncing Gravity", i++, 1, 11, true);
        this.addLabelTextCombo("Bouncing Launch Velocity", i++, 1, 12, true);
        this.addLabelTextCombo("Horiz. Oscillating Period", i++, 1, 14, false);
        this.addLabelTextCombo("Vert. Oscillating Period", i++, 1, 13, false);
        this.addTitle("Cannon Physics", i++, 1);
        this.addLabelTextCombo("Bullet Velocity", i++, 1, 15, true);
        this.addTitle("Auto-Scrolling Physics", i++, 1);
        this.addLabelTextCombo("Scrolling Velocity", i++, 1, 28, true);
        ++i;
        this.add(new JSeparator(1), new GridBagConstraints(5, 0, 1, ++i, 1.0, 1.0, 10, 3, new Insets(0, 0, 0, 0), 0, 0));
        i = 0;
        this.addTitle("Platform Physics", i++, 2);
        this.addLabelTextCombo("Repeating Velocity", i++, 2, 19, true);
        this.addLabelTextCombo("Horiz. Oscillating Period", i++, 2, 21, false);
        this.addLabelTextCombo("Vert. Oscillating Period", i++, 2, 20, false);
        this.addLabelTextCombo("Carrier Velocity", i++, 2, 22, true);
        this.addLabelTextCombo("Falling Velocity", i++, 2, 23, true);
        this.addTitle("Spring Physics", i++, 2);
        this.addLabelTextCombo("Bounce Velocity", i++, 2, 24, true);
        this.addLabelTextCombo("Launch Velocity", i++, 2, 25, true);
        this.addTitle("Star Physics", i++, 2);
        this.addLabelTextCombo("Gravity", i++, 2, 27, true);
        this.addLabelTextCombo("Bounce Velocity", i++, 2, 26, true);
        this.addBottomButtons(13);
        this.storeValues(this.defaults);
        this.setDefaultValues();
        this.storeValues(this.stored);
        this.addChangeListeners();
        this.addActionListeners();
    }
    
    private void addTitle(final String title, final int row, final int col) {
        this.add(new JLabel("<html><b><u>" + title + "</u></b></html>", 0), this.constraints(col * 2 + col, row, 2, 1, 10));
    }
    
    private void addLabelTextCombo(final String label, final int row, final int col, final int type, final boolean isDouble) {
        this.add(new JLabel(label, 4), this.constraints(col * 2 + col, row, 1, 1, 13));
        this.add(this.boxes[type] = new TextBox(6, isDouble, type, this.stored), this.constraints(col * 2 + col + 1, row, 1, 1, 17));
    }
    
    @Override
    public void hideConsole() {
        this.setDefaultValues();
        this.frame.setVisible(false);
    }
    
    private void storeValues(final double[] array) {
        array[0] = Level.GRAVITY / 8.0;
        array[1] = Level.TERMINAL_VELOCITY / 8.0;
        array[2] = Level.WATER_GRAVITY / 8.0;
        array[3] = Level.TERMINAL_VELOCITY_WATER / 8.0;
        array[4] = Mario.MAX_WALKING_SPEED / 8.0;
        array[5] = Mario.MAX_RUNNING_SPEED / 8.0;
        array[6] = Mario.X_ACCELERATION_FRONT / 8.0;
        array[7] = Mario.SLOWING_DECELERATION / 8.0;
        array[8] = Mario.CROUCHING_DECELERATION / 8.0;
        array[9] = Mario.SKID_DECELERATION / 8.0;
        array[10] = Mario.MAX_JUMP_HEIGHT / 8.0;
        array[11] = Koopa.BOUNCING_GRAVITY / 8.0;
        array[12] = Math.abs(Koopa.BOUNCE_VELOCITY / 8.0);
        array[13] = Koopa.V_FLYING_PERIOD;
        array[14] = Koopa.H_FLYING_PERIOD;
        array[15] = Bullet.BULLET_SPEED / 8.0;
        array[28] = Level.AUTO_SCROLL_VEL / 8.0;
        array[16] = Constants.ENEMY_X_SPEED / 8.0;
        array[17] = Constants.FRIEND_X_SPEED / 8.0;
        array[18] = Constants.LAUNCHED_SHELL_X_SPEED / 8.0;
        array[19] = Platform.REPEATING_Y_VELOCITY / 8.0;
        array[20] = Platform.TOTAL_OSC_Y_PERIOD;
        array[21] = Platform.TOTAL_OSC_X_PERIOD;
        array[22] = Platform.CLOUD_CARRIER_X_VELOCITY / 8.0;
        array[23] = Platform.FALLING_PLATFORM_Y_VELOCITY / 8.0;
        array[25] = Math.abs(Spring.LAUNCH_VELOCITY / 8.0);
        array[24] = Math.abs(Spring.BOUNCE_VELOCITY / 8.0);
        array[27] = Star.BOUNCING_GRAVITY / 8.0;
        array[26] = Math.abs(Star.BOUNCE_VELOCITY / 8.0);
    }
    
    private void addActionListeners() {
        for (int i = 0; i < this.boxes.length; ++i) {
            this.boxes[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    PhysicsTestbox.this.submitValues();
                    PhysicsTestbox.this.game.requestFocus();
                }
            });
        }
    }
    
    private void setDefaultValues() {
        this.boxes[0].setText(String.valueOf(this.defaults[0]));
        this.boxes[1].setText(String.valueOf(this.defaults[1]));
        this.boxes[2].setText(String.valueOf(this.defaults[2]));
        this.boxes[3].setText(String.valueOf(this.defaults[3]));
        this.boxes[4].setText(String.valueOf(this.defaults[4]));
        this.boxes[5].setText(String.valueOf(this.defaults[5]));
        this.boxes[6].setText(String.valueOf(this.defaults[6]));
        this.boxes[7].setText(String.valueOf(this.defaults[7]));
        this.boxes[8].setText(String.valueOf(this.defaults[8]));
        this.boxes[9].setText(String.valueOf(this.defaults[9]));
        this.boxes[10].setText(String.valueOf(this.defaults[10]));
        this.boxes[11].setText(String.valueOf(this.defaults[11]));
        this.boxes[12].setText(String.valueOf(this.defaults[12]));
        this.boxes[14].setText(String.valueOf((int)this.defaults[14]));
        this.boxes[13].setText(String.valueOf((int)this.defaults[13]));
        this.boxes[15].setText(String.valueOf(this.defaults[15]));
        this.boxes[28].setText(String.valueOf(this.defaults[28]));
        this.boxes[16].setText(String.valueOf(this.defaults[16]));
        this.boxes[17].setText(String.valueOf(this.defaults[17]));
        this.boxes[18].setText(String.valueOf(this.defaults[18]));
        this.boxes[19].setText(String.valueOf(this.defaults[19]));
        this.boxes[20].setText(String.valueOf((int)this.defaults[20]));
        this.boxes[21].setText(String.valueOf((int)this.defaults[21]));
        this.boxes[22].setText(String.valueOf(this.defaults[22]));
        this.boxes[23].setText(String.valueOf(this.defaults[23]));
        this.boxes[25].setText(String.valueOf(this.defaults[25]));
        this.boxes[24].setText(String.valueOf(this.defaults[24]));
        this.boxes[26].setText(String.valueOf(this.defaults[26]));
        this.boxes[27].setText(String.valueOf(this.defaults[27]));
    }
    
    private void submitValues() {
        for (int i = 0; i < this.boxes.length; ++i) {
            final String t = this.boxes[i].getText();
            if (t.isEmpty() || t.equals(".") || t.equals("0.") || t.equals(".0")) {
                this.boxes[i].setText("0.0");
            }
            this.boxes[i].setFont(this.boxes[i].getFont().deriveFont(0));
        }
        Level.GRAVITY = this.boxes[0].getDouble() * 8.0;
        Level.TERMINAL_VELOCITY = this.boxes[1].getDouble() * 8.0;
        Level.WATER_GRAVITY = this.boxes[2].getDouble() * 8.0;
        Level.TERMINAL_VELOCITY_WATER = this.boxes[3].getDouble() * 8.0;
        Mario.MAX_WALKING_SPEED = this.boxes[4].getDouble() * 8.0;
        Mario.MAX_RUNNING_SPEED = this.boxes[5].getDouble() * 8.0;
        Mario.X_ACCELERATION_FRONT = this.boxes[6].getDouble() * 8.0;
        Mario.SLOWING_DECELERATION = this.boxes[7].getDouble() * 8.0;
        Mario.CROUCHING_DECELERATION = this.boxes[8].getDouble() * 8.0;
        Mario.SKID_DECELERATION = this.boxes[9].getDouble() * 8.0;
        Mario.MAX_JUMP_HEIGHT = this.boxes[10].getDouble() * 8.0;
        Koopa.BOUNCING_GRAVITY = this.boxes[11].getDouble() * 8.0;
        Koopa.BOUNCE_VELOCITY = -this.boxes[12].getDouble() * 8.0;
        Koopa.H_FLYING_PERIOD = this.boxes[14].getInt();
        Koopa.V_FLYING_PERIOD = this.boxes[13].getInt();
        Bullet.BULLET_SPEED = this.boxes[15].getDouble() * 8.0;
        Level.AUTO_SCROLL_VEL = this.boxes[28].getDouble() * 8.0;
        Constants.ENEMY_X_SPEED = this.boxes[16].getDouble() * 8.0;
        Constants.FRIEND_X_SPEED = this.boxes[17].getDouble() * 8.0;
        Constants.LAUNCHED_SHELL_X_SPEED = this.boxes[18].getDouble() * 8.0;
        Platform.REPEATING_Y_VELOCITY = this.boxes[19].getDouble() * 8.0;
        Platform.TOTAL_OSC_Y_PERIOD = this.boxes[20].getInt();
        Platform.TOTAL_OSC_X_PERIOD = this.boxes[21].getInt();
        Platform.CLOUD_CARRIER_X_VELOCITY = this.boxes[22].getDouble() * 8.0;
        Platform.FALLING_PLATFORM_Y_VELOCITY = this.boxes[23].getDouble() * 8.0;
        Spring.BOUNCE_VELOCITY = -this.boxes[24].getDouble() * 8.0;
        Spring.LAUNCH_VELOCITY = -this.boxes[25].getDouble() * 8.0;
        Star.BOUNCE_VELOCITY = -this.boxes[26].getDouble() * 8.0;
        Star.BOUNCING_GRAVITY = this.boxes[27].getDouble() * 8.0;
        this.storeValues(this.stored);
    }
    
    private void addChangeListeners() {
        for (int i = 0; i < this.boxes.length; ++i) {
            this.boxes[i].getDocument().addDocumentListener(this.boxes[i]);
        }
    }
    
    private void addBottomButtons(int row) {
        final GridBagConstraints c = this.separatorConstraints(row++, 0);
        c.gridwidth = 8;
        this.add(new JSeparator(), c);
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PhysicsTestbox.this.setDefaultValues();
                PhysicsTestbox.this.submitValues();
                PhysicsTestbox.this.game.requestFocus();
            }
        });
        this.add(reset, this.constraints(3, row, 1, 1, 13));
        final JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PhysicsTestbox.this.submitValues();
                PhysicsTestbox.this.game.requestFocus();
            }
        });
        this.add(submit, this.constraints(4, row, 1, 1, 17));
        final JButton help = new JButton("Help");
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final String message = "Physics Testbox Units:\n\nDisplacements (ie Mario Jump Height): Tiles\nVelocities (speed): Tiles per Second\nAccelerations (gravity): Tiles per Second Squared\nPeriods (time): Milliseconds\n\nTiles are 8 x 8 pixel squares which compose a level.";
                JOptionPane.showMessageDialog(PhysicsTestbox.this, message, "Physics Testbox Help", 1);
            }
        });
        this.add(help, this.constraints(7, row, 1, 1, 17));
    }
    
    private GridBagConstraints separatorConstraints(final int y, final int x) {
        final GridBagConstraints c = this.constraints(x * 2 + x, y, 2, 1, 10);
        c.insets = new Insets(0, 0, 0, 0);
        c.fill = 2;
        return c;
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
}
