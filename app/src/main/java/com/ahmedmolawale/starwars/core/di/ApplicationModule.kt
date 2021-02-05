/**
 * Copyright (C) 2020 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ahmedmolawale.starwars.core.di

import android.content.Context
import androidx.room.Room
import com.ahmedmolawale.starwars.BuildConfig
import com.ahmedmolawale.starwars.features.characters.data.local.StarWarDatabase
import com.ahmedmolawale.starwars.features.characters.data.remote.api.StarWarApi
import com.ahmedmolawale.starwars.features.characters.data.repository.CharacterRepository
import com.ahmedmolawale.starwars.features.characters.domain.repository.ICharacterRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://swapi.dev/api/")
            .client(createClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideStarWarService(retrofit: Retrofit): StarWarApi {
        return retrofit.create(StarWarApi::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): StarWarDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            StarWarDatabase::class.java,
            StarWarDatabase.DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun providesCharacterDao(db: StarWarDatabase) = db.charactersDao()

}

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {
    @Binds
    abstract fun bindCharacterRepository(repo: CharacterRepository): ICharacterRepository
}
