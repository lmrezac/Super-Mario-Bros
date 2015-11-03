package supermario.game;

import java.awt.Image;

import javax.swing.ImageIcon;

public class MultiIcon extends ImageIcon{
	private static final long serialVersionUID = -3605428801079815466L;
	private ImageIcon light, dark, castle, water, ghost;
	public MultiIcon(ImageIcon lt, ImageIcon dk, ImageIcon cstl, ImageIcon wtr, ImageIcon ghost){
		this.light = lt;
		if(dk!=null)
			dark = dk;
		else
			dark = light;
		if(wtr!=null)
			water = wtr;
		else
			water = light;
		if(ghost!=null)
			this.ghost = ghost;
		else
			this.ghost = dark;
		if(cstl!=null)
			castle = cstl;
		else
			castle = dark;
	}
	public Image getImage(){
		return light.getImage();
	}
	public Image getImage(int i){
		ImageIcon icon = getType(i);
		return icon.getImage();
	}
	public ImageIcon getType(int i){
		ImageIcon icon;
		switch(i){
		case Level.LEVEL_TYPE_OUTSIDE:
		case Level.LEVEL_TYPE_OUTSIDE_NIGHT:
		case Level.LEVEL_TYPE_COIN_ZONE_DAY:
		case Level.LEVEL_TYPE_COIN_ZONE_NIGHT:
			icon = light;
		case Level.LEVEL_TYPE_CASTLE:
			icon = castle;
		case Level.LEVEL_TYPE_UNDER_WATER:
			icon = water;
		case Level.LEVEL_TYPE_UNDERGROUND:
			icon = dark;
		case Level.LEVEL_TYPE_GHOST_HOUSE:
			icon = ghost;
		default:
			icon = light;
		}
		if(icon == null)return light;
		return icon;
	}
	@Override
	public int getIconWidth(){
		return light.getIconWidth();
	}
	@Override
	public int getIconHeight(){
		return light.getIconHeight();
	}
	

}
