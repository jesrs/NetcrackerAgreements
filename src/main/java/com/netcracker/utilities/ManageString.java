package com.netcracker.utilities;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * Utility to manage string 
 * Jesús Rodríguez Salazar jesrs@yahoo.com
 * v1.0
 * Date: 27/07/2021
 */
public class ManageString {
	
	// divides the string taking a reference character 
	public static List<String> divideString(String line, String divideChar){
		return Stream.of(line.split(divideChar))
	      .map (elem -> new String(elem))
	      .collect(Collectors.toList());
	}

}
