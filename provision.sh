#!/bin/sh

activator clean dist
gradle clean liquibaseDistZip
vagrant provision