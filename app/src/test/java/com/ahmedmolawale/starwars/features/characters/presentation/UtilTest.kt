package com.ahmedmolawale.starwars.features.characters.presentation

import com.ahmedmolawale.starwars.UnitTest
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UtilTest : UnitTest() {

    @Test
    fun `convertCMToInches should return height in inches`() {
        val heightInCm = 4.toString()
        val expectedInInches = 1.575.toString()
        val res = convertCMToInches(heightInCm)
        assertThat(res).isEqualTo(expectedInInches)
    }

    @Test
    fun `convertCMToInches should return unknown if cm is not convertible`() {
        val heightInCm = "string"
        val expectedInInches = "unknown"
        val res = convertCMToInches(heightInCm)
        assertThat(res).isEqualTo(expectedInInches)
    }

    @Test
    fun `populationValue should return value as Long`() {
        val populationAsString = 40000.toString()
        val expected = 40000.toString()
        val res = populationValue(populationAsString)
        assertThat(res).isEqualTo(expected)
    }

    @Test
    fun `populationValue should return unknown if population is not convertible`() {
        val populationAsString = "string"
        val expected = "unknown"
        val res = populationValue(populationAsString)
        assertThat(res).isEqualTo(expected)
    }

    @Test
    fun `extractInitials should return empty`() {
        val name = ""
        val res = extractInitials(name)
        assertThat(res).isEmpty()
    }

    @Test
    fun `extractInitials should return a character`() {
        val name = "ola"
        val res = extractInitials(name)
        assertThat(res).isEqualTo("O")
    }

    @Test
    fun `extractInitials should return two characters`() {
        val name = "ola wale"
        val res = extractInitials(name)
        assertThat(res).isEqualTo("OW")
    }

    @Test
    fun `convertCMTonches should return unknown if cm is not convertible`() {
        val heightInCm = "string"
        val expectedInInches = "unknown"
        val res = convertCMToInches(heightInCm)
        assertThat(res).isEqualTo(expectedInInches)
    }
}
