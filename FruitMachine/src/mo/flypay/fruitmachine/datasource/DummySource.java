package mo.flypay.fruitmachine.datasource;

import java.util.ArrayList;

import android.content.Context;
import mo.flypay.fruitmachine.R;
import mo.flypay.fruitmachine.model.Fruit;
import mo.flypay.fruitmachine.util.BitmapUtil;

/**
 * 
 * @author Mohamed
 * 
 */
public class DummySource extends Source
{
			
	ArrayList<Fruit> fruits = new ArrayList<Fruit>();
	
	public DummySource(Context c){
		super(c);
		generateData();
	}
        
    private void generateData() {
    	fruits.clear();
    	fruits.add(new Fruit(BitmapUtil.getBitmap(context,R.drawable.fruittype_avocado), "Avocado"));
    	fruits.add(new Fruit(BitmapUtil.getBitmap(context,R.drawable.fruittype_burrito), "Burrito"));
    	fruits.add(new Fruit(BitmapUtil.getBitmap(context,R.drawable.fruittype_skeleton), "Skeleton"));
	}


    @Override
    public Fruit getFruit(int index){
       return fruits.get(index);
    }
    
    @Override
    public ArrayList<Fruit> getFruitList(){
        return fruits;
    }
 
}
