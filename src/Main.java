public class Main {
    public static void main(String[] args) {
        Controller ctrl = new Controller();
        View ui = new View(ctrl, "in.txt");
        ui.run();
    }
}
