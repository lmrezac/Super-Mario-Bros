// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import supermario.builder.IO;

public final class GameLoader
{
    public static final String metaDataFileName = "metaData";
    private LevelData[] levelDataArray;
    private int levelsCount;
    private boolean validGame;
    private String gameAuthor;
    public static final int AUTHOR_MAX_CHARS = 20;
    
    public GameLoader(final String filename) {
        this.validGame = true;
        this.gameAuthor = "ANONYMOUS";
        this.loadGame(filename);
    }
    
    public GameLoader(final IO.GameData gameData) throws Exception {
        this.gameAuthor = gameData.author;
        this.levelsCount = gameData.levels.length;
        this.levelDataArray = new LevelData[gameData.levels.length];
        for (int i = 0; i < gameData.levels.length; ++i) {
            this.levelDataArray[i] = new LevelData(i, gameData.levels[i]);
        }
    }
    
    public int levelCount() {
        return this.levelsCount;
    }
    
    public boolean validGame() {
        return this.validGame;
    }
    
    public String getGameAuthor() {
        return this.gameAuthor;
    }
    
    public void loadGame(final String filename) {
        try {
            final ZipFile zipFile = new ZipFile(filename);
            this.loadMetaData(zipFile);
            if (!this.validGame) {
                return;
            }
            this.levelDataArray = new LevelData[this.levelsCount];
            for (int i = 0; i < this.levelDataArray.length; ++i) {
                this.levelDataArray[i] = this.getLevelData(zipFile, i);
            }
            zipFile.close();
        }
        catch (Exception e) {
            this.validGame = false;
            e.printStackTrace();
        }
    }
    
    public LevelLoader loadLevel(final Game game, final int levelNumber) {
        if (levelNumber < 0 || levelNumber >= this.levelDataArray.length) {
            throw new RuntimeException("Level request outside bounds of game: " + levelNumber);
        }
        return new LevelLoader(game, this.levelDataArray[levelNumber].levelChars, levelNumber);
    }
    
    public LinkedList<Warp> getLevelWarps(final int levelNumber) {
        return this.levelDataArray[levelNumber].warps;
    }
    
    private void loadMetaData(final ZipFile zipFile) {
        try {
            final ZipEntry zipEntry = zipFile.getEntry("metaData");
            final InputStream iStream = zipFile.getInputStream(zipEntry);
            final InputStreamReader inputStreamReader = new InputStreamReader(iStream, Game.ENCODING);
            final BufferedReader bReader = new BufferedReader(inputStreamReader);
            final String levelCount = bReader.readLine();
            this.levelsCount = Integer.valueOf(levelCount);
            final String author = bReader.readLine().trim();
            if (author.length() > 0) {
                this.gameAuthor = author;
            }
            else if (author.length() > 20) {
                this.gameAuthor = author.substring(0, 20);
            }
            bReader.close();
            inputStreamReader.close();
        }
        catch (Exception e) {
            this.validGame = false;
            e.printStackTrace();
        }
    }
    
    private LevelData getLevelData(final ZipFile zipFile, final int levelNumber) {
        if (levelNumber < 0 || levelNumber >= this.levelDataArray.length) {
            this.validGame = false;
            throw new RuntimeException("Level request outside bounds of game: " + levelNumber);
        }
        LevelData levelData = null;
        try {
            final String levelName = this.getLevelName(levelNumber);
            final ZipEntry zipEntry = zipFile.getEntry(levelName);
            final char[] levelChars = new char[(int)zipEntry.getSize()];
            final InputStream iStream = zipFile.getInputStream(zipEntry);
            final BufferedInputStream bStream = new BufferedInputStream(iStream);
            final InputStreamReader inputStreamReader = new InputStreamReader(bStream, Game.ENCODING);
            inputStreamReader.read(levelChars);
            inputStreamReader.close();
            bStream.close();
            iStream.close();
            levelData = new LevelData(levelNumber, levelChars);
        }
        catch (Exception e) {
            this.validGame = false;
            e.printStackTrace();
        }
        return levelData;
    }
    
    private String getLevelName(final int levelNum) {
        String levelName;
        for (levelName = String.valueOf(levelNum); levelName.length() < 3; levelName = "0" + levelName) {}
        return levelName;
    }
    
    private class LevelData
    {
        public char[] levelChars;
        private LinkedList<Warp> warps;
        private int levelNumber;
        
        public LevelData(final int levelNumber, final char[] level) throws Exception {
            this.levelNumber = levelNumber;
            this.levelChars = level;
            this.findWarps();
        }
        
        private void findWarps() throws Exception {
            this.warps = new LinkedList<Warp>();
            for (int lineLength = this.getLineLength(), i = 0; i < (lineLength + 2) * Game.yTiles; ++i) {
                if (this.isPossibleWarp(this.levelChars[i])) {
                    this.handlePossibleWarp(i, lineLength);
                }
            }
        }
        
        private boolean isPossibleWarp(final char c) {
            return LevelLoader.isBowserEnding(c) || (c == '\u2665' || c == '\u21a8') || c == 'k' || LevelLoader.isSideOpeningPipe(c) || (c == '~' || c == ';' || c == '\u0108') || (c == '\u0110') || LevelLoader.isBeanstalkBlock(c) || c == '\u03e4' || c == '½';
        }
        
