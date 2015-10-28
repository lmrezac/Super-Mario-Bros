// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game;

import java.io.ByteArrayOutputStream;
import java.io.BufferedInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.DataLine;
import java.io.InputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayInputStream;
import supermario.Utilities;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.Control;
import javax.sound.sampled.FloatControl;

public final class Audio
{
    private Game game;
    private static final int DEFAULT_REDUNDANCIES = 1;
    public static final float MAX_VOLUME = 1.0f;
    private boolean validModule;
    private double musicCompletionRatio;
    private ClipPool[] clipPools;
    private DataLineAudio music;
    private DataLineAudio[] dataLinePools;
    private InitThread initThread;
    private boolean mute;
    private float volume;
    private float unmuteVolume;
    public static final int EXTRA_LIFE = 0;
    public static final int POWER_UP = 1;
    public static final int LARGE_JUMP = 2;
    public static final int SMALL_JUMP = 3;
    public static final int PAUSE = 4;
    public static final int POWER_DOWN_AND_PIPE = 5;
    public static final int BRICK_BREAK = 6;
    public static final int SMUSH = 7;
    public static final int BLOCK_BUMP = 8;
    public static final int FRIEND_GROW = 9;
    public static final int SHELL_KICK_AND_BUMP_KILL = 10;
    public static final int BEANSTALK_GROW = 11;
    public static final int COIN_COLLECT = 12;
    public static final int BULLET_FIRED = 13;
    public static final int FIREBALL = 14;
    public static final int FLAGPOLE = 15;
    public static final int BOWSER_FLAME = 16;
    public static final int BOWSER_DEATH = 17;
    public static final int EXCESS_TIME = 18;
    public static final int SNAPSHOT = 19;
    public static final int CLIP_ARRAY_SIZE = 20;
    public static final int GAME_OVER = 0;
    public static final int LEVEL_FINISHED = 1;
    public static final int CASTLE_FINISHED = 2;
    public static final int GAME_FINISHED = 3;
    public static final int MUSIC_STAR = 4;
    public static final int MUSIC_UNDERWATER = 5;
    public static final int MUSIC_OVERWORLD = 6;
    public static final int DEATH = 7;
    public static final int MUSIC_UNDERWORLD = 8;
    public static final int MUSIC_CASTLE = 9;
    public static final int TIME_CRISIS = 10;
    public static final int MUSIC_STAR_FAST = 11;
    public static final int MUSIC_OVERWORLD_FAST = 12;
    public static final int MUSIC_UNDERWORLD_FAST = 13;
    public static final int MUSIC_CASTLE_FAST = 14;
    public static final int MUSIC_UNDERWATER_FAST = 15;
    public static final int LINE_ARRAY_SIZE = 16;
    public static final int MINIMUM_SOUND_INTERVAL_ON_REPEAT_NANO = 50000000;
    
    public Audio(final Game game) {
        this.game = game;
        this.validModule = true;
        (this.initThread = new InitThread()).start();
    }
    
    public boolean isValid() {
        return this.validModule;
    }
    
