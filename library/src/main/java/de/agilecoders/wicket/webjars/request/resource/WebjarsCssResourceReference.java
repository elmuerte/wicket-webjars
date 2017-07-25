package de.agilecoders.wicket.webjars.request.resource;

import static de.agilecoders.wicket.webjars.util.WebjarsVersion.useRecent;

import org.apache.wicket.markup.html.resources.CompressedResourceReference;

import de.agilecoders.wicket.webjars.util.Helper;

/**
 * Static resource reference for webjars css resources. The resources are filtered (stripped comments and
 * whitespace) if there is registered compressor.
 * <p>
 * You are able find out how a specific path looks like on http://www.webjars.org/.
 * </p>
 *
 * @author miha
 */
public class WebjarsCssResourceReference extends CompressedResourceReference implements IWebjarsResourceReference {

    final String originalName;

    /**
     * Construct.
     *
     * @param name The webjars path to load
     */
    public WebjarsCssResourceReference(final String name) {
        super(WebjarsCssResourceReference.class, Helper.removeLeadingSlash(useRecent(name)));

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
        return "[webjars css resource] " + getOriginalName() + " (resolved name: " + getName() + ")";
    }

}
