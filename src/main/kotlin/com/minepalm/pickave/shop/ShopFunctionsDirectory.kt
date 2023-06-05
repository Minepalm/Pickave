package com.minepalm.pickave.shop

import com.minepalm.pickave.Pickave
import java.io.File

class ShopFunctionsDirectory(
    val directory: File
) {

    fun readAll(): List<ShopFunctionsConfig> {
        return directory.listFiles()
            ?.filter { it.name.endsWith(".yml") }
            ?.apply { Pickave.logger.info("loaded ${size} shops") }
            ?.mapNotNull {
                try {
                    ShopFunctionsConfig(it)
                }catch (e: Exception) {
                    Pickave.logger.warning("[PickaveShop] failed to load shop ${it.name}")
                    e.printStackTrace()
                    null
                }
            }
            ?: run {
                Pickave.logger.warning("[PickaveShop] failed to load shops (empty)")
                emptyList()
            }
    }

}