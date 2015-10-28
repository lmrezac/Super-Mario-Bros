// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.AudioFormat;

public class TAudioFormat extends AudioFormat
{
    private Map<String, Object> m_properties;
    private Map<String, Object> m_unmodifiableProperties;
    
    public TAudioFormat(final Encoding encoding, final float sampleRate, final int sampleSizeInBits, final int channels, final int frameSize, final float frameRate, final boolean bigEndian, final Map<String, Object> properties) {
        super(encoding, sampleRate, sampleSizeInBits, channels, frameSize, frameRate, bigEndian);
        this.initMaps(properties);
    }
    
    public TAudioFormat(final float sampleRate, final int sampleSizeInBits, final int channels, final boolean signed, final boolean bigEndian, final Map<String, Object> properties) {
        super(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        this.initMaps(properties);
    }
    
    private void initMaps(final Map<String, Object> properties) {
        (this.m_properties = new HashMap<String, Object>()).putAll(properties);
        this.m_unmodifiableProperties = Collections.unmodifiableMap((Map<? extends String, ?>)this.m_properties);
    }
    
    public Map<String, Object> properties() {
        return this.m_unmodifiableProperties;
    }
    
    protected void setProperty(final String key, final Object value) {
        this.m_properties.put(key, value);
    }
}
