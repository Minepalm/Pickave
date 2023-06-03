package com.minepalm.pickave.shop.button

import hasMoney
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
                player.inventory.addItem(item().apply { amount = 64 })
            }
        } else {
            val player = event.whoClicked as Player ?: return
            if(player.hasMoney(price)) {
                player.inventory.addItem(item())
            }
        }
    }
}