package com.baconeggninja.serverplugin;

import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class WaypointCommand implements CommandExecutor{

	FileConfiguration config = ServerPlugin.getPlugin(ServerPlugin.class).config;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player)sender;
		if(args.length < 1){return false;}else{
			if(args[0].equalsIgnoreCase("set")){
				//if(player.getName() == args[1] || player.getName() == "baconeggninja"){} (Still working on this bit...)
				String name = player.getName();
				Location loc = player.getLocation();
				config.set(name + "-Waypoint-x", loc.getX());
				config.set(name + "-Waypoint-y", loc.getY());
				config.set(name + "-Waypoint-z", loc.getZ());
				ServerPlugin.getPlugin(ServerPlugin.class).saveConfig();
				return true;
			}
			else{
				if(config.contains(args[0] + "-Waypoint-x") && args[0] != null){
					player.teleport(new Location(player.getWorld(), config.getDouble(args[0] + "-Waypoint-x"), config.getDouble(args[0] + "-Waypoint-y"), config.getDouble(args[0] + "-Waypoint-z")));
					return true;
				}else{player.sendMessage("Ehmmm... Not a valid argument"); return false;}
			}
		}

	}

}