package com.minepalm.pickave.misc

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class PickaveMisc(plugin: JavaPlugin) {

    init {
        PlayerTicker(plugin).loop()
        Bukkit.getPluginManager().registerEvents(MiscListener(), plugin)
    }
}