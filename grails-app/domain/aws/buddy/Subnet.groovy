package aws.buddy

import org.joda.time.DateTime

class Subnet implements HasJsonBody {

    String identifier
    DateTime dateCreated, lastUpdated

    static constraints = {
    }

    @Override
    def getJsonBody() {
        [id        : id,
         identifier: identifier]
    }
}
