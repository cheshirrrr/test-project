import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.activeStorage
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.s3Storage
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.finishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2021.1"

project {

    vcsRoot(HttpsGithubComCheshirrrrTestProjectGit)

    buildType(CloudArtifacts)
    buildType(DownloadArtifact)

    features {
        s3Storage {
            id = "PROJECT_EXT_2"
            storageName = "test-artifact-s3-storage"
            bucketName = "artifacts.dkirkhmeier.nl"
            bucketPrefix = "test"
            accessKey = "credentialsJSON:92420876-353c-4fe1-90a0-9c3ce6b6fafe"
            awsEnvironment = default {
                awsRegionName = "eu-west-2"
            }
            accessKeyID = "AKIA5JH2VERVF6FH2TFT"
            param("teamcitySshKey", "private_key.pem")
            param("storage.s3.cloudfront.keypair.id", "K15N40E4L8R788")
            param("storage.s3.cloudfront.domain", "d2207kp36y0r1u.cloudfront.net")
            param("storage.s3.cloudfront.enabled", "true")
            param("storage.s3.cloudfront.distribution", "EGSFHEAS0KG5C")
            param("cloudfrontKeyPairSelect", "K15N40E4L8R788")
            param("cloudfrontDistributionSelect", "EGSFHEAS0KG5C")
            param("projectId", "Test")
        }
        activeStorage {
            id = "PROJECT_EXT_3"
            activeStorageID = "PROJECT_EXT_2"
        }
    }
}

object CloudArtifacts : BuildType({
    name = "cloud-artifacts"

    artifactRules = "test-project-1.0-SNAPSHOT.jar"
    publishArtifacts = PublishMode.SUCCESSFUL

    vcs {
        root(HttpsGithubComCheshirrrrTestProjectGit)
    }

    steps {
        maven {
            goals = "clean"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
        }
    }
})

object DownloadArtifact : BuildType({
    name = "download-artifact"

    artifactRules = "pom.xml"

    vcs {
        root(HttpsGithubComCheshirrrrTestProjectGit)
    }

    steps {
        script {
            name = "echo"
            scriptContent = """echo "Downloading artifacts""""
        }
    }

    triggers {
        finishBuildTrigger {
            buildType = "${CloudArtifacts.id}"
            successfulOnly = true
            branchFilter = "master"
        }
    }

    dependencies {
        artifacts(CloudArtifacts) {
            buildRule = lastSuccessful()
            artifactRules = "test-project-1.0-SNAPSHOT.jar"
        }
    }
})

object HttpsGithubComCheshirrrrTestProjectGit : GitVcsRoot({
    name = "https://github.com/cheshirrrr/test-project.git"
    url = "https://github.com/cheshirrrr/test-project"
    branch = "refs/heads/master"
    userNameStyle = GitVcsRoot.UserNameStyle.NAME
    authMethod = password {
        userName = "cheshirrrr"
        password = "credentialsJSON:1b4c3eaa-ea84-4866-97ec-f6608079b009"
    }
    param("oauthProviderId", "PROJECT_EXT_7")
})
