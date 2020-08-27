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

package ru.netfantazii.easynotificationview

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import ru.netfantazii.easynotificationview.animation.base.AppearAnimator
import ru.netfantazii.easynotificationview.animation.base.DisappearAnimator
import ru.netfantazii.easynotificationview.animation.bottomslide.BottomSlideAppearAnimator
import ru.netfantazii.easynotificationview.animation.bottomslide.BottomSlideDisappearAnimator
import java.lang.IllegalArgumentException
import java.lang.UnsupportedOperationException

@SuppressLint("ViewConstructor")
class EasyNotificationView(
    private val activity: Activity,
    @LayoutRes private val contentsLayout: Int,
    @ColorInt private val overlayColor: Int,
    private val appearAnimator: AppearAnimator,
    private val disappearAnimator: DisappearAnimator
) : ConstraintLayout(activity) {
    constructor(
        fragment: Fragment,
        @LayoutRes contentsLayout: Int,
        @ColorInt overlayColor: Int,
        appearAnimator: AppearAnimator,
        disappearAnimator: DisappearAnimator
    ) : this(
        fragment.requireActivity(),
        contentsLayout,
        overlayColor,
        appearAnimator,
        disappearAnimator
    ) {
        this.fragment = fragment
        fragment.viewLifecycleOwner.lifecycle.addObserver(fragmentViewLifecycleObserver)
    }

    companion object {
        @JvmOverloads
                /** Creates EasyNotificationView instance.
                 * @param activity activity where you are going to show the notification
                 * @param overlayColor color of the background overlay view. Default is black with 70% opacity.
                 * @param appearAnimator animator which is capable of creating appear animation.
                 * @param disappearAnimator animator which is capable of creating disappear animation.*/
        fun create(
            activity: Activity,
            @LayoutRes layoutResId: Int,
            @ColorInt overlayColor: Int? = null,
            appearAnimator: AppearAnimator? = null,
            disappearAnimator: DisappearAnimator? = null
        ): EasyNotificationView {
            val easyNotificationView =
                EasyNotificationView(
                    activity,
                    layoutResId,
                    overlayColor ?: ContextCompat.getColor(activity, R.color.overlay_color),
                    appearAnimator ?: BottomSlideAppearAnimator(),
                    disappearAnimator ?: BottomSlideDisappearAnimator()
                )
            easyNotificationView.id = R.id.easy_notification_view
            return easyNotificationView
        }

        /** Creates EasyNotificationView instance.
         * @param fragment fragment where you are going to show the notification
         * @param overlayColor color of the background overlay view. Default is black with 70% opacity.
         * @param appearAnimator animator which is capable of creating appear animation.
         * @param disappearAnimator animator which is capable of creating disappear animation.*/
        fun create(
            fragment: Fragment,
            @LayoutRes layoutResId: Int,
            @ColorInt overlayColor: Int? = null,
            appearAnimator: AppearAnimator? = null,
            disappearAnimator: DisappearAnimator? = null
        ): EasyNotificationView {
            if (fragment.activity == null) {
                throw IllegalArgumentException("The fragment is not attached to an activity.")
            }
            val easyNotificationView =
                EasyNotificationView(
                    fragment,
                    layoutResId,
                    overlayColor ?: ContextCompat.getColor(
                        fragment.requireContext(),
                        R.color.overlay_color
                    ),
                    appearAnimator ?: BottomSlideAppearAnimator(),
                    disappearAnimator ?: BottomSlideDisappearAnimator()
                )
            easyNotificationView.id = R.id.easy_notification_view
            return easyNotificationView
        }
    }

    private var fragment: Fragment? = null
    private val fragmentViewLifecycleObserver = object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun removeNotification() {
            container?.removeView(this@EasyNotificationView)
            fragment?.viewLifecycleOwner?.lifecycle?.removeObserver(this)
        }
    }

    internal var container: ViewGroup? = null
    internal lateinit var overlay: FrameLayout
    internal lateinit var contents: View

    private var timer: CountDownTimer? = null

    private var button1: View? = null
    private var button2: View? = null
    private var button3: View? = null
    private var button4: View? = null
    private var button5: View? = null
    private var button6: View? = null
    private var button7: View? = null
    private var button8: View? = null
    private var button9: View? = null

    private var onBackPressedCallback: OnBackPressedCallback? =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                hide()
            }
        }
        set(value) {
            field?.remove()
            field = value
        }

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

    var appearAnimationEndListener: ((easyNotificationView: EasyNotificationView) -> Unit)? = null

    var disappearAnimationEndListener: ((easyNotificationView: EasyNotificationView) -> Unit)? =
        null

    /** Defines on back button press behavior while the notification is visible. Default is hide().
     * This action invokes only if the parent activity is the AppCompatActivity class. If the context
     * is the lifecycle owner it will be connected with this callback ([<a href=https://developer.android.com/guide/navigation/navigation-custom-back>more information here</a>]).
     * If requirements are not met, you can override on back press behavior manually.
     * To disable the callback pass null*/
    fun setOnBackPressAction(action: (() -> Unit)?) {
        if (action == null) {
            onBackPressedCallback = null
            return
        }
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                action.invoke()
            }
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
    @JvmOverloads
    fun show(containerForNotification: ViewGroup? = null, skipAnimation: Boolean = false) {
        attachToContainer(containerForNotification)
        post {
            appearAnimator.startAppearAnimation(this, skipAnimation, appearAnimationEndListener)
        }
        overrideBackButtonBehavior()
    }

    /** Attaches the notification view to the specified container or to the context's root ViewGroup
     * (if the container is not specified) and shows the notification. The notification will be
     * automatically hidden when the time is over.*/
    @JvmOverloads
    fun showAutoHide(
        timeToLiveMillis: Long,
        containerForNotification: ViewGroup? = null,
        skipStartingAnimation: Boolean = false,
        skipHidingAnimation: Boolean = false
    ) {
        if (timeToLiveMillis <= 0) {
            throw UnsupportedOperationException("timeToLiveMillis should be greater than zero.")
        }
        show(containerForNotification, skipStartingAnimation)
        startTimer(timeToLiveMillis, skipHidingAnimation)
    }

    private fun attachToContainer(containerForNotification: ViewGroup? = null) {
        container = containerForNotification ?: getRootContainerView()
        container?.let {
            val params =
                ViewGroup.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT
                )
            it.addView(this, params)
            return
        }
        throw IllegalArgumentException("Failed to identify the container view. Try to specify it explicitly in the show() method.")
    }

    private fun getRootContainerView(): ViewGroup? {
        val view = fragment?.view ?: activity.findViewById<ViewGroup>(android.R.id.content)
        return view as? ViewGroup
    }

    private fun overrideBackButtonBehavior() {
        val appCompatActivity: AppCompatActivity? = activity as? AppCompatActivity
        appCompatActivity?.let {
            val lifecycleOwner: LifecycleOwner? =
                fragment?.viewLifecycleOwner ?: (activity as? LifecycleOwner)
            onBackPressedCallback?.let { callback ->
                if (lifecycleOwner != null) {
                    it.onBackPressedDispatcher.addCallback(lifecycleOwner, callback)
                } else {
                    it.onBackPressedDispatcher.addCallback(callback)
                }
            }
        }
    }

    private fun startTimer(timeToLiveMillis: Long, skipAnimation: Boolean) {
        timer = object : CountDownTimer(timeToLiveMillis, timeToLiveMillis) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                hide(skipAnimation)
            }
        }
        timer?.start()
    }

    /** Hides the notification and removes it's view from the parent container at the end of the animation.*/
    @JvmOverloads
    fun hide(skipAnimation: Boolean = false) {
        post {
            disappearAnimator.startDisappearAnimation(
                this,
                skipAnimation,
                disappearAnimationEndListener
            )
        }
        onBackPressedCallback?.remove()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        timer?.cancel()
        cancelAnimations()
        removeViewLifecycleOwnerFromTheFragment()
        onBackPressedCallback?.remove()
        fragment = null
        container = null
    }

    private fun removeViewLifecycleOwnerFromTheFragment() {
        fragment?.viewLifecycleOwner?.lifecycle?.removeObserver(fragmentViewLifecycleObserver)
    }

    private fun cancelAnimations() {
        appearAnimator.cancelAnimation()
        disappearAnimator.cancelAnimation()
    }
}