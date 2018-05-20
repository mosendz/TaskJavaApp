package name.mosendz.ilya.iba.consoleapp;

import java.io.*;
import java.util.Date;
import java.util.regex.Matcher;

class Command extends Action {

    private final static String OUT = "cmd_out.txt";
    private final static String ERR = "cmd_err.txt";
    private PrintWriter err, out;
    private final static String INPUT_CHARSET = "cp866";

    Command(String param) {
        super(param);
    }

    @Override
    void execute() throws ActionException {

        try {
            err = new PrintWriter(new FileWriter(ERR), true);
        } catch (IOException e) {
            throw new ActionException(e);
        }
        try {
            out = new PrintWriter(new FileWriter(OUT), true);
            Process process = Runtime.getRuntime().exec(param);
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                BufferedReader processReader = new BufferedReader(new InputStreamReader(process.getInputStream(), INPUT_CHARSET));
                String str;
                while ((str = processReader.readLine()) != null) {
                    System.out.println(str);
                    out.println(str);
                }
            } else {
                err.println(new Date() + " command returned error " + exitCode);
            }
        } catch (FileNotFoundException e) {
            err.println(new Date() + " файл не найден");
            throw new ActionException(e);
        } catch (IOException | InterruptedException e) {
            err.println(new Date() + " " + e.getMessage());
            throw new ActionException(e);
        } finally {
            if (out != null) out.close();
            if (err != null) err.close();
        }

    }
}
