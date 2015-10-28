// 
// Decompiled by Procyon v0.5.29
// 

package supermario.builder.itemPanels;

import java.awt.Component;
import supermario.game.Game;
import supermario.builder.Item;
import javax.swing.ImageIcon;
import supermario.builder.Button;
import supermario.builder.BuilderFrame;
import javax.swing.JPanel;

public final class EnemiesPanel extends JPanel implements ItemPanel
{
    private BuilderFrame frame;
    public Button lightGoomba;
    public Button darkGoomba;
    public Button grayGoomba;
    public Button lightKoopaNormal;
    public Button lightKoopaBouncing;
    public Button lightKoopaFlyingV;
    public Button lightKoopaFlyingH;
    public Button darkKoopaNormal;
    public Button darkKoopaBouncing;
    public Button darkKoopaFlyingV;
    public Button darkKoopaFlyingH;
    public Button redKoopaNormal;
    public Button redKoopaBouncing;
    public Button redKoopaFlyingV;
    public Button redKoopaFlyingH;
    public Button squid;
    public Button redFish;
    public Button grayFishStraight;
    public Button grayFishZigZag;
    public Button lightHammerBro;
    public Button darkHammerBro;
    public Button spiny;
    public Button lightBeetle;
    public Button darkBeetle;
    public Button grayBeetle;
    public Button shortCannon;
    public Button tallCannon;
    public Button cannonBase;
    public Button firebarShort;
    public Button firebarLong;
    public Button lavaBall;
    public boolean waterMode;
    
    public EnemiesPanel(final BuilderFrame frame) {
        this.frame = frame;
        this.init();
        this.setLandEnemies();
        this.waterMode = false;
        this.construct();
    }
    
