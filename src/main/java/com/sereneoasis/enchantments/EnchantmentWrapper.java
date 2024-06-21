package com.sereneoasis.enchantments;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EnchantmentWrapper {

    public EnchantmentWrapper(ItemStack itemStack, Enchantments enchantment) {

        ItemMeta meta = itemStack.getItemMeta();
        List<String> newLore = new ArrayList<>();
        if (meta.hasLore() ){
            newLore = meta.getLore();
        }
        newLore.add(enchantment.getColor() + enchantment.getName());
        meta.setLore(newLore);
        itemStack.setItemMeta(meta);

    }
}
