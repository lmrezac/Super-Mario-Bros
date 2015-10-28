// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.convert;

import java.io.IOException;
import org.tritonus.share.TDebug;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import javax.sound.sampled.AudioFormat;
import org.tritonus.share.TCircularBuffer;

public abstract class TAsynchronousFilteredAudioInputStream extends TAudioInputStream implements TCircularBuffer.Trigger
{
    private static final int DEFAULT_BUFFER_SIZE = 327670;
    private static final int DEFAULT_MIN_AVAILABLE = 4096;
    private static final byte[] EMPTY_BYTE_ARRAY;
    private TCircularBuffer m_circularBuffer;
    private int m_nMinAvailable;
    private byte[] m_abSingleByte;
    
    public TAsynchronousFilteredAudioInputStream(final AudioFormat outputFormat, final long lLength) {
        this(outputFormat, lLength, 327670, 4096);
    }
    
    public TAsynchronousFilteredAudioInputStream(final AudioFormat outputFormat, final long lLength, final int nBufferSize, final int nMinAvailable) {
        super(new ByteArrayInputStream(TAsynchronousFilteredAudioInputStream.EMPTY_BYTE_ARRAY), outputFormat, lLength);
        if (TDebug.TraceAudioConverter) {
            TDebug.out("TAsynchronousFilteredAudioInputStream.<init>(): begin");
        }
        this.m_circularBuffer = new TCircularBuffer(nBufferSize, false, true, this);
        this.m_nMinAvailable = nMinAvailable;
        if (TDebug.TraceAudioConverter) {
            TDebug.out("TAsynchronousFilteredAudioInputStream.<init>(): end");
        }
    }
    
    protected TCircularBuffer getCircularBuffer() {
        return this.m_circularBuffer;
    }
    
    protected boolean writeMore() {
        return this.getCircularBuffer().availableWrite() > this.m_nMinAvailable;
    }
    
    public int read() throws IOException {
        int nByte = -1;
        if (this.m_abSingleByte == null) {
            this.m_abSingleByte = new byte[1];
        }
        final int nReturn = this.read(this.m_abSingleByte);
        if (nReturn == -1) {
            nByte = -1;
        }
        else {
            nByte = (this.m_abSingleByte[0] & 0xFF);
        }
        return nByte;
    }
    
    public int read(final byte[] abData) throws IOException {
        if (TDebug.TraceAudioConverter) {
            TDebug.out("TAsynchronousFilteredAudioInputStream.read(byte[]): begin");
        }
        final int nRead = this.read(abData, 0, abData.length);
        if (TDebug.TraceAudioConverter) {
            TDebug.out("TAsynchronousFilteredAudioInputStream.read(byte[]): end");
        }
        return nRead;
    }
    
    public int read(final byte[] abData, final int nOffset, final int nLength) throws IOException {
        if (TDebug.TraceAudioConverter) {
            TDebug.out("TAsynchronousFilteredAudioInputStream.read(byte[], int, int): begin");
        }
        final int nRead = this.m_circularBuffer.read(abData, nOffset, nLength);
        if (TDebug.TraceAudioConverter) {
            TDebug.out("TAsynchronousFilteredAudioInputStream.read(byte[], int, int): end");
        }
        return nRead;
    }
    
    public long skip(final long lSkip) throws IOException {
        for (long lSkipped = 0L; lSkipped < lSkip; ++lSkipped) {
            final int nReturn = this.read();
            if (nReturn == -1) {
                return lSkipped;
            }
        }
        return lSkip;
    }
    
    public int available() throws IOException {
        return this.m_circularBuffer.availableRead();
    }
    
    public void close() throws IOException {
        this.m_circularBuffer.close();
    }
    
    public boolean markSupported() {
        return false;
    }
    
    public void mark(final int nReadLimit) {
    }
    
    public void reset() throws IOException {
        throw new IOException("mark not supported");
    }
    
    static {
        EMPTY_BYTE_ARRAY = new byte[0];
    }
}
