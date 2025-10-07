package com.tvremote.app

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class RemoteConfig(
    val id: String = System.currentTimeMillis().toString(),
    val name: String = "My Remote",
    val tvBrand: String = "Generic",
    val frequency: Int = 38000,
    val address: Int = 0x00,
    val powerCode: Int = 0x12,
    val muteCode: Int = 0x10,
    val volumeUpCode: Int = 0x16,
    val volumeDownCode: Int = 0x17,
    val channelUpCode: Int = 0x20,
    val channelDownCode: Int = 0x21,
    val upCode: Int = 0x1A,
    val downCode: Int = 0x1B,
    val leftCode: Int = 0x19,
    val rightCode: Int = 0x18,
    val okCode: Int = 0x1C,
    val sourceCode: Int = 0x0B
) {
    companion object {
        // Predefined configurations for common TV brands
        fun getSamsungConfig() = RemoteConfig(
            name = "Samsung TV",
            tvBrand = "Samsung",
            frequency = 38000,
            address = 0x07,
            powerCode = 0x02,
            muteCode = 0x07,
            volumeUpCode = 0x07,
            volumeDownCode = 0x0B,
            channelUpCode = 0x12,
            channelDownCode = 0x10,
            upCode = 0x60,
            downCode = 0x61,
            leftCode = 0x65,
            rightCode = 0x62,
            okCode = 0x68,
            sourceCode = 0x01
        )

        fun getLGConfig() = RemoteConfig(
            name = "LG TV",
            tvBrand = "LG",
            frequency = 38000,
            address = 0x04,
            powerCode = 0x08,
            muteCode = 0x09,
            volumeUpCode = 0x02,
            volumeDownCode = 0x03,
            channelUpCode = 0x00,
            channelDownCode = 0x01,
            upCode = 0x40,
            downCode = 0x41,
            leftCode = 0x07,
            rightCode = 0x06,
            okCode = 0x44,
            sourceCode = 0x0B
        )

        fun getSonyConfig() = RemoteConfig(
            name = "Sony TV",
            tvBrand = "Sony",
            frequency = 40000,
            address = 0x01,
            powerCode = 0x15,
            muteCode = 0x14,
            volumeUpCode = 0x12,
            volumeDownCode = 0x13,
            channelUpCode = 0x10,
            channelDownCode = 0x11,
            upCode = 0x74,
            downCode = 0x75,
            leftCode = 0x34,
            rightCode = 0x33,
            okCode = 0x65,
            sourceCode = 0x25
        )

        fun getGenericConfig() = RemoteConfig(
            name = "Generic TV",
            tvBrand = "Generic",
            frequency = 38000,
            address = 0x00,
            powerCode = 0x12,
            muteCode = 0x10,
            volumeUpCode = 0x16,
            volumeDownCode = 0x17,
            channelUpCode = 0x20,
            channelDownCode = 0x21,
            upCode = 0x1A,
            downCode = 0x1B,
            leftCode = 0x19,
            rightCode = 0x18,
            okCode = 0x1C,
            sourceCode = 0x0B
        )
    }
}

class RemoteConfigManager(context: Context) {
    private val prefs: SharedPreferences = 
        context.getSharedPreferences("RemoteConfigs", Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        private const val KEY_CURRENT_CONFIG = "current_config"
        private const val KEY_SAVED_CONFIGS = "saved_configs"
        private const val KEY_HAS_CONFIG = "has_config"
    }

    /**
     * Check if any configuration exists
     */
    fun hasConfiguration(): Boolean {
        return prefs.getBoolean(KEY_HAS_CONFIG, false)
    }

    /**
     * Save current active configuration
     */
    fun saveCurrentConfig(config: RemoteConfig) {
        prefs.edit().apply {
            putString(KEY_CURRENT_CONFIG, gson.toJson(config))
            putBoolean(KEY_HAS_CONFIG, true)
            apply()
        }
        
        // Also add to saved configs list
        addToSavedConfigs(config)
    }

    /**
     * Get current active configuration
     */
    fun getCurrentConfig(): RemoteConfig? {
        val json = prefs.getString(KEY_CURRENT_CONFIG, null) ?: return null
        return try {
            gson.fromJson(json, RemoteConfig::class.java)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Add configuration to saved list
     */
    private fun addToSavedConfigs(config: RemoteConfig) {
        val configs = getSavedConfigs().toMutableList()
        
        // Remove existing config with same ID
        configs.removeAll { it.id == config.id }
        
        // Add new config
        configs.add(config)
        
        // Save back
        prefs.edit().apply {
            putString(KEY_SAVED_CONFIGS, gson.toJson(configs))
            apply()
        }
    }

    /**
     * Get all saved configurations
     */
    fun getSavedConfigs(): List<RemoteConfig> {
        val json = prefs.getString(KEY_SAVED_CONFIGS, null) ?: return emptyList()
        return try {
            val type = object : TypeToken<List<RemoteConfig>>() {}.type
            gson.fromJson(json, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Delete a saved configuration
     */
    fun deleteConfig(configId: String) {
        val configs = getSavedConfigs().toMutableList()
        configs.removeAll { it.id == configId }
        
        prefs.edit().apply {
            putString(KEY_SAVED_CONFIGS, gson.toJson(configs))
            apply()
        }
    }

    /**
     * Clear all configurations
     */
    fun clearAllConfigs() {
        prefs.edit().clear().apply()
    }
}

