# Changelog


*   4.0.0
    *   Update jclouds version to 1.9.2. AsyncBlobStore is no longer supported. 
    
*   3.0.0
    *   Update dropwizard version to 0.9.2 
    
*   2.0.0
    *   Rewrite BlobStoreHealthCheck to simply check whether a given container exists rather than attempting operations on a blob, which has proven to be unreliable

*   1.2.0
    *   Update BlobStoreHealthCheck to take a `blobNamePrefix` instead of a complete `blobName` so that health checks in multi-node installations don't interfere with each other

*   1.1.0
    *   Add blobStore health check class

*   1.0.2
    *   Fix issue where dependencies were incorrectly listed in the runtime scope in published Maven POM files

*   1.0.1
    *   Build fixes

*   1.0.0
    *   Initial release
