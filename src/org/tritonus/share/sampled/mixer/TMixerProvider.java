// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.mixer;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import org.tritonus.share.TDebug;
import java.util.Map;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.spi.MixerProvider;

public abstract class TMixerProvider extends MixerProvider
{
    private static final Mixer.Info[] EMPTY_MIXER_INFO_ARRAY;
    private static Map<Class, MixerProviderStruct> sm_mixerProviderStructs;
    private boolean m_bDisabled;
    
    public TMixerProvider() {
        this.m_bDisabled = false;
        if (TDebug.TraceMixerProvider) {
            TDebug.out("TMixerProvider.<init>(): begin");
        }
        if (TDebug.TraceMixerProvider) {
            TDebug.out("TMixerProvider.<init>(): end");
        }
    }
    
    protected void staticInit() {
    }
    
    private MixerProviderStruct getMixerProviderStruct() {
        if (TDebug.TraceMixerProvider) {
            TDebug.out("TMixerProvider.getMixerProviderStruct(): begin");
        }
        final Class cls = this.getClass();
        if (TDebug.TraceMixerProvider) {
            TDebug.out("TMixerProvider.getMixerProviderStruct(): called from " + cls);
        }
        synchronized (TMixerProvider.class) {
            MixerProviderStruct struct = TMixerProvider.sm_mixerProviderStructs.get(cls);
            if (struct == null) {
                if (TDebug.TraceMixerProvider) {
                    TDebug.out("TMixerProvider.getMixerProviderStruct(): creating new MixerProviderStruct for " + cls);
                }
                struct = new MixerProviderStruct();
                TMixerProvider.sm_mixerProviderStructs.put(cls, struct);
            }
            if (TDebug.TraceMixerProvider) {
                TDebug.out("TMixerProvider.getMixerProviderStruct(): end");
            }
            return struct;
        }
    }
    
    protected void disable() {
        if (TDebug.TraceMixerProvider) {
            TDebug.out("disabling " + this.getClass().getName());
        }
        this.m_bDisabled = true;
    }
    
    protected boolean isDisabled() {
        return this.m_bDisabled;
    }
    
    protected void addMixer(final Mixer mixer) {
        if (TDebug.TraceMixerProvider) {
            TDebug.out("TMixerProvider.addMixer(): begin");
        }
        final MixerProviderStruct struct = this.getMixerProviderStruct();
        synchronized (struct) {
            struct.m_mixers.add(mixer);
            if (struct.m_defaultMixer == null) {
                struct.m_defaultMixer = mixer;
            }
        }
        if (TDebug.TraceMixerProvider) {
            TDebug.out("TMixerProvider.addMixer(): end");
        }
    }
    
    protected void removeMixer(final Mixer mixer) {
        if (TDebug.TraceMixerProvider) {
            TDebug.out("TMixerProvider.removeMixer(): begin");
        }
        final MixerProviderStruct struct = this.getMixerProviderStruct();
        synchronized (struct) {
            struct.m_mixers.remove(mixer);
            if (struct.m_defaultMixer == mixer) {
                struct.m_defaultMixer = null;
            }
        }
        if (TDebug.TraceMixerProvider) {
            TDebug.out("TMixerProvider.removeMixer(): end");
        }
    }
    
    public boolean isMixerSupported(final Mixer.Info info) {
        if (TDebug.TraceMixerProvider) {
            TDebug.out("TMixerProvider.isMixerSupported(): begin");
        }
        boolean bIsSupported = false;
        final Mixer.Info[] infos = this.getMixerInfo();
        for (int i = 0; i < infos.length; ++i) {
            if (infos[i].equals(info)) {
                bIsSupported = true;
                break;
            }
        }
        if (TDebug.TraceMixerProvider) {
            TDebug.out("TMixerProvider.isMixerSupported(): end");
        }
        return bIsSupported;
    }
    
    public Mixer getMixer(final Mixer.Info info) {
        if (TDebug.TraceMixerProvider) {
            TDebug.out("TMixerProvider.getMixer(): begin");
        }
        final MixerProviderStruct struct = this.getMixerProviderStruct();
        Mixer mixerResult = null;
        synchronized (struct) {
            if (info == null) {
                mixerResult = struct.m_defaultMixer;
            }
            else {
                for (final Mixer mixer : struct.m_mixers) {
                    if (mixer.getMixerInfo().equals(info)) {
                        mixerResult = mixer;
                        break;
                    }
                }
            }
        }
        if (mixerResult == null) {
            throw new IllegalArgumentException("no mixer available for " + info);
        }
        if (TDebug.TraceMixerProvider) {
            TDebug.out("TMixerProvider.getMixer(): end");
        }
        return mixerResult;
    }
    
    public Mixer.Info[] getMixerInfo() {
        if (TDebug.TraceMixerProvider) {
            TDebug.out("TMixerProvider.getMixerInfo(): begin");
        }
        final Set<Mixer.Info> mixerInfos = new HashSet<Mixer.Info>();
        final MixerProviderStruct struct = this.getMixerProviderStruct();
        synchronized (struct) {
            for (final Mixer mixer : struct.m_mixers) {
                mixerInfos.add(mixer.getMixerInfo());
            }
        }
        if (TDebug.TraceMixerProvider) {
            TDebug.out("TMixerProvider.getMixerInfo(): end");
        }
        return mixerInfos.toArray(TMixerProvider.EMPTY_MIXER_INFO_ARRAY);
    }
    
    static {
        EMPTY_MIXER_INFO_ARRAY = new Mixer.Info[0];
        TMixerProvider.sm_mixerProviderStructs = new HashMap<Class, MixerProviderStruct>();
    }
    
    private class MixerProviderStruct
    {
        public List<Mixer> m_mixers;
        public Mixer m_defaultMixer;
        
        public MixerProviderStruct() {
            this.m_mixers = new ArrayList<Mixer>();
            this.m_defaultMixer = null;
        }
    }
}
