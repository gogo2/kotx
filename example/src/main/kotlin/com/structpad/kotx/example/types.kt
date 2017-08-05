package com.structpad.kotx.example

import com.structpad.kotx.Action
import com.structpad.kotx.Getter
import com.structpad.kotx.Mutation
import java.util.*

object updateDate : Action
data class UpdateUsername(val newUsername: String) : Action
object updateIfDiffWeekday : Action

data class SetDate(val date: Date) : Mutation
data class SetUsername(val newName: String) : Mutation

object loggedUsername : Getter
object lastDate : Getter
object appStatus : Getter
