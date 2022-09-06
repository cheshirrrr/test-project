import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.amazonEC2CloudImage
import jetbrains.buildServer.configs.kotlin.amazonEC2CloudProfile
import jetbrains.buildServer.configs.kotlin.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.buildSteps.exec
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.failureConditions.BuildFailureOnMetric
import jetbrains.buildServer.configs.kotlin.failureConditions.failOnMetricChange
import jetbrains.buildServer.configs.kotlin.projectFeatures.activeStorage
import jetbrains.buildServer.configs.kotlin.projectFeatures.dockerRegistry
import jetbrains.buildServer.configs.kotlin.projectFeatures.s3Storage
import jetbrains.buildServer.configs.kotlin.triggers.vcs

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

version = "2022.04"

project {
    description = "Tests settings DSL & Kotlin"

    buildType(Build)
    buildType(Build2symbolcheck)
    buildType(ImageBuilderTest)
    buildType(Symbol_Check)

    params {
        param("teamcity.internal.storage.s3.upload.enableConsistencyCheck", "true")
        param("teamcity.internal.storage.s3.upload.numberOfRetries", "10")
        param("storage.s3.acl", "bucket-owner-read")
        param("teamcity.internal.storage.s3.upload.retryDelayMs", "10")
    }

    features {
        feature {
            id = "PROJECT_EXT_14"
            type = "OAuthProvider"
            param("awsSessionDuration", "60")
            param("awsStsEndpoint", "https://sts.amazonaws.com")
            param("awsSessionCredentials", "true")
            param("awsRegionName", "eu-west-2")
            param("awsAccessKeyId", "AKIA5JH2VERVF6FH2TFT")
            param("displayName", "Amazon Web Services")
            param("awsIamRoleSessionName", "TeamCity-session")
            param("principalAwsConnectionProjectId", "CloudFrontCheck")
            param("projectFeatureId", "testconnectionID")
            param("awsCredentialsType", "awsAccessKeys")
            param("providerType", "AWS")
            param("secure:awsSecretAccessKey", "credentialsJSON:92420876-353c-4fe1-90a0-9c3ce6b6fafe")
        }
        feature {
            id = "PROJECT_EXT_2"
            type = "storage_settings"
            param("storage.name", "google stoarge")
            param("storage.type", "google-storage")
            param("secure:access-key", "credentialsJSON:b9a1a66e-404a-4007-bffc-22b87f598249")
            param("bucket-name", "dkirkhmeier-test")
            param("credentials.type", "key")
            param("credentials.useSignedUrl", "true")
        }
        s3Storage {
            id = "PROJECT_EXT_20"
            bucketName = "artifacts-dkirkhmeier-nl"
            forceVirtualHostAddressing = true
            multipartThreshold = "6MB"
            multipartChunksize = "6MB"
            cloudFrontUploadDistribution = "E3TUL82J6GQKBR"
            cloudFrontDownloadDistribution = "E26W3X3SO9YMEZ"
            cloudFrontPublicKeyId = "KW4EK93Z1LMQE"
            cloudFrontPrivateKey = "credentialsJSON:8bbc6026-0be4-4a0a-ba8a-272fa03b0aa2"
            awsEnvironment = default {
                awsRegionName = "eu-west-2"
            }
            accessKeyID = "AKIA5JH2VERVF6FH2TFT"
            accessKey = "credentialsJSON:92420876-353c-4fe1-90a0-9c3ce6b6fafe"
        }
        activeStorage {
            id = "PROJECT_EXT_22"
            activeStorageID = "PROJECT_EXT_20"
        }
        amazonEC2CloudImage {
            id = "PROJECT_EXT_23"
            profileId = "amazon-1"
            agentPoolId = "-2"
            name = "test image"
            instanceType = "t3.micro"
            securityGroups = listOf("sg-40c23029")
            customizeLaunchTemplate = false
            launchTemplateCustomAmi = ""
            source = Source("ami-0f3f1515d4ff9e09a")
        }
        amazonEC2CloudImage {
            id = "PROJECT_EXT_25"
            profileId = "amazon-1"
            agentPoolId = "-2"
            name = "second image"
            instanceType = "t2.micro"
            securityGroups = listOf("sg-40c23029")
            customizeLaunchTemplate = false
            launchTemplateCustomAmi = ""
            source = Source("ami-0041a51065d2f1298")
        }
        feature {
            id = "PROJECT_EXT_33"
            type = "storage_settings"
            param("account-name", "dkirkhmeiertest")
            param("storage.name", "dkirkhmeier-test")
            param("storage.type", "azure-storage")
            param("container-name", "test")
            param("secure:account-key", "credentialsJSON:3e8fb3ce-b0da-4af9-b98e-00182144831b")
        }
        dockerRegistry {
            id = "PROJECT_EXT_4"
            name = "Space"
            url = "https://registry.jetbrains.team"
            userName = "Dmitrii.Kirkhmeier"
            password = "credentialsJSON:b6afeacb-a1ee-499e-ab35-0556a5a91339"
        }
        feature {
            id = "PROJECT_EXT_8"
            type = "CloudImage"
            param("subnet", "teamcity-it")
            param("growingId", "false")
            param("agent_pool_id", "-2")
            param("sourceProject", "teamcity-buildserver-agents")
            param("source-id", "teamcity-it-agent-1640177592")
            param("network", "shared-vpc")
            param("preemptible", "false")
            param("zone", "europe-north1-a")
            param("profileId", "google-1")
            param("sourceImage", "teamcity-it-agent-1640177592")
            param("machineCustom", "false")
            param("maxInstances", "1")
            param("imageType", "Image")
            param("machineType", "c2-standard-4")
            param("diskSizeGb", "")
        }
        s3Storage {
            id = "PROJECT_EXT_9"
            storageName = "parentS3"
            bucketName = "artifacts.dkirkhmeier.nl"
            bucketPrefix = "settings"
            forceVirtualHostAddressing = true
            multipartThreshold = "100MB"
            multipartChunksize = "100MB"
            cloudFrontEnabled = true
            cloudFrontUploadDistribution = "EM90WXYV2J1VW"
            cloudFrontDownloadDistribution = "E3OKYJUZRW4C51"
            cloudFrontPublicKeyId = "K1FL2MYX64DCXE"
            cloudFrontPrivateKey = "credentialsJSON:01ccd0f7-5067-4b8d-a384-029219063de8"
            awsEnvironment = default {
                awsRegionName = "eu-west-2"
            }
            accessKeyID = "AKIA5JH2VERVF6FH2TFT"
            accessKey = "credentialsJSON:92420876-353c-4fe1-90a0-9c3ce6b6fafe"
        }
        amazonEC2CloudProfile {
            id = "amazon-1"
            name = "second profile"
            terminateIdleMinutes = 30
            region = AmazonEC2CloudProfile.Regions.EU_WEST_LONDON
            authType = accessKey {
                keyId = "credentialsJSON:7a5d60ce-88ef-4004-8fc0-abc8b2caf77d"
                secretKey = "credentialsJSON:92420876-353c-4fe1-90a0-9c3ce6b6fafe"
            }
        }
        feature {
            id = "google-1"
            type = "CloudProfile"
            param("profileId", "google-1")
            param("profileServerUrl", "")
            param("system.cloud.profile_id", "google-1")
            param("name", "network test")
            param("total-work-time", "")
            param("credentialsType", "key")
            param("description", "")
            param("next-hour", "")
            param("cloud-code", "google")
            param("terminate-idle-time", "30")
            param("enabled", "true")
            param("secure:accessKey", "credentialsJSON:fa163c00-e19b-4933-8242-161708d7b03c")
        }
    }

    subProject(TestProject)
}

