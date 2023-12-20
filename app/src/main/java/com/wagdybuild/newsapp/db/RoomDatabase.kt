package com.wagdybuild.newsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.TypeConverters
import com.wagdybuild.newsapp.models.Article

@Database(entities = [Article::class], version = 1)
@TypeConverters(SourceConverter::class)
abstract class RoomDatabase : androidx.room.RoomDatabase() {
    abstract fun roomDAO(): ArticleDAO

    companion object {
        @Volatile
        private var INSTANCE: RoomDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: createDatabase(context).also { INSTANCE = it }
        }

        private fun createDatabase(context: Context)=
            databaseBuilder(
                context.applicationContext,
                RoomDatabase::class.java,
                "RoomDatabase"
            ).build()


    }
}
/*abstract fun roomDAO(): ArticleDAO

    companion object {
        @Volatile
        private var INSTANCE: RoomDatabase? = null
        fun getINSTANCE(context: Context): RoomDatabase? {
            if (INSTANCE == null) {
                synchronized(RoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = databaseBuilder(context.applicationContext,
                            RoomDatabase::class.java,
                            "RoomDatabase"
                        ).allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }*/
