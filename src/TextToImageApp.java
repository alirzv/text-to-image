
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextToImageApp extends JFrame implements ActionListener {
    public static final String URDU_DEFAULT_FONT = "JameelNooriNastaleeq";
    public static final String ENGLISH_DEFAULT_FONT = "Serif";
    JFrame frame;
    JLabel engFtSizeLabel;
    JLabel urduFtSizeLabel;
    JLabel fileLabel,fileLabel2,fileLabel3,fileLabel4;
    JLabel engFontListLabel, urduFontListLabel;
    JButton bgColorButton;
    JButton ftColorButton;
    JTextArea bgColorArea;
    JTextArea ftColorArea;
    JTextArea infoArea;
    JComboBox engTextSizeBox;
    JComboBox urduTextSizeBox;
    JComboBox engFontListBox, urduFontListBox;
    JButton subButton;
    Color bgColor = Color.BLACK;
    Color ftColor = Color.WHITE;
    FileChooser f1,f2,f3, f4;
    JButton fileButton,fileButton2,fileButton3,fileButton4 ;
    String fileName, backgroundImage, headerImage, footerImage;


    TextToImageApp() {
        frame = new JFrame("Text-To-Image Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int y = 0;
        bgColorArea = new JTextArea();
        bgColorArea.setBounds(10, 13, 80, 25);
        bgColorArea.setEditable(false);
        bgColorArea.setBackground(Color.black);
        bgColorButton = new JButton("Select image background color");
        bgColorButton.setBounds(110, 10, 300, 30);
        bgColorButton.setBackground(Color.gray);
        bgColorButton.setOpaque(true);
        bgColorButton.addActionListener(this);
        frame.add(bgColorButton);
        frame.add(bgColorArea);

        y=y+50;
        ftColorArea = new JTextArea();
        ftColorArea.setBounds(10, y, 80, 25);
        ftColorArea.setEditable(false);
        ftColorArea.setBackground(Color.white);
        ftColorButton = new JButton("Select Text Font Color");
        ftColorButton.setBounds(110, y, 300, 30);
        ftColorButton.setBackground(Color.gray);
        ftColorButton.setOpaque(true);
        ftColorButton.addActionListener(this);
        frame.add(ftColorArea);
        frame.add(ftColorButton);

        y=y+40;
        engFtSizeLabel = new JLabel("English Font Size");
        engFtSizeLabel.setBounds(10, y, 200, 30);
        engTextSizeBox = new JComboBox(fontSizesArray());
        engTextSizeBox.setBounds(210, y, 100, 30);
        engTextSizeBox.setSelectedItem(35);
        frame.add(engTextSizeBox);
        frame.add(engFtSizeLabel);

        y=y+40;
        urduFtSizeLabel = new JLabel("Urdu Font Size");
        urduFtSizeLabel.setBounds(10, y, 200, 30);
        urduTextSizeBox = new JComboBox(fontSizesArray());
        urduTextSizeBox.setBounds(210, y, 100, 30);
        urduTextSizeBox.setSelectedItem(35);
        frame.add(urduFtSizeLabel);
        frame.add(urduTextSizeBox);

        y=y+40;
        engFontListLabel = new JLabel("English Font");
        engFontListLabel.setBounds(10, y, 200, 30);
        engFontListBox = new JComboBox(getFontslist());
        engFontListBox.setSelectedItem(ENGLISH_DEFAULT_FONT);
        engFontListBox.setBounds(210, y, 100, 30);
        frame.add(engFontListLabel);
        frame.add(engFontListBox);

        y=y+40;
        urduFontListLabel = new JLabel("Urdu Font");
        urduFontListLabel.setBounds(10, y, 200, 30);
        urduFontListBox = new JComboBox(getFontslist());
        urduFontListBox.setSelectedItem(URDU_DEFAULT_FONT);
        urduFontListBox.setBounds(210, y, 100, 30);
        frame.add(urduFontListLabel);
        frame.add(urduFontListBox);

        y=y+40;
        f1 = new FileChooser();
        fileButton = new JButton("Source File");
        fileButton.setBounds(20, y, 200, 30);
        fileButton.addActionListener(this);
        fileLabel = new JLabel("");
        fileLabel.setBounds(150, y, 300, 30);
        frame.add(fileButton);
        frame.add(fileLabel);

        y=y+40;
        f2 = new FileChooser();
        fileButton2 = new JButton("Background Image");
        fileButton2.setBounds(20, y, 200, 30);
        fileButton2.addActionListener(this);
        fileLabel2 = new JLabel("");
        fileLabel2.setBounds(150, y, 300, 30);
        frame.add(fileButton2);
        frame.add(fileLabel2);

        y=y+40;
        f3 = new FileChooser();
        fileButton3 = new JButton("Header Image");
        fileButton3.setBounds(20, y, 200, 30);
        fileButton3.addActionListener(this);
        fileLabel3 = new JLabel("");
        fileLabel3.setBounds(150, y, 300, 30);
        frame.add(fileButton3);
        frame.add(fileLabel3);

        y=y+40;
        f4 = new FileChooser();
        fileButton4 = new JButton("Footer Image");
        fileButton4.setBounds(20, y, 200, 30);
        fileButton4.addActionListener(this);
        fileLabel4 = new JLabel("");
        fileLabel4.setBounds(150, y, 300, 30);
        frame.add(fileButton4);
        frame.add(fileLabel4);

        y=y+40;
        subButton = new JButton("SUBMIT");
        subButton.setBounds(200, y, 100, 30);
        subButton.setBackground(Color.CYAN);
        subButton.addActionListener(this);
        frame.add(subButton);

        y=y+40;
        infoArea = new JTextArea("Select Values And Click Submit");
        infoArea.setBounds(10, y, 300, 45);
        infoArea.setEditable(false);
        frame.add(infoArea);

        frame.setLayout(null);
        frame.setSize(500, 600);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bgColorButton) {
            Color c = JColorChooser.showDialog(this, "Choose Background Color", Color.GRAY);
            bgColorArea.setBackground(c);
            bgColor = c;
            infoArea.setText("Select Values And Click Submit");
        } else if (e.getSource() == ftColorButton) {
            Color c = JColorChooser.showDialog(this, "Choose Font Color", Color.CYAN);
            ftColorArea.setBackground(c);
            ftColor = c;
            infoArea.setText("Select Values And Click Submit");
        } else if (e.getSource() == fileButton) {
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int r = j.showOpenDialog(null);
            fileLabel.setText(j.getSelectedFile().getAbsolutePath());
            fileName = j.getSelectedFile().getAbsolutePath();
            infoArea.setText("Select Values And Click Submit");
        } else if (e.getSource() == fileButton2) {
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int r = j.showOpenDialog(null);
            fileLabel2.setText(j.getSelectedFile().getAbsolutePath());
            backgroundImage = j.getSelectedFile().getAbsolutePath();
            infoArea.setText("Select Values And Click Submit");
        } else if (e.getSource() == fileButton3) {
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int r = j.showOpenDialog(null);
            fileLabel3.setText(j.getSelectedFile().getAbsolutePath());
            headerImage = j.getSelectedFile().getAbsolutePath();
            infoArea.setText("Select Values And Click Submit");
        } else if (e.getSource() == fileButton4) {
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int r = j.showOpenDialog(null);
            fileLabel4.setText(j.getSelectedFile().getAbsolutePath());
            footerImage = j.getSelectedFile().getAbsolutePath();
            infoArea.setText("Select Values And Click Submit");
        } else if (e.getSource() == subButton) {
            if (e.getActionCommand() == "SUBMIT") {
                System.out.println("Background" + bgColor + " FontColor" + ftColor);
                System.out.println(engTextSizeBox.getSelectedItem());
                System.out.println(urduTextSizeBox.getSelectedItem());
                String englishFontName = ENGLISH_DEFAULT_FONT;
                String urduFontName = URDU_DEFAULT_FONT;
                if (fileName.isEmpty()) {
                    infoArea.setText("Please choose a valid CSV file.");
                } else {
                    try {
                        TextToGraphicConverter tgc = new TextToGraphicConverter(fileName, backgroundImage, headerImage , footerImage, bgColor, ftColor, (String) engFontListBox.getSelectedItem(), (int) engTextSizeBox.getSelectedItem(), (String) urduFontListBox.getSelectedItem(), (int) urduTextSizeBox.getSelectedItem());
                        String message = tgc.createImages();
                        if(message.equalsIgnoreCase("success")){
                            infoArea.setText("Generated Images !!");
                        }else{
                            infoArea.setText(message);
                        }
                    } catch (Exception e1) {
                        infoArea.setText("Something went wrong!");
                        e1.printStackTrace();
                    }
                }
            }
        }

    }

    private Integer[] fontSizesArray() {
        Integer[] fontArray = new Integer[90];
        for (int i = 10, j = 0; i <= 99; i++, j++) {
            fontArray[j] = i;
        }
        return fontArray;
    }

    private String[] getFontslist() {
        return new AvailableFonts().getAllFontNames();
    }

    public static void main(String[] args) {
        TextToImageApp cc = new TextToImageApp();
    }
}