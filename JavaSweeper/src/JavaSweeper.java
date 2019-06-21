import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;


public class JavaSweeper extends JFrame
{
    private Game game;
    private JPanel panel;
    private JLabel label;

    private final int COLS = 9; // столбцы
    private final int ROWS = 9; // строки
    private final int BOMBS = 10; // количество бомб
    private final int IMAGE_SIZE = 50; // размер картинки одинаковый по x и по y


    public static void main(String[] args)
    {
        new JavaSweeper();
    }

    private JavaSweeper ()
    {
        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }

    private void initLabel ()
    {
        label = new JLabel("hi");
        add (label, BorderLayout.SOUTH);
    }

    private void initPanel (){
        panel = new JPanel() // при инициализации выводим картинки
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords())
                    //g.drawImage((Image) game.getBox(coord).image, coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this); //приведение типа к Image
                    g.drawImage((Image) game.getBox(coord).image, coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, IMAGE_SIZE, IMAGE_SIZE, this); //приведение типа к Image

            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x, y);
                if (e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton (coord);
                if (e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightButton (coord);
                if (e.getButton() == MouseEvent.BUTTON2)
                    game.start ();
                label.setText(getessage ());
                panel.repaint();
            }
        });

        panel.setPreferredSize(new Dimension(Ranges.getSize().x * IMAGE_SIZE, Ranges.getSize().y * IMAGE_SIZE));
        add (panel);
    }

    private String getessage()
    {
        switch (game.getState())
        {
            case PLAYED: return "mouse2 to restart";
            case BOMBED:return "Lose! SUKA TEChies v komande";
            case WINNER: return "Win! SOLA NA AME V FINALE!!";
            default: return "Welcome";
        }
    }

    private void initFrame ()
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("ATO");
        setResizable(false);
        setVisible(true);
        pack();
        setIconImage(getImage("icon"));
        setLocationRelativeTo(null);

    }

    private void setImages ()
    {
        for (Box box : Box.values())
            box.image = getImage(box.name().toLowerCase());
    }

    private Image getImage (String name){
        String filename = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();

    }

}