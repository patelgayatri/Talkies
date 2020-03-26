package com.nikunj.talkies.models

data class AddFavouriteModel(
    var media_type:String,
    var media_id: Int,
    var favorite: Boolean

)