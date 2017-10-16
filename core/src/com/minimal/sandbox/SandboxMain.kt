package com.minimal.sandbox

import com.badlogic.gdx.Game
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.Screen
import com.minimal.gdx.justPressed
import java.util.*

class SandboxMain : Game() {
    var currentScreen = 0
    val screens = ArrayList<Screen>()

    override fun create() {
        screens.add(TailSandbox())

        setScreen(screens[currentScreen])
    }

    override fun render() {
        super.render()
        if(Keys.TAB.justPressed()) {
            setScreen(nextScreen())
        }
        if(Keys.R.justPressed()) {
            setScreen(screens[currentScreen])
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

    private fun nextScreen(): Screen {
        currentScreen = (currentScreen + 1) % screens.size
        /*currentScreen++
        if (currentScreen >= screens.size) {
            currentScreen = 0
        }*/
        return screens[currentScreen]
    }
}