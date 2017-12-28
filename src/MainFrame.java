import javax.swing.*;

class MainFrame extends JFrame
{
    private DrawingPanel panel = new DrawingPanel();
    private MainFrame()
    {
        super("ChaosGame by Rafal Bukowski");
        setLocation(100,100);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800,600);

        add(panel);
        setContentPane(panel);
        setVisible(true);
    }

    public static void main(String ... args)
    {
        new MainFrame();
    }

}
