[![Picture](https://orig00.deviantart.net/913a/f/2013/007/c/1/icarus_sketch_by_insomniacattack-d5qszxs.png)]()

# The Icarus Framework

[![Java Version](https://img.shields.io/badge/java-v1.8-blue.svg)](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
[![Latest](https://img.shields.io/badge/latest-v1.0-blue.svg)](https://github.com/merlinosayimwen/icarus)
[![License](https://img.shields.io/badge/license-apache--2.0-lightgrey.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

Icarus is a high-performance caching framework,running on the java platform, that allows you to 
easily cache results of past calculations. The use of annotations makes it easy for you to implement Icarus in your
projects without much effort.

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

Common Pitfalls
--
Using *Icarus* will introduce some pitfalls to you application. The framework does only take account
the parameters of your method. Variables of the instance that the method is invoked one will simply 
be ignored. Even though clearing of the cache is an opportunity when an important variable of your object
has changed, you should try to only cache results which are only calculated based on their arguments
and not some mutable state of their object. 

