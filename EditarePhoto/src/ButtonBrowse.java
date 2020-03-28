import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ButtonBrowse extends JButton implements Command {

    protected JLabel picLabel;
    protected JTextField textImageName;

    public ButtonBrowse(JLabel picLabel, JTextField textImageName) {
        super("Browse");
        this.picLabel = picLabel;
        this.textImageName = textImageName;
    }

    @Override
    public void execute() {
        System.out.println("Button browse pushed");
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
            setImage(path, this.picLabel);
            this.textImageName.setText(path);
        }
        //if the user click on save in Jfilechooser

        else if(result == JFileChooser.CANCEL_OPTION){
            System.out.println("No File Select");
        }

    }

    public static void setImage(String path, JLabel picLabel){
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File(path));
            DocumentManager.getInstance().setImage(myPicture);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int height = myPicture.getHeight();
        int width = myPicture.getWidth();

        while(height>400 || width > 650){
            height = height /2;
            width = width /2 ;
        }

        System.out.println("Height: " +height+" Width: " + width);
        Image scaled = myPicture.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaled);
        picLabel.setIcon(icon);
        System.out.println("Image loaded");
    }


}
