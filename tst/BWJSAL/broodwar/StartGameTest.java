package BWJSAL.broodwar;

import BWJSAL.Information.EnemyBaseTracker;
import bwapi.DefaultBWListener;
import bwapi.Mirror;
import bwapi.Race;
import bwapi.Unit;
import bwta.BWTA;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Semaphore;

import static org.assertj.core.api.Assertions.assertThat;

public class StartGameTest {
//    private Mirror mirror = new Mirror();
//
//    private Semaphore startGameSemaphore = new Semaphore(0, true);
//    private Semaphore onFrameSemaphore = new Semaphore(0, true);
//    private Semaphore freezeFrame = new Semaphore(1, true);
//    private Semaphore gameOver = new Semaphore(0, true);
//
//    private EnemyBaseTracker enemyBaseTracker;
//
//    @Before
//    public void run() throws InterruptedException {
//        mirror.getModule()
//              .setEventListener(new DefaultBWListener() {
//                  @Override
//                  public void onStart() {
//                      BWTA.analyze();
//
//                      System.out.println("onStart has been called. Releasing startGameSemaphore so that  ");
//                      enemyBaseTracker = new EnemyBaseTracker(mirror.getGame());
//                      mirror.getGame().enableFlag(1);
//                      mirror.getGame().sendText("power overwhelming");
//                      mirror.getGame().sendText("black sheep wall");
//                      mirror.getGame().sendText("war aint what it used to be");
//                      mirror.getGame().sendText("operation cwal");
//                      mirror.getGame().sendText("show me the money");
//                      mirror.getGame().setLocalSpeed(0);
//                      startGameSemaphore.release(1);
//
//                      enemyBaseTracker.onStart();
//                  }
//
//                  @Override
//                  public void onFrame() {
//                      for (Unit enemyUnit : mirror.getGame().enemy().getUnits()) {
//                          if (enemyUnit.getType().isBuilding()) {
//
//                              //mirror.getGame().drawTextMap(enemyUnit.getPosition(), "" + enemyBaseTracker.possibleEnemyStartingLocation(enemyUnit).isPresent());
//
//                          }
//                      }
//                  }
//
//
//              });
//
//        new Thread() {
//            @Override
//            public void run() {
//                System.out.println("About to call mirror.startGame()");
//                mirror.startGame();
//            }
//        }.start();
//
//        startGameSemaphore.acquire(1);
//
//        System.out.println("Can run tests now!");
//    }
//
//    @Test
//    public void enemyIsProtoss() throws InterruptedException {
//        Thread.sleep(1000 * 60 * 60);
//    }
//
//    @After
//    public void endGame() throws InterruptedException {
//        freezeFrame.acquire(1);
//        System.out.println("ending game??");
//        mirror.getGame()
//              .leaveGame();
//    }
}
