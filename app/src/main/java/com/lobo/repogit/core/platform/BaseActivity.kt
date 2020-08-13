package com.lobo.repogit.core.platform

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ViewDataBinding
import com.lobo.repogit.R
import com.lobo.repogit.core.helpers.Preferences
import com.lobo.repogit.core.helpers.ResourceHelper
import com.lobo.repogit.data.exception.ApiException
import com.lobo.repogit.data.exception.GeneralException
import org.koin.android.ext.android.inject
import retrofit2.HttpException

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    val preferences: Preferences by inject()

    val resourceHelper: ResourceHelper by inject()

    lateinit var binding: T

    protected abstract fun getContentLayoutId(): Int

    abstract fun init()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getContentLayoutId())

        init()
    }

    fun configureViewModel(viewModel: BaseViewModel) {
        viewModel.showLoading.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                if (viewModel.isLoading()) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        })
    }

    fun showLoading() {
//        val loading = ProgressDialog.getInstance()
//        FragmentDialogUtils.showDialog(this, loading as ProgressDialog, "SHOW_LOADING")
    }

    fun hideLoading() {
//        val loading = ProgressDialog.getInstance()
//        loading?.dismiss()
    }

    fun handleError(error: Throwable) {
        val message: String = when (error) {
            is HttpException -> getString(R.string.api_general_error)
            is GeneralException, is ApiException -> error.message ?: ""
            else -> getString(R.string.api_general_error)
        }
        Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
    }
}