// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.enemies;

import supermario.game.sprites.Mario;
import supermario.game.sprites.effects.Points;
import supermario.game.interfaces.Shelled;
import java.awt.Rectangle;
import supermario.game.Tile;
import supermario.game.Level;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.interfaces.Enemy;
import supermario.game.Sprite;

public class HammerBro extends Sprite implements Enemy
{
    public static final int STATE_BETWEEN_VOLLEYS = 0;
    public static final int STATE_MID_VOLLEY_REST = 1;
    public static final int STATE_HESITATING = 2;
    public static final int HESITATING_DELAY = 800;
    public static final int TIME_BETWEEN_THROWS = 400;
    public static final int MAX_HAMMERS_IN_VOLLEY = 6;
    public static final int MIN_HAMMERS_IN_VOLLEY = 1;
    public static final int MAX_TIME_BETWEEN_VOLLEY = 1500;
    public static final int MIN_TIME_BETWEEN_VOLLEY = 500;
    public static final int MAX_TIME_BEFORE_JUMP = 3300;
    public static final int MIN_TIME_BEFORE_JUMP = 2000;
    public static final double MAX_X_TRAVEL = 14.4;
    public static final int X_VELOCITY = 16;
    public static final int UP_JUMP_VELOCITY = -440;
    public static final int DOWN_JUMP_VELOCITY = -200;
    private double betweenVolleyTime;
    private double volleyTicks;
    private double jumpTicks;
    private double jumpTime;
    private double xStart;
    private double savedXVel;
    private int hammersLeft;
    private int downJumpYMin;
    private int generalImageIndex;
    private boolean smushed;
    private boolean upJumping;
    private boolean downJumping;
    private boolean lightBro;
    private Hammer currentHammer;
    
    public HammerBro(final Game game, final ImageIcon[] images, final boolean lightBro) {
        super(game, images);
        this.lightBro = lightBro;
        this.avoidedCollisionRowsOnBottom = 1;
        this.avoidedCollisionRowsOnTop = 6;
        this.avoidedCollisionCols = 0;
        this.setNextCycle();
        this.betweenVolleyTime /= 3.0;
        this.setNextJump();
    }
    
    @Override
    public Sprite reset() {
        if (this.visible && !this.bumpKilled && this.y < Game.renderHeight) {
            return new HammerBro(this.game, this.images, this.lightBro);
        }
        return null;
    }
    
    public void activate() {
        this.xVel = -16.0;
        --this.xPos;
        this.finalizePosition();
        this.xStart = this.x;
    }
    
