package com.lobo.repogit.core.platform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.lobo.repogit.R
import com.lobo.repogit.core.helpers.Preferences
import com.lobo.repogit.core.helpers.ResourceHelper
import com.lobo.repogit.data.exception.ApiException
import com.lobo.repogit.data.exception.GeneralException
import retrofit2.HttpException

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    lateinit var binding: T

    lateinit var preferences: Preferences

    lateinit var resourceHelper: ResourceHelper

    protected abstract fun getContentLayoutId(): Int

    protected abstract fun initBinding()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getContentLayoutId(), container, false)
        initBinding()
        return binding.root
    }

    fun handleError(error: Throwable) {
        val message: String = when (error) {
            is HttpException -> getString(R.string.api_general_error)
            is GeneralException, is ApiException -> error.message ?: ""
            else -> getString(R.string.api_general_error)
        }
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}