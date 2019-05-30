package org.golde.bukkit.pingwhitelistfix;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedServerPing;

public class Main extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		ProtocolLibrary.getProtocolManager().addPacketListener(
		          // I mark my listener as async, as I don't use the Bukkit API. Please note that
		          // your listener may be executed on the main thread regardless.
		          new PacketAdapter(this, ListenerPriority.NORMAL,
		          Arrays.asList(PacketType.Status.Server.SERVER_INFO), ListenerOptions.ASYNC) {
		  
		            @Override
		            public void onPacketSending(PacketEvent event) {
		                WrappedServerPing ping = event.getPacket().getServerPings().read(0);
		                if(Bukkit.hasWhitelist()) {
		                	ping.setVersionProtocol(-1);
		                }
		            }
		        });
		
	}
}