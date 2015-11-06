// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled;

import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;

public class FloatSampleBuffer
{
    @SuppressWarnings("unused")
	private static final boolean LAZY_DEFAULT = true;
    private ArrayList<float[]> channels;
    private int sampleCount;
    private int channelCount;
    private float sampleRate;
    private int originalFormatType;
    public static final int DITHER_MODE_AUTOMATIC = 0;
    public static final int DITHER_MODE_ON = 1;
    public static final int DITHER_MODE_OFF = 2;
    private float ditherBits;
    private int ditherMode;
    
    public FloatSampleBuffer() {
        this(0, 0, 1.0f);
    }
    
    public FloatSampleBuffer(final int channelCount, final int sampleCount, final float sampleRate) {
        this.channels = new ArrayList<float[]>();
        this.sampleCount = 0;
        this.channelCount = 0;
        this.sampleRate = 0.0f;
        this.originalFormatType = 0;
        this.ditherBits = 0.7f;
        this.ditherMode = 0;
        this.init(channelCount, sampleCount, sampleRate, true);
    }
    
    public FloatSampleBuffer(final byte[] buffer, final int offset, final int byteCount, final AudioFormat format) {
        this(format.getChannels(), byteCount / (format.getSampleSizeInBits() / 8 * format.getChannels()), format.getSampleRate());
        this.initFromByteArray(buffer, offset, byteCount, format);
    }
    
    protected void init(final int channelCount, final int sampleCount, final float sampleRate) {
        this.init(channelCount, sampleCount, sampleRate, true);
    }
    
    protected void init(final int channelCount, final int sampleCount, final float sampleRate, final boolean lazy) {
        if (channelCount < 0 || sampleCount < 0) {
            throw new IllegalArgumentException("invalid parameters in initialization of FloatSampleBuffer.");
        }
        this.setSampleRate(sampleRate);
        if (this.getSampleCount() != sampleCount || this.getChannelCount() != channelCount) {
            this.createChannels(channelCount, sampleCount, lazy);
        }
    }
    
    private void createChannels(final int channelCount, final int sampleCount, final boolean lazy) {
        this.sampleCount = sampleCount;
        this.channelCount = 0;
        for (int ch = 0; ch < channelCount; ++ch) {
            this.insertChannel(ch, false, lazy);
        }
        if (!lazy) {
            while (this.channels.size() > channelCount) {
                this.channels.remove(this.channels.size() - 1);
            }
        }
    }
    
    public void initFromByteArray(final byte[] buffer, final int offset, final int byteCount, final AudioFormat format) {
        this.initFromByteArray(buffer, offset, byteCount, format, true);
    }
    
    public void initFromByteArray(final byte[] buffer, final int offset, final int byteCount, final AudioFormat format, final boolean lazy) {
        if (offset + byteCount > buffer.length) {
            throw new IllegalArgumentException("FloatSampleBuffer.initFromByteArray: buffer too small.");
        }
        final int thisSampleCount = byteCount / format.getFrameSize();
        this.init(format.getChannels(), thisSampleCount, format.getSampleRate(), lazy);
        this.originalFormatType = FloatSampleTools.getFormatType(format);
        FloatSampleTools.byte2float(buffer, offset, this.channels, 0, this.sampleCount, format);
    }
    
    public void initFromFloatSampleBuffer(final FloatSampleBuffer source) {
        this.init(source.getChannelCount(), source.getSampleCount(), source.getSampleRate());
        for (int ch = 0; ch < this.getChannelCount(); ++ch) {
            System.arraycopy(source.getChannel(ch), 0, this.getChannel(ch), 0, this.sampleCount);
        }
    }
    
    public void reset() {
        this.init(0, 0, 1.0f, false);
    }
    
    public void reset(final int channels, final int sampleCount, final float sampleRate) {
        this.init(channels, sampleCount, sampleRate, false);
    }
    
    public int getByteArrayBufferSize(final AudioFormat format) {
        return this.getByteArrayBufferSize(format, this.getSampleCount());
    }
    
    public int getByteArrayBufferSize(final AudioFormat format, final int lenInSamples) {
        FloatSampleTools.getFormatType(format);
        return format.getFrameSize() * lenInSamples;
    }
    
    public int convertToByteArray(final byte[] buffer, final int offset, final AudioFormat format) {
        return this.convertToByteArray(0, this.getSampleCount(), buffer, offset, format);
    }
    
