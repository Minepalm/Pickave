package com.minepalm.pickave.chest

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Subcommand
import com.minepalm.pickave.Pickave
import org.bukkit.block.Block
import org.bukkit.entity.Player

@CommandAlias("pkvchest")
@CommandPermission("pickave.chest")
class ChestCommand: BaseCommand() {

    val repo
        get() = Pickave.chest.repo

    @Subcommand("tp")
    fun teleport(player: Player, index: Int) {
        repo.get(index)?.let {
            player.teleport(it.add(0.5, 1.0, 0.5))
        } ?: player.sendMessage("해당 번호의 상자가 없습니다.")
    }

    @Subcommand("list")
    fun listIndex(player: Player) {
        repo.all().forEachIndexed { index, location ->
            player.sendMessage("$index: ${location.blockX}, ${location.blockY}, ${location.blockZ}")
        }
        player.sendMessage("총 ${repo.all().size}개의 상자가 있습니다.")
    }

    @Subcommand("set")
    fun setLocation(player: Player, index: Int) {
        val block = getWatchingBlock(player)
        if(block == null) {
            player.sendMessage("상자를 바라보고 있지 않습니다.")
            return
        } else {
            repo.add(index, block.location)
            player.sendMessage("$index 번 상자를 설정했습니다.")
        }

    }

    @Subcommand("resetbox")
    fun resetBox(player: Player) {
        Pickave.chest.resetBox()
        player.sendMessage("상자를 초기화했습니다.")
    }

    @Default
    fun help(player: Player) {
        player.sendMessage("/pkvchest tp <index>: 해당 번호의 상자로 이동합니다.")
        player.sendMessage("/pkvchest list: 상자의 목록을 봅니다.")
        player.sendMessage("/pkvchest set <index>: 상자를 설정합니다.")
        player.sendMessage("/pkvchest resetbox: 상자를 초기화합니다.")
    }

    fun getWatchingBlock(player: Player): Block? {
        return player.getTargetBlockExact(4)
    }
}