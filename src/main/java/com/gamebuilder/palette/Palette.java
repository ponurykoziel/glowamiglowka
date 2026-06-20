package com.gamebuilder.palette;

import com.gamebuilder.utils.BijectMap;
import com.gamebuilder.domain.Component;
import com.gamebuilder.domain.Entity;
import com.gamebuilder.domain.Realm;
import com.gamebuilder.operator.Operator;

import java.util.List;
import java.util.Objects;

public final class Palette {
    private final String id;
    private final BijectMap<String, Component> components;
    private final BijectMap<String, Realm> realms;
    private final BijectMap<String, Operator> operators;

    public Palette(String id) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.components = new BijectMap<>();
        this.realms = new BijectMap<>();
        this.operators = new BijectMap<>();
    }

    public String getId() {
        return id;
    }

    public BijectMap<String, Component> getComponents() {
        return new BijectMap<>(components);
    }

    public BijectMap<String, Realm> getRealms() {
        return new BijectMap<>(realms);
    }

    public BijectMap<String, Operator> getOperators() {
        return new BijectMap<>(operators);
    }

    public Entity findEntity(String id) {
        Entity entity = components.getByKey(id);
        if (entity != null) return entity;
        entity = realms.getByKey(id);
        if (entity != null) return entity;
        return operators.getByKey(id);
    }

    // The instanceof cascade below is the correct approach for this PoC.
    // A visitor pattern would be over-engineered here: the entity type set
    // (Component, Realm, Operator) is fixed and extremely unlikely to expand,
    // and adding a visitor would ripple through half the source code for no
    // practical benefit in a simple proof of concept.
    public void add(Entity entity) {
        if (entity instanceof Component) {
            components.put(entity.getId(), (Component) entity);
        } else if (entity instanceof Realm) {
            realms.put(entity.getId(), (Realm) entity);
        } else if (entity instanceof Operator) {
            operators.put(entity.getId(), (Operator) entity);
        } else {
            throw new IllegalArgumentException("Unsupported entity type: " + entity.getClass());
        }
    }

    public void addAll(List<Entity> entities) {
        for (Entity entity : entities) {
            add(entity);
        }
    }

    public void remove(String id) {
        components.removeByKey(id);
        realms.removeByKey(id);
        operators.removeByKey(id);
    }

    public void remove(Entity entity) {
        if (entity instanceof Component) {
            components.removeByKey(entity.getId());
        } else if (entity instanceof Realm) {
            realms.removeByKey(entity.getId());
        } else if (entity instanceof Operator) {
            operators.removeByKey(entity.getId());
        } else {
            throw new IllegalArgumentException("Unsupported entity type: " + entity.getClass());
        }
    }

    public void clear() {
        components.clear();
        realms.clear();
        operators.clear();
    }

    public Palette withId(String id) {
        Palette copy = new Palette(id);
        copy.components.putAll(this.components);
        copy.realms.putAll(this.realms);
        copy.operators.putAll(this.operators);
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Palette)) return false;
        Palette palette = (Palette) o;
        return id.equals(palette.id) && components.equals(palette.components) && realms.equals(palette.realms) && operators.equals(palette.operators);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, components, realms, operators);
    }

    @Override
    public String toString() {
        return "Palette{" + "id='" + id + '\'' + ", components=" + components + ", realms=" + realms + ", operators=" + operators + '}';
    }
}
