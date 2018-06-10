[![Picture](https://orig00.deviantart.net/913a/f/2013/007/c/1/icarus_sketch_by_insomniacattack-d5qszxs.png)]()

# The Icarus Framework (WIP)

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

So and now... How do you cache results of method?
Thats very easily done with our annotation-api.
You just need to annotate your method and the framework caches it for you.

Following **Java** code shows how to implement *Icarus*:
```java
  @Cached
  @Expires(3L)
  public Stream<File> findFilesWithinDirectory(final File root) {
    Preconditions.checkNotNull(root);
    
    if(!root.isDirectory()) {
      return Stream.empty();
    }
    
    //... Scan the contents of the root
  }
```
The code simply searches for Files and sub-directories and collects them.
This can take some time and you might want to remember the files within a root for some time.
*Icarus* has a great feature, it allows you to return a lot of stuff even streams and it will
still cache it in such a manner, that it can be reused. When you have worked with streams bevor, 
you probably know that they can't be operated upon multiple times. It would be fatal to simply 
cache the instances that have been used allready. What *Icarus* does is, defining special cases
in which a type is first converted bevor it will be put into the cache and then converted back, when accessed.
You can actually write your own cases in so called: *IcarusModules*.

Common Pitfalls
--
Using *Icarus* will introduce some pitfalls to you application. The framework does only take account
the parameters of your method. Variables of the instance that the method is invoked one will simply 
be ignored. Even though clearing of the cache is an opportunity when an important variable of your object
has changed, you should try to only cache results which are only calculated based on their arguments
and not some mutable state of their object. 

