! This repo is entirely AI-generated.

# GameBuilder

A primitive game definition studio PoC — define game ontologies (realms, components, operators) with algebraic contracts, compose definitions, validate coherence, and get AI assistance via Ollama.

---

## Setup

### Build

```bash
mvn clean package
```

Produces an uber-jar at `target/gamebuilder-0.21.37-runner.jar`.

### Run

```bash
java -jar target/gamebuilder-0.21.37-runner.jar
```

Or rename `prod.application.properties.sample` to `prod.application.properties` and use the included script:

```bash
sh run.sh
```


### Dev run
```bash
mvn quarkus:dev
```

Builds and starts the application in Quarkus dev mode (hot reload, stuff)

### External configuration

Supply a properties file at runtime:

```bash
QUARKUS_CONFIG_LOCATIONS=prod.application.properties java -jar target/gamebuilder-1.0-SNAPSHOT-runner.jar
```

### Configuration properties

| Property | Default | Description |
|---|---|---|
| `quarkus.http.host` | `127.0.0.1` | interface |
| `quarkus.http.port` | `2157` | HTTP listen port |
| `ollama.host` | `http://localhost:11434` | Ollama server URL |
| `ollama.api.key` | `ollama` | API key sent as `Authorization: Bearer` |
| `ollama.model` | `llama3` | Model name passed in chat completion requests |
| `ollama.cooldown.ms` | `5000` | Minimum delay between successive AI requests |
| `ollama.request.cap` | `2000` | Maximum total character count of the full prompt sent to Ollama (palette context + user prompt) |
| `ollama.max.user.prompt.length` | `1000` | Maximum length of the user's text in the Ask AI box |


---

## Usage

Open `http://localhost:2157` in a browser. The Studio page lets you build a game ontology interactively.

### Concepts

**Realm** — A scope or category bucket. Examples: `item`, `area`, `coordinate`.

**Component** — A concrete thing within a realm. Examples: `player` (in `item`), `field` (in `area`), `granular_time` (in `coordinate`).

**Operator** — A relation over components, with 1–3 operand slots and an algebraic contract (14 boolean properties: reflexive, irreflexive, antisymmetric, asymmetric, idempotent, involutive, monotonic, associative, cancellative, distributive, transitive, identity element, inverse element, absorbing element). Examples: `occupy(player, wall, enemy || field)`, `kill(explosion, enemy || player, enemy)`.

**Artifact** — A human-readable description attached to any entity.

**Example** — A minimal 1D-Snake ontology:

| Type | Name | Realm | Slots |
|---|---|---|---|
| Realm | `item` | — | — |
| Realm | `area` | — | — |
| Component | `head` | item | — |
| Component | `body` | item | — |
| Component | `tail` | item | — |
| Component | `wall` | item | — |
| Component | `field` | area | — |
| Operator | `occupation` | — | `body, wall` → `field` |
| Operator | `presence` | — | `head, body, wall` → `field` |
| Operator | `adjacency` | — | `field` → `field` |
| Operator | `chain` | — | `head, body` → `body, tail` |
| Operator | `momentum` | — | `head` |
| Operator | `voiding` | — | `tail` |
| Operator | `collision` | — | `head` → `wall` |

please note that a 1D-snake cannot collide with its body. Thus, collusion should not be defined as:

| Type | Name | Realm | Slots |
|---|---|---|---|
| Operator | `collision` | — | `head` → `body, wall` |

### Validation

Click **Compose & Validate** to check coherence of the system.

### Ask AI

The **Ask AI** box sends your prompt to Ollama along with the full current palette context (realms, components, operators, artifacts). The LLM sees everything you've defined and answers the prompt.
