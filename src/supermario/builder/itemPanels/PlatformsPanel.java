// 
// Decompiled by Procyon v0.5.29
// 

package supermario.builder.itemPanels;

import java.awt.Component;
import javax.swing.ImageIcon;
import supermario.builder.Item;
import supermario.builder.Button;
import supermario.builder.BuilderFrame;
import javax.swing.JPanel;

public class PlatformsPanel extends JPanel implements ItemPanel
{
    private BuilderFrame frame;
    public Button longRepUp;
    public Button longRepDown;
    public Button longOscV;
    public Button longOscH;
    public Button longFalling;
    public Button longPulley;
    public Button shortRepUp;
    public Button shortRepDown;
    public Button shortOscV;
    public Button shortOscH;
    public Button shortFalling;
    public Button shortPulley;
    public Button extraShortRepUp;
    public Button extraShortRepDown;
    public Button carrierLong;
    public Button carrierShort;
    
    public PlatformsPanel(final BuilderFrame frame) {
        this.frame = frame;
        this.init();
        this.construct();
    }
    
    @Override
    public void init() {
        (this.longRepUp = new Button(this.frame, this.frame.textures.iconPlatformLongRepUp, this.frame.textures.displayPlatformLongRepUp, "<html><center>Long<br>Rep. Up</center></html>", "A long set of platforms that repeatedly move up.")).setItem(new Item(this.frame, '>', "Long Platform Rep. Up", this.longRepUp, 0, 3, 0, 0, false, true, 10));
        (this.longRepDown = new Button(this.frame, this.frame.textures.iconPlatformLongRepDown, this.frame.textures.displayPlatformLongRepDown, "<html><center>Long<br>Rep. Down</center></html>", "A long set of platforms that repeatedly move down.")).setItem(new Item(this.frame, '/', "Long Platform Rep. Down", this.longRepDown, 0, 3, 0, 0, false, true, 10));
        (this.longOscV = new Button(this.frame, this.frame.textures.iconPlatformLongOscV, this.frame.textures.displayPlatformLongOscV, "<html><center>Long<br>Osc. Vert.</center></html>", "A long platform that oscillates vertically.")).setItem(new Item(this.frame, '?', "Long Platform Osc. Vert.", this.longOscV, -1, 24, 0, 72, false, true, 13));
        (this.longOscH = new Button(this.frame, this.frame.textures.iconPlatformLongOscH, this.frame.textures.displayPlatformLongOscH, "<html><center>Long<br>Osc. Horiz.</center></html>", "A long platform that oscillates horizontally.")).setItem(new Item(this.frame, '\u263a', "Long Platform Osc. Horiz.", this.longOscH, -1, 7, 48, 0, false, true, 13));
        (this.longFalling = new Button(this.frame, this.frame.textures.displayPlatformLongFalling, null, "<html><center>Long<br>Falling</center></html>", "A long platform that falls when stood upon by Mario.")).setItem(new Item(this.frame, '\u25bc', "Long Platform Falling", this.longFalling, -1, 22, 0, 0, false, true, 1));
        (this.longPulley = new Button(this.frame, this.frame.textures.iconPlatformLongPulley, this.frame.textures.displayPlatformLongPulley, "<html><center>Long<br>Pulley</center></html>", "Two platforms joined together in opposite motion when Mario stands upon one.")).setItem(new Item(this.frame, '\u25ac', "Long Platform Pulley", this.longPulley, -1, 23, 0, 61, false, true, 1));
        (this.shortRepUp = new Button(this.frame, this.frame.textures.iconPlatformShortRepUp, this.frame.textures.displayPlatformShortRepUp, "<html><center>Short<br>Rep. Up</center></html>", "A short set of platforms that repeatedly move up.")).setItem(new Item(this.frame, '\u2194', "Short Platform Rep. Up", this.shortRepUp, 0, 3, 0, 0, false, true, 10));
        (this.shortRepDown = new Button(this.frame, this.frame.textures.iconPlatformShortRepDown, this.frame.textures.displayPlatformShortRepDown, "<html><center>Short<br>Rep. Down</center></html>", "A short set of platforms that repeatedly move down.")).setItem(new Item(this.frame, '\u2191', "Short Platform Rep. Down", this.shortRepDown, 0, 3, 0, 0, false, true, 10));
        (this.shortOscV = new Button(this.frame, this.frame.textures.iconPlatformShortOscV, this.frame.textures.displayPlatformShortOscV, "<html><center>Short<br>Osc. Vert.</center></html>", "A short platform that oscillates vertically.")).setItem(new Item(this.frame, '\u266a', "Short Platform Osc. Vert.", this.shortOscV, -1, 24, 0, 72, false, true, 13));
        (this.shortOscH = new Button(this.frame, this.frame.textures.iconPlatformShortOscH, this.frame.textures.displayPlatformShortOscH, "<html><center>Short<br>Osc. Horiz.</center></html>", "A short platform that oscillates horizontally.")).setItem(new Item(this.frame, '\u2195', "Short Platform Osc. Horiz.", this.shortOscH, -1, 7, 48, 0, false, true, 13));
        (this.shortFalling = new Button(this.frame, this.frame.textures.displayPlatformShortFalling, null, "<html><center>Short<br>Falling</center></html>", "A short platform that falls when stood upon by Mario.")).setItem(new Item(this.frame, '\u203c', "Short Platform Falling", this.shortFalling, -1, 22, 0, 0, false, true, 1));
        (this.shortPulley = new Button(this.frame, this.frame.textures.iconPlatformShortPulley, this.frame.textures.displayPlatformShortPulley, "<html><center>Short<br>Pulley</center></html>", "Two platforms joined togeher in opposite motion when Mario stands upon one.")).setItem(new Item(this.frame, '\u0398', "Short Platform Pulley", this.shortPulley, -1, 23, 0, 61, false, true, 1));
        (this.extraShortRepUp = new Button(this.frame, this.frame.textures.iconPlatformExtraShortRepUp, this.frame.textures.displayPlatformExtraShortRepUp, "<html><center>Extra Short<br>Rep. Up</center></html>", "A very short set of platforms that repeatedly move up.")).setItem(new Item(this.frame, '¶', "Extra Short Platform Rep. Up", this.extraShortRepUp, 0, 3, 0, 0, false, true, 10));
        (this.extraShortRepDown = new Button(this.frame, this.frame.textures.iconPlatformExtraShortRepDown, this.frame.textures.displayPlatformExtraShortRepDown, "<html><center>Extra Short<br>Rep. Down</center></html>", "A very short set of platforms that repeatedly move down.")).setItem(new Item(this.frame, 'V', "Extra Short Platform Rep. Down", this.extraShortRepDown, 0, 3, 0, 0, false, true, 10));
        (this.carrierLong = new Button(this.frame, this.frame.game.textures.cloudCarrierLong, null, "<html><center>Long<br>Carrier</center></html>", "Platform that starts moving horizontally forward when Mario first steps upon it. Stops on collision with solid objects.")).setItem(new Item(this.frame, '\u263b', "Long Carrier Platform", this.carrierLong, -1, 2, 0, 0, false, true, 1));
        (this.carrierShort = new Button(this.frame, this.frame.game.textures.cloudCarrierShort, null, "<html><center>Short<br>Carrier</center></html>", "Platform that starts moving horizontally forward when Mario first steps upon it. Stops on collision with solid objects.")).setItem(new Item(this.frame, '\u00e7', "Short Carrier Platform", this.carrierShort, -1, 2, 0, 0, false, true, 1));
    }
    
