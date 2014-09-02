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
import org.jclouds.filesystem.reference.FilesystemConstants;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;

import java.util.Properties;

@JsonTypeName("filesystem")
public class FilesystemManagedBlobStoreContextFactory implements ManagedBlobStoreContextFactory {

    private String baseDir;

    @Override
    public ManagedBlobStoreContext build() {
        Properties overrides = new Properties();
        if (baseDir != null && baseDir.trim().length() > 0) {
            overrides.setProperty(FilesystemConstants.PROPERTY_BASEDIR, baseDir);
        }
        Iterable<Module> modules = ImmutableSet.<Module>of(new SLF4JLoggingModule());
        BlobStoreContext context = ContextBuilder.newBuilder("filesystem")
                .modules(modules)
                .overrides(overrides)
                .buildView(BlobStoreContext.class);
        return ManagedBlobStoreContext.of(context);
    }

    @JsonProperty
    public String getBaseDir() {
        return baseDir;
    }

    @JsonProperty
    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

}
