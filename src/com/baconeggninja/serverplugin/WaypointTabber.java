package com.baconeggninja.serverplugin;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public class WaypointTabber implements TabCompleter{

	private List<String> options;
	private String[] waypoints = ServerPlugin.getPlugin(ServerPlugin.class).waypoints;

	@Deprecated
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		try{
			if(command.getName() == "waypoint"){
				if(args.length <= 1){
					options.clear();
					for(String waypoint : waypoints){
						options.add(waypoint);
					}
					options.add("set");
					options = StringUtil.copyPartialMatches(args[0], options, options);
					return options;
				}
				else if(args.length <= 2 && args[0] == "set"){
					options.clear();
					for(String waypoint : waypoints){
						options.add(waypoint);
					}
					options = StringUtil.copyPartialMatches(args[0], options, options);
					return options;
				}
				else{
					options.clear();
					options.add("" + "Ehmm... I got nothing...");
					return options;
				}
			}
			options.clear();
			options.add("This shouldn't happen");
			return options;
		}catch(Exception e){e.printStackTrace();}
		return options;
	}



}
