package com.example.e_bookapp.DI

import android.app.Application
import androidx.room.Room
import com.example.e_bookapp.BaseApplication
import com.example.e_bookapp.data_layer.database.EbookAppDatabase
import com.example.e_bookapp.data_layer.repository.Repo
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DIModule {

    @Provides
    @Singleton
    fun provideFirebaseRealtimeDatabase() : FirebaseDatabase {
        return FirebaseDatabase.getInstance("https://e-bookapp-49494-default-rtdb.asia-southeast1.firebasedatabase.app/")
    }

    @Provides
    @Singleton
    fun provideRepo(firebaseDatabase : FirebaseDatabase  , database : EbookAppDatabase) : Repo {
        return Repo(firebaseDatabase,database)
    }

    @Provides
    @Singleton
    fun provideDatabase(application : Application): EbookAppDatabase {
        return Room.databaseBuilder(context = application,
            klass = EbookAppDatabase::class.java,
            name = "EbookAppDatabase").fallbackToDestructiveMigration().build()
    }



}