    public int convertToByteArray(final int readOffset, final int lenInSamples, final byte[] buffer, final int writeOffset, final AudioFormat format) {
        final int byteCount = this.getByteArrayBufferSize(format, lenInSamples);
        if (writeOffset + byteCount > buffer.length) {
            throw new IllegalArgumentException("FloatSampleBuffer.convertToByteArray: buffer too small.");
        }
        if (format.getSampleRate() != this.getSampleRate()) {
            throw new IllegalArgumentException("FloatSampleBuffer.convertToByteArray: different samplerates.");
        }
        if (format.getChannels() != this.getChannelCount()) {
            throw new IllegalArgumentException("FloatSampleBuffer.convertToByteArray: different channel count.");
        }
        FloatSampleTools.float2byte(this.channels, readOffset, buffer, writeOffset, lenInSamples, format, this.getConvertDitherBits(FloatSampleTools.getFormatType(format)));
        return byteCount;
    }
    
    public byte[] convertToByteArray(final AudioFormat format) {
        final byte[] res = new byte[this.getByteArrayBufferSize(format)];
        this.convertToByteArray(res, 0, format);
        return res;
    }
    
    public void changeSampleCount(final int newSampleCount, final boolean keepOldSamples) {
        final int oldSampleCount = this.getSampleCount();
        if (oldSampleCount == newSampleCount) {
            return;
        }
        Object[] oldChannels = null;
        if (keepOldSamples) {
            oldChannels = this.getAllChannels();
        }
        this.init(this.getChannelCount(), newSampleCount, this.getSampleRate());
        if (keepOldSamples) {
            final int copyCount = (newSampleCount < oldSampleCount) ? newSampleCount : oldSampleCount;
            for (int ch = 0; ch < this.getChannelCount(); ++ch) {
                final float[] oldSamples = (float[])oldChannels[ch];
                final float[] newSamples = this.getChannel(ch);
                if (oldSamples != newSamples) {
                    System.arraycopy(oldSamples, 0, newSamples, 0, copyCount);
                }
                if (oldSampleCount < newSampleCount) {
                    for (int i = oldSampleCount; i < newSampleCount; ++i) {
                        newSamples[i] = 0.0f;
                    }
                }
            }
        }
    }
    
    public void makeSilence() {
        if (this.getChannelCount() > 0) {
            this.makeSilence(0);
            for (int ch = 1; ch < this.getChannelCount(); ++ch) {
                this.copyChannel(0, ch);
            }
        }
    }
    
    public void makeSilence(final int channel) {
        final float[] samples = this.getChannel(channel);
        for (int i = 0; i < this.getSampleCount(); ++i) {
            samples[i] = 0.0f;
        }
    }
    
    public void addChannel(final boolean silent) {
        this.insertChannel(this.getChannelCount(), silent);
    }
    
    public void insertChannel(final int index, final boolean silent) {
        this.insertChannel(index, silent, true);
    }
    
    public void insertChannel(final int index, final boolean silent, final boolean lazy) {
        final int physSize = this.channels.size();
        final int virtSize = this.getChannelCount();
        float[] newChannel = null;
        if (physSize > virtSize) {
            for (int ch = virtSize; ch < physSize; ++ch) {
                final float[] thisChannel = this.channels.get(ch);
                if ((lazy && thisChannel.length >= this.getSampleCount()) || (!lazy && thisChannel.length == this.getSampleCount())) {
                    newChannel = thisChannel;
                    this.channels.remove(ch);
                    break;
                }
            }
        }
        if (newChannel == null) {
            newChannel = new float[this.getSampleCount()];
        }
        this.channels.add(index, newChannel);
        ++this.channelCount;
        if (silent) {
            this.makeSilence(index);
        }
    }
    
    public void removeChannel(final int channel) {
        this.removeChannel(channel, true);
    }
    
    public void removeChannel(final int channel, final boolean lazy) {
        if (!lazy) {
            this.channels.remove(channel);
        }
        else if (channel < this.getChannelCount() - 1) {
            this.channels.add(this.channels.remove(channel));
        }
        --this.channelCount;
    }
    
    public void copyChannel(final int sourceChannel, final int targetChannel) {
        final float[] source = this.getChannel(sourceChannel);
        final float[] target = this.getChannel(targetChannel);
        System.arraycopy(source, 0, target, 0, this.getSampleCount());
    }
    
