package ru.netfantazii.easynotificationviewtest

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import ru.netfantazii.easynotificationview.EasyNotificationView
import ru.netfantazii.easynotificationview.animation.bottomslide.BottomSlideAppearAnimator
import ru.netfantazii.easynotificationview.animation.bottomslide.BottomSlideDisappearAnimator

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun show(view: View) {

        EasyNotificationView.create(
            this,
            R.layout.layout_for_notification,
            Color.TRANSPARENT,
            appearAnimator = BottomSlideAppearAnimator(5000),
            disappearAnimator = BottomSlideDisappearAnimator(5000)
        )
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

    fun showAutoHide(view: View) {
        EasyNotificationView.create(
            this,
            R.layout.layout_for_notification,
            Color.TRANSPARENT,
            disappearAnimator = BottomSlideDisappearAnimator(5000)
        )
            .apply {
                isOverlayClickable = false
                onButton1ClickListener = {
                    openSecondActivity(null)
                    hide()
                }
                appearAnimationEndListener = { Log.d(TAG, "showNoAnim: appear ends") }
                disappearAnimationEndListener = { Log.d(TAG, "showNoAnim: disappear ends") }
            }.showAutoHide(5000)
    }

    fun openSecondActivity(view: View?) {
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)
    }
}