package de.fzi.sepa.model.client.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.fzi.cep.sepa.model.client.ontology.Range;



public class Utils {

	public static Gson getGson()
	{
		GsonBuilder gsonBuilder = getGsonBuilder();
		Gson gson = gsonBuilder.create();
		return gson;
	}
	
	public static GsonBuilder getGsonBuilder()
	{
		GsonBuilder gsonBuilder = new com.google.gson.GsonBuilder();
		gsonBuilder.registerTypeAdapter(Range.class, new RangeSerializer());
		return gsonBuilder;	
	}
	
}
