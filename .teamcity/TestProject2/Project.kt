package TestProject2

import TestProject2.buildTypes.*
import TestProject2.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.activeStorage
import jetbrains.buildServer.configs.kotlin.projectFeatures.s3Storage

object Project : Project({
    id("TestProject2")
    name = "S3 Check"

    vcsRoot(TestProject2_HttpsGithubComCheshirrrrTestProjectGitRefsHeadsMaster)

    buildType(TestProject2_Build)

    features {
        s3Storage {
            id = "PROJECT_EXT_5"
            storageName = "test-project-s3"
            bucketName = "artifacts.dkirkhmeier.nl"
            bucketPrefix = "test2"
            acl = ""
            accessKey = "credentialsJSON:92420876-353c-4fe1-90a0-9c3ce6b6fafe"
            awsEnvironment = default {
                awsRegionName = "eu-west-2"
            }
            accessKeyID = "AKIA5JH2VERVF6FH2TFT"
            param("projectId", "TestProject2")
        }
        activeStorage {
            id = "PROJECT_EXT_6"
            activeStorageID = "PROJECT_EXT_5"
        }
    }
})
