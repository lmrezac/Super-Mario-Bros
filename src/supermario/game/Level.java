// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.ImageIcon;

import supermario.Utilities;
import supermario.game.interfaces.Effect;
import supermario.game.interfaces.Enemy;
import supermario.game.interfaces.EnemyHolder;
import supermario.game.interfaces.Friend;
import supermario.game.interfaces.Shelled;
import supermario.game.sprites.Mario;
import supermario.game.sprites.effects.BumpCoin;
import supermario.game.sprites.enemies.Beetle;
import supermario.game.sprites.enemies.Bowser;
import supermario.game.sprites.enemies.Bullet;
import supermario.game.sprites.enemies.Chomper;
import supermario.game.sprites.enemies.FireballEnemy;
import supermario.game.sprites.enemies.FlameBreath;
import supermario.game.sprites.enemies.Goomba;
import supermario.game.sprites.enemies.GrayFish;
import supermario.game.sprites.enemies.Hammer;
import supermario.game.sprites.enemies.HammerBro;
import supermario.game.sprites.enemies.Koopa;
import supermario.game.sprites.enemies.LavaBall;
import supermario.game.sprites.enemies.PoisonMushroom;
import supermario.game.sprites.enemies.RedFish;
import supermario.game.sprites.enemies.Spiny;
import supermario.game.sprites.enemies.SpinyThrower;
import supermario.game.sprites.enemies.Squid;
import supermario.game.sprites.friends.Coin;
import supermario.game.sprites.friends.FireballFriend;
import supermario.game.sprites.misc.ArrivalVine;
import supermario.game.sprites.misc.Beanstalk;
import supermario.game.sprites.misc.BowserBattle;
import supermario.game.sprites.misc.Checkpoint;
import supermario.game.sprites.misc.Flag;
import supermario.game.sprites.misc.Pipe;
import supermario.game.sprites.misc.Platform;
import supermario.game.sprites.misc.Pulley;
import supermario.game.sprites.misc.Spring;
import supermario.game.sprites.throwers.BulletThrower;
import supermario.game.sprites.throwers.FishThrower;
import supermario.game.sprites.throwers.FlameThrower;

public final class Level
{
    private Game game;
    public Tile[][] tiles;
    public int xTiles;
    public int leftMostTile;
    public LinkedList<Tile> backgroundItemsPending;
    public LinkedList<Sprite> sprites;
    public LinkedList<Pipe> pipes;
    public LinkedList<Pulley> pulleys;
    public LinkedList<Platform> platforms;
    public LinkedList<Platform> platformsToAdd;
    public LinkedList<Spring> springs;
    public LinkedList<Enemy> enemies;
    public LinkedList<Effect> effects;
    public LinkedList<Checkpoint> checkpoints;
    public LinkedList<Sprite> spritesToAdd;
    public LinkedList<Effect> effectsToAdd;
    public LinkedList<Tile> tilesToRemove;
    public LinkedList<FireballFriend> fireballFriends;
    private LinkedList<Sprite> priority1;
    private LinkedList<Sprite> priority2;
    private LinkedList<Sprite> priority3;
    private LinkedList<Sprite> priority4;
    private LinkedList<Sprite> priority5;
    private LinkedList<Sprite> priority6;
    private AffineTransform transform;
    private Color backgroundColor;
    public int shadowType;
    public int leftMostX;
    public double exactLeftMostX;
    public double levelTime;
    public boolean timedLevel;
    public String levelNamePart1;
    public String levelNamePart2;
    public String levelAuthor;
    public int levelType;
    public int pipeType;
    public int levelNumber;
    public int cliffDestLevel;
    public int cliffDestID;
    public static final int LEVEL_TYPE_OUTSIDE = 0;
    public static final int LEVEL_TYPE_UNDERGROUND = 1;
    public static final int LEVEL_TYPE_CASTLE = 2;
    public static final int LEVEL_TYPE_UNDER_WATER = 3;
    public static final int LEVEL_TYPE_OUTSIDE_NIGHT = 4;
    public static final int LEVEL_TYPE_COIN_ZONE_DAY = 5;
    public static final int LEVEL_TYPE_COIN_ZONE_NIGHT = 6;
    public static final int LEVEL_TYPE_GHOST_HOUSE = 7;
    public static final int TILES_OFF_SCREEN_TO_UPDATE_ON_LEFT = 19;
    public static final int TILES_OFF_SCREEN_TO_UPDATE_ON_RIGHT = 19;
    public static final int TILES_OFF_SCREEN_TO_UPDATE_ON_RIGHT_FOR_MOBILE_ENEMIES = 5;
    public static final int SPRITE_X_OFFSCREEN_LIMIT_ON_LEFT;
    public static final int SPRITE_X_OFFSCREEN_LIMIT_ON_RIGHT;
    public static final int DEATH_ON_CLIFF = -1;
    public static final int TIME_CRISIS_THRESHOLD = 30;
    public static final int SHADOW_COLOR_LIGHT = 0;
    public static final int SHADOW_COLOR_DARK = 1;
    public static final int SHADOW_COLOR_STONE = 2;
    public static final char TYPE_GREEN_PIPES = '\0';
    public static final char TYPE_WHITE_PIPES = '\u0001';
    public static final char TYPE_BLUE_PIPES = '\u0002';
    public static final char TYPE_ORANGE_PIPES = '\u0003';
    public static final int TEXTURES_STANDARD = 0;
    public static final int TEXTURES_LOST_LEVELS = 1;
    public static double TERMINAL_VELOCITY_WATER;
    public static double WATER_GRAVITY;
    public static double TERMINAL_VELOCITY;
    public static double GRAVITY;
    public static double AUTO_SCROLL_VEL;
    public static int MOUSE_OPTION_NONE;
    public static int MOUSE_OPTION_RESUME;
    public static int MOUSE_OPTION_FULLSCREEN;
    public static int MOUSE_OPTION_CONTROLS;
    public static int MOUSE_OPTION_MUTE;
    public static int MOUSE_OPTION_RETRY_LVL;
    public static int MOUSE_OPTION_RETRY_CHK;
    public static int MOUSE_OPTION_QUIT;
    public int mouseHoveredIndex;
    public boolean startReleased;
    public boolean paused;
    private int errorCode;
    public int maxTravelX;
    public static final int VALID_LEVEL = -1;
    public double timeSincePausePress;
    public static final int PAUSE_REPEAT_MINIMUM_TIME = 100;
    public boolean setToDisable;
    public boolean disable;
    public boolean bowserBattleOngoing;
    public boolean hasSpinyThrower;
    public boolean hasFlyingFish;
    public boolean levelEndPresent;
    public boolean hasBulletThrower;
    public boolean hasFlameThrower;
    public boolean blackAndWhite;
    private FishThrower fishThrower;
    private BulletThrower bulletThrower;
    private FlameThrower flameThrower;
    public boolean caughtSmallCastleFlag;
    public boolean caughtBigCastleFlag;
    public boolean autoScrollingLevel;
    
    public Level(final Game game, final int levelNumber, final int startingWarpID) {
        this.transform = new AffineTransform();
        this.game = game;
        this.levelNumber = levelNumber;
        this.backgroundItemsPending = new LinkedList<Tile>();
        this.sprites = new LinkedList<Sprite>();
        this.enemies = new LinkedList<Enemy>();
        this.effects = new LinkedList<Effect>();
        this.effectsToAdd = new LinkedList<Effect>();
        this.tilesToRemove = new LinkedList<Tile>();
        this.spritesToAdd = new LinkedList<Sprite>();
        this.fireballFriends = new LinkedList<FireballFriend>();
        this.checkpoints = new LinkedList<Checkpoint>();
        this.pulleys = new LinkedList<Pulley>();
        this.platforms = new LinkedList<Platform>();
        this.platformsToAdd = new LinkedList<Platform>();
        this.springs = new LinkedList<Spring>();
        this.priority1 = new LinkedList<Sprite>();
        this.priority2 = new LinkedList<Sprite>();
        this.priority3 = new LinkedList<Sprite>();
        this.priority4 = new LinkedList<Sprite>();
        this.priority5 = new LinkedList<Sprite>();
        this.priority6 = new LinkedList<Sprite>();
        this.errorCode = this.loadLevel(levelNumber, startingWarpID);
    }
    
    public void pause() {
        this.startReleased = false;
        if (this.game.mario.isDead() || this.timeSincePausePress < 100.0 || this.game.mario.transitioning) {
            return;
        }
        this.timeSincePausePress = 0.0;
        this.paused = true;
        this.game.mario.visible = true;
        this.game.audio.play(4);
        this.game.audio.pauseGameSounds();
        Utilities.savePulsingState();
        this.game.settingOptions(false);
    }
    
