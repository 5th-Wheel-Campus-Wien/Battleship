#!/bin/bash

set -euo pipefail

# Colors for better visibility
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

info() {
    echo -e "${GREEN}Compiles project${NC}"
    usage
}

usage() {
    echo -e "Usage: build.sh [options]

Options:
    -v        Enable verbose output
    -h        Show this help message"
}

log() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

error() {
    echo -e "${RED}[ERROR]${NC} $1"
    exit 1
}

# Initialize variables
VERBOSE=""
MAVEN_SWITCH=""

while getopts "hv" opt; do
    case $opt in
        v)
            VERBOSE=true
            ;;
        h)
            info
            exit 0
            ;;
        \?)
            error "Invalid option. Use -h for help"
            ;;
    esac
done

log "Starting compilation process"

if ! command -v java &> /dev/null; then
    error "Java is not installed. Please install Java before building"
fi

# Set Maven verbosity
if [ -z "${VERBOSE}" ]; then
    MAVEN_SWITCH="$MAVEN_SWITCH -q"
fi

# Ensure Maven wrapper is executable
chmod +x ./mvnw

log "Compiling project"
./mvnw compile $MAVEN_SWITCH || error "Compilation failed"

log "Build completed successfully! 🚀"
