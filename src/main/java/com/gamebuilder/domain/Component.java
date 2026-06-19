package com.gamebuilder.domain;

import java.util.Objects;

public final class Component implements Entity {
    private final String id;
    private final String name;
    private final Realm realm;

    public Component(String id, String name, Realm realm) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.realm = Objects.requireNonNull(realm, "realm must not be null");
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public Realm getRealm() {
        return realm;
    }

    public Component withId(String id) {
        return new Component(id, this.name, this.realm);
    }

    public Component withName(String name) {
        return new Component(this.id, name, this.realm);
    }

    public Component withRealm(Realm realm) {
        return new Component(this.id, this.name, realm);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Component)) return false;
        Component component = (Component) o;
        return id.equals(component.id) && name.equals(component.name) && realm.equals(component.realm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, realm);
    }

    @Override
    public String toString() {
        return "Component{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", realm=" + realm + '}';
    }
}
