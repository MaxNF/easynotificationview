package ru.netfantazii.easynotificationviewtest

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_first.*
import leakcanary.AppWatcher
import ru.netfantazii.easynotificationview.EasyNotificationView
import ru.netfantazii.easynotificationview.animation.bottomslide.BottomSlideAppearAnimator
import ru.netfantazii.easynotificationview.animation.bottomslide.BottomSlideDisappearAnimator

class FirstFragment : Fragment() {
    private val TAG = "Fragment"
    private lateinit var root: ViewGroup


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: ")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")
        root = inflater.inflate(R.layout.fragment_first, container, false) as ViewGroup
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        show_button.setOnClickListener(::showInFragment)
        show_no_anime_button.setOnClickListener(::showInActivity)
        check_button.setOnClickListener(::check)
        show_auto_hide.setOnClickListener(::showAutoHide)
        go_to_second_activity.setOnClickListener(::openSecondActivity)
        go_to_second_fragment.setOnClickListener(::goToSecondFragment)
        view.findViewById<Button>(R.id.show_default_view).setOnClickListener { showDefaultView() }
        view.findViewById<Button>(R.id.hide_default_views).setOnClickListener { hideDefaultViews() }
    }

    fun showInFragment(view: View) {
        EasyNotificationView.create(
            this,
            R.layout.layout_for_notification,
            Color.TRANSPARENT
        )
            .apply {
                isOverlayClickable = false
                disappearAnimationEndListener = {
                    AppWatcher.objectWatcher.watch(it, "detached notification")
                    Log.d(TAG, "animation ended")
                }
            }.show()
    }

    fun showInActivity(view: View) {
        EasyNotificationView.create(
            requireActivity(),
            R.layout.layout_for_notification,
            Color.TRANSPARENT
        )
            .apply {
                isOverlayClickable = false
                disappearAnimationEndListener = {
                    AppWatcher.objectWatcher.watch(it, "detached notification")
                    Log.d(TAG, "animation ended")
                }
            }.show()
    }

    fun check(view: View) {
        val easeView = requireActivity().findViewById<View>(R.id.easy_notification_view)
        val exists = easeView != null
        Log.d(TAG, "check: $exists")
    }

    fun showAutoHide(view: View) {
        EasyNotificationView.create(
            this,
            R.layout.layout_for_notification,
            Color.TRANSPARENT,
            disappearAnimator = BottomSlideDisappearAnimator(300)
        )
            .apply {
                isOverlayClickable = false
                onButton1ClickListener = {
                    openSecondActivity(null)
                    hide()
                }
                appearAnimationEndListener = { Log.d(TAG, "showNoAnim: appear ends") }
                disappearAnimationEndListener = { Log.d(TAG, "showNoAnim: disappear ends") }
            }.showAutoHide(1000)
    }

    fun openSecondActivity(view: View?) {
        val intent = Intent(requireContext(), MainActivity2::class.java)
        startActivity(intent)
    }

    fun goToSecondFragment(view: View?) {
        findNavController().navigate(R.id.secondFragment)
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView: ")
        super.onDestroyView()
    }

    private val views = mutableListOf<View>()

    fun showDefaultView() {
        repeat(5) {
            val testView = View(requireContext())
            val params = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            (view as ViewGroup).addView(testView, params)
            views.add(testView)
        }
    }

    fun hideDefaultViews() {
        views.forEachIndexed { index, testView ->
            AppWatcher.objectWatcher.watch(testView, "testView witn index#$index")
            (view as ViewGroup).removeView(testView)
        }
        views.clear()
    }
}