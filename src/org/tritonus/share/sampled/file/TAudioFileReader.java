// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.file;

import java.io.DataInputStream;
import java.io.EOFException;
import java.net.URLConnection;
import java.io.BufferedInputStream;
import javax.sound.sampled.AudioInputStream;
import java.net.URL;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.InputStream;
import java.io.FileInputStream;
import org.tritonus.share.TDebug;
import javax.sound.sampled.AudioFileFormat;
import java.io.File;
import javax.sound.sampled.spi.AudioFileReader;

public abstract class TAudioFileReader extends AudioFileReader
{
    private int m_nMarkLimit;
    private boolean m_bRereading;
    
    protected TAudioFileReader(final int nMarkLimit) {
        this(nMarkLimit, false);
    }
    
    protected TAudioFileReader(final int nMarkLimit, final boolean bRereading) {
        this.m_nMarkLimit = -1;
        this.m_nMarkLimit = nMarkLimit;
        this.m_bRereading = bRereading;
    }
    
    private int getMarkLimit() {
        return this.m_nMarkLimit;
    }
    
    private boolean isRereading() {
        return this.m_bRereading;
    }
    
    public AudioFileFormat getAudioFileFormat(final File file) throws UnsupportedAudioFileException, IOException {
        if (TDebug.TraceAudioFileReader) {
            TDebug.out("TAudioFileReader.getAudioFileFormat(File): begin");
        }
        final long lFileLengthInBytes = file.length();
        final InputStream inputStream = new FileInputStream(file);
        AudioFileFormat audioFileFormat = null;
        try {
            audioFileFormat = this.getAudioFileFormat(inputStream, lFileLengthInBytes);
        }
        finally {
            inputStream.close();
        }
        if (TDebug.TraceAudioFileReader) {
            TDebug.out("TAudioFileReader.getAudioFileFormat(File): end");
        }
        return audioFileFormat;
    }
    
    public AudioFileFormat getAudioFileFormat(final URL url) throws UnsupportedAudioFileException, IOException {
        if (TDebug.TraceAudioFileReader) {
            TDebug.out("TAudioFileReader.getAudioFileFormat(URL): begin");
        }
        final long lFileLengthInBytes = getDataLength(url);
        final InputStream inputStream = url.openStream();
        AudioFileFormat audioFileFormat = null;
        try {
            audioFileFormat = this.getAudioFileFormat(inputStream, lFileLengthInBytes);
        }
        finally {
            inputStream.close();
        }
        if (TDebug.TraceAudioFileReader) {
            TDebug.out("TAudioFileReader.getAudioFileFormat(URL): end");
        }
        return audioFileFormat;
    }
    
    public AudioFileFormat getAudioFileFormat(final InputStream inputStream) throws UnsupportedAudioFileException, IOException {
        if (TDebug.TraceAudioFileReader) {
            TDebug.out("TAudioFileReader.getAudioFileFormat(InputStream): begin");
        }
        final long lFileLengthInBytes = -1L;
        inputStream.mark(this.getMarkLimit());
        AudioFileFormat audioFileFormat = null;
        try {
            audioFileFormat = this.getAudioFileFormat(inputStream, lFileLengthInBytes);
        }
        finally {
            inputStream.reset();
        }
        if (TDebug.TraceAudioFileReader) {
            TDebug.out("TAudioFileReader.getAudioFileFormat(InputStream): end");
        }
        return audioFileFormat;
    }
    
    protected abstract AudioFileFormat getAudioFileFormat(final InputStream p0, final long p1) throws UnsupportedAudioFileException, IOException;
    
