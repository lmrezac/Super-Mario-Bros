// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites;

import supermario.game.sprites.enemies.Spiny;
import supermario.game.sprites.enemies.Bullet;
import supermario.game.interfaces.Shelled;
import supermario.game.sprites.enemies.RedFish;
import supermario.game.sprites.enemies.HammerBro;
import supermario.game.sprites.enemies.SpinyThrower;
import supermario.game.sprites.enemies.Goomba;
import supermario.game.sprites.effects.Points;
import supermario.game.interfaces.Enemy;
import supermario.game.sprites.effects.Firework;
import supermario.game.sprites.blocks.QuestionBox;
import supermario.game.sprites.blocks.Brick;
import supermario.game.interfaces.Block;
import java.awt.Rectangle;
import supermario.game.sprites.friends.FireballFriend;
import java.awt.Graphics2D;
import supermario.game.Level;
import supermario.game.sprites.effects.AirBubble;
import supermario.game.Game;
import javax.swing.ImageIcon;
import supermario.game.sprites.misc.ArrivalVine;
import supermario.game.sprites.misc.Beanstalk;
import supermario.game.sprites.misc.BowserBattle;
import supermario.game.sprites.misc.Flag;
import supermario.game.sprites.misc.Checkpoint;
import supermario.game.Warp;
import supermario.game.Tile;
import supermario.game.Sprite;

public class Mario extends Sprite
{
    public static final int SMALL_STANDING = 0;
    public static final int SMALL_WALKING1 = 1;
    public static final int SMALL_WALKING2 = 2;
    public static final int SMALL_WALKING3 = 3;
    public static final int SMALL_RUNNING = 4;
    public static final int SMALL_JUMPING = 5;
    public static final int LARGE_STANDING = 6;
    public static final int LARGE_WALKING1 = 7;
    public static final int LARGE_WALKING2 = 8;
    public static final int LARGE_WALKING3 = 9;
    public static final int LARGE_JUMPING = 10;
    public static final int LARGE_CROUCHING = 11;
    public static final int SKID = 12;
    public static final int SMALL_SKID = 13;
    public static final int DEAD = 14;
    public static final int SMALL_CLIMBING1 = 15;
    public static final int SMALL_CLIMBING2 = 16;
    public static final int LARGE_CLIMBING1 = 17;
    public static final int LARGE_CLIMBING2 = 18;
    public static final int LARGE_SWIMMING_STROKE1 = 19;
    public static final int LARGE_SWIMMING_STROKE2 = 20;
    public static final int LARGE_SWIMMING_STROKE3 = 21;
    public static final int SMALL_SWIMMING_STROKE1 = 22;
    public static final int SMALL_SWIMMING_STROKE2 = 23;
    public static final int SMALL_SWIMMING_STROKE3 = 24;
    public static final int TRANSITION_SMALL_TO_BIG = 1;
    public static final int TRANSITION_BIG_TO_SMALL = 2;
    public static final int TRANSITION_DEAD = 3;
    public static final int TRANSITION_BIG_TO_FLOWER = 4;
    public static final int TRANSITION_TUBE_ARRIVING_UP = 5;
    public static final int TRANSITION_TUBE_DEPARTING_DOWN = 6;
    public static final int TRANSITION_TUBE_ARRIVING_DOWN = 7;
    public static final int TRANSITION_TUBE_DEPARTING_UP = 8;
    public static final int TRANSITION_TUBE_ENTER_LEFT = 9;
    public static final int TRANSITION_TUBE_ENTER_RIGHT = 10;
    public static final int TRANSITION_TUBE_EXIT_LEFT = 11;
    public static final int TRANSITION_TUBE_EXIT_RIGHT = 12;
    public static final int TRANSITION_FLAG_CATCH = 13;
    public static final int TRANSITION_AXE_CATCH = 14;
    public static final int TRANSITION_BEANSTALK_CATCH = 15;
    public static final int TRANSITION_BEANSTALK_ARRIVAL = 16;
    public static final int TRANSITION_CLIFF_WARP = 17;
    private boolean hasFlowerPower;
    private boolean hasStar;
    public int transitionState;
    private int stateAfterTransition;
    public boolean transitioning;
    private double bubbleTicks;
    private double bubbleDelay;
    private double invincibleTicks;
    private double transitionTicks;
    private double shootTicks;
    private double starTicks;
    private double starImageChangeTicks;
    private double starImageIndex;
    private boolean mustReleaseBeforeJump;
    public double inJumpTime;
    private boolean jumpHoldAllowed;
    public static final int MAX_LIVES = 99;
    public static final int INVINCIBLE_TIME = 2000;
    public static final int STAR_TIME = 12000;
    public static final int STAR_LEAVING_TIME = 9000;
    public static final int MAX_JUMP_HOLD_TIME = 500;
    public static final int FULL_JUMP_COMMIT_THRESHOLD = 400;
    public static final int MIN_JUMP_HOLD_TIME = 90;
    public static double MAX_JUMP_HEIGHT;
    public static final int EARLY_JUMP_END_RECOIL = -120;
    public static final int PINCH_SPEED = 48;
    public static double MAX_WALKING_SPEED;
    public static double MAX_WALL_PUSH_SPEED;
    public static final int SPEED_TO_SKIP_HOLES = 96;
    public static final int SPEED_TO_CATCH_EDGES = 232;
    public static double MAX_RUNNING_SPEED;
    public static final int SKID_THRESHOLD = 48;
    public static double X_ACCELERATION_FRONT;
    public static final int X_ACCELERATION_AIR_COUNTER_FRONT_FACING = 240;
    public static double SLOWING_DECELERATION;
    public static double CROUCHING_DECELERATION;
    public static final int WALK_BUILD_UP_IMAGE_CHANGE_DELAY = 125;
    public static final int FULL_WALK_IMAGE_CHANGE_DELAY = 70;
    public static final int FULL_RUN_IMAGE_CHANGE_DELAY = 40;
    public static final int PADDLE_IMAGE_CHANGE_DELAY = 70;
    public static final int STROKE_IMAGE_CHANGE_DELAY = 150;
    public static final int STAR_IMAGE_CHANGE_DELAY = 35;
    public static final int DELAYED_STAR_IMAGE_CHANGE_DELAY = 150;
    public static final int SWIM_STROKE_VELOCITY = -96;
    public static double SKID_DECELERATION;
    public static final int BUMP_RECOIL_VELOCITY = 64;
    public static final int FLAG_FALL_VELOCITY = 32;
    public static final double MARIO_SMUSH_BOUNCE_VELOCITY_GOOMBA = -176.0;
    public static final double MARIO_SMUSH_BOUNCE_VELOCITY_SHELLED_ENEMY = -240.0;
    public static final double MARIO_SMUSH_BOUNCE_VELOCITY_BULLET = -120.0;
    public static final double MARIO_TUBE_WARP_Y_VELOCITY = -32.0;
    public static final int DEATH_GRAVITY = 560;
    public static final int DEATH_INITIAL_VELOCITY = -280;
    public static final int DEATH_TERMINAL_VELOCITY = 280;
    public static final int SHOOTING_IMAGE_INDEX_TIME = 70;
    public static final int MAX_FIREBALLS_AT_A_TIME = 2;
    public static final int MIN_BUBBLE_TIME = 700;
    public static final int MAX_BUBBLE_TIME = 2500;
    public static final int EXTRA_LIFE_SMUSH_THRESHOLD = 7;
    public double jumpBaseHeight;
    public double actualJumpHeightLimit;
    public double pixelsToMove;
    public double MARIO_SMUSH_POWER_BOUNCE;
    public final double MOVE_OVER_SPEED = 48.0;
    private boolean largeMario;
    private Tile ignoredTile;
    private boolean pinched;
    private boolean pinchLeft;
    private boolean wasPinched;
    private boolean verticalPinch;
    private boolean stuck;
    public double timeSinceLastWalkChange;
    private boolean skiddingLeft;
    private boolean skiddingRight;
    public boolean inJump;
    public boolean inJumpDescent;
    public boolean springLaunched;
    public int coins;
    public int lives;
    public int points;
    public boolean invincible;
    public boolean cliffDeath;
    private double pendingYPos;
    private double pendingYVel;
    public boolean runKeyReleased;
    public boolean shooting;
    public Warp warp;
    public Checkpoint checkpoint;
    public boolean returnTubeSoundPlayed;
    public double totalTime;
    public double levelTime;
    public Flag flag;
    public BowserBattle bowserBattle;
    public Beanstalk beanstalk;
    public ArrivalVine arrivalVine;
    public int levelRestartNumber;
    public String levelNamePart1;
    public String levelNamePart2;
    private int[] starTranslation;
    public boolean godMode;
    public boolean unlimitedFireballs;
    public boolean swimming;
    public boolean stateHeightHalved;
    public boolean outOfTimeDeath;
    public boolean timedLevel;
    public boolean playedTimeWarning;
    public boolean loopedFastMusic;
    public boolean powerBounceMode;
    private boolean paddleSwitch;
    private boolean swimStroking;
    private boolean strokeReset;
    private double paddleTicks;
    private double strokeTicks;
    private int indexDuringTransition;
    private int flowerChangeIndex;
    private int strokeState;
    private int killCount;
    public ImageIcon[] marioImages, luigiImages, spongeImages;
    public byte character = asMario;
    public static final byte asMario = 0, asLuigi = 1, asSponge = 2;
    public boolean wallPushing;
    
    public Mario(final Game game, final ImageIcon[] images, final ImageIcon[] luigiImages, final ImageIcon[] spongeImages) {
        super(game, images);
        this.MARIO_SMUSH_POWER_BOUNCE = -400.0;
        this.starTranslation = new int[] { 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 30, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68 };
        this.marioImages = images;
        this.luigiImages = luigiImages;
        this.spongeImages = spongeImages;
        this.setNextBubbleTime();
        this.avoidedCollisionCols = 2;
        this.avoidedCollisionRowsOnTop = 3;
        this.avoidedCollisionRowsOnBottom = 1;
    }
    
    @Override
    public Sprite reset() {
        return null;
    }
    
    public void prepareForGameStart(final int startingLives, final int startingLevel) {
        this.lives = startingLives;
        this.checkpoint = null;
        this.points = 0;
        this.reset(true);
        this.coins = 0;
        this.levelRestartNumber = startingLevel;
    }
    
    public void reset(final boolean fullReset) {
        if (fullReset) {
            this.returnTubeSoundPlayed = true;
            this.hasStar = false;
            if (this.game.testMode && this.game.input.fireMarioTesting) {
                this.hasFlowerPower = true;
                this.largeMario = true;
                this.stateHeightHalved = false;
            }
            else if (this.game.testMode && this.game.input.superMarioTesting) {
                this.hasFlowerPower = false;
                this.largeMario = true;
                this.stateHeightHalved = false;
            }
            else {
                this.hasFlowerPower = false;
                this.largeMario = false;
                this.stateHeightHalved = true;
            }
            this.flip = false;
            this.transitioning = false;
            this.transitionState = -1;
            this.totalTime = 0.0;
            this.starTicks = 0.0;
            this.starImageChangeTicks = 0.0;
            this.starImageIndex = 0.0;
            this.playedTimeWarning = false;
            this.loopedFastMusic = false;
            if (this.largeMario) {
                this.spriteState = 6;
            }
            else {
                this.spriteState = 0;
            }
        }
        this.springLaunched = false;
        this.killCount = 0;
        this.starImageIndex = 0.0;
        this.flag = null;
        this.bowserBattle = null;
        this.beanstalk = null;
        this.arrivalVine = null;
        this.ignoredTile = null;
        this.collidable = true;
        this.inJump = false;
        this.inJumpDescent = false;
        this.inJumpTime = 0.0;
        this.jumpBaseHeight = this.y;
        this.actualJumpHeightLimit = 0.0;
        this.shooting = false;
        this.visible = true;
        this.jumpHoldAllowed = false;
        this.ticks = 0.0;
        this.endInvincibility();
        if (this.game.input.runDown) {
            this.runKeyReleased = false;
        }
        else {
            this.runKeyReleased = true;
        }
        this.mustReleaseBeforeJump = false;
        this.xVel = 0.0;
        this.yVel = 0.0;
    }
    
