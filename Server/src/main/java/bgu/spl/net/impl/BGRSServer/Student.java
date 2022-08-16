package bgu.spl.net.impl.BGRSServer;
import java.util.Collections;
import java.util.LinkedList;

public class Student extends User {

    private LinkedList<Short> currentCoursesList;


    public Student(String userName, String password) {
        super(userName, password);
        currentCoursesList = new LinkedList<>();
    }

    public synchronized void registerCourse(short courseNum, Course course) {
        currentCoursesList.addLast(courseNum);
        course.setNumOfRegStudents(true);
    }

    public synchronized void unRegisterCourse(short courseNum, Course course) {
        for (int i = 0; i < currentCoursesList.size(); i++) {
            if (currentCoursesList.get(i) == courseNum)
                currentCoursesList.remove(currentCoursesList.get(i));
        }
        course.setNumOfRegStudents(false);
    }


    public LinkedList<Short> getCurrentCoursesList() {
        return currentCoursesList;
    }

    public LinkedList<Short> getCoursesByOrder() {
        Collections.sort(currentCoursesList, new ComparatorBySerialNum());
        return currentCoursesList;
    }


}
