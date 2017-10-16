package com.minimal.sandbox

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture

class TailSandbox : ScreenAdapter() {
    lateinit var tex: Texture

    override fun show() {
        tex = Texture("badlogic.jpg")
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        Ctx.batch.projectionMatrix.set(Ctx.hudCamera.combined)
        Ctx.batch.begin()
        Ctx.batch.draw(tex, 0f, 0f)
        Ctx.batch.end()
    }

    override fun dispose() {
        tex.dispose()
    }
}