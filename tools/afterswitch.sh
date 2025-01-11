#!/bin/bash

set -euo pipefail

# Colors for better visibility
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

info() {
    echo -e "${GREEN}After Switch for 5th Wheel Battleship${NC}"
    echo -e "Compiles and prepares the machine for running."
    usage
}

usage() {
    echo -e "Usage: afterswitch.sh [options]\n"
    echo -e "Options:"
    echo -e "    -s        Run Setup"
    echo -e "    -b        Run Build"
    echo -e "    -v        Enable verbose output"
    echo -e "    -h        Show this help message"
}

log() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

error() {
    echo -e "${RED}[ERROR]${NC} $1"
    exit 1
}

# Initialize variables
SETUP=false
BUILD=false
VERBOSE=""

while getopts "sbvh" opt; do
    case $opt in
        s)
            SETUP=true
            ;;
        b)
            BUILD=true
            ;;
        v)
            VERBOSE="-v"
            ;;
        h)
            info
            exit 0
            ;;
        \?)
            error "Invalid option. Use -h for help."
            ;;
    esac
done

# Default to running both Setup and Build if no specific options were provided
if ! $SETUP && ! $BUILD; then
    SETUP=true
    BUILD=true
fi

SCRIPT_ROOT="$(cd "$(dirname "$0")" && pwd)"

if $SETUP; then
    log "Running Setup..."
    "$SCRIPT_ROOT/setup.sh" $VERBOSE || error "Setup failed."
fi

if $BUILD; then
    log "Running Build..."
    "$SCRIPT_ROOT/build.sh" $VERBOSE || error "Build failed."
fi

log "Afterswitch completed successfully!"
