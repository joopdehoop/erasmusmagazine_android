package nl.erasmusmagazine.newsapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import nl.erasmusmagazine.newsapp.controller.SettingsController
import nl.erasmusmagazine.newsapp.data.repository.SettingsRepository
import nl.erasmusmagazine.newsapp.databinding.ActivityNotificationSettingsBinding

class NotificationSettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationSettingsBinding
    private lateinit var controller: SettingsController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controller = SettingsController(SettingsRepository(this))
        val prefs = controller.loadPreferences()
        binding.pushSwitch.isChecked = prefs.pushEnabled

        binding.topAppBar.setNavigationOnClickListener { finish() }

        binding.pushSwitch.setOnCheckedChangeListener { _, isChecked ->
            controller.savePushEnabled(isChecked)
            setResult(RESULT_OK)
        }
    }
}
