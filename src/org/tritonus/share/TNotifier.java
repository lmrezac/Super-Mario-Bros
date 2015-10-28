// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share;

import java.util.Iterator;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import java.util.Collection;
import java.util.EventObject;
import java.util.ArrayList;
import java.util.List;

public class TNotifier extends Thread
{
    public static TNotifier notifier;
    private List<NotifyEntry> m_entries;
    
    public TNotifier() {
        super("Tritonus Notifier");
        this.m_entries = new ArrayList<NotifyEntry>();
    }
    
    public void addEntry(final EventObject event, final Collection<LineListener> listeners) {
        synchronized (this.m_entries) {
            this.m_entries.add(new NotifyEntry(event, listeners));
            this.m_entries.notifyAll();
        }
    }
    
    public void run() {
        while (true) {
            NotifyEntry entry = null;
            synchronized (this.m_entries) {
                while (this.m_entries.size() == 0) {
                    try {
                        this.m_entries.wait();
                    }
                    catch (InterruptedException e) {
                        if (!TDebug.TraceAllExceptions) {
                            continue;
                        }
                        TDebug.out(e);
                    }
                }
                entry = this.m_entries.remove(0);
            }
            entry.deliver();
        }
    }
    
    static {
        TNotifier.notifier = null;
        (TNotifier.notifier = new TNotifier()).setDaemon(true);
        TNotifier.notifier.start();
    }
    
    public static class NotifyEntry
    {
        private EventObject m_event;
        private List<LineListener> m_listeners;
        
        public NotifyEntry(final EventObject event, final Collection<LineListener> listeners) {
            this.m_event = event;
            this.m_listeners = new ArrayList<LineListener>(listeners);
        }
        
        public void deliver() {
            for (final LineListener listener : this.m_listeners) {
                listener.update((LineEvent)this.m_event);
            }
        }
    }
}
