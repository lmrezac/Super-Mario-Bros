// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game;

import java.util.Iterator;
import java.util.LinkedList;
import supermario.game.interfaces.Warpable;
import supermario.game.sprites.misc.BowserBattle;
import supermario.game.sprites.misc.Flag;
import supermario.game.sprites.misc.Platform;
import supermario.game.sprites.enemies.LavaBall;
import supermario.game.sprites.misc.Firebar;
import supermario.game.sprites.misc.Cannon;
import supermario.game.sprites.enemies.Beetle;
import supermario.game.sprites.enemies.Spiny;
import supermario.game.sprites.enemies.HammerBro;
import supermario.game.sprites.enemies.GrayFish;
import supermario.game.sprites.enemies.RedFish;
import supermario.game.sprites.enemies.Squid;
import supermario.game.sprites.enemies.Koopa;
import supermario.game.sprites.enemies.Goomba;
import supermario.game.sprites.misc.Pipe;
import supermario.game.sprites.blocks.QuestionBox;
import supermario.game.sprites.blocks.Brick;
import supermario.game.sprites.misc.InfiniteCorridor;
import supermario.game.sprites.misc.ThinAirWarp;
import supermario.game.sprites.misc.Spring;
import supermario.game.sprites.misc.ArrivalVine;
import supermario.game.sprites.misc.Checkpoint;
import javax.swing.ImageIcon;
import supermario.game.sprites.friends.Coin;
import java.awt.Point;

public class LevelLoader
{
    private Game game;
    private Textures textures;
    private int levelType;
    private int pipeColor;
    private int levelNumber;
    private int cliffDestLevel;
    private int cliffDestID;
    private Tile[][] levelTiles;
    private boolean[][] collisionArray;
    private Point[][] tileAssociations;
    private Sprite[][] spriteArray;
    private boolean levelLoadedSuccessfully;
    private boolean hasSpinyThrower;
    private boolean hasFlyingFish;
    private boolean hasBulletThrower;
    private boolean hasFlameThrower;
    private boolean isTimedLevel;
    private boolean blackAndWhite;
    private boolean autoScrollingLevel;
    private int lineLength;
    private int maxTravelX;
    private int levelTime;
    private String levelNamePart1;
    private String levelNamePart2;
    public static final int LEVEL_NAME_MAX_CHARS = 8;
    private static final int MARIO_NOT_PLACED = 0;
    private static final int MULTIPLE_MARIOS_PLACED = 1;
    private static final int UNIMPLEMENTED_CHARACTER = 2;
    private static final int MINIMUM_LEVEL_DIMENSIONS_UNMET = 3;
    private static final int UNKNOWN_ERROR = 4;
    private static final int INVALID_LEVEL_LENGTH = 5;
    private static final int LEVEL_TYPE_CHARACTER_INVALID = 6;
    private static final int UNDEFINED_CHARACTER = 7;
    private static final int MULTIPLE_SPRITES_IN_TILE = 8;
    private static final int INVALID_FOLLOWING_CHARACTER = 9;
    private static final int NOT_ENOUGH_FLAG_ROOM = 10;
    private static final int FLAG_NOT_ON_LINE_ZERO = 11;
    private static final int FLAG_NOT_AT_LEVEL_END = 12;
    private static final int SPINY_THROWER_IN_WATER_LEVEL = 13;
    private static final int FLYING_FISH_IN_WATER_LEVEL = 14;
    public static final int INVALID_WARP_ID = 15;
    private static final int WARP_DISTRIBUTION_FAILURE = 16;
    private static final int NO_ERROR = -1;
    private int errorCode;
    public static final char SINGLE_CLOUD = '«';
    public static final char DOUBLE_CLOUD = '©';
    public static final char TRIPLE_CLOUD = '¦';
    public static final char SINGLE_BUSH = '¥';
    public static final char DOUBLE_BUSH = '¤';
    public static final char TRIPLE_BUSH = '£';
    public static final char TALL_TRIMMED_BUSH = '¢';
    public static final char SHORT_TRIMMED_BUSH = '¡';
    public static final char SHORT_SNOWY_BUSH = '\u2192';
    public static final char TALL_SNOWY_BUSH = '\u2190';
    public static final char PICKET_FENCE = 'i';
    public static final char CASTLE_WALL_SECTION = 'a';
    public static final char SMALL_HILL = 'b';
    public static final char BIG_HILL = 'c';
    public static final char SMALL_CASTLE = 'd';
    public static final char BIG_CASTLE = 'e';
    public static final char TREE_BARK = 'f';
    public static final char MUSHROOM_BARK = '\u2660';
    public static final char LAVA_TOP = 'g';
    public static final char LAVA_BOTTOM = 'µ';
    public static final char WATER_TOP = '¾';
    public static final char WATER_BOTTOM = '\u00e5';
    public static final char WARP_ZONE_MESSAGE = '\u00f1';
    public static final char BRIDGE_CHAIN = '\u00fd';
    public static final char SPRING = 'j';
    public static final char MARIO_STARTING_POSITION = 'k';
    public static final char MARIO_THIN_AIR_WARP = '½';
    public static final char INFINITE_CORRIDOR = '\u00f7';
    public static final char COIN = 'h';
    public static final char CHECKPOINT = '\u0292';
    public static final char BEANSTALK_ARRIVAL = '\u03e4';
    public static final char LIGHT_GROUND = 'l';
    public static final char DARK_GROUND = 'm';
    public static final char STONE_GROUND = 'n';
    public static final char SEA_GROUND = 'o';
    public static final char SEA_STONE = '§';
    public static final char CLOUD_GROUND = 'p';
    public static final char LIGHT_BLOCK = 'q';
    public static final char DARK_BLOCK = 'r';
    public static final char STONE_BLOCK = '\u045e';
    public static final char LIGHT_METAL = 's';
    public static final char DARK_METAL = 't';
    public static final char STONE_METAL = 'u';
    public static final char TREE_TOP_LEFT_END = 'v';
    public static final char TREE_TOP_RIGHT_END = 'w';
    public static final char TREE_TOP_MIDDLE_SECTION = 'y';
    public static final char MUSHROOM_TOP_LEFT_END = '\u25cb';
    public static final char MUSHROOM_TOP_RIGHT_END = '\u25d9';
    public static final char MUSHROOM_TOP_MIDDLE_SECTION = '\u2642';
    public static final char MUSHROOM_BARK_TOP = '\u2193';
    public static final char CORAL = 'z';
    public static final char BRIDGE = '\u25b2';
    public static final char BOWSER_BRIDGE = '\u00e6';
    public static final char LIGHT_BRICK_NOTHING = 'A';
    public static final char LIGHT_BRICK_SINGLE_COIN = 'B';
    public static final char LIGHT_BRICK_COINS = 'C';
    public static final char LIGHT_BRICK_GROW = 'D';
    public static final char LIGHT_BRICK_LIFE = 'E';
    public static final char LIGHT_BRICK_STAR = 'F';
    public static final char LIGHT_BRICK_BEANSTALK = 'G';
    public static final char DARK_BRICK_NOTHING = 'H';
    public static final char DARK_BRICK_SINGLE_COIN = 'I';
    public static final char DARK_BRICK_COINS = 'J';
    public static final char DARK_BRICK_GROW = 'K';
    public static final char DARK_BRICK_LIFE = 'L';
    public static final char DARK_BRICK_STAR = 'M';
    public static final char DARK_BRICK_BEANSTALK = 'N';
    public static final char STONE_BRICK_NOTHING = '\u2022';
    public static final char STONE_BRICK_SINGLE_COIN = '\u25d8';
    public static final char STONE_BRICK_COINS = '\u2640';
    public static final char STONE_BRICK_GROW = '\u266b';
    public static final char STONE_BRICK_LIFE = '\u263c';
    public static final char STONE_BRICK_STAR = '\u25ba';
    public static final char STONE_BRICK_BEANSTALK = '\u25c4';
    public static final char QUESTION_BOX_COIN = 'O';
    public static final char QUESTION_BOX_GROW = 'P';
    public static final char QUESTION_BOX_LIFE = 'Q';
    public static final char QUESTION_BOX_STAR = 'R';
    public static final char QUESTION_BOX_BEANSTALK = '\u03d8';
    public static final char INVISIBLE_BOX_COIN = 'W';
    public static final char INVISIBLE_BOX_GROW = 'X';
    public static final char INVISIBLE_BOX_LIFE = 'Y';
    public static final char INVISIBLE_BOX_STAR = 'Z';
    public static final char INVISIBLE_BOX_BEANSTALK = '\u03dc';
    public static final char PIPE_SIDE_SECTION = '`';
    public static final char PIPE_TOP_WO_CHOMPER = '~';
    public static final char PIPE_TOP_W_CHOMPER = ';';
    public static final char PIPE_BOTTOM_WO_CHOMPER = '\u0110';
    public static final char PIPE_BOTTOM_W_CHOMPER = '¿';
    public static final char PIPE_LEFT_WO_CHOMPER = '!';
    public static final char PIPE_LEFT_W_CHOMPER = '\u2021';
    public static final char PIPE_RIGHT_WO_CHOMPER = '\u2663';
    public static final char PIPE_RIGHT_W_CHOMPER = '\u00ee';
    public static final char PIPE_TOP_SECTION = '@';
    public static final char PIPE_SIDE_CONNECTOR_LEFT = '#';
    public static final char PIPE_SIDE_CONNECTOR_RIGHT = 'S';
    public static final char PIPE_SIDE_CONNECTOR_DOUBLE = 'T';
    public static final char PIPE_TOP_CONNECTOR = 'U';
    public static final char PIPE_BOTTOM_CONNECTOR = '\u010e';
    public static final char PIPE_WARP_ZONE = '\u0108';
    public static final char LIGHT_GOOMBA = '$';
    public static final char DARK_GOOMBA = '%';
    public static final char GRAY_GOOMBA = '\u02e6';
    public static final char LIGHT_KOOPA = '^';
    public static final char LIGHT_KOOPA_BOUNCING = '&';
    public static final char LIGHT_KOOPA_FLYING_V = '*';
    public static final char LIGHT_KOOPA_FLYING_H = '¬';
    public static final char DARK_KOOPA = '(';
    public static final char DARK_KOOPA_BOUNCING = ')';
    public static final char DARK_KOOPA_FLYING_V = '-';
    public static final char DARK_KOOPA_FLYING_H = '±';
    public static final char RED_KOOPA = '_';
    public static final char RED_KOOPA_BOUNCING = '=';
    public static final char RED_KOOPA_FLYING_V = '+';
    public static final char RED_KOOPA_FLYING_H = '®';
    public static final char SQUID = '[';
    public static final char RED_FISH = '{';
    public static final char GRAY_FISH_STRAIGHT = ']';
    public static final char GRAY_FISH_ZIG_ZAG = '|';
    public static final char LIGHT_HAMMER_THROWER = '}';
    public static final char DARK_HAMMER_THROWER = '\u00f6';
    public static final char SPINY = '\\';
    public static final char LIGHT_BEETLE = ':';
    public static final char DARK_BEETLE = '\u00f4';
    public static final char GRAY_BEETLE = '\u02e7';
    public static final char TALL_CANNON = '\'';
    public static final char CANNON_BASE = '\u260e';
    public static final char SHORT_CANNON = '\"';
    public static final char FIREBAR_SHORT = ',';
    public static final char FIREBAR_LONG = '<';
    public static final char LAVABALL = '.';
    public static final char LONG_PLATFORM_REP_UP = '>';
    public static final char LONG_PLATFORM_REP_DOWN = '/';
    public static final char LONG_PLATFORM_OSC_V = '?';
    public static final char LONG_PLATFORM_OSC_H = '\u263a';
    public static final char LONG_PLATFORM_FALLING = '\u25bc';
    public static final char SHORT_PLATFORM_REP_UP = '\u2194';
    public static final char SHORT_PLATFORM_REP_DOWN = '\u2191';
    public static final char SHORT_PLATFORM_OSC_V = '\u266a';
    public static final char SHORT_PLATFORM_OSC_H = '\u2195';
    public static final char SHORT_PLATFORM_FALLING = '\u203c';
    public static final char EXTRA_SHORT_PLATFORM_REP_UP = '¶';
    public static final char EXTRA_SHORT_PLATFORM_REP_DOWN = 'V';
    public static final char LONG_PLATFORM_PULLEY = '\u25ac';
    public static final char SHORT_PLATFORM_PULLEY = '\u0398';
    public static final char CLOUD_CARRIER_LONG = '\u263b';
    public static final char CLOUD_CARRIER_SHORT = '\u00e7';
    public static final char SOLID_TEST_TILE = '\u0245';
    public static final char FLAG_AND_SMALL_CASTLE = '\u2665';
    public static final char FLAG_AND_BIG_CASTLE = '\u21a8';
    public static final char BOWSER_BATTLE_1 = '\u2666';
    public static final char BOWSER_BATTLE_2 = '¼';
    public static final char BOWSER_BATTLE_3 = '\u00d7';
    public static final char BOWSER_BATTLE_4 = '\u00d8';
    public static final char BOWSER_BATTLE_5 = '\u00de';
    public static final char BOWSER_BATTLE_6 = '\u00df';
    public static final char BOWSER_BATTLE_7 = '\u00f0';
    public static final char BOWSER_BATTLE_8 = '\u00ff';
    public static final char BOWSER_BATTLE_TILES_WIDTH = 'D';
    public static final char FLAG_TILES_WIDTH = '\u001c';
    
