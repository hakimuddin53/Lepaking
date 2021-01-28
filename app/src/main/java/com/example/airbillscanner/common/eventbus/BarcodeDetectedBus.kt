package com.example.airbillscanner.common.eventbus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

object BarcodeDetectedBus {

    private val bus = PublishSubject.create<String>()

    fun send(barcode: String){
        bus.onNext(barcode)
    }

    fun toObservable(): Observable<String> {
        return bus
    }

    fun hasObservers(): Boolean {
        return bus.hasObservers()
    }
}