package ru.netfantazii.easynotificationviewtest

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import ru.netfantazii.easynotificationview.EasyNotificationView

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun show(view: View) {

        EasyNotificationView.create(this, R.layout.layout_for_notification, Color.TRANSPARENT)
            .apply {
                isOverlayClickable = false
                appearAnimationEndListener = { Log.d(TAG, "show: appear ends") }
                disappearAnimationEndListener = { Log.d(TAG, "show: disappear ends") }
                setOnBackPressAction {
                    Log.d(TAG, "show: КНОПКА НАЗАД НАЖАТА")
                    hide(true)
                }
            }.show()
    }

    fun showNoAnim(view: View) {
        EasyNotificationView.create(this, R.layout.layout_for_notification, Color.TRANSPARENT)
            .apply {
                isOverlayClickable = false
                appearAnimationEndListener = { Log.d(TAG, "showNoAnim: appear ends") }
                disappearAnimationEndListener = { Log.d(TAG, "showNoAnim: disappear ends") }
            }.show(skipAnimation = true)
    }

    fun check(view: View) {
        val easeView = findViewById<View>(R.id.easy_notification_view)
        val exists = easeView != null
        Log.d(TAG, "check: $exists")
    }

    fun onOverlayClickFunction() {

    }

    fun greatFunction() {

    }

    fun anotherGreatFunction() {

    }
}