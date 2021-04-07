package com.maji.mvvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.maji.mvvm.model.Subject

@Database(entities = [Subject::class], version = 3)
abstract class SubjectDataBase : RoomDatabase() {
    abstract fun subjectDao():SubjectDao

    companion object {
        val TAG = SubjectDataBase::class.java.simpleName
        const val currentDBVersion = 3

        @Volatile
        private var mInst: SubjectDataBase? = null
        private const val DATA_BASE_NAME = "maji.db"

        fun getInstance(context: Context): SubjectDataBase? {

            if(mInst == null) {
                synchronized(this) {
                    if(mInst == null) {
                        mInst = createDataBase(context)
                    }
                }
            }
            return mInst
        }

        private fun createDataBase(context: Context): SubjectDataBase {
            return Room.databaseBuilder(context.applicationContext, SubjectDataBase::class.java, DATA_BASE_NAME)
                .allowMainThreadQueries()
                //.fallbackToDestructiveMigration() // 升级数据库失败后清空表重建
                    .addMigrations(object : Migration(1, currentDBVersion) { // 维护数据库升级
                        override fun migrate(database: SupportSQLiteDatabase) {
                            database.execSQL("ALTER TABLE subject "
                                    + " ADD COLUMN create_time VARCHAR(100) NOT NULL DEFAULT 2021-04-07 12:45:00")
                        }
                    })
                    .addCallback(object : RoomDatabase.Callback(){

                    })
                .build()
        }

    }


}