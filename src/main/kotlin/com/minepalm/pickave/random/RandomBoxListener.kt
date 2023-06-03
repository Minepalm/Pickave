package com.minepalm.pickave.random

import com.minepalm.pickave.Pickave
import com.minepalm.pickave.isSimilarTo
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

class RandomBoxListener : Listener {

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if(Pickave.randombox.item == null)
            return
        if(event.action.isRightClick) {
            val item = event.player.inventory.itemInMainHand
            if(item.isSimilarTo(Pickave.randombox.item!!)) {
                item.amount -= 1
                event.player.inventory.addItem(Pickave.randombox.random())
            }
        }
    }

    @EventHandler
    fun onInteract2(event: PlayerInteractEntityEvent) {

    }

}