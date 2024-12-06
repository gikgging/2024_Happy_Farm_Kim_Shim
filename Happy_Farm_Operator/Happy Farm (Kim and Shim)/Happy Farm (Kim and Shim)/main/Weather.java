package main;

public class Weather {
	
	//The name of Weather
	private String weatherName;
	
	//The Score of sun light
	private double sunScore;
	
	//The Score of water from rain
	private double rainScore;
	
	//The Constructor of weather this constructor is for initialization
	public Weather(String name, double initSun, double initRain) {
		weatherName = name;
		sunScore = initSun;
		rainScore = initRain;
	}
	
	public double GetSun() {
		return sunScore;
	}
	
	public double GetRain() {
		return rainScore;
	}
	
	public String getWeatherName() {
		return weatherName;
	}
	
}
