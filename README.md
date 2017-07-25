wicket-webjars-legacy
=====================

Integration of webjars for Apache Wicket. This is a backport of wicket-webjars 2.0.4 to Wicket 1.4.x.

Current build status: [![Build Status](https://travis-ci.org/elmuerte/wicket-webjars-legacy14.svg?branch=master)](https://travis-ci.org/elmuerte/wicket-webjars-legacy14)

wicket-webjars dependes on [webjars](https://github.com/webjars/webjars).


**Note** This version is only for wicket 1.4.x.

For newer versions of wicket please use the original [wicket-webjars](https://github.com/l0rdn1kk0n/wicket-webjars).


Documentation:

- [Webjars Documentation](http://www.webjars.org/documentation)
- [Available Webjars](http://www.webjars.org)

Add maven dependency:

```xml
<dependency>
  <groupId>de.agilecoders.wicket.webjars</groupId>
  <artifactId>wicket-webjars-legacy14</artifactId>
  <version>2.0.4</version>
</dependency>
```

Installation:

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

Usage
=====

Add a webjars resource reference (css,js) to your IHeaderResponse:

```java
public WebjarsComponent extends Panel {

  public WebjarsComponent(String id) {
      super(id);
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);

    response.render(JavaScriptHeaderItem.forReference(new WebjarsJavaScriptResourceReference("jquery/1.8.3/jquery.js")));
  }
}
```

Add dependencies to your pom.xml:

```xml
<dependencies>
  <dependency>
      <groupId>de.agilecoders.wicket.webjars</groupId>
      <artifactId>wicket-webjars-legacy14</artifactId>
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

SecurePackageResourceGuard
==========================

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

Limitations
===========

The following functionality from wicket-webjars 2.x is not supported in this backport:

- CDN
- /webjar/ urls in the HTML; this depends on Servlet 3 containers


Authors
-------

[![Ohloh profile for Michael Haitz](https://www.openhub.net/accounts/l0rdn1kk0n/widgets/account_detailed.gif)](https://www.openhub.net/accounts/l0rdn1kk0n?ref=Detailed)

[![Ohloh profile for Martin Grigorov](https://www.openhub.net/accounts/mgrigorov/widgets/account_detailed.gif)](https://www.openhub.net/accounts/mgrigorov?ref=Detailed)

[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/l0rdn1kk0n/wicket-webjars/trend.png)](https://bitdeli.com/free "Bitdeli Badge")

Michiel Hendriks (wicket 1.4.x backport)

