package com.minepalm.pickave.chest

import org.bukkit.Location
import java.util.concurrent.ConcurrentHashMap

class ChestLocationRepo(
    private val locationConfig: ChestLocationFile
) {

    private val chestLocations = ConcurrentHashMap<Int, Location>()

    fun add(index: Int, location: Location) {
        chestLocations[index] = location
        locationConfig.writeLocation(index, location)
    }

    operator fun get(index: Int): Location? {
        return chestLocations[index]
    }

    fun remove(index: Int) {
        chestLocations.remove(index)
        locationConfig.removeLocation(index)
    }

    fun all(): List<Location> {
        return chestLocations.values.toList()
    }

}