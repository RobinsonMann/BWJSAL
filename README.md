# Broodwar Java Standard Addon Library

## About

This project is (mostly) a Java clone of the C++ [BWSAL](https://github.com/Fobbah/bwsal) project.

## API deviations from BWSAL

	- Singleton concept is removed. Every class can be created arbitrary number of times.
	- Static usage has been replaced with constructor provided instances.

## Building and running tests

BWJSAL's build system uses Apache Ant. The following targets have been defined:

	clean - remove output directories
	setup - setup output directories
	build.relase - build source files without debugging information
	build.debug - build source files with debugging information
	build.test - build test files
	test - run all unit tests

Apache Ant is very straight forward to use. To execute a target, enter `ant <target_name>` in the command line. For example, to run the unit tests, you run `ant test`.

Unit tests use the junit framework.

## Dependencies

	- [bwmirror](https://github.com/vjurenka/BWMirror)

## Testing Dependencies

	- assertj-core-3.5.2 - Assertion framework
	- junit-4.12 - Testing frameworks
	- mockito-all-1.10.19 - Mock framework
