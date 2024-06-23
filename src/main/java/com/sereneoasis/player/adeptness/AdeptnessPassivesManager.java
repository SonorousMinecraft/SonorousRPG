package com.sereneoasis.player.adeptness;

import com.sereneoasis.player.SerenePlayer;
import com.sereneoasis.utils.ReflectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

/***
 * Organises all passives for all players for all adeptness organised by level
 */
public class AdeptnessPassivesManager {


    private static final HashMap<PlayerAdeptness, AdeptnessPassiveTracker> adeptnessPassivesMap = new HashMap<>();

    public static void enablePassives(){
        Arrays.stream(PlayerAdeptness.values()).forEach(playerAdeptness -> initPassives(playerAdeptness));
    }

    public static void checkForPassives(PlayerAdeptness adeptness, SerenePlayer serenePlayer, Event event){
        adeptnessPassivesMap.get(adeptness).getOrderedPassives().forEach(passive -> {
            if (!serenePlayer.getDisabledPassives().contains(passive)) {
                passive.performPassive(event);
            }
        });
    }

    /***
     * Uses Reflection to initialise all passives of an adeptness
     */
    private static void initPassives(PlayerAdeptness adeptness) {
        Set<Passive> passiveSet = new HashSet<>();
        ReflectionUtils.findAllClasses("com.sereneoasis.player.adeptness.passives." + adeptness.getName().toLowerCase(), Passive.class).stream()
                .forEach(aClass -> {
                    try {
                        Bukkit.getServer().getLogger().log(Level.INFO, () -> aClass.getName() + " is loaded");
                        Passive passive = (Passive) aClass.newInstance();
                        passiveSet.add(passive);
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
        AdeptnessPassiveTracker adeptnessPassiveTracker = new AdeptnessPassiveTracker(adeptness, passiveSet);
        adeptnessPassivesMap.put(adeptness, adeptnessPassiveTracker);
    }


    public static List<Passive>getAdeptnessPassives(PlayerAdeptness playerAdeptness){
        return adeptnessPassivesMap.get(playerAdeptness).getOrderedPassives();
    }

    /***
     * Tracks passives of a particular adeptness
     */
    public static class AdeptnessPassiveTracker {

        private final PlayerAdeptness playerAdeptness;

        private final List<Passive> orderedPassives;
        public AdeptnessPassiveTracker(PlayerAdeptness playerAdeptness, Collection<Passive> passiveCollection){
            this.playerAdeptness = playerAdeptness;
            this.orderedPassives = passiveCollection.stream().sorted(Comparator.comparingInt(Passive::getRequiredLevel)).collect(Collectors.toList());
        }

        public List<Passive> getOrderedPassives() {
            return orderedPassives;
        }
    }
}
