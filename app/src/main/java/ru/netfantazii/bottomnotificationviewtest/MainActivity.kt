package ru.netfantazii.bottomnotificationviewtest

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import ru.netfantazii.easy_notification_view.EasyNotificationCreator
import ru.netfantazii.easy_notification_view.animation.fade.FadeInAppearAnimator
import ru.netfantazii.easy_notification_view.animation.fade.FadeOutDisappearAnimator

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun show(view: View) {
        EasyNotificationCreator(this).apply {
            disappearAnimator = FadeOutDisappearAnimator()
            appearAnimator = FadeInAppearAnimator()
        }.apply {
            overlayColor = Color.TRANSPARENT
            isOverlayClickable = false
        }.create(R.layout.notification_layout_test2).show()
    }

    fun check(view: View) {
        val easeView = findViewById<View>(R.id.easy_notification_view)
        val exists = easeView != null
        Log.d(TAG, "check: $exists")
    }
}