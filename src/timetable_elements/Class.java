package timetable_elements;

public class Class {
    int classId;
    int groupId;
    int moduleId;
    int lecturerId;
    int practikId;
    int timeslotId;
    int roomId;

    public Class(int classId, int groupId, int moduleId) {
        this.classId = classId;
        this.moduleId = moduleId;
        this.groupId = groupId;
    }

    public void addLecturer(int lecturerId) {
        this.lecturerId = lecturerId;
    }

    public void addPractik(int practikId) {
        this.practikId = practikId;
    }

    public void addTimeslot(int timeslotId) {
        this.timeslotId = timeslotId;
    }

    public int getClassId() {
        return this.classId;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public int getModuleId() {
        return this.moduleId;
    }

    public int getlecturerId() {
        return this.lecturerId;
    }

    public int getPractikId() {
        return this.practikId;
    }

    public int getTimeslotId() {
        return this.timeslotId;
    }

    public int getRoomId() {
        return this.roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
