// 
// Decompiled by Procyon v0.5.29
// 

package supermario.builder;

public class Warp
{
    public static final int CLIFF_WARP_CONFLICT = -2;
    public static final int NEXT_LEVEL_WARP_CONFLICT = -3;
    public boolean outgoing;
    public boolean incoming;
    public int sourceLevelNumber;
    public int sourceWarpID;
    public int destLevelNumber;
    public int destWarpID;
    public Item item;
    
    public Warp(final boolean outgoing, final boolean incoming, final int sourceLevelNumber, final int sourceWarpID, final int destLevelNumber, final int destWarpID, final Item item) {
        this.outgoing = outgoing;
        this.incoming = incoming;
        this.sourceLevelNumber = sourceLevelNumber;
        this.sourceWarpID = sourceWarpID;
        this.destLevelNumber = destLevelNumber;
        this.destWarpID = destWarpID;
        this.item = item;
    }
    
    public void update(final boolean outgoing, final boolean incoming, final int sourceLevelNumber, final int sourceWarpID, final int destLevelNumber, final int destWarpID, final Item item) {
        this.outgoing = outgoing;
        this.incoming = incoming;
        this.sourceLevelNumber = sourceLevelNumber;
        this.sourceWarpID = sourceWarpID;
        this.destLevelNumber = destLevelNumber;
        this.destWarpID = destWarpID;
        this.item = item;
    }
    
    public void clearOutgoing() {
        this.outgoing = false;
        this.destLevelNumber = -1;
        this.destWarpID = -1;
    }
    
    public Warp copy(final Item i) {
        return new Warp(this.outgoing, this.incoming, this.sourceLevelNumber, this.sourceWarpID, this.destLevelNumber, this.destWarpID, i);
    }
    
    @Override
    public String toString() {
        return "Out: " + this.outgoing + ", In: " + this.incoming + ", Src Lvl: " + this.sourceLevelNumber + ", Src ID: " + this.sourceWarpID + ", Dst Lvl: " + this.destLevelNumber + ", Dst ID: " + this.destWarpID;
    }
}
