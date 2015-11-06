// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.file;

import org.tritonus.share.sampled.AudioFormats;
import org.tritonus.share.sampled.TConversionTool;

import java.io.OutputStream;
import java.io.IOException;

import org.tritonus.share.sampled.AudioUtils;

import java.io.File;
import java.util.Iterator;

import org.tritonus.share.ArraySet;

import javax.sound.sampled.AudioInputStream;

import org.tritonus.share.TDebug;

import java.util.Collection;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.spi.AudioFileWriter;
@SuppressWarnings("unused")
public abstract class TAudioFileWriter extends AudioFileWriter
{
    protected static final int ALL = -1;
    public static AudioFormat.Encoding PCM_SIGNED;
    public static AudioFormat.Encoding PCM_UNSIGNED;
    private static final int BUFFER_LENGTH = 16384;
    protected static final AudioFileFormat.Type[] NULL_TYPE_ARRAY;
    private Collection<AudioFileFormat.Type> m_audioFileTypes;
    private Collection<AudioFormat> m_audioFormats;
    
    protected TAudioFileWriter(final Collection<AudioFileFormat.Type> fileTypes, final Collection<AudioFormat> audioFormats) {
        if (TDebug.TraceAudioFileWriter) {
            TDebug.out("TAudioFileWriter.<init>(): begin");
        }
        this.m_audioFileTypes = fileTypes;
        this.m_audioFormats = audioFormats;
        if (TDebug.TraceAudioFileWriter) {
            TDebug.out("TAudioFileWriter.<init>(): end");
        }
    }
    
    public AudioFileFormat.Type[] getAudioFileTypes() {
        return this.m_audioFileTypes.toArray(TAudioFileWriter.NULL_TYPE_ARRAY);
    }
    
    public boolean isFileTypeSupported(final AudioFileFormat.Type fileType) {
        return this.m_audioFileTypes.contains(fileType);
    }
    
    public AudioFileFormat.Type[] getAudioFileTypes(final AudioInputStream audioInputStream) {
        final AudioFormat format = audioInputStream.getFormat();
        final ArraySet<AudioFileFormat.Type> res = new ArraySet<AudioFileFormat.Type>();
        for (final AudioFileFormat.Type thisType : this.m_audioFileTypes) {
            if (this.isAudioFormatSupportedImpl(format, thisType)) {
                res.add(thisType);
            }
        }
        return res.toArray(TAudioFileWriter.NULL_TYPE_ARRAY);
    }
    
    public boolean isFileTypeSupported(final AudioFileFormat.Type fileType, final AudioInputStream audioInputStream) {
        return this.isFileTypeSupported(fileType) && (this.isAudioFormatSupportedImpl(audioInputStream.getFormat(), fileType) || this.findConvertableFormat(audioInputStream.getFormat(), fileType) != null);
    }
    
    public int write(final AudioInputStream audioInputStream, final AudioFileFormat.Type fileType, final File file) throws IOException {
        if (TDebug.TraceAudioFileWriter) {
            TDebug.out(">TAudioFileWriter.write(.., File): called");
            TDebug.out("class: " + this.getClass().getName());
        }
        if (!this.isFileTypeSupported(fileType)) {
            if (TDebug.TraceAudioFileWriter) {
                TDebug.out("< file type is not supported");
            }
            throw new IllegalArgumentException("file type is not supported.");
        }
        final AudioFormat inputFormat = audioInputStream.getFormat();
        if (TDebug.TraceAudioFileWriter) {
            TDebug.out("input format: " + inputFormat);
        }
        AudioFormat outputFormat = null;
        boolean bNeedsConversion = false;
        if (this.isAudioFormatSupportedImpl(inputFormat, fileType)) {
            if (TDebug.TraceAudioFileWriter) {
                TDebug.out("input format is supported directely");
            }
            outputFormat = inputFormat;
            bNeedsConversion = false;
        }
        else {
            if (TDebug.TraceAudioFileWriter) {
                TDebug.out("input format is not supported directely; trying to find a convertable format");
            }
            outputFormat = this.findConvertableFormat(inputFormat, fileType);
            if (outputFormat == null) {
                if (TDebug.TraceAudioFileWriter) {
                    TDebug.out("< input format is not supported and not convertable.");
                }
                throw new IllegalArgumentException("format not supported and not convertable");
            }
            bNeedsConversion = true;
            if (outputFormat.getSampleSizeInBits() == 8 && outputFormat.getEncoding().equals(inputFormat.getEncoding())) {
                bNeedsConversion = false;
            }
        }
        final long lLengthInBytes = AudioUtils.getLengthInBytes(audioInputStream);
        final TDataOutputStream dataOutputStream = new TSeekableDataOutputStream(file);
        final AudioOutputStream audioOutputStream = this.getAudioOutputStream(outputFormat, lLengthInBytes, fileType, dataOutputStream);
        final int written = this.writeImpl(audioInputStream, audioOutputStream, bNeedsConversion);
        if (TDebug.TraceAudioFileWriter) {
            TDebug.out("< wrote " + written + " bytes.");
        }
        return written;
    }
    
