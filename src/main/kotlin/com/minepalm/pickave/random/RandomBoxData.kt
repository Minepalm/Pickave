package com.minepalm.pickave.random

import com.minepalm.pickave.parseItem

class RandomBoxData(
    val name: String,
    val weight: Int
) {

    val item
        get() = name.parseItem()
}