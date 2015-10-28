// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.enemies;

import supermario.game.sprites.Mario;
import java.awt.Rectangle;
import supermario.game.Level;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.sprites.misc.BowserBattle;
import supermario.game.interfaces.Enemy;
import supermario.game.Sprite;

public class Bowser extends Sprite implements Enemy
{
    public static final int FOOT_CHANGE_DELAY = 500;
    public static final int POST_FLAME_TURN_DELAY = 300;
    public static final int FIREBALL_HITS_TO_KILL = 8;
    public static final double DEATH_VELOCITY = -120.0;
    public static final int MIN_FLAME_DELAY = 1000;
    public static final int MAX_FLAME_DELAY = 3500;
    public static final int MIN_PAUSE_DELAY = 2000;
    public static final int MAX_PAUSE_DELAY = 4000;
    public static final int JUMP_VELOCITY = -136;
    public static final int X_VELOCITY = 16;
    public static final int MIN_JUMP_DELAY = 1000;
    public static final int MAX_JUMP_DELAY = 2500;
    public static final int MAX_X_DRIFT_FROM_START = 72;
    public static final int PAUSE_TIME = 1000;
    public static final int MAX_HAMMERS_IN_A_ROW = 7;
    public static final int MIN_HAMMERS_IN_A_ROW = 3;
    public static final int MAX_TIME_BETWEEN_VOLLEY = 1500;
    public static final int MIN_TIME_BETWEEN_VOLLEY = 500;
    public static final int TIME_BETWEEN_THROWS = 150;
    private double footTicks;
    private double flameTicks;
    private double flameDelay;
    private double jumpTicks;
    private double jumpDelay;
    private double pauseTicks;
    private double pauseDelay;
    public double normalX;
    public double volleyTicks;
    public double betweenVolleyTime;
    private int fireballHits;
    private int hammersLeft;
    private boolean preparingToFire;
    private boolean killedByStar;
    private BowserBattle bowserBattle;
    private boolean throwsHammers;
    
    public Bowser(final Game game, final ImageIcon[] images, final BowserBattle bowserBattle, final boolean throwsHammers) {
        super(game, images);
        this.bowserBattle = bowserBattle;
        this.throwsHammers = throwsHammers;
        this.avoidedCollisionCols = 0;
        this.avoidedCollisionRowsOnBottom = 1;
        this.avoidedCollisionRowsOnTop = 0;
        this.setNextHammerCycle();
        this.setNextFlame();
        this.xVel = 16.0;
    }
    
    @Override
    public Sprite reset() {
        return null;
    }
    
    @Override
    public void update(final double delta) {
        this.preUpdate(delta);
        if (this.bowserBattle.caught && !this.bowserBattle.bridgeGone) {
            if (this.bumpKilled) {
                this.applyGravity(delta, Level.GRAVITY);
            }
        }
        else if (!this.bumpKilled && this.collidable) {
            this.applyGravity(delta, Level.GRAVITY / 10.0 * 1.5);
            this.pauseTicks += delta;
            if (this.pauseTicks < this.pauseDelay) {
                this.xPos += this.xVel * delta / 1000.0;
            }
            else if (this.pauseTicks > this.pauseDelay + 1000.0) {
                this.setNextPause();
            }
            if (this.xVel < 0.0 && this.xPos < this.normalX - 72.0) {
                this.xPos = this.normalX - 72.0;
                this.xVel = -this.xVel;
            }
            else if (this.xVel > 0.0 && this.xPos > this.normalX + 72.0) {
                this.xPos = this.normalX + 72.0;
                this.xVel = -this.xVel;
            }
            this.checkForCollisions();
            if (!this.game.mario.transitioning) {
                if (this.throwsHammers) {
                    this.volleyTicks += delta;
                    if (this.volleyTicks > 150.0 && this.hammersLeft > 0) {
                        this.volleyTicks = 0.0;
                        final Hammer hammer = new Hammer(this.game, new ImageIcon[] { this.game.textures.hammerGray }, this);
                        this.game.level.spritesToAdd.add(hammer);
                        hammer.xPos = this.x;
                        if (this.flip) {
                            final Hammer hammer2 = hammer;
                            hammer2.xPos += this.width - hammer.width;
                        }
                        hammer.yPos = this.y;
                        hammer.thrown();
                        --this.hammersLeft;
                    }
                    else if (this.hammersLeft == 0 && this.volleyTicks > this.betweenVolleyTime) {
                        this.setNextHammerCycle();
                    }
                }
                this.flameTicks += delta;
                if (this.flameTicks > this.flameDelay - 500.0 && !this.preparingToFire) {
                    this.preparingToFire = true;
                    if (this.imageIndex == 0) {
                        this.imageIndex = 2;
                    }
                    else if (this.imageIndex == 1) {
                        this.imageIndex = 3;
                    }
                }
                if (this.flameTicks >= this.flameDelay) {
                    this.fire();
                }
                if (this.grounded) {
                    this.jumpTicks += delta;
                    if (this.jumpTicks >= this.jumpDelay) {
                        this.jump();
                    }
                }
            }
        }
        else if (this.bumpKilled && !this.killedByStar) {
            this.applyGravity(delta, Level.GRAVITY * 2.0);
        }
        else {
            this.applyGravity(delta, Level.GRAVITY);
        }
        this.finalizePosition();
        this.grounded = this.isGrounded();
        if (!this.bowserBattle.caught) {
            if (this.flameTicks >= 300.0) {
                if (this.flip && this.game.mario.x + this.game.mario.width - this.avoidedCollisionCols * 2 < this.xPos && this.grounded) {
                    this.flip = false;
                }
                else if (!this.flip && this.game.mario.x + this.avoidedCollisionCols > this.xPos + this.width && this.grounded) {
                    this.flip = true;
                }
            }
        }
        this.footTicks += delta;
        if (this.footTicks > 500.0 && !this.bowserBattle.caught) {
            this.changeFooting();
            this.footTicks -= 500.0;
        }
        this.grounded = this.isGrounded();
        if (this.bumpKilled) {
            this.imageIndex = 4;
        }
    }
    
