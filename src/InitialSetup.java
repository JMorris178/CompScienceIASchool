import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

import static java.lang.Float.parseFloat;

public class InitialSetup {
    public static ArrayList<Float> createNewFile(Settings car){
        Scanner input = new Scanner(System.in);
        ArrayList<Float> tempToAddToFile = new ArrayList<Float>(); //allows the program to push the settings data to the file if needed
        try {
            File fileCreate = new File("userData");
            if (fileCreate.createNewFile()) { //If the file doesn't exist
                System.out.println("File created: " + fileCreate.getName());
                System.out.println("Since this is the first time you are using this, please input some basic data for this program to function");
                System.out.println("What is your cars MPG?");
                float tempMPG = input.nextFloat();
                System.out.println("What is your fuel tanks max capacity?");
                float tempFuelTank = input.nextFloat();
                System.out.println("What is the current cost of fuel?");
                float tempFuelCost = input.nextFloat();
                tempToAddToFile.add(tempMPG);
                tempToAddToFile.add(tempFuelTank);
                tempToAddToFile.add(tempFuelCost);
                for (int i = 0; i < 3; i++){ //Loop allows each part of the users input to be added to the database
                    Object x = tempToAddToFile.get(i);
                    Utilisation.addToFile(x);
                }
                Utilisation.addToFile(tempFuelTank); //adds a copy of the fuel tank as a max - so it will return to this if it runs out
                return (tempToAddToFile);
            } else {
                System.out.println("File exists"); //If the file exists.
            }
            //Now the file is set up (or it already exists), the different settings are saved to the constructor
            String tempPassMPG = String.valueOf(Utilisation.readFromFile(0));
            String tempPassFuelTank = String.valueOf(Utilisation.readFromFile(1));
            String tempPassFuelCost = String.valueOf(Utilisation.readFromFile(2));
            String tempPassMaxCap = String.valueOf(Utilisation.readFromFile(3));
            car.setMPG(parseFloat(tempPassMPG));
            car.setFuelTank(parseFloat(tempPassFuelTank));
            car.setPrices(parseFloat(tempPassFuelCost));
            car.setMaxCap(parseFloat(tempPassMaxCap));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return (tempToAddToFile);
    }


}

