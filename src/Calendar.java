import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/** @author Radu
 * Calendar - Class modeling the booked time of an individual during one day
 * fields:
 *      limitTimes Pair<Time, Time> - the hours in between which the individual has a program
 *      bookedTimes List<Pair<Time, Time>> - list of pairs of hours in which the individual is busy
 */
public class Calendar {
    /**
     * Time - Class modeling the time
     * fields:
     *      hour int
     *      minute int
     */
    public static class Time implements Comparable<Time> {
        private int hour;
        private int minute;

        /**
         * Parameterized Constructor
         * @param h int
         * @param m int
         */
        public Time(int h, int m) {
            hour = h;
            minute = m;
        }

        /**
         * No-arg Constructor
         */
        public Time() {
            hour = 0;
            minute = 0;
        }

        /**
         * Computes the difference between two hours in minutes
         * @param other Time - the time on which the operation is done
         * @return int - the difference
         */
        public int differenceInMins(Time other) {
            return abs((this.hour - other.hour) * 60 + this.minute - other.minute);
        }

        @Override
        public int compareTo(Time time) {
            if (this.hour < time.hour) {
                return -1;
            } else if (this.hour == time.hour) {
                if (this.minute < time.minute) {
                    return -1;
                } else if (this.minute == time.minute) {
                    return 0;
                } else return 1;
            } else return 1;
        }

        @Override
        public String toString() {
            String m = Integer.toString(minute);
            String h = Integer.toString(hour);
            if (hour < 10) h = "0" + hour;
            if (minute < 10) m = "0" + minute;
            return h + ":" + m;
        }
    }

    private Pair<Time, Time> limitTimes;
    private List<Pair<Time, Time>> bookedTimes;

    /**
     * Parameterized Constructor
     * @param limitTimes Pair<Time, Time>
     * @param bookedTimes List<Pair<Time, Time>
     */
    public Calendar(Pair<Time, Time> limitTimes, List<Pair<Time, Time>> bookedTimes) {
        this.limitTimes = limitTimes;
        this.bookedTimes = bookedTimes;
    }

    /**
     * No-arg Constructor
     */
    public Calendar() {
        bookedTimes = new ArrayList<>();
    }

    /**
     * Adds a new interval in which the individual is busy
     * @param time1 Time - starting time
     * @param time2 Time - end time
     */
    public void addBookedInterval(Time time1, Time time2) {
        bookedTimes.add(new Pair<>(time1, time2));
    }


    /*----------------GETTERS AND SETTERS----------------*/
    public Pair<Time, Time> getLimitTimes() {
        return limitTimes;
    }

    public void setLimitTimes(Pair<Time, Time> limitTimes) {
        this.limitTimes = limitTimes;
    }

    public List<Pair<Time, Time>> getBookedTimes() {
        return bookedTimes;
    }

    public void setBookedTimes(List<Pair<Time, Time>> bookedTimes) {
        this.bookedTimes = bookedTimes;
    }

    @Override
    public String toString() {
        return "Calendar{" +
                "limitTimes=" + limitTimes +
                ", bookedTimes=" + bookedTimes +
                '}';
    }
}
