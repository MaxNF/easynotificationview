package ru.netfantazii.bottomnotificationviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import ru.netfantazii.bottom_notification_view.BottomNotificationView

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun show(view: View) {
        val bottomNotificationView = BottomNotificationView.show(this, R.layout.notification_layout_test2)
        Log.d(TAG, "show: ${bottomNotificationView::class.simpleName}")
    }
}