import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Project {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        processApp();
    }

    public static void processApp() {
        System.out.println("\n\nНачало приложения. Введите свои данные (фамалия, имя, отчество, номер телефона)\n" +
                           "в соответствующем порядке и через пробел без запятых:");
        try{
            String[] arrayInfo = inputInformation();
            writeInformation(arrayInfo);
            System.out.println("Ваши данные успешно записались!!! Желаете продолжить?\n" +
                              "(Да - введите в консоль 1, Нет - введите в консоль 0)\n");
        } catch (MyLengthException e) {
            System.out.printf("%s.\nКоличество введенной информации = %d, а должно быть 4.\n"+
                              "Попробуйте заново!\n", e.getMessage(), e.getLength());
            processApp();
        } catch (MyClassException e) {
            System.out.printf("%s.\nПроверьте введенную информацию и попробуйте еще раз!\n", e.getMessage());
            processApp();
        } catch (MyFileException e) {
            System.out.printf("%s.\nПопробуйте еще раз!\n", e.getMessage());
            processApp();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            processApp();
        }
        
    }

    public static String[] inputInformation() throws MyLengthException {
        String inputStr = sc.nextLine();
        String[] inputArray = inputStr.split(" ");
        if (inputArray.length != 4){
            throw new MyLengthException("Некорректно введена информация (лишняя или недостаток)", inputArray.length);
        }
        return inputArray; 
    }

    public static void writeInformation(String[] arr) throws MyClassException, MyFileException {
        try{

            String firstName = arr[0];
            String secondName = arr[1];
            String lastName = arr[2];
            long number = Integer.parseInt(arr[3]);

            for (int i = 0; i < arr.length - 1; i++) {
                for (char c : arr[i].toCharArray()){
                    if (Character.isDigit(c)){
                        throw new MyClassException("Невозможно преобразовать введенную информацию");
                    }
                }
            }

            String fileName = firstName + ".txt";

            try (FileWriter writer = new FileWriter(fileName,true)){
                writer.write("<"+firstName+">"+"<"+secondName+">"+"<"+lastName+">"+"<"+number+">");
                writer.append("\n");
            } catch (IOException e) {
                throw new MyFileException("Произошла ошибка с чтением - записью файла.");
            }

        } catch (IllegalArgumentException e){
            throw new MyClassException("Невозможно преобразовать введенную информацию");
        }
    }
}


class MyExceptions extends Exception {

    public MyExceptions (String message) {
        super(message);
    }
}

class MyLengthException extends Exception {
    private int length;

    public MyLengthException (String message, int length) {
        super(message);
        this.length = length;
    }

    public int getLength() {
        return length;
    }
}

class MyClassException extends MyExceptions {

    public MyClassException (String message) {
        super(message);
    }
}

class MyFileException extends MyExceptions {

    public MyFileException (String message) {
        super(message);
    }
}