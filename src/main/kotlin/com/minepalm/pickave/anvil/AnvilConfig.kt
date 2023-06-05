package com.minepalm.pickave.anvil

import com.minepalm.arkarangutils.bukkit.SimpleConfig
import org.bukkit.plugin.java.JavaPlugin

class AnvilConfig(plugin: JavaPlugin): SimpleConfig(plugin, "anvil.yml") {

    val presetName: String
    val hammer: String

    init {
        presetName = config.getString("preset", "pickaveAnvil")!!
        hammer = config.getString("hammer", "HAMMER")!!
    }
}