package checkers04;
//класс обработчик ходов, работает с массивом fieldArray

import java.util.ArrayList;

public class Handler {

    public Handler(Field [][] fieldArray) {
        this.fieldArray = fieldArray;
        RedMove = true;
        BlackMove = false;
    }

    private Field [][] fieldArray;
    private boolean RedMove;//переменная показывает , ходят ли крансые
    private boolean RedTake;//переменная , поакзывает, подхвачена ли красная шашка
    private int xRed;//координаты подхваченной красной шашки
    private int yRed;//координаты подхваченной красной шашки
    private boolean BlackMove;//переменная показывает , ходят ли крансые
    private boolean BlackTake;//переменная , поакзывает, подхвачена ли красная шашка
    private int xBlack;//координаты подхваченной красной шашки
    private int yBlack;//координаты подхваченной красной шашки
    
    public void clickHandler(int x, int y) {
        System.out.println("Handler called");
        //если ходят красные
        if (RedMove) {
            System.out.println("Red move");
            //если подхвачена красная шашка
            if (RedTake) {
                //если щелчёк на доступной красной шашке
                if (availableRed(x, y)) {
                    //перезахватываем другую шашку (или эту же)
                    xRed = x;
                    yRed = y;
                }
                //
                else {                    
                    //щелчёк на доступном поле
                    if (availableFieldRed(x, y)) {
                        //тогда либо просто ход, либо ход со съедением черной шашки
                        if (eatingRed(x, y)) {
                            //destroyBlack();//уничтожить черную шашку??? - засунуть в eating
                            moveRed(x, y);
                            if (continueEatingRed()) {
                                //System.out.println("CONTINUJE EATING RED");
                             return;
                            }
                            else {
                            RedTake = false;
                            RedMove = false;
                            BlackMove = true;
                            }
                        }
                        else {
                            //перемещаем шашку
                            moveRed(x, y);
                            RedTake = false;
                            RedMove = false;
                            BlackMove = true;
                        }
                    }
                    else {
                        return;
                    }
                }
            }
            //если красная шашка не подхвачена
            else {
                //если щелчёк на доступной красной шашке
                if (availableRed(x, y)) {
                    RedTake = true;//захват шашки
                    xRed = x;
                    yRed = y;
                }
            }
        }
        //если ходят черные
        else {
            //если подхвачена черная шашка
            if (BlackTake) {
                System.out.println("Black move");
                //если щелчёк на доступной черной шашке
                if (availableBlack(x, y)) {
                    //перезахватываем другую шашку (или эту же)
                    xBlack = x;
                    yBlack = y;
                }
                //
                else {
                    //щелчёк на доступном поле
                    if (availableFieldBlack(x, y)) {
                        //тогда либо просто ход, либо ход со съедением красной шашки
                        if (eatingBlack(x, y)) {
                            //destroyBlack();//уничтожить красную шашку??? - засунуть в eating
                            moveBlack(x, y);
                            if (continueEatingBlack()) {
                                //System.out.println("CONTINUJE EATING RED");
                             return;
                            }
                            else {
                            BlackTake = false;
                            BlackMove = false;
                            RedMove = true;
                            }
                        }
                        else {
                            //перемещаем шашку
                            moveBlack(x, y);
                            BlackTake = false;
                            BlackMove = false;
                            RedMove = true;
                        }
                    }
                    else {
                        return;
                    }
                }
            }
            //если черная шашка не подхвачена
            else {
                //если щелчёк на доступной черной шашке
                if (availableBlack(x, y)) {
                    BlackTake = true;//захват шашки
                    xBlack = x;
                    yBlack = y;
                }
            }
        }
    }


