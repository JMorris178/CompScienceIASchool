
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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
            System.out.println(MT);
            System.out.println(Float.valueOf(line));
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

    public static void findInFile(String searchedItem[]){
        try {
            FileReader fr = new FileReader("userData");
            BufferedReader br = new BufferedReader(fr);
            int count = 0;
            boolean found = false;
            String line = br.readLine();
            while (line != null) { //Passes through every line in the file, and when it is found it returns the index where it was found
                line = br.readLine();
                if(line == null) { //avoids an error where the line being null clashes with the rest of the code
                } else {
                    System.out.println(line);
                    String[] parts = line.split(", ");
                    if (searchedItem[0].equals(parts[0]) && searchedItem[1].equals(parts[1]) && searchedItem[2].equals(parts[2])) { //checks to see if the dates line up with the searched dates, meaning the dates act as an identifier
                        found = true;
                        int foundAtIndex = count;
                        System.out.println("found at " + foundAtIndex);
                    }
                }
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
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



}

