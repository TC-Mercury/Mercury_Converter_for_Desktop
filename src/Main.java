import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JLabel lblDosyaKonumunuAc;
    private JProgressBar progressBar;
    private JButton btnOpenFolder;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Main frame = new Main();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public Main() {
        setTitle("Mercury Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 464, 389);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblWelcome = new JLabel("Welcome To Mercury Converter");
        lblWelcome.setFont(new Font("Verdana Pro Cond Semibold", Font.PLAIN, 13));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setBounds(118, 10, 208, 35);
        contentPane.add(lblWelcome);

        textField = new JTextField();
        textField.setBounds(118, 100, 208, 19);
        contentPane.add(textField);
        textField.setColumns(10);

        JLabel lblYoutubeLink = new JLabel("Please insert a valid YouTube video URL:");
        lblYoutubeLink.setHorizontalAlignment(SwingConstants.CENTER);
        lblYoutubeLink.setFont(new Font("Verdana Pro Cond Semibold", Font.PLAIN, 13));
        lblYoutubeLink.setBounds(103, 55, 239, 35);
        contentPane.add(lblYoutubeLink);

        JLabel lblOperationDone = new JLabel("Completed");
        lblOperationDone.setForeground(new Color(0, 255, 0));
        lblOperationDone.setHorizontalAlignment(SwingConstants.CENTER);
        lblOperationDone.setFont(new Font("Verdana Pro Cond Semibold", Font.PLAIN, 13));
        lblOperationDone.setBounds(118, 202, 208, 35);
        lblOperationDone.setVisible(false);
        contentPane.add(lblOperationDone);

        lblDosyaKonumunuAc = new JLabel("Click to open the file:");
        lblDosyaKonumunuAc.setHorizontalAlignment(SwingConstants.RIGHT);
        lblDosyaKonumunuAc.setFont(new Font("Verdana Pro Cond Semibold", Font.PLAIN, 10));
        lblDosyaKonumunuAc.setBounds(95, 247, 123, 35);
        lblDosyaKonumunuAc.setVisible(false);
        contentPane.add(lblDosyaKonumunuAc);

        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setBounds(118, 178, 208, 14);
        progressBar.setVisible(false);
        contentPane.add(progressBar);

        btnOpenFolder = new JButton("File");
        btnOpenFolder.setFont(new Font("Tahoma", Font.BOLD, 10));
        btnOpenFolder.setVisible(false);
        btnOpenFolder.setBounds(228, 247, 85, 35);
        contentPane.add(btnOpenFolder);

        // Create the file MercuryFile
        String userHome = System.getProperty("user.home");
        File mercuryDir = new File(userHome + "\\Downloads\\MercuryFile");
        if (!mercuryDir.exists()) {
            mercuryDir.mkdir();
        }

        btnOpenFolder.addActionListener(e -> {
            try {
                Desktop desktop = Desktop.getDesktop();
                if (mercuryDir.exists() && mercuryDir.isDirectory()) {
                    desktop.open(mercuryDir);
                } else {
                    JOptionPane.showMessageDialog(Main.this, "File could not be found!");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(Main.this, "File could not be opened!");
            }
        });

        JButton btnConvertDownload = new JButton("Convert and Install");
        btnConvertDownload.setBounds(147, 129, 146, 35);
        contentPane.add(btnConvertDownload);

        btnConvertDownload.addActionListener(e -> {
            String link = textField.getText().trim();
            if (link.isEmpty()) {
                JOptionPane.showMessageDialog(Main.this, "Please insert a valid YouTube video URL!");
                return;
            }

            String outputTemplate = mercuryDir.getAbsolutePath() + "\\%(title)s.%(ext)s";

            // Execute the process with a new thread (To prevent UI freezing)
            new Thread(() -> {
                try {
                    SwingUtilities.invokeLater(() -> {
                        progressBar.setVisible(true);
                        lblOperationDone.setVisible(false);
                        lblDosyaKonumunuAc.setVisible(false);
                        btnOpenFolder.setVisible(false);
                    });

                    ProcessBuilder pb = new ProcessBuilder(
                            userHome + "\\Downloads\\yt-dlp.exe",
                            "-x", "--audio-format", "mp3",
                            "-o", outputTemplate,
                            link
                    );

                    pb.redirectErrorStream(true);

                    Process process = pb.start();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }

                    int exitCode = process.waitFor();

                    SwingUtilities.invokeLater(() -> {
                        progressBar.setVisible(false);
                        if (exitCode == 0) {
                            lblOperationDone.setVisible(true);
                            lblDosyaKonumunuAc.setVisible(true);
                            btnOpenFolder.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(Main.this, "Proccess has failed!");
                        }
                    });
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        progressBar.setVisible(false);
                        JOptionPane.showMessageDialog(Main.this, "An error has occured: " + ex.getMessage());
                    });
                }
            }).start();
        });

    }
}
