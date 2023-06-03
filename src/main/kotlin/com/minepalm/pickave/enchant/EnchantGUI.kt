package com.minepalm.pickave.enchant

import com.minepalm.gui.preset.GUIPreset
import com.minepalm.gui.preset.PresetGUI
import com.minepalm.pickave.Pickave
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack
import java.util.function.Consumer

class EnchantGUI(
    preset: GUIPreset,
    config: EnchantConfig
) : PresetGUI(preset) {

    companion object {
        const val BOTTLE_SLOT = 17
        const val ENCHANT_SLOT = 1
        const val PROCESS_SLOT = 2

        private val pickaxes = listOf(
            Material.NETHERITE_PICKAXE,
            Material.DIAMOND_PICKAXE,
            Material.GOLDEN_PICKAXE,
            Material.IRON_PICKAXE,
            Material.STONE_PICKAXE,
            Material.WOODEN_PICKAXE
        )
    }

    private val originalItem = inv.getItem(ENCHANT_SLOT)?.clone()

    init {
        cancelled[BOTTLE_SLOT] = false
        cancelled[ENCHANT_SLOT] = false
        funcs[BOTTLE_SLOT] = Consumer { event ->
            val item = inv.getItem(BOTTLE_SLOT) ?: return@Consumer
            if (item.type != Material.EXPERIENCE_BOTTLE) {
                event.isCancelled = true
                return@Consumer
            }
        }
        funcs[ENCHANT_SLOT] = Consumer {
            val item = inv.getItem(ENCHANT_SLOT)
            val player = it.whoClicked as? Player ?: return@Consumer
            if(item != null && item.type != Material.AIR) {
                if(item.type !in pickaxes) {
                    it.isCancelled = true
                    player.playSound(player.location, Sound.BLOCK_ANVIL_HIT, 1f, 1f)
                    return@Consumer
                }
                if(item.enchantments.isNotEmpty()) {
                    player.playSound(player.location, Sound.BLOCK_ANVIL_HIT, 1f, 1f)
                    it.isCancelled = true
                    return@Consumer
                }
            }
        }
        funcs[PROCESS_SLOT] = Consumer { event ->
            val player = event.whoClicked as? Player ?: return@Consumer
            if (!containsBottle()) {
                error("&c경험치 병을 우측 슬롯에 넣어주세요")
                return@Consumer
            }
            if (hasEnchant()) {
                error("&c이미 인챈트가 되어있습니다")
                return@Consumer
            }
            if (!containsPickaxe()) {
                error("&c곡괭이를 좌측 슬롯에 넣어주세요")
                return@Consumer
            }
            val enchant = config.random()
            val item = inv.getItem(ENCHANT_SLOT)!!
            item.addUnsafeEnchantment(enchant.enchant, enchant.level)
            inv.setItem(ENCHANT_SLOT, item)
            inv.setItem(BOTTLE_SLOT, null)
            inv.setItem(PROCESS_SLOT, originalItem)
            player.updateInventory()

        }
    }

    override fun onClose(event: InventoryCloseEvent?) {
        val player = event?.player ?: return
        val item1 = inv.getItem(ENCHANT_SLOT)
        val item2 = inv.getItem(BOTTLE_SLOT)
        if(item1 != null)
            player.inventory.addItem(item1)
        if(item2 != null)
            player.inventory.addItem(item2)
    }

    fun containsBottle(): Boolean {
        val item = inv.getItem(BOTTLE_SLOT) ?: return false
        return item.type == Material.EXPERIENCE_BOTTLE
    }

    fun containsPickaxe(): Boolean {
        val item = inv.getItem(ENCHANT_SLOT) ?: return false
        return pickaxes.contains(item.type)
    }

    fun hasEnchant(): Boolean {
        val item = inv.getItem(ENCHANT_SLOT) ?: return false
        return item.enchantments.isNotEmpty()
    }

    fun error(msg: String) {
        val item = ItemStack(Material.BARRIER).apply {
            itemMeta = itemMeta.apply {
                displayName(LegacyComponentSerializer.legacyAmpersand().deserialize(msg))
            }
        }
        inv.setItem(PROCESS_SLOT, item)
        Pickave.ex.sync(40) {
            inv.setItem(PROCESS_SLOT, originalItem)
        }
    }
}