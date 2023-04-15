package com.goingbacking.goingbacking.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.goingbacking.goingbacking.utils.GoingBackingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class ViewModelActivity<VD: ViewDataBinding, VM: BaseViewModel>(
    @LayoutRes protected val layoutResId: Int,
) : AppCompatActivity(), GoingBackingActivity {

    protected abstract val viewModel: VM

    protected open val isFinishWhenNotFound: Boolean = true

    protected val viewDataBinding: VD by lazy {
        DataBindingUtil.setContentView(this, layoutResId)
    }

    val insetsController by lazy {
        WindowCompat.getInsetsController(window, window.decorView)
    }

    @StyleRes
    protected open val themeResId: Int = -1

    init {
//        addOnContextAvailableListener {
//            viewDataBinding.notifyChange()
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(themeResId)
        viewDataBinding.apply {
            lifecycleOwner = this@ViewModelActivity
            setVariable(6, viewModel)
        }

        handleCommonError(isFinishWhenNotFound)
    }

    open fun handleCommonError(finishWhenNotFound: Boolean = true) {

    }

    override fun onDestroy() {
        viewDataBinding.unbind()
        super.onDestroy()
    }
}