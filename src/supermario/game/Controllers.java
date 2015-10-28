// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game;

import de.hardcode.jxinput.event.JXInputAxisEvent;
import de.hardcode.jxinput.event.JXInputDirectionalEvent;
import de.hardcode.jxinput.event.JXInputButtonEvent;
import de.hardcode.jxinput.event.JXInputEventManager;
import de.hardcode.jxinput.JXInputDevice;
import de.hardcode.jxinput.event.JXInputAxisEventListener;
import de.hardcode.jxinput.event.JXInputDirectionalEventListener;
import de.hardcode.jxinput.event.JXInputButtonEventListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.net.URLDecoder;
import java.io.BufferedInputStream;
import de.hardcode.jxinput.JXInputManager;
import de.hardcode.jxinput.Axis;
import de.hardcode.jxinput.Directional;
import de.hardcode.jxinput.Button;

public class Controllers
{
    private Device[] devices;
    private Button upB;
    private Button leftB;
    private Button downB;
    private Button rightB;
    private Button bB;
    private Button aB;
    private Button startB;
    private Directional upD;
    private Directional leftD;
    private Directional downD;
    private Directional rightD;
    private Directional aD;
    private Directional bD;
    private Directional startD;
    private Axis upA;
    private Axis downA;
    private Axis leftA;
    private Axis rightA;
    private String upAName;
    private String downAName;
    private String leftAName;
    private String rightAName;
    private boolean upANeg;
    private boolean downANeg;
    private boolean leftANeg;
    private boolean rightANeg;
    private int upDDir;
    private int leftDDir;
    private int downDDir;
    private int rightDDir;
    private int aDDir;
    private int bDDir;
    private int startDDir;
    private Button[] savedButtons;
    private Directional[] savedDirectionals;
    private Axis[] savedAxis;
    private String[] savedAxisNames;
    private boolean[] savedAxisNegative;
    private int[] savedDDirs;
    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean aPressed;
    private boolean bPressed;
    private boolean startPressed;
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int A = 4;
    public static final int B = 5;
    public static final int START = 6;
    private boolean polling;
    private int pollType;
    public boolean valid;
    public boolean controlsSet;
    public boolean inUse;
    public boolean gettingControls;
    private Game game;
    public int selectedDevice;
    public static final double AXIS_DEADZONE_THRESHOLD = 0.5;
    
    public Controllers(final Game game) {
        this.game = game;
        this.valid = true;
        try {
            final boolean success = this.loadDLL();
            if (!success) {
                throw new RuntimeException("JXInput DLL not loaded successfully.");
            }
            this.initDevices();
            if (this.devices.length == 0) {
                this.valid = false;
            }
            else {
                this.savedButtons = new Button[7];
                this.savedDirectionals = new Directional[7];
                this.savedDDirs = new int[7];
                this.savedAxis = new Axis[4];
                this.savedAxisNames = new String[4];
                this.savedAxisNegative = new boolean[4];
            }
        }
        catch (Exception e) {
            this.valid = false;
        }
    }
    
    public String[] getDevicesList() {
        final String[] deviceList = new String[this.devices.length + 1];
        deviceList[0] = "Keyboard";
        for (int i = 1; i < deviceList.length; ++i) {
            deviceList[i] = this.devices[i - 1].name;
        }
        return deviceList;
    }
    
    public void pollDevices() {
        final boolean upWasPressed = this.upPressed;
        final boolean downWasPressed = this.downPressed;
        final boolean leftWasPressed = this.leftPressed;
        final boolean rightWasPressed = this.rightPressed;
        JXInputManager.updateFeatures();
        if (this.controlsSet) {
            if (!this.game.input.defUpDown || this.upPressed) {
                this.game.input.upDown = this.upPressed;
            }
            if (!this.game.input.defDownDown || this.downPressed) {
                this.game.input.downDown = this.downPressed;
            }
            this.game.input.leftDown = this.leftPressed;
            this.game.input.rightDown = this.rightPressed;
            this.game.input.runDown = this.bPressed;
            this.game.input.jumpDown = this.aPressed;
            if (!this.game.input.defStartDown || this.startPressed) {
                this.game.input.startDown = this.startPressed;
            }
            if (!upWasPressed && this.upPressed) {
                final Input input = this.game.input;
                this.game.input.getClass();
                input.addCheatKey(38);
            }
            else if (!downWasPressed && this.downPressed) {
                final Input input2 = this.game.input;
                this.game.input.getClass();
                input2.addCheatKey(40);
            }
            else if (!leftWasPressed && this.leftPressed) {
                final Input input3 = this.game.input;
                this.game.input.getClass();
                input3.addCheatKey(37);
            }
            else if (!rightWasPressed && this.rightPressed) {
                final Input input4 = this.game.input;
                this.game.input.getClass();
                input4.addCheatKey(39);
            }
        }
    }
    
