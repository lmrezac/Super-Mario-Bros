// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.file;

import java.io.IOException;
import java.io.DataOutput;

public interface TDataOutputStream extends DataOutput
{
    boolean supportsSeek();
    
    void seek(long p0) throws IOException;
    
    long getFilePointer() throws IOException;
    
    long length() throws IOException;
    
    void writeLittleEndian32(int p0) throws IOException;
    
    void writeLittleEndian16(short p0) throws IOException;
    
    void close() throws IOException;
}
