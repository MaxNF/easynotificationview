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

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ru.netfantazii.easy_notification_view.animation.base.AppearAnimator
import ru.netfantazii.easy_notification_view.animation.base.DisappearAnimator
import ru.netfantazii.easy_notification_view.animation.bottomslide.BottomSlideAppearAnimator
import ru.netfantazii.easy_notification_view.animation.bottomslide.BottomSlideDisappearAnimator
import java.lang.IllegalArgumentException

@SuppressLint("ViewConstructor")
class EasyNotificationView(
    context: Context,
    @LayoutRes private val contentsLayout: Int,
    @ColorInt private val overlayColor: Int,
    private val appearAnimator: AppearAnimator,
    private val disappearAnimator: DisappearAnimator
) : ConstraintLayout(context) {
    companion object {
        @JvmOverloads
                /** Creates EasyNotificationView instance.
                 * @param context Do not use application context if you are not going to explicitly specify
                 * root view in EasyNotificationView.show() method. In this case use activity of fragment
                 * context.
                 * @param overlayColor color of the background overlay view. Default is black with 70% opacity.
                 * @param appearAnimator animator which is capable of creating appear animation.
                 * @param disappearAnimator animator which is capable of creating disappear animation.*/
        fun create(
            context: Context,
            @LayoutRes layoutResId: Int,
            @ColorInt overlayColor: Int? = null,
            appearAnimator: AppearAnimator? = null,
            disappearAnimator: DisappearAnimator? = null
        ): EasyNotificationView {
            val easyNotificationView =
                EasyNotificationView(
                    context,
                    layoutResId,
                    overlayColor ?: ContextCompat.getColor(context, R.color.overlay_color),
                    appearAnimator ?: BottomSlideAppearAnimator(),
                    disappearAnimator ?: BottomSlideDisappearAnimator()
                )
            easyNotificationView.id = R.id.easy_notification_view
            return easyNotificationView
        }
    }

    internal var container: ViewGroup? = null
    internal lateinit var overlay: FrameLayout
    internal lateinit var contents: View

    private var button1: View? = null
    private var button2: View? = null
    private var button3: View? = null
    private var button4: View? = null
    private var button5: View? = null
    private var button6: View? = null
    private var button7: View? = null
    private var button8: View? = null
    private var button9: View? = null

    /** Defines is the overlay clickable or not. If false, it will propagate clicks to underlying views.
     * Default is true. If the overlay listener is set, this behaves as "true" regardless of the actual value.*/
    var isOverlayClickable = true
        set(value) {
            field = value
            overlay.isClickable = value
        }

    var onOverlayClickListener: (() -> Unit)? = null
        set(value) {
            field = value
            value?.let { action ->
                overlay.setOnClickListener { action() }
            }
        }

    /** Defines env_button1 click behavior. Default is hide()*/
    var onButton1ClickListener: (() -> Unit)? = ::defaultOnEveryButtonClickBehavior
        set(value) {
            field = value
            button1?.setOnClickListener { value?.invoke() }
        }

    /** Defines env_button2 click behavior. Default is hide()*/
    var onButton2ClickListener: (() -> Unit)? = ::defaultOnEveryButtonClickBehavior
        set(value) {
            field = value
            button2?.setOnClickListener { value?.invoke() }
        }

    /** Defines env_button3 click behavior. Default is hide()*/
    var onButton3ClickListener: (() -> Unit)? = ::defaultOnEveryButtonClickBehavior
        set(value) {
            field = value
            button3?.setOnClickListener { value?.invoke() }
        }

    /** Defines env_button4 click behavior. Default is hide()*/
    var onButton4ClickListener: (() -> Unit)? = ::defaultOnEveryButtonClickBehavior
        set(value) {
            field = value
            button4?.setOnClickListener { value?.invoke() }
        }

    /** Defines env_button5 click behavior. Default is hide()*/
    var onButton5ClickListener: (() -> Unit)? = ::defaultOnEveryButtonClickBehavior
        set(value) {
            field = value
            button5?.setOnClickListener { value?.invoke() }
        }

    /** Defines env_button6 click behavior. Default is hide()*/
    var onButton6ClickListener: (() -> Unit)? = ::defaultOnEveryButtonClickBehavior
        set(value) {
            field = value
            button6?.setOnClickListener { value?.invoke() }
        }

    /** Defines env_button7 click behavior. Default is hide()*/
    var onButton7ClickListener: (() -> Unit)? = ::defaultOnEveryButtonClickBehavior
        set(value) {
            field = value
            button7?.setOnClickListener { value?.invoke() }
        }

    /** Defines env_button8 click behavior. Default is hide()*/
    var onButton8ClickListener: (() -> Unit)? = ::defaultOnEveryButtonClickBehavior
        set(value) {
            field = value
            button8?.setOnClickListener { value?.invoke() }
        }

    /** Defines env_button9 click behavior. Default is hide()*/
    var onButton9ClickListener: (() -> Unit)? = ::defaultOnEveryButtonClickBehavior
        set(value) {
            field = value
            button9?.setOnClickListener { value?.invoke() }
        }

    init {
        inflate(context)
    }

    private fun inflate(context: Context) {
        attachOverlay(context)
        attachContents(context)
        assignChildViews()
        setDefaultButtonListeners()
        setInitialState()
    }

    private fun attachOverlay(context: Context) {
        overlay = FrameLayout(context).apply {
            id = R.id.env_overlay
            setBackgroundColor(overlayColor)
            isClickable = isOverlayClickable
        }
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(overlay, params)
    }

    private fun attachContents(context: Context) {
        val inflater = LayoutInflater.from(context)
        contents = inflater.inflate(this.contentsLayout, this, false).apply {
            id = R.id.env_contents
            isClickable = true
        }
        addView(contents)
    }

    private fun assignChildViews() {
        button1 = findViewById(R.id.env_button1)
        button2 = findViewById(R.id.env_button2)
        button3 = findViewById(R.id.env_button3)
        button4 = findViewById(R.id.env_button4)
        button5 = findViewById(R.id.env_button5)
        button6 = findViewById(R.id.env_button6)
        button7 = findViewById(R.id.env_button7)
        button8 = findViewById(R.id.env_button8)
        button9 = findViewById(R.id.env_button9)
    }

    private fun setDefaultButtonListeners() {
        button1?.setOnClickListener { onButton1ClickListener?.invoke() }
        button2?.setOnClickListener { onButton2ClickListener?.invoke() }
        button3?.setOnClickListener { onButton3ClickListener?.invoke() }
        button4?.setOnClickListener { onButton4ClickListener?.invoke() }
        button5?.setOnClickListener { onButton5ClickListener?.invoke() }
        button6?.setOnClickListener { onButton6ClickListener?.invoke() }
        button7?.setOnClickListener { onButton7ClickListener?.invoke() }
        button8?.setOnClickListener { onButton8ClickListener?.invoke() }
        button9?.setOnClickListener { onButton9ClickListener?.invoke() }
    }

    private fun defaultOnEveryButtonClickBehavior() {
        hide()
    }

    private fun setInitialState() {
        visibility = View.INVISIBLE
    }

    /** Attaches the notification view to the specified container or to the context's root ViewGroup
     * (if the container is not specified) and shows the notification.*/
    fun show(containerForNotification: ViewGroup? = null) {
        container = containerForNotification ?: getContainerView()
        val params =
            ViewGroup.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
        container?.addView(this, params)
        post {
            appearAnimator.startAppearAnimation(this)
        }
    }

    private fun getContainerView(): ViewGroup {
        val attachedContext = context
        return when (attachedContext) {
            is Fragment -> {
                attachedContext.requireActivity().findViewById(android.R.id.content)
            }
            is Activity -> {
                attachedContext.findViewById(android.R.id.content)
            }
            else -> throw IllegalArgumentException("Please specify the container view or provide EasyNotification.create() method with a valid context (should be either a Fragment or an Activity)")
        }
    }

    /** Hides the notification and removes it's view from the parent container at the end of the animation.*/
    fun hide() {
        post {
            disappearAnimator.startDisappearAnimation(this)
        }
    }
}