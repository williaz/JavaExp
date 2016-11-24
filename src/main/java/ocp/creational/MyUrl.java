package ocp.creational;

/**
 * Created by williaz on 11/24/16.
 */
public final class MyUrl {
    private final String scheme;
    private final String authority;
    private final String domain;
    private final String path;
    private final String query; //optional

    // default for builder in same package; or private for static inner builder

    public MyUrl(String scheme, String authority, String domain, String path, String query) {
        this.scheme = scheme;
        this.authority = authority;
        this.domain = domain;
        this.path = path;
        this.query = query;
    }

    @Override
    public String toString() {
        return  scheme + authority + domain + path + query;
    }
}