    public void copy(final int sourceIndex, final int destIndex, final int length) {
        for (int i = 0; i < this.getChannelCount(); ++i) {
            this.copy(i, sourceIndex, destIndex, length);
        }
    }
    
    public void copy(final int channel, final int sourceIndex, final int destIndex, final int length) {
        final float[] data = this.getChannel(channel);
        final int bufferCount = this.getSampleCount();
        if (sourceIndex + length > bufferCount || destIndex + length > bufferCount || sourceIndex < 0 || destIndex < 0 || length < 0) {
            throw new IndexOutOfBoundsException("parameters exceed buffer size");
        }
        System.arraycopy(data, sourceIndex, data, destIndex, length);
    }
    
    public void expandChannel(final int targetChannelCount) {
        if (this.getChannelCount() != 1) {
            throw new IllegalArgumentException("FloatSampleBuffer: can only expand channels for mono signals.");
        }
        for (int ch = 1; ch < targetChannelCount; ++ch) {
            this.addChannel(false);
            this.copyChannel(0, ch);
        }
    }
    
    public void mixDownChannels() {
        final float[] firstChannel = this.getChannel(0);
        final int sampleCount = this.getSampleCount();
        final int channelCount = this.getChannelCount();
        for (int ch = channelCount - 1; ch > 0; --ch) {
            final float[] thisChannel = this.getChannel(ch);
            for (int i = 0; i < sampleCount; ++i) {
                final float[] array = firstChannel;
                final int n = i;
                array[n] += thisChannel[i];
            }
            this.removeChannel(ch);
        }
    }
    
    public void setSamplesFromBytes(final byte[] input, final int inByteOffset, final AudioFormat format, final int floatOffset, final int frameCount) {
        if (floatOffset < 0 || frameCount < 0 || inByteOffset < 0) {
            throw new IllegalArgumentException("FloatSampleBuffer.setSamplesFromBytes: negative inByteOffset, floatOffset, or frameCount");
        }
        if (inByteOffset + frameCount * format.getFrameSize() > input.length) {
            throw new IllegalArgumentException("FloatSampleBuffer.setSamplesFromBytes: input buffer too small.");
        }
        if (floatOffset + frameCount > this.getSampleCount()) {
            throw new IllegalArgumentException("FloatSampleBuffer.setSamplesFromBytes: frameCount too large");
        }
        FloatSampleTools.byte2float(input, inByteOffset, this.channels, floatOffset, frameCount, format);
    }
    
    public int getChannelCount() {
        return this.channelCount;
    }
    
    public int getSampleCount() {
        return this.sampleCount;
    }
    
    public float getSampleRate() {
        return this.sampleRate;
    }
    
    public void setSampleRate(final float sampleRate) {
        if (sampleRate <= 0.0f) {
            throw new IllegalArgumentException("Invalid samplerate for FloatSampleBuffer.");
        }
        this.sampleRate = sampleRate;
    }
    
    public float[] getChannel(final int channel) {
        if (channel < 0 || channel >= this.getChannelCount()) {
            throw new IllegalArgumentException("FloatSampleBuffer: invalid channel number.");
        }
        return this.channels.get(channel);
    }
    
    public Object[] getAllChannels() {
        final Object[] res = new Object[this.getChannelCount()];
        for (int ch = 0; ch < this.getChannelCount(); ++ch) {
            res[ch] = this.getChannel(ch);
        }
        return res;
    }
    
    public void setDitherBits(final float ditherBits) {
        if (ditherBits <= 0.0f) {
            throw new IllegalArgumentException("DitherBits must be greater than 0");
        }
        this.ditherBits = ditherBits;
    }
    
    public float getDitherBits() {
        return this.ditherBits;
    }
    
    public void setDitherMode(final int mode) {
        if (mode != 0 && mode != 1 && mode != 2) {
            throw new IllegalArgumentException("Illegal DitherMode");
        }
        this.ditherMode = mode;
    }
    
    public int getDitherMode() {
        return this.ditherMode;
    }
    
    protected float getConvertDitherBits(final int newFormatType) {
        boolean doDither = false;
        switch (this.ditherMode) {
            case 0: {
                doDither = ((this.originalFormatType & 0x7) > (newFormatType & 0x7));
                break;
            }
            case 1: {
                doDither = true;
                break;
            }
            case 2: {
                doDither = false;
                break;
            }
        }
        return doDither ? this.ditherBits : 0.0f;
    }
}
