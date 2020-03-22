package com.nikunj.talkies.Database

import android.provider.BaseColumns

object FavouriteContract {
    object FavouriteEntrry : BaseColumns {
        const val TABLE_NAME = "FAVOURITE"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_TITLE = "title"
    }
}