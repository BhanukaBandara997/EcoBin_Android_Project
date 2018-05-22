package com.tm_synchronizer.ecobinmobileappv10.model;

public class EcoBinData {

    private String temperatureValue;
    private String humidityValue;
    private String methanePPM;
    private String binUltrasonicValue;
    private String waterPump;
    private String binStatus;
    private String stirringStatus;
    private String waterLevel;
    private String daysLeft;

    public String getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(String daysLeft) {
        this.daysLeft = daysLeft;
    }

    public String getBinStatus() {
        return binStatus;
    }

    public void setBinStatus(String binStatus) {
        this.binStatus = binStatus;
    }

    public String getStirringStatus() {
        return stirringStatus;
    }

    public void setStirringStatus(String stirringStatus) {
        this.stirringStatus = stirringStatus;
    }

    public String getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(String waterLevel) {
        this.waterLevel = waterLevel;
    }


    public String getTemperatureValue() {
        return temperatureValue;
    }

    public void setTemperatureValue(String temperatureValue) {
        this.temperatureValue = temperatureValue;
    }

    public String getHumidityValue() {
        return humidityValue;
    }

    public void setHumidityValue(String humidityValue) {
        this.humidityValue = humidityValue;
    }

    public String getMethanePPM() {
        return methanePPM;
    }

    public void setMethanePPM(String methanePPM) {
        this.methanePPM = methanePPM;
    }

    public String getBinUltrasonicValue() {
        return binUltrasonicValue;
    }

    public void setBinUltrasonicValue(String binUltrasonicValue) {
        this.binUltrasonicValue = binUltrasonicValue;
    }

    public String getWaterPump() {
        return waterPump;
    }

    public void setWaterPump(String waterPump) {
        this.waterPump = waterPump;
    }

}
