package mo.flypay.fruitmachine.datasource;

import java.util.ArrayList;

import android.content.Context;
import mo.flypay.fruitmachine.model.Fruit;
/**
 * 
 * @author Mohamed
 * abstract class to ease providing machine data
 */
public abstract class Source
{
	Context context;
	ArrayList<Fruit> fruits = new ArrayList<Fruit>();

	public Source(Context c){
		this.context = c;
	}
            
    //getPrize name for index
    public abstract Fruit getFruit(int index);
    
    //getPrize Bitmap for index
    public abstract ArrayList<Fruit> getFruitList();

    
}
