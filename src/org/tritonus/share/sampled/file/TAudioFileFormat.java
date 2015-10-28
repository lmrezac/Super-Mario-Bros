// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.file;

import java.util.Collections;
import java.util.HashMap;
import javax.sound.sampled.AudioFormat;
import java.util.Map;
import javax.sound.sampled.AudioFileFormat;

public class TAudioFileFormat extends AudioFileFormat
{
    private Map<String, Object> m_properties;
    private Map<String, Object> m_unmodifiableProperties;
    
    public TAudioFileFormat(final Type type, final AudioFormat audioFormat, final int nLengthInFrames, final int nLengthInBytes) {
        super(type, nLengthInBytes, audioFormat, nLengthInFrames);
    }
    
    public TAudioFileFormat(final Type type, final AudioFormat audioFormat, final int nLengthInFrames, final int nLengthInBytes, final Map<String, Object> properties) {
        super(type, nLengthInBytes, audioFormat, nLengthInFrames);
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
