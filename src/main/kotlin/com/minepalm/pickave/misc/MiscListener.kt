package com.minepalm.pickave.misc

import com.minepalm.pickave.Pickave
import com.minepalm.pickave.fear.GlobalFear
import com.minepalm.pickave.isSimilarTo
import com.minepalm.pickave.parseItem
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class MiscListener : Listener{

    val spawnCompass: ItemStack?
        get() = "custom:spawn_compass".parseItem()
    val forgiveCompass: ItemStack?
        get() = "custom:forgive_compass".parseItem()
    val oxegenPump: ItemStack?
        get() = "custom:oxegen_pump".parseItem()

    @EventHandler
    fun onItemInteract(event: PlayerInteractEvent) {
        val item = event.player.inventory.itemInMainHand
        val fgc = forgiveCompass
        val spc = spawnCompass
        val oxp = oxegenPump

        if (spc != null && item.isSimilarTo(spc)) {
            item.amount -= 1
            event.player.isOp = true
            event.player.performCommand("spawn")
            event.player.isOp = false
        }

        if (fgc != null && item.isSimilarTo(fgc)) {
            GlobalFear.addFear(-1)
            item.amount -= 1
            Pickave.allPlayers.forEach { player ->
                player.sendMessage("§a${event.player.name} 님이 만회의 나침반을 사용했습니다.")
            }
        }

        if(oxp != null && item.isSimilarTo(oxp)) {
            item.amount -= 1
            Pickave.oxegen[event.player.uniqueId].addOxegen(180)
            event.player.sendMessage("§a산소펌프를 사용하여 3분간 산소를 충전했습니다.")

        }
    }


}