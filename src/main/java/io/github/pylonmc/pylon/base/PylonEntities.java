package io.github.pylonmc.pylon.base;

import io.github.pylonmc.pylon.base.fluid.pipe.FluidPipeDisplay;
import io.github.pylonmc.pylon.base.fluid.pipe.connection.FluidConnectionDisplay;
import io.github.pylonmc.pylon.base.fluid.pipe.connection.FluidConnectionInteraction;
import io.github.pylonmc.pylon.base.items.fluid.*;
import io.github.pylonmc.pylon.base.items.multiblocks.Grindstone;
import io.github.pylonmc.pylon.base.items.multiblocks.Pedestal;
import io.github.pylonmc.pylon.base.items.multiblocks.smelting.SmelteryController;
import io.github.pylonmc.pylon.core.entity.PylonEntity;
import io.github.pylonmc.pylon.core.entity.display.PylonItemDisplay;
import io.github.pylonmc.pylon.core.entity.display.PylonTextDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Interaction;

public final class PylonEntities {

    private PylonEntities() {
        throw new AssertionError("Utility class");
    }

    public static void initialize() {
        PylonEntity.registerPacketBased(Grindstone.ITEM_ENTITY_KEY, EntityType.ITEM_DISPLAY, PylonItemDisplay.class);
        PylonEntity.registerPacketBased(Grindstone.BLOCK_ENTITY_KEY, EntityType.ITEM_DISPLAY, PylonItemDisplay.class);
        PylonEntity.registerPacketBased(FluidConnectionDisplay.KEY, EntityType.ITEM_DISPLAY, FluidConnectionDisplay.class);
        PylonEntity.registerReal(FluidConnectionInteraction.KEY, Interaction.class, FluidConnectionInteraction.class);
        PylonEntity.registerPacketBased(Pedestal.PEDESTAL_ITEM_KEY, EntityType.ITEM_DISPLAY, PylonItemDisplay.class);
        PylonEntity.registerPacketBased(FluidFilter.FluidDisplay.KEY, EntityType.ITEM_DISPLAY, FluidFilter.FluidDisplay.class);
        PylonEntity.registerPacketBased(FluidFilter.MAIN_DISPLAY_KEY, EntityType.ITEM_DISPLAY, PylonItemDisplay.class);
        PylonEntity.registerPacketBased(FluidPipeDisplay.KEY, EntityType.ITEM_DISPLAY, FluidPipeDisplay.class);
        PylonEntity.registerPacketBased(PortableFluidTank.FLUID_TANK_ENTITY_KEY, EntityType.ITEM_DISPLAY, PylonItemDisplay.class);
        PylonEntity.registerPacketBased(FluidValve.FluidValveDisplay.KEY, EntityType.ITEM_DISPLAY, FluidValve.FluidValveDisplay.class);
        PylonEntity.registerPacketBased(FluidMeter.FlowRateDisplay.KEY, EntityType.TEXT_DISPLAY, FluidMeter.FlowRateDisplay.class);
        PylonEntity.registerPacketBased(FluidVoider.MAIN_DISPLAY_KEY, EntityType.ITEM_DISPLAY, PylonItemDisplay.class);
        PylonEntity.registerPacketBased(SmelteryController.FLUID_PIXEL_KEY, EntityType.TEXT_DISPLAY, PylonTextDisplay.class);
    }
}
