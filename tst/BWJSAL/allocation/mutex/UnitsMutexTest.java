package BWJSAL.allocation.mutex;

import BWJSAL.utils.UnitBuilder;
import bwapi.Unit;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static BWJSAL.utils.ThreadUtils.startMultipleThreadsAndWaitUntilComplete;
import static org.assertj.core.api.Assertions.assertThat;

public class UnitsMutexTest {

    private UnitsMutex target;

    @Before
    public void setUp() {
        this.target = new UnitsMutex();
    }

    @Test
    public void isUnitFree_unitIsFree_returnsTrue() {
        final Unit unit = UnitBuilder.mockUnit();
        assertThat(this.target.isUnitFree(unit)).isTrue();
    }

    @Test
    public void isUnitFree_unitIsLocked_returnsFalse() {
        final Unit unit = UnitBuilder.mockUnit();
        this.target.lockUnit(unit);
        assertThat(this.target.isUnitFree(unit)).isFalse();
    }

    @Test
    public void isUnitUnlocked_unitIsFree_returnsTrue() {
        final Unit unit = UnitBuilder.mockUnit();
        assertThat(this.target.isUnitUnlocked(unit)).isTrue();
    }

    @Test
    public void isUnitUnlocked_unitIsLocked_returnsFalse() {
        final Unit unit = UnitBuilder.mockUnit();
        this.target.lockUnit(unit);
        assertThat(this.target.isUnitUnlocked(unit)).isFalse();
    }

    @Test
    public void isUnitLocked_unitIsFree_returnsFalse() {
        final Unit unit = UnitBuilder.mockUnit();
        assertThat(this.target.isUnitLocked(unit)).isFalse();
    }

    @Test
    public void isUnitLocked_unitIsLocked_returnsTrue() {
        final Unit unit = UnitBuilder.mockUnit();
        this.target.lockUnit(unit);
        assertThat(this.target.isUnitLocked(unit)).isTrue();
    }

    @Test
    public void unlockUnit_unitNotLocked_unitStillUnlocked() {
        final Unit unit = UnitBuilder.mockUnit();
        assertThat(this.target.isUnitLocked(unit)).as("Sanity test that unit unlocked").isFalse();

        this.target.unlockUnit(unit);

        assertThat(this.target.isUnitLocked(unit)).as("unit still unlocked").isFalse();
    }

    @Test
    public void unlockUnit_unitLocked_unitUnlocked() {
        final Unit unit = UnitBuilder.mockUnit();
        assertThat(this.target.isUnitLocked(unit)).as("Sanity test that unit unlocked").isFalse();

        this.target.lockUnit(unit);
        this.target.unlockUnit(unit);

        assertThat(this.target.isUnitLocked(unit)).as("unit unlocked").isFalse();
    }

    @Test
    public void lockUnit_unitPreviouslyUnlocked_returnsTrue() {
        final Unit unit = UnitBuilder.mockUnit();
        assertThat(this.target.isUnitLocked(unit)).as("Sanity test that unit unlocked").isFalse();
        assertThat(this.target.lockUnit(unit)).as("lock acquired").isTrue();
        assertThat(this.target.isUnitLocked(unit)).isTrue();
    }

    @Test
    public void lockUnit_unitPreviouslyLocked_returnsFalse() {
        final Unit unit = UnitBuilder.mockUnit();
        assertThat(this.target.isUnitLocked(unit)).as("Sanity test that unit unlocked").isFalse();
        assertThat(this.target.lockUnit(unit)).as("lock acquired").isTrue();
        assertThat(this.target.lockUnit(unit)).as("lock was already acquired").isFalse();
        assertThat(this.target.isUnitLocked(unit)).isTrue();
    }

    /**
     * Start lots of threads, and all threads try to acquire lock for an object. Verify that
     * only 1 such thread was able to acquire the lock.
     */
    @Test
    public void acquireLock_multithreaded_onlyAcquiredOnce() throws Exception {
        final int numberOfThreads = 10;

        final AtomicInteger numberOfTimesLockAcquired = new AtomicInteger(0);
        final AtomicInteger numberOfTimesLockFailedAcquired = new AtomicInteger(0);

        final Unit unitToLock = UnitBuilder.mockUnit();

        startMultipleThreadsAndWaitUntilComplete(IntStream.range(0, numberOfThreads)
                                                          .mapToObj(x -> new Runnable() {
                                                              @Override
                                                              public void run() {
                                                                  // Try to acquire unit. Only 1 thread should succeeded
                                                                  if (UnitsMutexTest.this.target.lockUnit(unitToLock)) {
                                                                      System.out.println(x);
                                                                      numberOfTimesLockAcquired.incrementAndGet();
                                                                  }
                                                                  else {
                                                                      numberOfTimesLockFailedAcquired.incrementAndGet();
                                                                  }

                                                              }
                                                          })
                                                          .collect(Collectors.toList()));

        assertThat(numberOfTimesLockAcquired.get())
                .as("Unit lock only acquired once")
                .isEqualTo(1);

        assertThat(numberOfTimesLockFailedAcquired.get())
                .as("Unit lock acquisition was blocked " + (numberOfThreads - 1) + " times")
                .isEqualTo(numberOfThreads - 1);
    }
}