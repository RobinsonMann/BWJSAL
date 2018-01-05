package BWJSAL.allocation.mutex;

import bwapi.Unit;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;

import static BWJSAL.ExceptionUtils.NonNullUtil.nonNull;

/**
 * This class represents a global mutex for all units. This can help solve the problem of the same unit
 * being used for multiple tasks, objectives, etc.
 *
 * Before any code starts to use a unit, it should check that the unit is unlocked, if it is unlocked then proceed
 * to lock it before any commands are issued. Once the code is complete with the unit, it should then unlock the unit.
 *
 * This class is safe even when used across multiple threads.
 *
 * NOTE: Unit locks can be unlocked by anyone. Be careful when unlocking units.
 */
@Singleton
public class UnitsMutex {
    private final Set<Unit> lockedUnits;

    @Inject
    public UnitsMutex() {
        this.lockedUnits = new HashSet<>();
    }

    /**
     * Is the given unit locked?
     * @param unit Unit to check locked status.
     * @return Return true if locked, false otherwise.
     */
    public synchronized boolean isUnitLocked(final Unit unit) {
        nonNull(unit, "unit");
        return this.lockedUnits.contains(unit);
    }

    /**
     * Is the given unit unlocked? Same as isUnitUnlocked.
     * @param unit Unit to check locked status.
     * @return Return true if unlocked, false otherwise.
     */
    public synchronized boolean isUnitFree(final Unit unit) {
        nonNull(unit, "unit");
        return !isUnitLocked(unit);
    }

    /**
     * Is the given unit unlocked? Same as isUnitFree.
     * @param unit Unit to check locked status.
     * @return Return true if unlocked, false otherwise.
     */
    public synchronized boolean isUnitUnlocked(final Unit unit) {
        nonNull(unit, "unit");
        return isUnitFree(unit);
    }

    /**
     * Attempts to lock the given unit.
     * @param unit Unit to lock.
     * @return Returns true if the lock was acquired, returns false if the unit was already locked.
     */
    public synchronized boolean lockUnit(final Unit unit) {
        nonNull(unit, "unit", "Cannot lock null unit.");

        // Check if the unit was already locked.
        if (this.lockedUnits.contains(unit)) {
            return false;
        }

        // Return true
        this.lockedUnits.add(unit);
        return true;
    }

    /**
     * Unlocks the given unit. If the unit is unlocked, it will remain unlocked on completion.
     * @param unit Unit to unlock.
     */
    public synchronized void unlockUnit(final Unit unit) {
        nonNull(unit, "unit", "Cannot unlock null unit.");

        this.lockedUnits.remove(unit);
    }
}
