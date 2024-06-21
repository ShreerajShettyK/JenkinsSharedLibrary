// vars/githubPush.groovy
def call() {
    properties([
        pipelineTriggers([
            [
                $class: 'GitHubPushTrigger',
                spec: ''
            ]
        ])
    ])
}
