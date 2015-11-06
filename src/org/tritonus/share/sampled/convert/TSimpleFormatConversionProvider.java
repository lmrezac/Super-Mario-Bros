// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.convert;

import java.util.Collection;

import javax.sound.sampled.AudioFormat;

import org.tritonus.share.ArraySet;
import org.tritonus.share.TDebug;
import org.tritonus.share.sampled.AudioFormats;

public abstract class TSimpleFormatConversionProvider extends TFormatConversionProvider
{
    private Collection<AudioFormat.Encoding> m_sourceEncodings;
    private Collection<AudioFormat.Encoding> m_targetEncodings;
    private Collection<AudioFormat> m_sourceFormats;
    private Collection<AudioFormat> m_targetFormats;
    
    protected TSimpleFormatConversionProvider(final Collection<AudioFormat> sourceFormats, final Collection<AudioFormat> targetFormats) {
        this.m_sourceEncodings = new ArraySet<AudioFormat.Encoding>();
        this.m_targetEncodings = new ArraySet<AudioFormat.Encoding>();
        this.m_sourceFormats = sourceFormats;
        this.m_targetFormats = targetFormats;
        collectEncodings(this.m_sourceFormats, this.m_sourceEncodings);
        collectEncodings(this.m_targetFormats, this.m_targetEncodings);
    }
    
    protected void disable() {
        if (TDebug.TraceAudioConverter) {
            TDebug.out("TSimpleFormatConversionProvider.disable(): disabling " + this.getClass().getName());
        }
        this.m_sourceEncodings = new ArraySet<AudioFormat.Encoding>();
        this.m_targetEncodings = new ArraySet<AudioFormat.Encoding>();
        this.m_sourceFormats = new ArraySet<AudioFormat>();
        this.m_targetFormats = new ArraySet<AudioFormat>();
    }
    
    private static void collectEncodings(final Collection<AudioFormat> formats, final Collection<AudioFormat.Encoding> encodings) {
        for (final AudioFormat format : formats) {
            encodings.add(format.getEncoding());
        }
    }
    
    public AudioFormat.Encoding[] getSourceEncodings() {
        return this.m_sourceEncodings.toArray(TSimpleFormatConversionProvider.EMPTY_ENCODING_ARRAY);
    }
    
    public AudioFormat.Encoding[] getTargetEncodings() {
        return this.m_targetEncodings.toArray(TSimpleFormatConversionProvider.EMPTY_ENCODING_ARRAY);
    }
    
    public boolean isSourceEncodingSupported(final AudioFormat.Encoding sourceEncoding) {
        return this.m_sourceEncodings.contains(sourceEncoding);
    }
    
    public boolean isTargetEncodingSupported(final AudioFormat.Encoding targetEncoding) {
        return this.m_targetEncodings.contains(targetEncoding);
    }
    
    public AudioFormat.Encoding[] getTargetEncodings(final AudioFormat sourceFormat) {
        if (this.isAllowedSourceFormat(sourceFormat)) {
            return this.getTargetEncodings();
        }
        return TSimpleFormatConversionProvider.EMPTY_ENCODING_ARRAY;
    }
    
    public AudioFormat[] getTargetFormats(final AudioFormat.Encoding targetEncoding, final AudioFormat sourceFormat) {
        if (this.isConversionSupported(targetEncoding, sourceFormat)) {
            return this.m_targetFormats.toArray(TSimpleFormatConversionProvider.EMPTY_FORMAT_ARRAY);
        }
        return TSimpleFormatConversionProvider.EMPTY_FORMAT_ARRAY;
    }
    
    protected boolean isAllowedSourceEncoding(final AudioFormat.Encoding sourceEncoding) {
        return this.m_sourceEncodings.contains(sourceEncoding);
    }
    
    protected boolean isAllowedTargetEncoding(final AudioFormat.Encoding targetEncoding) {
        return this.m_targetEncodings.contains(targetEncoding);
    }
    
    protected boolean isAllowedSourceFormat(final AudioFormat sourceFormat) {
        for (final AudioFormat format : this.m_sourceFormats) {
            if (AudioFormats.matches(format, sourceFormat)) {
                return true;
            }
        }
        return false;
    }
    
    protected boolean isAllowedTargetFormat(final AudioFormat targetFormat) {
        for (final AudioFormat format : this.m_targetFormats) {
            if (AudioFormats.matches(format, targetFormat)) {
                return true;
            }
        }
        return false;
    }
    
    protected Collection<AudioFormat.Encoding> getCollectionSourceEncodings() {
        return this.m_sourceEncodings;
    }
    
    protected Collection<AudioFormat.Encoding> getCollectionTargetEncodings() {
        return this.m_targetEncodings;
    }
    
    protected Collection<AudioFormat> getCollectionSourceFormats() {
        return this.m_sourceFormats;
    }
    
    protected Collection<AudioFormat> getCollectionTargetFormats() {
        return this.m_targetFormats;
    }
    
    protected static boolean doMatch(final int i1, final int i2) {
        return i1 == -1 || i2 == -1 || i1 == i2;
    }
    
    protected static boolean doMatch(final float f1, final float f2) {
        return f1 == -1.0f || f2 == -1.0f || Math.abs(f1 - f2) < 1.0E-9;
    }
    
    protected AudioFormat replaceNotSpecified(final AudioFormat sourceFormat, AudioFormat targetFormat) {
        boolean bSetSampleSize = false;
        boolean bSetChannels = false;
        boolean bSetSampleRate = false;
        boolean bSetFrameRate = false;
        if (targetFormat.getSampleSizeInBits() == -1 && sourceFormat.getSampleSizeInBits() != -1) {
            bSetSampleSize = true;
        }
        if (targetFormat.getChannels() == -1 && sourceFormat.getChannels() != -1) {
            bSetChannels = true;
        }
        if (targetFormat.getSampleRate() == -1.0f && sourceFormat.getSampleRate() != -1.0f) {
            bSetSampleRate = true;
        }
        if (targetFormat.getFrameRate() == -1.0f && sourceFormat.getFrameRate() != -1.0f) {
            bSetFrameRate = true;
        }
        if (bSetSampleSize || bSetChannels || bSetSampleRate || bSetFrameRate || (targetFormat.getFrameSize() == -1 && sourceFormat.getFrameSize() != -1)) {
            final float sampleRate = bSetSampleRate ? sourceFormat.getSampleRate() : targetFormat.getSampleRate();
            final float frameRate = bSetFrameRate ? sourceFormat.getFrameRate() : targetFormat.getFrameRate();
            final int sampleSize = bSetSampleSize ? sourceFormat.getSampleSizeInBits() : targetFormat.getSampleSizeInBits();
            final int channels = bSetChannels ? sourceFormat.getChannels() : targetFormat.getChannels();
            final int frameSize = this.getFrameSize(targetFormat.getEncoding(), sampleRate, sampleSize, channels, frameRate, targetFormat.isBigEndian(), targetFormat.getFrameSize());
            targetFormat = new AudioFormat(targetFormat.getEncoding(), sampleRate, sampleSize, channels, frameSize, frameRate, targetFormat.isBigEndian());
        }
        return targetFormat;
    }
    
    protected int getFrameSize(final AudioFormat.Encoding encoding, final float sampleRate, final int sampleSize, final int channels, final float frameRate, final boolean bigEndian, final int oldFrameSize) {
        if (sampleSize == -1 || channels == -1) {
            return -1;
        }
        return sampleSize * channels / 8;
    }
}
