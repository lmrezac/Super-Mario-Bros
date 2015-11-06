// 
// Decompiled by Procyon v0.5.29
// 

package org.tritonus.share;

import java.security.AccessControlException;

public class TSettings
{
    public static boolean SHOW_ACCESS_CONTROL_EXCEPTIONS;
    @SuppressWarnings("unused")
	private static final String PROPERTY_PREFIX = "tritonus.";
    public static boolean AlsaUsePlughw;
    
    private static boolean getBooleanProperty(final String strName) {
        final String strPropertyName = "tritonus." + strName;
        String strValue = "false";
        try {
            strValue = System.getProperty(strPropertyName, "false");
        }
        catch (AccessControlException e) {
            if (TSettings.SHOW_ACCESS_CONTROL_EXCEPTIONS) {
                TDebug.out(e);
            }
        }
        final boolean bValue = strValue.toLowerCase().equals("true");
        return bValue;
    }
    
    static {
        TSettings.SHOW_ACCESS_CONTROL_EXCEPTIONS = false;
        TSettings.AlsaUsePlughw = getBooleanProperty("AlsaUsePlughw");
    }
}
