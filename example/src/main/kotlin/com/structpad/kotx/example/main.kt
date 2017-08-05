package com.structpad.kotx.example

import com.structpad.kotx.Action
import com.structpad.kotx.Getter
import com.structpad.kotx.Mutation
import com.structpad.kotx.Store

class State {
    var i: Int = 2
    var j: Int = 3
}

data class TT(val p: Int) : Mutation

object TT2 : Mutation {
    val p: Int = 6
    val q: Int = 7
}

data class DD(val p: Int) : Action

object gg : Getter
object g2 : Getter

fun main(args: Array<String>) {
    val store = Store(State())
    store.registerGetter<gg, Int> { state, get -> state.i }
    store.registerGetter<g2, Int> { state, get -> get(gg) }
    store.registerAction<DD>({ context, (p) ->
        context.commit(TT(p * context.state.j))
    })

    store.registerMutation<TT>({ state, (p) ->
        state.i *= p
        state
    })

    store.registerMutation<TT2>({
        state, x ->
        state.i = x.p
        state.j = x.q
        state
    })


    val subh = store.subscribe<TT>({ (p), _ -> println("sub$p") })
    val subh2 = store.subscribe<TT>({ (p), _ -> println("sub$p") })
    println(subh)
    println(subh2)
    store.dispatch(DD(2))
    store.dispatch(DD(2))
    store.unsubscribe<TT>(subh)
    store.unsubscribe<TT>(subh)
    store.dispatch(DD(3))
    println(store.get<Int>(gg))
    val ffff: Int = store.get(g2)
    println(ffff)


}