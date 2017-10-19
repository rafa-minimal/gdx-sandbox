package com.minimal.sandbox

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody
import com.badlogic.gdx.physics.box2d.World
import com.minimal.camera.WorldCamera
import com.minimal.fx.SnakeTail
import com.minimal.fx.FadingTail
import com.minimal.fx.Tail
import com.minimal.gdx.*
import ktx.box2d.body
import ktx.math.vec2

class TailSandbox : ScreenAdapter() {
    val worldSize = 40f
    var running = true

    val camera = WorldCamera(worldSize, worldSize)

    var world = World(vec2(0f, -10f), false)
    lateinit var ball: Body

    // Gradient tail
    var gradientTex = Texture("gradient_tail.png")
    var gradientRegion = TextureRegion(gradientTex)

    // Solid tail (alpha = 1)
    var solidTex = Texture("solid_tail.png")
    var solidRegion = TextureRegion(solidTex)

    val balls = ArrayList<Ball>()
    var mouseTail = SnakeTail(solidRegion, 1f, 60)

    inner class Ball(val radius: Float, length: Int = 60, snake: Boolean) {
        val tail: Tail
        init {
            if (snake)
                tail = SnakeTail(rnd(gradientRegion, solidRegion), radius, length)
            else
                tail = FadingTail(rnd(gradientRegion, solidRegion), radius, length)
        }
        val ball = world.body(DynamicBody) {
            linearVelocity.rnd(15f)
            position.rnd(20f)
            fixedRotation = true
            circle(radius) {
                density = 1f
                restitution = 1f
                friction = 1f
            }
        }
        fun draw(batch: SpriteBatch) {
            tail.draw(batch)
        }

        fun update() {
            tail.add(ball.position)
        }
    }

    init {
        val s = worldSize / 2
        world.body {
            chain(vec2(-s, -s), vec2(-s, s), vec2(s, s), vec2(s, -s), vec2(-s, -s)) {
                restitution = 1f
                friction = 0f
            }
        }

        repeat(5) {
            balls.add(Ball(rnd(0.5f, 1.5f), rnd(10, 120), rndbool()))
        }
    }

    override fun hide() {
        world.dispose()
        gradientTex.dispose()
        solidTex.dispose()
        balls.clear()
    }

    private var isTouched: Boolean = false

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        if (running || Keys.S.justPressed()) {
            world.step(maxOf(delta, 1 / 30f), 8, 3)
            balls.forEach { b -> b.update() }
            if (Gdx.input.isTouched) {
                if (!isTouched)
                    mouseTail.add(getTouchPos(camera))
                isTouched = true
            } else {
                isTouched = false
            }
        }
        if (Keys.SPACE.justPressed()) {
            running = !running
        }
        if (Keys.MINUS.justPressed()) {
            camera.zoom *= 2f
            camera.update()
        }
        if (Keys.PLUS.justPressed()) {
            camera.zoom /= 2f
            camera.update()
        }
        if (Gdx.input.isTouched) {
            Gdx.input.getX(0)
        }

        Ctx.boxRenderer.render(world, camera.combined)

        Ctx.batch.render(camera, Color.WHITE) {
            balls.forEach { b -> b.draw(Ctx.batch) }
            mouseTail.draw(Ctx.batch)
        }
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        camera.resize(width, height)
    }
}