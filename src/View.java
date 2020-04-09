import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * View - Class which deals with parsing the input from a txt file and calls the methods needed to solve the problem
 * fields:
 *      ctrl Controller - controller which holds the methods needed for the computation
 *      filename String - filename of the input txt file
 */
public class View {
    Controller ctrl;
    String filename;

    /**
     * Parameterized Constructor
     * @param ctrl Controller
     * @param filename String
     */
    public View(Controller ctrl, String filename) {
        this.ctrl = ctrl;
        this.filename = filename;
    }

    /**
     * Method which runs the application
     * @Input from text file:
     * 1st line: booked time intervals for the first individual of the form [['HH:MM','HH:MM'], ...]
     * 2nd line: limit of calendar for the first individual of the form ['HH:MM','HH:MM']
     * 3rd line: booked time intervals for the second individual of the form [['HH:MM','HH:MM'], ...]
     * 4th line: limit of calendar for the second individual of the form ['HH:MM','HH:MM']
     * 5th line: time needed for the meeting in minutes
     * @Output in console:
     * List with the available time
     * @throws IOException - for unsuccessful file operations
     */
    void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.filename))) {
            //file parsing and adding as input of the first individual for the Controller
            //the list of booked intervals
            String[] timesCalendar1 = reader.readLine().replaceAll("[\\]\\[\\' ]", "").split(",");
            for (int i = 0; i < timesCalendar1.length - 1; i+=2) {
                String[] splitTime1 = timesCalendar1[i].split(":");
                String[] splitTime2 = timesCalendar1[i + 1].split(":");
                this.ctrl.addIntervalCalendar1(new Calendar.Time(Integer.parseInt(splitTime1[0]), Integer.parseInt(splitTime1[1])),
                        new Calendar.Time(Integer.parseInt(splitTime2[0]), Integer.parseInt(splitTime2[1])));
            }
            //the limit of the schedule for the first individual
            String[] limitCalendar1 = reader.readLine().replaceAll("[\\]\\[\\' ]", "").split(",");
            this.ctrl.setLimitCalendar1(new Pair<>(
                    new Calendar.Time(Integer.parseInt(limitCalendar1[0].split(":")[0]), Integer.parseInt(limitCalendar1[0].split(":")[1])),
                    new Calendar.Time(Integer.parseInt(limitCalendar1[1].split(":")[0]), Integer.parseInt(limitCalendar1[1].split(":")[1]))
            ));


            //file parsing and adding as input of the first individual for the Controller
            //the list of booked intervals
            String[] timesCalendar2 = reader.readLine().replaceAll("[\\]\\[\\' ]", "").split(",");
            for (int i = 0; i < timesCalendar2.length - 1; i+=2) {
                String[] splitTime1 = timesCalendar2[i].split(":");
                String[] splitTime2 = timesCalendar2[i + 1].split(":"); //error here
                this.ctrl.addIntervalCalendar2(new Calendar.Time(Integer.parseInt(splitTime1[0]), Integer.parseInt(splitTime1[1])),
                        new Calendar.Time(Integer.parseInt(splitTime2[0]), Integer.parseInt(splitTime2[1])));
            }
            //the limit of the schedule for the first individual
            String[] limitCalendar2 = reader.readLine().replaceAll("[\\]\\[\\' ]", "").split(",");
            this.ctrl.setLimitCalendar2(new Pair<>(
                    new Calendar.Time(Integer.parseInt(limitCalendar2[0].split(":")[0]), Integer.parseInt(limitCalendar2[0].split(":")[1])),
                    new Calendar.Time(Integer.parseInt(limitCalendar2[1].split(":")[0]), Integer.parseInt(limitCalendar2[1].split(":")[1]))
            ));

            //parsing the meeting time needed
            this.ctrl.setMeetingTime(Integer.parseInt(reader.readLine()));
            //showing output
            System.out.println(this.ctrl.getFreeTime());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
