// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.enemies;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import supermario.Utilities;
import supermario.game.Game;
import supermario.game.Sprite;
import supermario.game.interfaces.Enemy;
import supermario.game.sprites.misc.Firebar;

public final class FireballEnemy extends Sprite implements Enemy
{
    private boolean clockwise;
    private boolean speedBoost;
    private double barXCenter;
    private double barYCenter;
    private double yDistance;
    private double imageTheta;
    public static final int ROTATION_PERIOD = 280;
    
    public FireballEnemy(final Game game, final Firebar firebar, final boolean clockwise, final boolean speedBoost, final int yDistance) {
        super(game, game.textures.getFireballTextures());
        this.clockwise = clockwise;
        this.speedBoost = speedBoost;
        this.barXCenter = firebar.x + firebar.width / 2;
        this.barYCenter = firebar.y + firebar.height / 2;
        this.yDistance = yDistance;
        this.imageIndex = 0;
        this.update(1.0);
    }
    
    @Override
    public Sprite reset() {
        return null;
    }
    
    @Override
    public void update(final double delta) {
        this.preUpdate(delta);
        double posTheta;
        if (this.speedBoost) {
            posTheta = Utilities.getPeriodicTheta(true);
        }
        else {
            posTheta = Utilities.getPeriodicTheta(false);
        }
        this.updateImageTheta();
        if (!this.clockwise) {
            posTheta *= -1.0;
        }
        this.xPos = this.barXCenter - 4.0 + Math.sin(posTheta) * this.yDistance;
        this.yPos = this.barYCenter - 4.0 - Math.cos(posTheta) * this.yDistance;
        this.finalizePosition();
    }
    
    private void updateImageTheta() {
        this.ticks %= 280.0;
        this.imageTheta = this.ticks / 280.0 * 2.0 * 3.141592653589793;
    }
    
    @Override
    public void draw(final Graphics2D g2D) {
        if (!this.visible) {
            return;
        }
        this.transform.setToIdentity();
        this.transform.translate(this.x, this.y);
        if (this.flip) {
            this.transform.translate(this.width, 0.0);
            this.transform.scale(-1.0, 1.0);
        }
        this.transform.rotate(this.imageTheta, this.width / 2, this.height / 2);
        g2D.drawImage(this.images[this.imageIndex].getImage(), this.transform, null);
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
    
    @Override
    public void checkForUnsettledTiles() {
    }
    
    @Override
    public Rectangle getSpriteContactRectangle() {
        return this.getRectangle();
    }
    
    @Override
    public void bumpKilled(final Sprite killer) {
    }
    
    @Override
    public void smushed(final Sprite killer) {
    }
    
    @Override
    public void xCollided() {
    }
}
