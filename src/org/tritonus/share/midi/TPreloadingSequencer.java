// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.midi;

import javax.sound.midi.MidiMessage;
import org.tritonus.share.TDebug;
import javax.sound.midi.Sequencer;
import java.util.Collection;
import javax.sound.midi.MidiDevice;

public abstract class TPreloadingSequencer extends TSequencer
{
    private static final int DEFAULT_LATENCY = 100;
    private int m_nLatency;
    private Thread m_loaderThread;
    
    protected TPreloadingSequencer(final MidiDevice.Info info, final Collection<Sequencer.SyncMode> masterSyncModes, final Collection<Sequencer.SyncMode> slaveSyncModes) {
        super(info, masterSyncModes, slaveSyncModes);
        if (TDebug.TraceSequencer) {
            TDebug.out("TPreloadingSequencer.<init>(): begin");
        }
        this.m_nLatency = 100;
        if (TDebug.TraceSequencer) {
            TDebug.out("TPreloadingSequencer.<init>(): end");
        }
    }
    
    public void setLatency(final int nLatency) {
        this.m_nLatency = nLatency;
    }
    
    public int getLatency() {
        return this.m_nLatency;
    }
    
    protected void openImpl() {
        if (TDebug.TraceSequencer) {
            TDebug.out("AlsaSequencer.openImpl(): begin");
        }
    }
    
    public abstract void sendMessageTick(final MidiMessage p0, final long p1);
}
