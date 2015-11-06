// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.midi;

import java.io.IOException;
import java.io.InputStream;
import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;

import org.tritonus.share.ArraySet;
import org.tritonus.share.TDebug;

public abstract class TSequencer extends TMidiDevice implements Sequencer
{
    @SuppressWarnings("unused")
	private static final float MPQ_BPM_FACTOR = 6.0E7f;
    private static final SyncMode[] EMPTY_SYNCMODE_ARRAY;
    private boolean m_bRunning;
    private Sequence m_sequence;
    private Set<MetaEventListener> m_metaListeners;
    private Set<ControllerEventListener>[] m_aControllerListeners;
    private float m_fNominalTempoInMPQ;
    private float m_fTempoFactor;
    private Collection<SyncMode> m_masterSyncModes;
    private Collection<SyncMode> m_slaveSyncModes;
    private SyncMode m_masterSyncMode;
    private SyncMode m_slaveSyncMode;
    private BitSet m_muteBitSet;
    private BitSet m_soloBitSet;
    private BitSet m_enabledBitSet;
    private long m_lLoopStartPoint;
    private long m_lLoopEndPoint;
    private int m_nLoopCount;
    
    @SuppressWarnings("unchecked")
	protected TSequencer(final MidiDevice.Info info, final Collection<SyncMode> masterSyncModes, final Collection<SyncMode> slaveSyncModes) {
        super(info);
        this.m_bRunning = false;
        this.m_sequence = null;
        this.m_metaListeners = new ArraySet<MetaEventListener>();
        this.m_aControllerListeners = (Set<ControllerEventListener>[])new Set[128];
        this.setTempoFactor(1.0f);
        this.setTempoInMPQ(500000.0f);
        this.m_masterSyncModes = masterSyncModes;
        this.m_slaveSyncModes = slaveSyncModes;
        if (this.getMasterSyncModes().length > 0) {
            this.m_masterSyncMode = this.getMasterSyncModes()[0];
        }
        if (this.getSlaveSyncModes().length > 0) {
            this.m_slaveSyncMode = this.getSlaveSyncModes()[0];
        }
        this.m_muteBitSet = new BitSet();
        this.m_soloBitSet = new BitSet();
        this.m_enabledBitSet = new BitSet();
        this.updateEnabled();
        this.setLoopStartPoint(0L);
        this.setLoopEndPoint(-1L);
        this.setLoopCount(0);
    }
    
    public void setSequence(final Sequence sequence) throws InvalidMidiDataException {
        if (this.getSequence() != sequence) {
            this.m_sequence = sequence;
            this.setSequenceImpl();
            this.setTempoFactor(1.0f);
        }
    }
    
    protected void setSequenceImpl() {
    }
    
    public void setSequence(final InputStream inputStream) throws InvalidMidiDataException, IOException {
        final Sequence sequence = MidiSystem.getSequence(inputStream);
        this.setSequence(sequence);
    }
    
    public Sequence getSequence() {
        return this.m_sequence;
    }
    
    public void setLoopStartPoint(final long lTick) {
        this.m_lLoopStartPoint = lTick;
    }
    
    public long getLoopStartPoint() {
        return this.m_lLoopStartPoint;
    }
    
    public void setLoopEndPoint(final long lTick) {
        this.m_lLoopEndPoint = lTick;
    }
    
    public long getLoopEndPoint() {
        return this.m_lLoopEndPoint;
    }
    
    public void setLoopCount(final int nLoopCount) {
        this.m_nLoopCount = nLoopCount;
    }
    
    public int getLoopCount() {
        return this.m_nLoopCount;
    }
    
    public synchronized void start() {
        this.checkOpen();
        if (!this.isRunning()) {
            this.m_bRunning = true;
            this.startImpl();
        }
    }
    
    protected void startImpl() {
    }
    
    public synchronized void stop() {
        this.checkOpen();
        if (this.isRunning()) {
            this.stopImpl();
            this.m_bRunning = false;
        }
    }
    
    protected void stopImpl() {
    }
    
    public synchronized boolean isRunning() {
        return this.m_bRunning;
    }
    
    protected void checkOpen() {
        if (!this.isOpen()) {
            throw new IllegalStateException("Sequencer is not open");
        }
    }
    
    protected int getResolution() {
        final Sequence sequence = this.getSequence();
        int nResolution;
        if (sequence != null) {
            nResolution = sequence.getResolution();
        }
        else {
            nResolution = 1;
        }
        return nResolution;
    }
    
    protected void setRealTempo() {
        float fTempoFactor = this.getTempoFactor();
        if (fTempoFactor == 0.0f) {
            fTempoFactor = 0.01f;
        }
        final float fRealTempo = this.getTempoInMPQ() / fTempoFactor;
        if (TDebug.TraceSequencer) {
            TDebug.out("TSequencer.setRealTempo(): real tempo: " + fRealTempo);
        }
        this.setTempoImpl(fRealTempo);
    }
    
