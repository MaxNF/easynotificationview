package ru.netfantazii.easynotificationviewtest

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import ru.netfantazii.easy_notification_view.EasyNotificationView
import ru.netfantazii.easy_notification_view.animation.bottomslide.BottomSlideAppearAnimator
import ru.netfantazii.easy_notification_view.animation.bottomslide.BottomSlideDisappearAnimator
import ru.netfantazii.easy_notification_view.animation.fade.FadeInAppearAnimator
import ru.netfantazii.easy_notification_view.animation.fade.FadeOutDisappearAnimator

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var notification: EasyNotificationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notification = EasyNotificationView.create(
            this,
            R.layout.notification_layout_test2,
            appearAnimator = BottomSlideAppearAnimator(),
            disappearAnimator = BottomSlideDisappearAnimator()
        )
    }

    fun show(view: View) {
        notification.show()
    }

    fun check(view: View) {
        val easeView = findViewById<View>(R.id.easy_notification_view)
        val exists = easeView != null
        Log.d(TAG, "check: $exists")
    }
}