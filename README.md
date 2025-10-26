# The VSCode Launch config should work with no fuss so use that.

## If you need to manually change things:
This Command compiles everything: `javac -g -d build ./backend/*.java ./gui/*.java`

This Command Runs the driver and all the unit tests: `java -cp build gui`

Java Version Info:
    openjdk 17.0.16 2025-07-15
    OpenJDK Runtime Environment (build 17.0.16+8-Debian-1deb12u1)
    OpenJDK 64-Bit Server VM (build 17.0.16+8-Debian-1deb12u1, mixed mode, sharing)