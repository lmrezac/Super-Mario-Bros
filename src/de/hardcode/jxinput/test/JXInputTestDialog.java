// 
// Decompiled by Procyon v0.5.29
// 

package de.hardcode.jxinput.test;

import javax.swing.JFrame;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusAdapter;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import de.hardcode.jxinput.JXInputDevice;
import de.hardcode.jxinput.virtual.VirtualAxis;
import java.awt.Component;
import javax.swing.SwingUtilities;
import de.hardcode.jxinput.JXInputManager;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.Frame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import de.hardcode.jxinput.Button;
import de.hardcode.jxinput.virtual.JXVirtualInputDevice;
import de.hardcode.jxinput.keyboard.JXKeyboardInputDevice;
import java.awt.event.ActionListener;
import javax.swing.JDialog;

public class JXInputTestDialog extends JDialog implements ActionListener
{
    private JXKeyboardInputDevice mKeyboardDevice;
    private JXVirtualInputDevice mVirtualDevice;
    Button mButtonUp;
    Button mButtonDown;
    Button mButtonLeft;
    Button mButtonRight;
    Button mButtonFire;
    Button mButtonSpace;
    private JButton mButtonReset;
    private JTabbedPane mDevicesTabbedPane;
    private JLabel mLabelNoDevice;
    private JPanel mMainPanel;
    
    public JXInputTestDialog(final Frame frame, final boolean b) {
        super(frame, b);
        this.mKeyboardDevice = null;
        this.mVirtualDevice = null;
        this.initComponents();
        this.configureKeyboardInputDevice();
        this.configureVirtualInputDevice();
        this.initDevicePanels();
        this.pack();
        this.mMainPanel.requestFocus();
        new Timer(50, this).start();
    }
    
    public void actionPerformed(final ActionEvent actionEvent) {
        JXInputManager.updateFeatures();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                for (int i = 0; i < JXInputTestDialog.this.mDevicesTabbedPane.getComponentCount(); ++i) {
                    ((JXInputDevicePanel)JXInputTestDialog.this.mDevicesTabbedPane.getComponent(i)).update();
                }
            }
        });
    }
    
    void configureKeyboardInputDevice() {
        (this.mKeyboardDevice = JXInputManager.createKeyboardDevice()).createButton(27);
        this.mKeyboardDevice.createButton(112);
        this.mKeyboardDevice.createButton(113);
        this.mKeyboardDevice.createButton(114);
        this.mKeyboardDevice.createButton(115);
        this.mKeyboardDevice.createButton(37);
        this.mKeyboardDevice.createButton(39);
        this.mKeyboardDevice.createButton(38);
        this.mKeyboardDevice.createButton(40);
        this.mKeyboardDevice.createButton(33);
        this.mKeyboardDevice.createButton(34);
        this.mButtonSpace = this.mKeyboardDevice.createButton(32);
        this.mButtonLeft = this.mKeyboardDevice.createButton(65);
        this.mButtonRight = this.mKeyboardDevice.createButton(68);
        this.mButtonDown = this.mKeyboardDevice.createButton(83);
        this.mButtonUp = this.mKeyboardDevice.createButton(87);
        this.mKeyboardDevice.listenTo(this.mMainPanel);
    }
    
    void configureVirtualInputDevice() {
        this.mVirtualDevice = JXInputManager.createVirtualDevice();
        final Button button = JXInputManager.getJXInputDevice(0).getButton(0);
        final VirtualAxis axis = this.mVirtualDevice.createAxis(0);
        axis.setButtons(this.mButtonRight, this.mButtonLeft);
        axis.setName("x: A-D");
        final VirtualAxis axis2 = this.mVirtualDevice.createAxis(1);
        axis2.setButtons(this.mButtonUp, this.mButtonDown);
        axis2.setSpringSpeed(0.0);
        axis2.setName("y: S|W");
        final VirtualAxis axis3 = this.mVirtualDevice.createAxis(6);
        axis3.setIncreaseButton(this.mButtonSpace);
        axis3.setTimeFor0To1(2000);
        axis3.setName("<space>");
        axis3.setType(2);
        if (null != button) {
            final VirtualAxis axis4 = this.mVirtualDevice.createAxis(7);
            axis4.setIncreaseButton(button);
            axis4.setTimeFor0To1(2000);
            axis4.setName("JoyButton 0");
        }
    }
    
    void initDevicePanels() {
        final int numberOfDevices = JXInputManager.getNumberOfDevices();
        this.mLabelNoDevice.setVisible(numberOfDevices == 0);
        this.mDevicesTabbedPane.setVisible(numberOfDevices != 0);
        for (int i = 0; i < numberOfDevices; ++i) {
            final JXInputDevice jxInputDevice = JXInputManager.getJXInputDevice(i);
            if (null != jxInputDevice) {
                this.mDevicesTabbedPane.addTab(jxInputDevice.getName(), new JXInputDevicePanel(jxInputDevice));
            }
        }
    }
    
    private void initComponents() {
        this.mMainPanel = new JPanel();
        this.mLabelNoDevice = new JLabel();
        this.mDevicesTabbedPane = new JTabbedPane();
        this.mButtonReset = new JButton();
        this.setTitle("JXInput (C) 2001-2006 HARDCODE Dev.");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent windowEvent) {
                JXInputTestDialog.this.closeDialog(windowEvent);
            }
        });
        this.mMainPanel.setLayout(new BorderLayout(10, 0));
        this.mLabelNoDevice.setHorizontalAlignment(0);
        this.mLabelNoDevice.setText("No JXInputDevice available!");
        this.mLabelNoDevice.setBorder(new SoftBevelBorder(0));
        this.mMainPanel.add(this.mLabelNoDevice, "North");
        this.mDevicesTabbedPane.addFocusListener(new FocusAdapter() {
            public void focusGained(final FocusEvent focusEvent) {
                JXInputTestDialog.this.mDevicesTabbedPaneFocusGained(focusEvent);
            }
        });
        this.mMainPanel.add(this.mDevicesTabbedPane, "Center");
        this.mButtonReset.setText("Reset ");
        this.mButtonReset.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent actionEvent) {
                JXInputTestDialog.this.mButtonResetActionPerformed(actionEvent);
            }
        });
        this.mMainPanel.add(this.mButtonReset, "South");
        this.getContentPane().add(this.mMainPanel, "Center");
        this.pack();
    }
    
    private void mButtonResetActionPerformed(final ActionEvent actionEvent) {
        while (this.mDevicesTabbedPane.getTabCount() > 0) {
            this.mDevicesTabbedPane.removeTabAt(0);
        }
        JXInputManager.reset();
        this.configureKeyboardInputDevice();
        this.configureVirtualInputDevice();
        this.initDevicePanels();
        this.pack();
        this.mMainPanel.requestFocus();
    }
    
    private void mDevicesTabbedPaneFocusGained(final FocusEvent focusEvent) {
        this.mMainPanel.requestFocus();
    }
    
    private void closeDialog(final WindowEvent windowEvent) {
        this.setVisible(false);
        this.dispose();
        System.exit(0);
    }
    
    public static void main(final String[] array) {
        new JXInputTestDialog(new JFrame(), true).setVisible(true);
    }
}
