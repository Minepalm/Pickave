package com.minepalm.pickave.itemdb

import org.bukkit.inventory.ItemStack

data class ItemStackData(
    val name: String,
    val itemStack: ItemStack,
    val file: YamlItemStack
)