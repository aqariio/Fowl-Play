package aqario.fowlplay.common.util;

public class PathBuilder {
    private final StringBuilder path = new StringBuilder();

    public PathBuilder add(String segment) {
        this.path.append(segment);
        return this;
    }

    public PathBuilder addIf(String segment, boolean condition) {
        if(condition) {
            this.path.append(segment);
        }
        return this;
    }

    public String build() {
        return this.path.toString();
    }
}
