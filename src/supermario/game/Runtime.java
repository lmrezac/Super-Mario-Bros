// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game;

import supermario.Utilities;

public class Runtime extends Thread
{
    public static final double MIN_FRAME_DELAY = 10.0;
    public static final double MAX_FRAME_DELAY = 20.0;
    public static final double MOUSE_HIDE_DELAY = 2000.0;
	public static Runtime instance;
    private Game game;
    private boolean running;
    private boolean paused;
    
    public Runtime(final Game game) {
        this.game = game;
        instance = this;
    }
    
    public void stopGameLoop() {
        this.running = false;
        this.paused = false;
        synchronized (this) {
            this.notify();
        }
    }
    
    public boolean isPaused() {
        return this.paused;
    }
    public boolean isRunning(){ 
    	return this.running;
    }
    public void pause() {
        if (!this.running || this.paused) {
            throw new IllegalStateException("Can't pause a game loop that isn't running");
        }
        this.paused = true;
    }
    
    public void unpause() {
        if (!this.running || !this.paused) {
            throw new IllegalStateException("Can't resume a game loop that isn't paused");
        }
        this.paused = false;
        synchronized (this) {
            this.notify();
        }
    }
    
    @Override
    public void run() {
        this.running = true;
        double lastTime = System.nanoTime();
        while (this.running) {
            if (this.paused) {
                synchronized (this) {
                    try {
                        this.wait();
                        if (!this.running) {
                            return;
                        }
                    }
                    catch (InterruptedException ex) {}
                }
            }
            final double startTime = System.nanoTime();
            if ((startTime - this.game.input.lastMouseEvent) / 1000000.0 > 2000.0) {
                this.game.hideCursor();
            }
            double delta = (startTime - lastTime) / 1000000.0;
            if (delta > 20.0) {
                delta = 20.0;
            }
            this.game.update(delta);
            this.game.paint(this.game.getGraphics());
            if (this.game.takeSnapshot) {
                Utilities.takeSnapshot(this.game);
                this.game.audio.play(19);
                this.game.takingSnapshot = true;
                this.game.snapshotTrans = 0.9f;
                this.game.takeSnapshot = false;
            }
            final double delay = System.nanoTime() - startTime;
            lastTime = startTime;
            if (delay < 1.0E7) {
                final double sleepTimeNanos = 1.0E7 - delay;
                final int sleepMillis = (int)(sleepTimeNanos / 1000000.0);
                final int sleepNanos = (int)(sleepTimeNanos % 1000000.0);
                try {
                    Thread.sleep(sleepMillis, sleepNanos);
                }
                catch (InterruptedException ex2) {}
            }
        }
    }
}
