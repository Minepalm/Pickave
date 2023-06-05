package com.minepalm.pickave.shop

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Subcommand
import com.minepalm.pickave.Pickave
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@CommandAlias("pkvshop")
@CommandPermission("pickave.shop")
class ShopCommand : BaseCommand() {

    @Subcommand("open")
    fun run(sender: CommandSender, shop: String, username: String) {
        val player = Bukkit.getPlayer(username) ?: return
        Pickave.shops.getGUI(shop, player.uniqueId)?.openGUI(player)
    }

    @Subcommand("reload")
    @CommandPermission("pickave.admin")
    fun reload(sender: Player) {
        Pickave.shops.repo.reload()
        sender.sendMessage("§a샵을 다시 불러왔습니다.")
    }

    @Subcommand("list")
    @CommandPermission("pickave.admin")
    fun list(sender: Player) {
        for (shopFunctions in Pickave.shops.repo.all()) {
            sender.sendMessage("§a${shopFunctions.presetName} ")
        }
    }
}