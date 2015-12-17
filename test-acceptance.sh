#!/bin/sh

activator -jvm-debug 9997 "testOnly org.coolestprojects.acceptance.user.*"
