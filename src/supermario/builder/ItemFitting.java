// 
// Decompiled by Procyon v0.5.29
// 

package supermario.builder;

import supermario.game.LevelLoader;
import supermario.game.Game;
import java.util.LinkedList;

public class ItemFitting
{
    private static Item[][] items;
    private static Item item;
    private static int s;
    private static char c;
    private static int itemX;
    private static int itemY;
    private static LinkedList<Item> blockItems;
    private static int levelLength;
    private static Rect testRect;
    private static final Rect rect;
    private static final Rect rect2;
    private static final Rect rect3;
    
    public static boolean itemCanFit(final Level level, final Item item, final int x, int y, final LinkedList<Item> blockItems) {
        boolean triggered = false;
        blockItems.clear();
        if (item == null || level == null) {
            return false;
        }
        setValues(level, item, x, y, blockItems);
        y = ItemFitting.itemY;
        if (!tileIsInLevelArea(x, y)) {
            return false;
        }
        if (ItemFitting.s == 15 || ItemFitting.s == 16) {
            return true;
        }
        char itemChar = item.character;
        if(itemChar == LevelLoader.PIPE_TOP_SECTION || itemChar == LevelLoader.PIPE_TOP_W_CHOMPER || itemChar == LevelLoader.PIPE_TOP_WO_CHOMPER){
        	//System.out.println("testing... (x="+x+", y="+y+", char="+itemChar+") for y>=25");
        	if( y >= 25)
        		return true;
        }
        ItemFitting.rect.setRectAsUseless();
        ItemFitting.rect2.setRectAsUseless();
        ItemFitting.rect3.setRectAsUseless();
        if (isInReservedItemTiles()) {
            triggered = true;
        }
        if (inReservedAreas(ItemFitting.rect)) {
            triggered = true;
        }
        if (!ItemFitting.rect.isRectSetAsUseless() && inReservedAreas(ItemFitting.rect2)) {
            triggered = true;
        }
        if (!ItemFitting.rect.isRectSetAsUseless() && inReservedAreas(ItemFitting.rect3)) {
            triggered = true;
        }
        if (!isWithinItemConstraints(x, y)) {
            triggered = true;
        }
        if (triggered && item.character == 'k' && blockItems.size() == 1 && blockItems.getFirst().character == 'k') {
            triggered = false;
        }
        return !triggered;
    }
    
    private static void addBlockingItem(final Item i) {
        if (!ItemFitting.blockItems.contains(i)) {
            ItemFitting.blockItems.add(i);
        }
    }
    
    private static boolean inReservedAreas(final Rect r) {
        ItemFitting.testRect = r;
        boolean triggered = false;
        if (isInPipePaddingArea()) {
            triggered = true;
        }
        if (isInBeanstalkBlockArea()) {
            triggered = true;
        }
        if (isInContentsBlockArea()) {
            triggered = true;
        }
        if (isInInvisibleBlockArea()) {
            triggered = true;
        }
        if (isInPlatformMotionArea()) {
            triggered = true;
        }
        if (isInPlatformPaddingArea()) {
            triggered = true;
        }
        if (isTooCloseToBeanstalkBlock()) {
            triggered = true;
        }
        return triggered;
    }
    
    public static boolean isInReservedColumn(final Level level, final int x, final int y) {
        ItemFitting.levelLength = level.items[0].length;
        ItemFitting.item = null;
        ItemFitting.testRect.setBounds(x, 0, x, Game.yTiles - 1);
        return isInPipePaddingArea() || (isInPlatformPaddingArea() && x < ItemFitting.levelLength - 1);
    }
    
    public static boolean itemAllowsPartialInsertion(final Item i) {
        final char c = i.character;
        return c == 'h' || c == '\"' || c == '\'' || c == '\u260e' || c == '\u2193' || c == '\u2660' || c == 'j' || (c == '\u010e' || c == 'T' || c == '#' || c == 'S' || c == '`' || c == 'U' || c == '@') || LevelLoader.isBlockBrickBoxTypeItem(c) || (ItemFitting.s == 15 || ItemFitting.s == 16 || ItemFitting.s == 6 || ItemFitting.s == 5 || ItemFitting.s == 7 || ItemFitting.s == 14);
    }
    
    private static void setValues(final Level level, final Item item, final int x, final int y, final LinkedList<Item> blockItems) {
        ItemFitting.items = level.items;
        ItemFitting.levelLength = level.items[0].length;
        ItemFitting.item = item;
        ItemFitting.s = item.spaceRequirement;
        ItemFitting.c = item.character;
        ItemFitting.itemX = x;
        if (item.requiredRow != -1) {
            ItemFitting.itemY = item.requiredRow;
        }
        else {
            ItemFitting.itemY = y;
        }
        ItemFitting.blockItems = blockItems;
    }
    