    private void init() {
        try {
            (this.clipPools = new ClipPool[20])[0] = new ClipPool("effects/extraLife", 2);
            this.clipPools[13] = new ClipPool("effects/bullet", 3);
            this.clipPools[1] = new ClipPool("effects/powerUp", 1);
            this.clipPools[2] = new ClipPool("effects/largeJump", 2);
            this.clipPools[3] = new ClipPool("effects/smallJump", 2);
            this.clipPools[4] = new ClipPool("effects/pause", 1);
            this.clipPools[5] = new ClipPool("effects/powerDownAndPipe", 1);
            this.clipPools[6] = new ClipPool("effects/brickBreak", 2);
            this.clipPools[7] = new ClipPool("effects/smush", 1);
            this.clipPools[8] = new ClipPool("effects/blockBump", 1);
            this.clipPools[9] = new ClipPool("effects/friendGrow", 1);
            this.clipPools[10] = new ClipPool("effects/shellKickAndBumpKill", 3);
            this.clipPools[12] = new ClipPool("effects/coinCollect", 3);
            this.clipPools[11] = new ClipPool("effects/beanstalk", 1);
            this.clipPools[14] = new ClipPool("effects/fireball", 3);
            this.clipPools[15] = new ClipPool("effects/flagpole", 1);
            this.clipPools[17] = new ClipPool("effects/bowserDeath", 1);
            this.clipPools[16] = new ClipPool("effects/flame", 1);
            this.clipPools[18] = new ClipPool("effects/excessTime", 1);
            this.clipPools[19] = new ClipPool("effects/snapshot", 1);
            (this.dataLinePools = new DataLineAudio[16])[0] = new DataLineAudio("music/gameOver");
            this.dataLinePools[7] = new DataLineAudio("music/death");
            this.dataLinePools[6] = new DataLineAudio("music/overworld");
            this.dataLinePools[12] = new DataLineAudio("music/overworldFast");
            this.dataLinePools[8] = new DataLineAudio("music/underworld");
            this.dataLinePools[13] = new DataLineAudio("music/underworldFast");
            this.dataLinePools[9] = new DataLineAudio("music/castle");
            this.dataLinePools[14] = new DataLineAudio("music/castleFast");
            this.dataLinePools[1] = new DataLineAudio("music/levelFinished");
            this.dataLinePools[2] = new DataLineAudio("music/castleFinished");
            this.dataLinePools[3] = new DataLineAudio("music/gameFinished");
            this.dataLinePools[4] = new DataLineAudio("music/star");
            this.dataLinePools[11] = new DataLineAudio("music/starFast");
            this.dataLinePools[5] = new DataLineAudio("music/underwater");
            this.dataLinePools[15] = new DataLineAudio("music/underwaterFast");
            this.dataLinePools[10] = new DataLineAudio("music/time");
        }
        catch (Exception e) {
            this.validModule = false;
            this.dataLinePools = null;
            this.clipPools = null;
            e.printStackTrace();
        }
    }
    
    public void dispose() {
        if (!this.validModule) {
            return;
        }
        if (this.music != null) {
            try {
                this.stopMusic(false);
            }
            catch (Exception ex) {}
        }
        for (int i = 0; i < this.clipPools.length; ++i) {
            if (this.clipPools[i] != null) {
                this.clipPools[i].stop();
            }
        }
        for (int i = 0; i < this.dataLinePools.length; ++i) {
            if (this.dataLinePools[i] != null) {
                this.dataLinePools[i].stop();
            }
        }
    }
    
    public void storeMusicCompletionRatio() {
        if (!this.validModule || this.music == null || !this.music.isPlaying()) {
            return;
        }
        this.musicCompletionRatio = this.music.getCompletionRatio();
    }
    
    public void resetMusicCompletionRatio() {
        this.musicCompletionRatio = 0.0;
    }
    
    public void switchToFastMusic(final boolean resumeMusicPosition) {
        if (!this.validModule) {
            return;
        }
        int newMusicValue = 0;
        final int l = this.game.level.levelType;
        if (this.game.mario.hasStar()) {
            newMusicValue = 11;
            this.storeMusicCompletionRatio();
            if (!this.dataLinePools[11].isPlaying()) {
                this.stopMusic(false);
            }
        }
        else if (l == 2) {
            newMusicValue = 14;
        }
        else if (l == 5 || l == 6) {
            newMusicValue = 11;
        }
        else if (l == 0 || l == 4) {
            newMusicValue = 12;
        }
        else if (l == 1) {
            newMusicValue = 13;
        }
        else if (l == 3) {
            newMusicValue = 15;
        }
        if (!this.dataLinePools[11].isPlaying()) {
            this.loopMusic(newMusicValue, resumeMusicPosition);
        }
    }
    
    public void loopMusic(final int type, final boolean resumeMusicPosition) {
        if (!this.validModule) {
            return;
        }
        this.music = this.dataLinePools[type];
        if (resumeMusicPosition) {
            this.music.loop(this.musicCompletionRatio);
        }
        else {
            this.music.loop(0.0);
        }
    }
    
