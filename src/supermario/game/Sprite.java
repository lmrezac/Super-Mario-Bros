// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

import javax.swing.ImageIcon;

import supermario.game.interfaces.Block;
import supermario.game.interfaces.Enemy;
import supermario.game.interfaces.Friend;
import supermario.game.interfaces.Shelled;
import supermario.game.sprites.Mario;
import supermario.game.sprites.enemies.Bowser;
import supermario.game.sprites.enemies.GrayFish;
import supermario.game.sprites.enemies.HammerBro;
import supermario.game.sprites.enemies.Koopa;
import supermario.game.sprites.enemies.LavaBall;
import supermario.game.sprites.friends.FireballFriend;
import supermario.game.sprites.misc.BowserBattle;
import supermario.game.sprites.misc.Checkpoint;
import supermario.game.sprites.misc.Firebar;
import supermario.game.sprites.misc.Flag;
import supermario.game.sprites.misc.Pipe;
import supermario.game.sprites.misc.Platform;
import supermario.game.sprites.misc.Spring;

public abstract class Sprite
{
    protected Game game;
    public int xTile;
    public int yTile;
    public int x;
    public int y;
    public double lastX;
    public double lastY;
    public double xPos;
    public double yPos;
    public double xVel;
    public double yVel;
    public double platformVel;
    public double xAcc;
    public double yAcc;
    public int width;
    public int height;
    public int tilesWidth;
    public int tilesHeight;
    public int spriteState;
    public ImageIcon[] images;
    public int imageIndex;
    public AffineTransform transform;
    public double ticks;
    public boolean flip;
    public boolean vFlip;
    public int avoidedCollisionRowsOnTop;
    public int avoidedCollisionRowsOnBottom;
    public int avoidedCollisionCols;
    public int avoidedUpsideDownCollisionRowsOnBottom;
    public boolean locked;
    public boolean visible;
    public boolean collidable;
    public boolean grounded;
    public boolean settled;
    public boolean bumped;
    public boolean justHit;
    public boolean bumpKilled;
    public boolean injected;
    public static final int COLLISION_ITERATIONS = 2;
    
    public Sprite(final Game game, final ImageIcon[] images) {
        this.game = game;
        this.setImages(images);
        this.transform = new AffineTransform();
        this.imageIndex = 0;
        this.visible = true;
        this.collidable = true;
    }
    
    public abstract Sprite reset();
    
    public abstract void update(final double p0);
    
    public void releaseSprite(final double delta, final Tile tile) {
        if (this instanceof Firebar) {
            ((Firebar)this).activate(tile);
        }
        else if (this instanceof Koopa) {
            ((Koopa)this).flyingYCenter = this.y;
            ((Koopa)this).flyingXCenter = this.x;
        }
        else if (this instanceof LavaBall) {
            ((LavaBall)this).activate(tile.xTile, tile.yTile);
        }
        else if (this instanceof Pipe) {
            ((Pipe)this).activate(tile);
        }
        else if (this instanceof Platform) {
            ((Platform)this).activate();
        }
        else if (this instanceof BowserBattle) {
            ((BowserBattle)this).activate();
            this.game.level.levelEndPresent = true;
        }
        else if (this instanceof Flag) {
            this.game.level.levelEndPresent = true;
        }
        else if (this instanceof Spring) {
            ((Spring)this).activate();
        }
        else if (this instanceof HammerBro) {
            ((HammerBro)this).activate();
        }
        else if (this instanceof GrayFish) {
            ((GrayFish)this).activate();
        }
        else if (this instanceof Checkpoint) {
            ((Checkpoint)this).activate();
        }
        if (!(this instanceof Checkpoint) && !(this instanceof Platform) && !(this instanceof Spring)) {
            this.game.level.sprites.add(this);
            this.update(delta);
        }
        this.xTile = tile.xTile;
        this.yTile = tile.yTile;
    }
    public void draw(Graphics2D g){
    	draw(g,-1);
    }
    public void draw(final Graphics2D g2D,int levelType) {
        if (!this.visible) {
            return;
        }
        this.transform.setToIdentity();
        this.transform.translate(this.x, this.y);
        if (this.flip) {
        
            this.transform.translate(this.width, 0.0);
            this.transform.scale(-1.0, 1.0);
        }
        if (this.vFlip) {
            this.transform.translate(0.0, this.height + this.avoidedUpsideDownCollisionRowsOnBottom);
            this.transform.scale(1.0, -1.0);
        }
        try{
        	if(levelType!= -1){
        		if(this.images[imageIndex] instanceof MultiIcon)
        			g2D.drawImage(((MultiIcon)(this.images[imageIndex])).getImage(levelType)/*this.game.textures.getLevelTypeAlt(levelType, this.images[this.imageIndex]).getImage()*/,this.transform,null);
        		else
        			g2D.drawImage(this.images[imageIndex].getImage(), this.transform,null);
        	}else
        		g2D.drawImage(this.images[this.imageIndex].getImage(), this.transform, null);
        }catch(ArrayIndexOutOfBoundsException e){
        	if(levelType != -1)
        		g2D.drawImage(((MultiIcon)(this.images[0])).getImage(),this.transform,null);
        	else
        		g2D.drawImage(this.images[this.images.length-1].getImage(),this.transform,null);
        }
    }	
    
