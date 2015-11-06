// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.midi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

import org.tritonus.share.TDebug;

public abstract class TMidiDevice implements MidiDevice
{
    private MidiDevice.Info m_info;
    private boolean m_bOpen;
    private boolean m_bUseIn;
    private boolean m_bUseOut;
    private List<Receiver> m_receivers;
    private List<Transmitter> m_transmitters;
    
    public TMidiDevice(final MidiDevice.Info info) {
        this(info, true, true);
    }
    
    public TMidiDevice(final MidiDevice.Info info, final boolean bUseIn, final boolean bUseOut) {
        this.m_info = info;
        this.m_bUseIn = bUseIn;
        this.m_bUseOut = bUseOut;
        this.m_bOpen = false;
        this.m_receivers = new ArrayList<Receiver>();
        this.m_transmitters = new ArrayList<Transmitter>();
    }
    
    public MidiDevice.Info getDeviceInfo() {
        return this.m_info;
    }
    
    public void open() throws MidiUnavailableException {
        if (TDebug.TraceMidiDevice) {
            TDebug.out("TMidiDevice.open(): begin");
        }
        if (!this.isOpen()) {
            this.m_bOpen = true;
            this.openImpl();
        }
        if (TDebug.TraceMidiDevice) {
            TDebug.out("TMidiDevice.open(): end");
        }
    }
    
    protected void openImpl() throws MidiUnavailableException {
        if (TDebug.TraceMidiDevice) {
            TDebug.out("TMidiDevice.openImpl(): begin");
        }
        if (TDebug.TraceMidiDevice) {
            TDebug.out("TMidiDevice.openImpl(): end");
        }
    }
    
    public void close() {
        if (TDebug.TraceMidiDevice) {
            TDebug.out("TMidiDevice.close(): begin");
        }
        if (this.isOpen()) {
            this.closeImpl();
            this.m_bOpen = false;
        }
        if (TDebug.TraceMidiDevice) {
            TDebug.out("TMidiDevice.close(): end");
        }
    }
    
    protected void closeImpl() {
        if (TDebug.TraceMidiDevice) {
            TDebug.out("TMidiDevice.closeImpl(): begin");
        }
        if (TDebug.TraceMidiDevice) {
            TDebug.out("TMidiDevice.closeImpl(): end");
        }
    }
    
    public boolean isOpen() {
        return this.m_bOpen;
    }
    
    protected boolean getUseIn() {
        return this.m_bUseIn;
    }
    
    protected boolean getUseOut() {
        return this.m_bUseOut;
    }
    
    public long getMicrosecondPosition() {
        return -1L;
    }
    
    public int getMaxReceivers() {
        int nMaxReceivers = 0;
        if (this.getUseOut()) {
            nMaxReceivers = -1;
        }
        return nMaxReceivers;
    }
    
    public int getMaxTransmitters() {
        int nMaxTransmitters = 0;
        if (this.getUseIn()) {
            nMaxTransmitters = -1;
        }
        return nMaxTransmitters;
    }
    
    public Receiver getReceiver() throws MidiUnavailableException {
        if (!this.getUseOut()) {
            throw new MidiUnavailableException("Receivers are not supported by this device");
        }
        return new TReceiver();
    }
    
    public Transmitter getTransmitter() throws MidiUnavailableException {
        if (!this.getUseIn()) {
            throw new MidiUnavailableException("Transmitters are not supported by this device");
        }
        return new TTransmitter();
    }
    
    public List<Receiver> getReceivers() {
        return Collections.unmodifiableList((List<? extends Receiver>)this.m_receivers);
    }
    
    public List<Transmitter> getTransmitters() {
        return Collections.unmodifiableList((List<? extends Transmitter>)this.m_transmitters);
    }
    
    protected void receive(final MidiMessage message, final long lTimeStamp) {
        if (TDebug.TraceMidiDevice) {
            TDebug.out("### [should be overridden] TMidiDevice.receive(): message " + message);
        }
    }
    
