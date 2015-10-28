// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.mixer;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import org.tritonus.share.TDebug;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Line;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.Mixer;

public class TSoftClip extends TClip implements Runnable
{
    private static final Class[] CONTROL_CLASSES;
    private static final int BUFFER_SIZE = 16384;
    private Mixer m_mixer;
    private SourceDataLine m_line;
    private byte[] m_abClip;
    private int m_nRepeatCount;
    private Thread m_thread;
    
    public TSoftClip(final Mixer mixer, final AudioFormat format) throws LineUnavailableException {
        super(null);
        this.m_mixer = mixer;
        final DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        this.m_line = (SourceDataLine)AudioSystem.getLine(info);
    }
    
    public void open(final AudioInputStream audioInputStream) throws LineUnavailableException, IOException {
        final AudioFormat audioFormat = audioInputStream.getFormat();
        this.setFormat(audioFormat);
        final int nFrameSize = audioFormat.getFrameSize();
        if (nFrameSize < 1) {
            throw new IllegalArgumentException("frame size must be positive");
        }
        if (TDebug.TraceClip) {
            TDebug.out("TSoftClip.open(): format: " + audioFormat);
        }
        final byte[] abData = new byte[16384];
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int nBytesRead = 0;
        while (nBytesRead != -1) {
            try {
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
            }
            catch (IOException e) {
                if (TDebug.TraceClip || TDebug.TraceAllExceptions) {
                    TDebug.out(e);
                }
            }
            if (nBytesRead >= 0) {
                if (TDebug.TraceClip) {
                    TDebug.out("TSoftClip.open(): Trying to write: " + nBytesRead);
                }
                baos.write(abData, 0, nBytesRead);
                if (!TDebug.TraceClip) {
                    continue;
                }
                TDebug.out("TSoftClip.open(): Written: " + nBytesRead);
            }
        }
        this.m_abClip = baos.toByteArray();
        this.setBufferSize(this.m_abClip.length);
        this.m_line.open(this.getFormat());
    }
    
    public int getFrameLength() {
        if (this.isOpen()) {
            return this.getBufferSize() / this.getFormat().getFrameSize();
        }
        return -1;
    }
    
    public long getMicrosecondLength() {
        if (this.isOpen()) {
            return (long)(this.getFrameLength() * this.getFormat().getFrameRate() * 1000000.0f);
        }
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
            TDebug.out("TSoftClip.loop(int): called; count = " + nCount);
        }
        this.m_nRepeatCount = nCount;
        (this.m_thread = new Thread(this)).start();
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
            TDebug.out("TSoftClip.start(): called");
        }
        if (TDebug.TraceClip) {
            TDebug.out("TSoftClip.start(): calling 'loop(0)' [hack]");
        }
        this.loop(0);
    }
    
    public void stop() {
    }
    
    public int available() {
        return -1;
    }
    
    public void run() {
        while (this.m_nRepeatCount >= 0) {
            this.m_line.write(this.m_abClip, 0, this.m_abClip.length);
            --this.m_nRepeatCount;
        }
    }
    
    static {
        CONTROL_CLASSES = new Class[0];
    }
}
