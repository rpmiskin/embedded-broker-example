package in.misk.timed.route;

import org.apache.camel.spring.SpringRouteBuilder;

public abstract class RouteBuilder extends SpringRouteBuilder {

	public RouteBuilder() {
		super();
	}

	public BeanMethodBuilder methodSignature(final String s) {
		return new BeanMethodBuilder(s);
	}

}