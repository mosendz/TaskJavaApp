package name.mosendz.ilya.iba.consoleapp;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class FileParser extends Action {
    private final static String OUT = "file_out.txt";
    private final static String ERR = "file_err.txt";
    private final static Pattern PATTERN = Pattern.compile("^\\S+\\s*=\\s*\\S+$");
    private PrintWriter err, out;
    private BufferedReader reader;
    private Pattern p;

    FileParser(String param) {
        super(param);
    }

    @Override
    public void execute() throws ActionException {

        try {
            err = new PrintWriter(new FileWriter(ERR), true);
        } catch (IOException e) {
            throw new ActionException(e);
        }
        try {
            out = new PrintWriter(new FileWriter(OUT), true);
            reader = new BufferedReader(new FileReader(param));
            String str;
            Matcher m = PATTERN.matcher("");
            while ((str = reader.readLine()) != null) {
                str = str.trim();
                m.reset(str);
                if (m.matches()) {
                    String s[] = str.split("=");
                    s[0] = s[0].trim();
                    s[1] = s[1].trim();
                    out.println(s[0]);
                    out.println(s[1]);
                } else {
                    out.println(str);
                }
            }
            System.out.println("Файл обработан");
            if (reader != null) reader.close();
        } catch (FileNotFoundException e) {
            if (err != null) err.println(new Date() + " файл не найден");
            throw new ActionException(e);
        } catch (IOException e) {
            if (err != null) err.println(new Date() + " " + e.getMessage());
            throw new ActionException(e);
        } finally {
            if (out != null) out.close();
            if (err != null) err.close();
        }

    }
}
