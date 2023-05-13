# Actions

You can execute actions to play with a context:

```kotlin
val result = Context().execute(listOf(
    SetAction("x", Integer.instantiate(2)),
    WhileAction(Equality(Variable.instantiate("x"), Integer.instantiate(10), Equality.Operator.LessThan), listOf(
        SetAction("x", Sum(Variable.instantiate("x"), Integer.instantiate(1)))
    )),
    PrintAction(listOf(StringValue("x = "), Variable.instantiate("x")))
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