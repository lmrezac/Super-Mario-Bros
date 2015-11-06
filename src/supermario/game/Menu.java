// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import sun.awt.image.ToolkitImage;
import supermario.Utilities;

public final class Menu
{
    private Game game;
    private Textures textures;
    public int selectorIndex;
    public int mouseHoveredIndex;
    public static final int NO_OPTION_HOVERED_BY_MOUSE = -1;
    public static final int PLAY_A_GAME_OPTION = 0;
    public static final int MAKE_A_GAME_OPTION = 1;
    public static final int SETTINGS_OPTION = 2;
    public static final int EXIT_OPTION = 3;
    public static final int BACK_OPTION = 4;
    public static final int FULLSCREEN_OPTION = 5;
    public static final int SET_CONTROLS_OPTION = 6;
    public static final int MUTE_OPTION = 7;
    public static final int TOGGLE_PLAYER_OPTION = 8;
    public static final int ABOUT_OPTION = 9;
    public boolean enterReleased;
    public boolean upReleased;
    public boolean downReleased;
    private static final String author;
    private static final String disclaimer;
    private static final String play;
    private static final String play1Game;
    private static final String play2Game;
    private static final String make;
    private static final String settings;
    private static final String exit;
    public static final String back;
    public static final String resume;
    public static final String fullScreen;
    public static final String windowed;
    public static final String controls;
    public static final String mute;
    public static final String unmute;
    public static final String noAudio;
    public static final String retryLvl;
    public static final String retryCkPt;
    public static final String quitTest;
    public static final String quitGame;
    public static final String pressUp;
    public static final String pressDown;
    public static final String pressLeft;
    public static final String pressRight;
    public static final String pressB;
    public static final String pressA;
    public static final String pressStart;
    public static final String pressCancel;
    public static final String vol;
    
    public Menu(final Game game) {
        this.game = game;
        this.textures = game.textures;
        this.mouseHoveredIndex = -1;
    }
    
    public void update(final double delta) {
        if (!this.enterReleased && !this.game.input.startDown) {
            this.enterReleased = true;
        }
        if (!this.upReleased && !this.game.input.upDown) {
            this.upReleased = true;
        }
        if (!this.downReleased && !this.game.input.downDown) {
            this.downReleased = true;
        }
        if (!this.game.inputSelection && this.enterReleased && !this.game.settingOptions && !this.game.input.promptingForController) {
            if (this.game.input.startDown && this.selectorIndex == 0) {
                this.game.playAGame();
            }
            else if (this.game.input.startDown && this.selectorIndex == 1) {
                if (this.game.isAutoStartBuild()) {
                    this.game.mario.asLuigi = true;
                    this.game.playAGame();
                }
                else {
                    this.game.switchToBuilder();
                }
            }
            else if (this.game.input.startDown && this.selectorIndex == 2) {
                this.game.settingOptions(true);
                this.enterReleased = false;
            }
            else if (this.game.input.startDown && this.selectorIndex == 3) {
                System.exit(0);
            }
            else if (this.game.input.upDown && !this.game.input.downDown && this.upReleased) {
                --this.selectorIndex;
                this.upReleased = false;
            }
            else if (this.game.input.downDown && !this.game.input.upDown && this.downReleased) {
                ++this.selectorIndex;
                this.downReleased = false;
            }
            if (this.selectorIndex < 0) {
                this.selectorIndex = 0;
            }
            else if (this.selectorIndex > 3) {
                this.selectorIndex = 3;
            }
        }
    }
    
