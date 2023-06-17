def call() {
    pipeline {
        agent {
            node {
                label 'workstation'
            }
        }
        options {
            ansiColor('xterm')
        }


        stages {
            stage ('Code Quality Check'){
                steps {
                   // sh 'ls -l'
                    //sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.91.157:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.qualitygate.wait=true'
                    sh 'echo Code Quality'
                }
            }
            stage ('Unit Test Cases'){
                steps {
                    sh 'echo Unit Test cases'
                    //sh 'npm test'
                }
            }
            stage ('Checkmarx SAST Scan'){
                steps {
                    sh 'echo Checkmarx SAST Scan'
                }
            }
            stage ('Checkmarx SCA Scan'){
                steps {
                    sh 'echo Checkmarx SCA Scan'
                }
            }
            stage ('Release Application'){
                when{
                    expression{
                        env.TAG_NAME ==~ '.*'
                    }
                }
                steps {

                    sh 'env'
                    sh 'curl -v -u admin:admin123 --upload-file server.js http://52.90.86.151:8081/repository/cart/server.js'
                }
            }
        }
        post {
            always {
                cleanWs()
            }
        }

    }
}