package ru.nobird.template.view.main.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import javax.inject.Inject
import ru.nobird.app.presentation.redux.container.ReduxView
import ru.nobird.android.view.redux.ui.extension.reduxViewModel
import ru.nobird.template.App
import ru.nobird.template.R
import ru.nobird.template.databinding.FragmentMainBinding
import ru.nobird.template.presentation.main.MainFeature
import ru.nobird.template.view.main.viewmodel.MainViewModel

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

    private val fragmentMainBinding by viewBinding(FragmentMainBinding::bind)

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentMainBinding.textView.setText(R.string.app_name)
    }

    override fun render(state: MainFeature.State) {
        // no op
    }

    override fun onAction(action: MainFeature.Action.ViewAction) {
        // no op
    }
}
