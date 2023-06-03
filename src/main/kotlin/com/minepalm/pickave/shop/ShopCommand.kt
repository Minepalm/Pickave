package com.minepalm.pickave.shop

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Subcommand
import com.minepalm.pickave.Pickave
import org.bukkit.entity.Player

@CommandAlias("pkvshop")
class ShopCommand : BaseCommand() {

    @Default
    fun run(sender: Player, name: String) {
        Pickave.shops.getGUI(name)?.openGUI(sender)
    }

    @Subcommand("reload")
    @CommandPermission("pickave.admin")
    fun reload(sender: Player) {
        Pickave.shops.repo.reload()
        sender.sendMessage("§a샵을 다시 불러왔습니다.")
    }
}