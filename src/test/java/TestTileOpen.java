package dungeon.textgame;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestTileOpen {

    @Test
    public void testHasItem() {
        TileOpen emptyTile = new TileOpen();
        assertFalse(emptyTile.hasItem(), "Tile should not have an item initially.");

        ItemGold gold = new ItemGold(5);
        TileOpen itemTile = new TileOpen(gold);
        assertTrue(itemTile.hasItem(), "Tile should have an item after being initialized with one.");
    }

    @Test
    public void testGetSymbol() {
        TileOpen emptyTile = new TileOpen();
        assertEquals('.', emptyTile.getSymbol(), "Empty tile should return '.' as its symbol.");

        ItemGold gold = new ItemGold(5);
        TileOpen itemTile = new TileOpen(gold);
        assertEquals('G', itemTile.getSymbol(), "Tile with an item should return item's symbol.");
    }

    @Test
    public void testInteractWithPlayer() {
        Player player = new Player("Test", 10, 0, 0, 1);
        ItemHealthPotion potion = new ItemHealthPotion(4);
        TileOpen itemTile = new TileOpen(potion);

        itemTile.interactWithPlayer(player);
        assertEquals(10, player.getHealth(), "Player's health should increase but not exceed max.");

        assertFalse(itemTile.hasItem(), "Non-trap items should disappear after interaction.");

        // Test trap behavior (should NOT disappear)
        ItemTrap trap = new ItemTrap(3);
        TileOpen trapTile = new TileOpen(trap);
        trapTile.interactWithPlayer(player);

        assertTrue(trapTile.hasItem(), "Trap items should remain after interaction.");
    }
}