    private static boolean isInReservedItemTiles() {
        boolean triggered = false;
        if (ItemFitting.s == 0) {
            return areaHasUnavailableTile(ItemFitting.rect.setBounds(ItemFitting.itemX, ItemFitting.itemY, ItemFitting.itemX, ItemFitting.itemY));
        }
        if (ItemFitting.s == 1) {
            return areaHasUnavailableTile(ItemFitting.rect.setBounds(ItemFitting.itemX, ItemFitting.itemY, ItemFitting.itemX + 1, ItemFitting.itemY));
        }
        if (ItemFitting.s == 2) {
            return areaHasUnavailableTile(ItemFitting.rect.setBounds(ItemFitting.itemX, ItemFitting.itemY, ItemFitting.itemX + ItemFitting.item.tilesWidth - 1, ItemFitting.itemY + ItemFitting.item.tilesHeight - 1));
        }
        if (ItemFitting.s == 21) {
            return areaHasUnavailableTile(ItemFitting.rect.setBounds(ItemFitting.itemX - 2, ItemFitting.itemY, ItemFitting.itemX + 3, ItemFitting.itemY + 2));
        }
        if (ItemFitting.s == 18) {
            return areaHasUnavailableTile(ItemFitting.rect.setBounds(ItemFitting.itemX, ItemFitting.itemY, ItemFitting.itemX + 1, ItemFitting.itemY + 1));
        }
        if (ItemFitting.s == 26) {
            return areaHasUnavailableTile(ItemFitting.rect.setBounds(ItemFitting.itemX, ItemFitting.itemY, ItemFitting.itemX + 1, ItemFitting.itemY + 1));
        }
        if (ItemFitting.s == 27) {
            return areaHasUnavailableTile(ItemFitting.rect.setBounds(ItemFitting.itemX, ItemFitting.itemY, ItemFitting.itemX + 1, ItemFitting.itemY + 1));
        }
        if (ItemFitting.s == 17) {
            if (areaHasUnavailableTile(ItemFitting.rect.setBounds(ItemFitting.itemX, ItemFitting.itemY, ItemFitting.itemX + 3, ItemFitting.itemY + 5))) {
                triggered = true;
            }
            if (areaHasUnavailableTile(ItemFitting.rect2.setBounds(ItemFitting.itemX, ItemFitting.itemY + 6, ItemFitting.itemX + 1, ItemFitting.itemY + 10))) {
                triggered = true;
            }
            return triggered;
        }
        if (ItemFitting.s == 9) {
            return areaHasUnavailableTile(ItemFitting.rect.setBounds(ItemFitting.itemX, ItemFitting.itemY, ItemFitting.itemX + ItemFitting.item.tilesWidth - 1, ItemFitting.itemY + ItemFitting.item.tilesHeight - 1));
        }
        if (ItemFitting.s == 10) {
            return areaHasUnavailableTile(ItemFitting.rect.setBounds(ItemFitting.itemX, ItemFitting.itemY, ItemFitting.itemX + ItemFitting.item.tilesWidth - 1, ItemFitting.itemY + ItemFitting.item.tilesHeight - 1));
        }
        if (ItemFitting.s == 11 || ItemFitting.s == 13) {
            return areaHasUnavailableTile(ItemFitting.rect.setBounds(ItemFitting.itemX, ItemFitting.itemY, ItemFitting.itemX + ItemFitting.item.tilesWidth - 1, ItemFitting.itemY + ItemFitting.item.tilesHeight - 1));
        }
        if (ItemFitting.s == 12) {
            return areaHasUnavailableTile(ItemFitting.rect.setBounds(ItemFitting.itemX, ItemFitting.itemY, ItemFitting.itemX + ItemFitting.item.tilesWidth - 1, ItemFitting.itemY + ItemFitting.item.tilesHeight - 1));
        }
        if (ItemFitting.s == 19 || ItemFitting.s == 28) {
            return areaHasUnavailableTile(ItemFitting.rect.setBounds(ItemFitting.itemX, ItemFitting.itemY, ItemFitting.itemX + ItemFitting.item.tilesWidth - 1, ItemFitting.itemY + ItemFitting.item.tilesHeight - 1));
        }
        if (ItemFitting.s == 3) {
            return areaHasUnavailableTile(ItemFitting.rect.setBounds(ItemFitting.itemX, 0, ItemFitting.itemX + ItemFitting.item.tilesWidth - 1, 0));
        }
        if (ItemFitting.s == 22) {
            return areaHasUnavailableTile(ItemFitting.rect.setBounds(ItemFitting.itemX, ItemFitting.itemY, ItemFitting.itemX + ItemFitting.item.tilesWidth - 1, ItemFitting.itemY));
        }
        if (ItemFitting.s == 24) {
            return areaHasUnavailableTile(ItemFitting.rect.setBounds(ItemFitting.itemX, ItemFitting.itemY, ItemFitting.item.tilesWidth - 1, ItemFitting.itemY));
        }
        if (ItemFitting.s == 4) {
            return areaHasUnavailableTile(ItemFitting.rect.setBounds(ItemFitting.itemX, ItemFitting.itemY, ItemFitting.itemX + ItemFitting.item.tilesWidth - 1, ItemFitting.itemY + 1));
        }
        if (ItemFitting.s == 14) {
            return areaHasUnavailableTile(ItemFitting.rect.setBounds(ItemFitting.itemX, ItemFitting.itemY - 2, ItemFitting.itemX + 1, ItemFitting.itemY + 3));
        }
        if (ItemFitting.s == 5) {
            return areaHasUnavailableTile(ItemFitting.rect.setBounds(ItemFitting.itemX, ItemFitting.itemY - 7, ItemFitting.itemX + 1, ItemFitting.itemY + 2 + 7));
        }
        if (ItemFitting.s == 6) {
            return areaHasUnavailableTile(ItemFitting.rect.setBounds(ItemFitting.itemX - 6, ItemFitting.itemY - 1, ItemFitting.itemX + 1 + 6, ItemFitting.itemY + 3));
        }
        if (ItemFitting.s == 7) {
            return areaHasUnavailableTile(ItemFitting.rect.setBounds(ItemFitting.itemX - 6, ItemFitting.itemY, ItemFitting.itemX - 6 + ItemFitting.item.tilesWidth - 1, ItemFitting.itemY));
        }
        if (ItemFitting.s == 8) {
            return areaHasUnavailableTile(ItemFitting.rect.setBounds(ItemFitting.itemX, ItemFitting.itemY, ItemFitting.itemX, ItemFitting.itemY));
        }
        if (ItemFitting.s == 23) {
            final int pWidth = Item.getPulleyWidth(ItemFitting.c);
            final int tilesWidth = ItemFitting.item.button.placedImage.getIconWidth() / 8;
            if (areaHasUnavailableTile(ItemFitting.rect.setBounds(ItemFitting.itemX, ItemFitting.itemY, ItemFitting.itemX + pWidth - 1, ItemFitting.itemY))) {
                triggered = true;
            }
            if (areaHasUnavailableTile(ItemFitting.rect2.setBounds(ItemFitting.itemX + pWidth / 2, ItemFitting.itemY - 8, ItemFitting.itemX + tilesWidth - pWidth / 2, ItemFitting.itemY - 8))) {
                triggered = true;
            }
            if (areaHasUnavailableTile(ItemFitting.rect3.setBounds(ItemFitting.itemX + tilesWidth - pWidth, ItemFitting.itemY + 4, ItemFitting.itemX + tilesWidth - 1, ItemFitting.itemY + 4))) {
                triggered = true;
            }
            return triggered;
        }
        if (ItemFitting.s == 25) {
            if (areaHasUnavailableTile(ItemFitting.rect.setBounds(ItemFitting.itemX - 56, ItemFitting.itemY, ItemFitting.itemX - 56 + 1, ItemFitting.itemY + 3))) {
                triggered = true;
            }
            if (areaHasUnavailableTile(ItemFitting.rect2.setBounds(ItemFitting.itemX - 56 + 2, ItemFitting.itemY + 3, ItemFitting.itemX - 1, ItemFitting.itemY + 3))) {
                triggered = true;
            }
            if (areaHasUnavailableTile(ItemFitting.rect3.setBounds(ItemFitting.itemX, ItemFitting.itemY, ItemFitting.itemX + 7, ItemFitting.itemY + 3))) {
                triggered = true;
            }
            return triggered;
        }
        throw new RuntimeException("Unreachable statement: " + ItemFitting.s);
    }
    
