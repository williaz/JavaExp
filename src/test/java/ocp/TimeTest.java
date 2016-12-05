package ocp;

import org.junit.Test;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import static org.junit.Assert.*;

/**
 * Created by williaz on 12/3/16.
 * Watch out:
 * 1. Mismatch of methods in LocalDateTime, LocalDate, LocalTime
 * 2. DataTimeFormatter's ofLocalizedXxx
 * 3. Period: P_Y_M_D; Duration: PT_H_M_S
 * 4. When deal with time zones, it is best to convert to GMT first by substracting the time zone.
 * 5. For daylight saving time, when calculating the time between, the time zone offset change matters!
 * 6. Java is smart enough to adjust the daylight savings time
 * 7. Properties get() no allow default value, getPreoperty() do!
 */
public class TimeTest {
    @Test
    public void test_Time() {
        LocalDate birth = LocalDate.of(1990, 10, 23);
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalTime midnight1 = LocalTime.of(0, 0, 0); //Invalid value for HourOfDay (valid values 0 - 23)
        LocalDateTime newYearEve = LocalDateTime.of(2017, 1, 1, 0, 0); //sec no need
        assertEquals(midnight, midnight1);
        System.out.println(birth);
        System.out.println(newYearEve); //Java uses T to separate the date and time when converting LocalDateTime to a String .
        System.out.println(ZonedDateTime.now()); //UTC offset - time zones away from Greenwich Mean Time (GMT).

        LocalDate today = LocalDate.of(2016, Month.DECEMBER, 3);
        assertTrue(Month.DECEMBER.ordinal() == 11);
        //Java is smart enough to hide the seconds and nanoseconds when we aren’t using them.
        LocalDate nowaday = birth.plusYears(26).plusMonths(2).minusDays(20);
        assertEquals(today, nowaday);

        System.out.println(today.getMonth() + " - "+ today.getMonthValue());
    }

    @Test(expected = DateTimeException.class)
    public void test_DateMismatch() {
        LocalDate birth = LocalDate.of(1990, 2, 29); //February 29 exists only in a leap year. Leap
        //runtime exception, similar to IllegalArgumentException
    }

    /**
     * Be able to perform calculations between times using UTC. Whether the format is -05:00, GMT-5, or UTC-5,
     *   you calculate by subtracting the offset from the time and then seeing how far the resulting times are.
     */

    @Test
    public void test_ZonedDateTime() {
        ZoneId.getAvailableZoneIds()
                .stream()
                .filter(s -> s.contains("China") || s.contains("PRC") || s.contains("Shanghai"))
                .forEach(System.out::println); //Try the country name or city name.
        ZoneId beijing = ZoneId.of("PRC");
        ZoneId beijing1 = ZoneId.of("Asia/Shanghai");
        ZonedDateTime homeNow = ZonedDateTime.of(LocalDateTime.now(), beijing1);
        System.out.println(homeNow);
        assertEquals(LocalTime.now().getHour(), ZonedDateTime.now(beijing).now().getHour());
    }

    /**
     * P1Y2W3D
     */
    @Test
    public void test_Period() {
        Period moore = Period.ofMonths(18);
        LocalDate epoch = LocalDate.of(1970, Month.JANUARY, 1);
        int counter = 0;
        while(epoch.plus(moore).isBefore(LocalDate.now())) {
            epoch = epoch.plus(moore);
            System.out.print(++counter+"th Evolution; ");
        }
        //It’s OK to have more days than are in a month.
        // Also it is OK to have more months than are in a year.
        //P1Y2M3D
        Period certain = Period.of(2, 14, 45);
        System.out.println("\n"+certain);
        Period weeks = Period.ofWeeks(3);
        System.out.println(weeks); //P21D
        LocalDateTime ocp = LocalDateTime.now();
        ocp = ocp.plus(Period.ofDays(32));
        System.out.println(ocp);
    }

