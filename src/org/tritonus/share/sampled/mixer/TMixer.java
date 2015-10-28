// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.mixer;

import org.tritonus.share.sampled.AudioFormats;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import java.util.Iterator;
import javax.sound.sampled.Port;
import org.tritonus.share.ArraySet;
import org.tritonus.share.TDebug;
import java.util.ArrayList;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.SourceDataLine;
import java.util.Set;
import javax.sound.sampled.AudioFormat;
import java.util.Collection;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;

public abstract class TMixer extends TLine implements Mixer
{
    private static Line.Info[] EMPTY_LINE_INFO_ARRAY;
    private static Line[] EMPTY_LINE_ARRAY;
    private Mixer.Info m_mixerInfo;
    private Collection<AudioFormat> m_supportedSourceFormats;
    private Collection<AudioFormat> m_supportedTargetFormats;
    private Collection<Line.Info> m_supportedSourceLineInfos;
    private Collection<Line.Info> m_supportedTargetLineInfos;
    private Set<SourceDataLine> m_openSourceDataLines;
    private Set<TargetDataLine> m_openTargetDataLines;
    
    protected TMixer(final Mixer.Info mixerInfo, final Line.Info lineInfo) {
        this(mixerInfo, lineInfo, new ArrayList<AudioFormat>(), new ArrayList<AudioFormat>(), new ArrayList<Line.Info>(), new ArrayList<Line.Info>());
    }
    
