package com.minepalm.pickave.shop

import com.minepalm.gui.preset.GUIPresetRegistry
import java.io.File

class PickaveShop(
    val directoryFile: File
) {
    val directory = ShopFunctionsDirectory(directoryFile)
    val repo = ShopFunctionsRepo(directory)

    fun getGUI(name: String): ShopPresetGUI? {
        val preset = GUIPresetRegistry[name] ?: return null
        return repo[name]?.let { ShopPresetGUI(preset, it) }
    }
}