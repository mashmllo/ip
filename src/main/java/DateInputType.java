import java.time.format.DateTimeFormatter;

/**
 * Enums representing types of data inputs
 *
 * There are 2 types of input:
 * <ul>
 *     <li>{@link #DATE_ONLY} - Input only contains the date </li>
 *     <li>{@link #DATETIME} - Input contains the date and time </li>
 * </ul>
 * The enums also provides constants for parsing and displaying
 * date and datetime in a specified format
 */
public enum DateInputType {
    DATE_ONLY,
    DATETIME;

    /** String patterns for date and time(24hr) */
    private final static String DATE_OUTPUT_PATTERN = "MMM dd yyyy";
    private final static String DATETIME_INPUT_PATTERN = "yyyy-MM-dd HH:mm";
    private final static String DATE_INPUT_PATTERN = "yyyy-MM-dd";
    private final static String DATETIME_OUTPUT_PATTERN = "MMM dd yyyy HH:mm";

    /** Format to display date and time (24hr) e.g. JAN 22 2026 13:00 */
    public static final DateTimeFormatter FORMAT_DATETIME_DISPLAY
            = DateTimeFormatter.ofPattern(DATETIME_OUTPUT_PATTERN);

    /** Format to display date only e.g. JAN 22 2026 */
    public static final DateTimeFormatter FORMAT_DATE_DISPLAY
            = DateTimeFormatter.ofPattern(DATE_OUTPUT_PATTERN);

    /** Input parser for parsing date and time (24hr) input.
     * e.g. 2026-01-22 13:00
     */
    public static final DateTimeFormatter FORMAT_DATETIME_INPUT
            = DateTimeFormatter.ofPattern(DATETIME_INPUT_PATTERN);

    /** Input parser for parsing date only input
     * e.g. 2026-01-22 13:00
     */
    public static final DateTimeFormatter FORMAT_DATE_INPUT
            = DateTimeFormatter.ofPattern(DATE_INPUT_PATTERN);

    /**
     * Determine the {@link DateInputType} of the given input string.
     * Input can either be date only (yyyy-MM-dd) or datetime(yyyy-MM-dd HH:mm)
     * @param input Input string to check
     * @return {@link #DATE_ONLY} if input matches date pattern
     *         {@link #DATETIME} if input matches datetime pattern
     * @throws InvalidFormatException if the input does not match any valid format
     */
    public static DateInputType datetimeInput(String input)
            throws InvalidFormatException {

        if (input.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return DATE_ONLY;
        } else if (input.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")) {
            return DATETIME;
        } else {
            throw new InvalidFormatException("Oops! Invalid date format. " +
                    "\n Use `" + DATE_INPUT_PATTERN +"` " +
                    "or `" + DATETIME_INPUT_PATTERN +"`");
        }
    }
}
