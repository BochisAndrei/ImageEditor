import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ButtonSave extends JButton implements Command {

    public ButtonSave(){
        super("Save");
    }
    @Override
    public void execute() {
        if(DocumentManager.getInstance().getProcessedImage() != null) {
            System.out.println("Button save pushed");
            JFileChooser file = new JFileChooser();
            file.setCurrentDirectory(new File(System.getProperty("user.home")));
            //filter the files
            FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
            file.addChoosableFileFilter(filter);
            int result = file.showSaveDialog(null);
            //if the user click on save in Jfilechooser
            if (result == file.APPROVE_OPTION) {
                File selectedFile = file.getSelectedFile();
                String path = selectedFile.getAbsolutePath();
                File outputfile = new File(path);
                BufferedImage image = DocumentManager.getInstance().getProcessedImage();
                try {
                    ImageIO.write(image, "jpg", outputfile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //if the user click on save in Jfilechooser

            else if (result == JFileChooser.CANCEL_OPTION) {
                System.out.println("No File Select");
            }
        }
    }
}
