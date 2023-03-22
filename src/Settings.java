public class Settings {
    //defines the 3 main variables that can be changed via the settings menu
    float MPG;
    float fuelTank;
    float prices;
    float maxCap;


    public Settings(float MPG, float fuelTank, float prices, float maxCap){
        this.MPG = MPG;
        this.fuelTank = fuelTank;
        this.prices = prices;
        this.maxCap = maxCap;
    }
    public float getMPG() {
        return MPG;
    }

    public void setMPG(float MPG) {
        this.MPG = MPG;
    }

    public float getFuelTank() {
        return fuelTank;
    }

    public void setFuelTank(float fuelTank) {
        this.fuelTank = fuelTank;
    }

    public float getPrices() {
        return prices;
    }

    public void setPrices(float prices) {
        this.prices = prices;
    }

    public float getMaxCap() {
        return maxCap;
    }

    public void setMaxCap(float maxCap) {
        this.maxCap = maxCap;
    }
}

