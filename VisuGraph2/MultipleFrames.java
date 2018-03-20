import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class MultipleFrames {
        JDesktopPane desk;
        JInternalFrame frame1, frame2, frame3, frame4;
        JFrame frame;

        public static void main(String[] args) {
               JFrame myFrame = new JFrame("Sample");
               JInternalFrame internalFrame1 = new JInternalFrame();
               JInternalFrame internalFrame2 = new JInternalFrame();
               myFrame.setLayout(new BorderLayout());
               JPanel panel1 = new JPanel();
               JPanel panel2 = new JPanel();
               internalFrame1.add(panel1);
               internalFrame2.add(panel2);
               myFrame.add(internalFrame1, BorderLayout.LINE_START);
               myFrame.add(internalFrame2, BorderLayout.LINE_START);
               myFrame.setVisible(true);
        }

        public MultipleFrames() {
                frame = new JFrame("Multiple Frames");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                desk = new JDesktopPane();
                frame1 = new JInternalFrame("Frame1", true, true, true, true);
                frame1.setBounds(20, 200, 150, 100);
                frame1.setVisible(true);
                frame2 = new JInternalFrame("Frame2", true, true, true, true);
                frame2.setBounds(20, 140, 150, 100);
                frame2.setVisible(true);
                frame3 = new JInternalFrame("Frame3", true, true, true, true);
                frame3.setBounds(20, 80, 150, 100);
                frame3.setVisible(true);
                frame4 = new JInternalFrame("Frame4", true, true, true, true);
                frame4.setBounds(20, 20, 150, 100);
                frame4.setVisible(true);
                desk.add(frame1);
                desk.add(frame2);
                desk.add(frame3);
                desk.add(frame4);
                frame.add(desk);
                frame.setSize(400, 400);
                frame.setVisible(true);
        }
}