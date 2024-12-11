package org.example;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class frame extends JFrame implements ActionListener, ChangeListener {
    JButton button1;
    JButton button2;
    JButton button3;
    JButton button4;
    JLabel label;
    JLabel label1;
    Clip clip;
    JPanel panel;
    JPanel panel1;
    JSlider slider;
    FloatControl volumeControl;
    JProgressBar bar=new JProgressBar();
    frame() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        File file=new File("12. CIRCUS MAXIMUS - (Hiphopde.com).wav");
        AudioInputStream audioStream=AudioSystem.getAudioInputStream(file);
        clip=AudioSystem.getClip();
        clip.open(audioStream);

        volumeControl=(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        slider=new JSlider(0,100,60);
        slider.setFont(new Font("MV Boli",Font.PLAIN,16));
        slider.setPreferredSize(new Dimension(400,50));
        slider.setBackground(Color.orange);
        slider.addChangeListener(this);

        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(10);

        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(25);
        slider.setPaintLabels(true);

        bar.setStringPainted(true);
        bar.setPreferredSize(new Dimension(400,30));
        bar.setMinimum(0);
        bar.setMaximum((int) clip.getMicrosecondLength());
        bar.setForeground(Color.RED);
        bar.setBackground(Color.BLACK);
        bar.setFont(new Font("MV Boli",Font.BOLD,16));

        panel=new JPanel();
        panel.setBackground(Color.orange);
        panel1=new JPanel();
        panel.add(slider);
        panel1.add(bar);
        panel1.setBackground(Color.black);

        ImageIcon icon=new ImageIcon("360_F_705409131_qkbY7Kl3XyxOFhxm4TsDC7jHoJnVr6eD.jpg");

        label =new JLabel();
        label.setOpaque(true);
        label.setVisible(true);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
//        label.setBounds(0,0,23,45);
        label.setForeground(Color.red);
        label.setFont(new Font("Comic Sans",Font.BOLD,18));
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setBackground(new Color(12,42,54));
        label.setIcon(icon);
        label.setIconTextGap(-5);
        label.setVisible(false);

        label1 =new JLabel();
        label1.setVisible(true);


        button1=new JButton();
        button1.addActionListener(this);
        button1.setFocusable(false);
        button1.setText("play");
        button1.setLayout(new FlowLayout());

        button2=new JButton();
        button2.addActionListener(this);
        button2.setFocusable(false);
        button2.setText("Pause");
        button2.setLayout(new FlowLayout());

        button3=new JButton();
        button3.addActionListener(this);
        button3.setFocusable(false);
        button3.setText("restart");
        button3.setLayout(new FlowLayout());

        button4=new JButton("select song");
        button4.addActionListener(this);
        button4.setFocusable(false);
        button4.setLayout(new FlowLayout());

        panel.add(label1);
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        panel1.add(label);
        panel.setPreferredSize(new Dimension(100,100));
        panel1.setPreferredSize(new Dimension(100,100));

        volumePercent(slider.getValue());



        new JFrame();
        this.setVisible(true);
        this.setSize(1400,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.add(panel,BorderLayout.SOUTH);
        this.add(panel1,BorderLayout.CENTER);



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button1){
            clip.start();
            label.setVisible(true);
            updateProgressBar();
        }
        else if(e.getSource()==button2){
            clip.stop();
        }
        else if(e.getSource()==button3){
            clip.setMicrosecondPosition(0);
        }
        else if (e.getSource()==button4){
            JFileChooser chooser=new JFileChooser();
          int response=  chooser.showOpenDialog(null);
          if(response==JFileChooser.APPROVE_OPTION){
              File file=new File(chooser.getSelectedFile().getAbsolutePath());
              AudioInputStream audioStream= null;
              try {
                  audioStream = AudioSystem.getAudioInputStream(file);
              } catch (UnsupportedAudioFileException ex) {
                  throw new RuntimeException(ex);
              } catch (IOException ex) {
                  throw new RuntimeException(ex);
              }
              try {
                  clip=AudioSystem.getClip();
              } catch (LineUnavailableException ex) {
                  throw new RuntimeException(ex);
              }
              try {
                  clip.open(audioStream);
              } catch (LineUnavailableException ex) {
                  throw new RuntimeException(ex);
              } catch (IOException ex) {
                  throw new RuntimeException(ex);
              }
              volumeControl=(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
              volumePercent(slider.getValue());
          }
        }

    }

    @Override
    public void stateChanged(ChangeEvent e) {
//        label1.setText(slider.getValue()+"%");
//        float volume=slider.getValue();
//        volumeControl.setValue(volume);
//        label.setText(slider.getValue()+" dB");
        volumePercent(slider.getValue());
        label.setText(slider.getValue()+"%");
    }

    public void volumePercent(int percent){
        float minDb=-80.0f;
        float maxDb=6.0f;
        float volume=minDb + (maxDb-minDb)* (percent/100.0f);
        volumeControl.setValue(volume);
    }
    public void updateProgressBar(){
        new Thread(()->{
            while(clip.isRunning()){

                bar.setValue((int) clip.getMicrosecondPosition());

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
