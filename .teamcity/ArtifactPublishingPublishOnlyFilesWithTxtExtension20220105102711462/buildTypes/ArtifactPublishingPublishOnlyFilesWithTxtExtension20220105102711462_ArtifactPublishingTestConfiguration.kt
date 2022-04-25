package ArtifactPublishingPublishOnlyFilesWithTxtExtension20220105102711462.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script

object ArtifactPublishingPublishOnlyFilesWithTxtExtension20220105102711462_ArtifactPublishingTestConfiguration : BuildType({
    name = "Artifact Publishing Test Configuration"

    artifactRules = "*.txt => "

    steps {
        script {
            scriptContent = "echo hello >> file1.txt"
        }
        script {
            scriptContent = "echo hello >> file2.log"
        }
    }
})
