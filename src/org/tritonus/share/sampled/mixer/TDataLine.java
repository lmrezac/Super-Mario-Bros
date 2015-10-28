// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.mixer;

import javax.sound.sampled.LineEvent;
import org.tritonus.share.TDebug;
import javax.sound.sampled.Control;
import java.util.Collection;
import javax.sound.sampled.Line;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;

public abstract class TDataLine extends TLine implements DataLine
{
    private static final int DEFAULT_BUFFER_SIZE = 128000;
    private AudioFormat m_format;
    private int m_nBufferSize;
    private boolean m_bRunning;
    
    public TDataLine(final TMixer mixer, final DataLine.Info info) {
        super(mixer, info);
        this.init(info);
    }
    
    public TDataLine(final TMixer mixer, final DataLine.Info info, final Collection<Control> controls) {
        super(mixer, info, controls);
        this.init(info);
    }
    
    private void init(final DataLine.Info info) {
        this.m_format = null;
        this.m_nBufferSize = -1;
        this.setRunning(false);
    }
    
    public void start() {
        if (TDebug.TraceSourceDataLine) {
            TDebug.out("TDataLine.start(): called");
        }
        this.setRunning(true);
    }
    
    public void stop() {
        if (TDebug.TraceSourceDataLine) {
            TDebug.out("TDataLine.stop(): called");
        }
        this.setRunning(false);
    }
    
    public boolean isRunning() {
        return this.m_bRunning;
    }
    
    protected void setRunning(final boolean bRunning) {
        final boolean bOldValue = this.isRunning();
        this.m_bRunning = bRunning;
        if (bOldValue != this.isRunning()) {
            if (this.isRunning()) {
                this.startImpl();
                this.notifyLineEvent(LineEvent.Type.START);
            }
            else {
                this.stopImpl();
                this.notifyLineEvent(LineEvent.Type.STOP);
            }
        }
    }
    
    protected void startImpl() {
    }
    
    protected void stopImpl() {
    }
    
    public boolean isActive() {
        return this.isRunning();
    }
    
    public AudioFormat getFormat() {
        return this.m_format;
    }
    
    protected void setFormat(final AudioFormat format) {
        if (TDebug.TraceDataLine) {
            TDebug.out("TDataLine.setFormat(): setting: " + format);
        }
        this.m_format = format;
    }
    
    public int getBufferSize() {
        return this.m_nBufferSize;
    }
    
    protected void setBufferSize(final int nBufferSize) {
        if (TDebug.TraceDataLine) {
            TDebug.out("TDataLine.setBufferSize(): setting: " + nBufferSize);
        }
        this.m_nBufferSize = nBufferSize;
    }
    
    public int getFramePosition() {
        return -1;
    }
    
    public long getLongFramePosition() {
        return -1L;
    }
    
    public long getMicrosecondPosition() {
        return (long)(this.getFramePosition() * this.getFormat().getFrameRate() * 1000000.0f);
    }
    
    public float getLevel() {
        return -1.0f;
    }
    
    protected void checkOpen() {
        if (this.getFormat() == null) {
            throw new IllegalStateException("format must be specified");
        }
        if (this.getBufferSize() == -1) {
            this.setBufferSize(this.getDefaultBufferSize());
        }
    }
    
    protected int getDefaultBufferSize() {
        return 128000;
    }
    
    protected void notifyLineEvent(final LineEvent.Type type) {
        this.notifyLineEvent(new LineEvent(this, type, this.getFramePosition()));
    }
}
