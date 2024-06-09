package com.sereneoasis.level.world;

import com.sereneoasis.SereneRPG;
import com.sereneoasis.utils.SchematicUtils;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;

/***
 * Caches schematics onEnable
 */
public class Schematics {

    private static final HashMap<String, Clipboard>schematicClipboards = new HashMap<>();

    public Schematics(){
        Arrays.stream(SereneRPG.getFileManager().getSchematics()).forEach(file -> {
            try {
                schematicClipboards.put(file.getName(), SchematicUtils.createClipboard(file));
            } catch (IOException e) {
                Bukkit.getServer().getLogger().log(Level.INFO, "Invalid schematic provided or mistake reading");
            }
        });
    }

    public static void pasteClipboard(String filename, Location location){
        Clipboard clipboard = schematicClipboards.get(filename + ".schem");
        SchematicUtils.pasteClipboard(clipboard, location);
    }

}
