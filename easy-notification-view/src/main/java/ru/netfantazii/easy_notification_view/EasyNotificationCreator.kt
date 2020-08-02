package ru.netfantazii.easy_notification_view

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import ru.netfantazii.easy_notification_view.animation.BottomSlideAppearAnimator
import ru.netfantazii.easy_notification_view.animation.BottomSlideDisappearAnimator
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
                listener,
                appearAnimator ?: BottomSlideAppearAnimator(),
                disappearAnimator ?: BottomSlideDisappearAnimator()
            )
        easyNotificationView.id = R.id.easy_notification_view
        return easyNotificationView
    }
}