    public void playMusic(final int type) {
        if (!this.validModule) {
            return;
        }
        (this.music = this.dataLinePools[type]).play(0.0);
    }
    
    public void stopMusic(final boolean restart) {
        if (!this.validModule || this.music == null) {
            return;
        }
        this.music.stop();
        this.music = null;
        if (restart) {
            this.game.level.playLevelMusic();
        }
    }
    public void haltMusic(){
    	this.music.stop();
    	
    	this.game.level.playLevelMusic();
    }
    
    public void stopMusic(final int type) {
        if (!this.validModule) {
            return;
        }
        this.dataLinePools[type].stop();
        //this.music.stop();
    }
    
    public void stopAllSounds() {
        if (!this.validModule) {
            return;
        }
        for (int i = 0; i < this.clipPools.length; ++i) {
            this.clipPools[i].stop();
        }
        for (int i = 0; i < this.dataLinePools.length; ++i) {
            this.dataLinePools[i].stop();
        }
        this.music = null;
    }
    
    public boolean isMuted() {
        return this.mute;
    }
    
    public void muteAudio() {
        if (!this.validModule) {
            return;
        }
        this.unmuteVolume = this.volume;
        this.mute = true;
        this.game.input.writeConfiguration();
        this.setVolume(0.0f);
    }
    
    public void unmuteAudio() {
        if (!this.validModule) {
            return;
        }
        this.mute = false;
        this.game.input.writeConfiguration();
        this.setVolume(this.unmuteVolume);
        this.play(12);
    }
    
    public void setVolume(final float newVolume) {
        if (newVolume < 0.0f || newVolume > 1.0f) {
            throw new IllegalArgumentException("Volume must be between 0 and 1 inclusive.");
        }
        if (newVolume == 0.0f) {
            this.mute = true;
            this.unmuteVolume = this.volume;
        }
        else if (this.mute) {
            this.mute = false;
            this.unmuteVolume = newVolume;
        }
        this.volume = newVolume;
        for (int i = 0; i < this.clipPools.length; ++i) {
            for (int j = 0; j < this.clipPools[i].clips.length; ++j) {
                final FloatControl control = (FloatControl)this.clipPools[i].clips[j].getControl(FloatControl.Type.MASTER_GAIN);
                control.setValue((float)(Math.log(newVolume) / Math.log(10.0)) * 20.0f);
            }
        }
        for (int i = 0; i < this.dataLinePools.length; ++i) {
            if (this.dataLinePools[i].dataLine != null) {
                final FloatControl control2 = (FloatControl)this.dataLinePools[i].dataLine.getControl(FloatControl.Type.MASTER_GAIN);
                control2.setValue((float)(Math.log(newVolume) / Math.log(10.0)) * 20.0f);
                this.dataLinePools[i].dataLine.flush();
            }
        }
        this.game.input.writeConfiguration();
    }
    
    public double getVolume() {
        return this.volume;
    }
    
    public void pauseGameSounds() {
        if (!this.validModule) {
            return;
        }
        for (int i = 0; i < this.clipPools.length; ++i) {
            if (i != 4) {
                this.clipPools[i].stop();
            }
        }
        for (int i = 0; i < this.dataLinePools.length; ++i) {
            if (i != 7) {
                this.dataLinePools[i].pause();
            }
        }
    }
    
    public void resumeGameSounds() {
        if (!this.validModule) {
            return;
        }
        for (int i = 0; i < this.clipPools.length; ++i) {
            this.clipPools[i].resume();
        }
        for (int i = 0; i < this.dataLinePools.length; ++i) {
            this.dataLinePools[i].resume();
        }
    }
    
    public void play(final int type) {
        if (!this.validModule) {
            return;
        }
        this.clipPools[type].play();
    }
    
    public void stop(final int type) {
        if (!this.validModule) {
            return;
        }
        if (type < 0 || type > this.clipPools.length) {
            throw new RuntimeException("Clip value out of range: " + type);
        }
        this.clipPools[type].stop();
    }
    
