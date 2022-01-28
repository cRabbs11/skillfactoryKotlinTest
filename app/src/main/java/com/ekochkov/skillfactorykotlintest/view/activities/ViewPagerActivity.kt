package com.ekochkov.skillfactorykotlintest.view.activities

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ekochkov.skillfactorykotlintest.domain.PagerItem
import com.ekochkov.skillfactorykotlintest.R
import com.ekochkov.skillfactorykotlintest.databinding.ActivityViewPagerBinding
import com.ekochkov.skillfactorykotlintest.databinding.ViewPagerItemBinding
import com.ekochkov.skillfactorykotlintest.view.rv_adapters.ViewPagerAdapter

class ViewPagerActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityViewPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Создаем адаптер
        val pagerAdapter = ViewPagerAdapter()

        //Привязываем созданный адаптер к нашему ViewPager, который у нас в разметке
        binding.viewPager.adapter = pagerAdapter

        //Создаем список элементов, который передадим в адаптер
        val pagerItems = listOf<PagerItem>(
                PagerItem(ContextCompat.getColor(this, R.color.blue_light_1), "Blue"),
                PagerItem(ContextCompat.getColor(this, R.color.green), "Green"),
                PagerItem(ContextCompat.getColor(this, R.color.yellow), "Yellow")
        )

        //Передаем список в адаптер
        pagerAdapter.setItems(pagerItems)
        binding.viewPager.setPageTransformer { page, position ->
            val pageWidth = page.width
            val binding = ViewPagerItemBinding.bind(page)
            binding.textView.translationX = -position * (pageWidth/2)
        }
    }
}