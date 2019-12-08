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

package org.streampipes.app.file.export;

public class ElasticsearchAppData {

    private String index;

    private long timestampFrom;

    private long timestampTo;

    private String output;

    private boolean allData;

    public ElasticsearchAppData() {
    }

    public ElasticsearchAppData(String index, long timestampFrom, long timeStampTo, String output, boolean allData) {
        this.index = index;
        this.timestampFrom = timestampFrom;
        this.timestampTo = timeStampTo;
        this.output = output;
        this.allData = allData;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public long getTimestampFrom() {
        return timestampFrom;
    }

    public void setTimestampFrom(long timestampFrom) {
        this.timestampFrom = timestampFrom;
    }

    public long getTimestampTo() {
        return timestampTo;
    }

    public void setTimeStampTo(long timestampTo) {
        this.timestampTo = timestampTo;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public boolean isAllData() {
        return allData;
    }

    public void setAllData(boolean allData) {
        this.allData = allData;
    }
}
