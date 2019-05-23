package com.minecraftdimensions.bungeesuitewarps.listeners;

import com.minecraftdimensions.bungeesuitewarps.BungeeSuiteWarps;
import com.minecraftdimensions.bungeesuitewarps.managers.WarpsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class WarpsMessageListener implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived( String channel, Player player, byte[] message ) {
        DataInputStream in = new DataInputStream( new ByteArrayInputStream( message ) );
        String task = null;

        try {
            task = in.readUTF();

            if ( task.equals( "TeleportPlayerToLocation" ) ) {
                WarpsManager.teleportPlayerToWarp( in.readUTF(), new Location( Bukkit.getWorld( in.readUTF() ), in.readDouble(), in.readDouble(), in.readDouble(), in.readFloat(), in.readFloat() ) );
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        if ( task.equals( "GetVersion" ) ) {
            String name = null;
            try {
                name = in.readUTF();
            } catch ( IOException e ) {

            }
            if ( name != null ) {
                Player p = Bukkit.getPlayer( name );
                p.sendMessage( ChatColor.RED + "Warps - " + ChatColor.GOLD + BungeeSuiteWarps.instance.getDescription().getVersion() );
            }
            WarpsManager.sendVersion();
            Bukkit.getConsoleSender().sendMessage( ChatColor.RED + "Warps - " + ChatColor.GOLD + BungeeSuiteWarps.instance.getDescription().getVersion() );
        }

    }
}