    public AudioInputStream getAudioInputStream(final File file) throws UnsupportedAudioFileException, IOException {
        if (TDebug.TraceAudioFileReader) {
            TDebug.out("TAudioFileReader.getAudioInputStream(File): begin");
        }
        final long lFileLengthInBytes = file.length();
        final InputStream inputStream = new FileInputStream(file);
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = this.getAudioInputStream(inputStream, lFileLengthInBytes);
        }
        catch (UnsupportedAudioFileException e) {
            inputStream.close();
            throw e;
        }
        catch (IOException e2) {
            inputStream.close();
            throw e2;
        }
        if (TDebug.TraceAudioFileReader) {
            TDebug.out("TAudioFileReader.getAudioInputStream(File): end");
        }
        return audioInputStream;
    }
    
    public AudioInputStream getAudioInputStream(final URL url) throws UnsupportedAudioFileException, IOException {
        if (TDebug.TraceAudioFileReader) {
            TDebug.out("TAudioFileReader.getAudioInputStream(URL): begin");
        }
        final long lFileLengthInBytes = getDataLength(url);
        final InputStream inputStream = url.openStream();
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = this.getAudioInputStream(inputStream, lFileLengthInBytes);
        }
        catch (UnsupportedAudioFileException e) {
            inputStream.close();
            throw e;
        }
        catch (IOException e2) {
            inputStream.close();
            throw e2;
        }
        if (TDebug.TraceAudioFileReader) {
            TDebug.out("TAudioFileReader.getAudioInputStream(URL): end");
        }
        return audioInputStream;
    }
    
    public AudioInputStream getAudioInputStream(final InputStream inputStream) throws UnsupportedAudioFileException, IOException {
        if (TDebug.TraceAudioFileReader) {
            TDebug.out("TAudioFileReader.getAudioInputStream(InputStream): begin");
        }
        final long lFileLengthInBytes = -1L;
        AudioInputStream audioInputStream = null;
        inputStream.mark(this.getMarkLimit());
        try {
            audioInputStream = this.getAudioInputStream(inputStream, lFileLengthInBytes);
        }
        catch (UnsupportedAudioFileException e) {
            inputStream.reset();
            throw e;
        }
        catch (IOException e2) {
            inputStream.reset();
            throw e2;
        }
        if (TDebug.TraceAudioFileReader) {
            TDebug.out("TAudioFileReader.getAudioInputStream(InputStream): end");
        }
        return audioInputStream;
    }
    
    protected AudioInputStream getAudioInputStream(InputStream inputStream, final long lFileLengthInBytes) throws UnsupportedAudioFileException, IOException {
        if (TDebug.TraceAudioFileReader) {
            TDebug.out("TAudioFileReader.getAudioInputStream(InputStream, long): begin");
        }
        if (this.isRereading()) {
            inputStream = new BufferedInputStream(inputStream, this.getMarkLimit());
            inputStream.mark(this.getMarkLimit());
        }
        final AudioFileFormat audioFileFormat = this.getAudioFileFormat(inputStream, lFileLengthInBytes);
        if (this.isRereading()) {
            inputStream.reset();
        }
        final AudioInputStream audioInputStream = new AudioInputStream(inputStream, audioFileFormat.getFormat(), audioFileFormat.getFrameLength());
        if (TDebug.TraceAudioFileReader) {
            TDebug.out("TAudioFileReader.getAudioInputStream(InputStream, long): end");
        }
        return audioInputStream;
    }
    
    protected static int calculateFrameSize(final int nSampleSize, final int nNumChannels) {
        return (nSampleSize + 7) / 8 * nNumChannels;
    }
    
    private static long getDataLength(final URL url) throws IOException {
        long lFileLengthInBytes = -1L;
        final URLConnection connection = url.openConnection();
        connection.connect();
        final int nLength = connection.getContentLength();
        if (nLength > 0) {
            lFileLengthInBytes = nLength;
        }
        return lFileLengthInBytes;
    }
    
    public static int readLittleEndianInt(final InputStream is) throws IOException {
        final int b0 = is.read();
        final int b = is.read();
        final int b2 = is.read();
        final int b3 = is.read();
        if ((b0 | b | b2 | b3) < 0) {
            throw new EOFException();
        }
        return (b3 << 24) + (b2 << 16) + (b << 8) + (b0 << 0);
    }
    
    public static short readLittleEndianShort(final InputStream is) throws IOException {
        final int b0 = is.read();
        final int b = is.read();
        if ((b0 | b) < 0) {
            throw new EOFException();
        }
        return (short)((b << 8) + (b0 << 0));
    }
    
    public static double readIeeeExtended(final DataInputStream dis) throws IOException {
        double f = 0.0;
        int expon = 0;
        long hiMant = 0L;
        long loMant = 0L;
        final double HUGE = 3.4028234663852886E38;
        expon = dis.readUnsignedShort();
        long t1 = dis.readUnsignedShort();
        long t2 = dis.readUnsignedShort();
        hiMant = (t1 << 16 | t2);
        t1 = dis.readUnsignedShort();
        t2 = dis.readUnsignedShort();
        loMant = (t1 << 16 | t2);
        if (expon == 0 && hiMant == 0L && loMant == 0L) {
            f = 0.0;
        }
        else if (expon == 32767) {
            f = HUGE;
        }
        else {
            expon -= 16383;
            expon -= 31;
            f = hiMant * Math.pow(2.0, expon);
            expon -= 32;
            f += loMant * Math.pow(2.0, expon);
        }
        return f;
    }
}
