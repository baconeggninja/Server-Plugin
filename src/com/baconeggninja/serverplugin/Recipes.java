package com.baconeggninja.serverplugin;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Recipes {
	
	public static void registerRecipes(){
		ItemStack grapplingHook = new ItemStack(Material.FIREWORK);
		ItemMeta gmeta = grapplingHook.getItemMeta();
		gmeta.setUnbreakable(true);
		gmeta.setDisplayName("Grappling... Rocket");
		grapplingHook.setItemMeta(gmeta);
	}
	
}