    public float getTempoInBPM() {
        final float fBPM = 6.0E7f / this.getTempoInMPQ();
        return fBPM;
    }
    
    public void setTempoInBPM(final float fBPM) {
        final float fMPQ = 6.0E7f / fBPM;
        this.setTempoInMPQ(fMPQ);
    }
    
    public float getTempoInMPQ() {
        return this.m_fNominalTempoInMPQ;
    }
    
    public void setTempoInMPQ(final float fMPQ) {
        this.m_fNominalTempoInMPQ = fMPQ;
        this.setRealTempo();
    }
    
    public void setTempoFactor(final float fFactor) {
        this.m_fTempoFactor = fFactor;
        this.setRealTempo();
    }
    
    public float getTempoFactor() {
        return this.m_fTempoFactor;
    }
    
    protected abstract void setTempoImpl(final float p0);
    
    public long getTickLength() {
        long lLength = 0L;
        if (this.getSequence() != null) {
            lLength = this.getSequence().getTickLength();
        }
        return lLength;
    }
    
    public long getMicrosecondLength() {
        long lLength = 0L;
        if (this.getSequence() != null) {
            lLength = this.getSequence().getMicrosecondLength();
        }
        return lLength;
    }
    
    public boolean addMetaEventListener(final MetaEventListener listener) {
        synchronized (this.m_metaListeners) {
            return this.m_metaListeners.add(listener);
        }
    }
    
    public void removeMetaEventListener(final MetaEventListener listener) {
        synchronized (this.m_metaListeners) {
            this.m_metaListeners.remove(listener);
        }
    }
    
    protected Iterator<MetaEventListener> getMetaEventListeners() {
        synchronized (this.m_metaListeners) {
            return this.m_metaListeners.iterator();
        }
    }
    
    protected void sendMetaMessage(final MetaMessage message) {
        final Iterator<MetaEventListener> iterator = this.getMetaEventListeners();
        while (iterator.hasNext()) {
            final MetaEventListener metaEventListener = iterator.next();
            final MetaMessage copiedMessage = (MetaMessage)message.clone();
            metaEventListener.meta(copiedMessage);
        }
    }
    
    public int[] addControllerEventListener(final ControllerEventListener listener, final int[] anControllers) {
        synchronized (this.m_aControllerListeners) {
            if (anControllers == null) {
                for (int i = 0; i < 128; ++i) {
                    this.addControllerListener(i, listener);
                }
            }
            else {
                for (int i = 0; i < anControllers.length; ++i) {
                    this.addControllerListener(anControllers[i], listener);
                }
            }
        }
        return this.getListenedControllers(listener);
    }
    
    private void addControllerListener(final int i, final ControllerEventListener listener) {
        if (this.m_aControllerListeners[i] == null) {
            this.m_aControllerListeners[i] = new ArraySet<ControllerEventListener>();
        }
        this.m_aControllerListeners[i].add(listener);
    }
    
    public int[] removeControllerEventListener(final ControllerEventListener listener, final int[] anControllers) {
        synchronized (this.m_aControllerListeners) {
            if (anControllers == null) {
                for (int i = 0; i < 128; ++i) {
                    this.removeControllerListener(i, listener);
                }
            }
            else {
                for (int i = 0; i < anControllers.length; ++i) {
                    this.removeControllerListener(anControllers[i], listener);
                }
            }
        }
        return this.getListenedControllers(listener);
    }
    
    private void removeControllerListener(final int i, final ControllerEventListener listener) {
        if (this.m_aControllerListeners[i] != null) {
            this.m_aControllerListeners[i].add(listener);
        }
    }
    
    private int[] getListenedControllers(final ControllerEventListener listener) {
        final int[] anControllers = new int[128];
        int nIndex = 0;
        for (int nController = 0; nController < 128; ++nController) {
            if (this.m_aControllerListeners[nController] != null && this.m_aControllerListeners[nController].contains(listener)) {
                anControllers[nIndex] = nController;
                ++nIndex;
            }
        }
        final int[] anResultControllers = new int[nIndex];
        System.arraycopy(anControllers, 0, anResultControllers, 0, nIndex);
        return anResultControllers;
    }
    
    protected void sendControllerEvent(final ShortMessage message) {
        final int nController = message.getData1();
        if (this.m_aControllerListeners[nController] != null) {
            for (final ControllerEventListener controllerEventListener : this.m_aControllerListeners[nController]) {
                final ShortMessage copiedMessage = (ShortMessage)message.clone();
                controllerEventListener.controlChange(copiedMessage);
            }
        }
    }
    
    protected void notifyListeners(final MidiMessage message) {
        if (message instanceof MetaMessage) {
            this.sendMetaMessage((MetaMessage)message);
        }
        else if (message instanceof ShortMessage && ((ShortMessage)message).getCommand() == 176) {
            this.sendControllerEvent((ShortMessage)message);
        }
    }
    
