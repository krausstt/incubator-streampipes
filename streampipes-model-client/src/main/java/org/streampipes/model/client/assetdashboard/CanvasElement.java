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
package org.streampipes.model.client.assetdashboard;

import java.util.List;

public class CanvasElement {

  private CanvasAttributes attrs;
  private String className;
  private List<CanvasElement> children;

  public CanvasElement() {
  }

  public CanvasAttributes getAttrs() {
    return attrs;
  }

  public void setAttrs(CanvasAttributes attrs) {
    this.attrs = attrs;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public List<CanvasElement> getChildren() {
    return children;
  }

  public void setChildren(List<CanvasElement> children) {
    this.children = children;
  }
}
