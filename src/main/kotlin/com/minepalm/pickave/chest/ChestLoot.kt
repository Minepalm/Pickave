package com.minepalm.pickave.chest

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.Chest
import org.bukkit.inventory.ItemStack

class ChestLoot(
    val randomBox: ItemStack?
) {

    fun inject(block: Block) {
        if(block.type == Material.CHEST) {
            val chest = block.state as Chest
            chest.blockInventory.setItem(9+4, randomBox)
        }
    }
}