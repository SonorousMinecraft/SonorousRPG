package com.sereneoasis.video;

import com.github.javafaker.Faker;
import com.sereneoasis.SereneRPG;
import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.nio.AnimatedGif;
import com.sksamuel.scrimage.nio.AnimatedGifReader;
import com.sksamuel.scrimage.nio.ImageSource;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
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
import java.util.HashMap;

public class VideoFrameGrabber {

    private ArrayList<BufferedImage> frames = new ArrayList<>();

    private BufferedImage cloneOriginal(BufferedImage displayImage){
        BufferedImage copyOfImage = new BufferedImage(displayImage.getWidth(), displayImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = copyOfImage.createGraphics();
        g.drawImage(displayImage, 0, 0, null);
        return copyOfImage; //or use it however you want

    }

    public static final HashMap<Player, VideoFrameGrabber> videos = new HashMap<>();

    private void stop(){
        Bukkit.getScheduler().cancelTasks(SereneRPG.plugin);
        if (currentVideo.exists()) {
            currentVideo.delete();
        }
    }

    private File currentVideo;

    public VideoFrameGrabber(String urlString) throws MalformedURLException, FrameGrabber.Exception {
        Player player =Bukkit.getPlayer("Sakrajin");
        if (videos.containsKey(player)) {
            videos.get(player).stop();
        }

        Faker faker = new Faker();
        String[] splitByDot = urlString.split("\\.");
        String extension = splitByDot[splitByDot.length-1];
        String fileName = faker.name().firstName() + "." + extension;


        Bukkit.broadcastMessage("Commencing download");
        Bukkit.getScheduler().runTaskAsynchronously(SereneRPG.plugin, () -> {
            URL url = null;
            try {
                url = new URL(urlString);

                if (currentVideo !=null && currentVideo.exists()) {
                    currentVideo.delete();
                }
                try (InputStream in = url.openStream()) {
                    Files.copy(in, Paths.get(fileName));
                    currentVideo = new File(fileName);
                    if (extension.equals("gif")){
                        playGifAfterDelay();
                    } else {
                        playVideoAfterDelay();
                    }
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

    private void playGifAfterDelay(){
        Bukkit.getScheduler().runTaskLaterAsynchronously(SereneRPG.plugin, () -> {
            if (currentVideo.exists()) {

                    Location loc = Bukkit.getPlayer("Sakrajin").getLocation().add(0, 3, 10);


                    AnimatedGif gif = null;
                    try {
                        gif = AnimatedGifReader.read(ImageSource.of(currentVideo));

                        ImmutableImage firstFrame = gif.getFrame(0);

                        Bukkit.getScheduler().runTask(SereneRPG.plugin, () -> {
                            mapStitcher = new MapStitcher(loc.clone(), firstFrame.awt());
                        });

                        long tickDelay = 0;

                        for (int i = 1; i < gif.getFrameCount(); i++) {

                            frames.add(gif.getFrame(i).awt());

                            Bukkit.getScheduler().runTaskLater(SereneRPG.plugin, () -> {
                                mapStitcher.changeImages(frames.get(0));

                                frames.remove(0);

                            }, tickDelay);
                            tickDelay += 1;
                        }
                } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
            } else {
                Bukkit.broadcastMessage("Still downloading");

                playGifAfterDelay();
            }
        }, 5);
    }

    private void playVideoAfterDelay(){
        Bukkit.getScheduler().runTaskLaterAsynchronously(SereneRPG.plugin, () -> {
            if (currentVideo.exists()) {
                try {
                    FFmpegFrameGrabber g = new FFmpegFrameGrabber(currentVideo);

                    g.start();

                    Location loc = Bukkit.getPlayer("Sakrajin").getLocation().add(0, 3, 10);


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
                        tickDelay+= 1;
//                        tickDelay += (long) (g.getFrameRate() / 50);


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

                playVideoAfterDelay();
            }
        }, 5);
    }

}