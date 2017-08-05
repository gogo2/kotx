# kotx
Kotx is a state management container with some tools. Concept is based on Vuex state manegement tools for Vue.js framework.
Some implementation ideas are influenced by [bansa](https://github.com/brianegan/bansa) similar tool, but based on Redux

##Usage
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
...
### Dispatch, commit, and get
...
### Subscribe
...
##TODO
* Vue.js plugin
* devtools
* more examples