    @Override
    public void update(final double delta) {
        this.preUpdate(delta);
        this.setInvincibleState(delta);
        this.setStarState(delta);
        if (this.coins >= 100) {
            this.extraLife();
            this.coins -= 100;
            this.game.audio.play(0);
        }
        if (this.transitioning) {
            this.transition(delta);
            return;
        }
        if (this.shooting) {
            this.shootTicks += delta;
            if (this.shootTicks > 70.0) {
                this.shooting = false;
            }
        }
        if (this.isCrushed()) {
            this.died(false, false);
            return;
        }
        if (this.swimming) {
            this.bubbleTicks += delta;
            if (this.bubbleTicks >= this.bubbleDelay) {
                this.game.level.effects.add(new AirBubble(this.game));
                this.setNextBubbleTime();
            }
            this.determineSwimState();
            this.applyWaterXForces(delta);
            if (!this.overCliff()) {
                this.applyGravity(delta, Level.WATER_GRAVITY);
            }
            else {
                this.applyGravity(delta, Level.WATER_GRAVITY * 4.0);
            }
            if (this.yPos < 8.0) {
                this.yVel = 16.0;
            }
            if (this.collidable) {
                this.checkForCollisions();
            }
            this.checkForShoot();
        }
        else if (!(this.pinched = this.isPinched())) {
            if (!(this.stuck = this.isStuck())) {
                this.applyXForces(delta);
                this.determineJumpState();
                this.applyYForces(delta);
                if (this.collidable) {
                    this.checkForCollisions();
                }
                this.checkForShoot();
                this.stuck = this.isStuck();
            }
        }
        else {
            this.correctPinch(delta);
            if (!this.isPinched()) {
                this.stuck = this.isStuck();
            }
        }
        this.setState();
        this.setImageIndex(true);
        this.updatePosition();
        this.setBounds();
        this.grounded = this.isGrounded();
        if (this.grounded) {
            this.killCount = 0;
        }
    }
    
    @Override
    public void draw(final Graphics2D g2D) {
        if (this.character == asLuigi && this.imageIndex < this.luigiImages.length) {
            this.images = this.luigiImages;
        }else if(this.character == asSponge && this.imageIndex < this.spongeImages.length){
        	
        }
        else {
            this.images = this.marioImages;
        }
        super.draw(g2D);
        this.images = this.marioImages;
    }
    
    private void setInvincibleState(final double delta) {
        if (this.invincible) {
            if (!this.transitioning) {
                this.invincibleTicks += delta;
            }
            this.transitionTicks = (this.transitionTicks + 1.0) % 3.0;
            this.visible = (this.transitionTicks % 3.0 == 0.0 || this.transitionTicks % 3.0 == 1.0);
            if (this.invincibleTicks > 2000.0) {
                this.endInvincibility();
            }
        }
    }
    
    private void endInvincibility() {
        this.invincible = false;
        this.visible = true;
    }
    
    private void setStarState(final double delta) {
        if (this.hasStar) {
            this.starTicks += delta;
            if (!this.transitioning || (this.transitionState != 4 && this.transitionState != 1)) {
                this.starImageChangeTicks += delta;
            }
            if (this.starTicks >= 12000.0) {
                this.starFinished(true);
            }
        }
    }
    
    public void extraLife() {
        ++this.lives;
        if (this.lives > 99) {
            this.lives = 99;
        }
    }
    
    private void setNextBubbleTime() {
        this.bubbleTicks = 0.0;
        this.bubbleDelay = this.game.rand.nextInt(1801) + 700;
    }
    
    private void checkForShoot() {
        if (!this.game.input.runDown && !this.runKeyReleased && (this.game.level.fireballFriends.size() < 2 || this.unlimitedFireballs) && !this.transitioning) {
            this.runKeyReleased = true;
        }
        if (!this.hasFlowerPower || this.spriteState == 11) {
            return;
        }
        if (this.runKeyReleased && this.game.input.runDown && (this.game.level.fireballFriends.size() < 2 || this.unlimitedFireballs) && !this.transitioning) {
            this.runKeyReleased = false;
            this.shooting = true;
            this.shootTicks = 0.0;
            this.game.audio.play(14);
            this.game.level.fireballFriends.add(new FireballFriend(this.game, this.game.textures.getFireballTextures()));
        }
    }
    
    private boolean overCliff() {
        if (this.grounded) {
            return false;
        }
        if (this.y + this.height - this.avoidedCollisionRowsOnBottom >= Game.renderHeight) {
            return true;
        }
        final Rectangle marioRect = this.getRectangle();
        final Tile left = this.game.level.getTileAtPixel(marioRect.x, marioRect.y + marioRect.height);
        final Tile middle = this.game.level.getTileAtPixel(marioRect.x + marioRect.width / 2, marioRect.y + marioRect.height);
        final Tile right = this.game.level.getTileAtPixel(marioRect.x + marioRect.width, marioRect.y + marioRect.height);
        return !this.solidTileBelow(left) && !this.solidTileBelow(middle) && !this.solidTileBelow(right);
    }
    
    private boolean solidTileBelow(final Tile tile) {
        for (Tile tempTile = tile; tempTile.isPermeableTile(); tempTile = this.game.level.getTile(tempTile.xTile, tempTile.yTile + 1)) {
            if (tempTile.yTile >= Game.yTiles - 1) {
                return false;
            }
        }
        return true;
    }
    
    public int getTimeLeft() {
        return (int)(this.levelTime - this.totalTime);
    }
    
    public void caughtAxe(final BowserBattle bowserBattle) {
        bowserBattle.caught = true;
        this.starFinished(false);
        this.transitioning = true;
        this.transitionState = 14;
        this.shooting = false;
        this.endInvincibility();
        this.game.level.fireballFriends.clear();
        this.game.level.spritesToAdd.clear();
        this.game.level.coinEnemies();
        this.ticks = 0.0;
    }
    
    public void caughtFlag(final Flag flag) {
        (this.flag = flag).setPoints();
        final int timeLeft = this.getTimeLeft();
        flag.setExcessTime(timeLeft);
        flag.setFireworks(timeLeft);
        this.starFinished(false);
        flag.caught = true;
        this.xPos = flag.x + this.avoidedCollisionCols;
        if (this.yPos < 0 - this.height + this.avoidedCollisionRowsOnTop + this.avoidedCollisionRowsOnBottom) {
            this.yPos = 0 - this.height + this.avoidedCollisionRowsOnTop + this.avoidedCollisionRowsOnBottom;
        }
        this.finalizePosition();
        this.flip = false;
        this.transitioning = true;
        this.transitionState = 13;
        this.shooting = false;
        this.endInvincibility();
        this.timeSinceLastWalkChange = 0.0;
        if (this.largeMario) {
            this.spriteState = 17;
            this.setImageIndex(true);
        }
        this.game.level.fireballFriends.clear();
        this.game.level.spritesToAdd.clear();
        this.game.level.coinEnemies();
        this.ticks = 0.0;
    }
    
    public void caughtStar() {
        if (!this.hasStar) {
            this.endInvincibility();
            if (this.shouldStopMusicAtLevelEnd()) {
                this.game.audio.stopMusic(false);
            }
            this.hasStar = true;
            if (this.game.mario.timedLevel && this.playedTimeWarning) {
                this.game.audio.loopMusic(11, false);
            }
            else {
                this.game.audio.loopMusic(4, false);
            }
        }
        else {
            this.starTicks = 0.0;
        }
    }
    
    public void caughtBeanstalk(final Beanstalk beanstalk) {
        this.beanstalk = beanstalk;
        final Block block = (Block)beanstalk.block;
        if (block instanceof Brick) {
            this.warp = ((Brick)block).warp;
        }
        else if (block instanceof QuestionBox) {
            this.warp = ((QuestionBox)block).warp;
        }
        final int beanstalkCenter = beanstalk.getXCenter();
        if (this.game.mario.getXCenter() > beanstalkCenter) {
            this.flip = true;
            this.xPos = beanstalkCenter - this.avoidedCollisionCols;
        }
        else {
            this.flip = false;
            this.xPos = beanstalkCenter - this.width + this.avoidedCollisionCols;
        }
        this.yPos = this.y;
        this.transitioning = true;
        this.transitionState = 15;
        this.timeSinceLastWalkChange = 0.0;
        if (this.largeMario) {
            this.spriteState = 17;
        }
        else {
            this.spriteState = 15;
        }
        this.setImageIndex(true);
        this.shooting = false;
        this.endInvincibility();
    }
    
    public void starFinished(final boolean restartMusic) {
        if (this.hasStar) {
            this.hasStar = false;
            if (restartMusic) {
                this.game.audio.resetMusicCompletionRatio();
                this.game.audio.stopMusic(true);
            }
            else {
                this.game.audio.stopMusic(false);
            }
            this.starTicks = 0.0;
            this.starImageChangeTicks = 0.0;
            this.starImageIndex = 0.0;
            this.setImageIndex(true);
        }
    }
    
    private boolean shouldStopMusicAtLevelEnd() {
        return !this.hasStar && (!this.timedLevel || !this.playedTimeWarning || this.loopedFastMusic);
    }
    
