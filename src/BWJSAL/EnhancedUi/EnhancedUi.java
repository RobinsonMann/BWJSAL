package BWJSAL.EnhancedUi;

import BWJSAL.bwta.BwtaWrapper;
import BWJSAL.DrawUtils.DrawUtils;
import bwapi.Color;
import bwapi.Position;
import bwapi.Unit;
import bwta.BaseLocation;

import static BWJSAL.ExceptionUtils.NonNullUtil.nonNull;

public class EnhancedUi {

    private static final Color MINERAL_CIRCLE_COLOR = Color.Cyan;
    private static final Color BASE_BOX_COLOR = Color.Blue;
    private static final Color BASE_ISLAND_COLOR = Color.Yellow;
    private static final Color GEYSER_COLOR = Color.Orange;

    private DrawUtils drawUtils;
    private BwtaWrapper bwtaWrapper;

    public EnhancedUi(final BwtaWrapper bwtaWrapper, final DrawUtils drawUtils) {
        nonNull(bwtaWrapper, "bwtaWrapper");
        nonNull(drawUtils, "drawUtils");

        this.bwtaWrapper = bwtaWrapper;
        this.drawUtils = drawUtils;
    }

    public void update() {
        drawTerrain();
        drawBases();
        drawProgress();
    }

    private void drawBases() {
        for (BaseLocation baseLocation : bwtaWrapper.getBaseLocations()) {
            Position basePosition = baseLocation.getPosition();

            drawUtils.highlightBaseLocation(baseLocation, BASE_BOX_COLOR);

            for (Unit mineral : baseLocation.getMinerals()) {
                drawUtils.highlightMineralField(mineral, MINERAL_CIRCLE_COLOR);
            }

            // Highlight vespene geysers
            for (Unit geyser : baseLocation.getGeysers())
            {
               drawUtils.highlightGeyser(geyser, GEYSER_COLOR);
            }

            // Draw a yellow circle around the base location if it is an island expansion
            if (baseLocation.isIsland()) {
                drawUtils.highlightIslandBaseLocation(baseLocation, BASE_ISLAND_COLOR);
            }
        }
    }

    private void drawTerrain() {

    }

    private void drawProgress() {

    }

    private void drawProgressBar(final Position position, final double progressFraction) {
        drawProgressBar(position, progressFraction, Color.Green);
    }

    private void drawProgressBar(final Position position, final double progressFraction, final Color innerBarColor) {

    }
}
