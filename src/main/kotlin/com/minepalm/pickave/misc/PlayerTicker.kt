package com.minepalm.pickave.misc

import com.minepalm.pickave.Pickave
import com.minepalm.pickave.has
import com.minepalm.pickave.isSimilarTo
import com.minepalm.pickave.parseItem
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class PlayerTicker(val plugin: JavaPlugin) {
    fun loop() {
        Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
            val lantern = "custom:lantern".parseItem() ?: return@Runnable
            Pickave.allPlayers.forEach {
                val inv = it.inventory
                for(i in 0 until 36) {
                    if(inv.getItem(i)?.isSimilarTo(lantern) == true) {
                        it.addPotionEffect(PotionEffect(PotionEffectType.NIGHT_VISION, 40, 0, true, false))
                    }
                }
            }
        }, 0, 20)
    }
}