package com.haman.dearme.ui.model

enum class ChallengeState(val title: String) {
    TODO("진행 예정\uD83D\uDCCC"),
    DOING("진행중\uD83D\uDD25"),
    SUCCESS("성공\uD83C\uDF89"),
    FAIL("실패\uD83D\uDE2D")
}