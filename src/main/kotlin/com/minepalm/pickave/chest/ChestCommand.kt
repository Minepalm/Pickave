package com.minepalm.pickave.chest

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.Subcommand
import com.minepalm.pickave.Pickave
import org.bukkit.block.Block
import org.bukkit.entity.Player

@CommandAlias("pckchest")
class ChestCommand: BaseCommand() {

    val repo
        get() = Pickave.chest.repo

    @Subcommand("tp")
    fun teleport(player: Player, index: Int) {
        repo.get(index)?.let {
            player.teleport(it.add(0.0, 1.0, 0.0))
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

    fun getWatchingBlock(player: Player): Block? {
        return player.getTargetBlockExact(4)
    }
}