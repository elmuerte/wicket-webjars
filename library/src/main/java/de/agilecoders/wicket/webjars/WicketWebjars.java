package de.agilecoders.wicket.webjars;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.file.IResourceFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.agilecoders.wicket.webjars.settings.IWebjarsSettings;
import de.agilecoders.wicket.webjars.settings.WebjarsSettings;
import de.agilecoders.wicket.webjars.util.CompositeResourceFinder;
import de.agilecoders.wicket.webjars.util.WebjarsVersion;
import de.agilecoders.wicket.webjars.util.file.WebjarsResourceFinder;

/**
 * Helper class for webjars resources
 *
 * @author miha
 */
public final class WicketWebjars {
	private static final Logger LOG = LoggerFactory.getLogger("wicket-webjars");

	/**
	 * The {@link org.apache.wicket.MetaDataKey} used to retrieve the {@link IWebjarsSettings} from the Wicket
	 * {@link Appendable}.
	 */
	private static final MetaDataKey<IWebjarsSettings> WEBJARS_SETTINGS_METADATA_KEY = new MetaDataKey<IWebjarsSettings>() {
	};

	/**
	 * Checks whether Webjars support is already installed
	 *
	 * @param application
	 *            the wicket application
	 * @return {@code true} if Webjars is already installed, otherwise {@code false}
	 */
	public static boolean isInstalled(Application application) {
		return application.getMetaData(WEBJARS_SETTINGS_METADATA_KEY) != null;
	}

	/**
	 * installs the webjars resource finder and uses a set of default settings.
	 *
	 * @param app
	 *            the wicket application
	 */
	public static void install(final WebApplication app) {
		install(app, null);
	}

	/**
	 * installs the webjars resource finder
	 *
	 * @param app
	 *            the wicket application
	 * @param settings
	 *            the settings to use
	 */
	public static void install(WebApplication app, IWebjarsSettings settings) {
		final IWebjarsSettings existingSettings = settings(app);

		if (existingSettings == null) {
			if (settings == null) {
				settings = new WebjarsSettings();
			}

			app.setMetaData(WEBJARS_SETTINGS_METADATA_KEY, settings);

			final IResourceFinder currentFinder = app.getResourceSettings().getResourceFinder();
			final WebjarsResourceFinder finder = new WebjarsResourceFinder(settings);

			if (currentFinder instanceof CompositeResourceFinder) {
				if (!((CompositeResourceFinder) currentFinder).getList().contains(finder)) {
					((CompositeResourceFinder) currentFinder).getList().add(finder);
				}
			} else {
				app.getResourceSettings().setResourceFinder(new CompositeResourceFinder(currentFinder, finder));
			}

			LOG.info("initialize wicket webjars with given settings: {}", settings);
		}
	}

	public static void reindex(final WebApplication application) {
		IResourceFinder rscFinder = application.getResourceSettings().getResourceFinder();
		List<IResourceFinder> resourceFinders;
		if (rscFinder instanceof CompositeResourceFinder) {
			resourceFinders = ((CompositeResourceFinder) rscFinder).getList();
		} else {
			resourceFinders = Collections.singletonList(rscFinder);
		}
		for (IResourceFinder resourceFinder : resourceFinders) {
			if (resourceFinder instanceof WebjarsResourceFinder) {
				WebjarsVersion.reset();
				WebjarsResourceFinder webjarsResourceFinder = (WebjarsResourceFinder) resourceFinder;
				webjarsResourceFinder.reindex();
				break;
			}
		}
	}

	/**
	 * returns the {@link IWebjarsSettings} which are assigned to given application
	 *
	 * @param app
	 *            The current application
	 * @return assigned {@link IWebjarsSettings}
	 */
	public static IWebjarsSettings settings(final Application app) {
		return app.getMetaData(WEBJARS_SETTINGS_METADATA_KEY);
	}

	/**
	 * returns the {@link IWebjarsSettings} which are assigned to current application
	 *
	 * @return assigned {@link IWebjarsSettings}
	 */
	public static IWebjarsSettings settings() {
		if (Application.exists()) {
			final IWebjarsSettings settings = Application.get().getMetaData(WEBJARS_SETTINGS_METADATA_KEY);

			if (settings != null) {
				return settings;
			} else {
				throw new IllegalStateException(
						"you have to call WicketWebjars.install() before you can use an " + "IWebjarsResourceReference or any other component.");
			}
		}

		throw new IllegalStateException("there is no active application assigned to this thread.");
	}

	/**
	 * private constructor.
	 */
	private WicketWebjars() {
		throw new UnsupportedOperationException();
	}
}
