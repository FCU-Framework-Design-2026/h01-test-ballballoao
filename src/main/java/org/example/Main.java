package org.example;

import java.util.Scanner;

class Player {
    String name;
    int side = -1; // 0代表紅方, 1代表黑方, -1代表還沒決定

    public Player(String n) {
        name = n;
    }
}

abstract class AbstractGame {
    Player p1;
    Player p2;
    Player currentPlayer;

    public void setPlayers(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.currentPlayer = p1; // 先讓 p1 下
    }
    abstract boolean gameOver();
    abstract boolean move(int location);
}

class Chess {
    String name;
    int weight; // 大小階級
    int side;   // 0=紅, 1=黑
    String loc; // 位置
    boolean isFlipped; // 是否翻開

    public Chess(String name, int weight, int side, String loc) {
        this.name = name;
        this.weight = weight;
        this.side = side;
        this.loc = loc;
        this.isFlipped = false;
    }

    public String toString() {
        if (isFlipped == false) {
            return "Ｘ";
        } else {
            return name;
        }
    }
}

class ChessGame extends AbstractGame {
    Chess[][] board = new Chess[8][4]; // 棋盤，8列(1~8) 4行(A~D)
    boolean firstFlip = true; // 紀錄是不是第一步
    int redCount = 16;
    int blackCount = 16;

