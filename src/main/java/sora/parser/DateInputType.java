package sora.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import sora.exception.InvalidFormatException;

/**
 * Enum representing types of date/time inputs
 * <p>
 * There are 2 types of input:
 * <ul>
 *     <li>{@link #DATE_ONLY} - Input only contains the date </li>
 *     <li>{@link #DATETIME} - Input contains both date and time </li>
 * </ul>
 * This enum also provides constants for parsing and displaying
 * date and datetime in a specified format.
 */
public enum DateInputType {
    DATE_ONLY,
    DATETIME;

    /** Pattern for displaying date only (e.g. "Jan 22 2026") */
    public static final String DATE_OUTPUT_PATTERN = "MMM dd uuuu";

    /**
     * Pattern for displaying date and time in 24-hour format
     * (e.g. "Jan 22 2026 14:00")
     */
    public static final String DATETIME_OUTPUT_PATTERN =
            "MMM dd uuuu HH:mm";

    /**
     * Pattern for parsing date and time in 24-hour format
     * (e.g. "yyyy-MM-dd HH:mm")
     */
    public static final String DATETIME_INPUT_PATTERN =
            "uuuu-MM-dd HH:mm";

    /**
     * Pattern for parsing date only
     * (e.g. "yyyy-MM-dd")
     */
    public static final String DATE_INPUT_PATTERN = "uuuu-MM-dd";


    /** Format to display date and time (24hr) (e.g. Jan 22 2026 13:00) */
    public static final DateTimeFormatter FORMAT_DATETIME_DISPLAY =
            DateTimeFormatter.ofPattern(DATETIME_OUTPUT_PATTERN);

    /** Format to display date only (e.g. Jan 22 2026) */
    public static final DateTimeFormatter FORMAT_DATE_DISPLAY =
            DateTimeFormatter.ofPattern(DATE_OUTPUT_PATTERN);

    /**
     * Input parser for parsing date and time (24hr) input.
     * (e.g. 2026-01-22 13:00)
     */
    public static final DateTimeFormatter FORMAT_DATETIME_INPUT =
            DateTimeFormatter.ofPattern(DATETIME_INPUT_PATTERN)
                    .withResolverStyle(ResolverStyle.STRICT);

    /**
     * Input parser for parsing date only input
     * (e.g. 2026-01-22)
     */
    public static final DateTimeFormatter FORMAT_DATE_INPUT =
            DateTimeFormatter.ofPattern(DATE_INPUT_PATTERN)
                    .withResolverStyle(ResolverStyle.STRICT);

    /**
     * Determines the {@link DateInputType} of the given input string.
     * <p>
     * The input can either be date only (yyyy-MM-dd) or datetime(yyyy-MM-dd HH:mm).
     *
     * @param input The input string to check.
     * @return {@link #DATE_ONLY} if the input matches date-only pattern
     *         {@link #DATETIME} if the input matches datetime pattern.
     * @throws InvalidFormatException If the input does not match any valid format.
     */
    public static DateInputType datetimeInput(String input)
            throws InvalidFormatException {
        if (input == null) {
            throw new InvalidFormatException("Date input must not be null");
        }

        if (input.length() == DATE_INPUT_PATTERN.length()
                && isValidDate(input)) {
            return DATE_ONLY;
        } else if (input.length() == DATETIME_INPUT_PATTERN.length()
                && isValidDateTime(input)) {
            return DATETIME;
        } else {
            throw new InvalidFormatException("Oops! Invalid date format."
                    + "\n Use `" + DATE_INPUT_PATTERN + "` "
                    + "or `" + DATETIME_INPUT_PATTERN + "`");
        }
    }

    private static boolean isValidDate(String inputDate) {
        try {
            LocalDate date = LocalDate.parse(inputDate, FORMAT_DATE_INPUT);
            return inputDate.equals(date.format(FORMAT_DATE_INPUT));
        } catch (DateTimeParseException dateTimeParseException) {
            throw new InvalidFormatException("Oops! This is an invalid date");
        }
    }
}
