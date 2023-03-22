import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class FileUtilisation {

        public static void addToFile(Object input) { //General util code to add stuff to files. Uses object so it can be fit all
            try (
                    FileWriter fw = new FileWriter("userData", true);
                    PrintWriter pw = new PrintWriter(fw)
            ) {
                pw.println(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public static void addToFile(ArrayList<Object> inputs) { //Polymorphic version of addToFile that takes arrayLists as a parameter
            try (
                    FileWriter fw = new FileWriter("userData", true);
                    PrintWriter pw = new PrintWriter(fw)
            ) {
                for(int i = 0; i < inputs.size(); i++){ //uses a for loop so array lists of different sizes can be used.
                    pw.print(inputs.get(i));
                    pw.print(", ");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static Object readFromFile() { //General code to read all the lines from the file
            try {
                FileReader fr = new FileReader("userData");
                BufferedReader br = new BufferedReader(fr);
                String line = br.readLine();
                while (line != null) {
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return (0);
        }

        public static Object readFromFile(int lineNum) { //General code to read stuff from the file. Takes the line number of the line in the file that needs to be read
            int n = lineNum; // The line number
            String line;
            try (Stream<String> lines = Files.lines(Paths.get("userData"))) {
                line = lines.skip(n).findFirst().get();
                return (line);
            } catch (IOException e) {
                System.out.println(e);
            }
            return (0);


        }


        // read file one line at a time
        // replace line as you read the file and store updated lines in StringBuffer
        // overwrite the file with the new lines
        public static void replaceLines(int lineNum, Object replacementLine) {
            try {
                // input the (modified) file content to the StringBuffer "input"
                BufferedReader file = new BufferedReader(new FileReader("userData"));
                StringBuffer inputBuffer = new StringBuffer();
                String line;
                int count = 0;

                while ((line = file.readLine()) != null) {

                    if (count == lineNum) {
                        line = replacementLine.toString();
                        inputBuffer.append(line);
                        inputBuffer.append('\n');
                    } else {
                        line = readFromFile(count).toString(); // Finds the original line in the original file then copies it.
                        inputBuffer.append(line);
                        inputBuffer.append('\n');
                    }
                    count++;
                }
                file.close();

                // write the new string with the replaced line OVER the same file
                FileOutputStream fileOut = new FileOutputStream("userData");
                fileOut.write(inputBuffer.toString().getBytes());
                fileOut.close();
            }

            catch(Exception e){
                System.out.println("Problem reading file.");
            }
        }
}
