package com.sereneoasis.utils;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import org.bukkit.Location;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/***
 * An intermediary between WorldEdit API to simplify schematic usage
 */
public class SchematicUtils {

    /***
     * Spawns a Schematic at a given location
     * @param schematicName the name of the schematic file
     * @param location The Bukkit Location within a World
     * @throws IOException If no file is found
     */
    public static void spawnSchematic(String schematicName, Location location) throws IOException {
        File file = new File(schematicName + ".schem");
        Clipboard clipboard;

        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {

            clipboard = reader.read();
            pasteClipboard(clipboard, location);
        }
    }

    /***
     * Spawns a Schematic at a given location
     * @param file the schematic file
     * @param location The Bukkit Location within a World
     * @throws IOException If no file is found
     */
    public static void spawnSchematic(File file, Location location) throws IOException {
        pasteClipboard(createClipboard(file), location);
    }

    /***
     * Initialises Clipboards which must be done before a schematic can be pasted
     * @param file the schematic file
     * @return the relevant clipboard to the schematic
     * @throws IOException If no file is found
     */
    public static Clipboard createClipboard(File file) throws IOException {
        Clipboard clipboard;

        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            clipboard = reader.read();
            return clipboard;
        }
    }

    /***
     * Pastes a clipboard at a given location
     * @param clipboard the clipboard which represents an intended schematic
     * @param location the Location for the clipboard to be pasted at
     */
    public static void pasteClipboard(Clipboard clipboard, Location location) {
        World world = BukkitAdapter.adapt(location.getWorld());
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(location.getX(), location.getY(), location.getZ()))
                    // configure here
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }
    }
}



