package aws.buddy

class PullDataJob {

    static triggers = {
        cron name: 'periodic pull job',
                jesqueJobName: PullDataJob.simpleName,
                jesqueQueue: "pull",
                cronExpression: "0 * * ? * * *",
                args: []
    }

    Ec2Service ec2Service

    def perform() {
        log.info "pulling data"
        ec2Service.get()
    }
}
