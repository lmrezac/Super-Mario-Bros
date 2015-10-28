// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.file;

import java.io.IOException;
import org.tritonus.share.TDebug;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFileFormat;
import java.util.Collection;

public class THeaderlessAudioFileWriter extends TAudioFileWriter
{
    protected THeaderlessAudioFileWriter(final Collection<AudioFileFormat.Type> fileTypes, final Collection<AudioFormat> audioFormats) {
        super(fileTypes, audioFormats);
        if (TDebug.TraceAudioFileWriter) {
            TDebug.out("THeaderlessAudioFileWriter.<init>(): begin");
        }
        if (TDebug.TraceAudioFileWriter) {
            TDebug.out("THeaderlessAudioFileWriter.<init>(): end");
        }
    }
    
    protected AudioOutputStream getAudioOutputStream(final AudioFormat audioFormat, final long lLengthInBytes, final AudioFileFormat.Type fileType, final TDataOutputStream dataOutputStream) throws IOException {
        if (TDebug.TraceAudioFileWriter) {
            TDebug.out("THeaderlessAudioFileWriter.getAudioOutputStream(): begin");
        }
        final AudioOutputStream aos = new HeaderlessAudioOutputStream(audioFormat, lLengthInBytes, dataOutputStream);
        if (TDebug.TraceAudioFileWriter) {
            TDebug.out("THeaderlessAudioFileWriter.getAudioOutputStream(): end");
        }
        return aos;
    }
}
