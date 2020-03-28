import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ButtonIpWarp extends JButton implements Command {

    protected JLabel processedPic;
    public ButtonIpWarp(JLabel processedPic){
        super("Rotate");
        this.processedPic = processedPic;
    }

    @Override
    public void execute() { //rotation
        //set the degree's of the rotation
        double degree = Math.toRadians(20);

        BufferedImage image = DocumentManager.getInstance().getImageCopy();
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage processedImage = DocumentManager.getInstance().getImageCopy();

        for (int y = 0; y < h ; y++) { //first with height
            for (int x = 0; x < w ; x++) {
                processedImage.setRGB(x, y, new Color(255,255,255).getRGB());
            }
        }

        for (int y = 0; y < h ; y++) { //first with height
            for (int x = 0; x < w ; x++) {
                int u = (int)((x * Math.cos(degree)) - y * Math.sin(degree));
                int v = (int)((x * Math.sin(degree)) + y * Math.cos(degree));
                if(u>0 && u<w && v>0 && v<h )
                    processedImage.setRGB(x, y, image.getRGB(u, v));
            }
        }
        setImage(processedImage, this.processedPic);
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
