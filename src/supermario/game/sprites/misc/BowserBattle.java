// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.misc;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

import supermario.Utilities;
import supermario.game.AnimatedIcon;
import supermario.game.Game;
import supermario.game.Sprite;
import supermario.game.Tile;
import supermario.game.Warp;
import supermario.game.interfaces.Warpable;
import supermario.game.sprites.blocks.Brick;
import supermario.game.sprites.enemies.Bowser;
import supermario.game.sprites.enemies.LavaBall;
@SuppressWarnings("unused")
public class BowserBattle extends Sprite implements Warpable
{
    public Warp warp;
    private static final int bridgeX = 32;
    private static final int bridgeY = 144;
    private int bridgeCount;
    private int chainX;
    private int chainY;
    private int axeX;
    private int axeY;
    private ImageIcon bowserBridge;
    private ImageIcon bowserChain;
    private ImageIcon lava;
    private ImageIcon stoneGround;
    private ImageIcon fakePrincess;
    private ImageIcon bubby;
    private AnimatedIcon axeImages;
    private int axeIndex;
    public boolean caught;
    public boolean bridgeGone;
    public boolean victoryWalking;
    public boolean showThanks;
    public boolean showBut;
    public boolean lastLevel;
    private String but;
    private String other;
    private boolean chainVisible;
    private int bridgesRemoved;
    private Platform platform;
    public static final int BRIDGE_REMOVE_DELAY = 70;
    public Bowser bowser;
    private char type;
    
    public BowserBattle(final Game game, final char type) {
        super(game, new ImageIcon[] { game.textures.bowserAxe });
        this.bridgeCount = 13;
        this.chainX = 224;
        this.chainY = 136;
        this.axeX = 240;
        this.axeY = 120;
        this.but = String.valueOf(new char[] { 'B', 'U', 'T', ' ', 'O', 'U', 'R', ' ', 'P', 'R', 'I', 'N', 'C', 'E', 'S', 'S', ' ', 'I', 'S', ' ', 'I', 'N' });
        this.other = String.valueOf(new char[] { 'A', 'N', 'O', 'T', 'H', 'E', 'R', ' ', 'C', 'A', 'S', 'T', 'L', 'E' });
        this.type = type;
        this.width = 528;
        this.bowserBridge = game.textures.bowserBridge;
        this.bowserChain = game.textures.bowserChain;
        this.lava = game.textures.lavaClassic;
        this.stoneGround = game.textures.stoneGround;
        this.fakePrincess = game.textures.fakePrincess;
        this.bubby = game.textures.bubby;
        this.axeImages =(AnimatedIcon) game.textures.bowserAxe;//new ImageIcon[] { game.textures.bowserAxe1, game.textures.bowserAxe2, game.textures.bowserAxe3 };
        this.chainVisible = true;
    }
    
    @Override
    public Sprite reset() {
        return null;
    }
    
    private boolean throwsHammers() {
        return this.type == '\u00df' || this.type == '\u00f0' || this.type == '\u00ff';
    }
    
    public void activate() {
        this.game.level.bowserBattleOngoing = true;
        this.game.mario.bowserBattle = this;
        this.bowser = new Bowser(this.game, this.game.textures.getBowserTextures(), this, this.throwsHammers());
        this.bowser.xPos = this.x + 32 + this.bridgeCount * 8;
        this.bowser.yPos = 144 - this.bowser.height + this.bowser.avoidedCollisionRowsOnBottom;
        this.bowser.normalX = this.bowser.xPos;
        this.bowser.finalizePosition();
        this.game.level.sprites.add(this.bowser);
        if (this.warp.destLevelNumber == 999) {
            this.lastLevel = true;
        }
        this.populateBattleByType();
    }
    
