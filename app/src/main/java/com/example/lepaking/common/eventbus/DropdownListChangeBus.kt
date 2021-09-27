package com.example.lepaking.common.eventbus

import com.example.lepaking.model.ui.DropdownList
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

object DropdownListChangeBus {

    private val bus = PublishSubject.create<DropdownList>()

    fun send(dropdownList : DropdownList){
        bus.onNext(dropdownList)
    }

    fun toObservable(): Observable<DropdownList> {
        return bus
    }

    fun hasObservers(): Boolean {
        return bus.hasObservers()
    }
}