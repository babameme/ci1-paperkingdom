package bases.renderers;

import bases.FrameCounter;
import bases.Vector2D;

import java.awt.Graphics2D;
import java.util.*;
import java.awt.image.BufferedImage;

/**
 * Created by huynq on 8/16/17.
 */
public class Animation implements Renderer {

    private List<BufferedImage> images;
    private FrameCounter frameCounter;
    private int currentImageIndex;
    private boolean reverse;
    private boolean oneTime;
    private boolean stopped;

    public Animation(int frameDelay, boolean reserve, BufferedImage... images) {
        this.images = Arrays.asList(images);
        this.frameCounter = new FrameCounter(frameDelay);
        this.currentImageIndex = 0;
        this.reverse = reserve;
    }

    public Animation(BufferedImage... images) {
        this(12, false, images);
    }

    @Override
    public void render(Graphics2D g2d, Vector2D position) {
        BufferedImage image = images.get(currentImageIndex);
        Vector2D renderPosition = position.subtract(
                image.getWidth() / 2,
                image.getHeight() / 2
        );

        g2d.drawImage(image, (int) renderPosition.x, (int) renderPosition.y, null);

        updateCurrentImage();
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    private void updateCurrentImage() {
        if (frameCounter.run()) {
            frameCounter.reset();
            if (!reverse) {
                currentImageIndex++;
                if (currentImageIndex >= images.size()) {
                    if(!oneTime) {
                        currentImageIndex = 0;
                    }

                }
            } else {
                currentImageIndex--;
                if (currentImageIndex < 0) {
                    if (!oneTime) {
                        currentImageIndex = images.size() - 1;
                    }
                }
            }
        }
    }
}
