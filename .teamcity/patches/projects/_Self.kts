package patches.projects

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.S3Storage
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.s3Storage
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the root project
accordingly, and delete the patch script.
*/
changeProject(DslContext.projectId) {
    features {
        val feature1 = find<S3Storage> {
            s3Storage {
                id = "PROJECT_EXT_9"
                bucketName = "artifacts.dkirkhmeier.nl"
                bucketPrefix = "settings"
                cloudFrontEnabled = true
                cloudFrontDistribution = "EGSFHEAS0KG5C"
                cloudFrontPublicKeyId = "K15N40E4L8R788"
                cloudFrontSshKeyType = "PRIVATE_KEY_FILE"
                accessKey = "credentialsJSON:92420876-353c-4fe1-90a0-9c3ce6b6fafe"
                awsEnvironment = default {
                    awsRegionName = "eu-west-2"
                }
                accessKeyID = "AKIA5JH2VERVF6FH2TFT"
                param("storage.s3.cloudfront.privateKey.passphrase", "passphrase")
                param("storage.s3.cloudfront.privateKey.path", "/test/test234")
            }
        }
        feature1.apply {
            param("storage.s3.cloudfront.privateKey.path", "/test/test23")
            param("aws.use.default.credential.provider.chain", "")
        }
    }
}
