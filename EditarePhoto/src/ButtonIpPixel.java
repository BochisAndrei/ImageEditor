import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class ButtonIpPixel extends JButton implements Command {

    private JLabel processedPic;

    public ButtonIpPixel(JLabel processedPic){
        super("Brightness");
        this.processedPic = processedPic;

    }
    @Override
    public void execute() {
        Color myColor;
        int oldR, oldG, oldB;
        //colors to use in the processed image
        int newR, newG, newB;
        int factor = 3;

        BufferedImage image = DocumentManager.getInstance().getImageCopy();
        int w = image.getWidth();
        int h = image.getHeight();

        for (int y = 0; y < h-1; y++) {
            for (int x = 1; x < w-1; x++) {
                myColor = new Color(image.getRGB(x, y));
                oldR = myColor.getRed();
                oldG = myColor.getGreen();
                oldB = myColor.getBlue();

                newR = oldR * factor; if(newR > 255) newR = 255;
                newG = oldG * factor; if(newG > 255) newG = 255;
                newB = oldB * factor; if(newB > 255) newB = 255;

                image.setRGB(x, y, new Color(newR,newG,newB).getRGB());
            }
        }
        setImage(image, this.processedPic);
    }

    public static void setImage(BufferedImage myPicture, JLabel picLabel){
        //add image into singleton
        DocumentManager.getInstance().setProcessedImage(myPicture);

        int height = myPicture.getHeight();
        int width = myPicture.getWidth();

        while(height>400 || width > 650){
            height = height /2;
            width = width /2 ;
        }
        Image scaled = myPicture.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaled);
        picLabel.setIcon(icon);
        System.out.println("Edited image loaded");
    }

}
