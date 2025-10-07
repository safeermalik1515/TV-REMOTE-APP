package com.tvremote.app

import android.content.Intent
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.tvremote.app.databinding.ActivityConfigurationBinding

class ConfigurationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfigurationBinding
    private lateinit var irController: IRController
    private lateinit var configManager: RemoteConfigManager
    private lateinit var vibrator: Vibrator

    // Config item views
    private data class ConfigItem(
        val editText: TextInputEditText,
        val testButton: MaterialButton,
        val label: String
    )

    private lateinit var configItems: Map<String, ConfigItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigurationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        irController = IRController(this)
        configManager = RemoteConfigManager(this)
        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator

        setupConfigItems()
        setupButtons()
        loadExistingConfig()
    }

    private fun setupConfigItems() {
        configItems = mapOf(
            "power" to createConfigItem(binding.configPower, "Power"),
            "mute" to createConfigItem(binding.configMute, "Mute"),
            "volUp" to createConfigItem(binding.configVolUp, "Vol Up"),
            "volDown" to createConfigItem(binding.configVolDown, "Vol Down"),
            "chUp" to createConfigItem(binding.configChUp, "CH Up"),
            "chDown" to createConfigItem(binding.configChDown, "CH Down"),
            "up" to createConfigItem(binding.configUp, "Up"),
            "down" to createConfigItem(binding.configDown, "Down"),
            "left" to createConfigItem(binding.configLeft, "Left"),
            "right" to createConfigItem(binding.configRight, "Right"),
            "ok" to createConfigItem(binding.configOk, "OK"),
            "source" to createConfigItem(binding.configSource, "Source")
        )

        // Set up test buttons
        configItems["power"]?.let { setupTestButton(it, "power") }
        configItems["mute"]?.let { setupTestButton(it, "mute") }
        configItems["volUp"]?.let { setupTestButton(it, "volUp") }
        configItems["volDown"]?.let { setupTestButton(it, "volDown") }
        configItems["chUp"]?.let { setupTestButton(it, "chUp") }
        configItems["chDown"]?.let { setupTestButton(it, "chDown") }
        configItems["up"]?.let { setupTestButton(it, "up") }
        configItems["down"]?.let { setupTestButton(it, "down") }
        configItems["left"]?.let { setupTestButton(it, "left") }
        configItems["right"]?.let { setupTestButton(it, "right") }
        configItems["ok"]?.let { setupTestButton(it, "ok") }
        configItems["source"]?.let { setupTestButton(it, "source") }
    }

    private fun createConfigItem(view: View, label: String): ConfigItem {
        val editText = view.findViewById<TextInputEditText>(R.id.etCode)
        val testButton = view.findViewById<MaterialButton>(R.id.btnTest)
        view.findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.tilCode)
            .hint = label
        return ConfigItem(editText, testButton, label)
    }

    private fun setupTestButton(item: ConfigItem, buttonType: String) {
        item.testButton.setOnClickListener {
            testButton(item, buttonType)
        }
    }

    private fun setupButtons() {
        // Back button
        binding.btnBack.setOnClickListener {
            finish()
        }

        // Preset buttons
        binding.btnPresetSamsung.setOnClickListener {
            loadPreset(RemoteConfig.getSamsungConfig())
            Toast.makeText(this, "Samsung preset loaded", Toast.LENGTH_SHORT).show()
        }

        binding.btnPresetLG.setOnClickListener {
            loadPreset(RemoteConfig.getLGConfig())
            Toast.makeText(this, "LG preset loaded", Toast.LENGTH_SHORT).show()
        }

        binding.btnPresetSony.setOnClickListener {
            loadPreset(RemoteConfig.getSonyConfig())
            Toast.makeText(this, "Sony preset loaded", Toast.LENGTH_SHORT).show()
        }

        // Test all button
        binding.btnTestAll.setOnClickListener {
            Toast.makeText(this, "Testing all buttons...", Toast.LENGTH_SHORT).show()
            // You can implement a sequence to test all buttons
        }

        // Save button
        binding.btnSave.setOnClickListener {
            saveConfiguration()
        }
    }

    private fun loadPreset(preset: RemoteConfig) {
        binding.etRemoteName.setText(preset.name)
        binding.etFrequency.setText(preset.frequency.toString())
        binding.etAddress.setText("0x%02X".format(preset.address))
        
        configItems["power"]?.editText?.setText("0x%02X".format(preset.powerCode))
        configItems["mute"]?.editText?.setText("0x%02X".format(preset.muteCode))
        configItems["volUp"]?.editText?.setText("0x%02X".format(preset.volumeUpCode))
        configItems["volDown"]?.editText?.setText("0x%02X".format(preset.volumeDownCode))
        configItems["chUp"]?.editText?.setText("0x%02X".format(preset.channelUpCode))
        configItems["chDown"]?.editText?.setText("0x%02X".format(preset.channelDownCode))
        configItems["up"]?.editText?.setText("0x%02X".format(preset.upCode))
        configItems["down"]?.editText?.setText("0x%02X".format(preset.downCode))
        configItems["left"]?.editText?.setText("0x%02X".format(preset.leftCode))
        configItems["right"]?.editText?.setText("0x%02X".format(preset.rightCode))
        configItems["ok"]?.editText?.setText("0x%02X".format(preset.okCode))
        configItems["source"]?.editText?.setText("0x%02X".format(preset.sourceCode))

        vibrate()
    }

    private fun loadExistingConfig() {
        configManager.getCurrentConfig()?.let { config ->
            loadPreset(config)
        } ?: run {
            // Load Samsung preset by default for first time
            loadPreset(RemoteConfig.getSamsungConfig())
        }
    }

    private fun testButton(item: ConfigItem, buttonType: String) {
        if (!irController.hasIrEmitter()) {
            Toast.makeText(this, "IR not available", Toast.LENGTH_SHORT).show()
            return
        }

        val codeStr = item.editText.text.toString()
        val code = parseHexValue(codeStr)
        if (code == -1) {
            Toast.makeText(this, "Invalid code format", Toast.LENGTH_SHORT).show()
            return
        }

        val addressStr = binding.etAddress.text.toString()
        val address = parseHexValue(addressStr)
        if (address == -1) {
            Toast.makeText(this, "Invalid address format", Toast.LENGTH_SHORT).show()
            return
        }

        val frequency = binding.etFrequency.text.toString().toIntOrNull() ?: 38000

        // Test the button
        val success = irController.sendCustomCommand(address, code, frequency)
        
        if (success) {
            Toast.makeText(this, "${item.label} sent", Toast.LENGTH_SHORT).show()
            vibrate()
        } else {
            Toast.makeText(this, "Failed to send", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveConfiguration() {
        val name = binding.etRemoteName.text.toString().trim()
        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter a remote name", Toast.LENGTH_SHORT).show()
            return
        }

        val frequency = binding.etFrequency.text.toString().toIntOrNull() ?: 38000
        val address = parseHexValue(binding.etAddress.text.toString())
        
        if (address == -1) {
            Toast.makeText(this, "Invalid address format", Toast.LENGTH_SHORT).show()
            return
        }

        val config = RemoteConfig(
            name = name,
            tvBrand = "Custom",
            frequency = frequency,
            address = address,
            powerCode = parseHexValue(configItems["power"]?.editText?.text.toString()),
            muteCode = parseHexValue(configItems["mute"]?.editText?.text.toString()),
            volumeUpCode = parseHexValue(configItems["volUp"]?.editText?.text.toString()),
            volumeDownCode = parseHexValue(configItems["volDown"]?.editText?.text.toString()),
            channelUpCode = parseHexValue(configItems["chUp"]?.editText?.text.toString()),
            channelDownCode = parseHexValue(configItems["chDown"]?.editText?.text.toString()),
            upCode = parseHexValue(configItems["up"]?.editText?.text.toString()),
            downCode = parseHexValue(configItems["down"]?.editText?.text.toString()),
            leftCode = parseHexValue(configItems["left"]?.editText?.text.toString()),
            rightCode = parseHexValue(configItems["right"]?.editText?.text.toString()),
            okCode = parseHexValue(configItems["ok"]?.editText?.text.toString()),
            sourceCode = parseHexValue(configItems["source"]?.editText?.text.toString())
        )

        configManager.saveCurrentConfig(config)
        Toast.makeText(this, "Configuration saved!", Toast.LENGTH_SHORT).show()
        
        vibrate(100)

        // Go to main activity
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun parseHexValue(hexStr: String): Int {
        return try {
            val cleaned = hexStr.trim().replace("0x", "", ignoreCase = true)
            cleaned.toInt(16)
        } catch (e: Exception) {
            -1
        }
    }

    private fun vibrate(duration: Long = 50) {
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
        }
    }
}

