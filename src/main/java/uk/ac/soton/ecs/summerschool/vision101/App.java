/**
 * Copyright (c) 2015, The University of Southampton.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 *   * 	Redistributions of source code must retain the above copyright notice,
 * 	this list of conditions and the following disclaimer.
 *
 *   *	Redistributions in binary form must reproduce the above copyright notice,
 * 	this list of conditions and the following disclaimer in the documentation
 * 	and/or other materials provided with the distribution.
 *
 *   *	Neither the name of the University of Southampton nor the names of its
 * 	contributors may be used to endorse or promote products derived from this
 * 	software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package uk.ac.soton.ecs.summerschool.vision101;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openimaj.content.slideshow.PictureSlide;
import org.openimaj.content.slideshow.Slide;
import org.openimaj.content.slideshow.SlideshowApplication;
import org.openimaj.content.slideshow.VideoSlide;
import org.openimaj.video.VideoDisplay.EndAction;

import uk.ac.soton.ecs.summerschool.vision101.utils.SpeakingSlide;

/**
 * ECS Summer School Computer Vision Lecture
 */
public class App {
	private static BufferedImage background = null;
	static int SLIDE_WIDTH;
	static int SLIDE_HEIGHT;

	static {
		final GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		final Dimension size = device.getDefaultConfiguration().getBounds().getSize();

		if (size.width >= 1024)
			SLIDE_WIDTH = 1024;
		else
			SLIDE_WIDTH = size.width;
		SLIDE_HEIGHT = SLIDE_WIDTH * 3 / 4;
	}

	/**
	 * Main method
	 *
	 * @param args
	 *            ignored
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		final List<Slide> slides = new ArrayList<Slide>();

		for (int i = 1; i <= 5; i++)
			slides.add(new PictureSlide(App.class.getResource(String.format("slides/slides.%03d.jpeg", i))));

		slides.add(new VideoSlide(App.class.getResource("tomato.mp4"), App.class.getResource("slides/slides.006.jpeg"),
				EndAction.PAUSE_AT_END));

		slides.add(new PictureSlide(App.class.getResource("slides/slides.007.jpeg")));

		slides.add(new VideoSlide(App.class.getResource("car.mp4"), App.class.getResource("slides/slides.008.jpeg"),
				EndAction.PAUSE_AT_END));

		// slides.add(new InmoovDemo(App.class.getResource("slides/slides.009.jpeg"))); // 9

		slides.add(new PictureSlide(App.class.getResource("slides/slides.010.jpeg")));

		slides.add(new ArtARDemo()); // 11

		for (int i = 12; i <= 26; i++)
			slides.add(new PictureSlide(App.class.getResource(String.format("slides/slides.%03d.jpeg", i))));

		// slides.add(new SimpleCameraDemo("FaceTime")); // 27

		for (int i = 28; i <= 29; i++)
			slides.add(new PictureSlide(App.class.getResource(String.format("slides/slides.%03d.jpeg", i))));

		slides.add(new SimpleMeanColourFeatureDemo()); // 30
		slides.add(new StickyFeaturesDemo()); // 31
		// slides.add(new PDMDemo()); // EXTRA
		slides.add(new CLMDemo()); // 32
		slides.add(new PuppeteerDemo()); // 33

		// for (int i = 34; i <= 39; i++)
		// 	slides.add(new PictureSlide(App.class.getResource(String.format("slides/slides.%03d.jpeg", i))));

		// slides.add(new BadTomatoDemo(App.class.getResource(String.format("slides/slides.%03d.jpeg", 40)))); // 40

		// slides.add(new PictureSlide(App.class.getResource(String.format("slides/slides.%03d.jpeg", 41))));
		
		// slides.add(new SpeakingSlide(App.class.getResource("slides/slides.042.jpeg"),
		// 		"a man is climbing up a rock face"));
		// slides.add(new SpeakingSlide(App.class.getResource("slides/slides.043.jpeg"),
		// 		"a motorcycle racer is driving a turn on a racetrack"));
		// slides.add(new SpeakingSlide(App.class.getResource("slides/slides.044.jpeg"),
		// 		"a basketball player in a red uniform is trying to score a player in the air"));
		// slides.add(new SpeakingSlide(App.class.getResource("slides/slides.045.jpeg"),
		// 		"a man in a red shirt is riding a bike on a snowy hill"));
		// slides.add(new SpeakingSlide(App.class.getResource("slides/slides.046.jpeg"),
		// 		"a surfer is jumping off a snowy hill"));

		for (int i = 47; i <= 48; i++)
			slides.add(new PictureSlide(App.class.getResource(String.format("slides/slides.%03d.jpeg", i))));

		new SlideshowApplication(slides, 1024, 768, getBackground());
	}

	/**
	 * @return the slide background
	 */
	public synchronized static BufferedImage getBackground() {
		if (background == null) {
			background = new BufferedImage(SLIDE_WIDTH, SLIDE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
			final Graphics2D g = background.createGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, background.getWidth(), background.getHeight());
		}
		return background;
	}

	public static int getVideoWidth(int remainder) {
		final int avail = SLIDE_WIDTH - remainder;
		if (avail >= 640)
			return 640;
		return 320;
	}

	public static int getVideoHeight(int remainder) {
		final int width = getVideoWidth(remainder);
		switch (width) {
		case 640:
			return 480;
		}
		return 240;
	}
}
