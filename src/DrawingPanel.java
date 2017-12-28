import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;


class DrawingPanel extends JPanel implements ActionListener, MouseListener
{

    private JButton reset_button = new JButton("RESET");
    private JLabel iterations_desc_label = new JLabel("Ilosc iteracji:");
    private JTextField iterations_textField = new JTextField("100",4);
    private JLabel iterations_count_label = new JLabel("Ilosc punktow = 0");
    private JTextArea log_textArea = new JTextArea(5,30);
    private JScrollPane scrollPane = new JScrollPane(log_textArea);
    private JButton active_points = new JButton("Wyświetl punkty");
    private JButton readme = new JButton("Instrukcje");
    private JButton next_iteration_button = new JButton("Losuj dalej");

    private ArrayList<Point> points = new ArrayList<>();
    private ArrayList<Point> dots = new ArrayList<>();
    private int iterations;
    private Random randomGenerator;

    DrawingPanel()
    {
        setSize(800,600);
        setLayout(null);
        iterations = 0;

        add(reset_button);
        add(iterations_desc_label);
        add(iterations_textField);
        add(iterations_count_label);
        add(scrollPane);
        add(active_points);
        add(readme);
        add(next_iteration_button);

        iterations_desc_label.setBounds(70,470,75,22);
        iterations_textField.setBounds(150,470,50,25);
        iterations_count_label.setBounds(305,465,50,22);
        reset_button.setBounds(275,500,75,50);
        scrollPane.setBounds(400,400,395,190);
        active_points.setBounds(250,420,130,30);
        readme.setBounds(90,420,100,30);
        next_iteration_button.setBounds(60,500,155,25);


        addMouseListener(this);
        log_textArea.setEditable(false);
        reset_button.addActionListener(this);
        active_points.addActionListener(this);
        readme.addActionListener(this);
        next_iteration_button.addActionListener(this);

        randomGenerator = new Random();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();

        if (source == reset_button)
        {
            log_textArea.append("\nZRESETOWANO STAN\n");
            iterations = 0;
            setIterations(iterations);
            dots.clear();
            points.clear();
            repaint();
        }
        if (source == readme) JOptionPane.showMessageDialog(this,message,"Instrukcje",JOptionPane.INFORMATION_MESSAGE);
        if (source == active_points)
        {
            String str = "Lista aktywnych punktów:\n";
            if (points.isEmpty()) str += "Brak punktów do wyświetlenia\n";
            else
            {
                for (Point point : points) str += "X = " + point.getX() + ", Y = " + point.getY() + "\n";
                JOptionPane.showMessageDialog(this,str,"Aktywne Punkty",JOptionPane.INFORMATION_MESSAGE);
            }
        }
        if (source == next_iteration_button)
        {
            if (!points.isEmpty()) {
                for (int x = Integer.parseInt(iterations_textField.getText()); x >= 0; x--)
                    random_move_towards_another_point();
                repaint();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        points.add(new Point(e.getX(),e.getY()));
        log_textArea.append("Dodano punkt o współrzędnych x = " + e.getX() + ", y = " + e.getY() + "\n");
        iterations++;
        setIterations(iterations);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        for(Point p : points)
        {
            g2d.setColor(Color.blue);
            g2d.fillOval(p.x-5,p.y-5,10,10);
        }
        for (Point d : dots)
        {
            g2d.setColor(Color.black);
            g2d.fillOval(d.x-1,d.y-1,2,2);
        }
    }

    private void random_move_towards_another_point()
    {
        int new_index = (randomGenerator.nextInt(points.size()));
        if (dots.isEmpty()) dots.add(points.get(0));
        dots.add(new Point(
                (int)((dots.get(dots.size()-1).getX() + points.get(new_index).getX()) / 2),
                (int)((dots.get(dots.size()-1).getY() + points.get(new_index).getY()) / 2)));
        log_textArea.append("Kropka: X = " + dots.get(dots.size()-1).getX() + ", Y = "
                + dots.get(dots.size()-1).getY() + "\n");
    }

    private void setIterations(int new_count)
    {
        iterations_count_label.setText("i = " + Integer.toString(new_count));
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    private final String message = "CHAOS GAME\n\n" +
            "Symulator gry w chaos\n" +
            "Instrukcje:\n" +
            "1. Wybierz dowolne punkty na ekranie, zostaną one zapisane do pamięci\n" +
            "   i na nich będzie się opierać w większości symulacja.\n" +
            "2. Wybierz ilość iteracji (ilość kroków symulacji, im więcej kroków tym więcej czasu\n" +
            "   i zasobów maszyny potrzeba na wykonanie zadania ale tym lepszy efekt\n" +
            "3. Reset przywraca wartości początkowe i kasuje efekty symulacji\n" +
            "4. W prawym dolnym rogu znajduje się log programu\n" +
            "\nIlość punktów dowolna, ciekawe ilości to 3 lub 6\n"+
            "\nCopyright \u00a9 Rafał Bukowski 2017";
}
