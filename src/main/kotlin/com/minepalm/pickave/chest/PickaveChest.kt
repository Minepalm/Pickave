package com.minepalm.pickave.chest

import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class PickaveChest(plugin: JavaPlugin) {

    val repo = ChestLocationRepo()
    val locationFile = ChestLocationFile(plugin)
    val conf = ChestConf(plugin)
    private val loot = ChestLoot(conf.item)

    init {
        val map = locationFile.readAllLocation()
        for ((index, location) in map) {
            repo.add(index, location)
        }
    }
    fun resetBox() {
        repo.all().forEach {
            loot.inject(it.block)
        }
    }
}