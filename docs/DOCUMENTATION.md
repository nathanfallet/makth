# Makth documentation

## Instantiate numbers

```kotlin
val integer = Integer.instantiate(2) // 2
val rational = Rational.instantiate(3, 2) // 3/2
val real = Real.instantiate(1.23456789) // 1.23456789
```

## Predefined constants

```kotlin
val pi = Real.pi
```

## Operations

```kotlin
a.sum(b) // a + b
a.multiply(b) // a * b
a.divide(b) // a / b
```

## Math Lexer

To use the math lexer, you should define a context first:

```kotlin
val context = Context()
```

Then, call the lexer with this context:

```kotlin
val result = MathLexer("1 + 2").execute(context) // 3
```

Thanks to the context, you can pass variables:

```kotlin
val context = Context(mapOf(
    "x" to Integer.instantiate(2)
))
val result = MathLexer("x + 3").execute(context) // 5
```

## Actions

You can execute actions to play with a context:

```kotlin
val result = Context().execute(listOf(
    SetAction("x", Integer.instantiate(2)),
    WhileAction(Equality(Variable("x"), Integer.instantiate(10), Equality.Operator.LessThan), listOf(
        SetAction("x", Sum(Variable("x"), Integer.instantiate(1)))
    )),
    PrintAction(listOf(StringValue("x = "), Variable("x")))
))
```

`result` will looks like this:

```kotlin
Context(
    mapOf(
        "x" to Integer.instantiate(10) // The value of x
    ),
    listOf(
        StringValue("x = "), Integer.instantiate(10), StringValue("\n") // What we printed
    )
)
```

## Algorithm Lexer

The same set of actions can be obtained using the algorithm lexer:

```kotlin
val raw = """
set(x, 2)
while (x < 10) {
    set(x, x + 1)
}
print("x = ", x)
"""
val actions = AlgorithmLexer(raw).execute()
val result = Context().execute(actions)
```
