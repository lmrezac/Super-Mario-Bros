// 
// Decompiled by Procyon v0.5.29
// 

package supermario.debug;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.swing.text.BadLocationException;
import javax.swing.text.AttributeSet;
import javax.swing.event.DocumentEvent;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import javax.swing.event.DocumentListener;
import javax.swing.JTextField;

public class TextBox extends JTextField implements DocumentListener
{
    private int type;
    private double[] stored;
    private int textColumns;
    
    public TextBox(final int charLimit, final boolean allowsPeriod, final int type, final double[] stored) {
        this.type = type;
        this.stored = stored;
        this.textColumns = charLimit + 1;
        this.setHorizontalAlignment(0);
        this.setColumns(this.textColumns);
        final PlainDocument document = new PlainDocument();
        final NESTextDocumentFilter filter = new NESTextDocumentFilter(charLimit, allowsPeriod);
        final AbstractDocument absDoc = document;
        absDoc.setDocumentFilter(filter);
        this.setDocument(document);
    }
    
    public double getDouble() {
        final String value = this.getText();
        if (value.isEmpty() || value.equals(".")) {
            return 0.0;
        }
        return Double.parseDouble(value);
    }
    
    public int getInt() {
        final String value = this.getText();
        if (value.isEmpty()) {
            return 0;
        }
        return Integer.valueOf(value);
    }
    
    @Override
    public void insertUpdate(final DocumentEvent e) {
        if (this.stored[this.type] != this.getDouble()) {
            this.setFont(this.getFont().deriveFont(1));
        }
        else {
            this.setFont(this.getFont().deriveFont(0));
        }
    }
    
    @Override
    public void removeUpdate(final DocumentEvent e) {
        if (!this.getText().isEmpty() && this.stored[this.type] != this.getDouble()) {
            this.setFont(this.getFont().deriveFont(1));
        }
        else {
            this.setFont(this.getFont().deriveFont(0));
        }
    }
    
    @Override
    public void changedUpdate(final DocumentEvent e) {
    }
    
    private class NESTextDocumentFilter extends DocumentFilter
    {
        private int charLimit;
        private boolean allowsPeriod;
        
        public NESTextDocumentFilter(final int charLimit, final boolean allowPeriod) {
            this.charLimit = charLimit;
            this.allowsPeriod = allowPeriod;
        }
        
        @Override
        public void insertString(final FilterBypass fb, final int offset, final String string, final AttributeSet attr) throws BadLocationException {
            fb.insertString(offset, string, attr);
        }
        
        @Override
        public void replace(final FilterBypass fb, final int offset, final int length, final String string, final AttributeSet attr) throws BadLocationException {
            final String oldText = TextBox.this.getText();
            boolean hasPeriod = oldText.contains(".");
            final String replaced = oldText.substring(offset, offset + length);
            if (string.equals(".") && oldText.contains(".") && !replaced.contains(".")) {
                return;
            }
            final StringBuilder sbNew = new StringBuilder(string);
            int removed = 0;
            for (int i = 0; i < string.length(); ++i) {
                final char c = string.charAt(i);
                if ((c != '.' || !this.allowsPeriod || (hasPeriod && !replaced.contains("."))) && !Character.isDigit(c)) {
                    sbNew.deleteCharAt(i - removed);
                    ++removed;
                }
                else if (c == '.') {
                    hasPeriod = true;
                }
            }
            if (oldText.length() + sbNew.length() - length > this.charLimit) {
                final int overage = oldText.length() + sbNew.length() - length - this.charLimit;
                sbNew.delete(sbNew.length() - overage, sbNew.length());
            }
            final StringBuilder sbFinal = new StringBuilder(oldText);
            sbFinal.replace(offset, offset + length, sbNew.toString());
            fb.replace(0, oldText.length(), sbFinal.toString(), attr);
            TextBox.this.getCaret().setDot(offset + sbNew.length());
        }
        
        @Override
        public void remove(final FilterBypass fb, final int offset, final int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }
    }
}
