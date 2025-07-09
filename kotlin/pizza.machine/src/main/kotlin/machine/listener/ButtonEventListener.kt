package org.example.machine.listener

import org.example.machine.event.ActivationEvent


interface ButtonEventListener {
    var isActive: Boolean

    fun onActivated(e: ActivationEvent)

    fun onPressed()
}