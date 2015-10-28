// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.convert;

import java.util.Collections;
import java.util.HashMap;
import javax.sound.sampled.AudioFormat;
import java.io.InputStream;
import java.util.Map;
import javax.sound.sampled.AudioInputStream;

public class TAudioInputStream extends AudioInputStream
{
    private Map<String, Object> m_properties;
    private Map<String, Object> m_unmodifiableProperties;
    
    public TAudioInputStream(final InputStream inputStream, final AudioFormat audioFormat, final long lLengthInFrames) {
        super(inputStream, audioFormat, lLengthInFrames);
        this.initMaps(new HashMap<String, Object>());
    }
    
    public TAudioInputStream(final InputStream inputStream, final AudioFormat audioFormat, final long lLengthInFrames, final Map<String, Object> properties) {
        super(inputStream, audioFormat, lLengthInFrames);
        this.initMaps(properties);
    }
    
    private void initMaps(final Map<String, Object> properties) {
        this.m_properties = properties;
        this.m_unmodifiableProperties = Collections.unmodifiableMap((Map<? extends String, ?>)this.m_properties);
    }
    
    public Map<String, Object> properties() {
        return this.m_unmodifiableProperties;
    }
    
    protected void setProperty(final String key, final Object value) {
        this.m_properties.put(key, value);
    }
}