    public void resume(final boolean playUnpauseAudio) {
        this.startReleased = false;
        if (this.timeSincePausePress < 100.0) {
            return;
        }
        this.timeSincePausePress = 0.0;
        this.paused = false;
        this.game.mario.runKeyReleased = false;
        if (playUnpauseAudio) {
            this.game.audio.play(4);
        }
        this.game.audio.resumeGameSounds();
        Utilities.restorePulsingState();
    }
    
    public void playLevelMusic() {
    	//game.audio.stopMusic(true);
        if (this.game.mario.timedLevel && this.game.mario.playedTimeWarning) {
            if (this.game.mario.loopedFastMusic) {
                this.game.audio.switchToFastMusic(false);
            }
        }
        else if (this.levelType == 0 || this.levelType == 4) {
            this.game.audio.loopMusic(6, false);
        }
        else if (this.levelType == 1) {
            this.game.audio.loopMusic(8, false);
        }
        else if (this.levelType == 2) {
            this.game.audio.loopMusic(9, false);
        }
        else if (this.levelType == 3) {
            this.game.audio.loopMusic(5, false);
        }
        else if (this.levelType == 5 || this.levelType == 6) {
            this.game.audio.loopMusic(4, false);
        }
    }
    
    public void prepare() {
        if (!this.game.mario.hasStar()) {
        	
            this.playLevelMusic();
        }
        Utilities.resetSynchronizedSprites();
        this.game.mario.outOfTimeDeath = false;
        this.startReleased = false;
        this.game.mario.reset(false);
        this.exactLeftMostX = this.game.mario.x - Game.xTiles * 8 / 2;
        if (this.exactLeftMostX < 0.0) {
            this.exactLeftMostX = 0.0;
        }
        this.leftMostX = (int)Math.round(this.exactLeftMostX);
        this.leftMostTile = this.getTileAtPixel(this.leftMostX, 0).xTile;
        if (this.game.level.levelType == 3) {
            this.game.mario.swimming = true;
        }
        else {
            this.game.mario.swimming = false;
        }
        if (this.hasSpinyThrower) {
            this.sprites.add(new SpinyThrower(this.game, this.game.textures.getSpinyThrowerTextures()));
        }
        if (this.hasFlyingFish) {
            this.fishThrower = new FishThrower(this.game);
        }
        if (this.hasBulletThrower) {
            this.bulletThrower = new BulletThrower(this.game, this.shadowType);
        }
        if (this.hasFlameThrower) {
            this.flameThrower = new FlameThrower(this.game);
        }
    }
    
    public Tile getTileAtPixel(final int x, final int y) {
        int tileX = x / 8;
        if (tileX >= this.xTiles) {
            tileX = this.xTiles - 1;
        }
        else if (tileX < 0) {
            tileX = 0;
        }
        final int tileY = y / 8;
        return this.tiles[tileY][tileX];
    }
    
    public Tile getTile(final int x, final int y) {
        return this.tiles[y][x];
    }
    
    public void update(final double delta) {
        if (this.disable) {
            return;
        }
        if (this.timeSincePausePress < 100.0) {
            this.timeSincePausePress += delta;
        }
        if (!this.startReleased && !this.game.input.startDown) {
            this.startReleased = true;
        }
        if (this.game.input.startDown && this.startReleased && !this.paused) {
            this.pause();
        }
        if (this.paused) {
            return;
        }
        if (!this.game.mario.isDead() && (!this.game.mario.transitioning || (this.game.mario.transitionState != 13 && this.game.mario.transitionState != 14))) {
            final Mario mario = this.game.mario;
            mario.totalTime += delta / 1000.0;
            if (!this.game.mario.timedLevel && this.game.mario.totalTime > 9999.0) {
                this.game.mario.totalTime = 9999.0;
            }
            else if (this.game.mario.timedLevel) {
                if (this.game.mario.totalTime > this.game.mario.levelTime) {
                    this.game.mario.died(true, false);
                }
                else if (this.game.mario.totalTime + 30.0 >= this.game.mario.levelTime && !this.game.mario.playedTimeWarning) {
                    this.game.audio.stopMusic(false);
                    this.game.audio.playMusic(10);
                    this.game.mario.playedTimeWarning = true;
                }
                else if (this.game.mario.totalTime + 30.0 - 2.8 > this.game.mario.levelTime && this.game.mario.playedTimeWarning && !this.game.mario.loopedFastMusic) {
                    this.game.audio.switchToFastMusic(true);
                    this.game.mario.loopedFastMusic = true;
                }
            }
        }
        if (this.autoScrollingLevel) {
            this.setCameraMotion(delta);
        }
        this.updateTiles(delta);
        this.updatePlatformsAndPulleys(delta);
        this.updateSprings(delta);
        this.game.mario.update(delta);
        if (this.setToDisable) {
            this.disable = true;
            return;
        }
        if (!this.game.mario.transitioning) {
            if (this.hasFlyingFish) {
                this.fishThrower.update(delta);
            }
            if (this.hasBulletThrower) {
                this.bulletThrower.update(delta);
            }
            if (this.hasFlameThrower) {
                this.flameThrower.update(delta);
            }
        }
        this.updateSprites(delta);
        this.updateFireballFriends(delta);
        if (!this.autoScrollingLevel) {
            this.setCameraMotion(delta);
        }
        if (this.game.mario.collidable && !this.game.mario.transitioning) {
            this.enemies.clear();
            this.checkCollisionsWithMario();
            this.updateCheckpoints(delta);
        }
        this.checkFriendlyFireballCollisionsWithEnemies();
        this.checkCollisionsAmongEnemies();
        this.updateEffects(delta);
        this.updateTiles();
    }
    
    public boolean isFlyingKoopa(final Sprite sprite) {
        if (sprite instanceof Koopa) {
            final Koopa koopa = (Koopa)sprite;
            if (koopa.spriteState == 1 || koopa.spriteState == 2) {
                return true;
            }
        }
        return false;
    }
    
    public void coinEnemies() {
        final Iterator<Sprite> spritesIter = this.sprites.iterator();
        while (spritesIter.hasNext()) {
            final Sprite sprite = spritesIter.next();
            if (sprite instanceof Enemy && !(sprite instanceof Bowser) && !(sprite instanceof FireballEnemy) && !(sprite instanceof FlameBreath)) {
                spritesIter.remove();
                if (sprite.isOnScreen() && sprite.collidable) {
                    this.effectsToAdd.add(new BumpCoin(this.game, sprite));
                    final Mario mario = this.game.mario;
                    ++mario.coins;
                    final Mario mario2 = this.game.mario;
                    mario2.points += 200;
                }
                sprite.visible = false;
            }
            else {
                if (!(sprite instanceof FireballEnemy) && !(sprite instanceof FlameBreath)) {
                    continue;
                }
                spritesIter.remove();
            }
        }
    }
    
