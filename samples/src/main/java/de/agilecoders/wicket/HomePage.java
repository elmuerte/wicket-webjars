package de.agilecoders.wicket;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.protocol.http.WebApplication;

import de.agilecoders.wicket.webjars.WicketWebjars;
import de.agilecoders.wicket.webjars.request.resource.WebjarsCssResourceReference;
import de.agilecoders.wicket.webjars.request.resource.WebjarsJavaScriptResourceReference;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		add(new Link<Void>("reindex") {
			@Override
			public void onClick() {
				WicketWebjars.reindex(WebApplication.get());
			}
		});
	}

	@Override
	public void renderHead(HtmlHeaderContainer response) {
		super.renderHead(response);

		response.getHeaderResponse().renderJavascriptReference(new WebjarsJavaScriptResourceReference("jquery/current/jquery.min.js"));
		response.getHeaderResponse().renderCSSReference(new WebjarsCssResourceReference("bootstrap/current/css/bootstrap.css"));
		response.getHeaderResponse().renderCSSReference(new WebjarsCssResourceReference("bootstrap/current/css/bootstrap-theme.css"));
	}
}
