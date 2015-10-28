// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.misc;

import java.awt.Rectangle;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.awt.Graphics2D;
import supermario.game.sprites.Mario;
import supermario.game.Game;
import javax.swing.ImageIcon;
import supermario.game.Warp;
import supermario.game.interfaces.Warpable;
import supermario.game.Sprite;

public class Flag extends Sprite implements Warpable
{
    public Warp warp;
    public boolean caught;
    public boolean flagFinished;
    public boolean deductionSwitch;
    private double flagY;
    private double castleFlagY;
    private double normalCastleFlagY;
    private double timeStepDelta;
    private double secondsCollected;
    public double totalTimeLeft;
    private ImageIcon flag;
    private ImageIcon flagpole;
    private ImageIcon singleCloud;
    private ImageIcon singleBush;
    private ImageIcon smallHill;
    private ImageIcon castle;
    private ImageIcon lightGround;
    private ImageIcon castleFlag;
    private ImageIcon points;
    public boolean bigCastle;
    public int fireworksLeft;
    public int lastSecondLeftWholeNumber;
    public static final double FLAG_FALL_SPEED = 136.0;
    public static final int STATE_CATCH_FLAG = 0;
    public static final int STATE_SLIDE_DOWN_FLAG = 1;
    public static final int STATE_TURN_AROUND_AT_FLAG_BASE = 2;
    public static final int STATE_PLAY_VICTORY_MUSIC = 3;
    public static final int STATE_PREPARE_TO_WALK_TO_CASTLE = 4;
    public static final int STATE_WALK_TO_CASTLE = 5;
    public static final int STATE_DECIDE_ON_FIREWORKS = 6;
    public static final int STATE_SHOW_FIREWORKS = 7;
    public static final int STATE_RAISE_CASTLE_FLAG = 8;
    public static final int STATE_DEDUCT_TIME = 9;
    public static final int STATE_WARP_TO_NEXT_LEVEL = 10;
    public static final double EXCESS_TIME_MAX_LENGTH = 3000.0;
    public static final double TIME_DEDUCT_INTERVAL = 20.0;
    public static final double MINIMUM_TIME_STEP_DELTA = 0.6;
    public static final int POINTS_TO_SECONDS_EXCHANGE_RATE = 150;
    
    public Flag(final Game game, final boolean bigCastle, final int pipeType) {
        super(game, new ImageIcon[] { game.textures.flag });
        this.bigCastle = bigCastle;
        if (pipeType == 1) {
            this.flagpole = game.textures.flagpoleWhite;
        }
        else {
            this.flagpole = game.textures.flagpoleGreen;
        }
        this.flag = game.textures.flag;
        this.singleCloud = game.textures.singleCloud;
        this.singleBush = game.textures.singleBush;
        this.smallHill = game.textures.smallHill;
        if (bigCastle) {
            this.castle = game.textures.largeCastle;
        }
        else {
            this.castle = game.textures.smallCastle;
        }
        this.normalCastleFlagY = 124.0;
        this.castleFlagY = this.normalCastleFlagY;
        this.lightGround = game.textures.lightGround;
        this.castleFlag = game.textures.castleFlag;
        this.yPos = 0.0;
        this.y = 0;
        this.flagY = 40.0;
        this.flagFinished = false;
    }
    
    @Override
    public Sprite reset() {
        return null;
    }
    
    @Override
    public void update(final double delta) {
        if (this.caught) {
            if (!this.flagFinished) {
                this.flagY += 136.0 * delta / 1000.0;
                if (this.flagY >= 168.0) {
                    this.flagY = 168.0;
                    this.flagFinished = true;
                }
            }
            else if (this.spriteState == 9) {
                this.ticks += delta;
                if (this.ticks >= 20.0) {
                    this.ticks %= 20.0;
                    if (this.deductionSwitch) {
                        final Mario mario = this.game.mario;
                        mario.totalTime += this.timeStepDelta - 0.5;
                        this.secondsCollected += this.timeStepDelta - 0.5;
                    }
                    else {
                        final Mario mario2 = this.game.mario;
                        mario2.totalTime += this.timeStepDelta + 0.5;
                        this.secondsCollected += this.timeStepDelta + 0.5;
                    }
                    this.deductionSwitch = !this.deductionSwitch;
                    if ((int)this.secondsCollected > this.lastSecondLeftWholeNumber) {
                        final Mario mario3 = this.game.mario;
                        mario3.points += 150;
                        ++this.lastSecondLeftWholeNumber;
                    }
                    if (this.secondsCollected >= this.totalTimeLeft) {
                        this.game.audio.stop(18);
                        this.spriteState = 8;
                    }
                }
            }
            else if (this.spriteState == 8 && this.normalCastleFlagY - this.castleFlagY < 20.0) {
                this.castleFlagY -= 34.0 * delta / 1000.0;
            }
        }
    }
    
