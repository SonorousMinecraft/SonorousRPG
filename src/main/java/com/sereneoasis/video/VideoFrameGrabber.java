package com.sereneoasis.video;

import com.github.javafaker.Faker;
import com.sereneoasis.SereneRPG;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.*;
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

    private BufferedImage cloneOriginal(BufferedImage displayImage){
        BufferedImage copyOfImage = new BufferedImage(displayImage.getWidth(), displayImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = copyOfImage.createGraphics();
        g.drawImage(displayImage, 0, 0, null);
        return copyOfImage; //or use it however you want

    }

    public VideoFrameGrabber(String urlString) throws MalformedURLException, FrameGrabber.Exception {
        Faker faker = new Faker();
        String fileName = faker.name().firstName() + ".mp4";

        Bukkit.broadcastMessage("Commencing download");
        Bukkit.getScheduler().runTaskAsynchronously(SereneRPG.plugin, () -> {
            URL url = null;
            try {
                url = new URL(urlString);

                if (new File(fileName).exists()) {

                    new File(fileName).delete();
                }
                try (InputStream in = url.openStream()) {
                    Files.copy(in, Paths.get(fileName));
                    playVideoAfterDelay(fileName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
//        }

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }

        });


    }

    private MapStitcher mapStitcher;

    private void playVideoAfterDelay(String fileName){
        Bukkit.getScheduler().runTaskLaterAsynchronously(SereneRPG.plugin, () -> {
            if (new File(fileName).exists()) {
                try {
                    FFmpegFrameGrabber g = new FFmpegFrameGrabber(fileName);

                    g.start();

                    Location loc = Bukkit.getPlayer("Sakrajin").getLocation();


                    Java2DFrameConverter converter = new Java2DFrameConverter();
                    g.setFrameNumber(1);
                    BufferedImage start = converter.getBufferedImage((g.grabFrame()));

                    Bukkit.getScheduler().runTask(SereneRPG.plugin, () -> {
                         mapStitcher = new MapStitcher(loc.clone(), cloneOriginal(start));
                    });

                    long tickDelay = 0;

                    for (int i = 2; i < g.getLengthInFrames(); i++) {
                        g.setFrameNumber(i);
                        Frame finalFrame = g.grab();
                        BufferedImage image = converter.getBufferedImage(finalFrame);
                        frames.add(cloneOriginal(image));
                        g.setFrameNumber(i);
                        Bukkit.getScheduler().runTaskLater(SereneRPG.plugin, () -> {
                            mapStitcher.changeImages(frames.get(0));
//                        MapStitcher mapStitcher2 =  new MapStitcher(loc.clone(), frames.get(0));

                            frames.remove(0);

                        }, tickDelay);
//                        tickDelay += (long) (g.getFrameRate() / 20);
                        tickDelay += 20/60 ;


//                    if (tickDelay > 100){
//                        break;
//                    }
                    }
                    g.stop();

                } catch (FrameGrabber.Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                Bukkit.broadcastMessage("Still downloading");

                playVideoAfterDelay(fileName);
            }
        }, 100);
    }

}