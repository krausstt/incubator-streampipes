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

package org.streampipes.container.api;

import org.streampipes.container.html.HTMLGenerator;
import org.streampipes.container.html.JSONGenerator;
import org.streampipes.container.html.page.WelcomePageGeneratorImpl;
import org.streampipes.container.init.DeclarersSingleton;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class WelcomePage {

  @GET
  @Produces(MediaType.TEXT_HTML)
  public String getWelcomePageHtml() {
    WelcomePageGeneratorImpl welcomePage = new WelcomePageGeneratorImpl(DeclarersSingleton
            .getInstance()
            .getBaseUri(), DeclarersSingleton
            .getInstance()
            .getDeclarers()
            .values());
    HTMLGenerator html = new HTMLGenerator(welcomePage.buildUris());
    return html.buildHtml();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String getWelcomePageJson() {
    WelcomePageGeneratorImpl welcomePage = new WelcomePageGeneratorImpl(DeclarersSingleton
            .getInstance()
            .getBaseUri(), DeclarersSingleton
            .getInstance()
            .getDeclarers()
            .values());
    JSONGenerator json = new JSONGenerator(welcomePage.buildUris());
    return json.buildJson();
  }
}
