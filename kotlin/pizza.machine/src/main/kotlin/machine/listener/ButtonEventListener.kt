package org.example.machine.listener


interface ButtonEventListener {
    var isActive: Boolean

    fun onButtonActivation() {
        // Logic to handle button activation
        isActive = true
    }

    fun onButtonDeactivation() {
        // Logic to handle button deactivation
        isActive = false
    }
}