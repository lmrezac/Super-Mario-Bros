// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.blocks;

import supermario.game.sprites.effects.BrokenBrick;
import supermario.game.sprites.effects.BumpCoin;
import supermario.game.sprites.Mario;
import java.awt.Rectangle;
import supermario.game.sprites.enemies.PoisonMushroom;
import supermario.game.sprites.misc.Beanstalk;
import supermario.game.sprites.friends.Star;
import supermario.game.sprites.friends.Life;
import supermario.game.sprites.friends.Flower;
import supermario.game.sprites.friends.Mushroom;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.Warp;
import supermario.game.interfaces.Warpable;
import supermario.game.interfaces.Block;
import supermario.game.Sprite;

public class Brick extends Sprite implements Block, Warpable
{
    public Warp warp;
    public boolean broken;
    public boolean metal;
    public int brickType;
    private int normalY;
    private int contents;
    private int coinCount;
    private int poison;
    public static final int DEFAULT_COIN_COUNT = 5;
    public static final int TYPE_LIGHT_BRICK = 0;
    public static final int TYPE_DARK_BRICK = 1;
    public static final int TYPE_STONE_BRICK = 2;
    public static final int CONTENTS_NOTHING = 0;
    public static final int CONTENTS_SINGLE_COIN = 1;
    public static final int CONTENTS_MULTIPLE_COINS = 2;
    public static final int CONTENTS_GROW = 3;
    public static final int CONTENTS_STAR = 4;
    public static final int CONTENTS_LIFE = 5;
    public static final int CONTENTS_BEANSTALK = 6;
    public static final int CONTENTS_POISON = 7;
    public static final int COIN_COUNT_RANDOM_MIN = 2;
    public static final int COIN_COUNT_RANDOM_MAX = 9;
    
    public Brick(final Game game, final ImageIcon[] images, final int brickType, final int contents) {
        super(game, images);
        this.brickType = brickType;
        this.contents = contents;
        this.settled = true;
        this.broken = false;
        this.metal = false;
        if (brickType == 0) {
            this.imageIndex = 0;
        }
        else if (brickType == 1) {
            this.imageIndex = 1;
        }
        else if (brickType == 2) {
            this.imageIndex = 2;
        }
    }
    
    @Override
    public Sprite reset() {
        return this;
    }
    
    public Brick(final Game game, final ImageIcon[] images, final int brickType, final int contents, int coinCount) {
        this(game, images, brickType, contents);
        if (coinCount < 0) {
            throw new RuntimeException("Blocks must contain a positive number of coins");
        }
        if (coinCount == 0) {
            coinCount = game.rand.nextInt(8) + 2;
        }
        this.coinCount = coinCount;
    }
    
    @Override
    public void update(final double delta) {
        if (this.bumped && !this.settled && !this.broken) {
            this.applyGravity(delta, 80.0);
            if (this.yPos > this.normalY) {
                this.yPos = this.normalY;
                this.settled = true;
                this.bumped = false;
                if (this.contents == 3 && !this.game.mario.isLarge()) {
                    this.game.level.sprites.add(new Mushroom(this.game, new ImageIcon[] { this.game.textures.growMushroom }, this, false));
                }
                else if (this.contents == 3 && this.game.mario.isLarge()) {
                    this.game.level.sprites.add(new Flower(this.game, this.game.textures.getFlowerTextures(), this));
                }
                else if (this.contents == 5) {
                    this.game.level.sprites.add(new Life(this.game, new ImageIcon[] { this.game.textures.lightExtraLife, this.game.textures.darkExtraLife }, this));
                }
                else if (this.contents == 4) {
                    this.game.level.sprites.add(new Star(this.game, this.game.textures.getStarTextures(), this));
                }
                else if (this.contents == 6) {
                    this.game.level.sprites.add(new Beanstalk(this.game, this.game.textures.getBeanstalkTextures(), this));
                }
                else if (this.contents == 7) {
                    this.game.level.sprites.add(new PoisonMushroom(this.game, new ImageIcon[] { this.game.textures.poisonMushroom }, this));
                }
            }
        }
        this.finalizePosition();
        if (this.brickType == 0) {
            this.imageIndex = 0;
        }
        else if (this.brickType == 1) {
            this.imageIndex = 1;
        }
        else if (this.brickType == 2) {
            this.imageIndex = 2;
        }
        if (this.metal) {
            this.imageIndex += 3;
        }
    }
    
    @Override
    public Rectangle getRectangle() {
        return null;
    }
    
    @Override
    public void bumped(final Sprite sprite) {
        if (!this.isOnScreen()) {
            return;
        }
        final boolean isMario = sprite instanceof Mario;
        if (isMario && !this.game.mario.isLarge() && !this.bumped && this.contents == 0) {
            if (sprite.isOnScreen() || this.isOnScreen()) {
                this.game.audio.play(8);
            }
            if (this.settled) {
                this.bumped = true;
                this.justHit = true;
                this.settled = false;
                this.yVel = -36.0;
            }
        }
        else if (!this.broken && (!isMario || this.game.mario.isLarge()) && this.contents == 0) {
            this.visible = false;
            this.bumped = true;
            this.justHit = true;
            this.settled = false;
            this.broken();
        }
        else if (!this.metal && this.contents == 1) {
            if (sprite.isOnScreen() || this.isOnScreen()) {
                this.game.audio.play(8);
            }
            if (this.settled) {
                this.bumped = true;
                this.justHit = true;
                this.settled = false;
                this.metal = true;
                this.yVel = -36.0;
                if (this.isOnScreen()) {
                    this.game.level.effects.add(new BumpCoin(this.game, this, this.isOnScreen()));
                    final Mario mario = this.game.mario;
                    ++mario.coins;
                    final Mario mario2 = this.game.mario;
                    mario2.points += 200;
                }
            }
        }
        else if (!this.metal && this.contents == 2) {
            if (sprite.isOnScreen() || this.isOnScreen()) {
                this.game.audio.play(8);
            }
            if (!this.settled) {
                this.yPos = this.normalY;
                this.settled = true;
                this.finalizePosition();
            }
            this.bumped = true;
            this.justHit = true;
            this.settled = false;
            --this.coinCount;
            if (this.coinCount == 0) {
                this.metal = true;
            }
            this.yVel = -36.0;
            if (this.isOnScreen()) {
                final Mario mario3 = this.game.mario;
                mario3.points += 200;
                this.game.level.effects.add(new BumpCoin(this.game, this, this.isOnScreen()));
                final Mario mario4 = this.game.mario;
                ++mario4.coins;
            }
        }
        else if (!this.metal && (this.contents == 3 || this.contents == 7 || this.contents == 5 || this.contents == 4 || this.contents == 6)) {
            if (sprite.isOnScreen() || this.isOnScreen()) {
                this.game.audio.play(8);
            }
            if (this.settled) {
                this.metal = true;
                this.bumped = true;
                this.justHit = true;
                this.settled = false;
                this.yVel = -36.0;
            }
        }
        else if (this.metal && (sprite.isOnScreen() || this.isOnScreen())) {
            this.game.audio.play(8);
        }
    }
    
    private void broken() {
        if (!this.isOnScreen()) {
            return;
        }
        final Mario mario = this.game.mario;
        mario.points += 50;
        this.game.audio.play(6);
        this.game.level.effects.add(new BrokenBrick(this.game, this));
        this.broken = true;
    }
    
    @Override
    public void setNormalYPosition(final int y) {
        this.normalY = y;
    }
    
    @Override
    public void setWarp(final Warp warp) {
        this.warp = warp;
    }
}
