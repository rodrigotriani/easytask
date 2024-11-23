package com.rtriani.easytask.android.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rtriani.easytask.android.classes.Converters

val MIGRATION = object : Migration(11, 12) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE todos DROP COLUMN status")
        db.execSQL("ALTER TABLE todos ADD COLUMN status TEXT NOT NULL DEFAULT 'PENDENTE'")
    }
}

@Database(
    entities = [
        TodoEntity::class,
        AuthorizationEntity::class
    ],
    version = 12
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