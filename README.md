# Icarus Framework


[![Build](https://travis-ci.org/merlinosayimwen/icarus.svg?branch=master)](https://travis-ci.org/merlinosayimwen/icarus/builds)
[![Java Version](https://img.shields.io/badge/java-v1.8-blue.svg)](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
[![Latest](https://img.shields.io/badge/latest-v1.0-blue.svg)](https://github.com/merlinosayimwen/icarus)
[![License](https://img.shields.io/badge/license-apache--2.0-lightgrey.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

Icarus is an extendable Caching-Framework, designed to increase
the performance of new or already existing, with just one little annotation. 

Why using it?
--

Plain Java code without any frameworks:
```java
final Map<Integer,Integer> cache = Maps.newHashMap();

int sum(final int... operands) {
  final int parameterHash = Objects.hash(operands);
  if(this.cache.containsKey(hash)) {
    return cache.get(hash);
  }
  
  final int sum = IntStream.of(operands).sum();
  this.cache.put(parameterHash, sum);
  return sum;
}
```

By using this framework following can be done like that:
```java
@Cached
int sum(final int... operands) {
  return IntStream.of(operands).sum();
}
```




Getting Started
--
You want to straight jump into using *Icarus*? The following section will show you how to 
implement it in to your project correctly.

To add Icarus to your **maven** dependencies following code can be used:
```xml
<dependency>
  <groupId>io.github.icarus</groupId>
  <artifactId>icarus-core</artifactId>
  <version>1.0</version>
</dependency>
```

When using **gradle** instead, following code can be used:
```groovy
dependencies {
  compile 'io.github.icarus:icarus-core:1.0'
}
```
