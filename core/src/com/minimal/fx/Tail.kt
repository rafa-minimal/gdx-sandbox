package com.minimal.fx

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.minimal.gdx.rotateRight
import com.minimal.utils.FloatCircularBuffer
import ktx.math.vec2

class Tail(val tex: TextureRegion) {
    val maxPointsNumber = 60
    val fadeFactor = 0.8f
    val tailWidth = 1f
    val lastPoint = vec2()
    val lastPointLeft = vec2()
    val lastPointRight = vec2()
    var hasLastPoint = false

    val POINTS_PER_VERT = 5
    val white = Color.WHITE.toFloatBits()

    val u = tex.u
    val v = tex.v2
    val u2 = tex.u2
    val v2 = tex.v

    // max segments
    // segment = 4 vertices
    // vertex = 6 values
    private val vertices = FloatCircularBuffer(maxPointsNumber * 4 * POINTS_PER_VERT)

    private val tmp = vec2()

    fun add(pos: Vector2) {
        if (hasLastPoint) {
            vertices.push(lastPointRight.x)
            vertices.push(lastPointRight.y)
            vertices.push(white)
            vertices.push(u)
            vertices.push(v)

            vertices.push(lastPointLeft.x)
            vertices.push(lastPointLeft.y)
            vertices.push(white)
            vertices.push(u)
            vertices.push(v2)

            tmp.set(pos).sub(lastPoint).nor()
            tmp.rotateRight().scl(tailWidth/2f)

            lastPointLeft.set(pos.x - tmp.x, pos.y - tmp.y)
            lastPointRight.set(pos.x + tmp.x, pos.y + tmp.y)

            vertices.push(pos.x - tmp.x)
            vertices.push(pos.y - tmp.y)
            vertices.push(white)
            vertices.push(u2)
            vertices.push(v2)

            vertices.push(pos.x + tmp.x)
            vertices.push(pos.y + tmp.y)
            vertices.push(white)
            vertices.push(u2)
            vertices.push(v2)
        } else {
            lastPointLeft.set(pos)
            lastPointRight.set(pos)
            /*vertices.push(pos.x)
            vertices.push(pos.y)
            vertices.push(white)
            vertices.push(u)
            vertices.push(v)

            vertices.push(pos.x)
            vertices.push(pos.y)
            vertices.push(white)
            vertices.push(u)
            vertices.push(v2)*/
            hasLastPoint = true
        }
        lastPoint.set(pos)
    }

    /*var x = 0f
    val step = 0.1f

    fun add(pos: Vector2, alph: Float) {
        vertices.push(x)
        vertices.push(10f)
        vertices.push(white)
        vertices.push(u)
        vertices.push(v)

        vertices.push(x)
        vertices.push(11f)
        vertices.push(white)
        vertices.push(u)
        vertices.push(v2)

        vertices.push(x+step)
        vertices.push(11f)
        vertices.push(white)
        vertices.push(u2)
        vertices.push(v2)

        vertices.push(x+step)
        vertices.push(10f)
        vertices.push(white)
        vertices.push(u2)
        vertices.push(v)

        x+=step
    }*/

    fun breakTail() {
        hasLastPoint = false
    }

    fun update(timeStepSec: Float) {
        /*for (i in 0 until alpha.size()) {
            alpha[i] = alpha[i]*(1 - fadeFactor * timeStepSec)
        }*/
        for (i in 0 until vertices.size()) {
            vertices[i+3] -= 1f/60f
        }
    }

    fun draw(batch: SpriteBatch) {
        var count = Math.min(vertices.array.size - vertices.head, vertices.size())
        batch.draw(tex.texture, vertices.array, vertices.head, count)
        count = vertices.size() - count
        if(count > 0) {
            batch.draw(tex.texture, vertices.array, 0, count)
        }

        //batch.draw(tex.texture, vertices.array, 0, vertices.size())

        /*for (i in 0 until alpha.size()) {
            batch.setColor(1f, 1f, 1f, alpha[i])
            batch.draw(tex, posX[i], posY[i],
                    0f, 0f,
                    length[i], tailWidth,
                    1f, 1f,
                    angle[i])
        }*/
    }
}