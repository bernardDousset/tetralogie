import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import java.awt.Dimension;
import java.awt.EventQueue;

public class Example extends JFrame {

    public static final String INIT_TITLE = "TestFrame v0.02";
    public static Example window;
    private JSplitPane left;
    private JTabbedPane center;
    private JSplitPane right;

    public Example(String windowName) {
        super(windowName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        left = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            new JButton("button1"), new JButton("button2"));
        left.setResizeWeight(0.5);

        right = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            new JButton("button3"), new JButton("button4"));
        right.setResizeWeight(0.5);

        center = new JTabbedPane();
        center.addTab("Panel1", new MyPanel());
        center.addTab("Panel2", new MyPanel());

        this.add(left, BorderLayout.WEST);
        this.add(center, BorderLayout.CENTER);
        this.add(right, BorderLayout.EAST);
        this.pack();
        this.setLocationByPlatform(true);
        this.setVisible(true);
    }

    private static class MyPanel extends JPanel {

        private Dimension d = new Dimension(320, 240);

        @Override
        public Dimension getPreferredSize() {
            return d;
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                window = new Example(INIT_TITLE);
                window.setVisible(true);
            }
        });
    }
    
    
}