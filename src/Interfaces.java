
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
                FileUtilisation.replaceLines(0,newMPG); //line 0 is always the MPG, so it can easily be accessed and changed.
                car.setMPG(newMPG); //updates it in the constructor
            } else if (choice == 2) {
                System.out.println("Please input the new fuel tank capacity in the form of a float (eg. 1.0)");
                float newFTC = userInput.nextFloat();
                FileUtilisation.replaceLines(1,newFTC);
                car.setFuelTank(newFTC);
            } else if (choice == 3) {
                System.out.println("Please input the new fuel cost in the form of a float (eg. 1.0)");
                float newFC = userInput.nextFloat();
                FileUtilisation.replaceLines(2,newFC);
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
            System.out.println("Do you want to 1. Change the distance travelled today, 2. Change the distance travelled on another date, 3. view the data from today or 4, view the data from another day? If you want to exit, type a number above 5.");
            Scanner userInput = new Scanner(System.in);
            ArrayList record = new ArrayList<>();
            ArrayList<String> passer = new ArrayList<>(); //creates a new arraylist to pass data through that isn't the records
            int choice = userInput.nextInt();
            boolean refillRequired = false;
            boolean extraValue = false;
            boolean replaceLines = false;
            float remainderInTank = 0;
            float offset;
            float originalValue;
            float newValue;
            float overshoot = 0;

            if (choice == 1) {
                record.add(calendar.get(Calendar.DAY_OF_MONTH)); //uses calendar to get the date automatically.
                record.add((calendar.get(Calendar.MONTH)) + 1);
                record.add(calendar.get(Calendar.YEAR));
                for(int i =0;i < 3 ; i++){
                    passer.add(String.valueOf(record.get(i))); //converts the date into strings and adds them to passer for later checks
                }
                System.out.println("How many miles did you travel?");
                float miles = userInput.nextInt();
                float fuelCon = Utilisation.findFuelConsumption(miles);
                if(FileUtilisation.findInFile(passer) == true) { //Checks if the dates are already in the database. If true, a different method needs to be carried out to replace the lines.
                    offset = Float.valueOf((String) FileUtilisation.returnFromFile(passer, 4)); //Takes the current fuel consumption in the record already in the file
                    newValue = car.getFuelTank() + offset;
                    if(newValue > car.getMaxCap()) {//checks to see if the new value has exceeded the capacity
                        overshoot = newValue - car.getMaxCap(); //Finds the overshoot
                    }
                    newValue = newValue - overshoot;
                    car.setFuelTank(newValue); //sets the fuel tank in the car back to what it was before the change
                    FileUtilisation.replaceLines(1, newValue);//uses originalValue to avoid a logic error
                    replaceLines = true; //sets to true so the if statement later is carries out
                }
                FileUtilisation.replaceLines(1, (car.getFuelTank()) - fuelCon);
                car.setFuelTank((car.getFuelTank()) - fuelCon);

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
                if(extraValue==true){ //adds an extra true or false to let the program distinguish a full refuel or partial refuel when records are called.
                    record.add("true");
                    record.add(remainderInTank);
                } else{
                    record.add("false");
                }

                if(replaceLines){
                    int lineNum = FileUtilisation.findLineNum(passer);
                    FileUtilisation.replaceLines((lineNum+1), record); //replaces the line that had the same date originally.
                }else{
                    FileUtilisation.addToFile(record);
                }



            } else if (choice == 2) {
                System.out.println("Please input the following in numerical form - the date of the month (eg : 02, 10, 30)");
                int day = userInput.nextInt();
                System.out.println("Please input the following in numerical form - the month (eg : 02, 10, 08)");
                int month = userInput.nextInt();
                System.out.println("Please input the following in numerical form - the year (eg : 2023, 1973)");
                int year = userInput.nextInt();
                record.add(day);
                record.add(month);
                record.add(year);
                for(int i =0;i < 3 ; i++){
                    passer.add(String.valueOf(record.get(i))); //converts the date into strings and adds them to passer for later checks
                }
                System.out.println("How many miles did you travel?");
                float miles = userInput.nextInt();
                float fuelCon = Utilisation.findFuelConsumption(miles);
                if(FileUtilisation.findInFile(passer) == true) { //Checks if the dates are already in the database. If true, a different method needs to be carried out to replace the lines.
                    offset = Float.valueOf((String) FileUtilisation.returnFromFile(passer, 4)); //Takes the current fuel consumption in the record already in the file
                    newValue = car.getFuelTank() + offset;
                    if(newValue > car.getMaxCap()) {//checks to see if the new value has exceeded the capacity
                        overshoot = newValue - car.getMaxCap(); //Finds the overshoot
                    }
                    newValue = newValue - overshoot;
                    car.setFuelTank(newValue); //sets the fuel tank in the car back to what it was before the change
                    FileUtilisation.replaceLines(1, newValue);//uses originalValue to avoid a logic error
                    replaceLines = true; //sets to true so the if statement later is carries out
                }
                car.setFuelTank((car.getFuelTank())-fuelCon);
                FileUtilisation.replaceLines(1,(car.getFuelTank())-fuelCon);
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
                if(extraValue==true){ //adds an extra true or false to let the program distinguish a full refuel or partial refuel when records are called.
                    record.add("true");
                    record.add(remainderInTank);
                } else{
                    record.add("false");
                }

                if(replaceLines){
                    int lineNum = FileUtilisation.findLineNum(passer);
                    FileUtilisation.replaceLines((lineNum+1), record); //replaces the line that had the same date originally.
                }else{
                    FileUtilisation.addToFile(record);
                }



            } else if (choice == 3){
                passer.add(String.valueOf((calendar.get(Calendar.DAY_OF_MONTH))));
                passer.add(String.valueOf((calendar.get(Calendar.MONTH)) + 1));
                passer.add(String.valueOf(calendar.get(Calendar.YEAR)));
                System.out.println("You travelled this many miles " + FileUtilisation.returnFromFile(passer,3)); //the miles travelled is always at index 4
                System.out.println("You used this much fuel " + FileUtilisation.returnFromFile(passer,4));
                if (String.valueOf(FileUtilisation.returnFromFile(passer,5)).equals("true")){
                    System.out.println("You refuelled today");
                }else{
                    System.out.println("you didn't refuel today");
                }



            }else if(choice == 4){
                System.out.println("Please input the following in numerical form - the date of the month (eg : 02, 10, 30)");
                int day = userInput.nextInt();
                System.out.println("Please input the following in numerical form - the month (eg : 02, 10, 08)");
                int month = userInput.nextInt();
                System.out.println("Please input the following in numerical form - the year (eg : 2023, 1973)");
                int year = userInput.nextInt();
                passer.add(String.valueOf(day));
                passer.add(String.valueOf(month));
                passer.add(String.valueOf(year));
                System.out.println("You travelled this many miles " + FileUtilisation.returnFromFile(passer,3)); //the miles travelled is always at index 4
                System.out.println("You used this much fuel " + FileUtilisation.returnFromFile(passer,4));
                if (String.valueOf(FileUtilisation.returnFromFile(passer,5)).equals("true")){
                    System.out.println("You refuelled today");
                }else{
                    System.out.println("you didn't refuel today");
                }
            }else {
                System.out.println("That choice is invalid");
                repeat = false;
            }
        }
    }

    public static void budgetingSheetInterface(Settings car, Calendar calendar){
        //By default it will display the fuel used today
        System.out.println("working");
        ArrayList<String> passer = new ArrayList<>();
        passer.add(String.valueOf((calendar.get(Calendar.DAY_OF_MONTH))));
        passer.add(String.valueOf((calendar.get(Calendar.MONTH)) + 1));
        passer.add(String.valueOf(calendar.get(Calendar.YEAR)));
        if (FileUtilisation.returnFromFile(passer,6).equals("true")){ //checks if index 6 is true. If it is then there's a remainder in index 7, which it will then pass through to the cost calculator
            float remainderInTank = Float.valueOf((String) FileUtilisation.returnFromFile(passer,7)); //Casts the returned value to a string so it can then be converted into a float.

        }else{
            if(Utilisation.costCalculator(car) == 0){
                System.out.println("You haven't spent any money on fuel today");
            }else{
                System.out.println(Utilisation.costCalculator(car));
            }
        }



    }
}
