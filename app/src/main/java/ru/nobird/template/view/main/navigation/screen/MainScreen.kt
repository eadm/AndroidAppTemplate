package ru.nobird.template.view.main.navigation.screen

import androidx.fragment.app.Fragment
import ru.nobird.template.view.main.ui.fragment.MainFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class MainScreen : SupportAppScreen() {
    override fun getFragment(): Fragment =
        MainFragment.newInstance()
}
