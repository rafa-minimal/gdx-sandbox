package com.minimal.gdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

fun Int.pressed(): Boolean {
    return Gdx.input.isKeyPressed(this)
}

fun Int.justPressed(): Boolean {
    return Gdx.input.isKeyJustPressed(this)
}

/**
 * Get touch position in scene (world) coordinates
 */
private val touchPos3 = Vector3()
private val touchPos = Vector2()
fun getTouchPos(camera: Camera, pointer: Int = 0): Vector2 {
    val x = Gdx.input.getX(pointer)
    val y = Gdx.input.getY(pointer)
    touchPos3.set(x.toFloat(), y.toFloat(), 0f)
    camera.unproject(touchPos3)
    return touchPos.set(touchPos3.x, touchPos3.y)
}