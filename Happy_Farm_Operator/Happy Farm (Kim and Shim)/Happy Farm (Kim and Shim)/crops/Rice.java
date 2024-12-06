package crops;

import main.Crop;

//Rice class extending the Crop class.
public class Rice extends Crop
{

    // Rice constructor.
    public Rice()
    {
        super("Rice", 10.0, 25.0, 3, 12.0, 12.0);//Name, buy price, sell price, dayMax, sunMax, rainMax
    }

}