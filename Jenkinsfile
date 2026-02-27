pipeline {
    agent any

    tools {
        maven 'mvn'
        jdk 'jdk'
        allure 'Allure'
    }

    environment {
        ALLURE_RESULTS = "allure-results"
    }

    stages {
        stage('Checkout') {
            steps {
                echo "🔄 Checking out code"
                checkout scm
            }
        }

        stage('Build and Run Tests') {
            steps {
                script {
                    def result = sh(script: 'mvn clean test -Dmaven.test.failure.ignore=true', returnStatus: true)
                    if (result != 0) {
                        echo "❌ Tests failed, but continuing to report generation."
                    } else {
                        echo "✅ All tests passed."
                    }
                }
            }
        }


        stage('Publish Surefire Reports') {
            steps {
                junit 'target/surefire-reports/*.xml'
            }
        }

        stage('Console Output Dump') {
            steps {
                echo "📜 Dumping Surefire output to Jenkins console log..."
                sh 'cat target/surefire-reports/*.txt || true'
            }
        }

        stage('Clean Empty Allure Files') {
            steps {
                echo "🧼 Cleaning empty JSONs in Allure results..."
                sh "find ${ALLURE_RESULTS} -name '*.json' -size 0 -delete || true"
            }
        }

        stage('Generate Allure Report') {
            when {
                expression { fileExists("${ALLURE_RESULTS}") }
            }
            steps {
                echo "🧪 Generating Allure report..."
                allure includeProperties: false, jdk: '', results: [[path: "${ALLURE_RESULTS}"]]
            }
        }
    }

    post {
        always {
            echo '🧹 Cleaning workspace...'
            cleanWs()
        }

        unstable {
            echo '⚠️ Build marked as UNSTABLE (likely due to test failures).'
        }

        failure {
            echo '🚨 Build failed. Check logs and test results.'
        }

        success {
            echo '✅ All tests passed. Build successful.'
        }
    }
}
