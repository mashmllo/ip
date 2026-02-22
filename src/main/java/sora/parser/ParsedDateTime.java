package sora.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;

import sora.exception.InvalidFormatException;

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
        assert dateTime != null : "dateTime must not be null";
        assert type != null : "DateInputType must not be null";
        this.dateTime = dateTime;
        this.type = type;
    }

    /**
     * Retrieves the date component of the input.
     *
     * @return The {@link LocalDate} representing the date component.
     */
    public LocalDate getDate() {
        return this.dateTime.toLocalDate();
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    /**
     * Returns the formatted string representation of the parsed date or datetime.
     *
     * @return A formatted string suitable for display.
     */
    @Override
    public String toString() {
        return (type == DateInputType.DATE_ONLY)
                ? this.dateTime.format(DateInputType.FORMAT_DATE_DISPLAY)
                : this.dateTime.format(DateInputType.FORMAT_DATETIME_DISPLAY);
    }

    /**
     * Returns a string representation for storage, which can be
     * parsed back into a {@link ParsedDateTime} instance upon loading.
     *
     * @return A formatted string suitable for storage.
     */
    public String toStorageString() {
        return (type == DateInputType.DATE_ONLY)
                ? this.dateTime.format(DateInputType.FORMAT_DATE_INPUT)
                : this.dateTime.format(DateInputType.FORMAT_DATETIME_INPUT);
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

        if (input == null) {
            throw new InvalidFormatException("Date input must not be null");
        }

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

    /**
     * Validates that the end {@link ParsedDateTime} is not before the start.
     *
     * @param start The start {@link ParsedDateTime}.
     * @param end   The end {@link ParsedDateTime}.
     * @return true if end is the same or after start; false if end is before start.
     */
    public static boolean isValidStartEnd(ParsedDateTime start, ParsedDateTime end) {
        return !end.getDateTime().isBefore(start.getDateTime());
    }
}
