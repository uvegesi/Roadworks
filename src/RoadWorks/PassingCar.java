package RoadWorks;

public class PassingCar {

    private int hour;
    private int minute;
    private int second;
    private int passThroughInSecs;
    private char carComingFrom;

    public PassingCar(int hour, int minute, int second, int passThroughInSecs, char carComingFrom) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.passThroughInSecs = passThroughInSecs;
        this.carComingFrom = carComingFrom;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public int getPassThroughInSecs() {
        return passThroughInSecs;
    }

    public char getCarComingFrom() {
        return carComingFrom;
    }
}
