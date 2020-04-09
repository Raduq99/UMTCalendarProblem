import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Controller - Class used for operations on the 2 calendars given as input to the problem
 * fields:
 *      calendar1 Calendar - calendar of first individual
 *      calendar2 Calendar - calendar of second individual
 *      meetingTime int - time needed for the meeting
 */
public class Controller {
    private Calendar calendar1;
    private Calendar calendar2;
    private int meetingTime;

    /**
     * Parameterized Constructor
     * @param calendar1 Calendar
     * @param calendar2 Calendar
     * @param meetingTime int
     */
    public Controller(Calendar calendar1, Calendar calendar2, int meetingTime) {
        this.calendar1 = calendar1;
        this.calendar2 = calendar2;
        this.meetingTime = meetingTime;
    }

    /**
     * No-arg Constructor
     */
    public Controller() {
        this.calendar1 = new Calendar();
        this.calendar2 = new Calendar();
        this.meetingTime = 0;
    }

    /**
     * Method which computes the time intervals in which the two individuals can meet
      * @return List<Pair<Time, Time>> - result of the computation
     */
    public List<Pair<Calendar.Time, Calendar.Time>> getFreeTime() {
        //new empty array which will be the result
        List<Pair<Calendar.Time, Calendar.Time>> result = new ArrayList<>();
        //start from max of lower limit, end at min of upper limit
        Calendar.Time start = calendar1.getLimitTimes().getKey().compareTo(calendar2.getLimitTimes().getKey()) > 0 ? calendar1.getLimitTimes().getKey() : calendar2.getLimitTimes().getKey();
        Calendar.Time end = calendar1.getLimitTimes().getValue().compareTo(calendar2.getLimitTimes().getValue()) < 0 ? calendar1.getLimitTimes().getValue() : calendar2.getLimitTimes().getValue();

        //all booked intervals of the two individuals, sorted by the starting time and than by the ending time of the interval
        List<Pair<Calendar.Time, Calendar.Time>> commonSchedule = calendar1.getBookedTimes();
        commonSchedule.addAll(calendar2.getBookedTimes());
        commonSchedule.sort(Comparator.comparing(Pair::getKey));
        commonSchedule.sort(Comparator.comparing(Pair::getValue));

        //we keep in the commonSchedule List only the intervals which fit in the start-end interval
        Pair<Calendar.Time, Calendar.Time> firstInterval = commonSchedule.get(0);
        if (firstInterval.getKey().compareTo(start) > 0) {
            commonSchedule.add(0, new Pair<>(start, start));
        } else {
            if (firstInterval.getValue().compareTo(start) <= 0) {
                commonSchedule.remove(firstInterval);
                commonSchedule.add(0, new Pair<>(start, start));
            } else {
                commonSchedule.remove(firstInterval);
                commonSchedule.add(0, new Pair<>(start, firstInterval.getValue()));
            }
        }

        Pair<Calendar.Time, Calendar.Time> lastInterval = commonSchedule.get(commonSchedule.size() - 1);
        if (lastInterval.getKey().compareTo(end) > 0) {
            commonSchedule.remove(lastInterval);
            commonSchedule.add(commonSchedule.size(), new Pair<>(end, end));
        } else {
            if (lastInterval.getValue().compareTo(end) <= 0)
                commonSchedule.add(commonSchedule.size(), new Pair<>(end, end));
            else {
                commonSchedule.remove(lastInterval);
                commonSchedule.add(commonSchedule.size(), new Pair<>(lastInterval.getValue(), end));
            }
        }

        //for every busy interval in the common schedule we check if the ending time of the first interval
        // is after the starting time of the next interval and if it fits in the given time needed for the meeting
        for (int i = 0; i < commonSchedule.size() - 1; i++) {
            Calendar.Time first = commonSchedule.get(i).getValue();
            Calendar.Time second = commonSchedule.get(i + 1).getKey();
            if (first.compareTo(second) < 0 && first.differenceInMins(second) >= this.meetingTime) {
                result.add(new Pair<>(first, second));
            }
        }

        return result;
    }


    /*----------------GETTERS AND SETTERS----------------*/
    public int getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(int meetingTime) {
        this.meetingTime = meetingTime;
    }

    public void setLimitCalendar1(Pair<Calendar.Time, Calendar.Time> limit) {
        this.calendar1.setLimitTimes(limit);
    }

    public void setLimitCalendar2(Pair<Calendar.Time, Calendar.Time> limit) {
        this.calendar2.setLimitTimes(limit);
    }

    public void addIntervalCalendar1(Calendar.Time limit1, Calendar.Time limit2) {
        this.calendar1.addBookedInterval(limit1, limit2);
    }

    public void addIntervalCalendar2(Calendar.Time limit1, Calendar.Time limit2) {
        this.calendar2.addBookedInterval(limit1, limit2);
    }

}
