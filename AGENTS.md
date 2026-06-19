# Agent Design Contract

## DTO Rules

All DTOs in this project must adhere to the following rules:

1. **Immutability**: All fields are `private final`. No setters.
2. **Full-args constructor**: Every DTO exposes a constructor that initializes every field.
3. **Getters**: Standard JavaBean-style getters for every field.
4. **Wither methods**: For every field, provide a `with-{field}` method (e.g., `withId(String id)`).
   - The method returns a **new copy** of the DTO.
   - The argument replaces the corresponding original field value.
   - All other fields remain unchanged.

## Domain Notes

- `Entity` — interface. Core identity contract. Exposes `String getId()` and `String getName()`.
- `Component` — concrete identity object. Implements `Entity`. Carries a `String id`, `String name`, and a `Realm`.
- `Realm` — scope/context identifier. Implements `Entity`. Carries a `String id` and `String name`.
- `Operator` — class for operations over components. Implements `Entity`. Built via `OperatorBuilder`. Declares operand count, operand list, and operator contract.
- `Artifact` — interface. Exposes `Entity getReferent()`.
