package com.ekochkov.skillfactorykotlintest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ekochkov.skillfactorykotlintest.databinding.FragmentHomeBinding
import com.ekochkov.skillfactorykotlintest.decoration.OffsetFilmItemDecoration
import com.ekochkov.skillfactorykotlintest.diff.FilmDiff

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private val filmList = ArrayList<Film>()
    private lateinit var  binding: FragmentHomeBinding
    var sharedTransitionPosition = "transition1"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        postponeEnterTransition()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FilmListAdapter(this, object: FilmListAdapter.OnItemClickListener<Film> {
            override fun onClick(film: Film, sharedView: View) {
                (activity as MainActivity).launchFilmPageFragment(film, sharedView)
            }
        })

        val parallaxPosterDecorator = OffsetFilmItemDecoration()

        binding.recyclerView.itemAnimator = ItemFilmAnimator(requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(parallaxPosterDecorator)
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("BMTH", "dx: $dx dy: $dy")
                if (recyclerView.childCount > 3) {
                    val view = recyclerView.getChildAt(2)
                }
            }
        })

        adapter.filmList = filmList

        val newFilmList = setFilms()
        val diff = FilmDiff(adapter.filmList, newFilmList)
        val diffResult = DiffUtil.calculateDiff(diff)
        adapter.filmList.clear()
        adapter.filmList.addAll(newFilmList)
        diffResult.dispatchUpdatesTo(adapter)

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

        binding.topAppBar.setNavigationOnClickListener {
            showToast(resources.getString(R.string.main_menu))
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    fun setFilms(): ArrayList<Film> {
        val films = ArrayList<Film>()
        films.add(
                Film(
                        "Зеленая миля",
                        R.drawable.test_poster_1,
                        "мистическая драма по одноимённому роману Стивена Кинга. Премьера состоялась 9 декабря 1999 года[3]. Имеет 4 номинации на «Оскар», 3 премии «Сатурн», ещё 10 наград и 23 номинации. Автор сценария и режиссёр Фрэнк Дарабонт"
                )
        )
        films.add(
                Film(
                        "Побег из шоушенка",
                        R.drawable.test_poster_2,
                        " американский художественный фильм-драма 1994 года, снятый режиссёром Фрэнком Дарабонтом по его же сценарию, и рассказывающий историю Энди Дюфрейна, незаслуженно приговорённого к пожизненному заключению и пробывшего в заключении почти 20 лет. Основой сюжета послужила повесть Стивена Кинга «Рита Хейуорт и спасение из Шоушенка». Главные роли сыграли Тим Роббинс и Морган Фримен. "
                )
        )
        films.add(
                Film(
                        "Властелин колец: Возвращение короля",
                        R.drawable.test_poster_3,
                        " кинокартина 2003 года, третья и заключительная часть кинотрилогии, снятой Питером Джексоном по роману Дж. Р. Р. Толкина «Властелин колец». Мировая премьера состоялась 1 декабря 2003 года в Новой Зеландии. "
                )
        )
        films.add(
                Film(
                        "Форрест Гамп",
                        R.drawable.test_poster_4,
                        "драма, девятый полнометражный фильм режиссёра Роберта Земекиса. Поставлен по одноимённому роману Уинстона Грума (1986), вышел на экраны в 1994 году. Наиболее успешный фильм режиссёра как среди зрителей (первое место по сборам в 1994 году), так и среди критиков и профессиональных кинематографистов (38 наград по всему миру, включая 6 премий «Оскар»). "
                )
        )
        films.add(
                Film(
                        "Иван Васильевич меняет профессию",
                        R.drawable.test_poster_5,
                        "советская фантастическая комедия 1973 года, снятая режиссёром Леонидом Гайдаем по мотивам пьесы Михаила Булгакова «Иван Васильевич». Фильм рассказывает об инженере-изобретателе Шурике, создавшем машину времени, которая открывает двери в XVI век — во времена Ивана Грозного, в результате чего царь оказывается в советской Москве, а его тёзка — управдом Иван Бунша вместе с вором-рецидивистом Жоржем Милославским — в палатах царя. "
                )
        )
        films.add(
                Film(
                        "Король Лев",
                        R.drawable.test_poster_6,
                        "32-й по счёту классический полнометражный мультфильм, выпущенный студией Диснея, о львёнке по имени Симба, которому предстоит пройти через предательство и изгнание, найти преданных друзей и свою любовь, а также стать истинным Королём. «Король Лев» впервые был показан 15 июня 1994 года[3] в нескольких городах США, а в широкий прокат мультфильм вышел 24 июня. Также 25 декабря 2002 года состоялась IMAX-премьера переработанной версии «Короля Льва» с улучшенным качеством изображения и звука. 16 сентября 2011 года состоялась повторная премьера этого мультфильма в формате 3D и IMAX 3D. "
                )
        )
        return films
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FirstFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}