package ru.netfantazii.easy_notification_view.animation.bottomslide

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import com.daasuu.ei.Ease
import com.daasuu.ei.EasingInterpolator
import ru.netfantazii.easy_notification_view.EasyNotificationView
import ru.netfantazii.easy_notification_view.animation.base.DisappearAnimator

/** Plays slide out animation to the bottom of the container with given duration and interpolator.
 * @param durationMillis duration of the animation. Default is 300 millis
 * @param interpolator EasingInterpolator. Default is EasingInterpolator(Ease.CIRC_IN)*/
class BottomSlideDisappearAnimator(
    private val durationMillis: Long = 300,
    private val interpolator: EasingInterpolator = EasingInterpolator(Ease.CIRC_IN)
) : DisappearAnimator() {
    override fun createDisappearAnimator(
        overlay: View,
        contents: View,
        container: EasyNotificationView
    ): AnimatorSet {
        val contentsSlideInAnimator =
            ObjectAnimator.ofFloat(contents, "translationY", 0f)
                .apply {
                    duration = durationMillis
                    interpolator = this@BottomSlideDisappearAnimator.interpolator
                }

        val backgroundAppearAnimator = ObjectAnimator.ofFloat(overlay, "alpha", 0f)

        return AnimatorSet().apply {
            playTogether(contentsSlideInAnimator, backgroundAppearAnimator)
        }
    }
}