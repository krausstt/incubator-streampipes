/*
 * Copyright 2018 FZI Forschungszentrum Informatik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.streampipes.connect.adapter.specific.sensemap;

import com.github.jqudt.onto.units.TemperatureUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.streampipes.connect.adapter.Adapter;
import org.streampipes.connect.adapter.specific.PullAdapter;
import org.streampipes.connect.adapter.specific.sensemap.model.CurrentLocation;
import org.streampipes.connect.adapter.specific.sensemap.model.SenseBox;
import org.streampipes.connect.adapter.specific.sensemap.model.Sensor;
import org.streampipes.connect.exception.AdapterException;
import org.streampipes.model.connect.adapter.AdapterDescription;
import org.streampipes.model.connect.adapter.SpecificAdapterStreamDescription;
import org.streampipes.model.connect.guess.GuessSchema;
import org.streampipes.model.schema.EventProperty;
import org.streampipes.model.schema.EventPropertyPrimitive;
import org.streampipes.model.schema.EventSchema;
import org.streampipes.model.staticproperty.AnyStaticProperty;
import org.streampipes.model.staticproperty.FreeTextStaticProperty;
import org.streampipes.model.staticproperty.Option;
import org.streampipes.sdk.builder.PrimitivePropertyBuilder;
import org.streampipes.sdk.helpers.EpProperties;
import org.streampipes.sdk.utils.Datatypes;
import org.streampipes.vocabulary.XSD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

public class OpenSenseMapAdapter extends PullAdapter {

    private Logger logger = LoggerFactory.getLogger(OpenSenseMapAdapter.class);

    public static final String ID = "http://streampipes.org/adapter/specific/opensensemap";

    private List<String> selectedSensors;

    private String standartKeys[] = {"id", "timestamp", "model", "latitude", "longitude"};

    private String url = "https://api.opensensemap.org/boxes";

    public OpenSenseMapAdapter() {
        super();
    }

    public OpenSenseMapAdapter(SpecificAdapterStreamDescription adapterDescription) {
        super(adapterDescription);

        this.selectedSensors = Arrays.asList(SensorNames.ALL_SENSOR_KEYS);

    }

    @Override
    public AdapterDescription declareModel() {
        AdapterDescription adapterDescription = new SpecificAdapterStreamDescription();
        adapterDescription.setAdapterId(ID);
        adapterDescription.setUri(ID);
        adapterDescription.setName("OpenSenseMap");
        adapterDescription.setDescription("Environment Sensors");
        adapterDescription.setIconUrl("https://raw.githubusercontent.com/sensebox/resources/master/images/openSenseMap_API_github.png");

        // TODO once any properties are developed in ui change the static properties to possible Sensors
        List<Option> options = new ArrayList<>();
        for (String s : SensorNames.ALL_SENSOR_LABELS) {
            options.add(new Option(s));
        }
        AnyStaticProperty possibleSensors = new AnyStaticProperty("sensors", "Sensors", "Select the sensors that are included in the data stream");
        possibleSensors.setOptions(options);

        FreeTextStaticProperty sensorType = new FreeTextStaticProperty("sensortype", "Sensor Type",
                "Follow this Hashtag.");
        adapterDescription.addConfig(possibleSensors);

//        adapterDescription.addConfig(sensorType);

        return adapterDescription;
    }

    @Override
    public Adapter getInstance(AdapterDescription adapterDescription) {
        return  new OpenSenseMapAdapter((SpecificAdapterStreamDescription) adapterDescription);
    }

    @Override
    public GuessSchema getSchema(AdapterDescription adapterDescription) {
        GuessSchema guessSchema = new GuessSchema();

        EventSchema eventSchema = new EventSchema();
        EventPropertyPrimitive eventPropertyPrimitive = new EventPropertyPrimitive();
        eventPropertyPrimitive.setRuntimeType(XSD._double.toString());

        List<EventProperty> allProperties = new ArrayList<>();

        // Set basic properties
        allProperties.add(EpProperties.timestampProperty(SensorNames.KEY_TIMESTAMP));
        allProperties.add(
                PrimitivePropertyBuilder
                        .create(Datatypes.Integer, SensorNames.KEY_ID)
                        .label(SensorNames.LABEL_ID)
                        .description("The unique identifier of a SenseBox")
                        .build());
        allProperties.add(
                PrimitivePropertyBuilder
                        .create(Datatypes.String, SensorNames.KEY_NAME)
                        .label(SensorNames.LABEL_NAME)
                        .description("The name of the SenseBox")
                        .build());
        allProperties.add(
                PrimitivePropertyBuilder
                        .create(Datatypes.String, SensorNames.KEY_MODEL)
                        .label(SensorNames.LABEL_MODEL)
                        .description("Model of the SenseBox")
                        .build());

        allProperties.add(
                PrimitivePropertyBuilder
                        .create(Datatypes.String, SensorNames.KEY_LATITUDE)
                        .label(SensorNames.LABEL_LATITUDE)
                        .description("Latitude value of box location")
                        .build());
         allProperties.add(
                PrimitivePropertyBuilder
                        .create(Datatypes.String, SensorNames.KEY_LONGITUDE)
                        .label(SensorNames.KEY_LONGITUDE)
                        .description("Longitude value of box location")
                        .build());


        if (selected(SensorNames.KEY_TEMPERATURE)) {
            allProperties.add(PrimitivePropertyBuilder
                    .create(Datatypes.Double, SensorNames.KEY_TEMPERATURE)
                    .label(SensorNames.LABEL_TEMPERATURE)
                    .description("Measurement for the temperature")
                    .measurementUnit(TemperatureUnit.CELSIUS.getResource())
                    .build());
        }
        if (selected(SensorNames.KEY_HUMIDITY)) {
            allProperties.add(PrimitivePropertyBuilder
                    .create(Datatypes.Double, SensorNames.KEY_HUMIDITY)
                    .label(SensorNames.LABEL_HUMIDITY)
                    .description("Measures the humidity in the air")
//                    .measurementUnit(CountingUnit.PERCENT.getResource())
                    .build());
        }
        if (selected(SensorNames.KEY_PRESSURE)) {
            allProperties.add(PrimitivePropertyBuilder
                    .create(Datatypes.Double, SensorNames.KEY_PRESSURE)
                    .label(SensorNames.LABEL_PRESSURE)
                    .description("Air pressure")
//                    .measurementUnit(PressureOrStressUnit.PASCAL.getResource())
                    .build());
        }
        if (selected(SensorNames.KEY_ILLUMINANCE)) {
            allProperties.add(PrimitivePropertyBuilder
                    .create(Datatypes.Double, SensorNames.KEY_ILLUMINANCE)
                    .label(SensorNames.LABEL_ILLUMINANCE)
                    .description("Total luminous flux incident on a surface per unit area")
                    // TODO
//                    .measurementUnit(.getResource())
                    .build());
        }
        if (selected(SensorNames.KEY_UV_INTENSITY)) {
            allProperties.add(PrimitivePropertyBuilder
                    .create(Datatypes.Double, SensorNames.KEY_UV_INTENSITY)
                    .label(SensorNames.LABEL_UV_INTENSITY)
                    .description("")
                    // TODO
//                    .measurementUnit(.getResource())
                    .build());
        }
        if (selected(SensorNames.KEY_PM10)) {
            allProperties.add(PrimitivePropertyBuilder
                    .create(Datatypes.Double, SensorNames.KEY_PM10)
                    .label(SensorNames.LABEL_PM10)
                    .description("Particulate Matter in the air with a diameter of 10 micrometer")
                    // TODO
//                    .measurementUnit(.getResource())
                    .build());
        }
        if (selected(SensorNames.KEY_PM2_5)) {
            allProperties.add(PrimitivePropertyBuilder
                    .create(Datatypes.Double, SensorNames.KEY_PM2_5)
                    .label(SensorNames.LABEL_PM2_5)
                    .description("Particulate Matter in the air with a diameter of 10 micrometer")
                    // TODO
//                    .measurementUnit(.getResource())
                    .build());
        }

        eventSchema.setEventProperties(allProperties);
        guessSchema.setEventSchema(eventSchema);
        guessSchema.setPropertyProbabilityList(new ArrayList<>());
        return guessSchema;
    }

    @Override
    public String getId() {
        return ID;
    }

    public List<Map<String, Object>> getEvents() {

        List<Map<String, Object>> eventResults = new ArrayList<>();

        SenseBox[] senseBoxResult = {};
        try {
            senseBoxResult = getDataFromEndpoint(url, SenseBox[].class);
        } catch (AdapterException e) {
            e.printStackTrace();
        }

        logger.info("Number of all detected SenseBoxes: " + senseBoxResult.length);

        for (SenseBox senseBox : senseBoxResult) {
            Map<String, Object> event = new HashMap<>();

            if (senseBox.getCreatedAt() != null) {

                event.put(SensorNames.KEY_ID, senseBox.get_id());
                // TODO change timestamp
                Long timestamp = getDateMillis(senseBox.getUpdatedAt());
                event.put(SensorNames.KEY_TIMESTAMP, timestamp);
                event.put(SensorNames.KEY_NAME, senseBox.getName());
                event.put(SensorNames.KEY_MODEL, senseBox.getModel());

                double latitude = getLatitude(senseBox);
                double longitude = getLongitude(senseBox);
                if (latitude != Double.MIN_VALUE && longitude != Double.MIN_VALUE) {
                    event.put(SensorNames.KEY_LATITUDE, getLatitude(senseBox));
                    event.put(SensorNames.KEY_LONGITUDE, getLongitude(senseBox));
                } else {
                    logger.info("Sense box id: " + senseBox.get_id() + " does not contain correct latitude or longitude values");
                }

//                Add Sensor values
                for (Sensor s : senseBox.getSensors()) {
                    if (s.getLastMeasurement() != null) {
                        String key = SensorNames.getKey(s.getTitle());
                        if (key != SensorNames.KEY_NOT_FOUND) {
                            double value = getDoubleSensorValue(s.getLastMeasurement().getValue());
                            if (value != Double.MIN_VALUE) {
                                event.put(key, value);
                            } else {
                                logger.info("Sensor value " +s.getLastMeasurement().getValue() + " of sensor id: " +
                                        s.get_id() + " in sense box id: " + senseBox.get_id() +
                                        " is not correctly formatted");
                            }
                        }
                    }
                }

                if (checkEvent(event)) {
                    eventResults.add(filterSensors(event));
                }
            }
        }

        logger.info("All data sucessfully processed and " + eventResults.size() + " events will be send to Kafka");

        return eventResults;
    }

    @Override
    protected void pullData() {

        List<Map<String, Object>> events = getEvents();

        for (Map<String, Object> event : events) {
           adapterPipeline.process(event);
        }

    }

    private Map<String, Object> filterSensors(Map<String, Object> event) {
        Map<String, Object> result = new HashMap<>();

        for (String key : selectedSensors) {
            result.put(key, event.get(key));
        }

        for (String key : SensorNames.ALL_META_KEYS) {
            result.put(key, event.get(key));
        }

        return result;
    }

    private boolean checkEvent(Map<String, Object> event) {
        for(String key : selectedSensors) {
            if (!event.keySet().contains(key)) {
                return false;
            }
        }

        for (String key : SensorNames.ALL_META_KEYS) {
            if (!event.keySet().contains(key)) {
                return false;
            }
        }

        return true;
    }

    private boolean selected(String value) {
        return this.selectedSensors.contains(value);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSelectedSensors(List<String> selectedSensors) {
        this.selectedSensors = selectedSensors;
    }

    private Long getDateMillis(String date) {

        if (date != null) {
            Date result = Date.from(Instant.parse(date));
            return result.getTime();
        }
        return Long.MIN_VALUE;
    }

    private double getDoubleSensorValue(String sensorValue) {
        try {
            return Double.parseDouble(sensorValue);
        } catch (NumberFormatException e) {
            return Double.MIN_VALUE;
        }
    }

    private double getLatitude(SenseBox box) {
       List<Double> latlong = getLatLong(box);

       if (latlong != null) {
           return latlong.get(1);
       } else {
           return Double.MIN_VALUE;
       }

    }

    private double getLongitude(SenseBox box) {
       List<Double> latlong = getLatLong(box);

       if (latlong != null) {
           return latlong.get(0);
       } else {
           return Double.MIN_VALUE;
       }
    }

    private List<Double> getLatLong(SenseBox box) {
        CurrentLocation currentLocation = box.getCurrentLocation();

        if (currentLocation == null) {
            return null;
        }

        List<Double> latlong = currentLocation.getCoordinates();

        return latlong;

    }
}
