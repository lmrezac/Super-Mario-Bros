// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game;

import static supermario.debug.Debugger.debug;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import supermario.Utilities;
import supermario.builder.BuilderFrame;
import supermario.builder.IO;
import supermario.game.sprites.Mario;
import supermario.game.sprites.misc.OverlayCoin;

public final class Game extends Canvas implements WindowListener, DropTargetListener, MouseListener, MouseMotionListener{
	private static final long serialVersionUID = 8174028778971010514L;
	public static Game instance;
	public static final String VERSION = "7.52";
	public static final String MOD_VERSION = "3.0";
	public static final String LATEST_DATE = "11/5/2015";
	public static int xTiles;
	public static int yTiles;
	public static int overlayXOffset;
	public static int overlayYOffset;
	public static final int TILE_SIZE = 8;
	public static final int BLOCK_SIZE = 16;
	public static int renderWidth;
	public static int renderHeight;
	public static final double initialRatio;
	public static final int DEFAULT_SCALE_FACTOR = 2;
	public static final String ENCODING;
	public static final int DEFAULT_LIVES = 3;
	public static final int DEFAULT_STARTING_LEVEL = 0;
	public static final int STATE_MENU = 0;
	public static final int STATE_LEVEL = 1;
	public static final int STATE_TRANSITION = 2;
	private Frame frame;
	private DropTarget dropTarget;
	private BufferedImage bufImage;
	private BufferedImage bufImageBW;
	public final Audio audio;
	public Textures textures;
	public TexturePacks texturePacks;
	private Runtime runtime;
	public Menu menu;
	public GameLoader gameLoader;
	public Level level;
	private Transition transition;
	public Input input;
	public JFileChooser fileChooser;
	public static final FileNameExtensionFilter gameFilter;
	public static final FileNameExtensionFilter programFilter;
	public static final FileNameExtensionFilter zipFilter;
	public Mario mario;
	private int gameState;
	private OverlayCoin overlayCoin;
	private Dimension canvasSize;
	private Insets border;
	public boolean switchToFullScreen;
	public boolean switchToWindowed;
	public boolean isFullScreen;
	public boolean inputSelection;
	public int controllerSelectionIndex;
	public int optionsSelectorIndex;
	public boolean settingOptions;
	public boolean enterReleased;
	public boolean upHold;
	public boolean downHold;
	public boolean leftHold;
	public boolean rightHold;
	public boolean fromMenu;
	private static final String FRAME_TITLE;
	public boolean testMode;
	public BuilderFrame builderFrame;
	private int lastScaleFactor;
	public String lastGameName;
	private File autostartGame;
	private boolean embeddedAutostart;
	public static final String AUTOSTART_NAME = "autostart.mario";
	public Random rand;
	public boolean takeSnapshot;
	public boolean takingSnapshot;
	public float snapshotTrans;
	public boolean luigiBros = false;
	public boolean sponge = false;

	public Game(final int scaleFactor){
		Game.instance = this;
		this.lastScaleFactor = 2;
		this.rand = new Random();

		final long startTime = System.nanoTime();
		System.setProperty("sun.awt.noerasebackground", "true");
		this.runtime = new Runtime(this);
		this.audio = new Audio(this);
		this.input = new Input(this);
		(this.frame = new Frame(Game.FRAME_TITLE)).setIgnoreRepaint(true);
		this.frame.add(this);
		try{
			UIManager.put("FileChooser.readOnly", Boolean.TRUE);
			Utilities.setDefaultLookAndFeel();
		}catch(Exception e){
			e.printStackTrace(System.out);
		}
		this.initFileChooser();
		this.frame.addKeyListener(this.input);
		this.addKeyListener(this.input);
		this.frame.addWindowListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.dropTarget = new DropTarget(this.frame, this);
		this.frame.setFocusable(true);
		this.setFocusable(true);
		this.setIgnoreRepaint(true);
		Utilities.setIcon(this.frame);
		this.frame.pack();
		this.border = this.frame.getInsets();
		this.frame.setMinimumSize(new Dimension(Game.renderWidth+this.border.left+this.border.right, Game.renderHeight+this.border.top+this.border.bottom));
		this.bufImage = new BufferedImage(Game.renderWidth, Game.renderHeight, 1);
		this.bufImageBW = new BufferedImage(Game.renderWidth, Game.renderHeight, 11);
		this.canvasSize = this.getSize();
		this.setBackground(Color.BLACK);
		this.frame.setSize(Game.renderWidth*scaleFactor+this.border.left+this.border.right, Game.renderHeight*scaleFactor+this.border.top+this.border.bottom);
		this.textures = new Textures();
		this.texturePacks = new TexturePacks(this.textures);
		if(!this.textures.validTextures||!this.texturePacks.validTextures){
			JOptionPane.showMessageDialog(null, "There was a problem loading the game's textures...", "Initialization Error", 0);
			System.exit(0);
		}
		Utilities.textures = this.textures;
		this.transition = new Transition(this);
		this.mario = new Mario(this, this.textures.getMarioTextures(), this.textures.getLuigiTextures());
		(this.overlayCoin = new OverlayCoin(this, this.textures.getOverlayCoinTextures())).setTileXY(Game.overlayXOffset+11, 2);
		this.menu = new Menu(this);
		this.gameState = 0;
		this.checkAutoStart();
		synchronized(this.audio){
			if(!this.audio.doneLoading()){
				try{
					this.audio.wait();
				}catch(InterruptedException ex){}
			}
			this.audio.notify();
		}
		Utilities.centerOnDisplay(this.frame);
		final long currentTime = System.nanoTime();
		final long minMS = 1500L;
		if(currentTime-startTime<minMS*1000000L){
			final long sleepTime = minMS*1000000L-(currentTime-startTime);
			try{
				Thread.sleep(sleepTime/1000000L);
			}catch(InterruptedException ex2){}
		}
		this.runtime.start();
		while(!audio.doneLoading()){}
		// state0(7);
		// audio.playMusic(Audio.MUSIC_OVERWORLD);
		int i = rand.nextInt(100);
		if(i==50){
			this.input.toggleLuigiBros();
			// System.out.println("luigibros == true");
		}
		this.frame.setVisible(true);
		debug(new Exception(), "Size = "+Game.renderWidth+", "+Game.renderHeight);
	}

