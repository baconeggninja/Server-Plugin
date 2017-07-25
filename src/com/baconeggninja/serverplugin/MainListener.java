package com.baconeggninja.serverplugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class MainListener implements Listener{

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event)
	{
		if(event.getEntity().getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK){
			switch(event.getEntity().getType()){

			case WITHER:
				event.getDrops().add(new ItemStack(Material.SKULL_ITEM, xInYChance(75, 100), (byte) 1));
				event.getDrops().add(new ItemStack(Material.SKULL_ITEM, xInYChance(25, 100), (byte) 1));
				break;

			case ENDER_DRAGON:
				event.getDrops().add(new ItemStack(Material.ELYTRA, 1));
				event.getDrops().add(new ItemStack(Material.SKULL_ITEM, 1, (byte) 5));
				break;

			case ZOMBIE:
				event.getDrops().add(new ItemStack(Material.SKULL_ITEM, xInYChance(5, 100), (byte) 2));
				break;

			case SKELETON:
				event.getDrops().add(new ItemStack(Material.SKULL_ITEM, xInYChance(5, 100), (byte) 0));
				break;

			case CREEPER:
				event.getDrops().add(new ItemStack(Material.SKULL_ITEM, xInYChance(5, 100), (byte) 4));
				break;

			case SILVERFISH:
				event.getDrops().add(new ItemStack(Material.GOLD_NUGGET, xInYChance(3, 4)));
				break;
				
			default:
				break;
			}

		}
	}

	public static List<Player> getNearbyEntities(Location loc, double range) {
		List<Entity> entities = loc.getWorld().getEntities();
		TreeMap<Double, Player> rtrn = new TreeMap<>();
		for (Entity en : entities) {
			if (en.getLocation().distance(loc) > range && en.getType() == EntityType.PLAYER) {
				continue;
			}
			rtrn.put(en.getLocation().distance(loc), (Player)en);
		}
		return new ArrayList<Player>(rtrn.values());
	}

	public static int xInYChance(int x, int y){
		assert x>y: "X should not be greater than Y";
		Random r = new Random();
		int i = r.nextInt(y)+1;
		if(i>x){return 0;}else{return 1;}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		ItemStack item = event.getItem();
		if(item != null && item.getType() == Material.NETHER_STAR){
			if(player.isGliding()){
					player.setVelocity(player.getVelocity().normalize().multiply(0.5));
			}
		}
		if(event.getItem() != null && event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.BEACON && player.getInventory().getItemInMainHand().getType() == Material.NETHER_STAR){
			player.spawnParticle(Particle.PORTAL, player.getLocation(), 20);
			player.teleport(new Location(player.getWorld(), player.getLocation().getX(), 256, player.getLocation().getZ()));
			player.playSound(player.getLocation(), Sound.ITEM_CHORUS_FRUIT_TELEPORT, 10, 1);
		}
	}

	@SuppressWarnings("deprecation")
	public static void every20Ticks() {

		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getInventory().getItemInMainHand()!=null && player.getInventory().getItemInMainHand().getType() == Material.TOTEM && player.getGameMode() == GameMode.SURVIVAL && player.getInventory().getItemInOffHand().getType() != Material.BOW) {
				player.setAllowFlight(true);
				player.setMaxHealth(2);
				player.setFlySpeed(0.03F);
			}
			else if(player.getGameMode() == GameMode.SURVIVAL){
				player.setAllowFlight(false);
				player.setMaxHealth(20);
			}
			else if(player.getGameMode() == GameMode.CREATIVE){
				player.setFlySpeed(0.1F);
				player.setAllowFlight(true);
			}

		}

	}


}


