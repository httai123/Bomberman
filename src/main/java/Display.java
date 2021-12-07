import javax.swing.*;
import java.awt.*;

public class Display extends JFrame {

    static final int HEIGHT = 48;
    static final String title = "My Bomberman";

    public Display(GamePanel game) {
        this.setTitle(title);
        this.setLayout(new BorderLayout());
        this.add(game, BorderLayout.CENTER);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
