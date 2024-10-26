package com.sereneoasis.player;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.nivixx.ndatabase.api.annotation.NTable;
import com.nivixx.ndatabase.api.model.NEntity;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author Sakrajin
 * Used by NDatabase API to represent players data within the database
 */
@NTable(name = "rpg_player", schema = "", catalog = "")
public class PlayerData extends NEntity<UUID> {

    @JsonProperty("name")
    private String name;
    @JsonProperty("specialisation")
    private String specialisation;

    @JsonProperty("unarmed")
    private double unarmed;

    @JsonProperty("swords")
    private double swords;

    @JsonProperty("mining")
    private double mining;

    @JsonProperty("movement")
    private double movement;

    @JsonProperty("tools")
    private double tools;

    @JsonProperty("ranged")
    private double ranged;


    public PlayerData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public double getUnarmed() {
        return unarmed;
    }

    public void setUnarmed(double unarmed) {
        this.unarmed = unarmed;
    }

    public double getSwords() {
        return swords;
    }

    public void setSwords(double swords) {
        this.swords = swords;
    }

    public double getMining() {
        return mining;
    }

    public void setMining(double mining) {
        this.mining = mining;
    }

    public double getMovement() {
        return movement;
    }

    public void setMovement(double movement) {
        this.movement = movement;
    }

    public double getTools() {
        return tools;
    }

    public void setTools(double tools) {
        this.tools = tools;
    }

    public double getRanged() {
        return ranged;
    }

    public void setRanged(double ranged) {
        this.ranged = ranged;
    }
}