    private void transition(final double delta) {
        if (this.transitionState == 1) {
            if (this.ticks < 800.0) {
                if (this.ticks < 80.0) {
                    this.imageIndex = 15;
                }
                else if (this.ticks < 160.0) {
                    this.imageIndex = 29;
                }
                else if (this.ticks < 240.0) {
                    this.imageIndex = 15;
                }
                else if (this.ticks < 320.0) {
                    this.imageIndex = 29;
                }
                else if (this.ticks < 400.0) {
                    this.imageIndex = 15;
                }
                else if (this.ticks < 480.0) {
                    this.imageIndex = 29;
                }
                else if (this.ticks < 560.0) {
                    this.imageIndex = 0;
                }
                else if (this.ticks < 640.0) {
                    this.imageIndex = 15;
                }
                else if (this.ticks < 720.0) {
                    this.imageIndex = 29;
                }
                else if (this.ticks < 800.0) {
                    this.imageIndex = 0;
                }
                this.translateImageIndices();
            }
            else if (this.ticks < 1100.0) {
                this.spriteState = this.stateAfterTransition;
                this.setImageIndex(true);
            }
            else {
                this.transitioning = false;
                this.transitionState = -1;
                this.ticks = 0.0;
            }
        }
        else if (this.transitionState == 2) {
            if (this.ticks >= 400.0) {
                final int ticksInterval = 80;
                if (this.ticks > 400 + ticksInterval * 8) {
                    this.spriteState = this.stateAfterTransition;
                    this.setImageIndex(true);
                    this.transitioning = false;
                    this.transitionState = -1;
                }
                else {
                    final int index = (int)this.ticks / ticksInterval;
                    if (index % 2 == 0) {
                        this.imageIndex = 20;
                    }
                    else if (index % 2 == 1) {
                        this.imageIndex = 5;
                    }
                    this.translateImageIndices();
                }
            }
        }
        else if (this.transitionState == 4) {
            final int ticksInterval = 80;
            if (this.ticks > ticksInterval * 12) {
                this.transitioning = false;
                this.transitionState = -1;
                this.ticks = 0.0;
            }
            else {
                final int index = (int)this.ticks / ticksInterval;
                if (index % 4 == 0 || index % 4 == 1 || index % 4 == 2 || index % 4 == 3) {
                    this.imageIndex = this.indexDuringTransition;
                    this.setState();
                    this.translateForFlowerCatch(index % 4);
                    this.flowerChangeIndex = this.imageIndex;
                }
                else {
                    this.imageIndex = this.flowerChangeIndex;
                }
            }
        }
        else if (this.transitionState == 3) {
            this.spriteState = 14;
            if (this.y >= Game.renderHeight) {
                this.visible = false;
            }
            this.setImageIndex(true);
            if (this.ticks < 400.0) {
                this.yVel = -280.0;
            }
            else if (this.yPos < Game.renderHeight) {
                this.yVel += 560.0 * delta / 1000.0;
                if (this.yVel > 280.0) {
                    this.yVel = 280.0;
                }
                this.yPos += this.yVel * delta / 1000.0;
                this.y = (int)Math.round(this.yPos);
            }
            else if (this.yPos >= Game.renderHeight && this.ticks > 3000.0) {
                this.reset(true);
                this.game.level.setToDisable = true;
                if (this.checkpoint != null) {
                    this.game.changeToTransition(this.checkpoint.levelNumber, -1, true, false);
                }
                else {
                    this.game.changeToTransition(this.levelRestartNumber, 0, true, false);
                }
            }
        }
        else if (this.transitionState == 5 || this.transitionState == 8) {
            if (!this.returnTubeSoundPlayed) {
                this.game.audio.play(5);
                this.returnTubeSoundPlayed = true;
            }
            this.yVel = -32.0;
            this.yPos += this.yVel * delta / 1000.0;
            if (this.transitionState == 8 && this.yPos < this.warp.yTile * 8) {
                this.yPos = this.warp.yTile * 8;
            }
            this.y = (int)Math.round(this.yPos);
            if (this.largeMario) {
                this.spriteState = 6;
            }
            else {
                this.spriteState = 0;
            }
            this.setImageIndex(true);
            if ((this.transitionState == 5 && this.y + this.height - this.avoidedCollisionRowsOnBottom <= this.warp.yTile * 8) || (this.transitionState == 8 && this.y + this.height - this.avoidedCollisionRowsOnBottom <= (this.warp.yTile + 4) * 8 && this.ticks > 1000.0)) {
                this.transitioning = false;
                final boolean changeLevels = this.transitionState == 8;
                this.transitionState = -1;
                this.ticks = 0.0;
                this.grounded = true;
                if (changeLevels) {
                    if (this.shouldStopMusicAtLevelEnd()) {
                        this.game.audio.stopMusic(false);
                    }
                    this.game.level.setToDisable = true;
                    this.game.changeToTransition(this.warp.destLevelNumber, this.warp.destWarpID, false, false);
                }
            }
        }
        else if (this.transitionState == 6) {
            this.yVel = 32.0;
            this.yPos += this.yVel * delta / 1000.0;
            if (this.yPos > this.warp.yTile * 8 - 10) {
                this.yPos = this.warp.yTile * 8 - 10;
            }
            this.finalizePosition();
            if (this.largeMario) {
                this.spriteState = 11;
            }
            else {
                this.spriteState = 0;
            }
            this.setImageIndex(true);
            if (this.y + 10 >= this.warp.yTile * 8 && this.ticks > 1000.0) {
                this.transitioning = false;
                this.transitionState = -1;
                this.ticks = 0.0;
                if (this.shouldStopMusicAtLevelEnd()) {
                    this.game.audio.stopMusic(false);
                }
                this.game.level.setToDisable = true;
                if (this.warp.type == 5) {
                    this.nextLevelWarp();
                }
                else {
                    this.game.changeToTransition(this.warp.destLevelNumber, this.warp.destWarpID, false, false);
                }
            }
        }
        else if (this.transitionState == 7) {
            if (!this.returnTubeSoundPlayed) {
                this.game.audio.play(5);
                this.returnTubeSoundPlayed = true;
            }
            this.yVel = 32.0;
            this.yPos += this.yVel * delta / 1000.0;
            this.finalizePosition();
            if (this.largeMario) {
                this.spriteState = 6;
            }
            else {
                this.spriteState = 0;
            }
            this.setImageIndex(true);
            if ((this.largeMario && this.y + this.avoidedCollisionRowsOnTop >= (this.warp.yTile + 4) * 8) || (!this.largeMario && this.y + this.tilesHeight * 8 / 2 > (this.warp.yTile + 4) * 8)) {
                this.transitioning = false;
                this.transitionState = -1;
                this.ticks = 0.0;
            }
        }
        else if (this.transitionState == 11) {
            if (!this.returnTubeSoundPlayed) {
                this.game.audio.play(5);
                this.returnTubeSoundPlayed = true;
            }
            this.flip = true;
            this.yVel = 0.0;
            this.xVel = -Mario.MAX_WALKING_SPEED / 4.0;
            this.xPos += this.xVel * delta / 1000.0;
            this.x = (int)Math.round(this.xPos);
            this.timeSinceLastWalkChange += delta;
            if (this.timeSinceLastWalkChange >= 125.0) {
                this.timeSinceLastWalkChange %= 125.0;
                if (this.x + this.width - this.avoidedCollisionCols <= this.warp.xTile * 8) {
                    this.transitioning = false;
                    this.transitionState = -1;
                    this.ticks = 0.0;
                    if (this.largeMario) {
                        this.spriteState = 6;
                    }
                    else {
                        this.spriteState = 0;
                    }
                }
                else {
                    this.nextWalkImage();
                    this.setImageIndex(true);
                }
            }
        }
        else if (this.transitionState == 12) {
            if (!this.returnTubeSoundPlayed) {
                this.game.audio.play(5);
                this.returnTubeSoundPlayed = true;
            }
            this.flip = false;
            this.yVel = 0.0;
            this.xVel = Mario.MAX_WALKING_SPEED / 4.0;
            this.xPos += this.xVel * delta / 1000.0;
            this.x = (int)Math.round(this.xPos);
            this.timeSinceLastWalkChange += delta;
            if (this.timeSinceLastWalkChange >= 125.0) {
                this.timeSinceLastWalkChange %= 125.0;
                if (this.x + this.avoidedCollisionCols >= this.warp.xTile * 8 + 24) {
                    this.transitioning = false;
                    this.transitionState = -1;
                    this.ticks = 0.0;
                    if (this.largeMario) {
                        this.spriteState = 6;
                    }
                    else {
                        this.spriteState = 0;
                    }
                }
                else {
                    this.nextWalkImage();
                }
            }
            this.setImageIndex(true);
        }
        else if (this.transitionState == 9) {
            this.flip = false;
            this.yVel = 0.0;
            this.xVel = Mario.MAX_WALKING_SPEED / 4.0;
            if (this.x <= this.warp.xTile * 8) {
                this.xPos += this.xVel * delta / 1000.0;
                this.x = (int)Math.round(this.xPos);
            }
            this.timeSinceLastWalkChange += delta;
            if (this.timeSinceLastWalkChange >= 125.0) {
                this.timeSinceLastWalkChange %= 125.0;
                this.nextWalkImage();
            }
            if (this.x >= this.warp.xTile * 8 && this.ticks > 1000.0) {
                this.transitioning = false;
                this.transitionState = -1;
                this.ticks = 0.0;
                if (this.shouldStopMusicAtLevelEnd()) {
                    this.game.audio.stopMusic(false);
                }
                this.game.level.setToDisable = true;
                this.game.changeToTransition(this.warp.destLevelNumber, this.warp.destWarpID, false, false);
            }
            else if (this.x >= this.warp.xTile * 8) {
                this.visible = false;
            }
            this.setImageIndex(true);
        }
        else if (this.transitionState == 10) {
            this.flip = true;
            this.yVel = 0.0;
            this.xVel = -Mario.MAX_WALKING_SPEED / 4.0;
            if (this.x >= this.warp.xTile * 8 + 8) {
                this.xPos += this.xVel * delta / 1000.0;
                this.x = (int)Math.round(this.xPos);
            }
            this.timeSinceLastWalkChange += delta;
            if (this.timeSinceLastWalkChange >= 125.0) {
                this.timeSinceLastWalkChange %= 125.0;
                this.nextWalkImage();
            }
            if (this.x - this.width <= this.warp.xTile * 8 && this.ticks > 1000.0) {
                this.transitioning = false;
                this.transitionState = -1;
                this.ticks = 0.0;
                if (this.shouldStopMusicAtLevelEnd()) {
                    this.game.audio.stopMusic(false);
                }
                this.game.level.setToDisable = true;
                this.game.changeToTransition(this.warp.destLevelNumber, this.warp.destWarpID, false, false);
            }
            else if (this.x - this.width <= this.warp.xTile * 8 - 8) {
                this.visible = false;
            }
            this.setImageIndex(true);
        }
        else if (this.transitionState == 14) {
            this.bowserBattle.update(delta);
            if (this.bowserBattle.spriteState == 0) {
                if (this.bowserBattle.bowser.bumpKilled) {
                    this.bowserBattle.spriteState = 2;
                }
                else if (this.ticks > 300.0) {
                    this.bowserBattle.spriteState = 1;
                    this.ticks = 0.0;
                    this.bowserBattle.ticks = 0.0;
                }
            }
            else if (this.bowserBattle.spriteState == 1) {
                if (this.bowserBattle.bridgeGone) {
                    this.bowserBattle.spriteState = 2;
                    this.ticks = 0.0;
                }
            }
            else if (this.bowserBattle.spriteState == 2) {
                if (this.bowserBattle.bowser.bumpKilled || this.ticks > 1000.0) {
                    this.game.audio.stopMusic(false);
                    this.game.audio.stopMusic(10);
                    this.game.audio.playMusic(2);
                    this.bowserBattle.victoryWalking = true;
                    this.bowserBattle.spriteState = 3;
                    this.xVel = Mario.MAX_WALKING_SPEED;
                }
            }
            else if (this.bowserBattle.spriteState == 3) {
                this.preUpdate(delta);
                this.xPos += this.xVel * delta / 1000.0;
                this.applyGravity(delta, Level.GRAVITY);
                this.checkForCollisions();
                this.finalizePosition();
                if (this.isGrounded()) {
                    this.bowserBattle.spriteState = 4;
                }
            }
            else if (this.bowserBattle.spriteState == 4) {
                if (this.largeMario) {
                    this.spriteState = 9;
                }
                else {
                    this.spriteState = 3;
                }
                this.timeSinceLastWalkChange = 0.0;
                this.bowserBattle.spriteState = 5;
                this.flip = false;
                this.game.level.bowserBattleOngoing = false;
            }
            else if (this.bowserBattle.spriteState == 5) {
                this.preUpdate(delta);
                this.xPos += this.xVel * delta / 1000.0;
                this.applyGravity(delta, Level.GRAVITY);
                this.checkForCollisions();
                this.finalizePosition();
                this.timeSinceLastWalkChange += delta;
                if (this.timeSinceLastWalkChange >= 40.0 && this.isGrounded()) {
                    this.timeSinceLastWalkChange %= 40.0;
                    this.nextWalkImage();
                    this.setImageIndex(true);
                }
                if (this.xPos >= 8 * this.game.level.xTiles - 144) {
                    this.xPos = 8 * this.game.level.xTiles - 144;
                    this.finalizePosition();
                    if (this.largeMario) {
                        this.spriteState = 6;
                    }
                    else {
                        this.spriteState = 0;
                    }
                    this.setImageIndex(true);
                    this.bowserBattle.spriteState = 6;
                }
            }
            else if (this.bowserBattle.spriteState != 6) {
                if (this.bowserBattle.spriteState != 7) {
                    if (this.bowserBattle.spriteState == 8) {
                        this.ticks = 0.0;
                        this.bowserBattle.spriteState = 9;
                    }
                    else if (this.bowserBattle.spriteState == 9 && ((this.bowserBattle.lastLevel && this.ticks > 1000.0) || this.ticks > 3000.0)) {
                        this.warp = this.bowserBattle.warp;
                        this.nextLevelWarp();
                    }
                }
            }
        }
        else if (this.transitionState == 13) {
            this.flag.update(delta);
            if (this.game.level.leftMostX < this.game.level.xTiles * 8 - Game.renderWidth) {
                final Level level = this.game.level;
                level.leftMostX += (int)(24.0 * delta / 1000.0);
            }
            if (this.flag.spriteState == 0) {
                this.game.audio.stopMusic(false);
                this.game.audio.stopMusic(10);
                this.game.audio.play(15);
                this.flag.spriteState = 1;
            }
            else if (this.flag.spriteState == 1) {
                this.yPos += 136.0 * delta / 1000.0;
                this.timeSinceLastWalkChange += delta;
                if (this.timeSinceLastWalkChange >= 70.0) {
                    this.timeSinceLastWalkChange %= 70.0;
                    if (this.largeMario) {
                        if (this.spriteState == 17) {
                            this.spriteState = 18;
                        }
                        else {
                            this.spriteState = 17;
                        }
                    }
                    else if (this.spriteState == 15) {
                        this.spriteState = 16;
                    }
                    else {
                        this.spriteState = 15;
                    }
                    this.setImageIndex(true);
                }
                if (this.yPos >= 152.0) {
                    this.yPos = 152.0;
                }
                if (this.yPos == 152.0 && this.flag.flagFinished) {
                    this.flag.spriteState = 2;
                }
            }
            else if (this.flag.spriteState == 2) {
                this.flip = true;
                this.xPos += this.width - this.avoidedCollisionCols * 2;
                this.ticks = 0.0;
                this.flag.spriteState = 3;
            }
            else if (this.flag.spriteState == 3) {
                if (this.ticks >= 750.0) {
                    this.flag.spriteState = 4;
                    this.game.audio.playMusic(1);
                }
            }
            else if (this.flag.spriteState == 4) {
                if (this.largeMario) {
                    this.spriteState = 9;
                }
                else {
                    this.spriteState = 3;
                }
                this.xVel = Mario.MAX_WALKING_SPEED / 2.0;
                this.timeSinceLastWalkChange = 0.0;
                this.flag.spriteState = 5;
                this.flip = false;
                this.setImageIndex(true);
            }
            else if (this.flag.spriteState == 5) {
                this.applyGravity(delta, Level.GRAVITY);
                this.checkForCollisions();
                this.xPos += this.xVel * delta / 1000.0;
                this.timeSinceLastWalkChange += delta;
                if (this.timeSinceLastWalkChange >= 70.0) {
                    this.timeSinceLastWalkChange %= 70.0;
                    this.nextWalkImage();
                    this.setImageIndex(true);
                }
                if (this.xPos > this.flag.x + 104) {
                    this.visible = false;
                    this.ticks = 0.0;
                    if (this.timedLevel && this.flag.totalTimeLeft > 0.0) {
                        this.game.audio.play(18);
                        this.flag.spriteState = 9;
                    }
                    else {
                        this.flag.spriteState = 8;
                    }
                }
            }
            else if (this.flag.spriteState == 9) {
                this.ticks = 0.0;
            }
            else if (this.flag.spriteState == 8) {
                if ((this.timedLevel && this.ticks > 1500.0) || this.ticks > 3250.0) {
                    this.ticks = 0.0;
                    this.flag.spriteState = 6;
                }
            }
            else if (this.flag.spriteState == 6) {
                if (this.flag.fireworksLeft == 0 && (!this.timedLevel || this.game.level.levelTime == 0.0) && this.ticks > 1750.0) {
                    this.flag.spriteState = 10;
                }
                else {
                    this.flag.spriteState = 7;
                    this.ticks = 0.0;
                }
            }
            else if (this.flag.spriteState == 7) {
                if (this.ticks > 500.0) {
                    if (this.flag.fireworksLeft == 0) {
                        if (this.ticks > 1750.0) {
                            this.flag.spriteState = 10;
                        }
                    }
                    else {
                        this.game.level.effectsToAdd.add(new Firework(this.game, this.flag.getFireworkLocation()));
                        final Flag flag = this.flag;
                        --flag.fireworksLeft;
                        this.points += 500;
                        this.ticks = 0.0;
                    }
                }
            }
            else if (this.flag.spriteState == 10) {
                this.warp = this.flag.warp;
                this.nextLevelWarp();
                return;
            }
            this.finalizePosition();
        }
        else if (this.transitionState == 15) {
            this.yPos -= 48.0 * delta / 1000.0;
            if (!this.beanstalk.growing) {
                this.timeSinceLastWalkChange += delta;
            }
            if (this.timeSinceLastWalkChange >= 125.0) {
                this.timeSinceLastWalkChange %= 125.0;
                if (this.largeMario) {
                    if (this.spriteState == 17) {
                        this.spriteState = 18;
                    }
                    else {
                        this.spriteState = 17;
                    }
                }
                else if (this.spriteState == 15) {
                    this.spriteState = 16;
                }
                else {
                    this.spriteState = 15;
                }
            }
            this.finalizePosition();
            this.setImageIndex(true);
            if (this.yPos + this.height < 0.0) {
                this.transitioning = false;
                this.transitionState = -1;
                this.ticks = 0.0;
                if (this.shouldStopMusicAtLevelEnd()) {
                    this.game.audio.stopMusic(false);
                }
                this.game.level.setToDisable = true;
                this.game.changeToTransition(this.warp.destLevelNumber, this.warp.destWarpID, false, false);
            }
        }
        else if (this.transitionState == 16) {
            this.preUpdate(delta);
            if (this.arrivalVine == null) {
                this.arrivalVine = (ArrivalVine)this.game.level.tiles[this.warp.yTile][this.warp.xTile].sprite;
                this.arrivalVine.yPos = (Game.yTiles - 2) * 8;
                this.arrivalVine.y = (int)this.yPos;
                this.game.audio.play(11);
            }
            if (this.arrivalVine.spriteState == 0) {
                this.arrivalVine.grow();
                this.arrivalVine.spriteState = 1;
            }
            else if (this.arrivalVine.spriteState == 2) {
                this.timeSinceLastWalkChange += delta;
                if (this.timeSinceLastWalkChange >= 125.0) {
                    this.timeSinceLastWalkChange %= 125.0;
                    if (this.largeMario) {
                        if (this.spriteState == 17) {
                            this.spriteState = 18;
                        }
                        else {
                            this.spriteState = 17;
                        }
                    }
                    else if (this.spriteState == 15) {
                        this.spriteState = 16;
                    }
                    else {
                        this.spriteState = 15;
                    }
                    this.setImageIndex(true);
                }
                this.yPos -= 48.0 * delta / 1000.0;
                if (this.yPos <= Game.renderHeight - this.arrivalVine.height + 8) {
                    this.yPos = Game.renderHeight - this.arrivalVine.height + 8;
                    this.arrivalVine.spriteState = 3;
                    this.ticks = 0.0;
                    this.flip = true;
                    this.xPos = this.arrivalVine.x + 8 - this.avoidedCollisionCols;
                }
            }
            else if (this.arrivalVine.spriteState == 3) {
                if (this.ticks >= 500.0) {
                    this.arrivalVine.spriteState = 4;
                }
            }
            else if (this.arrivalVine.spriteState == 4) {
                this.xPos += 8.0;
                this.yVel = 0.0;
                this.flip = false;
                this.transitioning = false;
                this.transitionState = -1;
                if (this.largeMario) {
                    if (this.swimming) {
                        this.spriteState = 19;
                    }
                    else {
                        this.spriteState = 7;
                    }
                }
                else if (this.swimming) {
                    this.spriteState = 22;
                }
                else {
                    this.spriteState = 1;
                }
            }
            this.finalizePosition();
            this.setImageIndex(true);
        }
        else if (this.transitionState == 17 && this.ticks >= 1000.0) {
            this.transitioning = false;
            this.transitionState = -1;
            if (this.shouldStopMusicAtLevelEnd()) {
                this.game.audio.stopMusic(false);
            }
            this.game.changeToTransition(this.game.level.cliffDestLevel, this.game.level.cliffDestID, false, false);
        }
    }
    
