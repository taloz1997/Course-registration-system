CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude
LDFLAGS:=-lboost_system -pthread -lboost_filesystem -lboost_thread

all:bin/BGRSclient

bin/BGRSclient: bin/Main.o bin/connectionHandler.o bin/KeyboardReader.o bin/ServerReader.o
	g++ -o bin/BGRSclient bin/Main.o bin/connectionHandler.o bin/KeyboardReader.o bin/ServerReader.o $(LDFLAGS)

bin/Main.o : src/Main.cpp
	g++ $(CFLAGS)  -o bin/Main.o src/Main.cpp

bin/connectionHandler.o : src/connectionHandler.cpp
	g++ $(CFLAGS)  -o bin/connectionHandler.o src/connectionHandler.cpp

bin/KeyboardReader.o : src/KeyboardReader.cpp
	g++ $(CFLAGS)  -o bin/KeyboardReader.o src/KeyboardReader.cpp

bin/ServerReader.o :  src/ServerReader.cpp
	g++ $(CFLAGS)  -o bin/ServerReader.o src/ServerReader.cpp


.PHONY: clean
clean:
	rm -f bin/*
