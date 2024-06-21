package com.sereneoasis.enchantments;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface EnchantmentFunction {

    void performFunction(Player player, Entity target);
}
