package ocp;

import org.junit.Test;

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
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;

/**
 * Created by williaz on 12/3/16.
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
    }

    @Test(expected = DateTimeException.class)
    public void test_DateMismatch() {
        LocalDate birth = LocalDate.of(1990, 2, 29); //February 29 exists only in a leap year. Leap
        //runtime exception, similar to IllegalArgumentException
    }

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
        //P1Y2W3D
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
        Duration duration = Duration.ofDays(2);
        duration = duration.plusSeconds(34).plusMillis(12).plusNanos(5);
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
        east = east.plusHours(1); //immutable
        System.out.println(east);

        LocalDateTime fallBack = LocalDateTime.of(2016, Month.NOVEMBER, 6, 1, 0);
        ZonedDateTime east1 = ZonedDateTime.of(fallBack, ZoneId.of("US/Eastern"));
        System.out.println(east1);
        east1 = east1.plusHours(1); //immutable
        System.out.println(east1);

        LocalDateTime empty = LocalDateTime.of(2016, Month.MARCH, 13, 2, 0); // no existing
        ZonedDateTime east2 = ZonedDateTime.of(empty, ZoneId.of("US/Eastern"));
        System.out.println(east2);
    }



}
