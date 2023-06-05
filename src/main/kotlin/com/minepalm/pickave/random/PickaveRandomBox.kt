package com.minepalm.pickave.random

import com.minepalm.pickave.Pickave
import com.minepalm.pickave.parseItem
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class PickaveRandomBox(plugin: JavaPlugin) {

    val conf = RandomBoxConfig(plugin)
    val item : ItemStack?
        get() {
            return Pickave.idb[conf.itemName]
        }
    private val list = mutableListOf<RandomBoxData>()

    init {
        list.addAll(conf.load())
        Bukkit.getPluginManager().registerEvents(RandomBoxListener(), plugin)
    }

    fun random(): ItemStack {
        val totalWeight = list.sumOf { it.weight }
        val random = (Math.random() * totalWeight).toInt()
        var sum = 0
        for (randomBoxData in list) {
            sum += randomBoxData.weight
            if(sum >= random) {
                return randomBoxData.item!!
            }
        }
        return ItemStack(Material.AIR)
    }
}