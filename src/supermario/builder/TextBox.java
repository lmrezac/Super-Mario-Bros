// 
// Decompiled by Procyon v0.5.29
// 

package supermario.builder;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.swing.text.BadLocationException;
import javax.swing.text.AttributeSet;
import javax.swing.JPasswordField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.Font;
import javax.swing.JTextField;

public class TextBox extends JTextField
{
    private static Font textFont;
    
    public TextBox(final int charLimit, final boolean numbersOnly, final boolean upperCaseRequired, final boolean customTextField) {
        this.setHorizontalAlignment(0);
        this.setFont(TextBox.textFont);
        this.setColumns(charLimit + 1);
        final PlainDocument document = new PlainDocument();
        final NESTextDocumentFilter filter = new NESTextDocumentFilter(this, charLimit, numbersOnly, upperCaseRequired, customTextField, true);
        final AbstractDocument absDoc = document;
        absDoc.setDocumentFilter(filter);
        this.setDocument(document);
    }
    
    public static JPasswordField getPasswordTextBox(final int charLimit, final boolean numbersOnly, final boolean upperCaseRequired) {
        final JPasswordField passwordField = new JPasswordField();
        passwordField.setEchoChar('*');
        passwordField.setHorizontalAlignment(0);
        passwordField.setFont(TextBox.textFont);
        passwordField.setColumns(charLimit + 1);
        final PlainDocument document = new PlainDocument();
        final NESTextDocumentFilter filter = new NESTextDocumentFilter(passwordField, charLimit, numbersOnly, upperCaseRequired, false, false);
        final AbstractDocument absDoc = document;
        absDoc.setDocumentFilter(filter);
        passwordField.setDocument(document);
        return passwordField;
    }
    
    static {
        TextBox.textFont = new Font("Monospaced", 0, 12);
    }
    
    private static class NESTextDocumentFilter extends DocumentFilter
    {
        private JTextField textField;
        private int charLimit;
        private boolean numbersOnly;
        private boolean upperCaseRequired;
        private boolean spaceAllowed;
        private boolean customTextField;
        private static final char[] limitedChars;
        
        public NESTextDocumentFilter(final JTextField textField, final int charLimit, final boolean numbersOnly, final boolean upperCaseRequired, final boolean customTextField, final boolean spaceAllowed) {
            this.textField = textField;
            this.charLimit = charLimit;
            this.numbersOnly = numbersOnly;
            this.upperCaseRequired = upperCaseRequired;
            this.customTextField = customTextField;
            this.spaceAllowed = spaceAllowed;
        }
        
        @Override
        public void insertString(final FilterBypass fb, final int offset, final String string, final AttributeSet attr) throws BadLocationException {
            fb.insertString(offset, string, attr);
        }
        
        private boolean isSpecialCharacter(final char ch) {
            return ch == '~' || ch == '`' || ch == '!' || ch == '@' || ch == '#' || ch == '$' || ch == '%' || ch == '^' || ch == '&' || ch == '*' || ch == '(' || ch == ')' || (ch == '_' || ch == '-' || ch == '+' || ch == '=' || ch == '{' || ch == '[' || ch == '}' || ch == ']' || ch == '|' || ch == '\\') || (ch == ':' || ch == ';' || ch == '\"' || ch == '\'' || ch == '<' || ch == ',' || ch == '>' || ch == '.' || ch == '?' || ch == '/');
        }
        
        private boolean isLimitedSpecialCharacter(final char ch) {
            for (int i = 0; i < NESTextDocumentFilter.limitedChars.length; ++i) {
                if (NESTextDocumentFilter.limitedChars[i] == ch) {
                    return true;
                }
            }
            return false;
        }
        
        private boolean isLetter(final char ch) {
            return String.valueOf(ch).matches("[a-zA-z]{1}");
        }
        
        @Override
        public void replace(final FilterBypass fb, final int offset, final int length, String string, final AttributeSet attr) throws BadLocationException {
            final String oldText = this.textField.getText();
            if (this.upperCaseRequired) {
                string = string.toUpperCase();
            }
            if (this.customTextField) {
                if (string != null && string.length() == 1) {
                    final char c = string.charAt(0);
                    if (Character.isDigit(c) || this.isLetter(c) || this.isLimitedSpecialCharacter(c)) {
                        fb.replace(0, oldText.length(), string, attr);
                    }
                }
                if (this.customTextField) {
                    this.textField.getCaret().setVisible(false);
                }
            }
            else {
                final StringBuilder sbNew = new StringBuilder(string);
                int removed = 0;
                for (int i = 0; i < string.length(); ++i) {
                    final char c2 = string.charAt(i);
                    if ((this.numbersOnly && !Character.isDigit(c2)) || (!this.spaceAllowed && c2 == ' ') || (!this.isLetter(c2) && !this.isSpecialCharacter(c2) && !Character.isDigit(c2) && c2 != ' ')) {
                        sbNew.deleteCharAt(i - removed);
                        ++removed;
                    }
                }
                if (oldText.length() + sbNew.length() - length > this.charLimit) {
                    final int overage = oldText.length() + sbNew.length() - length - this.charLimit;
                    sbNew.delete(sbNew.length() - overage, sbNew.length());
                }
                final StringBuilder sbFinal = new StringBuilder(oldText);
                sbFinal.replace(offset, offset + length, sbNew.toString());
                fb.replace(0, oldText.length(), sbFinal.toString(), attr);
                this.textField.getCaret().setDot(offset + sbNew.length());
            }
        }
        
        @Override
        public void remove(final FilterBypass fb, final int offset, final int length) throws BadLocationException {
            if (!this.customTextField) {
                super.remove(fb, offset, length);
            }
        }
        
        static {
            limitedChars = new char[] { '!', '@', '#', '$', '%', '&', '*', '(', ')', '-', '+', '=', '\"', ',', '.', '\'', '~', ';', ':', '\\', '/', '^', '?', '<', '>', '|', '[', ']', '{', '}', '_' };
        }
    }
}