    //функция, проверяет доступна ли красная шашка , находящаяся на этих координатах
    private boolean availableRed(int x, int y) {
        ArrayList<Pair> eating = new ArrayList<Pair>();//если длина этого списочного массива будет > 0 , значит существует хотя бы одна шашка, которая должна есть, её координаты и будут храниться в этом массиве
        //ArrayList<Pair> simple = new ArrayList<Pair>();//здесь будут храниться координаты шашек, которые могут быть "захвачены"

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (fieldArray[i][j].Red) {
                    if ( (i - 1) >= 0 && (j - 1) >= 0) {
                        if (fieldArray[i - 1][j - 1].Black || fieldArray[i - 1][j - 1].BlackQueen) {
                            if ((i - 2) >= 0 && (j - 2) >= 0) {
                                if (fieldArray[i - 2][j - 2].Empty) {
                                    eating.add(new Pair(i, j));
                                }
                            }
                        }
                    }
                    if ( (i - 1) >= 0 && (j + 1) < 8) {
                        if (fieldArray[i - 1][j + 1].Black || fieldArray[i - 1][j + 1].BlackQueen) {
                            if ((i - 2) >= 0 && (j + 2) < 8) {
                                if (fieldArray[i - 2][j + 2].Empty) {
                                    eating.add(new Pair(i, j));
                                }
                            }
                        }
                    }
                }
                if (fieldArray[i][j].RedQueen) {
                    //лево верх
                    if ((i - 1) >= 0 && (j - 1) >= 0 && (fieldArray[i - 1][j - 1].Black || fieldArray[i - 1][j - 1].BlackQueen)) {
                        if ((i - 2) >= 0 && (j - 2) >= 0 && fieldArray[i- 2][j - 2].Empty) {
                            eating.add(new Pair(i, j));
                        }
                    }
                    //право верх
                    if ((i - 1) >= 0 && ( j + 1) < 8 && (fieldArray[i - 1][ j + 1].Black || fieldArray[i - 1][ j + 1].BlackQueen)) {
                        if (( i - 2) >= 0 && (j + 2) < 8 && fieldArray[i - 2][j + 2].Empty) {
                            eating.add(new Pair(i, j));
                        }
                    }
                    //низ лево
                    if ((i + 1) < 8 && (j - 1) >= 0 && (fieldArray[i + 1][j - 1].Black || fieldArray[i + 1][j - 1].BlackQueen)) {
                        if ((i + 2) < 8 && (j - 2) >= 0 && fieldArray[i + 2][j - 2].Empty) {
                            eating.add(new Pair(i, j));
                        }
                    }
                    //низ право
                    if ((i + 1) < 8 && (j + 1) < 8 && (fieldArray[i + 1][ j + 1].Black || fieldArray[i + 1][ j + 1].BlackQueen)) {
                        if (( i + 2) < 8 && (j + 2) < 8 && fieldArray[i + 2][ j + 2].Empty) {
                            eating.add(new Pair(i, j));
                        }
                    }
                }
            }
        }

        if (eating.size() > 0) {
                for (int i = 0; i < eating.size(); i++) {
                    if (eating.get(i).x == x && eating.get(i).y == y) {
                        clearCurrent();
                        fieldArray[x][y].Current = true;
                        return true;
                    }
                }
            }
        else if (fieldArray[x][y].Red && (x - 1) >= 0 && (y - 1) >= 0 && fieldArray[x - 1][y - 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }
        else if (fieldArray[x][y].Red && (x - 1) >= 0 && (y + 1) < 8 && fieldArray[x - 1][y + 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }
        else if (fieldArray[x][y].RedQueen && (x - 1) >= 0 && (y - 1) >= 0 && fieldArray[x - 1][y - 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }
        else if (fieldArray[x][y].RedQueen && (x - 1) >= 0 && (y + 1) < 8 && fieldArray[x - 1][y + 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }
        else if (fieldArray[x][y].RedQueen && (x + 1) < 8 && (y - 1) >= 0 && fieldArray[x + 1][y - 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }
        else if (fieldArray[x][y].RedQueen && (x + 1) < 8 && (y + 1) < 8 && fieldArray[x + 1][y + 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }
        return false;
    }

    //функция, проверяет доступно ли поле
    private boolean availableFieldRed(int x, int y) {
        boolean key = false;
        if ((fieldArray[xRed][yRed].RedQueen || fieldArray[xRed][yRed].Red) &&(xRed - 2) >= 0 && (yRed - 2) >= 0 && fieldArray[xRed -2][yRed -2].Empty && (xRed - 1) >= 0 && (yRed - 1) >= 0 && (fieldArray[xRed -1][yRed -1].Black || fieldArray[xRed -1][yRed -1].BlackQueen)) {
            key = true;
            if (x == (xRed - 2) && y == (yRed - 2)) return true;
        }
        if ((fieldArray[xRed][yRed].RedQueen || fieldArray[xRed][yRed].Red) && (xRed - 2) >= 0 && (yRed + 2) < 8 && fieldArray[xRed -2][yRed +2].Empty && (xRed - 1) >= 0 && (yRed + 1) < 8 && (fieldArray[xRed -1][yRed +1].Black || fieldArray[xRed -1][yRed +1].BlackQueen)) {
            key = true;
            if (x == (xRed - 2) && y == (yRed + 2)) return true;
        }
        //для дамки
        if (fieldArray[xRed][yRed].RedQueen && (xRed + 2) < 8 && (yRed - 2) >= 0 && fieldArray[xRed + 2][yRed -2].Empty && (xRed + 1) < 8 && (yRed - 1) >= 0 && (fieldArray[xRed + 1][yRed -1].Black || fieldArray[xRed + 1][yRed -1].BlackQueen)) {
            key = true;
            if (x == (xRed + 2) && y == (yRed - 2)) return true;
        }
        if (fieldArray[xRed][yRed].RedQueen && (xRed + 2) < 8 && (yRed + 2) < 8 && fieldArray[xRed + 2][yRed + 2].Empty && (xRed + 1) < 8 && (yRed + 1) < 8 && (fieldArray[xRed + 1][yRed +1].Black || fieldArray[xRed + 1][yRed +1].BlackQueen)) {
            key = true;
            if (x == (xRed + 2) && y == (yRed + 2)) return true;
        }
        //
        if (!key ) {
        if ((fieldArray[x][y].Empty && x == (xRed - 1) && y == (yRed -1)) || (fieldArray[x][y].Empty && x == (xRed - 1) && y == (yRed +1))) {
            return true;
        }        
        //для дамки
        if ((fieldArray[xRed][yRed].RedQueen && fieldArray[x][y].Empty && x == (xRed - 1) && y == (yRed -1)) || (fieldArray[xRed][yRed].RedQueen && fieldArray[x][y].Empty && x == (xRed - 1) && y == (yRed +1)) || (fieldArray[xRed][yRed].RedQueen && fieldArray[x][y].Empty && x == (xRed + 1) && y == (yRed +1)) || (fieldArray[xRed][yRed].RedQueen && fieldArray[x][y].Empty && x == (xRed + 1) && y == (yRed - 1))) {
            return true;
        }
        }
        return false;   
    }

    //функция, проверяет, произошло ли съедение шашки, уничтожает черную шашку
    private boolean eatingRed(int x, int y) {
        if (x == (xRed - 2) && y == (yRed - 2) && fieldArray[x][y].Empty && (fieldArray[xRed - 1][yRed - 1].Black || fieldArray[xRed - 1][yRed - 1].BlackQueen)){
            if (fieldArray[xRed - 1][yRed - 1].Black) fieldArray[xRed - 1][yRed - 1].Black  = false;
            else fieldArray[xRed - 1][yRed - 1].BlackQueen = false;
            fieldArray[xRed - 1][yRed - 1].Empty = true;
            return true;
        }
        if (x == (xRed - 2) && y == (yRed + 2) && fieldArray[x][y].Empty && (fieldArray[xRed - 1][yRed + 1].Black || fieldArray[xRed - 1][yRed + 1].BlackQueen)){
            if (fieldArray[xRed - 1][yRed + 1].Black) fieldArray[xRed - 1][yRed + 1].Black = false;
            else fieldArray[xRed - 1][yRed + 1].BlackQueen = false;
            fieldArray[xRed - 1][yRed + 1].Empty = true;
            return true;
        }
        //для дамки
        if (fieldArray[xRed][yRed].RedQueen && x == (xRed + 2) && y == (yRed - 2) && fieldArray[x][y].Empty && (fieldArray[xRed + 1][yRed - 1].Black || fieldArray[xRed + 1][yRed - 1].BlackQueen)){
            if (fieldArray[xRed + 1][yRed - 1].Black) fieldArray[xRed + 1][yRed - 1].Black = false;
            else fieldArray[xRed + 1][yRed - 1].BlackQueen = false;
            fieldArray[xRed + 1][yRed - 1].Empty = true;
            return true;
        }
        if (fieldArray[xRed][yRed].RedQueen && x == (xRed + 2) && y == (yRed + 2) && fieldArray[x][y].Empty && (fieldArray[xRed + 1][yRed + 1].Black || fieldArray[xRed + 1][yRed + 1].BlackQueen)){
            if (fieldArray[xRed + 1][yRed + 1].Black) fieldArray[xRed + 1][yRed + 1].Black = false;
            else fieldArray[xRed + 1][yRed + 1].BlackQueen = false;
            fieldArray[xRed + 1][yRed + 1].Empty = true;
            return true;
        }
        return false;
    }
    //ф-ция перемещения шашки
    private void moveRed(int x, int y) {

        if (fieldArray[xRed][yRed].Red) {
            if (x == 0) {
                fieldArray[x][y].RedQueen = true;
                fieldArray[x][y].Empty = false;
                fieldArray[xRed][yRed].Empty = true;
                fieldArray[xRed][yRed].Red = false;
            }
            else {
                fieldArray[x][y].Red = true;
                fieldArray[x][y].Empty = false;
                fieldArray[xRed][yRed].Empty = true;
                fieldArray[xRed][yRed].Red = false;
            }
        }
        else {
            fieldArray[x][y].RedQueen = true;
                fieldArray[x][y].Empty = false;
                fieldArray[xRed][yRed].Empty = true;
                fieldArray[xRed][yRed].RedQueen = false;
        }
        xRed = x;
        yRed = y;
    }

    private boolean continueEatingRed() {
        if ((xRed - 2) >= 0 && (yRed - 2) >= 0 && fieldArray[xRed - 2][yRed - 2].Empty && fieldArray[xRed - 1][yRed - 1].Black) {
            return true;
        }
        if ((xRed - 2) >= 0 && (yRed + 2) < 8 && fieldArray[xRed - 2][yRed + 2].Empty && fieldArray[xRed - 1][yRed + 1].Black) {
            return true;
        }
        //для дамки
        if (fieldArray[xRed][yRed].RedQueen  && (xRed + 2) < 8 && (yRed - 2) >= 0 && fieldArray[xRed + 2][yRed - 2].Empty && (fieldArray[xRed + 1][yRed - 1].Black || fieldArray[xRed + 1][yRed - 1].BlackQueen)) {
            return true;
        }
        if (fieldArray[xRed][yRed].RedQueen  && (xRed + 2) < 8 && (yRed + 2) < 8 && fieldArray[xRed + 2][yRed + 2].Empty && (fieldArray[xRed + 1][yRed + 1].Black || fieldArray[xRed + 1][yRed + 1].BlackQueen)) {
            return true;
        }
        return false;
    }   

    //функция, проверяет доступна ли черная шашка , находящаяся на этих координатах
    private boolean availableBlack(int x, int y) {
        ArrayList<Pair> eating = new ArrayList<Pair>();//если длина этого списочного массива будет > 0 , значит существует хотя бы одна шашка, которая должна есть, её координаты и будут храниться в этом массиве
        //ArrayList<Pair> simple = new ArrayList<Pair>();//здесь будут храниться координаты шашек, которые могут быть "захвачены"

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (fieldArray[i][j].Black) {
                    if ( (i + 1) < 8 && (j - 1) >= 0) {
                        if (fieldArray[i + 1][j - 1].Red || fieldArray[i + 1][j - 1].RedQueen) {
                            if ((i + 2) < 8 && (j - 2) >= 0) {
                                if (fieldArray[i + 2][j - 2].Empty) {
                                    eating.add(new Pair(i, j));
                                }
                            }
                        }
                    }
                    if ( (i + 1) < 8 && (j + 1) < 8) {
                        if (fieldArray[i + 1][j + 1].Red || fieldArray[i + 1][j + 1].RedQueen) {
                            if ((i + 2) < 8 && (j + 2) < 8) {
                                if (fieldArray[i + 2][j + 2].Empty) {
                                    eating.add(new Pair(i, j));
                                }
                            }
                        }
                    }
                }
                if (fieldArray[i][j].BlackQueen) {
                    //лево верх
                    if ((i - 1) >= 0 && (j - 1) >= 0 && (fieldArray[i - 1][j - 1].Red || fieldArray[i - 1][j - 1].RedQueen)) {
                        if ((i - 2) >= 0 && (j - 2) >= 0 && fieldArray[i- 2][j - 2].Empty) {
                            eating.add(new Pair(i, j));
                        }
                    }
                    //право верх
                    if ((i - 1) >= 0 && ( j + 1) < 8 && (fieldArray[i - 1][ j + 1].Red || fieldArray[i - 1][ j + 1].RedQueen)) {
                        if (( i - 2) >= 0 && (j + 2) < 8 && fieldArray[i - 2][j + 2].Empty) {
                            eating.add(new Pair(i, j));
                        }
                    }
                    //низ лево
                    if ((i + 1) < 8 && (j - 1) >= 0 && (fieldArray[i + 1][j - 1].Red || fieldArray[i + 1][j - 1].RedQueen)) {
                        if ((i + 2) < 8 && (j - 2) >= 0 && fieldArray[i + 2][j - 2].Empty) {
                            eating.add(new Pair(i, j));
                        }
                    }
                    //низ право
                    if ((i + 1) < 8 && (j + 1) < 8 && (fieldArray[i + 1][ j + 1].Red || fieldArray[i + 1][ j + 1].RedQueen)) {
                        if (( i + 2) < 8 && (j + 2) < 8 && fieldArray[i + 2][ j + 2].Empty) {
                            eating.add(new Pair(i, j));
                        }
                    }
                }
            }
        }

        if (eating.size() > 0) {
                for (int i = 0; i < eating.size(); i++) {
                    if (eating.get(i).x == x && eating.get(i).y == y) {
                        clearCurrent();
                        fieldArray[x][y].Current = true;
                        return true;
                    }
                }
            }
        else if (fieldArray[x][y].Black && (x + 1) < 8 && (y - 1) >= 0 && fieldArray[x + 1][y - 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }
        else if (fieldArray[x][y].Black && (x + 1) < 8 && (y + 1) < 8 && fieldArray[x + 1][y + 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }

        else if (fieldArray[x][y].BlackQueen && (x - 1) >= 0 && (y - 1) >= 0 && fieldArray[x - 1][y - 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }
        else if (fieldArray[x][y].BlackQueen && (x - 1) >= 0 && (y + 1) < 8 && fieldArray[x - 1][y + 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }
        else if (fieldArray[x][y].BlackQueen && (x + 1) < 8 && (y - 1) >= 0 && fieldArray[x + 1][y - 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }
        else if (fieldArray[x][y].BlackQueen && (x + 1) < 8 && (y + 1) < 8 && fieldArray[x + 1][y + 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }
        return false;
    }

    //функция, проверяет доступно ли поле
    private boolean availableFieldBlack(int x, int y) {
        boolean key = false;
        if ((fieldArray[xBlack][yBlack].BlackQueen || fieldArray[xBlack][yBlack].Black) && (xBlack + 2) < 8 && (yBlack - 2) >= 0 && fieldArray[xBlack + 2][yBlack -2].Empty && (xBlack + 1) < 8 && (yBlack - 1) >= 0 && (fieldArray[xBlack + 1][yBlack - 1].Red || fieldArray[xBlack + 1][yBlack - 1].RedQueen)) {
            key = true;
            if (x == (xBlack + 2) && y == (yBlack - 2)) return true;
        }
        if ((fieldArray[xBlack][yBlack].BlackQueen || fieldArray[xBlack][yBlack].Black) && (xBlack + 2) < 8 && (yBlack + 2) < 8 && fieldArray[xBlack + 2][yBlack + 2].Empty && (xBlack + 1) < 8 && (yBlack + 1) < 8 && (fieldArray[xBlack + 1][yBlack + 1].Red || fieldArray[xBlack + 1][yBlack + 1].RedQueen)) {
            key = true;
            if (x == (xBlack + 2) && y == (yBlack + 2)) return true;
        }
        //для дамки
        if (fieldArray[xBlack][yBlack].BlackQueen && (xBlack - 2) >= 0 && (yBlack - 2) >= 0 && fieldArray[xBlack - 2][yBlack -2].Empty && (xBlack - 1) >= 0 && (yBlack - 1) >= 0 && (fieldArray[xBlack - 1][yBlack -1].Red || fieldArray[xBlack - 1][yBlack -1].RedQueen)) {
            key = true;
            if (x == (xBlack - 2) && y == (yBlack - 2)) return true;
        }
        if (fieldArray[xBlack][yBlack].BlackQueen && (xBlack - 2) >= 0 && (yBlack + 2) < 8 && fieldArray[xBlack - 2][yBlack +2].Empty && (xBlack - 1) >= 0 && (yBlack + 1) < 8 && (fieldArray[xBlack - 1][yBlack +1].Red || fieldArray[xBlack - 1][yBlack +1].RedQueen)) {
            key = true;
            if (x == (xBlack - 2) && y == (yBlack + 2)) return true;
        }

        if (!key) {
            if ((fieldArray[x][y].Empty && x == (xBlack + 1) && y == (yBlack -1)) || (fieldArray[x][y].Empty && x == (xBlack + 1) && y == (yBlack +1))) {
                return true;
            }
            //для дамки
        if ((fieldArray[xBlack][yBlack].BlackQueen && fieldArray[x][y].Empty && x == (xBlack - 1) && y == (yBlack -1)) || (fieldArray[xBlack][yBlack].BlackQueen && fieldArray[x][y].Empty && x == (xBlack - 1) && y == (yBlack +1)) || (fieldArray[xBlack][yBlack].BlackQueen && fieldArray[x][y].Empty && x == (xBlack + 1) && y == (yBlack +1)) || (fieldArray[xBlack][yBlack].BlackQueen && fieldArray[x][y].Empty && x == (xBlack + 1) && y == (yBlack - 1))) {
            return true;
        }
        }
        return false;
    }

    //функция, проверяет, произошло ли съедение шашки
    private boolean eatingBlack(int x, int y) {
        if ((fieldArray[xBlack][yBlack].BlackQueen || fieldArray[xBlack][yBlack].Black) && x == (xBlack + 2) && y == (yBlack - 2) && fieldArray[x][y].Empty && (fieldArray[xBlack + 1][yBlack - 1].Red || fieldArray[xBlack + 1][yBlack - 1].RedQueen)){
            if (fieldArray[xBlack + 1][yBlack - 1].Red) fieldArray[xBlack + 1][yBlack - 1].Red = false;
            else fieldArray[xBlack + 1][yBlack - 1].RedQueen = false;
            fieldArray[xBlack + 1][yBlack - 1].Empty = true;
            return true;
        }
        if ((fieldArray[xBlack][yBlack].BlackQueen || fieldArray[xBlack][yBlack].Black) && x == (xBlack + 2) && y == (yBlack + 2) && fieldArray[x][y].Empty && (fieldArray[xBlack + 1][yBlack + 1].Red || fieldArray[xBlack + 1][yBlack + 1].RedQueen)){
            if (fieldArray[xBlack + 1][yBlack + 1].Red) fieldArray[xBlack + 1][yBlack + 1].Red = false;
            else fieldArray[xBlack + 1][yBlack + 1].RedQueen = false;
            fieldArray[xBlack + 1][yBlack + 1].Empty = true;
            return true;
        }
        //для дамки
        if (fieldArray[xBlack][yBlack].BlackQueen && x == (xBlack - 2) && y == (yBlack - 2) && fieldArray[x][y].Empty && (fieldArray[xBlack - 1][yBlack - 1].Red || fieldArray[xBlack - 1][yBlack - 1].RedQueen)){
            if (fieldArray[xBlack - 1][yBlack - 1].Red) fieldArray[xBlack - 1][yBlack - 1].Red = false;
            else fieldArray[xBlack - 1][yBlack - 1].RedQueen = false;
            fieldArray[xBlack - 1][yBlack - 1].Empty = true;
            return true;
        }
        if (fieldArray[xBlack][yBlack].BlackQueen && x == (xBlack - 2) && y == (yBlack + 2) && fieldArray[x][y].Empty && (fieldArray[xBlack - 1][yBlack + 1].Red || fieldArray[xBlack - 1][yBlack + 1].RedQueen)){
            if (fieldArray[xBlack - 1][yBlack + 1].Red) fieldArray[xBlack - 1][yBlack + 1].Red = false;
            else fieldArray[xBlack - 1][yBlack + 1].RedQueen = false;
            fieldArray[xBlack - 1][yBlack + 1].Empty = true;
            return true;
        }
        return false;
    }
    //ф-ция перемещения шашки
    private void moveBlack(int x, int y) {

         if (fieldArray[xBlack][yBlack].Black) {
            if (x == 7) {
                fieldArray[x][y].BlackQueen = true;
                fieldArray[x][y].Empty = false;
                fieldArray[xBlack][yBlack].Empty = true;
                fieldArray[xBlack][yBlack].Black = false;
            }
            else {
                fieldArray[x][y].Black = true;
                fieldArray[x][y].Empty = false;
                fieldArray[xBlack][yBlack].Empty = true;
                fieldArray[xBlack][yBlack].Black = false;
            }
        }
        else {
            fieldArray[x][y].BlackQueen = true;
                fieldArray[x][y].Empty = false;
                fieldArray[xBlack][yBlack].Empty = true;
                fieldArray[xBlack][yBlack].BlackQueen = false;
        }


        /*
        fieldArray[x][y].Black = true;
        fieldArray[x][y].Empty = false;
        fieldArray[xBlack][yBlack].Empty = true;
        fieldArray[xBlack][yBlack].Black = false;*/
        xBlack = x;
        yBlack = y;
    }

    private boolean continueEatingBlack() {
        if ((xBlack + 2) < 8 && (yBlack - 2) >= 0 && fieldArray[xBlack + 2][yBlack - 2].Empty && fieldArray[xBlack + 1][yBlack - 1].Red) {
            return true;
        }
        if ((xBlack + 2) < 8 && (yBlack + 2) < 8 && fieldArray[xBlack + 2][yBlack + 2].Empty && fieldArray[xBlack + 1][yBlack + 1].Red) {
            return true;
        }
        if (fieldArray[xBlack][yBlack].BlackQueen && (xBlack - 2) >= 0 && (yBlack - 2) >= 0 && fieldArray[xBlack - 2][yBlack - 2].Empty && (fieldArray[xBlack - 1][yBlack - 1].Red || fieldArray[xBlack - 1][yBlack - 1].RedQueen)) {
            return true;
        }
        if (fieldArray[xBlack][yBlack].BlackQueen && (xBlack - 2) >= 0 && (yBlack + 2) < 8 && fieldArray[xBlack - 2][yBlack + 2].Empty && (fieldArray[xBlack - 1][yBlack + 1].Red && fieldArray[xBlack - 1][yBlack + 1].RedQueen)) {
            return true;
        }
        return false;
    }

    private void clearCurrent() {
        for (int i = 0; i < 8; i++ ) {
            for (int j = 0; j < 8; j++) {
                fieldArray[i][j].Current = false;
            }
        }
    }

    //служебный внутренний класс для хранения пар значений координат элементов, хотя можно тупо массив из 2-х integer сделать
    private class Pair {
        public Pair() {
        }
        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public int x;
        public int y;
    }

}