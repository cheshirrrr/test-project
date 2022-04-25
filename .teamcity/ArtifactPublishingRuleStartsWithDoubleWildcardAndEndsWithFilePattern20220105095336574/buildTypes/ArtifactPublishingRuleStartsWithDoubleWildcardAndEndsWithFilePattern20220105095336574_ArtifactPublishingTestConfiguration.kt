package ArtifactPublishingRuleStartsWithDoubleWildcardAndEndsWithFilePattern20220105095336574.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script

object ArtifactPublishingRuleStartsWithDoubleWildcardAndEndsWithFilePattern20220105095336574_ArtifactPublishingTestConfiguration : BuildType({
    name = "Artifact Publishing Test Configuration"

    artifactRules = "**/*.txt => "

    steps {
        script {
            scriptContent = "mkdir folder"
        }
        script {
            scriptContent = "echo hello >> folder/file1.txt"
        }
        script {
            scriptContent = "cd folder && mkdir subfolder"
        }
        script {
            scriptContent = "echo hello >> folder/subfolder/file2.txt"
        }
    }
})
