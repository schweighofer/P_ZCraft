package at.pmzcraft.menu;

import at.pmzcraft.game.program.PMZCraftLauncher;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Menu{

    private static int countOfJFrames = 0;
    public String selectedLanguage;
    public static Boolean isHovered = false;
    public ImageIcon background = new ImageIcon("src/main/resources/menu/texture/Titelbild.jpeg");
    public ImageIcon btn1icon;
    public ImageIcon btn2icon;
    public ImageIcon btn3icon;


    public Menu(JFrame f, String language, Musician musikant) {
        countOfJFrames = countOfJFrames + 1;
        System.out.println(countOfJFrames);


        selectedLanguage = language;
        btn1icon = new ImageIcon("src/main/resources/menu/texture/play_"+selectedLanguage+".png");
        btn2icon = new ImageIcon("src/main/resources/menu/texture/setting_"+selectedLanguage+".png");
        btn3icon = new ImageIcon("src/main/resources/menu/texture/exit_"+selectedLanguage+".png");

        JButton btnplay = new JButton(btn1icon);
        btnplay.setBounds(485,285,520,120);
        JButton btnsettings = new JButton(btn2icon);
        btnsettings.setBounds(485,475,520,120);
        JButton btnexit = new JButton(btn3icon);
        btnexit.setBounds(485,630,520,96);

        btnplay.addActionListener(ae -> {
                System.out.println("play");
                PMZCraftLauncher.startGameInstance();
                f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));

        });
        btnexit.addActionListener(ae -> {
                System.out.println("exit");
                System.exit(0);
        });

        btnsettings.addActionListener(ae -> {

                btnplay.setIcon(new ImageIcon("src/main/resources/menu/texture/L_"+selectedLanguage+"_1.png"));
                btnplay.removeActionListener(btnplay.getActionListeners()[0]);
                btnplay.addActionListener(be -> {
                        if (selectedLanguage.equals("GR")){
                            selectedLanguage = "EN";
                            System.out.println(selectedLanguage);
                        }
                        else if (selectedLanguage.equals("SR")){
                            selectedLanguage = "EN";
                            System.out.println(selectedLanguage);
                        }
                        else if (selectedLanguage.equals("EN")){
                            selectedLanguage = "GR";
                            System.out.println(selectedLanguage);
                        }

                        f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                        JFrame frame = new JFrame("P*ZCraft");
                        new Menu(frame,selectedLanguage, musikant);

                });
                btnsettings.setIcon(new ImageIcon("src/main/resources/menu/texture/L_"+selectedLanguage+"_2.png"));
                btnsettings.removeActionListener(btnexit.getActionListeners()[0]);
                btnsettings.addActionListener(be -> {
                        if (selectedLanguage.equals("GR")){
                            selectedLanguage = "SR";
                            System.out.println(selectedLanguage);
                        }
                        else if (selectedLanguage.equals("SR")){
                            selectedLanguage = "GR";
                            System.out.println(selectedLanguage);
                        }
                        else if (selectedLanguage.equals("EN")){
                            selectedLanguage = "SR";
                            System.out.println(selectedLanguage);
                        }
                        f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                        JFrame frame = new JFrame("P*ZCraft");
                        new Menu(frame,selectedLanguage, musikant);

                });
                btnexit.setIcon(new ImageIcon("src/main/resources/menu/texture/zruck_"+selectedLanguage+".png"));
                btnexit.removeActionListener(btnexit.getActionListeners()[0]);
                btnexit.addActionListener(be -> {
                        f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                        JFrame frame = new JFrame("P*ZCraft");
                        new Menu(frame,selectedLanguage, musikant);
                });

                JLabel volumeLabel = new JLabel();
                volumeLabel.setBounds(40,250,40,20);
                f.add(volumeLabel);
                volumeLabel.setVisible(true);

                JSlider volumeSlider = new JSlider(0, 100, 50);
                volumeSlider.setMaximum(6);
                volumeSlider.setMinimum(-80);
                volumeSlider.setValue(-37);
                volumeSlider.setBounds(40,285,400,120);
                f.add(volumeSlider);
                volumeSlider.addChangeListener(ce -> {
                        musikant.changeVolume(volumeSlider.getValue());
                });

                String buttonTxt = switch (selectedLanguage) {
                    case "EN" -> "S A V E";
                    case "GR" -> "SICHERUNG";
                    case "SR" -> "SKLADISTE";
                    default -> "S A V E";
                };
                JButton saveButton = new JButton(buttonTxt);
                saveButton.setFont(new Font("Arial", Font.PLAIN, 36));
                saveButton.setBounds(485,830,520 / 2,96);

                saveButton.addActionListener(be -> {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Specify a file to save");

                    int userSelection = fileChooser.showSaveDialog(f);

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        String filePath = fileToSave.getAbsolutePath();
                        if (!filePath.contains(".pmzmenuconfig")) {
                            filePath += ".pmzmenuconfig";
                        }
                        try {
                            Files.writeString(Path.of(filePath), selectedLanguage);
                        } catch (IOException e) {
                            System.out.println("Couldn't save config!!!");
                        }
                    }
                });

                f.add(saveButton);

            buttonTxt = switch (selectedLanguage) {
                case "EN" -> "L O A D";
                case "GR" -> "LADEN";
                case "SR" -> "OPTERECENJE";
                default -> "S A V E";
            };

            JButton loadButton = new JButton(buttonTxt);

            loadButton.setBounds(485 + 520 / 2,830,520 / 2,96);
            loadButton.setFont(new Font("Arial", Font.PLAIN, 36));

            loadButton.addActionListener(be -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath =  selectedFile.getAbsolutePath();

                    try {
                        selectedLanguage = Files.readAllLines(Path.of(filePath)).get(0);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                    JFrame frame = new JFrame("P*ZCraft");
                    new Menu(frame,selectedLanguage, musikant);
                }
            });

            f.add(loadButton);

                //todo: Eingaben fixen. zbs. die nächsten 2 Componente

                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());

                JLabel nameLabel = new JLabel("your name: ");
                //nameLabel.setBounds(1200,285,100,120);
                panel.add(nameLabel);
                //todo: xml datei to save settings
                nameLabel.setBackground(Color.lightGray);

                JTextField nameInput = new JTextField();
                //nameInput.setBounds(1320,285,300,120);
                nameInput.setBackground(Color.lightGray);
                panel.add(nameInput);

                panel.revalidate();
                panel.repaint();
                panel.setVisible(true);
                f.add(panel);
                


                //todo: settings erweitern
                //-> schieb bar für volume
                //-> namen eingabe
                //-> ?how many chunks
                //-> ?easy or impossible
                //-> ?farbenblindheit (wenn ja einen random filder über die textur legen)
                //-> ...
                System.out.println("settings");
        });

        Border emptyBorder = BorderFactory.createEmptyBorder();
        btnplay.setBorder(emptyBorder);
        btnsettings.setBorder(emptyBorder);
        btnexit.setBorder(emptyBorder);

        f.add(new JLabel(background));
        f.pack();
        f.setResizable(false);
        f.add(btnplay);
        f.add(btnsettings);
        f.add(btnexit);
        f.setLayout(null);
        f.setVisible(true);

        new Thread(new MouseMover()).start();
    }
}
