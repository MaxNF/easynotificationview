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
            appearAnimator = BottomSlideAppearAnimator(100),
            disappearAnimator = BottomSlideDisappearAnimator()
        )
        notification.isOverlayClickable = false
        notification.onOverlayClickListener = {
            Log.d(TAG, "onCreate: OVERLAY CLICKED")
            notification.hide()
        }
        notification.apply {
            onButton1ClickListener = null
            onButton2ClickListener = { Log.d(TAG, "onCreate: BUTTON2") }
            onButton3ClickListener = { Log.d(TAG, "onCreate: BUTTON3") }
            onButton5ClickListener = {
                notification.onButton4ClickListener = {
                    Log.d(
                        TAG,
                        "onCreate: BUTTON4"
                    )
                }
            }
            onButton6ClickListener = { notification.onButton4ClickListener = null }
            onButton7ClickListener = { Log.d(TAG, "onCreate: BUTTON7") }
            onButton8ClickListener = {
                notification.onButton1ClickListener = {
                    Log.d(TAG, "onCreate: BUTTON1")
                }
            }
            onButton9ClickListener = { notification.onButton1ClickListener = null }
        }
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