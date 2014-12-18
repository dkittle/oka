#!/bin/sh

sbt clean clean-files
find . -name target -type d -exec rm -rf {} \;

