package com.minimal.sandbox

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter.Linear
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.utils.Align
import com.minimal.camera.HudCamera
import com.minimal.gdx.render


class DistanceFieldFontSandbox : ScreenAdapter() {
    val fontTex = Texture("fonts/century-gothic-40.png")
    val font: BitmapFont
    val fontShader: ShaderProgram
    val batch = SpriteBatch()
    val camera = HudCamera()

    var zoom = 1f
    //val targetZoom = 0.5f

    init {
        fontTex.setFilter(Linear, Linear)
        font = BitmapFont(Gdx.files.internal("fonts/century-gothic-40.fnt"), TextureRegion(fontTex), false)

        fontShader = ShaderProgram(Gdx.files.internal("shaders/distance-font.vert"), Gdx.files.internal("shaders/distance-font.frag"))
        if (!fontShader.isCompiled) {
            Gdx.app.error("fontShader", "compilation failed:\n" + fontShader.log)
        }
        batch.shader = fontShader
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        //zoom = zoom + (targetZoom - zoom) * 0.1f
        zoom -= 0.002f
        camera.zoom = zoom
        camera.update()

        batch.render(camera, Color.WHITE) {
            font.draw(batch, "minimal", 0f, Gdx.graphics.height/2f, Gdx.graphics.width.toFloat(), Align.center, false)
        }
    }

    override fun resize(width: Int, height: Int) {
        camera.resize(width, height)
    }

    override fun dispose() {
        batch.dispose()
    }
}