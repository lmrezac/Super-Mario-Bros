// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share;

import java.security.AccessControlException;
import java.util.StringTokenizer;
import java.io.PrintStream;

public class TDebug
{
    public static boolean SHOW_ACCESS_CONTROL_EXCEPTIONS;
    private static final String PROPERTY_PREFIX = "tritonus.";
    public static PrintStream m_printStream;
    private static String indent;
    public static boolean TraceAllExceptions;
    public static boolean TraceAllWarnings;
    public static boolean TraceInit;
    public static boolean TraceCircularBuffer;
    public static boolean TraceService;
    public static boolean TraceAudioSystem;
    public static boolean TraceAudioConfig;
    public static boolean TraceAudioInputStream;
    public static boolean TraceMixerProvider;
    public static boolean TraceControl;
    public static boolean TraceLine;
    public static boolean TraceDataLine;
    public static boolean TraceMixer;
    public static boolean TraceSourceDataLine;
    public static boolean TraceTargetDataLine;
    public static boolean TraceClip;
    public static boolean TraceAudioFileReader;
    public static boolean TraceAudioFileWriter;
    public static boolean TraceAudioConverter;
    public static boolean TraceAudioOutputStream;
    public static boolean TraceEsdNative;
    public static boolean TraceEsdStreamNative;
    public static boolean TraceEsdRecordingStreamNative;
    public static boolean TraceAlsaNative;
    public static boolean TraceAlsaMixerNative;
    public static boolean TraceAlsaPcmNative;
    public static boolean TraceMixingAudioInputStream;
    public static boolean TraceOggNative;
    public static boolean TraceVorbisNative;
    public static boolean TraceMidiSystem;
    public static boolean TraceMidiConfig;
    public static boolean TraceMidiDeviceProvider;
    public static boolean TraceSequencer;
    public static boolean TraceMidiDevice;
    public static boolean TraceAlsaSeq;
    public static boolean TraceAlsaSeqDetails;
    public static boolean TraceAlsaSeqNative;
    public static boolean TracePortScan;
    public static boolean TraceAlsaMidiIn;
    public static boolean TraceAlsaMidiOut;
    public static boolean TraceAlsaMidiChannel;
    public static boolean TraceAlsaCtlNative;
    public static boolean TraceCdda;
    public static boolean TraceCddaNative;
    
    public static void out(final String strMessage) {
        if (strMessage.length() > 0 && strMessage.charAt(0) == '<') {
            if (TDebug.indent.length() > 2) {
                TDebug.indent = TDebug.indent.substring(2);
            }
            else {
                TDebug.indent = "";
            }
        }
        String newMsg = null;
        if (TDebug.indent != "" && strMessage.indexOf("\n") >= 0) {
            newMsg = "";
            final StringTokenizer tokenizer = new StringTokenizer(strMessage, "\n");
            while (tokenizer.hasMoreTokens()) {
                newMsg = newMsg + TDebug.indent + tokenizer.nextToken() + "\n";
            }
        }
        else {
            newMsg = TDebug.indent + strMessage;
        }
        TDebug.m_printStream.println(newMsg);
        if (strMessage.length() > 0 && strMessage.charAt(0) == '>') {
            TDebug.indent += "  ";
        }
    }
    
    public static void out(final Throwable throwable) {
        throwable.printStackTrace(TDebug.m_printStream);
    }
    
    public static void assertion(final boolean bAssertion) {
        if (!bAssertion) {
            throw new AssertException();
        }
    }
    
    private static boolean getBooleanProperty(final String strName) {
        final String strPropertyName = "tritonus." + strName;
        String strValue = "false";
        try {
            strValue = System.getProperty(strPropertyName, "false");
        }
        catch (AccessControlException e) {
            if (TDebug.SHOW_ACCESS_CONTROL_EXCEPTIONS) {
                out(e);
            }
        }
        final boolean bValue = strValue.toLowerCase().equals("true");
        return bValue;
    }
    
