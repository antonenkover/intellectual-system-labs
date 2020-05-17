package timetable_elements;

public class Classroom {

    int roomId;
    int roomCapacity;
    String roomNumber;

    public Classroom(int roomId, int roomCapacity, String roomNumber) {
        this.roomId = roomId;
        this.roomCapacity = roomCapacity;
        this.roomNumber = roomNumber;
    }

    public int getRoomId() {
        return this.roomId;
    }

    public String getRoomNumber() {
        return this.roomNumber;
    }

    public int getRoomCapacity() {
        return this.roomCapacity;
    }
}