package com.example.apptuyendungvieclam.common.eventbus

interface ActionBus<Data> :BaseAction {
    fun call(data: Data)
}