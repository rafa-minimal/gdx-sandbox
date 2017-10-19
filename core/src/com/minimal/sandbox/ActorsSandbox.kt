package com.minimal.sandbox

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.Actions.*
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.minimal.gdx.rnd
import ktx.actors.plus


class Remove : Action() {
    override fun act(delta: Float): Boolean {
        target.remove()
        return true
    }
}

class ActorsSandbox : ScreenAdapter() {
    val viewport = ScreenViewport()
    val stage = Stage()
    val star = Texture("star.png")
    var timer = 1f

    override fun show() {
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(delta)
        stage.draw()

        timer -= delta
        if (timer <= 0) {
            val image = Image(star)
            image.setSize(20f, 20f)
            image.setPosition(rnd(Gdx.graphics.width).toFloat(), rnd(Gdx.graphics.height).toFloat())
            image.setOrigin(image.width / 2, image.height / 2)

            val die = Actions.action(Remove::class.java)

            image + sequence(
                    parallel(moveTo(10f, 10f, 0.5f), rotateTo(360f, 0.5f)),
                    scaleTo(2f, 2f, 0.2f, Interpolation.pow2),
                    scaleTo(0.1f, 0.1f, 0.1f),
                    die)


            stage + image
            timer = rnd(1f)
        }
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun hide() {
        Gdx.input.inputProcessor = null
    }

    override fun dispose() {
        stage.dispose()
        star.dispose()
    }
}