    public void preUpdate(final double delta) {
        this.lastX = this.x;
        this.lastY = this.y;
        this.ticks += delta;
        if (this.ticks > 8.988465674311579E307) {
            this.ticks = 8.988465674311579E307;
        }
    }
    
    public void finalizePosition() {
        this.x = (int)Math.round(this.xPos);
        this.y = (int)Math.round(this.yPos);
    }
    
    public final void setXY(final int x, final int y) {
        this.xPos = x;
        this.yPos = y + this.avoidedCollisionRowsOnBottom;
        this.x = x;
        this.y = y;
    }
    
    public final void setTileXY(final int xTile, final int yTile) {
        this.xPos = xTile * 8;
        this.yPos = yTile * 8 + this.avoidedCollisionRowsOnBottom;
        this.x = (int)this.xPos;
        this.y = (int)this.yPos;
    }
    
    public abstract Rectangle getRectangle();
    
    public int getXCenter() {
        return this.x + this.width / 2;
    }
    
    public void applyGravity(final double deltaDouble, final double magnitude) {
        this.yVel += magnitude * deltaDouble / 1000.0;
        if (this.game.level.levelType == 3) {
            if (this.yVel > Level.TERMINAL_VELOCITY_WATER) {
                this.yVel = Level.TERMINAL_VELOCITY_WATER;
            }
        }
        else if (this.yVel > Level.TERMINAL_VELOCITY) {
            this.yVel = Level.TERMINAL_VELOCITY;
        }
        this.yPos += this.yVel * deltaDouble / 1000.0;
    }
    
