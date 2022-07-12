package com.example.puzzlethebrain;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class GameView extends View {

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private Cell[][] cells;
    private Cell player, exit;
    private static final int cols = 15, rows = 25;

    private static final float Wall_Thickness = 4;
    private float cellSize, horMargin, verMargin;
    private Paint wallPaint, playerPaint, exitPaint;
    private Random random;

    public GameView(Context context , @Nullable AttributeSet attrs) {
        super(context , attrs);

        wallPaint = new Paint();
        wallPaint.setColor(Color.BLACK);
        wallPaint.setStrokeWidth(Wall_Thickness);

        playerPaint = new Paint();
        playerPaint.setColor(Color.BLUE);

        exitPaint = new Paint();
        exitPaint.setColor(Color.RED);

        random = new Random();

        createMaze();
    }


    private Cell getNeighbour(Cell cell) {
        ArrayList<Cell> neighbours = new ArrayList<>();

        if (cell.col > 0) {
            if (!cells[cell.col - 1][cell.row].visited) {
                neighbours.add(cells[cell.col - 1][cell.row]);
            }
        }

        if (cell.col < cols - 1) {
            if (!cells[cell.col + 1][cell.row].visited) {
                neighbours.add(cells[cell.col + 1][cell.row]);
            }
        }

        if (cell.row > 0) {
            if (!cells[cell.col][cell.row - 1].visited) {
                neighbours.add(cells[cell.col][cell.row - 1]);
            }
        }

        if (cell.row < rows - 1) {
            if (!cells[cell.col][cell.row + 1].visited) {
                neighbours.add(cells[cell.col][cell.row + 1]);
            }
        }

        if (neighbours.size() > 0) {
            int index = random.nextInt(neighbours.size());
            return neighbours.get(index);
        }

        return null;
    }

    private void removeWall(Cell current , Cell next) {
        if (current.col == next.col && current.row == next.row + 1) {
            current.topWall = false;
            next.bottomWall = false;
        }

        if (current.col == next.col && current.row == next.row - 1) {
            current.bottomWall = false;
            next.topWall = false;
        }

        if (current.col == next.col + 1 && current.row == next.row) {
            current.leftWall = false;
            next.rightWall = false;
        }

        if (current.col == next.col - 1 && current.row == next.row) {
            current.rightWall = false;
            next.leftWall = false;
        }

    }

    private void createMaze() {
        Stack<Cell> stack = new Stack<>();
        Cell current, next;

        cells = new Cell[cols][rows];

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                cells[i][j] = new Cell(i , j);
            }
        }

        player = cells[0][0];
        exit = cells[cols - 1][rows - 1];

        current = cells[0][0];
        current.visited = true;

        do {
            next = getNeighbour(current);
            if (next != null) {
                removeWall(current , next);
                stack.push(current);
                current = next;
                current.visited = true;
            } else {
                current = stack.pop();
            }
        } while (!stack.empty());
    }

    protected void onDraw(Canvas canvas) {
//            canvas.drawColor(Color.GREEN);

        int width = getWidth();
        int height = getHeight();

        if (width / height < cols / rows) {
            cellSize = width / (cols + 1);
        } else {
            cellSize = height / (rows + 1);
        }

        horMargin = (width - cols * cellSize) / 2;
        verMargin = (height - rows * cellSize) / 2;

        canvas.translate(horMargin , verMargin);

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (cells[i][j].topWall) {
                    canvas.drawLine(
                            i * cellSize ,
                            j * cellSize ,
                            (i + 1) * cellSize ,
                            (j) * cellSize ,
                            wallPaint);
                }
                if (cells[i][j].leftWall) {
                    canvas.drawLine(
                            i * cellSize ,
                            j * cellSize ,
                            (i) * cellSize ,
                            (j + 1) * cellSize ,
                            wallPaint);
                }
                if (cells[i][j].rightWall) {
                    canvas.drawLine(
                            (i + 1) * cellSize ,
                            j * cellSize ,
                            (i + 1) * cellSize ,
                            (j + 1) * cellSize ,
                            wallPaint);
                }
                if (cells[i][j].bottomWall) {
                    canvas.drawLine(
                            i * cellSize ,
                            (j + 1) * cellSize ,
                            (i + 1) * cellSize ,
                            (j + 1) * cellSize ,
                            wallPaint);
                }
            }
        }

        float margin = cellSize / 10;

        canvas.drawRect(
                player.col * cellSize + margin ,
                player.row * cellSize + margin ,
                (player.col + 1) * cellSize - margin ,
                (player.row + 1) * cellSize - margin ,
                playerPaint);

        canvas.drawRect(
                exit.col * cellSize + margin ,
                exit.row * cellSize + margin ,
                (exit.col + 1) * cellSize - margin ,
                (exit.row + 1) * cellSize - margin ,
                exitPaint);
    }

    private void movePlayer(Direction direction) {
        switch (direction) {
            case UP:
                if (!player.topWall) {
                    player = cells[player.col][player.row - 1];
                }
                break;
            case DOWN:
                if (!player.bottomWall) {
                    player = cells[player.col][player.row + 1];
                }
                break;
            case LEFT:
                if (!player.leftWall) {
                    player = cells[player.col - 1][player.row];
                }
                break;
            case RIGHT:
                if (!player.rightWall) {
                    player = cells[player.col + 1][player.row];
                }
                break;
        }

        checkExit();
        invalidate();
    }

    private void checkExit() {
        if (player == exit) {
            Toast.makeText(getContext() , "You Won!!!" , Toast.LENGTH_SHORT).show();

            createMaze();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float i = event.getX();
            float j = event.getY();

            float playerCenterX = horMargin + (player.col + 0.5f) * cellSize;
            float playerCenterY = verMargin + (player.row + 0.5f) * cellSize;

            float dx = i - playerCenterX;
            float dy = j - playerCenterY;

            float absDx = Math.abs(dx);
            float absDy = Math.abs(dy);

            if (absDx > cellSize || absDy > cellSize) {
                if (absDx > absDy) {
                    if (dx > 0) {
                        movePlayer(Direction.RIGHT);
                    } else {
                        movePlayer(Direction.LEFT);
                    }
                } else {
                    if (dy > 0) {
                        movePlayer(Direction.DOWN);
                    } else {
                        movePlayer(Direction.UP);
                    }
                }
            }
            return true;
        }

        return super.onTouchEvent(event);
    }

    private class Cell {
        boolean topWall = true,
                leftWall = true,
                rightWall = true,
                bottomWall = true,
                visited = false;

        int col, row;

        public Cell(int col , int row) {
            this.col = col;
            this.row = row;
        }
    }
}
