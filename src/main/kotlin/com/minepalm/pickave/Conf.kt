package com.minepalm.pickave

import com.minepalm.arkarangutils.bukkit.SimpleConfig
import org.bukkit.Location
import org.bukkit.plugin.java.JavaPlugin

class Conf(plugin: JavaPlugin) : SimpleConfig(plugin, "config.yml") {


    private val world = config.getString("location.world", "world")!!
    private val x = config.getDouble("location.x", 0.0)
    private val y = config.getDouble("location.y", 0.0)
    private val z = config.getDouble("location.z", 0.0)

    val spawnLocation
        get() = Location(plugin.server.getWorld(world), x, y, z)

}