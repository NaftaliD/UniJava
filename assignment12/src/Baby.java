/**
 * This class represents a Baby object.
 *
 * @author  Naftali Deutsch
 * @version 1
 */
public class Baby {
    private String _firstName;
    private String _lastName;
    private String _id;
    private Date _dateOfBirth;
    private Weight _birthWeight;
    private Weight _currentWeight;

    private final static String DEFAULT_ID = "000000000";
    private final static int DAYS_IN_YEAR = 365;
    private final static int MIN_DAY = 1;
    private final static int DAYS_IN_WEEK = 7;
    private final static double WEIGHT_CHANGE_FOR_WEEK = 0.10/DAYS_IN_WEEK;
    private final static int DAYS_IN_TWO_MONTHS = 60;
    private final static int WEIGHT_CHANGE_FOR_TWO_MONTHS = 30;
    private final static int DAYS_IN_FOUR_MONTHS = 120;
    private final static int WEIGHT_CHANGE_FOR_FOUR_MONTHS = 25;
    private final static int DAYS_IN_EIGHT_MONTHS = 240;
    private final static int WEIGHT_CHANGE_FOR_EIGHT_MONTHS = 16;
    private final static int WEIGHT_CHANGE_FOR_YEAR = 8;


    /**
     * Baby constructor - If the given id and birthWeightInGrams are valid, creates a new Baby object with the parameters.
     * Otherwise, if the id is not valid, creates the Baby with id = "000000000" and all other parameters.
     * If the weight of the baby at birth is not 1KG and above, it will set it to 1KG.
     * If the date of birth is not valid, it will be set to 1/1/2024
     *
     * @param fName the first name of the baby.
     * @param lName the last name of the baby.
     * @param id the id of the baby (9 characters).
     * @param day the day of the baby's birth.
     * @param month the month of the baby's birth.
     * @param year the year of the baby's birth.
     * @param birthWeightInGrams the weight of the baby at birth in grams (should be minimum 1KG).
     */
    public Baby(String fName, String lName, String id,
                int day, int month, int year, int birthWeightInGrams){
        _firstName = fName;
        _lastName = lName;
        _id = (id.length() == DEFAULT_ID.length()) ? id : DEFAULT_ID;
        _dateOfBirth = new Date(day, month, year);
        _birthWeight = new Weight(birthWeightInGrams);
        _currentWeight = _birthWeight; //Weight is an immutable class, no problem with double pointer
    }

    /**
     * Copy constructor.
     *
     * @param other the baby to be copied.
     */
    public Baby(Baby other){
        _firstName = other._firstName;
        _lastName = other._lastName;
        _id = other._id;
        _dateOfBirth = new Date(other._dateOfBirth);
        _birthWeight = other._birthWeight; //Weight is an immutable class, no problem with double pointer
        _currentWeight = other._currentWeight; //Weight is an immutable class, no problem with double pointer
    }

    /**
     * Gets the first name.
     *
     * @return the first name of this baby.
     */
    public String getFirstName(){ return _firstName; }

    /**
     * Gets the last name.
     *
     * @return the last name of this baby.
     */
    public String getLastName() { return _lastName; }

    /**
     * Gets the id.
     *
     * @return the id of this baby.
     */
    public String getId() { return _id; }

    /**
     * Gets the date of birth.
     *
     * @return the date of birth of this baby.
     */
    public Date getDateOfBirth() { return _dateOfBirth; }

    /**
     * Gets the birth weight.
     *
     * @return the weight of this baby at birth.
     */
    public Weight getBirthWeight() { return _birthWeight; }

    /**
     * Gets the current weight.
     *
     * @return the current weight of this baby.
     */
    public Weight getCurrentWeight() { return _currentWeight; }

    /**
     * Sets the current weight if the given parameter is valid.
     *
     * @param weightToSet the new current weight.
     */
    public void setCurrentWeight(Weight weightToSet) {
        _currentWeight = weightToSet;
    }

    /**
     * Returns a String that represents this baby.
     *
     * @return a String that represents this baby.
     */
    @Override
    public String toString(){
        return "Name: " + _firstName + " " + _lastName + "\n" +
                "Id: " + _id + "\n" +
                "Date of Birth: " + _dateOfBirth.toString() + "\n" +
                "Birth Weight: " + _birthWeight.toString() + "\n" +
                "Current Weight: " + _currentWeight.toString() + "\n";
    }

    /**
     * Checks if two babies are the same.
     * Two babies are considered the same if they have the same first and last name,
     * same ID, and similar date of birth.
     *
     * @param other the baby to compare this baby with.
     * @return true if the babies are the same.
     */
    public boolean equals (Baby other) {
        return _firstName.equals(other._firstName) && _lastName.equals(other._lastName) && _id.equals(other._id) && _dateOfBirth.equals(other._dateOfBirth);
    }

