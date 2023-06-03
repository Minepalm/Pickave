package com.minepalm.pickave.shop.button

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class CommandButton(
    val command: String
) : Button("command") {

    override fun run(event: InventoryClickEvent) {
        val sender = Bukkit.getConsoleSender()
        val player = event.whoClicked as? Player ?: return
        Bukkit.dispatchCommand(sender, command.replace("%player%", player.name))
    }
}