    public LevelLoader(final Game game, final char[] chars, final int levelNumber) {
        this.game = game;
        this.levelNumber = levelNumber;
        this.textures = game.textures;
        this.errorCode = -1;
        this.loadLevel(chars);
    }
    
    private void loadLevel(final char[] chars) {
        try {
            this.levelLoadedSuccessfully = false;
            this.getLineLength(chars);
            if (this.lineLength < Game.xTiles) {
                this.errorCode = 3;
                throw new RuntimeException("Level doesn't meet minimum length of " + Game.xTiles);
            }
            this.levelTiles = new Tile[Game.yTiles][this.lineLength];
            this.collisionArray = new boolean[Game.yTiles][this.lineLength];
            this.tileAssociations = new Point[Game.yTiles][this.lineLength];
            this.spriteArray = new Sprite[Game.yTiles][this.lineLength];
            this.interpretChars(chars, this.lineLength);
            this.levelLoadedSuccessfully = true;
        }
        catch (Exception e) {
            e.printStackTrace();
            if (this.errorCode == -1) {
                this.errorCode = 4;
            }
        }
    }
    
    public int getErrorCode() {
        return this.errorCode;
    }
    
    public int getMaxTravelX() {
        return this.maxTravelX;
    }
    
    public Tile[][] getLevelTiles() {
        if (!this.levelLoadedSuccessfully) {
            throw new RuntimeException("Valid level has not been loaded");
        }
        return this.levelTiles;
    }
    
    public int getLevelType() {
        if (!this.levelLoadedSuccessfully) {
            throw new RuntimeException("Valid level has not been loaded");
        }
        return this.levelType;
    }
    
    public int getPipeType() {
        if (!this.levelLoadedSuccessfully) {
            throw new RuntimeException("Valid level has not been loaded");
        }
        return this.pipeColor;
    }
    
    public boolean spinyThrowerPresent() {
        return this.hasSpinyThrower;
    }
    
    public boolean flyingFishPresent() {
        return this.hasFlyingFish;
    }
    
    public boolean bulletThrowerPresent() {
        return this.hasBulletThrower;
    }
    
    public boolean flameThrowerPresent() {
        return this.hasFlameThrower;
    }
    
    public boolean isBlackAndWhite() {
        return this.blackAndWhite;
    }
    
    public boolean isAutoScrolling() {
        return this.autoScrollingLevel;
    }
    
    public int getCliffDestLevel() {
        return this.cliffDestLevel;
    }
    
    public int getCliffDestID() {
        return this.cliffDestID;
    }
    
    public String getLevelNamePart1() {
        return this.levelNamePart1.trim();
    }
    
    public String getLevelNamePart2() {
        return this.levelNamePart2.trim();
    }
    
    public boolean isTimedLevel() {
        return this.isTimedLevel;
    }
    
    public double getLevelTime() {
        return this.levelTime + 0.2;
    }
    
    private void getLineLength(final char[] chars) {
        this.lineLength = -1;
        for (int i = 0; i < chars.length; ++i) {
            if (chars[i] == '\r') {
                this.lineLength = i;
                break;
            }
        }
        if (this.lineLength == -1 || this.lineLength < Game.xTiles) {
            this.errorCode = 5;
            throw new RuntimeException("Line length is invalid");
        }
    }
    
    private void getLevelType(final char[] chars) throws Exception {
        final char levelTypeChar = chars[(this.lineLength + 2) * Game.yTiles];
        if (levelTypeChar == '0') {
            this.levelType = 0;
        }
        else if (levelTypeChar == '1') {
            this.levelType = 1;
        }
        else if (levelTypeChar == '2') {
            this.levelType = 2;
        }
        else if (levelTypeChar == '3') {
            this.levelType = 3;
        }
        else if (levelTypeChar == '4') {
            this.levelType = 4;
        }
        else if (levelTypeChar == '5') {
            this.levelType = 5;
        }
        else {
            if (levelTypeChar != '6') {
                this.errorCode = 6;
                throw new RuntimeException("Level type character is invalid in file");
            }
            this.levelType = 6;
        }
    }
    
    private void getPipeType(final char[] chars) {
        this.pipeColor = 0;
        if (this.levelType == 4 || this.levelType == 2) {
            this.pipeColor = 1;
        }
        else if (this.levelType == 0 || this.levelType == 1 || this.levelType == 5 || this.levelType == 6) {
            this.pipeColor = 0;
        }
        else if (this.levelType == 3) {
            this.pipeColor = 2;
        }
        int pipeOverride;
        try {
            pipeOverride = this.getPipeColor(chars);
        }
        catch (Exception e) {
            pipeOverride = 0;
        }
        if (pipeOverride != 0) {
            if (pipeOverride == 1) {
                this.pipeColor = 0;
            }
            else if (pipeOverride == 2) {
                this.pipeColor = 1;
            }
            else if (pipeOverride == 3) {
                this.pipeColor = 2;
            }
            else if (pipeOverride == 4) {
                this.pipeColor = 3;
            }
        }
    }
    
