// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled;

import java.util.List;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
@SuppressWarnings("unused")
public class FloatSampleTools
{
    public static final float DEFAULT_DITHER_BITS = 0.7f;
    private static Random random;
    static final int F_8 = 1;
    static final int F_16 = 2;
    static final int F_24 = 3;
    static final int F_32 = 4;
    static final int F_SAMPLE_WIDTH_MASK = 7;
    static final int F_SIGNED = 8;
    static final int F_BIGENDIAN = 16;
    static final int CT_8S = 9;
    static final int CT_8U = 1;
    static final int CT_16SB = 26;
    static final int CT_16SL = 10;
    static final int CT_24SB = 27;
    static final int CT_24SL = 11;
    static final int CT_32SB = 28;
    static final int CT_32SL = 12;
    private static final float twoPower7 = 128.0f;
    private static final float twoPower15 = 32768.0f;
    private static final float twoPower23 = 8388608.0f;
    private static final float twoPower31 = 2.14748365E9f;
    private static final float invTwoPower7 = 0.0078125f;
    private static final float invTwoPower15 = 3.0517578E-5f;
    private static final float invTwoPower23 = 1.1920929E-7f;
    private static final float invTwoPower31 = 4.656613E-10f;
    
    static void checkSupportedSampleSize(final int ssib, final int channels, final int frameSize) {
        if (ssib * channels != frameSize * 8) {
            throw new IllegalArgumentException("unsupported sample size: " + ssib + " stored in " + frameSize / channels + " bytes.");
        }
    }
    
