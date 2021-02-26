package com.ahmedmolawale.starwars.core.ext

import com.ahmedmolawale.starwars.UnitTest

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class StringExtensionsTest : UnitTest(){


    @Test
    fun `enforceHttps should change http to https`(){
        val url = "http://swapi.dev/api/planets/1/"
        val expectedUrl = "https://swapi.dev/api/planets/1/"
        assertThat(url.enforceHttps()).isEqualTo(expectedUrl)
    }

    @Test
    fun `enforceHttps should return if already using https`(){
        val url = "https://swapi.dev/api/planets/1/"
        val expectedUrl = "https://swapi.dev/api/planets/1/"
        assertThat(url.enforceHttps()).isEqualTo(expectedUrl)
    }
}