    public void storeControls() {
        this.savedButtons[0] = this.upB;
        this.savedButtons[1] = this.downB;
        this.savedButtons[2] = this.leftB;
        this.savedButtons[3] = this.rightB;
        this.savedButtons[4] = this.bB;
        this.savedButtons[5] = this.aB;
        this.savedButtons[6] = this.startB;
        this.savedDirectionals[0] = this.upD;
        this.savedDirectionals[1] = this.downD;
        this.savedDirectionals[2] = this.leftD;
        this.savedDirectionals[3] = this.rightD;
        this.savedDirectionals[4] = this.aD;
        this.savedDirectionals[5] = this.bD;
        this.savedDirectionals[6] = this.startD;
        this.savedDDirs[0] = this.upDDir;
        this.savedDDirs[1] = this.downDDir;
        this.savedDDirs[2] = this.leftDDir;
        this.savedDDirs[3] = this.rightDDir;
        this.savedDDirs[4] = this.aDDir;
        this.savedDDirs[5] = this.bDDir;
        this.savedDDirs[6] = this.startDDir;
        this.savedAxis[0] = this.upA;
        this.savedAxis[1] = this.downA;
        this.savedAxis[2] = this.leftA;
        this.savedAxis[3] = this.rightA;
        this.savedAxisNames[0] = this.upAName;
        this.savedAxisNames[1] = this.downAName;
        this.savedAxisNames[2] = this.leftAName;
        this.savedAxisNames[3] = this.rightAName;
        this.savedAxisNegative[0] = this.upANeg;
        this.savedAxisNegative[1] = this.downANeg;
        this.savedAxisNegative[2] = this.leftANeg;
        this.savedAxisNegative[3] = this.rightANeg;
    }
    
    public void clearControls() {
        final Button upB = null;
        this.startB = upB;
        this.aB = upB;
        this.bB = upB;
        this.rightB = upB;
        this.leftB = upB;
        this.downB = upB;
        this.upB = upB;
        final Directional upD = null;
        this.startD = upD;
        this.bD = upD;
        this.aD = upD;
        this.rightD = upD;
        this.leftD = upD;
        this.downD = upD;
        this.upD = upD;
        final Axis axis = null;
        this.rightA = axis;
        this.leftA = axis;
        this.downA = axis;
        this.upA = axis;
    }
    
    public void restoreControls() {
        this.upB = this.savedButtons[0];
        this.downB = this.savedButtons[1];
        this.leftB = this.savedButtons[2];
        this.rightB = this.savedButtons[3];
        this.bB = this.savedButtons[4];
        this.aB = this.savedButtons[5];
        this.startB = this.savedButtons[6];
        this.upD = this.savedDirectionals[0];
        this.downD = this.savedDirectionals[1];
        this.leftD = this.savedDirectionals[2];
        this.rightD = this.savedDirectionals[3];
        this.aD = this.savedDirectionals[4];
        this.bD = this.savedDirectionals[5];
        this.startD = this.savedDirectionals[6];
        this.upDDir = this.savedDDirs[0];
        this.downDDir = this.savedDDirs[1];
        this.leftDDir = this.savedDDirs[2];
        this.rightDDir = this.savedDDirs[3];
        this.aDDir = this.savedDDirs[4];
        this.bDDir = this.savedDDirs[5];
        this.startDDir = this.savedDDirs[6];
        this.upA = this.savedAxis[0];
        this.downA = this.savedAxis[1];
        this.leftA = this.savedAxis[2];
        this.rightA = this.savedAxis[3];
        this.upAName = this.savedAxisNames[0];
        this.downAName = this.savedAxisNames[1];
        this.leftAName = this.savedAxisNames[2];
        this.rightAName = this.savedAxisNames[3];
        this.upANeg = this.savedAxisNegative[0];
        this.downANeg = this.savedAxisNegative[1];
        this.leftANeg = this.savedAxisNegative[2];
        this.rightANeg = this.savedAxisNegative[3];
    }
    
