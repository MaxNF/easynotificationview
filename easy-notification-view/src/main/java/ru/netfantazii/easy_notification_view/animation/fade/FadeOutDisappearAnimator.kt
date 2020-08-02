package ru.netfantazii.easy_notification_view.animation.fade

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import com.daasuu.ei.Ease
import com.daasuu.ei.EasingInterpolator
import ru.netfantazii.easy_notification_view.EasyNotificationView
import ru.netfantazii.easy_notification_view.animation.base.DisappearAnimator

class FadeOutDisappearAnimator(
    private val durationMillis: Long = 300,
    private val interpolator: EasingInterpolator = EasingInterpolator(Ease.LINEAR)
) : DisappearAnimator() {
    override fun createDisappearAnimator(
        overlay: View,
        contents: View,
        container: EasyNotificationView
    ): Animator {
        return ObjectAnimator.ofFloat(container, "alpha", 0f).apply {
            duration = durationMillis
            interpolator = this@FadeOutDisappearAnimator.interpolator
        }
    }
}