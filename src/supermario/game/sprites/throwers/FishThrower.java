// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.throwers;

import supermario.game.sprites.enemies.RedFish;
import supermario.game.Game;
import supermario.game.interfaces.EnemyThrower;

public class FishThrower implements EnemyThrower
{
    private Game game;
    private double ticks;
    private double nextDelayTicks;
    private double throwDelay1;
    private double throwDelay2;
    private double throwDelay3;
    private boolean throw1Done;
    private boolean throw2Done;
    private boolean throw3Done;
    private boolean waitingForNextVolley;
    public static final int LEFT_MOST_X_BEFORE_FISH_START;
    public static final int MIN_DELAY_BEFORE_THROW = 0;
    public static final int MAX_DELAY_BEFORE_THROW = 2000;
    public static final int VOLLEY_DELAY = 2500;
    
    public FishThrower(final Game game) {
        this.game = game;
        this.schedule();
    }
    
    @Override
    public void update(final double delta) {
        if (this.game.level.leftMostX < FishThrower.LEFT_MOST_X_BEFORE_FISH_START) {
            return;
        }
        if ((this.game.level.maxTravelX >= 0 && this.game.level.leftMostX + Game.renderWidth >= this.game.level.maxTravelX) || this.game.level.levelEndPresent) {
            return;
        }
        this.ticks += delta;
        if (!this.waitingForNextVolley) {
            if (this.ticks >= this.throwDelay1 && !this.throw1Done) {
                this.launch();
                this.throw1Done = true;
            }
            if (this.ticks >= this.throwDelay2 && !this.throw2Done) {
                this.launch();
                this.throw2Done = true;
            }
            if (this.ticks >= this.throwDelay3 && !this.throw3Done) {
                this.launch();
                this.throw3Done = true;
            }
        }
        else {
            this.nextDelayTicks += delta;
            if (this.nextDelayTicks > 2500.0) {
                this.schedule();
            }
        }
        if (this.throw1Done && this.throw2Done && this.throw3Done && !this.waitingForNextVolley) {
            this.waitingForNextVolley = true;
            this.nextDelayTicks = 0.0;
        }
    }
    
    @Override
    public void launch() {
        this.game.level.spritesToAdd.add(new RedFish(this.game, this.game.textures.getRedFishTextures(), false, true));
    }
    
    @Override
    public void schedule() {
        this.waitingForNextVolley = false;
        this.ticks = 0.0;
        this.throw1Done = false;
        this.throw2Done = false;
        this.throw3Done = false;
        this.throwDelay1 = this.game.rand.nextInt(2001) + 0;
        this.throwDelay2 = this.game.rand.nextInt(2001) + 0;
        this.throwDelay2 = this.game.rand.nextInt(2001) + 0;
    }
    
    static {
        LEFT_MOST_X_BEFORE_FISH_START = Game.renderWidth / 4;
    }
}
