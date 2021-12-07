
import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Dùng để điều khiển game
 */
public class KeyControl implements KeyListener {

    private GamePanel gamePanel;
    public KeyControl(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.out.println("Escape key pressed: Closing game");
            this.gamePanel.exit();
        }
        if (e.getKeyCode() == KeyEvent.VK_F1) {
            System.out.println("F1 key pressed: Displaying help");

            String[] columnHeaders = {"", "Red", "Blue"};
            Object[][] controls = {
                    {"Up", "Up", "W"},
                    {"Down", "Down", "S"},
                    {"Left", "Left", "A"},
                    {"Right", "Right", "D"},
                    {"Bomb", "ENTER", "J"},
                    {"", "", ""},
                    {"Help", "F1", ""},
                    {"Reset", "F5", ""},
                    {"Exit", "ESC", ""}
            };

            JTable controlsTable = new JTable(controls, columnHeaders);
            JTableHeader tableHeader = controlsTable.getTableHeader();
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.add(tableHeader, BorderLayout.NORTH);
            panel.add(controlsTable, BorderLayout.CENTER);

            JOptionPane.showMessageDialog(this.gamePanel, panel, "Controls", JOptionPane.PLAIN_MESSAGE);
        }

        // Reset game
        // Delay prevents resetting too fast which causes the game to crash
        if (e.getKeyCode() == KeyEvent.VK_F5) {
            if (this.gamePanel.screenDelay >= 20) {
                System.out.println("F5 key pressed: Resetting game");
                this.gamePanel.resetGame();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_F9) {
            System.out.println("hack game");
            this.gamePanel.nextMap();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
