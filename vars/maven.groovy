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

        environment {
            NEXUS = credentials('NEXUS')
        }

        stages {
            stage ('Code Compile'){
                steps {
                    sh 'mvn compile'
                }
            }
            stage ('Code Quality Check'){
                steps {
                    //sh 'ls -l'
                    //sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.91.157:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.qualitygate.wait=true -Dsonar.java.binaries=./target'
                    sh 'echo Code Quaity'
                }
            }
            stage ('Unit Test Cases'){
                steps {
                    sh 'echo Unit Test cases'
                    sh 'mvn test'
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

                    sh 'mvn package; cp target/${component}-1.0.jar ${component}.jar'
                    sh 'echo ${TAG_NAME} >VERSION'
                    sh 'zip -r ${component}-${TAG_NAME}.zip ${component}.jar VERSION'
                    sh 'curl -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${component}-${TAG_NAME}.zip http://52.90.86.151:8081/repository/${component}/${component}-${TAG_NAME}.zip'
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