    static int getFormatType(final AudioFormat format) {
        final boolean signed = format.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED);
        if (!signed && !format.getEncoding().equals(AudioFormat.Encoding.PCM_UNSIGNED)) {
            throw new IllegalArgumentException("unsupported encoding: only PCM encoding supported.");
        }
        if (!signed && format.getSampleSizeInBits() != 8) {
            throw new IllegalArgumentException("unsupported encoding: only 8-bit can be unsigned");
        }
        checkSupportedSampleSize(format.getSampleSizeInBits(), format.getChannels(), format.getFrameSize());
        final int formatType = getFormatType(format.getSampleSizeInBits(), signed, format.isBigEndian());
        return formatType;
    }
    
    static int getFormatType(final int ssib, final boolean signed, final boolean bigEndian) {
        final int bytesPerSample = ssib / 8;
        int res = 0;
        if (ssib == 8) {
            res = 1;
        }
        else if (ssib == 16) {
            res = 2;
        }
        else if (ssib == 24) {
            res = 3;
        }
        else if (ssib == 32) {
            res = 4;
        }
        if (res == 0) {
            throw new IllegalArgumentException("FloatSampleBuffer: unsupported sample size of " + ssib + " bits per sample.");
        }
        if (!signed && bytesPerSample > 1) {
            throw new IllegalArgumentException("FloatSampleBuffer: unsigned samples larger than 8 bit are not supported");
        }
        if (signed) {
            res |= 0x8;
        }
        if (bigEndian && ssib != 8) {
            res |= 0x10;
        }
        return res;
    }
    
    static int getSampleSize(final int formatType) {
        switch (formatType & 0x7) {
            case 1: {
                return 1;
            }
            case 2: {
                return 2;
            }
            case 3: {
                return 3;
            }
            case 4: {
                return 4;
            }
            default: {
                return 0;
            }
        }
    }
    
    static String formatType2Str(final int formatType) {
        String res = "" + formatType + ": ";
        switch (formatType & 0x7) {
            case 1: {
                res += "8bit";
                break;
            }
            case 2: {
                res += "16bit";
                break;
            }
            case 3: {
                res += "24bit";
                break;
            }
            case 4: {
                res += "32bit";
                break;
            }
        }
        res += (((formatType & 0x8) == 0x8) ? " signed" : " unsigned");
        if ((formatType & 0x7) != 0x1) {
            res += (((formatType & 0x10) == 0x10) ? " big endian" : " little endian");
        }
        return res;
    }
    
    public static void byte2float(final byte[] input, int inByteOffset, final List<float[]> output, final int outOffset, final int frameCount, final AudioFormat format) {
        for (int channel = 0; channel < format.getChannels(); ++channel) {
            float[] data;
            if (output.size() < channel) {
                data = new float[frameCount + outOffset];
                output.add(data);
            }
            else {
                data = output.get(channel);
                if (data.length < frameCount + outOffset) {
                    data = new float[frameCount + outOffset];
                    output.set(channel, data);
                }
            }
            byte2floatGeneric(input, inByteOffset, format.getFrameSize(), data, outOffset, frameCount, format);
            inByteOffset += format.getFrameSize() / format.getChannels();
        }
    }
    
    public static void byte2floatInterleaved(final byte[] input, final int inByteOffset, final float[] output, final int outOffset, final int frameCount, final AudioFormat format) {
        byte2floatGeneric(input, inByteOffset, format.getFrameSize() / format.getChannels(), output, outOffset, frameCount * format.getChannels(), format);
    }
    
    static void byte2floatGeneric(final byte[] input, final int inByteOffset, final int inByteStep, final float[] output, final int outOffset, final int sampleCount, final AudioFormat format) {
        final int formatType = getFormatType(format);
        byte2floatGeneric(input, inByteOffset, inByteStep, output, outOffset, sampleCount, formatType);
    }
    
    static void byte2floatGeneric(final byte[] input, final int inByteOffset, final int inByteStep, final float[] output, final int outOffset, final int sampleCount, final int formatType) {
        for (int endCount = outOffset + sampleCount, inIndex = inByteOffset, outIndex = outOffset; outIndex < endCount; ++outIndex, inIndex += inByteStep) {
            switch (formatType) {
                case 9: {
                    output[outIndex] = input[inIndex] * 0.0078125f;
                    break;
                }
                case 1: {
                    output[outIndex] = ((input[inIndex] & 0xFF) - 128) * 0.0078125f;
                    break;
                }
                case 26: {
                    output[outIndex] = (input[inIndex] << 8 | (input[inIndex + 1] & 0xFF)) * 3.0517578E-5f;
                    break;
                }
                case 10: {
                    output[outIndex] = (input[inIndex + 1] << 8 | (input[inIndex] & 0xFF)) * 3.0517578E-5f;
                    break;
                }
                case 27: {
                    output[outIndex] = (input[inIndex] << 16 | (input[inIndex + 1] & 0xFF) << 8 | (input[inIndex + 2] & 0xFF)) * 1.1920929E-7f;
                    break;
                }
                case 11: {
                    output[outIndex] = (input[inIndex + 2] << 16 | (input[inIndex + 1] & 0xFF) << 8 | (input[inIndex] & 0xFF)) * 1.1920929E-7f;
                    break;
                }
                case 28: {
                    output[outIndex] = (input[inIndex] << 24 | (input[inIndex + 1] & 0xFF) << 16 | (input[inIndex + 2] & 0xFF) << 8 | (input[inIndex + 3] & 0xFF)) * 4.656613E-10f;
                    break;
                }
                case 12: {
                    output[outIndex] = (input[inIndex + 3] << 24 | (input[inIndex + 2] & 0xFF) << 16 | (input[inIndex + 1] & 0xFF) << 8 | (input[inIndex] & 0xFF)) * 4.656613E-10f;
                    break;
                }
                default: {
                    throw new IllegalArgumentException("unsupported format=" + formatType2Str(formatType));
                }
            }
        }
    }
    
    private static byte quantize8(float sample, final float ditherBits) {
        if (ditherBits != 0.0f) {
            sample += FloatSampleTools.random.nextFloat() * ditherBits;
        }
        if (sample >= 127.0f) {
            return 127;
        }
        if (sample <= -128.0f) {
            return -128;
        }
        return (byte)((sample < 0.0f) ? (sample - 0.5f) : (sample + 0.5f));
    }
    
    private static int quantize16(float sample, final float ditherBits) {
        if (ditherBits != 0.0f) {
            sample += FloatSampleTools.random.nextFloat() * ditherBits;
        }
        if (sample >= 32767.0f) {
            return 32767;
        }
        if (sample <= -32768.0f) {
            return -32768;
        }
        return (int)((sample < 0.0f) ? (sample - 0.5f) : (sample + 0.5f));
    }
    
    private static int quantize24(float sample, final float ditherBits) {
        if (ditherBits != 0.0f) {
            sample += FloatSampleTools.random.nextFloat() * ditherBits;
        }
        if (sample >= 8388607.0f) {
            return 8388607;
        }
        if (sample <= -8388608.0f) {
            return -8388608;
        }
        return (int)((sample < 0.0f) ? (sample - 0.5f) : (sample + 0.5f));
    }
    
    private static int quantize32(float sample, final float ditherBits) {
        if (ditherBits != 0.0f) {
            sample += FloatSampleTools.random.nextFloat() * ditherBits;
        }
        if (sample >= 2.14748365E9f) {
            return Integer.MAX_VALUE;
        }
        if (sample <= -2.14748365E9f) {
            return Integer.MIN_VALUE;
        }
        return (int)((sample < 0.0f) ? (sample - 0.5f) : (sample + 0.5f));
    }
    
    public static void float2byte(final List<?> input, final int inOffset, final byte[] output, int outByteOffset, final int frameCount, final AudioFormat format, final float ditherBits) {
        for (int channel = 0; channel < format.getChannels(); ++channel) {
            final float[] data = (float[]) input.get(channel);
            float2byteGeneric(data, inOffset, output, outByteOffset, format.getFrameSize(), frameCount, format, ditherBits);
            outByteOffset += format.getFrameSize() / format.getChannels();
        }
    }
    
    public static void float2byteInterleaved(final float[] input, final int inOffset, final byte[] output, final int outByteOffset, final int frameCount, final AudioFormat format, final float ditherBits) {
        float2byteGeneric(input, inOffset, output, outByteOffset, format.getFrameSize() / format.getChannels(), frameCount * format.getChannels(), format, ditherBits);
    }
    
    static void float2byteGeneric(final float[] input, final int inOffset, final byte[] output, final int outByteOffset, final int outByteStep, final int sampleCount, final AudioFormat format, final float ditherBits) {
        final int formatType = getFormatType(format);
        float2byteGeneric(input, inOffset, output, outByteOffset, outByteStep, sampleCount, formatType, ditherBits);
    }
    
    static void float2byteGeneric(final float[] input, final int inOffset, final byte[] output, final int outByteOffset, final int outByteStep, final int sampleCount, final int formatType, final float ditherBits) {
        if (inOffset < 0 || inOffset + sampleCount > input.length || sampleCount < 0) {
            throw new IllegalArgumentException("invalid input index: input.length=" + input.length + " inOffset=" + inOffset + " sampleCount=" + sampleCount);
        }
        if (outByteOffset < 0 || outByteOffset + sampleCount * outByteStep >= output.length + outByteStep || outByteStep < getSampleSize(formatType)) {
            throw new IllegalArgumentException("invalid output index: output.length=" + output.length + " outByteOffset=" + outByteOffset + " outByteStep=" + outByteStep + " sampleCount=" + sampleCount + " format=" + formatType2Str(formatType));
        }
        if (ditherBits != 0.0f && FloatSampleTools.random == null) {
            FloatSampleTools.random = new Random();
        }
        for (int endSample = inOffset + sampleCount, outIndex = outByteOffset, inIndex = inOffset; inIndex < endSample; ++inIndex, outIndex += outByteStep) {
            switch (formatType) {
                case 9: {
                    output[outIndex] = quantize8(input[inIndex] * 128.0f, ditherBits);
                    break;
                }
                case 1: {
                    output[outIndex] = (byte)(quantize8(input[inIndex] * 128.0f, ditherBits) + 128);
                    break;
                }
                case 26: {
                    final int iSample = quantize16(input[inIndex] * 32768.0f, ditherBits);
                    output[outIndex] = (byte)(iSample >> 8);
                    output[outIndex + 1] = (byte)(iSample & 0xFF);
                    break;
                }
                case 10: {
                    final int iSample = quantize16(input[inIndex] * 32768.0f, ditherBits);
                    output[outIndex + 1] = (byte)(iSample >> 8);
                    output[outIndex] = (byte)(iSample & 0xFF);
                    break;
                }
                case 27: {
                    final int iSample = quantize24(input[inIndex] * 8388608.0f, ditherBits);
                    output[outIndex] = (byte)(iSample >> 16);
                    output[outIndex + 1] = (byte)(iSample >>> 8 & 0xFF);
                    output[outIndex + 2] = (byte)(iSample & 0xFF);
                    break;
                }
                case 11: {
                    final int iSample = quantize24(input[inIndex] * 8388608.0f, ditherBits);
                    output[outIndex + 2] = (byte)(iSample >> 16);
                    output[outIndex + 1] = (byte)(iSample >>> 8 & 0xFF);
                    output[outIndex] = (byte)(iSample & 0xFF);
                    break;
                }
                case 28: {
                    final int iSample = quantize32(input[inIndex] * 2.14748365E9f, ditherBits);
                    output[outIndex] = (byte)(iSample >> 24);
                    output[outIndex + 1] = (byte)(iSample >>> 16 & 0xFF);
                    output[outIndex + 2] = (byte)(iSample >>> 8 & 0xFF);
                    output[outIndex + 3] = (byte)(iSample & 0xFF);
                    break;
                }
                case 12: {
                    final int iSample = quantize32(input[inIndex] * 2.14748365E9f, ditherBits);
                    output[outIndex + 3] = (byte)(iSample >> 24);
                    output[outIndex + 2] = (byte)(iSample >>> 16 & 0xFF);
                    output[outIndex + 1] = (byte)(iSample >>> 8 & 0xFF);
                    output[outIndex] = (byte)(iSample & 0xFF);
                    break;
                }
                default: {
                    throw new IllegalArgumentException("unsupported format=" + formatType2Str(formatType));
                }
            }
        }
    }
    
    static {
        FloatSampleTools.random = null;
    }
}
