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
    private var originalContentsY = 0f
    private var originalOverlayAlpha = 0f

    override fun resetState(overlay: View, contents: View, container: EasyNotificationView) {
        contents.y = originalContentsY
        overlay.alpha = originalOverlayAlpha
    }

    override fun createDisappearAnimator(
        overlay: View,
        contents: View,
        container: EasyNotificationView
    ): AnimatorSet {
        originalContentsY = contents.y
        originalOverlayAlpha = overlay.alpha
        val contentsSlideInAnimator =
            ObjectAnimator.ofFloat(contents, "y", container.height.toFloat())
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