    private boolean hasSpinyThrower(final char[] chars) {
        final char hasThrowerChar = chars[(this.lineLength + 2) * Game.yTiles + 1];
        if (hasThrowerChar != '1') {
            return false;
        }
        if (this.levelType == 3) {
            this.errorCode = 13;
            throw new RuntimeException("Water levels can't have spiny thrower");
        }
        return true;
    }
    
    private boolean hasFlyingFish(final char[] chars) {
        final char hasFishChar = chars[(this.lineLength + 2) * Game.yTiles + 2];
        if (hasFishChar != '1') {
            return false;
        }
        if (this.levelType == 3) {
            this.errorCode = 14;
            throw new RuntimeException("Water levels can't have flying fish");
        }
        return true;
    }
    
    private boolean hasBulletThrower(final char[] chars) {
        final char hasBulletChar = chars[(this.lineLength + 2) * Game.yTiles + 3];
        return hasBulletChar == '1';
    }
    
    private boolean isBlackAndWhite(final char[] chars) {
        final char blackAndWhiteChar = chars[(this.lineLength + 2) * Game.yTiles + 4];
        return blackAndWhiteChar == '1';
    }
    
    private boolean isAutoScrolling(final char[] chars) {
        try {
            final char as = chars[(this.lineLength + 2) * Game.yTiles + 7 + 8 + 10 + 10 + 3 + 6 + 1];
            return as == '1';
        }
        catch (Exception e) {
            return false;
        }
    }
    
    private int getTexturePack(final char[] chars) {
        char texturePackChar = '0';
        try {
            texturePackChar = chars[(this.lineLength + 2) * Game.yTiles + 7 + 8 + 10 + 10 + 3 + 6 + 2];
        }
        catch (Exception ex) {}
        int i = Integer.parseInt(""+texturePackChar);
        if(i > TexturePacks.HIGHEST_PACK_VALUE)i = 0;
        return i;
    }
    
    private void getCliffDestination(final char[] chars) {
        final char[] destLevel = new char[3];
        final char[] destID = new char[3];
        final int nameIndex = (this.lineLength + 2) * Game.yTiles + 2 + 5;
        for (int x = 0; x < 3; ++x) {
            destLevel[x] = chars[nameIndex + x];
            destID[x] = chars[nameIndex + 3 + x];
        }
        if (destLevel[0] == 'x') {
            this.cliffDestLevel = -1;
        }
        else {
            this.cliffDestLevel = Integer.valueOf(String.valueOf(destLevel));
            this.cliffDestID = Integer.valueOf(String.valueOf(destID));
        }
    }
    
    private void setTimedLevel(final char[] chars) {
        final char timedChar = chars[(this.lineLength + 2) * Game.yTiles + 7 + 8 + 10 + 10];
        if (timedChar == '1') {
            this.isTimedLevel = true;
            this.levelTime = this.getLevelTime(chars);
        }
        else {
            this.isTimedLevel = false;
        }
    }
    
    private int getLevelTime(final char[] chars) {
        final char[] timeChars = new char[4];
        for (int i = 0; i < timeChars.length; ++i) {
            timeChars[i] = chars[(this.lineLength + 2) * Game.yTiles + 7 + 8 + 10 + 10 + 3 + i];
        }
        return Integer.valueOf(String.valueOf(timeChars));
    }
    
    private void getLevelNames(final char[] chars) {
        final char[] levelName1Chars = new char[8];
        final char[] levelName2Chars = new char[8];
        int x = 0;
        int i;
        for (int nameIndex = i = (this.lineLength + 2) * Game.yTiles + 2 + 5 + 8; i < nameIndex + 8; ++i) {
            levelName1Chars[x] = chars[i];
            levelName2Chars[x++] = chars[i + 8 + 2];
        }
        this.levelNamePart1 = String.valueOf(levelName1Chars);
        this.levelNamePart2 = String.valueOf(levelName2Chars);
    }
    
    private int getPipeColor(final char[] chars) {
        return Integer.valueOf(String.valueOf(chars[(this.lineLength + 2) * Game.yTiles + 7 + 8 + 10 + 10 + 3 + 6]));
    }
    
    public static int getShadowType(final int levelType) {
        int shadowColor = 0;
        if (levelType == 2) {
            shadowColor = 2;
        }
        else if (levelType == 1) {
            shadowColor = 1;
        }
        return shadowColor;
    }
    
    public static boolean isBeanstalkBlock(final char c) {
        return c == 'G' || c == 'N' || c == '\u25c4' || (c == '\u03d8' || c == '\u03dc');
    }
    
    public static boolean isPowerupBlock(final char c) {
        return c == 'D' || c == 'K' || c == '\u266b' || c == 'X' || c == 'P';
    }
    
    public static boolean isWaterEnemyOnly(final char c) {
        return c == '[' || c == '{' || c == '|' || c == ']';
    }
    
    public static boolean isLevelEndCharacter(final char c) {
        return c == '\u2666' || c == '\u2665' || c == '\u21a8';
    }
    
    public static boolean multiCoinBlock(final char c) {
        return c == 'C' || c == 'J' || c == '\u2640';
    }
    
    public static boolean isFlippableEnemy(final char c) {
        return c == '\u02e7' || c == '$' || c == '^' || c == '\u02e6' || c == '%' || c == '&' || c == '(' || c == ')' || c == '_' || c == '=' || c == '{' || c == ']' || c == '|' || c == '\\' || c == ':' || c == '\u00f4';
    }
    
    public static boolean isShiftableBackground(final char c) {
        return c == '\u00fd' || c == '«' || c == '©' || c == '¦' || c == '¥' || c == '¤' || c == '£' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == '\u00f1';
    }
    
    public static boolean isRepeatablePlatform(final char c) {
        return c == '>' || c == '/' || c == '\u2194' || c == '\u2191' || c == '¶' || c == 'V';
    }
    
    public static boolean isBowserEnding(final char c) {
        return c == '\u2666' || c == '¼' || c == '\u00d7' || c == '\u00d8' || (c == '\u00de' || c == '\u00df' || c == '\u00f0' || c == '\u00ff');
    }
    
    public static boolean isMarioPlacementItem(final char c) {
        return c == 'k' || c == '½' || c == '\u0292' || c == '\u00f7';
    }
    
    public static boolean isFallingPlatform(final char c) {
        return c == '\u203c' || c == '\u25bc';
    }
    
    public static boolean isHOscPlatform(final char c) {
        return c == '\u2195' || c == '\u263a';
    }
    
    public static boolean isHOscKoopa(final char c) {
        return c == '®' || c == '±' || c == '¬';
    }
    
    public static boolean isVOscKoopa(final char c) {
        return c == '+' || c == '-' || c == '*';
    }
    
    public static boolean isFlyingKoopa(final char c) {
        return isHOscKoopa(c) || isVOscKoopa(c);
    }
    
    public static boolean isOscSprite(final char c) {
        return isHOscPlatform(c) || isVOscPlatform(c) || isFlyingKoopa(c);
    }
    
    public static boolean isVOscPlatform(final char c) {
        return c == '\u266a' || c == '?';
    }
    
    public static boolean isPulleyPlatform(final char c) {
        return c == '\u0398' || c == '\u25ac';
    }
    
    public static boolean isUpwardOpeningPipe(final char c) {
        return c == '~' || c == ';' || c == '\u0108';
    }
    
    public static boolean isDownwardOpeningPipe(final char c) {
        return c == '\u0110' || c == '¿';
    }
    
    public static boolean isLeftOpeningPipe(final char c) {
        return c == '!' || c == '\u2021';
    }
    
    public static boolean isRightOpeningPipe(final char c) {
        return c == '\u2663' || c == '\u00ee';
    }
    
    public static boolean isSideOpeningPipe(final char c) {
        return c == '!' || c == '\u2021' || c == '\u2663' || c == '\u00ee';
    }
    
    public static boolean isVerticalOpeningPipe(final char c) {
        return c == '~' || c == ';' || c == '\u0108' || c == '\u0110' || c == '¿';
    }
    
    public static boolean isPiranhaPipe(final char c) {
        return c == '¿' || c == '\u2021' || c == '\u00ee' || c == ';';
    }
    
    public static int getPipeType(final char c) {
        if (c == '\u0110' || c == '¿') {
            return 3;
        }
        if (c == '!' || c == '\u2021') {
            return 0;
        }
        if (c == '\u2663' || c == '\u00ee') {
            return 1;
        }
        if (c == '~' || c == ';') {
            return 2;
        }
        throw new IllegalStateException("Non pipe type item provided.");
    }
    
    public static boolean isBlockBrickBoxTypeItem(final char c) {
        return isBlockItem(c) || isBrickItem(c) || isBoxItem(c);
    }
    
    public static boolean isBlockItem(final char c) {
        return c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == '§' || c == 'p' || (c == 'q' || c == 'r' || c == '\u045e' || c == 's' || c == 't' || c == 'u') || (c == 'v' || c == 'y' || c == 'w' || c == 'z') || (c == '\u25cb' || c == '\u2642' || c == '\u25d9' || c == '\u00e6');
    }
    
