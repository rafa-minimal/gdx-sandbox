package com.minimal.gdx

import com.badlogic.gdx.math.MathUtils

/**
 * Random in <from, end>
 */
fun rnd(from: Int, to: Int) = MathUtils.random(from, to)

/**
 * Random in <from, end)
 */
fun rnd(from: Float, to: Float) = MathUtils.random(from, to)

/**
 * Random in <0.0, 1.0)
 */
fun rnd() = MathUtils.random()

/**
 * Random boolean value
 */
fun rndbool() = rnd() < 0.5f

/**
 * Pick randomly either a or b
 */
fun <T> rnd(a: T, b: T) = if (rnd() < 0.5f) a else b