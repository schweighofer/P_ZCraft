package at.pmzcraft.menu;

import at.pmzcraft.game.program.PMZCraftLauncher;
import at.pmzcraft.menu.MouseMover;
import at.pmzcraft.menu.Musician;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class Menu extends CardLayout {

    private static int countOfJFrames = 0;
    public String selectedLanguage;
    public static Boolean isHovered = false;
    public ImageIcon background = new ImageIcon("src/main/resources/menu/Titelbild.jpeg");
    public ImageIcon btn1icon;
    public ImageIcon btn2icon;
    public ImageIcon btn3icon;


    public Menu(JFrame f, String language, Musician musikant) {
        countOfJFrames = countOfJFrames + 1;
        System.out.println(countOfJFrames);

        selectedLanguage = language;
        btn1icon = new ImageIcon("src/main/resources/menu/play_"+selectedLanguage+".png");
        btn2icon = new ImageIcon("src/main/resources/menu/setting_"+selectedLanguage+".png");
        btn3icon = new ImageIcon("src/main/resources/menu/exit_"+selectedLanguage+".png");

        JButton btnplay = new JButton(btn1icon);
        btnplay.setBounds(485,285,520,120);
        JButton btnsettings = new JButton(btn2icon);
        btnsettings.setBounds(485,475,520,120);
        JButton btnexit = new JButton(btn3icon);
        btnexit.setBounds(485,630,520,96);

        btnplay.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("play");
                //todo: spiel verknüpfen
                PMZCraftLauncher.startGameInstance();
                f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
            }
        });
        btnexit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.out.println("exit");
                System.exit(0);
            }
        });

        btnsettings.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                btnplay.setIcon(new ImageIcon("src/main/resources/menu/L_"+selectedLanguage+"_1.png"));
                btnplay.removeActionListener(btnplay.getActionListeners()[0]);
                btnplay.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
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
                        JFrame f = new JFrame("P*ZCraft");
                        CardLayout layout = new Menu(f,selectedLanguage, musikant);
                    }
                });
                btnsettings.setIcon(new ImageIcon("src/main/resources/menu/L_"+selectedLanguage+"_2.png"));
                btnsettings.removeActionListener(btnexit.getActionListeners()[0]);
                btnsettings.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
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
                        JFrame f = new JFrame("P*ZCraft");
                        CardLayout layout = new Menu(f,selectedLanguage, musikant);
                    }
                });
                btnexit.setIcon(new ImageIcon("src/main/resources/menu/zruck_"+selectedLanguage+".png"));
                btnexit.removeActionListener(btnexit.getActionListeners()[0]);
                btnexit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                        JFrame f = new JFrame("P*ZCraft");
                        CardLayout layout = new Menu(f,selectedLanguage, musikant);
                    }
                });

                JSlider volumeSlider = new JSlider(0, 100, 50);
                volumeSlider.setMaximum(6);
                volumeSlider.setMinimum(-80);
                volumeSlider.setValue(-37);
                volumeSlider.setBounds(40,285,400,120);
                f.add(volumeSlider);
                volumeSlider.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        musikant.changeVolume(volumeSlider.getValue());
                    }
                });

                //todo: die nächsten 2 wichser erscheinen einfach nicht
                JLabel nameLabel = new JLabel();
                nameLabel.setText("your name: ");//todo: csv datei oder so um settings zu speichern und csv datei um text zu jeweiliger Sprache zu speichern
                nameLabel.setBounds(1200,285,100,120);
                nameLabel.setBackground(Color.lightGray);
                f.add(nameLabel);

                JEditorPane nameInput = new JEditorPane();
                nameInput.setBounds(1320,285,300,120);
                nameInput.setBackground(Color.lightGray);
                f.add(nameInput);



                //todo: settings erweitern
                //-> schieb bar für volume
                //-> namen eingabe
                //-> ?how many chunks
                //-> ?easy or impossible
                //-> ?farbenblindheit (wenn ja einen random filder über die textur legen)
                //-> ...
                System.out.println("settings");
            }
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
