package io.github.pylonmc.pylon.base.items.multiblocks;

import io.github.pylonmc.pylon.core.block.PylonBlock;
import io.github.pylonmc.pylon.core.block.base.PylonEntityHolderBlock;
import io.github.pylonmc.pylon.core.block.base.PylonInteractableBlock;
import io.github.pylonmc.pylon.core.block.context.BlockBreakContext;
import io.github.pylonmc.pylon.core.block.context.BlockCreateContext;
import io.github.pylonmc.pylon.core.datatypes.PylonSerializers;
import io.github.pylonmc.pylon.core.entity.PylonEntity;
import io.github.pylonmc.pylon.core.entity.display.PylonItemDisplay;
import io.github.pylonmc.pylon.core.entity.display.builder.ItemDisplayBuilder;
import io.github.pylonmc.pylon.core.entity.display.builder.transform.TransformBuilder;
import lombok.Setter;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

import static io.github.pylonmc.pylon.base.util.KeyUtils.pylonKey;
import static java.lang.Math.PI;

public class Pedestal extends PylonBlock implements PylonEntityHolderBlock, PylonInteractableBlock {

    public static final NamespacedKey PEDESTAL_KEY = pylonKey("pedestal");
    public static final NamespacedKey MAGIC_PEDESTAL_KEY = pylonKey("magic_pedestal");

    public static final NamespacedKey PEDESTAL_ITEM_KEY = pylonKey("pedestal_item");

    private static final NamespacedKey ROTATION_KEY = pylonKey("rotation");
    private static final NamespacedKey LOCKED_KEY = pylonKey("locked");
    private double rotation;
    @Setter
    private boolean locked;

    @SuppressWarnings("unused")
    public Pedestal(@NotNull Block block, @NotNull BlockCreateContext context) {
        super(block);

        rotation = 0;
        locked = false;
}

    @SuppressWarnings({"unused", "DataFlowIssue"})
    public Pedestal(@NotNull Block block, @NotNull PersistentDataContainer pdc) {
        super(block);
        
        rotation = pdc.get(ROTATION_KEY, PylonSerializers.DOUBLE);
        locked = pdc.get(LOCKED_KEY, PylonSerializers.BOOLEAN);
    }
    
    @Override
    public @NotNull Map<String, PylonEntity> createEntities(@NotNull BlockCreateContext context) {
        PylonItemDisplay display = new ItemDisplayBuilder()
                .transformation(transformBuilder().buildForItemDisplay())
                .buildPacketBased(PEDESTAL_ITEM_KEY, getBlock().getLocation().toCenterLocation());
        return Map.of("item", display);
    }

    public TransformBuilder transformBuilder() {
        return new TransformBuilder()
                .translate(0, 0.7, 0)
                .scale(0.4)
                .rotate(0, rotation, 0);
    }

    @Override
    public void write(@NotNull PersistentDataContainer pdc) {
        pdc.set(ROTATION_KEY, PylonSerializers.DOUBLE, rotation);
        pdc.set(LOCKED_KEY, PylonSerializers.BOOLEAN, locked);
    }

    @Override
    public void onInteract(@NotNull PlayerInteractEvent event) {
        if (locked || event.getHand() != EquipmentSlot.HAND || event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        event.setCancelled(true);

        PylonItemDisplay display = getItemDisplay();

        // rotate
        if (event.getPlayer().isSneaking()) {
            rotation += PI / 4;
            display.setTransformation(transformBuilder().buildForItemDisplay());
            return;
        }

        // drop old item
        ItemStack oldStack = display.getItem();
        ItemStack newStack = event.getItem();
        if (!oldStack.getType().isAir()) {
            display.getLocation().getWorld().dropItem(display.getLocation().toCenterLocation().add(0, 0.7, 0), oldStack);
            display.setItem(ItemStack.empty());
            return;
        }

        // insert new item
        if (newStack != null) {
            ItemStack stackToInsert = newStack.clone();
            stackToInsert.setAmount(1);
            display.setItem(stackToInsert);
            newStack.subtract();
        }
    }

    @Override
    public void onBreak(@NotNull List<ItemStack> drops, @NotNull BlockBreakContext context) {
        PylonEntityHolderBlock.super.onBreak(drops, context);
        drops.add(getItemDisplay().getItem());
    }

    public PylonItemDisplay getItemDisplay() {
        return getHeldEntity(PylonItemDisplay.class, "item");
    }
}
