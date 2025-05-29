package dungeon.textgame;

import static org.junit.jupiter.api.Assertions.*;

import dungeon.engine.*;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the TileOpen class.
 */
public class TestTileOpen {

    /**
     * Tests whether TileOpen correctly identifies whether it contains an item.
     */
    @Test
    public void testHasItem() {
        TileOpen emptyTile = new TileOpen();
        assertFalse(emptyTile.hasItem(), "Tile should not have an item initially.");

        ItemGold gold = new ItemGold(5);
        TileOpen itemTile = new TileOpen(gold);
        assertTrue(itemTile.hasItem(), "Tile should have an item after being initialized with one.");
    }

    /**
     * Tests whether TileOpen correctly returns the appropriate symbol
     * based on whether it contains an item.
     */
    @Test
    public void testGetSymbol() {
        TileOpen emptyTile = new TileOpen();
        assertEquals('.', emptyTile.getSymbol(), "Empty tile should return '.' as its symbol.");

        ItemGold gold = new ItemGold(5);
        TileOpen itemTile = new TileOpen(gold);
        assertEquals('G', itemTile.getSymbol(), "Tile with an item should return item's symbol.");
    }

    /**
     * Tests whether TileOpen correctly interacts with a player by applying item effects
     * and ensuring appropriate item behavior post-interaction.
     */
    @Test
    public void testInteractWithPlayer() {
        Player player = new Player("Test", 10, 0, 0, 1);
        ItemHealthPotion potion = new ItemHealthPotion(4);
        TileOpen itemTile = new TileOpen(potion);

        // Player interacts with a health potion
        itemTile.interactWithPlayer(player);
        assertEquals(10, player.getHealth(), "Player's health should increase but not exceed max.");
        assertFalse(itemTile.hasItem(), "Non-trap items should disappear after interaction.");

        // Player interacts with a trap (should NOT disappear)
        ItemTrap trap = new ItemTrap(3);
        TileOpen trapTile = new TileOpen(trap);
        trapTile.interactWithPlayer(player);
        assertTrue(trapTile.hasItem(), "Trap items should remain after interaction.");
    }
}
