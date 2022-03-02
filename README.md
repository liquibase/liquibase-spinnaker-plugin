# Liquibase Spinnaker Job Plugin
Spinnaker plugin for configuring and running Liquibase jobs.  

- Add Liquibase to the spinnaker plugin list by modifying the halyard config as provided below. Refer: [plugin-v2-configuration](https://spinnaker.io/guides/user/plugins/#plugin-v2-configuration-changes)

```
  spinnaker:
    extensibility:
      plugins:
        Liquibase.PreconfiguredJobPlugin:
          id: Liquibase.PreconfiguredJobPlugin
          enabled: true
          extensions: {}
      repositories:
        spinnaker-plugins-repository:
          id: spinnaker-plugins-repository
          url: https://raw.githubusercontent.com/mcred/test-spinnaker-plugin/master/plugins.json
```


## Development
The run job plugin has 3 parts: the docker container, the yaml configuration, and the versioned jar for release. Main development will be between the Dockerfile and yaml config. The kotlin file `LiquibasePlugin.kt` contains boilerplate code and loads the `liquibase.yaml` config file. Very little, if any, development should be needed to this file.  
#### Dockerfile
Based on the latest `liquibase/liquibase` image, the dockerfile adds a new entry point to setup the container runtime based on passed in environment variables from the yaml config. `entry.sh` checks for expected environment variables and sets up the container before running the standard liquibase entrypoint.   
#### liquibase.yaml
Defines all the environment variables passed to container, the container image to use, and also mounts the host kerberos files to the container `/etc` directory.

## Releasing
* jdk11 is used for the build
* bump version in `build.gradle`
* run `./gradlew clean releaseBundle`
* upload zip from `./build/distributions/` to artifactory
* update `plugins.json` with contents of `./build/distributions/plugin-info.json`
* add `url` attribute with address of zip file in artifactory