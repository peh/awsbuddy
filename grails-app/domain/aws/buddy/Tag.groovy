package aws.buddy

import org.joda.time.DateTime

class Tag implements HasJsonBody {

    String tagKey
    String tagValue

    DateTime dateCreated

    static hasMany = [instances: Instance]
    static belongsTo = Instance

    static constraints = {
        tagKey unique: ['tagValue']
    }

    @Override
    def getJsonBody() {
        [id   : id,
         value: tagValue,
         key  : tagKey]
    }
}
