package com.sereneoasis.video;

import com.sereneoasis.SereneRPG;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class VideoFrameGrabber {

    private ArrayList<BufferedImage> frames = new ArrayList<>();

    public VideoFrameGrabber(String urlString) throws MalformedURLException, FrameGrabber.Exception {

        URL url = new URL(urlString);

        if (!new File("test.mp4").exists()) {
            try (InputStream in = url.openStream()) {
                Files.copy(in, Paths.get("test.mp4"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

//        Bukkit.getScheduler().runTaskLater(SereneRPG.plugin, () -> {
            try {
                FFmpegFrameGrabber g = new FFmpegFrameGrabber("test.mp4");

                g.start();

                int tickDelay = 0;
                Location loc = Bukkit.getPlayer("Sakrajin").getLocation();


                Java2DFrameConverter converter = new Java2DFrameConverter();
                for (int i = 0 ; i < g.getLengthInFrames() ; i ++){
                    g.setFrameNumber(i);
                    Frame finalFrame = g.grabFrame();
                    BufferedImage image = converter.getBufferedImage(finalFrame);
                    frames.add(image);
                    Bukkit.getScheduler().runTaskLater(SereneRPG.plugin, () -> {
                        new MapStitcher(loc.clone(), frames.get(0));
                        frames.remove(0);
                    }, tickDelay);
                    tickDelay += 10;
                }
                g.stop();

            } catch (FrameGrabber.Exception e) {
                throw new RuntimeException(e);
            }



//        }, 200);


    }
}