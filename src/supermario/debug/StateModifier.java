// 
// Decompiled by Procyon v0.5.29
// 

package supermario.debug;

import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import supermario.game.interfaces.Enemy;
import java.awt.Component;
import java.awt.Point;
import supermario.game.sprites.Mario;
import supermario.game.sprites.effects.Points;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JButton;
import supermario.game.Game;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;

public class StateModifier extends DebugTool implements WindowListener, KeyListener
{
    public StateModifier(final Game game) {
        super(game, "State Modifier");
    }
    
    @Override
    public void hideConsole() {
        this.frame.setVisible(false);
    }
    
    @Override
    public void initComponents() {
        final JButton giveMushroom = new JButton(this.game.textures.growMushroom);
        giveMushroom.setToolTipText("Give Mario a mushroom.");
        giveMushroom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (StateModifier.this.actionAllowed(false)) {
                    final Mario mario = StateModifier.this.game.mario;
                    mario.points += 1000;
                    final Point p = StateModifier.this.getPointsPosition();
                    StateModifier.this.game.level.effectsToAdd.add(new Points(StateModifier.this.game, p.x, p.y, 5));
                    StateModifier.this.game.audio.play(1);
                    if (!StateModifier.this.game.mario.isLarge()) {
                        StateModifier.this.game.mario.grow(false);
                    }
                }
                StateModifier.this.game.requestFocus();
            }
        });
        this.add(giveMushroom, this.constraints(0, 0, 1, 1, 10));
        final JButton giveFlower = new JButton(this.game.textures.lightFlower1);
        giveFlower.setToolTipText("Give Mario a flower.");
        giveFlower.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (StateModifier.this.actionAllowed(false)) {
                    final Mario mario = StateModifier.this.game.mario;
                    mario.points += 1000;
                    final Point p = StateModifier.this.getPointsPosition();
                    StateModifier.this.game.level.effectsToAdd.add(new Points(StateModifier.this.game, p.x, p.y, 5));
                    StateModifier.this.game.audio.play(1);
                    StateModifier.this.game.mario.grow(true);
                }
                StateModifier.this.game.requestFocus();
            }
        });
        this.add(giveFlower, this.constraints(1, 0, 1, 1, 10));
        final JButton giveStar = new JButton(this.game.textures.star1);
        giveStar.setToolTipText("Give Mario a star.");
        giveStar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (StateModifier.this.actionAllowed(false)) {
                    final Mario mario = StateModifier.this.game.mario;
                    mario.points += 1000;
                    final Point p = StateModifier.this.getPointsPosition();
                    StateModifier.this.game.level.effectsToAdd.add(new Points(StateModifier.this.game, p.x, p.y, 5));
                    StateModifier.this.game.audio.play(1);
                    StateModifier.this.game.mario.caughtStar();
                }
                StateModifier.this.game.requestFocus();
            }
        });
        this.add(giveStar, this.constraints(2, 0, 1, 1, 10));
        final JButton giveLife = new JButton(this.game.textures.lightExtraLife);
        giveLife.setToolTipText("Give Mario an extra life.");
        giveLife.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (StateModifier.this.actionAllowed(true)) {
                    StateModifier.this.game.audio.play(0);
                    final Point p = StateModifier.this.getPointsPosition();
                    StateModifier.this.game.level.effectsToAdd.add(new Points(StateModifier.this.game, p.x, p.y, 10));
                    StateModifier.this.game.mario.extraLife();
                }
                StateModifier.this.game.requestFocus();
            }
        });
        this.add(giveLife, this.constraints(3, 0, 1, 1, 10));
        final JButton attack = new JButton(this.game.textures.lightGoomba1);
        attack.setToolTipText("Simulate Mario getting hit.");
        attack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (StateModifier.this.actionAllowed(false)) {
                    StateModifier.this.game.mario.attacked(true, null);
                }
                StateModifier.this.game.requestFocus();
            }
        });
        this.add(attack, this.constraints(0, 1, 1, 1, 10));
        final JButton kill = new JButton(this.game.textures.marioDeadButton);
        kill.setToolTipText("Simulate Mario dying.");
        kill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (StateModifier.this.actionAllowed(false) && !StateModifier.this.game.mario.isDead()) {
                    StateModifier.this.game.mario.died(false, false);
                }
                StateModifier.this.game.requestFocus();
            }
        });
        this.add(kill, this.constraints(1, 1, 1, 1, 10));
        final JButton stopStar = new JButton(this.game.textures.starCancel);
        stopStar.setToolTipText("Stop Mario's star power if he has it.");
        stopStar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (StateModifier.this.actionAllowed(false)) {
                    StateModifier.this.game.mario.starFinished(true);
                }
                StateModifier.this.game.requestFocus();
            }
        });
        this.add(stopStar, this.constraints(2, 1, 1, 1, 10));
        final JButton takeLife = new JButton("- 1", this.game.textures.lightExtraLife);
        takeLife.setToolTipText("Take a life from Mario if he has at least one to spare.");
        takeLife.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (StateModifier.this.actionAllowed(true) && StateModifier.this.game.mario.lives > 1) {
                    final Mario mario = StateModifier.this.game.mario;
                    --mario.lives;
                }
                StateModifier.this.game.requestFocus();
            }
        });
        this.add(takeLife, this.constraints(3, 1, 1, 1, 10));
        final JButton giveCoin = new JButton(this.game.textures.lightCoin);
        giveCoin.setToolTipText("Give Mario a coin.");
        giveCoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (StateModifier.this.actionAllowed(false)) {
                    StateModifier.this.game.audio.play(12);
                    final Mario mario = StateModifier.this.game.mario;
                    ++mario.coins;
                }
                StateModifier.this.game.requestFocus();
            }
        });
        this.add(giveCoin, this.constraints(0, 2, 1, 1, 10));
        final JButton give10Coins = new JButton("x 10", this.game.textures.lightCoin);
        give10Coins.setToolTipText("Give Mario ten coins.");
        give10Coins.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (StateModifier.this.actionAllowed(false)) {
                    StateModifier.this.game.audio.play(12);
                    final Mario mario = StateModifier.this.game.mario;
                    mario.coins += 10;
                }
                StateModifier.this.game.requestFocus();
            }
        });
        this.add(give10Coins, this.constraints(1, 2, 1, 1, 10));
        final JButton resetTime = new JButton("Reset Time");
        resetTime.setToolTipText("Set 0 seconds for untimed levels and starting time for timed levels.");
        resetTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (StateModifier.this.actionAllowed(false) && StateModifier.this.game.level.timedLevel && (!StateModifier.this.game.mario.playedTimeWarning || StateModifier.this.game.mario.loopedFastMusic)) {
                    StateModifier.this.game.mario.totalTime = 0.0;
                    if (StateModifier.this.game.mario.loopedFastMusic) {
                        StateModifier.this.game.mario.playedTimeWarning = false;
                        StateModifier.this.game.mario.loopedFastMusic = false;
                        StateModifier.this.game.audio.stopMusic(true);
                    }
                    else {
                        StateModifier.this.game.mario.playedTimeWarning = false;
                        StateModifier.this.game.mario.loopedFastMusic = false;
                    }
                }
                StateModifier.this.game.requestFocus();
            }
        });
        this.add(resetTime, this.constraints(2, 2, 1, 1, 10));
        final JButton timeCrisis = new JButton("30 Seconds");
        timeCrisis.setToolTipText("Set Mario just prior to running low on time if in a timed level.");
        timeCrisis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (StateModifier.this.actionAllowed(false) && StateModifier.this.game.level.timedLevel) {
                    StateModifier.this.game.mario.playedTimeWarning = false;
                    StateModifier.this.game.mario.loopedFastMusic = false;
                    StateModifier.this.game.mario.totalTime = StateModifier.this.game.mario.levelTime - 30.0;
                    StateModifier.this.game.audio.stopMusic(false);
                }
                StateModifier.this.game.requestFocus();
            }
        });
        this.add(timeCrisis, this.constraints(3, 2, 1, 1, 10));
    }
    
    private Point getPointsPosition() {
        final int x = this.game.mario.getXCenter();
        final int y = this.game.mario.getRectangle().y - 8;
        return new Point(x, y);
    }
    
    private boolean actionAllowed(final boolean pauseAllowed) {
        return this.game.testMode && this.game.getGameState() == 1 && this.game.level != null && !this.game.mario.transitioning && (!this.game.level.paused || pauseAllowed);
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
