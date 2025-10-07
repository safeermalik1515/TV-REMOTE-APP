package com.tvremote.app

import android.content.Context
import android.hardware.ConsumerIrManager
import android.os.Build

class IRController(private val context: Context) {

    private val irManager: ConsumerIrManager? = 
        context.getSystemService(Context.CONSUMER_IR_SERVICE) as? ConsumerIrManager

    // Check if device has IR blaster
    fun hasIrEmitter(): Boolean {
        return irManager?.hasIrEmitter() ?: false
    }

    // Get carrier frequencies
    fun getCarrierFrequencies(): IntArray? {
        return irManager?.carrierFrequencies?.map { it.minFrequency }?.toIntArray()
    }

    /**
     * Convert hex string pattern to int array pattern
     * NEC Protocol format commonly used in TV remotes
     */
    private fun hexStringToPattern(hexString: String): IntArray {
        val hex = hexString.replace(" ", "")
        val pattern = mutableListOf<Int>()
        
        for (i in hex.indices step 4) {
            if (i + 3 < hex.length) {
                val value = hex.substring(i, i + 4).toInt(16)
                pattern.add(value)
            }
        }
        
        return pattern.toIntArray()
    }

    /**
     * Generic NEC protocol pattern generator
     * NEC protocol: 38kHz carrier, 9ms AGC burst + 4.5ms space + address + ~address + command + ~command
     */
    private fun generateNECPattern(address: Int, command: Int): IntArray {
        val pattern = mutableListOf<Int>()
        
        // NEC Start: 9ms burst + 4.5ms space
        pattern.add(342) // 9ms at 38kHz
        pattern.add(171) // 4.5ms space
        
        // Send address (8 bits)
        addBits(pattern, address, 8)
        
        // Send inverted address (8 bits)
        addBits(pattern, address.inv() and 0xFF, 8)
        
        // Send command (8 bits)
        addBits(pattern, command, 8)
        
        // Send inverted command (8 bits)
        addBits(pattern, command.inv() and 0xFF, 8)
        
        // End burst
        pattern.add(21) // Final burst
        
        return pattern.toIntArray()
    }

    /**
     * Add bits to pattern (LSB first)
     */
    private fun addBits(pattern: MutableList<Int>, data: Int, bitCount: Int) {
        for (i in 0 until bitCount) {
            // Burst
            pattern.add(21) // 0.56ms burst
            
            // Space (logical 0 = 0.56ms, logical 1 = 1.69ms)
            if ((data shr i) and 1 == 1) {
                pattern.add(63) // Logical 1: 1.69ms space
            } else {
                pattern.add(21) // Logical 0: 0.56ms space
            }
        }
    }

