package com.minepalm.pickave

import co.aikar.commands.BukkitCommandManager
import com.minepalm.arkarangutils.bukkit.ArkarangGUI
import com.minepalm.arkarangutils.bukkit.ArkarangGUIListener
import com.minepalm.arkarangutils.bukkit.BukkitExecutor
import com.minepalm.pickave.anvil.PickaveAnvil
import com.minepalm.pickave.chest.PickaveChest
import com.minepalm.pickave.enchant.PickaveEnchant
import com.minepalm.pickave.ender.PickaveEnder
import com.minepalm.pickave.itemdb.DBCommands
import com.minepalm.pickave.itemdb.ItemDirectory
import com.minepalm.pickave.itemdb.ItemRepo
import com.minepalm.pickave.misc.PickaveMisc
import com.minepalm.pickave.random.PickaveRandomBox
import com.minepalm.pickave.shop.PickaveShop
import com.minepalm.pickave.shop.ShopCommand
import com.minepalm.pickave.shop.ShopFunctionsDirectory
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class PickavePlugin : JavaPlugin(){

    private lateinit var acf: BukkitCommandManager

    override fun onEnable() {
        ArkarangGUIListener.init(this)
        acf = BukkitCommandManager(this)

        Pickave.ex = BukkitExecutor(this, Bukkit.getScheduler())
        Pickave.conf = Conf(this)
        Pickave.logger = logger

        initItemDb()
        initShop()
        initEnchant()
        initAnvil()
        initChest()
        initRandomBox()
        initEnder()
        initMisc()
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

    private fun initChest() {
        Pickave.chest = PickaveChest(this)
    }

    private fun initRandomBox() {
        Pickave.randombox = PickaveRandomBox(this)
    }

    private fun initEnder() {
        Pickave.ender = PickaveEnder(this)
    }

    private fun initMisc() {
        PickaveMisc(this)
    }
}