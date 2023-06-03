package com.minepalm.pickave.itemdb

import java.io.File

class ItemDirectory(
    private val directory: File
) {

    val defaultFile: YamlItemStack
    val files: List<YamlItemStack>

    init {
        if (!directory.exists()) {
            directory.mkdirs()
        }
        defaultFile = loadDefaultFile()
        files = loadFiles()
    }

    fun loadItems(): List<ItemStackData> {
        val items = mutableListOf<ItemStackData>()
        for (file in files) {
            items.addAll(file.read())
        }
        return items
    }

    private fun loadDefaultFile(): YamlItemStack {
        val file = File(directory, "default.yml")
        if (!file.exists()) {
            file.createNewFile()
        }
        return YamlItemStack(file)
    }

    private fun loadFiles(): List<YamlItemStack> {
        val files = mutableListOf<YamlItemStack>()
        for (file in directory.listFiles()!!) {
            files.add(YamlItemStack(file))
        }
        return files
    }
}