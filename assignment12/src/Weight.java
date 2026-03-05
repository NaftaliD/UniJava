/**
 * This class represents a Weight object.
 *
 * @author  Naftali Deutsch
 * @version 1
 */
public class Weight {
    private int _kilos;
    private int _grams;

    private final static int MIN_KILOS = 1;
    private final static int MIN_GRAMS = 0;
    private final static int MAX_GRAMS = 999;
    public final static int GRAMS_IN_KILO = 1000;
    private final static int DIGIT_BASE = 10;
    private final static int HUNDREDS_BASE = 100;


    /**
     * Weight constructor - If the given weight is valid, creates a new Weight
     * object; otherwise, if one of the parameters is not valid, initialize it to 1.
     *
     * @param kilos the number of kilos in Weight (greater or equal to 1)
     * @param grams the number of grams in Weight (0-999)
     */
    public Weight(int kilos, int grams) {
        if(kilos < MIN_KILOS || grams < MIN_GRAMS || grams > MAX_GRAMS){
            _kilos = MIN_KILOS;
            _grams = MIN_GRAMS;
            return;
        }
        _kilos = kilos;
        _grams = grams;
    }

    /**
     * Copy constructor.
     *
     * @param other the weight to be copied
     */
    public Weight(Weight other) {
        _kilos = other._kilos;
        _grams = other._grams;
    }

    /**
     * Constructor with only one parameter. if the value is invalid initialize the weight to 1 kilo.
     *
     * @param totalGrams the total number of grams
     */
    public Weight(int totalGrams){
        if(totalGrams < GRAMS_IN_KILO ){
            _kilos = MIN_KILOS;
            _grams = MAX_GRAMS;
            return;
        }
        _kilos = totalGrams / GRAMS_IN_KILO;
        _grams = totalGrams % GRAMS_IN_KILO;
    }

    /**
     * Gets the kilos.
     *
     * @return the number of kilos for this weight
     */
    public int getKilos() {
        return _kilos;
    }

    /**
     * Gets the grams.
     *
     * @return the number of grams for this weight
     */
    public int getGrams() {
        return _grams;
    }

    /**
     * Checks if two weights are the same.
     *
     * @param other the weight to compare this weight to
     * @return true if the weights are the same
     */
    public boolean equals (Weight other) {
        return _kilos == other._kilos && _grams == other._grams;
    }

    /**
     * Checks if this weight is lighter than another weight.
     *
     * @param other weight to compare this weight to
     * @return true if this weight is lighter than the other weight
     */
    public boolean lighter (Weight other){
        return (_kilos*GRAMS_IN_KILO + _grams) < (other._kilos*GRAMS_IN_KILO + other._grams);
    }

    /**
     * Checks if this weight is heavier than another weight.
     *
     * @param other weight to compare this weight to
     * @return true if this weight is heavier than the other weight
     */
    public boolean heavier (Weight other){
        return other.lighter(this);
    }

    /**
     * Returns a String that represents this weight.
     *
     * @return a String that represents this weight in the following format:
     *         kilos.grams (3 digits), for example: 4.07 or 3.055 or 4.005
     */
    @Override
    public String toString(){
        String res = Integer.toString(_kilos) + ".";
        res += _grams/HUNDREDS_BASE; // add the hundreds digit even if its zero
        if(_grams % HUNDREDS_BASE != 0){ //check if the grams number has a tens or singles digit
            res += (_grams % HUNDREDS_BASE) / DIGIT_BASE; //write the tens digit, even if tis zero
        }
        res += (_grams % DIGIT_BASE != 0) ? _grams % DIGIT_BASE : ""; //write the single digit only if it's not zero

        return res;
    }

    /**
     * Return a new weight with the additional grams given as parameter.
     *
     * @param grams the additional grams to add to the new returned weight
     * @return a new weight with the additional grams given as parameter
     */
    public Weight add (int grams){
        int totalGrams = _kilos*GRAMS_IN_KILO + _grams;
        return (totalGrams + grams > GRAMS_IN_KILO) ? new Weight(totalGrams + grams) : new Weight(this); //if the new total grams is valid return the new changed weight, if not return a copy of this
    }


}
