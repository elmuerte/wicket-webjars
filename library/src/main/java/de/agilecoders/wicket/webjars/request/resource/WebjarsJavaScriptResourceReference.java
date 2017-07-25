package de.agilecoders.wicket.webjars.request.resource;

import static de.agilecoders.wicket.webjars.util.WebjarsVersion.useRecent;

import org.apache.wicket.markup.html.resources.JavascriptResourceReference;

import de.agilecoders.wicket.webjars.util.Helper;

/**
 * Static resource reference for javascript webjars resources. The resources are filtered (stripped comments
 * and whitespace) if there is a registered compressor.
 * <p>
 * You are able find out how a specific name looks like on http://www.webjars.org/.
 * </p>
 *
 * @author miha
 */
public class WebjarsJavaScriptResourceReference extends JavascriptResourceReference implements IWebjarsResourceReference {

    private final String originalName;

    /**
     * Construct.
     *
     * @param name The webjars path to load
     */
    public WebjarsJavaScriptResourceReference(final String name) {
        super(WebjarsJavaScriptResourceReference.class, Helper.removeLeadingSlash(useRecent(name)));

        this.originalName = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getOriginalName() {
        return originalName;
    }

    @Override
    public String toString() {
        return "[webjars js resource] " + getOriginalName() + " (resolved name: " + getName() + ")";
    }
}
