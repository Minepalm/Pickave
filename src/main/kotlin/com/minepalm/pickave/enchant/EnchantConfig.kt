package com.minepalm.pickave.enchant

import com.minepalm.arkarangutils.bukkit.SimpleConfig
import com.minepalm.gui.preset.GUIPresetRegistry
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.plugin.java.JavaPlugin

class EnchantConfig(plugin: JavaPlugin): SimpleConfig(plugin, "enchant.yml") {

    val presetName: String
    private val enchants = mutableMapOf<String, EnchantObj>()

    init {
        presetName = config.getString("preset", "pickaveEnchant")!!
        if(GUIPresetRegistry[presetName] == null)
            plugin.logger.warning("Enchant Preset $presetName not found")
        load()
    }

    @Synchronized
    fun clear() {
        enchants.clear()
    }

    @Synchronized
    fun load() {
        val section = config.getConfigurationSection("enchants") ?: return
        for (key in section.getKeys(false)) {
            val enchantName = section.getString("$key.enchant") ?: continue
            val nk = NamespacedKey.minecraft(enchantName)
            val enchant = Enchantment.getByKey(nk) ?: continue
            val level = section.getInt("$key.level")
            val percent = section.getDouble("$key.percent")
            enchants[key] = EnchantObj(enchant, level, percent)
        }
    }

    fun random(): EnchantObj {
        val list = enchants.values.toList()
        val total = list.sumOf { it.percent }
        var random = Math.random() * total
        for (enchant in list) {
            random -= enchant.percent
            if (random <= 0) {
                return enchant
            }
        }
        throw IllegalStateException("EnchantConfig#random() failed")
    }
}