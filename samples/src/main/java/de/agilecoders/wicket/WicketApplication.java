package de.agilecoders.wicket;

import org.apache.wicket.markup.html.IPackageResourceGuard;
import org.apache.wicket.markup.html.SecurePackageResourceGuard;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

import de.agilecoders.wicket.webjars.WicketWebjars;
import de.agilecoders.wicket.webjars.settings.WebjarsSettings;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start
 * class.
 */
public class WicketApplication extends WebApplication {

	@Override
	public Class<? extends WebPage> getHomePage() {
		return HomePage.class;
	}

	@Override
	public void init() {
		super.init();

		WebjarsSettings settings = new WebjarsSettings();

		WicketWebjars.install(this, settings);

		IPackageResourceGuard packageGuard = getResourceSettings().getPackageResourceGuard();
		if (packageGuard instanceof SecurePackageResourceGuard) {
			SecurePackageResourceGuard secGuard = (SecurePackageResourceGuard) packageGuard;
			// In 1.4 these patterns are not included yet
			// Especially the webfonts break various packages
			secGuard.addPattern("+*.cur");
			secGuard.addPattern("+*.map");

			secGuard.addPattern("+*.svg");

			secGuard.addPattern("+*.eot");
			secGuard.addPattern("+*.ttf");
			secGuard.addPattern("+*.woff");
			secGuard.addPattern("+*.woff2");
		}
	}
}
