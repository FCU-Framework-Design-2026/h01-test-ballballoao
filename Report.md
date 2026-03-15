# H1 Report

* Name: 楊博宇
* ID: D1227477

---

## 題目：象棋翻棋遊戲

## 設計方法概述
用物件導向概念模擬傳統的暗棋遊戲。主要包含以下幾個核心類別：
 * 玩家 (Player 類別)：儲存玩家姓名與所屬陣營（0代表紅方，1代表黑方，-1代表尚未決定）。
 * 遊戲 (AbstractGame 抽象類別)：定義遊戲的基本元素，包含兩位玩家 (p1, p2) 以及當前輪到的玩家 (currentPlayer)。
 * 棋子 (Chess 類別)：記錄棋子的屬性，包含名稱、大小階級 (weight)、陣營 (side)、棋盤位置 (loc) 以及是否已經翻開的狀態 (isFlipped)。
 * 遊戲主體 (ChessGame 類別)：繼承 AbstractGame，實作暗棋的核心資料結構，包含一個 8 列 4 行的二維陣列 (Chess[][] board = new Chess) 作為棋盤，並使用變數記錄紅黑雙方的剩餘棋子數量（預設各 16 顆），以及判斷是否為遊戲的第一步 (firstFlip)。

## 程式、執行畫面及其說明
以下為本專案的核心基礎架構程式碼：

```java
package org.example;
import java.util.Scanner;

// 玩家類別
class Player { 
    String name; 
    int side = -1; // 0代表紅方, 1代表黑方, -1代表還沒決定
}

// 遊戲抽象類別，提供基礎框架
abstract class AbstractGame { 
    Player p1; 
    Player p2; 
    Player currentPlayer;
}

// 棋子類別
class Chess { 
    String name; 
    int weight; // 大小階級 (例如：將帥為最高階)
    int side;   // 0=紅, 1=黑 
    String loc; // 位置 
    boolean isFlipped; // 是否翻開
}

// 暗棋遊戲主類別，繼承 AbstractGame
class ChessGame extends AbstractGame { 
    Chess[][] board = new Chess; // 棋盤，8列(1~8) 4行(A~D) 
    boolean firstFlip = true; // 紀錄是不是第一步 
    int redCount = 16; // 紅方剩餘數量
    int blackCount = 16; // 黑方剩餘數量
}

public class Main { 
    public static void main(String[] args) { 
        ChessGame game = new ChessGame(); 
        Player p1 = new Player("玩家1"); 
        Player p2 = new Player("玩家2");
        
        // 系統初始化與後續邏輯將在此執行
    }
}
```

執行畫面與說明

![](img/image.png)
說明：在此畫面中，系統成功實例化了 ChessGame 與兩名玩家 p1、p2。這證明了基礎的物件架構已經正確建立，記憶體也成功配置了 8x4 的棋盤空間

# AI 使用狀況與心得

這個展示比較容易，所以沒有用到 AI

## 心得
我學到的迴圈的使用。
