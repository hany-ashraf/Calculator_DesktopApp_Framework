pipeline {
    agent any

    triggers {
        // Poll the GitHub repo every minute for changes
        pollSCM('* * * * *')
    }

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
                bat 'mvn clean test -DsuiteXmlFile=testng.xml'
            }
        }

        stage('Publish TestNG Report') {
            steps {
                script {
                    // Detect the actual folder dynamically
                    def reportDir = ''
                    if (fileExists('test-output')) {
                        reportDir = 'test-output'
                    } else if (fileExists('test-reports')) {
                        reportDir = 'test-reports'
                    } else {
                        error('❌ No TestNG/ChainTest report folder found!')
                    }

                    echo "✅ Found report folder: ${reportDir}"

                    publishHTML(target: [
                            reportDir: reportDir,
                            reportFiles: 'index.html',
                            reportName: 'Calculator Testcases Report',
                            keepAll: true,
                            alwaysLinkToLastBuild: true,
                            allowMissing: false
                    ])
                }
            }
        }
    }

    post {
        always {
            echo "📦 Archiving test results..."
            archiveArtifacts artifacts: 'test-output/**, test-reports/**', fingerprint: true
        }

        success {
            echo "📧 Sending success email..."
            emailext(
                    subject: "✅ SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                    body: """
                    <h2>✅ TestNG Build Succeeded</h2>
                    <p><b>Job:</b> ${env.JOB_NAME} #${env.BUILD_NUMBER}</p>
                    <p><b>Branch:</b> ${env.GIT_BRANCH}</p>
                    <p><a href="${env.BUILD_URL}">View Build</a></p>
                    <p><a href="${env.BUILD_URL}Calculator_20Testcases_20Report/">View TestNG Report</a></p>
                """,
                    mimeType: 'text/html',
                    to: 'hany.ashraf1729@gmail.com',
                    attachmentsPattern: 'test-output/index.html, test-reports/index.html'
            )
        }

        failure {
            echo "📧 Sending failure email..."
            emailext(
                    subject: "❌ FAILURE: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                    body: """
                    <h2>❌ TestNG Build Failed</h2>
                    <p><b>Job:</b> ${env.JOB_NAME} #${env.BUILD_NUMBER}</p>
                    <p><a href="${env.BUILD_URL}console">${env.BUILD_URL}console</a></p>
                """,
                    mimeType: 'text/html',
                    to: 'hany.ashraf1729@gmail.com'
            )
        }
    }
}
