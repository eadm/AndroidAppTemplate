package ru.nobird.template.view.main.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.nobird.android.presentation.redux.container.ReduxView
import ru.nobird.android.view.redux.ui.extension.reduxViewModel
import ru.nobird.template.App
import ru.nobird.template.R
import ru.nobird.template.presentation.main.MainFeature
import ru.nobird.template.view.main.viewmodel.MainViewModel
import javax.inject.Inject

class MainFragment :
    Fragment(R.layout.fragment_main),
    ReduxView<MainFeature.State, MainFeature.Action.ViewAction> {

    companion object {
        fun newInstance(): Fragment =
            MainFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mainViewModel: MainViewModel by reduxViewModel(this) { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectComponent()
        mainViewModel.onNewMessage(MainFeature.Message.Init)
    }

    private fun injectComponent() {
        App.component()
            .mainComponentBuilder()
            .build()
            .inject(this)
    }

    override fun render(state: MainFeature.State) {
        // no op
    }

    override fun onAction(action: MainFeature.Action.ViewAction) {
        // no op
    }
}
