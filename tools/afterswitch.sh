#!/bin/bash

SCRIPT_ROOT="$(cd "$(dirname "$0")" && pwd)"

info() {
    echo "After Switch for 5th Wheel Battleship.
Compiles and prepares machine for run."

    usage
}

usage() {
    echo "usage: afterswitch [options]

-s        Run Setup
-b        Run Build
-v        Make it verbose
-h        Show this help"
}

ALL=YES

while getopts "sbhv" opt; do
    case $opt in
    	s)
    		ALL=
    		SETUP=YES
    		;;
    	b)
    		ALL=
    		BUILD=YES
    		;;
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

if [ $ALL ]
then
	SETUP=YES
	BUILD=YES
fi

if [ $SETUP ]
then
	$SCRIPT_ROOT/setup.sh $VERBOSE
	if [ $? -ne 0 ]; then
	    echo "Setup failed."
	    exit 1
	fi
fi

if [ $BUILD ]
then
	$SCRIPT_ROOT/build.sh $VERBOSE
	if [ $? -ne 0 ]; then
	    echo "Build failed."
	    exit 1
	fi
fi

echo "Afterswitch completed."
