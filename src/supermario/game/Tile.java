// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game;

import supermario.game.sprites.blocks.QuestionBox;
import java.awt.Rectangle;
import supermario.game.sprites.misc.Spring;
import supermario.game.sprites.misc.BowserBattle;
import supermario.game.sprites.misc.Flag;
import supermario.game.sprites.misc.Platform;
import supermario.game.interfaces.EnemyHolder;
import supermario.game.interfaces.Friend;
import supermario.game.interfaces.Enemy;
import supermario.game.sprites.misc.Checkpoint;
import supermario.game.sprites.enemies.Squid;
import supermario.game.sprites.enemies.Spiny;
import supermario.game.sprites.enemies.RedFish;
import supermario.game.sprites.enemies.HammerBro;
import supermario.game.sprites.enemies.GrayFish;
import supermario.game.sprites.enemies.Beetle;
import supermario.game.sprites.enemies.Goomba;
import supermario.game.sprites.enemies.Koopa;
import supermario.game.sprites.blocks.SolidTestTile;
import supermario.game.sprites.misc.InfiniteCorridor;
import supermario.game.sprites.blocks.Brick;
import supermario.game.sprites.misc.Cannon;
import supermario.game.sprites.misc.Pipe;
import supermario.game.interfaces.Block;
import supermario.game.sprites.misc.ArrivalVine;
import javax.swing.ImageIcon;

public class Tile
{
    private Game game;
    public Sprite sprite;
    public ImageIcon image;
    public boolean solid;
    public boolean shiftOver;
    public boolean disabled;
    public boolean extendColor;
    public int rootXTile;
    public int rootYTile;
    public boolean removable;
    public int xTile;
    public int yTile;
    
    public Tile(final Game game, final int yTile, final int xTile) {
        this.game = game;
        this.xTile = xTile;
        this.yTile = yTile;
    }
    
    public Tile(final Game game, final int yTile, final int xTile, final ImageIcon image, final boolean shiftOver, final boolean removable) {
        this.game = game;
        this.xTile = xTile;
        this.yTile = yTile;
        this.image = image;
        this.shiftOver = shiftOver;
        this.removable = removable;
    }
    
    public Tile(final Game game, final int yTile, final int xTile, final Sprite sprite) {
        this.game = game;
        this.xTile = xTile;
        this.yTile = yTile;
        this.setSprite(sprite);
    }
    
    public final void setSprite(final Sprite sprite) {
        if (sprite == null) {
            return;
        }
        this.sprite = sprite;
        this.sprite.xTile = this.xTile;
        this.sprite.yTile = this.yTile;
        this.sprite.xPos = this.xTile * 8;
        this.sprite.yPos = this.yTile * 8 + this.sprite.avoidedCollisionRowsOnBottom;
        if (sprite instanceof ArrivalVine) {
            this.sprite.yPos = (Game.yTiles - 2) * 8;
        }
        this.sprite.finalizePosition();
        if (sprite instanceof Block) {
            ((Block)sprite).setNormalYPosition(this.yTile * 8);
        }
    }
    
    public void update(final double delta, final int lastMobileEnemyTile) {
        if (this.sprite != null) {
            if (this.detachableSprite()) {
                if (!this.isMobileEnemy() || this.xTile <= lastMobileEnemyTile) {
                    if (this.sprite instanceof Pipe || this.sprite instanceof Cannon) {
                        this.flagTilesAsColorExtending();
                    }
                    this.sprite.releaseSprite(delta, this);
                    this.sprite = null;
                }
            }
            else if (this.sprite instanceof Block) {
                this.sprite.update(delta);
                if (this.sprite instanceof Brick && !this.sprite.visible) {
                    this.solid = false;
                }
                this.sprite.justHit = false;
            }
            else if (this.sprite instanceof ArrivalVine || this.sprite instanceof InfiniteCorridor || this.sprite instanceof SolidTestTile) {
                this.sprite.update(delta);
            }
        }
    }
    
    private void flagTilesAsColorExtending() {
        boolean applies = true;
        if (this.sprite instanceof Pipe) {
            final int t = ((Pipe)this.sprite).type;
            if (t != 11 && t != 2 && t != 3 && t != 6 && t != 7 && t != 4) {
                applies = false;
            }
        }
        if (applies) {
            for (int i = this.xTile; i < this.xTile + this.sprite.tilesWidth; ++i) {
                for (int j = this.yTile; j < this.yTile + this.sprite.tilesHeight; ++j) {
                    if (j < Game.yTiles && i < this.game.level.tiles[0].length) {
                        this.game.level.tiles[j][i].extendColor = true;
                    }
                }
            }
        }
    }
    
    private boolean isMobileEnemy() {
        return this.sprite != null && (this.sprite instanceof Koopa || this.sprite instanceof Goomba || this.sprite instanceof Beetle || this.sprite instanceof GrayFish || this.sprite instanceof HammerBro || this.sprite instanceof RedFish || this.sprite instanceof Spiny || this.sprite instanceof Squid);
    }
    
    private boolean detachableSprite() {
        return this.sprite instanceof Checkpoint || this.sprite instanceof Enemy || this.sprite instanceof Friend || this.sprite instanceof EnemyHolder || this.sprite instanceof Pipe || this.sprite instanceof Platform || this.sprite instanceof Flag || this.sprite instanceof BowserBattle || this.sprite instanceof Spring;
    }
    
    public void bumped(final Sprite bumper) {
        if (bumper.isOnScreen()) {
            if (this.sprite != null && this.sprite instanceof Block) {
                ((Block)this.sprite).bumped(bumper);
            }
            else {
                this.game.audio.play(8);
            }
        }
    }
    
    public Rectangle getRectangle() {
        return new Rectangle(this.xTile * 8, this.yTile * 8, 8, 8);
    }
    
    public boolean isUnexposedHiddenTile() {
        if (this.sprite != null && this.sprite instanceof QuestionBox) {
            final QuestionBox q = (QuestionBox)this.sprite;
            return q.hidden && !q.exposed;
        }
        return false;
    }
    
    public boolean isPermeableTile() {
        return !this.solid || this.isUnexposedHiddenTile();
    }
    public String toString(){
    	String s = "Tile[x="+xTile+", y="+yTile;
    	if(this.image == this.game.textures.lightGround){
    		s+=", image=lightGround";
    	}else if(this.image == this.game.textures.darkGround){
    		s+=", image=darkGround";
    	}
    	s+="]";
    	return s;
    }
}