    private void populateBattleByType() {
        if (this.type == '\u2666' || this.type == '¼' || this.type == '\u00d7' || this.type == '\u00de' || this.type == '\u00df') {
            this.platform = new Platform(this.game, new ImageIcon[] { this.game.textures.platformShort }, 8, false);
            this.platform.xPos = this.x + 144;
            this.platform.yPos = 88.0;
            this.platform.finalizePosition();
            this.platform.activate();
        }
        if (this.type == '¼' || this.type == '\u00de') {
            for (int i = this.x / 8 + 4; i < this.x / 8 + 12 + 4; i += 2) {
                final Sprite brick = new Brick(this.game, this.game.textures.getBrickTextures(), 2, 0);
                this.game.level.tiles[9][i] = new Tile(this.game, 9, i, brick);
                for (int j = i; j < i + 2; ++j) {
                    for (int k = 9; k < 11; ++k) {
                        this.game.level.tiles[k][j].solid = true;
                        this.game.level.tiles[k][j].sprite = brick;
                    }
                }
            }
        }
        if (this.type == '\u00d7') {
            for (int h = 5; h < 11; h += 2) {
                for (int l = this.x / 8 + 4 + 8; l < this.x / 8 + 4 + 8 + 4; l += 2) {
                    final Sprite brick2 = new Brick(this.game, this.game.textures.getBrickTextures(), 2, 0);
                    this.game.level.tiles[h][l] = new Tile(this.game, h, l, brick2);
                    for (int m = l; m < l + 2; ++m) {
                        for (int k2 = h; k2 < h + 2; ++k2) {
                            this.game.level.tiles[k2][m].solid = true;
                            this.game.level.tiles[k2][m].sprite = brick2;
                        }
                    }
                }
            }
        }
        if (this.type == '\u00d8') {
            final Firebar firebar = new Firebar(this.game, new ImageIcon[] { this.game.textures.lightMetal }, true, false, false);
            firebar.visible = false;
            this.game.level.tiles[19][this.x / 8 + 8] = new Tile(this.game, 19, this.x / 8 + 8, firebar);
        }
        if (this.type == '\u00d8' || this.type == '\u00de' || this.type == '\u00df' || this.type == '\u00f0') {
            final LavaBall lavaBall = new LavaBall(this.game, new ImageIcon[] { this.game.textures.lavaball1, this.game.textures.lavaball2 }, 0);
            this.game.level.tiles[Game.yTiles - 2][this.x / 8 + 13] = new Tile(this.game, Game.yTiles - 2, this.x / 8 + 13, lavaBall);
        }
    }
    
    @Override
    public void update(final double delta) {
        this.ticks += delta;
        this.axeIndex = Utilities.getPulsingImageIndex();
        if (this.caught) {
            this.bowser.update(delta);
            if (this.spriteState == 1) {
                if (this.ticks > 70.0) {
                    if (this.chainVisible) {
                        this.chainVisible = false;
                    }
                    else {
                        this.game.audio.play(6);
                        ++this.bridgesRemoved;
                        if (!this.bowser.bumpKilled) {
                            this.bowser.changeFooting();
                        }
                    }
                    this.ticks -= 70.0;
                    if (this.bridgesRemoved > this.bridgeCount) {
                        if (!this.bowser.bumpKilled) {
                            this.bowser.collidable = false;
                        }
                        this.bridgeGone = true;
                        if (!this.bowser.bumpKilled) {
                            this.game.audio.play(17);
                        }
                    }
                }
            }
            else if (this.spriteState == 6) {
                if (this.game.level.maxCameraPan()) {
                    this.showThanks = true;
                    this.ticks = 0.0;
                    this.spriteState = 7;
                }
            }
            else if (this.spriteState == 7 && this.ticks > 2500.0) {
                this.showBut = true;
                this.ticks = 0.0;
                this.spriteState = 8;
            }
        }
    }
    
