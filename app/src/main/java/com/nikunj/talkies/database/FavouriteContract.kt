package com.nikunj.talkies.database

import android.provider.BaseColumns

object FavouriteContract {
    object FavouriteEntry : BaseColumns {
        const val TABLE_NAME = "FAVOURITE"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_TITLE = "title"
    }
}