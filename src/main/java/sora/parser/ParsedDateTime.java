package sora.parser;

import sora.exception.InvalidFormatException;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a parsed date or datetime input
 * <p>
 * This helps to distinguish between date-only and datetime inputs
 */
public class ParsedDateTime {

    private final LocalDateTime dateTime;
    private final DateInputType type;

    /**
     * Constructs a new ParsedDateTime instance
     * @param dateTime The parsed {@link LocalDateTime}
     * @param type     The {@link DateInputType} indicating if the input
     *                 contains date only or date with time
     */
    public ParsedDateTime(LocalDateTime dateTime, DateInputType type) {
        this.dateTime = dateTime;
        this.type = type;
    }

    /**
     * Retrieve the date component of the input
     *
     * @return the date component
     */
    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    /**
     * Returns the formatted string representation of the parsed datetime.
     * @return formatted string representation
     */
    @Override
    public String toString() {
        if (type == DateInputType.DATE_ONLY) {
            return dateTime.format(DateInputType.FORMAT_DATE_DISPLAY);
        } else  {
            return dateTime.format(DateInputType.FORMAT_DATETIME_DISPLAY);
        }
    }

    /**
     * Returns a string representation for storage such that
     * it can be parsed back later on load
     *
     * @return formatted string for storage
     */
    public String toStorageString() {
        if (type == DateInputType.DATE_ONLY) {
            return dateTime.format(DateInputType.FORMAT_DATE_INPUT);
        } else  {
            return dateTime.format(DateInputType.FORMAT_DATETIME_INPUT);
        }
    }

    /**
     * Parsed the string input into a {@link ParsedDateTime} instance.
     *
     * @param input Input string to be parsed
     * @return a {@link ParsedDateTime} instance
     * @throws InvalidFormatException if input does not match a valid date or datetime
     */
    public static ParsedDateTime dateTimeParser(String input)
            throws InvalidFormatException {
        DateInputType type = DateInputType.datetimeInput(input);

        if (type == DateInputType.DATE_ONLY) {
            LocalDate date = LocalDate.parse(input, DateInputType.FORMAT_DATE_INPUT);
            return new ParsedDateTime(date.atStartOfDay(), type);
        } else {
            LocalDateTime dateTime = LocalDateTime.parse(input,
                    DateInputType.FORMAT_DATETIME_INPUT);
            return new ParsedDateTime(dateTime, type);
        }
    }
}
