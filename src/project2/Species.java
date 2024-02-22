package project2;
import java.util.*;

/**
 * The Species class is used as a way to organize each data point from the .csv file into a usable object
 * Various methods are specified in order for the user interface to function.
 *
 * @author Sanay Daptardar
 */
public class Species implements Comparable<Species>{
    private String category;
    private String taxonomicGroup;
    private String taxonomicSubGroup;
    private String scientificName;
    private String commonName;
    private String NYListingStatus;
    private ArrayList<String> counties;

    /**
     *Allows retrieval of the Species category
     *
     * @return  returns the category of the species
     */
    public String getCategory(){
        return this.category;
    }

    /**
     *Allows retrieval of the Species taxonomic group
     *
     * @return  returns the taxonomic group of the species
     */
    public String getTaxonomicGroup(){
        return this.taxonomicGroup;
    }

    /**
     *Allows retrieval of the Species taxonomic subgroup
     *
     * @return  returns the taxonomic subgroup of the species
     */
    public String getTaxonomicSubGroup(){
        return this.taxonomicSubGroup;
    }

    /**
     *Allows retrieval of the Species scientific name
     *
     * @return  returns the scientific name of the species
     */
    public String getScientificName(){
        return this.scientificName;
    }

    /**
     *Allows retrieval of the Species common name
     *
     * @return  returns the common name of the species
     */
    public String getCommonName(){
        return this.commonName;
    }

    /**
     *Allows retrieval of the Species NY listing status
     *
     * @return  returns the NY listing status of the species
     */
    public String getNYListingStatus(){
        return this.NYListingStatus;
    }

    /**
     *Allows retrieval of the number of counties in which the Species appears
     *
     * @return  returns the size of the Species list of counties
     */
    public int getCountiesSize(){
        return this.counties.size();
    }


    /**
     * This is the constructor for the Species class. It takes the specified values and stores them within
     * a newly created instance of the Species class.
     *
     * @param category
     * @param taxonomicGroup
     * @param taxonomicSubGroup
     * @param scientificName
     * @param commonName
     * @param NYListingStatus
     */
    public Species(  String category,
                     String taxonomicGroup,
                     String taxonomicSubGroup,
                     String scientificName,
                     String commonName,
                     String NYListingStatus
    ){
        nullCheck(category);
        nullCheck(taxonomicGroup);
        nullCheck(taxonomicSubGroup);
        nullCheck(scientificName);
        nullCheck(commonName);
        nullCheck(NYListingStatus);
        this.category = category;
        this.taxonomicGroup = taxonomicGroup;
        this.taxonomicSubGroup = taxonomicSubGroup;
        this.scientificName = scientificName;
        this.commonName = commonName;
        this.NYListingStatus = NYListingStatus;
        this.counties = new ArrayList<String>();
    }

    /**
     * This method throws an IllegalArgumentException if the specified String is null or empty
     *
     * @param test; the String to test
     * @throws IllegalArgumentException; when the specified string is null or empty
     */
    private void nullCheck(String test){
        if(test == null || test.length() == 0){
            throw new IllegalArgumentException("Categories/counties cannot be filled with null or empty strings.");
        }
    }

    /**
     * This method checks if the Species is present in a specified county.
     *
     * @param county; the county to check
     * @return returns true if the Species is in the county or false if not.
     */
    public boolean isPresentIn(String county){
        nullCheck(county);
        Iterator<String> iter = this.counties.iterator();
        while(iter.hasNext()){
            if(iter.next().equalsIgnoreCase(county)){
                return true;
            }
        }
        return false;
    }

    /**
     * This method adds a specified county to the Species list of counties, IF it is not already on the list
     *
     * @param county; the county to add to the list.
     * @return returns true if the county was successfully added or false if the county was found in the list of
     * counties
     */
    public boolean addCounty(String county){
        nullCheck(county);
        if(this.isPresentIn(county)){
            return false;
        }
        this.counties.add(county); //if the county is not on the list, adds it to the list
        return true;
    }

    /**
     * This method specifies a comparison between two Species objects. Compares by common name, and if those are
     * equal, then compares by scientific name.
     *
     * @param species the object to be compared to.
     * @return returns 0 if the two Species are equal (highly unlikely), a negative integer if
     * THIS species (the one invoking the method) is lexicographically less than the other Species, and a positive
     * integer if vise versa.
     */
    @Override
    public int compareTo(Species species){
        if(this.getCommonName().toLowerCase().equalsIgnoreCase(species.getCommonName().toLowerCase())){
            return this.getScientificName().toLowerCase().compareTo(species.getScientificName().toLowerCase());
        }
        return this.getCommonName().toLowerCase().compareTo(species.getCommonName().toLowerCase());
    }

    /**
     * This method tests if two Species objects are equal.
     * Common name, Scientific name, Taxonomic group and subgroup,
     * and Category must be equal for two species to be equal
     *
     * @param obj; the object to be compared to
     * @return returns true if the two Species are equal or false if not
     */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Species)){
            return false;
        }
        Species s = (Species)obj;
        if(
                (this.getCommonName().equalsIgnoreCase(s.getCommonName()))
                && (this.getScientificName().equalsIgnoreCase(s.getScientificName()))
                && (this.getTaxonomicGroup().equalsIgnoreCase(s.getTaxonomicGroup()))
                && (this.getTaxonomicSubGroup().equalsIgnoreCase(s.getTaxonomicSubGroup()))
                && (this.getCategory().equalsIgnoreCase(s.getCategory())) //test conditions
        ){
            return true;
        }
        return false;
    }

    /**
     * Overrides the toString() method from the Object class to format and display the information contained
     * in the Species object.
     *
     * @return returns the String representing the Species information
     */
    @Override
    public String toString(){
        return String.format("%-26s (%s)\n%-26s %s\n%s\npresent in %d/62 counties.\n",
                this.getCommonName(),
                this.getScientificName(),
                this.getTaxonomicGroup(),
                this.getTaxonomicSubGroup(),
                this.getNYListingStatus(),
                this.getCountiesSize()
        );
    }
}
