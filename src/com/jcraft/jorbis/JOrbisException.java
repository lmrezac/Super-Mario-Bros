// 
// Decompiled by Procyon v0.5.29
// 

package com.jcraft.jorbis;

public class JOrbisException extends Exception
{
    public JOrbisException() {
    }
    
    public JOrbisException(final String s) {
        super("JOrbis: " + s);
    }
}
