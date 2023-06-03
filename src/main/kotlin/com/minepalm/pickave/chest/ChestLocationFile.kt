package com.minepalm.pickave.chest

import com.minepalm.arkarangutils.bukkit.SimpleConfig
import org.bukkit.Location

class ChestLocationFile : SimpleConfig(plugin, "chests.yml"){

    fun readAllLocation(): Map<Int, Location> {
        val map = mutableMapOf<Int, Location>()
        val section = config.getConfigurationSection("chests") ?: return map
        for (key in section.getKeys(false)) {
            val location = section.getLocation(key) ?: continue
            map[key.toInt()] = location
        }
        return map
    }
}