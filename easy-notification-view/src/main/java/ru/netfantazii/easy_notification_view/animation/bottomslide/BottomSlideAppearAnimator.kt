package ru.netfantazii.easy_notification_view.animation.bottomslide

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import com.daasuu.ei.Ease
import com.daasuu.ei.EasingInterpolator
import ru.netfantazii.easy_notification_view.EasyNotificationView
import ru.netfantazii.easy_notification_view.R
import ru.netfantazii.easy_notification_view.animation.base.AppearAnimator

/** Plays slide in animation from the bottom of the container with given duration and interpolator.
 * @param durationMillis duration of the animation. Default is 300 millis
 * @param interpolator EasingInterpolator. Default is EasingInterpolator(Ease.CIRC_OUT)*/
class BottomSlideAppearAnimator(
    private val durationMillis: Long = 300,
    private val interpolator: EasingInterpolator = EasingInterpolator(Ease.CIRC_OUT)
) : AppearAnimator() {

    override fun setInitialState(overlay: View, contents: View, container: EasyNotificationView) {
        overlay.alpha = 0f
        val set = ConstraintSet().apply { clone(container) }
        set.connect(
            R.id.bnv_contents,
            ConstraintSet.TOP,
            R.id.easy_notification_view,
            ConstraintSet.BOTTOM
        )
        set.applyTo(container)
    }

    override fun createAppearAnimator(
        overlay: View,
        contents: View,
        container: EasyNotificationView
    ): Animator {
        val params = contents.layoutParams as ViewGroup.MarginLayoutParams
        val targetOffset = (contents.height + params.bottomMargin + params.topMargin)
        val contentsSlideInAnimator =
            ObjectAnimator.ofFloat(contents, "translationY", -targetOffset.toFloat())
                .apply {
                    duration = durationMillis
                    interpolator = this@BottomSlideAppearAnimator.interpolator
                }

        val backgroundAppearAnimator = ObjectAnimator.ofFloat(overlay, "alpha", 1f)

        return AnimatorSet().apply {
            playTogether(contentsSlideInAnimator, backgroundAppearAnimator)
        }
    }
}