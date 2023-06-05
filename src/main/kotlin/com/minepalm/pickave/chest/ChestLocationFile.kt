package com.minepalm.pickave.chest

import com.minepalm.arkarangutils.bukkit.SimpleConfig
import org.bukkit.Location
import org.bukkit.plugin.java.JavaPlugin

class ChestLocationFile(plugin: JavaPlugin) : SimpleConfig(plugin, "chest_locations.yml"){

    fun readAllLocation(): Map<Int, Location> {
        val map = mutableMapOf<Int, Location>()
        val section = config.getConfigurationSection("chests") ?: return map
        for (key in section.getKeys(false)) {
            val location = section.getLocation(key) ?: continue
            map[key.toInt()] = location
        }
        return map
    }

    fun writeLocation(index: Int, loc: Location) {
        config.set(index.toString(), loc)
        save()
    }

    fun removeLocation(index: Int) {
        config.set(index.toString(), null)
        save()
    }

    fun writeAllLocation(map: Map<Int, Location>) {
        val section = config.createSection("chests")
        for ((index, location) in map) {
            section.set(index.toString(), location)
        }
        save()
    }
}