    private static boolean isWithinItemConstraints(final int x, final int y) {
        boolean triggered = false;
        if (ItemFitting.s == 0) {
            triggered = (ItemFitting.c == 'i' && isAdjacentToItem(ItemFitting.itemX, ItemFitting.itemY, 'i'));
        }
        else if (ItemFitting.s == 1) {
            triggered = !tileIsWithinXBounds(0, ItemFitting.levelLength - 2);
        }
        else {
            if (ItemFitting.s == 2) {
                return (!LevelLoader.isMarioPlacementItem(ItemFitting.c) || tileIsWithinYBounds(0, Game.yTiles - ItemFitting.item.tilesHeight - 1)) && (!LevelLoader.isBlockBrickBoxTypeItem(ItemFitting.c) || tileIsWithinXBounds(0, ItemFitting.levelLength - 2)) && (ItemFitting.c != 'j' || tileIsWithinXBounds(0, ItemFitting.levelLength - 2));
            }
            if (ItemFitting.s == 21) {
                triggered = !tileIsWithinXBounds(2, ItemFitting.levelLength - 4);
            }
            else if (ItemFitting.s == 18) {
                if (LevelLoader.isBlockBrickBoxTypeItem(ItemFitting.c) && !tileIsWithinXBounds(0, ItemFitting.levelLength - 2)) {
                    triggered = true;
                }
                if (areaHasItemsNotAllowedInContentsBlockArea(Rect.tempRect.setBounds(x, y - 2, x + 1, y - 1))) {
                    triggered = true;
                }
            }
            else if (ItemFitting.s == 26) {
                if (LevelLoader.isBlockBrickBoxTypeItem(ItemFitting.c) && !tileIsWithinXBounds(0, ItemFitting.levelLength - 2)) {
                    triggered = true;
                }
                if (areaHasItemsNotAllowedInInvisibleBlockArea(Rect.tempRect.setBounds(x, y + 2, x + 1, y + 3))) {
                    triggered = true;
                }
            }
            else if (ItemFitting.s == 27) {
                if (LevelLoader.isBlockBrickBoxTypeItem(ItemFitting.c) && !tileIsWithinXBounds(0, ItemFitting.levelLength - 2)) {
                    triggered = true;
                }
                if (areaHasItemsNotAllowedInContentsBlockArea(Rect.tempRect.setBounds(x, y - 2, x + 1, y - 1))) {
                    triggered = true;
                }
                if (areaHasItemsNotAllowedInInvisibleBlockArea(Rect.tempRect.setBounds(x, y + 2, x + 1, y + 3))) {
                    triggered = true;
                }
            }
            else if (ItemFitting.s == 17) {
                triggered = !tileIsWithinXBounds(0, ItemFitting.levelLength - 4);
            }
            else if (ItemFitting.s == 9) {
                if (!tileIsWithinXBounds(2, ItemFitting.levelLength - 1)) {
                    triggered = true;
                }
                if (areaHasItemsNotAllowedInPipePaddingArea(Rect.tempRect.setBounds(x - 2, y, x - 1, y + 3))) {
                    triggered = true;
                }
                if (isInRepeatingPlatformColumn(Rect.tempRect.setBounds(x - 2, y, x - 1, y + 3))) {
                    triggered = true;
                }
            }
            else if (ItemFitting.s == 10) {
                if (!tileIsWithinXBounds(0, ItemFitting.levelLength - 5)) {
                    triggered = true;
                }
                if (areaHasItemsNotAllowedInPipePaddingArea(Rect.tempRect.setBounds(x + 3, y, x + 4, y + 3))) {
                    triggered = true;
                }
                if (isInRepeatingPlatformColumn(Rect.tempRect.setBounds(x + 3, y, x + 4, y + 3))) {
                    triggered = true;
                }
            }
            else if (ItemFitting.s == 11) {
                if (!tileIsWithinYBounds(1, Game.yTiles - 1)) {
                    triggered = true;
                }
                if (areaHasItemsNotAllowedInPipePaddingArea(Rect.tempRect.setBounds(x, y - 4, x + 3, y - 1))) {
                    triggered = true;
                }
            }
            else if (ItemFitting.s == 13) {
                if (!tileIsWithinYBounds(4, Game.yTiles - 4)) {
                    triggered = true;
                }
                if (areaHasItemsNotAllowedInPipePaddingArea(Rect.tempRect.setBounds(x, y - 4, x + 3, y - 1))) {
                    triggered = true;
                }
            }
            else if (ItemFitting.s == 12) {
                if (!tileIsWithinYBounds(0, Game.yTiles - 8)) {
                    triggered = true;
                }
                if (areaHasItemsNotAllowedInPipePaddingArea(Rect.tempRect.setBounds(x, y + 4, x + 3, y + 7))) {
                    triggered = true;
                }
            }
            else if (ItemFitting.s == 19) {
                if (!tileIsWithinXBounds(0, ItemFitting.levelLength - 2)) {
                    triggered = true;
                }
                if (!tileIsWithinYBounds(3, Game.yTiles - 1)) {
                    triggered = true;
                }
                if (areaHasItemsNotAllowedInBeanstalkBlockArea(Rect.tempRect.setBounds(x, 0, x + 1, y - 1))) {
                    triggered = true;
                }
                if (areaHasItemsNotAllowedInBeanstalkBlockArea(Rect.tempRect.setBounds(x, 0, x + 1, y))) {
                    triggered = true;
                }
                if (areaHasItemsTooCloseToBeanstalkBlock(Rect.tempRect.setBounds(x, y - 3, x + 1, y - 1))) {
                    triggered = true;
                }
            }
            else if (ItemFitting.s == 28) {
                if (!tileIsWithinXBounds(0, ItemFitting.levelLength - 2)) {
                    triggered = true;
                }
                if (!tileIsWithinYBounds(3, Game.yTiles - 1)) {
                    triggered = true;
                }
                if (areaHasItemsNotAllowedInBeanstalkBlockArea(Rect.tempRect.setBounds(x, 0, x + 1, y - 1))) {
                    triggered = true;
                }
                if (areaHasItemsNotAllowedInInvisibleBlockArea(Rect.tempRect.setBounds(x, y + 2, x + 1, y + 3))) {
                    triggered = true;
                }
                if (areaHasItemsTooCloseToBeanstalkBlock(Rect.tempRect.setBounds(x, y - 3, x + 1, y - 1))) {
                    triggered = true;
                }
            }
            else if (ItemFitting.s == 3) {
                if (areaHasItemsNotAllowedInPlatformMotionArea(Rect.tempRect.setBounds(x, 0, x + ItemFitting.item.tilesWidth - 1, Game.yTiles - 1))) {
                    triggered = true;
                }
                if (areaHasItemsNotAllowedInPlatformPaddingArea(Rect.tempRect.setBounds(x - 0, 0, x + ItemFitting.item.tilesWidth - 1 + 0, Game.yTiles - 1))) {
                    triggered = true;
                }
                ItemFitting.testRect = Rect.tempRect.setBounds(x, 0, x + ItemFitting.item.tilesWidth - 1, Game.yTiles - 1);
                if (isInPipePaddingArea()) {
                    triggered = true;
                }
            }
            else if (ItemFitting.s == 22) {
                if (areaHasItemsNotAllowedInPlatformMotionArea(Rect.tempRect.setBounds(x, y + 1, x + ItemFitting.item.tilesWidth - 1, Game.yTiles - 1))) {
                    triggered = true;
                }
                if (areaHasItemsNotAllowedInPlatformPaddingArea(Rect.tempRect.setBounds(x - 0, y, x + ItemFitting.item.tilesWidth - 1 + 0, Game.yTiles - 1))) {
                    triggered = true;
                }
            }
            else if (ItemFitting.s == 24) {
                if (!tileIsWithinXBounds(0, ItemFitting.levelLength - ItemFitting.item.tilesWidth)) {
                    triggered = true;
                }
                if (areaHasItemsNotAllowedInPlatformMotionArea(Rect.tempRect.setBounds(x, y - 9, x + ItemFitting.item.tilesWidth - 1, y + 9))) {
                    triggered = true;
                }
                if (areaHasItemsNotAllowedInPlatformPaddingArea(Rect.tempRect.setBounds(x - 0, y - 9 - 4, x + ItemFitting.item.tilesWidth - 1 + 0, y + 9 + 4))) {
                    triggered = true;
                }
            }
            else if (ItemFitting.s == 4) {
                triggered = false;
            }
            else if (ItemFitting.s == 14) {
                if (!tileIsWithinXBounds(0, ItemFitting.levelLength - 2)) {
                    triggered = true;
                }
                if (!tileIsWithinYBounds(0, Game.yTiles - 2)) {
                    triggered = true;
                }
            }
            else if (ItemFitting.s == 5) {
                if (!tileIsWithinXBounds(0, ItemFitting.levelLength - 2)) {
                    triggered = true;
                }
                if (!tileIsWithinYBounds(0, Game.yTiles - 3)) {
                    triggered = true;
                }
            }
            else if (ItemFitting.s == 6) {
                if (!tileIsWithinXBounds(0, ItemFitting.levelLength - 2)) {
                    triggered = true;
                }
                if (!tileIsWithinYBounds(0, Game.yTiles - 3)) {
                    triggered = true;
                }
            }
            else if (ItemFitting.s == 7) {
                if (!tileIsWithinXBounds(0, ItemFitting.levelLength - 6)) {
                    triggered = true;
                }
                if (areaHasItemsNotAllowedInPlatformPaddingArea(Rect.tempRect.setBounds(x - 6 - 0, y - 4, x - 6 + ItemFitting.item.tilesWidth - 1 + 0, y + 4))) {
                    triggered = true;
                }
            }
            else if (ItemFitting.s == 8) {
                triggered = false;
            }
            else if (ItemFitting.s == 23) {
                final int pWidth = Item.getPulleyWidth(ItemFitting.c);
                final int tilesWidth = ItemFitting.item.button.placedImage.getIconWidth() / 8;
                if (!tileIsWithinXBounds(0, ItemFitting.levelLength - tilesWidth)) {
                    triggered = true;
                }
                if (!tileIsWithinYBounds(8, Game.yTiles - 1 - 4)) {
                    triggered = true;
                }
                if (areaHasItemsNotAllowedInPlatformMotionArea(Rect.tempRect.setBounds(x, y - 6, x + pWidth - 1, Game.yTiles - 1))) {
                    triggered = true;
                }
                if (areaHasItemsNotAllowedInPlatformPaddingArea(Rect.tempRect.setBounds(x - 0, y - 6, x + pWidth - 1 + 0, Game.yTiles - 1))) {
                    triggered = true;
                }
                if (areaHasItemsNotAllowedInPlatformMotionArea(Rect.tempRect.setBounds(x + tilesWidth - pWidth, y - 6, x + tilesWidth - 1, Game.yTiles - 1))) {
                    triggered = true;
                }
                if (areaHasItemsNotAllowedInPlatformPaddingArea(Rect.tempRect.setBounds(x + tilesWidth - pWidth - 0, y - 6, x + tilesWidth - 1 + 0, Game.yTiles - 1))) {
                    triggered = true;
                }
            }
            else {
                if (ItemFitting.s != 25) {
                    throw new RuntimeException("Unknown requirement specified: " + ItemFitting.s);
                }
                if (!tileIsWithinYBounds(0, Game.yTiles - ItemFitting.item.tilesHeight - 1)) {
                    triggered = true;
                }
                if (!tileIsWithinXBounds(56, ItemFitting.levelLength - 8)) {
                    triggered = true;
                }
            }
        }
        return !triggered;
    }
    
