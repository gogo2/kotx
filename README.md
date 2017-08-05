# kotx
Kotx is a state management container with some tools. Its concept is based on [Vuex](https://vuex.vuejs.org/en/) state manegement tools for Vue.js framework.
Some implementation ideas are influenced by [bansa](https://github.com/brianegan/bansa), similar tool, but based on Redux

## Core concept
* Store object holds your application state
* State should be changed only by commiting mutations, not directly
* Mutations should be used only in actions
* To get data from state access it directly or use getters
* ...

To get a bette feel about kotx visit Vuex's [website](https://vuex.vuejs.org/en/)

## Usage

### Design your state and create a Store
You can define you own state class 
```kotlin
data class State(var username: String, var updates: Int = 0, var date: Date = Date()) 
val store = Store(State(username))
```
or, if your state is simple, use built in type or collection
```kotlin
val store = Store(0)
```
### Define and register your actions, mutations and getters
Every Action type should be defined as an object or class implementing Action interface.

```kotlin
object updateDate : Action
```
Mutations and getters are defined in a similar way. 
```kotlin
data class SetDate(val date: Date) : Mutation

object loggedUsername : Getter
object lastDate : Getter
```
Now you can define what your actions, mutations and getters will do simply by registering them as in the example below
```kotlin
store.registerAction<updateDate>({ context, _ ->
    val date = Date()
    context.commit(SetDate(date))
})
 
store.registerMutation<SetDate>({ state, (date) ->
    state.date = date
    state.updates += 1
    state
})
       
store.registerGetter<lastDate, Date>({ state, _ -> state.date })
store.registerGetter<appStatus, String>({ state, get ->
   "${get(loggedUsername)} updated date ${state.updates} times \n last date: ${state.date}"
})
```
### Dispatch, commit and get
```kotlin
store.dispatch(updateDate)

context.commit(SetDate(date))

store.get(lastDate)
```
Take a look on my [example](example)
### Subscribe
Function passed to `subscribe` function will be called every time when mutation given by generic parameter is commited.
```kotlin
store.subscribe<SetDate>({ _, _ -> printStatus() })
```


## TODO

- [ ] Vue.js plugin
- [ ] devtools
- [ ] more examples
