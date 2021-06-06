package com.mistar.memo.domain.model.entity.user.flag

abstract class FlagExpression {

    var operand: Int? = null
    var reverse: Boolean = false

    abstract infix fun evaluate(flag: FlagDefinition): Boolean
}

open class BeExpression : FlagExpression() {

    override infix fun evaluate(flag: FlagDefinition): Boolean {
        if (reverse) return !be(flag.value)
        return be(flag.value)
    }

    private fun be(flag: Int): Boolean {
        if (operand == null) return false
        return operand?.xor(flag)?.and(operand?.inv()!!) == 0
    }
}

val be: FlagExpression = BeExpression()