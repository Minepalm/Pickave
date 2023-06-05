package com.minepalm.pickave.shop

import com.minepalm.gui.preset.GUIPresetRegistry
import com.minepalm.pickave.Pickave
import java.io.File
import java.util.UUID

class PickaveShop(
    val directoryFile: File
) {
    val directory = ShopFunctionsDirectory(directoryFile)
    val repo = ShopFunctionsRepo(directory)

    fun getGUI(name: String, uuid: UUID): ShopPresetGUI? {
        val preset = GUIPresetRegistry[name] ?: return null
        return repo[name]?.let { ShopPresetGUI(uuid, preset, it) } ?: run {
            Pickave.logger.warning("[PickaveShop] querying non-exist shop $name")
            return null
        }
    }
}