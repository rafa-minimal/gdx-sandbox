package com.minimal.fx

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.minimal.gdx.rotateRight
import com.minimal.utils.FloatCircularBuffer
import ktx.math.vec2

class FadingTail(val tex: TextureRegion, val tailWidth: Float, maxSegmentsNumber: Int) : Tail {
    val lastPoint = vec2()
    val lastDirection = vec2()
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
    private val vertices = FloatCircularBuffer(maxSegmentsNumber * 4 * POINTS_PER_VERT)

    private val tmp = vec2()

    override fun add(pos: Vector2) {
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
            val flip = lastDirection.dot(tmp) < 0f
            lastDirection.set(tmp)
            tmp.rotateRight().scl(tailWidth/2f)

            lastPointLeft.set(pos.x - tmp.x, pos.y - tmp.y)
            lastPointRight.set(pos.x + tmp.x, pos.y + tmp.y)

            val sign = if (flip) -1f else 1f
            vertices.push(pos.x - sign*tmp.x)
            vertices.push(pos.y - sign*tmp.y)
            vertices.push(white)
            vertices.push(u2)
            vertices.push(v2)

            vertices.push(pos.x + sign*tmp.x)
            vertices.push(pos.y + sign*tmp.y)
            vertices.push(white)
            vertices.push(u2)
            vertices.push(v)
        } else {
            lastPointLeft.set(pos)
            lastPointRight.set(pos)
            hasLastPoint = true
        }
        lastPoint.set(pos)
    }

    fun breakTail() {
        hasLastPoint = false
    }

    override fun draw(batch: SpriteBatch) {
        // update alpha
        for (i in 0 until vertices.size() / POINTS_PER_VERT) {
            vertices[i*POINTS_PER_VERT+2] = Color.toFloatBits(1f, 1f, 1f, i.toFloat() / vertices.size())
        }

        var count = Math.min(vertices.array.size - vertices.head, vertices.size())
        batch.draw(tex.texture, vertices.array, vertices.head, count)
        count = vertices.size() - count
        if(count > 0) {
            batch.draw(tex.texture, vertices.array, 0, count)
        }
    }
}