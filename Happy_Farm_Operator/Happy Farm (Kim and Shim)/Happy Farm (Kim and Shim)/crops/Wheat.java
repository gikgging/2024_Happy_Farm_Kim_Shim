package crops;

import main.Crop;

//Wheat class extending the Crop class.
public class Wheat extends Crop
{

    //Wheat constructor.
    public Wheat()
    {
        super("Wheat", 10.0, 20.0, 3, 12.0, 12.0);//Name, buy price, sell price, dayMax, sunMax, rainMax
    }

}