// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled;

import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioSystem;

import java.util.Iterator;

import org.tritonus.share.StringHashedSet;

import javax.sound.sampled.AudioFormat;

public class Encodings extends AudioFormat.Encoding
{
    private static StringHashedSet encodings;
    
    Encodings(final String name) {
        super(name);
    }
    
    public static AudioFormat.Encoding getEncoding(final String name) {
        AudioFormat.Encoding res = (Encoding) Encodings.encodings.get(name);
        if (res == null) {
            res = new Encodings(name);
            Encodings.encodings.add(res);
        }
        return res;
    }
    
    public static boolean equals(final AudioFormat.Encoding e1, final AudioFormat.Encoding e2) {
        return e2.toString().equals(e1.toString());
    }
    
    public static AudioFormat.Encoding[] getEncodings() {
        final StringHashedSet iteratedSources = new StringHashedSet();
        final StringHashedSet retrievedTargets = new StringHashedSet();
        for (final Object source : Encodings.encodings) {
            iterateEncodings((Encoding)source, iteratedSources, retrievedTargets);
        }
        return (AudioFormat.Encoding[])retrievedTargets.toArray(new AudioFormat.Encoding[retrievedTargets.size()]);
    }
    
    private static void iterateEncodings(final AudioFormat.Encoding source, final StringHashedSet iteratedSources, final StringHashedSet retrievedTargets) {
        if (!iteratedSources.contains(source)) {
            iteratedSources.add(source);
            final AudioFormat.Encoding[] targets = AudioSystem.getTargetEncodings(source);
            for (int i = 0; i < targets.length; ++i) {
                final AudioFormat.Encoding target = targets[i];
                if (retrievedTargets.add(target.toString())) {
                    iterateEncodings(target, iteratedSources, retrievedTargets);
                }
            }
        }
    }
    
    static {
        (Encodings.encodings = new StringHashedSet()).add(AudioFormat.Encoding.PCM_SIGNED);
        Encodings.encodings.add(AudioFormat.Encoding.PCM_UNSIGNED);
        Encodings.encodings.add(AudioFormat.Encoding.ULAW);
        Encodings.encodings.add(AudioFormat.Encoding.ALAW);
    }
}