    private static boolean isInPlatformMotionArea() {
        if (isAllowedInPlatformMotionArea(ItemFitting.item)) {
            return false;
        }
        boolean triggered = false;
        for (int i = ItemFitting.testRect.y1; i <= ItemFitting.testRect.y2; ++i) {
            for (int j = ItemFitting.testRect.x1; j <= ItemFitting.testRect.x2; ++j) {
                if (tileIsInLevelArea(j, i) && ItemFitting.items[0][j] != null && ItemFitting.items[0][j].spaceRequirement == 3) {
                    addBlockingItem(ItemFitting.items[0][j]);
                    triggered = true;
                }
                for (int y = i - 9; y <= i + 9; ++y) {
                    if (tileIsInLevelArea(j, y) && ItemFitting.items[y][j] != null && ItemFitting.items[y][j].spaceRequirement == 24) {
                        addBlockingItem(ItemFitting.items[y][j]);
                        triggered = true;
                    }
                }
                for (int y = 0; y <= i; ++y) {
                    if (tileIsInLevelArea(j, y) && ItemFitting.items[y][j] != null && ItemFitting.items[y][j].spaceRequirement == 22) {
                        addBlockingItem(ItemFitting.items[y][j]);
                        triggered = true;
                    }
                }
                for (int y = i; y <= i + 6; ++y) {
                    if (tileIsInLevelArea(j, y) && ItemFitting.items[y][j] != null && ItemFitting.items[y][j].spaceRequirement == 23 && y >= ItemFitting.items[y][j].yTile - 6 && Item.isPulleyLeftSide(ItemFitting.items[y][j], j)) {
                        addBlockingItem(ItemFitting.items[y][j]);
                        triggered = true;
                    }
                }
                for (int y = i; y >= 2; --y) {
                    if (tileIsInLevelArea(j, y) && ItemFitting.items[y][j] != null && ItemFitting.items[y][j].spaceRequirement == 23 && y >= ItemFitting.items[y][j].yTile && Item.isPulleyLeftSide(ItemFitting.items[y][j], j)) {
                        addBlockingItem(ItemFitting.items[y][j]);
                        triggered = true;
                    }
                }
                for (int y = i; y <= i + 10; ++y) {
                    if (tileIsInLevelArea(j, y) && ItemFitting.items[y][j] != null && ItemFitting.items[y][j].spaceRequirement == 23 && y >= ItemFitting.items[y][j].yTile - 6 && !Item.isPulleyLeftSide(ItemFitting.items[y][j], j)) {
                        addBlockingItem(ItemFitting.items[y][j]);
                        triggered = true;
                    }
                }
                for (int y = i; y >= 2; --y) {
                    if (tileIsInLevelArea(j, y) && ItemFitting.items[y][j] != null && ItemFitting.items[y][j].spaceRequirement == 23 && y >= ItemFitting.items[y][j].yTile && !Item.isPulleyLeftSide(ItemFitting.items[y][j], j)) {
                        addBlockingItem(ItemFitting.items[y][j]);
                        triggered = true;
                    }
                }
            }
        }
        return triggered;
    }
    
