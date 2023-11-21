package entity;

public class Weather {
    private double temperature;
    private double humidity;

    // Default constructor
    public Weather() {
    }

    // Parameterized constructor
    public Weather(double temperature, double humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
    }

    // Getters
    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    // Setters
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    // Optional: Override toString method for easy printing
    @Override
    public String toString() {
        return "Weather{" +
                "temperature=" + temperature +
                ", humidity=" + humidity +
                '}';
    }
}
