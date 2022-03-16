# Checklist
- [ ] Make sure you merge branch `development` into `main`
# How to test
Run `./gradlew build` to make sure that everything builds properly and the checkstyle does not fail.

Run `./gradlew bootRun`, and on another terminal `./gradlew run` and inspect the current application behavior. Note any inconsistency and make sure the other team members are aware of this issue immediately, as the `main` branch should ALWAYS be working properly.

# Prerequisite merge requests (mark once merged)
List of branches that should have been merged before the weekly marge of `development` into `main`:

<!---
Include checklist of all merge requests corresponding to the issues in the current sprint. 
-->

[//]: <> (
Information should be inserted instead of the comments of the format "<!--- -->". 
If not removed, the comments would still preserve the information containing - for example, commented tasks are still counted by GitLAb, so make sure you delete them.
)