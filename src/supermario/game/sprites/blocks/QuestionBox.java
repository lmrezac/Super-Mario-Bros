// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.blocks;

import supermario.game.sprites.Mario;
import supermario.game.sprites.effects.BumpCoin;

import java.awt.Rectangle;

import supermario.game.sprites.enemies.PoisonMushroom;
import supermario.game.sprites.misc.Beanstalk;
import supermario.game.sprites.friends.Star;
import supermario.game.sprites.friends.Life;
import supermario.game.sprites.friends.Flower;
import supermario.game.sprites.friends.Mushroom;
import supermario.Utilities;

import javax.swing.ImageIcon;

import supermario.game.AnimatedIcon;
import supermario.game.Game;
import supermario.game.Warp;
import supermario.game.interfaces.Warpable;
import supermario.game.interfaces.Block;
import supermario.game.Sprite;

public class QuestionBox extends Sprite implements Block, Warpable
{
    public Warp warp;
    private boolean hit;
    private boolean metal;
    public boolean hidden;
    public boolean exposed;
    private int normalY;
    private int contents;
    private int shadowColor;
    public static int CONTENTS_COIN;
    public static int CONTENTS_GROWTH;
    public static int CONTENTS_EXTRA_LIFE;
    public static int CONTENTS_STAR;
    public static int CONTENTS_BEANSTALK;
    public static int CONTENTS_POISON;
    
    public QuestionBox(final Game game, final ImageIcon[] images, final int shadowColor, final int contents, final boolean hidden) {
        super(game, images);
        this.shadowColor = shadowColor;
        this.contents = contents;
        this.hidden = hidden;
        this.exposed = false;
        if (hidden) {
            this.imageIndex = images.length - 1;
        }
        else if (shadowColor == 0) {
            this.imageIndex = 0;
        }
        else if (shadowColor == 1) {
            this.imageIndex = 3;
        }
        else if (shadowColor == 2) {
            this.imageIndex = 6;
        }
        this.settled = true;
    }
    
    @Override
    public Sprite reset() {
        return this;
    }
    
    @Override
    public void update(final double delta) {
        this.ticks += delta;
       // final int pulseIndex = Utilities.getPulsingImageIndex();
        if (!this.hit && !this.hidden) {
            //this.imageIndex = pulseIndex;
            if (this.shadowColor == 1) {
                this.imageIndex += 1;
            }
            else if (this.shadowColor == 2) {
                this.imageIndex += 2;
            }
        }
        else if (!this.hit && this.hidden) {
            this.imageIndex = this.images.length - 1;
        }
        else if (this.metal) {
            if (!this.hidden) {
                this.imageIndex = 3;
                if (this.shadowColor == 1) {
                    this.imageIndex = 4;
                }
                else if (this.shadowColor == 2) {
                    this.imageIndex = 5;
                }
            }
            else if (this.game.level.levelType == 0 || this.game.level.levelType == 4 || this.game.level.levelType == 3) {
                this.imageIndex = 3;
            }
            else if (this.game.level.levelType == 1) {
                this.imageIndex = 4;
            }
            else if (this.game.level.levelType == 2) {
                this.imageIndex = 5;
            }
        }
        if (this.hit && !this.settled) {
            this.applyGravity(delta, 80.0);
            if (this.yPos > this.normalY) {
                this.yPos = this.normalY;
                this.settled = true;
                if (this.contents == QuestionBox.CONTENTS_GROWTH && !this.game.mario.isLarge()) {
                    this.game.level.sprites.add(new Mushroom(this.game, new ImageIcon[] { this.game.textures.growMushroom }, this, false));
                }
                else if (this.contents == QuestionBox.CONTENTS_GROWTH && this.game.mario.isLarge()) {
                    this.game.level.sprites.add(new Flower(this.game, this.game.textures.getFlowerTextures(), this));
                }
                else if (this.contents == QuestionBox.CONTENTS_EXTRA_LIFE) {
                    this.game.level.sprites.add(new Life(this.game, new ImageIcon[] { this.game.textures.lightExtraLife, this.game.textures.darkExtraLife }, this));
                }
                else if (this.contents == QuestionBox.CONTENTS_STAR) {
                    this.game.level.sprites.add(new Star(this.game, this.game.textures.getStarTextures(), this));
                }
                else if (this.contents == QuestionBox.CONTENTS_BEANSTALK) {
                    this.game.level.sprites.add(new Beanstalk(this.game, this.game.textures.getBeanstalkTextures(), this));
                }
                else if (this.contents == QuestionBox.CONTENTS_POISON) {
                    this.game.level.sprites.add(new PoisonMushroom(this.game, new ImageIcon[] { this.game.textures.poisonMushroom }, this));
                }
            }
        }
        this.y = (int)Math.round(this.yPos);
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
        if (this.settled && !this.metal) {
            this.hit = true;
            this.justHit = true;
            this.settled = false;
            this.metal = true;
            if (this.hidden) {
                this.exposed = true;
            }
            this.yVel = -36.0;
            if (this.contents == QuestionBox.CONTENTS_COIN && this.isOnScreen()) {
                this.game.level.effects.add(new BumpCoin(this.game, this, sprite.isOnScreen()));
                final Mario mario = this.game.mario;
                mario.points += 200;
                final Mario mario2 = this.game.mario;
                ++mario2.coins;
            }
        }
        if (sprite.isOnScreen()) {
            this.game.audio.play(8);
        }
    }
    
    @Override
    public void setNormalYPosition(final int y) {
        this.normalY = y;
    }
    
    @Override
    public void setWarp(final Warp warp) {
        this.warp = warp;
    }
    @Override
    protected void setImages(final ImageIcon[] images) {
        this.images = images;
        if (images.length > 1) {
        	
            this.width = images[0].getIconWidth();
            this.height = images[0].getIconHeight();
            this.tilesWidth = this.width / 8;
            this.tilesHeight = this.height / 8;
            for (int i = 1; i < images.length; ++i) {
                if (images[i] == null) {
                    throw new RuntimeException("Problem loading index " + i + " from " + this.toString());
                }
                if (images[i].getIconWidth() != this.width || images[i].getIconHeight() != this.height) {
                    throw new RuntimeException("Sprite image dimensions aren't all equal! Expected: "+this.width+"x"+this.height+"; found: "+images[i].getIconWidth()+"x"+images[i].getIconHeight()+" "+((images[i] instanceof AnimatedIcon)? "Image is an AnimatedIcon": "Image is not an AnimatedIcon"));
                }
            }
        }
        else {
            this.width = images[0].getIconWidth();
            this.height = images[0].getIconHeight();
            this.tilesWidth = this.width / 8;
            this.tilesHeight = this.height / 8;
        }
    }
    
    static {
        QuestionBox.CONTENTS_COIN = 0;
        QuestionBox.CONTENTS_GROWTH = 2;
        QuestionBox.CONTENTS_EXTRA_LIFE = 3;
        QuestionBox.CONTENTS_STAR = 4;
        QuestionBox.CONTENTS_BEANSTALK = 5;
        QuestionBox.CONTENTS_POISON = 6;
    }
}
