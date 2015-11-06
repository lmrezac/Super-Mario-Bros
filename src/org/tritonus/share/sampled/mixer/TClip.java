// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.mixer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;

import org.tritonus.share.TDebug;
@SuppressWarnings("unused")
public class TClip extends TDataLine implements Clip
{
    @SuppressWarnings("rawtypes")
	private static final Class[] CONTROL_CLASSES;
    private static final int BUFFER_FRAMES = 16384;
    
    public TClip(final DataLine.Info info) {
        super(null, info);
    }
    
    public TClip(final DataLine.Info info, final Collection<Control> controls) {
        super(null, info, controls);
    }
    
    public void open(final AudioFormat audioFormat, final byte[] abData, final int nOffset, final int nLength) throws LineUnavailableException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(abData, nOffset, nLength);
        final AudioInputStream audioInputStream = new AudioInputStream(bais, audioFormat, -1L);
        try {
            this.open(audioInputStream);
        }
        catch (IOException e) {
            if (TDebug.TraceAllExceptions) {
                TDebug.out(e);
            }
            throw new LineUnavailableException("IOException occured");
        }
    }
    
    public void open(final AudioInputStream audioInputStream) throws LineUnavailableException, IOException {
        final AudioFormat audioFormat = audioInputStream.getFormat();
        final DataLine.Info info = new DataLine.Info(Clip.class, audioFormat, -1);
    }
    
    public int getFrameLength() {
        return -1;
    }
    
    public long getMicrosecondLength() {
        return -1L;
    }
    
    public void setFramePosition(final int nPosition) {
    }
    
    public void setMicrosecondPosition(final long lPosition) {
    }
    
    public int getFramePosition() {
        return -1;
    }
    
    public long getMicrosecondPosition() {
        return -1L;
    }
    
    public void setLoopPoints(final int nStart, final int nEnd) {
    }
    
    public void loop(final int nCount) {
        if (TDebug.TraceClip) {
            TDebug.out("TClip.loop(int): called; count = " + nCount);
        }
        if (nCount == 0) {
            if (TDebug.TraceClip) {
                TDebug.out("TClip.loop(int): starting sample (once)");
            }
        }
        else if (TDebug.TraceClip) {
            TDebug.out("TClip.loop(int): starting sample (forever)");
        }
    }
    
    public void flush() {
    }
    
    public void drain() {
    }
    
    public void close() {
    }
    
    public void open() {
    }
    
    public void start() {
        if (TDebug.TraceClip) {
            TDebug.out("TClip.start(): called");
        }
        if (TDebug.TraceClip) {
            TDebug.out("TClip.start(): calling 'loop(0)' [hack]");
        }
        this.loop(0);
    }
    
    public void stop() {
    }
    
    public int available() {
        return -1;
    }
    
    static {
        CONTROL_CLASSES = new Class[0];
    }
}
