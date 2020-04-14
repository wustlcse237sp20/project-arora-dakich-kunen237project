#!/bin/bash

cd src
cd com
cd company
javac -cp ../../ Main.java
java -cp ../../ com/company/Main
cd ..
cd ..
cd ..
cd GUI
javac GUI.java
java GUI
