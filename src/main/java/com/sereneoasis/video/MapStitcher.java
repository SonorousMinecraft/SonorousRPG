package com.sereneoasis.video;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MapStitcher {


    private BufferedImage displayImage;

    private BufferedImage cloneOriginal(){
        BufferedImage copyOfImage = new BufferedImage(displayImage.getWidth(), displayImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = copyOfImage.createGraphics();
        g.drawImage(displayImage, 0, 0, null);
        return copyOfImage; //or use it however you want

    }

    public MapStitcher(Location loc, BufferedImage img){

            displayImage = img;

            int xBlocks = Math.floorDiv(displayImage.getWidth(), 128)  ;
            int yBlocks = Math.floorDiv(displayImage.getHeight(), 128) ;


            loc.getWorld().getNearbyEntities(loc, xBlocks, yBlocks, xBlocks)
                    .stream()
                    .filter(entity -> entity.getType() == EntityType.ITEM_FRAME)
                    .forEach(entity -> entity.remove());

            for (int x = 0; x < xBlocks; x+=1) {

                for (int y = 0; y < yBlocks; y += 1) {

//                        if (displayImage.getWidth() > xBlocks*128 + 128  && displayImage.getHeight() > yBlocks*128 + 128) {
                    ItemStack mapItem = new ItemStack(Material.FILLED_MAP);
                    MapMeta mapMeta = (MapMeta) mapItem.getItemMeta();
                    mapMeta.setMapView(Bukkit.createMap(loc.getWorld()));
//                        BufferedImage image = cloneOriginal().getSubimage(x * 128, y * 128, 128, 128);
                    BufferedImage image = displayImage.getSubimage(x * 128, y * 128, 128, 128);
                    mapMeta.getMapView().addRenderer(new MapRenderMan(image));
                    mapItem.setItemMeta(mapMeta);

//                        player.getInventory().addItem(mapItem);
//                        }
                    loc.clone().add(0,0,1).getBlock().setType(Material.DIRT);

                    loc.getWorld().spawn(loc, EntityType.ITEM_FRAME.getEntityClass(), (entity) -> {
                        ((ItemFrame) entity).setItem(mapItem);

                    });


                    loc.add(0,-1,0);
                }
                loc.add(0,yBlocks,0);
                loc.add(-1,0,0);
            }



//            player.sendMessage(ChatColor.GREEN + "Here you go!");

    }

    public MapStitcher(Player player, String url){
        try {

            displayImage = ImageIO.read(new URL(url));

            int xBlocks = Math.floorDiv(displayImage.getWidth(), 128)  ;
            int yBlocks = Math.floorDiv(displayImage.getHeight(), 128) ;


            Location loc = player.getLocation().add(player.getLocation().getDirection().multiply(3)).add(0,yBlocks,0);

            for (int x = 0; x < xBlocks; x+=1) {

                for (int y = 0; y < yBlocks; y += 1) {

//                        if (displayImage.getWidth() > xBlocks*128 + 128  && displayImage.getHeight() > yBlocks*128 + 128) {
                    ItemStack mapItem = new ItemStack(Material.FILLED_MAP);
                    MapMeta mapMeta = (MapMeta) mapItem.getItemMeta();
                    mapMeta.setMapView(Bukkit.createMap(player.getWorld()));
//                        BufferedImage image = cloneOriginal().getSubimage(x * 128, y * 128, 128, 128);
                    BufferedImage image = displayImage.getSubimage(x * 128, y * 128, 128, 128);
                    mapMeta.getMapView().addRenderer(new MapRenderMan(image));
                    mapItem.setItemMeta(mapMeta);

//                        player.getInventory().addItem(mapItem);
//                        }
                    loc.clone().add(0,0,1).getBlock().setType(Material.DIRT);
                    player.getWorld().spawn(loc, EntityType.ITEM_FRAME.getEntityClass(), (entity) -> {
                        ((ItemFrame) entity).setItem(mapItem);
                    });


                    loc.add(0,-1,0);
                }
                loc.add(0,yBlocks,0);
                loc.add(-1,0,0);
            }



            player.sendMessage(ChatColor.GREEN + "Here you go!");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MapStitcher(Player player){
            try {

                displayImage = ImageIO.read(new URL("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/Instagram_icon.png/2048px-Instagram_icon.png"));

                int xBlocks = Math.floorDiv(displayImage.getWidth(), 128)  ;
                int yBlocks = Math.floorDiv(displayImage.getHeight(), 128) ;


                Location loc = player.getLocation().add(player.getLocation().getDirection().multiply(3)).add(0,yBlocks,0);

                for (int x = 0; x < xBlocks; x+=1) {

                    for (int y = 0; y < yBlocks; y += 1) {

//                        if (displayImage.getWidth() > xBlocks*128 + 128  && displayImage.getHeight() > yBlocks*128 + 128) {
                        ItemStack mapItem = new ItemStack(Material.FILLED_MAP);
                        MapMeta mapMeta = (MapMeta) mapItem.getItemMeta();
                        mapMeta.setMapView(Bukkit.createMap(player.getWorld()));
//                        BufferedImage image = cloneOriginal().getSubimage(x * 128, y * 128, 128, 128);
                        BufferedImage image = displayImage.getSubimage(x * 128, y * 128, 128, 128);
                        mapMeta.getMapView().addRenderer(new MapRenderMan(image));
                        mapItem.setItemMeta(mapMeta);

//                        player.getInventory().addItem(mapItem);
//                        }
                        loc.clone().add(0,0,1).getBlock().setType(Material.DIRT);
                        player.getWorld().spawn(loc, EntityType.ITEM_FRAME.getEntityClass(), (entity) -> {
                            ((ItemFrame) entity).setItem(mapItem);
                        });


                        loc.add(0,-1,0);
                    }
                    loc.add(0,yBlocks,0);
                    loc.add(-1,0,0);
                }



                player.sendMessage(ChatColor.GREEN + "Here you go!");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
