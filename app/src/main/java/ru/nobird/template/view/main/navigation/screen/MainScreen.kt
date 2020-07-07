package ru.nobird.template.view.main.navigation.screen

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.nobird.template.view.main.ui.fragment.MainFragment

class MainScreen : SupportAppScreen() {
    override fun getFragment(): Fragment =
        MainFragment.newInstance()
}