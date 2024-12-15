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

/**
 * The LoginForm class creates and manages the game's login interface.
 * This form allows users to enter their username and either load an existing save file
 * or create a new user profile. The form features a stylized interface with custom
 * backgrounds, fonts, and colors to match the game's aesthetic.
 * 
 * Features:
 * <ul>
 *   <li>Username input field with custom styling</li>
 *   <li>Automatic save file loading for existing users</li>
 *   <li>New user profile creation with initial game progress</li>
 *   <li>Persistent data storage using serialization</li>
 *   <li>Custom themed UI elements matching game style</li>
 * </ul>
 */
public class LoginForm extends JFrame implements ActionListener
{
    /** Submit button for the login form. */
    JButton b1;
    
    /** Panel containing all form elements. */
    JPanel newPanel;
    
    /** Label displaying the username input prompt. */
    JLabel userLabel;
    
    /** Text field for username input. */
    final JTextField textField1;

    /**
     * Constructs a new LoginForm with custom styling and layout.
     * Sets up the form's visual elements, including background images,
     * custom fonts, and color scheme.
     */
    public LoginForm()
    {
        BufferedImageLoader loader = new BufferedImageLoader();
        Image background = loader.loadImage("/textures/ROCKS.png");
        Image icon = loader.loadImage("/textures/Terraformar.png");

        // Create and style username label
        userLabel = new JLabel(new ImageIcon(background));
        userLabel.setText("ENTER YOUR USERNAME");
        userLabel.setHorizontalTextPosition(JLabel.CENTER);
        userLabel.setVerticalTextPosition(JLabel.CENTER);
        userLabel.setBackground(new Color(125, 90, 41, 0));
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont((new Font("VERDANA", Font.BOLD, 45)));

        // Create and style username text field
        textField1 = new JTextField(1);
        textField1.setBackground(new Color(254, 184, 84));
        textField1.setHorizontalAlignment(JLabel.CENTER);
        textField1.setForeground(Color.BLACK);
        textField1.setFont((new Font("VERDANA", Font.BOLD, 45)));

        // Create and style submit button
        b1 = new JButton(new ImageIcon(background));
        b1.setText("SUBMIT");
        b1.setBackground(new Color(125, 90, 41));
        b1.setHorizontalTextPosition(JLabel.CENTER);
        b1.setVerticalTextPosition(JLabel.CENTER);
        b1.setForeground(Color.WHITE);
        b1.setSelectedIcon(new ImageIcon(background));
        b1.setFont((new Font("VERDANA", Font.BOLD, 45)));

        // Create and style form panel
        newPanel = new JPanel(new GridLayout(3, 1));
        newPanel.setBackground(new Color(125, 90, 41));
        newPanel.setForeground(Color.WHITE);
        newPanel.setFont((new Font("VERDANA", Font.BOLD, 45)));

        // Add components to panel
        newPanel.add(userLabel);
        newPanel.add(textField1);
        newPanel.add(b1);

        // Configure window properties
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

        b1.addActionListener(this);
        setTitle("TERRAFORMAR!");
    }

    /**
     * Handles form submission when the submit button is clicked.
     * This method:
     * 1. Retrieves the entered username
     * 2. Loads existing user save files
     * 3. Either loads an existing user's game or creates a new user profile
     * 4. Handles serialization and deserialization of user data
     *
     * @param ae The ActionEvent triggered by the submit button
     */
    public void actionPerformed(ActionEvent ae)
    {
        String userValue = textField1.getText();
        if(userValue.isEmpty()) return;

        // Load existing users
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

        // Check for existing user or create new one
        boolean existingUser = false;
        for(UserFiles userFile : users) {
            if(Objects.equals(userValue, userFile.username())) {
                new Game(userFile);
                this.setVisible(false);
                existingUser = true;
                break;
            }
        }

        // Create new user if not found
        if (!existingUser) {
            UserFiles userFiles = new UserFiles(
                UUID.randomUUID().toString(),
                userValue,
                new ArrayList<>(List.of(new String[]{LevelID.LEVEL_A_I.group})),
                new HashMap<>()
            );

            String filename = "saves/"+userFiles.uuid()+".data";

            // Serialize new user data
            try
            {
                FileOutputStream file = new FileOutputStream(filename);
                ObjectOutputStream out = new ObjectOutputStream(file);
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
