package com.ahmedmolawale.starwars

import org.junit.Rule

/**
 * Base class for Unit tests. Inherit from it to create test cases which DO NOT contain android
 * framework dependencies or components.
 *
 *
 */

abstract class UnitTest {
    @Suppress("LeakingThis")
    @Rule
    @JvmField
    val injectMocks = InjectMocksRule.create(this@UnitTest)
}