package com.minepalm.pickave.itemdb

import co.aikar.commands.BaseCommand
import co.aikar.commands.CommandIssuer
import co.aikar.commands.RegisteredCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Subcommand
import com.minepalm.pickave.Pickave
import org.bukkit.Bukkit
import org.bukkit.entity.Player

@CommandAlias("idb")
@CommandPermission("pickave.itemdb")
class DBCommands : BaseCommand() {

    private val repo
        get() = Pickave.idb
    //ItemDB
    //idb get <name> - 아이템을 얻습니다.
    //idb set <name> - 손에 들고 있는 아이템을 등록합니다.
    //idb delete <name> - 아이템을 삭제합니다.
    //idb list - 아이템 목록 GUI를 봅니다.
    @Default
    fun help(sender: Player) {
        sender.sendMessage("§aItemDB Help")
        sender.sendMessage("§a/idb get <name> - 아이템을 얻습니다.")
        sender.sendMessage("§a/idb set <name> - 손에 들고 있는 아이템을 등록합니다.")
        sender.sendMessage("§a/idb delete <name> - 아이템을 삭제합니다.")
        sender.sendMessage("§a/idb list - 아이템 목록 GUI를 봅니다.")
    }

    @Subcommand("get")
    fun get(sender: Player, name: String) {
        val item = repo[name]
        if(item == null) {
            sender.sendMessage("§c존재하지 않는 아이템입니다.")
            return
        }
        sender.inventory.addItem(item)
        sender.sendMessage("§a아이템을 얻었습니다.")
    }

    @Subcommand("set")
    fun set(sender: Player, name: String) {
        val item = sender.inventory.itemInMainHand
        repo[name] = item
        sender.sendMessage("§a아이템을 등록했습니다.")
    }

    @Subcommand("delete")
    fun delete(sender: Player, name: String) {
        if(repo.delete(name)) {
            sender.sendMessage("§a아이템을 삭제했습니다.")
        } else {
            sender.sendMessage("§c존재하지 않는 아이템입니다.")
        }
    }

    @Subcommand("list")
    fun list(sender: Player) {
        ItemDBGUI(repo.all()).openGUI(sender)
    }

    override fun showSyntax(issuer: CommandIssuer, cmd: RegisteredCommand<*>) {
        if(issuer.isPlayer)
            help(Bukkit.getPlayer(issuer.uniqueId) ?: return)
    }
}