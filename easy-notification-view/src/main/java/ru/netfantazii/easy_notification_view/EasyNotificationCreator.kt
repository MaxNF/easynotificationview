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

package ru.netfantazii.easy_notification_view

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import ru.netfantazii.easy_notification_view.animation.bottomslide.BottomSlideAppearAnimator
import ru.netfantazii.easy_notification_view.animation.bottomslide.BottomSlideDisappearAnimator
import ru.netfantazii.easy_notification_view.animation.base.AppearAnimator
import ru.netfantazii.easy_notification_view.animation.base.DisappearAnimator

class EasyNotificationCreator(
    private val context: Context
) {
    /** Default is black with 70% opacity*/
    @ColorInt
    var overlayColor: Int? = null

    /** Default is true*/
    var isOverlayClickable: Boolean = true

    /** Default is hiding notification on every button click*/
    var listener: EasyNotificationView.ButtonsClickListener? = null

    /** Default is Bottom Slide In animation*/
    var appearAnimator: AppearAnimator? = null

    /** Default is Bottom Slide Out animation*/
    var disappearAnimator: DisappearAnimator? = null

    /** Creates an EasyNotificationView with the specified layout and settings.
     * @param layoutResId id of the layout for a notification. Clickable views in this layout must have
     * following ids: bnv_button1, bnv_button2, ... bnv_button9. */
    fun create(@LayoutRes layoutResId: Int): EasyNotificationView {
        val easyNotificationView =
            EasyNotificationView(
                context,
                layoutResId,
                overlayColor ?: ContextCompat.getColor(context, R.color.overlay_color),
                isOverlayClickable,
                listener,
                appearAnimator ?: BottomSlideAppearAnimator(),
                disappearAnimator ?: BottomSlideDisappearAnimator()
            )
        easyNotificationView.id = R.id.easy_notification_view
        return easyNotificationView
    }
}