    public int write(final AudioInputStream audioInputStream, final AudioFileFormat.Type fileType, final OutputStream outputStream) throws IOException {
        if (!this.isFileTypeSupported(fileType)) {
            throw new IllegalArgumentException("file type is not supported.");
        }
        if (TDebug.TraceAudioFileWriter) {
            TDebug.out(">TAudioFileWriter.write(.., OutputStream): called");
            TDebug.out("class: " + this.getClass().getName());
        }
        final AudioFormat inputFormat = audioInputStream.getFormat();
        if (TDebug.TraceAudioFileWriter) {
            TDebug.out("input format: " + inputFormat);
        }
        AudioFormat outputFormat = null;
        boolean bNeedsConversion = false;
        if (this.isAudioFormatSupportedImpl(inputFormat, fileType)) {
            if (TDebug.TraceAudioFileWriter) {
                TDebug.out("input format is supported directely");
            }
            outputFormat = inputFormat;
            bNeedsConversion = false;
        }
        else {
            if (TDebug.TraceAudioFileWriter) {
                TDebug.out("input format is not supported directely; trying to find a convertable format");
            }
            outputFormat = this.findConvertableFormat(inputFormat, fileType);
            if (outputFormat == null) {
                if (TDebug.TraceAudioFileWriter) {
                    TDebug.out("< format is not supported");
                }
                throw new IllegalArgumentException("format not supported and not convertable");
            }
            bNeedsConversion = true;
            if (outputFormat.getSampleSizeInBits() == 8 && outputFormat.getEncoding().equals(inputFormat.getEncoding())) {
                bNeedsConversion = false;
            }
        }
        final long lLengthInBytes = AudioUtils.getLengthInBytes(audioInputStream);
        final TDataOutputStream dataOutputStream = new TNonSeekableDataOutputStream(outputStream);
        final AudioOutputStream audioOutputStream = this.getAudioOutputStream(outputFormat, lLengthInBytes, fileType, dataOutputStream);
        final int written = this.writeImpl(audioInputStream, audioOutputStream, bNeedsConversion);
        if (TDebug.TraceAudioFileWriter) {
            TDebug.out("< wrote " + written + " bytes.");
        }
        return written;
    }
    
    protected int writeImpl(final AudioInputStream audioInputStream, final AudioOutputStream audioOutputStream, final boolean bNeedsConversion) throws IOException {
        if (TDebug.TraceAudioFileWriter) {
            TDebug.out(">TAudioFileWriter.writeImpl(): called");
            TDebug.out("class: " + this.getClass().getName());
        }
        int nTotalWritten = 0;
        final AudioFormat inputFormat = audioInputStream.getFormat();
        final AudioFormat outputFormat = audioOutputStream.getFormat();
        final int nBytesPerSample = outputFormat.getFrameSize() / outputFormat.getChannels();
        final int nBufferSize = 16384 / outputFormat.getFrameSize() * outputFormat.getFrameSize();
        final byte[] abBuffer = new byte[nBufferSize];
        while (true) {
            if (TDebug.TraceAudioFileWriter) {
                TDebug.out("trying to read (bytes): " + abBuffer.length);
            }
            final int nBytesRead = audioInputStream.read(abBuffer);
            if (TDebug.TraceAudioFileWriter) {
                TDebug.out("read (bytes): " + nBytesRead);
            }
            if (nBytesRead == -1) {
                break;
            }
            if (bNeedsConversion) {
                TConversionTool.changeOrderOrSign(abBuffer, 0, nBytesRead, nBytesPerSample);
            }
            final int nWritten = audioOutputStream.write(abBuffer, 0, nBytesRead);
            nTotalWritten += nWritten;
        }
        if (TDebug.TraceAudioFileWriter) {
            TDebug.out("<TAudioFileWriter.writeImpl(): after main loop. Wrote " + nTotalWritten + " bytes");
        }
        audioOutputStream.close();
        return nTotalWritten;
    }
    
    protected Iterator<AudioFormat> getSupportedAudioFormats(final AudioFileFormat.Type fileType) {
        return this.m_audioFormats.iterator();
    }
    
