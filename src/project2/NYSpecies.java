package project2;
import java.util.*;
import java.io.*;

/**
 * The NYSpecies class implements the Species and SpeciesList classes in order for users to search through a database
 * The database is given as a .csv file which is read by the program.
 *
 * @author Sanay Daptardar
 */
public class NYSpecies {
    /**
     * Main method is where the console based user interface is made and run. It expects one file path as an argument
     * from which to read the animal data.
     *
     * @param args
     */
    public static void main(String [] args){
        if(args.length != 1){
            System.err.println("Error: program expects one file name as an argument.");
            System.exit(0);
        } //prints varying error messages and terminates if exactly one valid filename is not passed.
        SpeciesList NYList = new SpeciesList();
        try{
            populate(NYList, new File(args[0])); //If the filename is valid this is where the master list gets created
        }catch(FileNotFoundException e) {
            System.err.printf("Error: \"%s\" does not exist,", args[0]);
            System.exit(0);
        }catch(IOException e){
            System.err.println(e);
        }
        run(NYList); //This is where the user interface loop is run, the program terminates within the method
    }

    /**
     * This is the meat and potatoes of this program basically. This is where the data from the .csv gets
     * organized into varios Species and stored in a manipulable Specieslist.
     *
     * @param speciesList; The list to fill
     * @param file; The file from which to read
     * @throws IOException
     */
    private static void populate(SpeciesList speciesList, File file) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(file)); //Reader to read from the file
        int count = 0;
        while(reader.ready()){
            count++;
            String line = reader.readLine(); //Storing each line in a temporary String
            if(count == 1){ //skipping first iteration to account for column headers
                continue;
            }
            ArrayList<String> lineList = new ArrayList<String>();//temporary array list
            if(line.contains("\"")){
                String [] lineArray = line.split("\"");//splts the string into array at double quotes
                for(int i = 0; i < lineArray.length; i++){
                    if(i%2 == 0){
                        String[] lineArraySplits = lineArray[i].split(",");//splits the string at commas
                        for(String s : lineArraySplits){
                            if(s.length() >= 2){//ignore empty spaces or accidental commas
                                lineList.add(s.trim());
                            }
                        }
                    }else{
                        lineList.add(lineArray[i]);
                    }
                }
            }else{
                for(String s: line.split(",")){
                    lineList.add(s);  //if no double quotes, only splitting by commas is necessary
                }
            }
            if(lineList.size() < 8){
                continue; //ignores lines that do not specify all required fields
            }
            speciesList.add(//passing required arguments to create a new Species to then add to the master list
                    new Species(lineList.get(1), lineList.get(2), lineList.get(3), lineList.get(4),
                            lineList.get(5), lineList.get(7)),
                    lineList.get(0)
            );
        }
    }

    /**
     * This is the method which loops the user interface, allowing the user to search the database for keywords
     * and quit whenever desired
     *
     * @param masterList; the List containing all relevant Species data
     */
    private static void run(SpeciesList masterList){
        Scanner in = new Scanner(System.in);
        String input = "-1"; //placeholder value

        System.out.printf("This database holds information regarding Biodervisty across the State of New York.\n");
        do{
            System.out.println("Please enter a keyword you would like to search for, or \"quit\" to stop: ");
            input = in.nextLine(); //reading the user input for the keyword

            if(input.equalsIgnoreCase("quit")){
                System.out.println("Exiting... Have a nice day!");
                System.exit(0);
            }
            SpeciesList temp = masterList.getByName(input.toLowerCase()); //temporary list stores applicable Species
            if(temp == null){
                System.out.println("No matching species found.");
                continue;
            }else if(temp.size() == 0){
                System.out.println("No matching species found.");
                continue;
            }
            temp.display(); //displaying applicable species, sorted alphabetically by name
        }while(!input.equalsIgnoreCase("quit"));
    }
}
