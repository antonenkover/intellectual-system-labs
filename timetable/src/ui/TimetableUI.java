package ui;

import timetable_elements.Class;
import timetable_elements.Timetable;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;

public class TimetableUI extends JFrame {
    private static final String MONDAY = "Monday";
    JTable table;
    String[] columns = {"Time", MONDAY, "Tuesday", "Wednesday", "Thursday", "Friday"};
    String[][] rows = {{"8:40-10:15", "", "", "", "", "", "", ""},
            {"10:35-12:10", "", "", "", "", "", "", ""},
            {"12:20-13:55", "", "", "", "", "", "", ""},
            {"14:05-15:45", "", "", "", "", "", "", ""}};

    public TimetableUI() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        String lookAndFeel = "javax.swing.plaf.metal.MetalLookAndFeel";
        UIManager.setLookAndFeel(lookAndFeel);
        table = new JTable(rows, columns);
        table.setDragEnabled(false);
        table.setEnabled(false);
        TableColumn firstColumn = table.getColumnModel().getColumn(0);
        firstColumn.setPreferredWidth(100);
        firstColumn.setMaxWidth(100);
        firstColumn.setMinWidth(100);

        for (int i = 1; i < columns.length; i++) {
            TableColumn Column = table.getColumnModel().getColumn(i);
            Column.setPreferredWidth(170);
            Column.setMaxWidth(170);
            Column.setMinWidth(170);
        }
        TableColumn Column = table.getColumnModel().getColumn(columns.length - 1);
        Column.setHeaderValue("FREE DAY");
        table.setRowHeight(100);
        table.setPreferredScrollableViewportSize(new Dimension(1020, 250));
        JScrollPane scrollPane = new JScrollPane(table);
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
        this.setTitle("KNU CYBERNETICS TIMETABLE");
        this.setVisible(true);
        this.setSize(950, 450);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void addClass(Class bestClass, Timetable timetable) {
        String timeslot = timetable.getTimeslot(bestClass.getTimeslotId()).getTimeslot();
        String lecturer = timetable.getlecturer(bestClass.getlecturerId()).getLecturerName();
        String practik = timetable.getPractik(bestClass.getPractikId()).getPractikName();
        String group = timetable.getGroup(bestClass.getGroupId()).getGroupName();
        String course = timetable.getModule(bestClass.getModuleId()).getCourseName();
        String room = timetable.getRoom(bestClass.getRoomId()).getRoomNumber();
        String freeDay = "<html>No classes</html>";
        String info = "<html>Group:" + group + "<br>" + course + "<br>Lecturer:" + lecturer +
                "<br>Practik:" + practik + "<br>Room:" + room + "</html>";
        int row = addTime(timeslot);
        if (timeslot.startsWith("Monday")) {
            table.setValueAt(info, row, 1);
        } else if (timeslot.startsWith("Tuesday")) {
            table.setValueAt(info, row, 2);
        } else if (timeslot.startsWith("Wednesday")) {
            table.setValueAt(info, row, 3);
        } else if (timeslot.startsWith("Thursday")) {
            table.setValueAt(info, row, 4);
        }
        if (timeslot.startsWith("Friday")) {
            table.setValueAt(freeDay, 0, 5);
            table.setValueAt(freeDay, 1, 5);
            table.setValueAt(freeDay, 2, 5);
            table.setValueAt(freeDay, 3, 5);
        }
    }

    private int addTime(String timeslot) {
        int row = -1;
        String trimmedTimeslot = timeslot.split(" ")[1];
        if (trimmedTimeslot.startsWith("8:40")) {
            row = 0;
        } else if (trimmedTimeslot.startsWith("10:35")) {
            row = 1;
        } else if (trimmedTimeslot.startsWith("12:20")) {
            row = 2;
        } else if (trimmedTimeslot.startsWith("14:05")) {
            row = 3;
        }
        return row;
    }
}
