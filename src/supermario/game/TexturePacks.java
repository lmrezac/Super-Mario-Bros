// 
// Decompiled by Procyon v0.5.29
// 

package supermario.game;

import java.util.Random;
import static supermario.debug.Debugger.*;

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
    public static final int STANDARD = 0, LOSTLEVELS = 1, NEWSMB = 2, SPECIAL = 3, SPONGE = 4;
    public boolean setTexturePack(int newTexturePack) {
    	debug(new Exception(),""+newTexturePack);
    	if(Game.instance.sponge){
    		newTexturePack = SPONGE;
    		debug(new Exception(),"overriden! "+SPONGE);
    	}
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
        		debug(new Exception(),"switch to New SMB textures");
        		this.switchToNewSMBTextures();
        		return true;
        	}
        	return false;
        }
        else if (newTexturePack == SPECIAL){
        	if(this.texturePack != SPECIAL){
        		debug(new Exception(),"switch to Special textures");
        		this.switchToSpecialTextures();
        		return true;
        	}
        	return false;
        }
        else if(newTexturePack == SPONGE){
        	if(this.texturePack != SPONGE){
        		debug(new Exception(),"switch to Sponge textures");
        		this.switchToSpongeTextures();
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
        debug(new Exception(),"switchToLostLevelsTextures called!");
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
    	debug(new Exception(),"switchToSMB3Textures called!");
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
    private void switchToSpongeTextures(){
    	this.texturePack = SPONGE;
    	this.textures.init("sponge/");
    	switched = true;
    	Game.instance.mario.marioImages = textures.getMarioTextures();
    	Game.instance.mario.luigiImages = textures.getLuigiTextures();
    }
    public static final int HIGHEST_PACK_VALUE = SPONGE;
    private static final String[] names = {"Standard","Lost Levels","New SMB","SMB Special"};
    private static final String[] spongeNames = {"Standard","Lost Levels","New SMB","SMB Special","...sponge?"};
    public static String[] getNames(){
    	if(Game.instance.sponge)
    		return spongeNames;
    	else return names;
    }
}
