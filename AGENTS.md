# Agent Design Contract

## DTO Rules

All DTOs in this project must adhere to the following rules:

1. **Immutability**: All fields are `private final`. No setters.
2. **Full-args constructor**: Every DTO exposes a `@JsonCreator` constructor that initializes every field via `@JsonProperty`.
3. **Getters**: Standard JavaBean-style getters for every field.

## Domain Notes

- `Entity` — interface. Core identity contract. Exposes `String getId()` and `String getName()`.
- `Component` — concrete identity object. Implements `Entity`. Carries a `String id`, `String name`, and a `Realm`.
- `Realm` — scope/context identifier. Implements `Entity`. Carries a `String id` and `String name`.
- `Operator` — interface for operations over components. Implements `Entity`. Built via `OperatorBuilder` chain. Declares operand count, operand list, and operator contract. Concrete types: `UnaryOperator`, `BinaryOperator`, `TernaryOperator`.
- `Artifact` — concrete final class. Carries `String entityId` and `String description`. Not an `Entity` — references entities by ID.
