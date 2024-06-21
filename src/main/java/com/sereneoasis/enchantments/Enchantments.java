package com.sereneoasis.enchantments;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public enum Enchantments {


    FROSTBANE("Frostbane", 1, ChatColor.AQUA, (player, target) -> {
        if (target != null && !target.isDead()){
            target.setFreezeTicks(20);
            target.setVelocity(new Vector());
            target.getLocation().getBlock().setType(Material.POWDER_SNOW);
        }
    });

    private static BukkitScheduler scheduler = Bukkit.getScheduler();


    private String name;

    private int maxEnchants;

    private ChatColor color;

    private EnchantmentFunction enchantmentFunction;

    Enchantments(String name, int maxEnchants, ChatColor chatColor, EnchantmentFunction enchantmentFunction) {
        this.name = name;
        this.maxEnchants = maxEnchants;
        this.color = chatColor;
        this.enchantmentFunction = enchantmentFunction;
    }

    public String getName() {
        return name;
    }

    public int getMaxEnchants() {
        return maxEnchants;
    }

    public ChatColor getColor() {
        return color;
    }

    public EnchantmentFunction getEnchantmentFunction() {
        return enchantmentFunction;
    }
}
