package com.sereneoasis.utils;

import com.sereneoasis.SereneRPG;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;

public class EconUtils {

    public static void payPlayer(Player player, double amount){
        Economy econ = SereneRPG.getEconomy();
        player.sendMessage(String.format("You have %s", econ.format(econ.getBalance(player.getName()))));
        EconomyResponse r = econ.depositPlayer(player, amount);
        if(r.transactionSuccess()) {
            player.sendMessage(String.format("You were given %s and now have %s", econ.format(r.amount), econ.format(r.balance)));
        } else {
            player.sendMessage(String.format("An error occured: %s", r.errorMessage));
        }
    }

    public static void withdrawPlayer(Player player, double amount){
        Economy econ = SereneRPG.getEconomy();
        player.sendMessage(String.format("You have %s", econ.format(econ.getBalance(player.getName()))));
        EconomyResponse r = econ.withdrawPlayer(player, amount);
        if(r.transactionSuccess()) {
            player.sendMessage(String.format("You lost %s and now have %s", econ.format(r.amount), econ.format(r.balance)));
        } else {
            player.sendMessage(String.format("An error occured: %s", r.errorMessage));
        }
    }
}
