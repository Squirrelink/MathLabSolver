import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.image.PixelGrabber;
import java.util.Scanner;
import java.util.Random;
public class Main {
    private static BufferedImage answerImage;
    private static BufferedImage currentImage;
    private static int problemsAnalyzed = 0;
    public static void main(String[] args) throws IOException, AWTException {
        initialBoot();
        runLoop();
        loopResultAnalyzer();
    }

    public static void runLoop() throws AWTException, IOException {
        while (!compareImages(answerImage, currentImage) && problemsAnalyzed < 12) {
            problemsAnalyzed++;
            GenerateNewProblem();
            officialScreenshot(false);
        }
    }

    public static void loopResultAnalyzer() {
        if (compareImages(answerImage, currentImage)) {
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXMatch Found!XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        }
        else {
            System.out.println("not found");
        }
        resetImages();
    }

    public static void click(int x, int y) throws AWTException {
        Robot bot = new Robot();
        bot.mouseMove(x, y);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public static void clickHelp() throws AWTException {
        click(2430, 230);
    }

    public static void clickSimilarQuestion() throws AWTException {
        click(2390, 1010);
    }

    public static void clickHelpSolve() throws AWTException {
        click(2430, 250);
    }

    public static void clickCloseHelpSolve() throws AWTException {
        click(2220, 325);
    }

    public static void GenerateNewProblem() throws AWTException {
        Robot robot = new Robot();
        clickHelp();
        clickHelpSolve();
        robot.delay(randomDelayGenerator());
        clickCloseHelpSolve();
    }

    public static void resetCursor() throws AWTException {
        click(2430, 700);
    }

    public static void resetImages() {
        answerImage = null;
        currentImage = null;
    }

    public static BufferedImage cropImage(BufferedImage bufferedImage, int x, int y, int width, int height) {
        BufferedImage croppedImage = bufferedImage.getSubimage(x, y, width, height);
        return croppedImage;
    }

    public static boolean compareImages(BufferedImage imgA, BufferedImage imgB) {
        // The images must be the same size.
        if (imgA.getWidth() != imgB.getWidth() || imgA.getHeight() != imgB.getHeight()) {
            return false;
        }
        int width = imgA.getWidth();
        int height = imgA.getHeight();
        int lastRGB = 0;
        int concurrentRGBCount = 0;
        // Loop over every pixel.
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Compare the pixels for equality.
                if (lastRGB != imgA.getRGB(x, y) || imgA.getRGB(x, y) == -1) {
                    lastRGB = imgA.getRGB(x, y);
                    concurrentRGBCount = 0;
                } else {
                    concurrentRGBCount++;
                    if (concurrentRGBCount > 1000) {
                        System.out.println("concurrenttops");
                        return true;
                    }
                }
                if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void captureScreenshot(boolean isAnswer) throws AWTException, IOException {
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage capture = new Robot().createScreenCapture(screenRect);
        if (isAnswer) {
            File outputfile = new File("answerScreenshot.jpg");
            ImageIO.write(capture, "jpg", outputfile);

            File croppedFile = new File("answerCrop.jpg");
            answerImage = cropImage(capture, 1285, 250, 1270, 540);
            ImageIO.write(answerImage, "jpg", croppedFile);
        } else {
            File outputfile = new File("currentScreenshot.jpg");
            ImageIO.write(capture, "jpg", outputfile);

            File croppedFile = new File("currentCrop.jpg");
            currentImage = cropImage(capture, 1285, 250, 1270, 540);
            ImageIO.write(currentImage, "jpg", croppedFile);
        }

    }

    public static void officialScreenshot(boolean isAnswer) throws AWTException, IOException {
        Robot robot = new Robot();
        robot.delay(randomDelayGenerator());
        resetCursor();
        captureScreenshot(isAnswer);
    }

    public static void initialBoot() throws AWTException, IOException {
        Robot robot = new Robot();
        // Initial Screenshot to compare generate problems
        officialScreenshot(true);
        clickSimilarQuestion();
        //Initial current Screenshot to compare to answer
        officialScreenshot(false);
    }

    public static int randomDelayGenerator() {
        int min = 600;
        int max = 830;
        //Generate random int value from 50 to 100
        int random_int = (int)(Math.random() * (max - min + 1) + min);
        return random_int;
    }
}
