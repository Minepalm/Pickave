package com.minepalm.pickave.ender

import com.minepalm.pickave.Pickave
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.block.data.type.EndPortalFrame
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class EnderEventListener : Listener {

    @EventHandler
    fun onEnderEvent(event: PlayerInteractEvent) {
        val block = event.clickedBlock ?: return
        val player = event.player
        val itemEnderEye = player.inventory.itemInMainHand
        if(block.type == Material.END_PORTAL_FRAME) {
            if(itemEnderEye.type == Material.ENDER_EYE) {
                itemEnderEye.amount = itemEnderEye.amount - 1
                val data = block.blockData as EndPortalFrame
                data.setEye(true)
                effectEnderFrame(event.clickedBlock!!)
            }
        }
    }

    fun effectEnderFrame(block: Block) {
        // 우클릭하면 엔더 프레임에 이펙트가 12도 각도 에서 곡선으로 모임.
        // 이펙트가 3번 모이고, 엔더눈이 깨짐.
        // 깨짐과 동시에 폭발 ( 데미지 x )
        // 깨지면서 보라색 이펙트가 펼쳐짐
        // code this here
        block.type = Material.END_PORTAL_FRAME
        val center = block.location.add(0.5, 1.5, 0.5)
        //create effect here ( 12 degree )
        for (i in 0 until 3) {
            Pickave.ex.sync(0 + (i * 18)) {
                drawGageParticle(center)
            }
        }
        Pickave.ex.sync(18 * 3 + 4) {
            block.world.createExplosion(center, 4f, false, false)
            val data = block.blockData as EndPortalFrame
            data.setEye(false)
            EnderEvents.allPlayers.first().let {
                it.playSound(it.location, Sound.ENTITY_ENDER_DRAGON_DEATH, 1f, 1f)
            }
            Pickave.ex.sync(10) {
                EnderEvents.allEvents[Random.nextInt(EnderEvents.allEvents.size)].run()
            }
        }

    }

    private fun drawGageParticle(center: Location) {
        val degree = 12 / 360
        var radius = 4.0
        val effectType = Particle.PORTAL
        var count = 0
        for (j in 0 until 8) {
            Pickave.ex.sync(2 * j) {

                for (i in 0 until 12) {
                    radius -= 0.5
                    val radian = Math.toRadians(degree.toDouble() * 360)
                    val x = radius * cos(radian)
                    val z = radius * sin(radian)
                    val loc = Location(center.world, center.x + x, center.y, center.z + z)
                    center.world.spawnParticle(effectType, loc, 1)
                }
            }
        }
    }
}