    public void setPoints() {
        final int pointsY = this.game.mario.y + this.game.mario.height - this.avoidedCollisionRowsOnBottom;
        if (pointsY >= 184) {
            final Mario mario = this.game.mario;
            mario.points += 100;
            this.points = this.game.textures.points100;
        }
        else if (pointsY >= 168) {
            final Mario mario2 = this.game.mario;
            mario2.points += 200;
            this.points = this.game.textures.points200;
        }
        else if (pointsY >= 152) {
            final Mario mario3 = this.game.mario;
            mario3.points += 400;
            this.points = this.game.textures.points400;
        }
        else if (pointsY >= 136) {
            final Mario mario4 = this.game.mario;
            mario4.points += 500;
            this.points = this.game.textures.points500;
        }
        else if (pointsY >= 120) {
            final Mario mario5 = this.game.mario;
            mario5.points += 800;
            this.points = this.game.textures.points800;
        }
        else if (pointsY >= 104) {
            final Mario mario6 = this.game.mario;
            mario6.points += 1000;
            this.points = this.game.textures.points1000;
        }
        else if (pointsY >= 88) {
            final Mario mario7 = this.game.mario;
            mario7.points += 2000;
            this.points = this.game.textures.points2000;
        }
        else {
            final Mario mario8 = this.game.mario;
            mario8.points += 5000;
            this.points = this.game.textures.points5000;
        }
    }
    
    public void setExcessTime(final int timeLeftInLevel) {
        this.totalTimeLeft = timeLeftInLevel;
        this.timeStepDelta = this.totalTimeLeft / 150.0;
        if (this.timeStepDelta < 0.6) {
            this.timeStepDelta = 0.6;
        }
    }
    
    public void setFireworks(final int timeLeftInLevel) {
        if (this.bigCastle) {
            this.fireworksLeft = 0;
        }
        else {
            final int lastDigit = timeLeftInLevel % 10;
            if (lastDigit == 1 || lastDigit == 3 || lastDigit == 6) {
                this.fireworksLeft = lastDigit;
            }
            else {
                this.fireworksLeft = 0;
            }
        }
    }
    
    @Override
    public void draw(final Graphics2D g2D) {
        this.transform.setToIdentity();
        this.transform.translate(this.x + 8, 32.0);
        g2D.drawImage(this.flagpole.getImage(), this.transform, null);
        this.transform.setToIdentity();
        this.transform.translate(this.x, (int)Math.round(this.flagY));
        g2D.drawImage(this.flag.getImage(), this.transform, null);
        this.transform.setToIdentity();
        this.transform.translate(this.x + 40, 32.0);
        g2D.drawImage(this.singleCloud.getImage(), this.transform, null);
        if (this.caught) {
            this.transform.setToIdentity();
            this.transform.translate(this.x + 16 + 2, 176 - ((int)Math.round(this.flagY) - 32));
            g2D.drawImage(this.points.getImage(), this.transform, null);
        }
        this.transform.setToIdentity();
        this.transform.translate(this.x + 128, 184.0);
        g2D.drawImage(this.singleBush.getImage(), this.transform, null);
        this.transform.setToIdentity();
        this.transform.translate(this.x + 168, 176.0);
        g2D.drawImage(this.smallHill.getImage(), this.transform, null);
        this.transform.setToIdentity();
        this.transform.translate(this.x + 104, this.castleFlagY);
        g2D.drawImage(this.castleFlag.getImage(), this.transform, null);
        this.transform.setToIdentity();
        this.transform.translate(this.x + 72, 0.0);
        if (this.bigCastle) {
            this.transform.translate(0.0, 24.0);
        }
        else {
            this.transform.translate(0.0, 120.0);
        }
        g2D.drawImage(this.castle.getImage(), this.transform, null);
        this.transform.setToIdentity();
        this.transform.translate(this.x, 200.0);
        final int xTranslate = 16;
        for (int i = 0; i < Game.renderWidth / 8; ++i) {
            g2D.drawImage(this.lightGround.getImage(), this.transform, null);
            this.transform.translate(0.0, this.lightGround.getIconHeight());
            g2D.drawImage(this.lightGround.getImage(), this.transform, null);
            this.transform.translate(0.0, -this.lightGround.getIconHeight());
            this.transform.translate(xTranslate, 0.0);
        }
    }
    
    public Point getFireworkLocation() {
        if (this.fireworksLeft % 3 == 0) {
            return new Point(this.x + 120, 40);
        }
        if (this.fireworksLeft % 3 == 2) {
            return new Point(this.x + 56, 96);
        }
        if (this.fireworksLeft % 3 == 1) {
            return new Point(this.x + 176, 80);
        }
        return null;
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x + 16 - 1, 24, 2, (Game.yTiles - 3 - 5) * 8);
    }
    
    @Override
    public void setWarp(final Warp warp) {
        this.warp = warp;
    }
}
