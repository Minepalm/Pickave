package com.minepalm.pickave.shop

import java.io.File

class ShopFunctionsDirectory(
    val directory: File
) {

    fun readAll(): List<ShopFunctionsConfig> {
        return directory.listFiles()?.map { ShopFunctionsConfig(it) } ?: emptyList()
    }

}