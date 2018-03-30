package BWJSAL;

import BWJSAL.DrawUtils.DrawUtils;
import bwapi.Color;
import bwapi.CoordinateType.Enum;
import bwapi.Game;
import bwapi.Position;
import bwapi.Mirror;
import bwapi.Unit;
import bwapi.UnitType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;

@RunWith(MockitoJUnitRunner.class)
public class DrawUtilsTest {

    private static final int MINERAL_X = 123;
    private static final int MINERAL_Y = 456;

    private static final int POSITION_X = 100;
    private static final int POSITION_Y = 500;

    @Mock
    public Unit mineral;

    @Mock
    public Unit baseLocation;

    @Mock
    public Position position;

    @Mock
    public Color color;

    @Mock
    public Game game;

    @InjectMocks
    public DrawUtils target;

    @Before
    public void setup() {
        when(mineral.getX()).thenReturn(MINERAL_X);
        when(mineral.getY()).thenReturn(MINERAL_Y);

        when(position.getX()).thenReturn(POSITION_X);
        when(position.getY()).thenReturn(POSITION_Y);
    }

    private static class JarResources {
        public boolean debugOn = false;
        private Hashtable htSizes = new Hashtable();
        private Hashtable htJarContents = new Hashtable();
        private String jarFileName;

        public JarResources(String var1) {
            this.jarFileName = var1;
            this.init();
        }

        public byte[] getResource(String var1) {
            return (byte[])((byte[])this.htJarContents.get(var1));
        }

        private void init() {
            try {
                ZipFile var1 = new ZipFile(this.jarFileName);

                ZipEntry var3;
                for(Enumeration var2 = var1.entries(); var2.hasMoreElements(); this.htSizes.put(var3.getName(), new Integer((int)var3.getSize()))) {
                var3 = (ZipEntry)var2.nextElement();
                    if(this.debugOn) {
                        System.out.println(this.dumpZipEntry(var3));
                    }
                }

                var1.close();
                FileInputStream var14 = new FileInputStream(this.jarFileName);
                BufferedInputStream var4 = new BufferedInputStream(var14);
                ZipInputStream var5 = new ZipInputStream(var4);
                ZipEntry var6 = null;

                while(true) {
                    do {
                        if((var6 = var5.getNextEntry()) == null) {
                            return;
                        }
                    } while(var6.isDirectory());

                    if(this.debugOn) {
                        System.out.println("ze.getName()=" + var6.getName() + "," + "getSize()=" + var6.getSize());
                    }

                    int var7 = (int)var6.getSize();
                    if(var7 == -1) {
                        var7 = ((Integer)this.htSizes.get(var6.getName())).intValue();
                    }

                    byte[] var8 = new byte[var7];
                    int var9 = 0;

                    int var15;
                    for(boolean var10 = false; var7 - var9 > 0; var9 += var15) {
                        var15 = var5.read(var8, var9, var7 - var9);
                        if(var15 == -1) {
                            break;
                        }
                    }

                    this.htJarContents.put(var6.getName(), var8);
                    if(this.debugOn) {
                        System.out.println(var6.getName() + "  rb=" + var9 + ",size=" + var7 + ",csize=" + var6.getCompressedSize());
                    }
                }
            } catch (NullPointerException var11) {
                System.out.println("done.");
            } catch (FileNotFoundException var12) {
                var12.printStackTrace();
            } catch (IOException var13) {
                var13.printStackTrace();
            }

        }

        private String dumpZipEntry(ZipEntry var1) {
            StringBuffer var2 = new StringBuffer();
            if(var1.isDirectory()) {
                var2.append("d ");
            } else {
                var2.append("f ");
            }

            if(var1.getMethod() == 0) {
                var2.append("stored   ");
            } else {
                var2.append("defalted ");
            }

            var2.append(var1.getName());
            var2.append("\t");
            var2.append("" + var1.getSize());
            if(var1.getMethod() == 8) {
                var2.append("/" + var1.getCompressedSize());
            }

            return var2.toString();
        }
    }

    @Test
    public void drawCircleAroundMineralTest() {
        // Test
        target.highlightMineralField(mineral, color);

        // Verify dependencies were called correctly
        verify(game).drawCircle(Enum.Map, MINERAL_X, MINERAL_Y, 30, color, false);
    }

    @Test
    public void drawProgressBarTest() {
        target.drawProgressBar(position, 0.5, color);

        // Verify top outline
        verify(game).drawLineMap(POSITION_X - 9, POSITION_Y + 2, POSITION_X + 9, POSITION_Y + 2, Color.Blue);

        // Verify bottom outline
        verify(game).drawLineMap(POSITION_X - 9, POSITION_Y - 2, POSITION_X + 9, POSITION_Y - 2, Color.Blue);

        // Verify left outline
        verify(game).drawLineMap(POSITION_X - 10, POSITION_Y + 3, POSITION_X - 10, POSITION_Y - 2, Color.Blue);

        // Verify right outline
        verify(game).drawLineMap(POSITION_X + 9, POSITION_Y + 3, POSITION_X + 9, POSITION_Y - 2, Color.Blue);

        // Verify bar background
        verify(game).drawBoxMap(eq(POSITION_X - 9), eq(POSITION_Y + 3), eq(POSITION_X + 9), eq(POSITION_Y - 2), any(), eq(true));

        // Verify progress bar
        verify(game).drawBoxMap(POSITION_X - 9, POSITION_Y + 3, POSITION_X, POSITION_Y - 2, color, true);
    }
}
