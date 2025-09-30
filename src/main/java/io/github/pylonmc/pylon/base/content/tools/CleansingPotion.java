package io.github.pylonmc.pylon.base.content.tools;

import io.github.pylonmc.pylon.core.item.PylonItem;
import io.github.pylonmc.pylon.core.item.base.PylonCustomProjectile;
import io.github.pylonmc.pylon.core.item.base.PylonSplashPotion;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.SplashPotion;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import static java.util.Objects.requireNonNull;

public class CleansingPotion extends PylonItem implements PylonCustomProjectile<SplashPotion>, PylonSplashPotion {
    public CleansingPotion(@NotNull ItemStack stack) {
        super(stack);
    }

    @Override
    public void onDispense(@NotNull BlockDispenseEvent event) {}

    @Override
    public void onSplash(@NotNull PotionSplashEvent event) {
        for (LivingEntity entity : event.getAffectedEntities()) {
            if (entity instanceof ZombieVillager villager) {
                // Convert to regular villager
                villager.setConversionTime(0, true);
                villager.heal(requireNonNull(villager.getAttribute(Attribute.MAX_HEALTH)).getValue());
            }
        }
    }

    @Override
    public @NotNull Class<SplashPotion> getProjectileType() {
        return SplashPotion.class;
    }

    @Override
    public boolean getConsumeWhenLaunched() {
        return true;
    }

    @Override
    public boolean getDispensable() {
        return true;
    }
}