    protected boolean isAudioFormatSupportedImpl(final AudioFormat audioFormat, final AudioFileFormat.Type fileType) {
        if (TDebug.TraceAudioFileWriter) {
            TDebug.out("> TAudioFileWriter.isAudioFormatSupportedImpl(): format to test: " + audioFormat);
            TDebug.out("class: " + this.getClass().getName());
        }
        final Iterator<?> audioFormats = this.getSupportedAudioFormats(fileType);
        while (audioFormats.hasNext()) {
            final AudioFormat handledFormat = (AudioFormat) audioFormats.next();
            if (TDebug.TraceAudioFileWriter) {
                TDebug.out("matching against format : " + handledFormat);
            }
            if (AudioFormats.matches(handledFormat, audioFormat)) {
                if (TDebug.TraceAudioFileWriter) {
                    TDebug.out("<...succeeded.");
                }
                return true;
            }
        }
        if (TDebug.TraceAudioFileWriter) {
            TDebug.out("< ... failed");
        }
        return false;
    }
    
    protected abstract AudioOutputStream getAudioOutputStream(final AudioFormat p0, final long p1, final AudioFileFormat.Type p2, final TDataOutputStream p3) throws IOException;
    
    private AudioFormat findConvertableFormat(final AudioFormat inputFormat, final AudioFileFormat.Type fileType) {
        if (TDebug.TraceAudioFileWriter) {
            TDebug.out("TAudioFileWriter.findConvertableFormat(): input format: " + inputFormat);
        }
        if (!this.isFileTypeSupported(fileType)) {
            if (TDebug.TraceAudioFileWriter) {
                TDebug.out("< input file type is not supported.");
            }
            return null;
        }
        final AudioFormat.Encoding inputEncoding = inputFormat.getEncoding();
        if ((inputEncoding.equals(TAudioFileWriter.PCM_SIGNED) || inputEncoding.equals(TAudioFileWriter.PCM_UNSIGNED)) && inputFormat.getSampleSizeInBits() == 8) {
            AudioFormat outputFormat = this.convertFormat(inputFormat, true, false);
            if (TDebug.TraceAudioFileWriter) {
                TDebug.out("trying output format: " + outputFormat);
            }
            if (this.isAudioFormatSupportedImpl(outputFormat, fileType)) {
                if (TDebug.TraceAudioFileWriter) {
                    TDebug.out("< ... succeeded");
                }
                return outputFormat;
            }
            outputFormat = this.convertFormat(inputFormat, false, true);
            if (TDebug.TraceAudioFileWriter) {
                TDebug.out("trying output format: " + outputFormat);
            }
            if (this.isAudioFormatSupportedImpl(outputFormat, fileType)) {
                if (TDebug.TraceAudioFileWriter) {
                    TDebug.out("< ... succeeded");
                }
                return outputFormat;
            }
            outputFormat = this.convertFormat(inputFormat, true, true);
            if (TDebug.TraceAudioFileWriter) {
                TDebug.out("trying output format: " + outputFormat);
            }
            if (this.isAudioFormatSupportedImpl(outputFormat, fileType)) {
                if (TDebug.TraceAudioFileWriter) {
                    TDebug.out("< ... succeeded");
                }
                return outputFormat;
            }
            if (TDebug.TraceAudioFileWriter) {
                TDebug.out("< ... failed");
            }
            return null;
        }
        else {
            if (!inputEncoding.equals(TAudioFileWriter.PCM_SIGNED) || (inputFormat.getSampleSizeInBits() != 16 && inputFormat.getSampleSizeInBits() != 24 && inputFormat.getSampleSizeInBits() != 32)) {
                if (TDebug.TraceAudioFileWriter) {
                    TDebug.out("< ... failed");
                }
                return null;
            }
            final AudioFormat outputFormat = this.convertFormat(inputFormat, false, true);
            if (TDebug.TraceAudioFileWriter) {
                TDebug.out("trying output format: " + outputFormat);
            }
            if (this.isAudioFormatSupportedImpl(outputFormat, fileType)) {
                if (TDebug.TraceAudioFileWriter) {
                    TDebug.out("< ... succeeded");
                }
                return outputFormat;
            }
            if (TDebug.TraceAudioFileWriter) {
                TDebug.out("< ... failed");
            }
            return null;
        }
    }
    
    private AudioFormat convertFormat(final AudioFormat format, final boolean changeSign, final boolean changeEndian) {
        AudioFormat.Encoding enc = TAudioFileWriter.PCM_SIGNED;
        if (format.getEncoding().equals(TAudioFileWriter.PCM_UNSIGNED) != changeSign) {
            enc = TAudioFileWriter.PCM_UNSIGNED;
        }
        return new AudioFormat(enc, format.getSampleRate(), format.getSampleSizeInBits(), format.getChannels(), format.getFrameSize(), format.getFrameRate(), format.isBigEndian() ^ changeEndian);
    }
    
    static {
        TAudioFileWriter.PCM_SIGNED = new AudioFormat.Encoding("PCM_SIGNED");
        TAudioFileWriter.PCM_UNSIGNED = new AudioFormat.Encoding("PCM_UNSIGNED");
        NULL_TYPE_ARRAY = new AudioFileFormat.Type[0];
    }
}
