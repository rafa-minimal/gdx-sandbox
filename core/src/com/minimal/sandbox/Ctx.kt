package com.minimal.sandbox

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.minimal.camera.HudCamera

object Ctx {
    val batch = SpriteBatch()
    val boxRenderer = Box2DDebugRenderer()
    val font = BitmapFont()
    val hudCamera = HudCamera()

    fun dispose() {
        font.dispose()
        boxRenderer.dispose()
        batch.dispose()
    }
}