package pl.gda.pg.ds.sok.filters;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

import pl.gda.pg.ds.sok.utils.PropertiesUtil;

public class CorsFilter implements ContainerResponseFilter {
	
	@Override
    public ContainerResponse filter(ContainerRequest creq, ContainerResponse cresp) {

        cresp.getHttpHeaders().putSingle("Access-Control-Allow-Origin", PropertiesUtil.getProperty("filters.Access-Control-Allow-Origin"));

        return cresp;
    }
}