    public void loseAllKeyPresses() {
        final boolean upPressed = false;
        this.startPressed = upPressed;
        this.bPressed = upPressed;
        this.aPressed = upPressed;
        this.rightPressed = upPressed;
        this.leftPressed = upPressed;
        this.downPressed = upPressed;
        this.upPressed = upPressed;
    }
    
    public void getControls(final int device) {
        this.initDeviceListener(device);
        this.gettingControls = true;
        this.controlsSet = false;
        this.polling = true;
        this.getControl(0);
    }
    
    private void getControl(final int type) {
        this.pollType = type;
    }
    
    private boolean loadDLL() throws Exception {
        String libName = "jxinput ";
        final String outputName = "jxinput.dll";
        if (System.getProperty("sun.arch.data.model").equals("32")) {
            libName += "x86.dll";
        }
        else {
            if (!System.getProperty("sun.arch.data.model").equals("64")) {
                return false;
            }
            libName += "x64.dll";
        }
        final InputStream inputStream = this.getClass().getResourceAsStream(libName);
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        final String directory = URLDecoder.decode(Controllers.class.getProtectionDomain().getCodeSource().getLocation().getPath(), Game.ENCODING);
        File tempFile = new File(directory);
        if (tempFile.isFile()) {
            tempFile = new File(URLDecoder.decode(tempFile.getParentFile().getPath() + "/" + outputName, Game.ENCODING));
        }
        else {
            tempFile = new File(URLDecoder.decode(tempFile.getAbsolutePath(), Game.ENCODING) + "/" + outputName);
        }
        if (tempFile.exists()) {
            tempFile.delete();
        }
        final FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        for (int b = bufferedInputStream.read(); b != -1; b = bufferedInputStream.read()) {
            bufferedOutputStream.write(b);
        }
        bufferedInputStream.close();
        bufferedOutputStream.close();
        try {
            System.load(tempFile.getAbsolutePath());
        }
        catch (Error e) {
            throw new RuntimeException("Native JXInput library could not be loaded.");
        }
        return true;
    }
    
    private void initDevices() {
        final int devicesCount = JXInputManager.getNumberOfDevices();
        if (devicesCount > 0) {
            this.devices = new Device[devicesCount];
            for (int i = 0; i < devicesCount; ++i) {
                this.devices[i] = new Device(i, JXInputManager.getJXInputDevice(i));
            }
        }
        else {
            this.devices = new Device[0];
        }
    }
    
    private void initDeviceListener(final int deviceNumber) {
        this.selectedDevice = deviceNumber;
        this.devices[deviceNumber].listenToDevice();
    }
    
    public void removeDeviceListener() {
        if (this.valid) {
            this.devices[this.selectedDevice].removeListeners();
        }
    }
    
    private class Device implements JXInputButtonEventListener, JXInputDirectionalEventListener, JXInputAxisEventListener
    {
        private JXInputDevice device;
        private int number;
        private String name;
        private int buttonsCount;
        private int directionalsCount;
        private int axisCountPossible;
        private boolean axisAdded;
        
        public Device(final int number, final JXInputDevice device) {
            this.number = number;
            this.device = device;
            this.name = device.getName();
            this.buttonsCount = device.getNumberOfButtons();
            this.directionalsCount = device.getNumberOfDirectionals();
            this.axisCountPossible = device.getMaxNumberOfAxes();
        }
        
        public void listenToDevice() {
            if (this.buttonsCount > 0) {
                for (int i = 0; i < this.buttonsCount; ++i) {
                    JXInputEventManager.addListener(this, this.device.getButton(i));
                }
            }
            if (this.directionalsCount > 0) {
                for (int i = 0; i < this.directionalsCount; ++i) {
                    JXInputEventManager.addListener(this, this.device.getDirectional(i));
                }
            }
            if (this.axisCountPossible > 0) {
                for (int i = 0; i < this.axisCountPossible; ++i) {
                    final Axis a = this.device.getAxis(i);
                    if (a != null) {
                        this.axisAdded = true;
                        JXInputEventManager.addListener(this, a);
                    }
                }
            }
        }
        
        public void removeListeners() {
            if (this.buttonsCount > 0) {
                JXInputEventManager.removeListener((JXInputButtonEventListener)this);
            }
            if (this.directionalsCount > 0) {
                JXInputEventManager.removeListener((JXInputDirectionalEventListener)this);
            }
            if (this.axisAdded) {
                JXInputEventManager.removeListener((JXInputAxisEventListener)this);
            }
        }
        
