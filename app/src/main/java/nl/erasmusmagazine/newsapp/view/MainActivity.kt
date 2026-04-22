package nl.erasmusmagazine.newsapp.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import nl.erasmusmagazine.newsapp.R
import nl.erasmusmagazine.newsapp.controller.MainController
import nl.erasmusmagazine.newsapp.data.repository.ArticlesRepository
import nl.erasmusmagazine.newsapp.data.repository.SettingsRepository
import nl.erasmusmagazine.newsapp.databinding.ActivityMainBinding
import nl.erasmusmagazine.newsapp.model.AppLanguage
import nl.erasmusmagazine.newsapp.util.NetworkFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: MainController
    private val adapter = ArticleAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val settingsRepository = SettingsRepository(this)
        controller = MainController(settingsRepository) { language ->
            ArticlesRepository(NetworkFactory.createWordPressApi(language))
        }

        binding.articleRecycler.layoutManager = LinearLayoutManager(this)
        binding.articleRecycler.adapter = adapter

        val prefs = controller.loadPreferences()
        configureMenu()
        loadArticles(prefs.language)

        binding.swipeRefresh.setOnRefreshListener {
            loadArticles(controller.loadPreferences().language)
        }
    }

    private fun configureMenu() {
        binding.topAppBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_language -> showLanguageDialog()
                R.id.menu_user -> showTextStub("Gebruiker-profiel volgt in volgende iteratie.")
                R.id.menu_notifications -> {
                    val prefs = controller.loadPreferences()
                    controller.updatePush(!prefs.pushEnabled)
                    showTextStub("Pushmeldingen: ${if (!prefs.pushEnabled) "aan" else "uit"}")
                }

                R.id.menu_tip_editor -> showTextStub("Open straks webformulier: /tip-ons")
            }
            true
        }
    }

    private fun showLanguageDialog() {
        val options = arrayOf("Nederlands", "English")
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.menu_language))
            .setItems(options) { _, which ->
                val language = if (which == 1) AppLanguage.ENGLISH else AppLanguage.DUTCH
                controller.updateLanguage(language)
                loadArticles(language)
            }
            .show()
    }

    private fun loadArticles(language: AppLanguage) {
        binding.swipeRefresh.isRefreshing = true
        controller.loadArticles(
            language = language,
            onSuccess = {
                adapter.submit(it)
                binding.swipeRefresh.isRefreshing = false
            },
            onError = {
                binding.swipeRefresh.isRefreshing = false
                Toast.makeText(this, "Kon nieuws niet laden", Toast.LENGTH_LONG).show()
            }
        )
    }

    private fun showTextStub(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
