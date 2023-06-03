package com.minepalm.pickave.shop.button

import org.bukkit.event.inventory.InventoryClickEvent

abstract class Button(
    val type: String
) {

    abstract fun run(event: InventoryClickEvent)
}