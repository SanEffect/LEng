package com.san.leng.core

import android.app.Activity

object Constants {

    const val DATABASE_NAME = "leng_database"

    const val WORDS_API_URL = "https://wordsapiv1.p.rapidapi.com"
    //        const val WORDS_API_KEY = "API_KEY"
    const val WORDS_API_KEY = "a76e4d09a9msh2c7ac1797e0784bp157a8cjsn10a314ec13a7"
    const val WORDS_API_HOST = "wordsapiv1.p.rapidapi.com"

    // Keys for navigation
    const val ADD_RESULT_OK = Activity.RESULT_FIRST_USER + 1
    const val EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 2
    const val DELETE_RESULT_OK = Activity.RESULT_FIRST_USER + 3
}
