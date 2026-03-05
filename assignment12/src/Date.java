/**
 * This class represents a Date object.
 *
 * @author  Naftali Deutsch
 * @version 1
 */
public class Date {

    private int _day;
    private int _month;
    private int _year;

    final static private int DEFAULT_YEAR = 2024;
    final static private int DEFAULT_MONTH_AND_DAY = 1;
    final static private int MAX_YEAR = 9999;
    final static private int MIN_YEAR = 1000;
    final static private int MAX_MONTH = 12;
    final static private int LONG_MONTH_MAX_DAY = 31;
    final static private int REGULAR_MONTH_MAX_DAY = 30;
    final static private int LONG_FEBRUARY = 29;
    final static private int SHORT_FEBRUARY = 28;

    /**
     * Default constructor.
     */
    public Date(){
        _day = DEFAULT_MONTH_AND_DAY;
        _month = DEFAULT_MONTH_AND_DAY;
        _year = DEFAULT_YEAR;
    }

    /**
     * Date constructor - If the given date is valid, creates a new Date object;
     * otherwise, creates the date 01/01/2000.
     *
     * @param day the day in the month (1-31)
     * @param month the month in the year (1-12)
     * @param year the year (4 digits)
     */
    public Date(int day, int month, int year) {
        this(); // initialize to default values

        //validate legal input and set date
        if(isValidDate(day, month, year)){
            _day = day;
            _month = month;
            _year = year;
        }
    }

    /**
     * Copy constructor.
     *
     * @param other the date to be copied
     */
    public Date(Date other){
        _day = other._day;
        _month = other._month;
        _year = other._year;
    }

    /**
     * Gets the year.
     *
     * @return the year of this date
     */
    public int getYear() { return _year; }

    /**
     * Gets the day.
     *
     * @return the day of this date
     */
    public int getDay() { return _day; }

    /**
     * Gets the month.
     *
     * @return the month of this date
     */

    public int getMonth() { return _month; }

    /**
     * Sets the year (only if date remains valid).
     *
     * @param yearToSet the new year value
     */
    public void setYear(int yearToSet) {
        if(isValidDate(_day, _month, yearToSet)){
            _year = yearToSet;
        }
    }

    /**
     * Sets the month (only if date remains valid).
     *
     * @param monthToSet the new month value
     */
    public void setMonth(int monthToSet) {
        if(isValidDate(_day, monthToSet, _year)){
            _month = monthToSet;
        }
    }

    /**
     * Sets the day (only if date remains valid).
     *
     * @param dayToSet the new day value
     */
    public void setDay(int dayToSet) {
        if(isValidDate(dayToSet, _month, _year)){
            _day = dayToSet;
        }
    }


    /**
     * Checks if two dates are the same.
     *
     * @param other the date to compare this date to
     * @return true if the dates are the same
     */
    public boolean equals (Date other){
        return _day == other._day && _month == other._month && _year == other._year;
    }

    /**
     * Checks if this date comes before another date.
     *
     * @param other the date to compare this date to
     * @return true if this date is before the other date
     */
    public boolean before (Date other){
        return calculateDate(_day, _month, _year) < calculateDate(other._day, other._month, other._year);
    }

    /**
     * Checks if this date comes after another date.
     *
     * @param other the date to compare this date to
     * @return true if this date is after the other date
     */
    public boolean after (Date other){
        return other.before(this);
    }

    /**
     * Calculates the difference in days between two dates.
     *
     * @param other the date to calculate the difference between
     * @return the number of days between the dates (non-negative value)
     */
    public int difference (Date other){
        return Math.abs(calculateDate(_day, _month, _year) - calculateDate(other._day, other._month, other._year));
    }

    /**
     * Returns a String that represents this date.
     *
     * @return a String that represents this date in the following format:
     *         day (2 digits) / month(2 digits) / year (4 digits), for example:
     *         02/03/1998
     */
    @Override
    public String toString(){
        return ((_day >= 10) ? Integer.toString(_day) : "0"+_day) + "/" + ((_month >= 10) ? Integer.toString(_month) : "0"+_month) + "/" + _year;
        //return String.format("%02d/%02d/%d", _day, _month, _year);
    }

    /**
     * Calculate the date of tomorrow.
     *
     * @return the date of tomorrow
     */
    public Date tomorrow(){
        if(isValidDate(_day+1, _month, _year)){
            return new Date(_day+1, _month, _year);
        }
        if(isValidDate(DEFAULT_MONTH_AND_DAY, _month+1, _year)){
            return new Date(DEFAULT_MONTH_AND_DAY, _month+1, _year);
        }
        if(isValidDate(DEFAULT_MONTH_AND_DAY, DEFAULT_MONTH_AND_DAY, _year+1)){
            return new Date(DEFAULT_MONTH_AND_DAY, DEFAULT_MONTH_AND_DAY, _year+1);
        }
        return null; //this will only be reached if the year is out of bounds (9999+)
    }

    // return the month length
    private int daysInMonth(int month, int year) {
        switch (month) {
            case 1, 3, 5, 7, 8, 10, 12:  // January, March, May, July, August, October, December
                    return LONG_MONTH_MAX_DAY;
            case 4, 6, 9, 11:  // April, June, September, November
                    return REGULAR_MONTH_MAX_DAY;
            case 2:  // February
                return (isLeapYear(year) ? LONG_FEBRUARY : SHORT_FEBRUARY);
            default:
                return -1;
        }
    }

    private boolean isValidDate(int day, int month, int year){
        if (day < DEFAULT_MONTH_AND_DAY || day > LONG_MONTH_MAX_DAY){ //validate day within valid range
            return false;
        }
        if (month < DEFAULT_MONTH_AND_DAY || month > MAX_MONTH){ //validate month within valid range
            return false;
        }
        if (year < MIN_YEAR || year > MAX_YEAR){ //validate year within valid range
            return false;
        }
        return day <= daysInMonth(month, year); // validate day is valid to month, eg 30 isn't valid for February
    }

    // computes the day number since the beginning of the Christian counting of years
    private int calculateDate ( int day, int month, int year)
    {
        if (month < 3) {
            year--;
            month = month + 12;
        }
        return 365 * year + year/4 - year/100 + year/400 + ((month+1) * 306)/10 + (day - 62);
    }

    // checks if the year is a leap year
    private boolean isLeapYear (int y)
    {
        return (y%4==0 && y%100!=0) || (y%400==0) ? true : false;
    }

}
