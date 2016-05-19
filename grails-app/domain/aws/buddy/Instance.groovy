package aws.buddy

import org.joda.time.DateTime

class Instance implements HasJsonBody {

    String identifier
    String name

    Ami ami
    Subnet subnet

    String privateDnsName
    String publicDnsName
    String privateIpAddress
    String publicIpAddress

    String instanceType

    State state
    DateTime lastUpdated, dateCreated, lastSeen, launchTime, lastMetricCheck

    static hasMany = [tags: Tag, securityGroups: SecurityGroup, metrics: Metric]

    static constraints = {
        identifier unique: true
        privateDnsName nullable: true
        privateIpAddress nullable: true
        publicDnsName nullable: true
        publicIpAddress nullable: true
        name nullable: true
    }

    @Override
    def getJsonBody() {
        [
                id              : id,
                identifier      : identifier,
                ami             : ami,
                name            : name,
                subnet          : subnet,
                state           : state,
                privateDnsName  : privateDnsName,
                publicDnsName   : publicDnsName,
                privateIpAddress: privateIpAddress,
                publicIpAddress : publicIpAddress,
                instanceType    : instanceType,
                tags            : tags,
                dateCreated     : dateCreated,
                lastSeen        : lastSeen,
                launchTime      : launchTime,
                lastMetricCheck : lastMetricCheck
        ]
    }

    private enum State {
        PENDING,
        RUNNING,
        SHUTTING_DOWN,
        TERMINATED,
        STOPPING,
        STOPPED
    }
}
