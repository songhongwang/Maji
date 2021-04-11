package com.maji.mvvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.maji.mvvm.model.Subject

@Database(entities = [Subject::class], version = SubjectDataBase.currentDBVersion)
abstract class SubjectDataBase : RoomDatabase() {
    abstract fun subjectDao():SubjectDao

    companion object {
        val TAG = SubjectDataBase::class.java.simpleName
        const val currentDBVersion = 4

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
            // 判断数据升级
            var start = 1
            if(getVersionCode(context) == 2) {
                start = 3
            }

            return Room.databaseBuilder(context.applicationContext, SubjectDataBase::class.java, DATA_BASE_NAME)
                .allowMainThreadQueries()
                //.fallbackToDestructiveMigration() // 升级数据库失败后清空表重建
                    .addMigrations(object : Migration(start, currentDBVersion) { // 维护数据库升级
                        override fun migrate(database: SupportSQLiteDatabase) {
                            if(start == 1) {
                                database.execSQL("ALTER TABLE subject "
                                        + " ADD COLUMN create_time TEXT NOT NULL DEFAULT 2021-04-07 12:45:00")
                            }

                            if(start == 3) {
                                database.execSQL("ALTER TABLE subject "
                                        + " ADD COLUMN commit_search_url TEXT")
                            }

                        }
                    })
                    .addCallback(object : RoomDatabase.Callback(){

                    })
                .build()
        }

        fun getVersionCode(context: Context):Int {
            return context.packageManager.getPackageInfo(context.packageName, 0).versionCode
        }
    }




}