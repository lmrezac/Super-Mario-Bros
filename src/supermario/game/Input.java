// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import static supermario.debug.Debugger.*;
@SuppressWarnings("unused")
public class Input implements KeyListener
{
    private Game game;
    private final String configFile = "mario.cfg";
    public static final int CANCEL_INPUT_SELECTED = -1;
    public boolean leftDown;
    public boolean rightDown;
    public boolean upDown;
    public boolean downDown;
    public boolean jumpDown;
    public boolean startDown;
    public boolean runDown;
    public boolean defUpDown;
    public boolean defDownDown;
    public boolean defStartDown;
    private boolean escapeReleased;
    public final int defaultUp = 38;
    public final int defaultDown = 40;
    public final int defaultLeft = 37;
    public final int defaultRight = 39;
    public final int defaultJump = KeyEvent.VK_Z;
    public final int defaultRun = KeyEvent.VK_X;
    public final int defaultStart = 10;
    private int upKey;
    private int downKey;
    private int leftKey;
    private int rightKey;
    private int jumpKey;
    private int runKey;
    private int startKey;
    public boolean showGrid;
    public boolean autoScroll;
    public boolean screenMarker;
    public boolean stretchButtons;
    public boolean godModeTesting;
    public boolean superMarioTesting;
    public boolean extraLivesCheat;
    public boolean fireMarioTesting;
    public boolean unlimitedFireballsTesting;
    public boolean lockDragRow;
    public boolean lockDragColumn;
    private static final int FEATURE_ON = 49;
    private static final int FEATURE_OFF = 48;
    public float loadedVolume;
    public boolean hidePhysicsWarning;
    private int[] newControls;
    public File jarFile;
    public String jarDirectory;
    private String sessionDirectory;
    public Controllers controllers;
    public boolean promptingForController;
    public long lastMouseEvent;
    private long lastCodePress;
    public static final int MAX_DELAY_CHEAT_KEY = 333;
    private LinkedList<Integer> cheatCode;
    
    public Input(final Game game) {
        this.escapeReleased = true;
        this.lastCodePress = System.currentTimeMillis();
        this.cheatCode = new LinkedList<Integer>();
        this.game = game;
        this.loadConfiguration();
        this.controllers = new Controllers(game);
    }
    
