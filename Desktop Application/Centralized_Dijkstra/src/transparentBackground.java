import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.AlphaComposite;
import java.awt.Color;
import javax.swing.JComponent;

public class transparentBackground extends JComponent {
	
	/**
	 * @author Christos Daoulas
	 */
	private static final long serialVersionUID = -1172182746660515606L;
	private int width, height;
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		final Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setComposite(AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, 0.4f));
		g2.setColor(Color.white);
		g2.fillRect(0, 0, width, height);
	}
	
	public void setDimensions(int width, int height) {
		this.width = width;
		this.height = height;
	}
}