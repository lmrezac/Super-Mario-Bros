// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share.sampled.convert;

import java.util.Iterator;
import org.tritonus.share.ArraySet;
import org.tritonus.share.TDebug;
import javax.sound.sampled.AudioFormat;
import java.util.Collection;

public abstract class TEncodingFormatConversionProvider extends TSimpleFormatConversionProvider
{
    protected TEncodingFormatConversionProvider(final Collection<AudioFormat> sourceFormats, final Collection<AudioFormat> targetFormats) {
        super(sourceFormats, targetFormats);
    }
    
    public AudioFormat[] getTargetFormats(final AudioFormat.Encoding targetEncoding, final AudioFormat sourceFormat) {
        if (TDebug.TraceAudioConverter) {
            TDebug.out(">TEncodingFormatConversionProvider.getTargetFormats(AudioFormat.Encoding, AudioFormat):");
            TDebug.out("checking if conversion possible");
            TDebug.out("from: " + sourceFormat);
            TDebug.out("to: " + targetEncoding);
        }
        if (this.isConversionSupported(targetEncoding, sourceFormat)) {
            final ArraySet<AudioFormat> result = new ArraySet<AudioFormat>();
            for (AudioFormat targetFormat : this.getCollectionTargetFormats()) {
                targetFormat = this.replaceNotSpecified(sourceFormat, targetFormat);
                result.add(targetFormat);
            }
            if (TDebug.TraceAudioConverter) {
                TDebug.out("< returning " + result.size() + " elements.");
            }
            return result.toArray(TEncodingFormatConversionProvider.EMPTY_FORMAT_ARRAY);
        }
        if (TDebug.TraceAudioConverter) {
            TDebug.out("< returning empty array.");
        }
        return TEncodingFormatConversionProvider.EMPTY_FORMAT_ARRAY;
    }
}
