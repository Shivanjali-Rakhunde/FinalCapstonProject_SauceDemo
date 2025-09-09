pipeline {
    agent any

    environment {
        BRANCH_NAME = 'main'
        ECLIPSE_WORKSPACE = 'C:\\Users\\Shivanjali\\OneDrive\\Desktop\\SauceDemoAutomation\\SauceDemoAutomation'
        COMMIT_MESSAGE = 'Jenkins: Auto-commit after build'
        GIT_EXE = 'C:\\Program Files\\Git\\bin\\git.exe'   // Change if Git is installed elsewhere
    }

    // Poll GitHub every 5 minutes
    triggers {
        pollSCM('H/5 * * * *')
    }

    stages {
        stage('Checkout from Git') {
            steps {
                git branch: "${env.BRANCH_NAME}",
                    url: 'https://github.com/Shivanjali-Rakhunde/FinalCapstonProject_SauceDemo.git'
            }
        }

        stage('Copy Files from Eclipse Workspace') {
            steps {
                bat """
                echo Copying files from Eclipse workspace...
                xcopy /E /Y /I "${ECLIPSE_WORKSPACE}\\*" "."
                """
            }
        }

        stage('Build & Test') {
            steps {
                bat 'mvn clean test -DsuiteXmlFile=testng.xml'
            }
        }

        stage('Commit & Push Changes') {
            steps {
                script {
                    echo 'Checking for changes to push...'
                    withCredentials([usernamePassword(
                        credentialsId: 'PAT_Jenkins1',
                        usernameVariable: 'GIT_USER',
                        passwordVariable: 'GIT_TOKEN')]) {

                        bat """
                            "${GIT_EXE}" config user.email "jenkins@pipeline.com"
                            "${GIT_EXE}" config user.name "Jenkins CI"

                            "${GIT_EXE}" status
                            "${GIT_EXE}" add .

                            REM Commit only if there are changes
                            "${GIT_EXE}" diff --cached --quiet || "${GIT_EXE}" commit -m "${COMMIT_MESSAGE}"

                            REM Push using token
                            "${GIT_EXE}" push https://%GIT_USER%:%GIT_TOKEN%@github.com/Shivanjali-Rakhunde/FinalCapstonProject_SauceDemo.git ${BRANCH_NAME}
                        """
                    }
                }
            }
        }
    }

    post {
        always {
            // Archive screenshots
            archiveArtifacts artifacts: 'test-output/screenshot/*', fingerprint: true

            // Publish Cucumber Report
            publishHTML(target: [
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'reports/cucumber-reports',
                reportFiles: 'cucumber.html',
                reportName: 'Cucumber Report'
            ])

            // Publish Extent Report
            publishHTML(target: [
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'reports/extent-reports',
                reportFiles: 'ExtentReport.html',
                reportName: 'Extent Report'
            ])
        }
    }
}
