import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import net.coobird.thumbnailator.Thumbnails;

public class Button extends JButton {
	public Button(double scaleFactor, String buttonImage) {
		
		try {
			BufferedImage orgImg = ImageIO.read( getClass().getResourceAsStream( buttonImage+".png" ) );
			BufferedImage scaledImg = Thumbnails.of(orgImg).scale(scaleFactor).asBufferedImage();
	        ImageIcon icon = new ImageIcon( scaledImg );
	        this.setIcon(icon);
		} catch(IOException ex) {
			System.out.println("Could not load "+buttonImage);
		}
		try {
			BufferedImage orgImg = ImageIO.read( getClass().getResourceAsStream( buttonImage+"Hover"+".png" ) );
			BufferedImage scaledImg = Thumbnails.of(orgImg).scale(scaleFactor).asBufferedImage();
	        ImageIcon icon = new ImageIcon( scaledImg );
	        this.setRolloverIcon(icon);
		} catch(IOException ex) {
			System.out.println("Could not load "+buttonImage);
		}
		try {
			BufferedImage orgImg = ImageIO.read( getClass().getResourceAsStream( buttonImage+"Click"+".png" ) );
			BufferedImage scaledImg = Thumbnails.of(orgImg).scale(scaleFactor).asBufferedImage();
	        ImageIcon icon = new ImageIcon( scaledImg );
	        this.setPressedIcon(icon);
		} catch(IOException ex) {
			System.out.println("Could not load "+buttonImage);
		}
		this.setBorderPainted(false);	//Border painting
		this.setBorder(null);	//Size of button->Rectangle of icon
		this.setContentAreaFilled(false);
		this.setFocusPainted( false );
		
	}
}
