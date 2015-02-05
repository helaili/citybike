package org.mongodb.citybike;

import org.glassfish.jersey.server.ResourceConfig;

public class CityBikeApplication extends ResourceConfig {
	public CityBikeApplication() {
        register(new CityBikeApplicationBinder());
        packages(true, "org.mongodb.citybike");
    }
}
