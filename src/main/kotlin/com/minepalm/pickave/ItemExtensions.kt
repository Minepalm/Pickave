package com.minepalm.pickave

import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

fun ItemStack.isSimilarTo(other: ItemStack): Boolean {
    val display1 = this.itemMeta?.displayName()
    val display2 = other.itemMeta?.displayName()
    return this.type == other.type && display1?.equals(display2) == true
}

fun Inventory.remove(item: ItemStack, amountIn: Int = 1) {
    var amount = amountIn
    for(i in 0 until 36) {
        val item = this.getItem(i) ?: continue
        if(item.isSimilarTo(item)) {
            if(item.amount > amount) {
                item.amount -= amount
                return
            } else {
                amount -= item.amount
                this.setItem(i, null)
            }
        }
        if(amount <= 0) return
    }

}

fun Inventory.has(item: ItemStack, amountIn: Int = 1): Boolean {
    var amount = amountIn
    for(i in 0 until 36) {
        val item = this.getItem(i) ?: continue
        if(item.isSimilarTo(item)) {
            amount -= item.amount
            if(amount <= 0) return true
        }
    }
    return false
}

fun String.parseItem(): ItemStack? {
    val itemName = this
    val split = itemName.split(":")
    if (split.size == 2) {
        val namespace = split[0]
        val name = split[1]
        when (namespace) {
            "custom" -> {
                val itemz = Pickave.idb[name] ?: return null
                return itemz.clone()
            }
            else -> {
                val material = Material.getMaterial(itemName)
                if (material != null) {
                    return ItemStack(material)
                } else
                    return null

            }
        }
    }
    val material = Material.getMaterial(itemName)
    return if (material != null) {
        ItemStack(material)
    } else
        null
}