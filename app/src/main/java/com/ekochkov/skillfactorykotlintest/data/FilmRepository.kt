package com.ekochkov.skillfactorykotlintest.data

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.ekochkov.skillfactorykotlintest.domain.Film

class FilmRepository(dbHelper: DatabaseHelper) {

    val filmList = setFilms()
    private val sqlDb: SQLiteDatabase = dbHelper.writableDatabase

    private fun setFilms(): ArrayList<Film> {
        val films = ArrayList<Film>()
        films.add(
            Film(
                "Зеленая миля",
                    "R.drawable.test_poster_1",
                "мистическая драма по одноимённому роману Стивена Кинга. Премьера состоялась 9 декабря 1999 года[3]. Имеет 4 номинации на «Оскар», 3 премии «Сатурн», ещё 10 наград и 23 номинации. Автор сценария и режиссёр Фрэнк Дарабонт",
                    false,
                    91

            )
        )
        films.add(
            Film(
                "Побег из шоушенка",
                    "R.drawable.test_poster_2",
                " американский художественный фильм-драма 1994 года, снятый режиссёром Фрэнком Дарабонтом по его же сценарию, и рассказывающий историю Энди Дюфрейна, незаслуженно приговорённого к пожизненному заключению и пробывшего в заключении почти 20 лет. Основой сюжета послужила повесть Стивена Кинга «Рита Хейуорт и спасение из Шоушенка». Главные роли сыграли Тим Роббинс и Морган Фримен. ",
                    false,
                    85
            )
        )
        films.add(
            Film(
                "Властелин колец: Возвращение короля",
                    "R.drawable.test_poster_3",
                " кинокартина 2003 года, третья и заключительная часть кинотрилогии, снятой Питером Джексоном по роману Дж. Р. Р. Толкина «Властелин колец». Мировая премьера состоялась 1 декабря 2003 года в Новой Зеландии. ",
                    false,
                    64
            )
        )
        films.add(
            Film(
                "Форрест Гамп",
                    "R.drawable.test_poster_4",
                "драма, девятый полнометражный фильм режиссёра Роберта Земекиса. Поставлен по одноимённому роману Уинстона Грума (1986), вышел на экраны в 1994 году. Наиболее успешный фильм режиссёра как среди зрителей (первое место по сборам в 1994 году), так и среди критиков и профессиональных кинематографистов (38 наград по всему миру, включая 6 премий «Оскар»). ",
                    false,
                    95

            )
        )
        films.add(
            Film(
                "Иван Васильевич меняет профессию",
                    "R.drawable.test_poster_5",
                "советская фантастическая комедия 1973 года, снятая режиссёром Леонидом Гайдаем по мотивам пьесы Михаила Булгакова «Иван Васильевич». Фильм рассказывает об инженере-изобретателе Шурике, создавшем машину времени, которая открывает двери в XVI век — во времена Ивана Грозного, в результате чего царь оказывается в советской Москве, а его тёзка — управдом Иван Бунша вместе с вором-рецидивистом Жоржем Милославским — в палатах царя. ",
                    false,
                    79
            )
        )
        films.add(
            Film(
                "Король Лев",
                    "R.drawable.test_poster_6",
                "32-й по счёту классический полнометражный мультфильм, выпущенный студией Диснея, о львёнке по имени Симба, которому предстоит пройти через предательство и изгнание, найти преданных друзей и свою любовь, а также стать истинным Королём. «Король Лев» впервые был показан 15 июня 1994 года[3] в нескольких городах США, а в широкий прокат мультфильм вышел 24 июня. Также 25 декабря 2002 года состоялась IMAX-премьера переработанной версии «Короля Льва» с улучшенным качеством изображения и звука. 16 сентября 2011 года состоялась повторная премьера этого мультфильма в формате 3D и IMAX 3D. ",
                    false,
                    34
            )
        )
        return films
    }

    fun putFilmInDB(film: Film) {
        val cv = ContentValues()
        cv.put(DatabaseHelper.COLUMN_TITLE, film.title)
        cv.put(DatabaseHelper.COLUMN_DESCR, film.descr)
        cv.put(DatabaseHelper.COLUMN_POSTER, film.poster)
        cv.put(DatabaseHelper.COLUMN_RATING, film.rating)

        val isInFav = if (film.isInFav) { 1 } else { 0 }
        cv.put(DatabaseHelper.COLUMN_IN_FAV, isInFav)

        sqlDb.insert(DatabaseHelper.TABLE_NAME, null, cv)
    }

    fun getAllFilmsFromBD(): List<Film> {
        val films = arrayListOf<Film>()

        val cursor = sqlDb.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_NAME}", null)
        cursor.use {
            if (it.moveToFirst()) {
                do {
                    films.add(convertFillmfromCursor(it))
                } while (it.moveToNext())
            }
        }

        return films
    }

    private fun convertFillmfromCursor(cursor: Cursor): Film {
        val title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE))
        val descr = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCR))
        val poster = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_POSTER))
        val rating = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_RATING))
        val isInFav = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_IN_FAV))==1

        return Film(title = title,
                poster = poster,
                descr = descr,
                isInFav = isInFav,
                rating = rating)
    }
}