    public void draw(final Graphics2D g2D) {
        g2D.setColor(this.textures.skyBlue);
        g2D.fillRect(0, 0, Game.renderWidth, Game.renderHeight);
        Utilities.drawAtTile(g2D, this.game.luigiBros? this.textures.title2.getImage() : this.textures.title.getImage(), 5, 3);
        if (this.game.inputSelection) {
            String text = "Keyboard:";
            if (this.game.input.controllers.gettingControls) {
                text = this.game.input.controllers.getDevicesList()[this.game.input.controllers.selectedDevice + 1] + ":";
            }
            final int width = Math.round(text.length() * 8);
            int xStartPos = Game.renderWidth / 2 - width / 2;
            if (xStartPos < 0) {
                xStartPos = 0;
            }
            Utilities.drawTextAtPixels(g2D, text, xStartPos, 120, this.textures.pink, 1.0f);
        }
        else if (this.game.isAutoStartBuild()) {
            Utilities.drawTextAtTiles(g2D, Menu.disclaimer, 13, 14, this.textures.pink, 1.0f);
        }
        else {
            Utilities.drawTextAtTiles(g2D, Menu.author, 10, 14, this.textures.pink, 1.0f);
        }
        Utilities.drawAtTile(g2D, this.textures.bigHill.getImage(), 0, Game.yTiles - 8);
        Utilities.drawAtTile(g2D, this.textures.tripleBush.getImage(), 23, Game.yTiles - 5);
        for (int i = 0; i < this.game.getWidth() / 8 + 1; ++i) {
            Utilities.drawAtTile(g2D, this.textures.lightGround.getImage(), i * 2, Game.yTiles - 3);
            Utilities.drawAtTile(g2D, this.textures.lightGround.getImage(), i * 2, Game.yTiles - 1);
        }
        Image image = (Image)this.textures.marioSmStand.getImage();
        if (this.game.mario.asLuigi || game.luigiBros) {
            image =  this.textures.luigiSmStand.getImage();
        }
        if(Game.instance.luigiBros){
        	image.getWidth(null);
        	//image = Utilities.horizontalFlip(image);
        	BufferedImage bimg = Utilities.horizontalFlip(image);
        	image = (Image)bimg;
        	//image = new ToolkitImage(bimg.getSource());
        }
        int width = (int)g2D.getDeviceConfiguration().getBounds().getWidth();
        g2D.drawImage(image, Game.instance.luigiBros? width-40 : 40, 168 + this.game.mario.avoidedCollisionRowsOnBottom, null);
        if (!this.game.settingOptions) {
            final int selectorPos = 17 + 2 * this.selectorIndex;
            g2D.drawImage(this.textures.selector.getImage(), 72, 8 * selectorPos, null);
            if (this.game.isAutoStartBuild()) {
                Utilities.drawTextAtTiles(g2D, Menu.play1Game, 11, 17);
                Utilities.drawTextAtTiles(g2D, Menu.play2Game, 11, 19);
            }
            else {
                Utilities.drawTextAtTiles(g2D, Menu.play, 11, 17);
                Utilities.drawTextAtTiles(g2D, Menu.make, 11, 19);
            }
            Utilities.drawTextAtTiles(g2D, Menu.settings, 11, 21);
            Utilities.drawTextAtTiles(g2D, Menu.exit, 11, 23);
        }
        if (this.mouseHoveredIndex != -1) {
            final Composite oldComposite = g2D.getComposite();
            g2D.setComposite(AlphaComposite.getInstance(3, 0.5f));
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2D.setColor(Color.WHITE);
            if (this.mouseHoveredIndex == 0 || this.mouseHoveredIndex == 4) {
                g2D.fillRoundRect(80, 136, 120, 8, 8, 8);
            }
            else if (this.mouseHoveredIndex == 1 || this.mouseHoveredIndex == 5) {
                g2D.fillRoundRect(80, 152, 120, 8, 8, 8);
            }
            else if (this.mouseHoveredIndex == 2 || this.mouseHoveredIndex == 6) {
                g2D.fillRoundRect(80, 168, 120, 8, 8, 8);
            }
            else if (this.mouseHoveredIndex == 3 || (this.mouseHoveredIndex == 7 && this.game.audio.isValid())) {
                final int unmuteExtraTiles = (this.game.audio.isMuted() && this.game.settingOptions) ? 2 : 0;
                g2D.fillRoundRect(80, 184, (6 + unmuteExtraTiles) * 8, 8, 8, 8);
            }
            else if (this.mouseHoveredIndex == 8 && !this.game.isAutoStartBuild()) {
                g2D.fillRoundRect(40, 185, 16, 16, 8, 8);
            }
            else if (this.mouseHoveredIndex == 9) {
                g2D.fillRoundRect(100, 112, 120, 8, 8, 8);
            }
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            g2D.setComposite(oldComposite);
        }
    }
    
