package com.ahmedmolawale.starwars.core.di

import android.content.Context
import androidx.room.Room
import com.ahmedmolawale.starwars.features.characters.data.local.StarWarDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDataBase(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, StarWarDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    @Provides
    @Named("test_dao")
    fun providesCharacterDao(db: StarWarDatabase) = db.charactersDao()
}
