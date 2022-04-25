package TestProject2.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object TestProject2_Build : BuildType({
    name = "Build"

    artifactRules = """
        русский.txt
        русс#%кий.txt
        file#
    """.trimIndent()

    vcs {
        root(TestProject2.vcsRoots.TestProject2_HttpsGithubComCheshirrrrTestProjectGitRefsHeadsMaster)
    }

    steps {
        maven {
            goals = "clean"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
        }
    }

    triggers {
        vcs {
        }
    }
})
