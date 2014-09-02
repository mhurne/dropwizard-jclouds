# Overview

A library that supports using the [Apache jclouds®](https://jclouds.apache.org/) BlobStore API in
[Dropwizard](http://dropwizard.io/) applications.

Currently, it includes a [Managed](http://dropwizard.io/manual/core.html#managed-objects)
[BlobStoreContext](https://jclouds.apache.org/reference/javadoc/1.8.x/org/jclouds/blobstore/BlobStoreContext.html) and
an associated [configuration factory](http://dropwizard.io/manual/core.html#configuration) interface. In most cases you
won't want to use this library directly. Instead, you'll use it as a transitive dependency of one or more of the other
dropwizard-jclouds-* libraries.

# Usage

First, add a dependency to your build file.  Releases are published to
[Bintray JCenter](https://bintray.com/bintray/jcenter).  See the [changelog](../CHANGES.md) for the latest version.

Gradle:

```groovy
dependencies {
    compile "com.commercehub.dropwizard:dropwizard-jclouds-blobstore:1.0.0"
}
```

Maven:

```xml
<dependency>
  <groupId>com.commercehub.dropwizard</groupId>
  <artifactId>dropwizard-jclouds-blobstore</artifactId>
  <version>1.0.0</version>
</dependency>
```

Now you can wrap your `BlobStoreContext` in a `ManagedBlobStoreContext`:

```java
public class App extends Application<AppConfiguration> {

    @Override
    public void run(AppConfiguration config, Environment environment) {
        environment.lifecycle().manage(ManagedBlobStoreContext.of(blobStoreContext));
    }
    
}
```

# License
This library is available under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

(c) All rights reserved Commerce Technologies, Inc.
