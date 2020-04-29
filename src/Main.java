import ga.GeneticAlgorithm;
import ga.Population;
import timetable_elements.Class;
import timetable_elements.Group;
import timetable_elements.Timetable;
import ui.TimetableUI;

import javax.swing.*;
import java.util.HashMap;

public class Main {

    public static void main(String[] args)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        Timetable timetable = createTimetable();
        //CHOOSE DESIRED VALUES FOR GA AND ENJOY
        GeneticAlgorithm ga = new GeneticAlgorithm(100,
                0.01,
                0.9,
                2,
                5);
        Population population = ga.initPopulation(timetable);
        ga.evaluatePopulation(population, timetable);
        int generation = 1;
        while (!ga.isTerminationConditionMet(generation, 1000) && !ga.isTerminationConditionMet(population)) {
            timetable.createClasses(population.getFittest(0));
            population = ga.crossoverPopulation(population);
            population = ga.mutatePopulation(population, timetable);
            ga.evaluatePopulation(population, timetable);
            generation++;
        }
        timetable.createClasses(population.getFittest(0));
        Class[] classes = timetable.getClasses();
        int classIndex = 1;
        Group[] groups = timetable.getGroupsAsArray();
        HashMap<Integer, TimetableUI> winds = new HashMap<>();
        for (Group group : groups) {
            TimetableUI timetableUI = new TimetableUI();
            timetableUI.setTitle(timetableUI.getTitle() + " " + group.getGroupName());
            winds.put(group.getGroupId(), timetableUI);
        }
        for (Class bestClass : classes) {
            System.out.println("Class " + classIndex + ":");
            System.out.println("Subject: " + timetable.getModule(bestClass.getModuleId()).getCourseName());
            int groupId = timetable.getGroup(bestClass.getGroupId()).getGroupId();
            System.out.println("Group: " + timetable.getGroup(bestClass.getGroupId()).getGroupName());
            System.out.println("Room: " + timetable.getRoom(bestClass.getRoomId()).getRoomNumber());
            System.out.println("Lecturer: " + timetable.getlecturer(bestClass.getlecturerId()).getLecturerName());
            System.out.println("Practik: " + timetable.getPractik(bestClass.getPractikId()).getPractikName());
            System.out.println("Time: " + timetable.getTimeslot(bestClass.getTimeslotId()).getTimeslot());
            winds.get(groupId).addClass(bestClass, timetable);
            System.out.println("-------------");
            classIndex++;
        }
    }

    private static Timetable createTimetable() {
        Timetable timetable = new Timetable();
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        //ADD ROOMS FOR CLASSES
        timetable.addRoom(1, "302", 33);
        timetable.addRoom(2, "40", 33);
        timetable.addRoom(3, "39", 33);
        timetable.addRoom(4, "305", 33);
        timetable.addRoom(5, "308", 33);
        timetable.addRoom(6, "01", 33);

        for (int i = 0; i < days.length; i++) {
            timetable.addTimeslot(1 + i * 6, days[i] + " 8:40 - 10:15");
            timetable.addTimeslot(2 + i * 6, days[i] + " 10:35 - 12:10");
            timetable.addTimeslot(3 + i * 6, days[i] + " 12:20 - 13:55");
            timetable.addTimeslot(4 + i * 6, days[i] + " 14:05 - 15:45");
        }

        //ADD LECTURERS
        timetable.addLecturer(1, "Ivan Ivanovich");
        timetable.addLecturer(2, "Petr Petrovich");
        timetable.addLecturer(3, "Vasyl Vasilyovich");
        timetable.addLecturer(4, "Sergey Sergeyevich");
        timetable.addLecturer(5, "Oleg Olegovich");
        timetable.addLecturer(6, "Marina Marinovna");

        //ADD PRAKTIKS
        timetable.addPractik(1, "John");
        timetable.addPractik(2, "Will");
        timetable.addPractik(3, "Mark");
        timetable.addPractik(4, "Danny");
        timetable.addPractik(5, "Drew");
        timetable.addPractik(6, "Irina Irinovna");

        //ADD MODULES AND TEACHERS FOR THEM
        timetable.addModule(1, "CS01", "Parallel programming", new int[]{1, 2}, new int[]{3, 5});
        timetable.addModule(2, "CS02", "Intellectual Systems", new int[]{3, 4}, new int[]{4, 3, 1});
        timetable.addModule(3, "CS03", "Refactoring", new int[]{5, 1}, new int[]{4, 3});
        timetable.addModule(4, "CS04", "Machine Learning", new int[]{3, 4}, new int[]{5, 1});
        timetable.addModule(5, "CS05", "Law", new int[]{6}, new int[]{6});
        timetable.addModule(6, "CS06", "Neural Networks", new int[]{1, 4}, new int[]{1, 2});
        timetable.addModule(7, "CS07", "Networking", new int[]{3, 5}, new int[]{1, 2, 3, 4, 5});

        //ENTER IDS OF MODULES TO ADD TO TIMETABLE
        timetable.addGroup(1, "MI-4", 30, new int[]{1, 1, 2, 2, 3, 3, 4, 5, 6, 6, 7, 7});
        //YOU CAN ADD OTHER GROUPS... but why would you?
//      timetable.addGroup(2, "TTP-42", 60, new int[]{1, 2, 3, 5, 5, 2, 7, 7, 7, 2});
//      timetable.addGroup(3, "TTP-41", 30, new int[]{1, 2, 3, 7, 2});
        return timetable;
    }
}
