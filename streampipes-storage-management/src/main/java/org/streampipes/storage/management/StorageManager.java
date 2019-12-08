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

package org.streampipes.storage.management;

import org.eclipse.rdf4j.repository.Repository;
import org.streampipes.storage.Rdf4JStorageManager;
import org.streampipes.storage.api.IBackgroundKnowledgeStorage;
import org.streampipes.storage.api.IOntologyContextStorage;
import org.streampipes.storage.api.IPipelineElementDescriptionStorageCache;

public enum StorageManager {

  INSTANCE;

  public IPipelineElementDescriptionStorageCache getPipelineElementStorage() {
    return Rdf4JStorageManager.INSTANCE.getPipelineElementStorage();
  }

  public IBackgroundKnowledgeStorage getBackgroundKnowledgeStorage() {
    return Rdf4JStorageManager.INSTANCE.getBackgroundKnowledgeStorage();
  }

  public Repository getRepository() {
    return Rdf4JStorageManager.INSTANCE.getRepository();
  }

  public IOntologyContextStorage getContextStorage() {
    return Rdf4JStorageManager.INSTANCE.getContextStorage();
  }



}
