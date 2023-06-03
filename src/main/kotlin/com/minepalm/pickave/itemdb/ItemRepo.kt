package com.minepalm.pickave.itemdb

import org.bukkit.inventory.ItemStack
import java.util.concurrent.ConcurrentHashMap

class ItemRepo(
    private val directory: ItemDirectory
) {

    private val items = ConcurrentHashMap<String, ItemStackData>()

    init {
        for (item in directory.loadItems()) {
            items[item.name] = item
        }
    }

    operator fun get(name: String): ItemStack? {
        return items[name.uppercase()]?.itemStack
    }

    operator fun set(name: String, item: ItemStack) {
        items[name]= ItemStackData(name, item, directory.defaultFile)
        directory.defaultFile.writeItemStack(name.uppercase(), item)
    }

    fun delete(name: String): Boolean {
        val data = items.remove(name.uppercase()) ?: return false
        data.file.deleteItemStack(name.uppercase())
        return true
    }

    fun all(): List<ItemStackData> {
        return items.values.toList()
    }
}