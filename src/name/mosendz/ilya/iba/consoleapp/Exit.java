package name.mosendz.ilya.iba.consoleapp;

class Exit extends Action {
    Exit(String param) {
        super(param);
    }

    @Override
    void execute() {
        System.exit(0);
    }
}
