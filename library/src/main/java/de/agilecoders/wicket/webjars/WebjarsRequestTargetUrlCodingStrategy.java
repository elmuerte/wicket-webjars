/*
 * Copyright 2017, MP Objects, http://www.mp-objects.com
 */
package de.agilecoders.wicket.webjars;

import org.apache.wicket.IRequestTarget;
import org.apache.wicket.request.RequestParameters;
import org.apache.wicket.request.target.coding.IRequestTargetUrlCodingStrategy;
import org.apache.wicket.request.target.coding.SharedResourceRequestTargetUrlCodingStrategy;
import org.apache.wicket.request.target.resource.SharedResourceRequestTarget;

import de.agilecoders.wicket.webjars.request.resource.IWebjarsResourceReference;
import de.agilecoders.wicket.webjars.util.Helper;

/**
 * Add this strategy to your application to allow you to use static /webjars/ urls in your markup. Only needed when not
 * using a Servlet 3+ container.
 *
 * <pre>
 * wicketApplication.mount(new WebjarsRequestTargetUrlCodingStrategy());
 * </pre>
 */
public class WebjarsRequestTargetUrlCodingStrategy extends SharedResourceRequestTargetUrlCodingStrategy implements IRequestTargetUrlCodingStrategy {

	private static final String RESCOURCE_KEY_BASE = IWebjarsResourceReference.class.getName() + Helper.PATH_PREFIX;

	public WebjarsRequestTargetUrlCodingStrategy() {
		this("webjars");
	}

	public WebjarsRequestTargetUrlCodingStrategy(String aMountPoint) {
		super(aMountPoint, aMountPoint);
	}

	@Override
	public IRequestTarget decode(RequestParameters aRequestParameters) {
		final String path = aRequestParameters.getPath().substring(getMountPath().length() + 1);
		aRequestParameters.setResourceKey(RESCOURCE_KEY_BASE + path);
		return new SharedResourceRequestTarget(aRequestParameters);
	}

	@Override
	public CharSequence encode(IRequestTarget aRequestTarget) {
		return super.encode(aRequestTarget);
	}

	@Override
	public boolean matches(IRequestTarget aRequestTarget) {
		return false;
	}

}
