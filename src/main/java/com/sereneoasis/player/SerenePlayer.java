package com.sereneoasis.player;

import com.nivixx.ndatabase.api.NDatabase;
import com.nivixx.ndatabase.api.repository.Repository;
import com.sereneoasis.player.adeptness.Passive;
import com.sereneoasis.player.adeptness.PlayerAdeptness;
import com.sereneoasis.player.specialisation.Specialisation;
import com.sereneoasis.player.specialisation.SpecialisationGUI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SerenePlayer {

    private static final Map<UUID, SerenePlayer> SERENE_PLAYER_DATA_MAP = new ConcurrentHashMap<>();

    public static SerenePlayer getSerenePlayer(Player player){
        return SERENE_PLAYER_DATA_MAP.get(player.getUniqueId());
    }

    private static final Repository<UUID, PlayerData> repository = NDatabase.api().getOrCreateRepository(PlayerData.class);;


    private final Player player;

    public Player getPlayer() {
        return player;
    }

    private Specialisation specialisation;

    private Set<Passive> disabledPassives = new HashSet<>();

    public Set<Passive> getDisabledPassives() {
        return disabledPassives;
    }

    private final HashMap<PlayerAdeptness, Double>adeptnessExpHashMap = new HashMap<>();

    public double getAdeptness(PlayerAdeptness playerAdeptness){
        return adeptnessExpHashMap.get(playerAdeptness);
    }

    public int getAdeptnessLevel (PlayerAdeptness playerAdeptness){
        return (int) Math.floor(Math.sqrt(adeptnessExpHashMap.get(playerAdeptness)));
    }

    public void incrementAdeptness(PlayerAdeptness playerAdeptness, double amount){
        double newAdeptness = adeptnessExpHashMap.get(playerAdeptness) + 1;
        adeptnessExpHashMap.put(playerAdeptness, newAdeptness);
        double level = Math.sqrt(newAdeptness);
        double previousLevel = Math.sqrt(newAdeptness - 1);
        if (Math.round(level) !=  Math.round(previousLevel)) {
            BaseComponent[] component =
                    new ComponentBuilder(playerAdeptness.name() + ": Level " + Math.round(level)).color(ChatColor.RED).create();
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);
        }
    }

    public void setSpecialisation(Specialisation specialisation) {
        this.specialisation = specialisation;
    }

    public SerenePlayer(Player player, Specialisation specialisation){
        this.player = player;
        this.specialisation = specialisation;
        SERENE_PLAYER_DATA_MAP.put(player.getUniqueId(), this);
        Arrays.stream(PlayerAdeptness.values()).forEach(playerAdeptness -> adeptnessExpHashMap.put(playerAdeptness, (double) 0));
    }

    public SerenePlayer(Player player, Specialisation specialisation,
                        double unarmed, double swords, double mining, double movement, double tools, double ranged){
        this.player = player;
        this.specialisation = specialisation;
        SERENE_PLAYER_DATA_MAP.put(player.getUniqueId(), this);
        adeptnessExpHashMap.put(PlayerAdeptness.UNARMED, unarmed);
        adeptnessExpHashMap.put(PlayerAdeptness.SWORDS, swords);
        adeptnessExpHashMap.put(PlayerAdeptness.MINING, mining);
        adeptnessExpHashMap.put(PlayerAdeptness.MOVEMENT, movement);
        adeptnessExpHashMap.put(PlayerAdeptness.TOOLS, tools);
        adeptnessExpHashMap.put(PlayerAdeptness.RANGED, ranged);

    }

    public static void loadPlayer(Player player) {
        UUID uuid = player.getUniqueId();
        if (SERENE_PLAYER_DATA_MAP.containsKey(uuid)) {
            return;
        }

        if (repository.get(uuid) == null) {

            new SerenePlayer(player, Specialisation.NONE);
            upsertPlayer(player);
            new SpecialisationGUI().openGUI(player);

        } else {
            repository.getAsync(uuid).thenAsync((PlayerData) -> {
                new SerenePlayer(player, Specialisation.getFromName(PlayerData.getSpecialisation()),
                        PlayerData.getUnarmed(), PlayerData.getSwords(), PlayerData.getMining(), PlayerData.getMovement(), PlayerData.getTools(), PlayerData.getRanged()
                );
            });
        }
    }

    public static void upsertPlayer(Player player) {
        SerenePlayer serenePlayer = SERENE_PLAYER_DATA_MAP.get(player.getUniqueId());
        PlayerData playerData = new PlayerData();
        playerData.setKey(player.getUniqueId());
        playerData.setSpecialisation(serenePlayer.specialisation.getName());
        playerData.setUnarmed(serenePlayer.getAdeptness(PlayerAdeptness.UNARMED));
        playerData.setSwords(serenePlayer.getAdeptness(PlayerAdeptness.SWORDS));
        playerData.setMining(serenePlayer.getAdeptness(PlayerAdeptness.MINING));
        playerData.setMovement(serenePlayer.getAdeptness(PlayerAdeptness.MOVEMENT));
        playerData.setTools(serenePlayer.getAdeptness(PlayerAdeptness.TOOLS));
        playerData.setRanged(serenePlayer.getAdeptness(PlayerAdeptness.RANGED));
        repository.upsertAsync(playerData);
    }


}