    private void addReceiver(final Receiver receiver) {
        synchronized (this.m_receivers) {
            this.m_receivers.add(receiver);
        }
    }
    
    private void removeReceiver(final Receiver receiver) {
        synchronized (this.m_receivers) {
            this.m_receivers.remove(receiver);
        }
    }
    
    private void addTransmitter(final Transmitter transmitter) {
        synchronized (this.m_transmitters) {
            this.m_transmitters.add(transmitter);
        }
    }
    
    private void removeTransmitter(final Transmitter transmitter) {
        synchronized (this.m_transmitters) {
            this.m_transmitters.remove(transmitter);
        }
    }
    
    protected void sendImpl(final MidiMessage message, final long lTimeStamp) {
        if (TDebug.TraceMidiDevice) {
            TDebug.out("TMidiDevice.sendImpl(): begin");
        }
        for (final Transmitter transmitter : this.m_transmitters) {
            MidiMessage copiedMessage = null;
            if (message instanceof MetaMessage) {
                final MetaMessage origMessage = (MetaMessage)message;
                final MetaMessage metaMessage = new MetaMessage();
                try {
                    metaMessage.setMessage(origMessage.getType(), origMessage.getData(), origMessage.getData().length);
                }
                catch (InvalidMidiDataException e) {
                    if (TDebug.TraceAllExceptions) {
                        TDebug.out(e);
                    }
                }
                copiedMessage = metaMessage;
            }
            else {
                copiedMessage = (MidiMessage)message.clone();
            }
            if (message instanceof MetaMessage) {
                if (TDebug.TraceMidiDevice) {
                    TDebug.out("TMidiDevice.sendImpl(): MetaMessage.getData().length (original): " + ((MetaMessage)message).getData().length);
                }
                if (TDebug.TraceMidiDevice) {
                    TDebug.out("TMidiDevice.sendImpl(): MetaMessage.getData().length (cloned): " + ((MetaMessage)copiedMessage).getData().length);
                }
            }
            ((Receiver) transmitter).send(copiedMessage, lTimeStamp);
        }
        if (TDebug.TraceMidiDevice) {
            TDebug.out("TMidiDevice.sendImpl(): end");
        }
    }
    
    public class TReceiver implements Receiver
    {
        private boolean m_bOpen;
        
        public TReceiver() {
            TMidiDevice.this.addReceiver(this);
            this.m_bOpen = true;
        }
        
        protected boolean isOpen() {
            return this.m_bOpen;
        }
        
        public void send(final MidiMessage message, final long lTimeStamp) {
            if (TDebug.TraceMidiDevice) {
                TDebug.out("TMidiDevice.TReceiver.send(): message " + message);
            }
            if (this.m_bOpen) {
                TMidiDevice.this.receive(message, lTimeStamp);
                return;
            }
            throw new IllegalStateException("receiver is not open");
        }
        
        public void close() {
            TMidiDevice.this.removeReceiver(this);
            this.m_bOpen = false;
        }
    }
    
    public class TTransmitter implements Transmitter
    {
        private boolean m_bOpen;
        private Receiver m_receiver;
        
        public TTransmitter() {
            this.m_bOpen = true;
            TMidiDevice.this.addTransmitter(this);
        }
        
        public void setReceiver(final Receiver receiver) {
            synchronized (this) {
                this.m_receiver = receiver;
            }
        }
        
        public Receiver getReceiver() {
            return this.m_receiver;
        }
        
        public void send(final MidiMessage message, final long lTimeStamp) {
            if (this.getReceiver() != null && this.m_bOpen) {
                this.getReceiver().send(message, lTimeStamp);
            }
        }
        
        public void close() {
            TMidiDevice.this.removeTransmitter(this);
            this.m_bOpen = false;
        }
    }
    
    public static class Info extends MidiDevice.Info
    {
        public Info(final String a, final String b, final String c, final String d) {
            super(a, b, c, d);
        }
    }
}
