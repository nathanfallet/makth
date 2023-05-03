# Algorithm Lexer

A set of actions can be obtained using the algorithm lexer:

```kotlin
val raw = """
// This is an example algorithm
set(x, 2)
while (x < 10) {
    set(x, x + 1)
}
print("x = ", x)
"""
val actions = AlgorithmLexer(raw).execute()
val result = Context().execute(actions)
```