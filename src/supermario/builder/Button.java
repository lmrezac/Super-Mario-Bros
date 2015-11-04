// 
// Decompiled by Procyon v0.5.29
// 

package supermario.builder;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import supermario.game.AnimatedIcon;
import supermario.game.MultiIcon;

public final class Button extends JButton implements ActionListener
{
    private static final long serialVersionUID = -8952784926128741984L;
	private BuilderFrame frame;
    public Item item;
    public String title;
    public String description;
    public ImageIcon iconImage;
    public ImageIcon placedImage;
    
    public Button(final BuilderFrame frame,ImageIcon iconImage, ImageIcon placedImage, final String title, final String description) {
        this.frame = frame;
        if(iconImage instanceof AnimatedIcon)
        	iconImage = ((AnimatedIcon)iconImage).getImages()[0];
        if(placedImage instanceof AnimatedIcon)
        	placedImage = ((AnimatedIcon)placedImage).getImages()[0];
        this.iconImage = iconImage;
        this.setFont(frame.plain);
        this.setIcon(iconImage);
        if (placedImage == null) {
            this.placedImage = iconImage;
        }
        else {
            this.placedImage = placedImage;
        }
        this.title = title;
        this.setToolTipText(this.description = description);
        this.setHorizontalTextPosition(0);
        this.setVerticalTextPosition(3);
        this.setMargin(new Insets(1, 1, 1, 1));
        this.addActionListener(this);
    }
    
    public void setItem(final Item item) {
        this.item = item;
        if (this.title == null) {
            this.title = item.name;
        }
        this.setText(this.title);
    }
    
    public void setTitle(String newTitle){
    	this.title = newTitle;
    }
    public void setImages(ImageIcon icon,ImageIcon placed) {
    	if(icon instanceof MultiIcon){
    		icon = ((MultiIcon)icon).getType(0);
    	}
        if(icon instanceof supermario.game.AnimatedIcon)
        	icon = ((AnimatedIcon)icon).getImages()[0];
    	if(placed != null){
    		if(placed instanceof MultiIcon){
        		placed = ((MultiIcon)placed).getType(0);
        	}
    		if(placed instanceof AnimatedIcon)
    			placed = ((AnimatedIcon)placed).getImages()[0];
    	}
    	this.setIcon(icon);
        this.iconImage = icon;
        if (placed != null) {
            this.placedImage = placed;
        }
        else {
            this.placedImage = icon;
        }
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        this.frame.levelPanel.item = this.item;
        if (this.item != null && !this.item.isWarpable()) {
            this.frame.changePropertiesPanel(new PropertiesPanel(this.frame, this.item.propertiesType, this.item));
        }
        else {
            this.frame.changePropertiesPanel(new PropertiesPanel(this.frame, 1, null));
        }
    }
}