    public boolean doneLoading() {
        return this.initThread.done;
    }
    
    private class DataLineAudio
    {
        private volatile boolean loopAudio;
        private volatile boolean paused;
        private byte[] audioBytes;
        private SourceDataLine dataLine;
        private AudioInputStream audioInputStream;
        private AudioFormat format;
        private volatile int totalBytesRead;
        private final int totalBytes;
        
        public DataLineAudio(final String filename) throws Exception {
            Utilities.decrypt(this.audioBytes = Utilities.getBytes("sounds/" + filename, Game.class));
            this.totalBytes = this.calcTotalBytes();
        }
        
        private int calcTotalBytes() throws Exception {
            this.audioInputStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(this.audioBytes));
            this.format = this.audioInputStream.getFormat();
            this.audioInputStream = AudioSystem.getAudioInputStream(this.format, this.audioInputStream);
            this.format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, this.format.getSampleRate(), 16, this.format.getChannels(), this.format.getChannels() * 2, this.format.getSampleRate(), false);
            final DataLine.Info info = new DataLine.Info(SourceDataLine.class, this.format);
            (this.dataLine = (SourceDataLine)AudioSystem.getLine(info)).open(this.format);
            this.dataLine.start();
            final byte[] buffer = new byte[2048];
            int bytesCount = 0;
            for (int bytesRead = 0; bytesRead != -1; bytesRead = this.audioInputStream.read(buffer), bytesCount += bytesRead) {}
            this.dataLine.drain();
            this.dataLine.close();
            this.audioInputStream.close();
            return bytesCount;
        }
        
        private double getCompletionRatio() {
            return this.totalBytesRead / this.totalBytes;
        }
        
        private boolean isPlaying() {
            return this.dataLine != null && this.dataLine.isRunning();
        }
        
        private void setVolume() {
            if (this.dataLine == null) {
                return;
            }
            final FloatControl control = (FloatControl)this.dataLine.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue((float)(Math.log(Audio.this.volume) / Math.log(10.0)) * 20.0f);
        }
        
        private void pause() {
            this.paused = true;
            this.dataLine.stop();
        }
        
        private void resume() {
            this.paused = false;
            this.dataLine.start();
            synchronized (this) {
                this.notify();
            }
        }
        
        private void stop() {
            this.loopAudio = false;
            this.paused = false;
            this.dataLine.stop();
            this.dataLine.flush();
            this.dataLine.close();
            synchronized (this) {
                this.notify();
            }
        }
        
        private void loop(final double ratio) {
            this.loopAudio = true;
            this.play(ratio);
        }
        
        private void play(final double startingPercentage) {
            try {
                this.audioInputStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(this.audioBytes));
                this.format = this.audioInputStream.getFormat();
                this.format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, this.format.getSampleRate(), 16, this.format.getChannels(), this.format.getChannels() * 2, this.format.getSampleRate(), false);
                this.audioInputStream = AudioSystem.getAudioInputStream(this.format, this.audioInputStream);
                final DataLine.Info info = new DataLine.Info(SourceDataLine.class, this.format);
                (this.dataLine = (SourceDataLine)AudioSystem.getLine(info)).open(this.format);
                this.setVolume();
                this.dataLine.start();
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            final byte[] buffer = new byte[1024];
                            DataLineAudio.this.totalBytesRead = 0;
                            int bytesRead = 0;
                            if (startingPercentage > 0.0) {
                                for (int bytesCount = 0; bytesCount < startingPercentage * DataLineAudio.this.totalBytes; bytesCount += DataLineAudio.this.audioInputStream.read(buffer)) {}
                            }
                            while (bytesRead != -1 && DataLineAudio.this.dataLine.isOpen()) {
                                bytesRead = DataLineAudio.this.audioInputStream.read(buffer);
                                DataLineAudio.this.totalBytesRead += bytesRead;
                                if (bytesRead >= 0) {
                                    DataLineAudio.this.dataLine.write(buffer, 0, bytesRead);
                                }
                                if (DataLineAudio.this.paused) {
                                    synchronized (DataLineAudio.this) {
                                        DataLineAudio.this.wait();
                                    }
                                }
                            }
                            DataLineAudio.this.dataLine.drain();
                            DataLineAudio.this.dataLine.close();
                            DataLineAudio.this.audioInputStream.close();
                            if (DataLineAudio.this.loopAudio) {
                                DataLineAudio.this.play(0.0);
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
            catch (Exception ex) {}
        }
    }
    
    private class ClipPool
    {
        private Clip[] clips;
        private final int MINIMUM_SOUND_INTERVAL_ON_REPEAT_NANO = 50000000;
        private long soundRepeatInterval;
        
        public ClipPool(final String filename, final int redundancies) throws Exception {
            this.clips = new Clip[redundancies];
            for (int i = 0; i < this.clips.length; ++i) {
                this.clips[i] = this.getClip(filename);
            }
            this.soundRepeatInterval = System.nanoTime();
        }
        
        private Clip getClip(final String filename) {
            Clip clip = null;
            try {
                clip = AudioSystem.getClip();
                byte[] audioBytes = Utilities.getBytes("sounds/" + filename, Game.class);
                Utilities.decrypt(audioBytes);
                final InputStream bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(audioBytes));
                final AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
                final AudioFormat baseFormat = audioInputStream.getFormat();
                final AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
                final AudioInputStream oggStream = AudioSystem.getAudioInputStream(decodedFormat, audioInputStream);
                
                //AudioSystem.write(oggStream, AudioFileFormat.Type.WAVE, new File(System.getProperty("user.dir")+"\\sounds\\"+filename+".wav"));
                
                final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                int b;
                for (b = 0, b = oggStream.read(); b != -1; b = oggStream.read()) {
                    outputStream.write(b);
                }
                audioBytes = outputStream.toByteArray();
                clip.open(decodedFormat, audioBytes, 0, audioBytes.length);
                audioBytes = null;
                outputStream.close();
                oggStream.close();
                audioInputStream.close();
                bufferedInputStream.close();
            }
            catch (Exception e) {
                e.printStackTrace();
                Audio.this.validModule = false;
                throw new RuntimeException("Problem with: " + filename);
            }
            return clip;
        }
        
        public void play() {
            final long currentTime = System.nanoTime();
            if (currentTime - this.soundRepeatInterval < 50000000L) {
                return;
            }
            this.soundRepeatInterval = currentTime;
            boolean played = false;
            int highestPosition = 0;
            int highestIndex = 0;
            for (int i = 0; i < this.clips.length; ++i) {
                if (!this.clips[i].isActive()) {
                    played = true;
                    this.playClip(i);
                    break;
                }
                if (this.clips[i].getFramePosition() >= highestPosition) {
                    highestPosition = this.clips[i].getFramePosition();
                    highestIndex = i;
                }
            }
            if (!played) {
                this.playClip(highestIndex);
            }
        }
        
        private void playClip(final int index) {
            this.clips[index].stop();
            this.clips[index].setFramePosition(0);
            this.clips[index].start();
        }
        
        public void stop() {
            for (int i = 0; i < this.clips.length; ++i) {
                this.clips[i].stop();
                this.clips[i].flush();
                this.clips[i].setFramePosition(0);
            }
        }
        
        public void resume() {
            for (int i = 0; i < this.clips.length; ++i) {
                if (this.clips[i].getFramePosition() > 0 && this.clips[i].getFramePosition() < this.clips[i].getFrameLength()) {
                    this.clips[i].start();
                }
            }
        }
    }
    
    private class InitThread extends Thread
    {
        public volatile boolean done;
        
        @Override
        public void run() {
            Audio.this.init();
            synchronized (Audio.this) {
                try {
                    this.done = true;
                    Audio.this.notify();
                    Audio.this.wait();
                }
                catch (InterruptedException ex) {}
            }
            Audio.this.setVolume(Audio.this.game.input.loadedVolume);
            if (Audio.this.isMuted()) {
                Audio.this.unmuteVolume = 1.0f;
            }
        }
    }
}
