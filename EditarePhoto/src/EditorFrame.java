import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;


public class EditorFrame extends JFrame implements ActionListener {

    protected JTextField textImageName = new JTextField();
    protected JLabel picLabel; //original pic
    protected JLabel processedPic; //processed pic
    protected JLabel original = new JLabel();
    protected JLabel edited = new JLabel();

    protected JPanel p1;//panel for browse button and text field
    protected JPanel panelMsg;//panel for text original/edited photo
    protected JPanel panelImage;
    protected JPanel p2; //panel for all the filters
    protected JPanel p3; //panel for save button
    protected JPanel pAll; //panel where all the panels are addade on y axis

    protected JButton browse;
    protected JButton floydSteinberg;
    protected JButton antiAliasing;
    protected JButton imageProcessingPixel;
    protected JButton imageProcessingConvultion;
    protected JButton imageProcessingWarp;
    protected JButton blend;
    protected JButton save;

    protected String path = "./src/startPhoto.png";


    public EditorFrame(){
        setResizable(false);
        setTitle("Editare photo");

        //edited/original text labels
        this.panelMsg = new JPanel();
        this.original.setText("Original");
        this.edited.setText("Edited");
        this.original.setPreferredSize(new Dimension(550, 20));
        this.edited.setPreferredSize(new Dimension(550, 20));
        this.original.setHorizontalAlignment(JLabel.CENTER);
        this.edited.setHorizontalAlignment(JLabel.CENTER);
        this.panelMsg.add(original);
        this.panelMsg.add(edited);

        //set start photo
        this.panelImage = new JPanel();
        this.picLabel = setImage(this.path);
        this.processedPic = setImage(this.path);
        this.panelImage.add(picLabel);
        this.panelImage.add(processedPic);

        //initialize buttons
        this.browse = new ButtonBrowse(this.picLabel, this.textImageName);
        this.browse.addActionListener(this);
        this.floydSteinberg = new ButtonFloydSteinberg(this.processedPic);
        this.floydSteinberg.addActionListener(this);
        this.antiAliasing = new JButton("Anti Aliasing");
        this.imageProcessingPixel = new ButtonIpPixel(this.processedPic);
        this.imageProcessingPixel.addActionListener(this);
        this.imageProcessingConvultion = new ButtonIpConvultion(this.processedPic);
        this.imageProcessingConvultion.addActionListener(this);
        this.imageProcessingWarp = new ButtonIpWarp(this.processedPic);
        this.imageProcessingWarp.addActionListener(this);
        this.blend = new ButtonBlend(this.processedPic);
        this.blend.addActionListener(this);
        this.save = new ButtonSave();
        this.save.addActionListener(this);

        //browse button and text field
        this.textImageName.setPreferredSize(new Dimension(700, 25));
        this.textImageName.setEditable(false);
        this.p1=new JPanel();
        this.p1.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.p1.add(textImageName);
        this.p1.add(browse);

        //filter selector
        this.p2=new JPanel();
        this.p2.add(floydSteinberg);
        this.p2.add(antiAliasing);
        this.p2.add(imageProcessingPixel);
        this.p2.add(imageProcessingConvultion);
        this.p2.add(imageProcessingWarp);
        this.p2.add(blend);
        this.p2.setBorder(BorderFactory.createTitledBorder("Filter"));
        this.p2.setBounds(10, 36, 227, 83);

        //submit button
        this.p3=new JPanel();
        this.p3.add(save);

        //add all panels to pAll
        this.pAll=new JPanel();
        this.pAll.add(p1);
        this.pAll.add(panelMsg);
        this.pAll.add(panelImage);
        this.pAll.add(p2);
        this.pAll.add(p3);

        //set y axis box layout and than pack the GUI
        BoxLayout layout = new BoxLayout(pAll, BoxLayout.Y_AXIS); //structurare layout
        this.pAll.setLayout(layout);
        this.add(pAll);
        this.pack();
        super.setVisible(true);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    //static func for setting the first images when the app is opened
    public static JLabel setImage(String path){
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int height = myPicture.getHeight();
        int width = myPicture.getWidth();

        while(height>500){
            height = height /3;
            width = width /3 ;
        }
        Image scaled = myPicture.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaled);
        JLabel picLabel = new JLabel();
        picLabel.setIcon(icon);
        return picLabel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ((Command)e.getSource()).execute();
    }

}
