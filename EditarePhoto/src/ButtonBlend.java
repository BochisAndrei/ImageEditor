import sun.awt.image.ToolkitImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ButtonBlend extends JButton implements Command {

    protected JLabel picProcessed;
    protected String path;

    public ButtonBlend(JLabel picProcessed){
        super("Blend");
        this.picProcessed = picProcessed;
    }

    @Override
    public void execute() {
        System.out.println("Button blend pushed");
        JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.home")));
        //filter the files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
        file.addChoosableFileFilter(filter);
        int result = file.showOpenDialog(null);
        //if the user click on save in Jfilechooser
        if(result == file.APPROVE_OPTION){
            File selectedFile = file.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
            //set the image to a bufferedImage variable
            this.path = path;
        }
        //if the user click on save in Jfilechooser
        else if(result == JFileChooser.CANCEL_OPTION){
            System.out.println("No File Select");
        }
        //blend two Images
        blendImage(this.picProcessed, getImage(this.path));

    }

    public static BufferedImage getImage(String path){
        BufferedImage secondPic = null;
        try {
            secondPic = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return secondPic;
    }

    public static void blendImage(JLabel picProcessed, BufferedImage secondPic){

        //where to place the seond image
        int coordinateX = 100;
        int coodinateY = 100;

        //get the first image
        BufferedImage myPicture = DocumentManager.getInstance().getImageCopy();
        int heightOfImage = myPicture.getHeight();
        int widthOfImage = myPicture.getWidth();
        //get the hight and width of the second image
        int secondPicHeight = secondPic.getHeight();
        int secondPicWidth = secondPic.getWidth();

        //change pixels from the first image
        Color myColor;
        int firstR, firstG, firstB;
        for (int y = 0; y < secondPicHeight; y++){ //first with height
            for (int x = 0; x < secondPicWidth; x++) {
                myColor = new Color(secondPic.getRGB(x, y));
                firstR = myColor.getRed();
                firstG = myColor.getGreen();
                firstB = myColor.getBlue();
                myPicture.setRGB(x+coordinateX, y+coodinateY, new Color(firstR,firstG,firstB).getRGB());
            }
        }

        DocumentManager.getInstance().setProcessedImage(myPicture);
        //shrink processedImage
        while(heightOfImage>400 || widthOfImage > 650){
            heightOfImage = heightOfImage /2;
            widthOfImage = widthOfImage /2 ;
        }
        //add it to the frame
        System.out.println("Height: " +heightOfImage+" Width: " + widthOfImage);
        Image scaled = myPicture.getScaledInstance(widthOfImage, heightOfImage, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaled);
        picProcessed.setIcon(icon);
        System.out.println("Image loaded");
    }

}