    public int handleMainMenuMouse(final Point mousePos) {
        if (this.game.getGameState() != 0 || this.game.inputSelection) {
            return -1;
        }
        final int width = this.game.getWidth();
        final int height = this.game.getHeight();
        double scaleFactor;
        int barWidth;
        int barHeight;
        if (width > height) {
            scaleFactor = height / Game.renderHeight;
            barWidth = (int)Math.round((width - Game.renderWidth * scaleFactor) / 2.0);
            barHeight = 0;
        }
        else {
            scaleFactor = width / Game.renderWidth;
            barHeight = (int)Math.round((height - Game.renderHeight * scaleFactor) / 2.0);
            barWidth = 0;
        }
        final int unmuteExtraTiles = (this.game.audio.isMuted() && this.game.settingOptions) ? 2 : 0;
        final Rectangle firstOption = new Rectangle((int)Math.round(barWidth + 88.0 * scaleFactor), (int)Math.round(barHeight + 132.0 * scaleFactor), (int)Math.round(104.0 * scaleFactor), (int)Math.round(8.0 * scaleFactor * 2.0));
        final Rectangle secondOption = new Rectangle((int)Math.round(barWidth + 88.0 * scaleFactor), (int)Math.round(barHeight + 148.0 * scaleFactor), (int)Math.round(104.0 * scaleFactor), (int)Math.round(8.0 * scaleFactor * 2.0));
        final Rectangle thirdOption = new Rectangle((int)Math.round(barWidth + 88.0 * scaleFactor), (int)Math.round(barHeight + 164.0 * scaleFactor), (int)Math.round(104.0 * scaleFactor), (int)Math.round(8.0 * scaleFactor * 2.0));
        final Rectangle fourthOption = new Rectangle((int)Math.round(barWidth + 88.0 * scaleFactor), (int)Math.round(barHeight + 180.0 * scaleFactor), (int)Math.round((4 + unmuteExtraTiles) * 8 * scaleFactor), (int)Math.round(8.0 * scaleFactor * 2.0));
        final Rectangle playerOption = new Rectangle((int)Math.round(barWidth + 40.0 * scaleFactor), (int)Math.round(barHeight + 184.0 * scaleFactor), (int)Math.round(16.0 * scaleFactor), (int)Math.round(8.0 * scaleFactor * 2.0));
        final Rectangle aboutOption = new Rectangle((int)Math.round(barWidth + 104.0 * scaleFactor), (int)Math.round(barHeight + 112.0 * scaleFactor), (int)Math.round(112.0 * scaleFactor), (int)Math.round(8.0 * scaleFactor * 1.0));
        final Rectangle mouseRect = new Rectangle(mousePos.x, mousePos.y, 1, 1);
        if (mouseRect.intersects(firstOption)) {
            if (!this.game.settingOptions) {
                return 0;
            }
            return 4;
        }
        else if (mouseRect.intersects(secondOption)) {
            if (!this.game.settingOptions) {
                return 1;
            }
            return 5;
        }
        else if (mouseRect.intersects(thirdOption)) {
            if (!this.game.settingOptions) {
                return 2;
            }
            return 6;
        }
        else if (mouseRect.intersects(fourthOption)) {
            if (!this.game.settingOptions) {
                return 3;
            }
            return 7;
        }
        else {
            if (mouseRect.intersects(playerOption) && !this.game.isAutoStartBuild()) {
                return 8;
            }
            if (mouseRect.intersects(aboutOption)) {
                return 9;
            }
            return -1;
        }
    }
    
