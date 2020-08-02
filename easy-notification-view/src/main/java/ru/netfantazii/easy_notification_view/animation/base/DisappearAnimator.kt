package ru.netfantazii.easy_notification_view.animation.base

import android.animation.Animator
import android.animation.AnimatorSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import ru.netfantazii.easy_notification_view.EasyNotificationView
import ru.netfantazii.easy_notification_view.R

abstract class DisappearAnimator {
    protected abstract fun createDisappearAnimator(
        overlay: View,
        contents: View,
        container: EasyNotificationView
    ): AnimatorSet

    private var hiding = false


    internal fun startDisappearAnimation(easyNotificationView: EasyNotificationView) {
        if (!hiding) {
            val animator = createDisappearAnimator(
                easyNotificationView.overlay,
                easyNotificationView.contents,
                easyNotificationView
            )
            animator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationEnd(animation: Animator) {
                    easyNotificationView.visibility = View.INVISIBLE
                    hiding = false
                    removeViewFromContainer(easyNotificationView)
                }

                override fun onAnimationRepeat(animation: Animator) {}
                override fun onAnimationCancel(animation: Animator) {
                    hiding = false
                    removeViewFromContainer(easyNotificationView)
                }

                override fun onAnimationStart(animation: Animator) {
                    hiding = true
                }
            })
            animator.start()
        }
    }

    private fun removeViewFromContainer(easyNotificationView: EasyNotificationView) {
        easyNotificationView.container?.removeView(easyNotificationView)
    }
}