    private void setCameraMotion(final double delta) {
        if (this.game.mario.transitioning && this.game.mario.transitionState == 13) {
            if (!this.maxCameraPan()) {
                this.exactLeftMostX += 40.0 * delta / 1000.0;
                this.leftMostX = (int)Math.round(this.exactLeftMostX);
            }
        }
        else if (this.game.mario.transitioning && this.game.mario.transitionState == 14) {
            if (this.game.mario.bowserBattle.victoryWalking && !this.maxCameraPan()) {
                this.exactLeftMostX += Mario.MAX_WALKING_SPEED * delta / 1000.0;
                this.leftMostX = (int)Math.round(this.exactLeftMostX);
            }
        }
        else if (this.autoScrollingLevel) {
            if (!this.paused && !this.game.mario.transitioning) {
                this.exactLeftMostX += Level.AUTO_SCROLL_VEL * delta / 1000.0;
                this.leftMostX = (int)Math.round(this.exactLeftMostX);
                this.leftMostTile = this.getTileAtPixel(this.leftMostX, 0).xTile;
            }
        }
        else if (this.game.mario.getXCenter() > this.leftMostX + Game.xTiles * 8 / 2) {
            if (this.game.mario.transitioning && this.game.mario.transitionState == 16) {
                this.exactLeftMostX = this.game.mario.getXCenter() - Game.xTiles * 8 / 2 + 24;
                this.leftMostX = (int)Math.round(this.exactLeftMostX);
            }
            else {
                this.leftMostX = this.game.mario.getXCenter() - Game.xTiles * 8 / 2;
                if (this.leftMostX < 0) {
                    this.leftMostX = 0;
                }
                this.leftMostTile = this.getTileAtPixel(this.leftMostX, 0).xTile;
                this.exactLeftMostX = this.leftMostX;
            }
        }
        else if (this.game.mario.lastX < (int)Math.round(this.game.mario.xPos) && !this.game.mario.transitioning && this.game.mario.pixelsToMove == 0.0 && this.game.mario.xVel > 0.0 && this.game.mario.getXCenter() > this.leftMostX + Game.renderWidth / 3) {
            this.exactLeftMostX += this.game.mario.xVel * delta / 1000.0 * 0.5;
            this.leftMostX = (int)Math.round(this.exactLeftMostX);
            if (this.leftMostX < 0) {
                this.leftMostX = 0;
            }
            this.leftMostTile = this.getTileAtPixel(this.leftMostX, 0).xTile;
        }
        if (this.bowserBattleOngoing && this.leftMostX > this.tiles[0].length * 8 - Game.renderWidth * 2 - 24) {
            this.exactLeftMostX = this.tiles[0].length * 8 - Game.renderWidth * 2 - 24;
            this.leftMostX = (int)Math.round(this.exactLeftMostX);
            this.leftMostTile = this.getTileAtPixel(this.leftMostX, 0).xTile;
        }
        else if (this.maxCameraPan()) {
            this.leftMostX = this.tiles[0].length * 8 - Game.xTiles * 8;
            this.exactLeftMostX = this.leftMostX;
            this.leftMostTile = this.getTileAtPixel(this.leftMostX, 0).xTile;
        }
    }
    
    public boolean maxCameraPan() {
        return this.leftMostX + Game.xTiles * 8 >= this.tiles[0].length * 8;
    }
    
    private void updatePlatformsAndPulleys(final double delta) {
        while (this.platformsToAdd.size() > 0) {
            this.platforms.add(this.platformsToAdd.removeFirst());
        }
        if (!this.canUpdateInCurrentState()) {
            return;
        }
        final Iterator<Pulley> pulleysIter = this.pulleys.iterator();
        while (pulleysIter.hasNext()) {
            final Pulley tempPulley = pulleysIter.next();
            if (this.shouldRemoveSprite(tempPulley)) {
                if (!this.shouldRemoveSprite(tempPulley.rightChild)) {
                    continue;
                }
                if (!tempPulley.injected) {
                    this.tiles[tempPulley.leftChild.yTile][tempPulley.leftChild.xTile].setSprite(tempPulley.leftChild.reset());
                }
                this.platforms.remove(tempPulley.leftChild);
                this.platforms.remove(tempPulley.rightChild);
                pulleysIter.remove();
            }
            else {
                tempPulley.update(delta);
            }
        }
        final Iterator<Platform> platformsIter = this.platforms.iterator();
        while (platformsIter.hasNext()) {
            final Platform tempPlatform = platformsIter.next();
            if (this.shouldRemoveSprite(tempPlatform) && tempPlatform.type != 14 && tempPlatform.type != 15 && tempPlatform.type != 13) {
                if (!tempPlatform.injected) {
                    this.tiles[tempPlatform.yTile][tempPlatform.xTile].setSprite(tempPlatform.reset());
                }
                platformsIter.remove();
            }
            else {
                tempPlatform.update(delta);
            }
        }
    }
    
    private void updateSprings(final double delta) {
        if (this.game.mario.transitioning) {
            return;
        }
        final Iterator<Spring> springsIter = this.springs.iterator();
        while (springsIter.hasNext()) {
            final Spring tempSpring = springsIter.next();
            if (this.shouldRemoveSprite(tempSpring)) {
                if (!tempSpring.injected) {
                    this.tiles[tempSpring.yTile][tempSpring.xTile].setSprite(tempSpring.reset());
                }
                springsIter.remove();
            }
            else {
                tempSpring.update(delta);
            }
        }
    }
    
    public boolean canUpdateInCurrentState() {
        return !this.game.mario.transitioning || this.game.mario.transitionState == 15 || this.game.mario.transitionState == 16 || this.game.mario.transitionState == 17 || (this.game.mario.transitionState == 3 && this.game.mario.cliffDeath);
    }
    
    private void updateSprites(final double delta) {
        while (!this.spritesToAdd.isEmpty()) {
            this.sprites.add(this.spritesToAdd.remove());
        }
        final Iterator<Sprite> spritesIter = this.sprites.iterator();
        while (spritesIter.hasNext()) {
            final Sprite tempSprite = spritesIter.next();
            if (this.canUpdateInCurrentState() || tempSprite instanceof Coin) {
                if (this.shouldRemoveSprite(tempSprite)) {
                    if (tempSprite.x + tempSprite.width >= this.leftMostX - 19 || this.spritesTileOnScreen(tempSprite)) {
                        continue;
                    }
                    if (!tempSprite.injected) {
                        this.tiles[tempSprite.yTile][tempSprite.xTile].setSprite(tempSprite.reset());
                    }
                    spritesIter.remove();
                }
                else {
                    tempSprite.update(delta);
                    if (this.maxTravelX < 0 || tempSprite.x + tempSprite.width - tempSprite.avoidedCollisionCols <= this.maxTravelX || this.spriteAllowedPastPastMaxX(tempSprite) || !(tempSprite instanceof Enemy)) {
                        continue;
                    }
                    ((Enemy)tempSprite).xCollided();
                    tempSprite.xPos = this.maxTravelX - tempSprite.width + tempSprite.avoidedCollisionCols;
                }
            }
        }
    }
    
    private boolean spritesTileOnScreen(final Sprite tempSprite) {
        final int xTile = tempSprite.xTile;
        return xTile >= this.leftMostTile && xTile <= this.leftMostTile + Game.xTiles + 1;
    }
    
    private boolean spriteAllowedPastPastMaxX(final Sprite tempSprite) {
        return tempSprite instanceof Bowser || tempSprite instanceof LavaBall || tempSprite instanceof FlameBreath || tempSprite instanceof Hammer || tempSprite instanceof Bullet;
    }
    
    private void updateFireballFriends(final double delta) {
        final Iterator<FireballFriend> fbIter = this.fireballFriends.iterator();
        while (fbIter.hasNext()) {
            final FireballFriend fb = fbIter.next();
            if (!this.game.mario.transitioning) {
                fb.update(delta);
                if (fb.visible) {
                    continue;
                }
                fbIter.remove();
            }
        }
    }
    
    private boolean shouldRemoveSprite(final Sprite sprite) {
        return !sprite.visible || (!(sprite instanceof SpinyThrower) && ((sprite.y >= Game.renderHeight && !(sprite instanceof Platform) && !(sprite instanceof FireballEnemy) && !(sprite instanceof LavaBall) && (!(sprite instanceof Koopa) || ((Koopa)sprite).spriteState != 1)) || sprite.x + sprite.width < this.leftMostX - Level.SPRITE_X_OFFSCREEN_LIMIT_ON_LEFT || sprite.x > this.leftMostX + Game.xTiles * 8 + Level.SPRITE_X_OFFSCREEN_LIMIT_ON_RIGHT));
    }
    
    private void updateTiles(final double delta) {
        int tilesToUpdate = Game.xTiles;
        if (this.leftMostX % 8 != 0 && this.leftMostX + Game.xTiles < this.game.level.xTiles) {
            ++tilesToUpdate;
        }
        int firstTileToUpdate = this.leftMostTile - 19;
        if (firstTileToUpdate < 0) {
            firstTileToUpdate = 0;
        }
        int lastTileToUpdate = this.leftMostTile + tilesToUpdate + 19;
        if (lastTileToUpdate > this.xTiles) {
            lastTileToUpdate = this.xTiles;
        }
        final int lastMobileEnemyTileToUpdate = this.leftMostTile + tilesToUpdate + 5;
        for (int i = firstTileToUpdate; i < lastTileToUpdate; ++i) {
            for (int j = 0; j < Game.yTiles; ++j) {
                this.tiles[j][i].update(delta, lastMobileEnemyTileToUpdate);
            }
        }
    }
    
    private void updateEffects(final double delta) {
        while (this.effectsToAdd.size() > 0) {
            this.effects.add(this.effectsToAdd.remove());
        }
        final Iterator<Effect> effectsIter = this.effects.iterator();
        while (effectsIter.hasNext()) {
            final Effect tempEffect = effectsIter.next();
            tempEffect.update(delta);
            if (tempEffect.isFinished()) {
                effectsIter.remove();
            }
        }
    }
    
