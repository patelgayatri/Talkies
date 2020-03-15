package com.nikunj.talkies.Model

data class AddFavourite(
    var media_type:String,
    var media_id: Int,
    var favorite: Boolean

)