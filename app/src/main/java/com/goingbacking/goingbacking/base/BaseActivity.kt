package com.goingbacking.goingbacking.base

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.utils.GoingBackingActivity


abstract class BaseActivity<VD: ViewDataBinding>(
    @LayoutRes protected val layoutResId: Int
) : AppCompatActivity(), GoingBackingActivity {

    protected val viewDataBinding: VD by lazy {
        DataBindingUtil.setContentView(this, layoutResId)
    }

    private val _loadingDialog: Dialog by lazy {
        Dialog(this, R.style.NewDialog).apply {
            setContentView(R.layout.activity_error) // R.layout.view_loading 으로 변경
            setCancelable(false)
            create()
        }
    }

    init {

        addOnContextAvailableListener {
            viewDataBinding.notifyChange()
        }
    }

    @StyleRes
    protected open val themeResId: Int = -1




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(themeResId)
        viewDataBinding.lifecycleOwner = this@BaseActivity
    }


    override fun onResume() {
        super.onResume()
    }

    fun loadingDialog(isShow: Boolean) {
        if(isShow) {
            if (!_loadingDialog.isShowing) _loadingDialog.show()
        } else {
            _loadingDialog.hide()
        }
    }

    override fun onDestroy() {
        _loadingDialog.dismiss()
        viewDataBinding.unbind()
        super.onDestroy()
    }

}