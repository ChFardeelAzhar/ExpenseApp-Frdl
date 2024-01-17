package com.example.expenseapproom.di

import android.app.Application
import androidx.room.Room
import com.example.expenseapproom.expenseData.ExpenseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabaseModule(application: Application): ExpenseDatabase {
        return Room.databaseBuilder(application, ExpenseDatabase::class.java, "expense_database")
            .allowMainThreadQueries().build()
    }

    @Provides
    fun provideDao(db: ExpenseDatabase) = db.getDao()

}