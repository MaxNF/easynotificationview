/*MIT License

Copyright (c) 2020 Maxim Bagaley

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.*/

package ru.netfantazii.easynotificationview.animation.bottomslide

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import com.daasuu.ei.Ease
import com.daasuu.ei.EasingInterpolator
import ru.netfantazii.easynotificationview.EasyNotificationView
import ru.netfantazii.easynotificationview.R
import ru.netfantazii.easynotificationview.animation.base.AppearAnimator

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
            R.id.env_contents,
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
            ObjectAnimator.ofFloat(contents, "translationY", 0f, -targetOffset.toFloat())
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