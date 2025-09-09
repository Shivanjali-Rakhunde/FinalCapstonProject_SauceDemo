pipeline {
    agent any
 
    environment {
        BRANCH_NAME = 'main'
        ECLIPSE_WORKSPACE = 'C:\\Users\\Shivanjali\\OneDrive\\Desktop\\SauceDemoAutomation\\SauceDemoAutomation'
        COMMIT_MESSAGE = 'Jenkins: Auto-commit after build'
        GIT_REPO = 'https://github.com/Shivanjali-Rakhunde/FinalCapstonProject_SauceDemo.git'
        GIT_CREDENTIALS_ID = '00a4b0dc-5a83-4bc5-b6ce-6c05579e1bba'
    }
 
    // Auto-trigger every 5 minutes
    triggers {
        pollSCM('H/5 * * * *')
    }
 
    stages {
        stage('Checkout from Git') {
            steps {
                git branch: "${env.BRANCH_NAME}",
                    credentialsId: "${env.GIT_CREDENTIALS_ID}",
                    url: "${env.GIT_REPO}"
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
                bat 'mvn clean test -DsuiteXmlFile=SauceDemoAutomation/testng.xml'
            }
        }
 
        stage('Commit & Push Changes') {
            steps {
                script {
                    echo 'Checking for changes to push...'
                    withCredentials([usernamePassword(
                        credentialsId: "${env.GIT_CREDENTIALS_ID}",
                        usernameVariable: 'GIT_USER',
                        passwordVariable: 'GIT_TOKEN')]) {
 
                        bat """
                            git config user.name "Shivanjali-Rakhunde"
                            git config user.email "shivanjalirakhunde2525@gmail.com"
 
                            git status
                            git add .
 
                            REM Commit only if there are changes
                            git diff --cached --quiet || git commit -m "${COMMIT_MESSAGE}"
 
                            REM Push to your repo using Jenkins credentials
                            git push https://%GIT_USER%:%GIT_TOKEN%@github.com/Shivanjali-Rakhunde/FinalCapstonProject_SauceDemo.git ${BRANCH_NAME}
                        """
                    }
                }
            }
        }
    }
 
    post {
        always {
            // Archive screenshots
            archiveArtifacts artifacts: 'SauceDemoAutomation/test-output/screenshot/*', fingerprint: true
 
            // Publish Cucumber Report
            publishHTML(target: [
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'reports/cucumber-reports',
                reportFiles: 'cucumber-report.html',
                reportName: 'Cucumber Report'
            ])
 
            // Publish Extent Report
            publishHTML(target: [
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'reports/extent-reports',
                reportFiles: 'index.html',
                reportName: 'Extent Report'
            ])
        }
    }
}
