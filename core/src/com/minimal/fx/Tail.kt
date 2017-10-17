package com.minimal.fx

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2

interface Tail {
    fun add(pos: Vector2)
    fun draw(batch: SpriteBatch)
}