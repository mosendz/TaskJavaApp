package name.mosendz.ilya.iba.consoleapp;

import java.util.NoSuchElementException;
import java.util.Scanner;

class ExecutableFactory {

    static Action getExecutable(String input) throws InvalidInput {
        Action exec;
        Scanner scanner = new Scanner(input);
        try {
            String firstToken = scanner.next().toLowerCase();
            if (firstToken.equals("exit")) {
                exec = new Exit(null);
            } else {
                String restOfInput = scanner.nextLine().trim();
                switch (firstToken) {
                    case "-f":
                        exec = new FileParser(restOfInput);
                        break;
                    case "-cmd":
                        exec = new Command(restOfInput);
                        break;
                    case "-rk":
                        exec = new RegParser(restOfInput);
                        break;
                    default:
                        throw new InvalidInput();
                }
            }
        } catch (NoSuchElementException e) {
            throw new InvalidInput();
        }
        return exec;
    }
}
