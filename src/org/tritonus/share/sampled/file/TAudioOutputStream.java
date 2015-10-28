// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.file;

import java.io.IOException;
import org.tritonus.share.TDebug;
import javax.sound.sampled.AudioFormat;

public abstract class TAudioOutputStream implements AudioOutputStream
{
    private AudioFormat m_audioFormat;
    private long m_lLength;
    private long m_lCalculatedLength;
    private TDataOutputStream m_dataOutputStream;
    private boolean m_bDoBackPatching;
    private boolean m_bHeaderWritten;
    
    protected TAudioOutputStream(final AudioFormat audioFormat, final long lLength, final TDataOutputStream dataOutputStream, final boolean bDoBackPatching) {
        this.m_audioFormat = audioFormat;
        this.m_lLength = lLength;
        this.m_lCalculatedLength = 0L;
        this.m_dataOutputStream = dataOutputStream;
        this.m_bDoBackPatching = bDoBackPatching;
        this.m_bHeaderWritten = false;
    }
    
    public AudioFormat getFormat() {
        return this.m_audioFormat;
    }
    
    public long getLength() {
        return this.m_lLength;
    }
    
    public long getCalculatedLength() {
        return this.m_lCalculatedLength;
    }
    
    protected TDataOutputStream getDataOutputStream() {
        return this.m_dataOutputStream;
    }
    
    public int write(final byte[] abData, final int nOffset, int nLength) throws IOException {
        if (TDebug.TraceAudioOutputStream) {
            TDebug.out("TAudioOutputStream.write(): wanted length: " + nLength);
        }
        if (!this.m_bHeaderWritten) {
            this.writeHeader();
            this.m_bHeaderWritten = true;
        }
        final long lTotalLength = this.getLength();
        if (lTotalLength != -1L && this.m_lCalculatedLength + nLength > lTotalLength) {
            if (TDebug.TraceAudioOutputStream) {
                TDebug.out("TAudioOutputStream.write(): requested more bytes to write than possible.");
            }
            nLength = (int)(lTotalLength - this.m_lCalculatedLength);
            if (nLength < 0) {
                nLength = 0;
            }
        }
        if (nLength > 0) {
            this.m_dataOutputStream.write(abData, nOffset, nLength);
            this.m_lCalculatedLength += nLength;
        }
        if (TDebug.TraceAudioOutputStream) {
            TDebug.out("TAudioOutputStream.write(): calculated (total) length: " + this.m_lCalculatedLength + " bytes = " + this.m_lCalculatedLength / this.getFormat().getFrameSize() + " frames");
        }
        return nLength;
    }
    
    protected abstract void writeHeader() throws IOException;
    
    public void close() throws IOException {
        if (TDebug.TraceAudioOutputStream) {
            TDebug.out("TAudioOutputStream.close(): called");
        }
        if (this.m_bDoBackPatching) {
            if (TDebug.TraceAudioOutputStream) {
                TDebug.out("TAudioOutputStream.close(): patching header");
            }
            this.patchHeader();
        }
        this.m_dataOutputStream.close();
    }
    
    protected void patchHeader() throws IOException {
        TDebug.out("TAudioOutputStream.patchHeader(): called");
    }
    
    protected void setLengthFromCalculatedLength() {
        this.m_lLength = this.m_lCalculatedLength;
    }
}
