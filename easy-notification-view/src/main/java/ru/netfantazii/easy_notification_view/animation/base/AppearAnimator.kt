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

    /** Function responsible for creating appear animation.*/
    internal fun startAppearAnimation(easyNotificationView: EasyNotificationView) {
        if (!showing) {
            setInitialState(
                easyNotificationView.overlay,
                easyNotificationView.contents,
                easyNotificationView
            )

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