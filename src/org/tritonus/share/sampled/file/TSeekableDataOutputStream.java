// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.file;

import java.io.IOException;
import java.io.File;
import java.io.RandomAccessFile;

public class TSeekableDataOutputStream extends RandomAccessFile implements TDataOutputStream
{
    public TSeekableDataOutputStream(final File file) throws IOException {
        super(file, "rw");
    }
    
    public boolean supportsSeek() {
        return true;
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