    private static boolean isAllowedInPlatformMotionArea(final Item i) {
        if (i == null) {
            return false;
        }
        final int s = i.spaceRequirement;
        final char c = i.character;
        return s == 0 || s == 1 || c == 'h';
    }
    
    private static boolean isInRepeatingPlatformColumn(final Rect r) {
        boolean triggered = false;
        for (int i = r.x1; i <= r.x2; ++i) {
            if (tileIsInLevelArea(i, 0) && ItemFitting.items[0][i] != null && ItemFitting.items[0][i].spaceRequirement == 3) {
                addBlockingItem(ItemFitting.items[0][i]);
                triggered = true;
            }
        }
        return triggered;
    }
    
    private static boolean areaHasItemsNotAllowedInPlatformMotionArea(final Rect r) {
        boolean triggered = false;
        for (int i = r.y1; i <= r.y2; ++i) {
            for (int j = r.x1; j <= r.x2; ++j) {
                if (tileIsInLevelArea(j, i) && ItemFitting.items[i][j] != null && !isAllowedInPlatformMotionArea(ItemFitting.items[i][j])) {
                    addBlockingItem(ItemFitting.items[i][j]);
                    triggered = true;
                }
            }
        }
        return triggered;
    }
    
    private static boolean isInPlatformPaddingArea() {
        if (isAllowedInPlatformPaddingArea(ItemFitting.item)) {
            return false;
        }
        boolean triggered = false;
        for (int i = ItemFitting.testRect.y1; i <= ItemFitting.testRect.y2; ++i) {
            for (int j = ItemFitting.testRect.x1 - 0; j <= ItemFitting.testRect.x2 + 0; ++j) {
                if (tileIsInLevelArea(j, i) && ItemFitting.items[0][j] != null && ItemFitting.items[0][j].spaceRequirement == 3) {
                    addBlockingItem(ItemFitting.items[0][j]);
                    triggered = true;
                }
                for (int y = i - 9 - 4; y <= i + 9 + 4; ++y) {
                    if (tileIsInLevelArea(j, y) && ItemFitting.items[y][j] != null && ItemFitting.items[y][j].spaceRequirement == 24) {
                        addBlockingItem(ItemFitting.items[y][j]);
                        triggered = true;
                    }
                }
                for (int y = 0; y <= i; ++y) {
                    if (tileIsInLevelArea(j, y) && ItemFitting.items[y][j] != null && ItemFitting.items[y][j].spaceRequirement == 22) {
                        addBlockingItem(ItemFitting.items[y][j]);
                        triggered = true;
                    }
                }
                for (int y = i - 4; y <= i + 4; ++y) {
                    if (tileIsInLevelArea(j, y) && ItemFitting.items[y][j] != null && ItemFitting.items[y][j].spaceRequirement == 7) {
                        addBlockingItem(ItemFitting.items[y][j]);
                        triggered = true;
                    }
                }
                for (int y = i; y <= i + 6; ++y) {
                    if (tileIsInLevelArea(j, y) && ItemFitting.items[y][j] != null && ItemFitting.items[y][j].spaceRequirement == 23 && y >= ItemFitting.items[y][j].yTile - 6 && Item.isPulleyLeftSide(ItemFitting.items[y][j], j)) {
                        addBlockingItem(ItemFitting.items[y][j]);
                        triggered = true;
                    }
                }
                for (int y = i; y >= 2; --y) {
                    if (tileIsInLevelArea(j, y) && ItemFitting.items[y][j] != null && ItemFitting.items[y][j].spaceRequirement == 23 && y >= ItemFitting.items[y][j].yTile && Item.isPulleyLeftSide(ItemFitting.items[y][j], j)) {
                        addBlockingItem(ItemFitting.items[y][j]);
                        triggered = true;
                    }
                }
                for (int y = i; y <= i + 10; ++y) {
                    if (tileIsInLevelArea(j, y) && ItemFitting.items[y][j] != null && ItemFitting.items[y][j].spaceRequirement == 23 && y >= ItemFitting.items[y][j].yTile - 6 && !Item.isPulleyLeftSide(ItemFitting.items[y][j], j)) {
                        addBlockingItem(ItemFitting.items[y][j]);
                        triggered = true;
                    }
                }
                for (int y = i; y >= 2; --y) {
                    if (tileIsInLevelArea(j, y) && ItemFitting.items[y][j] != null && ItemFitting.items[y][j].spaceRequirement == 23 && y >= ItemFitting.items[y][j].yTile && !Item.isPulleyLeftSide(ItemFitting.items[y][j], j)) {
                        addBlockingItem(ItemFitting.items[y][j]);
                        triggered = true;
                    }
                }
            }
        }
        return triggered;
    }
    