    private void nextLevelWarp() {
        if (this.hasStar()) {
            this.starFinished(false);
        }
        this.transitioning = false;
        this.transitionState = -1;
        this.playedTimeWarning = false;
        this.loopedFastMusic = false;
        this.ticks = 0.0;
        this.checkpoint = null;
        this.game.level.setToDisable = true;
        this.levelRestartNumber = this.warp.destLevelNumber;
        this.game.changeToTransition(this.warp.destLevelNumber, this.warp.destWarpID, true, true);
    }
    
    public boolean isDead() {
        return this.spriteState == 14;
    }
    
    private void updatePosition() {
        if (!this.stuck) {
            this.finalizePosition();
        }
        else if (this.largeMario) {
            this.attacked(false, null);
        }
    }
    
    private boolean topHalfSolid() {
        for (int i = this.x + this.avoidedCollisionCols; i < this.x + this.width - this.avoidedCollisionCols; ++i) {
            if (!this.game.level.getTileAtPixel(i, this.y + this.avoidedCollisionRowsOnTop).isPermeableTile() || !this.game.level.getTileAtPixel(i, this.y + this.avoidedCollisionRowsOnTop + 8).isPermeableTile()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isWalking() {
        return this.spriteState == 7 || this.spriteState == 8 || this.spriteState == 9 || (this.spriteState == 1 || this.spriteState == 2 || this.spriteState == 3);
    }
    
    private void correctPinch(final double delta) {
        if (!this.verticalPinch) {
            this.pinchLeft = this.canUnpinchLeft();
            if (this.pinchLeft) {
                this.xPos -= 48.0 * delta / 1000.0;
            }
            else {
                this.xPos += 48.0 * delta / 1000.0;
            }
            this.yVel = 0.0;
            this.yPos = this.y;
        }
        else {
            this.applyGravity(delta, Level.GRAVITY);
            if (this.isGrounded()) {
                this.verticalPinch = false;
            }
        }
    }
    
    private boolean canUnpinchLeft() {
        if (this.game.level.getTileAtPixel(this.x + this.avoidedCollisionCols, this.y + this.avoidedCollisionRowsOnTop).isPermeableTile() && this.game.level.getTileAtPixel(this.x + this.avoidedCollisionCols, this.y + 8).isPermeableTile()) {
            Tile solidTile = null;
            for (int i = this.x + this.avoidedCollisionCols; i < this.x + this.avoidedCollisionCols + this.getRectangle().width; ++i) {
                final Tile topTile = this.game.level.getTileAtPixel(i, this.y + this.avoidedCollisionRowsOnTop);
                final Tile bottomTile = this.game.level.getTile(topTile.xTile, topTile.yTile + 1);
                if (!topTile.isPermeableTile()) {
                    solidTile = topTile;
                    break;
                }
                if (!bottomTile.isPermeableTile()) {
                    solidTile = bottomTile;
                    break;
                }
            }
            return (solidTile.xTile - 2) * 8 >= this.game.level.leftMostX;
        }
        return false;
    }
    
    public boolean willBePinchedOnTile(final Tile tile) {
        return !this.stateHeightHalved && tile.yTile != 0 && (tile.yTile != 1 || this.game.level.tiles[tile.yTile - 1][tile.xTile].solid || this.game.level.tiles[tile.yTile - 1][tile.xTile].isUnexposedHiddenTile()) && (tile.yTile != 2 || this.game.level.tiles[tile.yTile - 1][tile.xTile].solid || this.game.level.tiles[tile.yTile - 1][tile.xTile].isUnexposedHiddenTile() || this.game.level.tiles[tile.yTile - 2][tile.xTile].solid || this.game.level.tiles[tile.yTile - 2][tile.xTile].isUnexposedHiddenTile()) && (tile.yTile != 3 || this.game.level.tiles[tile.yTile - 1][tile.xTile].solid || this.game.level.tiles[tile.yTile - 1][tile.xTile].isUnexposedHiddenTile() || this.game.level.tiles[tile.yTile - 2][tile.xTile].solid || this.game.level.tiles[tile.yTile - 2][tile.xTile].isUnexposedHiddenTile() || this.game.level.tiles[tile.yTile - 3][tile.xTile].solid || this.game.level.tiles[tile.yTile - 3][tile.xTile].isUnexposedHiddenTile()) && ((this.game.level.tiles[tile.yTile - 1][tile.xTile].solid && !this.game.level.tiles[tile.yTile - 1][tile.xTile].isUnexposedHiddenTile()) || (this.game.level.tiles[tile.yTile - 2][tile.xTile].solid && !this.game.level.tiles[tile.yTile - 2][tile.xTile].isUnexposedHiddenTile()) || (this.game.level.tiles[tile.yTile - 3][tile.xTile].solid && !this.game.level.tiles[tile.yTile - 3][tile.xTile].isUnexposedHiddenTile()) || (this.game.level.tiles[tile.yTile - 4][tile.xTile].solid && !this.game.level.tiles[tile.yTile - 4][tile.xTile].isUnexposedHiddenTile()));
    }
    
    private boolean isStuck() {
        return !this.stateHeightHalved && !this.inJump && this.isColliding();
    }
    
    private boolean isCrushed() {
        if (!this.game.level.autoScrollingLevel || !this.wallPushing) {
            return false;
        }
        final int rightMostTile = (this.x + this.width - this.avoidedCollisionCols) / 8;
        if (rightMostTile > this.game.level.leftMostTile + Game.xTiles / 2) {
            return false;
        }
        final Rectangle rect = this.getRectangle();
        for (int i = rect.y; i < rect.y + rect.height; ++i) {
            if (i >= 0 && i < Game.renderHeight && !this.game.level.tiles[i / 8][rightMostTile].isPermeableTile()) {
                return true;
            }
        }
        return false;
    }
    
    public void unignoreTilesAfterForcedMotion() {
        if (this.isColliding()) {
            return;
        }
        this.pixelsToMove = 0.0;
        if (this.ignoredTile != null) {
            this.ignoredTile.disabled = false;
            if (this.ignoredTile.yTile > 0) {
                this.game.level.tiles[this.ignoredTile.yTile - 1][this.ignoredTile.xTile].disabled = false;
            }
            if (this.ignoredTile.yTile > 1) {
                this.game.level.tiles[this.ignoredTile.yTile - 2][this.ignoredTile.xTile].disabled = false;
            }
            this.ignoredTile = null;
        }
    }
    
    private void applyWaterXForces(final double delta) {
        if (!this.grounded) {
            this.paddleTicks += delta;
            if (this.paddleTicks > 70.0) {
                this.paddleSwitch = !this.paddleSwitch;
                this.paddleTicks -= 70.0;
            }
            if (this.swimStroking) {
                this.strokeTicks += delta;
                if (this.strokeTicks >= 150.0) {
                    ++this.strokeState;
                    if (this.strokeReset) {
                        this.strokeState %= 3;
                        this.strokeReset = false;
                    }
                    this.strokeTicks = 0.0;
                }
                if (this.strokeState == 6) {
                    this.strokeState = 0;
                    this.swimStroking = false;
                    this.strokeTicks = 0.0;
                }
            }
        }
        if (this.pixelsToMove == 0.0) {
            this.unignoreTilesAfterForcedMotion();
            this.applyWaterXMotion(delta);
        }
        else {
            this.moveAroundTile(delta);
        }
    }
    
    private void applyXForces(final double delta) {
        if (this.pixelsToMove == 0.0) {
            this.unignoreTilesAfterForcedMotion();
            this.applyXMotion(delta);
        }
        else {
            this.moveAroundTile(delta);
        }
    }
    
    private void moveAroundTile(final double delta) {
        if (this.pixelsToMove < 0.0) {
            if (-this.xVel * delta / 1000.0 > 48.0 * delta / 1000.0) {
                this.xPos += this.xVel * delta / 1000.0;
                this.pixelsToMove += -(this.xVel * delta) / 1000.0;
            }
            else {
                this.xPos -= 48.0 * delta / 1000.0;
                this.pixelsToMove += 48.0 * delta / 1000.0;
            }
            if (this.pixelsToMove > 0.0) {
                this.xPos -= this.pixelsToMove;
                this.pixelsToMove = 0.0;
                this.unignoreTilesAfterForcedMotion();
                if (this.xVel > 0.0) {
                    this.xVel = 0.0;
                }
            }
        }
        else if (this.pixelsToMove > 0.0) {
            if (this.xVel * delta / 1000.0 > 48.0 * delta / 1000.0) {
                this.xPos += this.xVel * delta / 1000.0;
                this.pixelsToMove -= this.xVel * delta / 1000.0;
            }
            else {
                this.xPos += 48.0 * delta / 1000.0;
                this.pixelsToMove -= 48.0 * delta / 1000.0;
            }
            if (this.pixelsToMove < 0.0) {
                this.xPos += this.pixelsToMove;
                this.pixelsToMove = 0.0;
                this.unignoreTilesAfterForcedMotion();
                if (this.xVel < 0.0) {
                    this.xVel = 0.0;
                }
            }
        }
    }
    
    public boolean moveOverTest(final Tile collidingTile) {
        if ((!this.swimming && this.inJumpTime == 0.0 && !this.springLaunched) || this.yPos < 1.0 || (this.swimming && this.grounded)) {
            return false;
        }
        boolean leftPossible = true;
        if (collidingTile.xTile < 2) {
            leftPossible = false;
        }
        else {
            int requiredFreeRows = 5;
            if (this.stateHeightHalved) {
                requiredFreeRows -= 2;
            }
            for (int i = 1; i <= 2; ++i) {
                for (int j = 0; j < requiredFreeRows; ++j) {
                    if (this.game.level.tiles[collidingTile.yTile + j][collidingTile.xTile - i].solid) {
                        leftPossible = false;
                    }
                }
            }
            if (collidingTile.xTile * 8 - this.width + this.avoidedCollisionCols < this.game.level.leftMostX) {
                leftPossible = false;
            }
        }
        boolean rightPossible = true;
        if (collidingTile.xTile >= this.game.level.xTiles - 2) {
            rightPossible = false;
        }
        else {
            int requiredFreeRows2 = 5;
            if (this.stateHeightHalved) {
                requiredFreeRows2 -= 2;
            }
            for (int k = 1; k <= 2; ++k) {
                for (int l = 0; l < requiredFreeRows2; ++l) {
                    if (this.game.level.tiles[collidingTile.yTile + l][collidingTile.xTile + k].solid) {
                        rightPossible = false;
                    }
                }
            }
            if (collidingTile.xTile * 8 + 8 + this.width - this.avoidedCollisionCols > this.game.level.xTiles * 8) {
                rightPossible = false;
            }
        }
        if (!leftPossible && !rightPossible) {
            return false;
        }
        final Rectangle marioRect = this.getRectangle();
        boolean left7Open = false;
        boolean right7Open = false;
        this.pixelsToMove = 0.0;
        if (leftPossible) {
            left7Open = true;
            for (int m = 6; m >= 0; --m) {
                if (this.game.level.getTileAtPixel(marioRect.x + m, marioRect.y - 1).solid) {
                    left7Open = false;
                }
            }
        }
        if (rightPossible) {
            right7Open = true;
            for (int m = 7; m < 12; ++m) {
                if (this.game.level.getTileAtPixel(marioRect.x + m, marioRect.y - 1).solid) {
                    right7Open = false;
                }
            }
        }
        if (left7Open) {
            for (int m = 11; m >= 5 && this.game.level.getTileAtPixel(marioRect.x + m, marioRect.y - 1).solid; --m) {
                --this.pixelsToMove;
            }
        }
        else if (right7Open) {
            for (int m = 0; m <= 6 && this.game.level.getTileAtPixel(marioRect.x + m, marioRect.y - 1).solid; ++m) {
                ++this.pixelsToMove;
            }
        }
        if (left7Open || right7Open) {
            this.ignoredTile = collidingTile;
            collidingTile.disabled = true;
            if (collidingTile.yTile > 0) {
                this.game.level.tiles[collidingTile.yTile - 1][collidingTile.xTile].disabled = true;
            }
            if (collidingTile.yTile > 1) {
                this.game.level.tiles[collidingTile.yTile - 2][collidingTile.xTile].disabled = true;
            }
            return true;
        }
        return false;
    }
    
    public Tile getTileToBump(final Tile bumpedTile) {
        final int pixelsNeeded = (this.width - this.avoidedCollisionCols * 2) / 2;
        int tally = 0;
        int xPixel = this.x + this.avoidedCollisionCols;
        final int yPixel = this.getRectangle().y;
        int totalPixelsTraversed = 0;
        int bestTallySoFar = 0;
        Tile bestTile;
        Tile consideredTile = bestTile = this.game.level.getTileAtPixel(xPixel, yPixel);
        while (tally < pixelsNeeded && totalPixelsTraversed < this.getRectangle().width) {
            if (this.game.level.getTileAtPixel(xPixel, yPixel) == consideredTile && consideredTile.solid) {
                if (++tally > bestTallySoFar) {
                    bestTallySoFar = tally;
                    bestTile = consideredTile;
                }
                ++xPixel;
                ++totalPixelsTraversed;
            }
            else {
                tally = 0;
                consideredTile = this.game.level.getTileAtPixel(++xPixel, yPixel);
                ++totalPixelsTraversed;
            }
        }
        return bestTile;
    }
    
    public void smushedAnEnemy(final Enemy enemy) {
        ++this.killCount;
        if (this.killCount > 7) {
            this.extraLife();
            this.game.level.effectsToAdd.add(new Points(this.game, this.getXCenter(), this.y, 10));
            this.game.audio.play(0);
        }
        if (this.pendingYPos > enemy.getSpriteContactRectangle().y - this.height) {
            this.pendingYPos = enemy.getSpriteContactRectangle().y - this.height;
        }
        if (this.powerBounceMode && this.isPowerBouncable(enemy) && this.pendingYVel > this.MARIO_SMUSH_POWER_BOUNCE) {
            this.pendingYVel = this.MARIO_SMUSH_POWER_BOUNCE;
        }
        else if (enemy instanceof Goomba || enemy instanceof SpinyThrower || enemy instanceof HammerBro || enemy instanceof RedFish) {
            if (this.pendingYVel > -176.0) {
                this.pendingYVel = -176.0;
            }
        }
        else if (enemy instanceof Shelled) {
            if (this.pendingYVel > -240.0) {
                this.pendingYVel = -240.0;
            }
        }
        else if (enemy instanceof Bullet && this.pendingYVel > -120.0) {
            this.pendingYVel = -120.0;
        }
    }
    
    private boolean isPowerBouncable(final Enemy enemy) {
        return enemy instanceof Goomba || enemy instanceof SpinyThrower || enemy instanceof HammerBro || enemy instanceof RedFish || enemy instanceof Shelled || enemy instanceof Bullet;
    }
    
    public void resetPendingYMotion() {
        this.pendingYPos = this.yPos;
        this.pendingYVel = this.yVel;
    }
    
    public void setPendingYMotion() {
        this.yPos = this.pendingYPos;
        this.y = (int)Math.round(this.yPos);
        this.yVel = this.pendingYVel;
    }
    
    public void attacked(final boolean ignoreGodMode, final Enemy attacker) {
        if ((this.invincible || (this.godMode && !ignoreGodMode)) && !this.stuck) {
            return;
        }
        if (attacker != null && (attacker instanceof Goomba || attacker instanceof Shelled || attacker instanceof Spiny || attacker instanceof HammerBro)) {
            final Sprite a = (Sprite)attacker;
            final int attackerCenter = a.x + a.width / 2;
            final int marioCenter = this.x + this.width / 2;
            if (attackerCenter < marioCenter && !a.flip) {
                attacker.xCollided();
                a.flip = true;
            }
            else if (attackerCenter > marioCenter && a.flip) {
                attacker.xCollided();
                a.flip = false;
            }
        }
        if (this.largeMario) {
            this.game.audio.stop(2);
            this.game.audio.play(5);
            this.hasFlowerPower = false;
            this.shooting = false;
            this.largeMario = false;
            this.stateHeightHalved = true;
            this.invincible = true;
            this.ticks = 0.0;
            this.invincibleTicks = 0.0;
            this.transitioning = true;
            this.transitionState = 2;
            if (this.spriteState == 11 || this.spriteState == 6) {
                this.stateAfterTransition = 0;
            }
            else if (this.spriteState == 12) {
                this.stateAfterTransition = 13;
            }
            else if (this.spriteState == 7 || this.spriteState == 8 || this.spriteState == 9) {
                this.stateAfterTransition = 1;
            }
            else if (this.spriteState == 10) {
                this.stateAfterTransition = 5;
            }
            else if (this.spriteState == 19 || this.spriteState == 20 || this.spriteState == 21) {
                this.stateAfterTransition = 22;
            }
        }
        else {
            this.died(false, false);
        }
    }
    
    public void died(final boolean outOfTime, final boolean cliffDeath) {
        this.outOfTimeDeath = outOfTime;
        this.cliffDeath = cliffDeath;
        if (this.spriteState == 14) {
            return;
        }
        this.game.audio.stopAllSounds();
        this.game.audio.playMusic(7);
        this.endInvincibility();
        this.hasStar = false;
        if (!this.godMode) {
            --this.lives;
        }
        this.transitionState = 3;
        this.spriteState = 14;
        this.transitioning = true;
        this.ticks = 0.0;
        this.collidable = false;
    }
    
    public void grow(final boolean isFlower) {
        if (!this.largeMario && this.spriteState != 11) {
            this.largeMario = true;
            this.stateHeightHalved = false;
            this.ticks = 0.0;
            this.transitioning = true;
            this.transitionState = 1;
            if (this.spriteState == 0) {
                this.stateAfterTransition = 6;
            }
            else if (this.spriteState == 13) {
                this.stateAfterTransition = 12;
            }
            else if (this.spriteState == 1 || this.spriteState == 2 || this.spriteState == 3) {
                this.stateAfterTransition = 7;
            }
            else if (this.spriteState == 22 || this.spriteState == 23 || this.spriteState == 24) {
                this.stateAfterTransition = 19;
            }
            else if (this.spriteState == 5) {
                this.stateAfterTransition = 10;
            }
            else if (this.spriteState == 22) {
                this.stateAfterTransition = 19;
            }
            else if (this.spriteState == 23) {
                this.stateAfterTransition = 20;
            }
            else if (this.spriteState == 24) {
                this.stateAfterTransition = 21;
            }
        }
        else if (!this.largeMario || isFlower) {
            if (this.largeMario && isFlower && !this.hasFlowerPower) {
                this.ticks = 0.0;
                this.setImageIndex(false);
                this.indexDuringTransition = this.imageIndex;
                this.transitioning = true;
                this.hasFlowerPower = true;
                this.runKeyReleased = false;
                this.transitionState = 4;
            }
        }
    }
    
    private void determineJumpState() {
        if (!this.game.input.jumpDown && this.inJump) {
            this.jumpStopped(false);
        }
        if (this.grounded && !this.game.input.jumpDown) {
            this.mustReleaseBeforeJump = false;
        }
        else if (!this.grounded && this.game.input.jumpDown) {
            this.mustReleaseBeforeJump = true;
        }
        if (this.game.input.jumpDown && this.grounded && !this.mustReleaseBeforeJump) {
            if (this.largeMario) {
                this.game.audio.play(2);
            }
            else {
                this.game.audio.play(3);
            }
            this.inJump = true;
            this.inJumpDescent = false;
            this.mustReleaseBeforeJump = true;
            this.jumpHoldAllowed = true;
            this.jumpBaseHeight = this.yPos;
            if (Math.abs(this.game.mario.xVel) > Mario.MAX_WALKING_SPEED) {
                this.actualJumpHeightLimit = Mario.MAX_JUMP_HEIGHT + 16.0;
            }
            else {
                this.actualJumpHeightLimit = Mario.MAX_JUMP_HEIGHT;
            }
        }
        if (this.inJumpDescent && this.grounded) {
            this.inJumpDescent = false;
            this.inJump = false;
        }
    }
    
    private void determineSwimState() {
        if (!this.game.input.jumpDown && this.mustReleaseBeforeJump) {
            this.mustReleaseBeforeJump = false;
        }
        if (this.game.input.jumpDown && !this.mustReleaseBeforeJump) {
            if (this.yPos > 16.0) {
                this.yVel = -96.0;
            }
            else if (this.yVel > 0.0 && this.yPos >= 12.0) {
                this.yVel = -96.0;
            }
            if (!this.swimStroking) {
                this.swimStroking = true;
                this.strokeTicks = 0.0;
                this.strokeState = 1;
            }
            else {
                this.strokeReset = true;
            }
            this.game.audio.play(7);
            this.mustReleaseBeforeJump = true;
        }
    }
    
    public void jumpStopped(final boolean forced) {
        if (forced) {
            this.inJumpTime = 0.0;
            this.jumpHoldAllowed = false;
            this.inJumpDescent = true;
            this.actualJumpHeightLimit = 0.0;
        }
        else if (this.inJumpTime >= 90.0) {
            if (this.inJumpTime <= 400.0) {
                this.jumpHoldAllowed = false;
                this.inJumpDescent = true;
                this.yVel = -120.0;
                this.inJumpTime = 0.0;
                this.actualJumpHeightLimit = 0.0;
            }
        }
    }
    
    public boolean isLarge() {
        return this.largeMario;
    }
    
    public boolean hasFlower() {
        return this.hasFlowerPower;
    }
    
    public boolean hasStar() {
        return this.hasStar;
    }
    
    private boolean isPinched() {
        this.wasPinched = this.pinched;
        if (this.x + this.width >= this.game.level.xTiles * 8) {
            return false;
        }
        if (this.y < 0) {
            return false;
        }
        if (this.y + this.height - this.avoidedCollisionRowsOnBottom > Game.renderHeight) {
            return false;
        }
        if (!this.stateHeightHalved && this.grounded) {
            for (int i = this.x + this.avoidedCollisionCols; i < this.x + this.width - this.avoidedCollisionCols; ++i) {
                if ((!this.game.level.getTileAtPixel(i, this.y + this.avoidedCollisionRowsOnTop).isPermeableTile() || !this.game.level.getTileAtPixel(i, this.y + 8 + this.avoidedCollisionRowsOnTop).isPermeableTile()) && this.game.level.getTileAtPixel(this.x + this.avoidedCollisionCols, this.y + this.avoidedCollisionRowsOnTop + 16).isPermeableTile() && this.game.level.getTileAtPixel(this.x + this.avoidedCollisionCols + 8, this.y + this.avoidedCollisionRowsOnTop + 16).isPermeableTile() && this.game.level.getTileAtPixel(this.x + this.avoidedCollisionCols, this.y + this.avoidedCollisionRowsOnTop + 24).isPermeableTile() && this.game.level.getTileAtPixel(this.x + this.avoidedCollisionCols + 8, this.y + this.avoidedCollisionRowsOnTop + 24).isPermeableTile() && this.game.level.getTileAtPixel(this.x + this.width - this.avoidedCollisionCols, this.y + this.avoidedCollisionRowsOnTop + 16).isPermeableTile() && this.game.level.getTileAtPixel(this.x + this.width - this.avoidedCollisionCols, this.y + this.avoidedCollisionRowsOnTop + 24).isPermeableTile()) {
                    this.verticalPinch = false;
                    this.xVel = 0.0;
                    return true;
                }
            }
        }
        return this.largeMario && this.wasPinched && this.topHalfSolid() && !this.grounded && (this.verticalPinch = true);
    }
    
    private void applyWaterXMotion(final double delta) {
        if (this.spriteState == 7 || this.spriteState == 8 || this.spriteState == 9 || this.spriteState == 1 || this.spriteState == 2 || this.spriteState == 3) {
            this.timeSinceLastWalkChange += delta;
        }
        if (this.wallPushing && this.game.input.rightDown) {
            this.xVel = Level.AUTO_SCROLL_VEL;
            this.wallPushing = false;
        }
        if (this.game.input.leftDown && !this.game.input.rightDown) {
            if (-this.xVel > Mario.MAX_WALKING_SPEED) {
                this.xVel = -Mario.MAX_WALKING_SPEED;
            }
            else if (this.grounded && this.xVel > Mario.MAX_WALKING_SPEED / 2.0) {
                this.xVel = Mario.MAX_WALKING_SPEED / 2.0;
            }
            else {
                this.xVel -= Mario.X_ACCELERATION_FRONT * delta / 1000.0;
            }
        }
        else if (!this.game.input.leftDown && this.game.input.rightDown) {
            if (this.xVel > Mario.MAX_WALKING_SPEED) {
                this.xVel = Mario.MAX_WALKING_SPEED;
            }
            else if (this.grounded && this.xVel < -Mario.MAX_WALKING_SPEED / 2.0) {
                this.xVel = -Mario.MAX_WALKING_SPEED / 2.0;
            }
            else {
                this.xVel += Mario.X_ACCELERATION_FRONT * delta / 1000.0;
            }
        }
        if (this.grounded) {
            if (this.spriteState == 11) {
                if (this.xVel < 0.0) {
                    this.xVel += Mario.CROUCHING_DECELERATION * delta / 1000.0;
                    if (this.xVel > 0.0) {
                        this.xVel = 0.0;
                    }
                }
                else if (this.xVel > 0.0) {
                    this.xVel -= Mario.CROUCHING_DECELERATION * delta / 1000.0;
                    if (this.xVel < 0.0) {
                        this.xVel = 0.0;
                    }
                }
                else {
                    this.xVel = 0.0;
                }
            }
            else if ((this.game.input.leftDown && this.game.input.rightDown) || (!this.game.input.leftDown && !this.game.input.rightDown)) {
                if (this.xVel < 0.0) {
                    this.xVel += Mario.SLOWING_DECELERATION * delta / 1000.0;
                    if (this.xVel > 0.0) {
                        this.xVel = 0.0;
                    }
                }
                else if (this.xVel > 0.0) {
                    this.xVel -= Mario.SLOWING_DECELERATION * delta / 1000.0;
                    if (this.xVel < 0.0) {
                        this.xVel = 0.0;
                    }
                }
            }
            if (this.yVel >= 0.0) {
                this.swimStroking = false;
            }
            if (this.xVel < 0.0 && this.game.input.rightDown) {
                this.skiddingRight = true;
                if (this.xVel < -Mario.MAX_WALKING_SPEED / 2.0) {
                    this.xVel = -Mario.MAX_WALKING_SPEED / 2.0;
                }
            }
            else if (this.xVel > 0.0 && this.game.input.leftDown) {
                this.skiddingLeft = true;
                if (this.xVel > Mario.MAX_WALKING_SPEED / 2.0) {
                    this.xVel = Mario.MAX_WALKING_SPEED / 2.0;
                }
            }
            else if (this.xVel > Mario.MAX_WALKING_SPEED / 4.0 * 3.0) {
                this.xVel = Mario.MAX_WALKING_SPEED / 4.0 * 3.0;
            }
            else if (this.xVel < -Mario.MAX_WALKING_SPEED / 4.0 * 3.0) {
                this.xVel = -Mario.MAX_WALKING_SPEED / 4.0 * 3.0;
            }
            if (this.xVel > 0.0) {
                this.flip = this.skiddingLeft;
            }
            else if (this.xVel < 0.0) {
                this.flip = !this.skiddingRight;
            }
            if (this.xVel >= 0.0) {
                this.skiddingRight = false;
            }
            if (this.xVel <= 0.0) {
                this.skiddingLeft = false;
            }
        }
        else if (this.game.input.leftDown && !this.game.input.rightDown) {
            this.flip = true;
        }
        else if (!this.game.input.leftDown && this.game.input.rightDown) {
            this.flip = false;
        }
        this.xPos += this.xVel * delta / 1000.0;
    }
    
    private void applyXMotion(final double delta) {
        if (this.spriteState == 7 || this.spriteState == 8 || this.spriteState == 9 || this.spriteState == 1 || this.spriteState == 2 || this.spriteState == 3) {
            this.timeSinceLastWalkChange += delta;
        }
        if (this.wallPushing && this.game.input.rightDown) {
            this.xVel = Level.AUTO_SCROLL_VEL;
            this.wallPushing = false;
        }
        if ((this.spriteState == 11 || (this.largeMario && this.game.input.downDown)) && this.grounded) {
            if (this.xVel < 0.0) {
                if (this.spriteState != 11) {
                    this.flip = true;
                }
                this.xVel += Mario.CROUCHING_DECELERATION * delta / 1000.0;
                if (this.xVel > 0.0) {
                    this.xVel = 0.0;
                }
            }
            else if (this.xVel > 0.0) {
                if (this.spriteState != 11) {
                    this.flip = false;
                }
                this.xVel -= Mario.CROUCHING_DECELERATION * delta / 1000.0;
                if (this.xVel < 0.0) {
                    this.xVel = 0.0;
                }
            }
            else {
                this.xVel = 0.0;
            }
        }
        else if (this.grounded) {
            if (this.game.input.runDown) {
                if ((this.game.input.leftDown && this.game.input.rightDown) || (!this.game.input.leftDown && !this.game.input.rightDown)) {
                    if (this.xVel < 0.0) {
                        if (!this.skiddingRight) {
                            this.flip = true;
                        }
                        this.xVel += Mario.SLOWING_DECELERATION * delta / 1000.0;
                        if (this.xVel > 0.0) {
                            this.xVel = 0.0;
                        }
                    }
                    else if (this.xVel > 0.0) {
                        if (!this.skiddingLeft) {
                            this.flip = false;
                        }
                        this.xVel -= Mario.SLOWING_DECELERATION * delta / 1000.0;
                        if (this.xVel < 0.0) {
                            this.xVel = 0.0;
                        }
                    }
                }
                else if (this.game.input.leftDown && !this.game.input.rightDown) {
                    this.flip = true;
                    if (this.xVel <= 0.0) {
                        this.xVel -= Mario.X_ACCELERATION_FRONT * delta / 1000.0;
                    }
                    else if (this.xVel > 0.0) {
                        this.skiddingLeft = true;
                        this.xVel -= Mario.SKID_DECELERATION * delta / 1000.0;
                        if (this.x < 0) {
                            this.xVel = 0.0;
                        }
                    }
                }
                else if (!this.game.input.leftDown && this.game.input.rightDown) {
                    this.flip = false;
                    if (this.xVel >= 0.0) {
                        this.xVel += Mario.X_ACCELERATION_FRONT * delta / 1000.0;
                    }
                    else if (this.xVel < 0.0) {
                        this.skiddingRight = true;
                        this.xVel += Mario.SKID_DECELERATION * delta / 1000.0;
                        if (this.xVel > 0.0) {
                            this.xVel = 0.0;
                        }
                    }
                }
            }
            else if (!this.game.input.runDown) {
                if ((this.game.input.leftDown && this.game.input.rightDown) || (!this.game.input.leftDown && !this.game.input.rightDown)) {
                    if (this.xVel < 0.0) {
                        if (!this.skiddingRight) {
                            this.flip = true;
                        }
                        this.xVel += Mario.SLOWING_DECELERATION * delta / 1000.0;
                        if (this.xVel > 0.0) {
                            this.xVel = 0.0;
                        }
                    }
                    else if (this.xVel > 0.0) {
                        if (!this.skiddingLeft) {
                            this.flip = false;
                        }
                        this.xVel -= Mario.SLOWING_DECELERATION * delta / 1000.0;
                        if (this.xVel < 0.0) {
                            this.xVel = 0.0;
                        }
                    }
                }
                else if (this.game.input.leftDown && !this.game.input.rightDown) {
                    this.flip = true;
                    if (this.xVel <= 48.0) {
                        if (-this.xVel > Mario.MAX_WALKING_SPEED) {
                            this.xVel += Mario.SLOWING_DECELERATION * delta / 1000.0;
                        }
                        else {
                            this.xVel -= Mario.X_ACCELERATION_FRONT * delta / 1000.0;
                        }
                    }
                    else if (this.xVel > 48.0) {
                        this.skiddingLeft = true;
                        this.xVel -= Mario.SKID_DECELERATION * delta / 1000.0;
                        if (this.xVel < 0.0) {
                            this.xVel = 0.0;
                        }
                    }
                }
                else if (!this.game.input.leftDown && this.game.input.rightDown) {
                    this.flip = false;
                    if (this.xVel >= -48.0) {
                        if (this.xVel > Mario.MAX_WALKING_SPEED) {
                            this.xVel -= Mario.SLOWING_DECELERATION * delta / 1000.0;
                        }
                        else {
                            this.xVel += Mario.X_ACCELERATION_FRONT * delta / 1000.0;
                        }
                    }
                    else if (this.xVel < -48.0) {
                        this.skiddingRight = true;
                        this.xVel += Mario.SKID_DECELERATION * delta / 1000.0;
                        if (this.xVel > 0.0) {
                            this.xVel = 0.0;
                        }
                    }
                }
                else if (!this.game.input.leftDown && !this.game.input.rightDown) {}
            }
        }
        else if (!this.grounded && !this.swimming) {
            if (this.game.input.leftDown && this.xVel > -Mario.MAX_WALKING_SPEED) {
                if (this.flip) {
                    this.xVel -= 240.0 * delta / 1000.0;
                }
                else {
                    this.xVel -= Mario.X_ACCELERATION_FRONT * delta / 1000.0;
                }
            }
            else if (this.game.input.rightDown && this.xVel < Mario.MAX_WALKING_SPEED) {
                if (!this.flip) {
                    this.xVel += 240.0 * delta / 1000.0;
                }
                else {
                    this.xVel += Mario.X_ACCELERATION_FRONT * delta / 1000.0;
                }
            }
        }
        if (this.xVel > Mario.MAX_RUNNING_SPEED) {
            this.xVel = Mario.MAX_RUNNING_SPEED;
        }
        else if (-this.xVel > Mario.MAX_RUNNING_SPEED) {
            this.xVel = -Mario.MAX_RUNNING_SPEED;
        }
        if (this.grounded && this.spriteState == 11 && Math.abs(this.xVel) < 1.0) {
            this.xVel = 0.0;
        }
        this.xPos += this.xVel * delta / 1000.0;
        if (this.game.input.rightDown || (this.skiddingLeft && this.xVel <= 0.0) || this.spriteState == 11 || !this.grounded) {
            this.skiddingLeft = false;
        }
        if (this.game.input.leftDown || (this.skiddingRight && this.xVel >= 0.0) || this.spriteState == 11 || !this.grounded) {
            this.skiddingRight = false;
        }
    }
    
    private void applyYForces(final double delta) {
        if (this.jumpHoldAllowed) {
            this.inJumpTime += delta;
            final double excess = this.inJumpTime - 500.0;
            if (excess >= 0.0) {
                this.inJumpTime = 500.0;
            }
            final double ratio = this.inJumpTime / 500.0;
            this.yPos = this.jumpBaseHeight - this.actualJumpHeightLimit * Math.sin(ratio * 1.5707963267948966);
            if (excess >= 0.0) {
                this.jumpStopped(true);
            }
            else {
                this.yVel = -Math.cos(ratio * 1.5707963267948966);
            }
        }
        else {
            this.applyGravity(delta, Level.GRAVITY);
        }
        if (this.springLaunched && this.yVel >= 0.0) {
            this.springLaunched = false;
        }
    }
    
    private boolean setBounds() {
        boolean correction = false;
        if (this.xPos <= this.game.level.leftMostX) {
            if (this.xVel < -Mario.MAX_WALKING_SPEED / 2.0) {
                this.xVel = -Mario.MAX_WALKING_SPEED / 2.0;
            }
            this.xPos = this.game.level.leftMostX;
            if (this.swimming && !this.grounded) {
                this.xVel = 0.0;
            }
            this.x = (int)this.xPos;
            if (this.game.level.autoScrollingLevel) {
                this.wallPushing = true;
            }
            correction = true;
        }
        else if (this.game.level.autoScrollingLevel && this.xPos + this.width >= this.game.level.leftMostX + Game.renderWidth) {
            if (this.xVel > Mario.MAX_WALKING_SPEED / 2.0) {
                this.xVel = Mario.MAX_WALKING_SPEED / 2.0;
            }
            this.xPos = this.game.level.leftMostX + Game.renderWidth - this.width;
            this.x = (int)Math.round(this.xPos);
            correction = true;
        }
        else if (this.xPos + this.width > this.game.level.xTiles * 8) {
            this.xPos = this.game.level.xTiles * 8 - this.width;
            if (this.swimming && !this.grounded) {
                this.xVel = 0.0;
            }
            if (this.xVel > Mario.MAX_WALKING_SPEED / 2.0) {
                this.xVel = Mario.MAX_WALKING_SPEED / 2.0;
            }
            this.x = (int)this.xPos;
            correction = true;
        }
        else if (this.game.level.bowserBattleOngoing && this.xPos + this.width > this.game.level.leftMostX + Game.renderWidth - 8) {
            this.xPos = this.game.level.leftMostX + Game.renderWidth - 8 - this.width;
        }
        else if (this.game.level.maxTravelX > 0 && this.yPos <= 0.0 && this.xPos + this.width - this.avoidedCollisionCols > this.game.level.maxTravelX) {
            this.xPos = this.game.level.maxTravelX - this.width + this.avoidedCollisionCols;
            this.xVel = 0.0;
        }
        if (this.yPos >= Game.yTiles * 8) {
            if (this.game.level.cliffDestLevel != -1) {
                this.transitioning = true;
                this.transitionState = 17;
                this.ticks = 0.0;
            }
            else {
                this.died(false, true);
                this.yPos = Game.yTiles * 8;
                this.y = (int)Math.round(this.yPos);
                correction = true;
                this.xVel = 0.0;
            }
        }
        return correction;
    }
    
    private void setState() {
        if (this.swimming && (this.swimStroking || !this.grounded)) {
            final int paddleMod = Math.abs(this.strokeState % 3);
            if (this.largeMario) {
                if (paddleMod == 0) {
                    this.spriteState = 19;
                }
                else if (paddleMod == 1) {
                    this.spriteState = 20;
                }
                else if (paddleMod == 2) {
                    this.spriteState = 21;
                }
            }
            else if (paddleMod == 0) {
                this.spriteState = 22;
            }
            else if (paddleMod == 1) {
                this.spriteState = 23;
            }
            else if (paddleMod == 2) {
                this.spriteState = 24;
            }
        }
        else if (this.spriteState == 10 && !this.inJump && this.yVel >= 0.0 && this.grounded) {
            this.spriteState = 6;
        }
        else if (this.spriteState == 5 && !this.inJump && this.yVel >= 0.0) {
            this.spriteState = 0;
        }
        else if ((this.inJump || this.pixelsToMove != 0.0) && this.spriteState != 11) {
            if (this.largeMario) {
                this.spriteState = 10;
            }
            else {
                this.spriteState = 5;
            }
        }
        else if (this.largeMario && this.game.input.downDown && this.grounded && (this.xVel == 0.0 || (!this.game.input.leftDown && !this.game.input.rightDown))) {
            this.spriteState = 11;
            if (this.xVel == 0.0 && (this.game.input.leftDown || this.game.input.rightDown)) {
                this.spriteState = 6;
            }
        }
        else if (!this.game.input.downDown && this.grounded && this.spriteState == 11) {
            this.spriteState = 6;
        }
        else if (!this.pinched && !this.skiddingLeft && !this.skiddingRight && this.grounded && this.spriteState != 11 && (this.xVel != 0.0 || this.game.input.leftDown || this.game.input.rightDown)) {
            if (this.largeMario && this.spriteState != 7 && this.spriteState != 8 && this.spriteState != 9) {
                this.spriteState = 7;
                this.timeSinceLastWalkChange = 0.0;
            }
            else if (!this.largeMario && this.spriteState != 1 && this.spriteState != 2 && this.spriteState != 3) {
                this.spriteState = 1;
                this.timeSinceLastWalkChange = 0.0;
            }
            if (this.xVel == 0.0 && this.game.input.leftDown && this.game.input.rightDown) {
                if (this.largeMario) {
                    this.spriteState = 6;
                }
                else {
                    this.spriteState = 0;
                }
            }
            else if (this.walkingSpeed() && this.timeSinceLastWalkChange >= 125.0) {
                this.timeSinceLastWalkChange %= 125.0;
                this.nextWalkImage();
            }
            else if (this.fullWalkingSpeed() && this.timeSinceLastWalkChange >= 70.0) {
                this.timeSinceLastWalkChange %= 70.0;
                this.nextWalkImage();
            }
            else if (this.runningSpeed() && this.timeSinceLastWalkChange >= 40.0) {
                this.timeSinceLastWalkChange %= 40.0;
                this.nextWalkImage();
            }
        }
        else if (this.skiddingRight || this.skiddingLeft) {
            if (this.grounded) {
                if (this.largeMario) {
                    this.spriteState = 12;
                }
                else {
                    this.spriteState = 13;
                }
            }
            else if (this.largeMario) {
                this.spriteState = 7;
            }
            else {
                this.spriteState = 1;
            }
        }
        else if (this.grounded && !this.game.input.leftDown && !this.game.input.rightDown && this.spriteState != 11 && this.xVel == 0.0) {
            if (this.largeMario) {
                this.spriteState = 6;
            }
            else {
                this.spriteState = 0;
            }
        }
        else if ((!this.grounded && this.spriteState == 6) || this.spriteState == 0) {
            if (this.largeMario) {
                this.spriteState = 9;
            }
            else {
                this.spriteState = 3;
            }
        }
        if (!this.largeMario || this.spriteState == 11) {
            this.stateHeightHalved = true;
        }
        else {
            this.stateHeightHalved = false;
        }
    }
    
    private boolean walkingSpeed() {
        final double vel = Math.abs(this.xVel);
        return (!this.swimming && vel < Mario.MAX_WALKING_SPEED - 3.0) || (this.swimming && Math.abs(this.xVel) < Mario.MAX_WALKING_SPEED / 4.0 * 3.0 - 3.0);
    }
    
    private boolean fullWalkingSpeed() {
        final double vel = Math.abs(this.xVel);
        return (!this.swimming && vel >= Mario.MAX_WALKING_SPEED - 3.0 && vel <= Mario.MAX_WALKING_SPEED + 3.0) || (this.swimming && vel >= Mario.MAX_WALKING_SPEED / 4.0 * 3.0 - 3.0 && vel <= Mario.MAX_WALKING_SPEED / 4.0 * 3.0 + 3.0);
    }
    
    private boolean runningSpeed() {
        final double vel = Math.abs(this.xVel);
        return vel > Mario.MAX_WALKING_SPEED + 3.0;
    }
    
    private void nextWalkImage() {
        if (this.largeMario) {
            if (this.spriteState == 7) {
                this.spriteState = 8;
            }
            else if (this.spriteState == 8) {
                this.spriteState = 9;
            }
            else if (this.spriteState == 9) {
                this.spriteState = 7;
            }
        }
        else if (this.spriteState == 1) {
            this.spriteState = 2;
        }
        else if (this.spriteState == 2) {
            this.spriteState = 3;
        }
        else if (this.spriteState == 3) {
            this.spriteState = 1;
        }
    }
    
    private void setImageIndex(final boolean translate) {
        if (this.spriteState == 6) {
            this.imageIndex = 0;
        }
        else if (this.spriteState == 10) {
            this.imageIndex = 1;
        }
        else if (this.spriteState == 11) {
            this.imageIndex = 14;
        }
        else if (this.spriteState == 0) {
            this.imageIndex = 15;
        }
        else if (this.spriteState == 5) {
            this.imageIndex = 16;
        }
        else if (this.spriteState == 7) {
            this.imageIndex = 2;
        }
        else if (this.spriteState == 8) {
            this.imageIndex = 3;
        }
        else if (this.spriteState == 9) {
            this.imageIndex = 4;
        }
        else if (this.spriteState == 12) {
            this.imageIndex = 11;
        }
        else if (this.spriteState == 1) {
            this.imageIndex = 17;
        }
        else if (this.spriteState == 2) {
            this.imageIndex = 18;
        }
        else if (this.spriteState == 3) {
            this.imageIndex = 19;
        }
        else if (this.spriteState == 13) {
            this.imageIndex = 26;
        }
        else if (this.spriteState == 14) {
            this.imageIndex = 30;
        }
        else if (this.spriteState == 15) {
            this.imageIndex = 27;
        }
        else if (this.spriteState == 16) {
            this.imageIndex = 28;
        }
        else if (this.spriteState == 17) {
            this.imageIndex = 12;
        }
        else if (this.spriteState == 18) {
            this.imageIndex = 13;
        }
        else if (this.spriteState == 22) {
            if (this.paddleSwitch) {
                this.imageIndex = 20;
            }
            else {
                this.imageIndex = 21;
            }
        }
        else if (this.spriteState == 19) {
            if (this.paddleSwitch) {
                this.imageIndex = 5;
            }
            else {
                this.imageIndex = 6;
            }
        }
        else if (this.spriteState == 23) {
            if (this.paddleSwitch) {
                this.imageIndex = 22;
            }
            else {
                this.imageIndex = 23;
            }
        }
        else if (this.spriteState == 20) {
            if (this.paddleSwitch) {
                this.imageIndex = 7;
            }
            else {
                this.imageIndex = 8;
            }
        }
        else if (this.spriteState == 24) {
            if (this.paddleSwitch) {
                this.imageIndex = 24;
            }
            else {
                this.imageIndex = 25;
            }
        }
        else if (this.spriteState == 21) {
            if (this.paddleSwitch) {
                this.imageIndex = 9;
            }
            else {
                this.imageIndex = 10;
            }
        }
        if (translate) {
            this.translateImageIndices();
        }
    }
    
    private void translateImageIndices() {
        if (this.hasFlowerPower) {
            this.translateToFlowerImage();
        }
        if (this.shooting) {
            if (this.imageIndex == 31 || this.imageIndex == 32 || this.imageIndex == 33) {
                this.imageIndex = 46;
            }
            else if (this.imageIndex == 34) {
                this.imageIndex = 47;
            }
            else if (this.imageIndex == 35) {
                this.imageIndex = 48;
            }
            else if (this.imageIndex == 42) {
                this.imageIndex = 49;
            }
            else if (this.imageIndex == 38 || this.imageIndex == 40) {
                this.imageIndex = 36;
            }
            else if (this.imageIndex == 39 || this.imageIndex == 41) {
                this.imageIndex = 37;
            }
        }
        if (this.hasStar && (!this.transitioning || (this.transitionState != 4 && this.transitionState != 1))) {
            if ((this.starTicks >= 9000.0 && this.starImageChangeTicks >= 150.0) || (this.starTicks < 9000.0 && this.starImageChangeTicks >= 35.0)) {
                if (this.starTicks < 11925.0) {
                    this.starImageIndex = (this.starImageIndex + 1.0) % 4.0;
                }
                if (this.starTicks < 9000.0) {
                    this.starImageChangeTicks -= 35.0;
                }
                else {
                    this.starImageChangeTicks -= 150.0;
                }
            }
            if (this.starImageIndex == 0.0) {
                this.imageIndex = this.starTranslation[this.imageIndex];
            }
            else if (this.starImageIndex != 1.0) {
                if (this.starImageIndex == 2.0) {
                    this.imageIndex = this.starTranslation[this.imageIndex] + 34;
                }
                else if (this.starImageIndex == 3.0) {
                    this.imageIndex = this.starTranslation[this.imageIndex] + 68;
                }
            }
        }
    }
    
    private void translateToFlowerImage() {
        if (this.imageIndex == 0) {
            this.imageIndex = 31;
        }
        else if (this.imageIndex == 1) {
            this.imageIndex = 32;
        }
        else if (this.imageIndex == 14) {
            this.imageIndex = 45;
        }
        else if (this.imageIndex == 2) {
            this.imageIndex = 33;
        }
        else if (this.imageIndex == 3) {
            this.imageIndex = 34;
        }
        else if (this.imageIndex == 4) {
            this.imageIndex = 35;
        }
        else if (this.imageIndex == 11) {
            this.imageIndex = 42;
        }
        else if (this.imageIndex == 12) {
            this.imageIndex = 43;
        }
        else if (this.imageIndex == 13) {
            this.imageIndex = 44;
        }
        else if (this.imageIndex == 5) {
            this.imageIndex = 36;
        }
        else if (this.imageIndex == 6) {
            this.imageIndex = 37;
        }
        else if (this.imageIndex == 7) {
            this.imageIndex = 38;
        }
        else if (this.imageIndex == 8) {
            this.imageIndex = 39;
        }
        else if (this.imageIndex == 9) {
            this.imageIndex = 40;
        }
        else if (this.imageIndex == 10) {
            this.imageIndex = 41;
        }
    }
    
    private void translateForFlowerCatch(final int type) {
        if (type == 0) {
            this.imageIndex = this.starTranslation[this.imageIndex];
        }
        else if (type == 1) {
            this.translateToFlowerImage();
        }
        else if (type == 2) {
            this.imageIndex = this.starTranslation[this.imageIndex] + 34;
        }
        else if (type == 3) {
            this.imageIndex = this.starTranslation[this.imageIndex] + 68;
        }
    }
    
    @Override
    public Rectangle getRectangle() {
        if (this.largeMario && this.spriteState != 11) {
            return new Rectangle(this.x + this.avoidedCollisionCols, this.y + this.avoidedCollisionRowsOnTop, this.tilesWidth * 8 - this.avoidedCollisionCols * 2, this.tilesHeight * 8 - this.avoidedCollisionRowsOnTop - this.avoidedCollisionRowsOnBottom);
        }
        if (!this.largeMario || this.spriteState == 11) {
            return new Rectangle(this.x + this.avoidedCollisionCols, this.y + this.tilesHeight * 8 / 2, this.tilesWidth * 8 - this.avoidedCollisionCols * 2, this.tilesHeight * 8 / 2 - this.avoidedCollisionRowsOnBottom);
        }
        return null;
    }
    
    static {
        Mario.MAX_JUMP_HEIGHT = 65.0;
        Mario.MAX_WALKING_SPEED = 96.0;
        Mario.MAX_WALL_PUSH_SPEED = Mario.MAX_WALKING_SPEED / 2.0;
        Mario.MAX_RUNNING_SPEED = 160.0;
        Mario.X_ACCELERATION_FRONT = 208.0;
        Mario.SLOWING_DECELERATION = 192.0;
        Mario.CROUCHING_DECELERATION = 288.0;
        Mario.SKID_DECELERATION = 400.0;
    }
}
