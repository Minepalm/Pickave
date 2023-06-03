package com.minepalm.pickave.enchant

import com.minepalm.pickave.Pickave
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

class EnchantListener : Listener {

    @EventHandler
    fun onInteractEnchantTable(event: PlayerInteractEvent) {
        val block = event.clickedBlock ?: return

        if (block.type == Material.ENCHANTING_TABLE) {
            event.isCancelled = true
            Pickave.enchant.newGUI()?.openGUI(event.player)
        }
    }

    @EventHandler
    fun onItemUse(event: PlayerInteractEvent) {
        if (event.item?.type == Material.EXPERIENCE_BOTTLE) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onItemUseEntity(event: PlayerInteractEntityEvent) {
        if (event.player.inventory.itemInMainHand.type == Material.EXPERIENCE_BOTTLE) {
            event.isCancelled = true
        }
    }
}