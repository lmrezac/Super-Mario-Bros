// 
// Decompiled by Procyon v0.5.29
// 

package com.jcraft.jorbis;

public class JOrbisException extends Exception
{
    private static final long serialVersionUID = -8275147793421088919L;

	public JOrbisException() {
    }
    
    public JOrbisException(final String s) {
        super("JOrbis: " + s);
    }
}
