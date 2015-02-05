package org.mongodb.citybike;

import javax.ws.rs.container.ContainerResponseFilter;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.mongodb.citybike.dao.BasicContractDAO;
import org.mongodb.citybike.dao.BasicStationDAO;
import org.mongodb.citybike.dao.ContractDAO;
import org.mongodb.citybike.dao.StationDAO;

public class CityBikeApplicationBinder extends AbstractBinder {

	 @Override
	    protected void configure() {
	        bind(BasicContractDAO.class).to(ContractDAO.class);
	        bind(BasicStationDAO.class).to(StationDAO.class);
	        bind(AccessControlAllowOriginResponseFilter.class).to(ContainerResponseFilter.class);
	    }

}
