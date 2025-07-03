package io.github.pylonmc.pylon.base.fluid.pipe.connection;

import io.github.pylonmc.pylon.core.entity.EntityStorage;
import io.github.pylonmc.pylon.core.entity.display.PylonItemDisplay;
import io.github.pylonmc.pylon.core.entity.display.builder.ItemDisplayBuilder;
import io.github.pylonmc.pylon.core.entity.display.builder.transform.TransformBuilder;
import io.github.pylonmc.pylon.core.fluid.FluidConnectionPoint;
import me.tofaa.entitylib.wrapper.WrapperEntity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockFace;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;

import static io.github.pylonmc.pylon.base.util.KeyUtils.pylonKey;


public class FluidConnectionDisplay extends PylonItemDisplay {

    public static final NamespacedKey KEY = pylonKey("fluid_connection_display");

    @SuppressWarnings("unused")
    public FluidConnectionDisplay(@NotNull WrapperEntity entity, @NotNull NamespacedKey key, @NotNull Location location) {
        super(entity, key, location);
    }

    @SuppressWarnings("unused")
    public FluidConnectionDisplay(@NotNull WrapperEntity entity, @NotNull PersistentDataContainer pdc) {
        super(entity, pdc);
    }

    private static @NotNull Material materialFromType(@NotNull FluidConnectionPoint.Type type) {
        return switch (type) {
            case INPUT -> Material.LIME_CONCRETE;
            case OUTPUT -> Material.RED_CONCRETE;
            case CONNECTOR -> Material.GRAY_CONCRETE;
        };
    }

    private static @NotNull FluidConnectionDisplay makeDisplay(@NotNull FluidConnectionPoint point, @NotNull Vector3d translation) {
        return (FluidConnectionDisplay) new ItemDisplayBuilder()
                .material(materialFromType(point.getType()))
                .brightness(7)
                .transformation(new TransformBuilder()
                        .translate(translation)
                        .scale(FluidConnectionInteraction.POINT_SIZE))
                .buildPacketBased(KEY, point.getPosition().getLocation().toCenterLocation());
    }

    /**
     * Convenience function that constructs the display, but then also adds it to EntityStorage
     */
    public static @NotNull FluidConnectionDisplay make(@NotNull FluidConnectionPoint point, @NotNull BlockFace face, float radius) {
        FluidConnectionDisplay display = makeDisplay(point, face.getDirection().clone().multiply(radius).toVector3d());
        EntityStorage.add(display);
        return display;
    }

    /**
     * Convenience function that constructs the display, but then also adds it to EntityStorage
     */
    public static @NotNull FluidConnectionDisplay make(@NotNull FluidConnectionPoint point) {
        FluidConnectionDisplay display = makeDisplay(point, new Vector3d(0, 0, 0));
        EntityStorage.add(display);
        return display;
    }
}
