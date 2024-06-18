package com.sereneoasis.video;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapFont;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class MapRenderMan extends MapRenderer{
    private BufferedImage displayImage;


    public MapRenderMan(BufferedImage displayImage) {
        this.displayImage = displayImage;
    }

    @Override
    public void render(MapView mapView, MapCanvas canvas, Player player) {
            canvas.drawImage(0, 0, displayImage);

    }

}