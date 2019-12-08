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

import org.streampipes.container.declarer.SemanticEventProcessingAgentDeclarer;
import org.streampipes.container.init.DeclarersSingleton;
import org.streampipes.container.util.Util;
import org.streampipes.model.graph.DataProcessorInvocation;
import org.streampipes.model.grounding.EventGrounding;
import org.streampipes.model.grounding.KafkaTransportProtocol;
import org.streampipes.model.grounding.TransportProtocol;
import org.streampipes.sdk.extractor.ProcessingElementParameterExtractor;

import java.util.Map;

import javax.ws.rs.Path;

@Path("/sepa")
public class SepaElement extends InvocableElement<DataProcessorInvocation,
        SemanticEventProcessingAgentDeclarer, ProcessingElementParameterExtractor> {

    public SepaElement() {

        super(DataProcessorInvocation.class);
    }

    @Override
    protected Map<String, SemanticEventProcessingAgentDeclarer> getElementDeclarers() {
        return DeclarersSingleton.getInstance().getEpaDeclarers();
    }

    @Override
    protected String getInstanceId(String uri, String elementId) {
        return Util.getInstanceId(uri, "sepa", elementId);
    }

    @Override
    protected ProcessingElementParameterExtractor getExtractor(DataProcessorInvocation graph) {
        return new ProcessingElementParameterExtractor(graph);
    }

    @Override
    protected DataProcessorInvocation createGroundingDebugInformation(DataProcessorInvocation graph) {
        graph.getInputStreams().forEach(is -> {
           modifyGrounding(is.getEventGrounding());

        });

        modifyGrounding(graph.getOutputStream().getEventGrounding());
        return graph;
    }

    private void modifyGrounding(EventGrounding grounding) {
        TransportProtocol protocol = grounding.getTransportProtocol();
        protocol.setBrokerHostname("localhost");
        if (protocol instanceof KafkaTransportProtocol) {
            ((KafkaTransportProtocol) protocol).setKafkaPort(9094);
        }
    }
}
