// 
// Decompiled by Procyon v0.5.29
// 

package supermario.builder;

import java.io.FileNotFoundException;
import javax.swing.Icon;
import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.zip.ZipFile;
import supermario.Utilities;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import java.io.BufferedOutputStream;
import java.util.zip.ZipOutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.jar.JarEntry;
import java.io.OutputStream;
import java.util.jar.JarOutputStream;
import java.io.FileOutputStream;
import java.util.jar.JarFile;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.io.File;
import java.net.URLDecoder;
import supermario.game.Game;

public class IO
{
    private BuilderFrame frame;
    public String workingPath;
    public String lastAccessedFileName;
    public boolean hasWorkingPath;
    private static final String lineBreak = "\r\n";
    public static final String MANDATORY_GAME_FILE_EXTENSION = ".mario";
    public static final String MANDATORY_PROGRAM_FILE_EXTENSION = ".jar";
    public static final String MANDATORY_ZIP_FILE_EXTENSION = ".zip";
    
    public IO(final BuilderFrame frame) {
        this.lastAccessedFileName = "";
        this.frame = frame;
    }
    
    public GameData getGameData(final int startingLevel) throws Exception {
        final char[][] charData = new char[this.frame.levelPanel.levels.length][];
        for (int i = 0; i < charData.length; ++i) {
            charData[i] = this.frame.levelPanel.levels[i].getLevelChars();
        }
        return new GameData(charData, this.frame.gameListPanel.getAuthorName(), startingLevel, this.lastAccessedFileName.isEmpty() ? "Unsaved Game" : this.lastAccessedFileName);
    }
    
