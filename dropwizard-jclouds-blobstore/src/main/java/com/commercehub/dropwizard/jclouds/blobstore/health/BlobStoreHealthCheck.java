package com.commercehub.dropwizard.jclouds.blobstore.health;

import com.codahale.metrics.health.HealthCheck;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.domain.Blob;
import org.jclouds.io.Payload;

import java.util.UUID;

public class BlobStoreHealthCheck extends HealthCheck {

    private final BlobStoreContext blobStoreContext;
    private final String container;
    private final String blobName;
    private final Payload payload;
    private final String contentType;

    public BlobStoreHealthCheck(BlobStoreContext blobStoreContext,
                                String container,
                                String blobNamePrefix,
                                Payload payload,
                                String contentType) {
        this.blobStoreContext = blobStoreContext;
        this.container = container;
        this.blobName = blobNamePrefix + "/" + UUID.randomUUID();
        this.payload = payload;
        this.contentType = contentType;
    }

    @Override
    protected Result check() throws Exception {
        BlobStore blobStore = blobStoreContext.getBlobStore();
        Blob blob = blobStore.blobBuilder(blobName)
                .payload(payload)
                .contentType(contentType)
                .build();
        blobStore.putBlob(container, blob);
        if (blobStore.blobExists(container, blobName)) {
            blobStore.removeBlob(container, blobName);
            return Result.healthy();
        }
        return Result.unhealthy("unable to store file in blobStore");
    }

}
