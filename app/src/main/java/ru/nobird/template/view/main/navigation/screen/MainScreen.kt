package ru.nobird.template.view.main.navigation.screen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.nobird.template.view.main.ui.fragment.MainFragment

class MainScreen : FragmentScreen {
    override fun createFragment(factory: FragmentFactory): Fragment =
        MainFragment.newInstance()
}