    @Override
    public void construct() {
        this.add(this.longRepUp);
        this.add(this.longRepDown);
        this.add(this.longOscV);
        this.add(this.longOscH);
        this.add(this.longFalling);
        this.add(this.longPulley);
        this.add(this.carrierLong);
        this.add(this.carrierShort);
        this.add(this.shortRepUp);
        this.add(this.shortRepDown);
        this.add(this.shortOscV);
        this.add(this.shortOscH);
        this.add(this.shortFalling);
        this.add(this.shortPulley);
        this.add(this.extraShortRepUp);
        this.add(this.extraShortRepDown);
    }
    
    @Override
    public void refreshIcons() {
        this.longRepUp.setImages(this.frame.textures.iconPlatformLongRepUp, this.frame.textures.displayPlatformLongRepUp);
        this.longRepDown.setImages(this.frame.textures.iconPlatformLongRepDown, this.frame.textures.displayPlatformLongRepDown);
        this.longOscV.setImages(this.frame.textures.iconPlatformLongOscV, this.frame.textures.displayPlatformLongOscV);
        this.longOscH.setImages(this.frame.textures.iconPlatformLongOscH, this.frame.textures.displayPlatformLongOscH);
        this.longFalling.setImages(this.frame.textures.displayPlatformLongFalling, null);
        this.longPulley.setImages(this.frame.textures.iconPlatformLongPulley, this.frame.textures.displayPlatformLongPulley);
        this.carrierLong.setImages(this.frame.game.textures.cloudCarrierLong, null);
        this.carrierShort.setImages(this.frame.game.textures.cloudCarrierShort, null);
        this.shortRepUp.setImages(this.frame.textures.iconPlatformShortRepUp, this.frame.textures.displayPlatformShortRepUp);
        this.shortRepDown.setImages(this.frame.textures.iconPlatformShortRepDown, this.frame.textures.displayPlatformShortRepDown);
        this.shortOscV.setImages(this.frame.textures.iconPlatformShortOscV, this.frame.textures.displayPlatformShortOscV);
        this.shortOscH.setImages(this.frame.textures.iconPlatformShortOscH, this.frame.textures.displayPlatformShortOscH);
        this.shortFalling.setImages(this.frame.textures.displayPlatformShortFalling, null);
        this.shortPulley.setImages(this.frame.textures.iconPlatformShortPulley, this.frame.textures.displayPlatformShortPulley);
        this.extraShortRepUp.setImages(this.frame.textures.iconPlatformExtraShortRepUp, this.frame.textures.displayPlatformExtraShortRepUp);
        this.extraShortRepDown.setImages(this.frame.textures.iconPlatformExtraShortRepDown, this.frame.textures.displayPlatformExtraShortRepDown);
    }
    
    public void setWaterLevel() {
        this.switchMode(true);
    }
    
    public void setLandLevel() {
        this.switchMode(false);
    }
    
    public void switchMode(final boolean waterMode) {
        this.longRepUp.setEnabled(!waterMode);
        this.longOscV.setEnabled(!waterMode);
        this.shortRepUp.setEnabled(!waterMode);
        this.shortOscV.setEnabled(!waterMode);
        this.extraShortRepUp.setEnabled(!waterMode);
        this.shortPulley.setEnabled(!waterMode);
        this.longPulley.setEnabled(!waterMode);
    }
    
    public boolean isLandOnlyPlatform(final Item i) {
        return i.button == this.longRepUp || i.button == this.longOscV || (i.button == this.shortRepUp | i.button == this.shortOscV) || i.button == this.extraShortRepUp || i.button == this.shortPulley || i.button == this.longPulley;
    }
}