        @Override
        public void changed(final JXInputButtonEvent jxibe) {
            final Button button = jxibe.getButton();
            if (Controllers.this.polling && !this.conflicting(button) && button.getState()) {
                if (Controllers.this.pollType == 0) {
                    Controllers.this.upB = button;
                    final Game access$400 = Controllers.this.game;
                    ++access$400.controllerSelectionIndex;
                    Controllers.this.getControl(1);
                }
                else if (Controllers.this.pollType == 1) {
                    Controllers.this.downB = button;
                    final Game access$2 = Controllers.this.game;
                    ++access$2.controllerSelectionIndex;
                    Controllers.this.getControl(2);
                }
                else if (Controllers.this.pollType == 2) {
                    Controllers.this.leftB = button;
                    final Game access$3 = Controllers.this.game;
                    ++access$3.controllerSelectionIndex;
                    Controllers.this.getControl(3);
                }
                else if (Controllers.this.pollType == 3) {
                    Controllers.this.rightB = button;
                    final Game access$4 = Controllers.this.game;
                    ++access$4.controllerSelectionIndex;
                    Controllers.this.getControl(5);
                }
                else if (Controllers.this.pollType == 5) {
                    Controllers.this.bB = button;
                    final Game access$5 = Controllers.this.game;
                    ++access$5.controllerSelectionIndex;
                    Controllers.this.getControl(4);
                }
                else if (Controllers.this.pollType == 4) {
                    Controllers.this.aB = button;
                    final Game access$6 = Controllers.this.game;
                    ++access$6.controllerSelectionIndex;
                    Controllers.this.getControl(6);
                }
                else if (Controllers.this.pollType == 6) {
                    Controllers.this.startB = button;
                    final Game access$7 = Controllers.this.game;
                    ++access$7.controllerSelectionIndex;
                    Controllers.this.game.inputSelection = false;
                    Controllers.this.inUse = true;
                    Controllers.this.gettingControls = false;
                    Controllers.this.polling = false;
                    Controllers.this.controlsSet = true;
                    Controllers.this.game.enterReleased = false;
                }
            }
            if (Controllers.this.aB != null && button == Controllers.this.aB) {
                Controllers.this.aPressed = Controllers.this.aB.getState();
            }
            else if (Controllers.this.bB != null & button == Controllers.this.bB) {
                Controllers.this.bPressed = Controllers.this.bB.getState();
            }
            else if (Controllers.this.startB != null & button == Controllers.this.startB) {
                Controllers.this.startPressed = Controllers.this.startB.getState();
            }
            else if (Controllers.this.upB != null & button == Controllers.this.upB) {
                Controllers.this.upPressed = Controllers.this.upB.getState();
            }
            else if (Controllers.this.downB != null & button == Controllers.this.downB) {
                Controllers.this.downPressed = Controllers.this.downB.getState();
            }
            else if (Controllers.this.leftB != null & button == Controllers.this.leftB) {
                Controllers.this.leftPressed = Controllers.this.leftB.getState();
            }
            else if (Controllers.this.rightB != null & button == Controllers.this.rightB) {
                Controllers.this.rightPressed = Controllers.this.rightB.getState();
            }
        }
        
