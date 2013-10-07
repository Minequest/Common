package com.theminequest.doc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DocArguments {
	
	/**
	 * Returns a list of arguments, in the format:
	 * <pre>Description</pre> and <pre>TYPE</pre>,
	 * Where <code>TYPE</code> is one of the following:
	 * <ul>
	 * <li>INT</li>
	 * <li>FLOAT</li>
	 * <li>STRING</li>
	 * <li>INTLOC</li>
	 * <li>FLOATLOC</li>
	 * <li>TASK</li>
	 * <li>EVENT</li>
	 * <li>REQUIREMENT</li>
	 * <li>TARGET</li>
	 * </ul>
	 * @return a list of arguments that this takes
	 */
	String[] arguments();
	DocArgType[] types();
	
}
