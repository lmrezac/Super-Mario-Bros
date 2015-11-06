// 
// Decompiled by Procyon v0.5.29
// 

package supermario.debug;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import supermario.Utilities;
import supermario.builder.BuilderFrame;
import supermario.game.Game;

public abstract class DebugTool extends JPanel implements WindowListener, KeyListener
{
    private static final long serialVersionUID = -2343310710233342607L;
	final int LEFT = 17;
    final int CENTER = 10;
    final int RIGHT = 13;
    final Color backgroundColor;
    protected Game game;
    protected JFrame frame;
    
    public DebugTool(final Game game, final String title) {
        this.backgroundColor = Color.WHITE;
        this.game = game;
        (this.frame = new JFrame(title)).setDefaultCloseOperation(0);
        this.frame.addWindowListener(this);
        this.frame.setResizable(false);
        Utilities.setIcon(this.frame);
        this.frame.addKeyListener(this);
        this.frame.setFocusable(true);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setLayout(new GridBagLayout());
        this.initComponents();
        this.frame.getContentPane().add(this);
    }
    
    public void packFrame() {
        this.frame.pack();
    }
    
    protected GridBagConstraints constraints(final int x, final int y, final int width, final int height, final int pos) {
        final Insets insets = new Insets(1, 5, 1, 5);
        return new GridBagConstraints(x, y, width, height, 1.0, 1.0, pos, 0, insets, 0, 0);
    }
    
    protected GridBagConstraints constraintsWithInsets(final int x, final int y, final int width, final int height, final int pos, final int left, final int right, final int top, final int bottom) {
        final Insets insets = new Insets(top, left, bottom, right);
        return new GridBagConstraints(x, y, width, height, 1.0, 1.0, pos, 0, insets, 0, 0);
    }
    
    public abstract void initComponents();
    
    public void showConsole(final BuilderFrame bFrame, final Component focusPoint) {
        if (!this.isShowing()) {
            this.frame.pack();
            this.frame.setLocationRelativeTo(bFrame);
            this.frame.setVisible(true);
            this.frame.setLocationRelativeTo(focusPoint);
        }
        else {
            this.requestFocus();
        }
    }
    
    public abstract void hideConsole();
}
