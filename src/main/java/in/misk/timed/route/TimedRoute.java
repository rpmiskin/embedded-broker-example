package in.misk.timed.route;

import java.util.Random;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TimedRoute extends RouteBuilder {

	@Autowired
	BeanMethodExample bean;

	private static final String QUEUE = "embedded:a.queue";
	// private static final String QUEUE = "direct:not.a.queue";
	Random r = new Random();

	@Override
	public void configure() throws Exception {
		//@formatter:off
		onException(Exception.class).
			useOriginalMessage()
			.log("Exception:")
			.handled(true)
			.setHeader("exception", constant("exception"))
			.to(QUEUE);

		from("timer://foo?period=1000")
			.log("ping")
			.to(QUEUE);

		from(QUEUE)
				.setBody()
				.constant("bodyString")
				.bean(bean, method("method1").done())
				.bean(bean, 
				      method("method2").param("${body}")
									   .param("123").done())
				.delay(getDelay())
				.log("        pong!").stop();
		//@formatter:on
	}

	/** Creates an expression for a random delay. */
	private Expression getDelay() {
		return new Expression() {

			@Override
			public <T> T evaluate(final Exchange arg0, final Class<T> arg1) {
				final Long l = Long.valueOf(1000);
				// if (r.nextBoolean()) {
				// throw new RuntimeException();
				// }
				T retVal = null;
				if (arg1.isAssignableFrom(l.getClass())) {
					retVal = arg1.cast(l);
				}
				return retVal;
			}
		};
	}

}
