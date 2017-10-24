package com.minimal.sandbox

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.Preferences
import com.badlogic.gdx.Screen
import com.minimal.gdx.justPressed

class SandboxMain : Game() {
    var currentScreen = 0
    lateinit var prefs: Preferences

    val screens: Array<() -> Screen> = arrayOf(
            { TailSandbox() },
            { ActorsSandbox() },
            { DistanceFieldFontSandbox() },
            { MenuScreenPortrait() }
    )

    fun nextScreen(): Screen {
        currentScreen++
        if (currentScreen >= screens.size) {
            currentScreen = 0
        }
        prefs.putInteger("last-screen", currentScreen)
        prefs.flush()
        return screens[currentScreen]()
    }

    override fun create() {
        prefs = Gdx.app.getPreferences("GdxSandbox")
        currentScreen = prefs.getInteger("last-screen", 0)
        if (currentScreen >= screens.size) {
            currentScreen = 0
        }
        setScreen(screens[currentScreen]())
    }

    override fun render() {
        super.render()
        if (Keys.TAB.justPressed()) {
            setScreen(nextScreen())
        }
        if (Keys.R.justPressed()) {
            currentScreen--
            setScreen(nextScreen())
        }
        if (Keys.ESCAPE.justPressed()) {
            Gdx.app.exit()
        }
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        Ctx.hudCamera.resize(width, height)
    }

    override fun dispose() {
        super.dispose()
        Ctx.dispose()
    }
}