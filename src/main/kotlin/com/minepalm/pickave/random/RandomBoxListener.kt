package com.minepalm.pickave.random

import com.minepalm.pickave.Pickave
import com.minepalm.pickave.isSimilarTo
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

class RandomBoxListener : Listener {

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if(Pickave.randombox.item == null)
            return

        val item = event.player.inventory.itemInMainHand
        if(item.isSimilarTo(Pickave.randombox.item!!)) {
            if (event.action.isRightClick) {
                item.amount -= 1
                event.player.playSound(event.player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)
                event.player.inventory.addItem(Pickave.randombox.random())
            }
            event.isCancelled = true
        }
    }

    @EventHandler
    fun blockPlace(event: BlockPlaceEvent) {
        if(Pickave.randombox.item == null)
            return

        val item = event.player.inventory.itemInMainHand
        if(item.isSimilarTo(Pickave.randombox.item!!)) {
            item.amount -= 1
            event.player.playSound(event.player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)
            event.player.inventory.addItem(Pickave.randombox.random())

            event.isCancelled = true
        }
    }

    @EventHandler
    fun onInteract2(event: PlayerInteractEntityEvent) {
        if (Pickave.randombox.item == null)
            return

        val item = event.player.inventory.itemInMainHand
        if(item.isSimilarTo(Pickave.randombox.item!!)) {
            item.amount -= 1
            event.player.playSound(event.player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)
            event.player.inventory.addItem(Pickave.randombox.random())

            event.isCancelled = true
        }

    }

}