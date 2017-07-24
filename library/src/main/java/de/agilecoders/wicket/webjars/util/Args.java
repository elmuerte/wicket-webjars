/*
 * Copyright 2017, MP Objects, http://www.mp-objects.com
 */
package de.agilecoders.wicket.webjars.util;

import org.apache.wicket.util.string.Strings;

/**
 * Somewhat a copy of org.apache.wicket.util.lang.Args from Wicket 1.5
 */
public class Args {
	public static <T extends CharSequence> T notEmpty(T argument, String name) {
		if (Strings.isEmpty(argument)) {
			throw new IllegalArgumentException("Argument '" + name + "' may not be null or empty.");
		}
		return argument;
	}

	public static <T> T notNull(final T argument, final String name) {
		if (argument == null) {
			throw new IllegalArgumentException("Argument '" + name + "' may not be null.");
		}
		return argument;

	}
}
