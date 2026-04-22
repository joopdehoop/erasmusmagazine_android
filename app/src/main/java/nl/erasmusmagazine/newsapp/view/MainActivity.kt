package nl.erasmusmagazine.newsapp.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
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
    private lateinit var adapter: ArticleAdapter

    private val settingsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        updateToolbarSubtitle()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val settingsRepository = SettingsRepository(this)
        controller = MainController(
            settingsRepository = settingsRepository,
            articlesRepository = ArticlesRepository(NetworkFactory.createWordPressApi())
        )

        adapter = ArticleAdapter { article ->
            val customTabsIntent = CustomTabsIntent.Builder().build()
            customTabsIntent.launchUrl(this, Uri.parse(article.url))
        }

        binding.articleRecycler.layoutManager = LinearLayoutManager(this)
        binding.articleRecycler.adapter = adapter

        configureMenu()
        updateToolbarSubtitle()

        val prefs = controller.loadPreferences()
        loadArticles(prefs.language)

        binding.swipeRefresh.setOnRefreshListener {
            loadArticles(controller.loadPreferences().language)
        }
    }

    override fun onResume() {
        super.onResume()
        loadArticles(controller.loadPreferences().language)
    }

    private fun configureMenu() {
        binding.topAppBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_language -> showLanguageDialog()
                R.id.menu_user -> settingsLauncher.launch(Intent(this, UserActivity::class.java))
                R.id.menu_notifications -> settingsLauncher.launch(Intent(this, NotificationSettingsActivity::class.java))
                R.id.menu_tip_editor -> {
                    val language = controller.loadPreferences().language
                    TipEditorActivity.open(this, language.tipFormUrl)
                }
            }
            true
        }
    }

    private fun showLanguageDialog() {
        val options = arrayOf("Nederlands", "English")
        val checkedItem = if (controller.loadPreferences().language == AppLanguage.ENGLISH) 1 else 0

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.menu_language))
            .setSingleChoiceItems(options, checkedItem) { dialog, which ->
                val language = if (which == 1) AppLanguage.ENGLISH else AppLanguage.DUTCH
                controller.updateLanguage(language)
                updateToolbarSubtitle()
                loadArticles(language)
                dialog.dismiss()
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
                Toast.makeText(this, getString(R.string.error_loading_news), Toast.LENGTH_LONG).show()
            }
        )
    }

    private fun updateToolbarSubtitle() {
        val prefs = controller.loadPreferences()
        val languageLabel = if (prefs.language == AppLanguage.ENGLISH) "English" else "Nederlands"
        val user = prefs.userName?.takeIf { it.isNotBlank() } ?: getString(R.string.user_guest)
        binding.topAppBar.subtitle = "$languageLabel • $user"
    }
}
