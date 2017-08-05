package com.structpad.kotx.example

import com.structpad.kotx.Store
import java.util.*


class App(val username: String) {
    val store = Store(State(username))

    init {
        registerActions()
        registerMutations()
        registerGetters()

        store.subscribe<SetDate>({ _, _ -> printStatus() })
    }

    fun printStatus() {
        println(store.get<String>(appStatus))
    }

    fun refresh() {
        store.dispatch(updateDate)
    }

    private fun registerActions() {
        store.registerAction<updateDate>({ context, _ ->
            val date = Date()
            context.commit(SetDate(date))
        })
        store.registerAction<UpdateUsername>({ context, (username) ->
            context.commit(SetUsername(username))
        })
        store.registerAction<updateIfDiffWeekday>({ context, _ ->
            val cal = Calendar.getInstance()
            val current = cal.get(Calendar.DAY_OF_WEEK)
            val inStoreDate: Date = context.state.date
            //or context.get(lastDate)
            cal.time = inStoreDate
            val inStore = cal.get(Calendar.DAY_OF_WEEK)
            if (current != inStore)
                context.commit(SetDate(Date()))
        })
    }

    private fun registerMutations() {
        store.registerMutation<SetDate>({ state, (date) ->
            state.date = date
            state.updates += 1
            state
        })
        store.registerMutation<SetUsername>({ state, (username) ->
            state.username = username
            state.updates = 0
            state
        })
    }

    private fun registerGetters() {
        store.registerGetter<lastDate, Date>({ state, _ -> state.date })
        store.registerGetter<loggedUsername, String>({ state, _ -> state.username })
        store.registerGetter<appStatus, String>({ state, get ->
            "${get(loggedUsername)} updated date ${state.updates} times \n last date: ${state.date}"
        })
    }


}