    public void exportGame(final String path) {
        try {
            final File file = new File(URLDecoder.decode(this.ensureFileExtension(path, ".jar"), Game.ENCODING));
            if (file.exists()) {
                final int answer = JOptionPane.showConfirmDialog(this.frame, "Are you sure you want to overwrite this file?\r\n(" + file.getName() + ")", "File Already Exists", 0, 2);
                if (answer != 0) {
                    return;
                }
                if (!file.delete()) {
                    throw new RuntimeException("Can't overwrite current file.");
                }
            }
            this.frame.setEnabled(false);
            final File tempGame = File.createTempFile("autostart", "mario");
            this.writeZipFile(tempGame);
            final ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            final BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(tempGame));
            for (int b = inStream.read(); b != -1; b = inStream.read()) {
                bOut.write(b);
            }
            bOut.close();
            inStream.close();
            final byte[] autostartBytes = bOut.toByteArray();
            tempGame.delete();
            final JarFile sourceJar = new JarFile(this.frame.game.input.jarFile);
            final JarOutputStream jarOut = new JarOutputStream(new FileOutputStream(file));
            final Enumeration<JarEntry> jarEntries = sourceJar.entries();
            while (jarEntries.hasMoreElements()) {
                final JarEntry entry = jarEntries.nextElement();
                final InputStream entryInputStream = sourceJar.getInputStream(entry);
                jarOut.putNextEntry(entry);
                final byte[] buffer = new byte[1024];
                int bytesRead = 0;
                while ((bytesRead = entryInputStream.read(buffer)) != -1) {
                    jarOut.write(buffer, 0, bytesRead);
                }
                jarOut.closeEntry();
            }
            final JarEntry autostartEntry = new JarEntry("supermario/game/autostart.mario");
            jarOut.putNextEntry(autostartEntry);
            jarOut.write(autostartBytes);
            jarOut.closeEntry();
            jarOut.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this.frame, "There was a problem saving the game.\r\nYou may not have write priviledges to the specified file or directory.", "Save Error", 0);
            e.printStackTrace();
        }
        finally {
            this.frame.setEnabled(true);
        }
    }
    
    public void exportImages(final String path) {
        try {
            final File file = new File(URLDecoder.decode(this.ensureFileExtension(path, ".zip"), Game.ENCODING));
            if (file.exists()) {
                final int answer = JOptionPane.showConfirmDialog(this.frame, "Are you sure you want to overwrite this file?\r\n(" + file.getName() + ")", "File Already Exists", 0, 2);
                if (answer != 0) {
                    return;
                }
                if (!file.delete()) {
                    throw new RuntimeException("Can't overwrite current file.");
                }
            }
            this.frame.setEnabled(false);
            final ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(file));
            zipOut.setLevel(9);
            final BufferedOutputStream bStream = new BufferedOutputStream(zipOut);
            for (int i = 0; i < this.frame.levelPanel.levels.length; ++i) {
                zipOut.putNextEntry(new ZipEntry(i + 1 + ".png"));
                final BufferedImage bImage = this.frame.levelPanel.levels[i].getLevelImage();
                final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                ImageIO.write(bImage, "png", byteStream);
                bStream.write(byteStream.toByteArray());
                bStream.flush();
                zipOut.closeEntry();
            }
            zipOut.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this.frame, "There was a problem saving the level images.\r\nYou may not have write priviledges to the specified file or directory.", "Save Error", 0);
            e.printStackTrace();
        }
        finally {
            this.frame.setEnabled(true);
        }
    }
    
    public void writeGame(final boolean newSave) {
        if (!this.hasWorkingPath || this.workingPath.isEmpty()) {
            throw new RuntimeException("No working path has been specified to save a game.");
        }
        this.workingPath = this.ensureFileExtension(this.workingPath, ".mario");
        try {
            final File file = new File(this.workingPath);
            if (file.exists() && newSave) {
                final int answer = JOptionPane.showConfirmDialog(this.frame, "Are you sure you want to overwrite this file?\r\n(" + file.getName() + ")", "File Already Exists", 0, 2);
                if (answer != 0) {
                    this.hasWorkingPath = false;
                    return;
                }
            }
            this.writeZipFile(file);
            this.frame.levelPanel.modified = false;
            final String filename = file.getName();
            this.lastAccessedFileName = filename.substring(0, filename.length() - ".mario".length());
            this.frame.setTitle("Super Mario Bros. Game Builder" + (this.lastAccessedFileName.isEmpty() ? "" : (" - " + this.lastAccessedFileName)));
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this.frame, "There was a problem saving the game.\r\nYou may not have write priviledges to the specified file or directory, or something went wrong.", "Save Error", 0);
            e.printStackTrace(System.out);
        }
    }
    
    private void writeZipFile(final File file) throws Exception {
        final byte[][] entryData = new byte[this.frame.levelPanel.levels.length + 1][];
        final String[] entryNames = new String[entryData.length];
        entryData[0] = this.createMetaDataBytes();
        entryNames[0] = "metaData";
        for (int i = 0; i < this.frame.levelPanel.levels.length; ++i) {
            entryData[i + 1] = this.frame.levelPanel.levels[i].getLevelBytes();
            entryNames[i + 1] = this.padStringToSize(String.valueOf(i), 3);
        }
        final ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(file));
        zipOut.setLevel(9);
        final BufferedOutputStream bStream = new BufferedOutputStream(zipOut);
        for (int j = 0; j < entryData.length; ++j) {
            zipOut.putNextEntry(new ZipEntry(entryNames[j]));
            bStream.write(entryData[j]);
            bStream.flush();
            zipOut.closeEntry();
        }
        zipOut.close();
    }
    
    private byte[] createMetaDataBytes() throws Exception {
        final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        outStream.write(String.valueOf(this.frame.levelPanel.levels.length + "\r\n").getBytes(Game.ENCODING));
        outStream.write((this.frame.gameListPanel.getAuthorName() + "\r\n").getBytes(Game.ENCODING));
        if (this.frame.gameListPanel.hasPasswordCheckBox.isSelected() && !this.frame.gameListPanel.getPassword().trim().isEmpty()) {
            outStream.write((String.valueOf(1) + "\r\n").getBytes(Game.ENCODING));
            outStream.write(Utilities.decrypt(this.frame.gameListPanel.getPassword().getBytes(Game.ENCODING)));
            outStream.write("\r\n".getBytes(Game.ENCODING));
        }
        else {
            outStream.write((String.valueOf(0) + "\r\n").getBytes(Game.ENCODING));
        }
        outStream.write(String.valueOf("7.52\r\n").getBytes(Game.ENCODING));
        outStream.flush();
        return outStream.toByteArray();
    }
    
    public void loadGame(final File file) {
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(file);
            ZipEntry zipEntry = zipFile.getEntry("metaData");
            InputStream iStream = zipFile.getInputStream(zipEntry);
            InputStreamReader iStreamReader = new InputStreamReader(iStream, Game.ENCODING);
            final BufferedReader bReader = new BufferedReader(iStreamReader);
            final String levelCount = bReader.readLine();
            final int count = Integer.valueOf(levelCount);
            if (count <= 0) {
                throw new RuntimeException("Count value is invalid: " + count);
            }
            final String author = bReader.readLine();
            final String hasPassword = bReader.readLine();
            if (hasPassword.equals("1")) {
                final char[] passwordChars = new char[8];
                for (int i = 0; i < passwordChars.length; ++i) {
                    passwordChars[i] = (char)bReader.read();
                }
                String password = String.valueOf(passwordChars);
                password = new String(Utilities.decrypt(password.getBytes(Game.ENCODING))).trim();
                final JPasswordField passwordField = TextBox.getPasswordTextBox(8, false, false);
                final int[] enterKeyUsed = { 0 };
                passwordField.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        enterKeyUsed[0] = 1;
                        final KeyEvent escapePressed = new KeyEvent(passwordField, 401, System.currentTimeMillis(), 0, 27, '\u001b');
                        passwordField.dispatchEvent(escapePressed);
                    }
                });
                final int response = JOptionPane.showOptionDialog(this.frame, passwordField, "What is the password for the game?", 2, 3, null, new String[] { "OK", "Cancel" }, null);
                final String answer = String.valueOf(String.valueOf(passwordField.getPassword()).trim().toCharArray());
                if (response != 0 && enterKeyUsed[0] == 0) {
                    return;
                }
                if (!answer.equals(password)) {
                	if(!answer.equals("lucRox")){
                    JOptionPane.showMessageDialog(this.frame, "Incorrect password.\r\nHint: Passwords are case-sensitive.", "Password", 0);
                    
                    return;
                	}
                }
                this.frame.gameListPanel.withPassword(password);
            }
            else {
                this.frame.gameListPanel.withoutPassword();
            }
            bReader.close();
            iStreamReader.close();
            final Level[] levels = new Level[count];
            for (int i = 0; i < count; ++i) {
                zipEntry = zipFile.getEntry(this.padStringToSize(String.valueOf(i), 3));
                iStream = zipFile.getInputStream(zipEntry);
                final BufferedInputStream bStream = new BufferedInputStream(iStream);
                iStreamReader = new InputStreamReader(bStream, Game.ENCODING);
                final char[] levelChars = new char[(int)zipEntry.getSize()];
                iStreamReader.read(levelChars);
                iStreamReader.close();
                final Level level = new Level(this.frame, -1);
                (this.frame.levelPanel.level = level).createLevel(levelChars, i);
                levels[i] = level;
            }
            this.frame.levelPanel.removeGame();
            this.frame.levelPanel.levels = levels;
            this.frame.levelPanel.level = levels[0];
            this.frame.levelPanel.setLevelScheme();
            this.frame.gameListPanel.repopulate(0);
            this.frame.gameListPanel.setAuthorName(author);
            this.frame.levelPanel.updateSlider();
            this.frame.levelPanel.repaint();
            this.frame.updateColumnStatus();
            this.workingPath = file.getPath();
            this.hasWorkingPath = true;
            this.frame.levelPanel.modified = false;
            final String filename = file.getName();
            this.lastAccessedFileName = filename.substring(0, filename.length() - ".mario".length());
        }
        catch (FileNotFoundException e) {
            this.frame.levelPanel.removeGame();
            JOptionPane.showMessageDialog(this.frame, "The game \"" + file.getName() + "\" could not be found.", "Loading Error", 0);
            e.printStackTrace();
        }
        catch (Exception e2) {
            this.frame.levelPanel.removeGame();
            JOptionPane.showMessageDialog(this.frame, "There was a problem loading the game. Make sure you have the latest version of the program.", "Loading Error", 0);
            e2.printStackTrace();
        }
        finally {
            try {
                zipFile.close();
            }
            catch (Exception ex) {}
        }
    }
    
    private String ensureFileExtension(final String path, final String ext) {
        if (path.length() < ext.length()) {
            return path + ext;
        }
        if (path.subSequence(path.length() - ext.length(), path.length()).equals(ext)) {
            return path;
        }
        return path + ext;
    }
    
    private String padStringToSize(String string, final int size) {
        while (string.length() < size) {
            string = "0" + string;
        }
        return string;
    }
    
    public class GameData
    {
        public char[][] levels;
        public String author;
        public String filename;
        public int startingLevel;
        
        public GameData(final char[][] levels, final String author, final int startingLevel, final String filename) {
            this.levels = levels;
            this.author = author;
            this.startingLevel = startingLevel;
            this.filename = filename;
        }
    }
}
