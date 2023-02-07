package pojos.getPojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ColorsPojo{
	private int id;
	private String name;
	private int year;
	private String color;
	@JsonProperty("pantone_value")
	private String pantoneValue;

	public int getId(){
		return id;
	}

	public String getName(){
		return name;
	}

	public int getYear(){
		return year;
	}

	public String getColor(){
		return color;
	}

	public String getPantoneValue(){
		return pantoneValue;
	}
}
