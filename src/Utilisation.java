
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;

public class Utilisation {
    public static float findFuelConsumption(float MT){
        int n = 0; // Set to 0 because the MPG is always on line 1
        String line;
        float temp;
        try (Stream<String> lines = Files.lines(Paths.get("userData"))) {
            line = lines.skip(n).findFirst().get();
            temp = MT/Float.valueOf(line);
            return(temp);
        } catch (IOException e) {
            System.out.println(e);
        }
        return(0);
    }

    public static void carOutOfFuel(Settings car){
        float remainder = car.getFuelTank();
        int n = 3; // The line number for the max capacity of the fuel tank
        String line;
        try (Stream<String> lines = Files.lines(Paths.get("userData"))) {
            line = lines.skip(n).findFirst().get();
            car.setFuelTank(Float.parseFloat(line)+remainder);
            FileUtilisation.replaceLines(1,Float.parseFloat(line)+remainder); //sets both the file and constructor back so they're in sync
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void carRefuel(Settings car) { //alternate version of CarOutOfFuel if the user refuels on not a full tank
        int n = 3; // The line number for the max capacity of the fuel tank
        String line;
        try (Stream<String> lines = Files.lines(Paths.get("userData"))) {
            line = lines.skip(n).findFirst().get();
            car.setFuelTank(Float.parseFloat(line)); //As there was fuel still in the tank there was no need to carry over any remainder
            FileUtilisation.replaceLines(1,Float.parseFloat(line)); //sets both the file and constructor back so they're in sync
        } catch (IOException e) {
            System.out.println(e);
        }
    }



    public static float costCalculator(Settings car){
        float total = car.getPrices() * car.getFuelTank(); //Multiplies the cost per gallon by the capacity of fuel tank to find the cost for a full refuel
        return total;
    }

    public static float costCalculator(Settings car, float remainderInTank){ //Alternate version of costCalculator if the user refueled while their tank wasn't empty
        float total = car.getPrices() * (car.getFuelTank()-remainderInTank); //Multiplies the cost per gallon by the capacity of fuel tank with the remainder in the tank subtracted to find the cost for a full refuel
        return total;
    }

    public static boolean refuelCheck(ArrayList<String> passer){
        boolean returnValue;
        if(FileUtilisation.returnFromFile(passer, 6).equals("true")){
            returnValue = true;
        }else{
            returnValue = false;
        }
        return returnValue;
    }

    public static float costOverTime(Settings car, Calendar calendar, ArrayList<String> passer){
        float total = 0;
        for(int i = 0; i<7; i++){
        passer = new ArrayList<>(); //resets Passer at the start of each loop
        calendar.add(Calendar.DATE, -1);
        passer.add(String.valueOf((calendar.get(Calendar.DAY_OF_MONTH))));
        passer.add(String.valueOf((calendar.get(Calendar.MONTH)) + 1));
        passer.add(String.valueOf(calendar.get(Calendar.YEAR)));
        if (Utilisation.refuelCheck(passer)) { //checks if the index at 5 has a value, and if it does it carries out the following as there was a refuel
            if (FileUtilisation.returnFromFile(passer, 6).equals("true")) { //checks if index 6 is true. If it is then there's a remainder in index 7, which it will then pass through to the cost calculator
                float remainderInTank = Float.valueOf((String) FileUtilisation.returnFromFile(passer, 7)); //Casts the returned value to a string so it can then be converted into a float.
                if (Utilisation.costCalculator(car) == 0) {
                    total = total + 0;
                } else {
                    total = total + Utilisation.costCalculator(car, remainderInTank);
                }
            } else {
                if (Utilisation.costCalculator(car) == 0) {
                    total = total + 0;
                } else {
                    total = total + Utilisation.costCalculator(car);
                }
            }


        } else {
            total = total + 0;
        }
    }
        return total;
    }

    public static float costOverTime(Settings car, Calendar calendar, ArrayList<String> passer, int repeats){
        float total = 0;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, -(day));//sets the calendar back to the start of the month
        for(int i = 0; i<repeats; i++){
            passer = new ArrayList<>(); //resets Passer at the start of each loop

            calendar.add(Calendar.DATE, +1);
            passer.add(String.valueOf((calendar.get(Calendar.DAY_OF_MONTH))));
            passer.add(String.valueOf((calendar.get(Calendar.MONTH)) + 1));
            passer.add(String.valueOf(calendar.get(Calendar.YEAR)));
            if (Utilisation.refuelCheck(passer)) { //checks if the index at 5 has a value, and if it does it carries out the following as there was a refuel
                if (FileUtilisation.returnFromFile(passer, 6).equals("true")) { //checks if index 6 is true. If it is then there's a remainder in index 7, which it will then pass through to the cost calculator
                    float remainderInTank = Float.valueOf((String) FileUtilisation.returnFromFile(passer, 7)); //Casts the returned value to a string so it can then be converted into a float.
                    if (Utilisation.costCalculator(car) == 0) {
                        total = total + 0;
                    } else {
                        total = total + Utilisation.costCalculator(car, remainderInTank);
                    }
                } else {
                    if (Utilisation.costCalculator(car) == 0) {
                        total = total + 0;
                    } else {
                        total = total + Utilisation.costCalculator(car);
                    }
                }


            } else {
                total = total + 0;
            }
        }
        return(total);
    }

}

