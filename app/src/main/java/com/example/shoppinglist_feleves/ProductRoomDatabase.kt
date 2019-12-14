package com.example.shoppinglist_feleves

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Product::class], version = 1)
abstract class ProductRoomDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object {

        private var INSTANCE: ProductRoomDatabase? = null

        fun getInstance(context: Context): ProductRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(ProductRoomDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ProductRoomDatabase::class.java,
                        "product_DB"
                    ).addCallback(roomCallback).build()
                }
            }
            return INSTANCE
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDBAsyncTask(INSTANCE).execute()

            }
        }
    }

    private class populateDBAsyncTask(productDatabase: ProductRoomDatabase?) :
        AsyncTask<Unit, Unit, Unit>() {
        val productDao = productDatabase?.productDao()

        override fun doInBackground(vararg params: Unit?) {

        }

    }
}