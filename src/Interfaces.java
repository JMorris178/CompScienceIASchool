import jdk.jshell.execution.Util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Scanner;
import java.util.ArrayList;

public class Interfaces {
    public static void settingsInterface(Settings car) {
        boolean repeat = true;
        while (repeat) {
            System.out.println("Do you want to change 1. The MPG, 2. The fuel tank capacity, or 3. The fuel cost? If you want to exit, type a number above 3.");
            Scanner userInput = new Scanner(System.in);
            int choice = userInput.nextInt();
            if (choice == 1) {
                System.out.println("Please input the new MPG in the form of a float (eg. 1.0)");
                float newMPG = userInput.nextFloat();
                Utilisation.replaceLines(0,newMPG); //line 0 is always the MPG, so it can easily be accessed and changed.
                car.setMPG(newMPG); //updates it in the constructor
            } else if (choice == 2) {
                System.out.println("Please input the new fuel tank capacity in the form of a float (eg. 1.0)");
                float newFTC = userInput.nextFloat();
                Utilisation.replaceLines(1,newFTC);
                car.setFuelTank(newFTC);
            } else if (choice == 3) {
                System.out.println("Please input the new fuel cost in the form of a float (eg. 1.0)");
                float newFC = userInput.nextFloat();
                Utilisation.replaceLines(2,newFC);
                car.setPrices(newFC);
            } else {
                System.out.println("That choice is invalid");
                repeat = false;
            }
        }
    }

    public static void mileageCalcInterface(Calendar calendar, Settings car){
        boolean repeat = true;
        while (repeat) {
            System.out.println("Do you want to change 1. The distance travelled today, or 2. The distance travelled on another date.If you want to exit, type a number above 2.");
            Scanner userInput = new Scanner(System.in);
            ArrayList record = new ArrayList<>();
            int choice = userInput.nextInt();
            boolean refillRequired = false;
            boolean extraValue = false;
            float remainderInTank = 0;

            if (choice == 1) {
                record.add(calendar.get(Calendar.DAY_OF_MONTH));
                record.add((calendar.get(Calendar.MONTH)) + 1);
                record.add(calendar.get(Calendar.YEAR));
                System.out.println("How many miles did you travel?");//TODO add functionality to let the user either add another record that is added on to the total, or replace the current record.
                float miles = userInput.nextInt();
                float fuelCon = Utilisation.findFuelConsumption(miles);
                car.setFuelTank((car.getFuelTank())-fuelCon);
                Utilisation.replaceLines(1,(car.getFuelTank())-fuelCon);
                if (car.getFuelTank() <= 0) {
                    refillRequired = true; //sets the refill to true
                    Utilisation.carOutOfFuel(car); //resets the fuel capacity, with the remainder taken away
                }else{
                    System.out.println("Did you refuel? 1 for yes, 2 for no");
                    choice = userInput.nextInt();
                    if(choice == 1) {
                        refillRequired = true;
                        remainderInTank = (car.getMaxCap() - car.getFuelTank()); //sets a value to remainderInTank
                        extraValue = true;
                        Utilisation.carRefuel(car);
                    }
                }

                record.add(miles);
                record.add(fuelCon);
                record.add(refillRequired);
                if(extraValue==true){
                    record.add(remainderInTank); //only adds remainder in tank if it's needed.
                }
                Utilisation.addToFile(record);
            } else if (choice == 2) {
                System.out.println("Please input the following in numerical form - the date of the month (eg : 02, 10, 30)");
                int day = userInput.nextInt();
                System.out.println("Please input the following in numerical form - the month (eg : 02, 10, 08)");
                int month = userInput.nextInt();
                System.out.println("Please input the following in numerical form - the year (eg : 2023, 1973)");
                int year = userInput.nextInt();
                System.out.println("How many miles did you travel?");//TODO add functionality to let the user either add another record that is added on to the total, or replace the current record. Would need findInFile to work, which it currently doesn't
                float miles = userInput.nextInt();
                float fuelCon = Utilisation.findFuelConsumption(miles);
                car.setFuelTank((car.getFuelTank())-fuelCon);
                Utilisation.replaceLines(1,(car.getFuelTank())-fuelCon);
                if (car.getFuelTank() <= 0) {
                    refillRequired = true; //sets the refill to true
                    Utilisation.carOutOfFuel(car); //resets the fuel capacity, with the remainder taken away
                }else {
                    System.out.println("Did you refuel? 1 for yes, 2 for no");
                    choice = userInput.nextInt();
                    if (choice == 1) {
                        remainderInTank = (car.getMaxCap() - car.getFuelTank()); //sets a value to remainderInTank
                        refillRequired = true;
                        extraValue = true;
                        Utilisation.carRefuel(car);
                    }
                }
                record.add(miles);
                record.add(fuelCon);
                record.add(refillRequired);
                if(extraValue==true){
                    record.add(remainderInTank); //only adds remainder in tank if it's needed.
                }
                Utilisation.addToFile(record);

            } else {
                System.out.println("That choice is invalid");
                repeat = false;
            }
        }
    }

    public static void budgetingSheetInterface(){

    }
}
