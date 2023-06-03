package com.minepalm.pickave

import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.UUID

object EconExtensions {

    lateinit var econ: Economy

}

fun Player.takeMoney(amount: Double): Boolean {
    return EconExtensions.econ.withdrawPlayer(this, amount).transactionSuccess()
}

fun Player.giveMoney(amount: Double): Boolean {
    return EconExtensions.econ.depositPlayer(this, amount).transactionSuccess()
}

fun Player.hasMoney(amount: Double): Boolean {
    return EconExtensions.econ.has(this, amount)
}

fun UUID.getMoney(): Double {
    val player = Bukkit.getPlayer(this) ?: return 0.0
    return EconExtensions.econ.getBalance(player)
}

fun UUID.takeMoney(amount: Double): Boolean {
    val player = Bukkit.getPlayer(this) ?: return false
    return player.takeMoney(amount)
}

fun UUID.giveMoney(amount: Double): Boolean {
    val player = Bukkit.getPlayer(this) ?: return false
    return player.giveMoney(amount)
}

fun UUID.hasMoney(amount: Double): Boolean {
    val player = Bukkit.getPlayer(this) ?: return false
    return player.hasMoney(amount)
}