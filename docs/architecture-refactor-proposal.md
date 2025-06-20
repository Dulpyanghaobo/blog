# Architecture Refactor Proposal

This project initially grouped all business logic under `api/v1` which made it difficult to reason about domain boundaries. To gradually decouple the code base we introduce a `module` package to host independent features.

## Mood Module
- `com.hab.blog.module.mood` contains the controller, service, repository and entities related to user moods.
- All previous classes from `api.v1.moods` have been relocated here with updated package declarations.

Further work should migrate other features such as authentication into their own modules and potentially split the build into multiple Gradle subprojects.
