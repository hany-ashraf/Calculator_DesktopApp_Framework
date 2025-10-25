pipeline {
    agent any

    environment {
        // Define tools configured in Jenkins (adjust names to match your setup)
        MAVEN_HOME = tool name: 'Maven 3'
        JAVA_HOME  = tool name: 'JDK 17'
        PATH = "${MAVEN_HOME}/bin:${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {

        stage('Checkout') {
            steps {
                echo "📦 Checking out source code..."
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo "🏗️ Building project with Maven (skipping tests)..."
                sh "${MAVEN_HOME}/bin/mvn clean install -DskipTests=true"
            }
        }

        stage('Run Tests') {
            steps {
                echo "🧪 Running TestNG tests..."
                sh "${MAVEN_HOME}/bin/mvn test -Dsurefire.suiteXmlFiles=testng.xml"
            }
            post {
                always {
                    echo "📊 Publishing TestNG and JUnit reports..."
                    junit '**/target/surefire-reports/*.xml'
                    archiveArtifacts artifacts: '**/test-reports/**/*.html', allowEmptyArchive: true
                }
            }
        }

        stage('Package / Deploy') {
            when {
                branch 'main'
            }
            steps {
                echo "🚀 Main branch build — Packaging & Deployment..."
                sh "${MAVEN_HOME}/bin/mvn clean package"
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        success {
            echo "✅ Pipeline completed successfully!"
        }
        failure {
            echo "❌ Pipeline failed."
        }
        always {
            echo "🧹 Cleaning up workspace..."
            cleanWs()
        }
    }
}
