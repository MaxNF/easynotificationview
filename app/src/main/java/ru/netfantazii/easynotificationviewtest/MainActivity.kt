package ru.netfantazii.easynotificationviewtest

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import ru.netfantazii.easynotificationview.EasyNotificationView
import ru.netfantazii.easynotificationview.animation.bottomslide.BottomSlideAppearAnimator
import ru.netfantazii.easynotificationview.animation.bottomslide.BottomSlideDisappearAnimator

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}