// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.file;

import java.io.IOException;
import javax.sound.sampled.AudioFormat;

public interface AudioOutputStream
{
    AudioFormat getFormat();
    
    long getLength();
    
    int write(byte[] p0, int p1, int p2) throws IOException;
    
    void close() throws IOException;
}
