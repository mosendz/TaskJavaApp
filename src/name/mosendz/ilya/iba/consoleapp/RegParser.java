package name.mosendz.ilya.iba.consoleapp;

import com.sun.jna.platform.win32.Advapi32Util;

import static com.sun.jna.platform.win32.WinReg.*;

import java.io.*;
import java.util.Date;

public class RegParser extends Action {

    private final static String OUT = "rk_out.txt";
    private final static String ERR = "rk_err.txt";
    private PrintWriter err, out;
    private HKEY hKey;
    private String path, name;

    RegParser(String param) {
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
            split();
            Object value = Advapi32Util.registryGetValue(hKey, path, name);
            if (value != null) {
                out.println(name + " " + value);
                System.out.println(name + " " + value);
            } else {
                throw new InvalidPathException();
            }
        } catch (FileNotFoundException e) {
            err.println(new Date() + " файл не найден");
            throw new ActionException(e);
        } catch (IOException e) {
            err.println(new Date() + " " + e.getMessage());
            throw new ActionException(e);
        } catch (InvalidPathException e) {
            err.println(new Date() + " " + "неверный путь или ключ не найден");
            throw new ActionException(e);
        }
    }

    private void split() throws InvalidPathException {
        param = param.trim();
        int firstIndex = param.indexOf("\\");
        String hKeyString = param.substring(0, firstIndex).toUpperCase();
        hKey = getHKey(hKeyString);
        int lastIndex = param.lastIndexOf(" ");
        path = param.substring(firstIndex + 1, lastIndex).trim();
        name = param.substring(lastIndex + 1);
    }

    private HKEY getHKey(String hKeyString) throws InvalidPathException {
        HKEY hKey;
        switch (hKeyString) {
            case "HKCR":
            case "HKEY_CLASSES_ROOT":
                hKey = HKEY_CLASSES_ROOT;
                break;
            case "HKCU":
            case "HKEY_CURRENT_USER":
                hKey = HKEY_CURRENT_USER;
                break;
            case "HKLM":
            case "HKEY_LOCAL_MACHINE":
                hKey = HKEY_LOCAL_MACHINE;
                break;
            case "HKDD":
            case "HKEY_DYN_DATA":
                hKey = HKEY_DYN_DATA;
                break;
            case "HKPD":
            case "HKEY_PERFORMANCE_DATA":
                hKey = HKEY_PERFORMANCE_DATA;
                break;
            case "HKU":
            case "HKEY_USER":
                hKey = HKEY_USERS;
                break;
            case "HKCC":
            case "HKEY_CURRENT_CONFIG":
                hKey = HKEY_CURRENT_CONFIG;
                break;
            default:
                throw new InvalidPathException();
        }
        return hKey;
    }

    private class InvalidPathException extends Exception {

    }
}
