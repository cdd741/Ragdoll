import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JPanel {
    private static Torso torso;
    private static JMenuItem reset;
    private static JMenuItem quit;
    private static JFrame frame;
    public static void main(String[] args) {
        // Menu Bar
        reset = new JMenuItem("Reset (Ctrl-R)");
        reset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
//        reset.addActionListener(this);

        quit = new JMenuItem("Quit (Ctrl-Q)");
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));

        registerControllers();
        JMenuBar jmb = new JMenuBar();
        JMenu file = new JMenu("File");
        jmb.add(file);
        file.add(reset);
        file.addSeparator();
        file.add(quit);

        // Frame
        frame = new JFrame("Ragdoll");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 900);
        frame.setMinimumSize(new Dimension(1600, 900));
        frame.setVisible(true);
        frame.setJMenuBar(jmb);
        frame.setContentPane(new Main()); // add canvas to jframe

        frame.pack();
    }

    Main(){
        setOpaque(true);
        setBackground(Color.WHITE);
        setFocusable(true);
        torso = new Torso(700,250,150,300);

        // Event Register
        var listener = new MyMouseListener();
        this.addMouseMotionListener(listener);
        this.addMouseListener(listener);
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        torso.paint(g2);
    }

    private class MyMouseListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e){
            super.mouseClicked(e);

        }

        public void mousePressed(MouseEvent e){
            super.mousePressed(e);
            torso.collide(new Point(e.getX(), e.getY()));
        }

        public void mouseDragged(MouseEvent e){
            super.mouseDragged(e);
            torso.move(e.getX(), e.getY());
            repaint();

        }

        public void mouseReleased(MouseEvent e){
            super.mousePressed(e);
            torso.move(e.getX(), e.getY());
            torso.reset();
            repaint();
        }
    }

    private static void registerControllers(){
        reset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                torso = new Torso(700,250,150,300);
                frame.repaint();
            }
        });

        quit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
    }
}