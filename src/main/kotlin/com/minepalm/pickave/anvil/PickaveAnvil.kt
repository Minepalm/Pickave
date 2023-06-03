package com.minepalm.pickave.anvil

import com.minepalm.gui.preset.GUIPresetRegistry
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class PickaveAnvil(plugin: JavaPlugin) {

    val config: AnvilConfig = AnvilConfig(plugin)

    init {
        Bukkit.getPluginManager().registerEvents(AnvilListener(), plugin)
    }

    fun newGUI(): AnvilGUI? {
        val preset = GUIPresetRegistry[config.presetName] ?: return null
        return AnvilGUI(preset, config)
    }
}