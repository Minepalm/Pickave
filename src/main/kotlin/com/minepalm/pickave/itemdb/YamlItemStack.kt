package com.minepalm.pickave.itemdb

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import java.io.File

class YamlItemStack(
    private val file: File
) {

    private val config: YamlConfiguration = YamlConfiguration.loadConfiguration(file)

    fun save() {
        if(!file.exists())
            file.createNewFile()
        config.save(file)
    }

    fun read(): List<ItemStackData> {
        val items = mutableListOf<ItemStackData>()
        for (key in config.getKeys(false)) {
            try{
                items.add(readItemStack(key))
            } catch (e: Exception) {

            }
        }
        return items
    }

    private fun readItemStack(name: String): ItemStackData {
        val item = config.getItemStack(name) ?: throw Exception("ItemStack $name not found in file ${file.name}")
        return ItemStackData(name, item, this)
    }

    fun writeItemStack(name: String, item: ItemStack) {
        config.set(name.uppercase(), item)
        save()
    }

    fun deleteItemStack(name: String) {
        config.set(name.uppercase(), null)
        save()
    }
}