    private static boolean isAllowedInPlatformPaddingArea(final Item i) {
        return i != null && !i.immobile;
    }
    
    private static boolean areaHasItemsNotAllowedInPlatformPaddingArea(final Rect r) {
        boolean triggered = false;
        for (int i = r.y1; i <= r.y2; ++i) {
            for (int j = r.x1; j <= r.x2; ++j) {
                if (tileIsInLevelArea(j, i) && ItemFitting.items[i][j] != null && !isAllowedInPlatformPaddingArea(ItemFitting.items[i][j])) {
                    addBlockingItem(ItemFitting.items[i][j]);
                    triggered = true;
                }
            }
        }
        return triggered;
    }
    
    private static boolean isInPipePaddingArea() {
        if (isAllowedInPipePaddingArea(ItemFitting.item)) {
            return false;
        }
        boolean triggered = false;
        for (int i = ItemFitting.testRect.y1; i <= ItemFitting.testRect.y2; ++i) {
            for (int j = ItemFitting.testRect.x1; j <= ItemFitting.testRect.x2; ++j) {
                for (int y = i + 1; y <= i + 4; ++y) {
                    if (tileIsInLevelArea(j, y) && ItemFitting.items[y][j] != null && LevelLoader.isUpwardOpeningPipe(ItemFitting.items[y][j].character)) {
                        addBlockingItem(ItemFitting.items[y][j]);
                        triggered = true;
                    }
                }
                for (int y = i - 1; y >= i - 4; --y) {
                    if (tileIsInLevelArea(j, y) && ItemFitting.items[y][j] != null && LevelLoader.isDownwardOpeningPipe(ItemFitting.items[y][j].character)) {
                        addBlockingItem(ItemFitting.items[y][j]);
                        triggered = true;
                    }
                }
                for (int x = j + 1; x <= j + 2; ++x) {
                    if (tileIsInLevelArea(x, i) && ItemFitting.items[i][x] != null && LevelLoader.isLeftOpeningPipe(ItemFitting.items[i][x].character)) {
                        addBlockingItem(ItemFitting.items[i][x]);
                        triggered = true;
                    }
                }
                for (int x = j - 1; x >= j - 2; --x) {
                    if (tileIsInLevelArea(x, i) && ItemFitting.items[i][x] != null && LevelLoader.isRightOpeningPipe(ItemFitting.items[i][x].character)) {
                        addBlockingItem(ItemFitting.items[i][x]);
                        triggered = true;
                    }
                }
            }
        }
        return triggered;
    }
    
