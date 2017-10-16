package com.minimal.gdx

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

fun Vector3.set(vec: Vector2) {
    this.set(vec.x, vec.y, 0f)
}

fun Vector2.rnd(radius: Float): Vector2 {
    val rad = MathUtils.random(radius)
    val phi = MathUtils.random(MathUtils.PI2)
    return set(rad * MathUtils.cos(phi), rad * MathUtils.sin(phi))
}

fun Vector2.rotateLeft(): Vector2 {
    val x = this.x
    this.x = -y
    y = x
    return this
}

fun Vector2.rotateRight(): Vector2 {
    val x = this.x
    this.x = y
    y = -x
    return this
}