#!/bin/bash

info() {
    echo "Compiles project."

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


echo "Compiling project"

if [ ! command -v java &> /dev/null ]
then
	echo "java is required to build"
	exit 1
fi

if [ ! $VERBOSE ]
then
	MAVEN_SWITCH="$MAVEN_SWITCH -q"
fi

echo "  Compiling"
./mvnw compile $MAVEN_SWITCH

if [ $? -ne 0 ]; then
    echo "Compile failed."
    exit 1
fi
