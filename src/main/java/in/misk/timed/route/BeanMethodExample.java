package in.misk.timed.route;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class BeanMethodExample {
	private static Logger LOG = Logger.getLogger(BeanMethodExample.class);

	public void method1(final String str) {
		LOG.info("method1 (" + str + ")");
	}

	public void method2(final String str, final String str2) {
		LOG.info("method1 (" + str + ", " + str2 + ")");
	}
}
