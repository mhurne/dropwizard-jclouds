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
import com.mongodb.MongoClientURI;
import org.jclouds.Constants;
import org.jclouds.ContextBuilder;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;

import java.util.Properties;

@JsonTypeName("gridfs")
public class GridFSManagedBlobStoreContextFactory implements ManagedBlobStoreContextFactory {

    private MongoClientURI uri;

    @Override
    public ManagedBlobStoreContext build() {
        Properties overrides = new Properties();
        overrides.setProperty(Constants.PROPERTY_ENDPOINT, uri.toString());
        Iterable<Module> modules = ImmutableSet.<Module>of(new SLF4JLoggingModule());
        BlobStoreContext context = ContextBuilder.newBuilder("gridfs")
                .modules(modules)
                .overrides(overrides)
                .buildView(BlobStoreContext.class);
        return ManagedBlobStoreContext.of(context);
    }

    @JsonProperty
    public MongoClientURI getUri() {
        return uri;
    }

    @JsonProperty
    public void setUri(MongoClientURI uri) {
        this.uri = uri;
    }

}
