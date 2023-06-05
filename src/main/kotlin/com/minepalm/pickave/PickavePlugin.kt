package com.minepalm.pickave

import co.aikar.commands.BukkitCommandManager
import com.minepalm.arkarangutils.bukkit.ArkarangGUI
import com.minepalm.arkarangutils.bukkit.ArkarangGUIListener
import com.minepalm.arkarangutils.bukkit.BukkitExecutor
import com.minepalm.pickave.anvil.PickaveAnvil
import com.minepalm.pickave.chest.ChestCommand
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
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.nio.file.Files

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
        Pickave.ex.sync {
            //Economy Implementation 을 무엇을 쓸지 알 수가 없기 때문에
            //모든 플러그인이 활성화 한 다음 초기화 하는게 바람직함.
            //예컨데, 활성화 시점에서 바로 Economy 를 가져오면
            //Essentials Hook 이전에 실행되어 찾을수 없는 경우도 있음.
            initEcon()
        }
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

        val file = File(directory, "shop_example.yml")
        if(!file.exists()) {
            Files.copy(getResource("shop_example.yml")!!, file.toPath())
        }
        Pickave.shops = PickaveShop(directory)
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
        acf.registerCommand(ChestCommand())
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

    private fun initEcon() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            Pickave.logger.warning("Vault is not installed")
            return
        } else {
            val rsp = Bukkit.getServicesManager().getRegistration(Economy::class.java)
            if (rsp == null) {
                Pickave.logger.warning("Vault is installed but no economy plugin is found")
                return
            } else {
                EconExtensions.econ = rsp.provider
            }
        }
    }
}