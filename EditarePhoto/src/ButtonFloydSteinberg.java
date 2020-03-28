import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ButtonFloydSteinberg extends JButton implements Command {

    protected JLabel processedPic;

    public ButtonFloydSteinberg(JLabel processedPic){
        super("Floyd Steinberg");
        this.processedPic = processedPic;
    }

    @Override
    public void execute() {
        Color myColor;
        int oldR, oldG, oldB;
        //colors to use in the processed image
        int newR, newG, newB;
        //errors
        int errR, errG, errB;
        int factor = 1;

        BufferedImage image = DocumentManager.getInstance().getImageCopy();
        int w = image.getWidth();
        int h = image.getHeight();

        if(image != null) {
            for (int y = 0; y < h-1; y++) { //first with height
                for (int x = 1; x < w-1; x++) {
                    myColor = new Color(image.getRGB(x, y));
                    oldR = myColor.getRed();
                    oldG = myColor.getGreen();
                    oldB = myColor.getBlue();
                    newR = Math.round(oldR*factor/255) * (255/factor);
                    newG = Math.round(oldG*factor/255) * (255/factor);
                    newB = Math.round(oldB*factor/255) * (255/factor);
                    image.setRGB(x, y, new Color(newR,newG,newB).getRGB());

                    errR = oldR - newR;
                    errG = oldG - newG;
                    errB = oldB - newB;

                    changePixel(image, x + 1, y, errR, errG, errB, 7);
                    changePixel(image, x - 1, y + 1, errR, errG, errB,3);
                    changePixel(image, x, y + 1, errR, errG, errB,5);
                    changePixel(image, x + 1, y + 1, errR, errG, errB,1);
                }
            }
            setImage(image, this.processedPic);
        }
    }

    public static void changePixel(BufferedImage image, int x, int y, int errR, int errG, int errB, int quant){
        Color myColor = new Color(image.getRGB(x,y));
        int r = myColor.getRed();
        int g = myColor.getGreen();
        int b = myColor.getBlue();
        r = (r + errR * quant / 16);
        if(r>255) r=255;
        g = (g + errG * quant / 16);
        if(g>255) g=255;
        b = (b + errB * quant / 16);
        if(b>255) b=255;
        image.setRGB(x ,y, new Color(r, g, b).getRGB());

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
