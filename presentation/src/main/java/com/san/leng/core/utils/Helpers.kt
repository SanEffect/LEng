package com.san.leng.core.utils

fun getWordIndexBySelectionStart(match: MatchResult?, selectionStart: Int): Int? {
    return if (match?.range?.contains(selectionStart) == false) getWordIndexBySelectionStart(
        match.next(),
        selectionStart
    ) else match?.range?.first
}

