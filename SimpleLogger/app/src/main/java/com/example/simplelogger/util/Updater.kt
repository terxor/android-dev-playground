package com.example.simplelogger.util

import com.example.simplelogger.ui.SimpleLoggerViewModel
import java.util.*

class Updater(private val viewModel: SimpleLoggerViewModel): TimerTask() {
    override fun run() {
        viewModel.async_update()
    }
}