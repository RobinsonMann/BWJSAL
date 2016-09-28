# Broodwar Java Standard Addon Library

## About

This project is (mostly) a Java clone of the C++ [BWSAL](https://github.com/Fobbah/bwsal) project.

## API deviations from BWSAL

	- Managers are now individual instances, instead of being signletons.
	- All classes that used the static Game instance Broodwar now used an instance of Game that is passed in via the consturctor.

## Dependencies

	- [bwmirror](https://github.com/vjurenka/BWMirror)

## Testing Dependencies

	- assertj-core-3.5.2 - Assertion framework
	- junit-4.12 - Testing frameworks
	- mockito-all-1.10.19 - Mock framework
