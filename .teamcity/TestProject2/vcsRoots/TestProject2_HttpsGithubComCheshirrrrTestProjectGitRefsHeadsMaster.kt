package TestProject2.vcsRoots

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object TestProject2_HttpsGithubComCheshirrrrTestProjectGitRefsHeadsMaster : GitVcsRoot({
    name = "https://github.com/cheshirrrr/test-project.git#refs/heads/master"
    url = "https://github.com/cheshirrrr/test-project.git"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = password {
        userName = "cheshirrrr"
        password = "credentialsJSON:c3c67b9e-1037-451a-92a1-4fe1a5166ab3"
    }
})
