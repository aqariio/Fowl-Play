package aqario.fowlplay.common.util;

public class ResourcePathBuilder {
    private final StringBuilder path = new StringBuilder();

    public ResourcePathBuilder add(String segment) {
        this.path.append(segment);
        return this;
    }

    public ResourcePathBuilder addIf(String segment, boolean condition) {
        if(condition) {
            this.path.append(segment);
        }
        return this;
    }

    public String build() {
        return this.path.toString();
    }
}
