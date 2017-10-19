package com.minimal.sandbox

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.Screen
import com.minimal.gdx.justPressed

class SandboxMain : Game() {
    var currentScreen = 0

    private fun nextScreen() : Screen {
        currentScreen++
        when (currentScreen) {
            1 -> return TailSandbox()
            2 -> return ActorsSandbox()
            else -> {
                currentScreen = 0
                return nextScreen()
            }
        }
    }

    override fun create() {
        setScreen(nextScreen())
    }

    override fun render() {
        super.render()
        if(Keys.TAB.justPressed()) {
            setScreen(nextScreen())
        }
        if(Keys.R.justPressed()) {
            currentScreen--
            setScreen(nextScreen())
        }
        if(Keys.ESCAPE.justPressed()) {
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