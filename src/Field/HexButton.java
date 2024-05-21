package Field;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class HexButton extends JButton {

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        HexagonPath path = new HexagonPath(this.getWidth());
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
            double centerX = size / 2;
            double centerY = size / 2;
            for (int i = 0; i < 6; i++) {
                double angleDegrees = (60 * i) - 30;
                double angleRad = (Math.PI * angleDegrees) / 180;
                double x = centerX + ((size / 2) * Math.cos(angleRad));
                double y = centerY + ((size / 2) * Math.sin(angleRad));

                if (i == 0) moveTo(x, y);
                else lineTo(x, y);
            }
            closePath();
        }

    }
}
