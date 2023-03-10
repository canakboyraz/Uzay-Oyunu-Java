import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameScreen extends JFrame {
    public GameScreen(String title) throws HeadlessException {
        super(title);
    }

    public static void main(String[] args) throws IOException {
        GameScreen screen = new GameScreen("Space Game");

        screen.setResizable(false); // bizim verdiğimiz boyutta çalışmasını istiyoruz
        screen.setFocusable(false);

        screen.setSize(800,600); // ekran boyutunu belirledik
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game game = new Game();
        game.requestFocus(); // klavyeden odağı almak için
        game.addKeyListener(game); // klavyeen işlemleri almamızı sağlıyor.
        game.setFocusable(true);
        game.setFocusTraversalKeysEnabled(false); // Klavye işlemlerini anlaması için.

        screen.add(game);

        screen.setVisible(true);
    }
}
