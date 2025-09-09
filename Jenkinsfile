pipeline {
    agent any
 
    environment {
        BRANCH_NAME = 'main'
        ECLIPSE_WORKSPACE = 'C:\\Users\\Shivanjali\\OneDrive\\Desktop\\SauceDemoAutomation\\SauceDemoAutomation'
        COMMIT_MESSAGE = 'Jenkins: Auto-commit after build'
    }
 
    // Auto-trigger every 5 mins on Git changes
    triggers {
        pollSCM('* * * * *')
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
                credentialsId: 'PAT_Jenkins',
                usernameVariable: 'GIT_USER',
                passwordVariable: 'GIT_TOKEN')]) {

                bat """
                    "C:\\\\Program Files\\\\Git\\\\bin\\\\git.exe" config user.email "jenkins@pipeline.com"
                    "C:\\\\Program Files\\\\Git\\\\bin\\\\git.exe" config user.name "Jenkins CI"

                    "C:\\\\Program Files\\\\Git\\\\bin\\\\git.exe" status
                    "C:\\\\Program Files\\\\Git\\\\bin\\\\git.exe" add .

                    REM Commit only if there are changes
                    "C:\\\\Program Files\\\\Git\\\\bin\\\\git.exe" diff --cached --quiet || "C:\\\\Program Files\\\\Git\\\\bin\\\\git.exe" commit -m "${COMMIT_MESSAGE}"

                    REM Push using token
                    "C:\\\\Program Files\\\\Git\\\\bin\\\\git.exe" push https://%GIT_USER%:%GIT_TOKEN%@github.com/Shivanjali-Rakhunde/FinalCapstonProject_SauceDemo.git ${BRANCH_NAME}
                """
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
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'reports/cucumber-reports',
                reportFiles: 'cucumber.html',
                reportName: 'Cucumber Report'
            ])
 
            // Publish Extent Report
            publishHTML(target: [
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'reports/extent-reports',
                reportFiles: 'ExtentReport.html',
                reportName: 'Extent Report'
            ])
        }
    }
}