    @Override
    public void init() {
        (this.lightGoomba = new Button(this.frame, this.frame.game.textures.lightGoomba1, null, "<html><center>Goomba</center></html>", "Basic enemy. Vulnerable and falls off cliffs")).setItem(new Item(this.frame, '$', "Light Goomba", this.lightGoomba, -1, 2, 0, 0, false, false, 3));
        (this.darkGoomba = new Button(this.frame, this.frame.game.textures.darkGoomba1, null, "<html><center>Goomba</center></html>", "Basic enemy. Vulnerable and falls off cliffs.")).setItem(new Item(this.frame, '%', "Dark Goomba", this.darkGoomba, -1, 2, 0, 0, false, false, 3));
        (this.grayGoomba = new Button(this.frame, this.frame.game.textures.grayGoomba1, null, "<html><center>Goomba</center></html>", "Basic enemy. Vulnerable and falls off cliffs")).setItem(new Item(this.frame, '\u02e6', "Gray Goomba", this.grayGoomba, -1, 2, 0, 0, false, false, 3));
        (this.lightKoopaNormal = new Button(this.frame, this.frame.game.textures.lightKoopa1, null, "<html><center>Koopa<br>Troopa</center><html>", "Shelled enemy that falls off cliffs.")).setItem(new Item(this.frame, '^', "Light Koopa Troopa", this.lightKoopaNormal, -1, 2, 0, 0, false, false, 3));
        (this.lightKoopaBouncing = new Button(this.frame, this.frame.game.textures.lightKoopa4, null, "<html><center>Bouncing</center></html>", "Bounces instead of walks.")).setItem(new Item(this.frame, '&', "Light Koopa Troopa Bouncing", this.lightKoopaBouncing, -1, 2, 0, 0, false, false, 3));
        (this.lightKoopaFlyingV = new Button(this.frame, this.frame.textures.iconLightKoopaFlyingV, this.frame.textures.displayLightKoopaFlyingV, "<html><center>Flying</center></html>", "Flys up and down repeatedly until smushed.")).setItem(new Item(this.frame, '*', "Light Koopa Troopa Flying Vertically", this.lightKoopaFlyingV, -1, 5, 0, 56, false, false, 13));
        (this.lightKoopaFlyingH = new Button(this.frame, this.frame.textures.iconLightKoopaFlyingH, this.frame.textures.displayLightKoopaFlyingH, "<html><center>Flying</center></html>", "Flys side to side repeatedly until smushed.")).setItem(new Item(this.frame, '¬', "Light Koopa Troopa Flying Horizontally", this.lightKoopaFlyingH, -1, 6, 48, 0, false, false, 13));
        (this.darkKoopaNormal = new Button(this.frame, this.frame.game.textures.darkKoopa1, null, "<html><center>Koopa<br>Troopa</center></html>", "Shelled enemy that falls off cliffs.")).setItem(new Item(this.frame, '(', "Dark Koopa Troopa", this.darkKoopaNormal, -1, 2, 0, 0, false, false, 3));
        (this.darkKoopaBouncing = new Button(this.frame, this.frame.game.textures.darkKoopa4, null, "<html><center>Bouncing</center></html>", "Bounces instead of walks.")).setItem(new Item(this.frame, ')', "Dark Koopa Troopa Bouncing", this.darkKoopaBouncing, -1, 2, 0, 0, false, false, 3));
        (this.darkKoopaFlyingV = new Button(this.frame, this.frame.textures.iconDarkKoopaFlyingV, this.frame.textures.displayDarkKoopaFlyingV, "<html><center>Flying</center></html>", "Flys up and down repeatedly until smushed.")).setItem(new Item(this.frame, '-', "Dark Koopa Troopa Flying Vertically", this.darkKoopaFlyingV, -1, 5, 0, 56, false, false, 13));
        (this.darkKoopaFlyingH = new Button(this.frame, this.frame.textures.iconDarkKoopaFlyingH, this.frame.textures.displayDarkKoopaFlyingH, "<html><center>Flying</center></html>", "Flys side to side repeatedly until smushed.")).setItem(new Item(this.frame, '±', "Dark Koopa Troopa Flying Horizontally", this.darkKoopaFlyingH, -1, 6, 48, 0, false, false, 13));
        (this.redKoopaNormal = new Button(this.frame, this.frame.game.textures.redKoopa1, null, "<html><center>Koopa<br>Troopa</center></html>", "Shelled enemy that turns around at cliff edges.")).setItem(new Item(this.frame, '_', "Red Koopa Troopa", this.redKoopaNormal, -1, 2, 0, 0, false, false, 3));
        (this.redKoopaBouncing = new Button(this.frame, this.frame.game.textures.redKoopa4, null, "<html><center>Bouncing</center></html>", "Bounces instead of walks.")).setItem(new Item(this.frame, '=', "Red Koopa Troopa Bouncing", this.redKoopaBouncing, -1, 2, 0, 0, false, false, 3));
        (this.redKoopaFlyingV = new Button(this.frame, this.frame.textures.iconRedKoopaFlyingV, this.frame.textures.displayRedKoopaFlyingV, "<html><center>Flying</center></html>", "Flys up and down repeatedly until smushed.")).setItem(new Item(this.frame, '+', "Red Koopa Troopa Flying Vertically", this.redKoopaFlyingV, -1, 5, 0, 56, false, false, 13));
        (this.redKoopaFlyingH = new Button(this.frame, this.frame.textures.iconRedKoopaFlyingH, this.frame.textures.displayRedKoopaFlyingH, "<html><center>Flying</center></html>", "Flys side to side repeatedly until smushed")).setItem(new Item(this.frame, '®', "Red Koopa Troopa Flying Horizontally", this.redKoopaFlyingH, -1, 6, 48, 0, false, false, 13));
        (this.squid = new Button(this.frame, this.frame.game.textures.squid1, null, null, "Follows Mario if he is nearby.")).setItem(new Item(this.frame, '[', "Bloober", this.squid, -1, 2, 0, 0, false, false, 1));
        (this.redFish = new Button(this.frame, this.frame.game.textures.redFish1, null, "<html><center>Cheep Cheep</center></html>", "Faster than normal fish. Always swims in a straight line.")).setItem(new Item(this.frame, '{', "Red Cheep Cheep", this.redFish, -1, 2, 0, 0, false, false, 3));
        (this.grayFishStraight = new Button(this.frame, this.frame.game.textures.grayFish1, null, null, "Slow swimmer. Swims in a straight line.")).setItem(new Item(this.frame, ']', "Cheep Cheep", this.grayFishStraight, -1, 2, 0, 0, false, false, 3));
        (this.grayFishZigZag = new Button(this.frame, this.frame.textures.displayGrayFishZigZag, null, "<html><center>Zig Zag<br>Cheep Cheep</center></html>", "Zig zags up and down while swimming.")).setItem(new Item(this.frame, '|', "Gray Zig Zag Cheep Cheep", this.grayFishZigZag, -1, 14, 0, 14, false, false, 3));
        (this.lightHammerBro = new Button(this.frame, this.frame.game.textures.lightHammerBro1, this.frame.textures.displayLightHammerBro, "<html><center>Hammer<br>Bro.</center></html>", "Jumps up and down on platforms within reach while throwing hammers.")).setItem(new Item(this.frame, '}', "Hammer Bro.", this.lightHammerBro, -1, 21, 16, 0, false, false, 1));
        (this.darkHammerBro = new Button(this.frame, this.frame.game.textures.darkHammerBro1, this.frame.textures.displayDarkHammerBro, "<html><center>Hammer<br>Bro.</center></html>", "Jumps up and down on platforms within reach while throwing hammers.")).setItem(new Item(this.frame, '\u00f6', "Hammer Bro.", this.darkHammerBro, -1, 21, 16, 0, false, false, 1));
        (this.spiny = new Button(this.frame, this.frame.game.textures.spiny2, null, null, "Cannot be smushed.")).setItem(new Item(this.frame, '\\', "Spiny", this.spiny, -1, 2, 0, 0, false, false, 3));
        (this.lightBeetle = new Button(this.frame, this.frame.game.textures.lightBeetle1, null, "<html><center>Buzzy<br>Beetle</center></html>", "Hard-shelled enemy. Can't be hurt by fireballs.")).setItem(new Item(this.frame, ':', "Light Buzzy Beetle", this.lightBeetle, -1, 2, 0, 0, false, false, 3));
        (this.darkBeetle = new Button(this.frame, this.frame.game.textures.darkBeetle1, null, "<html><center>Buzzy<br>Beetle</center></html>", "Hard-shelled enemy. Can't be hurt by fireballs.")).setItem(new Item(this.frame, '\u00f4', "Dark Buzzy Beetle", this.darkBeetle, -1, 2, 0, 0, false, false, 3));
        (this.grayBeetle = new Button(this.frame, this.frame.game.textures.grayBeetle1, null, "<html><center>Buzzy<br>Beetle</center></html>", "Hard-shelled enemy. Can't be hurt by fireballs.")).setItem(new Item(this.frame, '\u02e7', "Gray Buzzy Beetle", this.grayBeetle, -1, 2, 0, 0, false, false, 3));
        (this.shortCannon = new Button(this.frame, this.frame.game.textures.lightShortCannon, null, "<html><center>Bill<br>Blaster</center></html>", "Fires Bullet Bills at a random time interval determined randomly with each level.")).setItem(new Item(this.frame, '\"', "Short Bill Blaster", this.shortCannon, -1, 2, 0, 0, false, true, 1));
        (this.tallCannon = new Button(this.frame, this.frame.game.textures.lightTallCannon, null, "<html><center>Bill<br>Blaster</center></html>", "Fires Bullet Bills at a random time interval determined randomly with each level.")).setItem(new Item(this.frame, '\'', "Tall Bill Blaster", this.tallCannon, -1, 2, 0, 0, false, true, 1));
        (this.cannonBase = new Button(this.frame, this.frame.game.textures.lightCannonBase, null, "<html><center>Billl<br>Blaster<br>Base</center></html>", "A base for the Bill Blaster to increase in height.")).setItem(new Item(this.frame, '\u260e', "Cannon Base", this.cannonBase, -1, 2, 0, 0, true, true, 1));
        (this.firebarShort = new Button(this.frame, this.frame.textures.displayFirebarLightShort, null, "<html><center>Short</center></html>", "Rotates 6 fireballs and can hurt other enemies.")).setItem(new Item(this.frame, ',', "Short Firebar", this.firebarShort, -1, 4, 0, 48, false, true, 9));
        (this.firebarLong = new Button(this.frame, this.frame.textures.displayFirebarLightShort, this.frame.textures.displayFirebarLightLong, "<html><center>Long</center></html>", "Rotates 12 fireballs and can hurt other enemies.")).setItem(new Item(this.frame, '<', "Long Firebar", this.firebarLong, -1, 4, 0, 96, false, true, 9));
        (this.lavaBall = new Button(this.frame, this.frame.game.textures.lavaball1, null, null, "Flys up at random time intervals and at random heights. Doesn't hurt other enemies.")).setItem(new Item(this.frame, '.', "Podoboo", this.lavaBall, Game.yTiles - 2, 2, 0, 0, false, false, 11));
    }
    
