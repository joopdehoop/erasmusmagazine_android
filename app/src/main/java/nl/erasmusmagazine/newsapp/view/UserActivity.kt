package nl.erasmusmagazine.newsapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import nl.erasmusmagazine.newsapp.controller.SettingsController
import nl.erasmusmagazine.newsapp.data.repository.SettingsRepository
import nl.erasmusmagazine.newsapp.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    private lateinit var controller: SettingsController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controller = SettingsController(SettingsRepository(this))

        val prefs = controller.loadPreferences()
        binding.userNameInput.setText(prefs.userName.orEmpty())

        binding.topAppBar.setNavigationOnClickListener { finish() }
        binding.saveButton.setOnClickListener {
            controller.saveUserName(binding.userNameInput.text.toString().trim())
            setResult(RESULT_OK)
            finish()
        }
    }
}
