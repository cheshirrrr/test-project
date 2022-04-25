package _Self

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.buildReportTab
import jetbrains.buildServer.configs.kotlin.projectFeatures.githubConnection
import jetbrains.buildServer.configs.kotlin.projectFeatures.spaceConnection

object Project : Project({
    description = "Contains all other projects"

    features {
        buildReportTab {
            id = "PROJECT_EXT_1"
            title = "Code Coverage"
            startPage = "coverage.zip!index.html"
        }
        spaceConnection {
            id = "PROJECT_EXT_31"
            displayName = "JetBrains Space"
            serverUrl = "https://jetbrains.team/"
            clientId = "79753b37-bf4c-423a-9e12-cedf2e0d032a"
            clientSecret = "credentialsJSON:17fa24bb-a3a3-4bef-b816-2de6278c9bd8"
        }
        githubConnection {
            id = "PROJECT_EXT_7"
            displayName = "GitHub.com"
            clientId = "e79121591e3ea9b87862"
            clientSecret = "credentialsJSON:e751fcb7-4122-47b9-aa4a-44f10740ed45"
        }
    }

    cleanup {
        baseRule {
            preventDependencyCleanup = false
        }
    }

    subProject(TestProject2.Project)
    subProject(ArtifactPublishingPublishOnlyFilesWithTxtExtension20220105102711462.Project)
    subProject(ArtifactPublishingRuleStartsWithDoubleWildcardAndEndsWithFilePattern20220105095336574.Project)
})
