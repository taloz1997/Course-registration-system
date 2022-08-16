package bgu.spl.net.impl.BGRSServer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {

    private ConcurrentHashMap<Short, Course> coursesMap;
    private ConcurrentHashMap<String, User> usersMap;
    Object lock;


    //to prevent user from creating new Database
    private Database() {
        coursesMap = new ConcurrentHashMap<>();
        usersMap = new ConcurrentHashMap<>();
        lock= new Object();

    }


    private static class SingeltonHolder {
        private static Database instance = new Database();
    }

    /**
     * Retrieves the single instance of this class.
     */
    public static Database getInstance() {
        return SingeltonHolder.instance;
    }

    /**
     * loades the courses from the file path specified
     * into the Database, returns true if successful.
     */
    boolean initialize(String coursesFilePath) {
        try {
            FileReader reader = new FileReader(coursesFilePath);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String textLine;
            int serialNumber = 0;
            while ((textLine = bufferedReader.readLine()) != null) {
                String[] input = textLine.split("\\|");
                short courseNum = Short.parseShort(input[0]);
                String courseName = input[1];
                LinkedList<Short> kdamCourses = new LinkedList<>();
                if (!input[2].equals("[]")) {
                    String[] x = input[2].substring(1, input[2].length() - 1).split(",");
                    for (int i = 0; i < x.length; i++)
                        kdamCourses.addLast(Short.parseShort(x[i]));
                }
                int maxNumOfStudents = Integer.parseInt(input[3]);
                coursesMap.put(courseNum, new Course(courseName, courseNum, kdamCourses, maxNumOfStudents, serialNumber));
                serialNumber++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;
    }

    public ConcurrentHashMap<Short, Course> getCoursesMap() {
        return coursesMap;
    }
    public ConcurrentHashMap<String, User> getUsersMap() {
        return usersMap;
    }



    public synchronized boolean addUser(char type, String username, String password) {
        if (!usersMap.containsKey(username)) {
            if(type == 's')
                usersMap.put(username, new Student(username, password)) ;
            else   //admin
                usersMap.put(username, new Admin(username, password)) ;

            return true;
        }
        return false;
    }

    public synchronized boolean loginUser(String userName, String password) {
        User user = usersMap.get(userName);
        if (usersMap.containsKey(userName)) {
            if (password.equals(user.getPassword()) && !user.isLoggedIn()) {
                user.setIsLoggedIn(true);
                return true;
            }
        }
        return false;
    }

    public synchronized boolean logoutUser(User user, String userName) {
        if (usersMap.containsKey(userName) && user.isLoggedIn()) {
            user.setIsLoggedIn(false);
            return true;
        }
        return false;
    }

    public boolean registerCourse(short courseNum, Student student) {
        synchronized (this) {
            if (coursesMap.containsKey(courseNum)) {
                Course regCourse = coursesMap.get(courseNum);
                if (regCourse.numOfseatsAvailable() > 0 && regCourse.areKdamCourses(student.getCurrentCoursesList()) && student.isLoggedIn()) {
                    regCourse.addStudent(student.getUserName());
                    student.registerCourse(courseNum, regCourse);
                    return true;
                }
            }
            return false;
        }
    }

    public String kdamCheckList(short courseNum) {
        if (coursesMap.containsKey(courseNum)) {
            LinkedList<Short> kdamCourseList = coursesMap.get(courseNum).getKdamCoursesList();
            Collections.sort(kdamCourseList, new ComparatorBySerialNum());
            return kdamCourseList.toString().replaceAll(" ", "");
        }
        return null;
    }

    public String courseStatus(short courseNum) {
        if (coursesMap.containsKey(courseNum)) {
            String output;
            Course currCourse = coursesMap.get(courseNum);
            output = "(" + courseNum + ") " + currCourse.getCourseName() + '*';
            output += currCourse.numOfseatsAvailable() + "/" + currCourse.getNumOfMaxStudents() + '*';
            output += (currCourse.getOrderedStudentList()).toString().replaceAll(" ", "") + '*';
            return output;
        }
        return null;
    }

    public String studentStatus(String userName) {
        if (usersMap.containsKey(userName)) {
            String output;
            output = userName + '*';
            output += (getCoursesOfStudentByOrder((Student) usersMap.get(userName))).toString().replaceAll(" ", "") + '*';
            return output;
        }
        return null;
    }

    public String getCoursesOfStudentByOrder(Student student) {
        return student.getCoursesByOrder().toString().replaceAll(" ", "");

    }

    public synchronized Object isRegistered(short courseNum, String userName) {
        if (coursesMap.containsKey(courseNum) && usersMap.containsKey(userName)) {
            Student currStudent = (Student) usersMap.get(userName);
            if (currStudent.getCurrentCoursesList().contains(courseNum))
                return true;
            return false;
        }
        return null;

    }

    public boolean unRegistered(short courseNum, String userName) {
        synchronized (this) {
            if (coursesMap.containsKey(courseNum)) {
                Student currStudent = (Student) usersMap.get(userName);
                Course currCourse = coursesMap.get(courseNum);
                currStudent.unRegisterCourse(courseNum, currCourse);
                currCourse.deleteStudent(userName);
                return true;
            }
            return false;
        }
    }

}