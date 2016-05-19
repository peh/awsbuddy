package aws.buddy

import grails.transaction.Transactional

@Transactional
class SubnetService {

    def getFromAws(String subnetId) {
        Subnet subnet = Subnet.findOrCreateByIdentifier(subnetId)
        if (!subnet.id) {
            if(subnet.validate()) {
                subnet.save()
            } else {
                log.error "could not save Subnet, cause: $Subnet.errors.allErrors"
                return null
            }
        }
        subnet
    }
}
