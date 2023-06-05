package com.minepalm.pickave.shop

import com.minepalm.pickave.Pickave
import java.util.concurrent.ConcurrentHashMap

class ShopFunctionsRepo(
    val directory: ShopFunctionsDirectory
) {

    private val map = ConcurrentHashMap<String, ShopFunctions>()

    init {
        for (config in directory.readAll()) {
            Pickave.logger.info("[PickaveShop] registered shop ${config.funcs.presetName}")
            map[config.funcs.presetName] = config.funcs
        }
    }

    @Synchronized
    fun reload() {
        map.clear()
        for (config in directory.readAll()) {
            map[config.funcs.presetName] = config.funcs
        }
    }

    operator fun get(name: String): ShopFunctions? {
        return map[name]
    }

    operator fun set(name: String, shopFunctions: ShopFunctions) {
        map[name] = shopFunctions
    }

    fun remove(name: String) {
        map.remove(name)
    }

    fun all(): List<ShopFunctions> {
        return map.values.toList()
    }

}