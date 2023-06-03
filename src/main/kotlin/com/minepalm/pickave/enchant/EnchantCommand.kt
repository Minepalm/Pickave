package com.minepalm.pickave.enchant

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Subcommand
import com.minepalm.pickave.Pickave
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

@CommandAlias("pkvenchant")
class EnchantCommand: BaseCommand() {

    @Subcommand("open")
    fun open(sender: CommandSender, name: String) {
        val player = Bukkit.getPlayer(name) ?: return
        Pickave.enchant.newGUI()?.openGUI(player)
    }
}