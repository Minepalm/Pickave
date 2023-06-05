package com.minepalm.pickave.shop.button

import com.minepalm.pickave.hasMoney
import com.minepalm.pickave.takeMoney
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class BuyButton(
    private val price: Double,
    private val item: ()-> ItemStack
) : Button("buy") {

    override fun run(event: InventoryClickEvent) {
        if(event.isShiftClick) {
            val player = event.whoClicked as Player ?: return
            if(player.hasMoney(price * 64)) {
                player.takeMoney(price * 64)
                player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                player.inventory.addItem(item().apply { amount = 64 })
            } else {
                player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f)
            }
        } else {
            val player = event.whoClicked as Player ?: return
            if(player.hasMoney(price)) {
                player.takeMoney(price)
                player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                player.inventory.addItem(item())
            } else {
                player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f)
            }
        }
    }
}