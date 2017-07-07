package org.streampipes.pe.sources.hella.sources;

import java.util.Arrays;
import java.util.List;

import org.streampipes.container.declarer.EventStreamDeclarer;
import org.streampipes.container.declarer.SemanticEventProducerDeclarer;
import org.streampipes.model.impl.graph.SepDescription;
import org.streampipes.pe.sources.hella.streams.ProductionPlanStream;
import org.streampipes.pe.sources.hella.streams.RawMaterialCertificateStream;
import org.streampipes.pe.sources.hella.streams.RawMaterialChangeStream;

public class HumanSensorDataProducer implements SemanticEventProducerDeclarer {

	@Override
	public SepDescription declareModel() {
		
		SepDescription sep = new SepDescription("source-human", "Human Sensor", "Provides streams generated manually by humans");
		
		return sep;
	}

	@Override
	public List<EventStreamDeclarer> getEventStreams() {
		return Arrays.asList(new RawMaterialCertificateStream(), new RawMaterialChangeStream(), new ProductionPlanStream());
	}

}