    @Override
    public void draw(final Graphics2D g2D) {
        if (this.type != '\u00d8' && this.type != '\u00f0') {
            this.transform.setToIdentity();
            this.transform.translate(this.x, 24.0);
            for (int i = 0; i < 3; ++i) {
                g2D.drawImage(this.stoneGround.getImage(), this.transform, null);
                this.transform.translate(16.0, 0.0);
                g2D.drawImage(this.stoneGround.getImage(), this.transform, null);
                this.transform.translate(-16.0, 0.0);
                this.transform.translate(0.0, 16.0);
            }
        }
        this.transform.setToIdentity();
        this.transform.translate(this.x, 152.0);
        for (int i = 0; i < 5; ++i) {
            g2D.drawImage(this.stoneGround.getImage(), this.transform, null);
            this.transform.translate(16.0, 0.0);
            g2D.drawImage(this.stoneGround.getImage(), this.transform, null);
            this.transform.translate(-16.0, 0.0);
            this.transform.translate(0.0, 16.0);
        }
        this.transform.setToIdentity();
        this.transform.translate(this.x + 16, 0.0);
        for (int i = 0; i < this.bridgeCount; ++i) {
            this.transform.translate(16.0, 0.0);
            this.transform.translate(0.0, 24.0);
            g2D.drawImage(this.stoneGround.getImage(), this.transform, null);
            this.transform.translate(0.0, 128.0);
            if (this.bridgeCount - this.bridgesRemoved >= i) {
                g2D.drawImage(this.bowserBridge.getImage(), this.transform, null);
            }
            this.transform.translate(0.0, 48.0);
            g2D.drawImage(this.lava.getImage(), this.transform, null);
            this.transform.translate(8.0, 0.0);
            g2D.drawImage(this.lava.getImage(), this.transform, null);
            this.transform.translate(-8.0, 0.0);
            this.transform.translate(0.0, -200.0);
        }
        this.transform.setToIdentity();
        this.transform.translate(this.x + 240, 24.0);
        g2D.drawImage(this.stoneGround.getImage(), this.transform, null);
        this.transform.translate(16.0, 0.0);
        for (int i = 0; i < 4; ++i) {
            g2D.drawImage(this.stoneGround.getImage(), this.transform, null);
            this.transform.translate(16.0, 0.0);
            g2D.drawImage(this.stoneGround.getImage(), this.transform, null);
            this.transform.translate(-16.0, 0.0);
            this.transform.translate(0.0, 16.0);
        }
        this.transform.setToIdentity();
        this.transform.translate(this.x + 240, 136.0);
        for (int i = 0; i < 6; ++i) {
            g2D.drawImage(this.stoneGround.getImage(), this.transform, null);
            this.transform.translate(16.0, 0.0);
            g2D.drawImage(this.stoneGround.getImage(), this.transform, null);
            this.transform.translate(16.0, 0.0);
            g2D.drawImage(this.stoneGround.getImage(), this.transform, null);
            this.transform.translate(-32.0, 0.0);
            this.transform.translate(0.0, 16.0);
        }
        if (this.caught) {
            this.transform.setToIdentity();
            this.transform.translate(this.x + 288, 24.0);
            for (int i = 0; i < Game.xTiles; ++i) {
                g2D.drawImage(this.stoneGround.getImage(), this.transform, null);
                this.transform.translate(0.0, 176.0);
                g2D.drawImage(this.stoneGround.getImage(), this.transform, null);
                this.transform.translate(0.0, 16.0);
                g2D.drawImage(this.stoneGround.getImage(), this.transform, null);
                this.transform.translate(16.0, -192.0);
            }
            this.transform.setToIdentity();
            this.transform.translate(this.x + 416 + 1, 177.0);
            if (!this.lastLevel) {
                g2D.drawImage(this.fakePrincess.getImage(), this.transform, null);
            }
            else {
                g2D.drawImage(this.bubby.getImage(), this.transform, null);
            }
        }
        if (this.chainVisible) {
            this.transform.setToIdentity();
            this.transform.translate(this.x + this.chainX, this.chainY);
            g2D.drawImage(this.bowserChain.getImage(), this.transform, null);
        }
        if (!this.caught) {
            this.transform.setToIdentity();
            this.transform.translate(this.x + this.axeX, this.axeY);
            g2D.drawImage(this.axeImages.getImage(), this.transform, null);
        }
        else if (this.showThanks && !this.lastLevel) {
            final AffineTransform oldTransform = g2D.getTransform();
            this.transform.setToIdentity();
            g2D.setTransform(this.transform);
            String thanks = "THANK YOU MARIO";
            if (this.game.mario.asLuigi) {
                thanks = "THANK YOU LUIGI";
            }
            Utilities.drawTextAtTiles(g2D, thanks, 8, 9);
            Utilities.drawAtTile(g2D, this.game.textures.symbols.get('\ufffc').getImage(), 23, 9);
            if (this.showBut) {
                Utilities.drawTextAtTiles(g2D, this.but, 5, 12);
                Utilities.drawTextAtTiles(g2D, this.other, 5, 14);
                Utilities.drawAtTile(g2D, this.game.textures.symbols.get('\ufffc').getImage(), 19, 14);
            }
            g2D.setTransform(oldTransform);
        }
    }
    
    @Override
    public Rectangle getRectangle() {
        return new Rectangle(this.x + this.axeX, this.axeY, 16, 16);
    }
    
    @Override
    public void setWarp(final Warp warp) {
        this.warp = warp;
    }
}
