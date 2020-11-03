package ru.nobird.template.view.main.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import ru.nobird.template.App
import ru.nobird.template.R
import ru.nobird.template.presentation.main.MainPresenter
import ru.nobird.template.presentation.main.MainView
import javax.inject.Inject

class MainFragment : Fragment(R.layout.fragment_main), MainView {
    companion object {
        fun newInstance(): Fragment =
            MainFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mainPresenter: MainPresenter by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectComponent()
    }

    private fun injectComponent() {
        App.component()
            .mainComponentBuilder()
            .build()
            .inject(this)
    }

    override fun onStart() {
        super.onStart()
        mainPresenter.attachView(this)
    }

    override fun onStop() {
        mainPresenter.detachView(this)
        super.onStop()
    }
}