    @Override
    public void update(final double delta) {
        this.preUpdate(delta);
        this.applyGravity(delta, Level.GRAVITY);
        this.xPos += this.xVel * delta / 1000.0;
        if (!this.bumpKilled && !this.smushed && this.collidable) {
            if (this.xPos <= this.xStart - 14.4) {
                this.xVel = 16.0;
            }
            else if (this.xPos >= this.xStart + 14.4) {
                this.xVel = -16.0;
            }
            this.flip = (this.game.mario.getXCenter() >= this.getXCenter());
            if (!this.upJumping) {
                this.jumpTicks += delta;
            }
            if (this.jumpTicks >= this.jumpTime) {
                this.jump();
            }
            if (!this.upJumping && !this.downJumping && this.yPos >= 0.0) {
                this.checkForCollisions(true);
                if (this.grounded) {
                    this.checkForCollisions(false);
                }
                this.grounded = this.isGrounded();
            }
            else {
                this.grounded = false;
            }
            this.checkForUnsettledTiles();
            if (this.upJumping && this.yVel > 0.0 && !this.isColliding()) {
                this.upJumping = false;
                this.setNextJump();
            }
            else if (this.downJumping && this.yVel > 0.0 && !this.isColliding() && this.yPos > this.downJumpYMin) {
                this.downJumping = false;
                this.setNextJump();
            }
            if (!this.upJumping && !this.downJumping && this.isGrounded() && this.xVel == 0.0) {
                this.xVel = this.savedXVel;
            }
        }
        if (!this.bumpKilled && !this.smushed) {
            if (this.ticks > 150.0) {
                this.ticks -= 150.0;
                if (this.generalImageIndex == 0) {
                    this.generalImageIndex = 1;
                }
                else if (this.generalImageIndex == 1) {
                    this.generalImageIndex = 0;
                }
                else if (this.generalImageIndex == 2) {
                    this.generalImageIndex = 3;
                }
                else if (this.generalImageIndex == 3) {
                    this.generalImageIndex = 2;
                }
            }
            this.volleyTicks += delta;
            if (this.spriteState == 0 && this.volleyTicks >= this.betweenVolleyTime) {
                this.volleyTicks = 0.0;
                this.spriteState = 1;
            }
            else if (this.spriteState == 2 && this.volleyTicks >= 800.0) {
                this.volleyTicks = 0.0;
                this.currentHammer.thrown();
                this.currentHammer = null;
                --this.hammersLeft;
                this.spriteState = 1;
                if (this.generalImageIndex == 2) {
                    this.generalImageIndex = 0;
                }
                else if (this.generalImageIndex == 3) {
                    this.generalImageIndex = 1;
                }
            }
            else if (this.spriteState == 1 && this.volleyTicks >= 400.0) {
                if (this.hammersLeft == 0) {
                    this.setNextCycle();
                }
                else {
                    this.volleyTicks = 0.0;
                    this.spriteState = 2;
                    this.currentHammer = new Hammer(this.game, new ImageIcon[] { this.game.textures.hammerBlack }, this);
                    this.game.level.spritesToAdd.add(this.currentHammer);
                    if (this.generalImageIndex == 0) {
                        this.generalImageIndex = 2;
                    }
                    else if (this.generalImageIndex == 1) {
                        this.generalImageIndex = 3;
                    }
                }
            }
        }
        else {
            this.generalImageIndex = 4;
            if (this.currentHammer != null) {
                this.currentHammer.thrown();
                this.currentHammer = null;
            }
        }
        this.finalizePosition();
        if (this.lightBro) {
            this.imageIndex = this.generalImageIndex;
        }
        else {
            this.imageIndex = this.generalImageIndex + 5;
        }
    }
    
    private void jump() {
        final boolean canUpJump = this.placeAbove();
        final boolean canDownJump = this.placeBelow();
        if (!canDownJump) {
            this.upJump();
        }
        else if (!canUpJump && canDownJump) {
            this.downJump();
        }
        else if (canUpJump && canDownJump) {
            final int choice = this.game.rand.nextInt(2);
            if (choice == 0) {
                this.downJump();
            }
            else {
                this.upJump();
            }
        }
    }
    
    private void upJump() {
        this.yVel = -440.0;
        this.upJumping = true;
        this.savedXVel = this.xVel;
        this.xVel = 0.0;
        this.grounded = false;
        this.jumpTicks = 0.0;
    }
    
    private void downJump() {
        this.yVel = -200.0;
        this.downJumping = true;
        this.savedXVel = this.xVel;
        this.xVel = 0.0;
        this.grounded = false;
        this.jumpTicks = 0.0;
        this.downJumpYMin = this.y / 8 * 8 + this.height + 8;
    }
    
