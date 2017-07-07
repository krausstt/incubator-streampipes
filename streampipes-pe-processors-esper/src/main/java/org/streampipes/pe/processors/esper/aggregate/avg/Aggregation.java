package org.streampipes.pe.processors.esper.aggregate.avg;

import java.util.List;

import org.streampipes.pe.processors.esper.EsperEventEngine;
import org.streampipes.model.impl.output.AppendOutputStrategy;

public class Aggregation extends EsperEventEngine<AggregationParameter> {

	@Override
	protected List<String> statements(AggregationParameter bindingParameters) {
		String aggregationType = "";
		if (bindingParameters.getAggregationType() == AggregationType.AVG)
		{
			aggregationType = "avg("; 
		}
		else if (bindingParameters.getAggregationType() == AggregationType.SUM)
		{
			aggregationType = "sum("; 
		}
		else if (bindingParameters.getAggregationType() == AggregationType.MIN)
		{
			aggregationType = "min("; 
		}
		else 
		{
			aggregationType = "max("; 
		}
		
		aggregationType = aggregationType +"cast(" +bindingParameters.getAggregate() +", double))";  
		String aggregationRuntimeName = ((AppendOutputStrategy)bindingParameters
				.getGraph()
				.getOutputStrategies()
				.get(0))
				.getEventProperties()
				.get(0)
				.getRuntimeName();
		
		String statement = "select " +getSelectClause(bindingParameters) +aggregationType +" as " +aggregationRuntimeName +" from " +fixEventName(bindingParameters.getInputStreamParams().get(0).getInName()) +".win:time(" +bindingParameters.getTimeWindowSize() +" sec) " +getGroupBy(bindingParameters) +" output last every " +bindingParameters.getOutputEvery() +" seconds";
		return makeStatementList(statement);
	}
	
	private String getSelectClause(AggregationParameter params)
	{
		String result = "";
		for(String property : params.getSelectProperties())
		{
			result = result +property +", ";
		}
		return result;
	}
	
	private String getGroupBy(AggregationParameter params)
	{
		String result = "";
		List<String> groupBy = params.getGroupBy();
		if (groupBy.size() == 0) return result;
		for(int i = 0; i < groupBy.size(); i++)
		{
			result += groupBy.get(i);
			if (! (i == groupBy.size()-1)) result += ", ";
		}
		return " group by " +result;
	}

}