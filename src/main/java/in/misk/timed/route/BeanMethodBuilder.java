package in.misk.timed.route;

import java.util.ArrayList;

/**
 * Builder to make bean method use a little clearer.
 * <p>
 * Rather than doing:<br>
 * bean(obj, "methodName(param1, param2)") you can do bean(obj, method("name")<br>
 * .param("param1")<br>
 * .param("param2")<br>
 * .done())
 * 
 * </p>
 * <p>
 * Work in progress, not sure this actually makes things any clearer.
 * </p>
 * 
 * @author richard
 * 
 */
public class BeanMethodBuilder {
	private final ArrayList<String> params = new ArrayList<String>();
	private final String methodName;

	BeanMethodBuilder(final String methodName) {
		this.methodName = methodName;
	}

	BeanMethodBuilder param(final String p) {
		params.add(p);
		return this;
	}

	String done() {
		final StringBuilder b = new StringBuilder(methodName);
		if (!params.isEmpty()) {
			b.append("(");
			for (int i = 0; i < params.size(); i++) {
				if (i > 0) {
					b.append(",");
				}
				b.append(params.get(i));
			}

			b.append(")");
		}
		return b.toString();
	}
}