    private void updateTiles() {
        while (this.tilesToRemove.size() > 0) {
            final Tile tileToRemove = this.tilesToRemove.remove();
            if (tileToRemove.image != null) {
                for (int i = tileToRemove.yTile; i < tileToRemove.yTile + tileToRemove.image.getIconHeight() / 8; ++i) {
                    for (int j = tileToRemove.xTile; j < tileToRemove.xTile + tileToRemove.image.getIconWidth() / 8; ++j) {
                        if (i < this.tiles.length && j < this.tiles[0].length) {
                            final Tile tile = this.tiles[i][j];
                            tile.sprite = null;
                            tile.solid = false;
                            tile.disabled = false;
                        }
                    }
                }
                tileToRemove.image = null;
                tileToRemove.removable = false;
            }
        }
    }
    
    private void updateCheckpoints(final double delta) {
        final Iterator<Checkpoint> checkpointsIter = this.checkpoints.iterator();
        while (checkpointsIter.hasNext()) {
            final Checkpoint tempCheckpoint = checkpointsIter.next();
            tempCheckpoint.update(delta);
            if (tempCheckpoint.isFinished()) {
                checkpointsIter.remove();
            }
            else {
                if (!this.game.mario.getRectangle().intersects(tempCheckpoint.getRectangle())) {
                    continue;
                }
                tempCheckpoint.gotCheckpoint();
            }
        }
    }
    
    private void checkCollisionsWithMario() {
        this.game.mario.resetPendingYMotion();
        for (final Sprite tempSprite : this.sprites) {
            if (tempSprite.collidable && tempSprite.isOnScreen()) {
                final Rectangle marioRect = this.game.mario.getRectangle();
                if (tempSprite instanceof Enemy) {
                    final Enemy tempEnemy = (Enemy)tempSprite;
                    this.enemies.add(tempEnemy);
                    if (!tempEnemy.getSpriteContactRectangle().intersects(marioRect)) {
                        continue;
                    }
                    this.handleEnemyCollision(tempEnemy, marioRect);
                }
                else if (tempSprite instanceof Friend) {
                    if (!((Friend)tempSprite).getContactRectangle().intersects(marioRect)) {
                        continue;
                    }
                    this.handleFriendCollision((Friend)tempSprite);
                }
                else if (tempSprite instanceof Flag) {
                    if (tempSprite.getRectangle().intersects(marioRect)) {
                        final Flag flag = (Flag)tempSprite;
                        if (flag.bigCastle) {
                            this.caughtBigCastleFlag = true;
                        }
                        else {
                            this.caughtSmallCastleFlag = true;
                        }
                        this.game.mario.caughtFlag(flag);
                        return;
                    }
                    continue;
                }
                else if (tempSprite instanceof BowserBattle) {
                    if (tempSprite.getRectangle().intersects(marioRect)) {
                        this.game.mario.caughtAxe((BowserBattle)tempSprite);
                        return;
                    }
                    continue;
                }
                else {
                    if (tempSprite instanceof Beanstalk && tempSprite.getRectangle().intersects(marioRect) && ((Beanstalk)tempSprite).block.y > this.game.mario.y + this.game.mario.height - this.game.mario.avoidedCollisionCols) {
                        this.game.mario.caughtBeanstalk((Beanstalk)tempSprite);
                        return;
                    }
                    continue;
                }
            }
        }
        this.game.mario.setPendingYMotion();
    }
    
    private void handleEnemyCollision(final Enemy enemy, final Rectangle marioRect) {
        final Rectangle enemyRect = enemy.getSpriteContactRectangle();
        if (enemy instanceof Goomba && ((Goomba)enemy).smushed) {
            return;
        }
        if (this.game.mario.hasStar()) {
            if (!(enemy instanceof FireballEnemy) && !(enemy instanceof LavaBall) && !(enemy instanceof FlameBreath) && !(enemy instanceof Hammer)) {
                enemy.bumpKilled(this.game.mario);
            }
        }
        else if (this.isWaterEnemy((Sprite)enemy) || enemy instanceof Spiny || enemy instanceof FireballEnemy || enemy instanceof Chomper || enemy instanceof LavaBall || enemy instanceof FlameBreath || enemy instanceof Hammer) {
            this.game.mario.attacked(false, enemy);
        }
        else if (enemy instanceof Bowser) {
            if (((Bowser)enemy).collision(marioRect)) {
                this.game.mario.attacked(false, enemy);
            }
        }
        else if ((this.game.mario.yVel >= 0.0 || (this.game.mario.yVel < 0.0 && ((Sprite)enemy).yVel < 0.0)) && marioRect.y + marioRect.height < enemyRect.y + enemyRect.height) {
            if (enemy instanceof Shelled) {
                final Shelled shelled = (Shelled)enemy;
                if ((!shelled.isShelled() && !shelled.isLaunched()) || (shelled.isLaunched() && shelled.isDangerous())) {
                    this.game.mario.smushedAnEnemy(enemy);
                }
                enemy.smushed(this.game.mario);
            }
            else if (enemy instanceof PoisonMushroom) {
                this.game.mario.attacked(false, enemy);
            }
            else {
                this.game.mario.smushedAnEnemy(enemy);
                enemy.smushed(this.game.mario);
            }
        }
        else if (enemy instanceof Shelled) {
            final Shelled shelled = (Shelled)enemy;
            if (shelled.isShelled()) {
                enemy.smushed(this.game.mario);
            }
            else if (!shelled.isLaunched() || shelled.isDangerous()) {
                this.game.mario.attacked(false, enemy);
            }
        }
        else {
            this.game.mario.attacked(false, enemy);
        }
    }
    
    private void handleFriendCollision(final Friend friend) {
        if (friend instanceof Coin) {
            if (this.game.mario.pixelsToMove == 0.0 || this.game.mario.getRectangle().y < friend.getContactRectangle().y) {
                friend.absorbed();
            }
        }
        else {
            friend.absorbed();
        }
    }
    
    private void checkCollisionsAmongEnemies() {
        for (final Enemy tempEnemy : this.enemies) {
            final Sprite tempEnemySprite = (Sprite)tempEnemy;
            if (tempEnemySprite.visible && tempEnemySprite.collidable && !this.nonFriendlyFireEnemy(tempEnemySprite)) {
                for (final Enemy subTempEnemy : this.enemies) {
                    final Sprite subTempEnemySprite = (Sprite)subTempEnemy;
                    if (tempEnemySprite != subTempEnemySprite && subTempEnemySprite.visible && subTempEnemySprite.collidable && !this.nonFriendlyFireEnemy(subTempEnemySprite) && tempEnemy.getSpriteContactRectangle().intersects(subTempEnemy.getSpriteContactRectangle())) {
                        this.handleEnemyToEnemyCollision(tempEnemySprite, subTempEnemySprite, tempEnemy, subTempEnemy);
                    }
                }
            }
        }
    }
    
    private boolean nonFriendlyFireEnemy(final Sprite sprite) {
        return sprite instanceof LavaBall || sprite instanceof FlameBreath;
    }
    
    private boolean isWaterEnemy(final Sprite sprite) {
        return sprite instanceof Squid || sprite instanceof GrayFish || (sprite instanceof RedFish && !((RedFish)sprite).flying);
    }
    
