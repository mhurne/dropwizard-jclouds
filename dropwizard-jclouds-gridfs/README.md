# dropwizard-jclouds-gridfs

[![Build Status](https://travis-ci.org/commercehub-oss/dropwizard-jclouds.svg?branch=master)](https://travis-ci.org/commercehub-oss/dropwizard-jclouds)
[ ![Download](https://api.bintray.com/packages/commercehub-oss/main/dropwizard-jclouds-gridfs/images/download.svg) ](https://bintray.com/commercehub-oss/main/dropwizard-jclouds-gridfs/_latestVersion)

A library that supports use of the [Apache jclouds®](https://jclouds.apache.org/)
[MongoDB GridFS BlobStore provider](https://github.com/commercehub-oss/jclouds-gridfs-blobstore) in
[Dropwizard](http://dropwizard.io/) applications.

Currently, it includes a [configuration factory](http://dropwizard.io/manual/core.html#configuration) that can be used
to configure a `gridfs`
[ManagedBlobStoreContext](../dropwizard-jclouds-blobstore/src/main/java/com/commercehub/dropwizard/jclouds/blobstore/ManagedBlobStoreContext.java).

# Usage

First, add a dependency to your build file.  Releases are published to
[Bintray JCenter](https://bintray.com/bintray/jcenter).  See the [changelog](../CHANGES.md) for the latest version.

Gradle:

```groovy
...
repositories {
    jcenter()
}
...
dependencies {
    compile "com.commercehub.dropwizard:dropwizard-jclouds-gridfs:2.0.0"
}
...
```

Maven:

```xml
...
<repositories>
  <repository>
    <id>jcenter</id>
    <url>http://jcenter.bintray.com</url>
  </repository>
</repositories>
...
<dependency>
  <groupId>com.commercehub.dropwizard</groupId>
  <artifactId>dropwizard-jclouds-gridfs</artifactId>
  <version>2.0.0</version>
</dependency>
...
```

If you're using the [Gradle Shadow Plugin](https://github.com/johnrengelman/shadow) or the
[Maven Shade Plugin](http://maven.apache.org/plugins/maven-shade-plugin/) to package your application as an uber-jar,
ensure that `META-INF/services/com.commercehub.dropwizard.jclouds.blobstore.BlobStoreContextFactory` is properly merged.

Next, add a field of type `ManagedBlobStoreContextFactory` to your application's configuration class:

```java
public class AppConfiguration extends Configuration {

    @Valid
    @NotNull
    private ManagedBlobStoreContextFactory blobStoreContext;
    
    @JsonProperty
    public ManagedBlobStoreContextFactory getBlobStoreContext() {
        return blobStoreContext;
    }
    
    @JsonProperty
    public void setBlobStoreContext(ManagedBlobStoreContextFactory blobStoreContext) {
        this.blobStoreContext = blobStoreContext;
    }

}
```

In your `Application` class, use the `ManagedBlobStoreContextFactory` to build a `ManagedBlobStoreContext`:

```java
public class App extends Application<AppConfiguration> {

    @Override
    public void run(AppConfiguration config, Environment environment) {
        environment.lifecycle().manage(config.getBlobStoreContext().build());
    }
    
}
```

Finally, add the necessary bits to your configuration file:

```yaml
blobStoreContext:
  type: gridfs
  uri: mongodb://localhost:27017/?maxPoolSize=50&maxIdleTimeMS=300000
```

# License
This library is available under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

(c) All rights reserved Commerce Technologies, Inc.
