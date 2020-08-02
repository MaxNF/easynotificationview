package ru.netfantazii.bottomnotificationviewtest

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import ru.netfantazii.easy_notification_view.EasyNotificationCreator
import ru.netfantazii.easy_notification_view.EasyNotificationView
import ru.netfantazii.easy_notification_view.animation.base.AppearAnimator
import ru.netfantazii.easy_notification_view.animation.base.DisappearAnimator

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    val fadeInAnimation = object : AppearAnimator() {

        override fun setInitialState(
            overlay: View,
            contents: View,
            container: EasyNotificationView
        ) {
            overlay.alpha = 0f
            contents.alpha = 0f
        }

        override fun createAppearAnimator(
            overlay: View,
            contents: View,
            container: EasyNotificationView
        ): AnimatorSet {
            val contentsAnimator = ObjectAnimator.ofFloat(contents, "alpha", 1f)
            val overlayAnimator = ObjectAnimator.ofFloat(overlay, "alpha", 1f)
            return AnimatorSet().apply {
                playTogether(contentsAnimator, overlayAnimator)
            }
        }
    }
    val fadeOutAnimation = object : DisappearAnimator() {
        override fun createDisappearAnimator(
            overlay: View,
            contents: View,
            container: EasyNotificationView
        ): AnimatorSet {
            val contentsAnimator = ObjectAnimator.ofFloat(contents, "alpha", 0f)
            val overlayAnimator = ObjectAnimator.ofFloat(overlay, "alpha", 0f)
            return AnimatorSet().apply {
                playTogether(contentsAnimator, overlayAnimator)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun show(view: View) {
        EasyNotificationCreator(this).apply {
            appearAnimator = fadeInAnimation
            disappearAnimator = fadeOutAnimation
        }.create(R.layout.notification_layout_test2).show()
    }
}