    private void handleEnemyToEnemyCollision(final Sprite tempEnemySprite, final Sprite subTempEnemySprite, final Enemy tempEnemy, final Enemy subTempEnemy) {
        if (tempEnemySprite instanceof Bullet || subTempEnemySprite instanceof Bullet) {
            if (tempEnemySprite instanceof Shelled && ((Shelled)tempEnemySprite).isLaunched()) {
                ((Enemy)subTempEnemySprite).bumpKilled(tempEnemySprite);
            }
            else if (subTempEnemySprite instanceof Shelled && ((Shelled)subTempEnemySprite).isLaunched()) {
                ((Enemy)tempEnemySprite).bumpKilled(subTempEnemySprite);
            }
        }
        else if (tempEnemySprite instanceof FireballEnemy || subTempEnemySprite instanceof FireballEnemy) {
            if (!(tempEnemy instanceof Beetle) && !(subTempEnemy instanceof Beetle)) {
                tempEnemy.bumpKilled(subTempEnemySprite);
                subTempEnemy.bumpKilled(tempEnemySprite);
            }
        }
        else if (!(tempEnemySprite instanceof RedFish) && !(subTempEnemySprite instanceof RedFish) && !(tempEnemySprite instanceof Chomper) && !(subTempEnemySprite instanceof Chomper) && !this.isWaterEnemy(tempEnemySprite)) {
            if (!this.isWaterEnemy(subTempEnemySprite)) {
                if (tempEnemySprite instanceof Bowser || subTempEnemySprite instanceof Bowser) {
                    subTempEnemy.bumpKilled(tempEnemySprite);
                    tempEnemy.bumpKilled(subTempEnemySprite);
                }
                else if (tempEnemySprite instanceof Hammer || subTempEnemySprite instanceof Hammer) {
                    tempEnemy.bumpKilled(subTempEnemySprite);
                    subTempEnemy.bumpKilled(tempEnemySprite);
                }
                else if (tempEnemySprite instanceof Shelled && ((Shelled)tempEnemySprite).isLaunched() && subTempEnemySprite instanceof Shelled && ((Shelled)subTempEnemySprite).isLaunched()) {
                    tempEnemy.bumpKilled(subTempEnemySprite);
                    subTempEnemy.bumpKilled(tempEnemySprite);
                }
                else if (tempEnemySprite instanceof Shelled && ((Shelled)tempEnemySprite).isLaunched()) {
                    subTempEnemy.bumpKilled(tempEnemySprite);
                }
                else if (subTempEnemySprite instanceof Shelled && ((Shelled)subTempEnemySprite).isLaunched()) {
                    tempEnemy.bumpKilled(subTempEnemySprite);
                }
                else if (!subTempEnemySprite.grounded && subTempEnemySprite.yPos > subTempEnemySprite.lastY && subTempEnemySprite.y + subTempEnemySprite.height - subTempEnemySprite.avoidedCollisionRowsOnBottom < tempEnemySprite.y + tempEnemySprite.height - tempEnemySprite.avoidedCollisionRowsOnBottom) {
                    if (tempEnemySprite instanceof Shelled) {
                        tempEnemy.bumpKilled(subTempEnemySprite);
                    }
                    else if (tempEnemySprite instanceof Goomba) {
                        tempEnemy.smushed(subTempEnemySprite);
                    }
                    else if (tempEnemySprite instanceof Spiny) {
                        subTempEnemy.bumpKilled(tempEnemySprite);
                    }
                    else if (tempEnemySprite instanceof HammerBro) {
                        tempEnemy.smushed(subTempEnemySprite);
                    }
                }
                else if (!tempEnemySprite.grounded && tempEnemySprite.yPos > tempEnemySprite.lastY && subTempEnemySprite.y + subTempEnemySprite.height - subTempEnemySprite.avoidedCollisionRowsOnBottom > tempEnemySprite.y + tempEnemySprite.height - tempEnemySprite.avoidedCollisionRowsOnBottom) {
                    if (subTempEnemySprite instanceof Shelled) {
                        subTempEnemy.bumpKilled(tempEnemySprite);
                    }
                    else if (subTempEnemySprite instanceof Goomba) {
                        subTempEnemy.smushed(tempEnemySprite);
                    }
                    else if (subTempEnemySprite instanceof Spiny) {
                        tempEnemy.bumpKilled(subTempEnemySprite);
                    }
                    else if (subTempEnemySprite instanceof HammerBro) {
                        subTempEnemy.bumpKilled(tempEnemySprite);
                    }
                }
                else if (!this.isFlyingKoopa(tempEnemySprite)) {
                    if (!this.isFlyingKoopa(subTempEnemySprite)) {
                        if (tempEnemySprite.x < subTempEnemySprite.x) {
                            tempEnemySprite.xVel = -Math.abs(tempEnemySprite.xVel);
                            subTempEnemySprite.xVel = Math.abs(subTempEnemySprite.xVel);
                        }
                        else {
                            tempEnemySprite.xVel = Math.abs(tempEnemySprite.xVel);
                            subTempEnemySprite.xVel = -Math.abs(subTempEnemySprite.xVel);
                        }
                    }
                }
            }
        }
    }
    
    private void checkFriendlyFireballCollisionsWithEnemies() {
        for (final FireballFriend fb : this.fireballFriends) {
            if (fb.collidable) {
                final Rectangle fbRect = fb.getContactRectangle();
                for (final Enemy tempEnemy : this.enemies) {
                    if (fbRect.intersects(tempEnemy.getSpriteContactRectangle())) {
                        if (tempEnemy instanceof Goomba && ((Goomba)tempEnemy).smushed) {
                            return;
                        }
                        if (tempEnemy instanceof Beetle || tempEnemy instanceof Bullet) {
                            fb.contact(false);
                            return;
                        }
                        if (tempEnemy instanceof Hammer || tempEnemy instanceof FireballEnemy || tempEnemy instanceof LavaBall) {
                            continue;
                        }
                        if (tempEnemy instanceof FlameBreath) {
                            continue;
                        }
                        if (!(tempEnemy instanceof Bowser)) {
                            tempEnemy.bumpKilled(fb);
                            fb.contact(true);
                            return;
                        }
                        final Bowser tempBowser = (Bowser)tempEnemy;
                        if (tempBowser.collision(fbRect)) {
                            tempBowser.hit();
                            fb.contact(true);
                            return;
                        }
                        continue;
                    }
                }
            }
        }
    }
    
    public void draw(final Graphics2D g2D) {
    	Rectangle bound = g2D.getDeviceConfiguration().getBounds();
    	BufferedImage buf = new BufferedImage((int)bound.getWidth(),(int)bound.getHeight(),BufferedImage.TYPE_INT_ARGB);
       Graphics2D g2d = buf.createGraphics();
    	if (this.disable) {
            return;
        }
        this.drawBackground(g2d);
        g2d.translate(-this.leftMostX, 0);
        this.drawBackgroundTiles(g2d);
        this.drawPulleys(g2d);
        this.drawPlatforms(g2d);
        this.drawSolidTiles(g2d);
        this.priority1.clear();
        this.priority2.clear();
        this.priority3.clear();
        this.priority4.clear();
        this.priority5.clear();
        this.priority6.clear();
        this.drawFriendsAndSortSprites(g2d);
        this.drawSpriteTiles(g2d);
        this.drawSprings(g2d);
        this.drawPriorityLevelSprites(g2d, this.priority1);
        this.drawCheckpoints(g2d);
        this.game.mario.draw(g2d);
        this.drawPriorityLevelSprites(g2d, this.priority2);
        this.drawPriorityLevelSprites(g2d, this.priority3);
        this.drawPriorityLevelSprites(g2d, this.priority4);
        this.drawPriorityLevelSprites(g2d, this.priority5);
        this.drawFriendlyFireballs(g2d);
        this.drawPriorityLevelSprites(g2d, this.priority6);
        if (this.game.mario.isDead()) {
            this.game.mario.draw(g2d);
        }
        final Iterator<Effect> effectsIter = this.effects.iterator();
        while (effectsIter.hasNext()) {
            effectsIter.next().draw(g2d);
        }
        g2d.dispose();
        if(game.luigiBros)
        	buf = Utilities.horizontalFlip(buf);
        g2D.drawImage(buf,0,0,null);
        
    }
    
    public void drawMouseOption(final Graphics2D g2D) {
        final int i = this.mouseHoveredIndex;
        if (i != Level.MOUSE_OPTION_NONE) {
            final Composite oldComposite = g2D.getComposite();
            g2D.setComposite(AlphaComposite.getInstance(3, 0.5f));
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2D.setColor(Color.WHITE);
            g2D.translate(0, -2);
            if (i == Level.MOUSE_OPTION_RESUME) {
                g2D.fillRoundRect(80, 56, 104, 8, 8, 8);
            }
            else if (i == Level.MOUSE_OPTION_FULLSCREEN) {
                g2D.fillRoundRect(80, 72, 120, 8, 8, 8);
            }
            else if (i == Level.MOUSE_OPTION_CONTROLS) {
                g2D.fillRoundRect(80, 88, 112, 8, 8, 8);
            }
            else if (i == Level.MOUSE_OPTION_MUTE) {
                g2D.fillRoundRect(80, 104, 64, 8, 8, 8);
            }
            else if (i == Level.MOUSE_OPTION_RETRY_LVL) {
                g2D.fillRoundRect(80, 120, 88, 8, 8, 8);
            }
            else if (i == Level.MOUSE_OPTION_RETRY_CHK) {
                g2D.fillRoundRect(80, 136, 128, 8, 8, 8);
            }
            else if (i == Level.MOUSE_OPTION_QUIT) {
                g2D.fillRoundRect(80, 152, 80, 8, 8, 8);
            }
            g2D.translate(0, 2);
            g2D.setComposite(oldComposite);
        }
    }
    
