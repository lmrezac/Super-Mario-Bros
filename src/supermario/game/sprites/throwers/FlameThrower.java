// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game.sprites.throwers;

import javax.swing.ImageIcon;

import supermario.game.Game;
import supermario.game.interfaces.EnemyThrower;
import supermario.game.sprites.enemies.FlameBreath;

public class FlameThrower implements EnemyThrower
{
    private Game game;
    public static final int MIN_DELAY_BEFORE_FLAME = 2000;
    public static final int MAX_DELAY_BEFORE_FLAME = 3500;
    private double ticks;
    private double throwDelay;
    
    public FlameThrower(final Game game) {
        this.game = game;
        this.schedule();
    }
    
    @Override
    public void update(final double delta) {
        if (this.game.level.leftMostX + Game.renderWidth / 2 < this.game.level.tiles[0].length * 8 / 2) {
            return;
        }
        if (this.game.level.bowserBattleOngoing) {
            return;
        }
        this.ticks += delta;
        if (this.ticks >= this.throwDelay) {
            this.launch();
            this.schedule();
        }
    }
    
    @Override
    public void schedule() {
        this.ticks = 0.0;
        this.throwDelay = this.game.rand.nextInt(1501) + 2000;
    }
    
    @Override
    public void launch() {
        this.game.audio.play(16);
        this.game.level.spritesToAdd.add(new FlameBreath(this.game, new ImageIcon[] { this.game.textures.flame1, this.game.textures.flame2 }, null));
    }
}