        private void handlePossibleWarp(final int location, final int lineLength) throws Exception {
            int offset = 0;
            final int nl = lineLength + 2;
            int type = -1;
            if (this.levelChars[location] == '!' || this.levelChars[location] == '\u2021') {
                type = 1;
            }
            else if (this.levelChars[location] == '\u2663' || this.levelChars[location] == '\u00ee') {
                type = 2;
            }
            else if (this.levelChars[location] == '~' || this.levelChars[location] == ';') {
                type = 3;
            }
            else if (this.levelChars[location] == '\u0110') {
                type = 4;
            }
            else if (this.levelChars[location] == '\u0108') {
                type = 5;
            }
            else if (LevelLoader.isBowserEnding(this.levelChars[location]) || this.levelChars[location] == '\u21a8' || this.levelChars[location] == '\u2665') {
                type = 6;
            }
            else if (this.levelChars[location] == 'k') {
                type = 0;
            }
            else if (LevelLoader.isBeanstalkBlock(this.levelChars[location])) {
                type = 7;
            }
            else if (this.levelChars[location] == '\u03e4') {
                type = 8;
            }
            else {
                if (this.levelChars[location] != '½') {
                    throw new RuntimeException("Invalid warp type being handled as warp: " + this.levelChars[location] + " at " + location);
                }
                type = 9;
            }
            if (type == 0) {
                this.warps.add(new Warp(false, false, this.levelNumber, 0, -1, -1, this.getXTileFromLocation(location, nl), this.getYTileFromLocation(location, nl), type));
            }
            else if (type == 7) {
                final char[] destLevelChars = { this.levelChars[location - nl * 3], this.levelChars[location - nl * 2], this.levelChars[location - nl] };
                final char[] destIDChars = { this.levelChars[location - nl * 3 + 1], this.levelChars[location - nl * 2 + 1], this.levelChars[location - nl + 1] };
                final int destLevelNum = Integer.valueOf(String.valueOf(destLevelChars));
                final int destIDNum = Integer.valueOf(String.valueOf(destIDChars));
                this.warps.add(new Warp(true, false, this.levelNumber, -1, destLevelNum, destIDNum, this.getXTileFromLocation(location, nl), this.getYTileFromLocation(location, nl), type));
            }
            else if (type == 8) {
                final char[] warpIDChars = { this.levelChars[location + nl * 1], this.levelChars[location + nl * 2], this.levelChars[location + nl * 3] };
                final int warpIDNum = Integer.valueOf(String.valueOf(warpIDChars));
                this.warps.add(new Warp(false, true, this.levelNumber, warpIDNum, -1, -1, this.getXTileFromLocation(location, nl), this.getYTileFromLocation(location, nl), type));
            }
            else if (type == 9) {
                final char[] warpIDChars = { this.levelChars[location + nl * 1], this.levelChars[location + nl * 2], this.levelChars[location + nl * 3] };
                final int warpIDNum = Integer.valueOf(String.valueOf(warpIDChars));
                this.warps.add(new Warp(false, true, this.levelNumber, warpIDNum, -1, -1, this.getXTileFromLocation(location, nl), this.getYTileFromLocation(location, nl), type));
            }
            else {
                if (LevelLoader.isSideOpeningPipe(this.levelChars[location])) {
                    offset = nl - 1;
                }
                final char[] warpIDChars = { this.levelChars[offset + location + 1], this.levelChars[offset + location + 2], this.levelChars[offset + location + 3] };
                final char[] destLevelChars2 = { this.levelChars[offset + location + 1 + nl], this.levelChars[offset + location + 2 + nl], this.levelChars[offset + location + 3 + nl] };
                final char[] destIDChars2 = { this.levelChars[offset + location + 1 + 2 * nl], this.levelChars[offset + location + 2 + 2 * nl], this.levelChars[offset + location + 3 + 2 * nl] };
                boolean outgoing = false;
                boolean incoming = false;
                int warpIDNum2;
                if (warpIDChars[0] == 'x') {
                    incoming = false;
                    warpIDNum2 = -1;
                }
                else {
                    if (type != 5) {
                        incoming = true;
                    }
                    warpIDNum2 = Integer.valueOf(String.valueOf(warpIDChars));
                }
                int destLevelNum2;
                int destIDNum2;
                if (destLevelChars2[0] == 'x') {
                    outgoing = false;
                    destLevelNum2 = -1;
                    destIDNum2 = -1;
                }
                else {
                    outgoing = true;
                    destLevelNum2 = Integer.valueOf(String.valueOf(destLevelChars2));
                    destIDNum2 = Integer.valueOf(String.valueOf(destIDChars2));
                }
                if (outgoing || incoming) {
                    final int xTile = this.getXTileFromLocation(location, nl);
                    final int yTile = this.getYTileFromLocation(location, nl);
                    this.warps.add(new Warp(outgoing, incoming, this.levelNumber, warpIDNum2, destLevelNum2, destIDNum2, xTile, yTile, type));
                }
            }
        }
        
        private int getXTileFromLocation(final int location, final int nl) {
            return location % nl;
        }
        
        private int getYTileFromLocation(final int location, final int nl) {
            return location / nl;
        }
        
        private int getLineLength() {
            int lineLength = -1;
            for (int i = 0; i < this.levelChars.length; ++i) {
                if (this.levelChars[i] == '\r') {
                    lineLength = i;
                    break;
                }
            }
            if (lineLength == -1) {
                GameLoader.this.validGame = false;
                try {
                    throw new RuntimeException("Line length is invalid. Can't find warps");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return lineLength;
        }
    }
}
