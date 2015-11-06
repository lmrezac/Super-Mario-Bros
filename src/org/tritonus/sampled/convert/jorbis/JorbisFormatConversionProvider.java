// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.sampled.convert.jorbis;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import org.tritonus.share.TDebug;
import org.tritonus.share.sampled.AudioFormats;
import org.tritonus.share.sampled.convert.TAsynchronousFilteredAudioInputStream;
import org.tritonus.share.sampled.convert.TEncodingFormatConversionProvider;

import com.jcraft.jogg.Packet;
import com.jcraft.jogg.Page;
import com.jcraft.jogg.StreamState;
import com.jcraft.jogg.SyncState;
import com.jcraft.jorbis.Block;
import com.jcraft.jorbis.Comment;
import com.jcraft.jorbis.DspState;
import com.jcraft.jorbis.Info;
@SuppressWarnings("unused")
public class JorbisFormatConversionProvider extends TEncodingFormatConversionProvider
{
    private static final AudioFormat.Encoding VORBIS;
    private static final AudioFormat.Encoding PCM_SIGNED;
    private static final AudioFormat[] INPUT_FORMATS;
    private static final AudioFormat[] OUTPUT_FORMATS;
    
    public JorbisFormatConversionProvider() {
        super(Arrays.asList(JorbisFormatConversionProvider.INPUT_FORMATS), Arrays.asList(JorbisFormatConversionProvider.OUTPUT_FORMATS));
    }
    
    public AudioInputStream getAudioInputStream(AudioFormat targetFormat, final AudioInputStream audioInputStream) {
        AudioInputStream convertedAudioInputStream = null;
        if (TDebug.TraceAudioConverter) {
            TDebug.out(">JorbisFormatConversionProvider.getAudioInputStream(): begin");
            TDebug.out("checking if conversion supported");
            TDebug.out("from: " + audioInputStream.getFormat());
            TDebug.out("to: " + targetFormat);
        }
        targetFormat = this.getDefaultTargetFormat(targetFormat, audioInputStream.getFormat());
        if (this.isConversionSupported(targetFormat, audioInputStream.getFormat())) {
            if (TDebug.TraceAudioConverter) {
                TDebug.out("conversion supported; trying to create DecodedJorbisAudioInputStream");
            }
            convertedAudioInputStream = new DecodedJorbisAudioInputStream(targetFormat, audioInputStream);
            if (TDebug.TraceAudioConverter) {
                TDebug.out("<JorbisFormatConversionProvider.getAudioInputStream(): end");
            }
            return convertedAudioInputStream;
        }
        if (TDebug.TraceAudioConverter) {
            TDebug.out("conversion not supported; throwing IllegalArgumentException");
            TDebug.out("<");
        }
        throw new IllegalArgumentException("conversion not supported");
    }
    
    protected AudioFormat getDefaultTargetFormat(final AudioFormat targetFormat, final AudioFormat sourceFormat) {
        if (TDebug.TraceAudioConverter) {
            TDebug.out("JorbisFormatConversionProvider.getDefaultTargetFormat(): target format: " + targetFormat);
        }
        if (TDebug.TraceAudioConverter) {
            TDebug.out("JorbisFormatConversionProvider.getDefaultTargetFormat(): source format: " + sourceFormat);
        }
        AudioFormat newTargetFormat = null;
        for (final AudioFormat format : this.getCollectionTargetFormats()) {
            if (AudioFormats.matches(targetFormat, format)) {
                newTargetFormat = format;
            }
        }
        if (newTargetFormat == null) {
            throw new IllegalArgumentException("conversion not supported");
        }
        if (TDebug.TraceAudioConverter) {
            TDebug.out("JorbisFormatConversionProvider.getDefaultTargetFormat(): new target format: " + newTargetFormat);
        }
        newTargetFormat = new AudioFormat(targetFormat.getEncoding(), sourceFormat.getSampleRate(), newTargetFormat.getSampleSizeInBits(), newTargetFormat.getChannels(), newTargetFormat.getFrameSize(), sourceFormat.getSampleRate(), newTargetFormat.isBigEndian());
        if (TDebug.TraceAudioConverter) {
            TDebug.out("JorbisFormatConversionProvider.getDefaultTargetFormat(): really new target format: " + newTargetFormat);
        }
        return newTargetFormat;
    }
    
