package com.minepalm.pickave.itemdb

import com.minepalm.arkarangutils.bukkit.ArkarangGUI
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.function.Consumer

class ItemDBGUI(
    private val listItems: List<ItemStackData>,
    private val currentPage: Int = 0,
) : ArkarangGUI(6, "아이템 데이터베이스: ${currentPage + 1}") {

    companion object {
        const val MAX_ITEM_PER_PAGE = 45
        const val NEXT_PAGE = 53
        const val PREV_PAGE = 45
        private val prevPage = ItemStack(
            Material.PAPER).apply {
                itemMeta = itemMeta.apply {
                setDisplayName("이전 페이지")
            }
        }
        private val nextPage = ItemStack(Material.PAPER).apply {
                itemMeta = itemMeta.apply {
                setDisplayName("다음 페이지")
            }
        }
    }

    init {
        for (i in 0 until MAX_ITEM_PER_PAGE) {
            val index = currentPage * MAX_ITEM_PER_PAGE + i
            if(index >= listItems.size) break
            val item = listItems.getOrNull(index)
            if (item != null) {
                setItem(i, item.itemStack) {
                    val player = it.whoClicked as? Player ?: return@setItem
                    if (it.click.isShiftClick) {
                        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
                        player.inventory.addItem(item.itemStack.clone().apply { amount = 64 })
                    } else {
                        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
                        player.inventory.addItem(item.itemStack.clone())
                    }
                }
            }
        }
        if (currentPage > 0) {
            setItem(PREV_PAGE, prevPage) {
                val player = it.whoClicked as? Player ?: return@setItem
                player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                ItemDBGUI(listItems,currentPage - 1).openGUI(it.whoClicked as? Player ?: return@setItem)
            }
        }
        if (currentPage < listItems.size / MAX_ITEM_PER_PAGE) {
            setItem(NEXT_PAGE, nextPage) {
                val player = it.whoClicked as? Player ?: return@setItem
                player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                ItemDBGUI( listItems, currentPage + 1).openGUI(it.whoClicked as? Player ?: return@setItem)
            }
        }
    }

    private fun setItem(slot: Int, item: ItemStack, onClick: (InventoryClickEvent) -> Unit) {
        inv.setItem(slot, item)
        funcs[slot] = Consumer { event ->
            event.isCancelled = true
            onClick(event)
        }
    }
}