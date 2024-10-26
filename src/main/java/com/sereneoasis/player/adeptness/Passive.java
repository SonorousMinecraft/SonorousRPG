package com.sereneoasis.player.adeptness;

import org.bukkit.event.Event;

import java.util.List;

public abstract class Passive {

    private String name;
    private List<String> description;
    private int requiredLevel;

    private PassiveFunction passiveFunction;

    public Passive(String name, List<String> description, int requiredLevel, PassiveFunction function){
        this.name = name;
        this.description = description;
        this.requiredLevel = requiredLevel;
        this.passiveFunction = function;
    }

    public String getName() {
        return name;
    }

    public List<String> getDescription() {
        return description;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public void performPassive(Event event){
        passiveFunction.performFunction(event);
    }
}
