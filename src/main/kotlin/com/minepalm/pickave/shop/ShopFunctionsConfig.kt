package com.minepalm.pickave.shop

import com.minepalm.gui.preset.GUIPreset
import com.minepalm.gui.preset.GUIPresetRegistry
import com.minepalm.pickave.Pickave
import com.minepalm.pickave.shop.button.Button
import com.minepalm.pickave.shop.button.BuyButton
import com.minepalm.pickave.shop.button.CommandButton
import com.minepalm.pickave.shop.button.SellButton
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import java.io.File

class ShopFunctionsConfig(
    private val file: File
) {

    private val config: YamlConfiguration
    private val defaultType: String
    private val presetName: String
    val funcs: ShopFunctions

    init {
        if(!file.exists())
            file.createNewFile()
        Pickave.logger.info("[PickaveShop] loading shop ${file.name}")
        config = YamlConfiguration.loadConfiguration(file)
        defaultType = config.getString("default-type") ?: "none"
        presetName = config.getString("preset") ?: run {
            alert("preset not found at file ${file.name}")
            throw IllegalArgumentException("preset not found at file ${file.name}")
        }
        if(!GUIPresetRegistry.has(presetName)) {
            alert("preset not registered at file ${file.name}")
            throw IllegalArgumentException("preset not registered at file ${file.name}")

        }
        val section = config.getConfigurationSection("functions") ?: run {
            alert("functions not found at file ${file.name}")
            null
        }
        funcs = readFunctions(section)
    }

    private fun readFunctions(section: ConfigurationSection?): ShopFunctions {
        val funcs = ShopFunctions(presetName)
        if(section != null) {
            for (key in section.getKeys(false)) {
                val index = key.toInt()
                val section = section.getConfigurationSection(key) ?: continue
                val func = readFunction(section) ?: continue
                funcs.add(index, func)
            }
        }
        return funcs
    }

    fun readFunction(section: ConfigurationSection): Button? {
        val type = (section.getString("type") ?: defaultType).lowercase()
        when(type) {
            "command" -> {
                val command = section.getString("command") ?: return run {
                    alert("command not found at ${section.name}")
                    null
                }
                return CommandButton(command)
            }
            "buy" -> {
                val price = section.getDouble("price")
                val itemName = section.getString("item") ?: return run {
                    alert("item not found at ${section.name}")
                    null
                }
                val itemFunction = itemFunction(itemName) ?: return null
                return BuyButton(price, itemFunction)
            }
            "sell" -> {
                val price = section.getDouble("price")
                val itemName = section.getString("item") ?: return run {
                    alert("item not found at ${section.name}")
                    null
                }
                val itemFunction = itemFunction(itemName) ?: return null
                return SellButton(price, itemFunction)
            }
            else -> return null
        }
    }

    private fun itemFunction(itemName: String): Function0<ItemStack>? {
        val split = itemName.split(":")
        if (split.size == 2) {
            val namespace = split[0]
            val name = split[1]
            when (namespace) {
                "custom" -> {
                    val itemz = Pickave.idb[name] ?: return run {
                        alert("custom item $name not found")
                        null
                    }
                    return { itemz.clone() }
                }
                else -> {
                    val material = Material.getMaterial(name.uppercase())
                    if (material != null) {
                        return { ItemStack(material) }
                    } else
                        return run {
                            alert("material $itemName not found")
                            null
                        }
                }
            }
        }
        val material = Material.getMaterial(itemName)
        if (material != null) {
            return { ItemStack(material) }
        } else
            return run {
                alert("material $itemName not found")
                null
            }
    }

    private fun alert(message: String) {
        Pickave.logger.warning(message)
    }
}