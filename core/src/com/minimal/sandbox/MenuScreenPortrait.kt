package com.minimal.sandbox

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.minimal.gdx.justPressed
import com.minimal.gdx.render
import ktx.actors.onClick

private fun alphaButton(skin: Skin, region: String) =
        Button(
                skin.newDrawable(region, Color(1f, 1f, 1f, .3f)),
                skin.newDrawable(region, Color(1f, 1f, 1f, .6f)))

private fun alphaButton(skin: Skin, region: String, onClick: () -> Unit): Button {
    val button = alphaButton(skin, region)
    button.onClick { onClick() }
    return button
}


class MenuScreenPortrait : ScreenAdapter() {
    val viewport = ScreenViewport()
    val stage = Stage(viewport, Ctx.batch)
    // This way we could load the default skin
    //val skin = Skin(Gdx.files.internal("skin/uiskin.json"), TextureAtlas(Gdx.files.internal("skin/uiskin.atlas")))
    val skin = Skin()
    val font = BitmapFont()
    val root = Table(skin)

    init {
        val labelStyle = LabelStyle(font, Color.WHITE)
        skin.add("default", labelStyle)
        skin.addRegions(TextureAtlas("atlas.atlas"))

        root.setFillParent(true)
        root.pad(10f)

        root.add(Label("Asteroids", skin)).pad(10f).colspan(3).row()
        root.add(Label("Bob", skin)).colspan(3).row()

        val playButton = alphaButton(skin, "play") { println("Play!") }
        root.add(playButton).colspan(3).expand().row()
        // root.add(TextField("opa", skin))

        val starButton = alphaButton(skin, "star") { println("Star!") }
        root.add(starButton).size(100f)
        root.add("minimal", "default").expandX().bottom()
        root.add(alphaButton(skin, "info") { println("Info!") }).size(100f)
        root.row()

        stage.addActor(root)
    }

    override fun show() {
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        Ctx.batch.render(Ctx.hudCamera, Color.WHITE) {
            Ctx.font.draw(Ctx.batch, "Asteroids", 0f, Gdx.graphics.height*0.8f, Gdx.graphics.width.toFloat(), Align.center, false)
        }

        if (Keys.D.justPressed()) {
            root.setDebug(!root.debug)
        }

        stage.act(delta)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun hide() {
        Gdx.input.inputProcessor = null
    }

    override fun dispose() {
        stage.dispose()
        skin.dispose()
        font.dispose()
    }
}