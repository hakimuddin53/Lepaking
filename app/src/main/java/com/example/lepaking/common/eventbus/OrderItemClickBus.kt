package com.example.lepaking.common.eventbus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

object OrderItemClickBus {

    private val bus = PublishSubject.create<OrderItemClick>()

    fun send(code:String, timeReceived:String){
        bus.onNext(OrderItemClick(code,timeReceived))
    }

    fun toObservable(): Observable<OrderItemClick> {
        return  bus
    }

    fun hasObservers(): Boolean {
        return bus.hasObservers()
    }

    data class OrderItemClick(val code: String, val timeReceived: String)
}