package pacman.maze;

import pacman.main.Finals;
import pacman.entities.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class DrawManager {
    public static void drawMaze(Maze maze, Graphics g) {
        int TOP_GAP = (Finals.WINDOW_HEIGHT - maze.getHeight() * Finals.CELL_SIZE) / 2;
        int SIDE_GAP = (Finals.WINDOW_WIDTH - maze.getWidth() * Finals.CELL_SIZE) / 2;
        g.setColor(new Color(233, 229, 1, 180));
        g.fillRect(0, 0, Finals.WINDOW_WIDTH, Finals.WINDOW_HEIGHT);
        for (Cell cell : maze.getCells()) {
            switch (cell.getType()) {
                case Wall:
                    g.setColor(Color.GREEN);
                    g.fillRect(SIDE_GAP + cell.getX() * Finals.CELL_SIZE, TOP_GAP + cell.getY() * Finals.CELL_SIZE,
                            Finals.CELL_SIZE, Finals.CELL_SIZE);
                    break;
                case Empty:
                    g.setColor(Color.BLACK);
                    g.fillRect(SIDE_GAP + cell.getX() * Finals.CELL_SIZE, TOP_GAP + cell.getY() * Finals.CELL_SIZE,
                            Finals.CELL_SIZE, Finals.CELL_SIZE);
                    break;
                case Tablet:
                    g.setColor(Color.BLACK);
                    g.fillRect(SIDE_GAP + cell.getX() * Finals.CELL_SIZE, TOP_GAP + cell.getY() * Finals.CELL_SIZE,
                            Finals.CELL_SIZE, Finals.CELL_SIZE);
                    g.setColor(Color.YELLOW);
                    g.fillRect(SIDE_GAP + cell.getX() * Finals.CELL_SIZE + Finals.TABLET_GAP,
                            TOP_GAP + cell.getY() * Finals.CELL_SIZE + Finals.TABLET_GAP,
                            Finals.TABLET_SIZE, Finals.TABLET_SIZE);
                    break;
            }
        }
    }

    public static void drawEntity(Entity entity, int height, int width, Graphics g) {
        int TOP_GAP = (Finals.WINDOW_HEIGHT - height * Finals.CELL_SIZE) / 2;
        int SIDE_GAP = (Finals.WINDOW_WIDTH - width * Finals.CELL_SIZE) / 2;
        BufferedImage PW_image = null;
        try {
            PW_image = ImageIO.read(new File(entity.getPicturePath()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        g.drawImage(PW_image, entity.getCurrentPosition().getX() * Finals.CELL_SIZE + SIDE_GAP + Finals.PACWOMAN_GAP,
                entity.getCurrentPosition().getY() * Finals.CELL_SIZE + TOP_GAP + Finals.PACWOMAN_GAP,
                Finals.PACWOMAN_SIZE, Finals.PACWOMAN_SIZE, null);
    }

    public static void drawScore(int level, int score, Maze maze, Graphics g) {
        String text = "Level: " + level + ". Score: " + score;
        Font font = new Font("SanSerif", Font.PLAIN, Finals.TEXT_FONT);
        int TOP_GAP = (Finals.WINDOW_HEIGHT - maze.getHeight() * Finals.CELL_SIZE) / 2;

        g.setColor(new Color(0, 0, 0));
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int x = (Finals.WINDOW_WIDTH - fm.stringWidth(text)) / 2;
        int y = (TOP_GAP - fm.getAscent()) / 2;
        g.drawString(text, x, y);
    }

    public static void drawText(String text, int height, Graphics g) {
        Font font = new Font("SanSerif", Font.PLAIN, Finals.TEXT_FONT);
        int TOP_GAP = (Finals.WINDOW_HEIGHT - height * Finals.CELL_SIZE) / 2;

        g.setColor(new Color(0, 0, 0, 254));
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int x = (Finals.WINDOW_WIDTH - fm.stringWidth(text)) / 2;
        int y = Finals.WINDOW_HEIGHT - (TOP_GAP - fm.getAscent()) / 2;
        g.drawString(text, x, y);
    }
}
