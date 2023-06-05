package com.minepalm.pickave.anvil

import com.minepalm.pickave.Pickave
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class AnvilListener : Listener {

    @EventHandler
    fun onInteractAnivl(event: PlayerInteractEvent) {
        val block = event.clickedBlock ?: return
        val player = event.player
        if(player.isOp && player.gameMode == GameMode.CREATIVE && event.action == Action.RIGHT_CLICK_BLOCK) {
            return
        }
        if (block.type == Material.ANVIL || block.type == Material.CHIPPED_ANVIL || block.type == Material.DAMAGED_ANVIL) {
            event.isCancelled = true
            Pickave.anvil.newGUI()?.openGUI(event.player)
        }
    }
}