    protected TMixer(final Mixer.Info mixerInfo, final Line.Info lineInfo, final Collection<AudioFormat> supportedSourceFormats, final Collection<AudioFormat> supportedTargetFormats, final Collection<Line.Info> supportedSourceLineInfos, final Collection<Line.Info> supportedTargetLineInfos) {
        super(null, lineInfo);
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.<init>(): begin");
        }
        this.m_mixerInfo = mixerInfo;
        this.setSupportInformation(supportedSourceFormats, supportedTargetFormats, supportedSourceLineInfos, supportedTargetLineInfos);
        this.m_openSourceDataLines = new ArraySet<SourceDataLine>();
        this.m_openTargetDataLines = new ArraySet<TargetDataLine>();
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.<init>(): end");
        }
    }
    
    protected void setSupportInformation(final Collection<AudioFormat> supportedSourceFormats, final Collection<AudioFormat> supportedTargetFormats, final Collection<Line.Info> supportedSourceLineInfos, final Collection<Line.Info> supportedTargetLineInfos) {
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.setSupportInformation(): begin");
        }
        this.m_supportedSourceFormats = supportedSourceFormats;
        this.m_supportedTargetFormats = supportedTargetFormats;
        this.m_supportedSourceLineInfos = supportedSourceLineInfos;
        this.m_supportedTargetLineInfos = supportedTargetLineInfos;
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.setSupportInformation(): end");
        }
    }
    
    public Mixer.Info getMixerInfo() {
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.getMixerInfo(): begin");
        }
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.getMixerInfo(): end");
        }
        return this.m_mixerInfo;
    }
    
    public Line.Info[] getSourceLineInfo() {
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.getSourceLineInfo(): begin");
        }
        final Line.Info[] infos = this.m_supportedSourceLineInfos.toArray(TMixer.EMPTY_LINE_INFO_ARRAY);
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.getSourceLineInfo(): end");
        }
        return infos;
    }
    
    public Line.Info[] getTargetLineInfo() {
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.getTargetLineInfo(): begin");
        }
        final Line.Info[] infos = this.m_supportedTargetLineInfos.toArray(TMixer.EMPTY_LINE_INFO_ARRAY);
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.getTargetLineInfo(): end");
        }
        return infos;
    }
    
    public Line.Info[] getSourceLineInfo(final Line.Info info) {
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.getSourceLineInfo(Line.Info): info to test: " + info);
        }
        return TMixer.EMPTY_LINE_INFO_ARRAY;
    }
    
    public Line.Info[] getTargetLineInfo(final Line.Info info) {
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.getTargetLineInfo(Line.Info): info to test: " + info);
        }
        return TMixer.EMPTY_LINE_INFO_ARRAY;
    }
    
    public boolean isLineSupported(final Line.Info info) {
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.isLineSupported(): info to test: " + info);
        }
        final Class lineClass = info.getLineClass();
        if (lineClass.equals(SourceDataLine.class)) {
            return isLineSupportedImpl(info, this.m_supportedSourceLineInfos);
        }
        if (lineClass.equals(TargetDataLine.class)) {
            return isLineSupportedImpl(info, this.m_supportedTargetLineInfos);
        }
        return lineClass.equals(Port.class) && (isLineSupportedImpl(info, this.m_supportedSourceLineInfos) || isLineSupportedImpl(info, this.m_supportedTargetLineInfos));
    }
    
    private static boolean isLineSupportedImpl(final Line.Info info, final Collection<Line.Info> supportedLineInfos) {
        for (final Line.Info info2 : supportedLineInfos) {
            if (info2.matches(info)) {
                return true;
            }
        }
        return false;
    }
    
    public Line getLine(final Line.Info info) throws LineUnavailableException {
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.getLine(): begin");
        }
        final Class lineClass = info.getLineClass();
        DataLine.Info dataLineInfo = null;
        Port.Info portInfo = null;
        AudioFormat[] aFormats = null;
        if (info instanceof DataLine.Info) {
            dataLineInfo = (DataLine.Info)info;
            aFormats = dataLineInfo.getFormats();
        }
        else if (info instanceof Port.Info) {
            portInfo = (Port.Info)info;
        }
        AudioFormat format = null;
        Line line = null;
        if (lineClass == SourceDataLine.class) {
            if (TDebug.TraceMixer) {
                TDebug.out("TMixer.getLine(): type: SourceDataLine");
            }
            if (dataLineInfo == null) {
                throw new IllegalArgumentException("need DataLine.Info for SourceDataLine");
            }
            format = this.getSupportedSourceFormat(aFormats);
            line = this.getSourceDataLine(format, dataLineInfo.getMaxBufferSize());
        }
        else if (lineClass == Clip.class) {
            if (TDebug.TraceMixer) {
                TDebug.out("TMixer.getLine(): type: Clip");
            }
            if (dataLineInfo == null) {
                throw new IllegalArgumentException("need DataLine.Info for Clip");
            }
            format = this.getSupportedSourceFormat(aFormats);
            line = this.getClip(format);
        }
        else if (lineClass == TargetDataLine.class) {
            if (TDebug.TraceMixer) {
                TDebug.out("TMixer.getLine(): type: TargetDataLine");
            }
            if (dataLineInfo == null) {
                throw new IllegalArgumentException("need DataLine.Info for TargetDataLine");
            }
            format = this.getSupportedTargetFormat(aFormats);
            line = this.getTargetDataLine(format, dataLineInfo.getMaxBufferSize());
        }
        else {
            if (lineClass != Port.class) {
                if (TDebug.TraceMixer) {
                    TDebug.out("TMixer.getLine(): unknown line type, will throw exception");
                }
                throw new LineUnavailableException("unknown line class: " + lineClass);
            }
            if (TDebug.TraceMixer) {
                TDebug.out("TMixer.getLine(): type: TargetDataLine");
            }
            if (portInfo == null) {
                throw new IllegalArgumentException("need Port.Info for Port");
            }
            line = this.getPort(portInfo);
        }
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.getLine(): end");
        }
        return line;
    }
    
    protected SourceDataLine getSourceDataLine(final AudioFormat format, final int nBufferSize) throws LineUnavailableException {
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.getSourceDataLine(): begin");
        }
        throw new IllegalArgumentException("this mixer does not support SourceDataLines");
    }
    
    protected Clip getClip(final AudioFormat format) throws LineUnavailableException {
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.getClip(): begin");
        }
        throw new IllegalArgumentException("this mixer does not support Clips");
    }
    
    protected TargetDataLine getTargetDataLine(final AudioFormat format, final int nBufferSize) throws LineUnavailableException {
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.getTargetDataLine(): begin");
        }
        throw new IllegalArgumentException("this mixer does not support TargetDataLines");
    }
    
    protected Port getPort(final Port.Info info) throws LineUnavailableException {
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.getTargetDataLine(): begin");
        }
        throw new IllegalArgumentException("this mixer does not support Ports");
    }
    
    private AudioFormat getSupportedSourceFormat(final AudioFormat[] aFormats) {
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.getSupportedSourceFormat(): begin");
        }
        AudioFormat format = null;
        for (int i = 0; i < aFormats.length; ++i) {
            if (TDebug.TraceMixer) {
                TDebug.out("TMixer.getSupportedSourceFormat(): checking " + aFormats[i] + "...");
            }
            if (this.isSourceFormatSupported(aFormats[i])) {
                if (TDebug.TraceMixer) {
                    TDebug.out("TMixer.getSupportedSourceFormat(): ...supported");
                }
                format = aFormats[i];
                break;
            }
            if (TDebug.TraceMixer) {
                TDebug.out("TMixer.getSupportedSourceFormat(): ...no luck");
            }
        }
        if (format == null) {
            throw new IllegalArgumentException("no line matchine one of the passed formats");
        }
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.getSupportedSourceFormat(): end");
        }
        return format;
    }
    
    private AudioFormat getSupportedTargetFormat(final AudioFormat[] aFormats) {
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.getSupportedTargetFormat(): begin");
        }
        AudioFormat format = null;
        for (int i = 0; i < aFormats.length; ++i) {
            if (TDebug.TraceMixer) {
                TDebug.out("TMixer.getSupportedTargetFormat(): checking " + aFormats[i] + " ...");
            }
            if (this.isTargetFormatSupported(aFormats[i])) {
                if (TDebug.TraceMixer) {
                    TDebug.out("TMixer.getSupportedTargetFormat(): ...supported");
                }
                format = aFormats[i];
                break;
            }
            if (TDebug.TraceMixer) {
                TDebug.out("TMixer.getSupportedTargetFormat(): ...no luck");
            }
        }
        if (format == null) {
            throw new IllegalArgumentException("no line matchine one of the passed formats");
        }
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.getSupportedTargetFormat(): end");
        }
        return format;
    }
    
    public Line[] getSourceLines() {
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.getSourceLines(): called");
        }
        return this.m_openSourceDataLines.toArray(TMixer.EMPTY_LINE_ARRAY);
    }
    
    public Line[] getTargetLines() {
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.getTargetLines(): called");
        }
        return this.m_openTargetDataLines.toArray(TMixer.EMPTY_LINE_ARRAY);
    }
    
    public void synchronize(final Line[] aLines, final boolean bMaintainSync) {
        throw new IllegalArgumentException("synchronization not supported");
    }
    
    public void unsynchronize(final Line[] aLines) {
        throw new IllegalArgumentException("synchronization not supported");
    }
    
    public boolean isSynchronizationSupported(final Line[] aLines, final boolean bMaintainSync) {
        return false;
    }
    
    protected boolean isSourceFormatSupported(final AudioFormat format) {
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.isSourceFormatSupported(): format to test: " + format);
        }
        for (final AudioFormat supportedFormat : this.m_supportedSourceFormats) {
            if (AudioFormats.matches(supportedFormat, format)) {
                return true;
            }
        }
        return false;
    }
    
    protected boolean isTargetFormatSupported(final AudioFormat format) {
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.isTargetFormatSupported(): format to test: " + format);
        }
        for (final AudioFormat supportedFormat : this.m_supportedTargetFormats) {
            if (AudioFormats.matches(supportedFormat, format)) {
                return true;
            }
        }
        return false;
    }
    
    void registerOpenLine(final Line line) {
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.registerOpenLine(): line to register: " + line);
        }
        if (line instanceof SourceDataLine) {
            synchronized (this.m_openSourceDataLines) {
                this.m_openSourceDataLines.add((SourceDataLine)line);
            }
        }
        else if (line instanceof TargetDataLine) {
            synchronized (this.m_openSourceDataLines) {
                this.m_openTargetDataLines.add((TargetDataLine)line);
            }
        }
    }
    
    void unregisterOpenLine(final Line line) {
        if (TDebug.TraceMixer) {
            TDebug.out("TMixer.unregisterOpenLine(): line to unregister: " + line);
        }
        if (line instanceof SourceDataLine) {
            synchronized (this.m_openSourceDataLines) {
                this.m_openSourceDataLines.remove(line);
            }
        }
        else if (line instanceof TargetDataLine) {
            synchronized (this.m_openTargetDataLines) {
                this.m_openTargetDataLines.remove(line);
            }
        }
    }
    
    static {
        TMixer.EMPTY_LINE_INFO_ARRAY = new Line.Info[0];
        TMixer.EMPTY_LINE_ARRAY = new Line[0];
    }
}
