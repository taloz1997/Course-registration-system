//
// Created by spl211 on 04/01/2021.
//

#include <thread>
#include "../include/ServerReader.h"


ServerReader::ServerReader(ConnectionHandler &connectionHandler1) : connectionHandler(connectionHandler1) {}

void ServerReader::run() {
    while (connectionHandler.isCHLoggedIn()) {
        char opCodeArr[2];
        if (!connectionHandler.getBytes(opCodeArr, 2)) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }
        short opCode = bytesToShort(opCodeArr);
        if (opCode == 12) {  //means that ACK msg received
            if (!connectionHandler.getIsMessageComplete()) {
                decodeACK();
            }
        } else if (opCode == 13) {  //means that ERR msg received
            if (!connectionHandler.getIsMessageComplete()) {
                decodeERR();
            }
        }
    }
}

short ServerReader::bytesToShort(char *bytesArr) {
    short result = (short) ((bytesArr[0] & 0xff) << 8);
    result += (short) (bytesArr[1] & 0xff);
    return result;
}

void ServerReader::decodeACK() {
    char opCodeACKArr[2];
    if (!connectionHandler.getBytes(opCodeACKArr, 2)) {
        std::cout << "Disconnected. Exiting...\n" << std::endl;
        return;
    }
    short ACKopcode = bytesToShort(opCodeACKArr);
    cout << "ACK " + to_string(ACKopcode) << endl;
    connectionHandler.setIsMessageComplete(true);

    if (ACKopcode == 6)
        kdamCheck();
    else if (ACKopcode == 7)
        courseStatus();
    else if (ACKopcode == 8)
        studentStatus();
    else if (ACKopcode == 9)
        isRegister();
    else if (ACKopcode == 11)
        myCourses();
    else if (ACKopcode == 4) {
        connectionHandler.close();
        std:: exit(0);
    }

}

void ServerReader::kdamCheck() {
    string kdamCoursesList;
    char currByte;
    while (currByte != ']') {
        connectionHandler.getBytes(&currByte, 1);
        kdamCoursesList += currByte;
    }
    cout << kdamCoursesList << endl;
}


void ServerReader::courseStatus() {
    string course;
    string seatsAvailable;
    string studentsRegistered;
    char currByte=0;
    while (currByte != '*') {
        connectionHandler.getBytes(&currByte, 1);
        course += currByte;
    }
    cout << "Course: " + course.substr(0, course.length() - 1) << endl;
    currByte=0;
    while (currByte != '*') {
        connectionHandler.getBytes(&currByte, 1);
        seatsAvailable += currByte;
    }
    cout << "Seats Available: " + seatsAvailable.substr(0, seatsAvailable.length() -1) << endl;
    currByte=0;
    while (currByte != '*') {
        connectionHandler.getBytes(&currByte, 1);
        studentsRegistered += currByte;
    }
    cout << "Students Registered: " + studentsRegistered.substr(0, studentsRegistered.length() - 1) << endl;
}


void ServerReader::studentStatus() {
    string student;
    string courses;
    char currByte=0;
    while (currByte != '*') {
        connectionHandler.getBytes(&currByte, 1);
        student += currByte;
    }
    cout << "Student: " + student.substr(0, student.length() - 1) << endl;
    currByte=0;
    while (currByte != '*') {
        connectionHandler.getBytes(&currByte, 1);
        courses += currByte;
    }
    cout << "Courses: " + courses.substr(0, courses.length() -1) << endl;

}

void ServerReader::isRegister() {
    string isRegistered;
    char currByte=0;
    connectionHandler.getBytes(&currByte, 1);
    if(currByte == '1')
        cout << "REGISTERED" << endl;
    else
        cout << "NOT REGISTERED" << endl;
}

void ServerReader::myCourses() {
    string myCourses;
    char currByte;
    while (currByte != ']') {
        connectionHandler.getBytes(&currByte, 1);
        myCourses += currByte;
    }
    cout << myCourses << endl;
}

void ServerReader::decodeERR() {
    char opCodeERRArr[2];
    if (!connectionHandler.getBytes(opCodeERRArr, 2)) {
        std::cout << "Disconnected. Exiting...\n" << std::endl;
        return;
    }
    short ERRopcode = bytesToShort(opCodeERRArr);
    cout << "ERROR " + to_string(ERRopcode) << endl;
    connectionHandler.setIsMessageComplete(true);
}