package com.minepalm.pickave.ender

import org.bukkit.plugin.java.JavaPlugin

class PickaveEnder(plugin: JavaPlugin) {

    init {
        plugin.server.pluginManager.registerEvents(EnderEventListener(), plugin)
    }

}