    /**
     * Transmit IR pattern
     */
    private fun transmit(frequency: Int, pattern: IntArray): Boolean {
        return try {
            irManager?.transmit(frequency, pattern)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // TV Command definitions using NEC protocol
    // These are generic codes - may need adjustment for specific TV brands
    private val NEC_FREQUENCY = 38000 // 38kHz
    private val TV_ADDRESS = 0x00 // Generic TV address
    
    companion object {
        // NEC Command codes for common TV functions
        const val CMD_POWER = 0x12
        const val CMD_MUTE = 0x10
        const val CMD_VOL_UP = 0x16
        const val CMD_VOL_DOWN = 0x17
        const val CMD_CH_UP = 0x20
        const val CMD_CH_DOWN = 0x21
        const val CMD_UP = 0x1A
        const val CMD_DOWN = 0x1B
        const val CMD_LEFT = 0x19
        const val CMD_RIGHT = 0x18
        const val CMD_OK = 0x1C
        const val CMD_SOURCE = 0x0B
    }

    // Public methods to send commands
    fun sendPower(): Boolean {
        val pattern = generateNECPattern(TV_ADDRESS, CMD_POWER)
        return transmit(NEC_FREQUENCY, pattern)
    }

    fun sendMute(): Boolean {
        val pattern = generateNECPattern(TV_ADDRESS, CMD_MUTE)
        return transmit(NEC_FREQUENCY, pattern)
    }

    fun sendVolumeUp(): Boolean {
        val pattern = generateNECPattern(TV_ADDRESS, CMD_VOL_UP)
        return transmit(NEC_FREQUENCY, pattern)
    }

    fun sendVolumeDown(): Boolean {
        val pattern = generateNECPattern(TV_ADDRESS, CMD_VOL_DOWN)
        return transmit(NEC_FREQUENCY, pattern)
    }

    fun sendChannelUp(): Boolean {
        val pattern = generateNECPattern(TV_ADDRESS, CMD_CH_UP)
        return transmit(NEC_FREQUENCY, pattern)
    }

    fun sendChannelDown(): Boolean {
        val pattern = generateNECPattern(TV_ADDRESS, CMD_CH_DOWN)
        return transmit(NEC_FREQUENCY, pattern)
    }

    fun sendUp(): Boolean {
        val pattern = generateNECPattern(TV_ADDRESS, CMD_UP)
        return transmit(NEC_FREQUENCY, pattern)
    }

    fun sendDown(): Boolean {
        val pattern = generateNECPattern(TV_ADDRESS, CMD_DOWN)
        return transmit(NEC_FREQUENCY, pattern)
    }

    fun sendLeft(): Boolean {
        val pattern = generateNECPattern(TV_ADDRESS, CMD_LEFT)
        return transmit(NEC_FREQUENCY, pattern)
    }

    fun sendRight(): Boolean {
        val pattern = generateNECPattern(TV_ADDRESS, CMD_RIGHT)
        return transmit(NEC_FREQUENCY, pattern)
    }

    fun sendOk(): Boolean {
        val pattern = generateNECPattern(TV_ADDRESS, CMD_OK)
        return transmit(NEC_FREQUENCY, pattern)
    }

    fun sendSource(): Boolean {
        val pattern = generateNECPattern(TV_ADDRESS, CMD_SOURCE)
        return transmit(NEC_FREQUENCY, pattern)
    }

    /**
     * Alternative: Raw pattern transmission for specific TV brands
     * You can replace these patterns with brand-specific codes
     */
    fun sendRawPattern(hexPattern: String, frequency: Int = 38000): Boolean {
        val pattern = hexStringToPattern(hexPattern)
        return transmit(frequency, pattern)
    }

    /**
     * Send custom command with configurable address and command code
     */
    fun sendCustomCommand(address: Int, command: Int, frequency: Int = 38000): Boolean {
        val pattern = generateNECPattern(address, command)
        return transmit(frequency, pattern)
    }

    /**
     * Send commands using RemoteConfig
     */
    fun sendWithConfig(config: RemoteConfig, commandType: CommandType): Boolean {
        val command = when (commandType) {
            CommandType.POWER -> config.powerCode
            CommandType.MUTE -> config.muteCode
            CommandType.VOLUME_UP -> config.volumeUpCode
            CommandType.VOLUME_DOWN -> config.volumeDownCode
            CommandType.CHANNEL_UP -> config.channelUpCode
            CommandType.CHANNEL_DOWN -> config.channelDownCode
            CommandType.UP -> config.upCode
            CommandType.DOWN -> config.downCode
            CommandType.LEFT -> config.leftCode
            CommandType.RIGHT -> config.rightCode
            CommandType.OK -> config.okCode
            CommandType.SOURCE -> config.sourceCode
        }
        return sendCustomCommand(config.address, command, config.frequency)
    }

    enum class CommandType {
        POWER, MUTE, VOLUME_UP, VOLUME_DOWN,
        CHANNEL_UP, CHANNEL_DOWN,
        UP, DOWN, LEFT, RIGHT, OK, SOURCE
    }
}