    /**
     * Checks if two babies are twins.
     * Twins should have similar last name, different first name, different ID,
     * and similar date of birth or a difference of one day between the date of birth of the current baby and the other.
     *
     * @param other the baby to compare this baby with.
     * @return true if the babies are twins.
     */
    public boolean areTwins (Baby other){
        if(!_lastName.equals(other._lastName)){ //Check for the same last name
            return false;
        }
        if(_id.equals(other._id) || _firstName.equals(other._firstName)){ // check for different id and first name
            return false;
        }
        return (_dateOfBirth.equals(other._dateOfBirth) || _dateOfBirth.tomorrow().equals(other._dateOfBirth) || _dateOfBirth.equals(other._dateOfBirth.tomorrow())); //check if birthdays are the same or are different by a day
    }

    /**
     * Checks if the weight of this baby is heavier than the weight of another baby.
     *
     * @param other baby to compare this baby's weight to.
     * @return true if the weight of this baby is heavier than the weight of the other baby.
     */
    public boolean heavier (Baby other){
        return _currentWeight.heavier(other._currentWeight);
    }

    /**
     * Updates the baby's current weight by adding the additional grams.
     * If the sum of the current weight and the additional grams is negative,
     * the baby's current weight will remain unchanged.
     *
     * @param grams the number of grams to add to the baby's current weight (can be negative).
     */
    public void updateCurrentWeight (int grams) {
        _currentWeight = _currentWeight.add(grams); // if the change is valid the weight will be updates if not, a copy of this._currentWeight will be returned with no change
    }

    /**
     * Checks if the date of birth of this baby is before the date of birth of another baby.
     *
     * @param other baby to compare this baby's date of birth to.
     * @return true if the date of birth of this baby is before the date of birth of the other baby.
     */
    public boolean older (Baby other){
        return _dateOfBirth.before(other._dateOfBirth);
    }

    /**
     * Checks if the current weight of this baby is within the valid range.
     *
     * @param numOfDays number of days passed since the baby was born.
     * @return
     *      1 - If the date given as a parameter is less than a day or more than a year.
     *      2 - If the progress is not correct according to the rules.
     *      3 - If the progress is correct according to the rules.
     */
    public int isWeightInValidRange (int numOfDays){
        if(numOfDays < MIN_DAY || numOfDays > DAYS_IN_YEAR){
            return 1;
        }

        double expectedMinWeightInGrams = _birthWeight.getKilos()*Weight.GRAMS_IN_KILO + _birthWeight.getGrams();
        double currentWeightInGrams = _currentWeight.getKilos()*Weight.GRAMS_IN_KILO + _currentWeight.getGrams();

        //look at weight change for first week
        int daysDelta = Math.max(numOfDays, DAYS_IN_WEEK); // the amount of days passed since day 0 (Birth) in the first week (7 days) period
        expectedMinWeightInGrams -= expectedMinWeightInGrams*(WEIGHT_CHANGE_FOR_WEEK*daysDelta); //calc the weight change according to the day

        //look at weight change for day 8 to 60
        if (numOfDays > DAYS_IN_WEEK){
            daysDelta = Math.max(numOfDays, DAYS_IN_TWO_MONTHS) - DAYS_IN_WEEK; // the amount of days passed since day 7 (end of previous period) in the two months (60 days) period
            expectedMinWeightInGrams += WEIGHT_CHANGE_FOR_TWO_MONTHS*daysDelta;
        }

        //look at weight change for day 61 to 120
        if (numOfDays > DAYS_IN_TWO_MONTHS){
            daysDelta = Math.max(numOfDays, DAYS_IN_FOUR_MONTHS) - DAYS_IN_TWO_MONTHS; // the amount of days passed since day 60 (end of previous period) in the four months (120 days) period
            expectedMinWeightInGrams += WEIGHT_CHANGE_FOR_FOUR_MONTHS*daysDelta;
        }

        //look at weight change for day 121 to 240
        if (numOfDays > DAYS_IN_FOUR_MONTHS){
            daysDelta = Math.max(numOfDays, DAYS_IN_EIGHT_MONTHS) - DAYS_IN_FOUR_MONTHS; // the amount of days passed since day 120 (end of previous period) in the eight months (240 days) period
            expectedMinWeightInGrams += WEIGHT_CHANGE_FOR_EIGHT_MONTHS*daysDelta;
        }

        //look at weight change for day 241 to a year
        if (numOfDays > DAYS_IN_EIGHT_MONTHS){
            daysDelta = numOfDays - DAYS_IN_EIGHT_MONTHS; // the amount of days passed since day 240 (end of previous period) in the year (356 days) period
            expectedMinWeightInGrams += WEIGHT_CHANGE_FOR_YEAR*daysDelta;
        }

        return (currentWeightInGrams >= expectedMinWeightInGrams) ? 3 : 2;
    }




}
