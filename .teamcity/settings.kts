import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.sshAgent
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.activeStorage
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.s3Storage
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
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

version = "2021.2"

project {
    description = "Tests settings DSL & Kotlin"

    vcsRoot(HttpsGithubComCheshirrrrTestProjectRefsHeadsMaster)

    buildType(Build1_2)
    buildType(Build)
    buildType(Build1)

    params {
        param("teamcity.internal.storage.s3.upload.numberOfRetries", "1")
    }

    features {
        activeStorage {
            id = "PROJECT_EXT_10"
            activeStorageID = "PROJECT_EXT_9"
        }
        feature {
            id = "PROJECT_EXT_2"
            type = "storage_settings"
            param("storage.name", "google stoarge")
            param("storage.type", "google-storage")
            param("secure:access-key", "credentialsJSON:92484592-b422-4bbd-a641-4e5350dbafa6")
            param("bucket-name", "dkirkhmeier-test")
            param("credentials.type", "key")
        }
        s3Storage {
            id = "PROJECT_EXT_9"
            storageName = "parentS3"
            bucketName = "artifacts.dkirkhmeier.nl"
            bucketPrefix = "settings"
            multipartThreshold = "8MB"
            multipartChunksize = "6MB"
            cloudFrontEnabled = true
            cloudFrontDistribution = "ELP77HKIVPCH0"
            cloudFrontPublicKeyId = "K764NWNQODVAO"
            cloudFrontPrivateKey = "credentialsJSON:d3ae9544-9757-4984-b158-826eb8728123"
            accessKey = "credentialsJSON:92420876-353c-4fe1-90a0-9c3ce6b6fafe"
            awsEnvironment = default {
                awsRegionName = "eu-west-2"
            }
            accessKeyID = "AKIA5JH2VERVF6FH2TFT"
            param("aws.use.default.credential.provider.chain", "")
            param("cloudfrontKeyPairSelect", "K764NWNQODVAO")
            param("cloudfrontDistributionSelect", "ELP77HKIVPCH0")
            param("storage.s3.acl", "BucketOwnerFullControl")
        }
    }
}

object Build : BuildType({
    name = "Build"

    artifactRules = """
        multiupload
        русский.txt
        .gitignore => subfolder1/subfolder2
    """.trimIndent()

    vcs {
        root(HttpsGithubComCheshirrrrTestProjectRefsHeadsMaster)
    }

    steps {
        script {
            scriptContent = """
                mkdir %system.teamcity.build.tempDir%/artifacts
                touch %system.teamcity.build.tempDir%/artifacts/artifact1.json
                echo "##teamcity[publishArtifacts '%system.teamcity.build.tempDir%/artifacts/artifact1.json']"
            """.trimIndent()
        }
    }

    triggers {
        vcs {
        }
    }
})

object Build1 : BuildType({
    name = "Download Artifact"

    vcs {
        root(HttpsGithubComCheshirrrrTestProjectRefsHeadsMaster)
    }

    triggers {
        vcs {
        }
    }

    dependencies {
        artifacts(Build) {
            buildRule = lastSuccessful()
            artifactRules = ".gitignore"
        }
    }
})

object Build1_2 : BuildType({
    name = "Build (1)"

    vcs {
        root(HttpsGithubComCheshirrrrTestProjectRefsHeadsMaster)
    }

    triggers {
        vcs {
        }
    }

    features {
        sshAgent {
            teamcitySshKey = "protected.pem"
        }
    }
})

object HttpsGithubComCheshirrrrTestProjectRefsHeadsMaster : GitVcsRoot({
    name = "https://github.com/cheshirrrr/test-project#refs/heads/master"
    url = "https://github.com/cheshirrrr/test-project"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = password {
        userName = "cheshirrrr"
        password = "credentialsJSON:1b4c3eaa-ea84-4866-97ec-f6608079b009"
    }
})
