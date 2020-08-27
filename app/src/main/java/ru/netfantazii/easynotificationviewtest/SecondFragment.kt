package ru.netfantazii.easynotificationviewtest

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_second.*
import leakcanary.AppWatcher
import ru.netfantazii.easynotificationview.EasyNotificationView

class SecondFragment : Fragment() {
    private val TAG = "Fragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        show_button.setOnClickListener(::showInFragment)
    }

    fun showInFragment(view: View) {
        EasyNotificationView.create(
            this,
            R.layout.layout_for_notification,
            Color.TRANSPARENT
        )
            .apply {
                isOverlayClickable = false
                disappearAnimationEndListener = {
                    AppWatcher.objectWatcher.watch(it, "detached notification")
                    Log.d(TAG, "animation ended")
                }
            }.show()
    }
}