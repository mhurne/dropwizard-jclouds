/*
 * Copyright (C) 2014 Commerce Technologies, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.commercehub.dropwizard.jclouds.blobstore;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import org.jclouds.ContextBuilder;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;

@JsonTypeName("aws-s3")
public class AWSS3ManagedBlobStoreContextFactory implements ManagedBlobStoreContextFactory {

    private String accessKeyId;
    private String secretKey;

    @Override
    public ManagedBlobStoreContext build() {
        Iterable<Module> modules = ImmutableSet.<Module>of(new SLF4JLoggingModule());
        BlobStoreContext context = ContextBuilder.newBuilder("aws-s3")
                .modules(modules)
                .credentials(accessKeyId, secretKey)
                .buildView(BlobStoreContext.class);
        return ManagedBlobStoreContext.of(context);
    }

    @JsonProperty
    public String getAccessKeyId() {
        return accessKeyId;
    }

    @JsonProperty
    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    @JsonProperty
    public String getSecretKey() {
        return secretKey;
    }

    @JsonProperty
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

}