    private void drawBackground(final Graphics2D g2D) {
        if (this.levelType != LEVEL_TYPE_UNDER_WATER) {
            g2D.setColor(this.backgroundColor);
            g2D.fillRect(0, 0, Game.renderWidth, Game.renderHeight);
        }
        else {
            g2D.setColor(this.backgroundColor);
            g2D.fillRect(0, 0, Game.renderWidth, 32);
            if(game.textures.waterBackground == null){
            	g2D.setColor(this.game.textures.waterBlue);
            	g2D.fillRect(0, 32, Game.renderWidth, Game.renderHeight - 32);
            }else{
            	g2D.drawImage(game.textures.waterBackground.getImage(),0,32,null);
            }
            final int offset = this.leftMostX % 8;
            for (int i = -offset; i < (Game.xTiles + 1) * 8; i += 8) {
                g2D.drawImage(this.game.textures.waves.getImage(), i, 24, null);
            }
        }
    }
    
    private void drawFriendsAndSortSprites(final Graphics2D g2D) {
        for (final Sprite tempSprite : this.sprites) {
            if (tempSprite.visible && tempSprite.isOnScreen()) {
                if (tempSprite instanceof Friend || tempSprite instanceof Beanstalk || tempSprite instanceof ArrivalVine || (tempSprite instanceof PoisonMushroom && !tempSprite.bumpKilled)) {
                    tempSprite.draw(g2D);
                }
                else if (this.isPriorityTwoSprite(tempSprite)) {
                    this.priority1.add(tempSprite);
                }
                else if (tempSprite instanceof Pipe) {
                    this.priority2.add(tempSprite);
                }
                else if (tempSprite instanceof Bullet || tempSprite instanceof FlameBreath || this.isWaterEnemy(tempSprite) || tempSprite instanceof RedFish || tempSprite instanceof LavaBall) {
                    this.priority3.add(tempSprite);
                }
                else if (tempSprite instanceof EnemyHolder || tempSprite instanceof Bowser) {
                    this.priority4.add(tempSprite);
                }
                else if (tempSprite instanceof FireballEnemy || tempSprite instanceof Hammer) {
                    this.priority5.add(tempSprite);
                }
                else {
                    if (!(tempSprite instanceof Enemy) || !tempSprite.bumpKilled) {
                        continue;
                    }
                    this.priority6.add(tempSprite);
                }
            }
        }
    }
    
    private boolean isPriorityTwoSprite(final Sprite sprite) {
        return !sprite.bumpKilled && ((sprite instanceof Enemy && !this.isWaterEnemy(sprite) && !(sprite instanceof RedFish) && !(sprite instanceof Bowser) && !(sprite instanceof FlameBreath) && !(sprite instanceof FireballEnemy) && !(sprite instanceof Bullet) && !(sprite instanceof Bowser) && !(sprite instanceof Hammer) && !(sprite instanceof LavaBall)) || (sprite instanceof BowserBattle || sprite instanceof Flag));
    }
    
    private void drawPriorityLevelSprites(final Graphics2D g2D, final LinkedList<Sprite> sprites) {
        final Iterator<Sprite> spritesIter = sprites.iterator();
        while (spritesIter.hasNext()) {
            spritesIter.next().draw(g2D);
        }
    }
    
    private void drawCheckpoints(final Graphics2D g2D) {
        final Iterator<Checkpoint> checkpointsIter = this.checkpoints.iterator();
        while (checkpointsIter.hasNext()) {
            checkpointsIter.next().draw(g2D);
        }
    }
    
    private void drawSprings(final Graphics2D g2D) {
        final Iterator<Spring> springsIter = this.springs.iterator();
        while (springsIter.hasNext()) {
            springsIter.next().draw(g2D);
        }
    }
    
    private void drawFriendlyFireballs(final Graphics2D g2D) {
        final Iterator<FireballFriend> fbIter = this.fireballFriends.iterator();
        while (fbIter.hasNext()) {
            fbIter.next().draw(g2D);
        }
    }
    
    private void drawPulleys(final Graphics2D g2D) {
        final Iterator<Pulley> pulleysIter = this.pulleys.iterator();
        while (pulleysIter.hasNext()) {
            pulleysIter.next().draw(g2D);
        }
    }
    
    private void drawPlatforms(final Graphics2D g2D) {
        for (final Platform platform : this.platforms) {
            if (platform.isExtraShortPlatform()) {
                platform.drawLine(g2D);
            }
        }
        final Iterator<Platform> platformsIter2 = this.platforms.iterator();
        while (platformsIter2.hasNext()) {
            platformsIter2.next().draw(g2D);
        }
    }
    
    private void drawBackgroundTiles(final Graphics2D g2D) {
        int firstDrawnTile = 0;
        if (this.leftMostTile - 19 > 0) {
            firstDrawnTile = this.leftMostTile - 19;
        }
        int lastDrawnTile = this.leftMostTile + Game.xTiles + 19;
        if (lastDrawnTile >= this.xTiles) {
            lastDrawnTile = this.xTiles - 1;
        }
        this.backgroundItemsPending.clear();
        for (int i = 0; i < Game.yTiles; ++i) {
            for (int j = firstDrawnTile; j <= lastDrawnTile; ++j) {
                if (this.tiles[i][j].image != null && this.tiles[i][j].isPermeableTile() && this.tiles[i][j].xTile * 8 + this.tiles[i][j].image.getIconWidth() >= this.leftMostX) {
                    this.transform.setToIdentity();
                    this.transform.translate(j * 8, i * 8);
                    if (this.tiles[i][j].shiftOver && this.tiles[i][j].image != this.game.textures.bowserChain) {
                        this.transform.translate(-this.tiles[i][j].image.getIconWidth() + 8, 0.0);
                        g2D.drawImage(this.game.textures.getLevelTypeAlt(this.game.level.levelType,this.tiles[i][j].image).getImage(), this.transform, null);
                    }
                    else {
                        this.backgroundItemsPending.add(this.tiles[i][j]);
                    }
                }
            }
        }
        for (final Tile backgroundTile : this.backgroundItemsPending) {
            this.transform.setToIdentity();
            this.transform.translate(backgroundTile.xTile * 8, backgroundTile.yTile * 8);
            if (backgroundTile.image == this.game.textures.bowserChain && backgroundTile.shiftOver) {
                this.transform.translate(backgroundTile.image.getIconWidth(), 0.0);
                this.transform.scale(-1.0, 1.0);
            }
            else if (backgroundTile.image == this.game.textures.lavaBottom || backgroundTile.image == this.game.textures.waterBottom) {
                this.checkForColorFill(backgroundTile, g2D, false);
            }
            else if (backgroundTile.image == this.game.textures.lavaTop || backgroundTile.image == this.game.textures.waterTop) {
                this.checkForColorFill(backgroundTile, g2D, true);
            }
            g2D.drawImage(this.game.textures.getLevelTypeAlt(this.game.level.levelType,backgroundTile.image).getImage(), this.transform, null);
        }
    }
    
    private void checkForColorFill(final Tile t, final Graphics2D g2D, final boolean topPiece) {
        if (t.xTile > 0 && this.tiles[t.yTile][t.xTile - 1].extendColor) {
            this.transform.translate(-8.0, 0.0);
            g2D.drawImage(t.image.getImage(), this.transform, null);
            this.transform.translate(8.0, 0.0);
        }
        if (t.xTile < this.tiles[0].length - 1 && this.tiles[t.yTile][t.xTile + 1].extendColor) {
            this.transform.translate(8.0, 0.0);
            g2D.drawImage(t.image.getImage(), this.transform, null);
            this.transform.translate(-8.0, 0.0);
        }
        if (!topPiece && t.yTile > 0 && this.tiles[t.yTile - 1][t.xTile].extendColor) {
            this.transform.translate(0.0, -8.0);
            g2D.drawImage(t.image.getImage(), this.transform, null);
            this.transform.translate(0.0, 8.0);
        }
        if (t.yTile < this.tiles.length - 1 && this.tiles[t.yTile + 1][t.xTile].extendColor) {
            this.transform.translate(0.0, 8.0);
            if (t.image == this.game.textures.lavaTop || t.image == this.game.textures.lavaBottom) {
                g2D.drawImage(this.game.textures.lavaBottom.getImage(), this.transform, null);
            }
            else if (t.image == this.game.textures.waterTop || t.image == this.game.textures.waterBottom) {
                g2D.drawImage(this.game.textures.waterBottom.getImage(), this.transform, null);
            }
            this.transform.translate(0.0, -8.0);
        }
    }
    
