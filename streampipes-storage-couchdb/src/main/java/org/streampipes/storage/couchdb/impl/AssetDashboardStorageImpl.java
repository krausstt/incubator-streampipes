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
package org.streampipes.storage.couchdb.impl;

import org.streampipes.model.client.assetdashboard.AssetDashboardConfig;
import org.streampipes.storage.api.IAssetDashboardStorage;
import org.streampipes.storage.couchdb.dao.AbstractDao;
import org.streampipes.storage.couchdb.utils.Utils;

import java.util.List;

public class AssetDashboardStorageImpl extends AbstractDao<AssetDashboardConfig> implements IAssetDashboardStorage {

  public AssetDashboardStorageImpl() {
    super(Utils::getCouchDbAssetDashboardClient, AssetDashboardConfig.class);
  }

  @Override
  public List<AssetDashboardConfig> getAllAssetDashboards() {
    return findAll();
  }

  @Override
  public AssetDashboardConfig getAssetDashboard(String dashboardId) {
    return findWithNullIfEmpty(dashboardId);
  }

  @Override
  public void storeAssetDashboard(AssetDashboardConfig assetDashboardConfig) {
    persist(assetDashboardConfig);
  }

  @Override
  public void deleteAssetDashboard(String dashboardId) {
    delete(dashboardId);
  }
}
