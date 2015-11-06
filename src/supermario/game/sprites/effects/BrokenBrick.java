// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.effects;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import supermario.game.Game;
import supermario.game.interfaces.Effect;
import supermario.game.sprites.blocks.Brick;
@SuppressWarnings("unused")
public class BrokenBrick implements Effect
{
    private Game game;
    private Brick brick;
    private double xPosLeft;
    private double xPosRight;
    private double yPosHigh;
    private double yPosLow;
    private int xLeft;
    private int xRight;
    private int yHigh;
    private int yLow;
    private double xVel;
    private double yVelHigh;
    private double yVelLow;
    private ImageIcon[] brickPieces;
    private int ticks;
    private int imageIndex;
    public final double BRICK_PIECE_GRAVITY = 1200.0;
    public final double BRICK_PIECE_X_VELOCITY = 48.0;
    public final double BRICK_PIECE_TERMINAL_VELOCITY = 280.0;
    
    public BrokenBrick(final Game game, final Brick brick) {
        this.game = game;
        this.brick = brick;
        this.brickPieces = new ImageIcon[4];
        if (brick.brickType == 0) {
            this.brickPieces[0] = game.textures.lightBrickPiece1;
            this.brickPieces[1] = game.textures.lightBrickPiece2;
            this.brickPieces[2] = game.textures.lightBrickPiece3;
            this.brickPieces[3] = game.textures.lightBrickPiece4;
        }
        else if (brick.brickType == 1) {
            this.brickPieces[0] = game.textures.darkBrickPiece1;
            this.brickPieces[1] = game.textures.darkBrickPiece2;
            this.brickPieces[2] = game.textures.darkBrickPiece3;
            this.brickPieces[3] = game.textures.darkBrickPiece4;
        }
        else if (brick.brickType == 2) {
            this.brickPieces[0] = game.textures.stoneBrickPiece1;
            this.brickPieces[1] = game.textures.stoneBrickPiece2;
            this.brickPieces[2] = game.textures.stoneBrickPiece3;
            this.brickPieces[3] = game.textures.stoneBrickPiece4;
        }
        this.xPosLeft = brick.x;
        this.xPosRight = brick.x + 8;
        this.yPosHigh = brick.y;
        this.yPosLow = brick.y + 8;
        this.xVel = 48.0;
        this.yVelHigh = -376.0;
        this.yVelLow = -320.0;
    }
    
    @Override
    public void update(final double delta) {
        this.ticks += (int)Math.round(delta);
        this.yVelHigh += 1200.0 * delta / 1000.0;
        if (this.yVelHigh > 280.0) {
            this.yVelHigh = 280.0;
        }
        this.yPosHigh += this.yVelHigh * delta / 1000.0;
        this.yVelLow += 1200.0 * delta / 1000.0;
        if (this.yVelLow > 280.0) {
            this.yVelLow = 280.0;
        }
        this.yPosLow += this.yVelLow * delta / 1000.0;
        this.xPosLeft -= this.xVel * delta / 1000.0;
        this.xPosRight += this.xVel * delta / 1000.0;
        this.xLeft = (int)Math.round(this.xPosLeft);
        this.xRight = (int)Math.round(this.xPosRight);
        this.yHigh = (int)Math.round(this.yPosHigh);
        this.yLow = (int)Math.round(this.yPosLow);
        if (this.ticks > 200) {
            this.ticks -= 200;
            this.imageIndex = (this.imageIndex + 1) % this.brickPieces.length;
        }
    }
    
    @Override
    public boolean isFinished() {
        return this.yPosHigh > Game.renderHeight;
    }
    
    @Override
    public void draw(final Graphics2D g2D) {
        g2D.drawImage(this.brickPieces[this.imageIndex].getImage(), this.xLeft, this.yHigh, null);
        g2D.drawImage(this.brickPieces[this.imageIndex].getImage(), this.xLeft, this.yLow, null);
        g2D.drawImage(this.brickPieces[this.imageIndex].getImage(), this.xRight, this.yHigh, null);
        g2D.drawImage(this.brickPieces[this.imageIndex].getImage(), this.xRight, this.yLow, null);
    }
}
