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

package org.streampipes.storage.rdf4j.config;


import org.streampipes.config.SpConfig;

import java.io.File;

public enum Rdf4JConfig {
  INSTANCE;

  private SpConfig config;

  private static final String BACKGROUND_KNOWLEDGE_STORAGE = "backgroundknowledge";
  private static final String PIPELINE_ELEMENT_STORAGE = "pipelineelements";

  Rdf4JConfig() {
    config = SpConfig.getSpConfig("storage/rdf4j");

    config.register(BACKGROUND_KNOWLEDGE_STORAGE, makeBackgroundStorageLocation(), "Directory of " +
            "the RDF4J native store directory (background knowledge)");
      config.register(PIPELINE_ELEMENT_STORAGE, makePipelineElementStorageLocation(), "Directory of " +
              "the RDF4J native store directory (pipeline element knowledge)");
  }

  private String makeBackgroundStorageLocation() {
    return makeStorageLocation()
            + "background";
  }

  private String makePipelineElementStorageLocation() {
    return makeStorageLocation()
            + "pipelineelements";
  }

  private String makeStorageLocation() {
    return System.getProperty("user.home")
            + File.separator
            + ".streampipes"
            + File.separator
            + "storage"
            + File.separator;
  }

  public String getBackgroundKnowledgeStorageLocation() {
      return config.getString(BACKGROUND_KNOWLEDGE_STORAGE);
  }

  public String getPipelineElementStorageLocation() {
      return config.getString(PIPELINE_ELEMENT_STORAGE);
  }


}
