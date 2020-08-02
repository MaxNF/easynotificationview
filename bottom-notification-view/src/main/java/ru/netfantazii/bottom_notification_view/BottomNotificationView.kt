package ru.netfantazii.bottom_notification_view

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.MarginLayoutParamsCompat
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import com.daasuu.ei.Ease
import com.daasuu.ei.EasingInterpolator
import java.lang.IllegalArgumentException

class BottomNotificationView(
    context: Context,
    @LayoutRes private val contentsLayout: Int,
    @ColorInt private val overlayColor: Int,
    private val buttonsClickListener: ButtonsClickListener?
) : ConstraintLayout(context) {
    companion object {
        @JvmOverloads
        fun show(
            context: Context,
            @LayoutRes layout: Int,
            @ColorRes overlayColor: Int = R.color.overlay_color,
            buttonsClickListener: ButtonsClickListener? = null,
            forceCreate: Boolean = false
        ): BottomNotificationView {
            val resolvedColor = ContextCompat.getColor(context, overlayColor)
            val view =
                createAndAttach(context, layout, resolvedColor, buttonsClickListener, forceCreate)
            view.show()
            return view
        }

        private fun createAndAttach(
            context: Context,
            @LayoutRes layout: Int,
            @ColorInt overlayColor: Int,
            buttonsClickListener: ButtonsClickListener?,
            forceCreate: Boolean
        ): BottomNotificationView {
            return when (context) {
                is Fragment -> {
                    val activity = context.requireActivity()
                    createForActivity(
                        activity,
                        layout,
                        overlayColor,
                        buttonsClickListener,
                        forceCreate
                    )
                }
                is Activity -> {
                    createForActivity(
                        context,
                        layout,
                        overlayColor,
                        buttonsClickListener,
                        forceCreate
                    )
                }
                else -> throw IllegalArgumentException("Context should be either a Fragment or an Activity.")
            }
        }

        private fun createForActivity(
            activity: Activity,
            @LayoutRes layout: Int,
            @ColorInt overlayColor: Int,
            buttonsClickListener: ButtonsClickListener?,
            forceCreate: Boolean
        ): BottomNotificationView {
            return if (!forceCreate && isCreated(activity)) {
                getFromActivity(activity)
            } else {
                val bottomNotificationView =
                    BottomNotificationView(activity, layout, overlayColor, buttonsClickListener)
                bottomNotificationView.id = R.id.bottom_notification_view
                val root = activity.findViewById<ViewGroup>(android.R.id.content)
                val params =
                    ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
                root.addView(bottomNotificationView, params)
                bottomNotificationView
            }
        }

        private fun isCreated(activity: Activity): Boolean {
            val view = activity.findViewById<View>(R.id.bottom_notification_view)
            return view != null && view is BottomNotificationView
        }

        private fun getFromActivity(activity: Activity): BottomNotificationView =
            activity.findViewById(R.id.bottom_notification_view)
    }

    private val TAG = "BottomNotificationView"

    private lateinit var overlay: FrameLayout
    private lateinit var contents: View

    private var button1: View? = null
    private var button2: View? = null
    private var button3: View? = null
    private var button4: View? = null
    private var button5: View? = null
    private var button6: View? = null
    private var button7: View? = null
    private var button8: View? = null
    private var button9: View? = null

    private var showing = false
    private var hiding = false

    init {
        Log.d(TAG, "init: ")
        inflate(context)
    }

    private fun inflate(context: Context) {
        attachOverlay(context)
        attachContents(context)
        applyConstraints()
        assignChildViews()
        setListeners()
        setInitialState()
    }

    private fun attachOverlay(context: Context) {
        overlay = FrameLayout(context).apply {
            id = R.id.bnv_overlay
            setBackgroundColor(overlayColor)
        }
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(overlay, params)
    }

    private fun attachContents(context: Context) {
        val inflater = LayoutInflater.from(context)
        contents = inflater.inflate(this.contentsLayout, this, false).apply {
            id = R.id.bnv_contents
            isClickable = true
        }
        addView(contents)
    }

    private fun applyConstraints() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.connect(
            R.id.bnv_contents,
            ConstraintSet.TOP,
            R.id.bottom_notification_view,
            ConstraintSet.BOTTOM
        )
        constraintSet.applyTo(this)
    }

    private fun assignChildViews() {
        button1 = findViewById(R.id.bnv_button1)
        button2 = findViewById(R.id.bnv_button2)
        button3 = findViewById(R.id.bnv_button3)
        button4 = findViewById(R.id.bnv_button4)
        button5 = findViewById(R.id.bnv_button5)
        button6 = findViewById(R.id.bnv_button6)
        button7 = findViewById(R.id.bnv_button7)
        button8 = findViewById(R.id.bnv_button8)
        button9 = findViewById(R.id.bnv_button9)
    }

    private fun setListeners() {
        overlay.setOnClickListener { buttonsClickListener?.onOverlayClick() ?: hide() }
        button1?.setOnClickListener { buttonsClickListener?.onButton1Click() ?: hide() }
        button2?.setOnClickListener { buttonsClickListener?.onButton2Click() ?: hide() }
        button3?.setOnClickListener { buttonsClickListener?.onButton3Click() ?: hide() }
        button4?.setOnClickListener { buttonsClickListener?.onButton4Click() ?: hide() }
        button5?.setOnClickListener { buttonsClickListener?.onButton5Click() ?: hide() }
        button6?.setOnClickListener { buttonsClickListener?.onButton6Click() ?: hide() }
        button7?.setOnClickListener { buttonsClickListener?.onButton7Click() ?: hide() }
        button8?.setOnClickListener { buttonsClickListener?.onButton8Click() ?: hide() }
        button9?.setOnClickListener { buttonsClickListener?.onButton9Click() ?: hide() }
    }

    private fun setInitialState() {
        visibility = View.INVISIBLE
        overlay.alpha = 0f
    }

    fun show() {
        post {
            if (!showing) {

                Log.d(
                    TAG,
                    "show: height=${contents.height}, translationY=${contents.translationY}, y=${contents.y}"
                )

                val animatorSet = getAppearAnimatorSet()
                animatorSet.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationEnd(animation: Animator?) {
                        Log.d(
                            TAG,
                            "on showing animation end: height=${contents.height}, translationY=${contents.translationY}, y=${contents.y}"
                        )
                        showing = false
                    }

                    override fun onAnimationRepeat(animation: Animator?) {}
                    override fun onAnimationCancel(animation: Animator?) {
                        showing = false
                    }

                    override fun onAnimationStart(animation: Animator?) {
                        Log.d(
                            TAG,
                            "on showing animation start: height=${contents.height}, translationY=${contents.translationY}, y=${contents.y}"
                        )
                        visibility = View.VISIBLE
                        showing = true
                    }
                })
                animatorSet.start()
            }
        }
    }

    fun hide() {
        post {
            Log.d(TAG, "hide: ")
            if (!hiding) {

                Log.d(
                    TAG,
                    "hide: height=${contents.height}, translationY=${contents.translationY}, y=${contents.y}"
                )
                val animatorSet = getDisappearAnimatorSet()
                animatorSet.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationEnd(animation: Animator?) {
                        Log.d(
                            TAG,
                            "on hiding animation end: height=${contents.height}, translationY=${contents.translationY}, y=${contents.y}"
                        )
                        visibility = View.INVISIBLE
                        hiding = false
                    }

                    override fun onAnimationRepeat(animation: Animator?) {}
                    override fun onAnimationCancel(animation: Animator?) {
                        hiding = false
                    }

                    override fun onAnimationStart(animation: Animator?) {
                        Log.d(
                            TAG,
                            "on hiding animation start: height=${contents.height}, translationY=${contents.translationY}, y=${contents.y}"
                        )
                        hiding = true
                    }
                })
                animatorSet.start()
            }
        }
    }

    private fun getAppearAnimatorSet(): AnimatorSet {
        val params = contents.layoutParams as MarginLayoutParams
        Log.d(
            TAG,
            "getAppearAnimatorSet: top=${params.topMargin}, bottom=${params.bottomMargin}, left=${params.leftMargin}, right=${params.rightMargin}"
        )
        val targetOffset = (contents.height + params.bottomMargin + params.topMargin)
        Log.d(TAG, "getAppearAnimatorSet: targetOffset=${targetOffset}")
        val contentsSlideInAnimator =
            ObjectAnimator.ofFloat(contents, "translationY", -targetOffset.toFloat())
                .apply {
                    duration = 300
                    interpolator = EasingInterpolator(Ease.CIRC_OUT)
                }

        val backgroundAppearAnimator = ObjectAnimator.ofFloat(overlay, "alpha", 1f)

        return AnimatorSet().apply {
            playTogether(contentsSlideInAnimator, backgroundAppearAnimator)
        }
    }

    private fun getDisappearAnimatorSet(): AnimatorSet {
        val contentsSlideInAnimator =
            ObjectAnimator.ofFloat(contents, "translationY", 0f)
                .apply {
                    duration = 300
                    interpolator = EasingInterpolator(Ease.CIRC_IN)
                }

        val backgroundAppearAnimator = ObjectAnimator.ofFloat(overlay, "alpha", 0f)

        return AnimatorSet().apply {
            playTogether(contentsSlideInAnimator, backgroundAppearAnimator)
        }
    }

    abstract class ButtonsClickListener {

        fun onOverlayClick() {}
        fun onButton1Click() {}
        fun onButton2Click() {}
        fun onButton3Click() {}
        fun onButton4Click() {}
        fun onButton5Click() {}
        fun onButton6Click() {}
        fun onButton7Click() {}
        fun onButton8Click() {}
        fun onButton9Click() {}
    }
}