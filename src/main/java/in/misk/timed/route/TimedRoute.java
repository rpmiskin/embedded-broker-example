package in.misk.timed.route;

import java.util.Random;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class TimedRoute extends SpringRouteBuilder {

	private static final String QUEUE = "embedded:destination";
	// private static final String QUEUE = "direct:destination";
	Random r = new Random();

	@Override
	public void configure() throws Exception {
		from("timer://foo?fixedRate=false&period=1000").log("foo!").setBody()
				.constant("body").to(QUEUE);

		from(QUEUE).log("Before").setHeader("delay", getDelay())
				// .log("delay: ${header.delay}")
				.delay(simple("${header.delay}")).log("---------------bar!")
				.stop();

	}

	private Expression getDelay() {
		// TODO Auto-generated method stub
		return new Expression() {

			@Override
			public <T> T evaluate(final Exchange arg0, final Class<T> arg1) {
				final Long l = Long.valueOf(10000);// Long.valueOf(r.nextInt(500)
													// + 85);
				T retVal = null;
				if (arg1.isAssignableFrom(l.getClass())) {
					retVal = arg1.cast(l);
				}
				return retVal;
			}
		};
	}

}
