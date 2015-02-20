#!/bin/sh

activator -jvm-debug 9999 "testOnly org.coolestprojects.acceptance.*"