    static {
        VORBIS = new AudioFormat.Encoding("VORBIS");
        PCM_SIGNED = new AudioFormat.Encoding("PCM_SIGNED");
        INPUT_FORMATS = new AudioFormat[] { new AudioFormat(JorbisFormatConversionProvider.VORBIS, -1.0f, -1, 1, -1, -1.0f, false), new AudioFormat(JorbisFormatConversionProvider.VORBIS, -1.0f, -1, 1, -1, -1.0f, true), new AudioFormat(JorbisFormatConversionProvider.VORBIS, -1.0f, -1, 2, -1, -1.0f, false), new AudioFormat(JorbisFormatConversionProvider.VORBIS, -1.0f, -1, 2, -1, -1.0f, true) };
        OUTPUT_FORMATS = new AudioFormat[] { new AudioFormat(JorbisFormatConversionProvider.PCM_SIGNED, -1.0f, 16, 1, 2, -1.0f, false), new AudioFormat(JorbisFormatConversionProvider.PCM_SIGNED, -1.0f, 16, 1, 2, -1.0f, true), new AudioFormat(JorbisFormatConversionProvider.PCM_SIGNED, -1.0f, 16, 2, 4, -1.0f, false), new AudioFormat(JorbisFormatConversionProvider.PCM_SIGNED, -1.0f, 16, 2, 4, -1.0f, true) };
    }
    
    public static class DecodedJorbisAudioInputStream extends TAsynchronousFilteredAudioInputStream
    {
        private static final int BUFFER_MULTIPLE = 4;
        private static final int BUFFER_SIZE = 2048;
        private static final int CONVSIZE = 4096;
        private InputStream m_oggBitStream;
        private SyncState m_oggSyncState;
        private StreamState m_oggStreamState;
        private Page m_oggPage;
        private Packet m_oggPacket;
        private Info m_vorbisInfo;
        private Comment m_vorbisComment;
        private DspState m_vorbisDspState;
        private Block m_vorbisBlock;
        private List<String> m_songComments;
        private int convsize;
        private byte[] convbuffer;
        private float[][][] _pcmf;
        private int[] _index;
        private boolean m_bHeadersExpected;
        
        public DecodedJorbisAudioInputStream(final AudioFormat outputFormat, final AudioInputStream bitStream) {
            super(outputFormat, -1L);
            this.m_oggBitStream = null;
            this.m_oggSyncState = null;
            this.m_oggStreamState = null;
            this.m_oggPage = null;
            this.m_oggPacket = null;
            this.m_vorbisInfo = null;
            this.m_vorbisComment = null;
            this.m_vorbisDspState = null;
            this.m_vorbisBlock = null;
            this.m_songComments = new ArrayList<String>();
            this.convsize = -1;
            this.convbuffer = new byte[4096];
            this._pcmf = null;
            this._index = null;
            if (TDebug.TraceAudioConverter) {
                TDebug.out("DecodedJorbisAudioInputStream.<init>(): begin");
            }
            this.m_oggBitStream = bitStream;
            this.m_bHeadersExpected = true;
            this.init_jorbis();
            if (TDebug.TraceAudioConverter) {
                TDebug.out("DecodedJorbisAudioInputStream.<init>(): end");
            }
        }
        
        private void init_jorbis() {
            this.m_oggSyncState = new SyncState();
            this.m_oggStreamState = new StreamState();
            this.m_oggPage = new Page();
            this.m_oggPacket = new Packet();
            this.m_vorbisInfo = new Info();
            this.m_vorbisComment = new Comment();
            this.m_vorbisDspState = new DspState();
            this.m_vorbisBlock = new Block(this.m_vorbisDspState);
            this.m_oggSyncState.init();
        }
        
        public void execute() {
            if (TDebug.TraceAudioConverter) {
                TDebug.out(">DecodedJorbisAudioInputStream.execute(): begin");
            }
            if (this.m_bHeadersExpected) {
                if (TDebug.TraceAudioConverter) {
                    TDebug.out("reading headers...");
                }
                try {
                    this.readHeaders();
                }
                catch (IOException e) {
                    if (TDebug.TraceAllExceptions) {
                        TDebug.out(e);
                    }
                    this.closePhysicalStream();
                    if (TDebug.TraceAudioConverter) {
                        TDebug.out("<DecodedJorbisAudioInputStream.execute(): end");
                    }
                    return;
                }
                this.m_bHeadersExpected = false;
                this.setupVorbisStructures();
            }
            if (TDebug.TraceAudioConverter) {
                TDebug.out("decoding...");
            }
            while (this.writeMore()) {
                try {
                    this.readOggPacket();
                }
                catch (IOException e) {
                    if (TDebug.TraceAllExceptions) {
                        TDebug.out(e);
                    }
                    this.closePhysicalStream();
                    if (TDebug.TraceAudioConverter) {
                        TDebug.out("<DecodedJorbisAudioInputStream.execute(): end");
                    }
                    return;
                }
                this.decodeDataPacket();
            }
            if (this.m_oggPacket.e_o_s != 0) {
                if (TDebug.TraceAudioConverter) {
                    TDebug.out("end of vorbis stream reached");
                }
                this.shutDownLogicalStream();
            }
            if (TDebug.TraceAudioConverter) {
                TDebug.out("<DecodedJorbisAudioInputStream.execute(): end");
            }
        }
        
