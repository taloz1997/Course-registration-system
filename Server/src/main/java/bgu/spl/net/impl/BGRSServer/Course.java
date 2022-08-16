package bgu.spl.net.impl.BGRSServer;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Course {
    private String courseName;
    private short courseNum;
    private LinkedList<Short> kdamCoursesList;
    private int numOfMaxStudents;
    private int numOfRegStudents;
    private LinkedList<String> studentList;
    private int serialNumber;

    public Course(String courseName, short courseNum, LinkedList<Short> kdamCourses, int numOfMaxStudents, int serialNumber) {
        this.courseName = courseName;
        this.courseNum = courseNum;
        this.kdamCoursesList = kdamCourses;
        this.numOfMaxStudents = numOfMaxStudents;
        numOfRegStudents = 0;
        studentList = new LinkedList<>();
        this.serialNumber = serialNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCourseNum() {
        return courseNum;
    }

    public LinkedList<Short> getKdamCoursesList() {
        return kdamCoursesList;
    }

    public int getNumOfMaxStudents() {
        return numOfMaxStudents;
    }

    public synchronized void setNumOfRegStudents(boolean regOrUnReg) {
        if (regOrUnReg)
            numOfRegStudents++;
        else
            numOfRegStudents--;
    }

    public synchronized void addStudent(String studentName) {
        studentList.add(studentName);
    }

    public synchronized void deleteStudent(String studentName) {
        studentList.remove(studentName);
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int counter) {
        serialNumber = counter;
    }

    public int numOfseatsAvailable() {
        return numOfMaxStudents - numOfRegStudents;
    }

    public boolean areKdamCourses(LinkedList allCourses) {
        for (int i = 0; i < kdamCoursesList.size(); i++) {
            short currNumCourse = kdamCoursesList.remove(i);  //to make the loop more efficient
            kdamCoursesList.addLast(currNumCourse);
            if (!allCourses.contains(currNumCourse)) {
                return false;
            }
        }
        return true;
    }

    public LinkedList<String> getOrderedStudentList() {
        Collections.sort(studentList);
        return studentList;

    }
}
