package ru.nobird.template.view.debug

import android.app.Application
import okhttp3.Interceptor

object DebugTools {
    fun initDebugTools(app: Application) {
        // no op
    }

    fun getDebugInterceptors(): Set<Interceptor> =
        emptySet()
}
