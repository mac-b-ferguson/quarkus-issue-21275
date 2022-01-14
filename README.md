# Quarkus issue 21275 reproducer

This project configures 2 datasources with filesystem flyway migrations under ./db/default/migrations and ./db/named_ds/migrations. 

Flyway is configured to start Postgresql in separate containers and run the migrations from the location configured with the datasource.

When quarkus is run, the containers are started but all migrations from both locations are run against both datasources, resulting in the following output:

```2022-01-13 22:08:10,421 ERROR [io.qua.dep.dev.IsolatedDevModeMain] (main) Failed to start quarkus: java.lang.RuntimeException: java.lang.RuntimeException: Failed to start quarkus
	at io.quarkus.dev.appstate.ApplicationStateNotification.waitForApplicationStart(ApplicationStateNotification.java:51)
	at io.quarkus.runner.bootstrap.StartupActionImpl.runMainClass(StartupActionImpl.java:122)
	at io.quarkus.deployment.dev.IsolatedDevModeMain.firstStart(IsolatedDevModeMain.java:145)
	at io.quarkus.deployment.dev.IsolatedDevModeMain.accept(IsolatedDevModeMain.java:456)
	at io.quarkus.deployment.dev.IsolatedDevModeMain.accept(IsolatedDevModeMain.java:67)
	at io.quarkus.bootstrap.app.CuratedApplication.runInCl(CuratedApplication.java:149)
	at io.quarkus.bootstrap.app.CuratedApplication.runInAugmentClassLoader(CuratedApplication.java:105)
	at io.quarkus.deployment.dev.DevModeMain.start(DevModeMain.java:145)
	at io.quarkus.deployment.dev.DevModeMain.main(DevModeMain.java:63)
Caused by: java.lang.RuntimeException: Failed to start quarkus
	at io.quarkus.runner.ApplicationImpl.doStart(Unknown Source)
	at io.quarkus.runtime.Application.start(Application.java:101)
	at io.quarkus.runtime.ApplicationLifecycleManager.run(ApplicationLifecycleManager.java:104)
	at io.quarkus.runtime.Quarkus.run(Quarkus.java:67)
	at io.quarkus.runtime.Quarkus.run(Quarkus.java:41)
	at io.quarkus.runner.GeneratedMain.main(Unknown Source)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:568)
	at io.quarkus.runner.bootstrap.StartupActionImpl$1.run(StartupActionImpl.java:103)
	at java.base/java.lang.Thread.run(Thread.java:833)
Caused by: org.flywaydb.core.api.FlywayException: Found more than one migration with version 0.0.0
Offenders:
-> /Users/macbferguson/github.com/mac-b-ferguson/quarkus-issue-21275/db/default/migrations/V0.0.0__init_default_schema.sql (SQL)
-> /Users/macbferguson/github.com/mac-b-ferguson/quarkus-issue-21275/db/named_ds/migrations/V0.0.0__init_named_schema.sql (SQL)
	at org.flywaydb.core.internal.resolver.CompositeMigrationResolver.checkForIncompatibilities(CompositeMigrationResolver.java:131)
	at org.flywaydb.core.internal.resolver.CompositeMigrationResolver.doFindAvailableMigrations(CompositeMigrationResolver.java:93)
	at org.flywaydb.core.internal.resolver.CompositeMigrationResolver.resolveMigrations(CompositeMigrationResolver.java:83)
	at org.flywaydb.core.internal.resolver.CompositeMigrationResolver.resolveMigrations(CompositeMigrationResolver.java:41)
	at org.flywaydb.core.internal.info.MigrationInfoServiceImpl.refresh(MigrationInfoServiceImpl.java:92)
	at org.flywaydb.core.internal.command.DbMigrate.migrateGroup(DbMigrate.java:170)
	at org.flywaydb.core.internal.command.DbMigrate.lambda$migrateAll$0(DbMigrate.java:138)
	at org.flywaydb.core.internal.database.postgresql.PostgreSQLAdvisoryLockTemplate.execute(PostgreSQLAdvisoryLockTemplate.java:69)
	at org.flywaydb.core.internal.database.postgresql.PostgreSQLConnection.lock(PostgreSQLConnection.java:99)
	at org.flywaydb.core.internal.schemahistory.JdbcTableSchemaHistory.lock(JdbcTableSchemaHistory.java:139)
	at org.flywaydb.core.internal.command.DbMigrate.migrateAll(DbMigrate.java:138)
	at org.flywaydb.core.internal.command.DbMigrate.migrate(DbMigrate.java:98)
	at org.flywaydb.core.Flyway$1.execute(Flyway.java:173)
	at org.flywaydb.core.Flyway$1.execute(Flyway.java:124)
	at org.flywaydb.core.FlywayExecutor.execute(FlywayExecutor.java:214)
	at org.flywaydb.core.Flyway.migrate(Flyway.java:124)
	at io.quarkus.flyway.runtime.FlywayRecorder.doStartActions(FlywayRecorder.java:76)
	at io.quarkus.deployment.steps.FlywayProcessor$createBeansAndStartActions2063183959.deploy_0(Unknown Source)
	at io.quarkus.deployment.steps.FlywayProcessor$createBeansAndStartActions2063183959.deploy(Unknown Source)
	... 12 more```