    static {
        TDebug.SHOW_ACCESS_CONTROL_EXCEPTIONS = false;
        TDebug.m_printStream = System.out;
        TDebug.indent = "";
        TDebug.TraceAllExceptions = getBooleanProperty("TraceAllExceptions");
        TDebug.TraceAllWarnings = getBooleanProperty("TraceAllWarnings");
        TDebug.TraceInit = getBooleanProperty("TraceInit");
        TDebug.TraceCircularBuffer = getBooleanProperty("TraceCircularBuffer");
        TDebug.TraceService = getBooleanProperty("TraceService");
        TDebug.TraceAudioSystem = getBooleanProperty("TraceAudioSystem");
        TDebug.TraceAudioConfig = getBooleanProperty("TraceAudioConfig");
        TDebug.TraceAudioInputStream = getBooleanProperty("TraceAudioInputStream");
        TDebug.TraceMixerProvider = getBooleanProperty("TraceMixerProvider");
        TDebug.TraceControl = getBooleanProperty("TraceControl");
        TDebug.TraceLine = getBooleanProperty("TraceLine");
        TDebug.TraceDataLine = getBooleanProperty("TraceDataLine");
        TDebug.TraceMixer = getBooleanProperty("TraceMixer");
        TDebug.TraceSourceDataLine = getBooleanProperty("TraceSourceDataLine");
        TDebug.TraceTargetDataLine = getBooleanProperty("TraceTargetDataLine");
        TDebug.TraceClip = getBooleanProperty("TraceClip");
        TDebug.TraceAudioFileReader = getBooleanProperty("TraceAudioFileReader");
        TDebug.TraceAudioFileWriter = getBooleanProperty("TraceAudioFileWriter");
        TDebug.TraceAudioConverter = getBooleanProperty("TraceAudioConverter");
        TDebug.TraceAudioOutputStream = getBooleanProperty("TraceAudioOutputStream");
        TDebug.TraceEsdNative = getBooleanProperty("TraceEsdNative");
        TDebug.TraceEsdStreamNative = getBooleanProperty("TraceEsdStreamNative");
        TDebug.TraceEsdRecordingStreamNative = getBooleanProperty("TraceEsdRecordingStreamNative");
        TDebug.TraceAlsaNative = getBooleanProperty("TraceAlsaNative");
        TDebug.TraceAlsaMixerNative = getBooleanProperty("TraceAlsaMixerNative");
        TDebug.TraceAlsaPcmNative = getBooleanProperty("TraceAlsaPcmNative");
        TDebug.TraceMixingAudioInputStream = getBooleanProperty("TraceMixingAudioInputStream");
        TDebug.TraceOggNative = getBooleanProperty("TraceOggNative");
        TDebug.TraceVorbisNative = getBooleanProperty("TraceVorbisNative");
        TDebug.TraceMidiSystem = getBooleanProperty("TraceMidiSystem");
        TDebug.TraceMidiConfig = getBooleanProperty("TraceMidiConfig");
        TDebug.TraceMidiDeviceProvider = getBooleanProperty("TraceMidiDeviceProvider");
        TDebug.TraceSequencer = getBooleanProperty("TraceSequencer");
        TDebug.TraceMidiDevice = getBooleanProperty("TraceMidiDevice");
        TDebug.TraceAlsaSeq = getBooleanProperty("TraceAlsaSeq");
        TDebug.TraceAlsaSeqDetails = getBooleanProperty("TraceAlsaSeqDetails");
        TDebug.TraceAlsaSeqNative = getBooleanProperty("TraceAlsaSeqNative");
        TDebug.TracePortScan = getBooleanProperty("TracePortScan");
        TDebug.TraceAlsaMidiIn = getBooleanProperty("TraceAlsaMidiIn");
        TDebug.TraceAlsaMidiOut = getBooleanProperty("TraceAlsaMidiOut");
        TDebug.TraceAlsaMidiChannel = getBooleanProperty("TraceAlsaMidiChannel");
        TDebug.TraceAlsaCtlNative = getBooleanProperty("TraceAlsaCtlNative");
        TDebug.TraceCdda = getBooleanProperty("TraceCdda");
        TDebug.TraceCddaNative = getBooleanProperty("TraceCddaNative");
    }
    
    public static class AssertException extends RuntimeException
    {
        public AssertException() {
        }
        
        public AssertException(final String sMessage) {
            super(sMessage);
        }
    }
}
