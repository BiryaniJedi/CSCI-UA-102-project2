package project2;
import java.util.*;

/**
 * The SpeciesList class is an extension of the ArrayList, but made specifically to hold Species objects
 * SpeciesLists allow for searching through a large database of species in an organized manner.
 *
 * @author Sanay Daptardar
 */
public class SpeciesList extends ArrayList<Species>{

    /**
     * Adds a specified Species to the SpeciesList IF it is not already in the SpeciesList.
     * If the Species already exists within the SpeciesList, the new specified county is added to that Species's
     * list of counties.
     *
     * @param species; The specified Species attempting to be added to the SpeciesList
     * @param county; The county specified by the data point
     */
    public void add(Species species, String county){
        nullCheck(species); nullCheck(county); //checking both variables for null or empty values

        Iterator<Species> iter = this.iterator(); //iterating through the values using an iterator
        boolean match = false;
        while(iter.hasNext()){ //checking if this already contains the species
            Species temp = iter.next();
            if(species.equals(temp)){
                temp.addCounty(county); //if the Species is not new, add the new county
                match = true;
            }
        }
        if(!match){
            species.addCounty(county);
            this.add(species);//if the Species is new, add it to the SpeciesList
        }
    }

    /**
     * This method checks if an object is null or is an empty String.
     *
     * @param obj; The object to checka
     * @throws IllegalArgumentException; thrown when the object is null or an empty string.
     */
    private void nullCheck(Object obj){
        if(obj == null){
            throw new IllegalArgumentException("Species cannot be filled with null or empty strings.");
        }
        if(obj instanceof String){
            String s = (String)obj;
            if(s.length() == 0){
                throw new IllegalArgumentException("County/keyword cannot be filled with an empty string.");
            }
        }
    }

    /**
     * This is the point of the entire program. This method searches through this SpeciesList (which in this case
     * is the master list of Species) and returns a SpeciesList containing all Species with Common Names or Scientific
     * Names containing that keyword.
     *
     * @param keyword; the keyword for which to search.
     * @return returns a SpeciesList populated with Species relevant to the keyword search or null if no matches are
     * found
     */
    public SpeciesList getByName(String keyword){
        nullCheck(keyword);
        keyword = keyword.toLowerCase(); //using lowercase for case insensitivity
        SpeciesList slist = new SpeciesList();
        Iterator<Species> iter = this.iterator(); //iterating through master list

        while(iter.hasNext()){
            Species temp = iter.next();
            String commonName = temp.getCommonName().toLowerCase();
            String scientificName = temp.getScientificName().toLowerCase();

            if(commonName.contains(keyword) ||
                    scientificName.contains(keyword)){
                slist.add(temp); //adds all keyword matches to a sub list
            }
        }
        if(slist.size() == 0){
            return null;
        }
        Collections.sort(slist); //sorting using our specified compareTo method in the Species class
        return slist;
    }

    /**
     * This is method displays each Species in the SpeciesList according to the toString method in the
     * Species class, each separated by a blank line
     */
    public void display() {
        Iterator iter = this.iterator();
        while(iter.hasNext()){
            System.out.println(iter.next());
        }

    }
}
