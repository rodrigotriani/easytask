package com.rtriani.easytask.android.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rtriani.easytask.android.classes.Converters

val MIGRATION = object : Migration(5, 6) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE todos DROP COLUMN taskDate")
    }
}

@Database(
    entities = [
        TodoEntity::class,
        AuthorizationEntity::class
    ],
    version = 6,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDB: RoomDatabase() {
    abstract val authorizationDAL: AuthorizationDAL
    abstract val todoDAL: TodoDAL
}

object AppDBProvider {
    @Volatile
    private var INSTANCE: AppDB? = null

    fun provide(context: Context): AppDB {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDB::class.java,
                name = "easyTaskDB"
            ).addMigrations(MIGRATION)
            .build()
            INSTANCE = instance
            instance
        }
    }
}