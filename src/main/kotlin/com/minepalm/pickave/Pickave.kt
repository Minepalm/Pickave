package com.minepalm.pickave

import com.minepalm.arkarangutils.bukkit.BukkitExecutor
import com.minepalm.pickave.anvil.PickaveAnvil
import com.minepalm.pickave.enchant.PickaveEnchant
import com.minepalm.pickave.itemdb.ItemRepo
import com.minepalm.pickave.shop.PickaveShop
import com.minepalm.pickave.shop.ShopFunctionsRepo
import java.util.logging.Logger

object Pickave {

    lateinit var ex: BukkitExecutor

    lateinit var logger: Logger
    lateinit var idb: ItemRepo
    lateinit var shops: PickaveShop
    lateinit var enchant: PickaveEnchant
    lateinit var anvil: PickaveAnvil
}