public class ItalianChef extends Chef{ // You have all the properties from Chef, but in this you also have access to this new makePasta method

    public void makePasta(){
        StdOut.println("Chef makes pasta");
    }

    @Override // this is used so that each chef can have their own special dish, it will take the function from Chef and override it
    public void makeSpecial(){
        StdOut.println("Make eggplant parm");
    }
}
