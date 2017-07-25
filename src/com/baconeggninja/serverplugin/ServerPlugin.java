package com.baconeggninja.serverplugin;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;



public class ServerPlugin extends JavaPlugin {

	int id;
	FileConfiguration config = this.getConfig();
	String[] waypoints = {"baconeggninja","Schnauzerninja","BOOM_TNT_BOOM_","knittingninja","staghornninja","Jackmclean12","jabanalive","xXBig_BoyXx","EDMMan","Shops","End","Mesa"};
	private static class StopServer extends TimerTask{@Override public void run(){for(World world:Bukkit.getWorlds()){world.save();}Bukkit.shutdown();}}
	
	@Override
	public void onEnable(){
		System.out.println("[ServerPlugin] yaaaaay i liek being enabled");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR_OF_DAY, 15);
		calendar.add(Calendar.MINUTE, -10);
		Date date = calendar.getTime();
		Timer timer = new Timer();
		timer.schedule(new StopServer(), date);
		System.out.println("Shutdown timer set to " + date.toString());
		addDefaults();
		config.options().copyDefaults(true);
		saveConfig();
		this.getCommand("waypoint").setExecutor((CommandExecutor)new WaypointCommand());
		//this.getCommand("waypoint").setTabCompleter(new WaypointTabber());
		getServer().getPluginManager().registerEvents(new MainListener(), this);
		id = getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				MainListener.every20Ticks();
			}}, 20, 1);
	}

	@Override
	public void onDisable(){
		System.out.println("[ServerPlugin] noooooooo pls dont disable me");
		HandlerList.unregisterAll(this);
		getServer().getScheduler().cancelTask(id);
	}
	
	/**
	 * Sets the defaults for the configuration file
	 */
	public void addDefaults(){
		for(String waypoint : waypoints){
			addDefaultWaypoint(waypoint);
		}
	}

	public void addDefaultWaypoint(String playerName){
		config.addDefault(playerName + "-Waypoint-x", 0D);
		config.addDefault(playerName + "-Waypoint-y", 0D);
		config.addDefault(playerName + "-Waypoint-z", 0D);
	}

}

