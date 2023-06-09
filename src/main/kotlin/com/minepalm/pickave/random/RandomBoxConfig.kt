package com.minepalm.pickave.random

import com.minepalm.arkarangutils.bukkit.SimpleConfig
import com.minepalm.pickave.parseItem
import org.bukkit.plugin.java.JavaPlugin

class RandomBoxConfig(plugin: JavaPlugin): SimpleConfig(plugin, "randombox.yml") {

    val itemName = config.getString("name", "RANDOM_BOX")!!
    fun load(): List<RandomBoxData> {
        val list0 = mutableListOf<RandomBoxData>()
        val section = config.getConfigurationSection("items") ?: return emptyList()
        for (key in section.getKeys(false)) {
            if(key.parseItem() == null) {
                plugin.logger.warning("Invalid item $key at randombox.yml. skipped.")
                continue
            }
            val weight = section.getInt(key)
            val randomBoxData = RandomBoxData(key, weight)
            list0.add(randomBoxData)
        }
        return list0
    }
}