        private boolean conflicting(final Object control) {
            return (Controllers.this.upB != null && Controllers.this.upB == control) || (Controllers.this.upD != null && Controllers.this.upD == control && Controllers.this.upD.getDirection() == Controllers.this.upDDir) || ((Controllers.this.downB != null && Controllers.this.downB == control) || (Controllers.this.downD != null && Controllers.this.downD == control && Controllers.this.downD.getDirection() == Controllers.this.downDDir)) || ((Controllers.this.leftB != null && Controllers.this.leftB == control) || (Controllers.this.leftD != null && Controllers.this.leftD == control && Controllers.this.leftD.getDirection() == Controllers.this.leftDDir)) || ((Controllers.this.rightB != null && Controllers.this.rightB == control) || (Controllers.this.rightD != null && Controllers.this.rightD == control && Controllers.this.rightD.getDirection() == Controllers.this.rightDDir)) || ((Controllers.this.aB != null && Controllers.this.aB == control) || (Controllers.this.aD != null && Controllers.this.aD == control && Controllers.this.aD.getDirection() == Controllers.this.aDDir)) || ((Controllers.this.bB != null && Controllers.this.bB == control) || (Controllers.this.bD != null && Controllers.this.bD == control && Controllers.this.bD.getDirection() == Controllers.this.bDDir)) || ((Controllers.this.startB != null && Controllers.this.startB == control) || (Controllers.this.startD != null && Controllers.this.startD == control && Controllers.this.startD.getDirection() == Controllers.this.startDDir)) || (Controllers.this.upA != null && Controllers.this.upA == control && Controllers.this.upA.getName().equals(Controllers.this.upAName) && ((Controllers.this.upA.getValue() < -0.5 && Controllers.this.upANeg) || (Controllers.this.upA.getValue() > 0.5 && !Controllers.this.upANeg))) || (Controllers.this.downA != null && Controllers.this.downA == control && Controllers.this.downA.getName().equals(Controllers.this.upAName) && ((Controllers.this.downA.getValue() < -0.5 && Controllers.this.downANeg) || (Controllers.this.downA.getValue() > 0.5 && !Controllers.this.downANeg))) || (Controllers.this.leftA != null && Controllers.this.leftA == control && Controllers.this.leftA.getName().equals(Controllers.this.leftAName) && ((Controllers.this.leftA.getValue() < -0.5 && Controllers.this.leftANeg) || (Controllers.this.leftA.getValue() > 0.5 && !Controllers.this.leftANeg))) || (Controllers.this.rightA != null && Controllers.this.rightA == control && Controllers.this.rightA.getName().equals(Controllers.this.rightAName) && ((Controllers.this.rightA.getValue() < -0.5 && Controllers.this.rightANeg) || (Controllers.this.rightA.getValue() > 0.5 && !Controllers.this.rightANeg)));
        }
        
        @Override
        public void changed(final JXInputDirectionalEvent jxide) {
            final Directional directional = jxide.getDirectional();
            if (Controllers.this.polling && !this.conflicting(directional) && directional.getValue() > 0.0) {
                if (Controllers.this.pollType == 0) {
                    Controllers.this.upD = directional;
                    Controllers.this.upDDir = directional.getDirection();
                    final Game access$400 = Controllers.this.game;
                    ++access$400.controllerSelectionIndex;
                    Controllers.this.getControl(1);
                }
                else if (Controllers.this.pollType == 1) {
                    Controllers.this.downD = directional;
                    Controllers.this.downDDir = directional.getDirection();
                    final Game access$2 = Controllers.this.game;
                    ++access$2.controllerSelectionIndex;
                    Controllers.this.getControl(2);
                }
                else if (Controllers.this.pollType == 2) {
                    Controllers.this.leftD = directional;
                    Controllers.this.leftDDir = directional.getDirection();
                    final Game access$3 = Controllers.this.game;
                    ++access$3.controllerSelectionIndex;
                    Controllers.this.getControl(3);
                }
                else if (Controllers.this.pollType == 3) {
                    Controllers.this.rightD = directional;
                    Controllers.this.rightDDir = directional.getDirection();
                    final Game access$4 = Controllers.this.game;
                    ++access$4.controllerSelectionIndex;
                    Controllers.this.getControl(5);
                }
                else if (Controllers.this.pollType == 5) {
                    Controllers.this.bD = directional;
                    Controllers.this.bDDir = directional.getDirection();
                    final Game access$5 = Controllers.this.game;
                    ++access$5.controllerSelectionIndex;
                    Controllers.this.getControl(4);
                }
                else if (Controllers.this.pollType == 4) {
                    Controllers.this.aD = directional;
                    Controllers.this.aDDir = directional.getDirection();
                    final Game access$6 = Controllers.this.game;
                    ++access$6.controllerSelectionIndex;
                    Controllers.this.getControl(6);
                }
                else if (Controllers.this.pollType == 6) {
                    Controllers.this.startD = directional;
                    Controllers.this.startDDir = directional.getDirection();
                    final Game access$7 = Controllers.this.game;
                    ++access$7.controllerSelectionIndex;
                    Controllers.this.game.inputSelection = false;
                    Controllers.this.inUse = true;
                    Controllers.this.gettingControls = false;
                    Controllers.this.polling = false;
                    Controllers.this.controlsSet = true;
                    Controllers.this.game.enterReleased = false;
                }
            }
            if (Controllers.this.aD != null && directional == Controllers.this.aD) {
                Controllers.this.aPressed = (Controllers.this.aD.getValue() != 0.0 && directional.getDirection() == Controllers.this.aDDir);
            }
            if (Controllers.this.bD != null & directional == Controllers.this.bD) {
                Controllers.this.bPressed = (Controllers.this.bD.getValue() != 0.0 && directional.getDirection() == Controllers.this.bDDir);
            }
            if (Controllers.this.startD != null & directional == Controllers.this.startD) {
                Controllers.this.startPressed = (Controllers.this.startD.getValue() != 0.0 && Controllers.this.startD.getDirection() == Controllers.this.startDDir);
            }
            if (Controllers.this.upD != null & directional == Controllers.this.upD) {
                Controllers.this.upPressed = (Controllers.this.upD.getValue() != 0.0 && Controllers.this.upD.getDirection() == Controllers.this.upDDir);
            }
            if (Controllers.this.downD != null & directional == Controllers.this.downD) {
                Controllers.this.downPressed = (Controllers.this.downD.getValue() != 0.0 && Controllers.this.downD.getDirection() == Controllers.this.downDDir);
            }
            if (Controllers.this.leftD != null & directional == Controllers.this.leftD) {
                Controllers.this.leftPressed = (Controllers.this.leftD.getValue() != 0.0 && Controllers.this.leftD.getDirection() == Controllers.this.leftDDir);
            }
            if (Controllers.this.rightD != null & directional == Controllers.this.rightD) {
                Controllers.this.rightPressed = (Controllers.this.rightD.getValue() != 0.0 && Controllers.this.rightD.getDirection() == Controllers.this.rightDDir);
            }
        }
        
