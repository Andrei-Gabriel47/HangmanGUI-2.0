package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;




public class CustomTools
{
    public static final Dimension FRAME_SIZE = new Dimension(540, 760);
    public static final Dimension BUTTON_PANEL_SIZE = new Dimension(FRAME_SIZE.width, (int)(FRAME_SIZE.height * 0.42));
    public static final Dimension RESULT_DIALOG_SIZE = new Dimension((int)(FRAME_SIZE.width/2), (int)(FRAME_SIZE.height/6));
    public static JLabel loadImage(String resource)
    {

        // Calea către resursă
        String imagePath = resource;

        // Încarcă imaginea
        ImageIcon icon = createImageIcon(imagePath);

        // Creează un JLabel cu imaginea încărcată
        JLabel label = new JLabel(icon);
        return label;

    }
    public static void updateImage(JLabel imageContainer, String resource){
        String imagePath = resource;

        // Încarcă imaginea
        ImageIcon icon = createImageIcon(imagePath);

        imageContainer.setIcon(new ImageIcon(icon.getImage()));

    }

    // Funcție pentru încărcarea unei imagini din resurse
    protected static ImageIcon createImageIcon(String path) {
        // Obține clasa curentă pentru a accesa resursele
        ClassLoader classLoader = CustomTools.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(path)) {
            if (inputStream != null) {
                // Încarcă imaginea din InputStream
                BufferedImage image = ImageIO.read(inputStream);
                return new ImageIcon(image);
            } else {
                System.err.println("Couldn't find file: " + path);
                return null;
            }
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
            return null;
        }
    }
    public static String hideWords(String word){
        String hiddenWord = "";
        for(int i = 0; i < word.length(); i++){
            if(!(word.charAt(i) == ' ')){
                hiddenWord += "*";
            }else{
                hiddenWord += " ";
            }
        }
        return hiddenWord;
    }

}
