#!/bin/bash

set -euo pipefail

# Colors for better visibility
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

info() {
    echo -e "${GREEN}Prepares development environment for compilation${NC}"
    usage
}

usage() {
    echo -e "Usage: setup.sh [options]

Options:
    -v        Enable verbose output
    -h        Show this help message
    -s        Skip Java version check"
}

log() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

error() {
    echo -e "${RED}[ERROR]${NC} $1"
    exit 1
}

check_java() {
    if [ -z "${JAVA_HOME:-}" ]; then
        error "JAVA_HOME is not set. Please set it to the path of your Java 21 installation."
    fi

    if [ ! -x "$JAVA_HOME/bin/java" ]; then
        error "JAVA_HOME is set to '$JAVA_HOME', but no valid 'java' executable was found. Please check your Java installation."
    fi

    JAVA_VERSION_OUTPUT=$("$JAVA_HOME/bin/java" -version 2>&1 | head -n 1 | awk -F '"' '{print $2}')
    JAVA_MAJOR_VERSION=$(echo "$JAVA_VERSION_OUTPUT" | awk -F '.' '{print $1}')

    if [[ "$JAVA_MAJOR_VERSION" =~ ^[0-9]+$ ]]; then
        if [ "$JAVA_MAJOR_VERSION" -lt 21 ]; then
            error "Java version $JAVA_MAJOR_VERSION detected. Java 21 or higher is required.
${YELLOW}Hint: Ensure JAVA_HOME points to a valid Java 21 or higher installation.${NC}"
        fi
    else
        error "Unable to determine Java version. Please ensure Java 21 or higher is installed."
    fi

    log "Java $JAVA_VERSION_OUTPUT detected"
}

# Initialize variables
VERBOSE=""
SKIP_JAVA_CHECK=""
MAVEN_SWITCH=""

while getopts "hvs" opt; do
    case $opt in
        v)
            VERBOSE=true
            ;;
        h)
            info
            exit 0
            ;;
        s)
            SKIP_JAVA_CHECK=true
            ;;
        \?)
            error "Invalid option. Use -h for help"
            ;;
    esac
done

log "Setting up development environment"

# Check Java version unless skipped
if [ -z "${SKIP_JAVA_CHECK}" ]; then
    check_java
fi

# Set Maven verbosity
if [ -z "${VERBOSE}" ]; then
    MAVEN_SWITCH="$MAVEN_SWITCH -q"
fi

# Ensure Maven wrapper is executable
chmod +x ./mvnw

log "Installing dependencies"
./mvnw clean install $MAVEN_SWITCH || error "Dependency installation failed"

log "Cleaning artifacts"
./mvnw clean $MAVEN_SWITCH || error "Artifact clean failed"

log "Setup completed successfully! 🚀"