object Build : BuildType({
    name = "Build"

    artifactRules = """
        nonexistent2.txt
        русс#%кий.txt
    """.trimIndent()
    publishArtifacts = PublishMode.SUCCESSFUL

    vcs {
        root(AbsoluteId("SshGitGitJetbrainsTeamTeamcityTestProjectTestProjectGit"))
    }

    steps {
        script {
            enabled = false
            scriptContent = """
                mkdir %system.teamcity.build.tempDir%/artifacts
                touch %system.teamcity.build.tempDir%/artifacts/artifact1.json
                echo "##teamcity[publishArtifacts '%system.teamcity.build.tempDir%/artifacts/artifact1.json']"
            """.trimIndent()
        }
        script {
            scriptContent = """
                mkfile -n 20m %system.teamcity.build.tempDir%/big_file
                mkfile -n 5k test+test.txt
                mkdir lots
                cd lots
                touch file{001..005}
                #touch "dmg-JetBrains.Rider-2022.2-EAP1D-222.1591.55.Checked-aarch64.dmg.log"
                #echo "##teamcity[publishArtifacts '%system.teamcity.build.checkoutDir%/lots/dmg-JetBrains.Rider-2022.2-EAP1D-222.1591.55.Checked-aarch64.dmg.log=>macos-logs']"
            """.trimIndent()
        }
    }

    triggers {
        vcs {
        }
    }

    failureConditions {
        failOnMetricChange {
            enabled = false
            metric = BuildFailureOnMetric.MetricType.ARTIFACT_SIZE
            threshold = 10
            units = BuildFailureOnMetric.MetricUnit.PERCENTS
            comparison = BuildFailureOnMetric.MetricComparison.DIFF
            compareTo = build {
                buildRule = lastSuccessful()
            }
        }
    }
})

