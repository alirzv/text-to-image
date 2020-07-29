import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextToGraphicConverter {

    public static final int CONSTANT_1080 = 1080;
    public static final int CONSTANT_1280 = 1280;
    private String actualFilename;
    private String URDU_FONT_NAME = "JameelNooriNastaleeq";
    private String ENGLISH_FONT_NAME = "Serif";
    private Color BACKGROUND_COLOR = new Color(68, 21, 46);
    private Color FONT_COLOR = new Color(227, 212, 255);
    private int FONT_SIZE_ENGLISH = 40;
    private int FONT_SIZE_URDU = 60;
    private String fileName;
    private String backgroundImage;
    private String headerImage;
    private String footerImage;

    TextToGraphicConverter(String fileName) {
        this.fileName = fileName;
    }

    TextToGraphicConverter(String fileName, String backgroundImage, String headerImage, String footerImage, Color backgroundColor, Color fontColor, String englishFontName, int fontSizeEnglish, String urduFontName, int fontSizeUrdu) {
        this.fileName = fileName;
        this.BACKGROUND_COLOR = backgroundColor;
        this.FONT_COLOR = fontColor;
        this.FONT_SIZE_ENGLISH = fontSizeEnglish;
        this.FONT_SIZE_URDU = fontSizeUrdu;
        this.URDU_FONT_NAME = urduFontName;
        this.ENGLISH_FONT_NAME = englishFontName;
        this.backgroundImage = backgroundImage;
        this.headerImage = headerImage;
        this.footerImage = footerImage;
    }

    public String createImages() throws Exception {
        List<List<String>> fileContentList = readData(fileName);
        String folder = createFolder();

        for (List<String> lineList : fileContentList) {
            String firstColumn = lineList.get(0);
            if(firstColumn.equalsIgnoreCase("English-urdu")){
                String fileNameEng = folder + fileContentList.indexOf(lineList) + "_english-urdu.png";
                createCombinedImage(lineList, fileNameEng, FONT_SIZE_ENGLISH, fileContentList.indexOf(lineList));
            }else if(firstColumn.equalsIgnoreCase("English") || firstColumn.equalsIgnoreCase("eng")){
                try {
                    String fileNameEng = folder + fileContentList.indexOf(lineList) + "_english.png";
                    ImageIO.write(createEnglishImage(lineList, FONT_SIZE_ENGLISH, CONSTANT_1080, CONSTANT_1080,true), "png", new File(fileNameEng));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(firstColumn.equalsIgnoreCase("urdu") || firstColumn.equalsIgnoreCase("اردو")){
                try {
                    String fileNameUrdu = folder + fileContentList.indexOf(lineList) + "_urdu.png";
                    ImageIO.write(createUrduImage(lineList, FONT_SIZE_URDU, CONSTANT_1080, CONSTANT_1080, true), "png", new File(fileNameUrdu));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else{
                return "Text file format is not valid ! Correct first column.";
            }
        }
        return "success";
    }


    private List<List<String>> readData(String fileName) throws IOException {
        int count = 0;
        List<List<String>> fileContentList = new ArrayList<>();
        ;
        List<String> lineList = new ArrayList<>();
        try {
            Path path = Paths.get(fileName);
            BufferedReader br = Files.newBufferedReader(path);
            actualFilename = path.getFileName().toString();
            System.out.println(br.ready());
            System.out.println("actualFilename: "+actualFilename);
            String line = "";
            while ((line = br.readLine()) != null) {
                lineList = Arrays.asList(line.split(","));
                fileContentList.add(lineList);
                System.out.println(line);
            }
        } catch (InvalidPathException e) {
            System.out.println("Exception!!");
            e.printStackTrace();
        }
        return fileContentList;
    }

    private String createFolder() {
        String folder = "./"+actualFilename.substring(0,actualFilename.length()-4);
        new File(folder).mkdir();
        folder = folder + "/";
        return folder;
    }

    private BufferedImage createEnglishImage(List<String> textList, int fontSize, int imageWidth, int imageHeight, boolean isHeaderFooter) throws IOException {
        BufferedImage image;
        Graphics2D g2d;
        if(null != backgroundImage && !backgroundImage.isEmpty()){
            image = ImageIO.read(new File(backgroundImage));
            g2d = image.createGraphics();
            g2d = intializeGraphics(g2d);
            g2d.drawImage(image, 0, 0, null);
        }else{
            image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
            g2d = image.createGraphics();
            g2d = intializeGraphics(g2d);
            g2d.setColor(BACKGROUND_COLOR);
            g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        }
        for (String text : textList) {
            if(textList.get(0) == text){
                continue;
            }
            if (null != text && !text.isEmpty()) {
                if (textList.get(1) == text) {
                    Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
                    fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    Font boldUnderline = new Font(ENGLISH_FONT_NAME, Font.BOLD, fontSize).deriveFont(fontAttributes);
                    g2d.setColor(FONT_COLOR);
                    g2d.setFont(boldUnderline);
                    g2d.drawString(text, getXForCenteredString(g2d, boldUnderline, text, image), textList.indexOf(text) * (fontSize) + fontSize);
                } else {
                    Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
                    fontAttributes.put(TextAttribute.BACKGROUND, Paint.TRANSLUCENT);
                    Font font = new Font(ENGLISH_FONT_NAME, Font.PLAIN, fontSize).deriveFont(fontAttributes);
                    g2d.setColor(FONT_COLOR);
                    g2d.setFont(font);
                    if (getXForCenteredString(g2d, font, text, image) < 0) {
                        int newFontSize = fontSize - 10;
                        Font newFont = new Font(ENGLISH_FONT_NAME, Font.ITALIC, newFontSize).deriveFont(fontAttributes);
                        g2d.setFont(newFont);
                        g2d.drawString(text, getXForCenteredString(g2d, newFont, text, image), textList.indexOf(text) * (fontSize + fontSize) );
                    } else {
                        g2d.drawString(text, getXForCenteredString(g2d, font, text, image), textList.indexOf(text) * (fontSize + fontSize) );
                    }
                }
            }
        }
        if(isHeaderFooter){
            headerImagePrint(g2d);
            footerImagePrint(g2d, image.getWidth(), image.getHeight());
        }
        g2d.dispose();
        return  image;
    }

    private BufferedImage createUrduImage(List<String> textList, int fontSize, int imageWidth, int imageHeight, boolean isHeaderFooter) throws IOException {
        BufferedImage image;
        Graphics2D g2d;
        if(null != backgroundImage && !backgroundImage.isEmpty()){
            image = ImageIO.read(new File(backgroundImage));
            g2d = image.createGraphics();
            g2d = intializeGraphics(g2d);
            g2d.drawImage(image, 0, 0, null);
        }else{
            image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
            g2d = image.createGraphics();
            g2d = intializeGraphics(g2d);
            g2d.setColor(BACKGROUND_COLOR);
            g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        }
        for (String text : textList) {
            if(textList.get(0) == text){
                continue;
            }
            if (null != text && !text.isEmpty()) {
                if (textList.get(1) == text) {
                    Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
                    fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    Font headingFont = new Font(URDU_FONT_NAME, Font.PLAIN, fontSize).deriveFont(fontAttributes);
                    g2d.setColor(FONT_COLOR);
                    g2d.setFont(headingFont);
                    g2d.drawString(text, getXForCenteredString(g2d, headingFont, text, image), textList.indexOf(text) * (fontSize) + fontSize);
                } else {
                    Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
                    //fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    Font font = new Font(URDU_FONT_NAME, Font.PLAIN, fontSize).deriveFont(fontAttributes);
                    g2d.setColor(FONT_COLOR);
                    g2d.setFont(font);
                    if (getXForCenteredString(g2d, font, text, image) < 0) {
                        int newFontSize = fontSize - 10;
                        Font newFont = new Font(URDU_FONT_NAME, Font.PLAIN, newFontSize).deriveFont(fontAttributes);
                        g2d.setFont(newFont);
                        g2d.drawString(text, getXForCenteredString(g2d, newFont, text, image), textList.indexOf(text) * (fontSize + fontSize) );
                    } else {
                        g2d.drawString(text, getXForCenteredString(g2d, font, text, image), textList.indexOf(text) * (fontSize + fontSize));
                    }
                }

            }
        }
        if(isHeaderFooter){
            headerImagePrint(g2d);
            footerImagePrint(g2d,image.getWidth(), image.getHeight());
        }
        g2d.dispose();
        return image;
    }

    private void createCombinedImage(List<String> textList, String filename, int fontSize , int imageIndex) throws IOException {

        List<String> englist = textList.subList(0, textList.indexOf("%"));
        List<String> urdulist = textList.subList(textList.indexOf("%"), textList.size());
        BufferedImage imgLeft = createEnglishImage(englist, FONT_SIZE_ENGLISH, 900, CONSTANT_1080, false);

        BufferedImage imgRight = createUrduImage(urdulist, FONT_SIZE_URDU, 900, CONSTANT_1080, false);

        BufferedImage image;
        Graphics2D g2d;

        try {
            image = new BufferedImage(imgLeft.getWidth()*2, imgLeft.getHeight(), BufferedImage.TYPE_INT_ARGB);
            g2d = image.createGraphics();
            g2d = intializeGraphics(g2d);
            g2d.setColor(BACKGROUND_COLOR);
            g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
            g2d.drawImage(imgLeft, 0, 0, null);
            g2d.drawImage(imgRight, imgLeft.getWidth(), 0, null);

            headerImagePrint(g2d);
            footerImagePrint(g2d,image.getWidth(), image.getHeight());

            ImageIO.write(image, "png", new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Graphics2D intializeGraphics(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        return g2d;
    }

    private int getXForCenteredString(Graphics2D g, Font f, String s, BufferedImage img) {
        FontMetrics fm = g.getFontMetrics(f);
        int textWidth = fm.stringWidth(s);
        int lineWidth = img.getWidth();

        int x = (lineWidth - textWidth) / 2;
        return x;
    }

    private void headerImagePrint(Graphics2D g) {
        if (null != headerImage && !headerImage.isEmpty()) {
            try {
                BufferedImage imgTop = ImageIO.read(new File(headerImage));
                g.drawImage(imgTop, 20, 20, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void footerImagePrint(Graphics2D g, int iWidth, int iHeight) {
        if (null != footerImage && !footerImage.isEmpty()) {
            try {
                BufferedImage footImage = ImageIO.read(new File(footerImage));
                g.drawImage(footImage, iWidth-footImage.getWidth()-20, iHeight-footImage.getHeight()-20, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
