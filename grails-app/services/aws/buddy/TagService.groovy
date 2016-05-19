package aws.buddy

import grails.transaction.Transactional

@Transactional
class TagService {

    def getForAwsTag(com.amazonaws.services.ec2.model.Tag awsTag) {
        Tag tag = Tag.findOrCreateByTagKeyAndTagValue(awsTag.key, awsTag.value)
        if (!tag.id) {
            if(tag.validate()) {
                tag.save()
            } else {
                log.error "could not save Tag, cause: $tag.errors.allErrors"
                return null
            }
        }
        tag
    }
}
