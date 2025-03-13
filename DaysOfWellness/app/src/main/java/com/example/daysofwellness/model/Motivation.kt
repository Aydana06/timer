package com.example.daysofwellness.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Motivation (
    @StringRes val motivationRes: Int,
    @StringRes val dayRes: Int,
    @StringRes val author: Int,
    @DrawableRes val imageRes: Int
)