    // 產生並隨機擺放棋子
    public void generateChess() {
        // 新手寫法：用一個一維陣列裝32顆棋子
        Chess[] allChess = new Chess[32];

        String[] names = {"帥","仕","仕","相","相","俥","俥","傌","傌","炮","炮","兵","兵","兵","兵","兵",
                "將","士","士","象","象","車","車","馬","馬","包","包","卒","卒","卒","卒","卒"};
        int[] weights = {7,6,6,5,5,4,4,3,3,2,2,1,1,1,1,1,
                7,6,6,5,5,4,4,3,3,2,2,1,1,1,1,1};

        for (int i = 0; i < 32; i++) {
            int side = 0; // 預設紅色
            if (i >= 16) {
                side = 1; // 後面16顆是黑色
            }
            allChess[i] = new Chess(names[i], weights[i], side, "");
        }

        for (int i = 0; i < 100; i++) {
            int r1 = (int)(Math.random() * 32); // 產生 0~31 隨機數
            int r2 = (int)(Math.random() * 32);
            // 把兩顆棋子交換
            Chess temp = allChess[r1];
            allChess[r1] = allChess[r2];
            allChess[r2] = temp;
        }

        // 把洗好的 32 顆棋子，放進 8x4 的二維陣列裡
        int index = 0;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 4; c++) {
                board[r][c] = allChess[index];
                index = index + 1;
            }
        }
    }

    // 印出橫向的棋盤
    public void showAllChess() {
        System.out.println("   1  2  3  4  5  6  7  8");
        for (int c = 0; c < 4; c++) {
            // 印出 A, B, C, D
            if (c == 0) System.out.print("A  ");
            if (c == 1) System.out.print("B  ");
            if (c == 2) System.out.print("C  ");
            if (c == 3) System.out.print("D  ");

            for (int r = 0; r < 8; r++) {
                if (board[r][c] == null) {
                    System.out.print("＿ "); // 空位印底線
                } else {
                    System.out.print(board[r][c].toString() + " ");
                }
            }
            System.out.println(); // 換行
        }
        System.out.println("--------------------------");
    }

    public boolean gameOver() {
        if (redCount == 0) {
            System.out.println("黑方贏了！");
            return true;
        }
        if (blackCount == 0) {
            System.out.println("紅方贏了！");
            return true;
        }
        return false;
    }

    // 移動與吃子的邏輯 
    public boolean move(int location) {
        // location 是一個 0~31 的數字，把它換算成二維陣列的位置
        int r = location / 4;
        int c = location % 4;

        Chess selected = board[r][c];

        if (selected == null) {
            System.out.println("錯誤：這裡沒東西！");
            return false;
        }

        //棋子還沒翻開 -> 執行翻開
        if (selected.isFlipped == false) {
            selected.isFlipped = true;
            System.out.println(">> 翻開了：" + selected.name);

            // 如果是整場遊戲第一次翻，決定誰是紅方誰是黑方
            if (firstFlip == true) {
                currentPlayer.side = selected.side;

                if (currentPlayer == p1) {
                    // currentPlayer 是 p1，那 p2 就是另一邊
                    if (selected.side == 0) p2.side = 1;
                    else p2.side = 0;
                } else {
                    if (selected.side == 0) p1.side = 1;
                    else p1.side = 0;
                }
                firstFlip = false;

                if (currentPlayer.side == 0) {
                    System.out.println(">> " + currentPlayer.name + " 變成了 紅方");
                } else {
                    System.out.println(">> " + currentPlayer.name + " 變成了 黑方");
                }
            }
            return true;
        }
        //棋子已經翻開了 -> 準備移動或吃子
        else {
            if (currentPlayer.side != -1 && selected.side != currentPlayer.side) {
                System.out.println("錯誤：這不是你的棋子！");
                return false;
            }

            System.out.print("請輸入要去哪裡 (例如 A2): ");
            Scanner sc = new Scanner(System.in);
            String targetLoc = sc.next().toUpperCase();

            // 拆解輸入的字串
            char colChar = targetLoc.charAt(0);
            char rowChar = targetLoc.charAt(1);

            // 把字母跟數字轉換
            int tc = -1;
            if (colChar == 'A') tc = 0;
            if (colChar == 'B') tc = 1;
            if (colChar == 'C') tc = 2;
            if (colChar == 'D') tc = 3;

            int tr = (rowChar - '0') - 1;

            if (tr < 0 || tr > 7 || tc < 0 || tc > 3) {
                System.out.println("錯誤：超出邊界！");
                return false;
            }

            Chess target = board[tr][tc];

            int rowDiff = r - tr;
            int colDiff = c - tc;
            if (rowDiff < 0) rowDiff = -rowDiff;
            if (colDiff < 0) colDiff = -colDiff;

            if (rowDiff + colDiff != 1) {
                System.out.println("錯誤：只能走一格！");
                return false;
            }

            // 如果目標位置有棋子，檢查能不能吃
            if (target != null) {
                if (target.isFlipped == false) {
                    System.out.println("錯誤：不能吃沒翻開的！");
                    return false;
                }
                if (target.side == selected.side) {
                    System.out.println("錯誤：不能吃自己人！");
                    return false;
                }
                if (selected.weight < target.weight) {
                    // 特別處理：卒可以吃帥
                    if (selected.weight == 1 && target.weight == 7) {
                        // 可以吃，不擋下來
                    } else {
                        System.out.println("錯誤：你太小了吃不掉他！");
                        return false;
                    }
                }
                if (selected.weight == 7 && target.weight == 1) {
                    System.out.println("錯誤：帥不能吃卒！");
                    return false;
                }
            }

            // 執行移動
            if (target != null) {
                System.out.println(">> 吃掉了：" + target.name);
                if (target.side == 0) redCount = redCount - 1;
                else blackCount = blackCount - 1;
            }

            board[tr][tc] = selected; // 棋子過去
            board[r][c] = null;       // 原本位置清空
            return true;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ChessGame game = new ChessGame();
        Player p1 = new Player("玩家1");
        Player p2 = new Player("玩家2");

        game.setPlayers(p1, p2);
        game.generateChess();

        Scanner sc = new Scanner(System.in);

        while (game.gameOver() == false) {
            game.showAllChess();

            String sideName = "";
            if (game.currentPlayer.side == 0) sideName = "(紅方)";
            if (game.currentPlayer.side == 1) sideName = "(黑方)";

            System.out.print("輪到 " + game.currentPlayer.name + sideName + "，請輸入位置: ");
            String input = sc.next().toUpperCase();

            if (input.length() != 2) {
                System.out.println("格式錯了！");
                continue;
            }

            char colChar = input.charAt(0);
            char rowChar = input.charAt(1);

            int c = -1;
            if (colChar == 'A') c = 0;
            if (colChar == 'B') c = 1;
            if (colChar == 'C') c = 2;
            if (colChar == 'D') c = 3;

            int r = (rowChar - '0') - 1;

            if (c == -1 || r < 0 || r > 7) {
                System.out.println("輸入有誤！");
                continue;
            }

            int location = r * 4 + c;
            boolean success = game.move(location);

            if (success == true) {
                if (game.currentPlayer == p1) {
                    game.currentPlayer = p2;
                } else {
                    game.currentPlayer = p1;
                }
            }
            System.out.println();
        }
    }
}