    @Override
    public void construct() {
        if (this.waterMode) {
            this.add(this.redFish);
            this.add(this.grayFishStraight);
            this.add(this.grayFishZigZag);
            this.add(this.squid);
            this.add(this.firebarShort);
            this.add(this.firebarLong);
            this.add(this.shortCannon);
            this.add(this.tallCannon);
            this.add(this.cannonBase);
        }
        else {
            this.add(this.lightGoomba);
            this.add(this.darkGoomba);
            this.add(this.grayGoomba);
            this.add(this.lightHammerBro);
            this.add(this.spiny);
            this.add(this.lightKoopaNormal);
            this.add(this.lightKoopaBouncing);
            this.add(this.lightKoopaFlyingV);
            this.add(this.lightKoopaFlyingH);
            this.add(this.lightBeetle);
            this.add(this.darkBeetle);
            this.add(this.grayBeetle);
            this.add(this.darkHammerBro);
            this.add(this.lavaBall);
            this.add(this.darkKoopaNormal);
            this.add(this.darkKoopaBouncing);
            this.add(this.darkKoopaFlyingV);
            this.add(this.darkKoopaFlyingH);
            this.add(this.shortCannon);
            this.add(this.tallCannon);
            this.add(this.cannonBase);
            this.add(this.firebarShort);
            this.add(this.firebarLong);
            this.add(this.redKoopaNormal);
            this.add(this.redKoopaBouncing);
            this.add(this.redKoopaFlyingV);
            this.add(this.redKoopaFlyingH);
        }
        this.repaint();
    }
    
