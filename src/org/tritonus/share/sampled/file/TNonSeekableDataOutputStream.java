// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.file;

import java.io.IOException;
import java.io.OutputStream;
import java.io.DataOutputStream;

public class TNonSeekableDataOutputStream extends DataOutputStream implements TDataOutputStream
{
    public TNonSeekableDataOutputStream(final OutputStream outputStream) {
        super(outputStream);
    }
    
    public boolean supportsSeek() {
        return false;
    }
    
    public void seek(final long position) throws IOException {
        throw new IllegalArgumentException("TNonSeekableDataOutputStream: Call to seek not allowed.");
    }
    
    public long getFilePointer() throws IOException {
        throw new IllegalArgumentException("TNonSeekableDataOutputStream: Call to getFilePointer not allowed.");
    }
    
    public long length() throws IOException {
        throw new IllegalArgumentException("TNonSeekableDataOutputStream: Call to length not allowed.");
    }
    
    public void writeLittleEndian32(final int value) throws IOException {
        this.writeByte(value & 0xFF);
        this.writeByte(value >> 8 & 0xFF);
        this.writeByte(value >> 16 & 0xFF);
        this.writeByte(value >> 24 & 0xFF);
    }
    
    public void writeLittleEndian16(final short value) throws IOException {
        this.writeByte(value & 0xFF);
        this.writeByte(value >> 8 & 0xFF);
    }
}
