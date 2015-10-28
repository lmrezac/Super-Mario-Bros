// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game;

import java.util.Random;

public class TexturePacks
{
    private Textures textures;
    public boolean validTextures;
    private int texturePack;
   
    
    public TexturePacks(final Textures textures) {
    	Random rand = new Random();
    	
        this.texturePack = rand.nextInt(HIGHEST_PACK_VALUE+1);
        setTexturePack(texturePack);
        this.textures = textures;
        this.validTextures = true;
    }
    
    public int getTexturePack() {
        return this.texturePack;
    }
    public static final int STANDARD = 0, LOSTLEVELS = 1, NEWSMB = 2, SPECIAL = 3;
    public boolean setTexturePack(final int newTexturePack) {
    	System.out.println("setTexturePack (2) "+newTexturePack);
    	if(Game.instance.luigiBros){switchToStandardTextures();return true;}
        if (newTexturePack == STANDARD) {
            if (this.texturePack != STANDARD) {
                this.switchToStandardTextures();
                return true;
            }
            return false;
        }
        else if (newTexturePack == LOSTLEVELS) {
            if (this.texturePack != LOSTLEVELS) {
                this.switchToLostLevelsTextures();
                return true;
            }
            return false;
        }
        else if (newTexturePack == NEWSMB){
        	if (this.texturePack != NEWSMB){
        		System.out.println("switch to New SMB textures");
        		this.switchToNewSMBTextures();
        		return true;
        	}
        	return false;
        }
        else if (newTexturePack == SPECIAL){
        	if(this.texturePack != SPECIAL){
        		System.out.println("switch to Special textures");
        		this.switchToSpecialTextures();
        		return true;
        	}
        	return false;
        }
        else {
            if (this.texturePack != STANDARD) {
                this.switchToStandardTextures();
                return true;
            }
            return false;
        }
    }
    
    private void switchToLostLevelsTextures() {
        this.texturePack = LOSTLEVELS;
        System.out.println("switchToLostLevelsTextures called!");
        this.textures.init("lostLevelImages/");
        if(switched){
        	switched = false;
        	Game.instance.mario.marioImages = textures.getMarioTextures();
        	Game.instance.mario.luigiImages = textures.getLuigiTextures();
        }
    }
    public boolean switched = false;
    private void switchToNewSMBTextures(){
    	switched = true;
    	this.texturePack = NEWSMB;
    	System.out.println("switchToSMB3Textures called!");
    	this.textures.init("newsmb/");
    	Game.instance.mario.marioImages = textures.getMarioTextures();
    	Game.instance.mario.luigiImages = textures.getLuigiTextures();
    }
    
    private void switchToStandardTextures() {
        this.texturePack = STANDARD;
        this.textures.init();
        if(switched){
        	switched = false;
        	Game.instance.mario.marioImages = textures.getMarioTextures();
        	Game.instance.mario.luigiImages = textures.getLuigiTextures();
        }
    }
    private void switchToSpecialTextures(){
    	this.texturePack = SPECIAL;
    	this.textures.init("special/");
    	switched = true;
    	Game.instance.mario.marioImages = textures.getMarioTextures();
    	Game.instance.mario.luigiImages = textures.getLuigiTextures();
    }
    public static final int HIGHEST_PACK_VALUE = SPECIAL;
    public static final String[] names = {"Standard","Lost Levels","New SMB","SMB Special"};
    /*private ImageIcon image(final String imageName) throws Exception {
        ImageIcon image = new ImageIcon(imageName);
        if (image.getIconWidth() == -1) {
            this.validTextures = false;
            throw new RuntimeException("Invalid image: " + imageName);
        }
	   	 return image;/**/
    //}
    
}
