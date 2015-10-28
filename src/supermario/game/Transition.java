// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game;

import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;
import supermario.Utilities;
import java.awt.Graphics2D;

public final class Transition
{
    private Game game;
    private Textures textures;
    private Animation animation;
    private long totalTime;
    private String title1;
    private String title2;
    private String combinedTitle;
    private String author;
    public final long TIME_DELAY_MS = 3500L;
    public static final long BLANK_DELAY = 400L;
    private long delay;
    public boolean enterReleased;
    public boolean blank;
    public boolean finishedGame;
    public boolean showOutOfTime;
    private boolean animating;
    private boolean shouldAnimate;
    private boolean blueSky;
    private boolean largeCastle;
    private int texturePack;
    
    public Transition(final Game game) {
        this.game = game;
        this.textures = game.textures;
        this.animation = new Animation(game);
    }
    
    public void load(final boolean finishedGame, final boolean shouldAnimate, final boolean blueSky, final boolean largeCastle, final int texturePack) {
        this.delay = 3500L;
        this.blank = false;
        this.finishedGame = finishedGame;
        if (this.game.mario.outOfTimeDeath && this.game.mario.lives > 0) {
            this.showOutOfTime = true;
        }
        this.title1 = this.game.mario.levelNamePart1;
        this.title2 = this.game.mario.levelNamePart2;
        this.combinedTitle = this.title1 + " " + this.title2;
        this.author = this.game.level.levelAuthor;
        this.totalTime = 0L;
        this.enterReleased = false;
        if (this.game.mario.lives <= 0) {
            this.game.audio.playMusic(0);
        }
        else if (finishedGame) {
            this.game.audio.loopMusic(3, false);
        }
        else {
            this.animating = false;
            this.shouldAnimate = shouldAnimate;
            this.blueSky = blueSky;
            this.largeCastle = largeCastle;
            this.texturePack = texturePack;
        }
    }
    
    public void blankForQuickDelay() {
        this.finishedGame = false;
        this.totalTime = 0L;
        this.delay = 400L;
        this.blank = true;
    }
    
    public void skip() {
    	//this.game.audio.stopMusic(false);
        if (this.game.mario.lives > 0 && !this.blank && !this.finishedGame && this.enterReleased) {
            if (this.showOutOfTime) {
                this.showOutOfTime = false;
                this.totalTime = 0L;
                this.enterReleased = false;
            }
            else {
                this.totalTime = 3500L;
            }
        }
        else if (this.finishedGame) {
            this.game.audio.stopMusic(false);
            this.game.changeToMenu();
        }
    }
    
    public void update(final double delta) {
        this.totalTime += (long)delta;
        if (!this.game.input.startDown) {
            this.enterReleased = true;
        }
        if (this.game.input.startDown && this.enterReleased) {
            this.skip();
        }
        if (this.totalTime >= this.delay && !this.finishedGame) {
            if (this.showOutOfTime) {
                this.showOutOfTime = false;
                this.totalTime = 0L;
            }
            else if (this.game.mario.lives > 0) {
                if (this.shouldAnimate && !this.animating) {
                    this.shouldAnimate = false;
                    this.animating = true;
                    this.animation.prepare(this.largeCastle, this.blueSky, this.texturePack);
                    this.game.setOverlayCoinLockedState(false);
                }
                else if (this.animating) {
                    if (this.animation.isFinished()) {
                        this.animating = false;
                        this.blank = true;
                    }
                    else {
                        this.animation.update(delta);
                    }
                }
                else {
                	//game.audio.haltMusic();
                	game.audio.stop(Audio.MUSIC_OVERWORLD);
                    this.game.changeToLevel();
                    
                }
            }
            else {
                this.game.changeToMenu();
            }
        }
    }
    
    public void draw(final Graphics2D g2D) {
        if (this.animating) {
            this.animation.draw(g2D);
            return;
        }
        g2D.setColor(this.textures.black);
        g2D.fillRect(0, 0, Game.renderWidth, Game.renderHeight);
        if (this.blank) {
            return;
        }
        if (this.game.mario.lives > 0 && !this.finishedGame) {
            if (this.showOutOfTime) {
                Utilities.drawTextAtTiles(g2D, "TIME UP", Game.overlayXOffset + 12, Game.overlayYOffset + 15);
            }
            else {
                Image image = this.textures.marioSmStand.getImage();
                if (this.game.mario.asLuigi) {
                    image = this.textures.luigiSmStand.getImage();
                }
                Utilities.drawAtTile(g2D, image, Game.overlayXOffset + 12, 10);
                Utilities.drawTextAtPixels(g2D, this.combinedTitle, Game.xTiles * 8 / 2 - this.combinedTitle.length() * 8 / 2, 72);
                Utilities.drawAtTile(g2D, this.textures.symbols.get('\ufffd').getImage(), Game.overlayXOffset + 15, 13);
                Utilities.drawTextAtTiles(g2D, "" + this.game.mario.lives, Game.overlayXOffset + 18, 13);
                Utilities.drawTextAtPixels(g2D, "Author:", Game.xTiles * 8 / 2 - 28, 144);
                Utilities.drawTextAtPixels(g2D, this.author, Game.xTiles * 8 / 2 - this.author.length() * 8 / 2, 160);
            }
        }
        else if (this.finishedGame) {
            Utilities.drawTextAtTiles(g2D, "THANK YOU " + (this.game.mario.asLuigi ? "LUIGI" : "MARIO"), Game.overlayXOffset + 8, Game.overlayYOffset + 12);
            Utilities.drawAtTile(g2D, this.game.textures.symbols.get('\ufffc').getImage(), Game.overlayXOffset + 23, Game.overlayYOffset + 12);
            Utilities.drawTextAtTiles(g2D, "YOUR QUEST IS OVER.", Game.overlayXOffset + 7, Game.overlayYOffset + 16);
            Image image;
            if (this.game.mario.hasFlower()) {
                image = this.game.textures.marioFlowerStand.getImage();
            }
            else if (this.game.mario.isLarge()) {
                if (this.game.mario.asLuigi) {
                    image = this.game.textures.luigiStand.getImage();
                }
                else {
                    image = this.game.textures.marioStand.getImage();
                }
            }
            else if (this.game.mario.asLuigi) {
                image = this.game.textures.luigiSmStand.getImage();
            }
            else {
                image = this.game.textures.marioSmStand.getImage();
            }
            g2D.drawImage(image, 112, (Game.overlayYOffset + 21) * 8 + 1, null);
            g2D.drawImage(this.game.textures.bubby.getImage(), 129, (Game.overlayYOffset + 22) * 8 + 1, null);
        }
        else {
            Utilities.drawTextAtTiles(g2D, "GAME OVER", Game.overlayXOffset + 11, Game.overlayYOffset + 16);
        }
    }
}
