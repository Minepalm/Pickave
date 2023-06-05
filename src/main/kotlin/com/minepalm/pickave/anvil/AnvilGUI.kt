package com.minepalm.pickave.anvil

import com.minepalm.gui.preset.GUIPreset
import com.minepalm.gui.preset.PresetGUI
import com.minepalm.pickave.Pickave
import com.minepalm.pickave.enchant.EnchantGUI
import com.minepalm.pickave.isSimilarTo
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack
import java.util.function.Consumer

class AnvilGUI(
    preset: GUIPreset,
    val config: AnvilConfig
) : PresetGUI(preset) {

    companion object {
        const val INPUT_SLOT_ORIGIN = 10
        const val INPUT_SLOT_MATERIAL = 13
        const val OUTPUT_SLOT = 10
        const val PROGRESS_SLOT = 4
        const val HAMMER_SLOT = 16

        private val pickaxes = listOf(
            Material.NETHERITE_PICKAXE,
            Material.DIAMOND_PICKAXE,
            Material.GOLDEN_PICKAXE,
            Material.IRON_PICKAXE,
            Material.STONE_PICKAXE,
            Material.WOODEN_PICKAXE
        )
    }

    private val originalItem = inv.getItem(PROGRESS_SLOT)?.clone()
    private val hammer = Pickave.idb[config.hammer]

    init {
        cancelled[INPUT_SLOT_ORIGIN] = false
        cancelled[INPUT_SLOT_MATERIAL] = false
        cancelled[HAMMER_SLOT] = false
        funcs[INPUT_SLOT_ORIGIN] = Consumer {
            val item = it.cursor
            val player = it.whoClicked as? Player ?: return@Consumer
            if(item != null && item.type != Material.AIR) {
                if(item.type !in pickaxes) {
                    it.isCancelled = true
                    player.playSound(player.location, Sound.BLOCK_ANVIL_HIT, 1f, 1f)
                    return@Consumer
                }
                if(item.enchantments.isEmpty()) {
                    player.playSound(player.location, Sound.BLOCK_ANVIL_HIT, 1f, 1f)
                    it.isCancelled = true
                    return@Consumer
                }
            }
        }
        funcs[INPUT_SLOT_MATERIAL] = Consumer {
            val item = it.cursor
            val itemOrigin = inv.getItem(INPUT_SLOT_ORIGIN)
            val player = it.whoClicked as? Player ?: return@Consumer
            if(item != null && item.type != Material.AIR) {
                if(item.type !in pickaxes) {
                    it.isCancelled = true
                    player.playSound(player.location, Sound.BLOCK_ANVIL_HIT, 1f, 1f)
                    return@Consumer
                }
                if(item.enchantments.isEmpty()) {
                    player.playSound(player.location, Sound.BLOCK_ANVIL_HIT, 1f, 1f)
                    it.isCancelled = true
                    return@Consumer
                }

                if(itemOrigin != null && itemOrigin.type != item.type) {
                    it.isCancelled = true
                    player.playSound(player.location, Sound.BLOCK_ANVIL_HIT, 1f, 1f)
                    return@Consumer
                }
            }
        }
        funcs[OUTPUT_SLOT] = Consumer { event ->
            event.isCancelled = !(inv.getItem(OUTPUT_SLOT) != null && inv.getItem(OUTPUT_SLOT)?.type != Material.AIR &&
                    (event.cursor == null || event.cursor?.type == Material.AIR))
        }
        funcs[HAMMER_SLOT] = Consumer {
            if(hammer == null) return@Consumer
            val player = it.whoClicked as? Player ?: return@Consumer
            if(it.cursor != null && it.cursor?.type != Material.AIR) {
                if(hammer!!.isSimilarTo(it.cursor!!)) {
                    it.isCancelled = true
                    player.playSound(player.location, Sound.BLOCK_ANVIL_HIT, 1f, 1f)
                    return@Consumer
                } else {
                    it.isCancelled = false
                }
            }
        }
        funcs[PROGRESS_SLOT] = Consumer {
            val player = it.whoClicked as? Player ?: return@Consumer
            val origin = inv.getItem(INPUT_SLOT_ORIGIN)
            val material = inv.getItem(INPUT_SLOT_MATERIAL)
            val hammerItem = inv.getItem(HAMMER_SLOT)
            if(origin == null || origin.type == Material.AIR) {
                error("&c인첸트 할 곡괭이를 넣어주세요.")
                return@Consumer
            }
            if(material == null || material.type == Material.AIR) {
                error("&c재료를 넣어주세요.")
                return@Consumer
            }
            if(origin.type !in pickaxes) {
                error("&c인첸트 할 곡괭이를 넣어주세요.")
                return@Consumer
            }
            if(!sameItem(INPUT_SLOT_ORIGIN, INPUT_SLOT_MATERIAL)) {
                error("&c인첸트 할 곡괭이의 종류가 다릅니다.")
                return@Consumer
            }
            if(hammerItem == null || hammer?.isSimilarTo(hammerItem) == false) {
                error("&c망치를 넣어주세요.")
                return@Consumer
            }

            val enchantsOrigin = origin.enchantments
            val enchantsMaterial = material.enchantments
            //merge
            val mergedEnchants = mutableMapOf<Enchantment, Int>()
            for((key, value) in enchantsOrigin) {
                mergedEnchants[key] = value
            }
            for((key, value) in enchantsMaterial) {
                if(mergedEnchants.containsKey(key)) {
                    mergedEnchants[key] = mergedEnchants.getOrDefault(key, 0).coerceAtLeast(value)
                } else {
                    mergedEnchants[key] = value
                }
            }
            val result = origin.clone()
            result.itemMeta = result.itemMeta.apply {
                mergedEnchants.forEach { addEnchant(it.key, it.value, true)  }
            }
            inv.setItem(INPUT_SLOT_ORIGIN, null)
            inv.setItem(INPUT_SLOT_MATERIAL, null)
            inv.setItem(HAMMER_SLOT, null)
            inv.setItem(OUTPUT_SLOT, result)
            player.playSound(player.location, Sound.BLOCK_ANVIL_USE, 1f, 1f)
            player.updateInventory()
        }
    }

    private fun sameItem(slot1: Int, slot2: Int): Boolean {
        return inv.getItem(slot1)?.type == inv.getItem(slot2)?.type && inv.getItem(slot1)?.type != null
    }

    override fun onClose(event: InventoryCloseEvent) {
        giveItem(event.player as Player, INPUT_SLOT_ORIGIN)
        giveItem(event.player as Player, INPUT_SLOT_MATERIAL)
        giveItem(event.player as Player, OUTPUT_SLOT)
    }

    private fun giveItem(player: Player, slot: Int) {
        val item = inv.getItem(slot) ?: return
        player.inventory.addItem(item)
    }

    fun error(msg: String) {
        val item = ItemStack(Material.BARRIER).apply {
            itemMeta = itemMeta.apply {
                displayName(LegacyComponentSerializer.legacyAmpersand().deserialize(msg))
            }
        }
        inv.setItem(PROGRESS_SLOT, item)
        Pickave.ex.sync(40) {
            inv.setItem(PROGRESS_SLOT, originalItem)
        }
    }
}