#!/bin/bash

info() {
    echo "Prepares machine for compile."

    usage
}

usage() {
    echo "usage: setup.sh [options]

-v        Make it verbose
-h        Show this help"
}

ALL=YES

while getopts "hv" opt; do
    case $opt in
        v)
            VERBOSE="-v"
            ;;
        h)
            info
            exit 0
            ;;
        \?)
            echo "Use -h for help"
            exit 1
            ;;
    esac
done


echo "Setting up dev machine"

if [ ! command -v $JAVA_HOME/bin/java &> /dev/null ]
then
    echo "Java is not installed. Please install Java 21 or higher."
   	exit 1
fi

JAVA_VERSION_OUTPUT=$($JAVA_HOME/bin/java -version 2>&1 | head -n 1 | awk -F '"' '{print $2}')
JAVA_MAJOR_VERSION=$(echo "$JAVA_VERSION_OUTPUT" | awk -F '.' '{print $1}')

if [[ "$JAVA_MAJOR_VERSION" =~ ^[0-9]+$ ]]; then
    if [ "$JAVA_MAJOR_VERSION" -lt 21 ]; then
        echo "Java version $JAVA_MAJOR_VERSION is installed. Java 21 or higher is required."
        echo "Hint: Your environment may default to an older version. Make sure to set JAVA_HOME to a current java version."
        echo "  Example: export JAVA_HOME=/usr/lib/jvm/java-21-openjdk/"
        exit 1
    fi
else
    echo "Unable to determine Java version. Please ensure Java 21 or higher is installed."
    exit 1
fi

if [ ! $VERBOSE ]
then
	MAVEN_SWITCH="$MAVEN_SWITCH -q"
fi

echo "  Clean Installing dependencies"
./mvnw clean install $MAVEN_SWITCH

if [ $? -ne 0 ]; then
    echo "Dependency installation failed."
    exit 1
fi

echo "  Cleaning artifacts"
./mvnw clean $MAVEN_SWITCH

if [ $? -ne 0 ]; then
    echo "Artifact clean failed."
    exit 1
fi