    public SyncMode getMasterSyncMode() {
        return this.m_masterSyncMode;
    }
    
    public void setMasterSyncMode(final SyncMode syncMode) {
        if (this.m_masterSyncModes.contains(syncMode)) {
            if (!this.getMasterSyncMode().equals(syncMode)) {
                this.setMasterSyncModeImpl(this.m_masterSyncMode = syncMode);
            }
            return;
        }
        throw new IllegalArgumentException("sync mode not allowed: " + syncMode);
    }
    
    protected void setMasterSyncModeImpl(final SyncMode syncMode) {
    }
    
    public SyncMode[] getMasterSyncModes() {
        final SyncMode[] syncModes = this.m_masterSyncModes.toArray(TSequencer.EMPTY_SYNCMODE_ARRAY);
        return syncModes;
    }
    
    public SyncMode getSlaveSyncMode() {
        return this.m_slaveSyncMode;
    }
    
    public void setSlaveSyncMode(final SyncMode syncMode) {
        if (this.m_slaveSyncModes.contains(syncMode)) {
            if (!this.getSlaveSyncMode().equals(syncMode)) {
                this.setSlaveSyncModeImpl(this.m_slaveSyncMode = syncMode);
            }
            return;
        }
        throw new IllegalArgumentException("sync mode not allowed: " + syncMode);
    }
    
    protected void setSlaveSyncModeImpl(final SyncMode syncMode) {
    }
    
    public SyncMode[] getSlaveSyncModes() {
        final SyncMode[] syncModes = this.m_slaveSyncModes.toArray(TSequencer.EMPTY_SYNCMODE_ARRAY);
        return syncModes;
    }
    
    public boolean getTrackSolo(final int nTrack) {
        boolean bSoloed = false;
        if (this.getSequence() != null && nTrack < this.getSequence().getTracks().length) {
            bSoloed = this.m_soloBitSet.get(nTrack);
        }
        return bSoloed;
    }
    
    public void setTrackSolo(final int nTrack, final boolean bSolo) {
        if (this.getSequence() != null && nTrack < this.getSequence().getTracks().length) {
            final boolean bOldState = this.m_soloBitSet.get(nTrack);
            if (bSolo != bOldState) {
                if (bSolo) {
                    this.m_soloBitSet.set(nTrack);
                }
                else {
                    this.m_soloBitSet.clear(nTrack);
                }
                this.updateEnabled();
                this.setTrackSoloImpl(nTrack, bSolo);
            }
        }
    }
    
    protected void setTrackSoloImpl(final int nTrack, final boolean bSolo) {
    }
    
    public boolean getTrackMute(final int nTrack) {
        boolean bMuted = false;
        if (this.getSequence() != null && nTrack < this.getSequence().getTracks().length) {
            bMuted = this.m_muteBitSet.get(nTrack);
        }
        return bMuted;
    }
    
    public void setTrackMute(final int nTrack, final boolean bMute) {
        if (this.getSequence() != null && nTrack < this.getSequence().getTracks().length) {
            final boolean bOldState = this.m_muteBitSet.get(nTrack);
            if (bMute != bOldState) {
                if (bMute) {
                    this.m_muteBitSet.set(nTrack);
                }
                else {
                    this.m_muteBitSet.clear(nTrack);
                }
                this.updateEnabled();
                this.setTrackMuteImpl(nTrack, bMute);
            }
        }
    }
    
    protected void setTrackMuteImpl(final int nTrack, final boolean bMute) {
    }
    
    private void updateEnabled() {
        final BitSet oldEnabledBitSet = (BitSet)this.m_enabledBitSet.clone();
        final boolean bSoloExists = this.m_soloBitSet.length() > 0;
        if (bSoloExists) {
            this.m_enabledBitSet = (BitSet)this.m_soloBitSet.clone();
        }
        else {
            for (int i = 0; i < this.m_muteBitSet.size(); ++i) {
                if (this.m_muteBitSet.get(i)) {
                    this.m_enabledBitSet.clear(i);
                }
                else {
                    this.m_enabledBitSet.set(i);
                }
            }
        }
        oldEnabledBitSet.xor(this.m_enabledBitSet);
        for (int i = 0; i < oldEnabledBitSet.size(); ++i) {
            if (oldEnabledBitSet.get(i)) {
                this.setTrackEnabledImpl(i, this.m_enabledBitSet.get(i));
            }
        }
    }
    
    protected void setTrackEnabledImpl(final int nTrack, final boolean bEnabled) {
    }
    
    protected boolean isTrackEnabled(final int nTrack) {
        return this.m_enabledBitSet.get(nTrack);
    }
    
    public void setLatency(final int nMilliseconds) {
    }
    
    public int getLatency() {
        return -1;
    }
    
    static {
        EMPTY_SYNCMODE_ARRAY = new SyncMode[0];
    }
}
