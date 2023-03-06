# Gradle nedsed cross-components bug

## Structure

```shell
> Task :projects

------------------------------------------------------------
Root project 'gradle-dep-bug'
------------------------------------------------------------

Root project 'gradle-dep-bug'
No sub-projects

Included builds
+--- Included build ':app-one'
\--- Included build ':app-two'

```

```shell
------------------------------------------------------------
Project ':app-one'
------------------------------------------------------------

Project ':app-one'
+--- Project ':app-one:one-proj'
\--- Project ':app-one:one-proj-dep'

Included builds
+--- Included build ':app-one'
\--- Included build ':app-two'
```

```shell
------------------------------------------------------------
Project ':app-two'
------------------------------------------------------------

Project ':app-two'
\--- Project ':app-two:two-proj'
     \--- Project ':app-two:two-proj:two-proj-in'

Included builds
+--- Included build ':app-one'
\--- Included build ':app-two'
```

## Problem

**app-one/one-proj-dep has a dependency to app-two/two-proj:two-proj-in**

The project dependencies are resolved but it doesn't compile:

```shell
❯ ./gradlew app-one:one-proj-dep:assemble
Starting a Gradle Daemon, 1 incompatible and 4 stopped Daemons could not be reused, use --status for details

> Task :app-one:one-proj-dep:compileKotlin FAILED
e: file:///Users/talabes/Documents/repositories/gradle-dep-bug/components/app-one/one-proj-dep/src/main/kotlin/proj/one/dep/Main.kt:3:13 Unresolved reference: two
e: file:///Users/talabes/Documents/repositories/gradle-dep-bug/components/app-one/one-proj-dep/src/main/kotlin/proj/one/dep/Main.kt:6:5 Unresolved reference: Person

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':app-one:one-proj-dep:compileKotlin'.
> A failure occurred while executing org.jetbrains.kotlin.compilerRunner.GradleCompilerRunnerWithWorkers$GradleKotlinCompilerWorkAction
   > Compilation error. See log for more details

* Try:
> Run with --stacktrace option to get the stack trace.
> Run with --info or --debug option to get more log output.
> Run with --scan to get full insights.

* Get more help at https://help.gradle.org

BUILD FAILED in 1m 11s

```
