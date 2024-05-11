package Field;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class HexButton extends JButton {

    private final int size;

    public HexButton(int size) {
        this.size = size;
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        HexagonPath path = new HexagonPath(this.size);
        g2d.setColor(getForeground());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.draw(path);
        g2d.dispose();
    }

    @Override
    public Color getForeground() {
        if (getModel().isArmed()) {
            return Color.GRAY;
        }
        return super.getForeground();
    }

    protected static class HexagonPath extends Path2D.Double {

        public HexagonPath(double size) {
            double centerX = size / 2d;
            double centerY = size / 2d;
            for (int i = 0; i < 6; i++) {
                double angleDegrees = (60d * i) - 30d;
                double angleRad = ((float) Math.PI / 180.0f) * angleDegrees;
                double x = centerX + ((size / 2f) * Math.cos(angleRad));
                double y = centerY + ((size / 2f) * Math.sin(angleRad));

                if (i == 0) moveTo(x, y);
                else lineTo(x, y);
            }
            closePath();
        }

    }
}
