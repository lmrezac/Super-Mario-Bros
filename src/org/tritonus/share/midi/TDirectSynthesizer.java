// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.midi;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.Synthesizer;

public abstract class TDirectSynthesizer extends TMidiDevice implements Synthesizer
{
    private int[] m_anBanks;
    
    public TDirectSynthesizer(final MidiDevice.Info info) {
        super(info, false, true);
        this.m_anBanks = new int[16];
        for (int i = 0; i < this.m_anBanks.length; ++i) {
            this.m_anBanks[i] = -1;
        }
    }
    
    protected void openImpl() throws MidiUnavailableException {
    }
    
    protected void closeImpl() {
    }
    
    protected void receive(final MidiMessage message, final long lTimeStamp) {
        Label_0529: {
            if (message instanceof ShortMessage) {
                final ShortMessage shortMsg = (ShortMessage)message;
                final int nChannel = shortMsg.getChannel();
                final int nCommand = shortMsg.getCommand();
                final int nData1 = shortMsg.getData1();
                final int nData2 = shortMsg.getData2();
                switch (nCommand) {
                    case 128: {
                        this.getChannel(nChannel).noteOff(nData1, nData2);
                        break;
                    }
                    case 144: {
                        this.getChannel(nChannel).noteOn(nData1, nData2);
                        break;
                    }
                    case 160: {
                        this.getChannel(nChannel).setPolyPressure(nData1, nData2);
                        break;
                    }
                    case 176: {
                        switch (nData1) {
                            case 0: {
                                this.m_anBanks[nChannel] = nData2 << 7;
                                break Label_0529;
                            }
                            case 32: {
                                final int[] anBanks = this.m_anBanks;
                                final int n = nChannel;
                                anBanks[n] |= nData2;
                                break Label_0529;
                            }
                            case 120: {
                                this.getChannel(nChannel).allSoundOff();
                                break Label_0529;
                            }
                            case 121: {
                                this.getChannel(nChannel).resetAllControllers();
                                break Label_0529;
                            }
                            case 122: {
                                this.getChannel(nChannel).localControl(nData2 == 127);
                                break Label_0529;
                            }
                            case 123: {
                                this.getChannel(nChannel).allNotesOff();
                                break Label_0529;
                            }
                            case 124: {
                                this.getChannel(nChannel).setOmni(false);
                                break Label_0529;
                            }
                            case 125: {
                                this.getChannel(nChannel).setOmni(true);
                                break Label_0529;
                            }
                            case 126: {
                                this.getChannel(nChannel).setMono(true);
                                break Label_0529;
                            }
                            case 127: {
                                this.getChannel(nChannel).setMono(false);
                                break Label_0529;
                            }
                            default: {
                                this.getChannel(nChannel).controlChange(nData1, nData2);
                                break Label_0529;
                            }
                        }
                        
                    }
                    case 192: {
                        if (this.m_anBanks[nChannel] != -1) {
                            this.getChannel(nChannel).programChange(this.m_anBanks[nChannel], nData1);
                            this.m_anBanks[nChannel] = -1;
                            break;
                        }
                        this.getChannel(nChannel).programChange(nData1);
                        break;
                    }
                    case 208: {
                        this.getChannel(nChannel).setChannelPressure(nData1);
                        break;
                    }
                    case 224: {
                        this.getChannel(nChannel).setPitchBend(nData1 | nData2 << 7);
                        break;
                    }
                }
            }
        }
    }
    
    private MidiChannel getChannel(final int nChannel) {
        return this.getChannels()[nChannel];
    }
}
