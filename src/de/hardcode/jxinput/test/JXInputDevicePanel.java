// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.test;

import java.util.Enumeration;
import de.hardcode.jxinput.Axis;
import javax.swing.JSlider;
import de.hardcode.jxinput.Button;
import javax.swing.JCheckBox;
import de.hardcode.jxinput.Directional;
import javax.swing.BorderFactory;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;
import java.util.Iterator;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import java.util.ArrayList;
import de.hardcode.jxinput.JXInputDevice;
import java.awt.Font;
import javax.swing.JPanel;

public class JXInputDevicePanel extends JPanel
{
    private static final Font AXIS_SLIDER_FONT;
    private final JXInputDevice mDev;
    private final ArrayList mAxisSliders;
    private final ArrayList mButtonCheckboxes;
    private final ArrayList mDirectionalLabels;
    private JPanel mAxesPanel;
    private JPanel mAxesPanelContainer;
    private JScrollPane mButtonScrollPane;
    private JPanel mButtonsPanel;
    private JPanel mDirectionalPanel;
    
    public JXInputDevicePanel(final JXInputDevice mDev) {
        this.mAxisSliders = new ArrayList();
        this.mButtonCheckboxes = new ArrayList();
        this.mDirectionalLabels = new ArrayList();
        this.mDev = mDev;
        this.initComponents();
        this.initFromDevice();
    }
    
    void initFromDevice() {
        if (null != this.mDev) {
            ((GridLayout)this.mAxesPanel.getLayout()).setRows(this.mDev.getNumberOfAxes());
            for (int i = 0; i < this.mDev.getMaxNumberOfAxes(); ++i) {
                if (null != this.mDev.getAxis(i)) {
                    final AxisSlider axisSlider = new AxisSlider(this.mDev.getAxis(i));
                    final JLabel label = new JLabel(this.mDev.getAxis(i).getName());
                    label.setVerticalAlignment(1);
                    label.setHorizontalAlignment(0);
                    label.setPreferredSize(new Dimension(90, 0));
                    final JPanel panel = new JPanel();
                    panel.setLayout(new BorderLayout());
                    panel.add(label, "West");
                    panel.add(axisSlider, "Center");
                    this.mAxesPanel.add(panel);
                    this.mAxisSliders.add(axisSlider);
                    new AxisListener(this.mDev.getAxis(i));
                }
            }
            final int numberOfButtons = this.mDev.getNumberOfButtons();
            ((GridLayout)this.mButtonsPanel.getLayout()).setRows(numberOfButtons);
            for (int j = 0; j < numberOfButtons; ++j) {
                if (null != this.mDev.getButton(j)) {
                    final ButtonCheckbox buttonCheckbox = new ButtonCheckbox(this.mDev.getButton(j));
                    this.mButtonCheckboxes.add(buttonCheckbox);
                    this.mButtonsPanel.add(buttonCheckbox);
                    new ButtonListener(this.mDev.getButton(j));
                }
            }
            ((GridLayout)this.mDirectionalPanel.getLayout()).setRows(this.mDev.getNumberOfDirectionals() / 2);
            for (int k = 0; k < this.mDev.getMaxNumberOfDirectionals(); ++k) {
                if (null != this.mDev.getDirectional(k)) {
                    final DirectionalLabel directionalLabel = new DirectionalLabel(this.mDev.getDirectional(k));
                    this.mDirectionalLabels.add(directionalLabel);
                    this.mDirectionalPanel.add(directionalLabel);
                    new DirectionalListener(this.mDev.getDirectional(k));
                }
            }
        }
    }
    
    public void update() {
        final Iterator<AxisSlider> iterator = this.mAxisSliders.iterator();
        while (iterator.hasNext()) {
            iterator.next().update();
        }
        final Iterator<ButtonCheckbox> iterator2 = this.mButtonCheckboxes.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().update();
        }
        final Iterator<DirectionalLabel> iterator3 = this.mDirectionalLabels.iterator();
        while (iterator3.hasNext()) {
            iterator3.next().update();
        }
    }
    
    private void initComponents() {
        this.mAxesPanelContainer = new JPanel();
        this.mAxesPanel = new JPanel();
        this.mDirectionalPanel = new JPanel();
        this.mButtonScrollPane = new JScrollPane();
        this.mButtonsPanel = new JPanel();
        this.setLayout(new BorderLayout(2, 2));
        this.addComponentListener(new ComponentAdapter() {
            public void componentShown(final ComponentEvent componentEvent) {
                JXInputDevicePanel.this.OnShow(componentEvent);
            }
        });
        this.mAxesPanelContainer.setLayout(new BorderLayout());
        this.mAxesPanelContainer.setBorder(BorderFactory.createBevelBorder(0));
        this.mAxesPanel.setLayout(new GridLayout(1, 1, 0, 20));
        this.mAxesPanelContainer.add(this.mAxesPanel, "North");
        this.add(this.mAxesPanelContainer, "Center");
        this.mDirectionalPanel.setLayout(new GridLayout(1, 1));
        this.mDirectionalPanel.setBorder(BorderFactory.createBevelBorder(0));
        this.add(this.mDirectionalPanel, "South");
        this.mButtonsPanel.setLayout(new GridLayout(1, 1));
        this.mButtonsPanel.setBorder(BorderFactory.createBevelBorder(0));
        this.mButtonScrollPane.setViewportView(this.mButtonsPanel);
        this.add(this.mButtonScrollPane, "East");
    }
    
    private void OnShow(final ComponentEvent componentEvent) {
    }
    
    static {
        AXIS_SLIDER_FONT = new Font("Verdana", 0, 9);
    }
    
    private class DirectionalLabel extends JLabel
    {
        Directional mDirectional;
        int mCurrent;
        
        DirectionalLabel(final Directional mDirectional) {
            super(mDirectional.getName());
            this.mCurrent = 0;
            this.mDirectional = mDirectional;
        }
        
        void update() {
            final int direction = this.mDirectional.getDirection();
            if (direction != this.mCurrent) {
                this.setText(this.mDirectional.getName() + ":  " + (this.mDirectional.isCentered() ? "-" : Integer.toString(direction)));
                this.mCurrent = direction;
            }
        }
    }
    
    private class ButtonCheckbox extends JCheckBox
    {
        Button mButton;
        
        ButtonCheckbox(final Button mButton) {
            super(mButton.getName());
            this.setEnabled(false);
            this.mButton = mButton;
        }
        
        void update() {
            final boolean state = this.mButton.getState();
            if (state != this.isSelected()) {
                this.setSelected(state);
            }
        }
    }
    
    private class AxisSlider extends JSlider
    {
        Axis mAxis;
        
        AxisSlider(final Axis mAxis) {
            super((2 == mAxis.getType()) ? 0 : -100, 100);
            this.setMajorTickSpacing((2 == mAxis.getType()) ? 25 : 50);
            this.setMinorTickSpacing(5);
            this.setPaintTicks(true);
            this.setPaintLabels(true);
            this.setEnabled(false);
            final Enumeration<JLabel> elements = this.getLabelTable().elements();
            while (elements.hasMoreElements()) {
                final JLabel label = elements.nextElement();
                label.setFont(JXInputDevicePanel.AXIS_SLIDER_FONT);
                label.setSize(32, 12);
                label.setHorizontalAlignment(2);
            }
            this.mAxis = mAxis;
        }
        
        void update() {
            final int value = (int)(this.mAxis.getValue() * 100.0);
            if (value != this.getValue()) {
                this.setValue(value);
                this.setToolTipText(this.mAxis.getName() + ": " + Double.toString(this.mAxis.getValue()));
            }
        }
    }
}
