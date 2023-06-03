package com.minepalm.pickave.enchant

import com.minepalm.gui.preset.GUIPresetRegistry
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class PickaveEnchant(plugin: JavaPlugin) {

    private val config: EnchantConfig

    init {
        config = EnchantConfig(plugin)
        config.load()
        Bukkit.getPluginManager().registerEvents(EnchantListener(), plugin)
    }

    fun newGUI(): EnchantGUI? {
        val preset = GUIPresetRegistry[config.presetName] ?: return null
        return EnchantGUI(preset, config)
    }
}