package com.minimal.camera

import com.badlogic.gdx.graphics.OrthographicCamera

class HudCamera : OrthographicCamera() {
    fun resize(width: Int, height: Int) {
        viewportWidth = width.toFloat()
        viewportHeight = height.toFloat()
        position.set(width / 2f, height / 2f, 0f)
        update()
    }
}