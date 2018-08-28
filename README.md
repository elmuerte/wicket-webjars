# wicket4-webjars

Integration of webjars for Apache Wicket 1.4. This is a backport of wicket-webjars 2.0.4 to Wicket 1.4.x.

Current build status: [![Build Status](https://travis-ci.org/mpobjects/wicket4-webjars.svg)](https://travis-ci.org/mpobjects/wicket4-webjars)

wicket-webjars dependes on [webjars](https://github.com/webjars/webjars).


**Note:** This version is only for wicket 1.4.x. This library is mostly forwards compatible. Once you upgrade to Wicket 6 or later you should be able to replace this library with the standard [wicket-webjars](https://github.com/l0rdn1kk0n/wicket-webjars).

Documentation:

- [Webjars Documentation](http://www.webjars.org/documentation)
- [Available Webjars](http://www.webjars.org)

## Latest Release

[![Maven Central](https://img.shields.io/maven-central/v/com.mpobjects.wicket/wicket4-webjars.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.mpobjects.wicket%22%20AND%20a:%22wicket4-webjars%22)

```xml
<dependency>
  <groupId>com.mpobjects.wicket</groupId>
  <artifactId>wicket4-webjars</artifactId>
  <version>2.0.4</version>
</dependency>
```

## Installation

```java
  /**
   * @see org.apache.wicket.Application#init()
   */
  @Override
  public void init() {
    super.init();

    // install 3 default collector instances
    // (FileAssetPathCollector(WEBJARS_PATH_PREFIX), JarAssetPathCollector, VfsAssetPathCollector)
    // and a webjars resource finder.
    WebjarsSettings settings = new WebjarsSettings();

    WicketWebjars.install(this, settings);
  }
```

## Usage

Add a webjars resource reference (css,js) to your IHeaderResponse:

```java
public WebjarsComponent extends Panel {

  public WebjarsComponent(String id) {
    super(id);
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);

    response.getHeaderResponse().renderJavascriptReference(new WebjarsJavaScriptResourceReference("jquery/1.12.4/jquery.min.js"));
    response.getHeaderResponse().renderCSSReference(new WebjarsCssResourceReference("bootstrap/3.3.7/css/bootstrap.css"));
  }
}
```

Or via header contributors:

```java
public WebjarsComponent extends Panel {

  public WebjarsComponent(String id) {
    super(id);
  }

  @Override
  protected void onInitialize() {
    add(JavascriptPackageResource.getHeaderContribution(new WebjarsJavaScriptResourceReference("jquery/1.12.4/jquery.min.js")));
    add(CSSPackageResource.getHeaderContribution(new WebjarsCssResourceReference("bootstrap/3.3.7/css/bootstrap.css")));
  }
}
```

Add dependencies to your pom.xml:

```xml
<dependencies>
  <dependency>
      <groupId>com.mpobjects.wicket</groupId>
      <artifactId>wicket4-webjars</artifactId>
  </dependency>

  <dependency>
      <groupId>org.webjars</groupId>
      <artifactId>jquery</artifactId>
      <version>1.8.3</version>
  </dependency>
</dependencies>
```

To use always recent version from your pom you have to replace the version in path with the string "current". When resource
name gets resolved this string will be replaced by recent available version in classpath. (this feature is available since 0.2.0)

```java
public WebjarsComponent extends Panel {

  public WebjarsComponent(String id) {
    super(id);
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);

    response.render(JavaScriptHeaderItem.forReference(new WebjarsJavaScriptResourceReference("jquery/current/jquery.js")));
  }
}
```

### SecurePackageResourceGuard

Wicket contains a filter that prevents arbitrarypackage resources from being served. This filter will however break certain webjars packages. For example, webfonts are not included in this filter in wicket 1.4 so you need to add them manually.

In the initialization of your WebApplication simply add the patterns you want to accept

```java
  IPackageResourceGuard packageGuard = getResourceSettings().getPackageResourceGuard();
  if (packageGuard instanceof SecurePackageResourceGuard) {
    SecurePackageResourceGuard secGuard = (SecurePackageResourceGuard) packageGuard;
    secGuard.addPattern("+*.cur");
    secGuard.addPattern("+*.map");

    secGuard.addPattern("+*.svg");

    secGuard.addPattern("+*.eot");
    secGuard.addPattern("+*.ttf");
    secGuard.addPattern("+*.woff");
    secGuard.addPattern("+*.woff2");
  }
```

### Static Linked Resources

In the original wicket-webjars, when running in a Servlet 3+ container you are able to specify URLs like

```html
<img src="webjars/jquery-ui/1.9.2/css/smoothness/images/ui-icons_cd0a0a_256x240.png"/>
```

This does not work by default in wicket4-webjars, as you are probably not running it in a Servlet 3+ container. In order to make this work you can simply mount an additional ```IRequestTargetUrlCodingStrategy``` in your wicket application as follows:

```java
  mount(new WebjarsRequestTargetUrlCodingStrategy());
```

An alternative method is by using the [Servlet 2 setup provided by WebJars](https://www.webjars.org/documentation#servlet2).

*Note:* this functionality is not forwards compatible, as it does not exist in the upstream library.

## Limitations

The following functionality from wicket-webjars 2.x is not supported in this backport:

- CDN

# Authors

   * Michael Haitz
   * Martin Grigorov
   * Michiel Hendriks (wicket 1.4.x backport)

