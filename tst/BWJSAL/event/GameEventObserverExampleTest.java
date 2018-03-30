package BWJSAL.event;

import BWJSAL.event.dispatch.HashMapEventDispatcher;
import BWJSAL.event.dispatch.GameEventDispatcher;
import BWJSAL.event.dispatch.InsertionOrderGameEventDispatcher;
import BWJSAL.event.listener.OnFrameListener;
import BWJSAL.event.listener.OnUnitDiscoverListener;
import bwapi.Unit;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GameEventObserverExampleTest {
    GameEventDispatcher hardcodeddispatcher;
    GameEventDispatcher hashMapBasedEventDispatcher;

    int calltimes = 0;

    @Before
    public void setUp() {
        this.hardcodeddispatcher = new InsertionOrderGameEventDispatcher();
        this.hashMapBasedEventDispatcher = new HashMapEventDispatcher();
    }

    @Test
    public void b_hardcoded() {
        System.out.println("Using hard coded style");
        hardcoded();
    }

    @Test
    public void a_hashmap() {
        System.out.println("Using hashmap style");
        hashmap();
    }

    public void hashmap() {
        for (int i = 0; i < 10; i++) {
            this.hashMapBasedEventDispatcher.addListener(new DebugInfo());
            this.hashMapBasedEventDispatcher.addListener(new DebugInfo2());
        }

        for (int i = 0; i < 1_000; i++) {
            this.hashMapBasedEventDispatcher.onFrame();
            this.hashMapBasedEventDispatcher.onUnitDiscover(null);
        }

        System.out.println("Call times: " + calltimes);
    }

    public void hardcoded() {
        for (int i = 0; i < 10; i++) {
            this.hardcodeddispatcher.addListener(new DebugInfo());
            this.hardcodeddispatcher.addListener(new DebugInfo2());
        }

        for (int i = 0; i < 1_000; i++) {
            this.hardcodeddispatcher.onFrame();
            this.hardcodeddispatcher.onUnitDiscover(null);
        }

        System.out.println("Call times: " + calltimes);
    }

    class DebugInfo implements OnFrameListener, OnUnitDiscoverListener {
        @Override
        public void onFrame() {
            int workSize = 10;
            List<Double> randomList = new LinkedList<>();
            Random random = new Random();
            for (int i = 0; i < workSize; i++) {
                randomList.add(random.nextDouble());
            }
            calltimes++;
        }

        @Override
        public void onUnitDiscover(Unit unit) {
            int workSize = 10;
            List<Double> randomList = new LinkedList<>();
            Random random = new Random();
            for (int i = 0; i < workSize; i++) {
                randomList.add(random.nextDouble());
            }
            calltimes++;
        }
    }

    class DebugInfo2 extends DebugInfo {
        @Override
        public void onUnitDiscover(Unit unit) {

        }
    }
}