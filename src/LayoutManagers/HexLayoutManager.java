package LayoutManagers;

import java.awt.*;

public class HexLayoutManager implements LayoutManager {

    private final int width;
    private final int height;

    public HexLayoutManager(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        int preferredWidth = (int)(parent.getWidth() * 0.8);
        int cellWidth = preferredWidth / width;
        int cellHeight = parent.getHeight() / height;
        return new Dimension(cellWidth * width, cellHeight * height);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return this.preferredLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
        int preferredWidth = (int)(parent.getWidth() * 0.65);
        int parentHeight = parent.getHeight();
        int cellWidth = (int)((double)(preferredWidth / width));
        int cellHeight = (int)((double)(parentHeight / height));
        int cellSize = Math.min(cellWidth, cellHeight);
        int cellWidthOffset = (parent.getWidth() - cellSize * width) / 8;
        int cellHeightOffset = (parentHeight - cellSize * height) / 2;

        if (cellSize * width > preferredWidth) {
            cellSize = cellWidth;
        }

        for (int i = 0; i < parent.getComponentCount(); i++) {
            Component component = parent.getComponent(i);
            int x = i % width;
            int y = i / width;
            int xPosition = cellWidthOffset + x * cellSize + (cellSize / 2) * y;
            int yPosition = cellHeightOffset + y * cellSize;
            component.setBounds(xPosition, yPosition, cellSize, cellSize);
        }
    }

}
