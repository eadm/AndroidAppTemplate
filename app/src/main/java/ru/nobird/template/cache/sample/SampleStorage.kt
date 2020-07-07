package ru.nobird.template.cache.sample

import android.content.Context
import com.chibatching.kotpref.KotprefModel
import javax.inject.Inject

class SampleStorage
@Inject
constructor(context: Context) : KotprefModel(context) {
    var sampleVal by nullableStringPref()
}