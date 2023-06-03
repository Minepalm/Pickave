package com.minepalm.pickave.shop

import com.minepalm.gui.preset.GUIPreset
import com.minepalm.gui.preset.PresetGUI
import com.minepalm.pickave.getMoney
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.util.UUID
import java.util.function.Consumer

class ShopPresetGUI(
    private val uuid: UUID,
    private val preset: GUIPreset,
    funcs: ShopFunctions
) : PresetGUI(preset) {

    companion object {
        const val GOLD_INDEX = 4
    }

    private val index = preset.row * 9

    init {
        for (i in 0 until index) {
            val buttons = funcs[i]
            if (buttons != null) {
                this.funcs[i] = Consumer { event ->
                    buttons.forEach { it.run(event) }
                    updateMoney()
                    Bukkit.getPlayer(uuid)?.updateInventory()
                }
            }
        }
        inv.setItem(GOLD_INDEX, currentMoneyIcon())
    }

    private fun currentMoneyIcon(): ItemStack {
        return ItemStack(Material.GOLD_INGOT).apply {
            itemMeta = itemMeta.apply {
                displayName(LegacyComponentSerializer.legacyAmpersand().deserialize("&7현재 소유하고 있는 금액 &l&e${uuid.getMoney().toInt()}"))
            }
        }
    }

    private fun updateMoney() {
        inv.setItem(GOLD_INDEX, currentMoneyIcon())
    }
}