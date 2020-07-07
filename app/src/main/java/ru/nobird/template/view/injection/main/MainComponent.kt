package ru.nobird.template.view.injection.main

import ru.nobird.template.view.main.ui.activity.MainActivity
import ru.nobird.template.view.main.ui.fragment.MainFragment
import dagger.Subcomponent
import ru.nobird.template.view.injection.sample.SampleDataModule

@Subcomponent(modules = [MainModule::class, SampleDataModule::class])
interface MainComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): MainComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(mainFragment: MainFragment)
}