        private void shutDownLogicalStream() {
            this.m_oggStreamState.clear();
            this.m_vorbisBlock.clear();
            this.m_vorbisDspState.clear();
            this.m_vorbisInfo.clear();
            this.m_bHeadersExpected = true;
        }
        
        private void closePhysicalStream() {
            if (TDebug.TraceAudioConverter) {
                TDebug.out("DecodedJorbisAudioInputStream.closePhysicalStream(): begin");
            }
            this.m_oggSyncState.clear();
            try {
                if (this.m_oggBitStream != null) {
                    this.m_oggBitStream.close();
                }
                this.getCircularBuffer().close();
            }
            catch (Exception e) {
                if (TDebug.TraceAllExceptions) {
                    TDebug.out(e);
                }
            }
            if (TDebug.TraceAudioConverter) {
                TDebug.out("DecodedJorbisAudioInputStream.closePhysicalStream(): end");
            }
        }
        
        private void readHeaders() throws IOException {
            this.readIdentificationHeader();
            this.readCommentAndCodebookHeaders();
            this.processComments();
        }
        
        private void readIdentificationHeader() throws IOException {
            this.readOggPage();
            this.m_oggStreamState.init(this.m_oggPage.serialno());
            this.m_vorbisInfo.init();
            this.m_vorbisComment.init();
            if (this.m_oggStreamState.pagein(this.m_oggPage) < 0) {
                throw new IOException("can't read first page of Ogg bitstream data, perhaps stream version mismatch");
            }
            if (this.m_oggStreamState.packetout(this.m_oggPacket) != 1) {
                throw new IOException("can't read initial header packet");
            }
            if (this.m_vorbisInfo.synthesis_headerin(this.m_vorbisComment, this.m_oggPacket) < 0) {
                throw new IOException("packet is not a vorbis header");
            }
        }
        
        private void readCommentAndCodebookHeaders() throws IOException {
            for (int i = 0; i < 2; ++i) {
                this.readOggPacket();
                if (this.m_vorbisInfo.synthesis_headerin(this.m_vorbisComment, this.m_oggPacket) < 0) {
                    throw new IOException("packet is not a vorbis header");
                }
            }
        }
        
        private void processComments() {
            final byte[][] ptr = this.m_vorbisComment.user_comments;
            String currComment = "";
            this.m_songComments.clear();
            for (int j = 0; j < ptr.length && ptr[j] != null; ++j) {
                currComment = new String(ptr[j], 0, ptr[j].length - 1).trim();
                this.m_songComments.add(currComment);
                if (currComment.toUpperCase().startsWith("ARTIST")) {
                    final String artistLabelValue = currComment.substring(7);
                }
                else if (currComment.toUpperCase().startsWith("TITLE")) {
                    final String titleLabelValue = currComment.substring(6);
                    final String miniDragLabel = currComment.substring(6);
                }
                if (TDebug.TraceAudioConverter) {
                    TDebug.out("Comment: " + currComment);
                }
            }
            currComment = "Bitstream: " + this.m_vorbisInfo.channels + " channel," + this.m_vorbisInfo.rate + "Hz";
            this.m_songComments.add(currComment);
            if (TDebug.TraceAudioConverter) {
                TDebug.out(currComment);
            }
            if (TDebug.TraceAudioConverter) {
                currComment = "Encoded by: " + new String(this.m_vorbisComment.vendor, 0, this.m_vorbisComment.vendor.length - 1);
            }
            this.m_songComments.add(currComment);
            if (TDebug.TraceAudioConverter) {
                TDebug.out(currComment);
            }
        }
        
        private void setupVorbisStructures() {
            this.convsize = 2048 / this.m_vorbisInfo.channels;
            this.m_vorbisDspState.synthesis_init(this.m_vorbisInfo);
            this.m_vorbisBlock.init(this.m_vorbisDspState);
            this._pcmf = new float[1][][];
            this._index = new int[this.m_vorbisInfo.channels];
        }
        