    private void drawSolidTiles(final Graphics2D g2D) {
        int firstDrawnTile = 0;
        if (this.leftMostTile - 19 > 0) {
            firstDrawnTile = this.leftMostTile - 19;
        }
        int lastDrawnTile = firstDrawnTile + 19 + Game.xTiles;
        if (lastDrawnTile >= this.xTiles) {
            lastDrawnTile = this.xTiles - 1;
        }
        for (int i = 0; i < Game.yTiles; ++i) {
            for (int j = firstDrawnTile; j <= lastDrawnTile; ++j) {
                if (this.tiles[i][j].image != null && !this.tiles[i][j].isPermeableTile() && this.tiles[i][j].xTile * 8 + this.tiles[i][j].image.getIconWidth() >= this.leftMostX) {
                    this.transform.setToIdentity();
                    this.transform.translate(j * 8, i * 8);
                    if (this.tiles[i][j].image == this.game.textures.bridge) {
                        this.transform.translate(0.0, -8.0);
                    }
                    //if this tile is a Ground tile
                    if(this.game.textures.isGroundTile(this.tiles[i][j].image)){
                    	ImageIcon image;
                    	
                    	Textures textures = this.game.textures;
                    	
                    	image = textures.getLevelTypeAlt(levelType,textures.lightGround);
                    	//if there is nothing above this tile
                    	if(i != 0 && tiles[i-1][j].isPermeableTile()){
                    		
                    		//if there is no tile to the right, draw top right
                    		if(j+2 < tiles[i].length && tiles[i][j+2].isPermeableTile())   {
                    			
                    			g2D.drawImage(textures.getGroundSide(image,"topRight").getImage(),this.transform,null);
                    		//else if there is no tile to the left, draw top left
                    		}else if(j != 0 && (tiles[i][j-1].isPermeableTile())){
                    			g2D.drawImage(textures.getGroundSide(image,"topLeft").getImage(),this.transform,null);
                    		//else just draw top
                    		}else{
                    		
                    			g2D.drawImage(textures.getGroundSide(image,"top").getImage(),this.transform,null);
                    		}
                    	//else if there is nothing below this tile
                    	}else if(i+2 < tiles.length && tiles[i+2][j].isPermeableTile()){
                    		
                    		//if there is no tile to the right, draw bottom right
                    		if(j+2 < tiles[i].length && tiles[i][j+2].isPermeableTile())   {
                    			g2D.drawImage(textures.getGroundSide(image,"bottomRight").getImage(),this.transform,null);
                    		//else if there is no tile to the left, draw bottom left
                    		}else if(j != 0 && (tiles[i][j-1].isPermeableTile())){
                    			g2D.drawImage(textures.getGroundSide(image,"bottomLeft").getImage(),this.transform,null);
                    		//else just draw bottom
                    		}else{
                    			g2D.drawImage(textures.getGroundSide(image,"bottom").getImage(),this.transform,null);
                    		}
                    	//else if there is no tile to the left
                    	}else if(j != 0 && tiles[i][j-1].isPermeableTile()){
                    		g2D.drawImage(textures.getGroundSide(image, "left").getImage(),this.transform,null);
                    	//else if there is no tile to the right
                    	}else if(j+2 <tiles[i].length && tiles[i][j+2].isPermeableTile()){
                    		
                    		g2D.drawImage(textures.getGroundSide(image,"right").getImage(),this.transform,null);
                    	//else draw it normally
                    	}else{
                    		g2D.drawImage(image.getImage(), this.transform, null);
                    	}
                   
                    //else draw it normally
                    }else{
                    	g2D.drawImage(this.game.textures.getLevelTypeAlt(this.levelType,this.tiles[i][j].image).getImage(), this.transform, null);
                    }
                }
            }
        }
    }
    
    private void drawSpriteTiles(final Graphics2D g2D) {
        int firstDrawnTile = 0;
        if (this.leftMostTile - 19 > 0) {
            firstDrawnTile = this.leftMostTile - 19;
        }
        int lastDrawnTile = firstDrawnTile + 19 + Game.xTiles;
        if (lastDrawnTile >= this.xTiles) {
            lastDrawnTile = this.xTiles - 1;
        }
        for (int i = 0; i < Game.yTiles; ++i) {
            for (int j = firstDrawnTile; j <= lastDrawnTile; ++j) {
                if (this.tiles[i][j].sprite != null && this.drawAsTile(this.tiles[i][j].sprite)) {
                	this.tiles[i][j].sprite.draw(g2D,this.levelType);
                }
            }
        }
    }
    
    private boolean drawAsTile(final Sprite sprite) {
        return !(sprite instanceof Platform) && !(sprite instanceof LavaBall);
    }
    
    public int loadLevel(final int levelNumber, final int startingWarpID) {
        final LevelLoader levelLoader = this.game.gameLoader.loadLevel(this.game, levelNumber);
        final int error = levelLoader.getErrorCode();
        if (error != -1) {
            return error;
        }
        final LinkedList<Warp> warps = this.game.gameLoader.getLevelWarps(levelNumber);
        this.levelAuthor = this.game.gameLoader.getGameAuthor();
        this.levelNamePart1 = levelLoader.getLevelNamePart1();
        this.levelNamePart2 = levelLoader.getLevelNamePart2();
        if (levelLoader.isTimedLevel()) {
            this.timedLevel = true;
            this.levelTime = levelLoader.getLevelTime();
        }
        else {
            this.timedLevel = false;
        }
        this.tiles = levelLoader.getLevelTiles();
        this.xTiles = this.tiles[0].length;
        this.maxTravelX = levelLoader.getMaxTravelX();
        this.hasSpinyThrower = levelLoader.spinyThrowerPresent();
        this.hasFlyingFish = levelLoader.flyingFishPresent();
        this.hasBulletThrower = levelLoader.bulletThrowerPresent();
        this.hasFlameThrower = levelLoader.flameThrowerPresent();
        this.blackAndWhite = levelLoader.isBlackAndWhite();
        this.autoScrollingLevel = levelLoader.isAutoScrolling();
        this.shadowType = LevelLoader.getShadowType(levelLoader.getLevelType());
        if (startingWarpID == -1) {
            this.setMarioFromCheckpoint();
        }
        else {
            final boolean warpFound = this.setMarioFromWarpID(warps, startingWarpID);
            if (!warpFound) {
                return 15;
            }
        }
        this.levelType = levelLoader.getLevelType();
        this.pipeType = levelLoader.getPipeType();
        this.cliffDestLevel = levelLoader.getCliffDestLevel();
        if (this.cliffDestLevel != -1) {
            this.cliffDestID = levelLoader.getCliffDestID();
        }
        this.setBackgroundColor();
        return -1;
    }
    
    public int getErrorCode() {
        return this.errorCode;
    }
    
    private void setMarioFromCheckpoint() {
        this.game.mario.setXY(this.game.mario.checkpoint.x, this.game.mario.checkpoint.y);
        this.game.mario.ticks = 0.0;
        if (this.game.mario.isLarge()) {
            this.game.mario.spriteState = 6;
        }
        else {
            this.game.mario.spriteState = 0;
        }
    }
    
