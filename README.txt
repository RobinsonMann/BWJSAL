Broodwar Java Standard Addon Library

This project is mostly a Java clone of the C++ BWSAL project.

Deviations from existing BWSAL code:
    - Managers are no longer singletons
    - All classes that used the static Game instance Broodwar now has the game instance injected

Dependencies:
    bwmirror-2.5

Testing Dependencies:
    assertj-core-3.5.2 - Assertion framework
    junit-4.12 - Testing frameworks
    mockito-all-1.10.19 - Mock framework
