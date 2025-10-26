pipeline {
    agent any

    tools {
        maven 'Maven 3'
        jdk 'JDK 17'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/hany-ashraf/Calculator_DesktopApp_Framework.git'
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Publish TestNG Report') {
            steps {
                // Publish HTML TestNG report in Jenkins UI
                publishHTML([
                        reportDir: 'test-output',
                        reportFiles: 'index.html',
                        reportName: 'TestNG HTML Report',
                        keepAll: true,
                        alwaysLinkToLastBuild: true,
                        allowMissing: true
                ])
            }
        }
    }

    post {
        always {
            // Archive TestNG report files
            archiveArtifacts artifacts: 'test-output/**', fingerprint: true
        }

        success {
            emailext(
                    subject: "✅ SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                    body: """
                    <h2>✅ TestNG Build Succeeded</h2>
                    <p>Job: ${env.JOB_NAME} #${env.BUILD_NUMBER}</p>
                    <p>Branch: ${env.GIT_BRANCH}</p>
                    <p><a href="${env.BUILD_URL}">View Build</a></p>
                    <p><a href="${env.BUILD_URL}TestNG_20HTML_20Report/">View TestNG Report</a></p>
                """,
                    mimeType: 'text/html',
                    to: 'youremail@gmail.com',
                    attachmentsPattern: 'test-output/index.html'
            )
        }

        failure {
            emailext(
                    subject: "❌ FAILURE: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                    body: """
                    <h2>❌ TestNG Build Failed</h2>
                    <p>Job: ${env.JOB_NAME} #${env.BUILD_NUMBER}</p>
                    <p><a href="${env.BUILD_URL}console">${env.BUILD_URL}console</a></p>
                """,
                    mimeType: 'text/html',
                    to: 'youremail@gmail.com',
                    attachmentsPattern: 'test-output/index.html'
            )
        }
    }
}
