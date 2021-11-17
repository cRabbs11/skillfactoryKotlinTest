package com.ekochkov.skillfactorykotlintest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ekochkov.skillfactorykotlintest.databinding.ActivityMainRecyclerViewBinding
import com.ekochkov.skillfactorykotlintest.decoration.OffsetFilmItemDecoration

import com.ekochkov.skillfactorykotlintest.diff.FilmDiff

class MainActivity : AppCompatActivity() {

    val filmList = ArrayList<Film>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = FilmListAdapter()
        binding.recyclerView.itemAnimator = ItemFilmAnimator(this)
        binding.recyclerView.adapter = adapter
        adapter.filmList = filmList
        val parallaxPosterDecorator = OffsetFilmItemDecoration()
        binding.recyclerView.addItemDecoration(parallaxPosterDecorator)
        binding.recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("BMTH", "dx: $dx dy: $dy")
                if (recyclerView.childCount>3) {
                    val view = recyclerView.getChildAt(2)
                }
            }
        })

        val diff = FilmDiff(adapter.filmList, setFilms())
        val diffResult = DiffUtil.calculateDiff(diff)
        adapter.filmList.clear()
        adapter.filmList.addAll(setFilms())
        diffResult.dispatchUpdatesTo(adapter)

        //binding.container!!.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        //binding.container!!.layoutTransition.setAnimator(LayoutTransition.APPEARING, AnimatorInflater.loadAnimator(this, R.animator.sample_animator))
        binding.topAppBar.setNavigationOnClickListener {
            showToast(resources.getString(R.string.main_menu))
        }
//
        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings -> {
                    showToast(resources.getString(R.string.settings))
                    true
                }
                R.id.search -> {
                    showToast(resources.getString(R.string.search))
                    true
                }
                else -> false
            }
        }

        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.compile -> {
                    showToast(resources.getString(R.string.compilations))

                    //if (binding.cardView5.alpha==1F) {
                    //    binding.cardView5.animate()
                    //            .setDuration(150)
                    //            .alpha(0.5F)
                    //} else {
                    //    binding.cardView5.animate()
                    //            .setDuration(150)
                    //            .alpha(1F)
                    //}
                    true
                }
                R.id.fav -> {
                    showToast(resources.getString(R.string.favorites))

                    //if (binding.container!!.childCount>0) {
                    //    binding.container!!.removeViewAt(binding.container!!.childCount-1)
                    //}
                    true
                }
                R.id.later -> {
                    showToast(resources.getString(R.string.later))
                    //ObjectAnimator.ofFloat(binding.imageView3, View.SCALE_Y, 0.5F, 1F).start()
                    true
                }
                else -> false
            }
        }


    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    fun setFilms(): ArrayList<Film>{
        val films = ArrayList<Film>()
        films.add(Film("Зеленая миля", R.drawable.test_poster_1, "мистическая драма по одноимённому роману Стивена Кинга. Премьера состоялась 9 декабря 1999 года[3]. Имеет 4 номинации на «Оскар», 3 премии «Сатурн», ещё 10 наград и 23 номинации. Автор сценария и режиссёр Фрэнк Дарабонт"))
        films.add(Film("Побег из шоушенка", R.drawable.test_poster_2, " американский художественный фильм-драма 1994 года, снятый режиссёром Фрэнком Дарабонтом по его же сценарию, и рассказывающий историю Энди Дюфрейна, незаслуженно приговорённого к пожизненному заключению и пробывшего в заключении почти 20 лет. Основой сюжета послужила повесть Стивена Кинга «Рита Хейуорт и спасение из Шоушенка». Главные роли сыграли Тим Роббинс и Морган Фримен. "))
        films.add(Film("Властелин колец: Возвращение короля", R.drawable.test_poster_3, " кинокартина 2003 года, третья и заключительная часть кинотрилогии, снятой Питером Джексоном по роману Дж. Р. Р. Толкина «Властелин колец». Мировая премьера состоялась 1 декабря 2003 года в Новой Зеландии. "))
        films.add(Film("Форрест Гамп", R.drawable.test_poster_4, "драма, девятый полнометражный фильм режиссёра Роберта Земекиса. Поставлен по одноимённому роману Уинстона Грума (1986), вышел на экраны в 1994 году. Наиболее успешный фильм режиссёра как среди зрителей (первое место по сборам в 1994 году), так и среди критиков и профессиональных кинематографистов (38 наград по всему миру, включая 6 премий «Оскар»). "))
        films.add(Film("Иван Васильевич меняет профессию", R.drawable.test_poster_5, "советская фантастическая комедия 1973 года, снятая режиссёром Леонидом Гайдаем по мотивам пьесы Михаила Булгакова «Иван Васильевич». Фильм рассказывает об инженере-изобретателе Шурике, создавшем машину времени, которая открывает двери в XVI век — во времена Ивана Грозного, в результате чего царь оказывается в советской Москве, а его тёзка — управдом Иван Бунша вместе с вором-рецидивистом Жоржем Милославским — в палатах царя. "))
        films.add(Film("Король Лев", R.drawable.test_poster_6, "32-й по счёту классический полнометражный мультфильм, выпущенный студией Диснея, о львёнке по имени Симба, которому предстоит пройти через предательство и изгнание, найти преданных друзей и свою любовь, а также стать истинным Королём. «Король Лев» впервые был показан 15 июня 1994 года[3] в нескольких городах США, а в широкий прокат мультфильм вышел 24 июня. Также 25 декабря 2002 года состоялась IMAX-премьера переработанной версии «Короля Льва» с улучшенным качеством изображения и звука. 16 сентября 2011 года состоялась повторная премьера этого мультфильма в формате 3D и IMAX 3D. "))
        return films
    }
}