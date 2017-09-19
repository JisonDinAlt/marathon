import java.lang.String;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ParserDemo {
    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();
        Scanner in = new Scanner(System.in);
        System.out.println("Please, insert file address");
        String textAddress = in.nextLine();
        try(FileReader reader = new FileReader(textAddress))
        {
            int c;
            while((c=reader.read())!=-1){
                builder.append((char)c);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        String inputString = builder.toString().toLowerCase (); //переводим текс в строчный формат, для корректности сравнения слов
       BracketsChecking (inputString); // метод для определения правильности скобок
       TopTenWords (inputString); // метод для вывода топ-10 слов
    }
    public static void BracketsChecking (String inputData) {
        char brackets[][] = {{'(', '{', '['}, {')', '}', ']'}}; // создаем двумерный массив со всеми видами скобок, открывающие скобки в 0 строке, закрывающие - в 1
        boolean resultFlag = true;
        int textLength = inputData.length();
        int left = 0, right = 0;
        for (int j = 0; j <= brackets.length; j++) {
            for (int i = 0; i < textLength; i++) {
                if (inputData.charAt(i) == brackets[0][j]) { //считаем количество открывающих скобок
                    left++;
                }
                if (inputData.charAt(i) == brackets[1][j]) { //считаем количество закрывающих скобок
                    right++;
                }
                if (right > left) {
                    resultFlag = false;
                    break; // если в любой момент вполнения программы закрывающих скобок любого типа окажется больше чем открывающих, условие не выполняется
                }
            }
            if (left != right) {
                resultFlag = false;
                break;  // если на момент проверки текста подсчитанное количество открывающих и закрывающих скобок одного типа не совпадает, условие не выполняется
            }
            left = 0;
            right = 0;
        }
        if (resultFlag) {
            System.out.println("correct");
        } else {
            System.out.println("incorrect");
        }
    }
    public static void TopTenWords (String inputData)
    {
        class Word { //создаем класс Word со свойствами имя (слово из текста) и индекс (количество повторений слова в тексте)

            public String wordinput;
            public int index;
            Word (String wordinput, int index)
            {
                this.wordinput = wordinput;
                this.index = index;
            }
            public String getName() {
                return this.wordinput;
            }

            public void setName(String word) {
                this.wordinput=word;
            }

            public int getIndex() {
                return this.index;
            }

            public void setName(int index) {
                this.index=index;
            }
        }
        String n = inputData.replaceAll("(?U)[\\pP]", " ").replaceAll("\\s+", " "); // убираем из текста всю пунктуацию и лишние пробелы
        String tempWords[] = n.split (" "); //создаем временный массив слов из предложения
        List<Word> listWord = new ArrayList<>(); // создаем коллекцию объектов-слов
            for (int j =0; j < tempWords.length; j++) { // проходим все слова текста
                if (tempWords[j].length() > 2) { //отсеиваем союзы, предлоги и местоимения - слова длинной меньше 2-х
                    int tempCounter = 0;
                    for (int ji = 0; ji < tempWords.length; ji++) { // считаем количество повторений текущего слова в тексте
                        if (tempWords[j].equals(tempWords[ji])) {
                            tempCounter++;
                        }
                    }
                    boolean existObject = true;
                    Word w = new Word(tempWords[j], tempCounter); // создаем новый объекст-слово
                    for(Word w1 :listWord) { // проверяем содержится ли новый объект-слово в коллекции, если не содерджитс - добавляем только что созданный
                        if (w1.getName().equals(w.getName())) {existObject = false;}
                    }
                    if (existObject) {
                        listWord.add(w);
                    }
                }
            }
            Comparator<Word> comparator = (w1, w2) -> Integer.compare(w1.getIndex(), w2.getIndex()); //создаем компаратор для сравнения индексов
            listWord.sort(comparator.reversed()); // сортируем коллекцию по убыванию при помощи компоратора
        for (Word x : listWord.subList(0,10)  )System.out.println (x.getName()); // выводим первых 10 элементов коллекции (топ-10 повторяющихся слов)
    }
}