package com.minepalm.pickave

import co.aikar.commands.BukkitCommandManager
import com.minepalm.arkarangutils.bukkit.ArkarangGUI
import com.minepalm.arkarangutils.bukkit.ArkarangGUIListener
import com.minepalm.pickave.anvil.PickaveAnvil
import com.minepalm.pickave.enchant.PickaveEnchant
import com.minepalm.pickave.itemdb.DBCommands
import com.minepalm.pickave.itemdb.ItemDirectory
import com.minepalm.pickave.itemdb.ItemRepo
import com.minepalm.pickave.shop.PickaveShop
import com.minepalm.pickave.shop.ShopCommand
import com.minepalm.pickave.shop.ShopFunctionsDirectory
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class PickavePlugin : JavaPlugin(){

    private lateinit var acf: BukkitCommandManager

    override fun onEnable() {
        ArkarangGUIListener.init(this)
        acf = BukkitCommandManager(this)
        initItemDb()
        initShop()
        initEnchant()
        initAnvil()
    }

    private fun initItemDb() {
        val directory = ItemDirectory(File(dataFolder, "items"))
        Pickave.idb = ItemRepo(directory)
        acf.registerCommand(DBCommands())
    }

    private fun initShop() {
        val directory = File(dataFolder, "shop")
        if(!directory.exists())
            directory.mkdirs()
        Pickave.shops = PickaveShop(file)
        acf.registerCommand(ShopCommand())
    }

    private fun initEnchant() {
        Pickave.enchant = PickaveEnchant(this)
    }

    private fun initAnvil() {
        Pickave.anvil = PickaveAnvil(this)
    }
}