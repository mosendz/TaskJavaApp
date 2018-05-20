package name.mosendz.ilya.iba.consoleapp;

class ActionException extends Exception {
    private Exception e;

    ActionException(Exception e) {
        this.e = e;
    }
}
