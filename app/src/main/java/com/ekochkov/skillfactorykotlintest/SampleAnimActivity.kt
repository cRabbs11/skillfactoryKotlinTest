package com.ekochkov.skillfactorykotlintest

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.ekochkov.skillfactorykotlintest.databinding.ActivitySampleAnimationSetBinding

class SampleAnimActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySampleAnimationSetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sunRiseAnim = ObjectAnimator.ofFloat(binding.sunImage, View.TRANSLATION_Y, 0F, -1000F)
        sunRiseAnim.duration = 1500
        sunRiseAnim.start()

        val dayBrightAnim = ObjectAnimator.ofFloat(binding.dayImage, View.ALPHA, 0F)
        dayBrightAnim.duration = 1500
//
        val cloud1Anim = ObjectAnimator.ofFloat(binding.cloud1, View.TRANSLATION_X, 0F, 750F)
        cloud1Anim.startDelay = 500
        cloud1Anim.duration = 2000
        //cloud1Anim.start()
//
        val cloud2Anim = ObjectAnimator.ofFloat(binding.cloud2, View.TRANSLATION_X, 0F, -700F)
        cloud2Anim.startDelay = 500
        cloud2Anim.duration = 2000
        //cloud2Anim.start()
//
        val animCloudsSet = AnimatorSet()
        animCloudsSet.playTogether(cloud1Anim, cloud2Anim)
        animCloudsSet.interpolator = AccelerateDecelerateInterpolator()
//
        val sunRiseSet = AnimatorSet()
        sunRiseSet.playTogether(sunRiseAnim, dayBrightAnim)
        sunRiseSet.startDelay = 1500
        sunRiseSet.interpolator = OvershootInterpolator()
//
        val animSet = AnimatorSet()
        animSet.playSequentially(sunRiseSet, animCloudsSet)
        animSet.start()
//
        binding.btn.setOnClickListener {
            binding.grassImage.animate()
                    .setDuration(1000)
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .alpha(0.5F)
                    .start()
        }
    }
}