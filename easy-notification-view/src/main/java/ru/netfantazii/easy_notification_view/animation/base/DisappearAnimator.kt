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

package ru.netfantazii.easy_notification_view.animation.base

import android.animation.Animator
import android.view.View
import ru.netfantazii.easy_notification_view.EasyNotificationView

abstract class DisappearAnimator {
    protected abstract fun createDisappearAnimator(
        overlay: View,
        contents: View,
        container: EasyNotificationView
    ): Animator

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