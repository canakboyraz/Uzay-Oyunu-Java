import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Fire{
    private int x; // x ekseni
    private int y; // y ekseni

    public Fire(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
public class Game extends JPanel implements KeyListener, ActionListener {

    Timer timer = new Timer(1,this);

    private int passing_time = 0;
    private int shots_number = 0;

    private BufferedImage image;
    private ArrayList<Fire> fires = new ArrayList<Fire>();

    private int firesY = 5;
    private int ball = 0; // sağa sola gitmeyi ayarlayacak
    private int ballX = 2;
    private int spaceShipX = 0; // uzay gemisinin nereden başlayacağı
    private int spaceX = 20; // Klavyeden tuşa basınca kaç birim kayacağını yazıyoruz.

    public  boolean control(){
        for (Fire fire : fires){
            if (new Rectangle(fire.getX(),fire.getY(),10,20).intersects(new Rectangle(ball,0,20,20)))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        passing_time += 5;
        g.setColor(Color.red);
        g.fillOval(ball,0,20,20);
        g.drawImage(image,spaceShipX,490,image.getWidth()/10,image.getHeight()/10,this);

        for (Fire fire: fires){
            if (fire.getY()<0){
                fires.remove(fire);
            }
        }

        g.setColor(Color.BLUE);
        for (Fire fire: fires){
            g.fillRect(fire.getX(),fire.getY(),10,20);
        }
        if (control()){
            timer.stop();
            String message = " Kazandınız..\n"
                            +" Harcanan ateş : " + shots_number + "\n"
                            +" Geçen süre : " + passing_time / 1000.0 + " sn";

            JOptionPane.showMessageDialog(this,message);
            System.exit(0);
        }
    }

    public Game() {
        try {
            image = ImageIO.read(new FileImageInputStream(new File("uzaygemisi.png"))); // uzay gemisi için png görsel ekledik.
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setBackground(Color.BLACK); // arka plan rengini ayarladık

        timer.start();

    }

    @Override
    public void actionPerformed(ActionEvent e) { // y ekseninde topun sınırlarını çizerek hareket etmesini sağladık.

        for (Fire fire : fires){
            fire.setY(fire.getY() - firesY );

        }

        ball += ballX;

        if (ball >= 750){
            ballX = -ballX;
        }
        if (ball <= 0){
            ballX = -ballX;
        }
        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) { // uzay mekiğini hareket ettirmek istiyoruz.
            int c = e.getKeyCode();

            if (c == KeyEvent.VK_LEFT){
                if (spaceShipX <=0 ){
                    spaceShipX = 0;
                }
                else {
                    spaceShipX -= spaceX;
                }
            }


            else if (c == KeyEvent.VK_RIGHT) {
                if (spaceShipX>= 740){
                    spaceShipX = 740;
                }
                else {
                    spaceShipX += spaceX;
                }

            }
            else if (c == KeyEvent.VK_CONTROL) {
                fires.add(new Fire(spaceShipX+15,465)); // ateş için gerekli görsel ayarlama

                shots_number++;

            }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