    @Override
    public void refreshIcons() {
    }
    
    public void setFirebarScheme() {
        if (this.frame.levelPanel.level == null || this.frame.levelPanel.level.levelType == 5 || this.frame.levelPanel.level.levelType == 6 || this.frame.levelPanel.level.levelType == 0 || this.frame.levelPanel.level.levelType == 4) {
            this.firebarShort.setImages(this.frame.textures.displayFirebarLightShort, null);
            this.firebarLong.setImages(this.frame.textures.displayFirebarLightShort, this.frame.textures.displayFirebarLightLong);
        }
        else if (this.frame.levelPanel.level.levelType == 2) {
            this.firebarShort.setImages(this.frame.textures.displayFirebarStoneShort, null);
            this.firebarLong.setImages(this.frame.textures.displayFirebarStoneShort, this.frame.textures.displayFirebarStoneLong);
        }
        else if (this.frame.levelPanel.level.levelType == 1) {
            this.firebarShort.setImages(this.frame.textures.displayFirebarDarkShort, null);
            this.firebarLong.setImages(this.frame.textures.displayFirebarDarkShort, this.frame.textures.displayFirebarDarkLong);
        }
    }
    
    public void setCannonScheme() {
        if (this.frame.levelPanel.level == null || this.frame.levelPanel.level.levelType == 5 || this.frame.levelPanel.level.levelType == 6 || this.frame.levelPanel.level.levelType == 0 || this.frame.levelPanel.level.levelType == 4) {
            this.shortCannon.setImages(this.frame.game.textures.lightShortCannon, null);
            this.tallCannon.setImages(this.frame.game.textures.lightTallCannon, null);
            this.cannonBase.setImages(this.frame.game.textures.lightCannonBase, null);
        }
        else if (this.frame.levelPanel.level.levelType == 2) {
            this.shortCannon.setImages(this.frame.game.textures.stoneShortCannon, null);
            this.tallCannon.setImages(this.frame.game.textures.stoneTallCannon, null);
            this.cannonBase.setImages(this.frame.game.textures.stoneCannonBase, null);
        }
        else if (this.frame.levelPanel.level.levelType == 1) {
            this.shortCannon.setImages(this.frame.game.textures.darkShortCannon, null);
            this.tallCannon.setImages(this.frame.game.textures.darkTallCannon, null);
            this.cannonBase.setImages(this.frame.game.textures.darkCannonBase, null);
        }
    }
    
    public final void setWaterEnemies() {
        this.switchMode(true);
        this.firebarShort.setImages(this.frame.textures.displayFirebarWaterShort, null);
        this.firebarLong.setImages(this.frame.textures.displayFirebarWaterShort, this.frame.textures.displayFirebarWaterLong);
    }
    
    public final void setLandEnemies() {
        this.switchMode(false);
    }
    
    private void switchMode(final boolean waterMode) {
        this.waterMode = waterMode;
        this.removeAll();
        this.construct();
        this.repaint();
        this.frame.repaint();
    }
    
    public boolean isLandEnemyOnly(final Item i) {
        return i.button == this.lightGoomba || i.button == this.darkGoomba || i.button == this.grayGoomba || (i.button == this.lightKoopaNormal || i.button == this.lightKoopaBouncing || i.button == this.lightKoopaFlyingV) || (i.button == this.darkKoopaNormal || i.button == this.darkKoopaBouncing || i.button == this.darkKoopaFlyingV) || (i.button == this.redKoopaNormal || i.button == this.redKoopaBouncing || i.button == this.redKoopaFlyingV) || (i.button == this.lightHammerBro || i.button == this.spiny || i.button == this.lightBeetle || i.button == this.darkBeetle || i.button == this.grayBeetle || i.button == this.lavaBall);
    }
    
    public boolean isWaterAndLandEnemy(final Item i) {
        return i.button == this.shortCannon || i.button == this.tallCannon || i.button == this.firebarShort || i.button == this.firebarLong;
    }
}
