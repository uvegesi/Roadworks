package RoadWorks;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {

    public static PassingCar[] passingCars;

    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("1. feladat");
        readFileIn("src/forgalom.txt");
        printFile();
        System.out.println();

        System.out.println("2. feladat");
        System.out.println("A jármű " + carGoingTowards(1) + " felé haladt.");
        System.out.println();

        System.out.println("3. feladat");
        System.out.println("A két utolsó jármű Felsőváros felé " + lastTwoCarsToA() + " másodperc különbséggel " +
                "tette meg az utat.");
        System.out.println();

        System.out.println("4. feladat");
        carsPerDirectionPerHour();
        System.out.println();

        System.out.println("5. feladat");
        tenFastestVehicle();
        System.out.println();

        System.out.println("6. feladat");
        carsExitToA();
    }

    public static void readFileIn(String string) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(string));

        int idx = sc.nextInt();
        passingCars = new PassingCar[idx];
        idx = 0;

        while (sc.hasNext()) {
            int hour = sc.nextInt();
            int minute = sc.nextInt();
            int sec = sc.nextInt();
            int passThrough = sc.nextInt();
            char carFrom = sc.next().charAt(0);
            passingCars[idx++] = new PassingCar(hour, minute, sec, passThrough, carFrom);
        }
    }

    public static void printFile() {
        for (PassingCar passingCar : passingCars) {
            System.out.println(passingCar.getHour() + " " + passingCar.getMinute() + " " + passingCar.getSecond() +
                    " " + passingCar.getPassThroughInSecs() + " " + passingCar.getCarComingFrom());
        }
    }

    public static char carGoingTowards(int carNumber) {
        char goingTo = 'x';
        for (int i = 0; i < passingCars.length; i++) {
            if (i == carNumber - 1) {
                if (passingCars[i].getCarComingFrom() == 'A') {
                    goingTo = 'F';
                } else {
                    goingTo = 'A';
                }
            }
        }
        return goingTo;
    }

    public static int lastTwoCarsToA() {
        int lastCarToF1 = 0;
        int lastCarTOF2 = 0;
        int idx = 0;
        for (int i = passingCars.length - 1; i > 0; i--) {
            if (lastCarToF1 == 0) {
                if (passingCars[i].getCarComingFrom() == 'A') {
                    lastCarToF1 = passingCars[i].getHour() * 3000 + passingCars[i].getMinute() * 60 + passingCars[i].getSecond();
                    idx = i;
                }
            }
            if (lastCarTOF2 == 0) {
                if (passingCars[i].getCarComingFrom() == 'A' && i != idx) {
                    lastCarTOF2 = passingCars[i].getHour() * 3000 + passingCars[i].getMinute() * 60 + passingCars[i].getSecond();
                }
            }
            if (lastCarToF1 != 0 && lastCarTOF2 != 0) {
                break;
            }
        }
        return Math.abs(lastCarToF1 - lastCarTOF2);
    }

    public static void carsPerDirectionPerHour() {
        for (int i = 0; i <= 23; i++) {
            int countFromA = 0;
            int countFromF = 0;
            for (PassingCar passingCar : passingCars) {
                if (passingCar.getHour() == i) {
                    if (passingCar.getCarComingFrom() == 'A') {
                        countFromA++;
                    } else if (passingCar.getCarComingFrom() == 'F') {
                        countFromF++;
                    }
                }
            }
            if (countFromA > 0 || countFromF > 0) {
                System.out.println(i + ": " + countFromA + " " + countFromF);
            }
        }
    }

    public static double fastestVehicle(int upperLimit) {
        double fastest = upperLimit;
        for (PassingCar passingCar : passingCars) {
            if (passingCar.getPassThroughInSecs() < fastest) {
                fastest = passingCar.getPassThroughInSecs();
            }
        }
        return fastest;
    }

    public static String oneDecimal(double num) {
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(num);
    }

    public static void tenFastestVehicle() {
        double fastest = fastestVehicle(300);
        int hour = 0;
        int min = 0;
        int sec = 0;
        char comingFrom = 'x';

        double num = 1000 / fastest;

        for (PassingCar passingCar : passingCars) {
            if (passingCar.getPassThroughInSecs() == fastest) {
                System.out.println("1.\t" + passingCar.getHour() + ":" + passingCar.getMinute() + ":" +
                        passingCar.getSecond() + " " + passingCar.getCarComingFrom() + " " + oneDecimal(num));
                break;
            }
        }
        double secondFastest = 300;
        for (int i = 2; i <= 10; i++) {
            for (PassingCar passingCar : passingCars) {
                if (passingCar.getPassThroughInSecs() > fastest && passingCar.getPassThroughInSecs() < secondFastest) {
                    secondFastest = passingCar.getPassThroughInSecs();
                    hour = passingCar.getHour();
                    min = passingCar.getMinute();
                    sec = passingCar.getSecond();
                    comingFrom = passingCar.getCarComingFrom();
                }
            }
            System.out.println(i + ".\t" + hour + ":" + min + ":" + sec + " " + comingFrom + " "
                    + oneDecimal(1000 / secondFastest));
            fastest = secondFastest;
            secondFastest = 150;
        }
    }

    public static void carsExitToA() {
        int seconds1 = 0;
        int seconds2 = 0;
        int count = 0;
        int hour = 0;
        int min = 0;
        int sec = 0;
        int mod = 0;
        for (int i = 0; i < passingCars.length; i++) {
            if (passingCars[i].getCarComingFrom() == 'F') {
                count++;
                if (count == 1) {
                    seconds1 = passingCars[i].getHour() * 3600 + passingCars[i].getMinute() * 60 + passingCars[i].getSecond() +
                            passingCars[i].getPassThroughInSecs();
                    hour = seconds1 / 3600;
                    mod = seconds1 % 3600;
                    min = mod / 60;
                    sec = mod % 60;
                    System.out.println(hour + " " + min + " " + sec);
                } else {
                    seconds1 = passingCars[i].getHour() * 3600 + passingCars[i].getMinute() * 60 + passingCars[i].getSecond() +
                            passingCars[i].getPassThroughInSecs();
                    seconds2 = passingCars[i - 1].getHour() * 3600 + passingCars[i - 1].getMinute() * 60 + passingCars[i - 1].getSecond() +
                            passingCars[i - 1].getPassThroughInSecs();
                    if (seconds1 <= seconds2) {
                        hour = seconds2 / 3600;
                        mod = seconds2 % 3600;
                        min = mod / 60;
                        sec = mod % 60;
                        System.out.println(hour + " " + min + " " + sec);
                    } else {
                        hour = seconds1 / 3600;
                        mod = seconds1 % 3600;
                        min = mod / 60;
                        sec = mod % 60;
                        System.out.println(hour + " " + min + " " + sec);
                    }
                }
            }
        }
    }

}
