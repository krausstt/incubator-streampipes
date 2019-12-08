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

package org.streampipes.connect.adapter.preprocessing.transform.schema;

import org.junit.Test;
import org.streampipes.connect.adapter.preprocessing.transform.TransformationRule;

import java.util.*;

import static org.junit.Assert.*;

public class SchemaEventTransformerTest {

    @Test
    public void transform() {
        Map<String, Object> event = getFirstEvent();

        List<TransformationRule> rules = new ArrayList<>();
        rules.add(new RenameTransformationRule(Arrays.asList("a"), "a1"));
        rules.add(new RenameTransformationRule(Arrays.asList("b"), "b1"));
        rules.add(new RenameTransformationRule(Arrays.asList("c"), "c1"));
        rules.add(new RenameTransformationRule(Arrays.asList("c1", "d"), "d1"));
        rules.add(new CreateNestedTransformationRule(Arrays.asList("c1", "f")));
        rules.add(new MoveTransformationRule(Arrays.asList("b1"), Arrays.asList("c1", "f")));
        rules.add(new DeleteTransformationRule(Arrays.asList("e")));

        SchemaEventTransformer eventTransformer = new SchemaEventTransformer(rules);

        Map<String, Object> result = eventTransformer.transform(event);


        assertEquals(2, result.keySet().size());
        assertTrue(result.containsKey("a1"));
        assertTrue(result.containsKey("c1"));

        Map<String, Object> nested = ((Map<String, Object>) result.get("c1"));

        assertEquals(2, nested.keySet().size());
        assertTrue(nested.containsKey("f"));

        nested = (Map<String, Object>) nested.get("f");
        assertEquals(1, nested.keySet().size());
        assertEquals("z", nested.get("b1"));

    }


    private Map<String, Object> getFirstEvent() {
        Map<String, Object> nested = new HashMap<>();
        nested.put("d", "z");

        Map<String, Object> event = new HashMap<>();
        event.put("a", 1);
        event.put("b", "z");
        event.put("e", "z");
        event.put("c", nested);

        return event;
    }
}