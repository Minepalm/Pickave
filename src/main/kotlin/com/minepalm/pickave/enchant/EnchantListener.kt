package com.minepalm.pickave.enchant

import com.minepalm.pickave.Pickave
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

class EnchantListener : Listener {

    @EventHandler
    fun onInteractEnchantTable(event: PlayerInteractEvent) {
        val block = event.clickedBlock ?: return
        val player = event.player
        if(player.isOp && player.gameMode == GameMode.CREATIVE && event.action == Action.RIGHT_CLICK_BLOCK) {
            return
        }
        if (block.type == Material.ENCHANTING_TABLE) {
            event.isCancelled = true
            Pickave.enchant.newGUI()?.openGUI(event.player)
        }
    }

    @EventHandler
    fun onItemUse(event: PlayerInteractEvent) {
        val player = event.player
        if(player.isOp && player.gameMode == GameMode.CREATIVE && event.action == Action.RIGHT_CLICK_BLOCK) {
            return
        }
        if (event.item?.type == Material.EXPERIENCE_BOTTLE) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onItemUseEntity(event: PlayerInteractEntityEvent) {
        val player = event.player
        if(player.isOp && player.gameMode == GameMode.CREATIVE) {
            return
        }
        if (event.player.inventory.itemInMainHand.type == Material.EXPERIENCE_BOTTLE) {
            event.isCancelled = true
        }
    }
}