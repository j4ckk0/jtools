package org.jtools.utils.image;

/*-
 * #%L
 * Java Tools - Utils
 * %%
 * Copyright (C) 2024 jtools.org
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.GrayFilter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
// TODO: Auto-generated Javadoc

/**
 * The Class ImageUtils.
 */
public class ImageUtils {

	/**
	 * Instantiates a new image utils.
	 */
	private ImageUtils() {
		super();
	}

	/**
	 * Hex 2 rgb.
	 *
	 * @param colorStr the color str
	 * @return the java.awt. color
	 */
	public static java.awt.Color hex2Rgb(String colorStr) {
		return new java.awt.Color(Integer.valueOf(colorStr.substring(1, 3), 16),
				Integer.valueOf(colorStr.substring(3, 5), 16), Integer.valueOf(colorStr.substring(5, 7), 16));
	}

	/**
	 * Gets the image for text.
	 *
	 * @param text the text
	 * @param alphaValue the alpha value
	 * @return the image for text
	 */
	public static BufferedImage getImageForText(String text, float alphaValue) {
		// Calculate the size of the image
		JLabel tmpLabel = new JLabel();
		Font font = UIManager.getDefaults().getFont("Label.font");
		FontMetrics fontMetrics = tmpLabel.getFontMetrics(font);
		int height = fontMetrics.getHeight() + 4;
		int width = SwingUtilities.computeStringWidth(fontMetrics, text) + 4;

		// Create the image
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();

		// Set the background
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, width, height);

		// Set the font of the text to be converted in the image
		g2d.setPaint(Color.BLACK);
		g2d.setFont(font);

		// Set the alpha value
		AlphaComposite alphaComposite = AlphaComposite.SrcOver.derive(alphaValue);
		Composite oldComposite = g2d.getComposite();
		g2d.setComposite(alphaComposite);

		// Draw the text
		g2d.drawString(text, 2, height - 4);

		// Set the old composite again
		g2d.setComposite(oldComposite);

		// Dispose the graphics
		g2d.dispose();

		return bi;
	}

	/**
	 * Resize image.
	 *
	 * @param originalImage the original image
	 * @param width the width
	 * @param height the height
	 * @return the buffered image
	 */
	public static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
		BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();

		return resizedImage;
	}

	/**
	 * Resize icon.
	 *
	 * @param originalImage the original image
	 * @param width the width
	 * @param height the height
	 * @return the image icon
	 */
	public static ImageIcon resizeIcon(ImageIcon originalImage, int width, int height) {
		return new ImageIcon(originalImage.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
	}

	/**
	 * Save icon.
	 *
	 * @param image the image
	 * @param imageName the image name
	 * @param imageFormat the image format
	 * @return the string
	 */
	public static String saveIcon(ImageIcon image, String imageName, String imageFormat) {
		Image img = image.getImage();

		BufferedImage bi = new BufferedImage(image.getIconWidth(), image.getIconHeight(),
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2 = bi.createGraphics();
		g2.drawImage(img, 0, 0, null);
		g2.dispose();

		return saveImage(bi, imageName, imageFormat);
	}

	/**
	 * Save image.
	 *
	 * @param imgData the img data
	 * @param imageName the image name
	 * @param imageFormat the image format
	 * @return the string
	 */
	public static String saveImage(BufferedImage imgData, String imageName, String imageFormat) {
		if (imgData == null) {
			return "";
		}
		try {
			File tempFile = File.createTempFile(imageName, "." + imgData.getType());

			ImageIO.write(imgData, imageFormat, tempFile);

			if (tempFile != null) {
				String fileabsolute = tempFile.getAbsolutePath();
				return fileabsolute;
			}

		} catch (IOException e) {
			Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, e.getMessage(), e);
		}
		return "";
	}

	/**
	 * Gets the gray.
	 *
	 * @param icon the icon
	 * @return the gray
	 */
	public static Icon getGray(Icon icon) {
		final int w = icon.getIconWidth();
		final int h = icon.getIconHeight();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		BufferedImage image = gc.createCompatibleImage(w, h);
		Graphics2D g2d = image.createGraphics();
		icon.paintIcon(null, g2d, 0, 0);
		Image gray = GrayFilter.createDisabledImage(image);
		return new ImageIcon(gray);
	}

	/**
	 * Creates the icon.
	 *
	 * @param width the width
	 * @param height the height
	 * @param borderThickness the border thickness
	 * @param color the color
	 * @return the image icon
	 */
	public static ImageIcon createIcon(int width, int height, int borderThickness, Color color) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = image.createGraphics();

		// Inside
		graphics.setPaint(color);
		graphics.fillRect(0, 0, image.getWidth(), image.getHeight());

		// border
		graphics.setPaint(color.darker().darker());
		for (int i = 0; i < borderThickness; i++) {
			graphics.drawRect(i, i, image.getWidth() - (2 * i) - 1, image.getHeight() - (2 * i) - 1);
		}

		return new ImageIcon(image);
	}

	/**
	 * Gets the color.
	 *
	 * @param percentValue the percent value
	 * @param percentColors the percent colors
	 * @param colors the colors
	 * @return the color
	 */
	public static Color getColor(float percentValue, float[] percentColors, Color[] colors) {

		if (percentValue < 0 || percentValue > 1) {
			throw new IllegalArgumentException("Percent value must be a float between 0f and 1f");
		}

		int i = 0;
		while (i < percentColors.length - 1) {
			++i;
			if (percentValue < percentColors[i]) {
				break;
			}
		}

		float lower = percentColors[i - 1];
		Color colorLower = colors[i - 1];

		float upper = percentColors[i];
		Color colorUpper = colors[i];

		float range = upper - lower;

		float rangePct = (percentValue - lower) / range;
		float pctLower = 1 - rangePct;
		float pctUpper = rangePct;

		return new Color((int) (colorLower.getRed() * pctLower + colorUpper.getRed() * pctUpper),
				(int) (colorLower.getGreen() * pctLower + colorUpper.getGreen() * pctUpper),
				(int) (colorLower.getBlue() * pctLower + colorUpper.getBlue() * pctUpper));
	}
}
