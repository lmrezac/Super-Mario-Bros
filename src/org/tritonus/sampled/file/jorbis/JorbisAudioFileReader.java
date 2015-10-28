// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.sampled.file.jorbis;

import java.io.IOException;
import org.tritonus.share.sampled.file.TAudioFileFormat;
import javax.sound.sampled.AudioFormat;
import org.tritonus.share.TDebug;
import com.jcraft.jogg.Buffer;
import javax.sound.sampled.UnsupportedAudioFileException;
import com.jcraft.jogg.Packet;
import com.jcraft.jogg.Page;
import com.jcraft.jogg.StreamState;
import com.jcraft.jogg.SyncState;
import javax.sound.sampled.AudioFileFormat;
import java.io.InputStream;
import org.tritonus.share.sampled.file.TAudioFileReader;

public class JorbisAudioFileReader extends TAudioFileReader
{
    private static final int INITAL_READ_LENGTH = 4096;
    private static final int MARK_LIMIT = 4097;
    
    public JorbisAudioFileReader() {
        super(4097, true);
    }
    
    protected AudioFileFormat getAudioFileFormat(final InputStream inputStream, final long lFileSizeInBytes) throws UnsupportedAudioFileException, IOException {
        final SyncState oggSyncState = new SyncState();
        final StreamState oggStreamState = new StreamState();
        final Page oggPage = new Page();
        final Packet oggPacket = new Packet();
        int bytes = 0;
        oggSyncState.init();
        final int index = oggSyncState.buffer(4096);
        bytes = inputStream.read(oggSyncState.data, index, 4096);
        oggSyncState.wrote(bytes);
        if (oggSyncState.pageout(oggPage) != 1) {
            if (bytes < 4096) {
                throw new UnsupportedAudioFileException("not a Vorbis stream: ended prematurely");
            }
            throw new UnsupportedAudioFileException("not a Vorbis stream: not in Ogg bitstream format");
        }
        else {
            oggStreamState.init(oggPage.serialno());
            if (oggStreamState.pagein(oggPage) < 0) {
                throw new UnsupportedAudioFileException("not a Vorbis stream: can't read first page of Ogg bitstream data");
            }
            if (oggStreamState.packetout(oggPacket) != 1) {
                throw new UnsupportedAudioFileException("not a Vorbis stream: can't read initial header packet");
            }
            final Buffer oggPacketBuffer = new Buffer();
            oggPacketBuffer.readinit(oggPacket.packet_base, oggPacket.packet, oggPacket.bytes);
            final int nPacketType = oggPacketBuffer.read(8);
            final byte[] buf = new byte[6];
            oggPacketBuffer.read(buf, 6);
            if (buf[0] != 118 || buf[1] != 111 || buf[2] != 114 || buf[3] != 98 || buf[4] != 105 || buf[5] != 115) {
                throw new UnsupportedAudioFileException("not a Vorbis stream: not a vorbis header packet");
            }
            if (nPacketType != 1) {
                throw new UnsupportedAudioFileException("not a Vorbis stream: first packet is not the identification header");
            }
            if (oggPacket.b_o_s == 0) {
                throw new UnsupportedAudioFileException("not a Vorbis stream: initial packet not marked as beginning of stream");
            }
            final int nVersion = oggPacketBuffer.read(32);
            if (nVersion != 0) {
                throw new UnsupportedAudioFileException("not a Vorbis stream: wrong vorbis version");
            }
            final int nChannels = oggPacketBuffer.read(8);
            final float fSampleRate = oggPacketBuffer.read(32);
            final int bitrate_upper = oggPacketBuffer.read(32);
            final int bitrate_nominal = oggPacketBuffer.read(32);
            final int bitrate_lower = oggPacketBuffer.read(32);
            final int[] blocksizes = { 1 << oggPacketBuffer.read(4), 1 << oggPacketBuffer.read(4) };
            if (fSampleRate < 1.0f || nChannels < 1 || blocksizes[0] < 8 || blocksizes[1] < blocksizes[0] || oggPacketBuffer.read(1) != 1) {
                throw new UnsupportedAudioFileException("not a Vorbis stream: illegal values in initial header");
            }
            if (TDebug.TraceAudioFileReader) {
                TDebug.out("JorbisAudioFileReader.getAudioFileFormat(): channels: " + nChannels);
            }
            if (TDebug.TraceAudioFileReader) {
                TDebug.out("JorbisAudioFileReader.getAudioFileFormat(): rate: " + fSampleRate);
            }
            int nByteSize = -1;
            if (lFileSizeInBytes != -1L && lFileSizeInBytes <= 2147483647L) {
                nByteSize = (int)lFileSizeInBytes;
            }
            final int nFrameSize = -1;
            final AudioFormat format = new AudioFormat(new AudioFormat.Encoding("VORBIS"), fSampleRate, -1, nChannels, -1, -1.0f, true);
            if (TDebug.TraceAudioFileReader) {
                TDebug.out("JorbisAudioFileReader.getAudioFileFormat(): AudioFormat: " + format);
            }
            final AudioFileFormat.Type type = new AudioFileFormat.Type("Ogg", "ogg");
            final AudioFileFormat audioFileFormat = new TAudioFileFormat(type, format, nFrameSize, nByteSize);
            if (TDebug.TraceAudioFileReader) {
                TDebug.out("JorbisAudioFileReader.getAudioFileFormat(): AudioFileFormat: " + audioFileFormat);
            }
            return audioFileFormat;
        }
    }
}
