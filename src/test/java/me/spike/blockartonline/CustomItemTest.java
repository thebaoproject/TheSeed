package me.spike.blockartonline;

import me.spike.blockartonline.abc.Ability;
import me.spike.blockartonline.abc.CustomItem;
import me.spike.blockartonline.abc.ItemAbilityUseAction;
import me.spike.blockartonline.customItems.AnnealBlade;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomItemTest {

    CustomItem c;
    Player shitass;
    Block fakeBlock;
    BlockFace blockFace;

    @BeforeEach
    public void setupTests() {
        c = new AnnealBlade();
        shitass = mock(Player.class);
        fakeBlock = mock(Block.class);
        blockFace = BlockFace.UP;
        when(shitass.getName()).thenReturn("shitass");
        when(fakeBlock.getType()).thenReturn(Material.DIAMOND_BLOCK);

    }

    @Test
    public void getName() {
        Assertions.assertEquals("Anneal Blade", c.getName(), "Name should be the factory defined one.");
    }

    @Test
    public void setName() {
        c.setName("shitass");
        Assertions.assertEquals("shitass", c.getName(), "Name should be the changed one.");
    }

    @Test
    public void getID() {
        Assertions.assertEquals("anneal_blade", c.getID(), "ID should be factory defined.");
    }

    @Test
    public void setID() {
        c.setID("shit_ass");
        Assertions.assertEquals("shit_ass", c.getID(), "ID should be factory defined.");
    }

    @Test
    public void getAbilities() {
        Assertions.assertEquals(ItemAbilityUseAction.RIGHT_CLICK, c.getAbilities().get(0).getUsage(), "Abilities should the same as the factory configured.");
    }

    @Test
    public void setAbilities() {
        List<Ability> a = List.of(new Ability().setName("ultra poop").setDescription("poop").setUsage(ItemAbilityUseAction.RIGHT_CLICK));
        c.setAbilities(a);
        Assertions.assertEquals(a, c.getAbilities(), "Abilities should be the one set");

    }
}