	private void checkAutoStart(){
		try{
			final String filePath = URLDecoder.decode(this.getClass().getResource("autostart.mario").getFile(), Game.ENCODING);
			this.autostartGame = new File(filePath);
			this.embeddedAutostart = true;
		}catch(NullPointerException|UnsupportedEncodingException e){
			try{
				final String filePath = URLDecoder.decode(this.input.jarDirectory+"/"+"autostart.mario", Game.ENCODING);
				this.autostartGame = new File(filePath);
				if(!this.autostartGame.exists()){
					this.autostartGame = null;
					return;
				}
				this.embeddedAutostart = false;
			}catch(NullPointerException ex){}catch(UnsupportedEncodingException ex){}
		}
	}

	public boolean isAutoStartBuild(){
		return this.autostartGame!=null;
	}

	public static void main(final String[] args){
		debug(new Exception(), "Startup!");
		new Game(2);
	}

	private void initFileChooser(){
		(this.fileChooser = new JFileChooser()).resetChoosableFileFilters();
		this.fileChooser.setAcceptAllFileFilterUsed(false);
		this.fileChooser.addChoosableFileFilter(Game.gameFilter);
		this.fileChooser.setMultiSelectionEnabled(false);
		this.fileChooser.validate();
	}

	public void switchToBuilder(){
		if(this.isFullScreen){
			this.switchToWindowed(this.lastScaleFactor);
		}
		this.setLastScaleFactor();
		this.runtime.pause();
		this.input.loseAllKeyPresses();
		final Point gamePos = this.getLocationOnScreen();
		this.builderFrame = new BuilderFrame(this, gamePos);
		this.frame.setVisible(false);
	}

	public void switchFromBuilder(final Point builderPos, final Dimension builderSize){
		this.changeToMenu();
		this.runtime.unpause();
		final int width = Game.renderWidth*this.lastScaleFactor+this.border.left+this.border.right;
		final int height = Game.renderHeight*this.lastScaleFactor+this.border.top+this.border.bottom;
		this.frame.setSize(width, height);
		if(builderPos!=null&&builderSize!=null){
			Utilities.centerOnPoint(this.frame, new Point(builderPos.x+builderSize.width/2, builderPos.y+builderSize.height/2));
		}
		this.frame.setVisible(true);
		Utilities.correctScreenLocation(this.frame);
		this.builderFrame.dispose();
		this.builderFrame = null;
	}

