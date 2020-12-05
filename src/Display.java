import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.JTextField;

public class Display extends JTextField {
	
	public Display(int columns) {
		
		try {
			this.setFont(  (Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("OpenSans-Regular.ttf"))).  deriveFont(Font.PLAIN,100) );
		} catch ( FontFormatException | IOException ex) {
			System.out.println("Could not load the custom font");
		}
		
		this.setForeground(Color.WHITE);
		this.setBackground(Color.BLACK);
		this.setBorder(null);
		this.setEditable(false);
		
	}
}
