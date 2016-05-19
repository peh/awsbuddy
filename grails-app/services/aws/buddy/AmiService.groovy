package aws.buddy

import grails.transaction.Transactional

@Transactional
class AmiService {

    def getForIdentifier(String identifier) {
        Ami ami = Ami.findOrCreateByIdentifier(identifier)
        if (!ami.id)
            ami.save()

        ami
    }
}
