package aws.buddy

import com.amazonaws.services.ec2.model.InstanceState
import grails.transaction.Transactional
import org.joda.time.DateTime

@Transactional
class InstanceService {

    AmiService amiService
    SecurityGroupService securityGroupService
    TagService tagService
    SubnetService subnetService

    Instance createFromAws(com.amazonaws.services.ec2.model.Instance awsInstance) {
        Instance instance = new Instance(
                identifier: awsInstance.instanceId,
                instanceType: awsInstance.instanceType
        )
        Ami ami = amiService.getForIdentifier(awsInstance.imageId)
        instance.ami = ami
        instance.subnet = subnetService.getFromAws(awsInstance.subnetId)
        instance.launchTime = new DateTime(awsInstance.launchTime)
        instance.state = getStateFromAws(awsInstance.state)
        instance.lastSeen = instance.state == Instance.State.RUNNING ? DateTime.now() : instance.lastSeen
        instance.lastMetricCheck = DateTime.now().minusMinutes(1)

        instance.privateIpAddress = awsInstance.privateIpAddress
        instance.publicIpAddress = awsInstance.publicIpAddress
        instance.privateDnsName = awsInstance.privateDnsName
        instance.publicDnsName = awsInstance.publicDnsName

        awsInstance.securityGroups.each {
            SecurityGroup securityGroup = securityGroupService.getForAwsSecurityGroup(it)
            if (securityGroup) {
                instance.addToSecurityGroups(securityGroup)
            }
        }

        awsInstance.tags.each {
            Tag tag = tagService.getForAwsTag(it)
            instance.addToTags(tag)
            if(tag.tagKey == 'Name') {
                instance.name = tag.tagValue
            }
        }

        if (instance.validate()) {
            instance.save()
        } else {
            log.error "could not validate Instance, cause: $instance.errors.allErrors"
            return null
        }
        instance
    }

    Instance.State getStateFromAws(InstanceState state) {
        switch (state.name) {
            case 'pending': return Instance.State.PENDING
            case 'running': return Instance.State.RUNNING
            case 'shutting-down': return Instance.State.SHUTTING_DOWN
            case 'terminated': return Instance.State.TERMINATED
            case 'stopping': return Instance.State.STOPPING
            case 'stopped': return Instance.State.STOPPED
            default: log.error "$state.name cannot be parsed as Instance.State"
        }
    }

    List<Instance> list(Filter filter, Map params) {
        Instance.createCriteria().list(params) {
            createAlias('tags', 't')
            ['name', 'identifier'].each {
                if (filter.getProperty(it)) {
                    ilike(it, "%${filter.getProperty(it)}%")
                }
            }

            filter.tags.each {
                ilike('t.tagKey', "%$it.tagKey%")
                ilike('t.tagValue', "%$it.tagKey%")
            }
        }
    }
}
