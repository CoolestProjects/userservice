#!/bin/sh

activator -jvm-debug 9998 "testOnly org.coolestprojects.acceptance.*"