    private static boolean isAllowedInPipePaddingArea(final Item i) {
        return i != null && !i.immobile;
    }
    
    private static boolean areaHasItemsNotAllowedInPipePaddingArea(final Rect r) {
        boolean triggered = false;
        for (int i = r.y1; i <= r.y2; ++i) {
            for (int j = r.x1; j <= r.x2; ++j) {
                if (tileIsInLevelArea(j, i) && ItemFitting.items[i][j] != null && !isAllowedInPipePaddingArea(ItemFitting.items[i][j])) {
                    addBlockingItem(ItemFitting.items[i][j]);
                    triggered = true;
                }
            }
        }
        return triggered;
    }
    
    private static boolean isTooCloseToBeanstalkBlock() {
        if (isAllowedTooCloseToBeanstalk(ItemFitting.item)) {
            return false;
        }
        boolean triggered = false;
        for (int i = ItemFitting.testRect.y1; i <= ItemFitting.testRect.y2; ++i) {
            for (int j = ItemFitting.testRect.x1; j <= ItemFitting.testRect.x2; ++j) {
                for (int y = i; y <= i + 3; ++y) {
                    if (tileIsInLevelArea(j, y) && ItemFitting.items[y][j] != null && LevelLoader.isBeanstalkBlock(ItemFitting.items[y][j].character)) {
                        addBlockingItem(ItemFitting.items[y][j]);
                        triggered = true;
                    }
                }
            }
        }
        return triggered;
    }
    
    private static boolean isAllowedTooCloseToBeanstalk(final Item i) {
        return i.character == 'k' || i.character == '\u0292';
    }
    
    private static boolean isInBeanstalkBlockArea() {
        if (isAllowedInBeanstalkBlockArea(ItemFitting.item)) {
            return false;
        }
        boolean triggered = false;
        for (int i = ItemFitting.testRect.y1; i <= ItemFitting.testRect.y2; ++i) {
            for (int j = ItemFitting.testRect.x1; j <= ItemFitting.testRect.x2; ++j) {
                for (int y = i; y <= Game.yTiles - 1; ++y) {
                    if (tileIsInLevelArea(j, y) && ItemFitting.items[y][j] != null && LevelLoader.isBeanstalkBlock(ItemFitting.items[y][j].character)) {
                        addBlockingItem(ItemFitting.items[y][j]);
                        triggered = true;
                    }
                }
            }
        }
        return triggered;
    }
    
    private static boolean isAllowedInBeanstalkBlockArea(final Item i) {
        return i != null && !i.immobile;
    }
    
    private static boolean areaHasItemsNotAllowedInBeanstalkBlockArea(final Rect r) {
        boolean triggered = false;
        for (int i = r.y1; i <= r.y2; ++i) {
            for (int j = r.x1; j <= r.x2; ++j) {
                if (tileIsInLevelArea(j, i) && ItemFitting.items[i][j] != null && !isAllowedInBeanstalkBlockArea(ItemFitting.items[i][j])) {
                    addBlockingItem(ItemFitting.items[i][j]);
                    triggered = true;
                }
            }
        }
        return triggered;
    }
    
    private static boolean areaHasItemsTooCloseToBeanstalkBlock(final Rect r) {
        boolean triggered = false;
        for (int i = r.y1; i <= r.y2; ++i) {
            for (int j = r.x1; j <= r.x2; ++j) {
                if (tileIsInLevelArea(j, i) && ItemFitting.items[i][j] != null && ItemFitting.items[i][j].character != 'k' && ItemFitting.items[i][j].character != '\u0292') {
                    addBlockingItem(ItemFitting.items[i][j]);
                    triggered = true;
                }
            }
        }
        return triggered;
    }
    
