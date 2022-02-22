package com.ekochkov.skillfactorykotlintest.view.fragments

import android.os.Bundle
import android.view.*
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.fragment.app.Fragment
import androidx.transition.*
import com.ekochkov.skillfactorykotlintest.utils.AnimationHelper
import com.ekochkov.skillfactorykotlintest.databinding.FragmentLaterBinding

class LaterFragment: Fragment() {

    lateinit var binding: FragmentLaterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLaterBinding.inflate(inflater, container, false)

        var diffX = 0f
        var diffY = 0f

        val springForce = SpringForce(0f).apply {
            stiffness = SpringForce.STIFFNESS_MEDIUM
            dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
        }

        val springAnimationX = SpringAnimation(binding.img, DynamicAnimation.TRANSLATION_X).setSpring(springForce)
        val springAnimationY = SpringAnimation(binding.img, DynamicAnimation.TRANSLATION_Y).setSpring(springForce)



        binding.img.setOnTouchListener { view, motionEvent ->
            view.performClick()

            when(motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    //Устанавливаем координаты для корректного перемещения
                    diffX = motionEvent.rawX - view.x
                    diffY = motionEvent.rawY - view.y

                    //Отменяем анимацию, если к примера нашу view еще "пружинит" с предыдущего раза
                    springAnimationX.cancel()
                    springAnimationY.cancel()
                }

                MotionEvent.ACTION_MOVE -> {
                    //rawX, rawY текущие координаты view
                    binding.img.x = motionEvent.rawX - diffX
                    binding.img.y = motionEvent.rawY - diffY
                }

                MotionEvent.ACTION_UP -> {
                    //Стартуем анимацию возвращения в прежнее положение
                    springAnimationX.start()
                    springAnimationY.start()
                }
            }
            true
        }

        binding.button1.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.laterContainer, ChangeTransform())
            binding.textView.translationY +=100

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(view, requireActivity(), 4)
    }
}