object Build2symbolcheck : BuildType({
    name = "Build2:symbol+check (parenthesis check)"

    artifactRules = """
        test/package-1.2.3+git1234abc.noarch.rpm
        multiupload
        test+test.txt
    """.trimIndent()

    vcs {
        root(AbsoluteId("SshGitGitJetbrainsTeamTeamcityTestProjectTestProjectGit"))
    }

    steps {
        script {
            enabled = false
            scriptContent = """
                mkdir %system.teamcity.build.tempDir%/artifacts
                touch %system.teamcity.build.tempDir%/artifacts/artifact1.json
                echo "##teamcity[publishArtifacts '%system.teamcity.build.tempDir%/artifacts/artifact1.json']"
            """.trimIndent()
        }
        script {
            scriptContent = """
                mkfile -n 20m %system.teamcity.build.tempDir%/big_file
                mkfile -n 5k test+test.txt
            """.trimIndent()
        }
    }

    triggers {
        vcs {
        }
    }
})

object ImageBuilderTest : BuildType({
    name = "ImageBuilder Test"

    vcs {
        root(AbsoluteId("SshGitGitJetbrainsTeamTeamcityTestProjectTestProjectGit"))
    }

    steps {
        script {
            name = "sending amiartifact message"
            enabled = false
            scriptContent = """
                echo "##teamcity[buildStatisticValue key='someKey' value='443']"
                echo "##teamcity[remoteArtifact amiId='ami-0857aa6981e97d02c' connectionId='PROJECT_EXT_14' type='AWS_AMI']"
            """.trimIndent()
        }
        step {
            name = "Image builder step"
            type = "awsImageBuilder"
            param("cloud.aws.imagebuilder.custom.scripts.inline", "echo 'test'")
            param("cloud.aws.imagebuilder.promotion.cloudProfile.image.id", "PROJECT_EXT_25")
            param("cloud.aws.imageBuilder.packer.version", "1.8.2")
            param("cloud.aws.imagebuilder.tags", """
                Name=Image builder test
                longTag=long tag to check spaces
            """.trimIndent())
            param("cloud.aws.imagebuilder.promotion.cloudProfile.id", "amazon-1")
            param("awsSessionDuration", "60")
            param("cloud.aws.imagebuilder.promotion.cloudProfile.enabled", "true")
            param("awsConnectionId", "PROJECT_EXT_14")
            param("cloud.aws.imagebuilder.base-ami", "ami-0339455b66e82a8ca")
            param("cloud.aws.imagebuilder.instance-type", "t2.nano")
            param("cloud.aws.imagebuilder.custom.scripts.files", """
                scripts/script1.sh
                scripts/script2.sh
            """.trimIndent())
            param("cloud.aws.imagebuilder.subnet-id", "subnet-54716f2c")
        }
        exec {
            name = "writing name"
            enabled = false
            path = "echo"
            arguments = "%teamcity.build.awsImageBuilder.amiId%"
            param("script.content", """echo "1"""")
        }
    }

    triggers {
        vcs {
        }
    }
})

object Symbol_Check : BuildType({
    name = "► Build 2"

    publishArtifacts = PublishMode.SUCCESSFUL

    vcs {
        root(AbsoluteId("SshGitGitJetbrainsTeamTeamcityTestProjectTestProjectGit"))
    }

    steps {
        script {
            enabled = false
            scriptContent = """
                mkdir %system.teamcity.build.tempDir%/artifacts
                touch %system.teamcity.build.tempDir%/artifacts/artifact1.json
                echo "##teamcity[publishArtifacts '%system.teamcity.build.tempDir%/artifacts/artifact1.json']"
            """.trimIndent()
        }
        script {
            enabled = false
            scriptContent = """
                mkfile -n 20m %system.teamcity.build.tempDir%/big_file
                mkfile -n 5k test+test.txt
                mkdir lots
                cd lots
                touch file{001..002}
            """.trimIndent()
        }
        script {
            enabled = false
            scriptContent = """echo "##teamcity[remoteArtifact amiId='ami-05412f54c12b57971' type='awsImageBuilder' region='eu-west-2']""""
        }
        dockerCommand {
            name = "docker test"
            enabled = false
            commandType = build {
                source = content {
                    content = """
                        # syntax=docker/dockerfile:1
                        FROM node:12-alpine
                        RUN apk add --no-cache python2 g++ make
                        WORKDIR /app
                        COPY . .
                        RUN yarn install --production
                        CMD ["node", "src/index.js"]
                        EXPOSE 3000
                    """.trimIndent()
                }
                namesAndTags = "registry.jetbrains.team/p/tc/docker/alpine-node:latest"
            }
        }
        dockerCommand {
            name = "docker push test"
            commandType = push {
                namesAndTags = "registry.jetbrains.team/p/tc/docker/alpine-node:latest"
                removeImageAfterPush = false
            }
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        dockerSupport {
            cleanupPushedImages = true
            loginToRegistry = on {
                dockerRegistryId = "PROJECT_EXT_4"
            }
        }
    }
})


object TestProject : Project({
    name = "Test Project"

    buildType(TestProject_Build)

    features {
        activeStorage {
            id = "PROJECT_EXT_15"
            activeStorageID = "PROJECT_EXT_9"
        }
    }
})

object TestProject_Build : BuildType({
    name = "Build"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        script {
            scriptContent = """echo "building""""
        }
    }

    triggers {
        vcs {
        }
    }
})
