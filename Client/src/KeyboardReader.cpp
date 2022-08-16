
//
// Created by spl211 on 03/01/2021.
//


#include "../include/KeyboardReader.h"

KeyboardReader::KeyboardReader(ConnectionHandler &connectionHandler1) : connectionHandler(connectionHandler1),
                                                                        command(), opcode(-1), userName(), password(),
                                                                        courseNum(-1) {
}

void KeyboardReader::run() {
    while (connectionHandler.isCHLoggedIn()) {
        const short bufsize = 1024;
        char buf[bufsize];
        std::cin.getline(buf, bufsize);
        std::string line(buf);
        encode(line);
    }
}

void KeyboardReader::encode(string line) {

    vector<string> splitLine = split(line, " ");
    command = splitLine[0];
    if (splitLine.size() == 3) {//means that the command has userName and password
        userName = splitLine[1];
        password = splitLine[2];
        if (command == "ADMINREG") {
            opcode = 1;
        } else {
            if (command == "STUDENTREG")
                opcode = 2;
            else //command == "LOGIN"
                opcode = 3;
        }
        vector<char> bytesArrUserName;
        vector<char> bytesArrPassword;
        shortToBytes(opcode);
        for (size_t i = 0; i < userName.size(); ++i) {
            bytesArrUserName.push_back(userName.at(i));
        }
        bytesArrUserName.push_back('\0');
        for (size_t i = 0; i < password.size(); ++i) {
            bytesArrPassword.push_back(password.at(i));
        }
        bytesArrPassword.push_back('\0');
        merge(bytesArrUserName);
        merge(bytesArrPassword);
    } else if (splitLine.size() == 1) {//this command has no parameters
        if (command == "LOGOUT") {
            opcode = 4;
        } else //MYCOURSES command
            opcode = 11;
        shortToBytes(opcode);
    } else { //splitLine.size() == 2;
        if (command == "STUDENTSTAT") {
            opcode = 8;
            userName = splitLine[1];
            vector<char> bytesArrUserName;
            shortToBytes(opcode);
            for (size_t i = 0; i < userName.size(); ++i) {
                bytesArrUserName.push_back(userName.at(i));
            }
            bytesArrUserName.push_back('\0');
            merge(bytesArrUserName);
        } else {
            if (command == "COURSEREG") opcode = 5;
            else if (command == "KDAMCHECK") opcode = 6;
            else if (command == "COURSESTAT") opcode = 7;
            else if (command == "ISREGISTERED") opcode = 9;
            else if (command == "UNREGISTER") opcode = 10;
            shortToBytes(opcode);
            courseNum = (short) stoi(splitLine[1]);
            shortToBytes(courseNum);
        }
    }
    connectionHandler.sendBytes(&bytesChar[0], bytesChar.size());
    connectionHandler.setIsMessageComplete(false);
    bytesChar.clear();
}

vector<string> KeyboardReader::split(string line, string delimiter) {
    vector<string> output;
    size_t pos = 0;
    string token;
    while ((pos = line.find(delimiter)) != string::npos) {
        token = line.substr(0, pos);
        output.push_back(token);
        line.erase(0, pos + delimiter.length());
    }
    output.push_back(line); //add the last word in the line
    return output;
}


void KeyboardReader::shortToBytes(short num) {
    bytesChar.push_back((num >> 8) & 0xFF);
    bytesChar.push_back(num & 0xFF);
}

void KeyboardReader::merge(vector<char> a1) {
    for (int i = 0; i < a1.size(); i++)
        bytesChar.push_back(a1[i]);

}