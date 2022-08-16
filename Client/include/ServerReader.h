//
// Created by spl211 on 04/01/2021.
//

#ifndef CPP_SERVERREADER_H
#define CPP_SERVERREADER_H

#include "../include/connectionHandler.h"
#include "../include/KeyboardReader.h"

using namespace std;

class ServerReader {
private:
    ConnectionHandler &connectionHandler;

    short bytesToShort(char *bytesArr);

    void decodeACK();

    void decodeERR();

    void kdamCheck();

    void courseStatus();

    void studentStatus();

    void isRegister();

    void myCourses();

public:
    ServerReader(ConnectionHandler &connectionHandler1);

    void run();



};


#endif //CPP_SERVERREADER_H
