package com.example.apptuyendungvieclam.ui.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.apptuyendungvieclam.BR
import com.example.apptuyendungvieclam.ui.base.callback.BaseCallBack
import com.example.apptuyendungvieclam.ui.base.viewmodel.BaseViewModel
import dagger.android.support.AndroidSupportInjection
import java.util.*
import javax.inject.Inject
abstract class BaseMvvmFragment<
        CallBack : BaseCallBack,
        Model : BaseViewModel<CallBack>
        > : BaseFragment() {
    protected var mFirstLoad: Long = 0

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var mModel: Model

    protected abstract fun getViewModel(): Class<Model>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }
    override fun onCreateViewControl(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = super.onCreateViewControl(inflater, container, savedInstanceState)
        mModel = ViewModelProvider(this, viewModelFactory).get(getViewModel())
        getBindingVariable()
        return view

    }

    override fun onViewCreatedControl(view: View, savedInstanceState: Bundle?) {
        mFirstLoad = Date().time
        super.onViewCreatedControl(view, savedInstanceState)
    }

    fun getBindingVariable() = BR.viewModel

    private fun performDataBinding() {
        mBinding.setVariable(getBindingVariable(), mModel)
        mBinding.executePendingBindings()
    }
}