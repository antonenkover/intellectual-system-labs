package timetable_elements;

public class Group {
    int groupId;
    int groupSize;
    int[] moduleIds;
    String groupName;

    public Group(int groupId, String groupName, int groupSize, int[] moduleIds) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupSize = groupSize;
        this.moduleIds = moduleIds;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public int getGroupSize() {
        return this.groupSize;
    }

    public int[] getModuleIds() {
        return this.moduleIds;
    }

    public String getGroupName() {
        return groupName;
    }
}
