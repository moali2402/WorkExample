package dev.vision.shopping.center.interfaces;

import dev.vision.shopping.center.api.BaseLocation;
import dev.vision.shopping.center.api.Conveyor;
import dev.vision.shopping.center.api.Store;

public interface MapSetup {
	
	public void setTo(BaseLocation t);
	public void setFrom(BaseLocation f);
	public void setNav(BaseLocation f, BaseLocation t);

	public void CustomerService();
	
	public void WC();
	
	public void Escalators();
	
	public void Elevators();
	
	public void ATM();
	
	public void RESET();


}