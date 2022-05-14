package com.ekochkov.skillfactorykotlintest.view.fragments

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionSet
import com.bumptech.glide.Glide
import com.ekochkov.skillfactorykotlintest.data.entity.Film
import com.ekochkov.skillfactorykotlintest.R
import com.ekochkov.skillfactorykotlintest.databinding.FragmentFilmPageBinding
import com.ekochkov.skillfactorykotlintest.utils.Constants
import com.ekochkov.skillfactorykotlintest.utils.TmdbApiConstants
import com.ekochkov.skillfactorykotlintest.view.activities.MainActivity
import com.ekochkov.skillfactorykotlintest.viewmodel.FilmPageFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*


class FilmPageFragment : Fragment() {

    init {
        val slide = Slide(Gravity.END).addTarget(R.id.film_page_container)
        val fade = Fade().addTarget(R.id.film_page_container)
        val tranistion = TransitionSet().apply {
            addTransition(slide)
            addTransition(fade)
            duration = 250
        }
        enterTransition = tranistion
        returnTransition = tranistion
    }

    companion object {
        const val FILM_OBJECT = "film"
        const val SHOW_THIS_FILM = "посмотеть данное кино"
        const val NOTIFICATION_CHANNEL_NAME = "channel_name"
        const val NOTIFICATION_CHANNEL_DESCRIPTION = "channel_description"
        const val NOTIFICATION_CHANNEL_ID = "1"
    }

    lateinit var binding: FragmentFilmPageBinding
    private val viewModel: FilmPageFragmentViewModel by viewModels()
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFilmPageBinding.inflate(inflater, container, false)

        val film = arguments?.get(FILM_OBJECT) as Film
        setFavIcon(film)

        binding.fabFav.setOnClickListener {
            film.isInFav = !film.isInFav
            setFavIcon(film)
        }

        binding.fabDownload.setOnClickListener {
            asyncLoadFilmPoster(film)
        }

        binding.fabShare.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, "Look at this: ${film.title} \n\n ${film.descr}"
            )
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent, "Share:"))
        }

        val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        binding.fabNotify.setOnClickListener {
            val notificationId = 1
            notificationManager.notify(notificationId, getFilmNotification(film, SHOW_THIS_FILM))
        }

        binding.includeContent.text.text = film.descr
        binding.toolbar.title = film.title
        Glide.with(requireContext())
                .load(TmdbApiConstants.IMAGES_URL + "w780" + film.poster)
                .centerCrop()
                .into(binding.image)
        //binding.image.setImageResource(film.poster)
        createNotificationChannel()
        return binding.root
    }

    private fun getFilmNotification(film : Film, text: String): Notification {
        val intent = Intent(requireContext(), MainActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(Constants.BUNDLE_KEY_FILM, film)
        bundle.putInt(Constants.BUNDLE_KEY_INTENT, Constants.BUNDLE_INTENT_OPEN_FILM_FRAGMENT)
        intent.putExtra(Constants.BUNDLE_KEY, bundle)

        val pendingIntent = PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        println("!!! bundle = ${bundle}")

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q) {
            val notification = Notification.Builder(requireContext(), NOTIFICATION_CHANNEL_ID)
                    .setContentTitle(film.title)
                    .setContentText(text)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(androidx.transition.R.drawable.notification_icon_background)
                    .build()
            return notification
        } else {
            val notification = Notification.Builder(requireContext())
                    .setContentTitle(film.title)
                    .setContentText(text)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(androidx.transition.R.drawable.notification_icon_background)
                    .build()
            return notification
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance)
            channel.description = NOTIFICATION_CHANNEL_DESCRIPTION
            val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setFavIcon(film: Film) {
        if (film.isInFav) {
            binding.fabFav.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            binding.fabFav.setImageResource(R.drawable.ic_round_favorite_border_24)
        }
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
        )
    }



    fun saveToGallery(bitmap: Bitmap, film: Film) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            val cv = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, film.title)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis())
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/SkillfactoryTestApp")
            }

            //Получаем экземпляр объекта ContentResolver
            val contentResolver = requireActivity().contentResolver
            //Указываем в какую коллекцию будем класть, в данном случае Images
            val uri = contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    cv
            )
            //Открываем поток
            val outputStream = contentResolver.openOutputStream(uri!!)
            //Загружаем картинку, опционально можно задать уровень компрессии
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            //Закрываем поток
            outputStream?.close()
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.insertImage(
                    requireActivity().contentResolver,
                    bitmap,
                    film.title.handleSingleQuote(),
                    film.descr.handleSingleQuote()
            )
        }
    }

    private fun String.handleSingleQuote(): String {
        return this.replace("'", "")
    }

    private fun asyncLoadFilmPoster(film: Film) {
        if (!checkPermission()) {
            requestPermission()
            return
        }
        //Создаем родительский скоуп с диспатчером Main потока, так как будем взаимодействовать с UI
        MainScope().launch {
            //Включаем Прогресс-бар
            binding.progressBar.isVisible = true
            //Создаем через async, так как нам нужен результат от работы, то есть Bitmap
            val job = scope.async {
                viewModel.loadBitmapImage(TmdbApiConstants.IMAGES_URL + "original" + film.poster)
            }
            //Сохраняем в галерею, как только файл загрузится
            saveToGallery(job.await(), film)
            //Выводим снекбар с кнопкой перейти в галерею
            Snackbar.make(
                    binding.root,
                    R.string.downloaded_into_gallery,
                    Snackbar.LENGTH_LONG
            )
                    .setAction(R.string.to_gallery) {
                        val intent = Intent()
                        intent.action = Intent.ACTION_VIEW
                        intent.type = "image/*"
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                    .show()

            //Отключаем Прогресс-бар
            binding.progressBar.isVisible = false
        }
    }
}