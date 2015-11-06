// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.convert;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import org.tritonus.share.TDebug;
import org.tritonus.share.sampled.AudioUtils;

public abstract class TSynchronousFilteredAudioInputStream extends TAudioInputStream
{
    private AudioInputStream originalStream;
    private AudioFormat originalFormat;
    private int originalFrameSize;
    private int newFrameSize;
    protected byte[] buffer;
    private boolean m_bConvertInPlace;
    
    public TSynchronousFilteredAudioInputStream(final AudioInputStream audioInputStream, final AudioFormat newFormat) {
        super(audioInputStream, newFormat, audioInputStream.getFrameLength());
        this.buffer = null;
        this.m_bConvertInPlace = false;
        this.originalStream = audioInputStream;
        this.originalFormat = audioInputStream.getFormat();
        this.originalFrameSize = ((this.originalFormat.getFrameSize() <= 0) ? 1 : this.originalFormat.getFrameSize());
        this.newFrameSize = ((this.getFormat().getFrameSize() <= 0) ? 1 : this.getFormat().getFrameSize());
        if (TDebug.TraceAudioConverter) {
            TDebug.out("TSynchronousFilteredAudioInputStream: original format =" + AudioUtils.format2ShortStr(this.originalFormat));
            TDebug.out("TSynchronousFilteredAudioInputStream: converted format=" + AudioUtils.format2ShortStr(this.getFormat()));
        }
        this.m_bConvertInPlace = false;
    }
    
    protected boolean enableConvertInPlace() {
        if (this.newFrameSize >= this.originalFrameSize) {
            this.m_bConvertInPlace = true;
        }
        return this.m_bConvertInPlace;
    }
    
    protected abstract int convert(final byte[] p0, final byte[] p1, final int p2, final int p3);
    
    protected void convertInPlace(final byte[] buffer, final int byteOffset, final int frameCount) {
        throw new RuntimeException("Illegal call to convertInPlace");
    }
    
    public int read() throws IOException {
        if (this.newFrameSize != 1) {
            throw new IOException("frame size must be 1 to read a single byte");
        }
        final byte[] temp = { 0 };
        final int result = this.read(temp);
        if (result == -1) {
            return -1;
        }
        if (result == 0) {
            return -1;
        }
        return temp[0] & 0xFF;
    }
    
    private void clearBuffer() {
        this.buffer = null;
    }
    
    public AudioInputStream getOriginalStream() {
        return this.originalStream;
    }
    
    public AudioFormat getOriginalFormat() {
        return this.originalFormat;
    }
    
    public int read(final byte[] abData, final int nOffset, final int nLength) throws IOException {
        final int nFrameLength = nLength / this.newFrameSize;
        final int originalBytes = nFrameLength * this.originalFrameSize;
        if (TDebug.TraceAudioConverter) {
            TDebug.out("> TSynchronousFilteredAIS.read(buffer[" + abData.length + "], " + nOffset + " ," + nLength + " bytes ^=" + nFrameLength + " frames)");
        }
        int nFramesConverted = 0;
        byte[] readBuffer;
        int readOffset;
        if (this.m_bConvertInPlace) {
            readBuffer = abData;
            readOffset = nOffset;
        }
        else {
            if (this.buffer == null || this.buffer.length < originalBytes) {
                this.buffer = new byte[originalBytes];
            }
            readBuffer = this.buffer;
            readOffset = 0;
        }
        final int nBytesRead = this.originalStream.read(readBuffer, readOffset, originalBytes);
        if (nBytesRead == -1) {
            this.clearBuffer();
            return -1;
        }
        final int nFramesRead = nBytesRead / this.originalFrameSize;
        if (TDebug.TraceAudioConverter) {
            TDebug.out("original.read returned " + nBytesRead + " bytes ^=" + nFramesRead + " frames");
        }
        if (this.m_bConvertInPlace) {
            this.convertInPlace(abData, nOffset, nFramesRead);
            nFramesConverted = nFramesRead;
        }
        else {
            nFramesConverted = this.convert(this.buffer, abData, nOffset, nFramesRead);
        }
        if (TDebug.TraceAudioConverter) {
            TDebug.out("< converted " + nFramesConverted + " frames");
        }
        return nFramesConverted * this.newFrameSize;
    }
    
    public long skip(final long nSkip) throws IOException {
        final long skipFrames = nSkip / this.newFrameSize;
        final long originalSkippedBytes = this.originalStream.skip(skipFrames * this.originalFrameSize);
        final long skippedFrames = originalSkippedBytes / this.originalFrameSize;
        return skippedFrames * this.newFrameSize;
    }
    
    public int available() throws IOException {
        final int origAvailFrames = this.originalStream.available() / this.originalFrameSize;
        return origAvailFrames * this.newFrameSize;
    }
    
    public void close() throws IOException {
        this.originalStream.close();
        this.clearBuffer();
    }
    
    public void mark(final int readlimit) {
        final int readLimitFrames = readlimit / this.newFrameSize;
        this.originalStream.mark(readLimitFrames * this.originalFrameSize);
    }
    
    public void reset() throws IOException {
        this.originalStream.reset();
    }
    
    public boolean markSupported() {
        return this.originalStream.markSupported();
    }
    
    @SuppressWarnings("unused")
	private int getFrameSize() {
        return this.getFormat().getFrameSize();
    }
}
