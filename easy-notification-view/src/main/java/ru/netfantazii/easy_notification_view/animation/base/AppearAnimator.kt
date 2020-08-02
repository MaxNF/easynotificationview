package ru.netfantazii.easy_notification_view.animation.base

import android.animation.Animator
import android.view.View
import ru.netfantazii.easy_notification_view.EasyNotificationView

abstract class AppearAnimator {

    private var showing = false
    protected abstract fun createAppearAnimator(
        overlay: View,
        contents: View,
        container: EasyNotificationView
    ): Animator


    /** Change here parameters as you want them to be in the beginning of the animation. For example
     *  you can make some views invisible, change their alpha, position coordinates or layout
     *  parameters. EasyNotificationView inherits ConstraintLayout, so you can control any positions
     *  of the child views as you wish. By default EasyNotificationView becomes invisible after it's
     *  creation, but you can change it here.*/
    protected abstract fun setInitialState(
        overlay: View,
        contents: View,
        container: EasyNotificationView
    )

    internal fun applyInitialState(easyNotificationView: EasyNotificationView) {
        setInitialState(
            easyNotificationView.overlay,
            easyNotificationView.contents,
            easyNotificationView
        )
    }

    internal fun startAppearAnimation(easyNotificationView: EasyNotificationView) {
        if (!showing) {
            val animator = createAppearAnimator(
                easyNotificationView.overlay,
                easyNotificationView.contents,
                easyNotificationView
            )
            animator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationEnd(animation: Animator) {
                    showing = false
                }

                override fun onAnimationRepeat(animation: Animator) {}
                override fun onAnimationCancel(animation: Animator) {
                    showing = false
                }

                override fun onAnimationStart(animation: Animator) {
                    easyNotificationView.visibility = View.VISIBLE
                    showing = true
                }
            })
            animator.start()
        }
    }
}