import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ButtonIpConvultion extends JButton implements Command {

    protected JLabel processedPic;

    public ButtonIpConvultion(JLabel processedPic){
        super("Blur");
        this.processedPic = processedPic;
    }

    @Override
    public void execute() { //Box blur
        //size of blur
        int factor = 5;

        BufferedImage image = DocumentManager.getInstance().getImageCopy();
        int w = image.getWidth();
        int h = image.getHeight();

        for (int y = factor; y < h-factor; y++) {
            for (int x = factor; x < w - factor; x++) {
                image.setRGB(x,y, getPixelColor(image, x, y,factor).getRGB());
            }
        }
        setImage(image, this.processedPic);
    }

    //getting 1/part of every pixel from surronding
    public Color getPixelColor(BufferedImage image, int x, int y, int range){
        Color myColor;
        int factor = 0;
        int newR=0, newG=0, newB=0;
        for(int j = (y+range); j>= (y-range); j--){
            for(int i = (x-range); i <= (x+range); i++){
                myColor = new Color(image.getRGB(i, j));
                newR += myColor.getRed();
                newG += myColor.getGreen();
                newB += myColor.getBlue();
                factor+=1;
            }
        }

        myColor = new Color(newR/factor, newG/factor, newB/factor);
        return myColor;
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

/*
    x-1 y+1 | x y+1 | x+1 y+1
    x-1 y   |       | x+1 y
    x-1 y-1 | x y-1 | x+1 y-1
*/