	public void testBuilderLevel(final Point builderPos, final Dimension builderSize, final IO.GameData gameData){
		final int width = Game.renderWidth*this.lastScaleFactor+this.border.left+this.border.right;
		final int height = Game.renderHeight*this.lastScaleFactor+this.border.top+this.border.bottom;
		this.frame.setSize(width, height);
		Utilities.centerOnPoint(this.frame, new Point(builderPos.x+builderSize.width/2, builderPos.y+builderSize.height/2));
		this.frame.setVisible(true);
		Utilities.correctScreenLocation(this.frame);
		try{
			this.testMode = true;
			this.mario.godMode = this.input.godModeTesting;
			this.mario.unlimitedFireballs = this.input.unlimitedFireballsTesting;
			this.lastGameName = gameData.filename;
			this.gameLoader = new GameLoader(gameData);
			final int startingLives = this.mario.godMode? 99 : 3;
			this.mario.prepareForGameStart(startingLives, gameData.startingLevel);
			this.gameState = 0;
			this.runtime.unpause();
			this.input.loseAllKeyPresses();
			this.changeToTransition(this.mario.levelRestartNumber, 0, true, false);
			this.setFrameTitle();
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.frame, "There was a problem initializing the test level.", "Test Level Error", 0);
			this.endBuilderLevelTest();
		}
	}

	private void endBuilderLevelTest(){
		if(this.isFullScreen){
			this.switchToWindowed(this.lastScaleFactor);
		}
		this.audio.stopAllSounds();
		this.testMode = false;
		this.mario.godMode = false;
		this.mario.powerBounceMode = false;
		this.mario.unlimitedFireballs = false;
		this.settingOptions = false;
		this.input.cancelControlSetting();
		this.setLastScaleFactor();
		this.runtime.pause();
		this.builderFrame.setVisible(true);
		this.builderFrame.setPosition(this.getLocationOnScreen());
		this.frame.setVisible(false);
	}

	private void setFrameTitle(){
		if(this.gameState==0||this.isAutoStartBuild()){
			this.frame.setTitle(Game.FRAME_TITLE);
		}else{
			this.frame.setTitle(Game.FRAME_TITLE+" - "+this.lastGameName);
		}
	}

	private void switchToFullScreen(){
		this.setLastScaleFactor();
		final Rectangle fullSize = Utilities.getDisplayBounds(Utilities.getDisplayNumber(this.frame));
		this.frame.removeKeyListener(this.input);
		this.frame.removeWindowListener(this);
		this.dropTarget.removeDropTargetListener(this);
		this.frame.remove(this);
		this.frame.removeAll();
		this.frame.dispose();
		this.frame = new Frame(Game.FRAME_TITLE);
		this.setFrameTitle();
		this.frame.setUndecorated(true);
		this.frame.setAlwaysOnTop(true);
		this.frame.setIgnoreRepaint(true);
		this.frame.add(this);
		this.frame.addKeyListener(this.input);
		this.frame.addWindowListener(this);
		this.frame.setFocusable(true);
		Dimension oldsize = this.getSize();
		int newwidth = oldsize.width * fullSize.height / oldsize.height;
		this.frame.setSize(newwidth, fullSize.height);
		this.frame.setLocation(fullSize.x+(fullSize.width-newwidth)/2, fullSize.y);
		this.frame.setVisible(true);
		Utilities.setIcon(this.frame);
		this.frame.toFront();
		this.switchToFullScreen = false;
		this.switchToWindowed = false;
		this.isFullScreen = true;
	}

	private void switchToWindowed(final int scaleFactor){
		final Rectangle fullSize = Utilities.getDisplayBounds(Utilities.getDisplayNumber(this.frame));
		this.frame.removeKeyListener(this.input);
		this.frame.removeWindowListener(this);
		this.frame.remove(this);
		this.frame.removeAll();
		this.frame.dispose();
		(this.frame = new Frame(Game.FRAME_TITLE)).setIgnoreRepaint(true);
		this.frame.add(this);
		this.frame.addKeyListener(this.input);
		this.frame.addWindowListener(this);
		try{
			this.dropTarget.setComponent(this.frame);
			this.dropTarget.addDropTargetListener(this);
		}catch(Exception e){
			e.printStackTrace();
		}
		this.frame.setFocusable(true);
		this.setFocusable(true);
		Utilities.setIcon(this.frame);
		this.frame.setMinimumSize(new Dimension(Game.renderWidth+this.border.left+this.border.right, Game.renderHeight+this.border.top+this.border.bottom));
		this.isFullScreen = false;
		this.switchToWindowed = false;
		this.switchToFullScreen = false;
		this.frame.setSize(Game.renderWidth*scaleFactor+this.border.left+this.border.right, Game.renderHeight*scaleFactor+this.border.top+this.border.bottom);
		this.frame.setLocation(fullSize.x+fullSize.width/2-this.frame.getWidth()/2, fullSize.y+fullSize.height/2-this.frame.getHeight()/2);
		this.setFrameTitle();
		this.frame.setVisible(true);
		if(!this.canFitScaledFrameSize(scaleFactor)){
			this.frame.setExtendedState(6);
		}
	}

	public void setFrameSize(final int scaleFactor){
		if(this.isFullScreen){
			this.switchToWindowed(scaleFactor);
		}else if(!this.canFitScaledFrameSize(scaleFactor)){
			this.frame.setExtendedState(6);
		}else{
			this.lastScaleFactor = scaleFactor;
			final int newWidth = this.border.left+Game.renderWidth*scaleFactor+this.border.right;
			final int newHeight = this.border.top+Game.renderHeight*scaleFactor+this.border.bottom;
			final Point center = Utilities.getFrameCenter(this.frame);
			this.frame.setSize(newWidth, newHeight);
			Utilities.centerOnPoint(this.frame, center);
			Utilities.correctScreenLocation(this.frame);
		}
	}

	private boolean canFitScaledFrameSize(final int scaleFactor){
		final Rectangle displayBounds = Utilities.getDisplayBounds(Utilities.getDisplayNumber(this.frame));
		return Game.xTiles*8*scaleFactor<=displayBounds.width&&Game.yTiles*8*scaleFactor<=displayBounds.height;
	}

	private void setLastScaleFactor(){
		if(this.getWidth()<this.getHeight()){
			this.lastScaleFactor = (int)Math.round(this.getWidth()/Game.renderWidth);
		}else{
			this.lastScaleFactor = (int)Math.round(this.getHeight()/Game.renderHeight);
		}
		if(!this.canFitScaledFrameSize(this.lastScaleFactor)){
			--this.lastScaleFactor;
		}
	}

	public void update(final double delta){

		Utilities.updatePulsingImageIndex(delta);
		if(this.input.controllers.valid&&(this.input.controllers.inUse||this.input.controllers.gettingControls)){
			this.input.controllers.pollDevices();
		}
		if(!this.enterReleased&&!this.input.startDown){
			this.enterReleased = true;
		}
		if(this.leftHold&&!this.input.leftDown){
			this.leftHold = false;
		}
		if(this.rightHold&&!this.input.rightDown){
			this.rightHold = false;
		}
		if(this.gameState==0){
			this.menu.update(delta);
		}else if(this.gameState==1){
			if(!this.level.paused){
				if(this.level.canUpdateInCurrentState()){
					Utilities.updatePeriodicTheta(delta);
					Utilities.updateOscillatingPlatformPeriods(delta);
				}
				Utilities.updateFireballImageIndex(delta);
			}
			this.level.update(delta);
		}else if(this.gameState==2){
			this.transition.update(delta);
		}
		if(this.gameState!=1||!this.level.paused){
			this.overlayCoin.update(delta);
		}
		if(this.takingSnapshot){
			this.snapshotTrans -= (float)(delta/1000.0/0.5);
			if(this.snapshotTrans<=0.0f){
				this.takingSnapshot = false;
			}
		}
	}

	public void setOverlayCoinLockedState(final boolean state){
		this.overlayCoin.locked = state;
	}

	@Override
	public void paint(final Graphics g){
		if(g==null){
			return;
		}
		if(this.switchToFullScreen&&!this.isFullScreen){
			this.switchToFullScreen();
			return;
		}
		if(this.switchToWindowed&&this.isFullScreen){
			this.switchToWindowed(this.lastScaleFactor);
			return;
		}
		final Graphics2D finalG = (Graphics2D)g;
		BufferedImage usedImage;
		if(this.gameState==1&&this.level.blackAndWhite){
			usedImage = this.bufImageBW;
		}else{
			usedImage = this.bufImage;
		}
		final Graphics2D g2D = usedImage.createGraphics();
		if(this.gameState==0){
			this.menu.draw(g2D);
		}else if(this.gameState==1){
			this.level.draw(g2D);
		}else if(this.gameState==2){
			this.transition.draw(g2D);
		}
		this.drawOverlay(g2D);
		if(this.settingOptions){
			this.settingsDisplay(g2D);
			if(this.gameState==1){
				this.level.drawMouseOption(g2D);
			}
		}
		if(this.inputSelection){
			this.inputSelection(g2D);
		}
		if(this.takingSnapshot){
			g2D.setColor(Color.WHITE);
			g2D.setComposite(AlphaComposite.getInstance(3, this.snapshotTrans));
			g2D.fillRect(0, 0, Game.renderWidth, Game.renderHeight);
		}
		final int panelWidth = this.getWidth();
		final int panelHeight = this.getHeight();
		final double windowRatio = panelWidth/panelHeight;
		// usedImage = Utilities.horizontalFlip(usedImage);
		if(windowRatio>Game.initialRatio){
			final int scaledWidth = Game.renderWidth* panelHeight/Game.renderHeight;
			final int imageX = panelWidth/2-scaledWidth/2;
			finalG.drawImage(usedImage, imageX, 0, scaledWidth, panelHeight, null);
			this.checkBackgroundRedraw(finalG, panelWidth, panelHeight, imageX, 0);
			finalG.setColor(this.textures.offBlack);
			finalG.fillRect(imageX-1, 0, 1, panelHeight);
			finalG.fillRect(imageX+scaledWidth, 0, 1, panelHeight);
		}else{
			final int scaledHeight = Game.renderHeight*panelWidth/Game.renderWidth;
			final int imageY = panelHeight/2-scaledHeight/2;
			finalG.drawImage(usedImage, 0, imageY, panelWidth, scaledHeight, null);
			this.checkBackgroundRedraw(finalG, panelWidth, panelHeight, 0, imageY);
		}

		g.dispose();
	}

	private void checkBackgroundRedraw(final Graphics2D finalG, final int panelWidth, final int panelHeight, final int x, final int y){
		if(this.isFullScreen||panelWidth!=this.canvasSize.width||panelHeight!=this.canvasSize.height){
			finalG.setColor(Color.BLACK);
			if(x==0){
				finalG.fillRect(0, 0, panelWidth, y);
				finalG.fillRect(0, panelHeight-y, panelWidth, y);
			}else{
				finalG.fillRect(0, 0, x, panelHeight);
				finalG.fillRect(panelWidth-x, 0, x, panelHeight);
			}
			this.canvasSize = this.getSize();
		}
	}

	public void gettingInput(){
		this.menu.mouseHoveredIndex = -1;
		this.controllerSelectionIndex = 0;
		if(this.input.controllers.valid){
			this.input.promptingForController = true;
			final int device = this.input.promptForControllers();
			this.input.promptingForController = false;
			if(device==-1){
				return;
			}
			if(device!=0){
				this.input.controllers.storeControls();
				this.input.controllers.clearControls();
				this.input.controllers.getControls(device-1);
			}
		}
		this.inputSelection = true;
	}

	private void inputSelection(final Graphics2D g2D){
		final AffineTransform oldTransform = g2D.getTransform();
		final AffineTransform identity = new AffineTransform();
		identity.setToIdentity();
		g2D.setTransform(identity);
		final int controllerOffset = 24;
		if(!this.fromMenu){
			g2D.translate(0, controllerOffset);
		}else{
			g2D.setColor(this.textures.skyBlue);
			g2D.fillRect(40, 24, this.textures.controller.getIconWidth(), this.textures.controller.getIconHeight());
		}
		Utilities.drawAtTile(g2D, this.textures.controller.getImage(), 5, 3);
		if(!this.fromMenu){
			g2D.translate(0, -controllerOffset);
		}
		float composite = 1.0f;
		final int pulsingIndex = Utilities.getPulsingImageIndex();
		if(pulsingIndex==0){
			composite = 0.7f;
		}else if(pulsingIndex==1){
			composite = 0.5f;
		}else if(pulsingIndex==2){
			composite = 0.2f;
		}
		final String up = Menu.pressUp;
		final String down = Menu.pressDown;
		final String left = Menu.pressLeft;
		final String right = Menu.pressRight;
		final String a = Menu.pressA;
		final String b = Menu.pressB;
		final String start = Menu.pressStart;
		final String escape = Menu.pressCancel;
		final float textComposite = composite+0.2f;
		final int keyYTile = 18;
		if(this.controllerSelectionIndex==0){
			Utilities.drawTextAtPixels(g2D, up, Game.renderWidth/2-32, keyYTile*8, Color.YELLOW, textComposite);
		}else if(this.controllerSelectionIndex==1){
			Utilities.drawTextAtPixels(g2D, down, Game.renderWidth/2-40, keyYTile*8, Color.YELLOW, textComposite);
		}else if(this.controllerSelectionIndex==2){
			Utilities.drawTextAtPixels(g2D, left, Game.renderWidth/2-40, keyYTile*8, Color.YELLOW, textComposite);
		}else if(this.controllerSelectionIndex==3){
			Utilities.drawTextAtPixels(g2D, right, Game.renderWidth/2-44, keyYTile*8, Color.YELLOW, textComposite);
		}else if(this.controllerSelectionIndex==4){
			Utilities.drawTextAtPixels(g2D, b, Game.renderWidth/2-56, keyYTile*8, Color.YELLOW, textComposite);
		}else if(this.controllerSelectionIndex==5){
			Utilities.drawTextAtPixels(g2D, a, Game.renderWidth/2-52, keyYTile*8, Color.YELLOW, textComposite);
		}else if(this.controllerSelectionIndex==6){
			Utilities.drawTextAtPixels(g2D, start, Game.renderWidth/2-44, keyYTile*8, Color.YELLOW, textComposite);
		}
		Utilities.drawTextAtPixels(g2D, escape, Game.renderWidth/2-64, 168);
		final Composite oldComposite = g2D.getComposite();
		g2D.setColor(Color.YELLOW);
		g2D.setComposite(AlphaComposite.getInstance(3, composite));
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(!this.fromMenu){
			g2D.translate(0, controllerOffset);
		}
		if(this.controllerSelectionIndex==0){
			g2D.fillOval(65, 59, 11, 12);
		}else if(this.controllerSelectionIndex==1){
			g2D.fillOval(65, 78, 11, 12);
		}else if(this.controllerSelectionIndex==2){
			g2D.fillOval(55, 69, 13, 10);
		}else if(this.controllerSelectionIndex==3){
			g2D.fillOval(74, 69, 13, 10);
		}else if(this.controllerSelectionIndex==4){
			g2D.fillOval(158, 74, 17, 17);
		}else if(this.controllerSelectionIndex==5){
			g2D.fillOval(181, 74, 17, 17);
		}else if(this.controllerSelectionIndex==6){
			g2D.fillOval(124, 80, 18, 7);
		}
		if(!this.fromMenu){
			g2D.translate(0, -controllerOffset);
		}
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		g2D.setComposite(oldComposite);
		g2D.setTransform(oldTransform);
	}

	private boolean canResume(){
		return this.gameState!=1||this.level.timeSincePausePress>=100.0;
	}

	public void settingOptions(final boolean fromMenu){
		this.fromMenu = fromMenu;
		this.settingOptions = true;
		this.upHold = true;
		this.downHold = true;
		this.optionsSelectorIndex = 0;
		this.enterReleased = false;
	}

	private void settingsDisplay(final Graphics2D g2D){
		if(!this.inputSelection&&!this.input.promptingForController&&this.enterReleased&&!this.leftHold&&!this.rightHold){
			if(this.upHold&&!this.input.upDown){
				this.upHold = false;
			}else if(this.downHold&&!this.input.downDown){
				this.downHold = false;
			}else if(this.input.startDown&&this.optionsSelectorIndex==0&&this.canResume()){
				this.settingOptions = false;
				if(!this.fromMenu){
					this.level.resume(true);
				}else{
					this.menu.upReleased = false;
					this.menu.downReleased = false;
					this.changeToMenu();
				}
			}else if(this.input.startDown&&this.optionsSelectorIndex==1){
				if(this.isFullScreen){
					this.switchToWindowed = true;
				}else{
					this.switchToFullScreen = true;
				}
				this.enterReleased = false;
			}else if(this.input.startDown&&this.optionsSelectorIndex==2){
				this.enterReleased = false;
				SwingUtilities.invokeLater(new Runnable(){
					@Override
					public void run(){
						Game.this.gettingInput();
					}
				});
			}else if(this.input.startDown&&this.optionsSelectorIndex==3&&this.audio.isValid()){
				if(this.audio.isMuted()){
					this.audio.unmuteAudio();
				}else{
					this.audio.muteAudio();
				}
				this.enterReleased = false;
			}else if(this.input.leftDown&&!this.input.rightDown&&this.optionsSelectorIndex==3&&this.audio.isValid()){
				if(!this.audio.isMuted()){
					this.audio.setVolume((Math.round(this.audio.getVolume()*10.0)-1L)/10.0f);
				}
				this.leftHold = true;
			}else if(this.input.rightDown&&!this.input.leftDown&&this.optionsSelectorIndex==3&&this.audio.isValid()){
				if(this.audio.getVolume()!=1.0){
					this.audio.setVolume((Math.round(this.audio.getVolume()*10.0)+1L)/10.0f);
				}
				this.rightHold = true;
			}else if(this.input.startDown&&this.optionsSelectorIndex==4&&this.canResume()){
				if(this.mario.lives>1){
					this.settingOptions = false;
					this.level.resume(false);
					if(!this.mario.isDead()){
						this.mario.checkpoint = null;
						this.mario.died(false, false);
					}
				}else{
					this.audio.play(8);
					this.enterReleased = false;
				}
			}else if(this.input.startDown&&this.optionsSelectorIndex==5&&this.canResume()){
				if(this.mario.lives>1&&this.mario.checkpoint!=null){
					this.settingOptions = false;
					this.level.resume(false);
					if(!this.mario.isDead()){
						this.mario.died(false, false);
					}
				}else{
					this.audio.play(8);
					this.enterReleased = false;
				}
			}else if(this.input.startDown&&this.optionsSelectorIndex==6){
				this.settingOptions = false;
				this.audio.stopAllSounds();
				Utilities.resetPulsingImageIndex();
				this.changeToMenu();
			}else if(this.input.upDown&&!this.input.downDown&&!this.upHold){
				--this.optionsSelectorIndex;
				this.upHold = true;
			}else if(this.input.downDown&&!this.input.upDown&&!this.downHold){
				++this.optionsSelectorIndex;
				this.downHold = true;
			}
			if(this.optionsSelectorIndex<0){
				this.optionsSelectorIndex = 0;
			}else if(!this.fromMenu&&this.optionsSelectorIndex>6){
				this.optionsSelectorIndex = 6;
			}else if(this.fromMenu&&this.optionsSelectorIndex>3){
				this.optionsSelectorIndex = 3;
			}
		}
		g2D.setColor(Color.BLACK);
		if(!this.inputSelection){
			String backResume = Menu.back;
			if(!this.fromMenu){
				backResume = Menu.resume;
				g2D.translate(0, -84);
				final Composite oldComposite = g2D.getComposite();
				g2D.setComposite(AlphaComposite.getInstance(3, 0.4f));
				g2D.fillRect(30, 135, Game.xTiles*8-52, 120);
				g2D.setComposite(oldComposite);
				g2D.translate(0, 8);
			}
			final int selectorPos = 17+2*this.optionsSelectorIndex;
			g2D.drawImage(this.textures.selector.getImage(), 72, 8*selectorPos, null);
			Utilities.drawTextAtTiles(g2D, backResume, 11, 17);
			if(this.isFullScreen){
				Utilities.drawTextAtTiles(g2D, Menu.windowed, 11, 19);
			}else{
				Utilities.drawTextAtTiles(g2D, Menu.fullScreen, 11, 19);
			}
			Utilities.drawTextAtTiles(g2D, Menu.controls, 11, 21);
			if(!this.audio.isValid()){
				Utilities.drawTextAtTiles(g2D, Menu.noAudio, 11, 23);
			}else{
				if(this.audio.isMuted()){
					Utilities.drawTextAtTiles(g2D, Menu.unmute, 11, 23);
				}else{
					Utilities.drawTextAtTiles(g2D, Menu.mute, 11, 23);
				}
				final int volumeSize = 40;
				final int volumeUsed = Math.round(Math.round(this.audio.getVolume()*10.0)/10.0f*volumeSize);
				final Composite oldComposite2 = g2D.getComposite();
				g2D.setComposite(AlphaComposite.getInstance(3, 0.5f));
				if(this.getGameState()==1&&this.level!=null&&this.level.getBackgroundColor()==this.textures.black){
					g2D.setColor(this.textures.waterBlue);
				}else{
					g2D.setColor(this.textures.offBlack);
				}
				g2D.fillRect(144, 183, volumeUsed, 9);
				g2D.setComposite(oldComposite2);
				g2D.setColor(this.textures.brickGray);
				g2D.drawRect(144, 182, volumeSize, 10);
				Utilities.drawAtTile(g2D, this.textures.symbols.get('\uffff').getImage(), 18, 23);
				Utilities.drawTextAtTiles(g2D, Menu.vol, 19, 23);
				Utilities.drawAtTile(g2D, this.textures.symbols.get('\ufffe').getImage(), 22, 23);
			}
			if(!this.fromMenu){
				this.drawLives(g2D);
				Utilities.drawTextAtTiles(g2D, Menu.retryLvl, 11, 25);
				Utilities.drawTextAtTiles(g2D, Menu.retryCkPt, 11, 27);
				g2D.setColor(this.textures.offBlack);
				if(this.mario.lives<=1){
					g2D.fillRect(83, 203, 77, 1);
					g2D.fillRect(83, 219, 128, 1);
				}else if(this.mario.checkpoint==null){
					g2D.fillRect(83, 219, 128, 1);
				}
				if(this.testMode){
					Utilities.drawTextAtTiles(g2D, Menu.quitTest, 11, 29);
				}else{
					Utilities.drawTextAtTiles(g2D, Menu.quitGame, 11, 29);
				}
				g2D.translate(0, 82);
			}
		}
	}

	private void drawLives(final Graphics2D g2D){
		final int xLoc = 21;
		final int yLoc = 25;
		Image image = this.textures.marioSmStand.getImage();
		if(this.mario.asLuigi){
			image = this.textures.luigiSmStand.getImage();
		}
		g2D.drawImage(image, (xLoc+1)*8, (yLoc-3)*8+4, null);
		Utilities.drawAtTile(g2D, this.textures.symbols.get('\ufffd').getImage(), xLoc+3, yLoc);
		String spaces = "    ";
		if(this.mario.lives>=10){
			spaces = "   ";
		}
		Utilities.drawTextAtTiles(g2D, "("+spaces+this.mario.lives+")", xLoc, yLoc);
	}

	public void drawOverlay(final Graphics2D g2D){
		final AffineTransform transform = g2D.getTransform();
		transform.setToIdentity();
		g2D.setTransform(transform);
		String name;

		if(this.mario.asLuigi || luigiBros)
			name = sponge? "PRETZEL" : "LUIGI";
		else name = sponge? "SPONGE" : "MARIO";
		
		Utilities.drawTextAtTiles(g2D, name, Game.overlayXOffset+3, Game.overlayYOffset+1);

		if(this.gameState==0){
			Utilities.drawTextAtTiles(g2D, "WORLD", Game.overlayXOffset+18, 1);
			Utilities.drawTextAtTiles(g2D, "1-1", Game.overlayXOffset+19, 2);
		}else{
			Utilities.drawTextAtPixels(g2D, this.mario.levelNamePart1, (Game.overlayXOffset+20)*8-this.mario.levelNamePart1.length()*8/2, 8);
			Utilities.drawTextAtPixels(g2D, this.mario.levelNamePart2, (Game.overlayXOffset+20)*8-this.mario.levelNamePart2.length()*8/2, 16);
		}
		Utilities.drawTextAtTiles(g2D, "TIME", Game.overlayXOffset+25, 1);
		String timeString;
		if((this.gameState==1||this.gameState==2)&&this.mario.timedLevel){
			int timeLeft = this.mario.getTimeLeft();
			if(timeLeft<0){
				timeLeft = 0;
			}
			timeString = String.valueOf(timeLeft);
		}else{
			timeString = String.valueOf((int)this.mario.totalTime);
		}
		while(timeString.length()<4){
			timeString = "0"+timeString;
		}
		if(this.gameState!=0){
			Utilities.drawTextAtTiles(g2D, timeString, Game.overlayXOffset+32-timeString.length()-3, 2);
		}
		if(this.mario.points>999999){
			this.mario.points = 999999;
		}
		String pointsString;
		for(pointsString = String.valueOf(this.mario.points); pointsString.length()<6; pointsString = "0"+pointsString){}
		Utilities.drawTextAtTiles(g2D, pointsString, Game.overlayXOffset+3, 2);
		this.overlayCoin.draw(g2D);
		g2D.drawImage(this.textures.symbols.get('\ufffd').getImage(), (Game.overlayXOffset+12)*8, 16, null);
		if(this.gameState==1||this.gameState==2){
			int coins = this.mario.coins;
			if(coins>99){
				coins = 99;
			}
			String coinString = String.valueOf(coins);
			if(coinString.length()==1){
				coinString = "0"+coinString;
			}
			Utilities.drawTextAtTiles(g2D, coinString, Game.overlayXOffset+13, 2);
		}else{
			Utilities.drawTextAtTiles(g2D, "00", Game.overlayXOffset+13, 2);
		}
	}

	boolean trans = false;

	public void changeToTransition(final int levelNumber, final int warpID, final boolean levelStart, final boolean freshRun){
		// audio.stopMusic(false);

		trans = true;
		if(levelNumber>=this.gameLoader.levelCount()){
			this.transition.load(true, false, false, false, 0);
			this.gameState = 2;
		}else{
			boolean tentativeAnimate = false;
			boolean blueSky = false;
			boolean largeCastle = false;
			final int texturePack = this.texturePacks.getTexturePack();
			if(freshRun&&this.level!=null&&(this.level.levelType==0||this.level.levelType==4)){
				tentativeAnimate = true;
				if(this.level.levelType==0){
					blueSky = true;
				}
				if(this.level.caughtBigCastleFlag){
					largeCastle = true;
				}else if(this.level.caughtSmallCastleFlag){
					largeCastle = false;
				}
			}
			this.level = new Level(this, levelNumber, warpID);
			final int errorCode = this.level.getErrorCode();
			if(errorCode==-1){
				this.overlayCoin.locked = true;
				if(levelStart){
					this.mario.totalTime = 0.0;
					if(warpID!=-1){
						this.mario.levelNamePart1 = this.level.levelNamePart1;
						this.mario.levelNamePart2 = this.level.levelNamePart2;
						this.mario.levelTime = this.level.levelTime;
						this.mario.timedLevel = this.level.timedLevel;
					}
					boolean shouldAnimate = false;
					if(tentativeAnimate&&(this.level.levelType==1||this.level.levelType==3)){
						shouldAnimate = true;
					}
					this.transition.load(false, shouldAnimate, blueSky, largeCastle, texturePack);
				}else{
					this.transition.blankForQuickDelay();
				}
				this.gameState = 2;
			}else{
				// TODO huh
				JOptionPane.showMessageDialog(this, "The level \""+levelNumber+"\" could not be loaded succesfully.\r\nIt could be corrupted.\r\n"+"Ensure you have the latest version of the game.\r\nError Code: "+errorCode, "Level Error", 0);
				this.changeToMenu();
			}
		}
	}

	public void changeToLevel(){
		// audio.stopAllSounds();
		this.overlayCoin.locked = false;
		this.level.prepare();
		this.level.update(1.0);
		this.mario.flip = false;
		this.gameState = 1;
	}

	public void changeToMenu(){
		if(this.texturePacks.getTexturePack()!=0){
			this.texturePacks.setTexturePack(0);
		}
		// state0(7);
		this.mario.points = 0;
		this.mario.reset(true);
		this.settingOptions = false;
		this.inputSelection = false;
		this.overlayCoin.locked = false;
		this.menu.enterReleased = false;
		this.menu.mouseHoveredIndex = -1;
		if(this.testMode){
			this.endBuilderLevelTest();
		}else{
			this.gameState = 0;
		}
		this.setFrameTitle();

		// this.audio.stopAllSounds();

		// if(this.audio.isMuted())System.out.println("audio is muted");
		// this.audio.loopMusic(8, false);
		// audio.playMusic(8);
		// this.audio.play(8);
	}

	public void playAGame(){
		if(this.isAutoStartBuild()){
			try{
				if(this.embeddedAutostart){
					final File tempFile = File.createTempFile("autostart", ".mario");
					final InputStream inputStream = this.getClass().getResourceAsStream("autostart.mario");
					final BufferedInputStream bIn = new BufferedInputStream(inputStream);
					final BufferedOutputStream bOut = new BufferedOutputStream(new FileOutputStream(tempFile));
					for(int b = bIn.read(); b!=-1; b = bIn.read()){
						bOut.write(b);
					}
					bIn.close();
					bOut.close();
					this.loadGame(tempFile.getAbsoluteFile());
					tempFile.delete();
				}else{
					this.loadGame(this.autostartGame.getAbsoluteFile());
				}
			}catch(Exception e){
				e.printStackTrace(System.out);
			}
		}else{
			this.fileChooser.setCurrentDirectory(new File(this.input.getSessionDirectory()));
			this.fileChooser.setDialogTitle(String.valueOf(new char[]{'C', 'h', 'o', 'o', 's', 'e', ' ', 'a', ' ', 'M', 'a', 'r', 'i', 'o', ' ', 'G', 'a', 'm', 'e' }));
			this.fileChooser.setApproveButtonText(String.valueOf(new char[]{'L', 'o', 'a', 'd' }));
			int returnVal = 0;
			returnVal = this.fileChooser.showOpenDialog(this);
			if(returnVal==0){
				final File file = this.fileChooser.getSelectedFile().getAbsoluteFile();
				this.input.setSessionDirectory("\\"+file.getParent()+"\\");
				this.loadGame(file);
			}
			this.input.loseAllKeyPresses();
		}
	}

	private void loadGame(final File file){
		final String filePath = file.getPath();
		this.mario.prepareForGameStart(this.input.extraLivesCheat? 99 : 3, 0);
		this.gameLoader = new GameLoader(filePath);
		this.enterReleased = true;
		if(this.gameLoader.validGame()){
			this.lastGameName = file.getName().substring(0, file.getName().length()-".mario".length());
			this.changeToTransition(this.mario.levelRestartNumber, 0, true, false);
			this.setFrameTitle();
		}else{
			JOptionPane.showMessageDialog(this, "The game could not be loaded succesfully.\r\nIt may not be a mario game, it may not exist, or it could be corrupted.", "Game Error", 0);
		}
	}

	public void skipTransition(){
		this.transition.skip();
	}

	public int getGameState(){
		return this.gameState;
	}

	public void showCursor(){
		this.input.lastMouseEvent = System.nanoTime();
		if(this.frame.getCursor().getType()!=0){
			this.frame.setCursor(Utilities.defaultCursor);
		}
	}

	public void hideCursor(){
		if(!this.frame.getCursor().getName().equals(Utilities.BLANK_CURSOR_DESCRIPTION)){
			this.frame.setCursor(Utilities.blankCursor);
		}
	}

	@Override
	public void windowOpened(final WindowEvent e){}

	@Override
	public void windowClosing(final WindowEvent e){
		if(this.testMode){
			this.endBuilderLevelTest();
		}else{
			this.frame.setVisible(false);
			this.runtime.stopGameLoop();
			this.audio.dispose();
			System.exit(0);
		}
	}

	@Override
	public void windowClosed(final WindowEvent e){}

	@Override
	public void windowIconified(final WindowEvent e){}

	@Override
	public void windowDeiconified(final WindowEvent e){}

	@Override
	public void windowActivated(final WindowEvent e){}

	@Override
	public void windowDeactivated(final WindowEvent e){
		if(this.input!=null){
			this.input.loseAllKeyPresses();
		}
	}

	@Override
	public void dragEnter(final DropTargetDragEvent dtde){}

	@Override
	public void dragOver(final DropTargetDragEvent dtde){}

	@Override
	public void dropActionChanged(final DropTargetDragEvent dtde){}

	@Override
	public void dragExit(final DropTargetEvent dte){}

	@Override
	public void drop(final DropTargetDropEvent dtde){
		if(this.gameState!=0||this.inputSelection){
			dtde.rejectDrop();
			return;
		}
		this.settingOptions = false;
		dtde.acceptDrop(1);
		final Transferable transferable = dtde.getTransferable();
		final DataFlavor[] flavors = transferable.getTransferDataFlavors();
		for(int i = 0; i<flavors.length; ++i){
			if(flavors[i].isFlavorJavaFileListType()){
				try{
					final Object data = transferable.getTransferData(flavors[i]);
					if(data instanceof List){
						final List<?> files = (List<?>)data;
						if(files.size()==1){
							final String path = files.get(0).toString();
							final String ext = ".mario";
							if(path.length()>ext.length()&&path.endsWith(ext)){
								this.loadGame((File)files.get(0));
								dtde.dropComplete(true);
								return;
							}
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	private void state0(int option){

		if(option==0){
			if(this.isAutoStartBuild()){
				this.mario.asLuigi = false;
			}
			this.playAGame();
			this.menu.selectorIndex = 0;
		}else if(option==1){
			if(this.isAutoStartBuild()){
				this.mario.asLuigi = true;
				this.playAGame();
			}else{
				this.switchToBuilder();
				this.menu.selectorIndex = 1;
			}
		}else if(option==2){
			this.settingOptions(true);
		}else if(option==3){
			System.exit(0);
		}else if(option==4){
			this.changeToMenu();
			this.menu.selectorIndex = 2;
		}else if(option==5){
			if(this.isFullScreen){
				this.switchToWindowed = true;
			}else{
				this.switchToFullScreen = true;
			}
			this.optionsSelectorIndex = 1;
			this.menu.mouseHoveredIndex = -1;
		}else if(option==6){
			this.menu.mouseHoveredIndex = -1;
			this.gettingInput();
			this.optionsSelectorIndex = 2;
		}else if(option==7&&this.audio.isValid()){
			if(this.audio.isMuted()){
				this.audio.unmuteAudio();

			}else{
				this.audio.muteAudio();
				muted = true;
			}
		}else if(option==8&&!this.isAutoStartBuild()){
			this.mario.asLuigi = !this.mario.asLuigi;
		}else if(option==9){
			Utilities.showAbout(this);
		}
	}

	@Override
	public void mouseClicked(final MouseEvent e){}

	@Override
	public void mousePressed(final MouseEvent e){
		if(this.gameState==0){
			final int option = this.menu.handleMainMenuMouse(e.getPoint());
			if(option==-1){
				if(this.testMode&&this.gameState==1&&!this.mario.transitioning&&!this.level.paused){
					final boolean leftClick = e.getButton()==1;
					final boolean rightClick = e.getButton()==3;
					if(leftClick||rightClick){
						this.builderFrame.itemInjector.ItemInjection(e.getPoint(), leftClick? 0 : 1, false);
					}
				}
			}else
				state0(option);
		}else if(this.gameState==1&&this.level.paused){
			final int option = this.level.handleMenuMouse(e.getPoint());
			if(option==Level.MOUSE_OPTION_RESUME){
				this.settingOptions = false;
				this.level.resume(true);
			}else if(option==Level.MOUSE_OPTION_FULLSCREEN){
				if(this.isFullScreen){
					this.switchToWindowed = true;
				}else{
					this.switchToFullScreen = true;
				}
				this.level.mouseHoveredIndex = Level.MOUSE_OPTION_NONE;
			}else if(option==Level.MOUSE_OPTION_CONTROLS){
				this.level.mouseHoveredIndex = Level.MOUSE_OPTION_NONE;
				this.gettingInput();
			}else if(option==Level.MOUSE_OPTION_MUTE){
				if(this.audio.isMuted()){
					this.audio.unmuteAudio();
				}else{
					this.audio.muteAudio();
				}
			}else if(option==Level.MOUSE_OPTION_RETRY_LVL){
				if(this.mario.lives>1){
					this.settingOptions = false;
					this.level.resume(false);
					if(!this.mario.isDead()){
						this.mario.checkpoint = null;
						this.mario.died(false, false);
					}
				}else{
					this.audio.play(8);
					this.enterReleased = false;
				}
			}else if(option==Level.MOUSE_OPTION_RETRY_CHK){
				if(this.mario.lives>1&&this.mario.checkpoint!=null){
					this.settingOptions = false;
					this.level.resume(false);
					if(!this.mario.isDead()){
						this.mario.died(false, false);
					}
				}else{
					this.audio.play(8);
					this.enterReleased = false;
				}
			}else if(option==Level.MOUSE_OPTION_QUIT){
				this.settingOptions = false;
				this.audio.stopAllSounds();
				Utilities.resetPulsingImageIndex();
				this.changeToMenu();
			}
		}
		this.showCursor();
	}

	@Override
	public void mouseReleased(final MouseEvent e){}

	@Override
	public void mouseEntered(final MouseEvent e){}

	@Override
	public void mouseExited(final MouseEvent e){}

	@Override
	public void mouseDragged(final MouseEvent e){
		if(this.testMode&&this.gameState==1&&!this.mario.transitioning&&!this.level.paused){
			final boolean leftClick = SwingUtilities.isLeftMouseButton(e);
			final boolean rightClick = SwingUtilities.isRightMouseButton(e);
			if(leftClick||rightClick){
				this.builderFrame.itemInjector.ItemInjection(e.getPoint(), leftClick? 0 : 1, true);
			}
		}
	}

	boolean muted = false;

	@Override
	public void mouseMoved(final MouseEvent e){
		if(this.gameState==0){
			this.menu.mouseHoveredIndex = this.menu.handleMainMenuMouse(e.getPoint());
			if(this.audio.isMuted()&&!muted)
				audio.unmuteAudio();
		}else if(this.gameState==1){
			this.level.mouseHoveredIndex = this.level.handleMenuMouse(e.getPoint());
		}
		this.showCursor();
	}

	static{
		Game.xTiles = 32;
		Game.yTiles = 28;
		Game.overlayXOffset = (Game.xTiles-32)/2;
		Game.overlayYOffset = Game.yTiles-28;
		Game.renderWidth = Game.xTiles*8;
		Game.renderHeight = Game.yTiles*8;
		initialRatio = Game.renderWidth/Game.renderHeight;
		ENCODING = String.valueOf(new char[]{'U', 'T', 'F', '-', '8' });
		gameFilter = new FileNameExtensionFilter(String.valueOf(new char[]{'M', 'a', 'r', 'i', 'o', ' ', 'L', 'e', 'v', 'e', 'l', ' ', '(', '.', 'm', 'a', 'r', 'i', 'o', ')' }), new String[]{String.valueOf(new char[]{'m', 'a', 'r', 'i', 'o' }) });
		programFilter = new FileNameExtensionFilter(String.valueOf(new char[]{'M', 'a', 'r', 'i', 'o', ' ', 'G', 'a', 'm', 'e', ' ', '(', '.', 'j', 'a', 'r', ')' }), new String[]{String.valueOf(new char[]{'j', 'a', 'r' }) });
		zipFilter = new FileNameExtensionFilter(String.valueOf(new char[]{'Z', 'i', 'p' }), new String[]{String.valueOf(new char[]{'z', 'i', 'p' }) });
		FRAME_TITLE = String.valueOf(new char[]{'S', 'u', 'p', 'e', 'r', ' ', 'M', 'a', 'r', 'i', 'o', ' ', 'B', 'r', 'o', 's', '.' });
	}
}
