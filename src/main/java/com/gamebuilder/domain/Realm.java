package com.gamebuilder.domain;

import java.util.Objects;

public final class Realm implements Entity {
    private final String id;
    private final String name;

    public Realm(String id, String name) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public Realm withId(String id) {
        return new Realm(id, this.name);
    }

    public Realm withName(String name) {
        return new Realm(this.id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Realm)) return false;
        Realm realm = (Realm) o;
        return id.equals(realm.id) && name.equals(realm.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Realm{" + "id='" + id + '\'' + ", name='" + name + '\'' + '}';
    }
}
