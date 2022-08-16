//
// Created by spl211 on 03/01/2021.
//

#ifndef CPP_KEYBOARDREADER_H
#define CPP_KEYBOARDREADER_H

#include <boost/lexical_cast.hpp>
#include "../include/connectionHandler.h"
#include <vector>

using namespace std;

class KeyboardReader {
private:
    ConnectionHandler &connectionHandler;
    string command;
    short opcode;
    string userName;
    string password;
    short courseNum;
    vector<char> bytesChar;

    vector<string> split(string line, string delimiter);

    void shortToBytes(short num);
    void merge(vector<char> a1);
    void encode(string line);



public:

    KeyboardReader(ConnectionHandler &connectionHandler);

    void run();


};


#endif //CPP_KEYBOARDREADER_H