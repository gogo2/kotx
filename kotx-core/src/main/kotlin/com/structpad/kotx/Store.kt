package com.structpad.kotx

open class Store<S>(var state: S) {

    @PublishedApi internal var mutations: MutationsMap<S> = mutableMapOf()

    @PublishedApi internal var actions: ActionsMap<ActionContext> = mutableMapOf()

    @PublishedApi internal var getters: GettersMap<S> = mutableMapOf()

    @PublishedApi internal var subscribers: SubscribersMap<S> = mutableMapOf()

    fun commit(mutation: Mutation) {
        @Suppress("UNCHECKED_CAST")
        state = mutations[mutation::class]?.invoke(state, mutation) as S ?: state
        for (subscriber in subscribers[mutation::class].orEmpty())
            subscriber(mutation, state)
    }

    fun dispatch(action: Action) {
        actions[action::class]?.invoke(ActionContext(), action)
    }

    fun <R> get(getter: Getter): R {
        @Suppress("UNCHECKED_CAST")
        return getters[getter::class]?.invoke(state) as R
    }

    inline fun <reified A : Action> registerAction(noinline actionHandler: ActionHandler<ActionContext, A>) {
        @Suppress("UNCHECKED_CAST")
        actions.put(A::class, actionHandler as ActionHandler<ActionContext, Action>)
    }

    inline fun <reified M : Mutation> registerMutation(noinline mutationHandler: MutationHandler<S, M>) {
        @Suppress("UNCHECKED_CAST")
        mutations.put(M::class, mutationHandler as MutationHandler<S, Mutation>)
    }

    inline fun <reified G : Getter, R> registerGetter(noinline getter: GetterFunc<S, R>) {
        @Suppress("UNCHECKED_CAST")
        getters.put(G::class, getter as GetterFunc<S, Any>)
    }

    inline fun <reified M : Mutation> subscribe(noinline handler: SubscriptionHandler<S, M>) {
        @Suppress("UNCHECKED_CAST")
        subscribers[M::class]?.add(handler as SubscriptionHandler<S, Mutation>) ?:
                subscribers.put(M::class, mutableListOf(handler as SubscriptionHandler<S, Mutation>))
    }

    inner class ActionContext {
        val state = this@Store.state
        val commit = this@Store::commit
        val dispatch = this@Store::dispatch
        fun <R> get(getter: Getter): R {
            return this@Store.get(getter)
        }
    }

}
