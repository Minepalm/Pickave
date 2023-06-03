package com.minepalm.pickave.chest

import org.bukkit.Location
import org.bukkit.enchantments.Enchantment
import java.util.concurrent.ConcurrentHashMap

class ChestLocaitonRepo {

    private val chestLocations = ConcurrentHashMap<Int, Location>()

    fun add(index: Int, location: Location) {
        chestLocations.put(index, location)
    }

    fun remove(index: Int) {
        chestLocations.remove(index)
    }

    fun all(): List<Location> {
        return chestLocations.values.toList()
    }

}