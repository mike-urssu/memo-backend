package com.mistar.memo.domain.model.entity.user.flag

abstract class Flag<E : FlagDefinition> {
    abstract val value: Int

    infix fun can(expression: FlagExpression): (FlagDefinition) -> Boolean {
        expression.operand = value
        expression.reverse = false

        return expression::evaluate
    }

    infix fun cant(expression: FlagExpression): (FlagDefinition) -> Boolean {
        expression.operand = value
        expression.reverse = true

        return expression::evaluate
    }
}