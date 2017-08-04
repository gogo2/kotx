package com.structpad.kotx

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

fun main(args: Array<String>) {
    val store = Store(State())
    store.registerGetter<gg, Int> { state -> state.i }
    val dd = DD(4)
    store.registerAction<DD>({ context, (p) ->
        println(context.get<Int>(gg))
        context.commit(TT(p * context.state.j))
        println(context.get<Int>(gg))
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

    store.dispatch(dd)


    val k: Int = store.get(gg)

    println(k)

    println(store.state.i)
    println(store.state.j)
    store.commit(TT2)

    println(store.state.i)
    println(store.state.j)


}