    /**
     * Duration is output beginning with PT, which you can think of as a period of time.
     * PT1H2M3.004000006S
     * Duration doesn’t have a constructor that takes multiple units like Period does.
     *
     * ChronoUnit is a great way to determine how far apart two Temporal values are.
     * Temporal includes LocalDate, LocalTime, and so on.
     */
    @Test
    public void test_Duration() {
        System.out.println(Duration.ofMinutes(4));
        Duration duration = Duration.ofDays(2);
        duration = duration.plusSeconds(64).plusMillis(12).plusNanos(5);
        System.out.println(duration);
        Duration d2 = Duration.of(5, ChronoUnit.HALF_DAYS); // DAYS, HALF_DAYS, HOURS, ...
        System.out.println(d2);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime todayEvent = LocalDateTime.now().plusHours(13);
        assertEquals(-1, ChronoUnit.HALF_DAYS.between(todayEvent, now));// truncate, not round

        ZonedDateTime beijingTime = ZonedDateTime.now(ZoneId.of("PRC"));
        System.out.println(beijingTime.plus(Duration.ofHours(-13)));

    }

    /**
     * The Instant class represents a specific moment in time in the GMT time zone.
     * If you have a ZonedDateTime, you can turn it into an Instant
     * If you have the number of seconds since 1970, you can also create an Instant that way
     * Instant allows you to add any unit day or smaller
     */
    @Test
    public void test_Instant() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(ZonedDateTime.of(now, ZoneId.of("US/Eastern")));
        Instant nowIn = Instant.ofEpochSecond(now.toEpochSecond(ZoneOffset.ofHours(-5)));
        Instant beijingIn = ZonedDateTime.now(ZoneId.of("PRC")).toInstant();
        Duration cptTimer = Duration.between(nowIn, beijingIn);
        System.out.println(cptTimer);
        System.out.println(nowIn.plus(4, ChronoUnit.DAYS));
    }

    /**
     * Daylight Saving Time:
     * Edge case: there is one day in March that is 23 hours long(miss 2:00 - 2:59) and one day in November that is 25 hours long.
     * The UTC offset also changes.
     */
    @Test
    public void test_DST() {
        LocalDateTime springForward = LocalDateTime.of(2016, Month.MARCH, 13, 1, 0);
        ZonedDateTime east = ZonedDateTime.of(springForward, ZoneId.of("US/Eastern"));
        System.out.println(east);
        ZonedDateTime eastS = east.plusHours(1); //immutable
        System.out.println(eastS);
        long springHour = ChronoUnit.HOURS.between(east, eastS); //1!!
        System.out.println(springHour);


        LocalDateTime fallBack = LocalDateTime.of(2016, Month.NOVEMBER, 6, 1, 0);
        ZonedDateTime east1 = ZonedDateTime.of(fallBack, ZoneId.of("US/Eastern"));
        System.out.println(east1);
        ZonedDateTime east1F = east1.plusHours(1); //immutable
        System.out.println(east1F);
        long fallHour = ChronoUnit.HOURS.between(east, eastS); //1!!
        System.out.println(fallHour);

        LocalDateTime empty = LocalDateTime.of(2016, Month.MARCH, 13, 2, 0); // no existing
        ZonedDateTime east2 = ZonedDateTime.of(empty, ZoneId.of("US/Eastern"));
        System.out.println(east2);
    }

    /**
     * Internationalization(i18n) is the process of designing your program so it can be adapted.
     *    This involves placing strings in a property file and using classes like DateFormat
     *    so that the right format is used based on user preferences.
     * Localization(l10n) means actually supporting multiple locales.
     *
     * The locale[ae] builder converts to uppercase or lowercase for you as needed
     */
    @Test
    public void test_Locale() {
        Locale locale = Locale.getDefault();
        System.out.println(locale); //Language(lowercase)(_Country)
        Locale here = new Locale("en", "US");
        Locale us = new Locale.Builder().setLanguage("en").setRegion("us").build();
        assertEquals(here, us);
        assertEquals(Locale.US, us);
        //the Locale changes only for that one Java program.
        Locale.setDefault(Locale.CHINA);
        System.out.println(Locale.getDefault());
    }

    /**
     * A resource bundle contains the local specifi c objects to be used by a program.
     *    It is like a map with keys and values. The resource bundle can be in a property fi le or in a Java class.
     * A property fi le is a fi le in a specifi c format with key/value pairs.
     *
     * use "=", (":", " ")
     * #.java -> .properties, most specific -> default one -> most general
     *
     * 1. Always look for the property file after the matching Java class.
     * 2. Drop one thing at a time if there are no matches. First drop the country and then the language.
     * 3. Look at the default locale and the default resource bundle last.
     *
     * it is common to substitute variables in the middle of a resource bundle string.
     *    The convention is to use a number inside brackets such as {0}, {1}.
     *    we can run it through the MessageFormat class to substitute the parameters. varargs
     */
    @Test
    public void test_ResourceBundle() {
        ResourceBundle rbd = ResourceBundle.getBundle("ocp.Menu"); // default locale
        ResourceBundle rb1 = ResourceBundle.getBundle("ocp.Menu", Locale.CANADA);
        Properties p = new Properties();
        rb1.keySet().stream().peek(System.out::println).forEach(k -> p.put(k, rb1.getString(k)));
        System.out.println("\n" + p.getProperty("Options", "Rice"));
        System.out.println(p.getProperty("Hello"));
        System.out.println(p.get("Cook"));
        System.out.println();

        ResourceBundle rb = ResourceBundle.getBundle("ocp.Menu", Locale.CHINA);
        Enumeration<String> keys = rb.getKeys();
        while (keys.hasMoreElements()) {
            System.out.println(rb.getObject(keys.nextElement()));
        }

        ResourceBundle rb2 = ResourceBundle.getBundle("ocp.Menu_en_UK", Locale.UK); //no ocp.Menu ??
        assertTrue(rb2.containsKey("Options"));
        System.out.println("\n" + rb2.getObject("Options"));
        Menu_en_UK.Bus bus = (Menu_en_UK.Bus) rb2.getObject("Bus");
        System.out.println(bus.getName());

        String format = rbd.getString("VIP");
        String formatted = MessageFormat.format(format, "Tammy", "Birthday", "Year");
        System.out.print(formatted);
    }

    /**
     * Format and Parse Numbers and Currency
     *
     * The parse method parses only the beginning of a string.
     *    After it reaches a character that cannot be parsed, the parsing stops and the value is returned.
     */
    @Test
    public void test_NumberFormat() {
        NumberFormat generalFmt= NumberFormat.getInstance(Locale.CHINA);
        NumberFormat numFmt= NumberFormat.getNumberInstance(Locale.GERMAN);
        NumberFormat percFmt = NumberFormat.getPercentInstance();
        NumberFormat dollarFmt= NumberFormat.getCurrencyInstance();
        NumberFormat yuanFmt= NumberFormat.getCurrencyInstance(Locale.CHINA);
        System.out.println(generalFmt.format(43451.435));
        System.out.println(numFmt.format(43451.435));
        System.out.println(percFmt.format(43451.435));
        System.out.println(dollarFmt.format(43451.435));
        BigDecimal money = new BigDecimal(53242.234);
        System.out.println(yuanFmt.format(money));

        try {
            double num = (Double)numFmt.parse("45,23");
            System.out.println(num);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            double mny = (Double) dollarFmt.parse("$899.99");
            System.out.println(mny);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * DateTimeFormatter can be used to format any type of date and/or time object.
     * Remember M (uppercase) is month and m (lowercase) is minute
     * yyyy MM dd hh MM , :
     */
    @Test
    public void test_DateTimeFormatter() {
        DateTimeFormatter shortFmt = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        System.out.println(shortFmt.format(LocalDate.of(2016, 12, 4))); //partial
        DateTimeFormatter mediumFmt = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);
        System.out.println(mediumFmt.format(LocalDateTime.now()));

        DateTimeFormatter customF = DateTimeFormatter.ofPattern("yy-MM-dd, hh:mm");
        String today = customF.format(LocalDateTime.now());
        System.out.println(today);
        LocalDate rnow = LocalDate.parse("12/4/16", shortFmt);
        LocalDate rnow1 = LocalDate.parse("16-12-04, 06:48", customF);
    }

}