    private static boolean isInContentsBlockArea() {
        if (isAllowedInContentsBlockArea(ItemFitting.item)) {
            return false;
        }
        boolean triggered = false;
        for (int i = ItemFitting.testRect.y1; i <= ItemFitting.testRect.y2; ++i) {
            for (int j = ItemFitting.testRect.x1; j <= ItemFitting.testRect.x2; ++j) {
                for (int y = i + 1; y <= i + 2; ++y) {
                    if (tileIsInLevelArea(j, y) && ItemFitting.items[y][j] != null && isContentsBlockTypeItem(ItemFitting.items[y][j])) {
                        addBlockingItem(ItemFitting.items[y][j]);
                        triggered = true;
                    }
                }
            }
        }
        return triggered;
    }
    
    private static boolean isAllowedInContentsBlockArea(final Item i) {
        return i != null && !i.immobile;
    }
    
    private static boolean areaHasItemsNotAllowedInContentsBlockArea(final Rect r) {
        boolean triggered = false;
        for (int i = r.y1; i <= r.y2; ++i) {
            for (int j = r.x1; j <= r.x2; ++j) {
                if (tileIsInLevelArea(j, i) && ItemFitting.items[i][j] != null && !isAllowedInContentsBlockArea(ItemFitting.items[i][j])) {
                    addBlockingItem(ItemFitting.items[i][j]);
                    triggered = true;
                }
            }
        }
        return triggered;
    }
    
    private static boolean isInInvisibleBlockArea() {
        if (isAllowedInInvisibleBlockArea(ItemFitting.item)) {
            return false;
        }
        boolean triggered = false;
        for (int i = ItemFitting.testRect.y1; i <= ItemFitting.testRect.y2; ++i) {
            for (int j = ItemFitting.testRect.x1; j <= ItemFitting.testRect.x2; ++j) {
                for (int y = i - 1; y >= i - 2; --y) {
                    if (tileIsInLevelArea(j, y) && ItemFitting.items[y][j] != null && isInvisibleTypeItem(ItemFitting.items[y][j])) {
                        addBlockingItem(ItemFitting.items[y][j]);
                        triggered = true;
                    }
                }
            }
        }
        return triggered;
    }
    
    private static boolean isAllowedInInvisibleBlockArea(final Item i) {
        return i == null || !i.immobile;
    }
    
    private static boolean areaHasItemsNotAllowedInInvisibleBlockArea(final Rect r) {
        boolean triggered = false;
        for (int i = r.y1; i <= r.y2; ++i) {
            for (int j = r.x1; j <= r.x2; ++j) {
                if (tileIsInLevelArea(j, i) && ItemFitting.items[i][j] != null && !isAllowedInInvisibleBlockArea(ItemFitting.items[i][j])) {
                    addBlockingItem(ItemFitting.items[i][j]);
                    triggered = true;
                }
            }
        }
        return triggered;
    }
    
    private static boolean tileIsInLevelArea(final int x, final int y) {
        return x >= 0 && x < ItemFitting.levelLength && y >= 0 && y < Game.yTiles;
    }
    
    private static boolean tileIsWithinXBounds(final int leftMostX, final int rightMostX) {
        return ItemFitting.itemX >= leftMostX && ItemFitting.itemX <= rightMostX;
    }
    
    private static boolean tileIsWithinYBounds(final int topMostY, final int bottomMostY) {
        return ItemFitting.itemY >= topMostY && ItemFitting.itemY <= bottomMostY;
    }
    
    private static boolean areaHasUnavailableTile(final Rect r) {
        boolean triggered = false;
        for (int i = r.y1; i <= r.y2; ++i) {
            for (int j = r.x1; j <= r.x2; ++j) {
                if (!tileIsInLevelArea(j, i) && !itemAllowsPartialInsertion(ItemFitting.item)) {
                    triggered = true;
                }
                else if (tileIsInLevelArea(j, i) && ItemFitting.items[i][j] != null) {
                    addBlockingItem(ItemFitting.items[i][j]);
                    triggered = true;
                }
            }
        }
        return triggered;
    }
    
    public static boolean isInvisibleTypeItem(final Item i) {
        return i.spaceRequirement == 27 || i.spaceRequirement == 26 || i.spaceRequirement == 28;
    }
    
    public static boolean isContentsBlockTypeItem(final Item i) {
        return i.spaceRequirement == 18 || i.spaceRequirement == 28 || i.spaceRequirement == 27;
    }
    
    private static boolean isAdjacentToItem(final int x, final int y, final char c) {
        for (int i = y - 1; i <= y + 1; ++i) {
            for (int j = x - 1; j <= x + 1; ++j) {
                if ((j != x || i != y) && tileIsInLevelArea(j, i) && ItemFitting.items[i][j] != null && ItemFitting.items[i][j].character == c) {
                    return true;
                }
            }
        }
        return false;
    }
    
    static {
        ItemFitting.testRect = new Rect();
        rect = new Rect();
        rect2 = new Rect();
        rect3 = new Rect();
    }
    
    public static class Rect
    {
        public static Rect tempRect;
        public int x1;
        public int x2;
        public int y1;
        public int y2;
        
        public Rect setBounds(final int x1, final int y1, final int x2, final int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            return this;
        }
        
        public void setRectAsUseless() {
            final int n = -1;
            this.y2 = n;
            this.y1 = n;
            this.x2 = n;
            this.x1 = n;
        }
        
        public boolean isRectSetAsUseless() {
            return this.x1 == -1 && this.x2 == -1 && this.y1 == -1 && this.y2 == -1;
        }
        
        static {
            Rect.tempRect = new Rect();
        }
    }
}
