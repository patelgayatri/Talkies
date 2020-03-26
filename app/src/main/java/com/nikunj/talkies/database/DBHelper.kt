package com.nikunj.talkies.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.nikunj.talkies.models.AddFavouriteModel
import com.nikunj.talkies.database.FavouriteContract.FavouriteEntry.COLUMN_NAME_ID
import com.nikunj.talkies.database.FavouriteContract.FavouriteEntry.COLUMN_NAME_TITLE
import com.nikunj.talkies.database.FavouriteContract.FavouriteEntry.TABLE_NAME

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    companion object {
        private const val DATABASE_NAME = "MOVIE_DATABASE"
        private const val DATABASE_VERSION = 1

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val SQL_CREATE_ENTRIES =
            ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                    "(" + COLUMN_NAME_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_NAME_TITLE + " TEXT)")
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun addFavourite(addFavourite: AddFavouriteModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NAME_ID, addFavourite.media_id)
        contentValues.put(COLUMN_NAME_TITLE,addFavourite.media_type)

        val success = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return success
    }

    fun isFavourite(id: Int): Boolean {
        var isFavourite = false

        val query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_ID + "=" + id
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        if (cursor.count > 0) {
            isFavourite = true
            cursor.close()
        }
        return isFavourite
    }
}