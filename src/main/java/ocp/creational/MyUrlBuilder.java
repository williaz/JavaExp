package ocp.creational;

/**
 * Created by williaz on 11/24/16.
 * @see org.springframework.web.util.UriComponentsBuilder
 */
public class MyUrlBuilder {
    private String scheme;
    private String authority;
    private String domain;
    private String path;
    private String query; //optional

    public MyUrlBuilder() {
    }

    public MyUrlBuilder setScheme(String scheme) {
        this.scheme = scheme + "://";
        return this;
    }

    public MyUrlBuilder setAuthority(String authority) {
        this.authority = authority;
        return this;
    }

    public MyUrlBuilder setDomain(String domain) {
        this.domain = "." + domain;
        return this;
    }

    public MyUrlBuilder setPath(String path) {
        this.path = "/" + path;
        return this;
    }

    public MyUrlBuilder setQuery(String query) {
        this.query = "?" + query;
        return this;
    }

    public MyUrl builder() {
        if (scheme == null || authority == null
                || path == null ) { // required field
            throw new IllegalStateException("cannot create URL");
        }
        if (domain == null) domain = ".com"; // set default value
        if (query == null) query = "";

        return new MyUrl(scheme, authority, domain, path, query);
    }
}
