package ru.netfantazii.easy_notification_view.animation.fade

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import com.daasuu.ei.Ease
import com.daasuu.ei.EasingInterpolator
import ru.netfantazii.easy_notification_view.EasyNotificationView
import ru.netfantazii.easy_notification_view.animation.base.AppearAnimator

class FadeInAppearAnimator(
    private val durationMillis: Long = 300,
    private val interpolator: EasingInterpolator = EasingInterpolator(Ease.LINEAR)
) : AppearAnimator() {
    override fun setInitialState(
        overlay: View,
        contents: View,
        container: EasyNotificationView
    ) {
        container.alpha = 0f
    }

    override fun createAppearAnimator(
        overlay: View,
        contents: View,
        container: EasyNotificationView
    ): Animator {
        return ObjectAnimator.ofFloat(container, "alpha", 1f).apply {
            duration = durationMillis
            interpolator = this@FadeInAppearAnimator.interpolator
        }
    }

}