    private void fire() {
        if (this.imageIndex == 2) {
            this.imageIndex = 0;
        }
        else if (this.imageIndex == 3) {
            this.imageIndex = 1;
        }
        if (!this.bowserBattle.caught) {
            this.game.audio.play(16);
            this.game.level.spritesToAdd.add(new FlameBreath(this.game, new ImageIcon[] { this.game.textures.flame1, this.game.textures.flame2 }, this));
        }
        this.preparingToFire = false;
        this.setNextFlame();
    }
    
    private void setNextFlame() {
        this.flameTicks = 0.0;
        this.flameDelay = this.game.rand.nextInt(2501) + 1000;
    }
    
    private void setNextPause() {
        this.pauseTicks = 0.0;
        this.pauseDelay = this.game.rand.nextInt(1) + 2000;
        if (this.game.rand.nextInt(2) == 0) {
            this.xVel = -this.xVel;
        }
    }
    
    private void setNextHammerCycle() {
        this.volleyTicks = 0.0;
        this.betweenVolleyTime = this.game.rand.nextInt(1001) + 500;
        this.hammersLeft = this.game.rand.nextInt(5) + 3;
    }
    
    private void jump() {
        this.jumpTicks = 0.0;
        this.jumpDelay = this.game.rand.nextInt(1501) + 1000;
        this.yVel = -136.0;
    }
    
    public void changeFooting() {
        if (this.imageIndex == 0) {
            this.imageIndex = 1;
        }
        else if (this.imageIndex == 1) {
            this.imageIndex = 0;
        }
        else if (this.imageIndex == 2) {
            this.imageIndex = 3;
        }
        else if (this.imageIndex == 3) {
            this.imageIndex = 2;
        }
    }
    
    public boolean collision(final Rectangle otherRect) {
        Rectangle head;
        Rectangle body;
        Rectangle feet;
        if (!this.flip) {
            head = new Rectangle(this.x, this.y + 2, 14, 9);
            body = new Rectangle(this.x + 8, this.y + 8, 16, 16);
            feet = new Rectangle(this.x + 16, this.y + 12, 20, 20);
        }
        else {
            head = new Rectangle(this.x + this.width - 14, this.y + 2, 14, 9);
            body = new Rectangle(this.x + this.width - 8 - 16, this.y + 8, 16, 16);
            feet = new Rectangle(this.x + this.width - 16 - 20, this.y + 12, 20, 20);
        }
        return otherRect.intersects(head) || otherRect.intersects(body) || otherRect.intersects(feet);
    }
    
    public void hit() {
        ++this.fireballHits;
        if (this.fireballHits >= 8) {
            this.killedByStar = false;
            this.bumpKilled = true;
            this.collidable = false;
            this.game.audio.play(17);
            this.yVel = -120.0;
        }
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
    
    @Override
    public Rectangle getSpriteContactRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height - this.avoidedCollisionRowsOnBottom);
    }
    
    @Override
    public void bumpKilled(final Sprite killer) {
        if (killer instanceof Mario && this.game.mario.hasStar()) {
            this.killedByStar = true;
            this.bumpKilled = true;
            this.collidable = false;
            this.game.audio.play(17);
            this.yVel = -120.0;
        }
    }
    
    @Override
    public void smushed(final Sprite killer) {
    }
    
    @Override
    public void xCollided() {
    }
}
