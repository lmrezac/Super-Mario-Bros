// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled;

import org.tritonus.share.StringHashedSet;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFileFormat.Type;

public class AudioFileTypes extends AudioFileFormat.Type
{
   	private static StringHashedSet<Object> types;
    
    AudioFileTypes(final String name, final String ext) {
        super(name, ext);
    }
    
    public static AudioFileFormat.Type getType(final String name) {
        return getType(name, null);
    }
    
    public static AudioFileFormat.Type getType(final String name, final String extension) {
        AudioFileFormat.Type res = (Type) AudioFileTypes.types.get(name);
        if (res == null) {
            if (extension == null) {
                return null;
            }
            res = new AudioFileTypes(name, extension);
            AudioFileTypes.types.add(res);
        }
        return res;
    }
    
    public static boolean equals(final AudioFileFormat.Type t1, final AudioFileFormat.Type t2) {
        return t2.toString().equals(t1.toString());
    }
    
    static {
        (AudioFileTypes.types = new StringHashedSet<Object>()).add(AudioFileFormat.Type.AIFF);
        AudioFileTypes.types.add(AudioFileFormat.Type.AIFC);
        AudioFileTypes.types.add(AudioFileFormat.Type.AU);
        AudioFileTypes.types.add(AudioFileFormat.Type.SND);
        AudioFileTypes.types.add(AudioFileFormat.Type.WAVE);
    }
}
