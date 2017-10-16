package com.minimal.camera

import com.badlogic.gdx.graphics.OrthographicCamera
import ktx.math.vec2

/**
 * Makes sure the world fits in the screen
 */
class WorldCamera(var worldWidth: Float, var worldHeight: Float) : OrthographicCamera() {

    val worldCenter = vec2()

    fun resize(width: Int, height: Int) {
        val xScale = worldWidth  / width
        val yScale = worldHeight  / height
        if (yScale < xScale) {
            viewportWidth = width * xScale
            viewportHeight = height * xScale
        } else {
            viewportWidth = width * yScale
            viewportHeight = height * yScale
        }

        position.set(worldCenter.x, worldCenter.y, 0f)

        update()
    }
}