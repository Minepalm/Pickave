package com.minepalm.pickave.ender

import com.minepalm.pickave.Pickave
import com.minepalm.pickave.fear.GlobalFear
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.random.Random

object EnderEvents {

    val allPlayers
        get() = Bukkit.getOnlinePlayers()
    val allEvents: List<EnderEvent>

    private val pickaxes = listOf(
        Material.NETHERITE_PICKAXE,
        Material.DIAMOND_PICKAXE,
        Material.GOLDEN_PICKAXE,
        Material.IRON_PICKAXE,
        Material.STONE_PICKAXE,
        Material.WOODEN_PICKAXE
    )

    val DIG_FAST_2 = object : EnderEvent {
        override fun run() {
            allPlayers.forEach {
                it.addPotionEffect(PotionEffect(PotionEffectType.FAST_DIGGING, 1000000, 1, false, false))
            }
        }
    }

    val SPEED_2 = object : EnderEvent {
        override fun run() {
            allPlayers.forEach {
                it.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 1000000, 1, false, false))
            }
        }
    }

    val OXYGEN_10 = object : EnderEvent {
        override fun run() {
            allPlayers.forEach {
                TODO()
                it.maximumNoDamageTicks = 12000
            }
        }
    }

    val SUPER_PICKAXE = object : EnderEvent {
        override fun run() {
            allPlayers.forEach {
                val item = ItemStack(Material.NETHERITE_PICKAXE).apply {
                    addEnchantment(Enchantment.DIG_SPEED, 5)
                    addEnchantment(Enchantment.DURABILITY, 3)
                    addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3)
                }
            }
        }
    }

    val EXP_BOTTLE = object : EnderEvent {
        override fun run() {
            allPlayers.forEach {
                it.inventory.addItem(ItemStack(Material.EXPERIENCE_BOTTLE, 1))
            }
        }
    }

    val HAMMER = object : EnderEvent {
        override fun run() {
            val hammer = Pickave.anvil.config.hammer
            val item = Pickave.idb[hammer]?.clone() ?: return
            allPlayers.forEach {
                it.inventory.addItem(item)
            }
        }
    }
    val RESET_FEAR = object : EnderEvent {
        override fun run() {
            GlobalFear.setFear(0)
        }
    }
    val RANDOM_PICKAXE = object : EnderEvent {
        override fun run() {
            val random = Random.nextInt(pickaxes.size)
            val item = ItemStack(pickaxes[random])
            allPlayers.forEach {
                it.inventory.addItem(item)
            }
        }
    }
    val GLOW = object : EnderEvent {
        override fun run() {
            allPlayers.forEach {
                it.addPotionEffect(PotionEffect(PotionEffectType.GLOWING, 1000000, 0, false, false))
            }
        }
    }
    val SPAWN_MOB = object : EnderEvent {
        override fun run() {

        }
    }
    val TELEPORT = object : EnderEvent {
        override fun run() {

        }
    }

    val MINING_FATIGUE_1 = object : EnderEvent {
        override fun run() {
            allPlayers.forEach {
                it.addPotionEffect(PotionEffect(PotionEffectType.SLOW_DIGGING, 1200, 0, false, false))
            }
        }
    }

    val BLINDNESS_1 = object : EnderEvent {
        override fun run() {
            allPlayers.forEach {
                it.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS, 1200, 0, false, false))
            }
        }
    }

    val FEAR_4 = object : EnderEvent {
        override fun run() {
            GlobalFear.setFear(4)
        }
    }

    val DEATH = object : EnderEvent {
        override fun run() {
            allPlayers.forEach {
                it.damage(1000.0)
            }
        }
    }

    init {
        //allEvents given members
        allEvents = listOf(
            DIG_FAST_2,
            SPEED_2,
            OXYGEN_10,
            SUPER_PICKAXE,
            EXP_BOTTLE,
            HAMMER,
            RESET_FEAR,
            RANDOM_PICKAXE,
            GLOW,
            SPAWN_MOB,
            TELEPORT,
            MINING_FATIGUE_1,
            BLINDNESS_1,
            FEAR_4,
            DEATH
        )
    }
}