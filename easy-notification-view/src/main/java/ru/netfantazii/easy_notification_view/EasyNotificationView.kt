package ru.netfantazii.easy_notification_view

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import com.daasuu.ei.Ease
import com.daasuu.ei.EasingInterpolator
import ru.netfantazii.easy_notification_view.animation.base.AppearAnimator
import ru.netfantazii.easy_notification_view.animation.base.DisappearAnimator
import java.lang.IllegalArgumentException

@SuppressLint("ViewConstructor")
class EasyNotificationView(
    context: Context,
    @LayoutRes private val contentsLayout: Int,
    @ColorInt private val overlayColor: Int,
    private val buttonsClickListener: ButtonsClickListener?,
    private val appearAnimator: AppearAnimator,
    private val disappearAnimator: DisappearAnimator
) : ConstraintLayout(context) {

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

    init {
        inflate(context)
    }

    private fun inflate(context: Context) {
        attachOverlay(context)
        attachContents(context)
        assignChildViews()
        setListeners()
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
        appearAnimator.applyInitialState(this)
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
            else -> throw IllegalArgumentException("Please specify the container view or provide EasyNotificationCreator with valid context (should be either a Fragment or an Activity)")
        }
    }

    /** Hides the notification and removes it's view from the parent container at the end of the animation.*/
    fun hide() {
        post {
            disappearAnimator.startDisappearAnimation(this)
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