package com.unipi.alexandris.game.echotrials.base.gui;

import com.unipi.alexandris.game.echotrials.base.Game;
import com.unipi.alexandris.game.echotrials.base.core.LevelID;
import com.unipi.alexandris.game.echotrials.base.loaders.BufferedImageLoader;
import com.unipi.alexandris.game.echotrials.base.core.UserFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.Exception;
import java.util.*;
import java.util.List;

//create CreateLoginForm class to create login form
//class extends JFrame to create a window where our component add
//class implements ActionListener to perform an action on button click
public class LoginForm extends JFrame implements ActionListener
{
    //initialize button, panel, label, and text field
    JButton b1;
    JPanel newPanel;
    JLabel userLabel;
    final JTextField  textField1;

    //calling constructor
    public LoginForm()
    {
        BufferedImageLoader loader = new BufferedImageLoader();
        Image background = loader.loadImage("/textures/ROCKS.png");
        Image icon = loader.loadImage("/textures/Terraformar.png");

        //create label for username
        userLabel = new JLabel(new ImageIcon(background));
        userLabel.setText("ENTER YOUR USERNAME");      //set label value for textField1
        userLabel.setHorizontalTextPosition(JLabel.CENTER);
        userLabel.setVerticalTextPosition(JLabel.CENTER);
        userLabel.setBackground(new Color(125, 90, 41, 0));
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont((new Font("VERDANA", Font.BOLD, 45)));

        //create text field to get username from the user
        textField1 = new JTextField(1);    //set length of the text
        textField1.setBackground(new Color(254, 184, 84));
        textField1.setHorizontalAlignment(JLabel.CENTER);
        textField1.setForeground(Color.BLACK);
        textField1.setFont((new Font("VERDANA", Font.BOLD, 45)));

        //create submit button
        b1 = new JButton(new ImageIcon(background)); //set label to button
        b1.setText("SUBMIT");
        b1.setBackground(new Color(125, 90, 41));
        b1.setHorizontalTextPosition(JLabel.CENTER);
        b1.setVerticalTextPosition(JLabel.CENTER);
        b1.setForeground(Color.WHITE);
        b1.setSelectedIcon(new ImageIcon(background));
        b1.setFont((new Font("VERDANA", Font.BOLD, 45)));

        //create panel to put form elements
        newPanel = new JPanel(new GridLayout(3, 1));
        newPanel.setBackground(new Color(125, 90, 41));
        newPanel.setForeground(Color.WHITE);
        newPanel.setFont((new Font("VERDANA", Font.BOLD, 45)));


        newPanel.add(userLabel);    //set username label to panel
        newPanel.add(textField1);   //set text field to panel
        newPanel.add(b1);           //set button to panel

        //set border to panel
        add(newPanel, BorderLayout.CENTER);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(Game.WIDTH/2, Game.HEIGHT/2));
        setMaximumSize(new Dimension(Game.WIDTH/2, Game.HEIGHT/2));
        setMinimumSize(new Dimension(Game.WIDTH/2, Game.HEIGHT/2));
        setLocationRelativeTo(null);
        setIconImage(icon);
        getRootPane().setDefaultButton(b1);
        setBackground(new Color(125, 90, 41));

        //perform action on button click
        b1.addActionListener(this);     //add action listener to button
        setTitle("TERRAFORMAR!");        //set title to the login form
    }

    //define abstract method actionPerformed() which will be called on button click
    public void actionPerformed(ActionEvent ae)     //pass action listener as a parameter
    {
        String userValue = textField1.getText();        //get user entered username from the textField1
        if(userValue.isEmpty()) return;

        //Load users
        ArrayList<UserFiles> users = new ArrayList<>();
        try
        {
            UserFiles userFiles;
            File folder = new File("saves");
            File[] listOfFiles = folder.listFiles();
            if(listOfFiles != null) {
                for (File listOfFile : listOfFiles) {
                    if (listOfFile.isFile()) {
                        System.out.println("File " + listOfFile.getName());

                        InputStream stream = new FileInputStream(listOfFile);

                        ObjectInputStream in = new ObjectInputStream(stream);
                        Object o = in.readObject();
                        System.out.println(o.getClass());

                        // Method for deserialization of object
                        userFiles = (UserFiles) o;

                        in.close();
                        stream.close();

                        System.out.println(userFiles.username() + "'s save data has been loaded.");
                        users.add(userFiles);
                    }
                }
            }
        }
        catch(IOException ex)
        {
            System.out.println(userValue + "'s deserialization has produced an exception.");
        }
        catch(ClassNotFoundException | NullPointerException ex)
        {
            System.out.println(userValue + "'s data was not found.");
        }

        boolean existingUser = false;
        for(UserFiles userFile : users) {
            if(Objects.equals(userValue, userFile.username())) {
                new  Game(userFile);
                this.setVisible(false);
                existingUser = true;
                break;
            }
        }
        //check whether the credentials are authentic or not
        if (!existingUser) {
            UserFiles userFiles = new UserFiles(UUID.randomUUID().toString(), userValue, new ArrayList<>(List.of(new String[]{LevelID.LEVEL_A_I.group})), new HashMap<>());

            String filename = "saves/"+userFiles.uuid()+".data";

            // Serialization
            try
            {
                //Saving of object in a file
                FileOutputStream file = new FileOutputStream(filename);
                ObjectOutputStream out = new ObjectOutputStream(file);

                // Method for serialization of object
                out.writeObject(userFiles);

                out.close();
                file.close();

                System.out.println(userFiles.username() + "' save has been created and serialized successfully!");
                actionPerformed(ae);
            }
            catch(Exception ex)
            {
                System.out.println("[SEVERE]: Creation or serialization of " + userFiles.username() + "' save data has failed!");
                System.exit(1);
            }

        }
    }
}
