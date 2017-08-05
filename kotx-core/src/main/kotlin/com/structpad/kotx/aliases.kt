package com.structpad.kotx

import kotlin.reflect.KClass


typealias ActionsMap<C> = MutableMap<KClass<*>, ActionHandler<C, Action>>
typealias MutationsMap<S> = MutableMap<KClass<*>, MutationHandler<S, Mutation>>
typealias SubscribersMap<S> = MutableMap<KClass<*>, MutableList<SubscriptionHandler<S, Mutation>>>
typealias GettersMap<S> = MutableMap<KClass<*>, GetterFunc<S, Any>>

typealias MutationHandler<S, M> = (S, M) -> S?
typealias ActionHandler<C, A> = (C, A) -> Unit
typealias SubscriptionHandler<S, M> = (M, S) -> Unit
typealias GetterFunc<S, R> = (S, (Getter) -> R) -> R