    static {
        author = "BY ANDREW & RICK ";//String.valueOf(new char[] { 'B', 'Y', ' ', 'A', 'N', 'D', 'R', 'E', 'W', ' ', 'K', 'E', 'L', 'L', 'O', 'G', 'G' });
        disclaimer = "©1985 NINTENDO";//String.valueOf(new char[] { '©', '1', '9', '8', '5', ' ', 'N', 'I', 'N', 'T', 'E', 'N', 'D', 'O' });
        play = "PLAY A GAME";//String.valueOf(new char[] { 'P', 'L', 'A', 'Y', ' ', 'A', ' ', 'G', 'A', 'M', 'E' });
        play1Game = "1 PLAYER";//String.valueOf(new char[] { 'M', 'A', 'R', 'I', 'O', ' ', 'G', 'A', 'M', 'E' });
        play2Game = "2 PLAYER";//String.valueOf(new char[] { 'L', 'U', 'I', 'G', 'I', ' ', 'G', 'A', 'M', 'E' });
        make = "MAKE A GAME";//String.valueOf(new char[] { 'M', 'A', 'K', 'E', ' ', 'A', ' ', 'G', 'A', 'M', 'E' });
        settings = "SETTINGS";//String.valueOf(new char[] { 'S', 'E', 'T', 'T', 'I', 'N', 'G', 'S' });
        exit = "EXIT";//String.valueOf(new char[] { 'E', 'X', 'I', 'T' });
        back = "BACK";//String.valueOf(new char[] { 'B', 'A', 'C', 'K' });
        resume = "RESUME GAME"; //String.valueOf(new char[] { 'R', 'E', 'S', 'U', 'M', 'E', ' ', 'G', 'A', 'M', 'E' });
        fullScreen = "GO FULLSCREEN"; //String.valueOf(new char[] { 'G', 'O', ' ', 'F', 'U', 'L', 'L', 'S', 'C', 'R', 'E', 'E', 'N' });
        windowed = "GO WINDOWED"; //String.valueOf(new char[] { 'G', 'O', ' ', 'W', 'I', 'N', 'D', 'O', 'W', 'E', 'D' });
        controls = "SET CONTROLS"; //String.valueOf(new char[] { 'S', 'E', 'T', ' ', 'C', 'O', 'N', 'T', 'R', 'O', 'L', 'S' });
        mute = "MUTE"; //String.valueOf(new char[] { 'M', 'U', 'T', 'E' });
        unmute = "UNMUTE"; //String.valueOf(new char[] { 'U', 'N', 'M', 'U', 'T', 'E' });
        noAudio = "(NO AUDIO)"; //String.valueOf(new char[] { '(', 'N', 'O', ' ', 'A', 'U', 'D', 'I', 'O', ')' });
        retryLvl = "RETRY LVL"; //String.valueOf(new char[] { 'R', 'E', 'T', 'R', 'Y', ' ', 'L', 'V', 'L' });
        retryCkPt = "RETRY AT CHK PT"; //String.valueOf(new char[] { 'R', 'E', 'T', 'R', 'Y', ' ', 'A', 'T', ' ', 'C', 'H', 'K', ' ', 'P', 'T' });
        quitTest = "QUIT TEST"; //String.valueOf(new char[] { 'Q', 'U', 'I', 'T', ' ', 'T', 'E', 'S', 'T' });
        quitGame = "QUIT GAME"; //String.valueOf(new char[] { 'Q', 'U', 'I', 'T', ' ', 'G', 'A', 'M', 'E' });
        pressUp = "PRESS UP"; //String.valueOf(new char[] { 'P', 'R', 'E', 'S', 'S', ' ', 'U', 'P' });
        pressDown = "PRESS DOWN"; //String.valueOf(new char[] { 'P', 'R', 'E', 'S', 'S', ' ', 'D', 'O', 'W', 'N' });
        pressLeft = "PRESS LEFT"; //String.valueOf(new char[] { 'P', 'R', 'E', 'S', 'S', ' ', 'L', 'E', 'F', 'T' });
        pressRight = "PRESS RIGHT"; //String.valueOf(new char[] { 'P', 'R', 'E', 'S', 'S', ' ', 'R', 'I', 'G', 'H', 'T' });
        pressB = "PRESS B (RUN)";//String.valueOf(new char[] { 'P', 'R', 'E', 'S', 'S', ' ', 'B', ' ', '(', 'R', 'U', 'N', ')' });
        pressA = "PRESS A (JUMP)";//String.valueOf(new char[] { 'P', 'R', 'E', 'S', 'S', ' ', 'A', ' ', '(', 'J', 'U', 'M', 'P', ')' });
        pressStart = "PRESS START";//String.valueOf(new char[] { 'P', 'R', 'E', 'S', 'S', ' ', 'S', 'T', 'A', 'R', 'T' });
        pressCancel = "ESCAPE TO CANCEL"; //String.valueOf(new char[] { 'E', 'S', 'C', 'A', 'P', 'E', ' ', 'T', 'O', ' ', 'C', 'A', 'N', 'C', 'E', 'L' });
        vol = "VOL";//String.valueOf(new char[] { 'V', 'O', 'L' });
    }
}
