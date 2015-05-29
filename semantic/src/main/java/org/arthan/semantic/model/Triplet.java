package org.arthan.semantic.model;

/**
 * Created by artur.shamsiev on 29.05.2015
 */
public class Triplet {

    private Resource subject = new Resource();
    private Resource predicate = new Resource();
    private Resource object = new Resource();

    public static class Resource {
        private String uri;
        private String label;

        public String getUri() {
            return uri;
        }

        public Resource setUri(String uri) {
            this.uri = uri;
            return this;
        }

        public String getLabel() {
            return label;
        }

        public Resource setLabel(String label) {
            this.label = label;
            return this;
        }
    }

    public Resource getSubject() {
        return subject;
    }

    public Resource getPredicate() {
        return predicate;
    }

    public Resource getObject() {
        return object;
    }
}
