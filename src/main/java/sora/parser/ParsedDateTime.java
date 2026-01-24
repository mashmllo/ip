package sora.parser;

import sora.exception.InvalidFormatException;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a parsed date or datetime input
 * <p>
 * Helps to distinguish between date-only and date-with-time inputs,
 * and provide formatted representations for display and storage.
 */
public class ParsedDateTime {

    private final LocalDateTime dateTime;
    private final DateInputType type;

    /**
     * Constructs a new {@link ParsedDateTime} instance.
     *
     * @param dateTime The parsed {@link LocalDateTime}.
     * @param type     The {@link DateInputType} indicating if the input
     *                 contains date only or date with time.
     */
    public ParsedDateTime(LocalDateTime dateTime, DateInputType type) {
        this.dateTime = dateTime;
        this.type = type;
    }

    /**
     * Retrieves the date component of the input.
     *
     * @return The {@link LocalDate} representing the date component.
     */
    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    /**
     * Returns the formatted string representation of the parsed date or datetime.
     *
     * @return A formatted string suitable for display.
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
     * Returns a string representation for storage, which can be
     * parsed back into a {@link ParsedDateTime} instance upon loading.
     *
     * @return A formatted string suitable for storage.
     */
    public String toStorageString() {
        if (type == DateInputType.DATE_ONLY) {
            return dateTime.format(DateInputType.FORMAT_DATE_INPUT);
        } else  {
            return dateTime.format(DateInputType.FORMAT_DATETIME_INPUT);
        }
    }

    /**
     * Parses a string input into a {@link ParsedDateTime} instance.
     *
     * @param input The input string to be parsed.
     * @return A {@link ParsedDateTime} instance representing the parsed input.
     * @throws InvalidFormatException If the input does not match a valid date
     *                                or datetime format.
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
