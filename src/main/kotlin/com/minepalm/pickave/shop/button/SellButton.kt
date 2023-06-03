package com.minepalm.pickave.shop.button

import com.minepalm.pickave.has
import com.minepalm.pickave.remove
import com.minepalm.pickave.giveMoney
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class SellButton(
    private val price: Double,
    private val item: () -> ItemStack
) : Button("sell") {

    override fun run(event: InventoryClickEvent) {
        if (event.isShiftClick) {
            val player = event.whoClicked as Player ?: return
            sellItem(player, item(), 64)
        } else {
            val player = event.whoClicked as Player ?: return
            sellItem(player, item(), 1)
        }
    }


    private fun sellItem(player: Player, item: ItemStack, amount: Int) {
        if(player.inventory.has(item, amount)) {
            player.inventory.remove(item, amount)
            player.giveMoney(price * amount)
            player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
        } else {
            player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f)
        }
    }
}