        private void decodeDataPacket() {
            if (this.m_vorbisBlock.synthesis(this.m_oggPacket) == 0) {
                this.m_vorbisDspState.synthesis_blockin(this.m_vorbisBlock);
            }
            int samples;
            while ((samples = this.m_vorbisDspState.synthesis_pcmout(this._pcmf, this._index)) > 0) {
                final float[][] pcmf = this._pcmf[0];
                final int bout = (samples < this.convsize) ? samples : this.convsize;
                for (int nChannel = 0; nChannel < this.m_vorbisInfo.channels; ++nChannel) {
                    int pointer = nChannel * this.getSampleSizeInBytes();
                    final int mono = this._index[nChannel];
                    for (int j = 0; j < bout; ++j) {
                        final float fVal = pcmf[nChannel][mono + j];
                        this.clipAndWriteSample(fVal, pointer);
                        pointer += this.getFrameSize();
                    }
                }
                this.m_vorbisDspState.synthesis_read(bout);
                this.getCircularBuffer().write(this.convbuffer, 0, this.getFrameSize() * bout);
            }
        }
        
        private void clipAndWriteSample(float fSample, int nPointer) {
            if (fSample > 1.0f) {
                fSample = 1.0f;
            }
            if (fSample < -1.0f) {
                fSample = -1.0f;
            }
            switch (this.getFormat().getSampleSizeInBits()) {
                case 16: {
                    final int nSample = (int)(fSample * 32767.0f);
                    if (this.isBigEndian()) {
                        this.convbuffer[nPointer++] = (byte)(nSample >> 8);
                        this.convbuffer[nPointer] = (byte)(nSample & 0xFF);
                        break;
                    }
                    this.convbuffer[nPointer++] = (byte)(nSample & 0xFF);
                    this.convbuffer[nPointer] = (byte)(nSample >> 8);
                    break;
                }
                case 24: {
                    final int nSample = (int)(fSample * 8388607.0f);
                    if (this.isBigEndian()) {
                        this.convbuffer[nPointer++] = (byte)(nSample >> 16);
                        this.convbuffer[nPointer++] = (byte)(nSample >>> 8 & 0xFF);
                        this.convbuffer[nPointer] = (byte)(nSample & 0xFF);
                        break;
                    }
                    this.convbuffer[nPointer++] = (byte)(nSample & 0xFF);
                    this.convbuffer[nPointer++] = (byte)(nSample >>> 8 & 0xFF);
                    this.convbuffer[nPointer] = (byte)(nSample >> 16);
                    break;
                }
                case 32: {
                    final int nSample = (int)(fSample * 2.14748365E9f);
                    if (this.isBigEndian()) {
                        this.convbuffer[nPointer++] = (byte)(nSample >> 24);
                        this.convbuffer[nPointer++] = (byte)(nSample >>> 16 & 0xFF);
                        this.convbuffer[nPointer++] = (byte)(nSample >>> 8 & 0xFF);
                        this.convbuffer[nPointer] = (byte)(nSample & 0xFF);
                        break;
                    }
                    this.convbuffer[nPointer++] = (byte)(nSample & 0xFF);
                    this.convbuffer[nPointer++] = (byte)(nSample >>> 8 & 0xFF);
                    this.convbuffer[nPointer++] = (byte)(nSample >>> 16 & 0xFF);
                    this.convbuffer[nPointer] = (byte)(nSample >> 24);
                    break;
                }
            }
        }
        
        private void readOggPacket() throws IOException {
            while (true) {
                final int result = this.m_oggStreamState.packetout(this.m_oggPacket);
                if (result == 1) {
                    return;
                }
                if (result == -1) {
                    throw new IOException("can't read packet");
                }
                this.readOggPage();
                if (this.m_oggStreamState.pagein(this.m_oggPage) < 0) {
                    throw new IOException("can't read page of Ogg bitstream data");
                }
            }
        }
        
        private void readOggPage() throws IOException {
            while (true) {
                final int result = this.m_oggSyncState.pageout(this.m_oggPage);
                if (result == 1) {
                    return;
                }
                final int nIndex = this.m_oggSyncState.buffer(2048);
                final int nBytes = this.readFromStream(this.m_oggSyncState.data, nIndex, 2048);
                if (nBytes == -1) {
                    throw new EOFException();
                }
                this.m_oggSyncState.wrote(nBytes);
            }
        }
        
        private int readFromStream(final byte[] buffer, final int nStart, final int nLength) throws IOException {
            return this.m_oggBitStream.read(buffer, nStart, nLength);
        }
        
        private int getSampleSizeInBytes() {
            return this.getFormat().getFrameSize() / this.getFormat().getChannels();
        }
        
        private int getFrameSize() {
            return this.getFormat().getFrameSize();
        }
        
        private boolean isBigEndian() {
            return this.getFormat().isBigEndian();
        }
        
        public void close() throws IOException {
            super.close();
            this.m_oggBitStream.close();
        }
    }
}
