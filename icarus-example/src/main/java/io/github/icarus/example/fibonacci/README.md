# The Fibonacci example
Why using a fibonacci sequence calculator as an example for a caching framework?
Well generating fibonacci sequences may take some time when precision is important. 
Caching results of older calculations can save a lot of it. The example is even better because
it shows how the `CachedBy` annotation is useful and needed.
 
Let's take a look
at the `calculate(int,CalculationApproach)` method in the `FibonacciCalculator` class.

While allowing to perform different approaches in order to calculate fibonacci sequences, e
very approach should lead to the same result and the onliest argument that is important is the `depth`.
The approach taken can simply be ignored when caching, thus giving a different
approach as an argument will not calculate the fibonacci again but also look the `depth` up in the cache.

What it shows:
---

Using the `CachedBy` annotation makes sense when not all arguments are needed in order
to process the result. 