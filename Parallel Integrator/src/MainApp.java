public class MainApp {

    public static void main(String[] args){
        Chef normalChef = new Chef();
        normalChef.makeChicken();

        ItalianChef italianChef = new ItalianChef();
        italianChef.makeSpecial();
    }
}
