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

import com.google.common.reflect.TypeToken;
import io.dropwizard.lifecycle.Managed;
import org.jclouds.Context;
import org.jclouds.blobstore.AsyncBlobStore;
import org.jclouds.blobstore.BlobRequestSigner;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.attr.ConsistencyModel;
import org.jclouds.rest.Utils;

import java.io.Closeable;

public class ManagedBlobStoreContext implements BlobStoreContext, Managed {

    private final BlobStoreContext context;

    private ManagedBlobStoreContext(BlobStoreContext context) {
        this.context = context;
    }

    static ManagedBlobStoreContext of(BlobStoreContext context) {
        return new ManagedBlobStoreContext(context);
    }

    @Override
    public TypeToken<?> getBackendType() {
        return context.getBackendType();
    }

    @Override
    public <C extends Context> C unwrap(TypeToken<C> type) throws IllegalArgumentException {
        return context.unwrap(type);
    }

    @Override
    public <C extends Context> C unwrap() throws ClassCastException {
        return context.unwrap();
    }

    @Override
    public <A extends Closeable> A unwrapApi(Class<A> apiClass) {
        return context.unwrapApi(apiClass);
    }

    @Override
    public BlobRequestSigner getSigner() {
        return context.getSigner();
    }

    @Override
    @Deprecated
    public AsyncBlobStore getAsyncBlobStore() {
        return context.getAsyncBlobStore();
    }

    @Override
    public BlobStore getBlobStore() {
        return context.getBlobStore();
    }

    @Override
    public ConsistencyModel getConsistencyModel() {
        return context.getConsistencyModel();
    }

    @Override
    public Utils utils() {
        return context.utils();
    }

    @Override
    public void close() {
        context.close();
    }

    @Override
    public void start() throws Exception {
    }

    @Override
    public void stop() throws Exception {
        close();
    }

}
