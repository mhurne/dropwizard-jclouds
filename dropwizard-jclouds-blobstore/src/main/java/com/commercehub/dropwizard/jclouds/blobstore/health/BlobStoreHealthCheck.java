package com.commercehub.dropwizard.jclouds.blobstore.health;

import com.codahale.metrics.health.HealthCheck;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;

import static com.google.common.base.Preconditions.*;

public class BlobStoreHealthCheck extends HealthCheck {

    private final BlobStoreContext blobStoreContext;
    private final String container;

    public BlobStoreHealthCheck(BlobStoreContext blobStoreContext, String container) {
        this.blobStoreContext = checkNotNull(blobStoreContext);
        this.container = checkNotNull(container);
        checkArgument(!this.container.isEmpty() && !this.container.trim().isEmpty());
    }

    @Override
    protected Result check() throws Exception {
        BlobStore blobStore = blobStoreContext.getBlobStore();
        if (blobStore.containerExists(container)) {
            return Result.healthy();
        }
        return Result.unhealthy("container does not exist: %s", container);
    }

}
