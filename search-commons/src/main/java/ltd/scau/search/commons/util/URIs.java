package ltd.scau.search.commons.util;

import java.net.URI;

/**
 * 直接使用URI::resolve方法会出现很多问题，封装一些情况的处理方法
 *
 * @author Weijie Wu
 */
public class URIs {

    public static URI resolve(final URI uri, final String path) {

        URI newURI = fix(uri);
        URI pathURI = URI.create(path);

        if (pathURI.getHost() != null) {
            newURI = fix(pathURI);
        } else if (path.startsWith("?")) {
            String s = newURI.toString();
            newURI = URI.create(s.contains("?") ? s.replaceAll("\\?.*", path) : s + path);
        } else if (path.startsWith("#")) {
            // abandon
        } else if (path.startsWith("mailto:")) {
            // abandon
        } else {
            newURI = newURI.resolve(path);
        }

        return newURI;
    }

    public static URI fix(URI u) {
        return fixPath(trimHash(URI.create(u.toString())));
    }

    public static URI fixPath(URI u) {
        String path;
        return (path = u.getPath()) == null || path.trim().isEmpty() ? u.resolve("/") : u;
    }

    public static URI trimHash(URI u) {
        String s = u.toString();
        int i = s.indexOf('#');
        return URI.create(i > 0 ? s.substring(0, i) : s);
    }
}
