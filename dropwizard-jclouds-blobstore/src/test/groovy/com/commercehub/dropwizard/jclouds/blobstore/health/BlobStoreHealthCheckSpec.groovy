package com.commercehub.dropwizard.jclouds.blobstore.health

import org.jclouds.blobstore.BlobStore
import org.jclouds.blobstore.BlobStoreContext
import spock.lang.Specification
import spock.lang.Unroll

class BlobStoreHealthCheckSpec extends Specification {

    private static final String CONTAINER_NAME_FOO = "foo"

    def blobStore = Stub(BlobStore)
    def blobStoreContext = Stub(BlobStoreContext)

    BlobStoreHealthCheck healthCheck

    def setup() {
        blobStoreContext.getBlobStore() >> blobStore
    }

    @Unroll
    def "constructor throws #expectedExceptionType when passed blobStoreContext [#context] and container ['#container']"() {
        when:
            healthCheck = new BlobStoreHealthCheck(context, container)

        then:
            thrown(expectedExceptionType)

        where:
            context                | container          | expectedExceptionType
            null                   | CONTAINER_NAME_FOO | NullPointerException
            Stub(BlobStoreContext) | null               | NullPointerException
            Stub(BlobStoreContext) | ''                 | IllegalArgumentException
            Stub(BlobStoreContext) | '       '          | IllegalArgumentException
    }

    def "is healthy when container exists"() {
        given:
            blobStore.containerExists(_) >> true
            healthCheck = new BlobStoreHealthCheck(blobStoreContext, CONTAINER_NAME_FOO)

        expect:
            healthCheck.execute().healthy
    }

    def "is unhealthy when container does not exist"() {
        given:
            blobStore.containerExists(_) >> false
            healthCheck = new BlobStoreHealthCheck(blobStoreContext, CONTAINER_NAME_FOO)

        expect:
            !healthCheck.execute().healthy
    }

    def "is unhealthy when exception is thrown while checking whether container exists"() {
        given:
            blobStore.containerExists(_) >> { throw new RuntimeException("something went wrong") }
            healthCheck = new BlobStoreHealthCheck(blobStoreContext, CONTAINER_NAME_FOO)

        expect:
            !healthCheck.execute().healthy
    }

}
