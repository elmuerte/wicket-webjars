/*
 * Copyright 2017, MP Objects, http://www.mp-objects.com
 */
package de.agilecoders.wicket.webjars.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.util.file.IResourceFinder;
import org.apache.wicket.util.resource.IResourceStream;

/**
 * Wicket 1.4 does not support a list of IResourceFinder, this will fake it
 */
public class CompositeResourceFinder implements IResourceFinder {

	protected List<IResourceFinder> list;

	public CompositeResourceFinder() {
		list = new ArrayList<IResourceFinder>();
	}

	public CompositeResourceFinder(IResourceFinder... aFinders) {
		this();
		if (aFinders != null) {
			for (IResourceFinder finder : aFinders) {
				list.add(finder);
			}
		}
	}

	public List<IResourceFinder> getList() {
		return list;
	}

	@Override
	public IResourceStream find(Class<?> aClazz, String aPathname) {
		for (IResourceFinder find : list) {
			IResourceStream res = find.find(aClazz, aPathname);
			if (res != null) {
				return res;
			}
		}
		return null;
	}

}
