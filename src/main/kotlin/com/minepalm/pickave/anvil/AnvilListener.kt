package com.minepalm.pickave.anvil

import com.minepalm.pickave.Pickave
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class AnvilListener : Listener {

    @EventHandler
    fun onInteractEnchantTable(event: PlayerInteractEvent) {
        val block = event.clickedBlock ?: return

        if (block.type == Material.ANVIL || block.type == Material.CHIPPED_ANVIL || block.type == Material.DAMAGED_ANVIL) {
            event.isCancelled = true
            Pickave.enchant.newGUI()?.openGUI(event.player)
        }
    }
}