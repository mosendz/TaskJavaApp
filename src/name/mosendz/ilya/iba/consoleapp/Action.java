package name.mosendz.ilya.iba.consoleapp;

abstract class Action {
    String param;

    Action(String param) {
        this.param = param;
    }

    abstract void execute() throws ActionException;
}
