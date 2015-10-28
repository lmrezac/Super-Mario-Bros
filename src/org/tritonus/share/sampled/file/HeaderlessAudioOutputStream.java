// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.file;

import java.io.IOException;
import javax.sound.sampled.AudioFormat;

public class HeaderlessAudioOutputStream extends TAudioOutputStream
{
    public HeaderlessAudioOutputStream(final AudioFormat audioFormat, final long lLength, final TDataOutputStream dataOutputStream) {
        super(audioFormat, lLength, dataOutputStream, false);
    }
    
    protected void writeHeader() throws IOException {
    }
}
