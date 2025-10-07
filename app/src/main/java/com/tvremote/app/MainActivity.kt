package com.tvremote.app

import android.content.Intent
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import com.google.android.material.button.MaterialButton
import com.tvremote.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var irController: IRController
    private lateinit var configManager: RemoteConfigManager
    private lateinit var vibrator: Vibrator
    private var isIRAvailable = false
    private var currentConfig: RemoteConfig? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize config manager
        configManager = RemoteConfigManager(this)
        
        // Check if configuration exists, if not go to configuration screen
        if (!configManager.hasConfiguration()) {
            startConfigurationActivity()
            return
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load configuration
        currentConfig = configManager.getCurrentConfig()

        // Initialize IR Controller
        irController = IRController(this)
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator

        // Check IR availability
        isIRAvailable = irController.hasIrEmitter()
        
        if (!isIRAvailable) {
            binding.tvStatus.text = getString(R.string.status_ir_not_available)
            showIRNotAvailableDialog()
        } else {
            val configName = currentConfig?.name ?: "Unknown"
            binding.tvStatus.text = "Ready - $configName"
        }

        // Update title with remote name
        title = currentConfig?.name ?: getString(R.string.app_name)

        // Setup button listeners
        setupButtons()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startConfigurationActivity()
                true
            }
            R.id.action_manage_remotes -> {
                showManageRemotesDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun startConfigurationActivity() {
        val intent = Intent(this, ConfigurationActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setupButtons() {
        val config = currentConfig ?: return

        // Power button
        binding.btnPower.setOnClickListener {
            sendCommand("Power", ButtonType.POWER) { 
                irController.sendWithConfig(config, IRController.CommandType.POWER)
            }
        }

        // Mute button
        binding.btnMute.setOnClickListener {
            sendCommand("Mute", ButtonType.MUTE) { 
                irController.sendWithConfig(config, IRController.CommandType.MUTE)
            }
        }

        // Navigation buttons
        binding.btnUp.setOnClickListener {
            sendCommand("Up", ButtonType.NAVIGATION) { 
                irController.sendWithConfig(config, IRController.CommandType.UP)
            }
        }

        binding.btnDown.setOnClickListener {
            sendCommand("Down", ButtonType.NAVIGATION) { 
                irController.sendWithConfig(config, IRController.CommandType.DOWN)
            }
        }

        binding.btnLeft.setOnClickListener {
            sendCommand("Left", ButtonType.NAVIGATION) { 
                irController.sendWithConfig(config, IRController.CommandType.LEFT)
            }
        }

        binding.btnRight.setOnClickListener {
            sendCommand("Right", ButtonType.NAVIGATION) { 
                irController.sendWithConfig(config, IRController.CommandType.RIGHT)
            }
        }

        binding.btnOk.setOnClickListener {
            sendCommand("OK", ButtonType.OK) { 
                irController.sendWithConfig(config, IRController.CommandType.OK)
            }
        }

        // Volume buttons
        binding.btnVolUp.setOnClickListener {
            sendCommand("Volume Up", ButtonType.VOLUME) { 
                irController.sendWithConfig(config, IRController.CommandType.VOLUME_UP)
            }
        }

        binding.btnVolDown.setOnClickListener {
            sendCommand("Volume Down", ButtonType.VOLUME) { 
                irController.sendWithConfig(config, IRController.CommandType.VOLUME_DOWN)
            }
        }

        // Channel buttons
        binding.btnChUp.setOnClickListener {
            sendCommand("Channel Up", ButtonType.CHANNEL) { 
                irController.sendWithConfig(config, IRController.CommandType.CHANNEL_UP)
            }
        }

        binding.btnChDown.setOnClickListener {
            sendCommand("Channel Down", ButtonType.CHANNEL) { 
                irController.sendWithConfig(config, IRController.CommandType.CHANNEL_DOWN)
            }
        }

        // Source button
        binding.btnSource.setOnClickListener {
            sendCommand("Source", ButtonType.SOURCE) { 
                irController.sendWithConfig(config, IRController.CommandType.SOURCE)
            }
        }
    }

    /**
     * Send IR command with feedback
     */
    private fun sendCommand(commandName: String, buttonType: ButtonType, command: () -> Boolean) {
        // Provide haptic feedback
        provideHapticFeedback(buttonType)

        if (!isIRAvailable) {
            Toast.makeText(this, "IR Blaster not available", Toast.LENGTH_SHORT).show()
            return
        }

        // Send command
        val success = command()
        
        if (success) {
            binding.tvStatus.text = "$commandName sent"
            // Visual feedback - you could add button animation here
        } else {
            binding.tvStatus.text = "Failed to send $commandName"
            Toast.makeText(this, "Command failed", Toast.LENGTH_SHORT).show()
        }

        // Reset status after 2 seconds
        binding.tvStatus.postDelayed({
            binding.tvStatus.text = getString(R.string.status_ready)
        }, 2000)
    }

    /**
     * Provide haptic feedback based on button type
     */
    private fun provideHapticFeedback(buttonType: ButtonType) {
        if (!vibrator.hasVibrator()) return

        val effect = when (buttonType) {
            ButtonType.POWER -> VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE)
            ButtonType.OK -> VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE)
            ButtonType.NAVIGATION -> VibrationEffect.createOneShot(30, 100)
            ButtonType.VOLUME, ButtonType.CHANNEL -> VibrationEffect.createOneShot(40, 120)
            ButtonType.MUTE, ButtonType.SOURCE -> VibrationEffect.createOneShot(60, VibrationEffect.DEFAULT_AMPLITUDE)
        }

        vibrator.vibrate(effect)
    }

    /**
     * Show dialog when IR is not available
     */
    private fun showIRNotAvailableDialog() {
        AlertDialog.Builder(this)
            .setTitle("IR Blaster Not Available")
            .setMessage("This device does not have an IR blaster or it's not supported. The app will not be able to control your TV.\n\nThis app is designed for Xiaomi devices with IR blaster support.")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .setNegativeButton("Exit") { _, _ -> finish() }
            .setCancelable(false)
            .show()
    }

    /**
     * Show manage remotes dialog
     */
    private fun showManageRemotesDialog() {
        val configs = configManager.getSavedConfigs()
        
        if (configs.isEmpty()) {
            Toast.makeText(this, "No saved remotes", Toast.LENGTH_SHORT).show()
            return
        }

        val items = configs.map { it.name }.toTypedArray()
        
        AlertDialog.Builder(this)
            .setTitle("Select Remote")
            .setItems(items) { dialog, which ->
                val selected = configs[which]
                configManager.saveCurrentConfig(selected)
                recreate() // Reload activity with new config
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    enum class ButtonType {
        POWER, MUTE, NAVIGATION, OK, VOLUME, CHANNEL, SOURCE
    }
}