    public boolean isColliding() {
        this.finalizePosition();
        final int xTile = (int)(this.xPos / 8.0);
        final int yTile = (int)(this.yPos / 8.0);
        for (int i = 0; i <= this.tilesWidth; ++i) {
            for (int j = 0; j <= this.tilesHeight; ++j) {
                if (xTile + i < this.game.level.xTiles && xTile + i > 0 && yTile + j < Game.yTiles && yTile + j > 0) {
                    final Tile tempTile = this.game.level.tiles[yTile + j][xTile + i];
                    if (!tempTile.isPermeableTile() && !tempTile.disabled && tempTile.getRectangle().intersects(this.getRectangle())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public final void checkForCollisions() {
        this.finalizePosition();
        this.platformVel = 0.0;
        for (int k = 0; k < 2; ++k) {
            this.checkForCollisions(true);
            this.checkForCollisions(false);
        }
        if (!(this instanceof Bowser)) {
            this.checkForPlatformCollisions();
        }
        this.addAdditionalPlatformVelocity();
    }
    
    private void checkForPlatformCollisions() {
        final Iterator<Platform> platformsIter = this.game.level.platforms.iterator();
        while (platformsIter.hasNext()) {
            final Rectangle spriteRect = this.getRectangle();
            final Platform platform = platformsIter.next();
            final Rectangle platformRect = platform.getRectangle();
            if (spriteRect.intersects(platformRect)) {
                if (platform.isHorizontallyOscillatingPlatform() || platform.type == 13) {
                    this.resolveHorizontalMotionPlatformCollision(spriteRect, platform, platformRect);
                }
                else {
                    this.resolveVerticalMotionPlatformCollision(spriteRect, platform, platformRect);
                }
                this.finalizePosition();
            }
        }
    }
    
    private void addAdditionalPlatformVelocity() {
        this.platformVel = 0.0;
        final Platform platform = this.onPlatform();
        if (platform != null && (platform.isHorizontallyOscillatingPlatform() || platform.type == 13)) {
            this.xPos += platform.xChange;
            this.platformVel = platform.xVel;
            this.checkForCollisions(true);
            this.checkForCollisions(false);
        }
    }
    
    private void resolveHorizontalMotionPlatformCollision(final Rectangle spriteRect, final Platform platform, final Rectangle platformRect) {
        if (spriteRect.y + spriteRect.height < platformRect.y + platformRect.height && ((Math.abs(this.xVel) >= 96.0 && this.lastY <= this.y) || (this.lastX + platform.xChange + this.width - this.avoidedCollisionCols > platformRect.x && this.lastX + platform.xChange + this.avoidedCollisionCols < platformRect.x + platformRect.width))) {
            if (this instanceof Mario && platform.type == 13 && (this.yVel < 0.0 || (this.yVel >= 0.0 && this.lastY + this.height - this.avoidedCollisionRowsOnBottom > platform.y))) {
                return;
            }
            this.yPos = platform.yPos - this.height + this.avoidedCollisionRowsOnBottom;
            this.yVel = 0.0;
        }
        else if (spriteRect.y > platformRect.y && this.yVel < 0.0 && this.lastX + platform.xChange + this.width - this.avoidedCollisionCols >= platformRect.x && this.lastX + platform.xChange + this.avoidedCollisionCols <= platformRect.x + platformRect.width) {
            if (this instanceof Mario) {
                if (platform.type == 13) {
                    return;
                }
                if (!this.game.mario.stateHeightHalved) {
                    this.yPos = platform.yPos + platformRect.height - this.avoidedCollisionRowsOnTop;
                }
                else {
                    this.yPos = platform.yPos + platformRect.height - this.height / 2;
                }
                this.game.mario.jumpStopped(true);
                if (platform.isOnScreen()) {
                    this.game.audio.play(8);
                }
            }
            else {
                this.yPos = platform.yPos + platformRect.height - this.avoidedCollisionRowsOnTop;
            }
            if (this instanceof FireballFriend) {
                this.yVel = Math.abs(this.yVel);
            }
            else {
                this.yVel = 0.0;
            }
        }
        else if (platform.type != 13) {
            if (!(this instanceof FireballFriend)) {
                if (this.x + this.width / 2 > platform.x + platform.width / 2) {
                    this.xPos = platform.x + platform.width - this.avoidedCollisionCols;
                }
                else {
                    this.xPos = platform.x - this.width + this.avoidedCollisionCols;
                }
                if (platform.xVel > 0.0 && this.xVel >= platform.xVel) {
                    this.xVel = platform.xVel;
                }
                else if (platform.xVel < 0.0 && this.xVel <= platform.xVel) {
                    this.xVel = platform.xVel;
                }
                else {
                    this.xVel = 0.0;
                }
            }
        }
    }
    
    private void resolveVerticalMotionPlatformCollision(final Rectangle spriteRect, final Platform platform, final Rectangle platformRect) {
        if (spriteRect.y + spriteRect.height <= platformRect.y + platformRect.height && this.lastX + this.width - this.avoidedCollisionCols > platformRect.x && this.lastX + this.avoidedCollisionCols < platformRect.x + platformRect.width) {
            this.yPos = platform.yPos - this.height + this.avoidedCollisionRowsOnBottom;
            this.yVel = platform.yVel;
            if (this.yVel < 0.0) {
                this.yVel = 0.0;
            }
            else if (this.yVel <= 56.0) {
                this.yVel = 56.0;
            }
        }
        else if (spriteRect.y > platformRect.y && this.lastX + this.width - this.avoidedCollisionCols > platformRect.x && this.lastX + this.avoidedCollisionCols < platformRect.x + platformRect.width) {
            if (this instanceof Mario) {
                if (!this.game.mario.stateHeightHalved) {
                    this.yPos = platform.yPos + platformRect.height - this.avoidedCollisionRowsOnTop;
                }
                else {
                    this.yPos = platform.yPos + platformRect.height - this.height / 2;
                }
                if (platform.yVel > 0.0) {
                    ++this.yPos;
                }
                this.game.mario.jumpStopped(true);
                if (platform.isOnScreen()) {
                    this.game.audio.play(8);
                }
            }
            else {
                this.yPos = platform.yPos + platformRect.height - this.avoidedCollisionRowsOnTop;
            }
            if (this instanceof FireballFriend) {
                this.yVel = Math.abs(this.yVel);
            }
            else if (platform.yVel < 0.0 && this.yVel < platform.yVel) {
                this.yVel = platform.yVel;
            }
            else if (platform.yVel > 0.0 && this.yVel < platform.yVel) {
                this.yVel = platform.yVel;
            }
        }
        else if (this.xVel < 0.0 && !(this instanceof FireballFriend)) {
            this.xPos = platformRect.x + platformRect.width - this.avoidedCollisionCols;
            if (this instanceof Mario) {
                this.xVel = 0.0;
            }
            else if (this instanceof Enemy) {
                ((Enemy)this).xCollided();
            }
            else if (this instanceof Friend) {
                ((Friend)this).xCollided();
            }
        }
        else if (this.xVel > 0.0 && !(this instanceof FireballFriend)) {
            this.xPos = platformRect.x - this.width + this.avoidedCollisionCols;
            if (this instanceof Mario) {
                this.xVel = 0.0;
            }
            else if (this instanceof Enemy) {
                ((Enemy)this).xCollided();
            }
            else if (this instanceof Friend) {
                ((Friend)this).xCollided();
            }
        }
    }
    
    public void checkForCollisions(final boolean yOnly) {
        this.finalizePosition();
        final int xTile = (int)(this.xPos / 8.0);
        final int yTile = (int)(this.yPos / 8.0);
        for (int i = 0; i <= this.tilesWidth; ++i) {
            for (int j = this.tilesHeight; j >= 0; --j) {
                if (xTile + i < this.game.level.xTiles && xTile + i >= 0 && yTile + j < Game.yTiles && yTile + j >= 0) {
                    final Tile tempTile = this.game.level.tiles[yTile + j][xTile + i];
                    if (tempTile.solid && !tempTile.disabled) {
                        final Rectangle tempRect = tempTile.getRectangle();
                        final Rectangle spriteRect = this.getRectangle();
                        if (tempRect.intersects(spriteRect)) {
                            final boolean aboveTwoSolid = this.aboveTwoSolid(xTile + i, yTile + j);
                            this.resolveCollision(spriteRect, tempTile, aboveTwoSolid, yOnly);
                        }
                    }
                }
            }
        }
    }
    
    private boolean aboveTwoSolid(final int xTile, final int yTile) {
        return yTile != 0 && ((yTile == 1 && !this.game.level.tiles[yTile - 1][xTile].isPermeableTile()) || (yTile >= 2 && (!this.game.level.tiles[yTile - 1][xTile].isPermeableTile() || !this.game.level.tiles[yTile - 2][xTile].isPermeableTile())));
    }
    
    private void resolveCollision(final Rectangle spriteRect, final Tile tile, final boolean aboveTwoSolid, final boolean yOnly) {
        if (tile.isUnexposedHiddenTile()) {
            if (this instanceof Mario && this.yVel < 0.0 && spriteRect.y > tile.getRectangle().y) {
                this.bumpedAtTile(tile);
            }
            else if (this instanceof Shelled && ((Shelled)this).isLaunched() && tile.sprite.isOnScreen()) {
                tile.bumped(this);
            }
        }
        else if (yOnly) {
            this.resolveYCollision(spriteRect, tile, aboveTwoSolid);
        }
        else if (!(this instanceof Bowser)) {
            this.resolveXCollision(tile);
        }
        this.finalizePosition();
    }
    
    private void resolveYCollision(final Rectangle spriteRect, final Tile tile, final boolean aboveTwoSolid) {
        final Rectangle tileRect = tile.getRectangle();
        final double yDelta = spriteRect.y + spriteRect.height - (this.lastY + this.height - this.avoidedCollisionRowsOnBottom);
        double continuousCollisionOffset = 0.0;
        if (yDelta > 1.0 && this instanceof Mario) {
            continuousCollisionOffset = yDelta - 1.0;
        }
        if (!aboveTwoSolid && spriteRect.y + spriteRect.height - continuousCollisionOffset < tileRect.y + tileRect.height && (this.yVel >= 232.0 || (Math.abs(this.xVel) >= 96.0 && this.lastY <= this.y) || (this.lastX + this.width - this.avoidedCollisionCols > tileRect.x && this.lastX + this.avoidedCollisionCols < tileRect.x + tileRect.width))) {
            if (this instanceof Mario) {
                if (!this.game.mario.willBePinchedOnTile(tile)) {
                    this.yPos = tileRect.y - 8 * this.tilesHeight + this.avoidedCollisionRowsOnBottom;
                    this.yVel = 0.0;
                }
            }
            else if (this instanceof Enemy || this instanceof Friend) {
                if (this.isOnOneTile()) {
                    if (this.xVel > 0.0 && this.lastX + this.width - this.avoidedCollisionCols <= tile.xTile * 8) {
                        return;
                    }
                    if (this.xVel < 0.0 && this.lastX + this.avoidedCollisionCols >= tile.xTile * 8 + 8) {
                        return;
                    }
                }
                this.yPos = tileRect.y - 8 * this.tilesHeight + this.avoidedCollisionRowsOnBottom;
                this.yVel = 0.0;
            }
        }
        else if (spriteRect.y > tileRect.y && this.yVel < 0.0 && this.lastX + this.width - this.avoidedCollisionCols > tileRect.x && this.lastX + this.avoidedCollisionCols < tileRect.x + tileRect.width) {
            if (tile.yTile >= Game.yTiles - 1 || this.game.level.tiles[tile.yTile + 1][tile.xTile].isPermeableTile()) {
                if (this instanceof Mario) {
                    final boolean movingOver = this.game.mario.moveOverTest(tile);
                    if (!movingOver) {
                        this.bumpedAtTile(tile);
                    }
                }
                else if (!(this instanceof Bowser) || !(tile.sprite instanceof Block)) {
                    this.yVel = 0.0;
                    this.yPos = tileRect.y + tileRect.height - this.avoidedCollisionRowsOnTop;
                }
            }
        }
    }
    
    private void bumpedAtTile(final Tile tile) {
        this.yPos = tile.getRectangle().y + tile.getRectangle().height;
        this.game.mario.jumpStopped(true);
        if (!this.game.mario.stateHeightHalved) {
            this.yPos -= this.game.mario.avoidedCollisionRowsOnTop;
        }
        else {
            this.yPos -= this.height / 2;
        }
        this.yVel = 64.0;
        final Tile bumpedTile = this.game.mario.getTileToBump(tile);
        bumpedTile.bumped(this);
    }
    
    private void resolveXCollision(final Tile tile) {
        final Rectangle tileRect = tile.getRectangle();
        if (this.xVel + this.platformVel < 0.0) {
            if (tile.xTile < this.game.level.xTiles - this.tilesWidth && this.game.level.tiles[tile.yTile][tile.xTile + 1].isPermeableTile() && this.game.level.tiles[tile.yTile][tile.xTile + 2].isPermeableTile()) {
                this.xPos = tileRect.x + tileRect.width - this.avoidedCollisionCols;
                if (this instanceof Mario) {
                    this.game.mario.unignoreTilesAfterForcedMotion();
                    this.xVel = 0.0;
                }
                else if (this instanceof Enemy) {
                    ((Enemy)this).xCollided();
                    if (this instanceof Shelled && ((Shelled)this).isLaunched()) {
                        if (tile.yTile > 0 && this.game.level.tiles[tile.yTile - 1][tile.xTile].solid && this.yPos < tile.yTile * 8) {
                            this.game.level.tiles[tile.yTile - 1][tile.xTile].bumped(this);
                        }
                        else {
                            tile.bumped(this);
                        }
                    }
                }
                else if (this instanceof FireballFriend) {
                    final FireballFriend fb = (FireballFriend)this;
                    if (!this.aboveTileSolid(tile) && fb.yPos + fb.height < tile.yTile * 8 + 4) {
                        fb.yPos = tile.yTile * 8 - fb.height;
                        fb.xPos = tile.xTile * 8 + 8 - fb.width;
                        fb.bounce();
                    }
                    else {
                        fb.xCollided();
                    }
                }
                else if (this instanceof Friend) {
                    ((Friend)this).xCollided();
                }
            }
        }
        else if (this.xVel + this.platformVel > 0.0 && tile.xTile >= this.tilesWidth && this.game.level.tiles[tile.yTile][tile.xTile - 1].isPermeableTile() && this.game.level.tiles[tile.yTile][tile.xTile - 2].isPermeableTile()) {
            this.xPos = tileRect.x - 8 * this.tilesWidth + this.avoidedCollisionCols;
            if (this instanceof Mario) {
                this.game.mario.unignoreTilesAfterForcedMotion();
                this.xVel = 0.0;
            }
            else if (this instanceof Enemy) {
                ((Enemy)this).xCollided();
                if (this instanceof Shelled && ((Shelled)this).isLaunched()) {
                    if (tile.yTile > 0 && this.game.level.tiles[tile.yTile - 1][tile.xTile].solid && this.yPos < tile.yTile * 8) {
                        this.game.level.tiles[tile.yTile - 1][tile.xTile].bumped(this);
                    }
                    else {
                        tile.bumped(this);
                    }
                }
            }
            else if (this instanceof FireballFriend) {
                final FireballFriend fb = (FireballFriend)this;
                if (!this.aboveTileSolid(tile) && fb.yPos + fb.height < tile.yTile * 8 + 4) {
                    fb.yPos = tile.yTile * 8 - fb.height;
                    fb.xPos = tile.xTile * 8;
                    fb.bounce();
                }
                else {
                    fb.xCollided();
                }
            }
            else if (this instanceof Friend) {
                ((Friend)this).xCollided();
            }
        }
    }
    
    private boolean aboveTileSolid(final Tile tile) {
        return tile.yTile > 0 && !this.game.level.tiles[tile.yTile - 1][tile.xTile].isPermeableTile();
    }
    
    public void checkForUnsettledTiles() {
        final Rectangle rect = this.getRectangle();
        if (rect.y + rect.height - this.avoidedCollisionRowsOnBottom + 1 >= Game.renderHeight) {
            return;
        }
        if (rect.y + rect.height - this.avoidedCollisionRowsOnBottom + 1 < 0) {
            return;
        }
        int minX = rect.x + this.avoidedCollisionCols;
        if (minX < 0) {
            minX = 0;
        }
        int maxX = rect.x + rect.width - this.avoidedCollisionCols * 2;
        if (maxX > this.game.level.xTiles * 8) {
            maxX = this.game.level.xTiles * 8;
        }
        int i = minX;
        while (i < maxX) {
            final Tile tempTile = this.game.level.getTileAtPixel(i, rect.y + rect.height - this.avoidedCollisionRowsOnBottom + 1);
            if (tempTile.sprite != null && !tempTile.sprite.settled && tempTile.sprite.justHit) {
                if (this instanceof Enemy) {
                    ((Enemy)this).bumpKilled(this.game.mario);
                    break;
                }
                if (this instanceof Friend) {
                    ((Friend)this).bumped();
                    break;
                }
                break;
            }
            else {
                ++i;
            }
        }
    }
    
    public boolean isGrounded() {
        if (this.onPlatform() != null) {
            return true;
        }
        final Rectangle rect = this.getRectangle();
        if (rect.y + rect.height + 1 >= Game.renderHeight) {
            return false;
        }
        if (rect.y + rect.height < 0) {
            return false;
        }
        int minX = rect.x;
        if (minX < 0) {
            minX = 0;
        }
        int maxX = rect.x + rect.width;
        if (maxX >= this.game.level.xTiles * 8) {
            maxX = this.game.level.xTiles * 8 - 1;
        }
        for (int i = minX; i < maxX; ++i) {
            final Tile tempTile = this.game.level.getTileAtPixel(i, rect.y + rect.height + 1);
            if (!tempTile.isPermeableTile()) {
                return true;
            }
        }
        return false;
    }
    
    public Platform onPlatform() {
        final Rectangle spriteRect = this.getRectangle();
        spriteRect.height += 4;
        for (final Platform platform : this.game.level.platforms) {
            if (platform.getRectangle().intersects(spriteRect) && spriteRect.y + spriteRect.height < platform.y + platform.height && (platform.type != 13 || this.yVel >= 0.0)) {
                return platform;
            }
        }
        return null;
    }
    
    public boolean isOnOneTile() {
        final int yRow = this.y + this.height - this.avoidedCollisionRowsOnBottom + 1;
        if (yRow >= Game.renderHeight) {
            return false;
        }
        int startingX = this.x + this.avoidedCollisionCols;
        int endingX = this.x + this.width - this.avoidedCollisionCols;
        if (startingX < 0) {
            startingX = 0;
        }
        if (endingX >= this.game.level.xTiles * 8) {
            endingX = this.game.level.xTiles * 8 - 1;
        }
        Tile markedTile = null;
        for (int i = startingX; i <= endingX; ++i) {
            final Tile testedTile = this.game.level.getTileAtPixel(i, yRow);
            if (!testedTile.isPermeableTile()) {
                if (markedTile != null && testedTile != markedTile) {
                    return false;
                }
                markedTile = testedTile;
            }
        }
        return markedTile != null;
    }
    
    public boolean isBetweenTwoSolidTiles() {
        if (this.x <= 8 || this.x + this.width > this.game.level.xTiles * 8 - 8) {
            return false;
        }
        boolean leftSideBlocked;
        boolean rightSideBlocked = leftSideBlocked = false;
        int yStart = this.y + this.avoidedCollisionRowsOnTop;
        if (yStart < 0) {
            yStart = 0;
        }
        int yStop = this.y + this.height - this.avoidedCollisionRowsOnBottom;
        if (yStop > Game.renderHeight) {
            yStop = Game.renderHeight;
        }
        for (int i = yStart; i < yStop; ++i) {
            if (!this.game.level.getTileAtPixel(this.x - 4, i).isPermeableTile()) {
                leftSideBlocked = true;
            }
            if (!this.game.level.getTileAtPixel(this.x + this.width + 4, i).isPermeableTile()) {
                rightSideBlocked = true;
            }
        }
        return leftSideBlocked && rightSideBlocked;
    }
    
    public boolean isOnScreen() {
        return this.x + this.width >= this.game.level.leftMostX && this.x <= this.game.level.leftMostX + Game.renderWidth && this.y + this.height >= 0 && this.y < Game.renderHeight;
    }
    
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
}