        @Override
        public void changed(final JXInputAxisEvent jxiae) {
            final Axis axis = jxiae.getAxis();
            final boolean neg = axis.getValue() < -0.5;
            final boolean beyondThreshold = Math.abs(axis.getValue()) > 0.5;
            if (Controllers.this.polling && !this.conflicting(axis) && beyondThreshold) {
                if (Controllers.this.pollType == 0) {
                    Controllers.this.upA = axis;
                    Controllers.this.upAName = axis.getName();
                    Controllers.this.upANeg = neg;
                    final Game access$400 = Controllers.this.game;
                    ++access$400.controllerSelectionIndex;
                    Controllers.this.getControl(1);
                }
                else if (Controllers.this.pollType == 1) {
                    Controllers.this.downA = axis;
                    Controllers.this.downAName = axis.getName();
                    Controllers.this.downANeg = neg;
                    final Game access$2 = Controllers.this.game;
                    ++access$2.controllerSelectionIndex;
                    Controllers.this.getControl(2);
                }
                else if (Controllers.this.pollType == 2) {
                    Controllers.this.leftA = axis;
                    Controllers.this.leftAName = axis.getName();
                    Controllers.this.leftANeg = neg;
                    final Game access$3 = Controllers.this.game;
                    ++access$3.controllerSelectionIndex;
                    Controllers.this.getControl(3);
                }
                else if (Controllers.this.pollType == 3) {
                    Controllers.this.rightA = axis;
                    Controllers.this.rightAName = axis.getName();
                    Controllers.this.rightANeg = neg;
                    final Game access$4 = Controllers.this.game;
                    ++access$4.controllerSelectionIndex;
                    Controllers.this.getControl(5);
                }
            }
            if ((Controllers.this.upA != null & axis == Controllers.this.upA) && axis.getName().equals(Controllers.this.upAName)) {
                Controllers.this.upPressed = (Controllers.this.upANeg == neg && beyondThreshold);
            }
            if (Controllers.this.downA != null && axis == Controllers.this.downA && axis.getName().equals(Controllers.this.downAName)) {
                Controllers.this.downPressed = (Controllers.this.downANeg == neg && beyondThreshold);
            }
            if (Controllers.this.leftA != null && axis == Controllers.this.leftA && axis.getName().equals(Controllers.this.leftAName)) {
                Controllers.this.leftPressed = (Controllers.this.leftANeg == neg && beyondThreshold);
            }
            if (Controllers.this.rightA != null && axis == Controllers.this.rightA && axis.getName().equals(Controllers.this.rightAName)) {
                Controllers.this.rightPressed = (Controllers.this.rightANeg == neg && beyondThreshold);
            }
        }
    }
}
