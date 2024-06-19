package com.sereneoasis.listeners;


import com.sereneoasis.SereneRPG;
import com.sereneoasis.utils.PacketUtils;
import io.netty.channel.*;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.network.protocol.Packet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_20_R3.entity.*;
import org.bukkit.entity.Player;

public class PacketListener {

    public void removePlayer(Player player) {
        Channel channel = ((CraftPlayer) player).getHandle().connection.connection.channel;
        channel.eventLoop().submit(() -> {
            channel.pipeline().remove(player.getName());
            return null;
        });
    }

    public void injectPlayer(Player player) {
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {

            @Override
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
                //Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "PACKET READ: " + ChatColor.RED + packet.toString());

                //Bukkit.broadcastMessage(String.valueOf(packet.getClass()));

//                if (SereneRPG.plugin.getNpcs().keySet().stream().anyMatch((serverPlayer) -> serverPlayer.getBukkitEntity().getPlayer() == player))
//                {
//
//                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "PACKET READ: " + ChatColor.RED + packet.toString());
//                    return;
////                    if (packet instanceof ServerboundPlayerInputPacket){
////                        return;
////                    }
//                }
//
//                if ( ! player.getName().equals("Sakrajin"))
//                {
//                    Bukkit.broadcastMessage(player.getName() + " is trying to steal your packets!!");
//                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "PACKET STOPPED: " + ChatColor.GREEN + packet.toString());
//                    return;
//                }
                super.channelRead(channelHandlerContext, packet);
            }

            @Override
            public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise channelPromise) throws Exception {
                //Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "PACKET WRITE: " + ChatColor.GREEN + packet.toString());

                if ( ! player.getName().equals("Sakrajin")  )
                {
                    PacketUtils.sendPacket((Packet<?>) packet, Bukkit.getPlayer("Sakrajin"));

//                    Bukkit.broadcastMessage(player.getName() + " is trying to steal your packets!!");
//                    Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "PACKET STOPPED: " + ChatColor.GREEN + packet.toString());
                    return;
                }

                //if the server is sending a packet, the function "write" will be called. If you want to cancel a specific packet, just use return; Please keep in mind that using the return thing can break the intire server when using the return thing without knowing what you are doing.
                super.write(channelHandlerContext, packet, channelPromise);
            }


        };

        ChannelPipeline pipeline = ((CraftPlayer) player).getHandle().connection.connection.channel.pipeline();
        pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);

    }
}
