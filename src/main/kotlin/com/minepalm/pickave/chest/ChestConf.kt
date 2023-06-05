package com.minepalm.pickave.chest

import com.minepalm.arkarangutils.bukkit.SimpleConfig
import com.minepalm.pickave.idb
import com.minepalm.pickave.parseItem
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class ChestConf(plugin: JavaPlugin) : SimpleConfig(plugin, "chest.yml"){

    val itemName = config.getString("item") ?: "DIAMOND_SWORD"
    val item: ItemStack?
        get() = itemName.idb()
}