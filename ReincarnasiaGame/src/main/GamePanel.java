package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Inputs.KeyboardInputs;
import Inputs.MouseInputs;

import static utils.Constants.PlayerConstants.*;
import static utils.Constants.Directions.*;

public class GamePanel extends JPanel{
	
	private MouseInputs mouseInputs;
	private float xDelta = 100, yDelta = 100;
	private BufferedImage img;
	private BufferedImage[][] animations;
	private BufferedImage[] aniImages;
	private int aniTick, aniIndex, aniSpeed = 14;
	private int playerAction = idle;
	private int playerDir = -1;
	private boolean moving = false;
	
	public GamePanel() {
		
		setFocusable(true);
		requestFocusInWindow();
		mouseInputs = new MouseInputs(this);
		
		importImg();
		loadAnimations();
		
		setPanelSize();
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);

	}
	
	private void loadAnimations() {
	    animations = new BufferedImage[aniImages.length][];

	    for (int action = 0; action < aniImages.length; action++) {
	        BufferedImage spriteSheet = aniImages[action];
	        int frames = GetSpriteAmount(action);

	        // Get width & height of a single frame dynamically
	        int frameWidth = spriteSheet.getWidth();  // The entire width is one frame
	        int frameHeight = spriteSheet.getHeight() / frames; // Height divided by frame count

	        animations[action] = new BufferedImage[frames];

	        for (int frame = 0; frame < frames; frame++) {
	            try {
	                // Extract frames correctly from different Y positions
	                animations[action][frame] = spriteSheet.getSubimage(0, frame * frameHeight, frameWidth, frameHeight);
	            } catch (RasterFormatException e) {
	                System.err.println("Error extracting frame " + frame + " for action " + action);
	                e.printStackTrace();
	            }
	        }
	    }
	}

	private void importImg() {
		String[] actionNames = {"idle", "run", "walk", "combatIdle", "jump", "slash", "hurt"};
		aniImages = new BufferedImage[actionNames.length];
		
		
		for (int i = 0; i < actionNames.length; i++) {
			InputStream is = getClass().getResourceAsStream("/" + actionNames[i] + ".png");
			
			try {
				aniImages[i] = ImageIO.read(is);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (is != null) is.close();
				} catch (IOException e) {
					e.printStackTrace();			
					}
			}
		}
	}

	private void setPanelSize() {
		Dimension size = new Dimension(1280, 800);
		setMinimumSize(size);
		setMaximumSize(size);
		setPreferredSize(size);
		
	}
	
	private void setAnimation() {
		if (moving) {
			playerAction = run;
		}
		else
			playerAction = idle;
		
	}
	
	private void updatePos() {
		if (moving) {
			switch (playerDir) {
			case left:
				xDelta -= 5;
				break;
			case up:
				yDelta -= 5;
				break;
			case right:
				xDelta += 5;
				break;
			case down:
				yDelta += 5;
				break;
			}
		}
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		updateAnimationTick();
		
		setAnimation();
		updatePos();

		g.drawImage(animations[playerAction][aniIndex], (int)xDelta, (int)yDelta, null);
	}
	


	public void setDirection(int direction) {
		this.playerDir = direction;
		moving = true;
	}
	
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	private void updateAnimationTick() {
		aniTick++;
		
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			
			if(aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
			}
		}
		
	}
}
