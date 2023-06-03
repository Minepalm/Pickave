package com.minepalm.pickave.enchant

import org.bukkit.enchantments.Enchantment

data class EnchantObj(
    val enchant: Enchantment,
    val level: Int,
    val percent: Double
) {
}