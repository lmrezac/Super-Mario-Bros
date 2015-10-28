// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.midi;

import java.io.IOException;
import org.tritonus.share.TDebug;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;

public class MidiUtils
{
    public static int getUnsignedInteger(final byte b) {
        return (b < 0) ? (b + 256) : b;
    }
    
    public static int get14bitValue(final int nLSB, final int nMSB) {
        return (nLSB & 0x7F) | (nMSB & 0x7F) << 7;
    }
    
    public static int get14bitMSB(final int nValue) {
        return nValue >> 7 & 0x7F;
    }
    
    public static int get14bitLSB(final int nValue) {
        return nValue & 0x7F;
    }
    
    public static byte[] getVariableLengthQuantity(final long lValue) {
        final ByteArrayOutputStream data = new ByteArrayOutputStream();
        try {
            writeVariableLengthQuantity(lValue, data);
        }
        catch (IOException e) {
            if (TDebug.TraceAllExceptions) {
                TDebug.out(e);
            }
        }
        return data.toByteArray();
    }
    
    public static int writeVariableLengthQuantity(final long lValue, final OutputStream outputStream) throws IOException {
        int nLength = 0;
        boolean bWritingStarted = false;
        int nByte = (int)(lValue >> 21 & 0x7FL);
        if (nByte != 0) {
            if (outputStream != null) {
                outputStream.write(nByte | 0x80);
            }
            ++nLength;
            bWritingStarted = true;
        }
        nByte = (int)(lValue >> 14 & 0x7FL);
        if (nByte != 0 || bWritingStarted) {
            if (outputStream != null) {
                outputStream.write(nByte | 0x80);
            }
            ++nLength;
            bWritingStarted = true;
        }
        nByte = (int)(lValue >> 7 & 0x7FL);
        if (nByte != 0 || bWritingStarted) {
            if (outputStream != null) {
                outputStream.write(nByte | 0x80);
            }
            ++nLength;
        }
        nByte = (int)(lValue & 0x7FL);
        if (outputStream != null) {
            outputStream.write(nByte);
        }
        return ++nLength;
    }
}
