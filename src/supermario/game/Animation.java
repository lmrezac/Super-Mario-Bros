// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import supermario.Utilities;
import supermario.game.sprites.Mario;
@SuppressWarnings("unused")
public class Animation
{
    private Game game;
    private Textures t;
    private boolean finished;
    private boolean preBlanking;
    private boolean blanking;
    private boolean bigCastle;
    private boolean blueSky;
    private boolean playedWarpSound;
    private int marioIndex;
    private double lastImageChangeDelay;
    private double marioX;
    private double preBlankDelayTime;
    private double blankDelayTime;
    private final long PRE_BLANK_DELAY = 1500L;
    private final long BLANK_DELAY = 400L;
    private final int MARIO_MAX_X = 160;
    private final double MARIO_X_SPEED;
    
    public Animation(final Game game) {
        this.MARIO_X_SPEED = Mario.MAX_WALKING_SPEED / 2.0;
        this.game = game;
        this.t = game.textures;
        this.finished = true;
    }
    
    public void prepare(final boolean bigCastle, final boolean blueSky, final int texturePack) {
        this.bigCastle = bigCastle;
        this.blueSky = blueSky;
        this.game.texturePacks.setTexturePack(texturePack);
        this.lastImageChangeDelay = 0.0;
        this.finished = false;
        this.preBlanking = false;
        this.blanking = false;
        this.playedWarpSound = false;
        this.marioX = 40.0;
        this.game.audio.playMusic(6);
        this.setNextMarioIndex();
    }
    
    public boolean isFinished() {
        return this.finished;
    }
    
    public void update(final double delta) {
        if (this.preBlanking) {
            this.preBlankDelayTime += delta;
        }
        if (this.blanking) {
            this.blankDelayTime += delta;
            if (this.blankDelayTime > 400.0) {
                this.finished = true;
                return;
            }
        }
        this.lastImageChangeDelay += delta;
        if (this.lastImageChangeDelay > 125.0) {
            this.lastImageChangeDelay %= 125.0;
            this.setNextMarioIndex();
        }
        final int roundedMarioX = (int)Math.round(this.marioX);
        if (roundedMarioX > 160 - this.game.mario.width && !this.playedWarpSound) {
            this.game.audio.play(5);
            this.playedWarpSound = true;
        }
        if (roundedMarioX > 160) {
            if (!this.preBlanking && !this.blanking) {
                this.game.audio.stopMusic(false);
                this.preBlanking = true;
                this.preBlankDelayTime = 0.0;
            }
            else if (this.preBlanking && this.preBlankDelayTime > 1500.0) {
                this.preBlanking = false;
                this.blanking = true;
                this.blankDelayTime = 0.0;
            }
        }
        this.marioX += this.MARIO_X_SPEED / 1000.0 * delta * (this.playedWarpSound ? 0.7 : 1.0);
    }
    
    private void setNextMarioIndex() {
        if (this.game.mario.isLarge()) {
            if (this.game.mario.hasFlower()) {
                if (this.marioIndex == 33) {
                    this.marioIndex = 34;
                }
                else if (this.marioIndex == 34) {
                    this.marioIndex = 35;
                }
                else {
                    this.marioIndex = 33;
                }
            }
            else if (this.marioIndex == 2) {
                this.marioIndex = 3;
            }
            else if (this.marioIndex == 3) {
                this.marioIndex = 4;
            }
            else {
                this.marioIndex = 2;
            }
        }
        else if (this.marioIndex == 17) {
            this.marioIndex = 18;
        }
        else if (this.marioIndex == 18) {
            this.marioIndex = 19;
        }
        else {
            this.marioIndex = 17;
        }
    }
    
    public void draw(final Graphics2D g2D) {
    	Rectangle bound = g2D.getDeviceConfiguration().getBounds();
    	BufferedImage buf = new BufferedImage((int)bound.getWidth(),(int)bound.getHeight(),BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g2d = buf.createGraphics();
        if (this.blanking) {
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, Game.renderWidth, Game.renderHeight);
            return;
        }
        if (this.blueSky) {
            g2d.setColor(this.t.skyBlue);
        }
        else {
            g2d.setColor(Color.BLACK);
        }
        g2d.fillRect(0, 0, Game.renderWidth, Game.renderHeight);
        for (int i = 0; i < Game.xTiles; i += 2) {
            g2d.drawImage(this.t.lightGround.getImage(), i * 8, Game.yTiles * 8 - 24, null);
            g2d.drawImage(this.t.lightGround.getImage(), i * 8, Game.yTiles * 8 - 8, null);
        }
        if (this.bigCastle) {
            g2d.drawImage(this.t.largeCastle.getImage(), 0, Game.renderHeight - 24 - this.t.largeCastle.getIconHeight(), null);
        }
        else {
            g2d.drawImage(this.t.smallCastle.getImage(), 0, Game.renderHeight - 24 - this.t.smallCastle.getIconHeight(), null);
            g2d.drawImage(this.t.doubleCloud.getImage(), 56, 40, null);
        }
        g2d.drawImage(this.t.singleCloud.getImage(), 152, 104, null);
        final int roundedMarioX = (int)Math.round(this.marioX);
        if (roundedMarioX < 160) {
            this.drawMario(g2d, roundedMarioX);
        }
        if (this.blueSky) {
            g2d.drawImage(this.t.greenPipes[0].getImage(), 160, 168, null);
            g2d.drawImage(this.t.greenPipes[5].getImage(), 184, 168, null);
            g2d.drawImage(this.t.greenPipes[6].getImage(), 192, 168, null);
            g2d.drawImage(this.t.greenPipes[2].getImage(), 192, 136, null);
        }
        else {
            g2d.drawImage(this.t.whitePipes[0].getImage(), 160, 168, null);
            g2d.drawImage(this.t.whitePipes[5].getImage(), 184, 168, null);
            g2d.drawImage(this.t.whitePipes[6].getImage(), 192, 168, null);
            g2d.drawImage(this.t.whitePipes[2].getImage(), 192, 136, null);
        }
        g2d.dispose();
        if(game.luigiBros)
        	buf = Utilities.horizontalFlip(buf);
        g2D.drawImage(buf,0,0,null);
    }
    
    private void drawMario(final Graphics2D g2D, final int xPos) {
        if (this.game.mario.character == Mario.asLuigi && this.marioIndex < this.game.mario.luigiImages.length) {
            this.game.mario.images = this.game.mario.luigiImages;
        }else if(this.game.mario.character == Mario.asSponge && this.marioIndex < this.game.mario.spongeImages.length){
        	this.game.mario.images = this.game.mario.spongeImages;
        }
        g2D.drawImage(this.game.mario.images[this.marioIndex].getImage(), xPos, Game.renderHeight - 24 - this.game.mario.height, null);
        this.game.mario.images = this.game.mario.marioImages;
    }
}
