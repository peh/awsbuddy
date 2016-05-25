package aws.buddy

import org.joda.time.DateTime

class Filter implements HasJsonBody {

    String identifier
    String name
    DateTime lastUpdated
    DateTime dateCreated

    static hasMany = [tags: Tag]

    static constraints = {
        identifier name: true
        identifier nullable: true
    }

    @Override
    def getJsonBody() {
        [
                id              : id,
                identifier      : identifier,
                name            : name,
                tags            : tags,
        ]
    }

}
