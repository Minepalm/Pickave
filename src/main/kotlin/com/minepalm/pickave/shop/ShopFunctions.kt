package com.minepalm.pickave.shop

import com.minepalm.pickave.shop.button.Button
import java.util.concurrent.ConcurrentHashMap

class ShopFunctions(
    val presetName: String
) {

    private val map = ConcurrentHashMap<Int, List<Button>>()

    operator fun get(page: Int): List<Button>? {
        return map[page]
    }

    operator fun set(page: Int, buttons: List<Button>) {
        map[page] = buttons
    }

    fun add(page: Int, button: Button) {
        map[page] = (map[page] ?: mutableListOf()) + button
    }
}