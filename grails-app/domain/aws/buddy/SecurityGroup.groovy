package aws.buddy

import org.joda.time.DateTime

class SecurityGroup implements HasJsonBody {

    String name
    String identifier

    DateTime dateCreated, lastUpdated

    static hasMany = [instance: Instance]
    static belongsTo = Instance

    static constraints = {
    }

    @Override
    def getJsonBody() {
        [id        : id,
         name      : name,
         identifier: identifier]
    }
}
