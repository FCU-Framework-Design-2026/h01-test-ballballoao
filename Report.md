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

1. 使用層級
(層級2) 用來除錯，且改善功能、架構
2. 概述你和 AI 互動的次數與內容
在這次作業中，我大約與 AI 互動了 5 到 8 次，主要內容包含：
除錯 (Debugging)：一開始在設定建構子和屬性預設值時，遇到一些編譯錯誤，AI 幫我指出是因為語法結構的小漏字。
功能提升與學習：我原本不知道該如何表示暗棋 32 顆棋子的棋盤，AI 建議我可以使用 Chess[][] board = new Chess 的二維陣列來模擬真實的 8x4 盤面
，這讓程式的邏輯更貼近現實。
Prompt 設計：我的提問方式通常是「我是一個 Java 初學者，我想寫一個暗棋遊戲，需要區分紅黑方和還沒決定的狀態，請問這段程式碼哪裡有錯？」或「請問 Java 中要怎麼建立抽象類別來管理玩家？」
3. 手動 (沒有用 AI) 的部份
系統中 Player 的基本屬性（如：name, side 等判斷邏輯）、變數命名（p1, p2, redCount, blackCount）以及基礎的 main 函式啟動流程，都是我自己構思與撰寫的
。
決定以 -1 代表陣營尚未決定，是我根據遊戲規則（第一步翻牌決定陣營）自己想出來的邏輯
。
4. 心得（AI的實用性、限制、對學習的影響）
省下了哪些程式/時間：AI 幫我省下了大量翻閱教科書查找「抽象類別 (Abstract Class) 怎麼宣告」以及「二維陣列初始化」語法的時間，讓我能專注在「暗棋」這個遊戲本身的邏輯設計上。
澄清了哪些觀念：以前我對「繼承 (extends)」的概念很模糊，透過 AI 建議讓 ChessGame 繼承 AbstractGame
，我突然理解到把共用的元素（如玩家）放在父類別，可以讓程式碼變得非常乾淨。
查證與發現 AI 的錯誤：在討論棋盤大小時，AI 一開始給了一個一維陣列長度 32 的範例。但我查證後發現，暗棋需要對應「上下左右」移動，一維陣列在計算座標時非常容易出錯，因此我手動請 AI 改寫並確認了 8 列 4 行的二維陣列做法才是最好的。
是否阻礙了 OOP 觀念的理解：某種程度上，依賴 AI 給出 class 框架會讓我減少「從零開始規劃類別」的練習機會。未來在考試或沒有 AI 輔助時，我可能會一時忘記 abstract 的確切拼法或放置位置，這是我需要自己多加手打練習來克服的限制。

## 心得
我學到的迴圈的使用。
