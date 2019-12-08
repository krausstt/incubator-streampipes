/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.streampipes.storage.couchdb.dao;

import org.lightcouch.CouchDbClient;
import org.lightcouch.NoDocumentException;

import java.util.function.Supplier;

public class DeleteCommand<T> extends DbCommand<Boolean, T> {

  private String key;

  public DeleteCommand(Supplier<CouchDbClient> couchDbClient, String key, Class<T> clazz) {
    super(couchDbClient, clazz);
    this.key = key;
  }

  @Override
  protected Boolean executeCommand(CouchDbClient couchDbClient) {
    try {
      T result = couchDbClient.find(clazz, key);
      couchDbClient.remove(result);
      return true;
    } catch (NoDocumentException e) {
      return false;
    }
  }
}