    private void loadCustomConfig() {
        FileInputStream fStream = null;
        InputStreamReader iStreamReader = null;
        BufferedReader bReader = null;
        try {
            fStream = new FileInputStream(new File("CustomDimensions.txt"));
            iStreamReader = new InputStreamReader(fStream, Game.ENCODING);
            bReader = new BufferedReader(iStreamReader);
            final String xDim = bReader.readLine();
            final String yDim = bReader.readLine();
            Game.xTiles = Integer.parseInt(xDim);
            Game.yTiles = Integer.parseInt(yDim);
            Game.overlayXOffset = (Game.xTiles - 32) / 2;
            Game.overlayYOffset = Game.yTiles - 28;
            Game.renderWidth = Game.xTiles * 8;
            Game.renderHeight = Game.yTiles * 8;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (fStream != null) {
                    fStream.close();
                }
                if (iStreamReader != null) {
                    iStreamReader.close();
                }
                if (bReader != null) {
                    bReader.close();
                }
            }
            catch (Exception ex) {}
        }
    }
    
    private void loadConfiguration() {
        try {
            this.jarFile = new File(URLDecoder.decode(Game.class.getProtectionDomain().getCodeSource().getLocation().getFile(), Game.ENCODING));
            this.jarDirectory = URLDecoder.decode(this.jarFile.getParent(), Game.ENCODING);
            FileInputStream fStream = null;
            try {
                fStream = new FileInputStream(new File("mario.cfg"));
            }
            catch (FileNotFoundException e) {
                this.sessionDirectory = this.jarDirectory;
                this.useDefaultSettings();
                return;
            }
            final InputStreamReader iStreamReader = new InputStreamReader(fStream, Game.ENCODING);
            final BufferedReader bReader = new BufferedReader(iStreamReader);
            try {
                final String loadedDirectory = bReader.readLine();
                this.sessionDirectory = loadedDirectory;
                final File file = new File(this.sessionDirectory);
                if (!file.exists()) {
                	bReader.close();
                    throw new Exception();
                }
            }
            catch (Exception e2) {
                this.sessionDirectory = this.jarDirectory;
            }
            (this.newControls = new int[7])[0] = bReader.read();
            this.newControls[1] = bReader.read();
            this.newControls[2] = bReader.read();
            this.newControls[3] = bReader.read();
            this.newControls[4] = bReader.read();
            this.newControls[5] = bReader.read();
            this.newControls[6] = bReader.read();
            final int gridChar = (char)bReader.read();
            this.showGrid = (gridChar != 48);
            final int autoScrollChar = (char)bReader.read();
            this.autoScroll = (autoScrollChar != 48);
            final int screenMarkerChar = (char)bReader.read();
            this.screenMarker = (screenMarkerChar != 48);
            final int stretchButtonsChar = (char)bReader.read();
            this.stretchButtons = (stretchButtonsChar == 49);
            final int godModeTestingChar = (char)bReader.read();
            this.godModeTesting = (godModeTestingChar == 49);
            final int superMarioTestingChar = (char)bReader.read();
            this.superMarioTesting = (superMarioTestingChar == 49);
            final int fireMarioTestingChar = (char)bReader.read();
            this.fireMarioTesting = (fireMarioTestingChar == 49);
            final int unlimitedFireballTestingChar = (char)bReader.read();
            this.unlimitedFireballsTesting = (unlimitedFireballTestingChar == 49);
            final int lockDragRowChar = (char)bReader.read();
            this.lockDragRow = (lockDragRowChar == 49);
            final int lockDragColChar = (char)bReader.read();
            this.lockDragColumn = (lockDragColChar == 49);
            final int volChar = bReader.read();
            if (volChar == -1 || (char)volChar == 'M') {
                this.loadedVolume = 1.0f;
            }
            else {
                this.loadedVolume = Integer.valueOf(String.valueOf((char)volChar)) / 10.0f;
            }
            final int hidePhysicsWarningChar = (char)bReader.read();
            this.hidePhysicsWarning = (hidePhysicsWarningChar == 49);
            bReader.close();
            iStreamReader.close();
            fStream.close();
            if (!this.noControlConflicts(this.newControls)) {
                throw new Exception();
            }
            this.setControls(this.newControls);
        }
        catch (Exception e3) {
            this.useDefaultSettings();
        }
    }
    
    private void useDefaultSettings() {
        this.upKey = defaultUp;
        this.downKey = defaultDown;
        this.leftKey = defaultLeft;
        this.rightKey = defaultRight;
        this.jumpKey = defaultJump;
        this.runKey = defaultRun;
        this.startKey = 10;
        this.showGrid = false;
        this.autoScroll = true;
        this.screenMarker = false;
        this.stretchButtons = false;
        this.godModeTesting = false;
        this.superMarioTesting = false;
        this.fireMarioTesting = false;
        this.lockDragRow = false;
        this.lockDragColumn = false;
        this.loadedVolume = 1.0f;
        this.hidePhysicsWarning = false;
    }
    
    public void writeConfiguration() {
        try {
            final FileOutputStream fStream = new FileOutputStream("mario.cfg");
            final OutputStreamWriter oStreamWriter = new OutputStreamWriter(fStream, Game.ENCODING);
            final BufferedWriter bWriter = new BufferedWriter(oStreamWriter);
            bWriter.write(this.sessionDirectory + "\r\n");
            bWriter.write(this.upKey);
            bWriter.write(this.downKey);
            bWriter.write(this.leftKey);
            bWriter.write(this.rightKey);
            bWriter.write(this.runKey);
            bWriter.write(this.jumpKey);
            bWriter.write(this.startKey);
            if (this.showGrid) {
                bWriter.write(49);
            }
            else {
                bWriter.write(48);
            }
            if (this.autoScroll) {
                bWriter.write(49);
            }
            else {
                bWriter.write(48);
            }
            if (this.screenMarker) {
                bWriter.write(49);
            }
            else {
                bWriter.write(48);
            }
            if (this.stretchButtons) {
                bWriter.write(49);
            }
            else {
                bWriter.write(48);
            }
            if (this.godModeTesting) {
                bWriter.write(49);
            }
            else {
                bWriter.write(48);
            }
            if (this.superMarioTesting) {
                bWriter.write(49);
            }
            else {
                bWriter.write(48);
            }
            if (this.fireMarioTesting) {
                bWriter.write(49);
            }
            else {
                bWriter.write(48);
            }
            if (this.unlimitedFireballsTesting) {
                bWriter.write(49);
            }
            else {
                bWriter.write(48);
            }
            if (this.lockDragRow) {
                bWriter.write(49);
            }
            else {
                bWriter.write(48);
            }
            if (this.lockDragColumn) {
                bWriter.write(49);
            }
            else {
                bWriter.write(48);
            }
            if (this.game.audio.isValid()) {
                final int vol = (int)Math.round(this.game.audio.getVolume() * 10.0);
                if (vol == 10) {
                    bWriter.write(77);
                }
                else {
                    bWriter.write(String.valueOf(vol).charAt(0));
                }
            }
            if (this.hidePhysicsWarning) {
                bWriter.write(49);
            }
            else {
                bWriter.write(48);
            }
            bWriter.close();
            oStreamWriter.close();
            fStream.close();
        }
        catch (Exception ex) {}
    }
    
    public String getSessionDirectory() {
        return this.sessionDirectory;
    }
    
    public void setSessionDirectory(final String path) {
        this.sessionDirectory = path;
        this.writeConfiguration();
    }
    
    public void loseAllKeyPresses() {
        final boolean leftDown = false;
        this.downDown = leftDown;
        this.runDown = leftDown;
        this.startDown = leftDown;
        this.jumpDown = leftDown;
        this.upDown = leftDown;
        this.rightDown = leftDown;
        this.leftDown = leftDown;
        final boolean defDownDown = false;
        this.defStartDown = defDownDown;
        this.defUpDown = defDownDown;
        this.defDownDown = defDownDown;
        if (this.controllers != null && this.controllers.valid && this.controllers.inUse) {
            this.controllers.loseAllKeyPresses();
        }
        this.game.enterReleased = true;
        this.escapeReleased = true;
        if (this.game.menu != null) {
            this.game.menu.enterReleased = true;
        }
        final Game game = this.game;
        final Game game2 = this.game;
        final boolean b = false;
        game2.downHold = b;
        game.upHold = b;
    }
    
    public void cancelControlSetting() {
        this.escapeReleased = false;
        this.newControls = null;
        this.game.inputSelection = false;
        if (this.controllers.gettingControls) {
            if (this.controllers.inUse) {
                this.controllers.restoreControls();
                this.controllers.controlsSet = true;
            }
            else {
                this.controllers.controlsSet = false;
                this.controllers.removeDeviceListener();
            }
            this.controllers.gettingControls = false;
        }
    }
    
    private boolean reservedKeyboardKey(final int key) {
        return key == 49 || key == 50 || key == 51 || (key == 52 || key == 53 || key == 54) || (key == 55 || key == 56 || key == 57 || key == 48) || key == 112;
    }
    
    private boolean noControlConflicts(final int[] controls) {
        for (int i = 0; i < controls.length; ++i) {
            for (int j = 0; j < controls.length; ++j) {
                if ((controls[i] == controls[j] && i != j) || controls[i] == -1 || this.reservedKeyboardKey(controls[i])) {
                    return false;
                }
            }
        }
        return (!this.reservedKey(controls[0]) || controls[0] == 38) && (!this.reservedKey(controls[1]) || controls[1] == 40) && (!this.reservedKey(controls[2]) || controls[2] == 37) && (!this.reservedKey(controls[3]) || controls[3] == 39) && (!this.reservedKey(controls[6]) || controls[6] == 10);
    }
    
    private void setControls(final int[] keys) {
        this.upKey = keys[0];
        this.downKey = keys[1];
        this.leftKey = keys[2];
        this.rightKey = keys[3];
        this.runKey = KeyEvent.VK_X;
        this.jumpKey = KeyEvent.VK_Z;
        this.startKey = keys[6];
        this.writeConfiguration();
    }
    
    private boolean previouslyEnteredNewControl(final int key, final int thisIndex) {
        for (int i = 0; i < thisIndex; ++i) {
            if (this.newControls[i] == key) {
                return true;
            }
        }
        return false;
    }
    
    private boolean keyIsUsed(final int k) {
        return this.upKey == k || this.downKey == k || this.leftKey == k || this.rightKey == k || this.runKey == k || this.jumpKey == k || this.startKey == k;
    }
    
    public void addCheatKey(final int code) {
        final long oldLastCodePress = this.lastCodePress;
        this.lastCodePress = System.currentTimeMillis();
        if (this.lastCodePress - oldLastCodePress > 333L) {
            this.cheatCode.clear();
        }
        if (this.isDirectionalKey(code)) {
            this.cheatCode.add(this.convertCheatCodeToDefault(code));
            this.checkForCheat();
        }
    }
    
    private int convertCheatCodeToDefault(final int code) {
       
       /*if (code == 38 || code == this.upKey) {
          
            return 38;
        }
       
        if (code == 40 || code == this.downKey) {
            return this.downKey;
        }
    
        if (code == 37 || code == this.leftKey) {
            return this.leftKey;
        }
        if (code == this.rightKey || code == this.rightKey) {
            return this.rightKey;
        }*/
    	if(code == downKey || code == upKey || code == leftKey || code == rightKey)
    		return code;
        throw new IllegalStateException("Unknown cheat code key entered");
    }
    
    private boolean isDirectionalKey(final int code) {
     
        if (code == this.upKey) {
            return true;
        }
        
        if (code == this.downKey) {
            return true;
        }
  
        return code == this.leftKey || code == this.rightKey;
    }
    private Integer[] getCodesAsArray(){
    	Integer[] codes = new Integer[this.cheatCode.size()];
    	for(int i = 0; i < cheatCode.size(); i++){
    		
    	}
    	return null;
    }
    private void checkForCheat() {
   
        final int up = defaultUp;
    
        final int dn = defaultDown;

        final int lf = defaultLeft;

        final int rt = defaultRight;
        debug(new Exception(),"check for cheat");
        Integer[] codes = new Integer[this.cheatCode.size()];
        codes = this.cheatCode.toArray(codes);
        debug(new Exception(),"codes = "+Arrays.toString(codes)+" cheatCode = "+cheatCode.toString());
        if (this.cheatCode.size() == 4) {
        	boolean playAsLuigi = this.cheatMatch(codes, new int[] {lf, up, rt, dn});
        	if(playAsLuigi){
        		debug(new Exception(),"toggle luigi");
        		Game.instance.mario.asLuigi = !Game.instance.mario.asLuigi;
        		if(this.game.mario.asLuigi)
        			this.game.audio.play(Audio.FRIEND_GROW);
        		else
        			this.game.audio.play(Audio.POWER_DOWN_AND_PIPE);
        	}
        }
        else if (this.cheatCode.size() == 5) {
            final boolean godMode = this.cheatMatch(codes, new int[] { up, dn, lf, rt, lf });
            if (godMode) {
            	debug(new Exception(),"godmode");
                this.game.mario.godMode = !this.game.mario.godMode;
                this.game.audio.play(this.game.mario.godMode ? 12 : 17);
            }
        }
        else if (this.cheatCode.size() == 6) {
            final boolean fireballs = this.cheatMatch(codes, new int[] { up, dn, up, dn, rt, rt });
            if (fireballs) {
            	debug(new Exception(),"unlimited fireballs");
                this.game.mario.unlimitedFireballs = !this.game.mario.unlimitedFireballs;
                this.game.audio.play(this.game.mario.unlimitedFireballs ? 12 : 17);
            }
        }
        else if (this.cheatCode.size() == 8) {
            final boolean extraLives = this.cheatMatch(codes, new int[] { up, up, dn, dn, up, up, dn, dn });
            if (extraLives) {
            	debug(new Exception(),"extra lives");
                this.extraLivesCheat = !this.extraLivesCheat;
                this.game.audio.play(this.extraLivesCheat ? 12 : 17);
            }else{
            	//System.out.println("test for luigibros");
            	boolean luigiBros = this.cheatMatch(codes, new int[] { up, up, dn, dn, lf, rt, lf, rt} );
            	debug(new Exception(),"luigibros = "+luigiBros);
            	if(luigiBros){
            		toggleLuigiBros();
            		this.game.audio.play(Audio.EXTRA_LIFE);
            	}
            }
        }
        
    }
    
	public void toggleLuigiBros(){
    	Game.instance.luigiBros = !Game.instance.luigiBros;
    	if(Game.instance.luigiBros){
    		debug(new Exception(),"luigibros on");
    		Game.instance.mario.asLuigi = true;
    		this.leftKey = KeyEvent.VK_RIGHT;
    		this.rightKey = KeyEvent.VK_LEFT;
    	}else{
    		debug(new Exception(),"luigibros off");
    		Game.instance.mario.asLuigi = false;
    		this.leftKey = this.defaultLeft;
    		this.rightKey = this.defaultRight;
    	}
    }
    
    private boolean cheatMatch(final Integer[] codes, final int[] keys) {
        if (codes.length != keys.length) {
            return false;
        }
        for (int i = 0; i < codes.length; ++i) {
            if (codes[i] != keys[i]) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public void keyTyped(final KeyEvent e) {
    }
    
    @Override
    public void keyPressed(final KeyEvent e) {
        final int key = e.getKeyCode();
        final boolean upWasPressed = this.upDown;
        final boolean downWasPressed = this.downDown;
        final boolean leftWasPressed = this.leftDown;
        final boolean rightWasPressed = this.rightDown;
        if (!this.game.inputSelection) {
            if (key == 49) {
                this.game.setFrameSize(1);
            }
            else if (key == 50) {
                this.game.setFrameSize(2);
            }
            else if (key == 51) {
                this.game.setFrameSize(3);
            }
            else if (key == 52) {
                this.game.setFrameSize(4);
            }
            else if (key == 53) {
                this.game.setFrameSize(5);
            }
            else if (key == 54) {
                this.game.setFrameSize(6);
            }
            else if (key == 55) {
                this.game.setFrameSize(7);
            }
            else if (key == 56) {
                this.game.setFrameSize(8);
            }
            else if (key == 57) {
                this.game.setFrameSize(9);
            }
            else if (key == 48) {
                if (this.game.isFullScreen) {
                    this.game.switchToWindowed = true;
                }
                else {
                    this.game.switchToFullScreen = true;
                }
            }
            else if (key == 77 && !this.keyIsUsed(key)) {
                if (this.game.audio.isMuted()) {
                    this.game.audio.unmuteAudio();
                }
                else {
                    this.game.audio.muteAudio();
                }
            }
        }
        if (this.game.inputSelection && !this.controllers.gettingControls && key != 27) {
            if (this.game.controllerSelectionIndex == 0 && !this.reservedKeyboardKey(key) && (!this.reservedKey(key) || key == 38)) {
                (this.newControls = new int[7])[0] = key;
                final Game game = this.game;
                ++game.controllerSelectionIndex;
            }
            else if (this.game.controllerSelectionIndex == 1 && !this.reservedKeyboardKey(key) && !this.previouslyEnteredNewControl(key, 1) && (!this.reservedKey(key) || key == 40)) {
                this.newControls[1] = key;
                final Game game2 = this.game;
                ++game2.controllerSelectionIndex;
            }
            else if (this.game.controllerSelectionIndex == 2 && !this.reservedKeyboardKey(key) && !this.previouslyEnteredNewControl(key, 2) && (!this.reservedKey(key) || key == 37)) {
                this.newControls[2] = key;
                final Game game3 = this.game;
                ++game3.controllerSelectionIndex;
            }
            else if (this.game.controllerSelectionIndex == 3 && !this.reservedKeyboardKey(key) && !this.previouslyEnteredNewControl(key, 3) && (!this.reservedKey(key) || key == 39)) {
                this.newControls[3] = key;
                final Game game4 = this.game;
                ++game4.controllerSelectionIndex;
            }
            else if (this.game.controllerSelectionIndex == 4 && !this.reservedKeyboardKey(key) && !this.previouslyEnteredNewControl(key, 4) && !this.reservedKey(key)) {
                this.newControls[4] = key;
                final Game game5 = this.game;
                ++game5.controllerSelectionIndex;
            }
            else if (this.game.controllerSelectionIndex == 5 && !this.reservedKeyboardKey(key) && !this.previouslyEnteredNewControl(key, 5) && !this.reservedKey(key)) {
                this.newControls[5] = key;
                final Game game6 = this.game;
                ++game6.controllerSelectionIndex;
            }
            else if (this.game.controllerSelectionIndex == 6 && !this.reservedKeyboardKey(key) && !this.previouslyEnteredNewControl(key, 6) && (!this.reservedKey(key) || key == 10)) {
                this.newControls[6] = key;
                this.setControls(this.newControls);
                this.controllers.inUse = false;
                this.game.inputSelection = false;
            }
            else {
                this.game.audio.play(8);
            }
            return;
        }
        if (this.game.inputSelection && key == 27 && this.escapeReleased) {
            this.cancelControlSetting();
            return;
        }
        if (!this.game.inputSelection && this.game.settingOptions && key == 27 && this.escapeReleased) {
            this.escapeReleased = false;
            if (this.game.getGameState() == 1) {
                this.game.level.resume(true);
            }
            this.game.settingOptions = false;
            return;
        }
        if (key == 27 && this.escapeReleased) {
            this.escapeReleased = false;
            if (this.game.getGameState() == 1) {
                if (this.game.level.paused) {
                    this.game.level.resume(true);
                }
                else {
                    this.game.level.pause();
                }
            }
            else if (this.game.getGameState() != 0) {
                if (this.game.getGameState() == 2) {
                    this.game.skipTransition();
                }
            }
        }
        if (key == this.leftKey) {
            this.leftDown = true;
        }
        else if (key == this.rightKey) {
            this.rightDown = true;
        }
        else if (key == this.upKey) {
            this.upDown = true;
            if (key == 38) {
                this.defUpDown = true;
            }
        }
        else if (key == this.downKey) {
            this.downDown = true;
            if (key == 40) {
                this.defDownDown = true;
            }
        }
        else if (key == this.jumpKey) {
            this.jumpDown = true;
        }
        else if (key == this.runKey) {
            this.runDown = true;
        }
        else if (key == this.startKey || key == 10) {
            this.startDown = true;
            if (key == 10) {
                this.defStartDown = true;
            }
        }
        if (this.game.getGameState() == 1 && key == 112 && (!this.game.takingSnapshot || this.game.snapshotTrans < 0.5f)) {
            this.game.takeSnapshot = true;
        }
        if (this.game.getGameState() == 0 && !this.game.inputSelection && ((!upWasPressed && this.upDown) || (!downWasPressed && this.downDown) || (!leftWasPressed && this.leftDown) || (!rightWasPressed && this.rightDown))) {
            this.addCheatKey(key);
        }
    }
    
    @Override
    public void keyReleased(final KeyEvent e) {
        final int key = e.getKeyCode();
        if (key == this.leftKey) {
            this.leftDown = false;
        }
        else if (key == this.rightKey ) {
            this.rightDown = false;
        }
        else if (key == this.upKey ) {
            this.upDown = false;
            if (key == 38) {
                this.defUpDown = false;
            }
        }
        else if (key == this.downKey) {
            this.downDown = false;
            if (key == 40) {
                this.defDownDown = false;
            }
        }
        else if (key == this.jumpKey) {
            this.jumpDown = false;
        }
        else if (key == this.runKey) {
            this.runDown = false;
        }
        else if (key == this.startKey || key == 10) {
            this.startDown = false;
            if (key == 10) {
                this.defStartDown = false;
            }
        }
        else if (key == 27) {
            this.escapeReleased = true;
        }
    }
    
    private boolean reservedKey(final int key) {
        return key == 38 || key == 40 || key == 37 || key == 39 || key == 10;
    }
    
    public int promptForControllers() {
        final String[] deviceList = this.controllers.getDevicesList();
        final String answer = (String)JOptionPane.showInputDialog(this.game, "Select an input device:", "Game Controller", -1, null, deviceList, "Keyboard");
        if (answer == null) {
            return -1;
        }
        for (int i = 0; i < deviceList.length; ++i) {
            if (answer.equals(deviceList[i])) {
                return i;
            }
        }
        return -1;
    }
}
