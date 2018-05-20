package name.mosendz.ilya.iba.consoleapp;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(">");
            String input = scanner.nextLine();
            try {
                Action action = ExecutableFactory.getExecutable(input);
                if (action instanceof Exit) break;
                action.execute();
            } catch (InvalidInput e) {
                System.out.println("Неверный ввод");
            } catch (ActionException e) {
                System.out.println("Во время выполенния команды произошла ошибка.");
            }
        }
    }
}