    public static boolean isBrickItem(final char c) {
        return c == 'A' || c == 'B' || c == 'C' || c == 'D' || c == 'E' || c == 'F' || c == 'G' || (c == 'H' || c == 'I' || c == 'J' || c == 'K' || c == 'L' || c == 'M' || c == 'N') || (c == '\u2022' || c == '\u25d8' || c == '\u2640' || c == '\u266b' || c == '\u263c' || c == '\u25ba' || c == '\u25c4');
    }
    
    public static boolean isBoxItem(final char c) {
        return c == 'O' || c == 'P' || c == 'Q' || c == 'R' || c == '\u03d8' || (c == 'W' || c == 'X' || c == 'Y' || c == 'Z' || c == '\u03dc');
    }
    
    public static boolean isCoinsBrick(final char c) {
        return c == 'C' || c == 'J' || c == '\u2640';
    }
    
    private void interpretChars(final char[] chars, final int lineLength) throws Exception {
        this.getLevelType(chars);
        this.getPipeType(chars);
        this.hasSpinyThrower = this.hasSpinyThrower(chars);
        this.hasFlyingFish = this.hasFlyingFish(chars);
        this.hasBulletThrower = this.hasBulletThrower(chars);
        this.blackAndWhite = this.isBlackAndWhite(chars);
        this.autoScrollingLevel = this.isAutoScrolling(chars);
        final int texturePack = this.getTexturePack(chars);
        this.game.texturePacks.setTexturePack(texturePack);
        this.hasFlameThrower = false;
        this.getCliffDestination(chars);
        this.getLevelNames(chars);
        this.setTimedLevel(chars);
        final ImageIcon[] bricks = this.textures.getBrickTextures();
        final ImageIcon[] questionBoxes = this.textures.getQuestionBoxTextures();
        final ImageIcon[] koopas = this.textures.getKoopaTextures();
        final int shadowColor = getShadowType(this.levelType);
        this.maxTravelX = -1;
        boolean marioSet = false;
        for (int line = 0, i = 0; line < Game.yTiles; ++line, i += lineLength + 2) {
            for (int j = 0; j < lineLength; ++j) {
                final char tile = chars[i + j];
                final char followingChar = chars[i + j + 1];
                if (Character.isDigit(tile) || tile == 'x') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j);
                }
                else if (tile == '«') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.singleCloud, this.isShifted(followingChar), false);
                }
                else if (tile == '©') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.doubleCloud, this.isShifted(followingChar), false);
                }
                else if (tile == '¦') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.tripleCloud, this.isShifted(followingChar), false);
                }
                else if (tile == '¥') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.singleBush, this.isShifted(followingChar), false);
                }
                else if (tile == '¤') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.doubleBush, this.isShifted(followingChar), false);
                }
                else if (tile == '£') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.tripleBush, this.isShifted(followingChar), false);
                }
                else if (tile == '¢') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.tallTrimmedBush, false, false);
                }
                else if (tile == '¡') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.shortTrimmedBush, false, false);
                }
                else if (tile == '\u2192') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.shortSnowyBush, false, false);
                }
                else if (tile == '\u2190') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.tallSnowyBush, false, false);
                }
                else if (tile == 'i') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.picketFence, false, false);
                }
                else if (tile == 'a') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.castleWall, false, false);
                }
                else if (tile == 'b') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.smallHill, this.isShifted(followingChar), false);
                }
                else if (tile == 'c') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.bigHill, this.isShifted(followingChar), false);
                }
                else if (tile == 'd') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.smallCastle, this.isShifted(followingChar), false);
                }
                else if (tile == 'e') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.largeCastle, this.isShifted(followingChar), false);
                }
                else if (tile == 'f') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.treeBark, false, false);
                }
                else if (tile == '\u2660') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.mushroomTreeBark, false, false);
                }
                else if (tile == 'g') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.lavaTop, false, false);
                }
                else if (tile == 'µ') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.lavaBottom, false, false);
                }
                else if (tile == '¾') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.waterTop, false, false);
                }
                else if (tile == '\u00e5') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.waterBottom, false, false);
                }
                else if (tile == '\u00f1') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.warpZoneMessage, this.isShifted(followingChar), false);
                }
                else if (tile == '\u00fd') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.bowserChain, this.isShifted(followingChar), false);
                }
                else if (tile == 'h') {
                    final Sprite coin = new Coin(this.game, this.textures.getCoinTextures(), shadowColor);
                    this.levelTiles[line][j] = new Tile(this.game, line, j, coin);
                    this.multiTileSprite(line, j, 1, 1, coin);
                }
                else if (tile == '\u0292') {
                    final Sprite checkpoint = new Checkpoint(this.game, new ImageIcon[] { this.game.textures.checkPtFlag }, this.getCheckpointType(followingChar));
                    this.levelTiles[line][j] = new Tile(this.game, line, j, checkpoint);
                    this.multiTileSprite(line, j, 1, 1, checkpoint);
                }
                else if (tile == '\u03e4') {
                    if (line != Game.yTiles - 11) {
                        throw new RuntimeException("Beanstalk arrivals must be in 11th to last row: line " + line + " column " + j);
                    }
                    ImageIcon[] images;
                    if (shadowColor == 1 || shadowColor == 2) {
                        images = new ImageIcon[] { this.game.textures.entryVineDark };
                    }
                    else {
                        images = new ImageIcon[] { this.game.textures.entryVineLight };
                    }
                    final Sprite arrivalVine = new ArrivalVine(this.game, images);
                    this.levelTiles[line][j] = new Tile(this.game, line, j, arrivalVine);
                    this.multiTileSprite(line, j, 1, 1, arrivalVine);
                    this.multiTileCollision(Game.yTiles - 2, j, 2, 2);
                }
                else if (tile == 'j') {
                    final boolean isSuperSpring = this.isSuperSpring(followingChar);
                    final ImageIcon[] springTextures = isSuperSpring ? this.game.textures.getGreenSpringTextures() : this.game.textures.getSpringTextures();
                    final Sprite spring = new Spring(this.game, springTextures, this.levelType, isSuperSpring);
                    this.levelTiles[line][j] = new Tile(this.game, line, j, spring);
                    this.multiTileSprite(line, j, 1, 1, spring);
                    this.multiTileCollision(line + 3, j, 1, 2);
                }
                else if (tile == 'k') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j);
                    if (marioSet) {
                        this.errorCode = 1;
                        throw new RuntimeException("Mario is being set in more than one starting place");
                    }
                    marioSet = true;
                }
                else if (tile == '½') {
                    final Sprite thinAir = new ThinAirWarp(this.game, new ImageIcon[] { this.game.textures.marioStand });
                    this.levelTiles[line][j] = new Tile(this.game, line, j, thinAir);
                }
                else if (tile == '\u00f7') {
                    final Sprite infiniteCorridor = new InfiniteCorridor(this.game, new ImageIcon[] { this.game.textures.infiniteCorridorSizer });
                    this.levelTiles[line][j] = new Tile(this.game, line, j, infiniteCorridor);
                    this.multiTileSprite(line, j, 1, 1, infiniteCorridor);
                }
                //MARK ground 1
                else if (tile == 'l') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.lightGround, false, true);
                    this.multiTileCollision(line, j, 2, 2);
                }
                else if (tile == 'm') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures./*dark*/lightGround, false, true);
                    this.multiTileCollision(line, j, 2, 2);
                    chars[i+j] = 'l';
                }
                else if (tile == 'n') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures./*stone*/lightGround, false, true);
                    this.multiTileCollision(line, j, 2, 2);
                    chars[i+j] = 'l';
                }
                else if (tile == 'o') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures./*sea*/lightGround, false, true);
                    this.multiTileCollision(line, j, 2, 2);
                    chars[i+j] = 'l';
                }
                else if (tile == 'p') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures./*cloud*/lightGround, false, true);
                    this.multiTileCollision(line, j, 2, 2);
                    chars[i+j] = 'l';
                }
                else if (tile == '§') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.seaBlock, false, true);
                    this.multiTileCollision(line, j, 2, 2);
                }
                else if (tile == 'q') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.lightBlock, false, true);
                    this.multiTileCollision(line, j, 2, 2);
                }
                else if (tile == 'r') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.darkBlock, false, true);
                    this.multiTileCollision(line, j, 2, 2);
                }
                else if (tile == '\u045e') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.stoneBlock, false, true);
                    this.multiTileCollision(line, j, 2, 2);
                }
                else if (tile == 's') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.lightMetal, false, true);
                    this.multiTileCollision(line, j, 2, 2);
                }
                else if (tile == 't') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.darkMetal, false, true);
                    this.multiTileCollision(line, j, 2, 2);
                }
                else if (tile == 'u') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.stoneMetal, false, true);
                    this.multiTileCollision(line, j, 2, 2);
                }
                else if (tile == 'v') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.treeTopLeftEnd, false, true);
                    this.multiTileCollision(line, j, 2, 2);
                }
                else if (tile == 'w') {
                    this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.treeTopRightEnd, false, true);
                    this.multiTileCollision(line, j, 2, 2);
                }
                else if (tile != 'x') {
                    if (tile == 'y') {
                        this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.treeTopMiddle, false, true);
                        this.multiTileCollision(line, j, 2, 1);
                    }
                    else if (tile == '\u25cb') {
                        this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.mushroomTreeLeftEnd, false, true);
                        this.multiTileCollision(line, j, 2, 2);
                    }
                    else if (tile == '\u25d9') {
                        this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.mushroomTreeRightEnd, false, true);
                        this.multiTileCollision(line, j, 2, 2);
                    }
                    else if (tile == '\u2642') {
                        this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.mushroomTreeMiddleSection, false, true);
                        this.multiTileCollision(line, j, 2, 2);
                    }
                    else if (tile == '\u2193') {
                        this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.mushroomTreeBarkTop, false, false);
                    }
                    else if (tile == 'z') {
                        this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.coral, false, true);
                        this.multiTileCollision(line, j, 2, 2);
                    }
                    else if (tile == '\u25b2') {
                        this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.bridge, false, true);
                        this.multiTileCollision(line, j, 1, 1);
                    }
                    else if (tile == '\u00e6') {
                        this.levelTiles[line][j] = new Tile(this.game, line, j, this.textures.bowserBridgeSection, false, true);
                        this.multiTileCollision(line, j, 2, 1);
                    }
                    else if (tile == 'A') {
                        final Sprite brick = new Brick(this.game, bricks, 0, 0);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, brick);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, brick);
                    }
                    else if (tile == 'B') {
                        final Sprite brick = new Brick(this.game, bricks, 0, 1);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, brick);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, brick);
                    }
                    else if (tile == 'C') {
                        int coinCount = 5;
                        if (followingChar != 'x') {
                            coinCount = Integer.valueOf(String.valueOf(followingChar));
                        }
                        final Sprite brick2 = new Brick(this.game, bricks, 0, 2, coinCount);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, brick2);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, brick2);
                    }
                    else if (tile == 'D') {
                        final Sprite brick = new Brick(this.game, bricks, 0, this.isPoison(followingChar) ? 7 : 3);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, brick);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, brick);
                    }
                    else if (tile == 'E') {
                        final Sprite brick = new Brick(this.game, bricks, 0, 5);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, brick);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, brick);
                    }
                    else if (tile == 'F') {
                        final Sprite brick = new Brick(this.game, bricks, 0, 4);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, brick);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, brick);
                    }
                    else if (tile == 'G') {
                        final Sprite brick = new Brick(this.game, bricks, 0, 6);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, brick);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, brick);
                    }
                    else if (tile == 'H') {
                        final Sprite brick = new Brick(this.game, bricks, 1, 0);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, brick);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, brick);
                    }
                    else if (tile == 'I') {
                        final Sprite brick = new Brick(this.game, bricks, 1, 1);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, brick);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, brick);
                    }
                    else if (tile == 'J') {
                        int coinCount = 0;
                        if (followingChar != 'x') {
                            coinCount = Integer.valueOf(String.valueOf(followingChar));
                        }
                        final Sprite brick2 = new Brick(this.game, bricks, 1, 2, coinCount);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, brick2);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, brick2);
                    }
                    else if (tile == 'K') {
                        final Sprite brick = new Brick(this.game, bricks, 1, this.isPoison(followingChar) ? 7 : 3);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, brick);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, brick);
                    }
                    else if (tile == 'L') {
                        final Sprite brick = new Brick(this.game, bricks, 1, 5);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, brick);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, brick);
                    }
                    else if (tile == 'M') {
                        final Sprite brick = new Brick(this.game, bricks, 1, 4);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, brick);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, brick);
                    }
                    else if (tile == 'N') {
                        final Sprite brick = new Brick(this.game, bricks, 1, 6);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, brick);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, brick);
                    }
                    else if (tile == '\u2022') {
                        final Sprite brick = new Brick(this.game, bricks, 2, 0);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, brick);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, brick);
                    }
                    else if (tile == '\u25d8') {
                        final Sprite brick = new Brick(this.game, bricks, 2, 1);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, brick);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, brick);
                    }
                    else if (tile == '\u2640') {
                        int coinCount = 0;
                        if (followingChar != 'x') {
                            coinCount = Integer.valueOf(String.valueOf(followingChar));
                        }
                        final Sprite brick2 = new Brick(this.game, bricks, 2, 2, coinCount);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, brick2);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, brick2);
                    }
                    else if (tile == '\u266b') {
                        final Sprite brick = new Brick(this.game, bricks, 2, this.isPoison(followingChar) ? 7 : 3);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, brick);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, brick);
                    }
                    else if (tile == '\u263c') {
                        final Sprite brick = new Brick(this.game, bricks, 2, 5);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, brick);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, brick);
                    }
                    else if (tile == '\u25ba') {
                        final Sprite brick = new Brick(this.game, bricks, 2, 4);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, brick);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, brick);
                    }
                    else if (tile == '\u25c4') {
                        final Sprite brick = new Brick(this.game, bricks, 2, 6);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, brick);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, brick);
                    }
                    else if (tile == 'O') {
                        final Sprite qBox = new QuestionBox(this.game, questionBoxes, shadowColor, QuestionBox.CONTENTS_COIN, false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, qBox);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, qBox);
                    }
                    else if (tile == 'P') {
                        final QuestionBox qBox2 = new QuestionBox(this.game, questionBoxes, shadowColor, this.isPoison(followingChar) ? QuestionBox.CONTENTS_POISON : QuestionBox.CONTENTS_GROWTH, false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, qBox2);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, qBox2);
                    }
                    else if (tile == 'Q') {
                        final QuestionBox qBox2 = new QuestionBox(this.game, questionBoxes, shadowColor, QuestionBox.CONTENTS_EXTRA_LIFE, false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, qBox2);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, qBox2);
                    }
                    else if (tile == 'R') {
                        final QuestionBox qBox2 = new QuestionBox(this.game, questionBoxes, shadowColor, QuestionBox.CONTENTS_STAR, false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, qBox2);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, qBox2);
                    }
                    else if (tile == '\u03d8') {
                        final QuestionBox qBox2 = new QuestionBox(this.game, questionBoxes, shadowColor, QuestionBox.CONTENTS_BEANSTALK, false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, qBox2);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, qBox2);
                    }
                    else if (tile == 'W') {
                        final Sprite qBox = new QuestionBox(this.game, questionBoxes, 0, QuestionBox.CONTENTS_COIN, true);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, qBox);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, qBox);
                    }
                    else if (tile == 'X') {
                        final QuestionBox qBox2 = new QuestionBox(this.game, questionBoxes, 0, this.isPoison(followingChar) ? QuestionBox.CONTENTS_POISON : QuestionBox.CONTENTS_GROWTH, true);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, qBox2);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, qBox2);
                    }
                    else if (tile == 'Y') {
                        final QuestionBox qBox2 = new QuestionBox(this.game, questionBoxes, 0, QuestionBox.CONTENTS_EXTRA_LIFE, true);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, qBox2);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, qBox2);
                    }
                    else if (tile == 'Z') {
                        final QuestionBox qBox2 = new QuestionBox(this.game, questionBoxes, 0, QuestionBox.CONTENTS_STAR, true);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, qBox2);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, qBox2);
                    }
                    else if (tile == '\u03dc') {
                        final QuestionBox qBox2 = new QuestionBox(this.game, questionBoxes, 0, QuestionBox.CONTENTS_BEANSTALK, true);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, qBox2);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 2, 2, qBox2);
                    }
                    else if (tile == '`') {
                        final Sprite pipe = this.getPipe(5, false, false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, pipe);
                        this.multiTileCollision(line, j, 4, 1);
                    }
                    else if (tile == '~') {
                        final Sprite pipe = this.getPipe(2, false, false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, pipe);
                        this.multiTileSprite(line, j, 1, 1, pipe);
                        this.multiTileCollision(line, j, 4, 4);
                    }
                    else if (tile == ';') {
                        final char belowCharacter = chars[i + j + (lineLength + 2)];
                        final Sprite pipe2 = this.getPipe(2, true, this.isRedPiranha(belowCharacter));
                        this.levelTiles[line][j] = new Tile(this.game, line, j, pipe2);
                        this.multiTileSprite(line, j, 1, 1, pipe2);
                        this.multiTileCollision(line, j, 4, 4);
                    }
                    else if (tile == '\u0110') {
                        final Sprite pipe = this.getPipe(3, false, false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, pipe);
                        this.multiTileSprite(line, j, 1, 1, pipe);
                        this.multiTileCollision(line, j, 4, 4);
                    }
                    else if (tile == '¿') {
                        final char belowCharacter = chars[i + j + (lineLength + 2)];
                        final Sprite pipe2 = this.getPipe(3, true, this.isRedPiranha(belowCharacter));
                        this.levelTiles[line][j] = new Tile(this.game, line, j, pipe2);
                        this.multiTileSprite(line, j, 1, 1, pipe2);
                        this.multiTileCollision(line, j, 4, 4);
                    }
                    else if (tile == '!') {
                        final Sprite pipe = this.getPipe(0, false, false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, pipe);
                        this.multiTileSprite(line, j, 1, 1, pipe);
                        this.multiTileCollision(line, j, 4, 3);
                    }
                    else if (tile == '\u2021') {
                        final Sprite pipe = this.getPipe(0, true, this.isRedPiranha(followingChar));
                        this.levelTiles[line][j] = new Tile(this.game, line, j, pipe);
                        this.multiTileSprite(line, j, 1, 1, pipe);
                        this.multiTileCollision(line, j, 4, 3);
                    }
                    else if (tile == '\u2663') {
                        final Sprite pipe = this.getPipe(1, false, false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, pipe);
                        this.multiTileSprite(line, j, 1, 1, pipe);
                        this.multiTileCollision(line, j, 4, 3);
                    }
                    else if (tile == '\u00ee') {
                        final Sprite pipe = this.getPipe(1, true, this.isRedPiranha(followingChar));
                        this.levelTiles[line][j] = new Tile(this.game, line, j, pipe);
                        this.multiTileSprite(line, j, 1, 1, pipe);
                        this.multiTileCollision(line, j, 4, 3);
                    }
                    else if (tile == '@') {
                        final Sprite pipe = this.getPipe(4, false, false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, pipe);
                        this.multiTileCollision(line, j, 1, 4);
                    }
                    else if (tile == '#') {
                        final Sprite pipe = this.getPipe(6, false, false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, pipe);
                        this.multiTileCollision(line, j, 4, 4);
                    }
                    else if (tile == 'S') {
                        final Sprite pipe = this.getPipe(7, false, false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, pipe);
                        this.multiTileCollision(line, j, 4, 4);
                    }
                    else if (tile == 'T') {
                        final Sprite pipe = this.getPipe(10, false, false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, pipe);
                        this.multiTileCollision(line, j, 4, 4);
                    }
                    else if (tile == 'U') {
                        final Sprite pipe = this.getPipe(8, false, false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, pipe);
                        this.multiTileCollision(line, j, 4, 4);
                    }
                    else if (tile == '\u010e') {
                        final Sprite pipe = this.getPipe(9, false, false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, pipe);
                        this.multiTileCollision(line, j, 4, 4);
                    }
                    else if (tile == '\u0108') {
                        char color = chars[i + j + (lineLength + 2)];
                        if (color == 'x') {
                            color = '0';
                        }
                        final int colorValue = Integer.valueOf(String.valueOf(color));
                        ImageIcon image = this.textures.warpZonePipeOrange;
                        if (colorValue == 1) {
                            image = this.textures.warpZonePipeGreen;
                        }
                        else if (colorValue == 2) {
                            image = this.textures.warpZonePipeWhite;
                        }
                        else if (colorValue == 3) {
                            image = this.textures.warpZonePipeBlue;
                        }
                        final Sprite pipe3 = new Pipe(this.game, new ImageIcon[] { image }, 11, true, false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, pipe3);
                        this.multiTileSprite(line, j, 1, 1, pipe3);
                        this.multiTileCollision(line, j, 6, 4);
                    }
                    else if (tile == '$') {
                        final Sprite goomba = new Goomba(this.game, this.textures.getGoombaTextures(), 0, this.isLeftStarting(followingChar));
                        this.levelTiles[line][j] = new Tile(this.game, line, j, goomba);
                        this.multiTileSprite(line, j, 1, 1, goomba);
                    }
                    else if (tile == '%') {
                        final Sprite goomba = new Goomba(this.game, this.textures.getGoombaTextures(), 1, this.isLeftStarting(followingChar));
                        this.levelTiles[line][j] = new Tile(this.game, line, j, goomba);
                        this.multiTileSprite(line, j, 1, 1, goomba);
                    }
                    else if (tile == '\u02e6') {
                        final Sprite goomba = new Goomba(this.game, this.textures.getGoombaTextures(), 3, this.isLeftStarting(followingChar));
                        this.levelTiles[line][j] = new Tile(this.game, line, j, goomba);
                        this.multiTileSprite(line, j, 1, 1, goomba);
                    }
                    else if (tile == '^') {
                        final Sprite koopa = new Koopa(this.game, koopas, 0, 0, this.isLeftStarting(followingChar));
                        this.levelTiles[line][j] = new Tile(this.game, line, j, koopa);
                        this.multiTileSprite(line, j, 1, 1, koopa);
                    }
                    else if (tile == '&') {
                        final Sprite koopa = new Koopa(this.game, koopas, 0, 1, this.isLeftStarting(followingChar));
                        this.levelTiles[line][j] = new Tile(this.game, line, j, koopa);
                        this.multiTileSprite(line, j, 1, 1, koopa);
                    }
                    else if (tile == '*') {
                        final Sprite koopa = new Koopa(this.game, koopas, 0, 2, true);
                        ((Koopa)koopa).startingOffset = this.getOscOffset(followingChar);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, koopa);
                        this.multiTileSprite(line, j, 1, 1, koopa);
                    }
                    else if (tile == '¬') {
                        final Sprite koopa = new Koopa(this.game, koopas, 0, 3, true);
                        ((Koopa)koopa).startingOffset = this.getOscOffset(followingChar);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, koopa);
                        this.multiTileSprite(line, j, 1, 1, koopa);
                    }
                    else if (tile == '(') {
                        final Sprite koopa = new Koopa(this.game, koopas, 1, 0, this.isLeftStarting(followingChar));
                        this.levelTiles[line][j] = new Tile(this.game, line, j, koopa);
                        this.multiTileSprite(line, j, 1, 1, koopa);
                    }
                    else if (tile == ')') {
                        final Sprite koopa = new Koopa(this.game, koopas, 1, 1, this.isLeftStarting(followingChar));
                        this.levelTiles[line][j] = new Tile(this.game, line, j, koopa);
                        this.multiTileSprite(line, j, 1, 1, koopa);
                    }
                    else if (tile == '-') {
                        final Sprite koopa = new Koopa(this.game, koopas, 1, 2, true);
                        ((Koopa)koopa).startingOffset = this.getOscOffset(followingChar);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, koopa);
                        this.multiTileSprite(line, j, 1, 1, koopa);
                    }
                    else if (tile == '±') {
                        final Sprite koopa = new Koopa(this.game, koopas, 1, 3, true);
                        ((Koopa)koopa).startingOffset = this.getOscOffset(followingChar);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, koopa);
                        this.multiTileSprite(line, j, 1, 1, koopa);
                    }
                    else if (tile == '_') {
                        final Sprite koopa = new Koopa(this.game, koopas, 2, 0, this.isLeftStarting(followingChar));
                        this.levelTiles[line][j] = new Tile(this.game, line, j, koopa);
                        this.multiTileSprite(line, j, 1, 1, koopa);
                    }
                    else if (tile == '=') {
                        final Sprite koopa = new Koopa(this.game, koopas, 2, 1, this.isLeftStarting(followingChar));
                        this.levelTiles[line][j] = new Tile(this.game, line, j, koopa);
                        this.multiTileSprite(line, j, 1, 1, koopa);
                    }
                    else if (tile == '+') {
                        final Sprite koopa = new Koopa(this.game, koopas, 2, 2, true);
                        ((Koopa)koopa).startingOffset = this.getOscOffset(followingChar);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, koopa);
                        this.multiTileSprite(line, j, 1, 1, koopa);
                    }
                    else if (tile == '®') {
                        final Sprite koopa = new Koopa(this.game, koopas, 2, 3, true);
                        ((Koopa)koopa).startingOffset = this.getOscOffset(followingChar);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, koopa);
                        this.multiTileSprite(line, j, 1, 1, koopa);
                    }
                    else if (tile == '[') {
                        final Sprite squid = new Squid(this.game, this.game.textures.getSquidTextures());
                        this.levelTiles[line][j] = new Tile(this.game, line, j, squid);
                        this.multiTileSprite(line, j, 1, 1, squid);
                    }
                    else if (tile == '{') {
                        final Sprite redFish = new RedFish(this.game, this.game.textures.getRedFishTextures(), this.isLeftStarting(followingChar), false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, redFish);
                        this.multiTileSprite(line, j, 1, 1, redFish);
                    }
                    else if (tile == ']') {
                        final Sprite grayFish = new GrayFish(this.game, this.game.textures.getGrayFishTextures(), this.isLeftStarting(followingChar), false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, grayFish);
                        this.multiTileSprite(line, j, 1, 1, grayFish);
                    }
                    else if (tile == '|') {
                        final Sprite grayFish = new GrayFish(this.game, this.game.textures.getGrayFishTextures(), this.isLeftStarting(followingChar), true);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, grayFish);
                        this.multiTileSprite(line, j, 1, 1, grayFish);
                    }
                    else if (tile == '}') {
                        final Sprite hammerBro = new HammerBro(this.game, this.game.textures.getHammerBroTextures(), true);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, hammerBro);
                        this.multiTileSprite(line, j, 1, 1, hammerBro);
                    }
                    else if (tile == '\u00f6') {
                        final Sprite hammerBro = new HammerBro(this.game, this.game.textures.getHammerBroTextures(), false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, hammerBro);
                        this.multiTileSprite(line, j, 1, 1, hammerBro);
                    }
                    else if (tile == '\\') {
                        final Sprite spiny = new Spiny(this.game, this.game.textures.getSpinyTextures(), this.isLeftStarting(followingChar));
                        this.levelTiles[line][j] = new Tile(this.game, line, j, spiny);
                        this.multiTileSprite(line, j, 1, 1, spiny);
                    }
                    else if (tile == ':') {
                        final Sprite beetle = new Beetle(this.game, this.textures.getBeetleTextures(), this.isLeftStarting(followingChar), 0);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, beetle);
                        this.multiTileSprite(line, j, 1, 1, beetle);
                    }
                    else if (tile == '\u00f4') {
                        final Sprite beetle = new Beetle(this.game, this.textures.getBeetleTextures(), this.isLeftStarting(followingChar), 1);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, beetle);
                        this.multiTileSprite(line, j, 1, 1, beetle);
                    }
                    else if (tile == '\u02e7') {
                        final Sprite beetle = new Beetle(this.game, this.textures.getBeetleTextures(), this.isLeftStarting(followingChar), 2);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, beetle);
                        this.multiTileSprite(line, j, 1, 1, beetle);
                    }
                    else if (tile == '\'') {
                        final Sprite cannon = new Cannon(this.game, this.game.textures.getTallCannonTextures(), shadowColor, true);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, cannon);
                        this.multiTileCollision(line, j, 4, 2);
                    }
                    else if (tile == '\u260e') {
                        ImageIcon image2 = this.textures.lightCannonBase;
                        if (shadowColor == 1) {
                            image2 = this.textures.darkCannonBase;
                        }
                        else if (shadowColor == 2) {
                            image2 = this.textures.stoneCannonBase;
                        }
                        this.levelTiles[line][j] = new Tile(this.game, line, j, image2, false, true);
                        this.multiTileCollision(line, j, 1, 2);
                    }
                    else if (tile == '\"') {
                        final Sprite cannon = new Cannon(this.game, this.game.textures.getShortCannonTextures(), shadowColor, false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, cannon);
                        this.multiTileCollision(line, j, 2, 2);
                    }
                    else if (tile == ',') {
                        final Sprite firebar = new Firebar(this.game, new ImageIcon[] { this.textures.lightMetal }, this.isCWFirebar(followingChar), this.isFastFirebar(followingChar), false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, firebar);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 1, 1, firebar);
                    }
                    else if (tile == '<') {
                        final Sprite firebar = new Firebar(this.game, new ImageIcon[] { this.textures.lightMetal }, this.isCWFirebar(followingChar), this.isFastFirebar(followingChar), true);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, firebar);
                        this.multiTileCollision(line, j, 2, 2);
                        this.multiTileSprite(line, j, 1, 1, firebar);
                    }
                    else if (tile == '.') {
                        int backgroundColor = 0;
                        if (followingChar == String.valueOf(1).toCharArray()[0]) {
                            backgroundColor = 1;
                        }
                        else if (followingChar == String.valueOf(2).toCharArray()[0]) {
                            backgroundColor = 2;
                        }
                        final Sprite lavaBall = new LavaBall(this.game, this.game.textures.getLavaballTextures(), backgroundColor);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, lavaBall);
                        this.multiTileSprite(line, j, 1, 1, lavaBall);
                    }
                    else if (tile == '>') {
                        final Sprite platform = new Platform(this.game, new ImageIcon[] { this.textures.platformLong }, 0, this.isSingleRepPlatform(followingChar));
                        this.levelTiles[line][j] = new Tile(this.game, line, j, platform);
                        this.multiTileSprite(line, j, 1, 1, platform);
                    }
                    else if (tile == '/') {
                        final Sprite platform = new Platform(this.game, new ImageIcon[] { this.textures.platformLong }, 1, this.isSingleRepPlatform(followingChar));
                        this.levelTiles[line][j] = new Tile(this.game, line, j, platform);
                        this.multiTileSprite(line, j, 1, 1, platform);
                    }
                    else if (tile == '?') {
                        final Sprite platform = new Platform(this.game, new ImageIcon[] { this.textures.platformLong }, 2, false);
                        ((Platform)platform).startingOffset = this.getOscOffset(followingChar);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, platform);
                        this.multiTileSprite(line, j, 1, 1, platform);
                    }
                    else if (tile == '\u263a') {
                        final Sprite platform = new Platform(this.game, new ImageIcon[] { this.textures.platformLong }, 3, false);
                        ((Platform)platform).startingOffset = this.getOscOffset(followingChar);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, platform);
                        this.multiTileSprite(line, j, 1, 1, platform);
                    }
                    else if (tile == '\u25bc') {
                        final Sprite platform = new Platform(this.game, new ImageIcon[] { this.textures.platformLong }, 4, false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, platform);
                        this.multiTileSprite(line, j, 1, 1, platform);
                    }
                    else if (tile == '\u2194') {
                        final Sprite platform = new Platform(this.game, new ImageIcon[] { this.textures.platformShort }, 5, this.isSingleRepPlatform(followingChar));
                        this.levelTiles[line][j] = new Tile(this.game, line, j, platform);
                        this.multiTileSprite(line, j, 1, 1, platform);
                    }
                    else if (tile == '\u2191') {
                        final Sprite platform = new Platform(this.game, new ImageIcon[] { this.textures.platformShort }, 6, this.isSingleRepPlatform(followingChar));
                        this.levelTiles[line][j] = new Tile(this.game, line, j, platform);
                        this.multiTileSprite(line, j, 1, 1, platform);
                    }
                    else if (tile == '\u266a') {
                        final Sprite platform = new Platform(this.game, new ImageIcon[] { this.textures.platformShort }, 7, false);
                        ((Platform)platform).startingOffset = this.getOscOffset(followingChar);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, platform);
                        this.multiTileSprite(line, j, 1, 1, platform);
                    }
                    else if (tile == '\u2195') {
                        final Sprite platform = new Platform(this.game, new ImageIcon[] { this.textures.platformShort }, 8, false);
                        ((Platform)platform).startingOffset = this.getOscOffset(followingChar);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, platform);
                        this.multiTileSprite(line, j, 1, 1, platform);
                    }
                    else if (tile == '\u203c') {
                        final Sprite platform = new Platform(this.game, new ImageIcon[] { this.textures.platformShort }, 9, false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, platform);
                        this.multiTileSprite(line, j, 1, 1, platform);
                    }
                    else if (tile == '¶') {
                        final Sprite platform = new Platform(this.game, new ImageIcon[] { this.textures.platformExtraShort }, 10, this.isSingleRepPlatform(followingChar));
                        this.levelTiles[line][j] = new Tile(this.game, line, j, platform);
                        this.multiTileSprite(line, j, 1, 1, platform);
                    }
                    else if (tile == 'V') {
                        final Sprite platform = new Platform(this.game, new ImageIcon[] { this.textures.platformExtraShort }, 11, this.isSingleRepPlatform(followingChar));
                        this.levelTiles[line][j] = new Tile(this.game, line, j, platform);
                        this.multiTileSprite(line, j, 1, 1, platform);
                    }
                    else if (tile == '\u25ac') {
                        final Sprite platform = new Platform(this.game, new ImageIcon[] { this.textures.platformLong }, 14, false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, platform);
                        this.multiTileSprite(line, j, 1, 1, platform);
                    }
                    else if (tile == '\u0398') {
                        final Sprite platform = new Platform(this.game, new ImageIcon[] { this.textures.platformShort }, 15, false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, platform);
                        this.multiTileSprite(line, j, 1, 1, platform);
                    }
                    else if (tile == '\u263b') {
                        final Sprite platform = new Platform(this.game, new ImageIcon[] { this.textures.cloudCarrierLong }, 13, false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, platform);
                        this.multiTileSprite(line, j, 1, 1, platform);
                    }
                    else if (tile == '\u00e7') {
                        final Sprite platform = new Platform(this.game, new ImageIcon[] { this.textures.cloudCarrierShort }, 13, false);
                        this.levelTiles[line][j] = new Tile(this.game, line, j, platform);
                        this.multiTileSprite(line, j, 1, 1, platform);
                    }
                    else if (tile == '\u2665') {
                        if (j + 26 >= lineLength) {
                            this.errorCode = 10;
                            throw new RuntimeException("Not enough tiles for the flag: line " + line + " column " + j);
                        }
                        if (line != 0) {
                            this.errorCode = 11;
                            throw new RuntimeException("Flag tile not on line zero: line " + line + " column " + j);
                        }
                        if (j + 28 != lineLength) {
                            this.errorCode = 12;
                            throw new RuntimeException("Flag needs to be at the end of the level: line " + line + " column " + j);
                        }
                        final Sprite flag = new Flag(this.game, false, this.pipeColor);
                        this.maxTravelX = (j + 1 + 1) * 8;
                        this.levelTiles[line][j] = new Tile(this.game, line, j, flag);
                        this.multiTileSprite(line, j, 1, 1, flag);
                        this.multiTileCollision(23, j + 1, 2, 2);
                        this.multiTileCollision(25, j, 3, Game.yTiles);
                    }
                    else if (tile == '\u21a8') {
                        if (j + 26 >= lineLength) {
                            this.errorCode = 10;
                            throw new RuntimeException("Not enough tiles for the flag: line " + line + " column " + j);
                        }
                        if (line != 0) {
                            this.errorCode = 11;
                            throw new RuntimeException("Flag tile not on line zero: line " + line + " column " + j);
                        }
                        if (j + 28 != lineLength) {
                            this.errorCode = 12;
                            throw new RuntimeException("Flag needs to be at the end of the level: line " + line + " column " + j);
                        }
                        final Sprite flag = new Flag(this.game, true, this.pipeColor);
                        this.maxTravelX = (j + 1 + 1) * 8;
                        this.levelTiles[line][j] = new Tile(this.game, line, j, flag);
                        this.multiTileSprite(line, j, 1, 1, flag);
                        this.multiTileCollision(23, j + 1, 2, 2);
                        this.multiTileCollision(25, j, 3, Game.yTiles);
                    }
                    else if (isBowserEnding(tile)) {
                        this.hasFlameThrower = true;
                        if (j + 67 >= lineLength) {
                            this.errorCode = 10;
                            throw new RuntimeException("Not enough tiles for the flag: line " + line + " column " + j);
                        }
                        if (line != 0) {
                            this.errorCode = 11;
                            throw new RuntimeException("Flag tile not on line zero: line " + line + " column " + j);
                        }
                        if (j + 68 != lineLength) {
                            this.errorCode = 12;
                            throw new RuntimeException("Flag needs to be at the end of the level: line " + line + " column " + j);
                        }
                        final Sprite bowserBattle = new BowserBattle(this.game, tile);
                        this.maxTravelX = (j + 4) * 8;
                        this.levelTiles[line][j] = new Tile(this.game, line, j, bowserBattle);
                        this.multiTileSprite(line, j, 1, 1, bowserBattle);
                        if (tile != '\u00d8' && tile != '\u00f0') {
                            this.multiTileCollision(3, j, 6, 4);
                        }
                        this.multiTileCollision(3, j + 4, 2, Game.yTiles);
                        this.multiTileCollision(5, j + 32, 6, 1);
                        this.multiTileCollision(19, j, 9, 4);
                        this.multiTileCollision(19, j + 3, 2, 26);
                        this.multiTileCollision(17, j + 30, 11, 6);
                        this.multiTileCollision(25, j + 30 + 6, 3, 32);
                    }
                    else {
                        if (!this.game.textures.customTextChars.containsKey(tile)) {
                            this.errorCode = 7;
                            throw new RuntimeException("Invalid tile found at line " + line + ", column " + j + ": " + tile);
                        }
                        this.levelTiles[line][j] = new Tile(this.game, line, j, this.game.textures.symbols.get(this.game.textures.customTextChars.get(tile)), false, false);
                    }
                }
            }
        }
        if (!marioSet) {
            this.errorCode = 0;
            throw new RuntimeException("Mario was never placed anywhere to start");
        }
        this.updateTilesWithCollisionData();
        this.updateTilesWithSpriteData();
        try {
            this.distributeWarps();
        }
        catch (Exception e) {
            this.errorCode = 16;
            throw new RuntimeException("Problem distributing warps");
        }
    }
    
    private Sprite getPipe(final int pipeType, final boolean hasChomper, final boolean redPiranha) {
        Sprite pipe = null;
        if (this.pipeColor == 1) {
            pipe = new Pipe(this.game, new ImageIcon[] { this.textures.whitePipes[pipeType] }, pipeType, hasChomper, redPiranha);
        }
        else if (this.pipeColor == 0) {
            pipe = new Pipe(this.game, new ImageIcon[] { this.textures.greenPipes[pipeType] }, pipeType, hasChomper, redPiranha);
        }
        else if (this.pipeColor == 2) {
            pipe = new Pipe(this.game, new ImageIcon[] { this.textures.bluePipes[pipeType] }, pipeType, hasChomper, redPiranha);
        }
        else if (this.pipeColor == 3) {
            pipe = new Pipe(this.game, new ImageIcon[] { this.textures.orangePipes[pipeType] }, pipeType, hasChomper, redPiranha);
        }
        return pipe;
    }
    
    private boolean isLeftStarting(final char positionChar) {
        if (positionChar == '\r' || positionChar == '\n') {
            this.errorCode = 9;
            throw new RuntimeException("Invalid following character.");
        }
        return positionChar == '0';
    }
    
    private boolean isShifted(final char positionChar) {
        if (positionChar == '\r' || positionChar == '\n') {
            this.errorCode = 9;
            throw new RuntimeException("Invalid shift over character");
        }
        return positionChar == '0';
    }
    
    private boolean isPoison(final char positionChar) {
        if (positionChar == '\r' || positionChar == '\n') {
            this.errorCode = 9;
            throw new RuntimeException("Invalid shift over character");
        }
        return positionChar == '0';
    }
    
    private boolean isRedPiranha(final char positionChar) {
        if (positionChar == '\r' || positionChar == '\n') {
            this.errorCode = 9;
            throw new RuntimeException("Invalid shift over character");
        }
        return positionChar == '0';
    }
    
    private boolean isSingleRepPlatform(final char positionChar) {
        if (positionChar == 'r' || positionChar == 'n') {
            this.errorCode = 9;
            throw new RuntimeException("Invalid repeating platform character");
        }
        return positionChar == '0';
    }
    
    private float getOscOffset(final char positionChar) {
        if (positionChar == 'x' || positionChar == '0') {
            return 0.0f;
        }
        if (positionChar == '1') {
            return 0.25f;
        }
        if (positionChar == '2') {
            return 0.5f;
        }
        if (positionChar == '3') {
            return 0.75f;
        }
        return 0.0f;
    }
    
    private int getCheckpointType(final char positionChar) {
        if (positionChar == 'x' || positionChar == '0') {
            return 0;
        }
        return 1;
    }
    
    private boolean isSuperSpring(final char positionChar) {
        return positionChar == '0';
    }
    
    private boolean isCWFirebar(final char positionChar) {
        if (positionChar == '\r' || positionChar == '\n') {
            throw new RuntimeException("Invalid CCW character");
        }
        return positionChar == '0' || positionChar == '1';
    }
    
    private boolean isFastFirebar(final char positionChar) {
        if (positionChar == '\r' || positionChar == '\n') {
            throw new RuntimeException("Invalid CCW character");
        }
        return positionChar == '1' || positionChar == '3';
    }
    
    private void multiTileCollision(final int firstTileY, final int firstTileX, int heightInTiles, int widthInTiles) {
        if (firstTileX + widthInTiles > this.lineLength) {
            widthInTiles = this.lineLength - firstTileX;
        }
        if (firstTileY + heightInTiles > Game.yTiles) {
            heightInTiles = Game.yTiles - firstTileY;
        }
        for (int i = 0; i < widthInTiles; ++i) {
            for (int j = 0; j < heightInTiles; ++j) {
                this.collisionArray[firstTileY + j][firstTileX + i] = true;
                this.tileAssociations[firstTileY + j][firstTileX + i] = new Point(firstTileX, firstTileY);
            }
        }
    }
    
    private void multiTileSprite(final int firstTileY, final int firstTileX, int heightInTiles, int widthInTiles, final Sprite sprite) {
        if (firstTileX + widthInTiles > this.lineLength) {
            widthInTiles = this.lineLength - firstTileX;
        }
        if (firstTileY + heightInTiles > Game.yTiles) {
            heightInTiles = Game.yTiles - firstTileY;
        }
        for (int i = 0; i < widthInTiles; ++i) {
            for (int j = 0; j < heightInTiles; ++j) {
                if (this.spriteArray[firstTileY + j][firstTileX + i] != null) {
                    this.errorCode = 8;
                    throw new RuntimeException("Multiple sprites assigned to the same tile: line " + (firstTileY + j) + " column " + (firstTileX + i) + " " + this.spriteArray[firstTileY + j][firstTileX + i].toString() + " " + sprite.toString());
                }
                this.spriteArray[firstTileY + j][firstTileX + i] = sprite;
            }
        }
    }
    
    private void updateTilesWithCollisionData() {
        for (int i = 0; i < this.levelTiles.length; ++i) {
            for (int j = 0; j < this.levelTiles[0].length; ++j) {
                this.levelTiles[i][j].solid = this.collisionArray[i][j];
                if (this.tileAssociations[i][j] != null) {
                    this.levelTiles[i][j].rootXTile = this.tileAssociations[i][j].x;
                    this.levelTiles[i][j].rootYTile = this.tileAssociations[i][j].y;
                }
            }
        }
    }
    
    private void updateTilesWithSpriteData() {
        for (int i = 0; i < this.levelTiles.length; ++i) {
            for (int j = 0; j < this.levelTiles[0].length; ++j) {
                if (this.levelTiles[i][j].sprite == null) {
                    this.levelTiles[i][j].sprite = this.spriteArray[i][j];
                }
            }
        }
    }
    
    private void distributeWarps() {
        final LinkedList<Warp> warps = this.game.gameLoader.getLevelWarps(this.levelNumber);
        for (final Warp tempWarp : warps) {
            if (tempWarp.type != 0) {
                ((Warpable)this.levelTiles[tempWarp.yTile][tempWarp.xTile].sprite).setWarp(tempWarp);
            }
        }
    }
}
