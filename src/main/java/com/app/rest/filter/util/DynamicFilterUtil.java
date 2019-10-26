package com.app.rest.filter.util;

import java.util.Optional;
import java.util.Set;

import org.springframework.http.converter.json.MappingJacksonValue;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class DynamicFilterUtil {

	public static MappingJacksonValue dynamicFiltering(Optional<?> entity, String jsonFilterName ,Set<String> setFilters){

		MappingJacksonValue jacksonValue = new MappingJacksonValue(entity.get());
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(setFilters);
		FilterProvider filters = new SimpleFilterProvider().addFilter(jsonFilterName, filter);
		jacksonValue.setFilters(filters);

		return jacksonValue;
	}
}
