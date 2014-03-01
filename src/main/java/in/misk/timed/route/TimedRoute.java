package in.misk.timed.route;

import java.util.Random;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Example that shows the use of the embedded broker.
 * 
 * <p>
 * Also shows the difference between using a queue vs direct when using a timer.
 * With the queue if the consumer is slow running multiple messages can build up
 * in the queue so you see ping, ping, pong, pong rather than ping, pong, ping,
 * pong.
 * </p>
 * <p>
 * When using direct the timer does not fire again until the slow consumer has
 * completed.
 * </p>
 */
@Component
public class TimedRoute extends SpringRouteBuilder {

	private static final String QUEUE = "embedded:a.queue";
	// private static final String QUEUE = "direct:not.a.queue";
	Random r = new Random();

	@Override
	public void configure() throws Exception {
		from("timer://foo?period=100").log("ping").to(QUEUE);

		from(QUEUE).delay(getDelay()).log("        pong!").stop();

	}

	/** Creates an expression for a random delay. */
	private Expression getDelay() {
		return new Expression() {

			@Override
			public <T> T evaluate(final Exchange arg0, final Class<T> arg1) {
				final Long l = Long.valueOf(r.nextInt(100) + 50);
				T retVal = null;
				if (arg1.isAssignableFrom(l.getClass())) {
					retVal = arg1.cast(l);
				}
				return retVal;
			}
		};
	}

}