    private boolean setMarioFromWarpID(final LinkedList<Warp> warps, final int warpID) {
        for (final Warp warp : warps) {
            if (warp.sourceWarpID == warpID && warp.type != 5) {
                if (warp.type == 0) {
                    this.game.mario.setTileXY(warp.xTile, warp.yTile);
                    this.game.mario.ticks = 0.0;
                    if (this.game.mario.isLarge()) {
                        this.game.mario.spriteState = 6;
                    }
                    else {
                        this.game.mario.spriteState = 0;
                    }
                }
                else if (warp.type == 3) {
                    this.game.mario.setTileXY(warp.xTile + 1, warp.yTile);
                    this.game.mario.warp = warp;
                    this.game.mario.returnTubeSoundPlayed = false;
                    this.game.mario.transitioning = true;
                    this.game.mario.ticks = 0.0;
                    this.game.mario.transitionState = 5;
                    if (this.game.mario.isLarge()) {
                        this.game.mario.spriteState = 6;
                    }
                    else {
                        this.game.mario.spriteState = 0;
                    }
                }
                else if (warp.type == 4) {
                    if (this.game.mario.isLarge()) {
                        this.game.mario.setTileXY(warp.xTile + 1, warp.yTile);
                    }
                    else {
                        this.game.mario.setTileXY(warp.xTile + 1, warp.yTile - 2);
                    }
                    this.game.mario.warp = warp;
                    this.game.mario.returnTubeSoundPlayed = false;
                    this.game.mario.transitioning = true;
                    this.game.mario.ticks = 0.0;
                    this.game.mario.transitionState = 7;
                    if (this.game.mario.isLarge()) {
                        this.game.mario.spriteState = 6;
                    }
                    else {
                        this.game.mario.spriteState = 0;
                    }
                }
                else if (warp.type == 1) {
                    this.game.mario.setTileXY(warp.xTile, warp.yTile);
                    this.game.mario.warp = warp;
                    this.game.mario.returnTubeSoundPlayed = false;
                    this.game.mario.transitioning = true;
                    this.game.mario.ticks = 0.0;
                    this.game.mario.transitionState = 11;
                    final Mario mario = this.game.mario;
                    mario.yPos -= this.game.mario.avoidedCollisionRowsOnBottom;
                    this.game.mario.y = (int)this.game.mario.yPos;
                    if (this.game.mario.isLarge()) {
                        this.game.mario.spriteState = 7;
                    }
                    else {
                        this.game.mario.spriteState = 1;
                    }
                }
                else if (warp.type == 2) {
                    this.game.mario.returnTubeSoundPlayed = false;
                    this.game.mario.setTileXY(warp.xTile, warp.yTile);
                    this.game.mario.warp = warp;
                    this.game.mario.transitioning = true;
                    this.game.mario.ticks = 0.0;
                    this.game.mario.transitionState = 12;
                    final Mario mario2 = this.game.mario;
                    mario2.xPos += 8.0;
                    this.game.mario.x = (int)this.game.mario.xPos;
                    final Mario mario3 = this.game.mario;
                    mario3.yPos -= this.game.mario.avoidedCollisionRowsOnBottom;
                    this.game.mario.y = (int)this.game.mario.yPos;
                    if (this.game.mario.isLarge()) {
                        this.game.mario.spriteState = 7;
                    }
                    else {
                        this.game.mario.spriteState = 1;
                    }
                }
                else if (warp.type == 8) {
                    this.game.mario.setXY(warp.xTile * 8 - 8 + this.game.mario.avoidedCollisionCols, warp.yTile * 8 + 88);
                    this.game.mario.transitioning = true;
                    this.game.mario.ticks = 0.0;
                    this.game.mario.flip = false;
                    this.game.mario.warp = warp;
                    this.game.mario.transitionState = 16;
                }
                else if (warp.type == 9) {
                    this.game.mario.setTileXY(warp.xTile, warp.yTile);
                    this.game.mario.ticks = 0.0;
                    if (this.game.mario.isLarge()) {
                        this.game.mario.spriteState = 6;
                    }
                    else {
                        this.game.mario.spriteState = 0;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    private void setBackgroundColor() {
        if (this.levelType == LEVEL_TYPE_OUTSIDE || this.levelType == LEVEL_TYPE_COIN_ZONE_DAY || this.levelType == LEVEL_TYPE_UNDER_WATER) {
            this.backgroundColor = this.game.textures.skyBlue;
        }
        else if (this.levelType == LEVEL_TYPE_UNDERGROUND || this.levelType == LEVEL_TYPE_CASTLE || this.levelType == LEVEL_TYPE_OUTSIDE_NIGHT || this.levelType == LEVEL_TYPE_COIN_ZONE_NIGHT || this.levelType == LEVEL_TYPE_GHOST_HOUSE) {
            this.backgroundColor = this.game.textures.black;
        }
    }
   
    public Color getBackgroundColor() {
        return this.backgroundColor;
    }
    
    public int handleMenuMouse(final Point mousePos) {
        if (this.game.getGameState() != 1 || !this.game.level.paused) {
            return Level.MOUSE_OPTION_NONE;
        }
        final int width = this.game.getWidth();
        final int height = this.game.getHeight();
        double scaleFactor;
        int barWidth;
        int barHeight;
        if (width > height) {
            scaleFactor = height / Game.renderHeight;
            barWidth = (int)Math.round((width - Game.renderWidth * scaleFactor) / 2.0);
            barHeight = 0;
        }
        else {
            scaleFactor = width / Game.renderWidth;
            barHeight = (int)Math.round((height - Game.renderHeight * scaleFactor) / 2.0);
            barWidth = 0;
        }
        final Rectangle firstOption = new Rectangle((int)Math.round(barWidth + 88.0 * scaleFactor), (int)Math.round(barHeight + 56.0 * scaleFactor), (int)Math.round(88.0 * scaleFactor), (int)Math.round(8.0 * scaleFactor * 2.0));
        final Rectangle secondOption = new Rectangle((int)Math.round(barWidth + 88.0 * scaleFactor), (int)Math.round(barHeight + 72.0 * scaleFactor), (int)Math.round(104.0 * scaleFactor), (int)Math.round(8.0 * scaleFactor * 2.0));
        final Rectangle thirdOption = new Rectangle((int)Math.round(barWidth + 88.0 * scaleFactor), (int)Math.round(barHeight + 84.0 * scaleFactor), (int)Math.round(96.0 * scaleFactor), (int)Math.round(8.0 * scaleFactor * 2.0));
        final Rectangle fourthOption = new Rectangle((int)Math.round(barWidth + 88.0 * scaleFactor), (int)Math.round(barHeight + 100.0 * scaleFactor), (int)Math.round(48.0 * scaleFactor), (int)Math.round(8.0 * scaleFactor * 2.0));
        final Rectangle fifthOption = new Rectangle((int)Math.round(barWidth + 88.0 * scaleFactor), (int)Math.round(barHeight + 116.0 * scaleFactor), (int)Math.round(72.0 * scaleFactor), (int)Math.round(8.0 * scaleFactor * 2.0));
        final Rectangle sixthOption = new Rectangle((int)Math.round(barWidth + 88.0 * scaleFactor), (int)Math.round(barHeight + 132.0 * scaleFactor), (int)Math.round(120.0 * scaleFactor), (int)Math.round(8.0 * scaleFactor * 2.0));
        final Rectangle seventhOption = new Rectangle((int)Math.round(barWidth + 88.0 * scaleFactor), (int)Math.round(barHeight + 148.0 * scaleFactor), (int)Math.round(72.0 * scaleFactor), (int)Math.round(8.0 * scaleFactor * 2.0));
        final Rectangle mouseRect = new Rectangle(mousePos.x, mousePos.y, 1, 1);
        if (mouseRect.intersects(firstOption)) {
            return Level.MOUSE_OPTION_RESUME;
        }
        if (mouseRect.intersects(secondOption)) {
            return Level.MOUSE_OPTION_FULLSCREEN;
        }
        if (mouseRect.intersects(thirdOption)) {
            return Level.MOUSE_OPTION_CONTROLS;
        }
        if (mouseRect.intersects(fourthOption)) {
            return Level.MOUSE_OPTION_MUTE;
        }
        if (mouseRect.intersects(fifthOption)) {
            return Level.MOUSE_OPTION_RETRY_LVL;
        }
        if (mouseRect.intersects(sixthOption)) {
            return Level.MOUSE_OPTION_RETRY_CHK;
        }
        if (mouseRect.intersects(seventhOption)) {
            return Level.MOUSE_OPTION_QUIT;
        }
        return Level.MOUSE_OPTION_NONE;
    }
    
    static {
        SPRITE_X_OFFSCREEN_LIMIT_ON_LEFT = Game.xTiles * 2 * 8;
        SPRITE_X_OFFSCREEN_LIMIT_ON_RIGHT = Game.xTiles * 2 * 8;
        Level.TERMINAL_VELOCITY_WATER = 184.0;
        Level.WATER_GRAVITY = 160.0;
        Level.TERMINAL_VELOCITY = 240.0;
        Level.GRAVITY = 1200.0;
        Level.AUTO_SCROLL_VEL = Mario.MAX_WALL_PUSH_SPEED - 8.0;
        Level.MOUSE_OPTION_NONE = -1;
        Level.MOUSE_OPTION_RESUME = 0;
        Level.MOUSE_OPTION_FULLSCREEN = 1;
        Level.MOUSE_OPTION_CONTROLS = 2;
        Level.MOUSE_OPTION_MUTE = 3;
        Level.MOUSE_OPTION_RETRY_LVL = 4;
        Level.MOUSE_OPTION_RETRY_CHK = 5;
        Level.MOUSE_OPTION_QUIT = 6;
    }
}