    private boolean placeAbove() {
        if (this.y > Game.yTiles * 8) {
            return false;
        }
        int topY = this.y - 80;
        if (topY < 0) {
            topY = 0;
        }
        for (int botY = this.y - this.height - 16, i = topY; i <= botY; i += 8) {
            if (this.roomInColumns(i)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean placeBelow() {
        int topY = (this.y + this.height + 8) / 8 * 8;
        final int botY = Game.renderHeight - this.height - 8;
        if (topY > botY) {
            return false;
        }
        if (topY < 0) {
            topY = 0;
        }
        for (int i = topY; i <= botY; i += 8) {
            if (this.roomInColumns(i)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean roomInColumns(final int columnPixel) {
        final Tile tile1 = this.game.level.getTileAtPixel(this.x, columnPixel);
        final Tile tile2 = this.game.level.getTileAtPixel(this.x + 8, columnPixel);
        final Tile tile3 = this.game.level.getTileAtPixel(this.x + this.width - 1, columnPixel);
        return this.roomAtTile(tile1) && this.roomAtTile(tile2) && this.roomAtTile(tile3) && this.atLeastOneSolid(tile2);
    }
    
    private boolean roomAtTile(final Tile tile) {
        return !tile.solid && !this.game.level.tiles[tile.yTile + 1][tile.xTile].solid && !this.game.level.tiles[tile.yTile + 2][tile.xTile].solid;
    }
    
    private boolean atLeastOneSolid(final Tile tile) {
        return (tile.xTile - 1 >= 0 && this.game.level.tiles[tile.yTile + 3][tile.xTile - 1].solid) || this.game.level.tiles[tile.yTile + 3][tile.xTile].solid || (tile.xTile + 1 < this.game.level.xTiles && this.game.level.tiles[tile.yTile + 3][tile.xTile + 1].solid);
    }
    
    private void setNextCycle() {
        this.spriteState = 0;
        this.volleyTicks = 0.0;
        this.betweenVolleyTime = this.game.rand.nextInt(1001) + 500;
        this.hammersLeft = this.game.rand.nextInt(6) + 1;
    }
    
    private void setNextJump() {
        this.jumpTicks = 0.0;
        this.jumpTime = this.game.rand.nextInt(1301) + 2000;
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x + this.avoidedCollisionCols, this.y + this.avoidedCollisionRowsOnTop, this.width - this.avoidedCollisionCols * 2, this.height - this.avoidedCollisionRowsOnTop - this.avoidedCollisionRowsOnBottom);
    }
    
    @Override
    public Rectangle getSpriteContactRectangle() {
        if (!this.flip) {
            return new Rectangle(this.x + 4, this.y + this.avoidedCollisionRowsOnTop, this.width - 4, this.height - this.avoidedCollisionRowsOnTop - this.avoidedCollisionRowsOnBottom);
        }
        return new Rectangle(this.x, this.y + this.avoidedCollisionRowsOnTop, this.width - 4, this.height - this.avoidedCollisionRowsOnTop - this.avoidedCollisionRowsOnBottom);
    }
    
    @Override
    public void bumpKilled(final Sprite killer) {
        if (this.game.level.isFlyingKoopa(killer) || this.bumpKilled || killer instanceof Hammer) {
            return;
        }
        if (this.isOnScreen()) {
            this.game.audio.play(10);
        }
        this.collidable = false;
        this.bumpKilled = true;
        this.upJumping = false;
        this.downJumping = false;
        if (killer instanceof Shelled && ((Shelled)killer).isDangerous()) {
            ((Shelled)killer).shellKilled();
        }
        else {
            final Mario mario = this.game.mario;
            mario.points += 500;
            this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 3));
        }
        this.yVel = -240.0;
        if (killer.xVel > 0.0) {
            this.xVel = -64.0;
        }
        else if (this.game.mario.xVel < 0.0) {
            this.xVel = 64.0;
        }
        else if (this.xVel < 0.0) {
            this.xVel = 64.0;
        }
        else {
            this.xVel = -64.0;
        }
    }
    
    @Override
    public void smushed(final Sprite killer) {
        if (this.game.level.isFlyingKoopa(killer) || this.smushed) {
            return;
        }
        if (this.isOnScreen()) {
            this.game.audio.play(7);
            final Mario mario = this.game.mario;
            mario.points += 500;
            this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 3));
        }
        this.smushed = true;
        this.upJumping = false;
        this.downJumping = false;
        this.collidable = false;
        this.ticks = 0.0;
        this.xVel = 0.0;
        if (this.yVel < 0.0) {
            this.yVel = 0.0;
        }
    }
    
    @Override
    public void xCollided() {
        this.xVel = -this.xVel;
    }
}
