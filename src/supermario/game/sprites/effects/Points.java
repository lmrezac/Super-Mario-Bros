// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.effects;

import java.awt.image.ImageObserver;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import supermario.game.Game;
import supermario.game.interfaces.Effect;

public class Points implements Effect
{
    public static final int POINTS_100 = 0;
    public static final int POINTS_200 = 1;
    public static final int POINTS_400 = 2;
    public static final int POINTS_500 = 3;
    public static final int POINTS_800 = 4;
    public static final int POINTS_1000 = 5;
    public static final int POINTS_2000 = 6;
    public static final int POINTS_5000 = 7;
    public static final int POINTS_8000 = 8;
    public static final int CHECK_POINT = 9;
    public static final int ONE_UP = 10;
    public static final int MAX_HEIGHT_FROM_START = 48;
    public static final double RISING_SPEED = 48.0;
    private Game game;
    private int x;
    private int y;
    private double xPos;
    private double yPos;
    private double xFromScreenLeft;
    private double startingY;
    private ImageIcon pointsImage;
    
    public Points(final Game game, final double objectXCenter, final double objectY, final int type) {
        this.game = game;
        this.pointsImage = this.getPointsImage(type);
        this.xFromScreenLeft = objectXCenter - this.pointsImage.getIconWidth() / 2 - game.level.leftMostX;
        this.startingY = objectY - this.pointsImage.getIconHeight();
        this.xPos = this.xFromScreenLeft;
        this.yPos = this.startingY;
        this.x = (int)Math.round(this.xPos);
        this.y = (int)Math.round(this.yPos);
    }
    
    private ImageIcon getPointsImage(final int type) {
        if (type == 0) {
            return this.game.textures.points100;
        }
        if (type == 1) {
            return this.game.textures.points200;
        }
        if (type == 2) {
            return this.game.textures.points400;
        }
        if (type == 3) {
            return this.game.textures.points500;
        }
        if (type == 4) {
            return this.game.textures.points800;
        }
        if (type == 5) {
            return this.game.textures.points1000;
        }
        if (type == 6) {
            return this.game.textures.points2000;
        }
        if (type == 7) {
            return this.game.textures.points5000;
        }
        if (type == 8) {
            return this.game.textures.points8000;
        }
        if (type == 9) {
            return this.game.textures.checkpoint;
        }
        if (type == 10) {
            return this.game.textures.oneUp;
        }
        return null;
    }
    
    @Override
    public void update(final double delta) {
        this.xPos = this.game.level.leftMostX + this.xFromScreenLeft;
        this.yPos -= 48.0 * delta / 1000.0;
        this.x = (int)Math.round(this.xPos);
        this.y = (int)Math.round(this.yPos);
    }
    
    @Override
    public void draw(final Graphics2D g2D) {
        g2D.drawImage(this.pointsImage.getImage(), this.x, this.y, null);
    }
    
    @Override
    public boolean isFinished() {
        return this.startingY - this.yPos > 48.0;
    }
}
