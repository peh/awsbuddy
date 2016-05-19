package aws.buddy

import com.amazonaws.services.ec2.model.GroupIdentifier
import grails.transaction.Transactional

@Transactional
class SecurityGroupService {

    SecurityGroup getForAwsSecurityGroup(GroupIdentifier awsSecGroup) {
        SecurityGroup securityGroup = SecurityGroup.findOrCreateByIdentifier(awsSecGroup.groupId)
        if (!securityGroup.id) {
            securityGroup.name = awsSecGroup.groupName
            if (securityGroup.validate()) {
                securityGroup.save()
            } else {
                log.error "could not save SecutiryGroup, cause: $securityGroup.errors.allErrors"
                return null
            }
        }
        securityGroup
    }
}
