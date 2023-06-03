package com.minepalm.pickave

import com.minepalm.arkarangutils.bukkit.BukkitExecutor
import com.minepalm.pickave.anvil.PickaveAnvil
import com.minepalm.pickave.chest.PickaveChest
import com.minepalm.pickave.enchant.PickaveEnchant
import com.minepalm.pickave.ender.PickaveEnder
import com.minepalm.pickave.itemdb.ItemRepo
import com.minepalm.pickave.oxegen.PickaveOxegen
import com.minepalm.pickave.oxegen.PlayerOxegen
import com.minepalm.pickave.random.PickaveRandomBox
import com.minepalm.pickave.shop.PickaveShop
import com.minepalm.pickave.shop.ShopFunctionsRepo
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.logging.Logger

object Pickave {

    lateinit var ex: BukkitExecutor
    lateinit var conf: Conf

    lateinit var logger: Logger
    lateinit var idb: ItemRepo
    lateinit var shops: PickaveShop
    lateinit var enchant: PickaveEnchant
    lateinit var anvil: PickaveAnvil
    lateinit var chest: PickaveChest
    lateinit var randombox: PickaveRandomBox
    lateinit var ender: PickaveEnder
    lateinit var oxegen: PickaveOxegen

    val allPlayers: Collection<Player>
        get() {
            return Bukkit.getOnlinePlayers()
        }
}