package org.cg.autoklick;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

/**
 * A refactoring of https://gist.github.com/TabsPH/4133843
 * in response to stack overflow question
 * https://stackoverflow.com/questions/58258396/auto-clicker-thread-wont-stop
 *
 * */

public class AutoClickFrame extends JFrame implements ActionListener {

   private static final long serialVersionUID = 1L;
    private final AutoClicker autoClicker;
    private JTextField txtN;
    private JTextField txtInterval;
    private JProgressBar progressBar;
    private JButton btnStart;
    private JButton btnStop;


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AutoClickFrame frame = new AutoClickFrame();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private AutoClickFrame() {
        setUndecorated(false);
        setTitle("Auto Clicker OT-CSC");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 294, 139);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "Settings", TitledBorder.LEADING,
                TitledBorder.TOP, null, null));
        panel.setBounds(10, 11, 168, 86);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblNumberOfClicks = new JLabel("Number of Click(s)");
        lblNumberOfClicks.setBounds(21, 21, 94, 20);
        panel.add(lblNumberOfClicks);

        JLabel lblInterval = new JLabel("Interval");
        lblInterval.setBounds(21, 52, 94, 19);
        panel.add(lblInterval);

        txtN = new JTextField();
        txtN.setText("10");
        txtN.setBounds(119, 21, 33, 20);
        panel.add(txtN);
        txtN.setColumns(10);

        txtInterval = new JTextField();
        txtInterval.setText("3000");
        txtInterval.setBounds(119, 51, 33, 20);
        panel.add(txtInterval);

        btnStart = new JButton("Start");
        btnStart.addActionListener(this);
        btnStart.setActionCommand("Start");
        btnStart.setBounds(188, 16, 89, 23);
        contentPane.add(btnStart);

        btnStop = new JButton("Stop");
        btnStop.addActionListener(this);
        btnStop.setActionCommand("Stop");
        btnStop.setBounds(187, 50, 90, 19);
        contentPane.add(btnStop);

        progressBar = new JProgressBar();
        progressBar.setString("Keep on Clicking");
        progressBar.setStringPainted(true);
        progressBar.setBounds(187, 77, 90, 19);

        contentPane.add(progressBar);

        JLabel lblByTabs = new JLabel("");
        lblByTabs.setForeground(Color.GRAY);
        lblByTabs.setFont(new Font("Tahoma", Font.PLAIN, 8));
        lblByTabs.setHorizontalAlignment(SwingConstants.TRAILING);
        lblByTabs.setBounds(188, 83, 89, 14);
        contentPane.add(lblByTabs);

        autoClicker = new AutoClicker();
        new Thread(autoClicker).start();
    }

    private void setAutoKlick(final boolean autoKlick) {
        SwingUtilities.invokeLater(() -> {
            Optional<Integer> maybeInterval = getInterval();
            if (autoKlick && maybeInterval.isPresent()) {
                autoClicker.setInterval(maybeInterval.get());
            }
            progressBar.setVisible(autoKlick);
            progressBar.setIndeterminate(autoKlick);
            btnStop.setEnabled(autoKlick);
            btnStart.setEnabled(!autoKlick);
            txtInterval.setEditable(!autoKlick);
            txtN.setEditable(!autoKlick);
            autoClicker.setShouldKlick(autoKlick);
        });
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("event: " + evt.getActionCommand());
        if (evt.getActionCommand().equals("Start")) {
            setAutoKlick(true);
        }
        if (evt.getActionCommand().equals("Stop")) {
            setAutoKlick(false);
        }
    }

    private Optional<Integer> getInterval() {
        try {
            return Optional.of(Integer.parseInt("0" + txtInterval.getText().trim()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid Input!", "Error", JOptionPane.ERROR_MESSAGE);
            return Optional.empty();
        }
    }
}